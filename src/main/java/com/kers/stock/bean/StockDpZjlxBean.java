package com.kers.stock.bean;

public class StockDpZjlxBean {
	//2017-09-26,-116.0068,-2.91%,-20.0869,-0.5%,-95.92,-2.4%,-18.9434,-0.47%,134.9502,3.38%,3343.58,0.06%,10950.77,0.18%
//	日期
	private String rq;
//	上证收盘价
	private float szspj;
//	上证涨跌幅	
	private float szzf; 
//	深证收盘价	
	private float zspj;
//	深证涨跌幅
	private float zzf;
//	主力净流入净额
	private float zljr;
//	主力净流入净占比
	private float zlzb;
//	超大单净流入净额
	private float cddjr;
//	超大单净流入净占比
	private float cddzb;
//	大单净流入净额
	private float ddjr;
//	大单净流入净占比
	private float ddzb;
//	中单净流入净额
	private float zdjr;
//	中单净流入净占比
	private float zdzb;
//	小单净流入净额
	private float xdjr;
//	小单净流入净占比
	private float xdzb;
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public float getSzspj() {
		return szspj;
	}
	public void setSzspj(float szspj) {
		this.szspj = szspj;
	}
	public float getSzzf() {
		return szzf;
	}
	public void setSzzf(float szzf) {
		this.szzf = szzf;
	}
	public float getZspj() {
		return zspj;
	}
	public void setZspj(float zspj) {
		this.zspj = zspj;
	}
	public float getZzf() {
		return zzf;
	}
	public void setZzf(float zzf) {
		this.zzf = zzf;
	}
	public float getZljr() {
		return zljr;
	}
	public void setZljr(float zljr) {
		this.zljr = zljr;
	}
	public float getZlzb() {
		return zlzb;
	}
	public void setZlzb(float zlzb) {
		this.zlzb = zlzb;
	}
	public float getCddjr() {
		return cddjr;
	}
	public void setCddjr(float cddjr) {
		this.cddjr = cddjr;
	}
	public float getCddzb() {
		return cddzb;
	}
	public void setCddzb(float cddzb) {
		this.cddzb = cddzb;
	}
	public float getDdjr() {
		return ddjr;
	}
	public void setDdjr(float ddjr) {
		this.ddjr = ddjr;
	}
	public float getDdzb() {
		return ddzb;
	}
	public void setDdzb(float ddzb) {
		this.ddzb = ddzb;
	}
	public float getZdjr() {
		return zdjr;
	}
	public void setZdjr(float zdjr) {
		this.zdjr = zdjr;
	}
	public float getZdzb() {
		return zdzb;
	}
	public void setZdzb(float zdzb) {
		this.zdzb = zdzb;
	}
	public float getXdjr() {
		return xdjr;
	}
	public void setXdjr(float xdjr) {
		this.xdjr = xdjr;
	}
	public float getXdzb() {
		return xdzb;
	}
	public void setXdzb(float xdzb) {
		this.xdzb = xdzb;
	}
	public StockDpZjlxBean(){
		
	}
	
	public StockDpZjlxBean(String rq, String szspj, String szzf, String zspj,
			String zzf, String zljr, String zlzb, String cddjr, String cddzb,
			String ddjr, String ddzb, String zdjr, String zdzb, String xdjr,
			String xdzb) {
		this.rq = rq;
		this.szspj = Float.parseFloat(szspj);
		this.szzf = Float.parseFloat(szzf);
		this.zspj = Float.parseFloat(zspj);
		this.zzf = Float.parseFloat(zzf);
		this.zljr = Float.parseFloat(zljr);
		this.zlzb = Float.parseFloat(zlzb);
		this.cddjr = Float.parseFloat(cddjr);
		this.cddzb = Float.parseFloat(cddzb);
		this.ddjr = Float.parseFloat(ddjr);
		this.ddzb = Float.parseFloat(ddzb);
		this.zdjr = Float.parseFloat(zdjr);
		this.zdzb = Float.parseFloat(zdzb);
		this.xdjr = Float.parseFloat(xdjr);
		this.xdzb = Float.parseFloat(xdzb);
	}
	
}
