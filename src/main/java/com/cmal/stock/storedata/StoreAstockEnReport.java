package com.cmal.stock.storedata;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;

import com.cmall.stock.Controller.StoreReportController;
import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.bean.StockReCupplement;
import com.cmall.stock.bean.StoreTrailer;
import com.cmall.stock.utils.CsvHandUtils;
import com.cmall.stock.utils.FilePath;
import com.cmall.stock.utils.TextUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

public class StoreAstockEnReport {
//	// static String FilePath.cnFinalReportPath =
//	// FilePath.FilePath.cnFinalReportPath;
//
//	public static void writeFinalReport(String stockCode) throws ClientProtocolException, IOException {
//		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&code=" + stockCode
//				+ "&st=14&sr=1&p=1&ps=100&rt=50331436&";
//		url = url + "js={pages:(pc),data:[(x)]}";
//		String content = BaseConnClient.baseGetReq(url);
//		String wPath = FilePath.cnFinalReportPath + "rep_" + stockCode + ".txt";
//		if (!new File(wPath).exists())
//			FileUtils.write(new File(wPath), content);
//	}
//
//	public static void writeFinalReportDetail(String stockCode) throws ClientProtocolException, IOException {
//		String url = "http://quotes.money.163.com/service/cwbbzy_" + stockCode + ".html";
//		// url = url + "js={pages:(pc),data:[(x)]}";
//		String content = BaseConnClient.baseGetReq(url);
//		String wPath = FilePath.cnFinalReportPathDetail + stockCode + ".csv";
//		if(!(new File(wPath).exists()))
//		FileUtils.write(new File(wPath), content, "GBK");
//	}
//
//	public static List<StockReCupplement> readFinalReportDetail(String stockCode) {
//		List<StockReCupplement> reList = Lists.newArrayList();
//		try {
//
//			String absPath = FilePath.cnFinalReportPathDetail + stockCode + ".csv";
//			CsvHandUtils csvHandUtils = new CsvHandUtils(absPath);
//			List<List<String>> lstSource = csvHandUtils.readCSVFile();
//			if (lstSource.size() >= 24) {
//				List<String> str = lstSource.get(0);
//				for (int i = 1; i < str.size(); i++) {
//					if (i >= lstSource.get(1).size() || i >= lstSource.get(2).size() || i >= lstSource.get(3).size()
//							|| i >= lstSource.get(4).size() || i >= lstSource.get(5).size()
//							|| i >= lstSource.get(6).size() || i >= lstSource.get(7).size()
//							|| i >= lstSource.get(8).size() || i >= lstSource.get(9).size()
//							|| i >= lstSource.get(10).size() || i >= lstSource.get(11).size()
//							|| i >= lstSource.get(12).size() || i >= lstSource.get(13).size()
//							|| i >= lstSource.get(14).size() || i >= lstSource.get(15).size()
//							|| i >= lstSource.get(16).size() || i >= lstSource.get(17).size()
//							|| i >= lstSource.get(18).size() || i >= lstSource.get(19).size()
//							|| i >= lstSource.get(20).size() || i >= lstSource.get(21).size()
//							|| i >= lstSource.get(22).size() || i >= lstSource.get(23).size()) {
//						break;
//					}
//					StockReCupplement cu = new StockReCupplement(stockCode, lstSource.get(0).get(i),
//							lstSource.get(1).get(i), lstSource.get(2).get(i), lstSource.get(3).get(i),
//							lstSource.get(4).get(i), lstSource.get(5).get(i), lstSource.get(6).get(i),
//							lstSource.get(7).get(i), lstSource.get(8).get(i), lstSource.get(9).get(i),
//							lstSource.get(10).get(i), lstSource.get(11).get(i), lstSource.get(12).get(i),
//							lstSource.get(13).get(i), lstSource.get(14).get(i), lstSource.get(15).get(i),
//							lstSource.get(16).get(i), lstSource.get(17).get(i), lstSource.get(18).get(i),
//							lstSource.get(19).get(i), lstSource.get(20).get(i), lstSource.get(21).get(i),
//							lstSource.get(22).get(i), lstSource.get(23).get(i));
//					// System.out.println(cu);
//					reList.add(cu);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//		return reList;
//	}
//
//	public static void downReportInfofromUrl(final String type) throws IOException {
//		List<StockDetailInfoBean> fStr = StockDetailInfoHand.getDetailForNetLst(); // CommonBaseStockInfo.getAllAStockInfo();//
//																					// FileUtils.readLines(new
//		// File("/opt/workspace/StockResearch/data/cnmarket/shareAstock"));
//		for (final StockDetailInfoBean cStocksInfos : fStr) {
//			final String cStocksInfo = cStocksInfos.getStockCode();
//			CommonBaseStockInfo.executorServiceLocal.execute(new Thread() {
//				@Override
//				public void run() {
//					String stockCode = cStocksInfo.split(",")[0];
//					try {
//						if (type.equals("report"))
//							writeFinalReport(stockCode);
//						else if (type.equals("reportDetail"))
//							writeFinalReportDetail(stockCode);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}
//
//	}
//
//	// public static final String stockCode = "002746";
//	public static double tranDouEroC(String num) {
//		if (num.equals("-"))
//			return 0;
//		else {
//			// if(num.indexOf("."))
//			// syso
//			// System.out.println(new
//			// DecimalFormat("######0.00").format(Double.parseDouble(num)));
//			if ((num.startsWith("0.") || num.startsWith("-0.")) && num.length() > 6)
//				num = num.substring(0, 5);
//
//			double dfnum = Double.parseDouble(df.format(Double.parseDouble(num)));
//			// System.out.println(dfnum);
//			return dfnum;
//		}
//	}
//
//	public static List<EastReportBean> retBeanLst(String content[]) throws IOException, ParseException {
//
//		List<EastReportBean> lstRestlt = Lists.newArrayList();
//		// Map<String, StockBaseInfo> maps = Maps.newConcurrentMap();
//		// // if (true)//content[0].split(",")[0].equals(stockCode))
//		// maps =
//		// StoreAstockTradInfo.fetchKeyStockInfo(content[0].split(",")[0]);
//		// Double jdzzl_before = 0d;
//		// for (int i = content.length - 1; i >= 0; i--) {
//		// String arrayconent[] = content[i].split(",");
//		// String stockCode = arrayconent[0];
//		// String stockName = arrayconent[1];
//		// double mgsy = tranDouEroC(arrayconent[2]);
//		// double mgsykc = tranDouEroC(arrayconent[3]);
//		// double yysr = tranDouEroC(arrayconent[4]);
//		// double yysr_tbzz = tranDouEroC(arrayconent[5]);
//		// double yysr_hbzz = tranDouEroC(arrayconent[6]);
//		// double jlr = tranDouEroC(arrayconent[7]);
//		// double jlr_tbzz = tranDouEroC(arrayconent[8]);
//		// double jlr_hbzz = tranDouEroC(arrayconent[9]);
//		// double mgjzc = tranDouEroC(arrayconent[10]);
//		// // System.out.println(arrayconent[11]);
//		// double jzcsyl = tranDouEroC(arrayconent[11]);
//		// double mgxjl = tranDouEroC(arrayconent[12]);
//		// double xsmll = tranDouEroC(arrayconent[13]);//
//		// tranDouEroC("-0.982");//
//		// // tranDdouEroC(arrayconent[13]);
//		// String lrfp = arrayconent[14];
//		// String gxl = arrayconent[15];
//		// String ggrq = arrayconent[16];
//		// String jzrq = arrayconent[17];
//		// Double jdzzl = 0d;
//		// if (i > 1) {
//		// double svf=Double.parseDouble(content[i -
//		// 1].split(",")[7])==0?1:Double.parseDouble(content[i -
//		// 1].split(",")[7]);
//		// jdzzl = ((jlr-svf) /svf)*100;
//		// }
//		//// Double jdzzl_before = 0d;
//		// if(i>2){
//		//// if(stockCode.equals("000546")){
//		//// System.out.println(jzrq+"====="+content[i - 2].split(",")[7]+"
//		// =="+content[i - 1].split(",")[7]);
//		////
//		//// }
//		// double svfgh=Double.parseDouble(content[i -
//		// 2].split(",")[7])==0?1:Double.parseDouble(content[i -
//		// 2].split(",")[7]);
//		// double svf=Double.parseDouble(content[i - 1].split(",")[7]);
//		// jdzzl_before =((svf-svfgh)/Math.abs(svfgh))*100; ////(
//		// Double.parseDouble(content[i - 1].split(",")[7])) / (svf==0?1:svf);
//		// }
//		// StockBaseInfo baseInfo = (maps == null ? null
//		// : StoreAstockTradInfo.getStockContinuePrice(maps, ggrq, true));
//		// String currentPrice = baseInfo == null ? "null" : baseInfo.getClose()
//		// + "";
//		// EastReportBean eastReportBean = new EastReportBean(stockCode,
//		// stockName, mgsy, mgsykc, yysr, yysr_tbzz,
//		// yysr_hbzz, jlr, jlr_tbzz, jlr_hbzz, mgjzc, jzcsyl, mgxjl, xsmll,
//		// lrfp, gxl, ggrq, jzrq, jdzzl,
//		// currentPrice);
//		// eastReportBean.setJdzzl_before(retERRNANDou(jdzzl_before));
//		// lstRestlt.add(eastReportBean);
//		//// jdzzl_before=eastReportBean.getJdzzl_before();
//		//// System.out.println(eastReportBean.getJzrq());
//		//
//		// }
//		return lstRestlt;
//
//		//
//	}
//
//	static java.text.DecimalFormat df = new java.text.DecimalFormat("######0.00");
//
//	public static void main(String[] args) throws Exception {
//
//		 downReportInfofromUrl("reportDetail");//"reportDetail");
////		writeES();
//
//		// File[] file = new File(FilePath.cnFinalReportPath).listFiles();
//		// Map<String,String> maps=Maps.newConcurrentMap();
//		// for(File files:file){
//		// if(files.getName().startsWith("rep_")){
//		// String code=files.getName().replace("rep_", "").replace(".txt", "");
//		// maps.put(code, code);
//		// }
//		// }
//		// List<String> lstNN = CommonBaseStockInfo.getAllAStockInfo();
//		// for(String nnn:lstNN){
//		// if(maps.get(nnn)==null){
//		// writeFinalReport(nnn);
//		// }
//		//// System.out.println(nnn);
//		// }
//
//		// for
//
//		// String content = FileUtils.readFileToString(new
//		// File("/opt/stockreportJson2")).replace("NaN", "0")
//		// .replace("Infinity", "0");
//		// Type type = new TypeToken<List<EastReportBean>>() {
//		// }.getType();
//		// Gson gson = new Gson();
//		// List<EastReportBean> lstResult = gson.fromJson(content, type);
//		// List<EastReportBean> lstResult1706 = Lists.newArrayList();
//		// List<EastReportBean> lstResult1709 = Lists.newArrayList();
//		// for (EastReportBean tp : lstResult) {
//		//
//		// if (tp.getJzrq().equals("2017-06-30"))
//		// lstResult1706.add(tp);
//		// else if (tp.getJzrq().equals("2017-09-30"))
//		// lstResult1709.add(tp);
//		//
//		// }
//		//
//		// reportOutput(lstResult1706);
//		// System.out.println("==============================");
//		// reportOutput(lstResult1709);
//
//		// System.out.println(beanOnes);
//		// List<EastReportBean> lstResult = retBeanLst(new
//		// Gson().fromJson(content, PaInfo.class).getData());
//
//	}
//
//	public static String getFormatNum(String numstr) {
//		if (Double.parseDouble(numstr) >= 100000000)// numstr.length()>8)
//			return new java.text.DecimalFormat("######0.00").format(Double.parseDouble(numstr) / 100000000) + "亿";
//		else
//			return new java.text.DecimalFormat("######.00").format(Double.parseDouble(numstr) / 10000) + "万";
//
//	}
//
//	// public static void reportOutput(List<EastReportBean> lstResult) {
//	// Collections.sort(lstResult, new Comparator<EastReportBean>() {
//	// public int compare(EastReportBean arg0, EastReportBean arg1) {
//	// Double ret = arg1.getJdzzl();
//	// Double ret2 = arg0.getJdzzl();
//	// return ret.compareTo(ret2);
//	// }
//	// });
//	// int i = 0;
//	// for (EastReportBean bean : lstResult) {
//	// if (i <= 60) {
//	// String output = bean.getStockCode() + " " + bean.getStockName() + " " +
//	// bean.getGgrq() + " "
//	// + df.format((bean.getJdzzl())) + " " + bean.getJlr() + " " +
//	// bean.getJlr_hbzz();
//	// System.out.println(output);
//	// i++;
//	// }
//	// }
//	// }
//
//	public static void writeES() throws Exception {
//		File[] files = new File(FilePath.cnFinalReportPath).listFiles();
//		// List<EastReportBean> lCCC = Lists.newArrayList();
//		final JestClient jestClient = BaseCommonConfig.clientConfig();
//		Map<String, StoreTrailer> map = StoreTrailerSet.getAllTrailerMap(StoreTrailerSet.P_TYPE_2017_12_31);
//		// List<EastReportBean> lstRac=Lists.newArrayList();
//		for (int i = 0; i < files.length; i++) {
//			if (files[i].getName().startsWith("rep_")) {// files[i].getAbsolutePath().toString().contains(stockCode))
//				// {
//
//				String content = FileUtils.readFileToString(files[i]);
//				try {
//
//					List<EastReportBean> lstResult = retBeanLst(new Gson().fromJson(content, PaInfo.class).getData());
//					List<EastReportBean> lstN = Lists.newArrayList();
//					// int inc=0;
//
//					for (int inc = 0; inc < lstResult.size(); inc++) {
//						EastReportBean eBean = lstResult.get(inc);
//						if (eBean.getStockCode().equals("001965"))
//							System.out.println("aaaaaaa");
//						StoreTrailer tr = map.get(eBean.getStockCode());
//						if (tr != null && lstResult.size() > 0) {
//							if (tr.getStartRangeability() == null) {
//								if (tr.getType().equals("预增") || tr.getType().equals("预盈")) {
//									eBean.setJlr_ycb(1d);
//								} else {
//									eBean.setJlr_ycb(-1d);
//								}
//							} else {
//								eBean.setJlr_ycb(retERRNANDou(tr.getStartRangeability()));
//							}
//						}
//						eBean.setJlr_gsh(getFormatNum(eBean.getJlr() + ""));
//						// System.out.println(eBean.getJzrq());
//						if (files[i].getName().equals("rep_001965.txt")) {
//							System.out.println("rep_001965");
//						}
//						lstN.add(eBean);
//					}
//					// lstRac.addAll(lstN);
//					final List<EastReportBean> lstRacc = lstN;
//					CommonBaseStockInfo.executorServiceLocal.execute(new Thread() {
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							try {
//								insBatchEs(lstRacc, jestClient, "storereport");
//							} catch (Exception e) {
//								System.out.println(lstRacc);
//								System.out.println(e.getMessage());
//								// e.printStackTrace();
//							}
//						}
//					});
//				} catch (Exception e) {
//					System.out.println(files[i].getAbsolutePath());
//					e.printStackTrace();
//				}
//			}
//		}
//
//		// insBatchEs(list, jestClient, indexIns);
//		// FileUtils.write(new File("/opt/stockreportJson2"), new
//		// Gson().toJson(lCCC));
//
//	}
//
//	public static void insBatchEs(List<EastReportBean> list, JestClient jestClient, String indexIns) throws Exception {
//		int i = 0;
//		Bulk.Builder bulkBuilder = new Bulk.Builder();
//		for (EastReportBean bean : list) {
//
//			try {
//				i++;
//				Index index = new Index.Builder(bean).index(indexIns).type(bean.getJzrq())
//						.id(bean.getStockCode() + bean.getJzrq()).build();// type("walunifolia").build();
//				// System.out.println(bean.getStockCode()+bean.getJzrq());
//				bulkBuilder.addAction(index);
//				if (i % 5000 == 0) {
//					jestClient.execute(bulkBuilder.build());
//					bulkBuilder = new Bulk.Builder();
//				}
//				jestClient.execute(bulkBuilder.build());
//			} catch (Exception e) {
//
//				System.out.println(bean.toString());
//				e.printStackTrace();
//			}
//
//		}
//
//		// jestClient.shutdownClient();
//	}
//
//	public static double retERRNANDou(double num) {
//		if ((num + "").equals("NaN"))
//			return 0;
//		else
//			return num;
//	}
//}
//
//class PaInfo {
//	public String pages;
//	public String[] data;
//
//	public String getPages() {
//		return pages;
//	}
//
//	public void setPages(String pages) {
//		this.pages = pages;
//	}
//
//	public String[] getData() {
//		return data;
//	}
//
//	public void setData(String[] data) {
//		this.data = data;
//	}

}