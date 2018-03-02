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
import com.kers.stock.bean.XsjjBean;
import com.kers.stock.utils.TimeUtils;

/**
 * 
 * @author 限售解禁
 * http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?token=70f12f2f4f091e459a279469fe49eca5&st=jjsj&sr=1&p=1&ps=100&type=XSJJ_MX_NJ&js=var%20WslZNCYr={pages:(tp),data:(x)}&filter=(zslx=%270%27)(jjsj%3E=%5e2018-03-02%5e%20and%20jjsj%3C=%5e2020-03-02%5e)&rt=50665835
 * *
 */
public class XsjjHand {
	
	static JestClient jestClient = BaseCommonConfig.clientConfig();
	
	static String endDate = TimeUtils.toString(new Date(), TimeUtils.DATE_FORMAT);
	
	public static void main(String[] args) throws Exception {
		System.out.println("查询开始日期："+endDate);
		getAllDatas("");
	}
	
	public static String getJsonUrlByP(int p) throws ClientProtocolException, IOException {
		String url = "http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?token=70f12f2f4f091e459a279469fe49eca5&st=jjsj&sr=1&p="+p+"&ps=100&type=XSJJ_MX_NJ&js=var%20WslZNCYr={pages:(tp),data:(x)}&filter=(zslx=%270%27)(jjsj%3E=%5e2018-01-01%5e%20and%20jjsj%3C=%5e2020-01-01%5e)&rt=50665835";
//		url = URLEncoder.encode(url, "UTF-8");
		String content = BaseConnClient.baseGetReq(url);
		return content;
	}
	
	public static void insBatchEs(List<XsjjBean> list, JestClient jestClient, String indexIns) throws Exception {
		if (list == null || list.isEmpty())
			return;
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (XsjjBean bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type(indexIns)
					.id(bean.getJjsj()+bean.getGpdm()).build();
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
		List<XsjjBean> list = Lists.newArrayList();
		z:while (p <= i ) {
			String json = getJsonUrlByP(p).replace("var WslZNCYr=", "");
			json = json.replace("\"-\"", "0");
			Jsonobj bean = gson.fromJson(json, type);
			if(null != bean){
				i = bean.getPages();
				List<XsjjBean> datas = bean.getData();
				for (XsjjBean beans : datas) {
					beans.setJjsj(beans.getJjsj().split("T")[0]);
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
			insBatchEs(list, jestClient, CommonBaseStockInfo.ES_INDEX_XSJJ);
		}
	}
	
	class Jsonobj{
		private int pages;
		private List<XsjjBean> data;
		public int getPages() {
			return pages;
		}
		public void setPages(int pages) {
			this.pages = pages;
		}
		public List<XsjjBean> getData() {
			return data;
		}
		public void setData(List<XsjjBean> data) {
			this.data = data;
		}
		
		
	}
}
