package com.yao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.reggie.bean.Dish;
import com.yao.reggie.bean.DishFlavor;
import com.yao.reggie.common.R;
import com.yao.reggie.dto.DishDto;
import com.yao.reggie.mapper.DishMapper;
import com.yao.reggie.service.DishFlavorService;
import com.yao.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yao
 * @create 2022-11-07 16:35
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public DishDto selectDishAndFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    public void addDish(DishDto dishDto) {
        this.save(dishDto);
        String name = dishDto.getName();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(name != null,Dish::getName,name);
        Dish dish = this.getOne(queryWrapper);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(item ->{
            item.setDishId(id);
            dishFlavorService.save(item);
        });
        String key = "dish_"+ dishDto.getCategoryId()  + "_1";
        redisTemplate.delete(key);
    }

    @Override
    public void updateDish(DishDto dishDto) {
        Long categoryId = dishDto.getCategoryId();
        String key = "dish_"+ categoryId  + "_1";
        redisTemplate.delete(key);
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        this.updateById(dish); //保存dish
        List<DishFlavor> flavors = dishDto.getFlavors();
        Long id = dish.getId();
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null,DishFlavor::getDishId,id);
        dishFlavorService.remove(queryWrapper);
        flavors.forEach(item ->{
            item.setDishId(id);
            dishFlavorService.save(item); //保存口味
        });
    }

    @Override
    public R<List<DishDto>> list(Dish dish) {
        //设置key值
        String key = "dish_"+ dish.getCategoryId() + "_" + dish.getStatus();//dish_2132323_1
        //从Redis查有没有菜品数据
        List<DishDto> dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        //有数据就不查
        if(dishDtoList != null){
            return R.success(dishDtoList);
        }
        //没数据就查询
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = this.list(queryWrapper);
        dishDtoList = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> flavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            flavorLambdaQueryWrapper.eq(id != null,DishFlavor::getDishId,id);
            List<DishFlavor> dishFlavors = dishFlavorService.list(flavorLambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());
        //没数据将数据存到Redis
        redisTemplate.opsForValue().set(key,dishDtoList,45, TimeUnit.MINUTES);
        return R.success(dishDtoList);
    }

}
