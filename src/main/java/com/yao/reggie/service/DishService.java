package com.yao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.reggie.bean.Dish;
import com.yao.reggie.bean.DishFlavor;
import com.yao.reggie.common.R;
import com.yao.reggie.dto.DishDto;

import java.util.List;

/**
 * @author yao
 * @create 2022-11-07 16:34
 */
public interface DishService extends IService<Dish> {
    public DishDto selectDishAndFlavor(Long id);
    public void addDish(DishDto dishDto);
    void updateDish(DishDto dishDto);
    R<List<DishDto>> list(Dish dish);
}
