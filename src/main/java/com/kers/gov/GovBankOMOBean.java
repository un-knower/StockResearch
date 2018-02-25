package com.kers.gov;


//http://www.chinamoney.com.cn/fe/jsp/CN/chinamoney/notice/ticketHandleMoreList.jsp?pagingPage_il_=37
public class GovBankOMOBean {
	
	private String date;// 操作日期	
	private String expDate;//逆回购到期时间
	private int       czqx;//期限(天)
	private  double        jyl;//交易量(亿)
	private double  zbrate;//中标利率(%)
	private  int   hgtype;//正/逆回购
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCzqx() {
		return czqx;
	}
	public void setCzqx(int czqx) {
		this.czqx = czqx;
	}
	public double getZbrate() {
		return zbrate;
	}
	public void setZbrate(double zbrate) {
		this.zbrate = zbrate;
	}
	public int getHgtype() {
		return hgtype;
	}
	public void setHgtype(int hgtype) {
		this.hgtype = hgtype;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public double getJyl() {
		return jyl;
	}
	public void setJyl(double jyl) {
		this.jyl = jyl;
	}

}
