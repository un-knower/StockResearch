package com.kers.stock.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kers.gov.GovBankOMOBean;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.utils.TimeUtils;
import com.kers.stock.vo.StockBasePageInfo;

@RestController
public class GobBankOMOController  extends BaseController<GovBankOMOBean>{
	
	@RequestMapping("/govomo/getList")
    public Map<String,Object> getList(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_GOV_OMO,type);
    }
	
	/**
	 * 获取本周到期
	 * @param page
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/govomo/getDateList")
    public Map<String,Object> getDateList(StockBasePageInfo page, String type) throws Exception {
		SimpleDateFormat  format = new  SimpleDateFormat(TimeUtils.DEFAULT_DATEYMD_FORMAT); 
//		Calendar ncalendar = new GregorianCalendar();
//    	TimeZone tz = TimeZone.getTimeZone("GMT+8");
//    	ncalendar.setTimeZone(tz);
//    	String startDate = format.format(ncalendar.getTime());
//    	int w = -1;
//    	while(w != 0){
//    		ncalendar.add(Calendar.DATE,1);
//    	//	w = ncalendar.get(Calendar.DAY_OF_WEEK) - 1;
//    	}
    	  String startDate=format.format(TimeUtils.addDay(new Date(), -1));
    	String endDate = format.format(TimeUtils.addDay(new Date(), 7));
    	//format.format(ncalendar.getTime());
    	String d = "[{\"must\":\"must\",\"name\":\"expDate\",\"type\":\">=\",\"value\":\""+startDate+"\"},{\"must\":\"must\",\"name\":\"expDate\",\"type\":\"<=\",\"value\":\""+endDate+"\"}]";
    	page.setDatas(d);
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_GOV_OMO,type);
    }
	
	/**
	 * 获取本周到期
	 * @param page
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/govomo/getDateList2")
    public Map<String,Object> getDateList2(StockBasePageInfo page, String type) throws Exception {
		SimpleDateFormat  format = new  SimpleDateFormat(TimeUtils.DEFAULT_DATEYMD_FORMAT); 
		Calendar ncalendar = new GregorianCalendar();
    	TimeZone tz = TimeZone.getTimeZone("GMT+8");
    	ncalendar.setTimeZone(tz);
    	String startDate = format.format(ncalendar.getTime());
    	int w = ncalendar.get(Calendar.DAY_OF_WEEK) - 1;
//    	while(w != 1){
//    		ncalendar.add(Calendar.DATE,-1);
//    		w = ncalendar.get(Calendar.DAY_OF_WEEK) - 1;
//    	}
    	startDate=format.format(TimeUtils.addDay(new Date(), -1));
    	String endDate = format.format(TimeUtils.addDay(new Date(), 7));
    	
    	//String endDate = format.format(ncalendar.getTime());
    	String d = "[{\"must\":\"must\",\"name\":\"date\",\"type\":\">=\",\"value\":\""+startDate+"\"},{\"must\":\"must\",\"name\":\"date\",\"type\":\"<=\",\"value\":\""+endDate+"\"}]";
    	page.setDatas(d);
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_GOV_OMO,type);
    }
    
    @RequestMapping("/govomo/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	GovBankOMOBean info = new GovBankOMOBean();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
    
   
}