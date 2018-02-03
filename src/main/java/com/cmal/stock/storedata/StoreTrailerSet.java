package com.cmal.stock.storedata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.bean.StoreTrailer;
import com.cmall.stock.utils.FilePath;
import com.cmall.stock.utils.MathsUtils;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.QueryComLstData;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * 获取业绩预告数据
 * 
 * @author temp1
 *
 */
public class StoreTrailerSet {
	public static final String P_TYPE_2017_12_31 = "2017-12-31";

	public static void savetrailerDetailInfo(String stockCode) {

		String url = "http://finance.sina.com.cn/realstock/stock_predict/predict_notice/detail_" + stockCode + ".html";
		try {
			String str = BaseConnClient.baseGetReq(url, "gb2312");
			String filePath = FilePath.saveSTORETRAILER + "detail_" + stockCode + ".html";
			FileUtils.write(new File(filePath), str);
		} catch (Exception e) {
			System.out.println(stockCode);
		}

	}

	public static String getTrailerDetailInfo(String stockCode, String endDate) throws IOException {
		String filePath = FilePath.saveSTORETRAILER + "detail_" + stockCode + ".html";
		if (!new File(filePath).exists())
			savetrailerDetailInfo(stockCode);

		List<String> content = FileUtils.readLines(new File(filePath));
		for (int i = 0; i < content.size(); i++) {
			String sss = content.get(i);

			// if(sss.startsWith("arr2[0][1]"))
			if (sss.contains("='" + endDate + "';")) {// 截止日期
				String cbp = sss.substring(sss.indexOf("='") + 2).replace("';", "").trim();
				;
				// String cbp= sss.replace("arr2[0][1]='", "").replace("';",
				// "").trim();
				String sln = content.get(i + 1);
				if (cbp.equals(endDate)) {
					// int inx = sln.indexOf("业绩变动原因说明")+1;
					// sln=sln.substring(inx);
					return sln.substring(sln.indexOf("='") + 2).replace("';", "").trim();
					// return sln.replace("arr2[0][2]='", "").replace("';", "");
				}
			}
		}
		// for (String sss : content) {
		// if (sss.startsWith("arr2[0][2]"&&)
		// return sss.replace("arr2[0][2]='", "").replace("", "';");
		// }
		return null;

	}

	public static void main(String[] args) throws Exception {

//		SearchSourceBuilder ssb = new SearchSourceBuilder();
//		BoolQueryBuilder query = QueryBuilders.boolQuery();
//		ssb.sort("startDate", SortOrder.DESC);
//		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
//		List<StoreTrailer> lstResult = new SelEsRelt<StoreTrailer>(new StoreTrailer()).getResultFromQuery(
//				searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STORETRAILER, P_TYPE_2017_12_31, 0, 1);
		
		
		// System.out.println(getTrailerDetailInfo("603619", "2017-12-31"));
		// List<StoreTrailer> lst = getLstResult(null, null);
		// for (StoreTrailer bean : lst) {
		// savetrailerDetailInfo(bean.getStockCode());
		//
		// }
		wsData();

	}

	public static void wsData() throws InterruptedException {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		// List<StoreTrailer> list= Lists.newArrayList();
		for (int i = 0; i <=50; i++) {
			// String content = StoreTrailerUrl(i);
			// System.out.println(content);|
			try {
				// list.addAll(getList(StoreTrailerUrl(i)));
				insBatchEs(getList(StoreTrailerUrl(i)), jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_STORETRAILER);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			// 保存es
		}
		Thread.sleep(100000);
		// System.out.println(list.size());
		// insBatchEs(list , jestClient , "storetrailer");

		// System.out.println(content);
	}

	public static List<StoreTrailer> getList(String data) {
		List<StoreTrailer> list = new ArrayList<StoreTrailer>();
		Map<String, StockBaseInfo> mapsInfo2 = QueryComLstData.getStockBaseInfo();

		if (StringUtils.isNotBlank(data)) {
			JSONObject obj = (JSONObject) JSONObject.parse(data);
			JSONArray arr = obj.getJSONArray("data");
			for (int i = 0; i < arr.size(); i++) {
				Object o = arr.get(i);
				StoreTrailer tr = getDate(o.toString());
				try {
					tr.setJlr(getJlr(tr.getPerChanges()));
					String detail = getTrailerDetailInfo(tr.getStockCode(), tr.getEndDate());
					tr.setChagDetail(StringUtils.isEmpty(detail) ? tr.getPerChanges() : detail);

					StockDetailInfoBean stBean = QueryComLstData.getDetailInfo().get(tr.getStockCode());
					StockBaseInfo baBean = mapsInfo2.get(tr.getStockCode());
					if (baBean != null && stBean != null && stBean.getTotalAssets() != 0 && tr.getJlr() != 0) {
						double npe = baBean.getClose() / (tr.getJlr() / stBean.getTotals());
						tr.setNpe(npe);
					}
					if (stBean != null)
						tr.setPe(stBean.getPe());

					// System.out.println(tr.getStockCode()+"
					// "+tr.getChagDetail());
					// StockDetailInfoBean stockBean
					// =SelGetStock.mapsInfo.get(tr.getStockCode());
					// if(stockBean!=null){
					//
					// }
					// tr.
					// if(tr.getPe()!=null &&tr.getn)
				} catch (Exception e) {
					e.printStackTrace();
					// System.out.println(tr.getPerChanges());
				}

				list.add(tr);
			}
		}
		return list;
	}

	public static String StoreTrailerUrl(int index) throws ClientProtocolException, IOException {
		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty="
				+ "YJYG&fd=2017-12-31&st=4&sr=-1&p=" + index + "&ps=50&js={pages:(pc),data:[(x)]}&stat=0&rt=50342803";
		String content = BaseConnClient.baseGetReq(url);
		// System.out.println(content);
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
		tr.setNetProfit( MathsUtils.parseDouble(datas[5]));
		tr.setStartDate(datas[7]);
		tr.setEndDate(datas[8]);
		return tr;
	}

	public static void insBatchEs(List<StoreTrailer> list, JestClient jestClient, String indexIns) throws Exception {

		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StoreTrailer bean : list) {
			i++;
			// System.out.println(bean.getUnionId());
			Index index = new Index.Builder(bean).index(indexIns).type(bean.getEndDate())
					.id(bean.getStockCode() + bean.getEndDate()).build();// type("walunifolia").build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
		// jestClient.shutdownClient();
	}

	public static Map<String, StoreTrailer> getAllTrailerMap(String type) throws Exception {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		Map<String, StoreTrailer> map = new HashMap<String, StoreTrailer>();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STORETRAILER, type, 0, 3800);
		JestResult results = jestClient.execute(selResult);
		List<StoreTrailer> lstBean = results.getSourceAsObjectList(StoreTrailer.class);
		for (StoreTrailer storeTrailer : lstBean) {
			map.put(storeTrailer.getStockCode(), storeTrailer);
		}
		return map;
	}

	public static double getJlr(String text) {
		// 第一个有可能为负数
		// 第二个为--的时候才为负
		// 第二个为至-
		// 文字里面有亏损的需要乘以-1
		boolean kui = false;
		if (text.indexOf("亏损") > 0) {
			kui = true;
		}
		// Pattern.compile("(-)?[0-9]+([.]{1}[0-9]+){0,1}万元");
		text = text.replace(" ", "").replace("至", "-");
		Pattern p = Pattern.compile("(-)?[0-9]+([.]{1}[0-9]+){0,1}万");
		java.util.regex.Matcher m = p.matcher(text);
		Double s1 = null;
		Double s2 = null;
		while (m.find()) {
			String s = m.group(0).replace("万", "").replace("万元", "");
			if (s1 != null) {
				s = s.substring(1);
				s2 = Double.parseDouble(s) * 10000;
			} else {
				s1 = Double.parseDouble(s) * 10000;
			}
		}

		if (s1 == null)
			return 0;
		Double result = s1;
		// if(s2==null){
		// result =s1;
		// }
		// else if(s1==null)
		// result = s2;
		// else if (s1!=null&&s2!=null){
		// result = (s2 + s1) / 2;
		// }

		if (kui) {
			result = result * -1;
		}
		return result;
	}

	public static List<StoreTrailer> getLstResult(BoolQueryBuilder query, String type) throws Exception {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		// BoolQueryBuilder query = QueryBuilders.boolQuery();
		// query.must(QueryBuilders.rangeQuery("macd").from(0));
		// query.must(QueryBuilders.termQuery("date", "2017-11-08"));
		if (query == null)
			query = QueryBuilders.boolQuery();

		if (type == null)
			type = "2017-12-31";

		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STORETRAILER, type,
				0, 3800);
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<StoreTrailer> lstBean = results.getSourceAsObjectList(StoreTrailer.class);
		return lstBean;

	}

}
