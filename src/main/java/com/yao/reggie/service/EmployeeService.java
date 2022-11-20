package com.yao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.reggie.bean.Employee;
import com.yao.reggie.bean.SetmealDish;

/**
 * @author yao
 * @create 2022-10-29 15:36
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * @author yao
     * @create 2022-11-13 16:23
     */
    interface SetmealDishService extends IService<SetmealDish> {
    }
}
