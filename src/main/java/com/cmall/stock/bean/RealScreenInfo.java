package com.cmall.stock.bean;

import java.io.Serializable;

public class RealScreenInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stockCode;
	private String stockName;
	
	//现在价格
    private double price;
    //涨幅
    private double rises;
    //开盘价
    private double open;
    //最高价
  	private double high;
  	//最低价
    private double low;
    //昨天的收盘价
    private double yestclose;
    //现在的量
    private int volume;
    //量比
    private double volumeRises;
	private String createDate;
	private String updateDate;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRises() {
		return rises;
	}
	public void setRises(double rises) {
		this.rises = rises;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getYestclose() {
		return yestclose;
	}
	public void setYestclose(double yestclose) {
		this.yestclose = yestclose;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public double getVolumeRises() {
		return volumeRises;
	}
	public void setVolumeRises(double volumeRises) {
		this.volumeRises = volumeRises;
	}
	
	
}
