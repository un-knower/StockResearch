package com.kers.esmodel;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.collect.Sets;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.google.common.collect.Lists;
import com.kers.stock.bean.jyfx.JyfxInfo;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

public class SelEsRelt<T> {

	private T obj; // 定义泛型成员变量

	public SelEsRelt(T obj) {
		this.obj = obj;
	}

	public T getOb() {
		return obj;
	}

	public void setOb(T obj) {
		this.obj = obj;

	}

	

	public List<T> getResultFromQuery(SearchSourceBuilder searchSourceBuilder , String type, String index, Integer from, Integer size) {
		//SearchSourceBuilder ssb = new SearchSourceBuilder();
		// BoolQueryBuilder query = QueryBuilders.boolQuery();
		// ssb.sort("startDate", SortOrder.DESC);
	//	SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		//System.out.println(searchSourceBuilder.toString());
		Search selResult = UtilEs.getSearch(searchSourceBuilder,index,type,from,size);

		final JestClient jestClient = BaseCommonConfig.clientConfig();
		// Stap100PPI bean = new Stap100PPI();
		try {
			JestResult results = jestClient.execute(selResult);
			return ( List<T>) results.getSourceAsObjectList(obj.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void main(String[] args) {
		
		SelEsRelt  sel = new SelEsRelt(new JyfxInfo());
		SearchSourceBuilder ssb = new SearchSourceBuilder();
				 BoolQueryBuilder query = QueryBuilders.boolQuery();
 				 query.must(QueryBuilders.termQuery("rq","2017-06-30"));
 				 query.must(QueryBuilders.termQuery("type","2"));
				SearchSourceBuilder searchSourceBuilder = ssb.query(query);
				List<JyfxInfo>  lstSource =sel.getResultFromQuery(searchSourceBuilder, "2017-06-30", "jyfx", 0, 6000);
				Map<String,List<JyfxInfo>> mapInfo = Maps.newConcurrentMap();
				Set<String> setS = Sets.newConcurrentHashSet();
				for(JyfxInfo  bean:lstSource){
					 
					if(mapInfo.get(bean.getZygc())==null)
						mapInfo.put(bean.getZygc(), Lists.newArrayList(bean));
					else {
						List<JyfxInfo>  lst = mapInfo.get(bean.getZygc());
						lst.add(bean);
						mapInfo.put(bean.getZygc(),lst);
					}
				//	setS.add(bean.getZygc());
					
				}
				for(String key : mapInfo.keySet()){  
		            System.out.println(key+"="+mapInfo.get(key));  
		        }  

		
	}

}
