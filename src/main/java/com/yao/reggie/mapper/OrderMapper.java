package com.yao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.reggie.bean.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yao
 * @create 2022-11-16 19:21
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
