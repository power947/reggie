package com.yao.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.reggie.bean.Employee;
import com.yao.reggie.mapper.EmployeeMapper;
import com.yao.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author yao
 * @create 2022-10-29 15:37
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
