package com.cmall.stock.Controller;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.cmall.stock.bean.GdZJcBean;
import com.cmall.stock.vo.StockBaseInfoVo;
import com.cmall.stock.vo.StockBasePageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BaseController {
	  public void setQuery(BoolQueryBuilder query , StockBasePageInfo info){
	    	if(!StringUtils.isEmpty(info.getDatas())){
	    		Type type = new TypeToken<List<StockBaseInfoVo>>() {}.getType();
	    		Gson gson = new Gson();
	    		List<StockBaseInfoVo> list = gson.fromJson(info.getDatas(), type);
	    		for (StockBaseInfoVo vo : list) {
	    			if(StringUtils.isEmpty(vo.getValue())){
	    				continue;
	    			}
	    			if(vo.getMust().equals("must")){
	    				query.must(getType(vo));
	    			}
	    			if(vo.getMust().equals("must_not")){
	    				query.mustNot(getType(vo));
	    			}
	    			if(vo.getMust().equals("should")){
	    				query.should(getType(vo));
	    			}
				}
	    	}
	    }
	    
	    public QueryBuilder getType(StockBaseInfoVo vo){
	    	QueryBuilder q = null;
	    	if(vo.getType().equals("=")){
				q = QueryBuilders.termQuery(vo.getName(), vo.getValue());
			}
	    	else if(vo.getType().equals(">")){
				q = QueryBuilders.rangeQuery(vo.getName()).from(vo.getValue()).includeLower(false);
			}
	    	else if(vo.getType().equals("<")){
				q = QueryBuilders.rangeQuery(vo.getName()).to(vo.getValue()).includeUpper(false);
			}
	    	else if(vo.getType().equals(">=")){
				q = QueryBuilders.rangeQuery(vo.getName()).from(vo.getValue()).includeLower(true);
			}
	    	else if(vo.getType().equals("<=")){
				q = QueryBuilders.rangeQuery(vo.getName()).to(vo.getValue()).includeUpper(true);
			}
	    	else if(vo.getType().equals("prefix")){
				q = QueryBuilders.prefixQuery(vo.getName(), vo.getValue());
			}
	    	else if(vo.getType().equals("queryStr")){
	    		q=QueryBuilders.queryString("\""+vo.getValue()+"\"").field(vo.getName());
			}
//	    	else if(vo.getType().equals("miss")){
//	    		QueryBuilders.
//	    		q=QueryBuilders.queryString(vo.getValue()).field(vo.getName());
//			}
			
	    	return q;
	    }
	    
	    
	    public String[] getClassNameList(Class info) throws Exception {
	    	java.lang.reflect.Field[] fields=info.getDeclaredFields();  
	        String[] fieldNames=new String[fields.length-1];  
		    for(int i=1;i<fields.length;i++){  
		        fieldNames[i-1]=fields[i].getName();  
		    }  
	        return fieldNames;
	    }

}
