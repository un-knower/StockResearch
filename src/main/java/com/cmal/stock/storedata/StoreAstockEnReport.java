package com.cmal.stock.storedata;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;

import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockBaseInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kers.httpmodel.BaseConnClient;

public class StoreAstockEnReport {
	static String cnFinalReportPath = "/opt/workspace/StockResearch/data/cnmarket/finalReport/";
	static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(600));

	public static void writeFinalReport(String stockCode) throws ClientProtocolException, IOException {
		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&code=" + stockCode
				+ "&st=14&sr=1&p=1&ps=100&rt=50331436&";
		url = url + "js={pages:(pc),data:[(x)]}";
		String content = BaseConnClient.baseGetReq(url);
		String wPath = cnFinalReportPath + "rep_" + stockCode + ".txt";
		FileUtils.write(new File(wPath), content);
	}

	public static void downReportInfofromUrl() throws IOException {
		List<String> fStr = FileUtils.readLines(new File("/opt/workspace/StockResearch/data/cnmarket/shareAstock"));
		for (final String cStocksInfo : fStr) {
			executorServiceLocal.execute(new Thread() {
				@Override
				public void run() {
					String stockCode = cStocksInfo.split(",")[0];
					try {
						writeFinalReport(stockCode);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

	public static final String stockCode = "002746";

	public static List<EastReportBean> retBeanLst(String content[]) throws IOException, ParseException {

		List<EastReportBean> lstRestlt = Lists.newArrayList();
		Map<String, StockBaseInfo> maps = Maps.newConcurrentMap();
		// if (true)//content[0].split(",")[0].equals(stockCode))
		maps = StoreAstockTradInfo.fetchKeyStockInfo(content[0].split(",")[0]);
		for (int i = content.length -1; i >= 0; i--) {
			String arrayconent[] = content[i].split(",");
			String stockCode = arrayconent[0];
			String stockName = arrayconent[1];
			String mgsy = arrayconent[2];
			String mgsykc = arrayconent[3];
			String yysr = arrayconent[4];
			String yysr_tbzz = arrayconent[5];
			String yysr_hbzz = arrayconent[6];
			String jlr = arrayconent[7];
			String jlr_tbzz = arrayconent[8];
			String jlr_hbzz = arrayconent[9];
			String mgjzc = arrayconent[10];
			String jzcsyl = arrayconent[11];
			String mgxjl = arrayconent[12];
			String xsmll = arrayconent[13];
			String lrfp = arrayconent[14];
			String gxl = arrayconent[15];
			String ggrq = arrayconent[16];
			String jzrq = arrayconent[17];
			Double jdzzl = 0d;
			if (i > 1) {
				jdzzl = Double.parseDouble(jlr) / Double.parseDouble(content[i - 1].split(",")[7]) ;
			}
			StockBaseInfo baseInfo = (maps == null ? null
					: StoreAstockTradInfo.getStockContinuePrice(maps, ggrq, true));
			String currentPrice = baseInfo == null ? "null" : baseInfo.getClose()+"";
			EastReportBean eastReportBean = new EastReportBean(stockCode, stockName, mgsy, mgsykc, yysr, yysr_tbzz,
					yysr_hbzz, jlr, jlr_tbzz, jlr_hbzz, mgjzc, jzcsyl, mgxjl, xsmll, lrfp, gxl, ggrq, jzrq, jdzzl,
					currentPrice);
			lstRestlt.add(eastReportBean);

		}
		return lstRestlt;

		//
	}

	static java.text.DecimalFormat df = new java.text.DecimalFormat("######0.00");

	public static void tmp() throws IOException {
		File[] files = new File(cnFinalReportPath).listFiles();
		List<EastReportBean> lCCC = Lists.newArrayList();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().startsWith("rep_")) {// files[i].getAbsolutePath().toString().contains(stockCode))
				// {

				String content = FileUtils.readFileToString(files[i]);
				try {

					List<EastReportBean> lstResult = retBeanLst(new Gson().fromJson(content, PaInfo.class).getData());

					// System.out.println(lstResult);
					for (int j = 0; j < lstResult.size(); j++) {
						EastReportBean republicCB = lstResult.get(j);
						if (true) {// republicCB.getJzrq().equals("2017-06-30"))
									// {
							lCCC.add(republicCB);

							// if (republicCB.getStockCode().equals(stockCode))
							// {
							// System.out.println(republicCB.getJzrq() + " " +
							// republicCB.getGgrq() + " "
							// + getFormatNum(republicCB.getJlr()) + " "
							// +
							// df.format(Double.parseDouble(republicCB.getJdzzl()))
							// + " ￥"
							// + republicCB.getCurrentPrice());
							//
							// }

						}
					}

				} catch (Exception e) {
					System.out.println(content);
					e.printStackTrace();
				}
			}
		}
		FileUtils.write(new File("/opt/stockreportJson2"), new Gson().toJson(lCCC));

	}

	public static void reportOutput(List<EastReportBean> lstResult) {
		Collections.sort(lstResult, new Comparator<EastReportBean>() {
			public int compare(EastReportBean arg0, EastReportBean arg1) {
				
				 
				Double  ret=arg1.getJdzzl();
				Double  ret2=arg0.getJdzzl();
				return ret.compareTo(ret2);
//						. -Double.parseDouble(arg0.getJdzzl());
//				ret
//				if(ret>0)
//					return 1;
//				else if(ret<0)
//					return 0;
//				else 
//					return -1;
//				    int i  =Integer.parseInt( ret.substring(0,ret.indexOf(".")));
//				    System.out.println(i);
//				return  i;//arg1.getJdzzl().compareTo(arg0.getJdzzl());
			}
		});
		int i = 0;
		for (EastReportBean bean : lstResult) {
			if (i <= 60) {
				String output = bean.getStockCode() + "  " + bean.getStockName() + " " + bean.getGgrq() + " "
						+ df.format((bean.getJdzzl()))+ " " + getFormatNum(bean.getJlr()) + " "
						+ bean.getJlr_hbzz();
				System.out.println(output);
				i++;
			}
		}
	}

	public static void main(String[] args) throws IOException {
//		 tmp();
		String content = FileUtils.readFileToString(new File("/opt/stockreportJson2")).replace("NaN", "0").replace("Infinity", "0");
		Type type = new TypeToken<List<EastReportBean>>() {
		}.getType();
		Gson gson = new Gson();
		List<EastReportBean> lstResult = gson.fromJson(content, type);
		List<EastReportBean> lstResult1706 = Lists.newArrayList();
		List<EastReportBean> lstResult1709 =  Lists.newArrayList();
		for (EastReportBean tp : lstResult) {
			
			if (tp.getJzrq().equals("2017-06-30"))
				lstResult1706.add(tp);
			else if (tp.getJzrq().equals("2017-09-30"))
				lstResult1709.add(tp);

		}
		
		reportOutput(lstResult1706);
		System.out.println("==============================");
		reportOutput(lstResult1709);
		
//		 System.out.println(beanOnes);
//		 List<EastReportBean> lstResult = retBeanLst(new
//		 Gson().fromJson(content, PaInfo.class).getData());

	}

	public static String getFormatNum(String numstr) {
		if (Double.parseDouble(numstr) >= 100000000)// numstr.length()>8)
			return new java.text.DecimalFormat("######0.00").format(Double.parseDouble(numstr) / 100000000) + "亿";
		else
			return new java.text.DecimalFormat("######.00").format(Double.parseDouble(numstr) / 10000) + "万";

	}
	// @Test
	// public void testA() throws ClientProtocolException, IOException{
	//// String url="http://data.eastmoney.com/bbsj/stock600291/yjbb.html";
	// String
	// url="http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&code=600291&st=14&sr=1&p=1&ps=50&rt=50331436&";
	// url=url+"js={pages:(pc),data:[(x)]}"; //{pages:(pc),data:[(x)]}
	// //URLEncoder.encode("js=var%20qWvWPqOp={pages:(pc),data:[(x)]}");
	// System.out.println(url);
	//
	// String content = BaseConnClient.baseGetReq(url);
	// FileUtils.write(new File(), data);
	// System.out.println(content);
	// PaInfo pObj = new Gson().fromJson(content, PaInfo.class);
	// String [] pdatas = pObj.getData();
	// for (int i = 0; i < pdatas.length; i++) {
	//// String [] sdata=pdatas[i].split(",");
	// System.out.println(pdatas[i]);
	// }
	//// System.out.println(pObj.data);
	// }
}

class PaInfo {
	public String pages;
	public String[] data;

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

}