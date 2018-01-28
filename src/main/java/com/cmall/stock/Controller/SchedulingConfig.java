package com.cmall.stock.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmal.stock.storedata.StockOptionalSet;
import com.cmal.stock.storedata.StoreAstockTradInfo;
import com.cmal.stock.storedata.StoreRealSet;
import com.cmall.staple.data.MonthsStapleData;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.bean.StockOptionalInfo;
import com.cmall.stock.bean.StockRealBean;
import com.cmall.stock.utils.FilePath;
import com.cmall.stock.utils.TextUtil;
import com.google.common.util.concurrent.MoreExecutors;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.QueryComLstData;

import io.searchbox.client.JestClient;

@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {

	Logger logger = Logger.getLogger("chapter07");
//	public static String path = "D://data//sto.txt";
	
	public static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(30));
	
	public static Map<String, StockBaseInfo> upMap;
	
	public static List<String> lstSource;
		
	/**
	 * 每日同步大宗商品
	 */
	@Scheduled(cron = "0 0 0/1 * * ?") // 每20秒执行一次
    public void updateDzsp() {
		try {
			System.out.println("开始同步大宗商品");
			MonthsStapleData.freshEsData();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新自选
	 */
	@Scheduled(cron = "0 0/5 * * * ?") // 每20秒执行一次
    public void updateOption() {
		if(shijian()){
			try {
				final JestClient  jestClient =BaseCommonConfig.clientConfig();
				List<StockOptionalInfo> list = StockOptionalSet.getList(CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL);
				for(final StockOptionalInfo  info:list){
					executorServiceLocal.execute(new Thread(){
						@Override
						public void run() {
					          try {
					        	  System.out.println("zixuan=="+info.getStockName());
					        	  StockRealBean bean = StoreRealSet.getBeanByCode(info.getStockCode());
					        	  info.setPrice(bean.getPrice());
					        	  StockOptionalSet.insBatchBean(info, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL);
							} catch (Exception e) {
								e.printStackTrace();
							}
								
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Scheduled(cron = "0 0/5 * * * ?") // 每20秒执行一次
    public void updateInfo2() {
		if(shijian()){
			try {
				wDataRealToEs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Scheduled(cron = "0 15 16 * * ?") // 每20秒执行一次
    public void updateInfo() {
			try {
				wDataRealToEs();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Scheduled(cron = "0 10 16 * * ?") // 每20秒执行一次
    public void updateHisDate() {
		try {
			StoreAstockTradInfo.getHistoryData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 55 15 * * ?") // 每天9点20初始化数据
    public void setMap() {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		try {
			upMap = StoreRealSet.getUpMap(jestClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  static  void  wDataRealToEs() throws Exception{
		List<String> lstSource = TextUtil.readTxtFile(FilePath.path);
		 final JestClient  jestClient =BaseCommonConfig.clientConfig();
		 final Map<String , StockDetailInfoBean> map =QueryComLstData.getDetailInfo();
		 
		for(final String  sat:lstSource){
			executorServiceLocal.execute(new Thread(){
				@Override
				public void run() {
			          try {
			        	  List<StockBaseInfo> lstInfo = StoreAstockTradInfo.getstockBaseInfo(sat ,  map.get(sat));
			        	  StoreAstockTradInfo.insBatchEs(lstInfo, jestClient, "stockpcse");
					} catch (Exception e) {
						System.out.println(sat);
						e.printStackTrace();
					}
						
				}
			});
		}
		
	}
	
	public boolean shijian(){
		Calendar ncalendar = Calendar.getInstance();
		int H = ncalendar.get(Calendar.HOUR_OF_DAY);
		int M = ncalendar.get(Calendar.MINUTE);
		int w = ncalendar.get(Calendar.DAY_OF_WEEK) - 2;
		System.out.println("H:"+H);
		System.out.println("M:"+M);
		System.out.println("w:"+w);
		boolean k = true;
		if(((H == 9 && M >= 30) || (H==10) || (H==11 && M <= 30) || (H==13) || (H==14)) 
				&& w !=6 && w !=7){
			k = true;
		}else{
			k = false;
		}
		return k;
	}
}
