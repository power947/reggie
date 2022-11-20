package com.yao.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yao.reggie.bean.Dish;
import com.yao.reggie.bean.ShoppingCart;
import com.yao.reggie.common.BaseContext;
import com.yao.reggie.common.R;
import com.yao.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author yao
 * @create 2022-11-15 15:51
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    /*
        *@author yao
        *@description 添加购物车
        *@date 2022/11/18 12:22
        *@return
        */
    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
//        shoppingCart.setUserId((Long) request.getSession().getAttribute("user"));
         boolean  IsFlag = false;
         shoppingCart.setUserId(BaseContext.get());
        if(shoppingCart.getDishId() != null){
            Long dishId = shoppingCart.getDishId();
            LambdaQueryWrapper<ShoppingCart> q = new LambdaQueryWrapper<>();
            q.eq(ShoppingCart::getDishId,dishId);
            q.eq(ShoppingCart::getUserId,BaseContext.get());
            List<ShoppingCart> list = shoppingCartService.list(q);
            for (ShoppingCart item : list) {
                String dishFlavor = item.getDishFlavor();
                if(shoppingCart.getDishFlavor() != null){
                    boolean equals = dishFlavor.equals(shoppingCart.getDishFlavor());
                    if (equals){
                        item.setNumber(item.getNumber() + 1); //如果有一样的就加num
                        shoppingCartService.updateById(item);
                        IsFlag = true;
                        break;
                    }
                }else{
                    item.setNumber(item.getNumber() + 1); //如果有一样的就加num
                    shoppingCartService.updateById(item);
                    IsFlag = true;
                }

            }
            if(!IsFlag){

                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCartService.save(shoppingCart);
            }
        }
        if(shoppingCart.getSetmealId() != null){
            Long setmealId = shoppingCart.getSetmealId();//
            LambdaQueryWrapper<ShoppingCart> q = new LambdaQueryWrapper<>();
            q.eq(ShoppingCart::getSetmealId,setmealId);
            ShoppingCart one = shoppingCartService.getOne(q);
            if(one != null){
                one.setNumber(one.getNumber() + 1);
                shoppingCartService.updateById(one);
            }else{
                shoppingCartService.save(shoppingCart);
            }


        }
        return R.success("cg");
    }
    /*
        *@author yao
        *@description 获取当前用户的购物车数据
        *@date 2022/11/18 12:22
        *@return
        */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpServletRequest request){
        Long userId = (Long) request.getSession().getAttribute("user");
        log.info("user" + userId);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null,ShoppingCart::getUserId,userId);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }
    /*
        *@author yao
        *@description 清空购物车
        *@date 2022/11/18 12:24
        *@return
        */
    @DeleteMapping("/clean")
    public R<String> delete(){
        Long aLong = BaseContext.get();
        log.info(aLong.toString());
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(aLong != null,ShoppingCart::getUserId,aLong);
        shoppingCartService.remove(queryWrapper);
        return R.success("清理购物车");
    }
    /*
        *@author yao
        *@description 减少购物车商品数量
        *@date 2022/11/18 12:24
        *@return
        */
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if(shoppingCart.getDishId() != null){
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
            queryWrapper.eq(ShoppingCart::getUserId,BaseContext.get());
            ShoppingCart one = shoppingCartService.getOne(queryWrapper);
            if (one.getNumber() == 1){
                Long id = one.getId();
                shoppingCartService.removeById(id);
            }else{
                one.setNumber(one.getNumber() - 1);
                shoppingCartService.updateById(one);
            }
        }
        if(shoppingCart.getSetmealId() != null){
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            ShoppingCart one = shoppingCartService.getOne(queryWrapper);
            if (one.getNumber() == 1){
                Long id = one.getId();
                shoppingCartService.removeById(id);
            }else{
                one.setNumber(one.getNumber() - 1);
                shoppingCartService.updateById(one);
            }
        }
        return R.success("减少成功");

    }
 }
