package com.cmall.staple.data;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.bean.jyfx.JyfxInfo;
import com.cmall.stock.utils.FilePath;
import com.cmall.stock.utils.MathsUtils;
import com.cmall.stock.utils.TextUtil;
import com.google.common.collect.Lists;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;

public class MonthsStapleData {
	
	public static Map<String, List<Double>> map = Maps.newHashMap();

	public  static List<Stap100PPI> getLstFromUrl(String content , String rq) throws ClientProtocolException, IOException {
		// String url =
		// "http://top.100ppi.com/hs/detail-month-2017-10-1-1.html";
		// url = "http://top.100ppi.com/zdb/detail-month-2017-10-1.html";
		
		Document document = Jsoup.parse(content);
		Elements trshead = document.select("table").select("tr");
		List<Stap100PPI> lstSource = Lists.newArrayList();
		for (int i = 0; i < trshead.size(); i++) {
			Elements tds = trshead.get(i).select("td");
			// for (int j = 0; j < tds.size(); j++) {

			if (i > 0) { // 标题
				// 商品 行业 月初价格 月末价格 单位 月涨跌 同比涨跌
				String productName = tds.get(0).text().replace("(市场)", "");
				String productHy = tds.get(1).text();
				double monthYcPrice = MathsUtils.parseDouble(tds.get(2).text());
				double monthYmPrice = MathsUtils.parseDouble(tds.get(3).text());
				String priceDw = tds.get(4).text();
				double monthRise = MathsUtils.parseDouble((tds.get(5).text()).replace("+", "").replace("%", ""));
				double tbRise = MathsUtils.parseDouble((tds.get(6).text()).replace("+", "").replace("%", ""));

				Stap100PPI stap100ppi = new Stap100PPI(productName, productHy, monthYcPrice, monthYmPrice, priceDw,
						monthRise, tbRise);
				stap100ppi.setRq(rq);
				//计算前5次的涨幅
				List<Double> doubleList = map.get(productName);
				if(doubleList == null){
					doubleList = Lists.newArrayList();
				}
				doubleList.add(monthYcPrice);
				if(doubleList.size() > 5){
					doubleList.remove(0);
				}
				map.put(productName, doubleList);
				if(doubleList.size() == 5){
					double wtzf = 0;
					double up = 0;
					for (int j = 4; j >= 0; j--) {
						if(j != 4){
							wtzf = wtzf + (up / doubleList.get(j) - 1);
						}
						up = doubleList.get(j);
					}
					if(wtzf != 0){
						BigDecimal b = new BigDecimal(wtzf * 100);
						wtzf = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					stap100ppi.setWtzf(wtzf);
				}
				lstSource.add(stap100ppi);
				// }
			}
		}
		return lstSource;
	}
	
	//大宗商品价格(月)
	public void stapleMonths() throws ClientProtocolException, IOException{
		List<Stap100PPI> lstSource = Lists.newArrayList();

		String url="http://top.100ppi.com/zdb/detail-month-2017-10-1.html";
//		lstSource.addAll(getLstFromUrl(url));
		  url="http://top.100ppi.com/zdb/detail-month-2017-11-1.html";
//		lstSource.addAll(getLstFromUrl(url));
		
		for (int i = 1; i <=22; i++) {
			String ss="";
			if(i<9){
				ss="0";
			}
			String day = "2017-12" + ss+i;
			url="http://top.100ppi.com/zdb/detail-day-"+day+"-1.html";
			String content = BaseConnClient.baseGetReq(url);
			lstSource.addAll(getLstFromUrl(content , day));
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

	
	public static void main(String[] args) throws Exception {
		//writeTextReport();
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		List<Stap100PPI> lstSource = Lists.newArrayList();
		try {
			for (int i = 1; i <= 12; i++) {
				String source ="2017-"+String.valueOf(i);
				Date date = format.parse(source);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int daynum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				for (int j = 1; j <= daynum; j++) {
					String ss="";
					if(j<=9){
						ss="0";
					}
					String days = source +"-"+ ss+j;
					String content =readTextReport(days);
					lstSource.addAll(getLstFromUrl(content , days));
					System.out.println(days);
				}
				if(lstSource.size() > 0){
					insBatchEs(lstSource ,jestClient  , CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY ,"2017" );
					lstSource = Lists.newArrayList(); 
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insBatchEs(List<Stap100PPI> list, JestClient jestClient, String indexIns , String type) throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (Stap100PPI bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type(type)
					.id(String.valueOf(bean.getRq() + bean.getProductName())).build();// type("walunifolia").build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}
	
	public static void writeTextReport() throws IOException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try {
			for (int i = 1; i <= 12; i++) {
				String source ="2017-"+String.valueOf(i);
				Date date = format.parse(source);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int daynum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				for (int j = 1; j <= daynum; j++) {
					String ss="";
					if(j<=9){
						ss="0";
					}
					String day = source + ss+j;
					String days = source +"-"+ ss+j;
					String url="http://top.100ppi.com/zdb/detail-day-"+day+"-1.html";
					String content = BaseConnClient.baseGetReq(url);
					TextUtil.writerTxt(FilePath.saveStapleDayPathsuff+days+".txt", content);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readTextReport(String days){
		String reText = "";
		List<String> list = TextUtil.readTxtFile(FilePath.saveStapleDayPathsuff+days+".txt");
		for (String string : list) {
			reText = reText + string;
		}
		return reText;
	}
	
}
