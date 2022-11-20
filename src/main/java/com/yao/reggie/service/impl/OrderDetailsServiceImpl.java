package com.yao.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.reggie.bean.OrderDetail;
import com.yao.reggie.mapper.OrderDetailsMapper;
import com.yao.reggie.service.OrderDetailsService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * @author yao
 * @create 2022-11-16 19:26
 */
@Service
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetail> implements OrderDetailsService {
}
