package com.kers.stock.bean.jyfx;

import java.io.Serializable;

public class JyfxInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stockCode;
	private String stockName;
	//日期
	private String rq;
	
	//类型  1是按行业分类，2是按产品分类，3是按地区分类
	private int type;
	
	//主营构成
	private String zygc;
	//主营收入(元)
	private double zysr;
	//收入比例
	private double srbl;
	//主营成本(元)
	private double zycb;
	//成本比例
	private double cbbl;
	//主营利润(元)
	private double zylr;
	//利润比例
	private double lrbl;
	//毛利率(%)
	private double mll;
	//单位
	private String dw;
	//排序
	private double orderby;
	
	//主营范围
	private String zyfwms;
		
	//经营评述
	private String jypsms;
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
	public String getZyfwms() {
		return zyfwms;
	}
	public void setZyfwms(String zyfwms) {
		this.zyfwms = zyfwms;
	}
	public String getJypsms() {
		return jypsms;
	}
	public void setJypsms(String jypsms) {
		this.jypsms = jypsms;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getZygc() {
		return zygc;
	}
	public void setZygc(String zygc) {
		this.zygc = zygc;
	}
	
	public double getZysr() {
		return zysr;
	}
	public void setZysr(double zysr) {
		this.zysr = zysr;
	}
	public double getSrbl() {
		return srbl;
	}
	public void setSrbl(double srbl) {
		this.srbl = srbl;
	}
	public double getZycb() {
		return zycb;
	}
	public void setZycb(double zycb) {
		this.zycb = zycb;
	}
	public double getCbbl() {
		return cbbl;
	}
	public void setCbbl(double cbbl) {
		this.cbbl = cbbl;
	}
	public double getZylr() {
		return zylr;
	}
	public void setZylr(double zylr) {
		this.zylr = zylr;
	}
	public double getLrbl() {
		return lrbl;
	}
	public void setLrbl(double lrbl) {
		this.lrbl = lrbl;
	}
	public double getMll() {
		return mll;
	}
	public void setMll(double mll) {
		this.mll = mll;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public double getOrderby() {
		return orderby;
	}
	public void setOrderby(double orderby) {
		this.orderby = orderby;
	}
	
	
}
