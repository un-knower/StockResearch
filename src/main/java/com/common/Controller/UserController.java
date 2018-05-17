package com.common.Controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.po.UserPo;
import com.common.service.UserService;
import com.common.vo.ValuesVo;

@RestController
public class UserController{
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/user/queryOne")
    public UserPo queryOne(ValuesVo info) throws Exception {
        return userService.queryOne();
    }

}
