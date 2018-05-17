package com.common.po;

import java.io.Serializable;

public class UserPo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nickName;
	
	private String phone;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserPo [nickName=" + nickName + ", phone=" + phone + "]";
	}
	
	

}
