package com.cmal.stock.strage;

import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmal.stock.storedata.StockDetailInfoHand;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.utils.TimeUtils;
import com.google.common.collect.Maps;

public class QueryComLstData {
	 static 	Map<String, StockBaseInfo> mapsInfo2 ;
	static {
		mapsInfo2 = Maps.newConcurrentMap();
		try {
			BoolQueryBuilder queryss = QueryBuilders.boolQuery();

			queryss.must(QueryBuilders.termQuery("date", TimeUtils.getDate("2018-01-22")));// TimeUtils.DEFAULT_DATEYMD_FORMAT)));//
																							// "2018-01-19"));
			List<StockBaseInfo> lstSource = CommonBaseStockInfo.getLstResult(queryss, "2018");

			for (StockBaseInfo bean : lstSource) {
				mapsInfo2.put(bean.getStockCode(), bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public static Map<String, StockDetailInfoBean> mapsInfo;
	

	static {
		try {
			mapsInfo = StockDetailInfoHand.getDetailForMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

		 
	}
	
	public  static Map<String, StockDetailInfoBean>   getDetailInfo(){
		return mapsInfo;
	}

	public  static Map<String, StockBaseInfo> getStockBaseInfo() {
		return mapsInfo2;
//		Map<String, StockBaseInfo> mapsInfo2 = Maps.newConcurrentMap();
//		try {
//			mapsInfo2 = Maps.newConcurrentMap();
//			BoolQueryBuilder queryss = QueryBuilders.boolQuery();
//
//			queryss.must(QueryBuilders.termQuery("date", TimeUtils.getDate("2018-01-22")));// TimeUtils.DEFAULT_DATEYMD_FORMAT)));//
//																							// "2018-01-19"));
//			List<StockBaseInfo> lstSource = CommonBaseStockInfo.getLstResult(queryss, "2018");
//
//			for (StockBaseInfo bean : lstSource) {
//				mapsInfo2.put(bean.getStockCode(), bean);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mapsInfo2;
	}
}
