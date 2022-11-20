package com.yao.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.reggie.bean.User;
import com.yao.reggie.mapper.UserMapper;
import com.yao.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author yao
 * @create 2022-11-17 21:32
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
