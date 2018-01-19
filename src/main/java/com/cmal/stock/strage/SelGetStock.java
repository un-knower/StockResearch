package com.cmal.stock.strage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.cmal.stock.storedata.StockDetailInfoHand;
import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.bean.jyfx.JyfxInfo;
import com.cmall.stock.vo.StockBasePageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kers.esmodel.BaseCommonConfig;
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

	public static Map<String, Object> getLstResult(BoolQueryBuilder query, StockBasePageInfo page) throws Exception {
		return getCommonLstResult(query, page, "stockpcse", "");
	}

	public static Map<String, Object> getTrailerLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {
		return getCommonLstResult(query, page, "storestrateinfo", type);
	}

	public static Map<String, Object> getStaLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {
		return getCommonLstResult(query, page, "storestrateinfo", type);
	}

	public static Map<String, Object> getCommonLstResult(BoolQueryBuilder query, StockBasePageInfo page, String index,
			String type) throws Exception {
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
		// (page.getPage() - 1) * page.getLimit(),page.getLimit()
		
		return UtilEs.getSearchRsult(searchSourceBuilder, index, type,page.getPage(),page.getLimit(), jestClient);
	}
	
	public static void main(String[] args) throws Exception {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery(); 
		query.must(QueryBuilders.termQuery("rq", "2017-06-30"));
		query.must(QueryBuilders.termQuery("type", "2"));
		query.must(QueryBuilders.queryString("\"水泥\"").field("zygc"));
		
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
//		final JestClient jestClient = BaseCommonConfig.clientConfig();
		
		
//		JestResult results=  UtilEs.getSearch(searchSourceBuilder, "jyfx", "2017-06-30", 1,100);
////		JestResult results = jestClient.execute(selResult);
//		List<StockBaseInfo> lstBean = results.getSourceAsObjectList(StockBaseInfo.class);
//				
//		jestClient.execute(clientRequest);
//		  System.out.println(UtilEs.getSearchRsult(, jestClient)); ;
		  
		  Search selResult = UtilEs.getSearch(ssb, "jyfx", "2017-06-30", 0, 3800);

//			ssb.sort(name, order)
			final JestClient jestClient = BaseCommonConfig.clientConfig();
			JestResult results = jestClient.execute(selResult);
			List<JyfxInfo> lstBean = results.getSourceAsObjectList(JyfxInfo.class);
			
			for(JyfxInfo  info:lstBean){
				System.out.println(info.getStockCode());
			}
	}

	
 
	static Map<String, StockDetailInfoBean> mapsInfo;
	static {
		try {
			mapsInfo = StockDetailInfoHand.getDetailForMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Map<String, Object> getReportLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {
		Map<String, Object> returnMap = Maps.newHashMap();
		List<String> types = Lists.newArrayList();
		// System.out.println(type);

		if (type.equals(",all")) {// StringUtils.isEmpty(type)||type.equals(",")){
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
		} else {
			types.add("2017-09-30");
		}
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if (!StringUtils.isEmpty(page.getSort())) {
			String order = page.getSort().split("\\.")[1];
			if (order.equalsIgnoreCase("desc")) {
				ssb.sort(page.getSort().split("\\.")[0], SortOrder.DESC);
			} else {
				ssb.sort(page.getSort().split("\\.")[0], SortOrder.ASC);
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		// System.out.println(types);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, "storereport", types,
				(page.getPage() - 1) * page.getLimit(), page.getLimit());

		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<EastReportBean> lstBean = results.getSourceAsObjectList(EastReportBean.class);

		for (EastReportBean bean : lstBean) {
			StockDetailInfoBean mapsBean = mapsInfo.get(bean.getStockCode());
			if (mapsBean != null) {
				bean.setTotals(mapsBean.getTotals() * mapsBean.getEsp() * mapsBean.getPe());// mapsBean.getTotalAssets());
				bean.setIndustry(mapsBean.getIndustry());
				bean.setPe(mapsBean.getPe());
			}
		}

		List<EastReportBean> lstBean2 = Lists.newArrayList();
		for (EastReportBean beans : lstBean) {
			if (beans.getJdzzl_before() / beans.getJdzzl() < 20) {
				if (beans.getJlr() < 200000000) {
					if (beans.getJdzzl() > beans.getJdzzl_before()) {
						lstBean2.add(beans);
					}
				} else {
					lstBean2.add(beans);
				}
			}
		}
		// lstBean=lstBean2;
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

	

	public static List<Stap100PPI> getList(BoolQueryBuilder query, StockBasePageInfo page,String index , String type ) throws Exception {
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
		Search selResult = UtilEs.getSearch(searchSourceBuilder, index, type, (page.getPage()- 1) * page.getLimit() , page.getLimit());
//		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<Stap100PPI> lstBean = results.getSourceAsObjectList(Stap100PPI.class);
		return lstBean;
	}
}
