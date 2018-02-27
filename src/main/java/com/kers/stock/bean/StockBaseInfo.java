package com.kers.stock.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class StockBaseInfo implements Serializable {

	/**
	 * serialVersionUID:TODO
	 */

	private static final long serialVersionUID = 1L;
	private String stockCode;
	private String stockName;
	private String industry;
	
	private String date;
	private float up2Rises;//上上一天涨幅
	private float upRises = 0; //上一天涨幅
	private float rises = 0;
	private float nextRises = 0; // 下一天涨幅
	private float minRises3 = 0; //5日内最大涨幅
	private float minRises5 = 0; //5日内最大涨幅
	private float minRises10 = 0; //10日内最大涨幅
	private int upDateNum = 0;
	private float upRisesDay=0; //连续上涨幅度
	private float up2Macd;//上上一天涨幅
	private float upMacd=0;
	private float macd = 0;
	private float nextMacd=0;
	// 连续上涨天数
	//股票开市天数
	private int openNum = 1;
	//5天内上涨天数
	private int upRisesDayNum5 = 0;
	
	private float up2J = 0;
	private float upJ = 0;
	private float j = 0;
	private float j3 = 0;
	private float j5 = 0;
	private float nextJ = 0;
	
	private String area;
	 // 5天内上涨次数
	private int up5 = 0;
	//10天内上涨次数
	private int up10 = 0;

	// 换手率
	private String hslv;
	// 成交金额
	private String cjje;
	// 总市值
	private double zsz;
	// 流通市值
	private String ltsz;
	// 成交笔数
	private String cjbs;
	// MACD 指标的三个属性
	private float dea = 0;
	private float diff = 0;
	
	private float macdx = -10;

	// macd 持续天数
	private int macdNum = 0;
	private int macdUp5 = 0;
	private int macdUp10  = 0;
	private float sumMacdUp5 = 0;
	private float sumMacdUp10 = 0;
	private float upSumRises2 = 0;
	private float upSumRises3 = 0;
	private float upSumRises5 = 0;
	private float upSumRises10 = 0;
	private float upSumRises20 = 0;
	private float upSumRises30=0;	
	private float upSumRises50=0;	
	private float upSumRises60=0;	
	private float upSumRises90=0;	
	private float upSumRises120=0;	
	private float upSumRises160=0;	
	private float upSumRises180=0;	





	private float minLowRises5 = 0;
	private float minLowRises10 = 0;
	private float minLowRises20 = 0;
	private float minLowRises30 = 0;
	// KDJ 指标的三个属性
	private float k = 0;
	private float d = 0;
	//当是kd两个值小于5的时候 显示具体的差值，当有相交的时候金叉为0 死叉为-1
	private float upx=-10;
	private float up2x=-10;
	private float x = -10;
	
	// 初始需全部赋值的属性
	private float open = 0; // 开盘价
	private float high = 0; // 最高价
	private float low = 0; // 最低价
	private float close = 0; // 收盘价
	
	
	private long volume = 0; // 量
   private double volumeRises = 0;
//Label; // X 轴标签

	// MA 指标的三个属性
	private float ma5 = 0;
	private float ma10 = 0;
	private float ma20 = 0;

	// 量的5日平均和10日平均
	private double volumeMa5 = 0;
	private double volumeMa10 = 0;

	
	// RSI 指标的三个属性
	private float rsi1 = 0;
	private float rsi2 = 0;
	private float rsi3 = 0;

	// BOLL 指标的三个属性
	private float up = 0; // 上轨线
	private float mb = 0; // 中轨线
	private float dn = 0; // 下轨线
	private String dayForWeek;
	private double pe = 0;
	private double npe=0;
	
	private String type;
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

	// public void setVolume(long volume) {
	// this. volume=volume;
	// }

	// public String getXLabel() {
	// return xLabel;
	// }
	//
	// public void setXLabel(String xLabel) {
	// this.xLabel = xLabel;
	// }

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
		return retERRNAN(d);
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

	public StockBaseInfo(String date, String open, String high, String low, String close, String volume, String rises) {
		this.date = date;
		this.open = Float.parseFloat(open);
		this.high = Float.parseFloat(high);
		this.low = Float.parseFloat(low);
		this.close = Float.parseFloat(close);
		this.volume = Long.parseLong(volume);
		this.rises = Float.parseFloat(rises);

	}

	public StockBaseInfo(String date, String open, String high, String low, String close, String volume, String rises,
			String stockCode, String stockName, String hslv, String String, String zsz, String ltsz, String cjbs,
			String dayForWeek) {
		super();
		this.date = date;
		this.open = Float.parseFloat(open);
		this.high = Float.parseFloat(high);
		this.low = Float.parseFloat(low);
		this.close = Float.parseFloat(close);
		this.volume = Long.parseLong(volume);
		this.rises = Float.parseFloat(rises);
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.hslv = hslv;
		this.zsz =StringUtils.isEmpty(zsz)?0: Double.parseDouble(zsz);
		this.ltsz = ltsz;
		this.cjbs = cjbs;
		this.dayForWeek = dayForWeek;
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

	public Double getZsz() {
		return zsz;
	}

	public void setZsz(Double zsz) {
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
		return "StockBaseInfo [stockCode=" + stockCode + ", stockName="
				+ stockName + ", industry=" + industry + ", date=" + date
				+ ", upRises=" + upRises + ", rises=" + rises + ", nextRises="
				+ nextRises + ", minRises3=" + minRises3 + ", minRises5="
				+ minRises5 + ", minRises10=" + minRises10 + ", upDateNum="
				+ upDateNum + ", upMacd=" + upMacd + ", macd=" + macd
				+ ", nextMacd=" + nextMacd + ", upJ=" + upJ + ", j=" + j
				+ ", j3=" + j3 + ", j5=" + j5 + ", nextJ=" + nextJ + ", area="
				+ area + ", up5=" + up5 + ", up10=" + up10 + ", hslv=" + hslv
				+ ", cjje=" + cjje + ", zsz=" + zsz + ", ltsz=" + ltsz
				+ ", cjbs=" + cjbs + ", dea=" + dea + ", diff=" + diff
				+ ", macdNum=" + macdNum + ", macdUp5=" + macdUp5
				+ ", macdUp10=" + macdUp10 + ", sumMacdUp5=" + sumMacdUp5
				+ ", sumMacdUp10=" + sumMacdUp10 + ", upSumRises5="
				+ upSumRises5 + ", upSumRises10=" + upSumRises10 + ", k=" + k
				+ ", d=" + d + ", open=" + open + ", high=" + high + ", low="
				+ low + ", close=" + close + ", volume=" + volume
				+ ", volumeRises=" + volumeRises + ", ma5=" + ma5 + ", ma10="
				+ ma10 + ", ma20=" + ma20 + ", volumeMa5=" + volumeMa5
				+ ", volumeMa10=" + volumeMa10 + ", rsi1=" + rsi1 + ", rsi2="
				+ rsi2 + ", rsi3=" + rsi3 + ", up=" + up + ", mb=" + mb
				+ ", dn=" + dn + ", dayForWeek=" + dayForWeek + "]";
	}

	public float retERRNAN(float num) {
		// System.out.println(num);
		if ((num + "").equals("NaN"))
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

	public int getUpDateNum() {
		return upDateNum;
	}

	public void setUpDateNum(int upDateNum) {
		this.upDateNum = upDateNum;
	}

	public int getUp5() {
		return up5;
	}

	public void setUp5(int up5) {
		this.up5 = up5;
	}

	public int getUp10() {
		return up10;
	}

	public void setUp10(int up10) {
		this.up10 = up10;
	}

	

	public float getUpRises() {
		return upRises;
	}

	public void setUpRises(float upRises) {
		this.upRises = upRises;
	}

	public float getNextRises() {
		return nextRises;
	}

	public void setNextRises(float nextRises) {
		this.nextRises = nextRises;
	}

	public float getUpSumRises5() {
		return upSumRises5;
	}

	public void setUpSumRises5(float upSumRises5) {
		this.upSumRises5 = upSumRises5;
	}

	public float getUpSumRises10() {
		return upSumRises10;
	}

	public void setUpSumRises10(float upSumRises10) {
		this.upSumRises10 = upSumRises10;
	}

	public int getMacdNum() {
		return macdNum;
	}

	public void setMacdNum(int macdNum) {
		this.macdNum = macdNum;
	}

	public int getMacdUp5() {
		return macdUp5;
	}

	public void setMacdUp5(int macdUp5) {
		this.macdUp5 = macdUp5;
	}

	public int getMacdUp10() {
		return macdUp10;
	}

	public void setMacdUp10(int macdUp10) {
		this.macdUp10 = macdUp10;
	}

	public float getSumMacdUp5() {
		return sumMacdUp5;
	}

	public void setSumMacdUp5(float sumMacdUp5) {
		this.sumMacdUp5 = sumMacdUp5;
	}

	public float getSumMacdUp10() {
		return sumMacdUp10;
	}

	public void setSumMacdUp10(float sumMacdUp10) {
		this.sumMacdUp10 = sumMacdUp10;
	}

	public float getRises() {
		return rises;
	}

	public void setRises(float rises) {
		this.rises = rises;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public float getUpJ() {
		return upJ;
	}

	public void setUpJ(float upJ) {
		this.upJ = upJ;
	}

	public float getNextJ() {
		return nextJ;
	}

	public void setNextJ(float nextJ) {
		this.nextJ = nextJ;
	}

	public float getUpMacd() {
		return upMacd;
	}

	public void setUpMacd(float upMacd) {
		this.upMacd = upMacd;
	}

	public float getNextMacd() {
		return nextMacd;
	}

	public void setNextMacd(float nextMacd) {
		this.nextMacd = nextMacd;
	}

	public float getJ3() {
		return j3;
	}

	public void setJ3(float j3) {
		this.j3 = j3;
	}

	public float getJ5() {
		return j5;
	}

	public void setJ5(float j5) {
		this.j5 = j5;
	}

	public double getVolumeRises() {
		return volumeRises;
	}

	public void setVolumeRises(double volumeRises) {
		this.volumeRises = volumeRises;
	}

	public float getMinRises3() {
		return minRises3;
	}

	public void setMinRises3(float minRises3) {
		this.minRises3 = minRises3;
	}

	public float getMinRises5() {
		return minRises5;
	}

	public void setMinRises5(float minRises5) {
		this.minRises5 = minRises5;
	}

	public float getMinRises10() {
		return minRises10;
	}

	public void setMinRises10(float minRises10) {
		this.minRises10 = minRises10;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public int getOpenNum() {
		return openNum;
	}

	public void setOpenNum(int openNum) {
		this.openNum = openNum;
	}

	public int getUpRisesDayNum5() {
		return upRisesDayNum5;
	}

	public void setUpRisesDayNum5(int upRisesDayNum5) {
		this.upRisesDayNum5 = upRisesDayNum5;
	}

	public float getMacdx() {
		return macdx;
	}

	public void setMacdx(float macdx) {
		this.macdx = macdx;
	}

	public double getPe() {
		return pe;
	}

	public void setPe(double pe) {
		this.pe = pe;
	}

	public float getMinLowRises5() {
		return minLowRises5;
	}

	public void setMinLowRises5(float minLowRises5) {
		this.minLowRises5 = minLowRises5;
	}

	public float getMinLowRises10() {
		return minLowRises10;
	}

	public void setMinLowRises10(float minLowRises10) {
		this.minLowRises10 = minLowRises10;
	}

	public float getMinLowRises20() {
		return minLowRises20;
	}

	public void setMinLowRises20(float minLowRises20) {
		this.minLowRises20 = minLowRises20;
	}

	public float getMinLowRises30() {
		return minLowRises30;
	}

	public void setMinLowRises30(float minLowRises30) {
		this.minLowRises30 = minLowRises30;
	}

	public float getUpSumRises20() {
		return upSumRises20;
	}

	public void setUpSumRises20(float upSumRises20) {
		this.upSumRises20 = upSumRises20;
	}

	public float getUpSumRises30() {
		return upSumRises30;
	}

	public void setUpSumRises30(float upSumRises30) {
		this.upSumRises30 = upSumRises30;
	}

	public float getUp2Rises() {
		return up2Rises;
	}

	public void setUp2Rises(float up2Rises) {
		this.up2Rises = up2Rises;
	}

	public float getUp2Macd() {
		return up2Macd;
	}

	public void setUp2Macd(float up2Macd) {
		this.up2Macd = up2Macd;
	}

	public float getUp2J() {
		return up2J;
	}

	public void setUp2J(float up2j) {
		up2J = up2j;
	}

	public float getUpx() {
		return upx;
	}

	public void setUpx(float upx) {
		this.upx = upx;
	}
	public float getUp2x() {
		return up2x;
	}

	public void setUp2x(float up2x) {
		this.up2x = up2x;
	}

	public double getNpe() {
		return npe;
	}

	public void setNpe(double npe) {
		this.npe = npe;
	}

	public float getUpSumRises2() {
		return upSumRises2;
	}

	public void setUpSumRises2(float upSumRises2) {
		this.upSumRises2 = upSumRises2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getUpSumRises3() {
		return upSumRises3;
	}

	public void setUpSumRises3(float upSumRises3) {
		this.upSumRises3 = upSumRises3;
	}

	public float getUpRisesDay() {
		return upRisesDay;
	}

	public void setUpRisesDay(float upRisesDay) {
		this.upRisesDay = upRisesDay;
	}

	public float getUpSumRises50() {
		return upSumRises50;
	}

	public void setUpSumRises50(float upSumRises50) {
		this.upSumRises50 = upSumRises50;
	}

	public float getUpSumRises60() {
		return upSumRises60;
	}

	public void setUpSumRises60(float upSumRises60) {
		this.upSumRises60 = upSumRises60;
	}

	public float getUpSumRises90() {
		return upSumRises90;
	}

	public void setUpSumRises90(float upSumRises90) {
		this.upSumRises90 = upSumRises90;
	}

	public float getUpSumRises120() {
		return upSumRises120;
	}

	public void setUpSumRises120(float upSumRises120) {
		this.upSumRises120 = upSumRises120;
	}

	public float getUpSumRises160() {
		return upSumRises160;
	}

	public void setUpSumRises160(float upSumRises160) {
		this.upSumRises160 = upSumRises160;
	}

	public float getUpSumRises180() {
		return upSumRises180;
	}

	public void setUpSumRises180(float upSumRises180) {
		this.upSumRises180 = upSumRises180;
	}

	
}
