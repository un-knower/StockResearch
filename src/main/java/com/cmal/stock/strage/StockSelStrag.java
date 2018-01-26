package com.cmal.stock.strage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockBaseInfo;
import com.kers.esmodel.QueryComLstData;
import com.kers.esmodel.SelEsRelt;

public class StockSelStrag {
	//财报差
           public final  static String  STRA_TYPE_BADEARNING="111";  
           //  总市值 基本面
           public final static  String  STRA_TYPE_BADTOTAL="009";
	/**
	 * 选优质股 蓝筹股
	 */
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
				CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, 0, 3000);
		return lstResult;
	}

	/**
	 * 
	 * @description
	 * 
	 * 财报有良好表现的股票
	 * @return    
	 * @Exception
	 */
	public static List<EastReportBean> queryEarningsStock() {
		String xjlr = "160000000";
		String excStock = "000629,002427,002075,002398,002484,002859,002716,002340,600545,002356,000975,000988,002118,002775,002334,603588,002753,002374,002649,000545,002088,002370,603108,002116,002497,002545,002515,600093,002496,002247,002083,002002,002434,002323,002439,002024,000058,600971,002087";

		SelEsRelt<EastReportBean> selBaseInfo = new SelEsRelt<EastReportBean>(new EastReportBean());
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.rangeQuery("xjlr").gte(xjlr));
		query.must(QueryBuilders.rangeQuery("npe").gte(0).lte("60"));
		query.must(QueryBuilders.rangeQuery("jlr_ycb").gte(1.2));
		query.must(QueryBuilders.rangeQuery("jlr_tbzz_xjd").gt(6));
		query.mustNot(QueryBuilders.inQuery("stockCode", excStock.split(",")));
		query.mustNot(QueryBuilders.prefixQuery("stockCode", "3"));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
		List<EastReportBean> lstResult = selBaseInfo.getResultFromQuery(searchSourceBuilder, "2017-09-30",
				CommonBaseStockInfo.ES_INDEX_STOCK_STOREREPORT, 0, 3000);
		return lstResult;

	}
 //  黑名单  
	//低市盈率
	
	public   static Map<String,String>  blckLstOfStock(){
		Map<String,String>  mapsInfo = Maps.newConcurrentMap();
		
		
		SelEsRelt<EastReportBean> selBaseInfo = new SelEsRelt<EastReportBean>(new EastReportBean());
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.should(QueryBuilders.rangeQuery("jlr_tbzz_xjd").lt(0));
		
		query.should(QueryBuilders.rangeQuery("npe").lt(0).gt(200));
		
		query.should(QueryBuilders.queryString("ST").field("stockName"));
		//query.mustNot(QueryBuilders.inQuery("stockCode", excStock.split(",")));
		query.should(QueryBuilders.prefixQuery("stockCode", "3"));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
		List<EastReportBean> lstResult = selBaseInfo.getResultFromQuery(searchSourceBuilder, "2017-09-30",
				CommonBaseStockInfo.ES_INDEX_STOCK_STOREREPORT, 0, 3000);
		// System.out.println(lstResult.size());
		
		for(EastReportBean bean:lstResult){
			mapsInfo.put(bean.getStockCode(), STRA_TYPE_BADEARNING);
		}
		
		Map<String, StockBaseInfo>    mapsStock=QueryComLstData.getStockBaseInfo();
		
		 for (String key : mapsStock.keySet()) {
			 StockBaseInfo   bean = mapsStock.get(key);
			 if(new BigDecimal(bean.getZsz()).compareTo(new BigDecimal("6000000000"))<0||bean.getPe()>400){
				 mapsInfo.put(bean.getStockCode(),STRA_TYPE_BADTOTAL);
			 }
		    }
		 
		 
		 for(EastReportBean bean:queryEarningsStock()){
			 if(mapsInfo.get(bean.getStockCode())!=null)
				 mapsInfo.remove(bean.getStockCode());
		 }
		 for(StockBaseInfo bean:queryBchipStock()){
			 if(mapsInfo.get(bean.getStockCode())!=null)
				 mapsInfo.remove(bean.getStockCode());
		 }
//		 for (String key : mapsInfo.keySet()) {
//		//	 String code =  mapsInfo.get(key);
//			 StockBaseInfo bean =  QueryComLstData.getStockBaseInfo().get(key);
////			 if(bean!=null)
////			 System.out.println(bean.getStockName()+"\t"+bean.getStockCode()+"\t"+bean.getZsz()+"\t"+bean.getPe());
//			  
//		 }
		return mapsInfo;
	}
	
	 // 如果黑名单跟优选发生冲突以优选为主
	//临时黑名单  如解禁 设置黑名单时间
	
	public static void main(String[] args) {
		
		System.out.println(blckLstOfStock().size());
		       
		// List<StockBaseInfo> beans= queryBchipStock();
		//
		//
		// List<EastReportBean> lstResult=queryEarningsStock();
		// Map<String,EastReportBean> mapsInfo = Maps.newConcurrentMap();
		// for(EastReportBean bean:lstResult){
		// mapsInfo.put(bean.getStockCode(), bean);
		// }
		//
		// for(StockBaseInfo bean:beans){
		// if(mapsInfo.get(bean.getStockCode())!=null){
		// System.out.println(bean);
		// }
		//
		// }
	   
	}
}
