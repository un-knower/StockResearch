package com.kers.stock.Controller;

import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kers.esmodel.SelEsRelt;
import com.kers.stock.bean.StockZjlx;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.utils.MathsUtils;
import com.kers.stock.vo.StockBasePageInfo;

@RestController
public class ZjlxController  extends BaseController<StockZjlx>{
	
	@RequestMapping("/zjlx/getList")
    public Map<String,Object> getList(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_STOCK_ZJLX,type);
    }
	
	@RequestMapping("/dpzjlx/getDpList")
    public Map<String,Object> getDpList(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_STOCK_DPZJLX,type);
    }
    
    @RequestMapping("/zjlx/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	StockZjlx info = new StockZjlx();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
    @RequestMapping("/zjlx/getListGraph")
    public String[][] getList2ByName(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	page.setLimit(30);
    	setQuery(query,page);
    	 
    	
    	SelEsRelt<StockZjlx> sel = new SelEsRelt<StockZjlx>(new StockZjlx());
    	SearchSourceBuilder ssb =new SearchSourceBuilder();
    	ssb.query(query);
    	ssb.sort("date", SortOrder.DESC);
		List<StockZjlx>  list =sel.getResultFromQuery(ssb, CommonBaseStockInfo.ES_INDEX_STOCK_ZJLX,CommonBaseStockInfo.ES_INDEX_STOCK_ZJLX, (page.getPage() - 1) * page.getLimit(),
				page.getLimit());

				
    	int j = 0;
    	String[][] str = new String[list.size()][2];
    	for (int i =list.size()-1;i>=0 ;i--) {
    		StockZjlx bean = list.get(i);
    		String[] s = new String[2];
    		s[0] = bean.getDate();
    		s[1] = MathsUtils.m2((bean.getZlNum()/10000));
    		str[j] = s;
    		j++;
		}
    	if(list.size()>0){
    	StockZjlx     zjBean = list.get(0);
    	str[2][0]="              主力占比:"+MathsUtils.m2(zjBean.getZlRatio())+"%               5流入:"+zjBean.getUp5()+"              10流入:"+zjBean.getUp10()+"              连续天数:\r\n"+zjBean.getUpNum();
    	}
    	return str;
    }
    
    
    
   
}