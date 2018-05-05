//package com.kers.stock.storedata;
//
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.http.client.ClientProtocolException;
//import org.elasticsearch.common.collect.Lists;
//import org.elasticsearch.common.collect.Maps;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//
//import io.searchbox.client.JestClient;
//import io.searchbox.client.JestResult;
//import io.searchbox.core.Search;
//
//import com.kers.esmodel.BaseCommonConfig;
//import com.kers.esmodel.UtilEs;
//import com.kers.stock.Controller.SchedulingConfig;
//import com.kers.stock.bean.RzRqBean;
//import com.kers.stock.bean.StockBaseInfo;
//import com.kers.stock.bean.StockOptionalInfo;
//import com.kers.stock.bean.StockRealBean;
//import com.kers.stock.strage.SelGetStock;
//import com.kers.stock.utils.TimeUtils;
//import com.kers.stock.vo.StockBasePageInfo;
//
///**
// * 
// * 
// * 获取指标加入自选的功能
// * 
// */
//public class ZxzbHand {
//
//	static JestClient jestClient = BaseCommonConfig.clientConfig();
//
//	public static void main(String[] args) throws Exception {
//		zbzx();
//	}
//
//	public static void zbzx() throws Exception {
//		// 先删除指标数据
//		StockOptionalSet.delzhi(CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL, jestClient);
//		// 然后新增指标数据
//		Map<String, StockOptionalInfo> map = Maps.newHashMap();
//		List<StockOptionalInfo> list = StockOptionalSet.getList(CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL, null,
//				10000);
//		for (StockOptionalInfo stockOptionalInfo : list) {
//			map.put(stockOptionalInfo.getStockCode(), stockOptionalInfo);
//		}
//		getBreakPointDatas(map);
//	}
//
//	/**
//	 * 获取指标列表
//	 * 
//	 * @throws Exception
//	 */
//	@SuppressWarnings("all")
//	public static void getBreakPointDatas(final Map<String, StockOptionalInfo> map) throws Exception {
//		String d = TimeUtils.getStockDate();
//
//	//	if (TimeUtils.tradeTime()) {
//			StockBasePageInfo page = new StockBasePageInfo();
//			final DecimalFormat df = new DecimalFormat("#.00");
//			BoolQueryBuilder query = QueryBuilders.boolQuery();
//			query.must(QueryBuilders.termQuery("szxType", "1"));
//			query.must(QueryBuilders.termQuery("date", d));
//			
//			List<StockBaseInfo> list = (List<StockBaseInfo>) SelGetStock.getfocDaysLstResult(query, page).get("items");
//			
//			BoolQueryBuilder query2 = QueryBuilders.boolQuery();
//			query2.must(QueryBuilders.termQuery("lsImp", "1"));
//			//query2.must(QueryBuilders.termQuery("date", d));
//			list.addAll( (List<StockBaseInfo>) SelGetStock.getfocDaysLstResult(query2, page).get("items"));
//			
//			for (final StockBaseInfo stockBaseInfo : list) {
//				SchedulingConfig.executorServiceLocal.execute(new Thread() {
//					@Override
//					public void run() {
//						List<StockOptionalInfo> addList = Lists.newArrayList();
//						if (null != map.get(stockBaseInfo.getStockCode())) {
//							StockOptionalInfo info = map.get(stockBaseInfo.getStockCode());
//							info.setJrzblt(1);
//							addList.add(info);
//						} else {
//							StockOptionalInfo info = new StockOptionalInfo();
//							StockRealBean bean;
//							try {
//								bean = StoreRealSet.getBeanByCode(stockBaseInfo.getStockCode());
//								if (null != bean) {
//									info.setStockCode(stockBaseInfo.getStockCode());
//									info.setStockName(bean.getName());
//									info.setOldPrice(bean.getPrice());
//									info.setPercent(Double.parseDouble(df.format(
//											(bean.getPrice() - bean.getYestclose()) / bean.getYestclose() * 100)));
//									info.setPrice(bean.getPrice());
//									info.setJrzblt(1);
//									info.setAddType(1);
//									addList.add(info);
//								}
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//
//						}
//						try {
//							StockOptionalSet.insBatchEs(addList, jestClient,
//									CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});
//
//		//	}
//		}
//	}
//	
//	public  static Map<String,StockOptionalInfo>   mapsNdzData=Maps.newConcurrentMap();
//	 {
//		 try {
//			 mapsNdzData=getAllNdateZhiData();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		 
//	 }
//	public  static Map<String,StockOptionalInfo> getAllNdateZhiData() throws Exception{
//		StockBasePageInfo page = new StockBasePageInfo();
//		BoolQueryBuilder query2 = QueryBuilders.boolQuery();
//		query2.must(QueryBuilders.termQuery("lsImp", "1"));
//		query2.must(QueryBuilders.termQuery("date",  TimeUtils.getStockDate()));
//		Map<String,StockOptionalInfo> mapSource = Maps.newConcurrentMap();
//		try {
//			
//		List<StockOptionalInfo> lstSource=		(List<StockOptionalInfo>)SelGetStock.getfocDaysLstResult(query2, page).get("items");
//		for(StockOptionalInfo  info:lstSource){
//			mapSource.put(info.getStockCode(), info);
//		}
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//		return mapSource;
//		
//	 
//		
//	}
//}
