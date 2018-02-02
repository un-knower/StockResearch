package com.cmal.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.search.sort.Sort;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockRealBean;
import com.cmall.stock.bean.StoreTrailer;
import com.cmall.stock.utils.MathsUtils;
import com.cmall.stock.utils.TextUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;

/**
 * 获取业绩预告数据
 * 
 * @author temp1
 *
 */
public class StoreRealSet {
	public static final String   P_TYPE_2017_12_31="2017-12-31";
	public static void main(String[] args) throws Exception {
		
		
		
		//sVo();
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		
		getUpMap(jestClient);
//		String code = "0600581";
//		for (int i = 0; i < 10000; i++) {
//			String content = StoreRealUrl(code);
//			List<StockRealBean> list = new ArrayList<StockRealBean>();
//			StockRealBean bean = getList(content , code);
//			System.out.println(bean.getPrice() + "\t" + bean.getHigh() + "\t" + (bean.getPrice() / bean.getYestclose() * 100 - 100));
//			list.add(bean);
//			insBatchEs(list,jestClient,"stockrealinfo");
//			Thread.sleep(2000);
//		}
	}
	
	public static StockRealBean getBeanByCode(String sat) throws ClientProtocolException, IOException{
		String code = sat;
		if((sat.split("")[0].equals("0") || sat.split("")[0].equals("3")) && !sat.equals("000001")){
			code = "1" + sat;
		}else{
			code = "0" + sat;
		}
		String content = StoreRealUrl(code);
//		System.out.println(content);
		StockRealBean bean = getList(content , code);
		return bean;
	}
	
	private static void sVo() throws Exception{
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		List<String> lstSource =CommonBaseStockInfo.getAllAStockInfo();
		for(String  sat:lstSource){
			//StockBaseInfo info = getNextDate(sat,jestClient);
			String code = sat;
			if((sat.split("")[0].equals("0") || sat.split("")[0].equals("3")) && !sat.equals("000001")){
				code = "1" + sat;
			}else{
				code = "0" + sat;
			}
//			System.out.println(sat);
			String content = StoreRealUrl(code);
			
			StockRealBean bean = getList(content , code);
			String www = bean.getName() + "\t" +
				sat  + "\t" + bean.getUpdown() + "\t"+ (bean.getPrice() / bean.getYestclose() * 100 - 100);
//			FileUtils.write(file, "");
//			Integer d = bean.getVolume();
//			if(d >= upVo){
//				double b = (double) d / (double)upVo - 1;
//				System.out.println(bean.getName() + " :" +
//				sat + " 昨天的量：" + upVo + " 现在的量：" + d + " 涨幅百分比："+(b*100) + "%");
//			}
			new TextUtil().writerTxt("D://gits//dataGrab//sssss.txt",www);
			
		}
	}

	public static StockRealBean getList(String data , String code) {
		StockRealBean bean = new StockRealBean();
//		System.out.println(data);
		data = data.replace("_ntes_quote_callback({\""+code+"\":" , "");
		data = data.replace("});", "");
		if(data.indexOf("callback") > 0){
			return null;
		}
		Type type = new TypeToken<StockRealBean>() {
		}.getType();
		Gson gson = new Gson();
		bean = gson.fromJson(data, type);
		return bean;
	}

	public static String StoreRealUrl(String StoreCode) throws ClientProtocolException, IOException {
		String url = "http://api.money.126.net/data/feed/"+StoreCode+",money.api";
		String content = BaseConnClient.baseGetReq(url);
		System.out.println(content);
		return content;
	}

	public static StoreTrailer getDate(String date) {
		StoreTrailer tr = new StoreTrailer();
		String[] datas = date.split(",");
		tr.setStockCode(datas[0]);
		tr.setStockName(datas[1]);
		tr.setPerChanges(datas[2]);
		tr.setRangeability(datas[3]);
		if (!StringUtils.isEmpty(tr.getRangeability())) {
			String[] s = tr.getRangeability().split("～");
			tr.setStartRangeability(Double.parseDouble(s[0].replace("%", "")));
			if (s.length > 1) {
				tr.setEndRangeability(Double.parseDouble(s[1].replace("%", "")));
			}
		}
		tr.setType(datas[4]);
		tr.setNetProfit(MathsUtils.parseDouble(datas[5]));
		tr.setStartDate(datas[7]);
		tr.setEndDate(datas[8]);
		return tr;
	}

	public static void insBatchEs(List<StockRealBean> list, JestClient jestClient, String indexIns) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockRealBean bean : list) {
			i++;
			// System.out.println(bean.getUnionId());
			Index index = new Index.Builder(bean).index(indexIns).type(formatter.format(new Date()))
					.id(bean.getSymbol() + formatter.format(new Date())).build();// type("walunifolia").build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
		// jestClient.shutdownClient();
	}
	
	public static StockBaseInfo getNextDate(String stockCode , JestClient  jestClient) throws Exception{
		StockBaseInfo date = null;
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
	        		date = stockBaseInfo;
				}
	        }
	       return date;
	}

	
	public static Map<String, StockBaseInfo> getUpMap( JestClient  jestClient) throws Exception{
		Map<String, StockBaseInfo> map = Maps.newHashMap();
		List<StockBaseInfo> list = Lists.newArrayList();
		Calendar calendar = Calendar.getInstance();
		while (list == null || list.size() == 0) {
			calendar.add(Calendar.DATE, -1);    //得到前一天
			String  yestedayDate
				= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			 SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();  
		        QueryBuilder queryBuilder = QueryBuilders  
		            .termQuery("date", yestedayDate);//单值完全匹配查询  
		        searchSourceBuilder.query(queryBuilder);  
		        searchSourceBuilder.size(5000);  
		        searchSourceBuilder.from(0);
		        String query = searchSourceBuilder.toString();   
		        Search search = new Search.Builder(query)
	         .addIndex("stockpcse")  
	         .addType("2017")
	         .build();  
		        JestResult result =jestClient.execute(search);  
		        list =  result.getSourceAsObjectList(StockBaseInfo.class);
		}
		for (StockBaseInfo stockBaseInfo : list) {
			map.put(stockBaseInfo.getStockCode(), stockBaseInfo);
		}
        return map;
	}
}
