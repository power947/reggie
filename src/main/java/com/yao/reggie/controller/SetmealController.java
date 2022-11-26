package com.yao.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.reggie.bean.Setmeal;
import com.yao.reggie.common.R;
import com.yao.reggie.dto.SetmealDto;
import com.yao.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yao
 * @create 2022-11-13 16:31
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> add(@RequestBody SetmealDto setmealDto){
        setmealService.addSetmeal(setmealDto);
        return R.success("添加套餐成功！");

    }
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page,int pageSize,String name){
        Page<SetmealDto> setmealDtoPage = setmealService.page(page, pageSize, name);
        return R.success(setmealDtoPage);

    }
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.delete(ids);
        return R.success("删除成功！");
    }
    @PostMapping("/status/{status}")
    public R<String> stopOrStart(@PathVariable String status,@RequestParam List<Long> ids){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> list = setmealService.list(queryWrapper);
        if("1".equals(status)){
//            log.info(ids);

            list.forEach(item ->{
                item.setStatus(1);
                setmealService.updateById(item);
            });
//            setmealService.update(queryWrapper);
        }else{

            list.forEach(item ->{
                item.setStatus(0);
                setmealService.updateById(item);
            });
        }
//        log.info(String.valueOf(ids));
        return R.success("s");
    }
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<SetmealDto>> list(Setmeal setmeal){

        List<SetmealDto> list = setmealService.list(setmeal);
        return R.success(list);

    }
}
