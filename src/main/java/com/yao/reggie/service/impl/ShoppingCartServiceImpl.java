package com.yao.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.reggie.bean.ShoppingCart;
import com.yao.reggie.mapper.ShoppingCartMapper;
import com.yao.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author yao
 * @create 2022-11-15 15:50
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
