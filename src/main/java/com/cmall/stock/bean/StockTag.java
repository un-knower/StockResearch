package com.cmall.stock.bean;

public class StockTag {

	private String tagType;
	private String tagName;
	private String stockCode;
	private String stockName;
	private String tagExpTime;// tag有效期

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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

	public String getTagExpTime() {
		return tagExpTime;
	}

	public void setTagExpTime(String tagExpTime) {
		this.tagExpTime = tagExpTime;
	}

}
