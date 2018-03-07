package com.kers.stock.storedata;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.stock.bean.RzRqBean;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockOptionalInfo;
import com.kers.stock.bean.StockRealBean;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.utils.TimeUtils;
import com.kers.stock.vo.StockBasePageInfo;

/**
 * 
 * 
 * 获取指标加入自选的功能
 * 
 */
public class ZxzbHand {
	
	static JestClient jestClient = BaseCommonConfig.clientConfig();
	
	public static void main(String[] args) throws Exception {
		zbzx();
	}
	
	public static void zbzx() throws Exception{
		//先删除指标数据
				StockOptionalSet.delzhi(CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL,jestClient);
				//然后新增指标数据
				Map<String, StockOptionalInfo> map = Maps.newHashMap();
				List<StockOptionalInfo> list = StockOptionalSet.getList(CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL,null);
				for (StockOptionalInfo stockOptionalInfo : list) {
					map.put(stockOptionalInfo.getStockCode(), stockOptionalInfo);
				}
				getBreakPointDatas(map);
	}
	
	/**
	 * 获取指标列表
	 * @throws Exception
	 */
	public static void getBreakPointDatas(Map<String, StockOptionalInfo> map ) throws Exception{
		String d = TimeUtils.getStockDate();
		System.out.println("获取"+d);
		StockBasePageInfo page = new StockBasePageInfo();
		DecimalFormat df = new DecimalFormat("#.00");
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.termQuery("szxType","1"));
		query.must(QueryBuilders.termQuery("date",d));
		List<StockBaseInfo> list = (List<StockBaseInfo>) SelGetStock.getfocDaysLstResult(query, page).get("items");
		List<StockOptionalInfo> addList = Lists.newArrayList();
		for (StockBaseInfo stockBaseInfo : list) {
			if(null != map.get(stockBaseInfo.getStockCode())){
				StockOptionalInfo info = map.get(stockBaseInfo.getStockCode());
				info.setJrzblt(1);
				addList.add(info);
			}else{
				StockOptionalInfo info = new StockOptionalInfo();
				StockRealBean bean = StoreRealSet.getBeanByCode(stockBaseInfo.getStockCode());
				info.setStockCode(stockBaseInfo.getStockCode());
				info.setStockName(bean.getName());
				info.setOldPrice(bean.getPrice());
				info.setPercent(Double.parseDouble(df.format((bean.getPrice()-bean.getYestclose()) /bean.getYestclose() * 100)));
				info.setPrice(bean.getPrice());
				info.setJrzblt(1);
				info.setAddType(1);
				addList.add(info);
			}
		}
		StockOptionalSet.insBatchEs(addList, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL);
	}
}
