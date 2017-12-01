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
	
	static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(30));
	
	@Scheduled(cron = "0 0/1 * * * ?") // 每20秒执行一次
    public void scheduler() {
		Calendar ncalendar = Calendar.getInstance();
		int H = ncalendar.get(Calendar.HOUR_OF_DAY);
		int M = ncalendar.get(Calendar.MINUTE);
		logger.info("===========:"+H);
		logger.info("===========:"+M);
		System.out.println(H);
		System.out.println(M);
		boolean k = false;
		if((H == 9 && M >= 30) || (H==10) || (H==11 && M <= 30) || (H==13) || (H==14)){
			k = true;
		}else{
			k = false;
		}
		System.out.println(k);
		if(k){
			try {
				List<String> lstSource = TextUtil.readTxtFile(FilePath.path);
				final JestClient jestClient = BaseCommonConfig.clientConfig();
				 for (final String string : lstSource) {
					executorServiceLocal.execute(new Thread(){
						@Override
						public void run() {
					          try {
					        	 List<StockRealBean> list = new ArrayList<StockRealBean>();
					        	 StockRealBean real = StoreRealSet.getBeanByCode(string);
					        	 real.setPercent(real.getPercent() * 100);
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
	
	@Scheduled(cron = "0 05 16 * * ?") // 每20秒执行一次
    public void updateInfo() {
		try {
			wDataRealToEs();
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
