package com.yao.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.reggie.bean.Setmeal;
import com.yao.reggie.dto.SetmealDto;

import java.util.List;

/**
 * @author yao
 * @create 2022-11-13 16:26
 */
public interface SetmealService extends IService<Setmeal> {
    void addSetmeal(SetmealDto setmealDto);
    Page<SetmealDto> page(int page,int pageSize,String name);
    void delete(List<Long> ids);
    List<SetmealDto> list(Setmeal setmeal);
}
