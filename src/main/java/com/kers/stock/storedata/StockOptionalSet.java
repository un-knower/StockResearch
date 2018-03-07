package com.kers.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.stock.bean.StockOptionalInfo;
import com.kers.stock.bean.StockRealBean;

/**
 * 获取自选数据
 * 
 * @author temp1
 *
 */
public class StockOptionalSet {

	public static void insBatchEs(List<StockOptionalInfo> list, JestClient jestClient, String indexIns) throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockOptionalInfo bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type("2018")
					.id(bean.getStockCode()).build();// type("walunifolia").build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}
	
	public static void insBatchBean(StockOptionalInfo bean, JestClient jestClient, String indexIns) throws Exception {
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		Index index = new Index.Builder(bean).index(indexIns).type("2018")
					.id(bean.getStockCode()).build();// type("walunifolia").build();
		bulkBuilder.addAction(index);
		jestClient.execute(bulkBuilder.build());
		bulkBuilder = new Bulk.Builder();
		jestClient.execute(bulkBuilder.build());
	}
	
	public static void deleteBean(StockOptionalInfo bean, JestClient jestClient, String indexIns) throws Exception {
		Delete delete = new Delete.Builder(indexIns , "2018" , bean.getStockCode())
		.index(indexIns).type("2018").id(bean.getStockCode()).build();
        jestClient.execute(delete) ;
	}
	
	public static List<StockOptionalInfo> getList(String indexIns,List<String> q) throws Exception{
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		if(null != q){
			for (String string : q) {
				String[] qs = string.split(",");
				query.must(QueryBuilders.termQuery(qs[0],qs[1]));
			}
		}
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		Search selResult = UtilEs.getSearch(searchSourceBuilder, indexIns, "2018", 0 , 1000);
		JestResult results = jestClient.execute(selResult);
		List<StockOptionalInfo> lstBean = results.getSourceAsObjectList(StockOptionalInfo.class);
		return lstBean;
	}
	
	public static void delzhi(String indexIns,JestClient jestClient) throws Exception{
		List<String> q = Lists.newArrayList();
		q.add("jrzblt,1");
		List<StockOptionalInfo> list = getList(indexIns,q);
		List<StockOptionalInfo> delList = Lists.newArrayList();
		List<StockOptionalInfo> upList = Lists.newArrayList();
		for (StockOptionalInfo stockOptionalInfo : list) {
			if(stockOptionalInfo.getAddType() == 1){
				delList.add(stockOptionalInfo);
			}
			if(stockOptionalInfo.getAddType() == 0){
				stockOptionalInfo.setJrzblt(0);
				upList.add(stockOptionalInfo);
			}
		}
		if(delList.size() > 0){
			for (StockOptionalInfo stockOptionalInfo : delList) {
				try{
					deleteBean(stockOptionalInfo,jestClient,indexIns);
				}catch(Exception e){
					
				}
			}
		}
		if(upList.size() > 0){
			insBatchEs(upList,jestClient,indexIns);
		}
	}
}
