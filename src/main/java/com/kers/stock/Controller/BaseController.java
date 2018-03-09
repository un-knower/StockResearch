package com.kers.stock.Controller;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kers.stock.vo.StockBaseInfoVo;
import com.kers.stock.vo.StockBasePageInfo;

public class BaseController<T> {
	  public void setQuery(BoolQueryBuilder query , StockBasePageInfo info){
	    	if(!StringUtils.isEmpty(info.getDatas())){
	    		Type type = new TypeToken<List<StockBaseInfoVo>>() {}.getType();
	    		Gson gson = new Gson();
	    		List<StockBaseInfoVo> list = gson.fromJson(info.getDatas(), type);
	    		for (StockBaseInfoVo vo : list) {
	    			if(StringUtils.isEmpty(vo.getValue())){
	    				continue;
	    			}
	    			
	    			
	    			if(vo.getValue().indexOf(",")>-1&&(!vo.getType().equals("in"))){
	    			 String [] valuesarr= vo.getValue().split(",");	
	    			 for (int i = 0; i < valuesarr.length; i++) {
	    				 StockBaseInfoVo baseInfoVo = new StockBaseInfoVo();
	    				 baseInfoVo.setValue(valuesarr[i]);
	    				 baseInfoVo.setMust(vo.getMust());
	    				 baseInfoVo.setName(vo.getName());
	    				 baseInfoVo.setType(vo.getType());
	    				 
	    				 if(baseInfoVo.getMust().equals("must")){
	 	    				
	 	    				query.must(getType(baseInfoVo));
	 	    			}
	 	    			if(vo.getMust().equals("must_not")){
	 	    				query.mustNot(getType(baseInfoVo));
	 	    			}
	 	    			if(vo.getMust().equals("should")){
	 	    				
	 	    				query.should(getType(baseInfoVo));
	 	    			}
	 	    			
					}
	    			
	    			}
	    			else{
	    				
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
	    }
	    
	    public QueryBuilder getType(StockBaseInfoVo vo){
	    	String quvalue=vo.getValue().replace(" ", "");
	    	QueryBuilder q = null;
	    	if(vo.getType().equals("=")){
				q = QueryBuilders.termQuery(vo.getName(),quvalue );
			}
	    	else if(vo.getType().equals(">")){
				q = QueryBuilders.rangeQuery(vo.getName()).from(quvalue).includeLower(false);
			}
	    	else if(vo.getType().equals("<")){
				q = QueryBuilders.rangeQuery(vo.getName()).to(quvalue).includeUpper(false);
			}
	    	else if(vo.getType().equals(">=")){
				q = QueryBuilders.rangeQuery(vo.getName()).from(quvalue).includeLower(true);
			}
	    	else if(vo.getType().equals("<=")){
				q = QueryBuilders.rangeQuery(vo.getName()).to(quvalue).includeUpper(true);
			}
	    	else if(vo.getType().equals("prefix")){
				q = QueryBuilders.prefixQuery(vo.getName(), quvalue);
			}
	    	else if(vo.getType().equals("queryStr")){
	    		
	    		q=QueryBuilders.queryString("\""+quvalue+"\"").field(vo.getName());
			}
	    	else if(vo.getType().equals("in")){
	    		q=QueryBuilders.inQuery(vo.getName(), quvalue.split(","));
			}
	    	else if(vo.getType().equals("matchPhrase")){
	    		q=QueryBuilders.matchPhraseQuery(vo.getName(), quvalue);
			}
	    	
	    	return q;
	    }
	    
	    
	    public String[] getClassNameList(T info) throws Exception {
	    	
	    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();
	        String[] fieldNames=new String[fields.length-1];  
		    for(int i=1;i<fields.length;i++){  
		        fieldNames[i-1]=fields[i].getName();  
		    }  
	        return fieldNames;
	    }

}
