package com.cmall.stock.bean;

import java.io.Serializable;
import java.util.List;

public class StockDetailInfoBean  implements  Serializable {
	private  String  stockCode;//代码
	private  String  stockName;//名称
	private  String industry;//所属行业
	private  String area;//地区
	private  double pe;//市盈率
	private double outstanding;//流通股本(亿)
	private double totals;//总股本(亿)
	private double totalAssets;//总资产(万)
	private double liquidAssets;//流动资产
	private double fixedAssets;//固定资产
	private double reserved;//公积金
	private double reservedPerShare;//每股公积金
	private double esp;//每股收益
	private double bvps;//每股净资
	private double pb;//市净率
	private String timeToMarket;//上市日期
	private double undp;//未分利润
	private double perundp;// 每股未分配
	private double rev;//收入同比(%)
	private double profit;//利润同比(%)
	private double gpr;//毛利率(%)
	private double npr;//净利润率(%)
	private double holders;//股东人数
	private double zsz;//总市值
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
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public double getPe() {
		return pe;
	}
	public void setPe(double pe) {
		this.pe = pe;
	}
	public double getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(double outstanding) {
		this.outstanding = outstanding;
	}
	public double getTotals() {
		return totals;
	}
	public void setTotals(double totals) {
		this.totals = totals;
	}
	public double getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}
	public double getLiquidAssets() {
		return liquidAssets;
	}
	public void setLiquidAssets(double liquidAssets) {
		this.liquidAssets = liquidAssets;
	}
	public double getFixedAssets() {
		return fixedAssets;
	}
	public void setFixedAssets(double fixedAssets) {
		this.fixedAssets = fixedAssets;
	}
	public double getReserved() {
		return reserved;
	}
	public void setReserved(double reserved) {
		this.reserved = reserved;
	}
	public double getReservedPerShare() {
		return reservedPerShare;
	}
	public void setReservedPerShare(double reservedPerShare) {
		this.reservedPerShare = reservedPerShare;
	}
	public double getEsp() {
		return esp;
	}
	public void setEsp(double esp) {
		this.esp = esp;
	}
	public double getBvps() {
		return bvps;
	}
	public void setBvps(double bvps) {
		this.bvps = bvps;
	}
	public double getPb() {
		return pb;
	}
	public void setPb(double pb) {
		this.pb = pb;
	}
	public String getTimeToMarket() {
		return timeToMarket;
	}
	public void setTimeToMarket(String timeToMarket) {
		this.timeToMarket = timeToMarket;
	}
	public double getUndp() {
		return undp;
	}
	public void setUndp(double undp) {
		this.undp = undp;
	}
	public double getPerundp() {
		return perundp;
	}
	public void setPerundp(double perundp) {
		this.perundp = perundp;
	}
	public double getRev() {
		return rev;
	}
	public void setRev(double rev) {
		this.rev = rev;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getGpr() {
		return gpr;
	}
	public void setGpr(double gpr) {
		this.gpr = gpr;
	}
	public double getNpr() {
		return npr;
	}
	public void setNpr(double npr) {
		this.npr = npr;
	}
	public double getHolders() {
		return holders;
	}
	public void setHolders(double holders) {
		this.holders = holders;
	}
	public StockDetailInfoBean(String stockCode, String stockName, String industry, String area, double pe,
			double outstanding, double totals, double totalAssets, double liquidAssets, double fixedAssets,
			double reserved, double reservedPerShare, double esp, double bvps, double pb, String timeToMarket,
			double undp, double perundp, double rev, double profit, double gpr, double npr, double holders) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.industry = industry;
		this.area = area;
		this.pe = pe;
		this.outstanding = outstanding;
		this.totals = totals;
		this.totalAssets = totalAssets;
		this.liquidAssets = liquidAssets;
		this.fixedAssets = fixedAssets;
		this.reserved = reserved;
		this.reservedPerShare = reservedPerShare;
		this.esp = esp;
		this.bvps = bvps;
		this.pb = pb;
		this.timeToMarket = timeToMarket;
		this.undp = undp;
		this.perundp = perundp;
		this.rev = rev;
		this.profit = profit;
		this.gpr = gpr;
		this.npr = npr;
		this.holders = holders;
	}
	public StockDetailInfoBean(List<String>  lstBean) {
		super();
		this.stockCode = lstBean.get(0);
		this.stockName = lstBean.get(1);
		this.industry = lstBean.get(2);
		this.area = lstBean.get(3);
		this.pe = Double.parseDouble(lstBean.get(4));
		this.outstanding =Double.parseDouble( lstBean.get(5))*100000000;
		this.totals = Double.parseDouble(lstBean.get(6))*100000000;
		this.totalAssets =Double.parseDouble( lstBean.get(7))*10000;
		this.liquidAssets =Double.parseDouble( lstBean.get(8));
		this.fixedAssets = Double.parseDouble(lstBean.get(9));
		this.reserved = Double.parseDouble(lstBean.get(10));
		this.reservedPerShare = Double.parseDouble(lstBean.get(11));
		this.esp = Double.parseDouble(lstBean.get(12));
		this.bvps =Double.parseDouble( lstBean.get(13));
		this.pb = Double.parseDouble(lstBean.get(14));
		this.timeToMarket = lstBean.get(15);
		this.undp = Double.parseDouble(lstBean.get(16));
		this.perundp = Double.parseDouble(lstBean.get(17));
		this.rev = Double.parseDouble(lstBean.get(18));
		this.profit = Double.parseDouble(lstBean.get(19));
		this.gpr =Double.parseDouble( lstBean.get(20));
		this.npr =Double.parseDouble( lstBean.get(21));
		this.holders = lstBean.size()>22?Double.parseDouble(lstBean.get(22)):0;
	}
	public StockDetailInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getZsz() {
		return zsz;
	}
	public void setZsz(double zsz) {
		this.zsz = zsz;
	}
	@Override
	public String toString() {
		return "StockDetailInfoBean [stockCode=" + stockCode + ", stockName=" + stockName + ", industry=" + industry
				+ ", area=" + area + ", pe=" + pe + ", outstanding=" + outstanding + ", totals=" + totals
				+ ", totalAssets=" + totalAssets + ", liquidAssets=" + liquidAssets + ", fixedAssets=" + fixedAssets
				+ ", reserved=" + reserved + ", reservedPerShare=" + reservedPerShare + ", esp=" + esp + ", bvps="
				+ bvps + ", pb=" + pb + ", timeToMarket=" + timeToMarket + ", undp=" + undp + ", perundp=" + perundp
				+ ", rev=" + rev + ", profit=" + profit + ", gpr=" + gpr + ", npr=" + npr + ", holders=" + holders
				+ ", zsz=" + zsz + "]";
	}
	
	
	
}
