package com.cmall.stock.utils;

public class MathsUtils {
	
	
	public  static double parseDouble(String input){
		try {
			return Double.parseDouble(input);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

}
