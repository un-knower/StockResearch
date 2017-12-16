package com.cmal.stock.storedata;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.kers.httpmodel.BaseConnClient;

public class StoreStockEnReportDetail {

	public static void main(String[] args) throws Exception {

		List<String> lstSource = FileUtils
				.readLines(new File("/opt/workspace/StockResearch/src/main/java/com/cmal/stock/storedata/tmp"));

		for (String t : lstSource) {
			int ln = 6 - t.length();
			if (ln == 1)
				t = "0" + t;
			else if (ln == 2)
				t = "00" + t;
			else if (ln == 3)
				t = "000" + t;
			else if (ln == 4)
				t = "0000" + t;
			else if (ln == 5)
				t = "00000" + t;

			String url = "http://quotes.money.163.com/f10/cwbbzy_" + t + ".html#01c04";
			String content = BaseConnClient.baseGetReq(url);
			Document document = Jsoup.parse(content);

			Elements elementshead = document.getElementsByClass("col_l");
			Elements trshead = elementshead.select("table").select("tr");
			int i;
			
			int  ns1=0;
			int ns2=0;
			for (i = 0; i < trshead.size(); i++) {
				Elements tds = trshead.get(i).select("td");
				for (int j = 0; j < tds.size(); j++) {
					// System.out.println(t);

					try {

						String txt = tds.get(j).text();
//						System.out.println(txt);
						if (txt.contains("经营活动产生的现金流量净额(万元)")) {
							ns1=i-2;
						}
						if (txt.contains("投资活动产生的现金流量净额(万元)")) {
                                     ns2=i-2;
							break;
						}

					
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
    System.out.println(ns1+"\t"+ns2+"\t"+t);
			Elements elements1 = document.getElementsByClass("col_r");
			Elements trs = elements1.select("table").select("tr");
			i = 0;
			for (i = 0; i < trs.size(); i++) {
				Elements tds = trs.get(i).select("td");
				for (int j = 0; j < tds.size(); j++) {
					// System.out.println(t);

					try {

						String txt = tds.get(j).text();

						if (i == ns2 && j == 0) {
							// 投资活动产生的现金流量净额(万元)
							System.out.print(txt + "\t" + t);
						}
						if (i == ns1 && j == 0) {
							// 经营活动产生的现金流量净额(万元)
							System.out.print(txt + "\t");
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
			System.out.println();
		}
	}
}
