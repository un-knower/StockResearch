package com.kers.stock.storedata;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.SelEsRelt;
import com.kers.httpmodel.BaseConnClient;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockTag;
import com.kers.stock.bean.jyfx.JyfxInfo;
import com.kers.stock.utils.FilePath;
import com.tuchaoshi.base.utils.StringUtil;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

/**
 * http://data.eastmoney.com/gstc/
 * 
 * ClassName：StockTagHand Description： author ：admin date ：2018年2月3日 上午8:56:20
 * Modified person：admin Modified date：2018年2月3日 上午8:56:20 Modify remarks：
 * 
 * @version V1.0
 *
 */
public class StockTagHand {

	public void fetchContentFromNet() throws ClientProtocolException, IOException {
		String gnUrl = "http://stockapp.finance.qq.com/mstats/?pgv_ref=fi_quote_navi_bar#mod=list&id=bd021247&module=SS&type=pt021247";
		String content = BaseConnClient.baseGetReq(gnUrl);

		Element element = Jsoup.parse(content).getElementById("breadnavi");
		Elements eleTag = element.getElementsByTag("a");

		int i = 1;
		while (i <= eleTag.size() - 2) {

			for (Element eTagBean : eleTag) {
				try {
					String href = eTagBean.attr("id").toString().replace("b-a-bd", "pt");

					String filePath = FilePath.stockTagFilePath + "/" + href + ".txt";
					if (StringUtils.isEmpty(href) || href.equals("pt_cpt"))
						continue;
					if (new File(filePath).exists()
							&& StringUtils.isNotBlank(FileUtils.readFileToString(new File(filePath))))
						continue;

					String text = eTagBean.text();
					String partUrl = "http://stock.gtimg.cn/data/index.php?appn=rank&t=" + href
							+ "/chr&p=1&o=0&l=1000&v=list_data";
					System.out.println(partUrl);
					WebDriver driver = new HtmlUnitDriver(false);
					driver.get(partUrl);

					String contentJsp = driver.findElement(By.tagName("body")).getText();
					System.out.println(contentJsp);
					FileUtils.write(new File(filePath), contentJsp);
					// int index = contentJsp.indexOf("data:'")+6;
					// String conText=
					// contentJsp.substring(index).replace("'};", "");
					// System.out.println(conText);

					i++;

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveLstInfo() throws Exception {
		String gnUrl = "http://stockapp.finance.qq.com/mstats/?pgv_ref=fi_quote_navi_bar#mod=list&id=bd021247&module=SS&type=pt021247";
		String content = BaseConnClient.baseGetReq(gnUrl);

		Element element = Jsoup.parse(content).getElementById("breadnavi");
		Elements eleTag = element.getElementsByTag("a");
		List<StockTag> lstTag = Lists.newArrayList();
		for (Element eTagBean : eleTag) {
			String href = eTagBean.attr("id").toString().replace("b-a-bd", "pt");
			if (StringUtils.isEmpty(href) || href.equals("pt_cpt"))
				continue;

			String filePath = FilePath.stockTagFilePath + "/" + href + ".txt";
			String contentTx = FileUtils.readFileToString(new File(filePath));
			int index = contentTx.indexOf("data:'") + 6;
			String codes = contentTx.substring(index).replace("'};", "").replace("sh", "").replace("sz", "").replace(",", "	");
			String text = eTagBean.text();

			StockTag stockTag = new StockTag();
			stockTag.setStockCode(codes);
			stockTag.setTagName(text);
			stockTag.setTagType("gn_tx");// 概念板块
			lstTag.add(stockTag);
		}
		insBatchEs(lstTag, BaseCommonConfig.clientConfig());

	}

	@SuppressWarnings("all")
	public static void main(String[] args) throws Exception {
		 saveLstInfo();
		
		SelEsRelt sel = new SelEsRelt(new  StockBaseInfo());
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.should(QueryBuilders.inQuery("lsImp",  "1"));
		query.should(QueryBuilders.inQuery("lsImp",  "2"));
		query.should(QueryBuilders.inQuery("lsImp",  "22"));
// 
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		List<StockBaseInfo> lstSource = sel.getResultFromQuery(searchSourceBuilder,"", CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, 0, 6000);
//	System.out.println(lstSource);
//	System.out.println(lstSource.size());
//	
	for(StockBaseInfo  stockBaseInfo:lstSource){
		String stockCode = stockBaseInfo.getStockCode();            
//		  System.out.println(stockCode);
		SelEsRelt sel2 = new SelEsRelt(new  StockTag());
		  ssb = new SearchSourceBuilder();
		  query = QueryBuilders.boolQuery();
//		
		query.must(QueryBuilders.queryString(stockCode).field("stockCode"));
// 
		  searchSourceBuilder = ssb.query(query);
		List<StockTag> lstSource2 = sel2.getResultFromQuery(searchSourceBuilder,"", CommonBaseStockInfo.ES_INDEX_STOCK_STOCKTAG, 0, 6000);

		
		//System.out.println(lstSource2);
	
	}
		
	
	}

	public static void insBatchEs(List<StockTag> list, JestClient jestClient) throws Exception {

		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (StockTag bean : list) {
			i++;
			// System.out.println(bean.getUnionId());
			String id = (bean.getTagType() + bean.getTagName()).hashCode() + "";
			Index index = new Index.Builder(bean).index(CommonBaseStockInfo.ES_INDEX_STOCK_STOCKTAG)
					.type(CommonBaseStockInfo.ES_INDEX_STOCK_STOCKTAG).id(id).build();
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
