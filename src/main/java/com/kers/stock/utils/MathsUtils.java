package com.kers.stock.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class MathsUtils {
	
	
	public  static double parseDouble(String input){
		try {
			return Double.parseDouble(input);
		} catch (Exception e) {
			//e.printStackTrace();
			// TODO: handle exception
		}
		return 0;
	}
	
	public  static float parseFloat(String input){
		try {
			return Float.parseFloat(input);
		} catch (Exception e) {
			//e.printStackTrace();
			// TODO: handle exception
		}
		return 0;
	}
	
	public  static double parseDoubleStockWy(Object input){
		try {
			return Double.parseDouble(input.toString())*10000;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	public  static double parseDoubleStockYy(Object input){
		try {
			return Double.parseDouble(input.toString())*100000000;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	public  static double parseDoubleStockWyFormat(Object input){
		try {
			if( input.toString().contains("万")){
				return Double.parseDouble(input.toString().replace("万", ""))*10000;
			}else if ( input.toString().contains("亿")){
				return Double.parseDouble(input.toString().replace("亿", ""))*100000000;
			}else 
				return Double.parseDouble(input.toString());
			
		} catch (Exception e) {
			//e.printStackTrace();
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
	
    public static boolean objIsEmpty(String str){
		if(StringUtils.isEmpty(str) || str.equals("0")||str.equals("None") ){
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println(priceTrans("26%"));
	}
}
