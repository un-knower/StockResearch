package com.cmal.stock.strage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmal.stock.storedata.StoreAstockTradInfo;
import com.cmall.stock.bean.StockBaseInfo;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

public class SelGetStock {

	public static List<StockBaseInfo> getLstResult(BoolQueryBuilder query) throws Exception {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		// BoolQueryBuilder query = QueryBuilders.boolQuery();
		// query.must(QueryBuilders.rangeQuery("macd").from(0));
		// query.must(QueryBuilders.termQuery("date", "2017-11-08"));
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, "stockpcse", "2017", 0, 3800);

		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<StockBaseInfo> lstBean = results.getSourceAsObjectList(StockBaseInfo.class);
		return lstBean;

	}

	public static void main(String[] args) throws Exception {

		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.rangeQuery("macd").from(0.01));
		query.must(QueryBuilders.termQuery("date", "2017-11-08"));
		List<StockBaseInfo> lstBean = getLstResult(query);

		query = QueryBuilders.boolQuery();
		// query.must(QueryBuilders.rangeQuery("macd").from(0.01));
		query.must(QueryBuilders.termQuery("date", "2017-11-09"));
		List<StockBaseInfo> lstBeanNw = getLstResult(query);
		Map<String, StockBaseInfo> maps = StoreAstockTradInfo.fetchKeyStockInfo(lstBean);
		// System.out.println(maps);
		System.out.println("股票代码" + "\t" + "股票名称" +"\t"+"MACD差值"+ "\t" + "成交量前一交易日增大倍数" + "\t" + "增长" + "\t" + "MACD" + "\t" + "J差值" + "\t"
				+ "成交量5平均" + "\t" + "K" + "\t" + "D" + "\t" + "J");
		for (StockBaseInfo b : lstBeanNw) {
			if ((b.getVolume() / b.getVolumeMa5()>1.3) && b.getVolume() > 0) {
				
				// System.out.println(b.toString());
				// System.out.println(b.getStockCode());
				StockBaseInfo obj = maps.get(b.getStockCode());
				if (obj != null) {// 非停牌
					String c = (Double.parseDouble(b.getVolume()+"") / Double.parseDouble(obj.getVolume()+"")  + "");
					c = c.substring(0, 4);
					String bps = maps.get(b.getStockCode()).getRises();// maps.get(b.getStockCode())==null?"
																		// ":((maps.get(b.getStockCode()).getClose()-b.getClose())/b.getClose()*100)+"";

					System.out.println(b.getStockCode() + "\t" + b.getStockName() +"\t"+(b.getMacd()/obj.getMacd())+ "\t" + c + "\t" + b.getRises() + "\t"
							+ b.getMacd() + "\t" + (b.getJ()-obj.getJ()) + "\t" +(b.getVolume() / b.getVolumeMa5())+ " \t" + b.getK() + "\t"
							+ b.getD() + "\t" + b.getJ());
				}
			}
		}

		// System.out.println(lstBean);
		// System.out.println(lstBean.size());
		// bean=results.getSourceAsObject(UnionFotoliaBean.class);
		// if(bean!=null)
		// return results.getSourceAsObject(UnionFotoliaBean.class);
		// selResult.getData(gson)
	}

}
