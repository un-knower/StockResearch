package com.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.common.po.UserPo;

@Mapper
public interface UserMapper {
	UserPo queryOne();
}
