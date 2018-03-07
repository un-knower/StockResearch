package com.kers.stock.strage;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.util.List;

import com.kers.stock.vo.StockBasePageInfo;

public class StrategySet {
	public static void insBatchEs(List<StockBasePageInfo> list, JestClient jestClient, String indexIns) throws Exception {
		if (list == null || list.isEmpty())
			return;
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockBasePageInfo bean : list) {
			i++;
			Index index = new Index.Builder(bean).index(indexIns).type(indexIns)
					.id(bean.getName()+bean.getBq()).build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
	}
}
