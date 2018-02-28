package com.kers.stock.bean;

import java.io.Serializable;

/**
 * 
 * 大宗交易
 *
 */
public class DzjyBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String TDATE;  //交易日期
    private String SECUCODE;//证券代码
    private String SNAME;//证券简称
    private double PRICE;//成交价(元)
    private double TVOL;//成交量(万股)
    private double TVAL;//成交额(万元)
    private String BUYERCODE;//买方营业部CODE
    private String BUYERNAME;//买方营业部名称
    private String SALESCODE;//卖方营业部CODE
    private String SALESNAME;//卖方营业部名称
    private String Stype;
    private String Unit;//单位
    private double RCHANGE;//涨跌幅(%)
    private double CPRICE;//收盘价(元)
    private long YSSLTAG;
    private float Zyl;//折溢率
    private double Cjeltszb;//成交额/流通市值
    private double RCHANGE1DC;//上榜后1天涨幅
    private double RCHANGE5DC;//上榜后1天涨幅
    private double RCHANGE10DC;//上榜后1天涨幅
    private String RCHANGE20DC;//上榜后1天涨幅
    private String TEXCH;
	public String getTDATE() {
		return TDATE;
	}
	public void setTDATE(String tDATE) {
		TDATE = tDATE;
	}
	public String getSECUCODE() {
		return SECUCODE;
	}
	public void setSECUCODE(String sECUCODE) {
		SECUCODE = sECUCODE;
	}
	public String getSNAME() {
		return SNAME;
	}
	public void setSNAME(String sNAME) {
		SNAME = sNAME;
	}
	public double getPRICE() {
		return PRICE;
	}
	public void setPRICE(double pRICE) {
		PRICE = pRICE;
	}
	public double getTVOL() {
		return TVOL;
	}
	public void setTVOL(double tVOL) {
		TVOL = tVOL;
	}
	public double getTVAL() {
		return TVAL;
	}
	public void setTVAL(double tVAL) {
		TVAL = tVAL;
	}
	public String getBUYERCODE() {
		return BUYERCODE;
	}
	public void setBUYERCODE(String bUYERCODE) {
		BUYERCODE = bUYERCODE;
	}
	public String getBUYERNAME() {
		return BUYERNAME;
	}
	public void setBUYERNAME(String bUYERNAME) {
		BUYERNAME = bUYERNAME;
	}
	public String getSALESCODE() {
		return SALESCODE;
	}
	public void setSALESCODE(String sALESCODE) {
		SALESCODE = sALESCODE;
	}
	public String getSALESNAME() {
		return SALESNAME;
	}
	public void setSALESNAME(String sALESNAME) {
		SALESNAME = sALESNAME;
	}
	public String getStype() {
		return Stype;
	}
	public void setStype(String stype) {
		Stype = stype;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public double getRCHANGE() {
		return RCHANGE;
	}
	public void setRCHANGE(double rCHANGE) {
		RCHANGE = rCHANGE;
	}
	public double getCPRICE() {
		return CPRICE;
	}
	public void setCPRICE(double cPRICE) {
		CPRICE = cPRICE;
	}
	public long getYSSLTAG() {
		return YSSLTAG;
	}
	public void setYSSLTAG(long ySSLTAG) {
		YSSLTAG = ySSLTAG;
	}
	
	public float getZyl() {
		return Zyl;
	}
	public void setZyl(float zyl) {
		Zyl = zyl;
	}
	public double getCjeltszb() {
		return Cjeltszb;
	}
	public void setCjeltszb(double cjeltszb) {
		Cjeltszb = cjeltszb;
	}
	public double getRCHANGE1DC() {
		return RCHANGE1DC;
	}
	public void setRCHANGE1DC(double rCHANGE1DC) {
		RCHANGE1DC = rCHANGE1DC;
	}
	public double getRCHANGE5DC() {
		return RCHANGE5DC;
	}
	public void setRCHANGE5DC(double rCHANGE5DC) {
		RCHANGE5DC = rCHANGE5DC;
	}
	public double getRCHANGE10DC() {
		return RCHANGE10DC;
	}
	public void setRCHANGE10DC(double rCHANGE10DC) {
		RCHANGE10DC = rCHANGE10DC;
	}
	public String getRCHANGE20DC() {
		return RCHANGE20DC;
	}
	public void setRCHANGE20DC(String rCHANGE20DC) {
		RCHANGE20DC = rCHANGE20DC;
	}
	public String getTEXCH() {
		return TEXCH;
	}
	public void setTEXCH(String tEXCH) {
		TEXCH = tEXCH;
	}
    
    
}
