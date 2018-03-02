package com.kers.stock.Controller;

import io.searchbox.client.JestClient;

import java.io.IOException;
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

import com.google.common.util.concurrent.MoreExecutors;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.QueryComLstData;
import com.kers.gov.GovBankOMOHand;
import com.kers.staple.data.MonthsStapleData;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockDetailInfoBean;
import com.kers.stock.bean.StockOptionalInfo;
import com.kers.stock.bean.StockRealBean;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.storedata.RzRqHand;
import com.kers.stock.storedata.StockDetailInfoHand;
import com.kers.stock.storedata.StockDpZjlxHand;
import com.kers.stock.storedata.StockOptionalSet;
import com.kers.stock.storedata.StockZjlxHand;
import com.kers.stock.storedata.StoreAstockTradInfo;
import com.kers.stock.storedata.StoreRealSet;
import com.kers.stock.storedata.StoreReportSet;
import com.kers.stock.storedata.StoreTrailerSet;
import com.kers.stock.utils.FilePath;
import com.kers.stock.utils.TextUtil;
import com.kers.stock.utils.TimeUtils;

@Configuration
@EnableScheduling 
public class SchedulingConfig {

	Logger logger = Logger.getLogger("chapter07");
//	public static String path = "D://data//sto.txt";
	
	public static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(30));
	
	public static Map<String, StockBaseInfo> upMap;
	
	public static List<String> lstSource;
		
	@Scheduled(cron = "0 00 02 * * ?") 
    public void updateDzsp() {
		try {
			MonthsStapleData.freshEsData();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 00 09 * * ?") 
    public void updateRzrq() {
		//大盘
		try {
			RzRqHand.getAllDatas(0,"");
			//个股
			for (int i = 0; i < 30; i++) {
				String datetime = TimeUtils.addSubDay(null, i * -1);
				System.out.println("个股日期:"+datetime);
				RzRqHand.getAllDatas(1,datetime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 20 09 * * ?") 
    public void updateOmO20() {
		GovBankOMOHand.getBreakPointDatas();
	}
	
	@Scheduled(cron = "0 30 11 * * ?") 
    public void updateOmO30() {
		GovBankOMOHand.getBreakPointDatas();
	}
	
	@Scheduled(cron = "0 00 10 * * ?") 
    public void updateDetail10() {
		try {
			StockDetailInfoHand.insBatchEsStore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Scheduled(cron = "0 00 12 * * ?") 
    public void updateDetail12() {
		try {
			StockDetailInfoHand.insBatchEsStore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Scheduled(cron = "0 00 15 * * ?") 
    public void updateDetail15() {
		try {
			StockDetailInfoHand.insBatchEsStore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 0/30 * * * ?") 
    public void updateZjlx() {
		try {
			if(shijian()){
				StockZjlxHand.impBkData();
				StockZjlxHand.impGguData();
				StockDpZjlxHand.impDpData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 30 15 * * ?") 
    public void updateZjlx1530() {
		try {
			if(shijian()){
				StockZjlxHand.impBkData();
				StockZjlxHand.impGguData();
				StockDpZjlxHand.impDpData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 0 03 * * ?") 
    public void updateyubao() {
		try {
			StoreTrailerSet.wsData(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 濮ｅ繑妫╃拹銏″Г閺囧瓨鏌?
//	 */
////	@Scheduled(cron = "0 0 15 * * ?")
//    public void updateCaoSc() {
//		try {
//			StoreAstockEnReport.downReportInfofromUrl("report");
//			StoreAstockEnReport.downReportInfofromUrl("reportDetail");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@Scheduled(cron = "0 30 15 * * ?")
    public void updateCaoScData() {
		try {
			StoreReportSet.daoshuju();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Scheduled(cron = "0 0/5 * * * ?") // 濮?0缁夋帗澧界悰灞肩濞?
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
	
	
	@Scheduled(cron = "0 0/5 * * * ?") 
    public void updateInfo2() {
		if(shijian()){
			try {
				wDataRealToEs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Scheduled(cron = "0 05 15 * * ?") 
    public void updateInfo() {
			try {
				wDataRealToEs();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Scheduled(cron = "0 00 08 * * ?") 
    public void updateHisDate() {
		try {
			StoreAstockTradInfo.getHistoryData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Scheduled(cron = "0 55 15 * * ?") 
    public void setMap() {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		try {
			upMap = StoreRealSet.getUpMap(jestClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args) {
    	try {
			MonthsStapleData.freshEsData();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			        	  if(lstInfo.size() > 0){
			        		  if(!Double.isInfinite(lstInfo.get(0).getK()) &&
			        				  !Double.isInfinite(lstInfo.get(0).getD())){
			        			  StoreAstockTradInfo.insBatchEs(lstInfo, jestClient, "stockpcse");
			        		  }
			        	  }
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
