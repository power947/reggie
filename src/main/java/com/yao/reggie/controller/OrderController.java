package com.yao.reggie.controller;

import com.yao.reggie.bean.Orders;
import com.yao.reggie.common.R;
import com.yao.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yao
 * @create 2022-11-16 19:27
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrdersService ordersService;
    /*
        *@author yao
        *@description 提交订单
        *@date 2022/11/18 12:25
        *@return
        */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.addOrder(orders);
        return R.success("提交订单成功！");
    }

}
