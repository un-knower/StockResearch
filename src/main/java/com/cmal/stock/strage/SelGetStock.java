package com.cmal.stock.strage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StoreTrailer;
import com.cmall.stock.vo.StockBasePageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.QueryComLstData;
import com.kers.esmodel.UtilEs;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

/**
 * 
 * 
 * ClassName：SelGetStock Description： author ：admin date ：2017年11月11日 下午3:37:33
 * Modified person：admin Modified date：2017年11月11日 下午3:37:33 Modify remarks：
 * 
 * @version V1.0
 * 
 * 
 * 
 *          1.macd连涨天数 2. 5|10天内macd涨的天数
 *
 */
public class SelGetStock {
	static Map<String, String> mapsInfo = StockSelStrag.blckLstOfStock();
	static Map<String, String> mapsSelStock = StockSelStrag.getAllChkStock();

	public static Map<String, Object> getLstResult(BoolQueryBuilder query, StockBasePageInfo page) throws Exception {

		Map<String, Object> returnMap = Maps.newHashMap();

		//query.must(QueryBuilders.inQuery("stockCode", mapsSelStock.keySet()));
		query.mustNot(QueryBuilders.inQuery("stockCode", mapsInfo.keySet()));
		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, "",
				(page.getPage() - 1) * page.getLimit(), page.getLimit());

		// final JestClient jestClient = BaseCommonConfig.clientConfig();
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		// StockSelStrag.queryGrowUpStock();//
		List<StockBaseInfo> lstBean = results.getSourceAsObjectList(StockBaseInfo.class);// .queryBchipStock();//queryGrowUpStock();//
																							// results.getSourceAsObjectList(StockBaseInfo.class);
		List<StockBaseInfo> lstResult = Lists.newArrayList();
		for (StockBaseInfo baseInfo : lstBean) {

			lstResult.add(baseInfo);

			// if (mapsInfo.get(baseInfo.getStockCode()) == null &&
			// (!baseInfo.getStockCode().startsWith("3"))) {//
			// &&baseInfo.getUpSumRises5()>=0)//(baseInfo.getUpSumRises10()>0||baseInfo.getUpSumRises5()>=0))

			// if(baseInfo.getStockCode().equals("601155")){
			// System.out.println(baseInfo.getUp5()+" ===="+baseInfo.getUp10());
			// }

			// if(StockSelStrag.getYZEarningStrag1(baseInfo))
			// lstResult.add(baseInfo);

			// 十日涨幅大于五日涨幅
			// if (baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5()) {
			// //
			// if (baseInfo.getUpSumRises10() >= 6 || baseInfo.getUpSumRises5()
			// >=5)
			// if (baseInfo.getUp5() > 2 &&baseInfo.getUp10() > 4) { //
			// 5/10天内上涨次数
			// if((baseInfo.getRises()+baseInfo.getUpRises())+baseInfo.getUpSumRises5()>0)
			// if(!(baseInfo.getUpSumRises10()>30&&(baseInfo.getUpSumRises40()>baseInfo.getUpSumRises5())))
			// //急涨急跌排除
			// //lstResult.add(baseInfo);
			// System.out.println("cc");
			// }
			// }

			// }
			// lstResult.add(baseInfo);

		}

		if (lstBean != null && lstBean.size() > 0) {
			Map hitsMap = (Map) results.getValue("hits");
			if (hitsMap != null) {
				Number total = (Number) hitsMap.get("total");
				if (total != null) {
					if (total.intValue() > lstResult.size())
						returnMap.put("totalCount", lstResult.size());
					else
						returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstResult);
		return returnMap;

		// return getCommonLstResult(query, page,
		// CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, "");
	}

	public static Map<String, Object> getTrailerLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {

		Map<String, Object> mapSource = Maps.newConcurrentMap();

		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);// ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STORETRAILER, type,
				(page.getPage() - 1) * page.getLimit(), page.getLimit());

		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<StoreTrailer> lstBeanInt = results.getSourceAsObjectList(StoreTrailer.class);

		List<StoreTrailer> lstResult = Lists.newArrayList();
		// System.out.println(mapsInfo);
		Map<String, StockBaseInfo> mapsInfo2 = QueryComLstData.getStockBaseInfo();
		for (StoreTrailer bean : lstBeanInt) {
			// StockDetailInfoBean stBean =
			// QueryComLstData.getDetailInfo().get(bean.getStockCode());
			// StockBaseInfo baBean = mapsInfo2.get(bean.getStockCode());
			// if (baBean != null && stBean != null) {
			// double npe = baBean.getClose() / (bean.getJlr() /
			// stBean.getTotals());
			// bean.setNpe(npe);
			// }
			// if (stBean != null)
			// bean.setPe(stBean.getPe());

			lstResult.add(bean);
		}

		// 股价/(净利润/总股数)=市盈率（PE）
		// mapSource.remove("items");
		if (lstBeanInt != null && lstBeanInt.size() > 0) {
			Map hitsMap = (Map) results.getValue("hits");
			if (hitsMap != null) {
				Number total = (Number) hitsMap.get("total");
				if (total != null) {
					mapSource.put("totalCount", total.intValue());
				}
			}
		}
		// System.out.println(lstBean);
		mapSource.put("items", lstResult);

		return mapSource;

	}

	public static Map<String, Object> getStaLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {
		return getCommonLstResult(query, page, "storestrateinfo", type);
	}

	public static SearchSourceBuilder buildQuery(StockBasePageInfo page, BoolQueryBuilder query) {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if (!StringUtils.isEmpty(page.getSort())) {
			String[] sorts = page.getSort().split(",");
			for (int i = 0; i < sorts.length; i++) {
				String s = sorts[i];
				String order = s.split("\\.")[1];
				if (order.equalsIgnoreCase("desc")) {
					ssb.sort(s.split("\\.")[0], SortOrder.DESC);
				} else {
					ssb.sort(s.split("\\.")[0], SortOrder.ASC);
				}
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		return searchSourceBuilder;
	}

	public static Map<String, Object> getCommonLstResult(BoolQueryBuilder query, StockBasePageInfo page, String index,
			String type) throws Exception {
		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);
		System.out.println(searchSourceBuilder.toString());
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		// (page.getPage() - 1) * page.getLimit(),page.getLimit()

		return UtilEs.getSearchRsult(searchSourceBuilder, index, type, page.getPage(), page.getLimit(), jestClient);
	}

	

	public static Map<String, Object> getReportLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {
		Map<String, Object> returnMap = Maps.newHashMap();
		List<String> types = Lists.newArrayList();

		if (type.equals(",all")) {
           types.addAll(getAllyearsInx());
		} else {
			types.add("2017-09-30");
		}
	   //query.must(QueryBuilders.inQuery("stockCode", StockSelStrag.blckLstOfStock().keySet()));
		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);// ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STOREREPORT, types,
				(page.getPage() - 1) * page.getLimit(), page.getLimit());

		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<EastReportBean> lstBeanInt =results.getSourceAsObjectList(EastReportBean.class);//StockSelStrag.queryEarningsStock();// results.getSourceAsObjectList(EastReportBean.class);// results.getSourceAsObjectList(EastReportBean.class);
		List<EastReportBean> lstBean = lstBeanInt;

		if (lstBean != null && lstBean.size() > 0) {
			Map hitsMap = (Map) results.getValue("hits");
			if (hitsMap != null) {
				Number total = (Number) hitsMap.get("total");
				if (total != null) {
					returnMap.put("totalCount", total.intValue());
				}
			}
		}
		// System.out.println(lstBean);
		returnMap.put("items", lstBean);
		return returnMap;

	}

	public static List<Stap100PPI> getList(BoolQueryBuilder query, StockBasePageInfo page, String index, String type)
			throws Exception {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if (!StringUtils.isEmpty(page.getSort())) {
			String[] sorts = page.getSort().split(",");
			for (int i = 0; i < sorts.length; i++) {
				String s = sorts[i];
				String order = s.split("\\.")[1];
				if (order.equalsIgnoreCase("desc")) {
					ssb.sort(s.split("\\.")[0], SortOrder.DESC);
				} else {
					ssb.sort(s.split("\\.")[0], SortOrder.ASC);
				}
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		// System.out.println(page.getLimit());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, index, type, (page.getPage() - 1) * page.getLimit(),
				page.getLimit());
		// final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<Stap100PPI> lstBean = results.getSourceAsObjectList(Stap100PPI.class);
		return lstBean;
	}

	public static List<String> getAllyearsInx() {

		List<String> types = Lists.newArrayList();
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

		types.add("2015-12-31");
		types.add("2015-09-30");
		types.add("2015-06-30");
		types.add("2015-03-31");

		types.add("2014-12-31");
		types.add("2014-09-30");
		types.add("2014-06-30");
		types.add("2014-03-31");

		types.add("2013-12-31");
		types.add("2013-09-30");
		types.add("2013-06-30");
		types.add("2013-03-31");

		types.add("2012-12-31");
		types.add("2012-09-30");
		types.add("2012-06-30");
		types.add("2012-03-31");

		types.add("2012-12-31");
		types.add("2012-09-30");
		types.add("2012-06-30");
		types.add("2012-03-31");

		types.add("2011-12-31");
		types.add("2011-09-30");
		types.add("2011-06-30");
		types.add("2011-03-31");

		types.add("2010-12-31");
		types.add("2010-09-30");
		types.add("2010-06-30");
		types.add("2010-03-31");

		types.add("2009-12-31");
		types.add("2009-09-30");
		types.add("2009-06-30");
		types.add("2009-03-31");

		types.add("2008-12-31");
		types.add("2008-09-30");
		types.add("2008-06-30");
		types.add("2008-03-31");

		types.add("2007-12-31");
		types.add("2007-09-30");
		types.add("2007-06-30");
		types.add("2007-03-31");
		return types;
	}
}
