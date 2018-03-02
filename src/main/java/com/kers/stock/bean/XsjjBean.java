package com.kers.stock.bean;

import java.io.Serializable;

public class XsjjBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5563750336127106317L;
	private String gpdm;//股票代码
    private String gpjc;//股票简称
    private String jjsj;//解禁时间
    private long jjsl;//解禁数量
    private float jjsz;//解禁市值
    private float jjqltszb;//占解禁前流通市值比例
    private float jjqyrspj;//
    private float jjqesrzdf;//解禁前20日涨跌幅
    private float jjhesrzdf;//解禁后20日涨跌幅
    private String xslx;//解禁类型
    private String zs;
    private String zszdf;
    private String zslx;
	public String getGpdm() {
		return gpdm;
	}
	public void setGpdm(String gpdm) {
		this.gpdm = gpdm;
	}
	public String getGpjc() {
		return gpjc;
	}
	public void setGpjc(String gpjc) {
		this.gpjc = gpjc;
	}
	public String getJjsj() {
		return jjsj;
	}
	public void setJjsj(String jjsj) {
		this.jjsj = jjsj;
	}
	public long getJjsl() {
		return jjsl;
	}
	public void setJjsl(long jjsl) {
		this.jjsl = jjsl;
	}
	public float getJjsz() {
		return jjsz;
	}
	public void setJjsz(float jjsz) {
		this.jjsz = jjsz;
	}
	public float getJjqltszb() {
		return jjqltszb;
	}
	public void setJjqltszb(float jjqltszb) {
		this.jjqltszb = jjqltszb;
	}
	public float getJjqyrspj() {
		return jjqyrspj;
	}
	public void setJjqyrspj(float jjqyrspj) {
		this.jjqyrspj = jjqyrspj;
	}
	public float getJjqesrzdf() {
		return jjqesrzdf;
	}
	public void setJjqesrzdf(float jjqesrzdf) {
		this.jjqesrzdf = jjqesrzdf;
	}
	
	public float getJjhesrzdf() {
		return jjhesrzdf;
	}
	public void setJjhesrzdf(float jjhesrzdf) {
		this.jjhesrzdf = jjhesrzdf;
	}
	public String getXslx() {
		return xslx;
	}
	public void setXslx(String xslx) {
		this.xslx = xslx;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getZszdf() {
		return zszdf;
	}
	public void setZszdf(String zszdf) {
		this.zszdf = zszdf;
	}
	public String getZslx() {
		return zslx;
	}
	public void setZslx(String zslx) {
		this.zslx = zslx;
	}
	
    
}
