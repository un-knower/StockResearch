package com.cmall.stock.bean;

import java.io.Serializable;

public class StoreTrailer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4906076360248153256L;
	private String stockCode;
	private String stockName;
	//业绩变动
	private String perChanges;
	//业绩变化幅度
	private String rangeability;
	
	private Double startRangeability;
	
	private Double endRangeability;
	//预测类型
	private String type;
	//净利润
	private String netProfit;
	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
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
	public String getPerChanges() {
		return perChanges;
	}
	public void setPerChanges(String perChanges) {
		this.perChanges = perChanges;
	}
	public String getRangeability() {
		return rangeability;
	}
	public void setRangeability(String rangeability) {
		this.rangeability = rangeability;
	}
	public Double getStartRangeability() {
		return startRangeability;
	}
	public void setStartRangeability(Double startRangeability) {
		this.startRangeability = startRangeability;
	}
	public Double getEndRangeability() {
		return endRangeability;
	}
	public void setEndRangeability(Double endRangeability) {
		this.endRangeability = endRangeability;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNetProfit() {
		return netProfit;
	}
	public void setNetProfit(String netProfit) {
		this.netProfit = netProfit;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
	
}
