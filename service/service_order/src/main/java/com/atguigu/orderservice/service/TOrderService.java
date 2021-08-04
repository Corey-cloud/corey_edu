package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author corey
 * @since 2021-08-01
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String memberIdByJwtToken);
}
