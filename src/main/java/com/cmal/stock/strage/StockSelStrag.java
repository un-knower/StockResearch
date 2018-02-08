package com.cmal.stock.strage;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmall.stock.bean.EastReportBean;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockDetailInfoBean;
import com.cmall.stock.utils.TimeUtils;
import com.kers.esmodel.SelEsRelt;

public class StockSelStrag {
	// 财报差
	public final static String STRA_TYPE_BADEARNING = "111";
	// 总市值 基本面
	public final static String STRA_TYPE_BADTOTAL = "009";

	public final static String STRA_TYPE_DEF = "003";

	public final static String execStockDefined = "002323,002925,601828,001965,300433"; // 次新股
	public final static String excStockBchipStock = "600010,6600023,00853,600025,600900,300072,600011,601018,000166,600061,601991,601985,000617,601919,601618,600795,600297,601669,601238,600688,601186,000938,601727,600663,601901,601006,600547,600406,002252,600893,601108,601808,601989,300059,002044,601878,002024,601857,300104,600028,002010,300015";
	public final static String excGrowUpStock = ",600383,600233,300026,000718,002818,600233,601689,600093,000892,000987,300267,600420,000728,002108,000021,600312,600307,002501,002818,600266,600787,600577,600621,600502,603319,601688,600711,600466,002563,600926,000937,002608,000750,002662,002247,600079,600153,600393,601128,600240,000060,000983,600060,600649,600970,601117,601555,603323,002092,601555,603323,601021,000783,600885,002440,600598,600066,600477,600109,600835,600823,600908,000423,000686,000761,600649,000581,600068,600859,000090,000990,600299,000402,600525,601019,002354,000550,601000,600633,002217,600409,000921,002048,600258,603444,002271,600498,002195,300182,000559,000012,600376,002839,000883,000539,601179,000826,000999,600260,600236,600236,600415,600827,002065,600522,601326,000685,600269,600400,601811,601801,601139,000598,000778,000709,000902,600528,000587,000541,600873,002007,601158,600674,600271,600611,600804,600219,600160,002203,600739,600373,600012,002372,002221,603113,002434,600705,600535,600350,000623,002091,002074,002051,600978,002002,601333,000959,000027,600231,000933,002241,000887,600699,000723,000525,600612,000501,002701,600642,600483,600886,601107,002831,600548,600377,600056,002468,600390,601866,601872,000951,300144,601966,600161,000157,600338,300156,600998,600618,000666,600026,603369,002477,601588,000059,600704,000591,600511,600170,603858,600820,600089,601098,601928,603766,000429,002242,000656,600201,601877,002624,600332,600717,000869,000028,002223";//
	// 白名单股票   
	public final static String whStock = "002001,002202,0000001,1399001";

	/**
	 * 选优质股 蓝筹股
	 */
	public static List<StockBaseInfo> queryBchipStock() {

		String date = "2018-1-24";
		String zsz = "55000000000";
		// 排除的股

		SelEsRelt<StockBaseInfo> selBaseInfo = new SelEsRelt<StockBaseInfo>(new StockBaseInfo());
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.termQuery("date", date));
		query.must(QueryBuilders.rangeQuery("zsz").gt(zsz));// termQuery("date",
															// date));
		query.must(QueryBuilders.rangeQuery("pe").gt(0));
		query.mustNot(QueryBuilders.inQuery("stockCode",
				(execStockDefined + "," + excStockBchipStock + "," + excGrowUpStock).split(",")));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
		List<StockBaseInfo> lstResult = selBaseInfo.getResultFromQuery(searchSourceBuilder, "2018",
				CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, 0, 3000);
		return lstResult;
	}

	/**
	 * 绩优成长股
	 */

	public static List<StockBaseInfo> queryGrowUpStock() {

		String date = "2018-1-24";
		String zsz = "55000000000";
		// 排除的股

		SelEsRelt<StockBaseInfo> selBaseInfo = new SelEsRelt<StockBaseInfo>(new StockBaseInfo());
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.termQuery("date", date));
		query.must(QueryBuilders.rangeQuery("zsz").gte("12000000000").lte(zsz));// termQuery("date",
		// date));
		query.must(QueryBuilders.rangeQuery("pe").gt(0).lte("43"));
		query.mustNot(QueryBuilders.inQuery("stockCode",
				(execStockDefined + "," + excStockBchipStock + "," + excGrowUpStock).split(",")));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
		List<StockBaseInfo> lstResult = selBaseInfo.getResultFromQuery(searchSourceBuilder, "2018",
				CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, 0, 3000);
		List<StockBaseInfo> lstResRet = Lists.newArrayList();
		for (StockBaseInfo bean : lstResult) {
			// 如果总市值小于2000亿 pe必须 <30
			if (new BigDecimal(bean.getZsz()).compareTo(new BigDecimal("20000000000")) < 0) {
				if (bean.getPe() <= 20)
					lstResRet.add(bean);
			} else {
				// 新和成
				if (bean.getPe() <= 32)
					lstResRet.add(bean);
			}
		}
		return lstResRet;
	}

	/**
	 * 
	 * @description
	 * 
	 * 				财报有良好表现的股票
	 * @return
	 * @Exception
	 */
	public static List<EastReportBean> queryEarningsStock() {
		// String xjlr = "160000000";
		// String excStock =
		// "";//"000629,002427,002075,002398,002484,002859,002716,002340,600545,002356,000975,000988,002118,002775,002334,603588,002753,002374,002649,000545,002088,002370,603108,002116,002497,002545,002515,600093,002496,002247,002083,002002,002434,002323,002439,002024,000058,600971,002087";
		String execRepStock = ",000780,300146,300274,002536,603886,600444,002404,603018,300121,002327,000042,600491,600736,601216,603518,002448,002564,002283,002564,002071,002454,000683,002152,000926,000011";
		SelEsRelt<EastReportBean> selBaseInfo = new SelEsRelt<EastReportBean>(new EastReportBean());
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		// query.must(QueryBuilders.rangeQuery("xjlr").gte(xjlr));
		// query.must(QueryBuilders.rangeQuery("npe").gte(0).lte("60"));
		// query.must(QueryBuilders.rangeQuery("jlr_ycb").gte(1.2));
		// query.must(QueryBuilders.rangeQuery("jlr_tbzz_xjd").gt(6));
		query.mustNot(QueryBuilders.inQuery("stockCode",
				(execStockDefined + "," + excStockBchipStock + "," + excGrowUpStock + execRepStock).split(",")));
		// query.mustNot(QueryBuilders.prefixQuery("stockCode", "3"));
		query.should(QueryBuilders.rangeQuery("pe").gt("0").lte("25"));
		query.should(QueryBuilders.rangeQuery("npe").gt("0").lte("25"));
		// query.should(QueryBuilders.rangeQuery("npe").gt("0").lte("15"));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
		List<EastReportBean> lstResult = selBaseInfo.getResultFromQuery(searchSourceBuilder, "2017-09-30",
				CommonBaseStockInfo.ES_INDEX_STOCK_STOREREPORT, 0, 38000);
		List<EastReportBean> lstReRet = Lists.newArrayList();
		for (EastReportBean bean : lstResult) {

			if (bean.getJlr_ycb() >= 0 && bean.getJlr_tbzz_xjd() > 0
					|| (bean.getJlr_ycb() > 0 && bean.getJlr_tbzz_xjd() >= 0)) {

				if (bean.getPe() > 0 && bean.getPe() <= 22 || (bean.getNpe() > 0 && bean.getNpe() <= 22)) {

					if (!((bean.getJdzzl_before() > bean.getJdzzl() && bean.getJdzzl() > bean.getJlr_ycb()
							&& bean.getPe() < bean.getNpe()))) {
						if (!(bean.getJdzzl() < 1.5 && bean.getJlr_ycb() < 1.5)) {
							if (bean.getNpe() > 15) {
								if (bean.getJlr_ycb() >= 2 || (bean.getJlr_tbzz_xjd() >= 50&&bean.getJlr_ycb()>1.25))
									lstReRet.add(bean);
							} else {
								lstReRet.add(bean);
							}

						}

						// if(bean.getNpe()>15){
						// lstReRet.add(bean);
						// }
					}

				}

			}
		}

		return lstReRet;

	}
	// 黑名单
	// 低市盈率

	public static Map<String, String> blckLstOfStock() {
		Map<String, String> mapsInfo = Maps.newConcurrentMap();

		SelEsRelt<EastReportBean> selBaseInfo = new SelEsRelt<EastReportBean>(new EastReportBean());
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.should(QueryBuilders.rangeQuery("jlr_tbzz_xjd").lt(0));

		query.should(QueryBuilders.rangeQuery("jlr_ycd").lt(0));
		query.should(QueryBuilders.rangeQuery("pe").gte(100));
		query.should(QueryBuilders.rangeQuery("npe").gte(100));
		query.should(QueryBuilders.rangeQuery("xjlr").lt(0));
		query.should(QueryBuilders.rangeQuery("jlr").lt(0));
		query.should(QueryBuilders.queryString("ST").field("stockName"));
		// query.should(QueryBuilders.queryString("ST").field("stockName"));
		// // query.mustNot(QueryBuilders.inQuery("stockCode",
		// // excStock.split(",")));
		// query.should(QueryBuilders.prefixQuery("stockCode", "3"));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
		List<EastReportBean> lstResult = selBaseInfo.getResultFromQuery(searchSourceBuilder, "2017-09-30",
				CommonBaseStockInfo.ES_INDEX_STOCK_STOREREPORT, 0, 3800);
		// System.out.println(lstResult.size());
		// System.out.println(lstResult);
		for (EastReportBean bean : lstResult) {
			// if(!((bean.getJlr_tbzz_xjd()<0&&bean.getXjlr()>0)||(bean.getJlr()<0&&bean.getJlr_ycb()>0&&bean.getNpe()<=80)))
			// mapsInfo.put(bean.getStockCode(), STRA_TYPE_BADEARNING);

			mapsInfo.put(bean.getStockCode(), STRA_TYPE_BADEARNING);

			// if(bean.getStockCode().equals("000950")){
			// System.out.println(bean.getPe()+" "+bean.getNpe() );
			// }

			if (!(bean.getStockName().contains("*ST") || bean.getNpe() > 200 || bean.getPe() > 1000)) {
				if ((bean.getPe() > 0 && bean.getNpe() > 0 && bean.getNpe() < 60)) {
					if (bean.getPe() > 0) {
						if (bean.getPe() < 200 && bean.getNpe() < 50)
							mapsInfo.remove(bean.getStockCode());
					}
				}

				if (bean.getXjlr() > 0) {
					if (bean.getNpe() > 0 && bean.getNpe() < 80)
						mapsInfo.remove(bean.getStockCode());
				} // new BigDecimal(bean.getXjlr()).compareTo(new
					// BigDecimal("500000000"))>0)

				if (new BigDecimal(bean.getXjlr()).compareTo(new BigDecimal("300000000")) > 0) {
					if (bean.getPe() > 0 || bean.getNpe() > 0)
						mapsInfo.remove(bean.getStockCode());
				}

				if (new BigDecimal(bean.getXjlr()).compareTo(new BigDecimal("100000000")) > 0
						&& (bean.getNpe() < bean.getPe()) && (bean.getNpe() > 0 && bean.getNpe() < 200)
						&& bean.getPe() < 500)
					mapsInfo.remove(bean.getStockCode());

			}

			// > 0)
			// if (bean.getJlr_tbzz_xjd() < 0 && bean.getXjlr() > 0 &&
			// (bean.getJdzzl() > 0 || bean.getJlr_ycb() > 0)
			// && (bean.getPe() < 60 || bean.getNpe() < 60) && (bean.getNpe() >
			// 0 && bean.getNpe() < 60)&&bean.getPe()>0) {
			//
			// if (bean.getPe() > 60) {
			// if (bean.getNpe() < 40)
			// mapsInfo.remove(bean.getStockCode());
			//
			// } else
			//
			// mapsInfo.remove(bean.getStockCode());
			// // mapsInfo.put(bean.getStockCode(), STRA_TYPE_BADEARNING);
			// }
			// if((bean.getXjlr()>0&&(bean.getNpe()>0&&bean.getNpe()<=60))||new
			// BigDecimal(bean.getXjlr()).compareTo(new BigDecimal("500000000"))
			// > 0)
			// mapsInfo.remove(bean.getStockCode());
		}

		// for (EastReportBean bean : lstResult) {
		// //
		// if(!((bean.getJlr_tbzz_xjd()<0&&bean.getXjlr()>0)||(bean.getJlr()<0&&bean.getJlr_ycb()>0&&bean.getNpe()<=80)))
		// mapsInfo.put(bean.getStockCode(), STRA_TYPE_BADEARNING);
		// }

		// Map<String, String> mapsInfoRet = Maps.newConcurrentMap();

		//
		// Map<String, StockBaseInfo> mapsStock =
		// QueryComLstData.getStockBaseInfo();
		//
		// for (String key : mapsStock.keySet()) {
		// StockBaseInfo bean = mapsStock.get(key);
		// if (new BigDecimal(bean.getZsz()).compareTo(new
		// BigDecimal("12000000000")) < 0 || bean.getPe() > 400
		// ) {//|| bean.getStockName().contains("ST") ||
		// bean.getStockName().contains("*ST")
		// mapsInfo.put(bean.getStockCode(), STRA_TYPE_BADTOTAL);
		// }
		// }
		//
		// for (EastReportBean bean : queryEarningsStock()) {
		// if (mapsInfo.get(bean.getStockCode()) != null)
		// mapsInfo.remove(bean.getStockCode());
		// }
		// for (StockBaseInfo bean : queryBchipStock()) {
		// if (mapsInfo.get(bean.getStockCode()) != null)
		// mapsInfo.remove(bean.getStockCode());
		// }
		//
		// for (StockBaseInfo bean : queryGrowUpStock()) {
		// if (mapsInfo.get(bean.getStockCode()) != null)
		// mapsInfo.remove(bean.getStockCode());
		// }

		mapsInfo.put("002346", STRA_TYPE_DEF);// 特殊之前接触过的坑股
		return mapsInfo;
	}

	// 如果黑名单跟优选发生冲突以优选为主
	// 临时黑名单 如解禁 设置黑名单时间

	// 持续长时间上涨 财报优质股 填权 如沧州大化 西水股份 新城控股 等

	public static boolean getYZEarningStrag1(StockBaseInfo baseInfo) {

		if (baseInfo.getMacdNum() > 10 && baseInfo.getMinLowRises10() >= -5 && baseInfo.getMinRises10() > 3) {

			// 策略1 持续长时间上涨 财报优质股 填权 如沧州大化 西水股份 新城控股 等 《取中段》
			if (baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5() && baseInfo.getUpSumRises5() > 0
					&& baseInfo.getUpSumRises10() > 10 && baseInfo.getUpSumRises5() > 2
					&& (baseInfo.getUpSumRises5() > 6 || baseInfo.getUpSumRises10() > 10)
					&& (baseInfo.getUp5() > 2 || baseInfo.getUp10() > 5))
				if (!(baseInfo.getUpSumRises10() > 30 && (baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5()))
						|| (baseInfo.getUpRises() + baseInfo.getRises() >= 19)) // 急涨急跌排除
					// if(baseInfo.getRises()-baseInfo.getUpSumRises10()>0)
					// //如果当日涨幅超过前五天涨幅 排除
					return true;

		}

		return false;

	}

	public static boolean getYZEarningStrag2(StockBaseInfo baseInfo) {

		// 策略1 持续长时间上涨 财报优质股 填权 如沧州大化 西水股份 新城控股 等 《取中段》
		if (baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5() && baseInfo.getUpSumRises5() > 0
				&& baseInfo.getUpSumRises10() > 10 && baseInfo.getUpSumRises5() > 2
				&& (baseInfo.getUpSumRises5() > 6 || baseInfo.getUpSumRises10() > 10)
				&& (baseInfo.getUp5() > 2 || baseInfo.getUp10() > 5))
			if (!(baseInfo.getUpSumRises10() > 30 && (baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5()))) // 急涨急跌排除
				// if(baseInfo.getRises()-baseInfo.getUpSumRises10()>0)
				// //如果当日涨幅超过前五天涨幅 排除
				// lstResult.add(baseInfo);

				if ((baseInfo.getUp5() > 2 || baseInfo.getUp10() > 5)
						&& baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5()) { // 5/10天内上涨次数;十日累计涨幅大于5日累计涨幅
					// 五天涨幅>5或 10天涨幅>6 两天内跌幅不能大于五日涨幅
					if ((baseInfo.getUpSumRises10() >= 8 || baseInfo.getUpSumRises5() >= 6)
							&& ((baseInfo.getRises() + baseInfo.getUpRises()) + baseInfo.getUpSumRises5() > 0))
						if (!(baseInfo.getUpSumRises10() > 30
								&& (baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5()))) // 急涨急跌排除
							if (baseInfo.getClose() > baseInfo.getMa5())
								if (baseInfo.getJ() < 100)
									return true;
					// if(baseInfo.getRises()-baseInfo.getUpSumRises5()<1)
				}

		return false;

	}

	public static Map<String, String> getAllChkStock() {
		Map<String, String> mapSource = Maps.newConcurrentMap();
		for (EastReportBean bean : queryEarningsStock()) {
			mapSource.put(bean.getStockCode(), "queryEarningsStock");
		}
		 for (StockBaseInfo bean : queryBchipStock()) {
		 mapSource.put(bean.getStockCode(), "queryBchipStock");
		 }
		 for (StockBaseInfo bean : queryGrowUpStock()) {
		 mapSource.put(bean.getStockCode(), "queryGrowUpStock");
		 }
		 
		 mapSource.remove(getSubnewStock(-60).keySet());
		// bug1 遗漏阳光城 第四季度 财报增长强劲
		mapSource.put(whStock, "whStock");
		return mapSource;

	}
	
	//次新股  对于次新股的定义  上市时间<6个月的股票
	
	public  static Map<String, StockDetailInfoBean>  getSubnewStock(int  days){
		SelEsRelt<StockDetailInfoBean>  stck =  new SelEsRelt<StockDetailInfoBean>(new StockDetailInfoBean());
		String startTime = 		TimeUtils.toString(TimeUtils.addDay(new Date(), days), "yyyyMMdd");
		
		
		BoolQueryBuilder queryss = QueryBuilders.boolQuery();

		//queryss.must(QueryBuilders.rangeQuery("timeToMarket").gt(startTime));//("date", TimeUtils.getDate("2018-02-02")));// TimeUtils.DEFAULT_DATEYMD_FORMAT)));//
																						// "2018-01-19"));
		//queryss.should(QueryBuilders.termQuery("timeToMarket", "0"));
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryss);
		List<StockDetailInfoBean> lstSource=stck.getResultFromQuery(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_DETAILINFO,  CommonBaseStockInfo.ES_INDEX_STOCK_DETAILINFO, 1, 4000);
		
		Map<String, StockDetailInfoBean>  mapSource = Maps.newConcurrentMap();
		for(StockDetailInfoBean  bean:lstSource){
			if(null!=bean.getTimeToMarket()&&!bean.getTimeToMarket().equals("0") ){
				long    cdays=TimeUtils.countDays(TimeUtils.toDate(bean.getTimeToMarket(), "yyyyMMdd"),new Date());
				  if(cdays<Math.abs(days))
				mapSource.put(bean.getStockCode(), bean);
			}
			  
		}
		return mapSource;
	}
	
	

	public static void main(String[] args) {
		System.out.println(getSubnewStock(-60));

//		System.out.println(getAllChkStock());

		// List<StockBaseInfo> beans= queryBchipStock();
		//
		//
		// List<EastReportBean> lstResult=queryEarningsStock();
		// Map<String,EastReportBean> mapsInfo = Maps.newConcurrentMap();
		// for(EastReportBean bean:lstResult){
		// mapsInfo.put(bean.getStockCode(), bean);
		// }
		//
		// for(StockBaseInfo bean:beans){
		// if(mapsInfo.get(bean.getStockCode())!=null){
		// System.out.println(bean);
		// }
		//
		// }

	}
}
