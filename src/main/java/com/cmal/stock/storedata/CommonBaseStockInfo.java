package com.cmal.stock.storedata;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;

import com.cmal.stock.strage.FilePath;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;

public class CommonBaseStockInfo {
	
	public static List<String> getAllAStockInfo() throws IOException {
		List<String> filePath = FileUtils.readLines(new File(FilePath.astockfilePath));
		List<String> lstCode = Lists.newArrayList();
		for (final String s : filePath) {
			String code = s.split(",")[0];
			if(!code.equals("code"))
			lstCode.add(code);
		}
		return lstCode;

	}
	static ExecutorService executorServiceLocal = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(30));


}
