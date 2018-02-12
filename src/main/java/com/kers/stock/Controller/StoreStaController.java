package com.kers.stock.Controller;

import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kers.stock.bean.StockStrategyInfo;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.vo.StockBasePageInfo;

@RestController
public class StoreStaController extends BaseController {
	
    @RequestMapping("/sta/getList")
    public Map<String,Object> getList(StockBasePageInfo page , String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getStaLstResult(query,page,type);
    }
    
    @RequestMapping("/sta/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	StockStrategyInfo info = new StockStrategyInfo();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
   
}