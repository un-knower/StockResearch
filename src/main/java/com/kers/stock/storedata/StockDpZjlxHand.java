package com.kers.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;
import com.kers.stock.bean.RzRqBean;
import com.kers.stock.bean.StockDpZjlxBean;
import com.kers.stock.bean.StockZjlx;
import com.kers.stock.storedata.RzRqHand.Jsonobj;
import com.kers.stock.utils.MathsUtils;


/**
 * 
 * @author chenshenlin
 * 大盘资金流向
 *
 */
public class StockDpZjlxHand {
	
	static JestClient jestClient = BaseCommonConfig.clientConfig();
	
	public static void impDpData(){
		try {
			StockZjlxHand.insBatchEs(parseGgZjlxFromUrl(""), jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_ZJLX);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<StockZjlx> parseGgZjlxFromUrl(String stopDate)
			throws ClientProtocolException, IOException {

		List<StockZjlx> lstRet = Lists.newArrayList();
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
		for (int i = 0; i < List.size(); i++) {
			String val = List.get(i).replace("%", "");
			String[] bean = val.split(",");
			
			String date = bean[0].toString();

			double zlNum = MathsUtils.parseDoubleStockYy(bean[1]);
			double zlRatio = MathsUtils.parseDouble(bean[2].toString());// 百分

			double cddNum = MathsUtils.parseDoubleStockYy(bean[3]);
			double cddRatio = MathsUtils.parseDouble(bean[4].toString());// 百分

			double ddNum = MathsUtils.parseDoubleStockYy(bean[5]);
			double ddRatio = MathsUtils.parseDouble(bean[6].toString());// 百分

			double zdNum = MathsUtils.parseDoubleStockYy(bean[7]);
			double zdRatio = MathsUtils.parseDouble(bean[8].toString());// 百分

			double xdNum = MathsUtils.parseDoubleStockYy(bean[9]);
			double xdRatio = MathsUtils.parseDouble(bean[10].toString());// 百分

			double close = MathsUtils.parseDouble(bean[11].toString());
			double rises = MathsUtils.parseDouble(bean[12].toString());
			
			if(null !=stopDate && !stopDate.equals("") && date.equals(stopDate)){
				break;
			}
			
			StockZjlx stockZjlx = new StockZjlx(date, close, rises, zlNum, zlRatio, cddNum, cddRatio, ddNum,
					ddRatio, zdNum, zdRatio, xdNum, xdRatio);
			stockZjlx.setStockCode("0000001");
			stockZjlx.setStockName("上证指数");
			stockZjlx.setType(0);

			int up3 = 0;
			double up3NumAvg = 0;
			for (int j = 0; j < 3; j++) {

				int index = i - j;
				if (index < 0)
					break;

				double zlUseNum = MathsUtils
						.parseDoubleStockYy(List.get(index).replace("\"", "").split(",")[1].toString());
				if (zlUseNum > 0) {
					up3++;
				}
				up3NumAvg = up3NumAvg + zlUseNum;
			}
			stockZjlx.setUp3(up3);
			stockZjlx.setUp3NumAvg((up3NumAvg / 3));

			int up5 = 0;
			double up5NumAvg = 0;

			for (int j = 0; j < 5; j++) {

				int index = i - j;
				if (index < 0)
					break;

				double zlUseNum = MathsUtils
						.parseDoubleStockYy(List.get(index).toString().replace("\"", "").split(",")[1].toString());

				if (zlUseNum > 0) {
					up5++;
				}
				up5NumAvg = up5NumAvg + zlUseNum;
			}
			stockZjlx.setUp5(up5);
			stockZjlx.setUp5NumAvg((up5NumAvg / 5));

			int up10 = 0;
			double up10NumAvg = 0;

			for (int j = 0; j < 10; j++) {

				int index = i - j;
				if (index < 0)
					break;

				double zlUseNum = MathsUtils
						.parseDoubleStockYy(List.get(index).toString().replace("\"", "").split(",")[1].toString());

				if (zlUseNum > 0) {
					up10++;
				}
				up10NumAvg = up10NumAvg + zlUseNum;
			}
			stockZjlx.setUp10(up10);
			stockZjlx.setUp10NumAvg((up10NumAvg / 10));

			if (i >=1) {
				StockZjlx lstStockZljx= lstRet.get(i - 1);
				int lstUpNm = lstStockZljx.getUpNum();

				if (zlNum > 0) {

					int lstNum = lstUpNm + 1;
					stockZjlx.setUpNum(lstNum);

					if (stockZjlx.getUpNum() <= 0)
						stockZjlx.setUpNum(1);
				} else {

					int inc = lstUpNm - 1;
					stockZjlx.setUpNum(inc);

					if (stockZjlx.getUpNum() >= 0)
						stockZjlx.setUpNum(-1);

				}
				double  zzRises=zlNum-(lstStockZljx.getZlNum()<0?Math.abs(lstStockZljx.getZlNum()):lstStockZljx.getZlNum());//((zlNum-lstStockZljx.getZlNum())/Math.abs((lstStockZljx.getZlNum()==0?10000:lstStockZljx.getZlNum())))*100;
				stockZjlx.setZzRises(zzRises);

			}
			
			 int   upabsNum=Math.abs(stockZjlx.getUpNum());
			  double ljlrNum = 0;
			 for (int j = 0; j < upabsNum; j++) {
				 
				 int index = i - j;
					if (index < 0)
						break;
					
					ljlrNum+=MathsUtils
							.parseDoubleStockYy(List.get(index).toString().replace("\"", "").split(",")[1].toString());
					
				
			}
			 stockZjlx.setLjlrNum(ljlrNum);
			
		

			lstRet.add(stockZjlx);
			
		}
		System.out.println("新增数据数量:"+lstRet.size());
		return lstRet;

	}
	
	
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
		impDpData();
	}
}

