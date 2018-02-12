package com.kers.stock.bean;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BGPO {
	//股票名称
	private String name;
	//股票行业
	private String hy;
	//股票季度，涨幅
	private Map<String,String> map = Maps.newConcurrentMap();
	//相同行业集合
	private List<BGPO> bgpos= Lists.newArrayList();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHy() {
		return hy;
	}
	public void setHy(String hy) {
		this.hy = hy;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public List<BGPO> getBgpos() {
		return bgpos;
	}
	public void setBgpos(List<BGPO> bgpos) {
		this.bgpos = bgpos;
	}
	@Override
	public String toString() {
		return "BGPO [name=" + name + ", hy=" + hy + ", map=" + map
				+ ", bgpos=" + bgpos + "]";
	}
	
	
	

}
