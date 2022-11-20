package com.yao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.reggie.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yao
 * @create 2022-11-17 21:31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
