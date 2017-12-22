package com.kers.esmodel;

import java.util.List;
import java.util.Map;

import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.google.common.collect.Maps;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Count;
import io.searchbox.core.Count.Builder;
import io.searchbox.core.Index;
import io.searchbox.core.Search;


/**
 * Es公共方法
 * 
 */
public class UtilEs {

	/**
	* @Title: 通过查询条件 获取 count 
	* @param searchSourceBuilder 查询条件
	* @param indexName 数据表
	* @param indexType 数据范围
	* @return   
	* @return Count    
	* @throws
	 */
    public static Count getCount(SearchSourceBuilder searchSourceBuilder,String indexName,String indexType ){
		Builder builder=new Count.Builder().query(searchSourceBuilder.toString());
		builder.addIndex(indexName);
		builder.addType(indexType);
		return builder.build();
    }
    
    /**
	* @Title: 通过查询条件 获取 count 
	* @param searchSourceBuilder 查询条件
	* @param indexName 数据表 集合
	* @param indexType 数据范围 集合
	* @return   
	* @return Count    
	* @throws
	 */
    public static Count getCount(SearchSourceBuilder searchSourceBuilder,String indexName,List<String> indexTypes ){
		Builder builder=new Count.Builder().query(searchSourceBuilder.toString());
		builder.addIndex(indexName);
		builder.addType(indexTypes);
		return builder.build();
    }
    /**
	* @Title: 通过查询条件 获取 count 
	* @param searchSourceBuilder 查询条件
	* @param indexName 数据表 集合
	* @param indexType 数据范围 集合
	* @return   
	* @return Count    
	* @throws
	 */
    public static Count getCount(SearchSourceBuilder searchSourceBuilder,List<String> indexNames,List<String> indexTypes ){
		Builder builder=new Count.Builder().query(searchSourceBuilder.toString());
		builder.addIndex(indexNames);
		builder.addType(indexTypes);
		return builder.build();
    }
    /**
    * @Title: getSearch 
    * @Description:  通过查询条件 获取 Search   
    * @param searchSourceBuilder 查询条件
    * @param indexName  数据表
    * @param indexType 数据存放范围
    * @param from 开始条数
    * @param size 页大小
    * @return   
    * @return Search    
    * @throws
     */
    public static Search getSearch(SearchSourceBuilder searchSourceBuilder,String indexName,String indexType,Integer from,Integer size ){
    	Search.Builder builder=new Search.Builder(searchSourceBuilder.from(from).size(size).toString());
    	builder.addIndex(indexName);
    	builder.addType(indexType);
    	return   builder.build();
    }
    public static Map<String,Object>  getSearchRsult(SearchSourceBuilder searchSourceBuilder,String indexName,String indexType,Integer pageFrom,Integer pageSize, JestClient jestClient ) throws Exception{
    	Map<String,Object> returnMap = Maps.newHashMap();
	Search selResult = getSearch(searchSourceBuilder, indexName, indexType, (pageFrom- 1) * pageSize , pageSize);
		
//		final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List lstBean = results.getSourceAsObjectList(Object.class);
		if(lstBean!= null && lstBean.size() > 0){
			Map hitsMap = (Map)results.getValue("hits");
			if(hitsMap!=null){
				Number total = (Number)hitsMap.get("total");
				if(total!=null){
					returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstBean);
		return  returnMap;
    }
    
    /**
     * @Title: getSearch 
     * @Description:  通过查询条件 获取 Search   
     * @param searchSourceBuilder 查询条件
     * @param indexName  数据表 集合
     * @param indexType 数据存放范围 集合
     * @param from 开始条数
     * @param size 页大小
     * @return   
     * @return Search    
     * @throws
      */
    public static Search getSearch(SearchSourceBuilder searchSourceBuilder,String indexName,List<String> indexTypes,Integer from,Integer size ){
    	Search.Builder builder=new Search.Builder(searchSourceBuilder.from(from).size(size).toString());
    	builder.addIndex(indexName);
    	builder.addType(indexTypes);
    	return   builder.build();
    }
    
    /**
     * @Title: getSearch 
     * @Description:  通过查询条件 获取 Search   
     * @param searchSourceBuilder 查询条件
     * @param indexName  数据表 集合
     * @param indexType 数据存放范围 集合
     * @param from 开始条数
     * @param size 页大小
     * @return   
     * @return Search    
     * @throws
      */
    public static Search getSearch(SearchSourceBuilder searchSourceBuilder,List<String> indexNames,List<String> indexTypes,Integer from,Integer size ){
    	Search.Builder builder=new Search.Builder(searchSourceBuilder.from(from).size(size).toString());
    	builder.addIndex(indexNames);
    	builder.addType(indexTypes);
    	return   builder.build();
    }
    
    public static void insBatchEs(List list,JestClient jestClient,String  indexIns) throws Exception{
		 
		 
		  int i =0;
		  Bulk.Builder bulkBuilder = new Bulk.Builder();
		 for(Object bean:list){
			   i++;
			   // System.out.println(bean.getUnionId());
			 Index index =new Index.Builder(bean).index(indexIns).type(indexIns).build();//type("walunifolia").build();
			 bulkBuilder.addAction(index);
			 if(i%5000==0){
				 jestClient.execute(bulkBuilder.build());
				 bulkBuilder = new   Bulk.Builder();
			 }
		 	}
		 jestClient.execute(bulkBuilder.build());
		 //jestClient.shutdownClient();
	 }
}