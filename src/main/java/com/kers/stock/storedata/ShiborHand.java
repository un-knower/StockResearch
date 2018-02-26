package com.kers.stock.storedata;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kers.httpmodel.BaseConnClient;

/**
 * 
 * @author chenshenlin
 * 基本指标
 * http://www.chinamoney.com.cn/fe/static/html/column/basecurve/benchmarks/shibor/latestShibor.html
 *
 */
public class ShiborHand {
	
	public static void main(String[] args) {
		String con;
		try {
			con = BaseConnClient.baseGetReq("http://www.chinamoney.com.cn/fe/static/html/column/basecurve/benchmarks/shibor/latestShibor.html");
			Document document = Jsoup.parse(con);
			Elements tables = document.select("table");
			Elements trs = tables.get(0).select("tr");
			String html = "";
			for (int i = 4; i < 7; i++) {
				html = html + "<tr>";
				Elements tds = trs.get(i).select("td");
				html = html + "<td>" + tds.get(0).select("a").get(0).html() + "</td>";
				html = html + "<td>" + tds.get(1).html() + "</td>";
				html = html + "<td>" + tds.get(2).select("span").get(0).html() + "</td>";
				html = html + "</tr>";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
}
