package com.yao.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.reggie.bean.Category;
import com.yao.reggie.bean.Dish;
import com.yao.reggie.bean.DishFlavor;
import com.yao.reggie.common.R;
import com.yao.reggie.dto.DishDto;
import com.yao.reggie.service.CategoryService;
import com.yao.reggie.service.DishFlavorService;
import com.yao.reggie.service.DishService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yao
 * @create 2022-11-12 15:38
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishFlavorService dishFlavorService;
    @GetMapping("/page")
    public R<Page<Dish>> page(int page,int pageSize,String name){
        Page<Dish> dishPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage, queryWrapper);
        Page dishDtoPage = new Page<DishDto>();
        BeanUtils.copyProperties(dishPage,dishDtoPage,"record");//将dishPage拷贝到dishDtoPage里除了record属性，因为record要进行处理
        //record是查到的数据
        List<Dish> records = dishPage.getRecords();
        List<DishDto> dtoList = records.stream().map(item -> {//steam流
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();//拿到菜品id
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);//进行名字赋值
            return dishDto;

        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dtoList);//将record的字段赋值
        return R.success(dishDtoPage);
    }
    @PostMapping
    public R<String> add(@RequestBody DishDto dishDto){
        dishService.addDish(dishDto);
        return R.success("add successfully");


    }
    @GetMapping("/{id}")
    public R<DishDto> select(@PathVariable Long id){
        DishDto dishDto = dishService.selectDishAndFlavor(id);
        return R.success(dishDto);
    }
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){

       dishService.updateDish(dishDto);
       return R.success("修改成功！");
    }
//    @GetMapping("/list") //按照dish属性查找dish
//    public R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
//        queryWrapper.eq(Dish::getStatus,1);
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = dishService.list(queryWrapper);
//        return R.success(list);
//    }
    @GetMapping("/list") //按照dish属性查找dish
    public R<List<DishDto>> list(Dish dish){
        R<List<DishDto>> list = dishService.list(dish);
        return list;
    }
}
