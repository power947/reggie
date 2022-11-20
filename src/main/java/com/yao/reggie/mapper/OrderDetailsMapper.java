package com.yao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.reggie.bean.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yao
 * @create 2022-11-16 19:22
 */
@Mapper
public interface OrderDetailsMapper extends BaseMapper<OrderDetail> {
}
