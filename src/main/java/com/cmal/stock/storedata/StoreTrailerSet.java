package com.cmal.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmall.baseutils.StringUtil;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StoreTrailer;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;

/**
 * 获取业绩预告数据
 * @author temp1
 *
 */
public class StoreTrailerSet {
	public static void main(String[] args) throws Exception {
		final JestClient  jestClient =BaseCommonConfig.clientConfig();
		for (int i = 0; i < 25; i++) {
			String content = StoreTrailerUrl(i);
			List<StoreTrailer> list = getList(content);
			//保存es
			insBatchEs(list , jestClient , "storetrailer");
		}
		
		//System.out.println(content);
	}
	
	public static List<StoreTrailer> getList(String data){
		List<StoreTrailer> list = new ArrayList<StoreTrailer>(); 
		if(StringUtil.isNotNull(data)){
			JSONObject obj = (JSONObject) JSONObject.parse(data);
			JSONArray arr = obj.getJSONArray("data");
			for (int i = 0; i < arr.size(); i++) {
				Object o = arr.get(i);
				StoreTrailer tr = getDate(o.toString());
				list.add(tr);
			}
		}
		return list;
	}
	
	public static String StoreTrailerUrl(int index) throws ClientProtocolException, IOException{
		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty="
				+ "YJYG&fd=2017-12-31&st=4&sr=-1&p=1&ps=50&js={pages:(pc),data:[(x)]}&stat="+index+"&rt=50342803";
		String content = BaseConnClient.baseGetReq(url);
		return content;
	}
	
	public static StoreTrailer getDate(String date){
		StoreTrailer tr = new StoreTrailer();
		String[] datas = date.split(",");
		tr.setStockCode(datas[0]);
		tr.setStockName(datas[1]);
		tr.setPerChanges(datas[2]);
		tr.setRangeability(datas[3]);
		tr.setType(datas[4]);
		tr.setNetProfit(datas[5]);
		tr.setStartDate(datas[7]);
		tr.setEndDate(datas[8]);
		return tr;
	}
	
	public static void insBatchEs(List<StoreTrailer> list,JestClient jestClient,String  indexIns) throws Exception{
		 
		 
		  int i =0;
		  Bulk.Builder bulkBuilder = new Bulk.Builder();
		 for(StoreTrailer bean:list){
			   i++;
			   // System.out.println(bean.getUnionId());
			 Index index =new Index.Builder(bean).index(indexIns).type(bean.getStartDate().substring(0, 4)).id(bean.getStockCode()+bean.getStartDate()).build();//type("walunifolia").build();
			 bulkBuilder.addAction(index);
			 if(i%5000==0){
				 jestClient.execute(bulkBuilder.build());
				 bulkBuilder = new   Bulk.Builder();
			 }
		 	}
		 jestClient.execute(bulkBuilder.build());
		 //jestClient.shutdownClient();
	 }
}




