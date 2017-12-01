package com.cmall.stock.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class EastReportBean   implements Serializable {
	/**    
	 * serialVersionUID:TODO      
	 */    
	
	private static final long serialVersionUID = 1L;
	// 股票代码[0]
	private String stockCode;
	// 股票名称[1]
	private String stockName;
	// 净利润[7];
	private double jlr;
	//上季度净利润
	private double sjlr;
	//下季度净利润
	private double xjlr;
	// 上季度到本季度的增长率
	private Double jdzzl;
	//上上季度增长率
	private Double jdzzl_before;
	//净利润格式换
		private String jlr_gsh;
	//净利润预测比例
		private Double jlr_ycb=0d;
	// 每股收益(元)[2]
	private double mgsy;
	// 每股收益(扣除)(元)[3]
	private double mgsykc;
	// 营业收入[4]
	private double yysr;
	//营业收入格式化
	private String yysr_yw;
	// 营业收入_同比增长[5]
	private String yysr_tbzz;
	// 营业收入季度环比 增长[6]
	private double yysr_hbzz;
	
	// 净利润_同比增长[8]
	private String jlr_tbzz;
	private String jlr_tbzz_str;
	// 净利润_季度环比[9]
	private double jlr_hbzz;
	
	// 每股净资产(元)[10]
	private double mgjzc;
	// 净资产收益率(%)[11]
	private String jzcsyl;
	// 每股经现金流量(元)[12]
	private double mgxjl;
	// 销售毛利率(%)[13]
	private double xsmll;
	// 利润分配[14]
	private String lrfp;
	// 股息率(%)[15]
	private String gxl;
	// 公告日期[16]
	private String ggrq;
	// 截止日期[17];
	private String jzrq;
	

	//公布当天收盘价格   非工作日开盘后一天
	private String currentPrice;
	
//	经营活动产生的现金流量净额(万元)
	private String jyhdcsdxjllje;
//	投资活动产生的现金流量净额(万元)
	private String tzhdcsdxjllje;


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

	public Double getJdzzl() {
		return jdzzl;
	}

	public void setJdzzl(Double jdzzl) {
		this.jdzzl = jdzzl;
	}
	
	public EastReportBean(){
		
	}

	public EastReportBean(String stockCode, String stockName, String mgsy, String mgsykc, String yysr, String yysr_tbzz,
			String yysr_hbzz, String jlr, String jlr_tbzz, String jlr_hbzz, String mgjzc, String jzcsyl, String mgxjl,
			String xsmll, String lrfp, String gxl, String ggrq, String jzrq, Double jdzzl,String currentPrice) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.mgsy = Double.parseDouble(mgsy);
		this.mgsykc = Double.parseDouble(mgsykc);
		this.yysr = Double.parseDouble(yysr);
		this.yysr_tbzz = yysr_tbzz;
		if(StringUtils.isNumeric(yysr_hbzz)){
			this.yysr_hbzz = Double.parseDouble(yysr_hbzz);
		}
		this.jlr = Double.parseDouble(jlr);
		this.jlr_tbzz = jlr_tbzz;
		if(StringUtils.isNumeric(jlr_hbzz)){
			this.jlr_hbzz = Double.parseDouble(jlr_hbzz);
		}
		if(StringUtils.isNumeric(mgjzc)){
			this.mgjzc = Double.parseDouble(mgjzc);
		}
		this.jzcsyl = jzcsyl;
		if(StringUtils.isNumeric(mgxjl)){
			this.mgxjl = Double.parseDouble(mgxjl);
		}
		if(StringUtils.isNumeric(xsmll)){
			this.xsmll = Double.parseDouble(xsmll);
		}
		
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

	

	public String getYysr_yw() {
		return yysr_yw;
	}

	public void setYysr_yw(String yysr_yw) {
		this.yysr_yw = yysr_yw;
	}


	public Double getJlr_ycb() {
		return jlr_ycb;
	}

	public void setJlr_ycb(Double jlr_ycb) {
		this.jlr_ycb = jlr_ycb;
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

	public double getMgsy() {
		return mgsy;
	}

	public void setMgsy(double mgsy) {
		this.mgsy = mgsy;
	}

	public double getMgsykc() {
		return mgsykc;
	}

	public void setMgsykc(double mgsykc) {
		this.mgsykc = mgsykc;
	}

	public double getYysr() {
		return yysr;
	}

	public void setYysr(double yysr) {
		this.yysr = yysr;
	}

	

	public String getYysr_tbzz() {
		return yysr_tbzz;
	}

	public void setYysr_tbzz(String yysr_tbzz) {
		this.yysr_tbzz = yysr_tbzz;
	}

	public double getYysr_hbzz() {
		return yysr_hbzz;
	}

	public void setYysr_hbzz(double yysr_hbzz) {
		this.yysr_hbzz = yysr_hbzz;
	}

	public double getJlr() {
		return jlr;
	}

	public void setJlr(double jlr) {
		this.jlr = jlr;
	}

	public String getJlr_gsh() {
		return jlr_gsh;
	}

	public void setJlr_gsh(String jlr_gsh) {
		this.jlr_gsh = jlr_gsh;
	}

	

	public String getJlr_tbzz() {
		return jlr_tbzz;
	}

	public void setJlr_tbzz(String jlr_tbzz) {
		this.jlr_tbzz = jlr_tbzz;
	}

	public double getJlr_hbzz() {
		return jlr_hbzz;
	}

	public void setJlr_hbzz(double jlr_hbzz) {
		this.jlr_hbzz = jlr_hbzz;
	}

	public double getMgjzc() {
		return mgjzc;
	}

	public void setMgjzc(double mgjzc) {
		this.mgjzc = mgjzc;
	}

	

	public String getJzcsyl() {
		return jzcsyl;
	}

	public void setJzcsyl(String jzcsyl) {
		this.jzcsyl = jzcsyl;
	}

	public double getMgxjl() {
		return mgxjl;
	}

	public void setMgxjl(double mgxjl) {
		this.mgxjl = mgxjl;
	}

	public double getXsmll() {
		return xsmll;
	}

	public void setXsmll(double xsmll) {
		this.xsmll = xsmll;
	}

	public Double getJdzzl_before() {
		return jdzzl_before;
	}

	public void setJdzzl_before(Double jdzzl_before) {
		this.jdzzl_before = jdzzl_before;
	}
	
	

	public double getSjlr() {
		return sjlr;
	}

	public void setSjlr(double sjlr) {
		this.sjlr = sjlr;
	}

	public double getXjlr() {
		return xjlr;
	}

	public void setXjlr(double xjlr) {
		this.xjlr = xjlr;
	}

	@Override
	public String toString() {
		return "EastReportBean [stockCode=" + stockCode + ", stockName=" + stockName + ", jlr=" + jlr + ", jdzzl="
				+ jdzzl + ", jdzzl_before=" + jdzzl_before + ", jlr_gsh=" + jlr_gsh + ", jlr_ycb=" + jlr_ycb + ", mgsy="
				+ mgsy + ", mgsykc=" + mgsykc + ", yysr=" + yysr + ", yysr_yw=" + yysr_yw + ", yysr_tbzz=" + yysr_tbzz
				+ ", yysr_hbzz=" + yysr_hbzz + ", jlr_tbzz=" + jlr_tbzz + ", jlr_hbzz=" + jlr_hbzz + ", mgjzc=" + mgjzc
				+ ", jzcsyl=" + jzcsyl + ", mgxjl=" + mgxjl + ", xsmll=" + xsmll + ", lrfp=" + lrfp + ", gxl=" + gxl
				+ ", ggrq=" + ggrq + ", jzrq=" + jzrq + ", currentPrice=" + currentPrice + "]";
	}

	public String getJyhdcsdxjllje() {
		return jyhdcsdxjllje;
	}

	public String getTzhdcsdxjllje() {
		return tzhdcsdxjllje;
	}

	public void setJyhdcsdxjllje(String jyhdcsdxjllje) {
		this.jyhdcsdxjllje = jyhdcsdxjllje;
	}

	public void setTzhdcsdxjllje(String tzhdcsdxjllje) {
		this.tzhdcsdxjllje = tzhdcsdxjllje;
	}

	public String getJlr_tbzz_str() {
		return jlr_tbzz_str;
	}

	public void setJlr_tbzz_str(String jlr_tbzz_str) {
		this.jlr_tbzz_str = jlr_tbzz_str;
	}

 
	

}
