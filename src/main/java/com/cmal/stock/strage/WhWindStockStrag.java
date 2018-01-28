package com.cmal.stock.strage;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmall.stock.bean.StockBaseInfo;
import com.kers.esmodel.SelEsRelt;

/**
 * 
 *       
 * ClassName：WhWindStockStrag    
 * Description：    
 * author ：admin    
 * date ：2018年1月28日 下午12:16:35    
 * Modified   person：admin    
 * Modified date：2018年1月28日 下午12:16:35    
 * Modify remarks：    
 * @version     V1.0
 * 
 * 值得投资的股票
 *
 */
public class WhWindStockStrag {
	
	 //1.市盈率不得大于茅台    18 1/28 取茅台市盈率
	public static List<StockBaseInfo> queryBchipStock() {

		String date = "2018-1-24";
		String zsz = "55000000000";
		// 排除的股
		String excStock = "600547,600406,002252,600893,601108,601808,601989,300059,002044,601878,002024,601857,300104,600028,002010,300015";

		SelEsRelt<StockBaseInfo> selBaseInfo = new SelEsRelt<StockBaseInfo>(new StockBaseInfo());
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.termQuery("date", date));
		query.must(QueryBuilders.rangeQuery("zsz").gt(zsz));// termQuery("date",
															// date));
		query.mustNot(QueryBuilders.inQuery("stockCode", excStock.split(",")));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
		List<StockBaseInfo> lstResult = selBaseInfo.getResultFromQuery(searchSourceBuilder, "2018",
				CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, 0, 3800);
		return lstResult;
	}
	
	
	
	

}
