package com.cmal.stock.storedata;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.utils.CsvHandUtils;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

public class CommonBaseStockInfo {
	public final static String DETAIL_CONNPATH = "http://file.tushare.org/tsdata/all.csv";

	public final static String   ES_INDEX_GDZJC="gdzjc";
	public final static String ES_INDEX_STOCKREALINFO="stockrealinfo";
	public final static String ES_INDEX_STOCK_DETAILINFO="stockdetailinfo";
	
	public final static String ES_INDEX_STOCK_JYFX="jyfx";
	
	public final static String ES_INDEX_STOCK_STAPLEDAY="stapledayinfo";
	
	public final static String ES_INDEX_STOCK_OPTIONAL="stockoptional";
	public final static String ES_INDEX_STOCK_STORETRAILER="storetrailer";
	public final static String ES_INDEX_STOCK_STOCKPCSE="stockpcse";
	public final static String ES_INDEX_STOCK_STOREREPORT="storereport"; 

	

	
	
	
	 // http://file.tushare.org/tsdata/all.csv
	public static List<String> getAllAStockInfo() throws IOException {
		CsvHandUtils csvHandUtils = new CsvHandUtils(BaseConnClient.baseGetReqToStream(DETAIL_CONNPATH));
		List<List<String>> lstSource = csvHandUtils.readCSVFile();
		List<String> list = Lists.newArrayList();
		for (int i = 1; i < lstSource.size(); i++) {
			List<String> lstBeanCon = lstSource.get(i);
			StockDetailInfoBean detailInfoBean = new StockDetailInfoBean(lstBeanCon);
			list.add(detailInfoBean.getStockCode());
		}
		return list;
//		List<String> filePath = FileUtils.readLines(new File(FilePath.astockfilePath));
//		List<String> lstCode = Lists.newArrayList();
//		for (final String s : filePath) {
//			String code = s.split(",")[0];
//			if(!code.equals("code"))
//			lstCode.add(code);
//		}
//		return lstCode;

	}
	public static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(30));

	public static List<StockBaseInfo> getLstResult(BoolQueryBuilder query,String type) throws Exception {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
//		 BoolQueryBuilder query = QueryBuilders.boolQuery();
//		 query.must(QueryBuilders.rangeQuery("macd").from(0));
//		 query.must(QueryBuilders.termQuery("date", "2017-11-08"));
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, ES_INDEX_STOCK_STOCKPCSE, type, 0, 3800);

//		ssb.sort(name, order)
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<StockBaseInfo> lstBean = results.getSourceAsObjectList(StockBaseInfo.class);
		return lstBean;

	}
}
