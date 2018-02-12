package com.cmall.stock.bean;

import java.io.Serializable;

//http://data.eastmoney.com/zjlx/002466.html
//  主力控盘程度  http://data.eastmoney.com/stockcomment/002466.html
public class StockZjlx   implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private  String date;
	 private String close;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
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
	 
	
	
}
