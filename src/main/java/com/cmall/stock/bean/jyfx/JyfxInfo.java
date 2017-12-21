package com.cmall.stock.bean.jyfx;

import java.io.Serializable;

public class JyfxInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stockCode;
	private String stockName;
	//主营范围
	private String zyfwms;
	
	//经营评述
	private String jypsms;
	
	//日期
	private String rq;
	
	//类型  1是按行业分类，2是按产品分类，3是按地区分类
	private int type;
	
	//主营构成
	private String zygc;
	//主营收入(元)
	private String zysr;
	//收入比例
	private String srbl;
	//主营成本(元)
	private String zycb;
	//成本比例
	private String cbbl;
	//主营利润(元)
	private String zylr;
	//利润比例
	private String lrbl;
	//毛利率(%)
	private String mll;
	//单位
	private String dw;
	//排序
	private double orderby;
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
	public String getZysr() {
		return zysr;
	}
	public void setZysr(String zysr) {
		this.zysr = zysr;
	}
	public String getSrbl() {
		return srbl;
	}
	public void setSrbl(String srbl) {
		this.srbl = srbl;
	}
	public String getZycb() {
		return zycb;
	}
	public void setZycb(String zycb) {
		this.zycb = zycb;
	}
	public String getCbbl() {
		return cbbl;
	}
	public void setCbbl(String cbbl) {
		this.cbbl = cbbl;
	}
	public String getZylr() {
		return zylr;
	}
	public void setZylr(String zylr) {
		this.zylr = zylr;
	}
	public String getLrbl() {
		return lrbl;
	}
	public void setLrbl(String lrbl) {
		this.lrbl = lrbl;
	}
	public String getMll() {
		return mll;
	}
	public void setMll(String mll) {
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
