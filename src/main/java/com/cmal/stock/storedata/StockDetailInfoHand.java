package com.cmal.stock.storedata;

import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.utils.CsvHandUtils;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class StockDetailInfoHand {

	public final static String DETAIL_CONNPATH = "http://file.tushare.org/tsdata/all.csv";

	public static Map<String, StockDetailInfoBean> getDetailForMap() throws Exception {
		Map<String, StockDetailInfoBean> mapSource = Maps.newConcurrentMap();
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();

		ssb.query(query);
		Search selResult = UtilEs.getSearch(ssb, CommonBaseStockInfo.ES_INDEX_STOCK_DETAILINFO,
				CommonBaseStockInfo.ES_INDEX_STOCK_DETAILINFO, 0, 3800);
		JestResult results =  BaseCommonConfig.clientConfig().execute(selResult);
		List<StockDetailInfoBean> lstBean = results.getSourceAsObjectList(StockDetailInfoBean.class);
		for (StockDetailInfoBean detailInfoBean : lstBean) {
			mapSource.put(detailInfoBean.getStockCode(), detailInfoBean);
		}
		return mapSource;

	}

	public static void main(String[] args) throws Exception {
		getDetailForMap();
	}

	public static void insBatchEsStore() throws Exception {
		CsvHandUtils csvHandUtils = new CsvHandUtils(BaseConnClient.baseGetReqToStream(DETAIL_CONNPATH));
		List<List<String>> lstSource = csvHandUtils.readCSVFile();
		List<StockDetailInfoBean> list = Lists.newArrayList();
		for (int i = 1; i < lstSource.size(); i++) {
			List<String> lstBeanCon = lstSource.get(i);
			StockDetailInfoBean detailInfoBean = new StockDetailInfoBean(lstBeanCon);
			list.add(detailInfoBean);
		}

		insBatchEsMec(list, BaseCommonConfig.clientConfig());
		Thread.sleep(10000);
	}

	public static void insBatchEsMec(List<StockDetailInfoBean> list, JestClient jestClient) throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockDetailInfoBean bean : list) {
			i++;

			Index index = new Index.Builder(bean).index(CommonBaseStockInfo.ES_INDEX_STOCK_DETAILINFO)
					.type(CommonBaseStockInfo.ES_INDEX_STOCK_DETAILINFO).id(bean.getStockCode()).build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}

}