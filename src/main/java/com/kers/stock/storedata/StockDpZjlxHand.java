package com.kers.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;
import com.kers.stock.bean.RzRqBean;
import com.kers.stock.bean.StockDpZjlxBean;
import com.kers.stock.storedata.RzRqHand.Jsonobj;


/**
 * 
 * @author chenshenlin
 * 大盘资金流向
 *
 */
public class StockDpZjlxHand {
	
	static JestClient jestClient = BaseCommonConfig.clientConfig();
	
	public static void getAllDatas(String stopDate) throws Exception{
		
		List<StockDpZjlxBean> addList = Lists.newArrayList();
		String url = "http://data.eastmoney.com/zjlx/dpzjlx.html";
		String content = BaseConnClient.baseGetReq(url,"utf-8");
		if(content.indexOf("var DefaultJson=") > 0){
			content = content.split("var DefaultJson=")[1];
			if(content.indexOf("]") > 0){
				content = content.split("]")[0] + "]";
			}
		}
		Type type = new TypeToken<List<String>>() {}.getType();
		Gson gson = new Gson();
		List<String> List = gson.fromJson(content, type);
		for (int i = List.size() - 1; i >= 0; i--) {
			String val = List.get(i).replace("%", "");
			String[] vals = val.split(",");
			StockDpZjlxBean bean = new StockDpZjlxBean(vals[0], vals[11], vals[12], vals[13], vals[14], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7], vals[8], vals[9], vals[10]);
			if(null !=stopDate && !stopDate.equals("") && bean.getRq().equals(stopDate)){
				break;
			}
			addList.add(bean);
		}
		System.out.println("本次添加数据：" + addList.size());
		if(addList.size() > 0){
			insBatchEs(addList,jestClient,CommonBaseStockInfo.ES_INDEX_STOCK_DPZJLX);
		}
	}
	
	public static void getBreakPointDatas(){
		String stopDate = "";
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		ssb.sort("rq", SortOrder.DESC);
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_DPZJLX, CommonBaseStockInfo.ES_INDEX_STOCK_DPZJLX,
				1, 1);
		StockDpZjlxBean bean = new StockDpZjlxBean();
		try {
			JestResult results = jestClient.execute(selResult);
			if(results.isSucceeded()){
				bean = results.getSourceAsObject(StockDpZjlxBean.class);
				stopDate = bean.getRq();
				System.out.println(stopDate);
			}
			getAllDatas(stopDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insBatchEs(List<StockDpZjlxBean> list, JestClient jestClient, String indexIns) throws Exception {
		if (list == null || list.isEmpty())
			return;
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockDpZjlxBean bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type(indexIns)
					.id(bean.getRq()).build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}
	
	
	public static void main(String[] args) throws Exception {
		getAllDatas("");
		
//		getBreakPointDatas();
	}
}

