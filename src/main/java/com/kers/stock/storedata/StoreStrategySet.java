package com.kers.stock.storedata;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.util.List;

import com.kers.stock.bean.StockStrategyInfo;

public class StoreStrategySet {
	public static void insBatchEs(List<StockStrategyInfo> list, JestClient jestClient, String indexIns) throws Exception {
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockStrategyInfo bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type("2017")
					.id(bean.getStockCode() + bean.getDate()).build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}
}
