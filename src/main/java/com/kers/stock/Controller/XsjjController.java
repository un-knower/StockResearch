package com.kers.stock.Controller;

import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kers.stock.bean.DzjyBean;
import com.kers.stock.bean.StockZjlx;
import com.kers.stock.bean.XsjjBean;
import com.kers.stock.bean.jyfx.JyfxInfo;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.vo.StockBasePageInfo;

@RestController
public class XsjjController  extends BaseController<StockZjlx>{
	
	@RequestMapping("/xsjj/getList")
    public Map<String,Object> getList(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_XSJJ,type);
    }
	
    @RequestMapping("/xsjj/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	XsjjBean info = new XsjjBean();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
    
   
}