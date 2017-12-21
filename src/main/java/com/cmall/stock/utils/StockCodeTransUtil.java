package com.cmall.stock.utils;

import com.cmall.baseutils.StringUtil;

/**
 * 股票code转换类
 * @author temp1
 *
 */
public class StockCodeTransUtil {
	
	public static String TransSzOrSh(String stockCode) {
		if(StringUtil.isNull(stockCode)){
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
