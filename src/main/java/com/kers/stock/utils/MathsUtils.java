package com.kers.stock.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathsUtils {
	
	
	public  static double parseDouble(String input){
		try {
			return Double.parseDouble(input);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public static Double priceTrans(String str) {
		Double re = 0d;
		if(str.contains("亿")){
			str = str.replace("亿", "");
			if(isNumeric(str)){
				re = Double.parseDouble(str) * 100000000;
			}
		}
		if(str.contains("万")){
			str = str.replace("万", "");
			if(isNumeric(str)){
				re = Double.parseDouble(str) * 10000;
			}
		}
		if(str.contains("%")){
			str = str.replace("%", "");
			if(isNumeric(str)){
				re = Double.parseDouble(str) / 100 ;
			}
		}
		return re;
	}
	
	/**
     * 判断是否为数字(正负数都行)
     * @param str 需要验证的字符串
     * @return
     */
    public static boolean isNumeric(String str){ 
       Pattern pattern = Pattern.compile("^[\\+\\-]?[\\d]+(\\.[\\d]+)?$"); 
       Matcher isNum = pattern.matcher(str);
       if( !isNum.matches() ){
           return false; 
       } 
       return true; 
    }
	
	public static void main(String[] args) {
		System.out.println(priceTrans("26%"));
	}
}
