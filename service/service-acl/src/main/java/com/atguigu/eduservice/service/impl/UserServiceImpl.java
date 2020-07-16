package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.User;
import com.atguigu.eduservice.mapper.UserMapper;
import com.atguigu.eduservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
