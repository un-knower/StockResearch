package com.kers.esmodel;

import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.google.common.collect.Maps;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockDetailInfoBean;
import com.kers.stock.bean.StoreTrailer;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.storedata.StockDetailInfoHand;
import com.kers.stock.storedata.StoreTrailerSet;
import com.kers.stock.utils.TimeUtils;

public class QueryComLstData {
	public static Map<String, StockBaseInfo> mapsInfo2;
	public static Map<String, StoreTrailer> mapsTrailer;

	static {
		try {
			mapsTrailer = StoreTrailerSet.getAllTrailerMap(StoreTrailerSet.P_TYPE_2017_12_31);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	static {
		mapsInfo2 = Maps.newConcurrentMap();
		try {
			BoolQueryBuilder queryss = QueryBuilders.boolQuery();

			queryss.must(QueryBuilders.termQuery("date", CommonBaseStockInfo.FIDUCIAL_TIME));// TimeUtils.DEFAULT_DATEYMD_FORMAT)));//
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

	public static Map<String, StockDetailInfoBean> getDetailInfo() {
		return mapsInfo;
	}

	public static Map<String, StoreTrailer> getStoreTrailerMapsInfo() {
		return mapsTrailer;

	}

	public static Map<String, StockBaseInfo> getStockBaseInfo() {
		return mapsInfo2;
		// Map<String, StockBaseInfo> mapsInfo2 = Maps.newConcurrentMap();
		// try {
		// mapsInfo2 = Maps.newConcurrentMap();
		// BoolQueryBuilder queryss = QueryBuilders.boolQuery();
		//
		// queryss.must(QueryBuilders.termQuery("date",
		// TimeUtils.getDate("2018-01-22")));//
		// TimeUtils.DEFAULT_DATEYMD_FORMAT)));//
		// // "2018-01-19"));
		// List<StockBaseInfo> lstSource =
		// CommonBaseStockInfo.getLstResult(queryss, "2018");
		//
		// for (StockBaseInfo bean : lstSource) {
		// mapsInfo2.put(bean.getStockCode(), bean);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return mapsInfo2;
	}
}
