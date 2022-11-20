package com.yao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.reggie.bean.Orders;

/**
 * @author yao
 * @create 2022-11-16 19:23
 */
public interface OrdersService extends IService<Orders> {
    void addOrder(Orders orders);
}
