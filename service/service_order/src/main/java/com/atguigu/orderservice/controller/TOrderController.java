package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/orderservice/order")
//@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        String orderId = orderService.saveOrder(courseId, memberId);
        System.out.println("orderId:"+orderId);
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        System.out.println("item:"+order);
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

