package com.kers.stock.Controller;

import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.stock.vo.ValuesVo;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

@RestController
public class GmCusController extends BaseController{
	public final  String ES_INDEX_CUS_KV="values";
	
	@RequestMapping("/kv/create")
    public Map<String,Object> create(ValuesVo info) throws Exception {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		insBatchBean(info, jestClient, ES_INDEX_CUS_KV);
		Map<String,Object> map = Maps.newHashMap();
		map.put("code", "200");
        return map;
    }

	@RequestMapping("/kv/queryValuesByKey")
    public String queryValuesByKey(String type , String key) throws Exception {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		ValuesVo vo = getBreakPointDatas(jestClient , key , type);
		if(null != vo && null != vo.getValues()){
			return vo.getValues();
		}
        return "";
    }
	
	public static void insBatchBean(ValuesVo bean, JestClient jestClient, String indexIns) throws Exception {
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		Index index = new Index.Builder(bean).index(indexIns).type(bean.getType())
					.id(bean.getType() + bean.getKeys()).build();// type("walunifolia").build();
		bulkBuilder.addAction(index);
		jestClient.execute(bulkBuilder.build());
		bulkBuilder = new Bulk.Builder();
		jestClient.execute(bulkBuilder.build());
	}
	
	public ValuesVo getBreakPointDatas(JestClient jestClient , String key , String type){
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.termQuery("keys",key));
		query.must(QueryBuilders.termQuery("type",type));
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, ES_INDEX_CUS_KV, type,
				0, 1);
		System.out.println(searchSourceBuilder.toString());
		ValuesVo bean = new ValuesVo();
		try {
			JestResult results = jestClient.execute(selResult);
			if(results.isSucceeded()){
				bean = results.getSourceAsObject(ValuesVo.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
}
