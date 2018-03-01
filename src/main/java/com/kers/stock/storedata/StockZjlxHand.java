package com.kers.stock.storedata;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;
import com.kers.stock.bean.StockDetailInfoBean;
import com.kers.stock.bean.StockZjlx;
import com.kers.stock.utils.FilePath;
import com.kers.stock.utils.MathsUtils;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

/**
 * 
 * 
 * ClassName：StockZjlxHand Description： author ：admin date ：2018年2月23日 下午1:38:19
 * Modified person：admin Modified date：2018年2月23日 下午1:38:19 Modify remarks：
 * 
 * @version V1.0
 * 
 *          资金连续三天流出
 *
 */
public class StockZjlxHand {

	static Logger logger = LoggerFactory.getLogger(StockZjlxHand.class);

	// 6 1 0 2
	// 个股
	public static List<StockZjlx> parseGgZjlxFromUrl(String stockCode, String stockName)
			throws ClientProtocolException, IOException {
		String scffix = "2";
		if (stockCode.startsWith("6"))
			scffix = "1";

		List<StockZjlx> lstRet = Lists.newArrayList();
		String url = "http://ff.eastmoney.com//EM_CapitalFlowInterface/api/js?type=hff&rtntype=2&js={data:(x)}&check=TMLBMSPROCR&acces_token=1942f5da9b46b069953c873404aad4b5&id="
				+ stockCode + scffix + "&_=1518424081605#";
		String data = BaseConnClient.baseGetReq(url);
		if (StringUtils.isNotBlank(data)) {
			JSONObject obj = (JSONObject) JSONObject.parse(data);
			JSONArray arr = obj.getJSONArray("data");

			for (int i = 0; i < arr.size(); i++) {
				Object bean[] = arr.get(i).toString().replace("\"", "").replace("%", "").split(",");
				String date = bean[0].toString();

				double zlNum = MathsUtils.parseDoubleStockWy(bean[1]);
				double zlRatio = MathsUtils.parseDouble(bean[2].toString());// 百分

				double cddNum = MathsUtils.parseDoubleStockWy(bean[3]);
				double cddRatio = MathsUtils.parseDouble(bean[4].toString());// 百分

				double ddNum = MathsUtils.parseDoubleStockWy(bean[5]);
				double ddRatio = MathsUtils.parseDouble(bean[6].toString());// 百分

				double zdNum = MathsUtils.parseDoubleStockWy(bean[7]);
				double zdRatio = MathsUtils.parseDouble(bean[8].toString());// 百分

				double xdNum = MathsUtils.parseDoubleStockWy(bean[9]);
				double xdRatio = MathsUtils.parseDouble(bean[10].toString());// 百分

				double close = MathsUtils.parseDouble(bean[11].toString());
				double rises = MathsUtils.parseDouble(bean[12].toString());

				StockZjlx stockZjlx = new StockZjlx(date, close, rises, zlNum, zlRatio, cddNum, cddRatio, ddNum,
						ddRatio, zdNum, zdRatio, xdNum, xdRatio);
				stockZjlx.setStockCode(stockCode);
				stockZjlx.setStockName(stockName);
				stockZjlx.setType(2);

				int up3 = 0;
				double up3NumAvg = 0;
				for (int j = 0; j < 3; j++) {

					int index = i - j;
					if (index < 0)
						break;

					double zlUseNum = MathsUtils
							.parseDoubleStockWy(arr.get(index).toString().replace("\"", "").split(",")[1].toString());
					if (zlUseNum > 0) {
						up3++;
					}
					up3NumAvg = up3NumAvg + zlUseNum;
				}
				stockZjlx.setUp3(up3);
				stockZjlx.setUp3NumAvg((up3NumAvg / 3));

				int up5 = 0;
				double up5NumAvg = 0;

				for (int j = 0; j < 5; j++) {

					int index = i - j;
					if (index < 0)
						break;

					double zlUseNum = MathsUtils
							.parseDoubleStockWy(arr.get(index).toString().replace("\"", "").split(",")[1].toString());

					if (zlUseNum > 0) {
						up5++;
					}
					up5NumAvg = up5NumAvg + zlUseNum;
				}
				stockZjlx.setUp5(up5);
				stockZjlx.setUp5NumAvg((up5NumAvg / 5));

				int up10 = 0;
				double up10NumAvg = 0;

				for (int j = 0; j < 10; j++) {

					int index = i - j;
					if (index < 0)
						break;

					double zlUseNum = MathsUtils
							.parseDoubleStockWy(arr.get(index).toString().replace("\"", "").split(",")[1].toString());

					if (zlUseNum > 0) {
						up10++;
					}
					up10NumAvg = up10NumAvg + zlUseNum;
				}
				stockZjlx.setUp10(up10);
				stockZjlx.setUp10NumAvg((up10NumAvg / 10));

				if (i >=1) {
					StockZjlx lstStockZljx= lstRet.get(i - 1);
					int lstUpNm = lstStockZljx.getUpNum();

					if (zlNum > 0) {

						int lstNum = lstUpNm + 1;
						stockZjlx.setUpNum(lstNum);

						if (stockZjlx.getUpNum() <= 0)
							stockZjlx.setUpNum(1);
					} else {

						int inc = lstUpNm - 1;
						stockZjlx.setUpNum(inc);

						if (stockZjlx.getUpNum() >= 0)
							stockZjlx.setUpNum(-1);

					}
					double  zzRises=zlNum-(lstStockZljx.getZlNum()<0?Math.abs(lstStockZljx.getZlNum()):lstStockZljx.getZlNum());//((zlNum-lstStockZljx.getZlNum())/Math.abs((lstStockZljx.getZlNum()==0?10000:lstStockZljx.getZlNum())))*100;
					stockZjlx.setZzRises(zzRises);

				}
				
				 int   upabsNum=Math.abs(stockZjlx.getUpNum());
				  double ljlrNum = 0;
				 for (int j = 0; j < upabsNum; j++) {
					 
					 int index = i - j;
						if (index < 0)
							break;
						
						ljlrNum+=MathsUtils
								.parseDoubleStockWy(arr.get(index).toString().replace("\"", "").split(",")[1].toString());
						
					
				}
				 stockZjlx.setLjlrNum(ljlrNum);
				
			

				lstRet.add(stockZjlx);
			}

		}
		return lstRet;

	}

	public static void insBatchEs(List<StockZjlx> list, JestClient jestClient, String indexIns) throws Exception {

		if (list == null || list.isEmpty())
			return;
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockZjlx bean : list) {
			i++;
			// System.out.println(bean.getUnionId());
			Index index = new Index.Builder(bean).index(indexIns).type(indexIns)
					.id(bean.getStockCode() + bean.getDate()).build();// type("walunifolia").build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
		// jestClient.shutdownClient();
	}

	static JestClient jClient = BaseCommonConfig.clientConfig();

	public static void impBkData() throws Exception {
		for (StockDetailInfoBean bean : getBkStocksInfo()) {
			try {

				String stockCode = bean.getStockCode();
				List<StockZjlx> lstResult = Lists.newArrayList();

				lstResult = parseBKZjlxFromUrl(stockCode, bean.getStockName());

				insBatchEs(lstResult, jClient, CommonBaseStockInfo.ES_INDEX_STOCK_ZJLX);

			} catch (ConnectException e1) {
				logger.error("connectException:" + bean.getStockCode() + " ");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void impGguData() throws Exception {
		List<StockDetailInfoBean> lstSource = StockDetailInfoHand.getDetailForLst();// StockDetailInfoHand.getDetailForLst();//
																					// CommonBaseStockInfo.getAllAStockInfo();
		for (final StockDetailInfoBean bean : lstSource) {

				final String stockCode = bean.getStockCode();
				
				CommonBaseStockInfo.executorServiceLocal.execute(new Thread(){
					
					@Override
					public void run() {
						  try {
								List<StockZjlx> lstResult = Lists.newArrayList();
								if (stockCode.startsWith("0") || stockCode.startsWith("6") || stockCode.startsWith("3")) {
									lstResult = parseGgZjlxFromUrl(bean.getStockCode(), bean.getStockName());
								}

								insBatchEs(lstResult, jClient, CommonBaseStockInfo.ES_INDEX_STOCK_ZJLX);
						} catch (ConnectException e1) {
							logger.error("connectException:" + bean.getStockCode() + " ");
						}catch (Exception e) {
						}
					}
				});

		}

	}

	public static List<StockDetailInfoBean> getBkStocksInfo() throws ClientProtocolException, IOException {

		List<StockDetailInfoBean> lstSource = Lists.newArrayList();

		try {

			for (int i = 1; i <= 2; i++) {
				String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C._BKHY&sty=DCFFPBFM&st=&sr=-1&p="
						+ i + "&ps=999&js={data:[(x)]}&token=894050c76af8597a853f5b408b759f5d&cb=";
				String data = BaseConnClient.baseGetReq(url);
				JSONObject obj = (JSONObject) JSONObject.parse(data);
				JSONArray arr = obj.getJSONArray("data");

				for (int nn = 0; nn < arr.size(); nn++) {
					String bean = arr.get(nn).toString();

					StockDetailInfoBean detailInfoBean = new StockDetailInfoBean();
					detailInfoBean.setStockCode(bean.split(",")[1]);
					detailInfoBean.setStockName(bean.split(",")[2]);
					detailInfoBean.setZsz(MathsUtils.parseDouble(bean.split(",")[3]) * 10000);

					lstSource.add(detailInfoBean);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (lstSource.isEmpty()) { // 断网读本地

			File[] files = new File(FilePath.zjlxFilePath).listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (fileName.startsWith("BK")) {
					StockDetailInfoBean detailInfoBean = new StockDetailInfoBean();
					detailInfoBean.setStockCode(fileName.replace(".txt", ""));

					Document docuemnt = Jsoup.parse(files[i], "utf-8");
					String title = docuemnt.getElementsByTag("title").get(0).text();
					detailInfoBean.setStockName(title.substring(0, title.indexOf("_")));

					lstSource.add(detailInfoBean);
				}
			}
		}

		return lstSource;

	}

	public static List<StockZjlx> parseBKZjlxFromUrl(String stockCode, String stockName) throws IOException {

		List<StockZjlx> lstRet = Lists.newArrayList();

		String url = "http://data.eastmoney.com/bkzj/" + stockCode + ".html";
		String content = "";
		String path = FilePath.zjlxFilePath + stockCode + ".txt";
		try {
			content = BaseConnClient.baseGetReq(url, "gb2312");
			FileUtils.write(new File(path), content);
		} catch (UnknownHostException e) {
			content = FileUtils.readFileToString(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document docuemnt = Jsoup.parse(content);
		Elements trs = docuemnt.getElementById("tb_lishi").select("table").select("tbody").select("tr");
		TvaSet.fullBkVal(trs, lstRet, stockCode, stockName);

		return lstRet;

	}

	public static void main(String[] args) throws Exception {

		impBkData();
//
		impGguData();
		
		StockDpZjlxHand.impDpData();
		// System.out.println(bkZjlx());

		// System.out.println(trs);

	}
}

class TvaSet {

	public static void fullBkVal(Elements eleLst, List<StockZjlx> lstRet, String stockCode, String stockName) {

		Elements trs = new Elements();

		for (int i = eleLst.size() - 1; i >= 0; i--) {
			trs.add(eleLst.get(i));

		}

		for (int i = 0; i < trs.size(); i++) {

			Elements bean = trs.get(i).select("td");
			String date = bean.get(0).text();
			double zlNum = MathsUtils.parseDoubleStockWyFormat(bean.get(1).text());
			double zlRatio = MathsUtils.parseDouble(bean.get(2).text().replace("%", ""));// 百分

			double cddNum = MathsUtils.parseDoubleStockWyFormat(bean.get(3).text());
			double cddRatio = MathsUtils.parseDouble(bean.get(4).text().replace("%", ""));// 百分

			double ddNum = MathsUtils.parseDoubleStockWyFormat(bean.get(5).text());
			double ddRatio = MathsUtils.parseDouble(bean.get(6).text().replace("%", ""));// 百分

			double zdNum = MathsUtils.parseDoubleStockWyFormat(bean.get(7).text());
			double zdRatio = MathsUtils.parseDouble(bean.get(8).text().replace("%", ""));// 百分

			double xdNum = MathsUtils.parseDoubleStockWyFormat(bean.get(9).text());
			double xdRatio = MathsUtils.parseDouble(bean.get(10).text().replace("%", ""));// 百分

			double close = 0;// MathsUtils.parseDouble(bean.get(11).toString());
			double rises = 0;// MathsUtils.parseDouble(bean.get(12).toString());

			StockZjlx stockZjlx = new StockZjlx(date, close, rises, zlNum, zlRatio, cddNum, cddRatio, ddNum, ddRatio,
					zdNum, zdRatio, xdNum, xdRatio);
			stockZjlx.setStockCode(stockCode);
			stockZjlx.setStockName(stockName);
			stockZjlx.setType(1);

			int up3 = 0;
			double up3NumAvg = 0;
			for (int j = 0; j < 3; j++) {

				int index = i - j;
				if (index < 0)
					break;
				double zlUseNum = MathsUtils.parseDoubleStockWyFormat(trs.get(index).select("td").get(1).text());
				if (zlUseNum > 0) {
					up3++;
				}
				up3NumAvg = up3NumAvg + zlUseNum;
			}
			stockZjlx.setUp3(up3);
			stockZjlx.setUp3NumAvg((up3NumAvg / 3));

			int up5 = 0;
			double up5NumAvg = 0;
			for (int j = 0; j < 5; j++) {

				int index = i - j;
				if (index < 0)
					break;

				double zlUseNum = MathsUtils.parseDoubleStockWyFormat(trs.get(index).select("td").get(1).text());
				if (zlUseNum > 0) {
					up5++;
				}
				up5NumAvg = up5NumAvg + zlUseNum;

			}
			stockZjlx.setUp5(up5);
			stockZjlx.setUp5NumAvg((up5NumAvg / 5));

			int up10 = 0;
			double up10NumAvg = 0;
			for (int j = 0; j < 10; j++) {

				int index = i - j;
				if (index < 0)
					break;

				double zlUseNum = MathsUtils.parseDoubleStockWyFormat(trs.get(index).select("td").get(1).text());
				if (zlUseNum > 0) {
					up10++;
				}
				up10NumAvg = up10NumAvg + zlUseNum;

			}
			stockZjlx.setUp10(up10);
			stockZjlx.setUp10NumAvg((up10NumAvg / 10));

			if (i >=1) {
				StockZjlx   lstStockZljx =  lstRet.get(i - 1);
				int lstUpNm =lstStockZljx.getUpNum();

				if (zlNum > 0) {

					int lstNum = lstUpNm + 1;
					stockZjlx.setUpNum(lstNum);

					if (stockZjlx.getUpNum() <= 0)
						stockZjlx.setUpNum(1);
				} else {

					int inc = lstUpNm - 1;
					stockZjlx.setUpNum(inc);

					if (stockZjlx.getUpNum() >= 0)
						stockZjlx.setUpNum(-1);

				}
				double  zzRises=zlNum-(lstStockZljx.getZlNum()<0?Math.abs(lstStockZljx.getZlNum()):lstStockZljx.getZlNum());//((zlNum-lstStockZljx.getZlNum())/Math.abs((lstStockZljx.getZlNum()==0?10000:lstStockZljx.getZlNum())))*100;
				stockZjlx.setZzRises(zzRises);
			}
			 int   upabsNum=Math.abs(stockZjlx.getUpNum());
			  double ljlrNum = 0;
			 for (int j = 0; j < upabsNum; j++) {
				 
				 int index = i - j;
					if (index < 0)
						break;
					
					ljlrNum+= MathsUtils.parseDoubleStockWyFormat(trs.get(index).select("td").get(1).text());
					
				
			}
			 stockZjlx.setLjlrNum(ljlrNum);


			lstRet.add(stockZjlx);
		}
	}

}
