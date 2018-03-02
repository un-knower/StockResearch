package com.kers.stock.bean;

public class RzRqGGBean {
	private String scode;//证券代码
    private String sname;//证券名称
    //0大盘 1个股
    private int type;
    private double hfqjg;
    private String tdate;//时间
    private String market;//交易所
    
    private double close;//收盘价
    private double zdf;//涨跌幅
    private double zdf3;//3天涨跌幅
    private double zdf5;//5天涨跌幅
    private double zdf10;//10天涨跌幅
    private double AGSZBHXS;
    private double rzye;//融资余额
    private double rzyezb;//余额占流通市值比例
    private double rzmre;//融资买入额
    private double rzmre3;//3天融资买入额
    private double rzmre5;//5天融资买入额
    private double rzmre10;//10天融资买入额
    private double rzche;//融资偿还额
    private double rzche3;//3天融资偿还额
    private double rzche5;//5天融资偿还额
    private double rzche10;//10天融资偿还额
    private double rzjmre;//融资净买入
    private double rzjmre3;//3天融资净买入
    private double rzjmre5;//5天融资净买入
    private double rzjmre10;//10天融资净买入
    private double rqye;//融券余额
    private double rqyl;//融券余量
    private double rqmcl;//融券卖出量
    private double rqmcl3;//3天融券卖出量
    private double rqmcl5;//5天融券卖出量
    private double rqmcl10;//10天融券卖出量
    private double rqchl;//融券偿还量
    private double rqchl3;//3天融券偿还量
    private double rqchl5;//5天融券偿还量
    private double rqchl10;//10天融券偿还量
    private double rqjmcl;//融券净卖出
    private double rqjmcl3;//3天融券净卖出
    private double rqjmcl5;//5天融券净卖出
    private double rqjmcl10;//10天融券净卖出
    private double rzrqye;//融资融券余额
    private double rzrqyecz;//融资融券余额差值

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public double getAGSZBHXS() {
		return AGSZBHXS;
	}

	public void setAGSZBHXS(double aGSZBHXS) {
		AGSZBHXS = aGSZBHXS;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getZdf() {
		return zdf;
	}

	public void setZdf(double zdf) {
		this.zdf = zdf;
	}

	public double getZdf3() {
		return zdf3;
	}

	public void setZdf3(double zdf3) {
		this.zdf3 = zdf3;
	}

	public double getZdf5() {
		return zdf5;
	}

	public void setZdf5(double zdf5) {
		this.zdf5 = zdf5;
	}

	public double getZdf10() {
		return zdf10;
	}

	public void setZdf10(double zdf10) {
		this.zdf10 = zdf10;
	}

	public double getRzye() {
		return rzye;
	}

	public void setRzye(double rzye) {
		this.rzye = rzye;
	}

	public double getRzyezb() {
		return rzyezb;
	}

	public void setRzyezb(double rzyezb) {
		this.rzyezb = rzyezb;
	}

	public double getRzmre() {
		return rzmre;
	}

	public void setRzmre(double rzmre) {
		this.rzmre = rzmre;
	}

	public double getRzmre3() {
		return rzmre3;
	}

	public void setRzmre3(double rzmre3) {
		this.rzmre3 = rzmre3;
	}

	public double getRzmre5() {
		return rzmre5;
	}

	public void setRzmre5(double rzmre5) {
		this.rzmre5 = rzmre5;
	}

	public double getRzmre10() {
		return rzmre10;
	}

	public void setRzmre10(double rzmre10) {
		this.rzmre10 = rzmre10;
	}

	public double getRzche() {
		return rzche;
	}

	public void setRzche(double rzche) {
		this.rzche = rzche;
	}

	public double getRzche3() {
		return rzche3;
	}

	public void setRzche3(double rzche3) {
		this.rzche3 = rzche3;
	}

	public double getRzche5() {
		return rzche5;
	}

	public void setRzche5(double rzche5) {
		this.rzche5 = rzche5;
	}

	public double getRzche10() {
		return rzche10;
	}

	public void setRzche10(double rzche10) {
		this.rzche10 = rzche10;
	}

	public double getRzjmre() {
		return rzjmre;
	}

	public void setRzjmre(double rzjmre) {
		this.rzjmre = rzjmre;
	}

	public double getRzjmre3() {
		return rzjmre3;
	}

	public void setRzjmre3(double rzjmre3) {
		this.rzjmre3 = rzjmre3;
	}

	public double getRzjmre5() {
		return rzjmre5;
	}

	public void setRzjmre5(double rzjmre5) {
		this.rzjmre5 = rzjmre5;
	}

	public double getRzjmre10() {
		return rzjmre10;
	}

	public void setRzjmre10(double rzjmre10) {
		this.rzjmre10 = rzjmre10;
	}

	public double getRqye() {
		return rqye;
	}

	public void setRqye(double rqye) {
		this.rqye = rqye;
	}

	public double getRqyl() {
		return rqyl;
	}

	public void setRqyl(double rqyl) {
		this.rqyl = rqyl;
	}

	public double getRqmcl() {
		return rqmcl;
	}

	public void setRqmcl(double rqmcl) {
		this.rqmcl = rqmcl;
	}

	public double getRqmcl3() {
		return rqmcl3;
	}

	public void setRqmcl3(double rqmcl3) {
		this.rqmcl3 = rqmcl3;
	}

	public double getRqmcl5() {
		return rqmcl5;
	}

	public void setRqmcl5(double rqmcl5) {
		this.rqmcl5 = rqmcl5;
	}

	public double getRqmcl10() {
		return rqmcl10;
	}

	public void setRqmcl10(double rqmcl10) {
		this.rqmcl10 = rqmcl10;
	}

	public double getRqchl() {
		return rqchl;
	}

	public void setRqchl(double rqchl) {
		this.rqchl = rqchl;
	}

	public double getRqchl3() {
		return rqchl3;
	}

	public void setRqchl3(double rqchl3) {
		this.rqchl3 = rqchl3;
	}

	public double getRqchl5() {
		return rqchl5;
	}

	public void setRqchl5(double rqchl5) {
		this.rqchl5 = rqchl5;
	}

	public double getRqchl10() {
		return rqchl10;
	}

	public void setRqchl10(double rqchl10) {
		this.rqchl10 = rqchl10;
	}

	public double getRqjmcl() {
		return rqjmcl;
	}

	public void setRqjmcl(double rqjmcl) {
		this.rqjmcl = rqjmcl;
	}

	public double getRqjmcl3() {
		return rqjmcl3;
	}

	public void setRqjmcl3(double rqjmcl3) {
		this.rqjmcl3 = rqjmcl3;
	}

	public double getRqjmcl5() {
		return rqjmcl5;
	}

	public void setRqjmcl5(double rqjmcl5) {
		this.rqjmcl5 = rqjmcl5;
	}

	public double getRqjmcl10() {
		return rqjmcl10;
	}

	public void setRqjmcl10(double rqjmcl10) {
		this.rqjmcl10 = rqjmcl10;
	}

	public double getRzrqye() {
		return rzrqye;
	}

	public void setRzrqye(double rzrqye) {
		this.rzrqye = rzrqye;
	}

	public double getRzrqyecz() {
		return rzrqyecz;
	}

	public void setRzrqyecz(double rzrqyecz) {
		this.rzrqyecz = rzrqyecz;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public double getHfqjg() {
		return hfqjg;
	}

	public void setHfqjg(double hfqjg) {
		this.hfqjg = hfqjg;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public RzRqGGBean transDatas(RzRqBean rzrq){
		RzRqGGBean gg = new RzRqGGBean();
		return gg;
	}
}
