package com.cmal.stock.storedata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmall.stock.bean.GdZJcBean;
import com.cmall.stock.bean.StockStrategyInfo;
import com.cmall.stock.bean.StoreTrailer;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

public class GdzjcInfo {

	public static List<GdZJcBean> convertBeanFromContent()
			throws ClientProtocolException, IOException, InterruptedException {

		final JestClient jestClient = BaseCommonConfig.clientConfig();
		for (int i = 100; i <= 6332; i++) {
			String url = "http://data.eastmoney.com/DataCenter_V3/gdzjc.ashx?pagesize=50&page=" + i
					+ "&js=&param=&sortRule=-1&sortType=NOTICEDATE&tabid=all&code=&name=&rt=50438113";

			String content = BaseConnClient.baseGetReq(url);
			try {
				insBatchEs(getList(content), jestClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		

			// final int n =i;
			// CommonBaseStockInfo.executorServiceLocal.execute(new Thread(){
			//
			// @Override
			// public void run() {
			// String
			// url="http://data.eastmoney.com/DataCenter_V3/gdzjc.ashx?pagesize=50&page="+n+"&js=&param=&sortRule=-1&sortType=NOTICEDATE&tabid=all&code=&name=&rt=50438113";
			//
			// String content="";
			// try {
			//
			// try {
			// content = BaseConnClient.baseGetReq(url);
			// } catch (Exception e) {
			// System.out.println(content);
			// }
			//
			// insBatchEs(getList(content), jestClient);
			// } catch (Exception e) {
			// System.out.println(content);
			// e.printStackTrace();
			// }
			// }
			//
			// });

		}
		System.out.println("fininshed");

		Thread.sleep(10000000);

		return null;

	}

	public static List<GdZJcBean> getList(String data) {
		List<GdZJcBean> list = new ArrayList<GdZJcBean>();
		if (StringUtils.isNotBlank(data)) {
			JSONObject obj = (JSONObject) JSONObject.parse(data);
			JSONArray arr = obj.getJSONArray("data");
			for (int i = 0; i < arr.size(); i++) {
				Object o = arr.get(i);
				String[] datas = o.toString().split(",");
				GdZJcBean tr = new GdZJcBean(datas[0], datas[1], parseDouble(datas[2]), parseDouble(datas[3]), datas[4],
						datas[5].trim(), parseDouble(datas[6]), (datas.length == 17 ? parseDouble(datas[16]) : 0),
						parseDouble(datas[7]), datas[8], parseDouble(datas[9]), parseDouble(datas[10]),
						parseDouble(datas[11]), parseDouble(datas[12]), datas[13], datas[14], datas[15]);
				list.add(tr);
			}
		}
		return list;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {

		convertBeanFromContent();

	}

	public static double parseDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public static void insBatchEs(List<GdZJcBean> list, JestClient jestClient) throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (GdZJcBean bean : list) {
			i++;
			String id = (bean.getStockCode() + bean.getShareHolderName() + bean.getZjcNum() + bean.getBdrq()).hashCode()
					+ "";
			Index index = new Index.Builder(bean).index(CommonBaseStockInfo.ES_INDEX_GDZJC).type("2017").id(id).build();
			// .id(bean.getStockCode() + bean.getDate()).build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}

}
