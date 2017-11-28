package com.cmal.stock.strage;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockStrategyInfo;
import com.cmall.stock.bean.StoreTrailer;
import com.cmall.stock.vo.StockBasePageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
/**
 * 
 *       
 * ClassName：SelGetStock    
 * Description：    
 * author ：admin    
 * date ：2017年11月11日 下午3:37:33    
 * Modified   person：admin    
 * Modified date：2017年11月11日 下午3:37:33    
 * Modify remarks：    
 * @version     V1.0
 * 
 * 
 * 
 * 1.macd连涨天数
 * 2.  5|10天内macd涨的天数
 *
 */
public class SelGetStock {
	
	

	public static Map<String,Object> getLstResult(BoolQueryBuilder query , StockBasePageInfo page) throws Exception {
		Map<String,Object> returnMap = Maps.newHashMap();
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if(!StringUtils.isEmpty(page.getSort())){
			String order = page.getSort().split("\\.")[1];
			if(order.equalsIgnoreCase("desc")){
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.DESC);
			}else{
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.ASC);
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, "stockpcse", "2017", (page.getPage()- 1) * page.getLimit() , page.getLimit());
		
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<StockBaseInfo> lstBean = results.getSourceAsObjectList(StockBaseInfo.class);
		if(lstBean!= null && lstBean.size() > 0){
			Map hitsMap = (Map)results.getValue("hits");
			if(hitsMap!=null){
				Number total = (Number)hitsMap.get("total");
				if(total!=null){
					returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstBean);
		return returnMap;

	}
	
	
	public static Map<String,Object> getReportLstResult(BoolQueryBuilder query , StockBasePageInfo page , String type) throws Exception {
		Map<String,Object> returnMap = Maps.newHashMap();
		List<String> types= Lists.newArrayList();
		System.out.println(type);
		if(type.equals("2017")){//StringUtils.isEmpty(type)||type.equals(",")){
			types.add("2017-09-30");
			types.add("2017-06-30");
			types.add("2017-03-31");
			
			types.add("2016-12-31");
			types.add("2016-09-30");
			types.add("2016-06-30");
			types.add("2016-03-31");
			
			types.add("2015-12-31");
			types.add("2015-09-30");
			types.add("2015-06-30");
			types.add("2015-03-31");
		}else{
			types.add("2017-09-30");
		}
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if(!StringUtils.isEmpty(page.getSort())){
			String order = page.getSort().split("\\.")[1];
			if(order.equalsIgnoreCase("desc")){
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.DESC);
			}else{
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.ASC);
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		System.out.println(types);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, "storereport", types, (page.getPage()- 1) * page.getLimit() , page.getLimit());
		
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<EastReportBean> lstBean = results.getSourceAsObjectList(EastReportBean.class);
		if(lstBean!= null && lstBean.size() > 0){
			Map hitsMap = (Map)results.getValue("hits");
			if(hitsMap!=null){
				Number total = (Number)hitsMap.get("total");
				if(total!=null){
					returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstBean);
		return returnMap;

	} 
	
	
	public static Map<String,Object> getTrailerLstResult(BoolQueryBuilder query , StockBasePageInfo page , String type) throws Exception {
		Map<String,Object> returnMap = Maps.newHashMap();
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if(!StringUtils.isEmpty(page.getSort())){
			String order = page.getSort().split("\\.")[1];
			if(order.equalsIgnoreCase("desc")){
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.DESC);
			}else{
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.ASC);
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, "storetrailer", type, (page.getPage()- 1) * page.getLimit() , page.getLimit());
		
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<StoreTrailer> lstBean = results.getSourceAsObjectList(StoreTrailer.class);
		if(lstBean!= null && lstBean.size() > 0){
			Map hitsMap = (Map)results.getValue("hits");
			if(hitsMap!=null){
				Number total = (Number)hitsMap.get("total");
				if(total!=null){
					returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstBean);
		return returnMap;

	} 
	
	public static Map<String,Object> getStaLstResult(BoolQueryBuilder query , StockBasePageInfo page , String type) throws Exception {
		Map<String,Object> returnMap = Maps.newHashMap();
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if(!StringUtils.isEmpty(page.getSort())){
			String order = page.getSort().split("\\.")[1];
			if(order.equalsIgnoreCase("desc")){
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.DESC);
			}else{
				ssb.sort(page.getSort().split("\\.")[0],SortOrder.ASC);
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, "storestrateinfo", type, (page.getPage()- 1) * page.getLimit() , page.getLimit());
		
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<StockStrategyInfo> lstBean = results.getSourceAsObjectList(StockStrategyInfo.class);
		if(lstBean!= null && lstBean.size() > 0){
			Map hitsMap = (Map)results.getValue("hits");
			if(hitsMap!=null){
				Number total = (Number)hitsMap.get("total");
				if(total!=null){
					returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstBean);
		return returnMap;

	} 

	public static void main(String[] args) throws Exception {

//		BoolQueryBuilder query = QueryBuilders.boolQuery();
//		query.must(QueryBuilders.rangeQuery("macd").from(0.01));
//		query.must(QueryBuilders.termQuery("date", "2017-11-09"));
//		List<StockBaseInfo> lstBean = getLstResult(query);
//
//		query = QueryBuilders.boolQuery();
//		// query.must(QueryBuilders.rangeQuery("macd").from(0.01));
//		query.must(QueryBuilders.termQuery("date", "2017-11-09"));
//		List<StockBaseInfo> lstBeanNw = getLstResult(query);
//		Map<String, StockBaseInfo> maps = StoreAstockTradInfo.fetchKeyStockInfo(lstBean);
//		// System.out.println(maps);
//		System.out.println("股票代码" + "\t" + "股票名称" +"\t"+"MACD差值"+ "\t" + "成交量前一交易日增大倍数" + "\t" + "增长" + "\t" + "MACD" + "\t" + "J差值" + "\t"
//				+ "成交量5平均" + "\t" + "K" + "\t" + "D" + "\t" + "J");
//		for (StockBaseInfo b : lstBeanNw) {
//			if ((b.getVolume() / b.getVolumeMa5()>1.3) && b.getVolume() > 0) {
//				
//				// System.out.println(b.toString());
//				// System.out.println(b.getStockCode());
//				StockBaseInfo obj = maps.get(b.getStockCode());
//				if (obj != null) {// 非停牌
//					String c = (Double.parseDouble(b.getVolume()+"") / Double.parseDouble(obj.getVolume()+"")  + "");
//					c = c.substring(0, 4);
//					String bps = maps.get(b.getStockCode()).getRises();// maps.get(b.getStockCode())==null?"
//																		// ":((maps.get(b.getStockCode()).getClose()-b.getClose())/b.getClose()*100)+"";
//
//					System.out.println(b.getStockCode() + "\t" + b.getStockName() +"\t"+(b.getMacd()/obj.getMacd())+ "\t" + c + "\t" + b.getRises() + "\t"
//							+ b.getMacd() + "\t" + (b.getJ()-obj.getJ()) + "\t" +(b.getVolume() / b.getVolumeMa5())+ " \t" + b.getK() + "\t"
//							+ b.getD() + "\t" + b.getJ());
//				}
//			}
//		}

		// System.out.println(lstBean);
		// System.out.println(lstBean.size());
		// bean=results.getSourceAsObject(UnionFotoliaBean.class);
		// if(bean!=null)
		// return results.getSourceAsObject(UnionFotoliaBean.class);
		// selResult.getData(gson)
	}

}
