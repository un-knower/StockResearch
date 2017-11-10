/**
  * Copyright 2017 bejson.com 
  */
package com.cmall.stock.bean;

import java.io.Serializable;

/**
 * Auto-generated: 2017-11-09 13:40:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class StockRealBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2658725412294038897L;
	private String symbol;
    private String code;
    private String name;
    private String trade;
    private String pricechange;
    private String changepercent;
    private String buy;
    private String sell;
    private String settlement;
    private String open;
    private String high;
    private String low;
    private String volume;
    private String amount;
    private String ticktime;
    private String per;
    private String pb;
    private String mktcap;
    private String nmc;
    private String turnoverratio;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getPricechange() {
		return pricechange;
	}
	public void setPricechange(String pricechange) {
		this.pricechange = pricechange;
	}
	public String getChangepercent() {
		return changepercent;
	}
	public void setChangepercent(String changepercent) {
		this.changepercent = changepercent;
	}
	public String getBuy() {
		return buy;
	}
	public void setBuy(String buy) {
		this.buy = buy;
	}
	public String getSell() {
		return sell;
	}
	public void setSell(String sell) {
		this.sell = sell;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTicktime() {
		return ticktime;
	}
	public void setTicktime(String ticktime) {
		this.ticktime = ticktime;
	}
	public String getPer() {
		return per;
	}
	public void setPer(String per) {
		this.per = per;
	}
	public String getPb() {
		return pb;
	}
	public void setPb(String pb) {
		this.pb = pb;
	}
	public String getMktcap() {
		return mktcap;
	}
	public void setMktcap(String mktcap) {
		this.mktcap = mktcap;
	}
	public String getNmc() {
		return nmc;
	}
	public void setNmc(String nmc) {
		this.nmc = nmc;
	}
	public String getTurnoverratio() {
		return turnoverratio;
	}
	public void setTurnoverratio(String turnoverratio) {
		this.turnoverratio = turnoverratio;
	}
	@Override
	public String toString() {
		return "StockRealBean [symbol=" + symbol + ", code=" + code + ", name="
				+ name + ", trade=" + trade + ", pricechange=" + pricechange
				+ ", changepercent=" + changepercent + ", buy=" + buy
				+ ", sell=" + sell + ", settlement=" + settlement + ", open="
				+ open + ", high=" + high + ", low=" + low + ", volume="
				+ volume + ", amount=" + amount + ", ticktime=" + ticktime
				+ ", per=" + per + ", pb=" + pb + ", mktcap=" + mktcap
				+ ", nmc=" + nmc + ", turnoverratio=" + turnoverratio + "]";
	}
    
}