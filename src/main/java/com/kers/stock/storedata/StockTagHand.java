package com.kers.stock.storedata;

import java.util.List;

import com.kers.esmodel.QueryComLstData;
import com.kers.esmodel.SelEsRelt;
import com.kers.stock.bean.StockTag;
import com.kers.stock.bean.jyfx.StockJyfx;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
/**
 * http://data.eastmoney.com/gstc/
 *       
 * ClassName：StockTagHand    
 * Description：    
 * author ：admin    
 * date ：2018年2月3日 上午8:56:20    
 * Modified   person：admin    
 * Modified date：2018年2月3日 上午8:56:20    
 * Modify remarks：    
 * @version     V1.0
 *
 */
public class StockTagHand {
	
	public static void main(String[] args) {
		//1.主营产品及概念  大宗商品类股票
		//SelEsRelt<StockJyfx>  selInfo = new SelEsRelt<StockJyfx>(new StockJyfx());
	//selInfo.getResultFromQuery(searchSourceBuilder, type, index, from, size);
	}

	
	
	public static void insBatchEs(List<StockTag> list, JestClient jestClient, String indexIns) throws Exception {

		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockTag bean : list) {
			i++;
			// System.out.println(bean.getUnionId());
			String id = bean.getStockCode() + bean.getTagName();
			Index index = new Index.Builder(bean).index(indexIns).type("stockTag")
					.id(id).build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
		// jestClient.shutdownClient();
	}
}
