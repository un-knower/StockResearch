package com.cmal.stock.storedata;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.collect.Lists;

import com.cmall.stock.bean.jyfx.Cp;
import com.cmall.stock.bean.jyfx.JyfxInfo;
import com.cmall.stock.bean.jyfx.StockJyfx;
import com.cmall.stock.bean.jyfx.Zyfw;
import com.cmall.stock.bean.jyfx.Zygcfx;
import com.cmall.stock.utils.FilePath;
import com.cmall.stock.utils.MathsUtils;
import com.cmall.stock.utils.StockCodeTransUtil;
import com.cmall.stock.utils.TextUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.httpmodel.BaseConnClient;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

/**
 * 主营业务
 * 
 * @author temp1
 *
 */
public class StockJyfxSet {
	public static void main(String[] args) throws Exception {
		
		//下载数据
//		writeTextReport();
		
		//读数据
		importEs();
		
	}
	
	public static void importEs() throws IOException{
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		List<String> lstSource =CommonBaseStockInfo.getAllAStockInfo();
		final Type type = new TypeToken<StockJyfx>() {
		}.getType();
		final Gson gson = new Gson();
		for(final String sat:lstSource){
			CommonBaseStockInfo.executorServiceLocal.execute(new Thread(){
				@Override
				public void run() {
					try {
						String content = readTextReport(sat);
						StockJyfx jyfx = new StockJyfx();
						jyfx = gson.fromJson(content, type);
						List<JyfxInfo> list = transData(jyfx , sat);
						insBatchEs(list,jestClient,CommonBaseStockInfo.ES_INDEX_STOCK_JYFX);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static String StoreRealUrl(String storeCode) throws ClientProtocolException, IOException {
		storeCode = StockCodeTransUtil.TransSzOrSh(storeCode);
		String url = "http://emweb.securities.eastmoney.com/PC_HSF10/BusinessAnalysis/BusinessAnalysisAjax?code="+storeCode;
		String content = BaseConnClient.baseGetReq(url);
		return content;
	}
	
	public static List<JyfxInfo> transData(StockJyfx jyfx , String stockCode){
		List<JyfxInfo> list = Lists.newArrayList();
		String zyms = "";
		String jpms = "";
		List<Zyfw> zyfw = jyfx.getZyfw();
		if(zyfw.size() > 0){
			zyms = zyfw.get(0).getMs();
		}
		if(jyfx.getJyps().size() > 0){
			jpms = jyfx.getJyps().get(0).getMs();
		}
		List<Zygcfx> zygcfxList = jyfx.getZygcfx();
		for (Zygcfx zygcfx : zygcfxList) {
			List<Cp> hyList = zygcfx.getHy();
			for (Cp cp : hyList) {
				list.add(transCp(cp , stockCode ,zyms,jpms, 1));
			}
			List<Cp> cpList = zygcfx.getCp();
			for (Cp cp : cpList) {
				list.add(transCp(cp , stockCode ,zyms,jpms, 2));
			}
			List<Cp> zyList = zygcfx.getQy();
			for (Cp cp : zyList) {
				list.add(transCp(cp , stockCode ,zyms,jpms, 3));
			}
		}
		return list;
	}
	
	public static JyfxInfo transCp(Cp cp , String stockCode ,String zyms , String jpms, int type){
		JyfxInfo info = new JyfxInfo();
		info.setStockCode(stockCode);
		info.setCbbl(MathsUtils.priceTrans(cp.getCbbl()));
		info.setDw(cp.getDw());
		info.setLrbl(MathsUtils.priceTrans(cp.getLrbl()));
		info.setMll(MathsUtils.priceTrans(cp.getMll()));
		info.setOrderby(cp.getOrderby());
		info.setRq(cp.getRq());
		info.setSrbl(MathsUtils.priceTrans(cp.getSrbl()));
		info.setType(type);
		info.setZycb(MathsUtils.priceTrans(cp.getZycb()));
		info.setZygc(cp.getZygc());
		info.setZylr(MathsUtils.priceTrans(cp.getZylr()));
		info.setZysr(MathsUtils.priceTrans(cp.getZysr()));
		info.setJypsms(jpms);
		info.setZyfwms(zyms);
		return info;
	}
	
	
	public static void insBatchEs(List<JyfxInfo> list, JestClient jestClient, String indexIns) throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (JyfxInfo bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type(bean.getRq())
					.id(String.valueOf(bean.getOrderby())).build();// type("walunifolia").build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}
	
	public static void writeTextReport() throws IOException{
		List<String> lstSource = CommonBaseStockInfo.getAllAStockInfo();
		for(final String  sat:lstSource){
			String content = StoreRealUrl(sat);
			TextUtil.writerTxt(FilePath.saveJyfxPathsuff+sat+".txt", content);
		}
	}
	
	public static String readTextReport(String stockCode){
		String reText = "";
		List<String> list = TextUtil.readTxtFile(FilePath.saveJyfxPathsuff+stockCode+".txt");
		for (String string : list) {
			reText = reText + string;
		}
		return reText;
	}
}
