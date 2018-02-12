package com.cmall.stock.Controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.strage.SelGetStock;
import com.cmall.stock.utils.TimeUtils;
import com.cmall.stock.vo.StockBaseInfoVo;
import com.cmall.stock.vo.StockBasePageInfo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class StockBaseInfoController extends BaseController<StockBaseInfo> {

	@RequestMapping("/getList")
	public Map<String, Object> getList(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		return SelGetStock.getLstResult(query, page);
	}
	
	/**
	 * 
	 * @description
	 * 	1.当日涨幅>=10% 或0
			2.两天涨幅超过15% 
			3.5天涨幅超过3天
			4.成交量大于前一日交易量2倍
			5.j值=0   》=2
			
			//3.5天内三天涨幅>0 
			//4.5天内涨超过15% 
			//5.10天内涨超过15% "
								
				次新股除外（上市时间超过6个月）   三点前查前一天数据（注意周六周日 向前减1,2天）				
	 */
	@RequestMapping("/getListLonghu")
	public Map<String, Object> getListLonghu(StockBasePageInfo page) throws Exception {
		//获取查询时间
		String date = TimeUtils.getStockDate();
		String dateJson = "{\"must\":\"must\",\"name\":\"date\",\"type\":\"=\",\"value\":\""+date+"\"}]";
		String json = page.getDatas();
		if(json.length() > 10){
			dateJson = "," + dateJson;
		}
		json = json.replace("]", dateJson);
		page.setDatas(json);
//		int pageStart = page.getPage();
//		int limit = page.getLimit();
//		page.setLimit(5000);
//		page.setPage(0);
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		return SelGetStock.getLstResult(query, page);
	}
	/**
	 * 指标榜
	 *     1. kdj 金叉  (kdj)
	 *     2.macd 金叉
	 *    3.5日内涨幅》-2  十日内涨幅》=-4且 macd大于0趋势向上
	 */
	@RequestMapping("/getListZhibiao")
	public Map<String, Object> getListZhibiao(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		return SelGetStock.getLstResult(query, page);
	}
	
	/**
	 * 查询风险指数(待实现逻辑)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFx")
	public Map<String, Object> getFx(StockBasePageInfo page) throws Exception {
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
	
	
	@RequestMapping("/getKDJ")
	public Map<String, Object> getKDJ(StockBasePageInfo page) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		List<StockBaseInfo> list = (List<StockBaseInfo>) SelGetStock.getLstResult(query, page).get("items");
		String[] dates = new String[list.size()];
		Float[] ks = new Float[list.size()];
		Float[] ds = new Float[list.size()];
		Float[] js = new Float[list.size()];
		Float[] diffs = new Float[list.size()];
		Float[] daes = new Float[list.size()];
		Object[][] object = new Object[list.size()][5];
		int j = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			Object[] ob = new Object[5];
			StockBaseInfo bean = list.get(i);
			dates[j] = bean.getDate();
			ks[j] = bean.getK();
			ds[j] = bean.getD();
			js[j] = bean.getJ()>100?100:bean.getJ();
			diffs[j] = bean.getDiff();
			daes[j] = bean.getDea();
			ob[0] = bean.getDate();
			ob[1] = bean.getOpen();
			ob[2] = bean.getClose();
			ob[3] = bean.getLow();
			ob[4] = bean.getHigh();
			object[j] = ob;
			j++;
		}
		map.put("date", dates);
		map.put("K", ks);
		map.put("D", ds);
		map.put("J", js);
		map.put("DIFF", diffs);
		map.put("DAE", daes);
		map.put("obj", object);
		return map;

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