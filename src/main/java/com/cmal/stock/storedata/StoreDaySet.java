package com.cmal.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.search.sort.Sort;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmal.stock.strage.StockStragEnSey;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.utils.CsvHandUtils;
import com.cmall.stock.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.kers.esmodel.BaseCommonConfig;

/**
 * 获取每天更新数据
 * @author temp1
 *
 */
public class StoreDaySet {
	
	public final static String savePathsuff = "D://data//info//";
	
	public static void main(String[] args) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<String> lstSource =CommonBaseStockInfo.getAllAStockInfo();;
		 final JestClient  jestClient =BaseCommonConfig.clientConfig();
		for(final String  sat:lstSource){
			if(sat.equals("600345")){
				String neDate = getNextDate(sat,jestClient);
				 List<StockBaseInfo> list = getstockBaseInfoFile(sat);
//				 List<StockBaseInfo> addlist = new ArrayList<StockBaseInfo>();
//				 for (StockBaseInfo stockBaseInfo : list) {
//					if(!neDate.equals("") && df.parse(stockBaseInfo.getDate()).getTime() <= df.parse(neDate).getTime() ){
//							break;
//					}
//					addlist.add(stockBaseInfo);
//					System.out.println(stockBaseInfo.toString());
//				}
//				 StoreAstockTradInfo.insBatchEs(addlist, jestClient, "stockpcse");
			}
		}
		 
		
	        
	    
	       
	}
	
	public static String getNextDate(String stockCode , JestClient  jestClient) throws Exception{
		String date = "";
		 SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();  
	        QueryBuilder queryBuilder = QueryBuilders  
	            .termQuery("stockCode", stockCode);//单值完全匹配查询  
	        searchSourceBuilder.query(queryBuilder);  
	        searchSourceBuilder.size(1000);  
	        searchSourceBuilder.from(0);
	        String query = searchSourceBuilder.toString();   
	       // System.out.println(query);
	        Sort sort = new Sort("date");
	        Search search = new Search.Builder(query)
         .addIndex("stockpcse")  
         .addType("2017").addSort(sort)
         .build();  
	        JestResult result =jestClient.execute(search);  
	        List<StockBaseInfo> dateList =  result.getSourceAsObjectList(StockBaseInfo.class);
	        if(dateList.size() > 0){
	        	for (StockBaseInfo stockBaseInfo : dateList) {
	        		date = stockBaseInfo.getDate();
				}
	        }
	       return date;
	}
	
	public static List<StockBaseInfo> getstockBaseInfoFile(String stockCode) throws Exception {
		  final StockStragEnSey stockStragEnSey = new StockStragEnSey();
		String absPath = savePathsuff + stockCode + ".csv";
		CsvHandUtils csvHandUtils = new CsvHandUtils(absPath);
		List<List<String>> lstSource = csvHandUtils.readCSVFile();
		List<StockBaseInfo> result = Lists.newArrayList();
		for (int i = lstSource.size() - 1; i >=1; i--) {
			// List<String> llData=
			List<String> objdata = lstSource.get(i);
			// System.out.println(lstSource.get(i).get(1));
			//Integer.parseInt(
			//日期,股票代码,名称,收盘价,最高价,最低价,开盘价,前收盘,涨跌额,涨跌幅,换手率(10),成交量,成交金额(12),总市值(13),流通市值(14),成交笔数(15)
			//String date, String open, String high, String low, String close, String volume, String rises,
			 
	
//		//,成交金额,总市值,流通市值,成交笔数
//			System.out.println(objdata);
//			System.out.println(objdata.size());
			StockBaseInfo stockBaseInfo = new StockBaseInfo(objdata.get(0), objdata.get(6), objdata.get(4),
					objdata.get(5), objdata.get(3), objdata.get(11), objdata.get(9), stockCode, objdata.get(2),objdata.get(10),objdata.get(12),objdata.get(13),objdata.get(14),objdata.size()<15?null: objdata.get(14),TimeUtils.dayForWeek(objdata.get(0))+"");
			//System.out.println(stockBaseInfo.toString());
			if(!(stockBaseInfo.getOpen()==0||stockBaseInfo.getClose()==0||stockBaseInfo.getHigh()==0||stockBaseInfo.getLow()==0)){///|StringUtils.isBlank(stockBaseInfo.getCjbs()))){//!(stockBaseInfo.getOpen()==0||stockBaseInfo.getClose()==0||stockBaseInfo.getHigh()==0||stockBaseInfo.getLow()==0||StringUtils.isBlank(stockBaseInfo.getCjbs()))){
				result.add(stockBaseInfo);
			}
			//|stockBaseInfo.getRises()))
			
		}
		  stockStragEnSey.addStockBaseInfos(result);
		  stockStragEnSey.computeStockIndex();
		return result;

	}
}
