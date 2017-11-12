package com.cmal.stock.storedata;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;

import com.artbulb.httpmodel.HttpClientEx;
import com.cmal.stock.strage.StockStragEnSey;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockRealBean;
import com.cmall.stock.utils.CsvHandUtils;
import com.cmall.stock.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

public class StoreAstockTradInfo {

	public final static String savePathsuff = "/opt/stock/stockhistdata/";
	public final static String stockHisCrawUrl = "http://quotes.money.163.com/service/chddata.html?code=";
	public final static String  stockRealTimeUrl="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=2&num=5000&sort=symbol&asc=1&node=hs_a&symbol=&_s_r_a=init#";
//symbol:"sz300720",code:"300720",name:"海川智能",trade:"22.510",pricechange:"2.050",changepercent:"10.020",buy:"22.510",sell:"0.000",settlement:"20.460",open:"22.510",high:"22.510",low:"22.510",volume:2300,amount:51773,ticktime:"11:35:03",per:32.157,pb:5.104,mktcap:162072,nmc:40518,turnoverratio:0.01278
	static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(30));

	
	@SuppressWarnings("all")
	public static void getHistoryData() throws IOException {
		  final String  sfd=new SimpleDateFormat("yyyyMMdd").format(new Date());

		List<String> filePath =CommonBaseStockInfo.getAllAStockInfo();// FileUtils.readLines(new File(CommonBaseStockInfo.astockfilePath));
		for (final String s : filePath) {

			executorServiceLocal.execute(new Thread() {

				public void run() {
					String code = s.split(",")[0];
					String scode = s.startsWith("6") ? "0" + code : "1" + code;
					 
					String webUri = stockHisCrawUrl + scode + "&start=20170101&end="+sfd;
					try {
						HttpClientEx.downloadFromUri(webUri, savePathsuff + code + ".csv");
					} catch (Exception e) {
						e.printStackTrace();
					}
				};

			});

		}

	}

	public static List<StockBaseInfo> getstockBaseInfoFile(String stockCode) throws Exception {
		  final StockStragEnSey stockStragEnSey = new StockStragEnSey();
		String absPath = savePathsuff + stockCode + ".csv";
		CsvHandUtils csvHandUtils = new CsvHandUtils(absPath);
		List<List<String>> lstSource = csvHandUtils.readCSVFile();
		List<StockBaseInfo> result = Lists.newArrayList();
		for (int i = lstSource.size() - 1; i >=1; i--) {
			// List<String> llData=
			List<String> objdata = lstSource.get(i);
			// System.out.println(lstSource.get(i).get(1));
			//Integer.parseInt(
			//日期,股票代码,名称,收盘价,最高价,最低价,开盘价,前收盘,涨跌额,涨跌幅,换手率(10),成交量,成交金额(12),总市值(13),流通市值(14),成交笔数(15)
			//String date, String open, String high, String low, String close, String volume, String rises,
	
//		//,成交金额,总市值,流通市值,成交笔数
//			System.out.println(objdata);
//			System.out.println(objdata.size());
			StockBaseInfo stockBaseInfo = new StockBaseInfo(objdata.get(0), objdata.get(6), objdata.get(4),
					objdata.get(5), objdata.get(3), objdata.get(11), objdata.get(9), stockCode, objdata.get(2),objdata.get(10),objdata.get(12),objdata.get(13),objdata.get(14),objdata.size()<15?null: objdata.get(14),TimeUtils.dayForWeek(objdata.get(0))+"");
			//System.out.println(stockBaseInfo.toString());
			if(!(stockBaseInfo.getOpen()==0||stockBaseInfo.getClose()==0||stockBaseInfo.getHigh()==0||stockBaseInfo.getLow()==0)){///|StringUtils.isBlank(stockBaseInfo.getCjbs()))){//!(stockBaseInfo.getOpen()==0||stockBaseInfo.getClose()==0||stockBaseInfo.getHigh()==0||stockBaseInfo.getLow()==0||StringUtils.isBlank(stockBaseInfo.getCjbs()))){
				result.add(stockBaseInfo);
			}
			//|stockBaseInfo.getRises()))
			
		}
		  stockStragEnSey.addStockBaseInfos(result);
		  stockStragEnSey.computeStockIndex();
		return result;

	}

	public static Map<String, StockBaseInfo> fetchKeyStockInfo(String stockCode) throws IOException, ParseException {
		Map<String, StockBaseInfo> mapsInfo = Maps.newConcurrentMap();
		String absPath = savePathsuff + stockCode + ".csv";
		CsvHandUtils csvHandUtils = new CsvHandUtils(absPath);
		List<List<String>> lstSource = csvHandUtils.readCSVFile();
//		List<StockBaseInfo> result = Lists.newArrayList();
		for (int i = lstSource.size() - 1; i > 0; i--) {
			// List<String> llData=
			List<String> objdata = lstSource.get(i);
			// System.out.println(lstSource.get(i).get(1));
			StockBaseInfo stockBaseInfo = new StockBaseInfo(objdata.get(0), objdata.get(6), objdata.get(4),
					objdata.get(5), objdata.get(3), objdata.get(11), objdata.get(9), stockCode, objdata.get(2),objdata.get(10),objdata.get(12),objdata.get(13),objdata.get(14),objdata.get(14),TimeUtils.dayForWeek(objdata.get(0))+"");
			mapsInfo.put(stockBaseInfo.getDate(), stockBaseInfo);
			// result.add(stockBaseInfo);
		}

		return mapsInfo;

	}
	public static Map<String, StockBaseInfo> fetchKeyStockInfo(List<StockBaseInfo> lstSource ) throws IOException {
		Map<String, StockBaseInfo> mapsInfo = Maps.newConcurrentMap();
		for(StockBaseInfo  infos:lstSource){
			mapsInfo.put(infos.getStockCode(), infos)	;
		}
		return mapsInfo;

	}

	public static StockBaseInfo getStockContinuePrice(Map<String, StockBaseInfo> lstResult, String date, boolean rise) {
		// true + false -
		StockBaseInfo info = null;
		Date cdate = TimeUtils.stringToDate(date, TimeUtils.DEFAULT_DATEYMD_FORMAT);
		for (int i = 1; i < 100; i++) {
			info = lstResult.get(date);
			if (info != null) {
				return info;
			}

			if (rise)
				date = TimeUtils.format(TimeUtils.addDay(cdate, i), TimeUtils.DEFAULT_DATEYMD_FORMAT);
			else
				date = TimeUtils.format(TimeUtils.addDay(cdate, -i), TimeUtils.DEFAULT_DATEYMD_FORMAT);
		}
		return info;
	}
	public static void insBatchEs(List<StockBaseInfo> list,JestClient jestClient,String  indexIns) throws Exception{
		 
		 
		  int i =0;
		  Bulk.Builder bulkBuilder = new Bulk.Builder();
		 for(StockBaseInfo bean:list){
			   i++;
			   // System.out.println(bean.getUnionId());
			 Index index =new Index.Builder(bean).index(indexIns).type(bean.getDate().substring(0, 4)).id(bean.getStockCode()+bean.getDate()).build();//type("walunifolia").build();
			 bulkBuilder.addAction(index);
			 if(i%5000==0){
				 jestClient.execute(bulkBuilder.build());
				 bulkBuilder = new   Bulk.Builder();
			 }
		 	}
		 jestClient.execute(bulkBuilder.build());
		 //jestClient.shutdownClient();
	 }
	public      static  void  wDataToEs() throws IOException{
		List<String> lstSource = CommonBaseStockInfo.getAllAStockInfo();
		 final JestClient  jestClient =BaseCommonConfig.clientConfig();

		for(final String  sat:lstSource){
//			if(sat.equals("603612")){
			executorServiceLocal.execute(new Thread(){
				@Override
				public void run() {
			          try {
			        	  List<StockBaseInfo> lstInfo = getstockBaseInfoFile(sat);
			  			 insBatchEs(lstInfo, jestClient, "stockpcse");
					} catch (Exception e) {
						System.out.println(sat);
						e.printStackTrace();
					}
						
				}
			});
		}
		
	}
	
	public static List<StockRealBean>   getRealTimeData() throws ClientProtocolException, IOException{
		List<StockRealBean>  lstRes= Lists.newArrayList();
		Type type = new TypeToken<List<StockRealBean>>() {
		}.getType();
		Gson gson = new Gson();
		
//		String ccc="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=0&num=100&sort=symbol&asc=0&node=hs_a&symbol=&_s_r_a=init#";
	     for (int i = 1; i <360; i++) {
	 		String ccc="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page="+i+"&num=100&sort=symbol&asc=0&node=hs_a&symbol=&_s_r_a=init#";
	 		String content=BaseConnClient.baseGetReq(ccc);
	 		System.out.println(content);
	 		List<StockRealBean> lstResult = gson.fromJson(content, type);
           
            if(null!=lstResult)
            lstRes.addAll(lstResult);
		}
//	     System.out.println(lstRes);
	     return lstRes;
		
	}
	public static void main(String[] args) throws ClientProtocolException, IOException, Exception {
//		getHistoryData();
//		getRealTimeData();
		wDataToEs();
	}
//		List<StockBaseInfo>  lstResult=getstockBaseInfoFile("000001");
//		String output="";
//		for (int i =0;i< lstResult.size();i++) {
//			//
//			StockBaseInfo  bean = lstResult.get(i);
//			output+=bean.getOpen()+"|"+bean.getHigh()+"|"+bean.getLow()+"|"+bean.getClose()+"|"+bean.getVolume()+"|"+bean.getDate()+",";
//			
//		}
//		output=(output.substring(0, output.length()-1)).replace("-", "/");
//		FileUtils.write(new File("/opt/stockAcd"), output);
//		 getHistoryData();
//		String stockCode = "000001";
//		getstockBaseInfoFile(stockCode)
//		System.out.println(getStockContinuePrice(fetchKeyStockInfo(stockCode), "2017-11-04", true));
//	}

	// public static List<List<String>> getUp20AStock(String stockCode) throws
	// Exception {
		
		
		 
		
//			}
//		}
	  
//		 System.out.println(lstInfo);
	
//	 FileUtils.write(new File("/opt/stock/stockhistdata"+stockCode+".json"), new Gson().toJson(lstInfo));
	// boolean flag = false;
	// String xdPrice = "0";// stockBaseInfo.getClose();
	// String startDate = "";// stockBaseInfo.getDate().replace("-", "/");
	// String output = "";// stockCode + "\t";
	// List<List<String>> lstResult = Lists.newArrayList();
//	 for (StockBaseInfo bean : lstInfo) {
//			System.out.println(bean.getDate()+bean.getOpen()+" "+bean.getK() + "---" + bean.getD() + "---" + bean.getJ());
//
	 }
//	 }
	//
	// if (stockBaseInfo.getStockName().startsWith("XD")) {
	// flag = true;
	// xdPrice = stockBaseInfo.getClose();
	// startDate = stockBaseInfo.getDate().replace("-", "/");
	// }
	// if (flag) {
	// double xcHprice = Double.parseDouble(xdPrice) * 1.2;
	//
	// if (Double.parseDouble(stockBaseInfo.getClose()) >= xcHprice) {
	// // System.out.println("startTime:"+startDate);
	// Date dateStart = new Date(startDate);
	// Date dateEnd = new Date(stockBaseInfo.getDate().replace("-", "//"));
	// output += "\r\n\t" + TimeUtils.format(dateStart, "yyyy-MM-dd") + "\t"
	// + TimeUtils.format(dateEnd, "yyyy-MM-dd") + " \t "
	// + TimeUtils.countDays(dateStart, dateEnd);
	// List<String> lstaaa = Lists.newArrayList();
	// lstaaa.add(stockCode);
	// lstaaa.add(TimeUtils.format(dateStart, "yyyy-MM-dd"));
	// lstaaa.add(TimeUtils.format(dateEnd, "yyyy-MM-dd"));
	// lstaaa.add(TimeUtils.countDays(dateStart, dateEnd) + "");
	// lstResult.add(lstaaa);
	//
	// flag = false;
	// }
	// }
	//
	// }
	// return lstResult;
	// }

	// public static void exportData() throws Exception {
	// List<List<String>> lstResult = Lists.newArrayList();
	//
	// for (String code : getAllAStockInfo()) {
	// try {
	// lstResult.addAll(getUp20AStock(code));
	// } catch (Exception e) {
	// e.printStackTrace();
	// // TODO: handle exception
	// }
	//
	// }
	//
	// ExportExcel<Object> ex2 = new ExportExcel<Object>();
	//
	// OutputStream out2 = new FileOutputStream("/opt/stock/a22.xls");
	// String[] headers = { "股票编码", "除权日期", "收益回收日期", "收益回收天数" };
	// ex2.exportExcelMap("财报回收", headers, lstResult, out2, "yyyy-MM-dd");
	// }

	//// List<String> lstSource= FileUtils.readLines(new
	//// File("/Users/admin/Desktop/tttt"));
	// Map<String,String> mmm = Maps.newConcurrentMap();
	// List<List<String>> llData =Lists.newArrayList();
	// for(String sss:lstSource){
	// try {
	// String source []= sss.split("\t");
	// String year = source[1].substring(0,
	//// source[1].indexOf("-"))+"\t"+source[3];
	// if(mmm.get(year)!=null&&!mmm.get(year).isEmpty()){
	// String sssss = Double.parseDouble(mmm.get(year))+1+"";
	// mmm.put(year, sssss);
	// List<String> lcc=Lists.newArrayList();
	// lcc.add( source[1].substring(0, source[1].indexOf("-")));
	// lcc.add(source[3]);
	//
	// }else
	// mmm.put(year,"1");
	// } catch (Exception e) {
	// e.printStackTrace();
	// // TODO: handle exception
	// }
	// }
	//
	// for (String key : mmm.keySet()) {
	// String sss=key + "\t " + mmm.get(key);
	//// String s444="";
	//// if(Double.valueOf(sss.split("\t") [1])<30)
	//// s444=30;
	//// if(sss.split("\t") [1]>200)
	// if(sss.startsWith("2016"))
	// System.out.println(sss);//(sss.split("\t") [1]);
	// }
	//
	//// System.out.println(mmm);
	// // String stockCode = "603993";
	//
	// // System.out.println(output);
	// // System.out.println(getstockBaseInfoFile("603993"));
	// // getHistoryData();
	// // File[] files = new File(savePathsuff).listFiles();
	// // for (File file : files) {
	// // String absPath = file.getAbsolutePath();
	// //
	// // }
	//
	// }

//}
