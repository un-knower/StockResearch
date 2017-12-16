package com.cmall.staple.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.utils.MathsUtils;
import com.google.common.collect.Lists;
import com.kers.httpmodel.BaseConnClient;

public class MonthsStapleData {

	public  static List<Stap100PPI> getLstFromUrl(String url) throws ClientProtocolException, IOException {
		// String url =
		// "http://top.100ppi.com/hs/detail-month-2017-10-1-1.html";
		// url = "http://top.100ppi.com/zdb/detail-month-2017-10-1.html";

		String content = BaseConnClient.baseGetReq(url);
		Document document = Jsoup.parse(content);
		Elements trshead = document.select("table").select("tr");
		List<Stap100PPI> lstSource = Lists.newArrayList();
		for (int i = 0; i < trshead.size(); i++) {
			Elements tds = trshead.get(i).select("td");
			// for (int j = 0; j < tds.size(); j++) {

			if (i > 0) { // 标题
				// 商品 行业 月初价格 月末价格 单位 月涨跌 同比涨跌
				String productName = tds.get(0).text();
				String productHy = tds.get(1).text();
				double monthYcPrice = MathsUtils.parseDouble(tds.get(2).text());
				double monthYmPrice = MathsUtils.parseDouble(tds.get(3).text());
				String priceDw = tds.get(4).text();
				double monthRise = MathsUtils.parseDouble((tds.get(5).text()).replace("+", "").replace("%", ""));
				double tbRise = MathsUtils.parseDouble((tds.get(6).text()).replace("+", "").replace("%", ""));

				Stap100PPI stap100ppi = new Stap100PPI(productName, productHy, monthYcPrice, monthYmPrice, priceDw,
						monthRise, tbRise);
				lstSource.add(stap100ppi);
				// }
			}
		}
		return lstSource;
	}

	public static void main(String[] args) throws Exception {
		List<Stap100PPI> lstSource = Lists.newArrayList();

		String url="http://top.100ppi.com/zdb/detail-month-2017-10-1.html";
//		lstSource.addAll(getLstFromUrl(url));
		  url="http://top.100ppi.com/zdb/detail-month-2017-11-1.html";
//		lstSource.addAll(getLstFromUrl(url));
		
		for (int i = 1; i <=15; i++) {
			String ss="";
			if(i<9){
				ss="0";
			}
			url="http://top.100ppi.com/zdb/detail-day-2017-12"+ss+i+"-1.html";
			lstSource.addAll(getLstFromUrl(url));
		}
		Map<String,Stap100PPI>  mapSource=Maps.newConcurrentMap();
		for(Stap100PPI  bean:lstSource){
//			i++;
			
			if(mapSource.get(bean.getProductName())!=null){
				double monthRises = bean.getMonthRise()+mapSource.get(bean.getProductName()).getMonthRise();
				bean.setMonthRise(monthRises);
				mapSource.put(bean.getProductName(), bean);
//				if(i==lstSource.size())
//				System.out.println(bean.getProductName()+"\t"+bean.getProductHy()+"\t"+bean.getMonthYcPrice()+"\t"+bean.getMonthYmPrice()+"\t"+monthRises+"\t"+bean.getTbRise());
				
			}
			mapSource.put(bean.getProductName(), bean);
//			 System.out.println(bean.getProductName());
		}
		
		for (Map.Entry<String,Stap100PPI> entry : mapSource.entrySet()) {  
			Stap100PPI  bean  = entry.getValue();
			System.out.println(bean.getProductName()+"\t"+bean.getProductHy()+"\t"+bean.getMonthYcPrice()+"\t"+bean.getMonthYmPrice()+"\t"+bean.getMonthRise()+"\t"+bean.getTbRise());
		    // 化工在线
		} 
	}
}
