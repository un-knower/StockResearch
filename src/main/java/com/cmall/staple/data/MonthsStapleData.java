package com.cmall.staple.data;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.utils.FilePath;
import com.cmall.stock.utils.MathsUtils;
import com.cmall.stock.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class MonthsStapleData {

	public static Map<String, List<Double>> map = Maps.newHashMap();
	final static int startNian = 2014;
	// http://top.100ppi.com/zdb/detail-day---1.html
	static final String stLink = "http://top.100ppi.com/zdb/detail-day---1.html";

	public static List<Stap100PPI> getLstFromUrl(String content, String rq)
			throws ClientProtocolException, IOException {
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
				String a = tds.get(0).select("a").get(0).attr("href");
				String code = a.split("-")[1].split("\\.")[0];
				String productHy = tds.get(1).text();
				double monthYcPrice = MathsUtils.parseDouble(tds.get(2).text());
				double monthYmPrice = MathsUtils.parseDouble(tds.get(3).text());
				String priceDw = tds.get(4).text();
				double monthRise = MathsUtils.parseDouble((tds.get(5).text()).replace("+", "").replace("%", ""));
				double tbRise = MathsUtils.parseDouble((tds.get(6).text()).replace("+", "").replace("%", ""));

				Stap100PPI stap100ppi = new Stap100PPI(productName, productHy, monthYcPrice, monthYmPrice, priceDw,
						monthRise, tbRise);
				stap100ppi.setRq(rq);
				stap100ppi.setCode(Integer.parseInt(code));
				// 计算前5次的涨幅
				List<Double> doubleList = map.get(productName);
				if (doubleList == null) {
					doubleList = Lists.newArrayList();
				}
				doubleList.add(monthYcPrice);
				if (doubleList.size() > 90) {
					doubleList.remove(0);
				}
				map.put(productName, doubleList);
				if (doubleList.size() >= 7) {
					double wtzf = 0;
					double yfzf = 0;
					double jdzf = 0;
					double up = 0;
					for (int j = doubleList.size() - 1; j >= 0; j--) {
						if (j != doubleList.size() - 1) {
							if (j >= doubleList.size() - 7) {
								wtzf = wtzf + (up / doubleList.get(j) - 1);
							}
							if (j >= doubleList.size() - 30) {
								yfzf = yfzf + (up / doubleList.get(j) - 1);
							}
							if (j >= doubleList.size() - 90) {
								jdzf = jdzf + (up / doubleList.get(j) - 1);
							}

						}
						up = doubleList.get(j);
					}
					if (wtzf != 0) {
						BigDecimal b = new BigDecimal(wtzf * 100);
						wtzf = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					if (yfzf != 0) {
						BigDecimal b = new BigDecimal(yfzf * 100);
						yfzf = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					if (jdzf != 0) {
						BigDecimal b = new BigDecimal(jdzf * 100);
						jdzf = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					wtzf = wtzf + stap100ppi.getMonthRise();
					stap100ppi.setWtzf(wtzf);
					yfzf = yfzf + stap100ppi.getMonthRise();
					stap100ppi.setYfzf(yfzf);
					jdzf = jdzf + stap100ppi.getMonthRise();
					stap100ppi.setJdzf(jdzf);
				}
				lstSource.add(stap100ppi);
				// }
			}
		}
		return lstSource;
	}

	/**
	 * 获取所需要的商品
	 * 
	 * @description
	 * @return
	 * @Exception
	 */
	public static List<String> getAllDataLink() throws ClientProtocolException, IOException {
		List<String> lst = Lists.newArrayList();
		Document document = Jsoup.parse(BaseConnClient.baseGetReq("http://www.100ppi.com/"));
		Elements elements = document.getElementsByTag("a");
		Set<String> set = new HashSet<String>();
		for (Element element : elements) {

			String elattr = element.attr("href");
			if ((!set.contains(elattr)) && elattr.contains("detail-day---")) {
				// System.out.println(element.attr("href")+";"+element.text());
				lst.add(elattr + ";" + element.text() + "/");
				set.add(elattr);
			}
		}
		return lst;

		// http://top.100ppi.com/dz/detail-day-2018-0117-1.html
		// http://top.100ppi.com/zdb/detail-day---11.html

	}

	public static List<String> getuseAllLink(List<String> lstSource) throws ClientProtocolException, IOException {
		List<String> lstResult = Lists.newArrayList();
		if (lstSource == null)
			lstSource = Lists.newArrayList();

		for (String source : lstSource) {

			// String floder = source.split(";")[1];
			String filePath = FilePath.saveStapleDayPathsuff + "zdb/" + source + ".txt";
			File file = new File(filePath);
			if (!file.exists() || FileUtils.sizeOf(file) < 1000) {
				String link = stLink.replace("---", "-" + source + "-");
				// http://top.100ppi.com/zdb/detail-day-2018-122-1.html
				String content = BaseConnClient.baseGetReq(link);
				FileUtils.write(file, content, "utf-8");
				lstResult.add(filePath);
			}
		}
		// System.out.println(lstResult);
		return lstResult;

	}

	public static Stap100PPI getLastBean() {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		ssb.sort("rq", SortOrder.DESC);
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY, "2018",
				1, 2);

		final JestClient jestClient = BaseCommonConfig.clientConfig();
		Stap100PPI bean = new Stap100PPI();
		try {
			JestResult results = jestClient.execute(selResult);
			if(results.isSucceeded()){
				bean = results.getSourceAsObject(Stap100PPI.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;

	}

	public static void freshEsData() throws ClientProtocolException, IOException, Exception {
		Stap100PPI bean = getLastBean();
		if(null == bean.getRq() || bean.getRq().equals("")){
			bean.setRq("2014-01-01");
		}
		List<String> lstDate = TimeUtils.getDayList(TimeUtils.toDate(bean.getRq(), TimeUtils.DEFAULT_DATEYMD_FORMAT2),
				new Date(), new SimpleDateFormat("yyyy-Mdd"));
		if (lstDate != null) {
			System.out.println("获取数据==="+lstDate.size());
			getuseAllLink(lstDate);
			wsData(null);
		}
	}

	public static void main(String[] args) throws Exception {
		freshEsData();

//		 getAllDataLink();
		// System.out.println(elements);

		// writeTextReport();

		// String fan = "";
		// Document document = Jsoup.parse("https://www.100ppi.com");
		// Elements eles = document.getElementsByClass("lnews");
		// if(eles.size() > 0){
		// fan = eles.get(0).html();
		// }
		// System.out.println(fan);

		// writeTextReport();

	}

	public static void wsData(List<String> lstStr) throws Exception {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		List<Stap100PPI> lstSource = Lists.newArrayList();
		if (lstStr == null)
			lstStr = com.cmall.stock.utils.FileUtils.getFiles(FilePath.saveStapleDayPathsuff+"/zdb/");
		if (lstStr.isEmpty())
			return;

		for (String s : lstStr) {
			// if(s.contains("2018")){
			File file = new File(s);
			if (s.endsWith(".txt") && FileUtils.sizeOf(file) > 1000) {

				String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));

				if (fileName.length() == 8) {// 2014-110.txt
					fileName = fileName.substring(0, 6) + "-" + fileName.substring(6);
				} else if (fileName.length() == 9) {// 2014-110.txt
					fileName = fileName.substring(0, 7) + "-" + fileName.substring(7);
				} else {
					System.out.println("error" + file.getName());
				}
				// System.out.println(fileName);
				String content = FileUtils.readFileToString(file);
				lstSource.addAll(getLstFromUrl(content, fileName));

				if (lstSource.size() > 0) {
					final List<Stap100PPI> lll = lstSource;
					final String fns = fileName;
					String type = fns.substring(0, fns.indexOf("-"));
					// System.out.println(type);
					// insBatchEs(lll, jestClient,
					// CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY,type
					// );
					// System.out.println(lll);
					CommonBaseStockInfo.executorServiceLocal.execute(new Thread() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
							try {
								insBatchEs(lll, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY,
										fns.substring(0, fns.indexOf("-")));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

					lstSource = Lists.newArrayList();
				}

				// }
			}
		}
	}

	//

	public static void insBatchEs(List<Stap100PPI> list, JestClient jestClient, String indexIns, String type)
			throws Exception {
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

	// public static void writeTextReport() throws IOException {
	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	// Date date1 = new Date();
	// Calendar calendar = new GregorianCalendar();
	// calendar.setTime(date1);
	// int nian = calendar.get(Calendar.YEAR);
	// int yue = calendar.get(Calendar.MARCH) + 1;
	// int tian = calendar.get(Calendar.DATE);
	// final List<String> lstSource = getAllDataLink();
	// try {
	// quan: for (int k = startNian; k <= nian; k++) {
	// for (int i = 1; i <= 12; i++) {
	// String source = k + "-" + String.valueOf(i);
	// // System.out.println(source);
	// Date date = format.parse(source);
	// calendar = new GregorianCalendar();
	// calendar.setTime(date);
	// int daynum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	// for (int j = 1; j <= daynum; j++) {
	// String ss = "";
	// if (j <= 9) {
	// ss = "0";
	// }
	// final String day = source + ss + j;
	// // String days = source + "-" + ss + j;
	//
	// CommonBaseStockInfo.executorServiceLocal.execute(new Thread() {
	// @Override
	// public void run() {
	// try {
	// getuseAllLink(day, lstSource);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	//
	// // TextUtil.writerTxt(, content);
	// // if(k == nian && i == yue){
	// // System.out.println(day);
	// // }
	// if (k == nian && i == yue && j == tian) {
	// getuseAllLink(TimeUtils.getDate(TimeUtils.DEFAULT_DATEYMD_FORMAT),
	// lstSource);
	// break quan;
	// }
	// }
	//
	// }
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// public static String readTextReport(String days) {
	// String reText = "";
	// List<String> list = TextUtil.readTxtFile(FilePath.saveStapleDayPathsuff +
	// days + ".txt");
	// for (String string : list) {
	// reText = reText + string;
	// }
	// return reText;
	// }
	// public static void wsDataRealTime() {
	// final JestClient jestClient = BaseCommonConfig.clientConfig();
	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	// List<Stap100PPI> lstSource = Lists.newArrayList();
	// Date date1 = new Date();
	// Calendar calendar = new GregorianCalendar();
	// calendar.setTime(date1);
	// int nian = calendar.get(Calendar.YEAR);
	// int yue = calendar.get(Calendar.MARCH) + 1;
	// int tian = calendar.get(Calendar.DATE);
	//
	// try {
	// quan: for (int k = startNian; k <= nian; k++) {
	// for (int i = 1; i <= 12; i++) {
	// String source = k + "-" + String.valueOf(i);
	// Date date = format.parse(source);
	// calendar = new GregorianCalendar();
	// calendar.setTime(date);
	// int daynum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	// z: for (int j = 1; j <= daynum; j++) {
	// String ss = "";
	// if (j <= 9) {
	// ss = "0";
	// }
	// String days = source + "-" + ss + j;
	// String content = readTextReport(days);
	// lstSource.addAll(getLstFromUrl(content, days));
	// // System.out.println(days);
	// if (k == nian && i == yue && j == tian) {
	// break z;
	// }
	// }
	// if (lstSource.size() > 0) {
	// insBatchEs(lstSource, jestClient,
	// CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY,
	// String.valueOf(k));
	// lstSource = Lists.newArrayList();
	// }
	// if (k == nian && i == yue) {
	// break quan;
	// }
	//
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// 大宗商品价格(月)
	// public void stapleMonths() throws ClientProtocolException, IOException {
	// List<Stap100PPI> lstSource = Lists.newArrayList();
	//
	// String url = "http://top.100ppi.com/zdb/detail-month-2017-10-1.html";
	// // lstSource.addAll(getLstFromUrl(url));
	// url = "http://top.100ppi.com/zdb/detail-month-2017-11-1.html";
	// // lstSource.addAll(getLstFromUrl(url));
	//
	// for (int i = 1; i <= 22; i++) {
	// String ss = "";
	// if (i < 9) {
	// ss = "0";
	// }
	// String day = "2017-12" + ss + i;
	// url = "http://top.100ppi.com/zdb/detail-day-" + day + "-1.html";
	// String content = BaseConnClient.baseGetReq(url);
	// lstSource.addAll(getLstFromUrl(content, day));
	// }
	// Map<String, Stap100PPI> mapSource = Maps.newConcurrentMap();
	// for (Stap100PPI bean : lstSource) {
	// // i++;
	//
	// if (mapSource.get(bean.getProductName()) != null) {
	// double monthRises = bean.getMonthRise() +
	// mapSource.get(bean.getProductName()).getMonthRise();
	// bean.setMonthRise(monthRises);
	// mapSource.put(bean.getProductName(), bean);
	// // if(i==lstSource.size())
	// //
	// System.out.println(bean.getProductName()+"\t"+bean.getProductHy()+"\t"+bean.getMonthYcPrice()+"\t"+bean.getMonthYmPrice()+"\t"+monthRises+"\t"+bean.getTbRise());
	//
	// }
	// mapSource.put(bean.getProductName(), bean);
	// // System.out.println(bean.getProductName());
	// }
	//
	// for (Map.Entry<String, Stap100PPI> entry : mapSource.entrySet()) {
	// Stap100PPI bean = entry.getValue();
	// System.out.println(bean.getProductName() + "\t" + bean.getProductHy() +
	// "\t" + bean.getMonthYcPrice() + "\t"
	// + bean.getMonthYmPrice() + "\t" + bean.getMonthRise() + "\t" +
	// bean.getTbRise());
	// // 化工在线
	// }
	// }
}
