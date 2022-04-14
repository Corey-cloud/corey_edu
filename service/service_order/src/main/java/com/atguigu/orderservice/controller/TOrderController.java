package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.orderservice.service.impl.TOrderServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author corey
 * @since 2021-08-01
 */
@RestController
@RequestMapping("/order")
//@CrossOrigin
public class TOrderController {

    public static String courseId;
    public static String memberId;

    @Autowired
    private TOrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        boolean token = JwtUtils.checkToken(request);
        if (!token) {
            return R.error().code(28004).message("请登录");
        }
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        TOrder one = orderService.getOne(new QueryWrapper<TOrder>().eq("course_id", courseId).eq("member_id", memberId).eq("status", 0));
        if (one != null) {
            return R.error().code(28001).message("当前存在已创建待支付的订单，请稍候再试");
        }
        String orderId = orderService.saveOrder(courseId, memberId);
        TOrderController.courseId = courseId;
        TOrderController.memberId = memberId;
        // 新建线程，延时处理该订单超时未支付的情况
        new Thread(new DelayTask()).start();

        return R.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId,
                               @PathVariable String courseId) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<TOrder>().eq("member_id",
                memberId).eq("course_id", courseId).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }
}

@Component
class DelayTask implements Runnable {

    @Autowired
    private TOrderService orderService;

    public DelayTask() {
        this.courseId = TOrderController.courseId;
        this.memberId = TOrderController.memberId;
    }

    private final String courseId;
    private final String memberId;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(10*1000);
        TOrder one = orderService.getOne(new QueryWrapper<TOrder>().eq("course_id", courseId).eq("member_id", memberId).eq("status", 0));
        System.out.println("one:"+one);
        if (one != null) {
            orderService.update(new TOrder().setStatus(2), new UpdateWrapper<TOrder>().eq("course_id", courseId).eq("member_id", memberId));
        }
    }
}