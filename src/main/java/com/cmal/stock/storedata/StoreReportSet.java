package com.cmal.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.search.sort.Sort;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmall.baseutils.StringUtil;
import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StoreTrailer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;

/**
 * 获取业绩报表数据
 * @author temp1
 *
 */
public class StoreReportSet {
	
	static String cnFinalReportPath = "D://data//Report//";
	
	public static void main(String[] args) throws Exception {
		final JestClient  jestClient =BaseCommonConfig.clientConfig();
		Map<String, StoreTrailer> map = getTrailerMap(jestClient);
		List<String> lstSource = StoreAstockTradInfo.getAllAStockInfo();
		List<EastReportBean> list = new ArrayList<EastReportBean>();
		for(final String  sat:lstSource){
			String content = writeFinalReport(sat);
			List<EastReportBean> ls = retBeanLst(new Gson().fromJson(content, PaInfo.class).getData());
			StoreTrailer tr = map.get(sat);
			if(tr != null && ls.size() > 0){
				EastReportBean bean = ls.get(0);
				if(tr.getStartRangeability() == null){
					if(tr.getType().equals("预增") || tr.getType().equals("预盈")){
						bean.setJlr_ycb(1d);
					}else{
						bean.setJlr_ycb(-1d);
					}
				}else{
					bean.setJlr_ycb(tr.getStartRangeability());
				}
			}
			list.addAll(ls);
		}
		insBatchEs(list , jestClient , "storereport");
		
//		final JestClient  jestClient =BaseCommonConfig.clientConfig();
//		for (int i = 0; i < 25; i++) {
//			String content = StoreTrailerUrl(i);
//			List<EastReportBean> lstResult = retBeanLst(new Gson().fromJson(content, PaInfo.class).getData());
//			//List<EastReportBean> list = getList(content);
//			//保存es
//			insBatchEs(lstResult , jestClient , "storereport");
//		}
		
		//System.out.println(content);
	}
	
	public static String writeFinalReport(String stockCode) throws ClientProtocolException, IOException {
		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&code=" + stockCode
				+ "&st=14&sr=1&p=1&ps=100&rt=50331436&";
		url = url + "js={pages:(pc),data:[(x)]}";
		return  BaseConnClient.baseGetReq(url);
//		String wPath = cnFinalReportPath + "rep_" + stockCode + ".txt";
//		FileUtils.write(new File(wPath), content);
	}
	
	
	public static String StoreTrailerUrl(int index) throws ClientProtocolException, IOException{
		String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&fd=2017-09-30&st=13&sr=-1&p=2&ps=50&"
				+ "js={pages:(pc),data:[(x)]}&stat="+index+"&rt=50343337";
		String content = BaseConnClient.baseGetReq(url);
		return content;
	}
	
	
	
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
			String yysr_yw = StoreAstockEnReport.getFormatNum(yysr);
			String yysr_tbzz = arrayconent[5];
			String yysr_hbzz = arrayconent[6];
			String jlr = arrayconent[7];
			String jlr_gsh = StoreAstockEnReport.getFormatNum(jlr);
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
				jdzzl = Double.parseDouble(jlr) / Double.parseDouble(content[i - 1].split(",")[7]);
			}
			StockBaseInfo baseInfo = (maps == null ? null
					: StoreAstockTradInfo.getStockContinuePrice(maps, ggrq, true));
			String currentPrice = baseInfo == null ? "null" : baseInfo.getClose()+"";
			EastReportBean eastReportBean = new EastReportBean(stockCode, stockName, mgsy, mgsykc, yysr, yysr_tbzz,
					yysr_hbzz, jlr, jlr_tbzz, jlr_hbzz, mgjzc, jzcsyl, mgxjl, xsmll, lrfp, gxl, ggrq, jzrq, jdzzl,
					currentPrice);
			eastReportBean.setYysr_yw(yysr_yw);
			eastReportBean.setJlr_gsh(jlr_gsh);
			lstRestlt.add(eastReportBean);
		}
		return lstRestlt;

	}
	
	public static void insBatchEs(List<EastReportBean> list,JestClient jestClient,String  indexIns) throws Exception{
		  int i =0;
		  Bulk.Builder bulkBuilder = new Bulk.Builder();
		 for(EastReportBean bean:list){
			   
			   try {
				   i++;
				   Index index =new Index.Builder(bean).index(indexIns).type(bean.getJzrq()).id(bean.getStockCode()+bean.getJzrq()).build();//type("walunifolia").build();
					 bulkBuilder.addAction(index);
					 if(i%5000==0){
						 jestClient.execute(bulkBuilder.build());
						 bulkBuilder = new   Bulk.Builder();
					 }
					 jestClient.execute(bulkBuilder.build());
			} catch (Exception e) {
				
				System.out.println(bean.toString());
				e.printStackTrace();
			}
			
		 	}

		 //jestClient.shutdownClient();
	 }
	
	
	public static Map<String, StoreTrailer> getTrailerMap( JestClient  jestClient) throws Exception{
		Map<String, StoreTrailer> map = new HashMap<String, StoreTrailer>();
		 SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		 BoolQueryBuilder query = QueryBuilders.boolQuery();
			Search selResult = UtilEs.getSearch(searchSourceBuilder, "storetrailer", "2017-12-31", 0, 3800);
			JestResult results = jestClient.execute(selResult);
			List<StoreTrailer> lstBean = results.getSourceAsObjectList(StoreTrailer.class);
			for (StoreTrailer storeTrailer : lstBean) {
				map.put(storeTrailer.getStockCode(), storeTrailer);
			}
	       return map;
	}
}




