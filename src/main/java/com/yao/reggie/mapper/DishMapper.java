package com.yao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.reggie.bean.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yao
 * @create 2022-11-07 16:32
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
