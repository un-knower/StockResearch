package com.kers.stock.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 股票code转换类
 * @author temp1
 *
 */
public class StockCodeTransUtil {
	
	public static String TransSzOrSh(String stockCode) {
		if(StringUtils.isEmpty(stockCode)){
			return stockCode;
		}
		if(stockCode.split("")[0].equals("0") || stockCode.split("")[0].equals("3")){
			stockCode = "sz" + stockCode;
		}else{
			stockCode = "sh" + stockCode;
		}
		return stockCode;
    }
	
	
}
