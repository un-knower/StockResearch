package com.cmal.stock.storedata;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
    @RequestMapping("/hello")
    public String index() throws Exception {
//    	final JestClient jestClient = BaseCommonConfig.clientConfig();
//    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();  
//        QueryBuilder queryBuilder = QueryBuilders  
//            .termQuery("stockCode", "");//单值完全匹配查询  
//        searchSourceBuilder.query(queryBuilder);  
//        searchSourceBuilder.size(10);  
//        searchSourceBuilder.from(0);
//        String query = searchSourceBuilder.toString();   
//       // System.out.println(query);
//        Sort sort = new Sort("date");
//        Search search = new Search.Builder(query)
//     .addIndex("stockpcse")  
//     .addType("2017").addSort(sort)
//     .build();  
//        JestResult result =jestClient.execute(search);
//        return result.toString();
    	return null;
    }
    
}