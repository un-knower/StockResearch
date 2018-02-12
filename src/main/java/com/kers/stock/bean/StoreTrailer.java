package com.kers.stock.bean;

import java.io.Serializable;

public class StoreTrailer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4906076360248153256L;
	private String stockCode;
	private String stockName;
	// 业绩变动
	private String perChanges;
	// 业绩变化幅度
	private String rangeability;

	private Double startRangeability;

	private Double endRangeability;
	// 预测类型
	private String type;
	// 上年同期净利润
	private double  netProfit;
	private double jlr;
	// 开始时间
	private String startDate;
	// 结束时间
	private String endDate;
	// 市盈率
	private double pe;
	// 财报后的市盈率
	private double npe;
	// 变化说明
	private String chagDetail;
	private String peRange;

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

	public double getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(double netProfit) {
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

	public double getJlr() {
		return jlr;
	}

	public void setJlr(double jlr) {
		this.jlr = jlr;
	}

	public double getPe() {
		return pe;
	}

	public void setPe(double pe) {
		this.pe = pe;
	}

	public double getNpe() {
		return npe;
	}

	public void setNpe(double npe) {
		this.npe = npe;
	}

	public String getChagDetail() {
		return chagDetail;
	}

	public void setChagDetail(String chagDetail) {
		this.chagDetail = chagDetail;
	}

	public String getPeRange() {
		return peRange;
	}

	public void setPeRange(String peRange) {
		this.peRange = peRange;
	}

}
