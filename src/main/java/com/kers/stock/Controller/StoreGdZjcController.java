package com.kers.stock.Controller;

import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kers.stock.bean.GdZJcBean;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.vo.StockBasePageInfo;

@RestController
public class StoreGdZjcController  extends BaseController{
	
    @RequestMapping("/gdzjc/getList")
    public Map<String,Object> getList(StockBasePageInfo page , String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
    	
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_GDZJC,type);
    }
    
    @RequestMapping("/gdzjc/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	return getClassNameList(GdZJcBean.class);
    }
    
  
}