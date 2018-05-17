package com.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.mapper.UserMapper;
import com.common.po.UserPo;
import com.common.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private UserMapper userMapper;
	
	public UserPo queryOne() {
		return userMapper.queryOne();
	}

}
