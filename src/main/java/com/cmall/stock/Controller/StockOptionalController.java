package com.cmall.stock.Controller;

import io.searchbox.client.JestClient;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmall.stock.bean.StockOptionalInfo;
import com.cmall.stock.bean.StockRealBean;
import com.cmall.stock.storedata.CommonBaseStockInfo;
import com.cmall.stock.storedata.StockOptionalSet;
import com.cmall.stock.storedata.StoreRealSet;
import com.cmall.stock.strage.SelGetStock;
import com.cmall.stock.vo.StockBasePageInfo;
import com.kers.esmodel.BaseCommonConfig;

@RestController
public class StockOptionalController extends BaseController{
	
	/**
	 * 加入自选
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/optional/create")
    public Map<String,Object> create(StockOptionalInfo info) throws Exception {
		DecimalFormat df = new DecimalFormat("#.00");
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		StockRealBean bean = StoreRealSet.getBeanByCode(info.getStockCode());
		info.setStockName(bean.getName());
		info.setOldPrice(bean.getPrice());
		info.setPercent(Double.parseDouble(df.format((bean.getPrice()-bean.getYestclose()) /bean.getYestclose() * 100)));
		info.setPrice(bean.getPrice());
		StockOptionalSet.insBatchBean(info, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL);
		Map<String,Object> map = Maps.newHashMap();
		map.put("code", "200");
        return map;
    }
	
	/**
	 * 取消自选
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/optional/delete")
    public Map<String,Object> delete(StockOptionalInfo info) throws Exception {
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		StockOptionalSet.deleteBean(info, jestClient, CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL);
        return null;
    }
	
	@RequestMapping("/optional/getList")
    public Map<String,Object> getList(StockBasePageInfo page) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_STOCK_OPTIONAL,"2018");
    }
}
