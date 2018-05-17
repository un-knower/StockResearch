package com.ifdiv.demo;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.ifdiv.sms.request.SmsSendRequest;
import com.ifdiv.sms.response.SmsSendResponse;
import com.ifdiv.sms.util.ChuangLanSmsUtil;
/**
 * 
 * @author tianyh 
 * @Description:普通短信发送
 */
public class SmsSendDemo {

	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N8722031";
	// 用户平台API密码(非登录密码)
	public static String pswd = "Pj1jJqcLjzpyKXJa";

	public static void main(String[] args) throws UnsupportedEncodingException {

		//请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
		String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
		// 短信内容
	    String msg = "【253云通讯】你好,你的验证码是123456";
		//手机号码
		String phone = "15807155045";
		//状态报告
		String report= "true";
		
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);
		
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		System.out.println("before request string is: " + requestJson);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		
		System.out.println("response after request result is :" + response);
		
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);
		
	
	}

	public static String sendM(String phoneNo , String code){
		//请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
				String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
				// 短信内容
			    String msg = "【IF定制】"+code+"（IF定制手机验证码，请完成验证）， 如非本人操作，请忽略本短信。";
				//手机号码
				String phone = phoneNo;
				//状态报告
				String report= "true";
				
				SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);
				
				String requestJson = JSON.toJSONString(smsSingleRequest);
				
				System.out.println("before request string is: " + requestJson);
				
				String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
				
				System.out.println("before request string is: " + response);
				return response;
	}
}
