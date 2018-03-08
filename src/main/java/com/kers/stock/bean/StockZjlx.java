package com.kers.stock.bean;

import java.io.Serializable;

//http://data.eastmoney.com/zjlx/002466.html
//  主力控盘程度  http://data.eastmoney.com/stockcomment/002466.html
public class StockZjlx   implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private String stockCode;
	private String stockName;
	private  String date;
	 private double close;
	 private double rises;
	 private double zlNum;
	 private double zlRatio;
	 private double cddNum;
	 private double cddRatio;
	 private double ddNum;
	 private double ddRatio;
	 private double zdNum;
	 private double zdRatio;
	 private double xdNum;
	 private double xdRatio;
	 private int up3;
	 private int up5;
	 private int up10;
	 private int  upNum;//连涨天数 
	 private double ljlrNum;//累计流入资金
	 private double up3NumAvg;  //3天平均量
	 private double up5NumAvg;  //3天平均量
	 private double up10NumAvg;  //3天平均量
	 private double  zzRises;//增长率
	 private double zsz;//总市值
	 
	 private int   type ;//0 大盘  1 板块   2 个股 
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getRises() {
		return rises;
	}
	public void setRises(double rises) {
		this.rises = rises;
	}
	public double getZlNum() {
		return zlNum;
	}
	public void setZlNum(double zlNum) {
		this.zlNum = zlNum;
	}
	public double getZlRatio() {
		return zlRatio;
	}
	public void setZlRatio(double zlRatio) {
		this.zlRatio = zlRatio;
	}
	public double getCddNum() {
		return cddNum;
	}
	public void setCddNum(double cddNum) {
		this.cddNum = cddNum;
	}
	public double getCddRatio() {
		return cddRatio;
	}
	public void setCddRatio(double cddRatio) {
		this.cddRatio = cddRatio;
	}
	public double getDdNum() {
		return ddNum;
	}
	public void setDdNum(double ddNum) {
		this.ddNum = ddNum;
	}
	public double getDdRatio() {
		return ddRatio;
	}
	public void setDdRatio(double ddRatio) {
		this.ddRatio = ddRatio;
	}
	public double getZdNum() {
		return zdNum;
	}
	public void setZdNum(double zdNum) {
		this.zdNum = zdNum;
	}
	public double getZdRatio() {
		return zdRatio;
	}
	public void setZdRatio(double zdRatio) {
		this.zdRatio = zdRatio;
	}
	public double getXdNum() {
		return xdNum;
	}
	public void setXdNum(double xdNum) {
		this.xdNum = xdNum;
	}
	public double getXdRatio() {
		return xdRatio;
	}
	public void setXdRatio(double xdRatio) {
		this.xdRatio = xdRatio;
	}
	public StockZjlx(String date, double close, double rises, double zlNum, double zlRatio, double cddNum,
			double cddRatio, double ddNum, double ddRatio, double zdNum, double zdRatio, double xdNum, double xdRatio) {
		super();
		this.date = date;
		this.close = close;
		this.rises = rises;
		this.zlNum = zlNum;
		this.zlRatio = zlRatio;
		this.cddNum = cddNum;
		this.cddRatio = cddRatio;
		this.ddNum = ddNum;
		this.ddRatio = ddRatio;
		this.zdNum = zdNum;
		this.zdRatio = zdRatio;
		this.xdNum = xdNum;
		this.xdRatio = xdRatio;
	}
	public StockZjlx() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	public int getUp3() {
		return up3;
	}
	public void setUp3(int up3) {
		this.up3 = up3;
	}
	public int getUp5() {
		return up5;
	}
	public void setUp5(int up5) {
		this.up5 = up5;
	}
	public int getUp10() {
		return up10;
	}
	public void setUp10(int up10) {
		this.up10 = up10;
	}
	public int getUpNum() {
		return upNum;
	}
	public void setUpNum(int upNum) {
		this.upNum = upNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getUp3NumAvg() {
		return up3NumAvg;
	}
	public void setUp3NumAvg(double up3NumAvg) {
		this.up3NumAvg = up3NumAvg;
	}
	public double getUp5NumAvg() {
		return up5NumAvg;
	}
	public void setUp5NumAvg(double up5NumAvg) {
		this.up5NumAvg = up5NumAvg;
	}
	public double getUp10NumAvg() {
		return up10NumAvg;
	}
	public void setUp10NumAvg(double up10NumAvg) {
		this.up10NumAvg = up10NumAvg;
	}
	public double getZzRises() {
		return zzRises;
	}
	public void setZzRises(double zzRises) {
		this.zzRises = zzRises;
	}
	public double getLjlrNum() {
		return ljlrNum;
	}
	public void setLjlrNum(double ljlrNum) {
		this.ljlrNum = ljlrNum;
	}
	public double getZsz() {
		return zsz;
	}
	public void setZsz(double zsz) {
		this.zsz = zsz;
	}
	
}
