package com.cmall.stock.Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmal.stock.strage.SelGetStock;
import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.vo.StockBasePageInfo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@RestController
public class StockBaseInfoController extends BaseController<StockBaseInfo> {

	@RequestMapping("/getList")
	public Map<String, Object> getList(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		return SelGetStock.getLstResult(query, page);
	}

	@RequestMapping("/getClassNameList")
	public String[] getClassNameList() throws Exception {
		StockBaseInfo info = new StockBaseInfo();
		java.lang.reflect.Field[] fields = info.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length - 1];
		for (int i = 1; i < fields.length; i++) {
			fieldNames[i - 1] = fields[i].getName();
		}
		return fieldNames;
	}

	@RequestMapping("/focDays/getList")
	public Map<String, Object> getfocDaysList(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();

		setQuery(query, page);
		return SelGetStock.getfocDaysLstResult(query, page);
	}

	@RequestMapping("/focDays/getGraphCompare")
	public Object[] getGraphCompare(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
		setQuery(query, page);
		System.out.println(page.getSort());
		List<StockBaseInfo> list = (List<StockBaseInfo>) SelGetStock.getfocDaysLstResult(query, page).get("items");

		Set<String> legendData = Sets.newTreeSet();
		Set<String> xAxisData = Sets.newTreeSet();
		Map<String, SeriesBean> maps = Maps.newConcurrentMap();

		for (StockBaseInfo bean : list) {
			legendData.add(bean.getStockName());
//			if(!xAxisData.contains(bean.getDate()))
//				System.out.println(bean.getDate());
			xAxisData.add(bean.getDate());
			
			// maps.put(bean.getStockName(), bean);
			String key=bean.getStockName().trim();
			if ( maps.get(key)== null) {
				SeriesBean beanss = new SeriesBean();
				beanss.setData(Lists.newArrayList(bean.getRises()));
				beanss.setName(bean.getStockName());
				beanss.setType("line");
				beanss.setStack("总量");
				maps.put(key, beanss);
			} else {
				SeriesBean beanss = maps.get(key);
				beanss.getData().add(bean.getRises());
				maps.put(key, beanss);
			}

		}
		
		Object[] str = new String[3];
		str[0] = "data:\""+legendData.toString()+"\"";

		str[1] = xAxisData.toString().replace("[", "").replace("]", "");
//		System.out.println(str[1]);
		str[2] = maps.values().toString();
		//System.out.println(maps);
		return str;

	}

	@RequestMapping("/focDays/getClassNameList")
	public String[] getClassfocDaysNameList() throws Exception {
		StockBaseInfo info = new StockBaseInfo();
		java.lang.reflect.Field[] fields = info.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length - 1];
		for (int i = 1; i < fields.length; i++) {
			fieldNames[i - 1] = fields[i].getName();
		}
		return fieldNames;
	}

}

class SeriesBean {
	private String name;
	private String type;
	private String stack;
	private List data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "{name: '" + name + "', type:'" + type + "', stack:'" + stack + "', data:" + data + "}";
	}
	
	
}