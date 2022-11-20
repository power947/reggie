package com.yao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.reggie.bean.Category;

/**
 * @author yao
 * @create 2022-11-07 15:06
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
