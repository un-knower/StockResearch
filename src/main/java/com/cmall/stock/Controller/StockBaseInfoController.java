package com.cmall.stock.Controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmal.stock.strage.SelGetStock;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.vo.StockBaseInfoVo;
import com.cmall.stock.vo.StockBasePageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class StockBaseInfoController extends BaseController {
	
    @RequestMapping("/getList")
    public Map<String,Object> getList(StockBasePageInfo page) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getLstResult(query,page);
    }
    
    @RequestMapping("/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	StockBaseInfo info = new StockBaseInfo();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
    @RequestMapping("/focDays/getList")
    public Map<String,Object> getfocDaysList(StockBasePageInfo page) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	
    	setQuery(query,page);
        return SelGetStock.getfocDaysLstResult(query,page);
    }
    
    @RequestMapping("/focDays/getClassNameList")
    public String[] getClassfocDaysNameList() throws Exception {
    	StockBaseInfo info = new StockBaseInfo();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
    
}