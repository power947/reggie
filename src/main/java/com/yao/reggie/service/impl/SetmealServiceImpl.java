package com.yao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.reggie.bean.Category;
import com.yao.reggie.bean.Setmeal;
import com.yao.reggie.bean.SetmealDish;
import com.yao.reggie.common.CustomerException;
import com.yao.reggie.dto.SetmealDto;
import com.yao.reggie.mapper.SetmealMapper;
import com.yao.reggie.service.CategoryService;
import com.yao.reggie.service.SetmealDishService;
import com.yao.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yao
 * @create 2022-11-07 16:36
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
     SetmealDishService setmealDishService;
    @Autowired
    CategoryService categoryService;
    @Override
    public void addSetmeal(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        dishes.forEach(item ->{
            item.setSetmealId(setmealDto.getId());
            setmealDishService.save(item);
        });
    }

    @Override
    public Page<SetmealDto> page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(name != null,Setmeal::getName,name);
//        queryWrapper.eq(Setmeal::getStatus,1);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        this.page(setmealPage,queryWrapper);
        BeanUtils.copyProperties(setmealPage,dtoPage,"record");
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> setmealDtoList = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Category category = categoryService.getById(item.getCategoryId());
            if(category != null){

                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(setmealDtoList);
        return dtoPage;
    }

    @Override
    public void delete(List<Long> ids) {
        //先判断套餐状态
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(setmealLambdaQueryWrapper);
        if (count > 0){
            throw new CustomerException("有套餐在售卖中，不能删除！");

        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper);
    }

    @Override
    public List<SetmealDto> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        Long categoryId = setmeal.getCategoryId();
        queryWrapper.eq(categoryId != null,Setmeal::getCategoryId,categoryId);
        List<Setmeal> list = this.list(queryWrapper);
        List<SetmealDto> collect = list.stream().map(item -> {
            SetmealDto setmealDto1 = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto1);
            return setmealDto1;
        }).collect(Collectors.toList());
        return collect;
    }
}
