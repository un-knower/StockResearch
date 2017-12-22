package com.cmall.stock.Controller;

import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmal.stock.strage.SelGetStock;
import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.vo.StockBasePageInfo;

@RestController
public class stapledayController  extends BaseController<Stap100PPI>{
	
	@RequestMapping("/stapleday/getList")
    public Map<String,Object> getList(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY,type);
    }
    
    @RequestMapping("/stapleday/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	
//    	return getClassNameList(Stap100PPI);
    	Stap100PPI info = new Stap100PPI();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
    
   
}