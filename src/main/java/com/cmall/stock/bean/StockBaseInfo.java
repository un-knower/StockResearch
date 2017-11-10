package com.cmall.stock.bean;

import java.io.Serializable;

public class StockBaseInfo implements Serializable {

	/**    
	 * serialVersionUID:TODO      
	 */    
	
	private static final long serialVersionUID = 1L;
	private String date;
	private String rises;
	private String stockCode;
	private String stockName;
	private String hslv;
	private String cjje;
	private String zsz;
	private String ltsz;
	private String cjbs;
//,成交金额,总市值,流通市值,成交笔数

	// 初始需全部赋值的属性
	private  float open; // 开盘价
	private  float high; // 最高价
	private  float low; // 最低价
	private  float close; // 收盘价
	private  long volume; // 量
//	private String xLabel; // X 轴标签

	// MA 指标的三个属性
	private float ma5=0;
	private float ma10=0;
	private float ma20=0;

	// 量的5日平均和10日平均
	private double volumeMa5=0;
	private double volumeMa10=0;

	// MACD 指标的三个属性
	private float dea=0;
	private float diff=0;
	private float macd=0;

	// KDJ 指标的三个属性
	private float k=0;
	private float d=0;
	private float j=0;

	// RSI 指标的三个属性
	private float rsi1=0;
	private float rsi2=0;
	private float rsi3=0;

	// BOLL 指标的三个属性
	private float up=0; // 上轨线
	private float mb=0; // 中轨线
	private float dn=0; // 下轨线
    private String dayForWeek;

	/**
	 * 自定义分时图用的数据
	 *
	 * @param close
	 *            收盘价
	 * @param volume
	 *            量
	 * @param xLabel
	 *            X 轴标签
	 */
	public StockBaseInfo(float close, long volume, String date) {
		this.open = 0;
		this.high = 0;
		this.low = 0;
		this.close = close;
		this.volume = volume;
		this.date = date;
	}

	/**
	 * 自定义 K 线图用的数据
	 *
	 * @param open
	 *            开盘价
	 * @param high
	 *            最高价
	 * @param low
	 *            最低价
	 * @param close
	 *            收盘价
	 * @param volume
	 *            量
	 * @param xLabel
	 *            X 轴标签
	 */
	public StockBaseInfo(float open, float high, float low, float close, int volume, String date) {
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.date = date;
	}

	public float getOpen() {
		return open;
	}

	public float getHigh() {
		return high;
	}


	public float getLow() {
		
		return retERRNAN(low);
	}

	public float getClose() {
		return close;
	}

	public long getVolume() {
		return volume;
	}

//	public void setVolume(long volume) {
//		this. volume=volume;
//	}

//	public String getXLabel() {
//		return xLabel;
//	}
//
//	public void setXLabel(String xLabel) {
//		this.xLabel = xLabel;
//	}

	public float getMa5() {
		return retERRNAN(ma5);
	}

	public void setMa5(float ma5) {
		this.ma5 = ma5;
	}

	public float getMa10() {
		return retERRNAN(ma10);
	}

	public void setMa10(float ma10) {
		this.ma10 = ma10;
	}

	public float getMa20() {
		return retERRNAN(ma20);
	}

	public void setMa20(float ma20) {
		this.ma20 = ma20;
	}

	public double getVolumeMa5() {
		return volumeMa5;
	}

	public void setVolumeMa5(double volumeMa5) {
		this.volumeMa5 = volumeMa5;
	}

	public double getVolumeMa10() {
		return volumeMa10;
	}

	public void setVolumeMa10(double volumeMa10) {
		this.volumeMa10 = volumeMa10;
	}

	public float getDea() {
		return dea;
	}

	public void setDea(float dea) {
		this.dea = dea;
	}

	public float getDiff() {
		return retERRNAN(diff);
	}

	public void setDiff(float diff) {
		this.diff = diff;
	}

	public float getMacd() {
		return retERRNAN(macd);
	}

	public void setMacd(float macd) {
		this.macd = macd;
	}

	public float getK() {
		return retERRNAN(k);
	}

	public void setK(float k) {
		this.k = k;
	}

	public float getD() {
		return retERRNAN( d);
	}

	public void setD(float d) {
		this.d = d;
	}

	public float getJ() {
		return retERRNAN(j);
	}

	public void setJ(float j) {
		this.j = j;
	}

	public float getRsi1() {
		return retERRNAN(rsi1);
	}

	public void setRsi1(float rsi1) {
		this.rsi1 = rsi1;
	}

	public float getRsi2() {
		return retERRNAN(rsi2);
	}

	public void setRsi2(float rsi2) {
		this.rsi2 = rsi2;
	}

	public float getRsi3() {
		return retERRNAN(rsi3);
	}

	public void setRsi3(float rsi3) {
		this.rsi3 = rsi3;
	}

	public float getUp() {
		return retERRNAN(up);
	}

	public void setUp(float up) {
		this.up = up;
	}

	public float getMb() {
		return retERRNAN(mb);
	}

	public void setMb(float mb) {
		this.mb = mb;
	}

	public float getDn() {
		return retERRNAN(dn);
	}

	public void setDn(float dn) {
		this.dn = dn;
	}

 

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRises() {
		return rises;
	}

	public void setRises(String rises) {
		this.rises = rises;
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

 
	public StockBaseInfo(String date, String open,String high,String low,String close,String volume,String rises){
		this.date=date;
		this.open=Float.parseFloat(open);
		this.high=Float.parseFloat(high);
		this.low=Float.parseFloat(low);
		this.close=Float.parseFloat(close);
		this.volume=Long.parseLong(volume);
		this.rises=rises;
		
	}
	public StockBaseInfo(String date, String open, String high, String low, String close, String volume, String rises,
			String stockCode, String stockName, String hslv,String String,String zsz,String ltsz,String cjbs,String dayForWeek) {
		super();
		this.date = date;
		this.open = Float.parseFloat(open);
		this.high = Float.parseFloat(high);
		this.low =Float.parseFloat( low);
		this.close = Float.parseFloat(close);
		this.volume =Long.parseLong( volume);
		this.rises = rises;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.hslv=hslv;
		this.zsz=zsz;
		this.ltsz=ltsz;
		this.cjbs=cjbs;
		this.dayForWeek=dayForWeek;
	}

	public String getHslv() {
		return hslv;
	}

	public void setHslv(String hslv) {
		this.hslv = hslv;
	}

	public String getCjje() {
		return cjje;
	}

	public void setCjje(String cjje) {
		this.cjje = cjje;
	}

	public String getZsz() {
		return zsz;
	}

	public void setZsz(String zsz) {
		this.zsz = zsz;
	}

	public String getLtsz() {
		return ltsz;
	}

	public void setLtsz(String ltsz) {
		this.ltsz = ltsz;
	}

	public String getCjbs() {
		return cjbs;
	}

	public void setCjbs(String cjbs) {
		this.cjbs = cjbs;
	}

	@Override
	public String toString() {
		return "StockBaseInfo [date=" + date + ", rises=" + rises + ", stockCode=" + stockCode + ", stockName="
				+ stockName + ", hslv=" + hslv + ", cjje=" + cjje + ", zsz=" + zsz + ", ltsz=" + ltsz + ", cjbs=" + cjbs
				+ ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume
				+ ", ma5=" + ma5 + ", ma10=" + ma10 + ", ma20=" + ma20 + ", volumeMa5=" + volumeMa5 + ", volumeMa10="
				+ volumeMa10 + ", dea=" + dea + ", diff=" + diff + ", macd=" + macd + ", k=" + k + ", d=" + d + ", j="
				+ j + ", rsi1=" + rsi1 + ", rsi2=" + rsi2 + ", rsi3=" + rsi3 + ", up=" + up + ", mb=" + mb + ", dn="
				+ dn + "]";
	}
	public    float retERRNAN(float num){
//		 System.out.println(num);
		if((num+"").equals("NaN"))
			return 0;
		else 
			return num;
	}

	public StockBaseInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDayForWeek() {
		return dayForWeek;
	}

	public void setDayForWeek(String dayForWeek) {
		this.dayForWeek = dayForWeek;
	}
	
}
