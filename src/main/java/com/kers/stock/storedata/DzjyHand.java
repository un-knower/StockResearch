package com.kers.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Date;
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
import com.kers.stock.bean.DzjyBean;
import com.kers.stock.bean.RzRqBean;
import com.kers.stock.utils.TimeUtils;

/**
 * 
 * @author 融资融券
 * http://dcfm.eastmoney.com//EM_MutiSvcExpandInterface/api/js/get?token=70f12f2f4f091e459a279469fe49eca5&st=tdate&sr=-1&p=1&ps=50&js=var%20gulIvAhy={pages:(tp),data:%20(x)}&type=RZRQ_LSTOTAL_NJ&mk_time=1&rt=50654628
 *
 */
public class DzjyHand {
	
	static JestClient jestClient = BaseCommonConfig.clientConfig();
	
	static String endDate = TimeUtils.toString(new Date(), TimeUtils.DATE_FORMAT);
	
	public static void main(String[] args) throws Exception {
		System.out.println("查询截止到日期："+endDate);
		getAllDatas("");
//		getBreakPointDatas();
	}
	
	public static String getJsonUrlByP(int p) throws ClientProtocolException, IOException {
		String url = "http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?type=DZJYXQ&token=70f12f2f4f091e459a279469fe49eca5&cmd=&st=SECUCODE&sr=1&p="+p+"&ps=50&js=var%20ftlVIctO={pages:(tp),data:(x)}&filter=(Stype=%27EQA%27)(TDATE%3E=%5e2018-01-01%5e%20and%20TDATE%3C=%5e"+endDate+"%5e)&rt=50660382";
//		url = URLEncoder.encode(url, "UTF-8");
		String content = BaseConnClient.baseGetReq(url);
		return content;
	}
	
	public static void insBatchEs(List<DzjyBean> list, JestClient jestClient, String indexIns) throws Exception {
		if (list == null || list.isEmpty())
			return;
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (DzjyBean bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type(indexIns)
					.id(bean.getTDATE()+bean.getSALESCODE()).build();
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
		List<DzjyBean> list = Lists.newArrayList();
		z:while (p <= i ) {
			String json = getJsonUrlByP(p).replace("var ftlVIctO=", "");
			json = json.replace("\"-\"", "0");
			Jsonobj bean = gson.fromJson(json, type);
			if(null != bean){
				i = bean.getPages();
				List<DzjyBean> datas = bean.getData();
				for (DzjyBean beans : datas) {
					beans.setTDATE(beans.getTDATE().split("T")[0]);
					if(null !=stopDate && !stopDate.equals("") && beans.getTDATE().equals(stopDate)){
						break z;
					}
					list.add(beans);
				}
			}else{
				break;
			}
			System.out.println("正在抓起第" +p+"页数据");
			p++;
		}
		System.out.println("总共加入数据：" + list.size());
		if(list.size() > 0){
			insBatchEs(list, jestClient, CommonBaseStockInfo.ES_INDEX_DZJY);
		}
	}
	
	public static void getBreakPointDatas(){
		String stopDate = "";
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		ssb.sort("tdate", SortOrder.DESC);
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_DZJY, CommonBaseStockInfo.ES_INDEX_DZJY,
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
		private List<DzjyBean> data;
		public int getPages() {
			return pages;
		}
		public void setPages(int pages) {
			this.pages = pages;
		}
		public List<DzjyBean> getData() {
			return data;
		}
		public void setData(List<DzjyBean> data) {
			this.data = data;
		}
		
	}
}
