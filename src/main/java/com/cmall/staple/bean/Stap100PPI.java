package com.cmall.staple.bean;

import java.io.Serializable;

public class Stap100PPI implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商品 行业 月初价格 月末价格 单位 月涨跌 同比涨跌
	private String rq;
	private String productName;
	private String productHy;
	private double monthYcPrice;
	private double monthYmPrice;
	private String priceDw;
	private double monthRise;
	private double tbRise;
	//7天涨幅
	private double wtzf;
	//月涨幅
	private double yfzf;
	//季度涨幅
	private double jdzf;

	
	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getMonthYcPrice() {
		return monthYcPrice;
	}

	public void setMonthYcPrice(double monthYcPrice) {
		this.monthYcPrice = monthYcPrice;
	}

	public double getMonthYmPrice() {
		return monthYmPrice;
	}

	public void setMonthYmPrice(double monthYmPrice) {
		this.monthYmPrice = monthYmPrice;
	}

	public String getPriceDw() {
		return priceDw;
	}

	public void setPriceDw(String priceDw) {
		this.priceDw = priceDw;
	}

	public double getMonthRise() {
		return monthRise;
	}

	public void setMonthRise(double monthRise) {
		this.monthRise = monthRise;
	}

	public double getTbRise() {
		return tbRise;
	}

	public void setTbRise(double tbRise) {
		this.tbRise = tbRise;
	}

	public Stap100PPI(String productName, String productHy,double monthYcPrice, double monthYmPrice, String priceDw, double monthRise,
			double tbRise) {
		super();
		this.productName = productName;
		this.monthYcPrice = monthYcPrice;
		this.monthYmPrice = monthYmPrice;
		this.priceDw = priceDw;
		this.monthRise = monthRise;
		this.tbRise = tbRise;
		this.productHy=productHy;
	}

	public Stap100PPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProductHy() {
		return productHy;
	}

	public void setProductHy(String productHy) {
		this.productHy = productHy;
	}

	public double getWtzf() {
		return wtzf;
	}

	public void setWtzf(double wtzf) {
		this.wtzf = wtzf;
	}

	public double getYfzf() {
		return yfzf;
	}

	public void setYfzf(double yfzf) {
		this.yfzf = yfzf;
	}

	public double getJdzf() {
		return jdzf;
	}

	public void setJdzf(double jdzf) {
		this.jdzf = jdzf;
	}
	
	

}
