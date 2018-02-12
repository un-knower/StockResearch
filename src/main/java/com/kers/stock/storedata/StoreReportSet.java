package com.kers.stock.storedata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.QueryComLstData;
import com.kers.httpmodel.BaseConnClient;
import com.kers.stock.bean.EastReportBean;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockDetailInfoBean;
import com.kers.stock.bean.StockReCupplement;
import com.kers.stock.bean.StoreTrailer;
import com.kers.stock.utils.CsvHandUtils;
import com.kers.stock.utils.FilePath;
import com.kers.stock.utils.TextUtil;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

/**
 * 获取业绩报表数据
 * 
 * @author temp1
 *
 */
public class StoreReportSet {
	// static Map<String, StockDetailInfoBean> mapsInfo;
	// static Map<String, StockBaseInfo> mapsInfo2;
	//
	// static {
	// try {
	// mapsInfo = StockDetailInfoHand.getDetailForMap();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// mapsInfo2 = Maps.newConcurrentMap();
	// BoolQueryBuilder queryss = QueryBuilders.boolQuery();
	//
	// queryss.must(QueryBuilders.termQuery("date",TimeUtils.getDate("2018-01-22")));//TimeUtils.DEFAULT_DATEYMD_FORMAT)));//
	// "2018-01-19"));
	// List<StockBaseInfo> lstSource = CommonBaseStockInfo.getLstResult(queryss,
	// "2018");
	//
	// for (StockBaseInfo bean : lstSource) {
	// mapsInfo2.put(bean.getStockCode(), bean);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	// static String cnFinalReportPath = "D://data//Report//";

	public static void main(String[] args) throws Exception {
		// writeTextReport();
		// ups();
//  StoreTrailerSet.wsData();
	 	daoshuju();

	}

	public static void daoshuju() throws Exception {

		List<String> lstSource =  CommonBaseStockInfo.getAllAStockInfo();
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		final Map<String, StoreTrailer> map = StoreTrailerSet.getAllTrailerMap(StoreTrailerSet.P_TYPE_2017_12_31);
		final Map<String, StockDetailInfoBean> mapsInfo = QueryComLstData.getDetailInfo();
		final Map<String, StockBaseInfo> mapsInfo2 = QueryComLstData.getStockBaseInfo();
		//lstSource.add("000055");
		for (final String sat : lstSource) {
			CommonBaseStockInfo.executorServiceLocal.execute(new Thread() {
				@Override
				public void run() {
					String content = readTextReport(sat);
					if (StringUtils.isEmpty(content))
						return;
					try {
						// 利润来源 暂时注释
//						List<StockReCupplement> mapList = StoreAstockEnReport.readFinalReportDetail(sat);
//						Map<String, StockReCupplement> CuMap = Maps.newHashMap();
//						for (StockReCupplement stockReCupplement : mapList) {
//							CuMap.put(stockReCupplement.getBgq(), stockReCupplement);
//						}
						List<EastReportBean> ls = retBeanLst(new Gson().fromJson(content, PaInfo.class).getData());
						List<EastReportBean> lstIns = Lists.newArrayList();
						for (int i = 0; i < ls.size(); i++) {
							EastReportBean bean = ls.get(i);
							if (i == 0) {
								StoreTrailer tr = map.get(sat);
								if (tr != null && ls.size() > 0) {
									bean.setXjlr(tr.getJlr());
									Double ycb = (bean.getXjlr()) / bean.getJlr();
									ycb = V(bean.getJlr(), bean.getXjlr(), ycb);
									bean.setJlr_ycb(ycb);
									bean.setJlr_tbzz_xjd(
											tr.getStartRangeability() == null ? 0 : tr.getStartRangeability());
									bean.setJlr_tbzz_str(tr.getRangeability());
								}
							}
							// 利润来源 暂时注释
//							StockReCupplement cu = CuMap.get(bean.getJzrq());
//							if (cu != null) {
//								bean.setJyhdcsdxjllje(cu.getJyhdcsdxjllje());
//								bean.setTzhdcsdxjllje(cu.getTzhdcsdxjllje());
//							}

							StockDetailInfoBean mapsBean = mapsInfo.get(bean.getStockCode());

							StockBaseInfo baBean = mapsInfo2.get(bean.getStockCode());
							if (baBean != null && mapsBean != null && bean.getXjlr() != 0) {
								double npe = baBean.getClose() / (bean.getXjlr() / mapsBean.getTotals());
								bean.setNpe(npe);
								//
								bean.setPe(baBean.getClose() / (bean.getJlr() / mapsBean.getTotals()));
							}
							if (mapsBean != null) {
								// bean.setTotals(mapsBean.getTotals() *
								// mapsBean.getEsp() *
								// mapsBean.getPe());//
								// mapsBean.getTotalAssets());
								bean.setIndustry(mapsBean.getIndustry());
								bean.setPe(mapsBean.getPe());
								bean.setArea(mapsBean.getArea());
								bean.setIndustry(mapsBean.getIndustry());
								double pe = bean.getPe();
								bean.setPe(pe == 0 ? mapsBean.getPe() : pe);

								bean.setZsz((mapsBean.getPe() * mapsBean.getEsp() * mapsBean.getTotals()));
								// System.out.println(mapsBean);
							}
							lstIns.add(bean);

						}

						insBatchEs(lstIns, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_STOREREPORT);
					} catch (Exception e) {
						System.out.println("error:" + content);
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static String writeFinalReport(String stockCode) throws ClientProtocolException, IOException {
		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&code=" + stockCode
				+ "&st=14&sr=1&p=1&ps=100&rt=50331436&";
		url = url + "js={pages:(pc),data:[(x)]}";
		return BaseConnClient.baseGetReq(url);
		// String wPath = cnFinalReportPath + "rep_" + stockCode + ".txt";
		// FileUtils.write(new File(wPath), content);
	}

	public static String StoreTrailerUrl(int index) throws ClientProtocolException, IOException {
		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&fd=2017-09-30&st=13&sr=-1&p=2&ps=50&"
				+ "js={pages:(pc),data:[(x)]}&stat=" + index + "&rt=50343337";
		String content = BaseConnClient.baseGetReq(url);
		return content;
	}

	public static List<EastReportBean> retBeanLst(String content[]) {

		List<EastReportBean> lstRestlt = Lists.newArrayList();
		Map<String, StockBaseInfo> maps = Maps.newConcurrentMap();

		try {

			// if (true)//content[0].split(",")[0].equals(stockCode))
			// try{
			maps = StoreAstockTradInfo.fetchKeyStockInfo(content[0].split(",")[0]);
			// }catch(Exception e){
			//
			// }
			EastReportBean upbean = null;
			for (int i = content.length - 1; i >= 0; i--) {
				String arrayconent[] = content[i].split(",");
				// System.out.println(arrayconent);
				String stockCode = arrayconent[0];
				String stockName = arrayconent[1];
				String mgsy = arrayconent[2];
				String mgsykc = arrayconent[3];
				String yysr = arrayconent[4];
				String yysr_yw = "0";//StoreAstockEnReport.getFormatNum(yysr);
				String yysr_tbzz = arrayconent[5];
				String yysr_hbzz = arrayconent[6];
				String jlr = arrayconent[7];
				String jlr_gsh ="0";// MathsUtils.getFormatNum(jlr);
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
				Double sjlr = 0d;
				Double xjlr = 0d;
				Double jdzzl_before = 0d;
				// if(upbean != null){
				// jdzzl_before = upbean.getJdzzl();
				// }
				if (i > 1) {
					double tfg = Double.parseDouble(content[i - 1].split(",")[7]);
					jdzzl = Double.parseDouble(jlr) / (tfg == 0 ? 1 : tfg);
					jdzzl = V(tfg, Double.parseDouble(jlr), jdzzl);
					sjlr = tfg;
				}
				if (i > 2) {
					double tfg2 = Double.parseDouble(content[i - 2].split(",")[7]);
					jdzzl_before = (Double.parseDouble(content[i - 1].split(",")[7])) / (tfg2 == 0 ? 1 : tfg2);
					jdzzl_before = V(tfg2, Double.parseDouble(content[i - 1].split(",")[7]), jdzzl_before);
				}
				double jlr_ycb = 0;
				if (i < content.length - 1) {
					jlr_ycb = (Double.parseDouble(content[i + 1].split(",")[7]))
							/ (Double.parseDouble(jlr) == 0 ? 1 : Double.parseDouble(jlr));
					jlr_ycb = V(Double.parseDouble(jlr), Double.parseDouble(content[i + 1].split(",")[7]), jlr_ycb);
					xjlr = Double.parseDouble(content[i + 1].split(",")[7]);
				}
				// System.out.println(jlr_tbzz);
				StockBaseInfo baseInfo = (maps == null ? null
						: StoreAstockTradInfo.getStockContinuePrice(maps, ggrq, true));
				String currentPrice = baseInfo == null ? "null" : baseInfo.getClose() + "";
				EastReportBean eastReportBean = new EastReportBean(stockCode, stockName, mgsy, mgsykc, yysr, yysr_tbzz,
						yysr_hbzz, jlr, jlr_tbzz, jlr_hbzz, mgjzc, jzcsyl, mgxjl, xsmll, lrfp, gxl, ggrq, jzrq, jdzzl,
						currentPrice);
				eastReportBean.setYysr_yw(yysr_yw);
				eastReportBean.setJlr_gsh(jlr_gsh);
				eastReportBean.setJlr_ycb(jlr_ycb);
				eastReportBean.setSjlr(sjlr);
				eastReportBean.setXjlr(xjlr);
				// eastReportBean.setJlr_tbzz(jlr_tbzz);
				eastReportBean.setJdzzl_before(jdzzl_before);
				lstRestlt.add(eastReportBean);
				upbean = eastReportBean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lstRestlt;

	}

	public static void insBatchEs(List<EastReportBean> list, JestClient jestClient, String indexIns) throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (EastReportBean bean : list) {

			try {
				i++;
				Index index = new Index.Builder(bean).index(indexIns).type(bean.getJzrq())
						.id(bean.getStockCode() + bean.getJzrq()).build();// type("walunifolia").build();
				bulkBuilder.addAction(index);
				if (i % 5000 == 0) {
					jestClient.execute(bulkBuilder.build());
					bulkBuilder = new Bulk.Builder();
				}
				jestClient.execute(bulkBuilder.build());
			} catch (Exception e) {

				System.out.println(bean.toString());
				e.printStackTrace();
			}

		}

		// jestClient.shutdownClient();
	}

	public static void ups() {
		List<String> lstSource = TextUtil.readTxtFile(FilePath.path);
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		for (final String sat : lstSource) {
			CommonBaseStockInfo.executorServiceLocal.execute(new Thread() {
				@Override
				public void run() {
					try {
						List<StockReCupplement> list = readFinalReportDetail(sat);
						try {
							UpsBatchEs(list, jestClient, "storereport");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static void UpsBatchEs(List<StockReCupplement> list, JestClient jestClient, String indexIns)
			throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockReCupplement bean : list) {

			try {
				i++;
				Index index = new Index.Builder(bean).index(indexIns).type(bean.getBgq())
						.id(bean.getStockCode() + bean.getBgq()).build();// type("walunifolia").build();
				bulkBuilder.addAction(index);
				if (i % 5000 == 0) {
					jestClient.execute(bulkBuilder.build());
					bulkBuilder = new Bulk.Builder();
				}
				jestClient.execute(bulkBuilder.build());
			} catch (Exception e) {

				System.out.println(bean.toString());
				e.printStackTrace();
			}

		}

		// jestClient.shutdownClient();
	}

	public static void writeTextReport() throws IOException {
		List<String> lstSource = CommonBaseStockInfo.getAllAStockInfo();
		for (final String sat : lstSource) {
			String content = writeFinalReport(sat);
			TextUtil.writerTxt(FilePath.cnFinalReportPath + sat + ".txt", content);
		}
	}

	public static String readTextReport(String stockCode) {
		String reText = "";
		List<String> list = TextUtil.readTxtFile(FilePath.cnFinalReportPath + "rep_" + stockCode + ".txt");
		for (String string : list) {
			reText = reText + string;
		}
		if (reText.startsWith("{pages:0"))
			return null;
		return reText;
	}

	public static Double V(Double v1, Double v2, Double value) {
		if (v2 > v1 && value < 0) {
			return (value * -1);
		}
		if (v2 < v1 && value > 0) {
			return (value * -1);
		}
		return value;
	}
	 public static List<StockReCupplement> readFinalReportDetail(String
		 stockCode) {
		 List<StockReCupplement> reList = Lists.newArrayList();
		 try {
		
		 String absPath = FilePath.cnFinalReportPathDetail + stockCode + ".csv";
		 CsvHandUtils csvHandUtils = new CsvHandUtils(absPath);
		 List<List<String>> lstSource = csvHandUtils.readCSVFile();
		 if (lstSource.size() >= 24) {
		 List<String> str = lstSource.get(0);
		 for (int i = 1; i < str.size(); i++) {
		 if (i >= lstSource.get(1).size() || i >= lstSource.get(2).size() || i >=
		 lstSource.get(3).size()
		 || i >= lstSource.get(4).size() || i >= lstSource.get(5).size()
		 || i >= lstSource.get(6).size() || i >= lstSource.get(7).size()
		 || i >= lstSource.get(8).size() || i >= lstSource.get(9).size()
		 || i >= lstSource.get(10).size() || i >= lstSource.get(11).size()
		 || i >= lstSource.get(12).size() || i >= lstSource.get(13).size()
		 || i >= lstSource.get(14).size() || i >= lstSource.get(15).size()
		 || i >= lstSource.get(16).size() || i >= lstSource.get(17).size()
		 || i >= lstSource.get(18).size() || i >= lstSource.get(19).size()
		 || i >= lstSource.get(20).size() || i >= lstSource.get(21).size()
		 || i >= lstSource.get(22).size() || i >= lstSource.get(23).size()) {
		 break;
		 }
		 StockReCupplement cu = new StockReCupplement(stockCode,
		 lstSource.get(0).get(i),
		 lstSource.get(1).get(i), lstSource.get(2).get(i),
		 lstSource.get(3).get(i),
		 lstSource.get(4).get(i), lstSource.get(5).get(i),
		 lstSource.get(6).get(i),
		 lstSource.get(7).get(i), lstSource.get(8).get(i),
		 lstSource.get(9).get(i),
		 lstSource.get(10).get(i), lstSource.get(11).get(i),
		 lstSource.get(12).get(i),
		 lstSource.get(13).get(i), lstSource.get(14).get(i),
		 lstSource.get(15).get(i),
		 lstSource.get(16).get(i), lstSource.get(17).get(i),
		 lstSource.get(18).get(i),
		 lstSource.get(19).get(i), lstSource.get(20).get(i),
		 lstSource.get(21).get(i),
		 lstSource.get(22).get(i), lstSource.get(23).get(i));
		 // System.out.println(cu);
		 reList.add(cu);
		 }
		 }
		 } catch (Exception e) {
		 e.printStackTrace();
		 // TODO: handle exception
		 }
		 return reList;
		 }
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