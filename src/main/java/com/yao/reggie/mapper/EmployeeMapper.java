package com.yao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.reggie.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yao
 * @create 2022-10-29 15:34
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
