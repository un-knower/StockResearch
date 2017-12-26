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
	
	/**
	 * 编号
	 */
	private String code;

    private double percent;

    //最高
    private double high;

    private int askvol3;

    private int askvol2;

    private int askvol5;

    private int askvol4;

    //现在价格
    private double price;

    //开盘价
    private double open;

    private double bid5;

    private double bid4;

    private double bid3;

    private double bid2;

    private double bid1;

    //最低价
    private double low;

    private double updown;

    private String type;

    private int bidvol1;

    private int status;

    private int bidvol3;

    private int bidvol2;

    //标签
    private String symbol;
    //修改时间
    private String update;

    private int bidvol5;

    private int bidvol4;

    private long volume;

    private int askvol1;

    private double ask5;

    private double ask4;

    private double ask1;

    //名字
    private String name;

    private double ask3;

    private double ask2;

    //涨还是跌
    private String arrow;

    //时间
    private String time;
    //昨天收盘价
    private double yestclose;
    //成交额
    private double turnover;
    
    //上一天涨幅
    private double upRises;
    //上一天的量
    private long upVolume; 
    //量比
    private Double volumeRises;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public int getAskvol3() {
		return askvol3;
	}

	public void setAskvol3(int askvol3) {
		this.askvol3 = askvol3;
	}

	public int getAskvol2() {
		return askvol2;
	}

	public void setAskvol2(int askvol2) {
		this.askvol2 = askvol2;
	}

	public int getAskvol5() {
		return askvol5;
	}

	public void setAskvol5(int askvol5) {
		this.askvol5 = askvol5;
	}

	public int getAskvol4() {
		return askvol4;
	}

	public void setAskvol4(int askvol4) {
		this.askvol4 = askvol4;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getBid5() {
		return bid5;
	}

	public void setBid5(double bid5) {
		this.bid5 = bid5;
	}

	public double getBid4() {
		return bid4;
	}

	public void setBid4(double bid4) {
		this.bid4 = bid4;
	}

	public double getBid3() {
		return bid3;
	}

	public void setBid3(double bid3) {
		this.bid3 = bid3;
	}

	public double getBid2() {
		return bid2;
	}

	public void setBid2(double bid2) {
		this.bid2 = bid2;
	}

	public double getBid1() {
		return bid1;
	}

	public void setBid1(double bid1) {
		this.bid1 = bid1;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getUpdown() {
		return updown;
	}

	public void setUpdown(double updown) {
		this.updown = updown;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getBidvol1() {
		return bidvol1;
	}

	public void setBidvol1(int bidvol1) {
		this.bidvol1 = bidvol1;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBidvol3() {
		return bidvol3;
	}

	public void setBidvol3(int bidvol3) {
		this.bidvol3 = bidvol3;
	}

	public int getBidvol2() {
		return bidvol2;
	}

	public void setBidvol2(int bidvol2) {
		this.bidvol2 = bidvol2;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public int getBidvol5() {
		return bidvol5;
	}

	public void setBidvol5(int bidvol5) {
		this.bidvol5 = bidvol5;
	}

	public int getBidvol4() {
		return bidvol4;
	}

	public void setBidvol4(int bidvol4) {
		this.bidvol4 = bidvol4;
	}

	

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public int getAskvol1() {
		return askvol1;
	}

	public void setAskvol1(int askvol1) {
		this.askvol1 = askvol1;
	}

	public double getAsk5() {
		return ask5;
	}

	public void setAsk5(double ask5) {
		this.ask5 = ask5;
	}

	public double getAsk4() {
		return ask4;
	}

	public void setAsk4(double ask4) {
		this.ask4 = ask4;
	}

	public double getAsk1() {
		return ask1;
	}

	public void setAsk1(double ask1) {
		this.ask1 = ask1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAsk3() {
		return ask3;
	}

	public void setAsk3(double ask3) {
		this.ask3 = ask3;
	}

	public double getAsk2() {
		return ask2;
	}

	public void setAsk2(double ask2) {
		this.ask2 = ask2;
	}

	public String getArrow() {
		return arrow;
	}

	public void setArrow(String arrow) {
		this.arrow = arrow;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getYestclose() {
		return yestclose;
	}

	public void setYestclose(double yestclose) {
		this.yestclose = yestclose;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public double getUpRises() {
		return upRises;
	}

	public long getUpVolume() {
		return upVolume;
	}

	public void setUpRises(double upRises) {
		this.upRises = upRises;
	}

	public void setUpVolume(long upVolume) {
		this.upVolume = upVolume;
	}

	public Double getVolumeRises() {
		return volumeRises;
	}

	public void setVolumeRises(Double volumeRises) {
		this.volumeRises = volumeRises;
	}
    
    
	
    
}