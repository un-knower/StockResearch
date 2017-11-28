package com.cmall.stock.bean;

import java.io.Serializable;

/**
 * 策略
 * @author temp1
 *
 */
public class StockStrategyInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stockCode;
	private String stockName;
	private String date;
	//策略类型 1.成交量翻倍
	private int type;
	//拓展值1 (当type为1的时候,放上一天的成交量)
	private float f1;
	//拓展值2 (当type为1的时候,放当天的成交量)
	private float f2;
	//拓展值3 (当type为1的时候,成交量翻倍比)
	private float f3;
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getF1() {
		return f1;
	}
	public void setF1(float f1) {
		this.f1 = f1;
	}
	public float getF2() {
		return f2;
	}
	public void setF2(float f2) {
		this.f2 = f2;
	}
	public float getF3() {
		return f3;
	}
	public void setF3(float f3) {
		this.f3 = f3;
	}
	
	
}
