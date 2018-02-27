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

/**
 * 
 * @author 融资融券
 * http://dcfm.eastmoney.com//EM_MutiSvcExpandInterface/api/js/get?token=70f12f2f4f091e459a279469fe49eca5&st=tdate&sr=-1&p=1&ps=50&js=var%20gulIvAhy={pages:(tp),data:%20(x)}&type=RZRQ_LSTOTAL_NJ&mk_time=1&rt=50654628
 *
 */
public class RzRqHand {
	
	static JestClient jestClient = BaseCommonConfig.clientConfig();
	
	public static void main(String[] args) throws Exception {
//		getAllDatas("");
		getBreakPointDatas();
	}
	
	public static String getJsonUrlByP(int p) throws ClientProtocolException, IOException {
		String url = "http://dcfm.eastmoney.com//EM_MutiSvcExpandInterface/api/js/get?token=70f12f2f4f091e459a279469fe49eca5&st=tdate&sr=-1&p="+p+"&ps=50&js=var%20gulIvAhy={pages:(tp),data:%20(x)}&type=RZRQ_LSTOTAL_NJ&mk_time=1&rt=50654628";
		String content = BaseConnClient.baseGetReq(url);
		return content;
	}
	
	public static void insBatchEs(List<RzRqBean> list, JestClient jestClient, String indexIns) throws Exception {
		if (list == null || list.isEmpty())
			return;
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (RzRqBean bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type(indexIns)
					.id(bean.getTdate()).build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}
	
	public static void getAllDatas(String stopDate) throws Exception{
		int p = 1;
		int i = 1;
		Type type = new TypeToken<Jsonobj>() {}.getType();
		Gson gson = new Gson();
		List<RzRqBean> list = Lists.newArrayList();
		z:while (p <= i ) {
			String json = getJsonUrlByP(p).replace("var gulIvAhy=", "");
			json = json.replace("\"-\"", "0");
			Jsonobj bean = gson.fromJson(json, type);
			if(null != bean){
				i = bean.getPages();
				List<RzRqBean> datas = bean.getData();
				for (RzRqBean rzRqBean : datas) {
					rzRqBean.setTdate(rzRqBean.getTdate().split("T")[0]);
					if(null !=stopDate && !stopDate.equals("") && rzRqBean.getTdate().equals(stopDate)){
						break z;
					}
					list.add(rzRqBean);
				}
			}else{
				break;
			}
			p++;
		}
		System.out.println("总共加入数据：" + list.size());
		if(list.size() > 0){
			insBatchEs(list, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_RZRQ);
		}
	}
	
	public static void getBreakPointDatas(){
		String stopDate = "";
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		ssb.sort("tdate", SortOrder.DESC);
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_RZRQ, CommonBaseStockInfo.ES_INDEX_STOCK_RZRQ,
				1, 1);
		RzRqBean bean = new RzRqBean();
		try {
			JestResult results = jestClient.execute(selResult);
			if(results.isSucceeded()){
				bean = results.getSourceAsObject(RzRqBean.class);
				stopDate = bean.getTdate();
				System.out.println(stopDate);
			}
			getAllDatas(stopDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class Jsonobj{
		private int pages;
		private List<RzRqBean> data;
		public int getPages() {
			return pages;
		}
		public void setPages(int pages) {
			this.pages = pages;
		}
		public List<RzRqBean> getData() {
			return data;
		}
		public void setData(List<RzRqBean> data) {
			this.data = data;
		}
	}
}
