package com.cmall.stock.bean;

import java.util.List;

import com.google.common.collect.Lists;

public class EastReportBean {
	// 股票代码[0]
	private String stockCode;
	// 股票名称[1]
	private String stockName;
	// 每股收益(元)[2]
	private String mgsy;
	// 每股收益(扣除)(元)[3]
	private String mgsykc;
	// 营业收入[4]
	private String yysr;

	// 营业收入_同比增长[5]
	private String yysr_tbzz;
	// 营业收入季度环比 增长[6]
	private String yysr_hbzz;
	// 净利润[7];
	private String jlr;
	// 净利润_同比增长[8]
	private String jlr_tbzz;
	// 净利润_季度环比[9]
	private String jlr_hbzz;
	// 每股净资产(元)[10]
	private String mgjzc;
	// 净资产收益率(%)[11]
	private String jzcsyl;
	// 每股经现金流量(元)[12]
	private String mgxjl;
	// 销售毛利率(%)[13]
	private String xsmll;
	// 利润分配[14]
	private String lrfp;
	// 股息率(%)[15]
	private String gxl;
	// 公告日期[16]
	private String ggrq;
	// 截止日期[17];
	private String jzrq;
	// 上季度到本季度的增长率
	private String jdzzl;
	//公布当天收盘价格   非工作日开盘后一天
	private String currentPrice;

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

	public String getMgsy() {
		return mgsy;
	}

	public void setMgsy(String mgsy) {
		this.mgsy = mgsy;
	}

	public String getMgsykc() {
		return mgsykc;
	}

	public void setMgsykc(String mgsykc) {
		this.mgsykc = mgsykc;
	}

	public String getYysr() {
		return yysr;
	}

	public void setYysr(String yysr) {
		this.yysr = yysr;
	}

	public String getYysr_tbzz() {
		return yysr_tbzz;
	}

	public void setYysr_tbzz(String yysr_tbzz) {
		this.yysr_tbzz = yysr_tbzz;
	}

	public String getYysr_hbzz() {
		return yysr_hbzz;
	}

	public void setYysr_hbzz(String yysr_hbzz) {
		this.yysr_hbzz = yysr_hbzz;
	}

	public String getJlr() {
		return jlr;
	}

	public void setJlr(String jlr) {
		this.jlr = jlr;
	}

	public String getJlr_tbzz() {
		return jlr_tbzz;
	}

	public void setJlr_tbzz(String jlr_tbzz) {
		this.jlr_tbzz = jlr_tbzz;
	}

	public String getJlr_hbzz() {
		return jlr_hbzz;
	}

	public void setJlr_hbzz(String jlr_hbzz) {
		this.jlr_hbzz = jlr_hbzz;
	}

	public String getMgjzc() {
		return mgjzc;
	}

	public void setMgjzc(String mgjzc) {
		this.mgjzc = mgjzc;
	}

	public String getJzcsyl() {
		return jzcsyl;
	}

	public void setJzcsyl(String jzcsyl) {
		this.jzcsyl = jzcsyl;
	}

	public String getMgxjl() {
		return mgxjl;
	}

	public void setMgxjl(String mgxjl) {
		this.mgxjl = mgxjl;
	}

	public String getXsmll() {
		return xsmll;
	}

	public void setXsmll(String xsmll) {
		this.xsmll = xsmll;
	}

	public String getLrfp() {
		return lrfp;
	}

	public void setLrfp(String lrfp) {
		this.lrfp = lrfp;
	}

	public String getGxl() {
		return gxl;
	}

	public void setGxl(String gxl) {
		this.gxl = gxl;
	}

	public String getGgrq() {
		return ggrq;
	}

	public void setGgrq(String ggrq) {
		this.ggrq = ggrq;
	}

	public String getJzrq() {
		return jzrq;
	}

	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}

	public String getJdzzl() {
		return jdzzl;
	}

	public void setJdzzl(String jdzzl) {
		this.jdzzl = jdzzl;
	}

	public EastReportBean(String stockCode, String stockName, String mgsy, String mgsykc, String yysr, String yysr_tbzz,
			String yysr_hbzz, String jlr, String jlr_tbzz, String jlr_hbzz, String mgjzc, String jzcsyl, String mgxjl,
			String xsmll, String lrfp, String gxl, String ggrq, String jzrq, String jdzzl,String currentPrice) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.mgsy = mgsy;
		this.mgsykc = mgsykc;
		this.yysr = yysr;
		this.yysr_tbzz = yysr_tbzz;
		this.yysr_hbzz = yysr_hbzz;
		this.jlr = jlr;
		this.jlr_tbzz = jlr_tbzz;
		this.jlr_hbzz = jlr_hbzz;
		this.mgjzc = mgjzc;
		this.jzcsyl = jzcsyl;
		this.mgxjl = mgxjl;
		this.xsmll = xsmll;
		this.lrfp = lrfp;
		this.gxl = gxl;
		this.ggrq = ggrq;
		this.jzrq = jzrq;
		this.jdzzl = jdzzl;
		this.currentPrice=currentPrice;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

}
