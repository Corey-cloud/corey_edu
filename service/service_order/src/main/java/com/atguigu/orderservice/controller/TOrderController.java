package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.orderservice.service.impl.TOrderServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    public TOrderService orderService;

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

        // 新建线程，延时处理该订单超时未支付的情况
        new Thread(
                ()->{
                    try {
                        Thread.sleep(5*60*60*1000);
                        System.out.println("order_no:"+orderId);
                        TOrder one1 = orderService.getOne(new QueryWrapper<TOrder>().eq("order_no", orderId).eq("status", 0));
                        System.out.println("one:"+one1);
                        if (one1 != null) {
                            orderService.update(new TOrder().setStatus(2), new UpdateWrapper<TOrder>().eq("order_no", orderId));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

        return R.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    @GetMapping("getMyOrderList/{page}/{limit}")
    public R getMyOrderList(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestParam String memberId) {
        Page<TOrder> pageParam = new Page<>(page, limit);
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        orderService.page(pageParam, wrapper);

        Map<String, Object> map = new HashMap<>();
        List<TOrder> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        long pages = pageParam.getPages();
        long current = pageParam.getCurrent();
        long size = pageParam.getSize();
        map.put("records", records);
        map.put("total", total);
        map.put("pages", pages);
        map.put("current", current);
        map.put("size", size);
        return R.ok().data(map);
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

    @PutMapping("/cancelOrder/{orderNo}")
    public R cancelOrder(@PathVariable String orderNo) {
        boolean flag = orderService.update(new TOrder().setStatus(3), new UpdateWrapper<TOrder>().eq("order_no", orderNo));
        if (flag) {
            return R.ok();
        }
        return R.error();
    }
}