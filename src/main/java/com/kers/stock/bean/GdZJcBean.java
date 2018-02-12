package com.kers.stock.bean;

import java.io.Serializable;

public class GdZJcBean   implements Serializable{
	/**    
	 * serialVersionUID:TODO      
	 */    
	
	private static final long serialVersionUID = 1L;
	private String stockCode;
	public String stockName;
	// 相关公告
	private String stockczType;
	// 最新价
	private double stockCurrPrice;
	// 跌涨幅
	private double stockRise;
	// 股东名称
	private String shareHolderName;
	// 增减持类型;
	private String zjcType;
	// 持股变动信息_____变动数量(万股)
	private double zjcNum;
	// 持股变动信息_____占总股本比例
	private double zjcZGBRatio;
	// 持股变动信息_____占流通股比例
	private double zjcLGTRatio;
	// 变动后持股情况____持股总数(万股)
	private double zjcNumLater;
	// 变动后持股情况____占总股本比例
	private double zjcZGBRatioLater;
	// 变动后持股情况____持流通股数(万股)
	private double zjcLGTNumLater;
	// 变动后持股情况____占流通股比例
	private double zjcLGTRatioLater;

	// 变动开始日
	private String bdrq;
	// 变动截止日
	private String bdzzrq;
	// 公告日
	private String reportDate;

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

 
	public double getStockCurrPrice() {
		return stockCurrPrice;
	}

	public void setStockCurrPrice(double stockCurrPrice) {
		this.stockCurrPrice = stockCurrPrice;
	}

	public double getStockRise() {
		return stockRise;
	}

	public void setStockRise(double stockRise) {
		this.stockRise = stockRise;
	}

	public String getShareHolderName() {
		return shareHolderName;
	}

	public void setShareHolderName(String shareHolderName) {
		this.shareHolderName = shareHolderName;
	}

	public String getZjcType() {
		return zjcType;
	}

	public void setZjcType(String zjcType) {
		this.zjcType = zjcType;
	}

	public double getZjcNum() {
		return zjcNum;
	}

	public void setZjcNum(double zjcNum) {
		this.zjcNum = zjcNum;
	}

	public double getZjcZGBRatio() {
		return zjcZGBRatio;
	}

	public void setZjcZGBRatio(double zjcZGBRatio) {
		this.zjcZGBRatio = zjcZGBRatio;
	}

	public double getZjcLGTRatio() {
		return zjcLGTRatio;
	}

	public void setZjcLGTRatio(double zjcLGTRatio) {
		this.zjcLGTRatio = zjcLGTRatio;
	}

	public double getZjcNumLater() {
		return zjcNumLater;
	}

	public void setZjcNumLater(double zjcNumLater) {
		this.zjcNumLater = zjcNumLater;
	}

	public double getZjcZGBRatioLater() {
		return zjcZGBRatioLater;
	}

	public void setZjcZGBRatioLater(double zjcZGBRatioLater) {
		this.zjcZGBRatioLater = zjcZGBRatioLater;
	}

	public double getZjcLGTNumLater() {
		return zjcLGTNumLater;
	}

	public void setZjcLGTNumLater(double zjcLGTNumLater) {
		this.zjcLGTNumLater = zjcLGTNumLater;
	}

	public double getZjcLGTRatioLater() {
		return zjcLGTRatioLater;
	}

	public void setZjcLGTRatioLater(double zjcLGTRatioLater) {
		this.zjcLGTRatioLater = zjcLGTRatioLater;
	}

	public String getBdrq() {
		return bdrq;
	}

	public void setBdrq(String bdrq) {
		this.bdrq = bdrq;
	}

	public String getBdzzrq() {
		return bdzzrq;
	}

	public void setBdzzrq(String bdzzrq) {
		this.bdzzrq = bdzzrq;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public GdZJcBean(String stockCode, String stockName, double stockCurrPrice, double stockRise,
			String shareHolderName,  String zjcType, double zjcNum, double zjcZGBRatio,double zjcLGTRatio, String stockczType,
			double zjcNumLater, double zjcZGBRatioLater, double zjcLGTNumLater, double zjcLGTRatioLater, String bdrq,
			String bdzzrq, String reportDate) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.stockczType = stockczType;
		this.stockCurrPrice = stockCurrPrice;
		this.stockRise = stockRise;
		this.shareHolderName = shareHolderName;
		this.zjcType = zjcType;
		this.zjcNum = zjcNum;
		this.zjcZGBRatio = zjcZGBRatio;
		this.zjcLGTRatio = zjcLGTRatio;
		this.zjcNumLater = zjcNumLater;
		this.zjcZGBRatioLater = zjcZGBRatioLater;
		this.zjcLGTNumLater = zjcLGTNumLater;
		this.zjcLGTRatioLater = zjcLGTRatioLater;
		this.bdrq = bdrq;
		this.bdzzrq = bdzzrq;
		this.reportDate = reportDate;
	}

	public String getStockczType() {
		return stockczType;
	}

	public void setStockczType(String stockczType) {
		this.stockczType = stockczType;
	}

	public GdZJcBean() {
		super();
	}
	

}
