package com.cmall.stock.Controller;

import io.searchbox.client.JestClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cmal.stock.storedata.StoreAstockTradInfo;
import com.cmal.stock.storedata.StoreRealSet;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockFuncDetailInfo;
import com.cmall.stock.bean.StockRealBean;
import com.cmall.stock.utils.FilePath;
import com.cmall.stock.utils.TextUtil;
import com.google.common.util.concurrent.MoreExecutors;
import com.kers.esmodel.BaseCommonConfig;

@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {

	Logger logger = Logger.getLogger("chapter07");
//	public static String path = "D://data//sto.txt";
	
	public static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(30));
	
	public static Map<String, StockBaseInfo> upMap;
	
	@Scheduled(cron = "0 0/1 * * * ?") // 每20秒执行一次
    public void scheduler() {
		if(upMap == null){
			final JestClient jestClient = BaseCommonConfig.clientConfig();
			try {
				upMap = StoreRealSet.getUpMap(jestClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Calendar ncalendar = Calendar.getInstance();
		int H = ncalendar.get(Calendar.HOUR_OF_DAY);
		int M = ncalendar.get(Calendar.MINUTE);
		int w = ncalendar.get(Calendar.DAY_OF_WEEK) - 1;
		boolean k = true;
		if(((H == 9 && M >= 30) || (H==10) || (H==11 && M <= 30) || (H==13) || (H==14)) 
				&& w !=6 && w !=7){
			k = true;
		}else{
			k = false;
		}
		if(k){
			try {
				List<String> lstSource = TextUtil.readTxtFile(FilePath.path);
				final JestClient jestClient = BaseCommonConfig.clientConfig();
				final Map<String, StockBaseInfo> map = upMap;
				 for (final String string : lstSource) {
					executorServiceLocal.execute(new Thread(){
						@Override
						public void run() {
					          try {
					        	  StockBaseInfo info = map.get(string);
					        	 List<StockRealBean> list = new ArrayList<StockRealBean>();
					        	 StockRealBean real = StoreRealSet.getBeanByCode(string);
					        	 real.setPercent(real.getPercent() * 100);
					        	 if(info != null){
					        		 real.setUpRises(info.getRises());
					        		 real.setUpVolume(info.getVolume());
					        		 real.setVolumeRises((double)real.getVolume() / (double)info.getVolume());
					        	 }
					        	 list.add(real);
					        	 StoreRealSet.insBatchEs(list,jestClient,"stockrealinfo");
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
	
	@Scheduled(cron = "0 00 16 * * ?") // 每20秒执行一次
    public void updateInfo() {
		try {
			wDataRealToEs();
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
		 final Map<String , StockFuncDetailInfo> map = StoreAstockTradInfo.getInfoByCsv();
		 
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
}
