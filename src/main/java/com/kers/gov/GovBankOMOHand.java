package com.kers.gov;

import java.util.Date;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.httpmodel.BaseConnClient;
import com.kers.staple.bean.Stap100PPI;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.utils.TimeUtils;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * 
 *      净投放=资金投放量-资金回笼量
 * 市场净投放
 * 天投放量查询
 * http://www.chinamoney.com.cn/fe/Channel/35768
 * 
 * http://www.chinamoney.com.cn/fe/jsp/CN/chinamoney/notice/ticketPutAndBackStatByMonthList.jsp?pagingPage_il_=1&beginMonth=2015-01&endMonth=2018-01&
 * 
 *
 */
public class GovBankOMOHand {

	public static void insBatchEs(List<GovBankOMOBean> list, JestClient jestClient, String indexIns) throws Exception {

		if (list == null || list.isEmpty())
			return;
		int i = 0;
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		for (GovBankOMOBean bean : list) {
			i++;
			// System.out.println(bean.getUnionId());

			String id = bean.getDate() + bean.getCzqx();
			Index index = new Index.Builder(bean).index(indexIns).type(indexIns).id(id).build();// type("walunifolia").build();
			bulkBuilder.addAction(index);
			if (i % 5000 == 0) {
				jestClient.execute(bulkBuilder.build());
				bulkBuilder = new Bulk.Builder();
			}
		}
		jestClient.execute(bulkBuilder.build());
		// jestClient.shutdownClient();
	}
	
	/**
	 * 抓取所有的数据
	 */
	public static void getAllDatas(String stopDate){
		String parUrl = "http://www.chinamoney.com.cn/fe/jsp/CN/chinamoney/notice/ticketHandleMoreList.jsp?pagingPage_il_=";
		List<GovBankOMOBean> lstRes = Lists.newArrayList();
		z:for (int i = 1; i < 113; i++) {
			try {
				String content = BaseConnClient.baseGetReq((parUrl + i));
				Document docuemnt = Jsoup.parse(content);
				Elements trs = docuemnt.select("table").select("tbody").select("tr");

				for (int j = 2; j < trs.size() - 2; j++) {

					Elements elementds = trs.get(j).select("td");
					if (!elementds.isEmpty() && elementds.size() > 1) {
						try {
							GovBankOMOBean bankOMOBean = new GovBankOMOBean();
							String dates = elementds.get(0).text().replace("&nbsp;", "").replace(" ", "");
							if(null !=stopDate && !stopDate.equals("") && dates.equals(stopDate)){
								break z;
							}
							bankOMOBean.setDate(dates);
							bankOMOBean.setDateWeek(TimeUtils.dateToWeek(dates));
							String qx = elementds.get(1).text().replace("&nbsp;", "").replace(" ", "");
							bankOMOBean.setCzqx(Integer.parseInt(qx));
							bankOMOBean.setJyl(
									Double.parseDouble(elementds.get(2).text().replace("&nbsp;", "").replace(" ", "")));
							bankOMOBean.setZbrate(
									Double.parseDouble(elementds.get(3).text().replace("&nbsp;", "").replace(" ", "")));
							int hgtype = elementds.get(4).text().replace("&nbsp;", "").trim().equals("逆回购") ? 0 : 1;
							bankOMOBean.setHgtype(hgtype);
							Date date = TimeUtils.addDay(
									TimeUtils.toDate(bankOMOBean.getDate(), TimeUtils.DEFAULT_DATEYMD_FORMAT),
									bankOMOBean.getCzqx());
							String expDate = TimeUtils.format(date, TimeUtils.DEFAULT_DATEYMD_FORMAT);
							bankOMOBean.setExpDate(expDate);
							bankOMOBean.setExpWeek(TimeUtils.dateToWeek(expDate));
							lstRes.add(bankOMOBean);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		try {
			if(lstRes.size() > 0){
				insBatchEs(lstRes, BaseCommonConfig.clientConfig(), CommonBaseStockInfo.ES_INDEX_GOV_OMO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getBreakPointDatas(){
		String stopDate = "";
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		ssb.sort("date", SortOrder.DESC);
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_GOV_OMO, CommonBaseStockInfo.ES_INDEX_GOV_OMO,
				1, 2);
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		GovBankOMOBean bean = new GovBankOMOBean();
		try {
			JestResult results = jestClient.execute(selResult);
			if(results.isSucceeded()){
				bean = results.getSourceAsObject(GovBankOMOBean.class);
				stopDate = bean.getDate();
				System.out.println(stopDate);
			}
			getAllDatas(stopDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		//一次更新全部
//		getAllDatas("");
		//更新到上次更新
		getBreakPointDatas();
	}

}
