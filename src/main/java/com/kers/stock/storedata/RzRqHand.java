//package com.kers.stock.storedata;
//
//import io.searchbox.client.JestClient;
//import io.searchbox.client.JestResult;
//import io.searchbox.core.Bulk;
//import io.searchbox.core.Index;
//import io.searchbox.core.Search;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.http.client.ClientProtocolException;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//
//import com.google.common.collect.Lists;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.kers.esmodel.BaseCommonConfig;
//import com.kers.esmodel.UtilEs;
//import com.kers.httpmodel.BaseConnClient;
//import com.kers.stock.Controller.SchedulingConfig;
//import com.kers.stock.bean.RzRqBean;
//import com.kers.stock.bean.RzRqGGBean;
//import com.kers.stock.bean.StockBaseInfo;
//import com.kers.stock.utils.TimeUtils;
//
///**
// * 
// * @author 
// * 大盘融资融券
// * http://dcfm.eastmoney.com//EM_MutiSvcExpandInterface/api/js/get?token=70f12f2f4f091e459a279469fe49eca5&st=tdate&sr=-1&p=1&ps=50&js=var%20gulIvAhy={pages:(tp),data:%20(x)}&type=RZRQ_LSTOTAL_NJ&mk_time=1&rt=50654628
// * 个股融资融券
// * http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?type=RZRQ_DETAIL_NJ&token=70f12f2f4f091e459a279469fe49eca5&filter=(tdate=%272018-03-01T00:00:00%27)&st=rzjmre&sr=-1&p=1&ps=50&js=var%20fPKpOBwo={pages:(tp),data:(x)}&type=RZRQ_DETAIL_NJ&time=1&rt=50665986
// */
//public class RzRqHand {
//	
//	static JestClient jestClient = BaseCommonConfig.clientConfig();
//	
////	static String datetime = TimeUtils.toString(new Date(), TimeUtils.DATE_FORMAT);
//	
//	public static void main(String[] args) throws Exception {
//		//大盘
//		getAllDatas(0,"");
//		//个股
//		for ( int i = 0; i < 30; i++) {
//			final int j = i;
//			SchedulingConfig.executorServiceLocal.execute(new Thread(){
//				@Override
//				public void run() {
//			          try {
//			        	String datetime = TimeUtils.addSubDay(null, j * -1);
//			  			System.out.println("个股日期:"+datetime);
//			  			getAllDatas(1,datetime);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}
//	}
//	
//	public static String getJsonUrlByP(int p) throws ClientProtocolException, IOException {
//		String url = "http://dcfm.eastmoney.com//EM_MutiSvcExpandInterface/api/js/get?token=70f12f2f4f091e459a279469fe49eca5&st=tdate&sr=-1&p="+p+"&ps=50&js=var%20gulIvAhy={pages:(tp),data:%20(x)}&type=RZRQ_LSTOTAL_NJ&mk_time=1&rt=50654628";
//		String content = BaseConnClient.baseGetReq(url);
//		content = content.replace("var gulIvAhy=", "");
//		return content;
//	}
//	
//	public static String getJsonUrlByPGeGu(int p , String time) throws ClientProtocolException, IOException {
//		String url = "http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?type=RZRQ_DETAIL_NJ&token=70f12f2f4f091e459a279469fe49eca5&filter=(tdate=%27"+time+"T00:00:00%27)&st=rzjmre&sr=-1&p="+p+"&ps=50&js=var%20fPKpOBwo={pages:(tp),data:(x)}&type=RZRQ_DETAIL_NJ&time=1&rt=50665986";
//		String content = BaseConnClient.baseGetReq(url);
//		content = content.replace("var fPKpOBwo=", "");
//		return content;
//	}
//	
//	
//	
//	public static void getAllDatas(int t , String time) throws Exception{
//		int p = 1;
//		int i = 1;
//		Type type = new TypeToken<Jsonobj>() {}.getType();
//		Gson gson = new Gson();
//		List<RzRqGGBean> list = Lists.newArrayList();
//		z:while (p <= i ) {
//			String json = "";
//			if(t == 0){
//				json = getJsonUrlByP(p);
//			}
//			if(t == 1){
//				json = getJsonUrlByPGeGu(p,time);
//			}
//			json = json.replace("\"-\"", "0");
//			Jsonobj bean = gson.fromJson(json, type);
//			if(null != bean){
//				i = bean.getPages();
//				List<RzRqGGBean> datas = bean.getData();
//				for (RzRqGGBean rzRqBean : datas) {
//					rzRqBean.setTdate(rzRqBean.getTdate().split("T")[0]);
////					if(null !=stopDate && !stopDate.equals("") && rzRqBean.getTdate().equals(stopDate)){
////						break z;
////					}
//					rzRqBean.setType(1);
//					if(t == 0){
//						rzRqBean.setSname("大盘");
//						rzRqBean.setType(0);
//					}
//					list.add(rzRqBean);
//				}
//			}else{
//				break;
//			}
//			System.out.println("正在抓取第" + p +"页数据");
//			p++;
//		}
//		System.out.println("总共加入数据：" + list.size());
//		if(list.size() > 0){
//			insBatchEs(list, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_RZRQ);
//		}
//	}
//	
//	public static void getBreakPointDatas(){
//		String stopDate = "";
//		SearchSourceBuilder ssb = new SearchSourceBuilder();
//		BoolQueryBuilder query = QueryBuilders.boolQuery();
//		ssb.sort("tdate", SortOrder.DESC);
//		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
//		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_RZRQ, CommonBaseStockInfo.ES_INDEX_STOCK_RZRQ,
//				1, 1);
//		RzRqBean bean = new RzRqBean();
//		try {
//			JestResult results = jestClient.execute(selResult);
//			if(results.isSucceeded()){
//				bean = results.getSourceAsObject(RzRqBean.class);
//				stopDate = bean.getTdate();
//			}
//			getAllDatas(0,stopDate);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void insBatchEs(List<RzRqGGBean> list, JestClient jestClient, String indexIns) throws Exception {
//		if (list == null || list.isEmpty())
//			return;
//		int i = 0;
//		Bulk.Builder bulkBuilder = new Bulk.Builder();
//		for (RzRqGGBean bean : list) {
//			i++;
//			Index index = new Index.Builder(bean).index(indexIns).type(indexIns)
//					.id(bean.getTdate()+bean.getScode()).build();
//			bulkBuilder.addAction(index);
//			if (i % 5000 == 0) {
//				jestClient.execute(bulkBuilder.build());
//				bulkBuilder = new Bulk.Builder();
//			}
//		}
//		jestClient.execute(bulkBuilder.build());
//	}
//	
//	class Jsonobj{
//		private int pages;
//		private List<RzRqGGBean> data;
//		public int getPages() {
//			return pages;
//		}
//		public void setPages(int pages) {
//			this.pages = pages;
//		}
//		public List<RzRqGGBean> getData() {
//			return data;
//		}
//		public void setData(List<RzRqGGBean> data) {
//			this.data = data;
//		}
//		
//	}
//}
