package com.kers.stock.strage;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.collect.Sets;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.QueryComLstData;
import com.kers.esmodel.UtilEs;
import com.kers.staple.bean.Stap100PPI;
import com.kers.stock.bean.EastReportBean;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockDetailInfoBean;
import com.kers.stock.bean.StoreTrailer;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.vo.StockBasePageInfo;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

/**
 * 
 * 
 * ClassName：SelGetStock Description： author ：admin date ：2017年11月11日 下午3:37:33
 * Modified person：admin Modified date：2017年11月11日 下午3:37:33 Modify remarks：
 * 
 * @version V1.0
 * 
 * 
 * 
 *          1.macd连涨天数 2. 5|10天内macd涨的天数
 *
 */
public class SelGetStock {
	
	static final JestClient jestClient = BaseCommonConfig.clientConfig();

	static Map<String, String> mapsInfo = StockSelStrag.blckLstOfStock();
	static Map<String, StockDetailInfoBean> mapsSubnewStock = 	StockSelStrag.getSubnewStock(-200);
	static Map<String, String> mapsSelStock = StockSelStrag.getAllChkStock();

	public static void revtmpMap(Map<String, String> mapsSelStockTmp) {// 临时剔除一些
																		// 走势不好股

		
		  //算法问题
		
		mapsSelStockTmp.remove("600844"); 
		mapsSelStockTmp.remove("002595"); 
		mapsSelStockTmp.remove("600120"); 
		mapsSelStockTmp.remove("000582"); 
		mapsSelStockTmp.remove("002233"); 
		mapsSelStockTmp.remove("600252"); 
		mapsSelStockTmp.remove("002152"); 
		mapsSelStockTmp.remove("002174"); 
		mapsSelStockTmp.remove("600582"); 
		mapsSelStockTmp.remove("002920"); 
		mapsSelStockTmp.remove("603816"); 
		mapsSelStockTmp.remove("300029"); 
		mapsSelStockTmp.remove("600293"); 
		mapsSelStockTmp.remove("000676"); 
		mapsSelStockTmp.remove("000662"); 
		
		
		
		
		
		
		mapsSelStockTmp.remove("600518"); // 曾经财务问题
		// 短时间内会剔除一批走势不好数据
		mapsSelStockTmp.remove("603993");
		mapsSelStockTmp.remove("300146");
		//mapsSelStockTmp.remove("002460");//赣锋锂业	
		//mapsSelStockTmp.remove("002466");// 锂电池   天齐锂业
		mapsSelStockTmp.remove("300274");
		mapsSelStockTmp.remove("000100");//TCL集团	
		mapsSelStockTmp.remove("600050");//中国联通
		//mapsSelStockTmp.remove("600362"); // 江西铜业
		mapsSelStockTmp.remove("002042"); // 市值太小 华孚时尚 127 亿
		mapsSelStockTmp.remove("002404");// 市值太小
		mapsSelStockTmp.remove("000790");// 财报不是特别耀眼
		// mapsSelStockTmp.remove("600449");/// 宁夏建材 600449 水泥板块竞争性不强
		mapsSelStockTmp.remove("000822");/// 000822 山东海化 收益率太低
		mapsSelStockTmp.remove("600681");// 收益率太低
		mapsSelStockTmp.remove("002385");// 收益率太低
	
		mapsSelStockTmp.remove("601992");// 无竞争优势
		mapsSelStockTmp.remove("002558"); // 近期走势差
		mapsSelStockTmp.remove("300676"); // 近期走势差
		mapsSelStockTmp.remove("601390");// 收益率太低
		mapsSelStockTmp.remove("601766");// 收益率太低
		mapsSelStockTmp.remove("600816");// 收益率太低
		mapsSelStockTmp.remove("601881");// 收益率太低 证券太多
		mapsSelStockTmp.remove("600958");// 收益率太低 证券太多
		mapsSelStockTmp.remove("601377");// 收益率太低 证券太多
		mapsSelStockTmp.remove("601633");// 同比收益大幅度下降
		mapsSelStockTmp.remove("002019");// 大热后没效益
		mapsSelStockTmp.remove("000050");// 竞争效益弱
		mapsSelStockTmp.remove("002352");// 顺丰
		mapsSelStockTmp.remove("600919");// 600919 江苏银行 收益低 无竞争优势
		mapsSelStockTmp.remove("601997");// 贵阳银行 收益低 无竞争优势
		mapsSelStockTmp.remove("601998");// 中信银行 收益低 无竞争优势		
		mapsSelStockTmp.remove("600000");// 浦发银行 收益低 无竞争优势
		mapsSelStockTmp.remove("603589");// 口子窖 收益低 无竞争优势
		mapsSelStockTmp.remove("300498");// 走势下行 且净利润下降
		mapsSelStockTmp.remove("601231"); // 环旭电子 同行业无竞争优势
		mapsSelStockTmp.remove("600291"); // 西水股份竞争弱 且一时增长因为财报问题不稳定
		mapsSelStockTmp.remove("300355"); // 收益低 无竞争优势
		mapsSelStockTmp.remove("600596"); // 新安股份 同行业无竞争优势
		mapsSelStockTmp.remove("601336");// 保险 同行业无竞争优势
		mapsSelStockTmp.remove("601336");// 002573 收益不稳定
		mapsSelStockTmp.remove("002408");// 齐翔腾达 收益不稳定 市值低 无竞争优势
		mapsSelStockTmp.remove("002032");// 苏 泊 尔 家电行业 市值低 无竞争优势
		mapsSelStockTmp.remove("600177");// 雅戈尔 收益低

		mapsSelStockTmp.remove("000063");// 短暂移除 前期炒作
		mapsSelStockTmp.remove("002555");// 长期处于震荡下行
		mapsSelStockTmp.remove("002573"); // 动荡
		mapsSelStockTmp.remove("603886"); // 元祖股份 不活跃
		mapsSelStockTmp.remove("002536"); //市值小  成长下滑 收益不稳定
		
		

		mapsSelStockTmp.remove("600444"); // 第四季度政府赔款
		mapsSelStockTmp.remove("600449");// 宁夏建材 相比水泥板块无竞争优势
		mapsSelStockTmp.remove("600881");// 亚泰集团 相比水泥板块无竞争优势
		mapsSelStockTmp.remove("000039");
		
		mapsSelStockTmp.remove("002736");// /证券 相比无太大竞争优势
		mapsSelStockTmp.remove("601788");// 证券 相比无太大竞争优势
		mapsSelStockTmp.remove("600999");// 证券 相比无太大竞争优势
		

		
		mapsSelStockTmp.remove("600566"); // 济川药业 收益率太低
		mapsSelStockTmp.remove("600062"); // 华润双鹤 化学制药 收益率太低 且震荡

		mapsSelStockTmp.remove("002067");// 景兴纸业 002067 短暂移除 相比优势不太明显
		mapsSelStockTmp.remove("000625"); // 财报 走势稳定下滑
		mapsSelStockTmp.remove("601800"); // 整体成下滑趋势 中国交建 相比优势不太明显
		mapsSelStockTmp.remove("601169"); // 北京银行 收益太低
		mapsSelStockTmp.remove("600015"); // 华夏银行 收益太低
		mapsSelStockTmp.remove("601229"); // 上海银行 收益太低 次新
		mapsSelStockTmp.remove("601166"); // 兴业银行 收益太低
		mapsSelStockTmp.remove("601288"); // 农业银行 收益太低 （相比工 建行）
		mapsSelStockTmp.remove("601818"); // 光大银行 收益太低
		mapsSelStockTmp.remove("600023"); //   收益太低
		mapsSelStockTmp.remove("600853");
		
		mapsSelStockTmp.remove("603179");
		mapsSelStockTmp.remove("603612");
		mapsSelStockTmp.remove("600837");
		mapsSelStockTmp.remove("002562");
		mapsSelStockTmp.remove("600686");//财报不突出
		mapsSelStockTmp.remove("600438");//
	
		mapsSelStockTmp.remove("300070");
		mapsSelStockTmp.remove("300306");
		mapsSelStockTmp.remove("000001");//中国平安
		mapsSelStockTmp.remove("601328");//交通银行
		mapsSelStockTmp.remove("600016");//民生银行   对标中国银行
		mapsSelStockTmp.remove("000553");//000553  四季度出现亏损

		//房地产  002147 000981  第四季度增长猛   没查出来 检查算法 
		mapsSelStockTmp.remove("000732"); //  泰禾集团	 炒作过头
		mapsSelStockTmp.remove("000043"); //无竞争优势
		mapsSelStockTmp.remove("000965"); //天保基建	 无太大竞争优势
		mapsSelStockTmp.remove("002244");//房地产中收益率低的
		mapsSelStockTmp.remove("600173");// 卧龙地产 相比之下并无太大优势
		mapsSelStockTmp.remove("600208");// 新湖中宝 暂时排除 相比之下并无太大优势 且下季财报不明
		mapsSelStockTmp.remove("600325");// 房地产 竞争差
		
		
		
		
		//中国国航》南方航空》东方航空
		mapsSelStockTmp.remove("603885");//吉祥航空
		mapsSelStockTmp.remove("600062"); // 南方航空 竞争无太大优势603885

		//晨鸣纸业(219)||山鹰纸业(162)	》太阳纸业(226)》博汇纸业	》景兴纸业	》荣晟环保	  002067
		mapsSelStockTmp.remove("603165");//荣晟环保	  无竞争优势|次新股前期操作过度
		mapsSelStockTmp.remove("600966");//景兴纸业无竞争优势
		
   // 化纤板块      桐昆股份	》= 恒力股份	》新凤鸣	
		mapsSelStockTmp.remove("002493"); //增长不是很明显 市盈率相对较高
		//化工原料	       万华化学	》金禾实业	》康得新	》龙蟒佰利	  （滨化股份	|黑猫股份	）
		mapsSelStockTmp.remove("002648"); //无竞争优势
		mapsSelStockTmp.remove("002068"); //无竞争优势
		//化学制药	
		mapsSelStockTmp.remove("002294"); //无竞争优势
	
		
	       //煤炭      中国神华(3774)	   陕西煤业(736)》兖州煤业(635)      潞安环能(263)》阳泉煤业(136)》平煤股份(119)	》露天煤业(139)
		//601898  ,601666   当板块大热的时候暂时移除关注
		mapsSelStockTmp.remove("601898");// 煤炭 无太大竞争优势
		mapsSelStockTmp.remove("601666"); //平煤股份 煤炭 无太大竞争优势
		
		//mapsSelStockTmp.put("600971", StockSelStrag.whStock);//最近煤炭板块大热加入 恒源煤电	
		//mapsSelStockTmp.remove("002128");// 短暂移除 相比优势不太明显
			// 阳泉煤业  成交量放大 macd稳步上升 kdj刚触百
			// 露天煤业  成交量相对放大  macd 平缓上升   kdj50左右向上
		
		
		
		//钢铁   目前各种指标下来   山东钢铁(第四季度大幅度增长)  杭钢股份  相对较弱
		//  宝钢股份(1640)>>> 鞍钢股份 (385)  				 太钢不锈(227) >三钢闽光(241)>=马钢股份(250)
		// *ST华菱(208)>南钢股份(174)>新钢股份(204)	>山东钢铁(205)	
       //  柳钢股份(140亿)	>韶钢松山(148)>方大特钢(156)>杭钢股份(138)	               安阳钢铁（92）》八一钢铁（82）
	
		// 方大特刚  空中加油   且j向上交叉    || 能买 柳钢就不买杭钢     柳钢即将macd金叉  kdj已经金叉   南刚行情启动（成交量放大 macd二次平行爆发 kdj向上交叉） 新钢 比较舒适的位置
		//     八一  安阳 处于早期  关注   太钢不锈（可入手） 量走势起来  非常值得关注    马钢股份(可入手)
		//  鞍钢股份 好行情（可入手）量走势起来    宝钢股份 创新高
		
		// 002061 值得关注
		//水泥板块    海螺水泥(1382亿)     》华新水泥(168)》上峰水泥(76)  》(同力水泥65)>=万年青(60)
		// 海螺水泥（创新高）  macd  kdj均向上  上峰水泥（成交量前期被放大，近期有望突破新高）  空中加油 kdj向上交叉60方向  华新水泥 macd kdj均向上 指标暂时不如上峰水泥（成交量也不大）

	}

	public static Map<String, Object> getfocDaysLstResult(BoolQueryBuilder query, StockBasePageInfo page)
			throws Exception {
		// 002597 002311 000039
		Map<String, Object> returnMap = Maps.newHashMap();
		//Map<String, String> mapsSelStockTmp = mapsSelStock;
		

	    // revtmpMap(mapsSelStockTmp);
		//query.must(QueryBuilders.inQuery("stockCode", mapsSelStockTmp.keySet()));
		//query.mustNot(QueryBuilders.inQuery("stockCode", mapsSubnewStock.keySet()));//排除次新股

		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);
//		System.out.println(searchSourceBuilder.toString());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, "",
				(page.getPage() - 1) * page.getLimit(), page.getLimit());

		JestResult results = jestClient.execute(selResult);
		List<StockBaseInfo> lstBean = results.getSourceAsObjectList(StockBaseInfo.class);
		List<StockBaseInfo> lstResult = Lists.newArrayList();
		for (StockBaseInfo baseInfo : lstBean) {
			
//			if(baseInfo.getUp2J()>baseInfo.getJ()&&baseInfo.getJ()>0)
			//if(baseInfo.)
				lstResult.add(baseInfo);
		//	if ((baseInfo.getUp10() > 4 || baseInfo.getUp5() >= 2)) {// &&(baseInfo.getUpSumRises5()>0&&baseInfo.getUpSumRises10()>0)){
				// // //最近走势比较好的股

				// //macd 出于下滑状态

//				if ((baseInfo.getUpSumRises5() > 0 || baseInfo.getUpSumRises10() > 0)
//						&& (baseInfo.getMinLowRises5() >= -4.99 && baseInfo.getMinLowRises10() >= -4.99)) {
//					
//					if (baseInfo.getMacdNum() < -3) {
//						if (baseInfo.getMacd() > baseInfo.getUpMacd() && baseInfo.getUpMacd() > baseInfo.getUp2Macd())
//							lstResult.add(baseInfo);
//						if (baseInfo.getStockCode().equals("002016")) {
//							System.out.println("bbb");
//						}
//					} else if (baseInfo.getMacdNum() < 0 && baseInfo.getMacd() > -3) {
//						if ((baseInfo.getUp2Rises() + baseInfo.getUpRises() + baseInfo.getRises()) >= -5)
//
//							lstResult.add(baseInfo);
//
//						if (baseInfo.getStockCode().equals("002016")) {
//							System.out.println("ccc");
//						}
//					} else
//						lstResult.add(baseInfo);
//				}
			//}
			 

			// }&&(baseInfo.getMacd()>=baseInfo.getUp2Macd())))//&&baseInfo.getMacd()<=baseInfo.getUpMacd()&&
			// lstResult.add(baseInfo);
			// if(baseInfo.getStockCode().equals("002493"))
			// System.out.println(baseInfo.getMacdNum()+" "+baseInfo.getMacd()+"
			// "+baseInfo.getUpMacd()+" "+baseInfo.getUp2Macd());
			// if(baseInfo.getUpSumRises5()>0||baseInfo.getUpSumRises10()>0)
			// //最低条件5天或者10天涨幅>0
			// lstResult.add(baseInfo);

		}

		if (lstBean != null && lstBean.size() > 0) {
			Map hitsMap = (Map) results.getValue("hits");
			if (hitsMap != null) {
				Number total = (Number) hitsMap.get("total");
				if (total != null) {
					if (total.intValue() > lstResult.size())
						returnMap.put("totalCount", lstResult.size());
					else
						returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstResult);
		return returnMap;

	}

	public static Map<String, Object> getLstResult(BoolQueryBuilder query, StockBasePageInfo page) throws Exception {

		Map<String, Object> returnMap = Maps.newHashMap();
        
		//query.mustNot(QueryBuilders.inQuery("stockCode", StockSelStrag.allBlckStockLst()));
		
		//query.mustNot(QueryBuilders.inQuery("stockCode", mapsSubnewStock.keySet()));

		// query.mustNot(QueryBuilders.inQuery("stockCode",
		// StockSelStrag.blckLstOfStock().keySet()));
		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, "",
				(page.getPage() - 1) * page.getLimit(), page.getLimit());
           //  System.out.println(searchSourceBuilder.toString());
		// final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		// StockSelStrag.queryGrowUpStock();//
		List<StockBaseInfo> lstBean = results.getSourceAsObjectList(StockBaseInfo.class);// .queryBchipStock();//queryGrowUpStock();//
																							// results.getSourceAsObjectList(StockBaseInfo.class);
		List<StockBaseInfo> lstResult = Lists.newArrayList();
		Set<String> setSrouce = Sets.newTreeSet();
		for (StockBaseInfo baseInfo : lstBean) {
			
			//if(!(baseInfo.getUpSumRises160()>baseInfo.getUpSumRises120()&&baseInfo.getUpSumRises120()>baseInfo.getUpSumRises90()&&baseInfo.getUpSumRises90()>baseInfo.getUpSumRises10()))
			//if(20>=baseInfo.getJ()&&baseInfo.getJ()>=baseInfo.getUpJ()&&baseInfo.getUpJ()>=baseInfo.getUp2J())	 
//			if(baseInfo.getJ()>baseInfo.getUpJ())
		//	if(!(baseInfo.getUp2J()>baseInfo.getUpJ()&&baseInfo.getMacd()<0))
			lstResult.add(baseInfo);
//			if(baseInfo!=null)
//			setSrouce.add(baseInfo.getIndustry()==null?"null":baseInfo.getIndustry());
			// if(baseInfo.getJ()>baseInfo.getUpJ())
			//if(baseInfo.getUp2J()<baseInfo.getJ())
			//lstResult.add(baseInfo);

			// if (mapsInfo.get(baseInfo.getStockCode()) == null &&
			// (!baseInfo.getStockCode().startsWith("3"))) {//
			// &&baseInfo.getUpSumRises5()>=0)//(baseInfo.getUpSumRises10()>0||baseInfo.getUpSumRises5()>=0))

			// if(baseInfo.getStockCode().equals("601155")){
			// System.out.println(baseInfo.getUp5()+" ===="+baseInfo.getUp10());
			// }

			// if(StockSelStrag.getYZEarningStrag1(baseInfo))
			// lstResult.add(baseInfo);

			// 十日涨幅大于五日涨幅
			// if (baseInfo.getUpSumRises10() > baseInfo.getUpSumRises5()) {
			// //
			// if (baseInfo.getUpSumRises10() >= 6 || baseInfo.getUpSumRises5()
			// >=5)
			// if (baseInfo.getUp5() > 2 &&baseInfo.getUp10() > 4) { //
			// 5/10天内上涨次数
			// if((baseInfo.getRises()+baseInfo.getUpRises())+baseInfo.getUpSumRises5()>0)
			// if(!(baseInfo.getUpSumRises10()>30&&(baseInfo.getUpSumRises40()>baseInfo.getUpSumRises5())))
			// //急涨急跌排除
			// //lstResult.add(baseInfo);
			// System.out.println("cc");
			// }
			// }

			// }
			

		}

//		System.out.println(setSrouce);
		if (lstBean != null && lstBean.size() > 0) {
			Map hitsMap = (Map) results.getValue("hits");
			if (hitsMap != null) {
				Number total = (Number) hitsMap.get("total");
				if (total != null) {
//					if (total.intValue() > lstResult.size())
//						returnMap.put("totalCount", lstResult.size());
//					else
						returnMap.put("totalCount", total.intValue());
				}
			}
		}
		returnMap.put("items", lstResult);
		return returnMap;

		// return getCommonLstResult(query, page,
		// CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, "");
	}

	public static Map<String, Object> getTrailerLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {

		Map<String, Object> mapSource = Maps.newConcurrentMap();

		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);// ssb.query(query);
//		System.out.println(searchSourceBuilder.toString());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STORETRAILER, type,
				(page.getPage() - 1) * page.getLimit(), page.getLimit());

 		JestResult results = jestClient.execute(selResult);
		List<StoreTrailer> lstBeanInt = results.getSourceAsObjectList(StoreTrailer.class);

		List<StoreTrailer> lstResult = Lists.newArrayList();
		// System.out.println(mapsInfo);
		Map<String, StockBaseInfo> mapsInfo2 = QueryComLstData.getStockBaseInfo();
		for (StoreTrailer bean : lstBeanInt) {
			// StockDetailInfoBean stBean =
			// QueryComLstData.getDetailInfo().get(bean.getStockCode());
			// StockBaseInfo baBean = mapsInfo2.get(bean.getStockCode());
			// if (baBean != null && stBean != null) {
			// double npe = baBean.getClose() / (bean.getJlr() /
			// stBean.getTotals());
			// bean.setNpe(npe);
			// }
			// if (stBean != null)
			// bean.setPe(stBean.getPe());

			lstResult.add(bean);
		}

		// 股价/(净利润/总股数)=市盈率（PE）
		// mapSource.remove("items");
		if (lstBeanInt != null && lstBeanInt.size() > 0) {
			Map hitsMap = (Map) results.getValue("hits");
			if (hitsMap != null) {
				Number total = (Number) hitsMap.get("total");
				if (total != null) {
					mapSource.put("totalCount", total.intValue());
				}
			}
		}
		// System.out.println(lstBean);
		mapSource.put("items", lstResult);

		return mapSource;

	}

	public static Map<String, Object> getStaLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {
		return getCommonLstResult(query, page, "storestrateinfo", type);
	}

	public static SearchSourceBuilder buildQuery(StockBasePageInfo page, BoolQueryBuilder query) {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if (!StringUtils.isEmpty(page.getSort())) {
			String[] sorts = page.getSort().split(",");
			for (int i = 0; i < sorts.length; i++) {
				String s = sorts[i];
				String order = s.split("\\.")[1];
				if (order.equalsIgnoreCase("desc")) {
					ssb.sort(s.split("\\.")[0], SortOrder.ASC);
				} else {
					ssb.sort(s.split("\\.")[0], SortOrder.DESC);
					
				}
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		// System.out.println(searchSourceBuilder.toString());
		return searchSourceBuilder;
	}

	public static Map<String, Object> getCommonLstResult(BoolQueryBuilder query, StockBasePageInfo page, String index,
			String type) throws Exception {
		
	//query.mustNot(QueryBuilders.inQuery("stockCode", StockSelStrag.allBlckStockLst()));
//		revtmpMap(mapsSelStock);
	 	//query.must(QueryBuilders.inQuery("stockCode",mapsSelStock.keySet()));
		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);
		System.out.println(searchSourceBuilder.toString());
		// (page.getPage() - 1) * page.getLimit(),page.getLimit()

		return UtilEs.getSearchRsult(searchSourceBuilder, index, type, page.getPage(), page.getLimit(), jestClient);
	}

	public static Map<String, Object> getReportLstResult(BoolQueryBuilder query, StockBasePageInfo page, String type)
			throws Exception {
		Map<String, Object> returnMap = Maps.newHashMap();
		List<String> types = Lists.newArrayList();
		
		if (type.equals(",all") || type.equals("all")) {
			types.addAll(getAllyearsInx());
		} else {
			types.add("2017-09-30");
		}
//		query.mustNot(QueryBuilders.inQuery("stockCode", StockSelStrag.blckLstOfStock().keySet()));
//		 query.must(QueryBuilders.inQuery("stockCode",
//		 mapsSelStock.keySet()));
		SearchSourceBuilder searchSourceBuilder = buildQuery(page, query);// ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, CommonBaseStockInfo.ES_INDEX_STOCK_STOREREPORT, types,
				(page.getPage() - 1) * page.getLimit(), page.getLimit());

		JestResult results = jestClient.execute(selResult);
		List<EastReportBean> lstBeanInt = results.getSourceAsObjectList(EastReportBean.class);// StockSelStrag.queryEarningsStock();//
																								// results.getSourceAsObjectList(EastReportBean.class);//
																								// results.getSourceAsObjectList(EastReportBean.class);
		List<EastReportBean> lstBean = lstBeanInt;

		if (lstBean != null && lstBean.size() > 0) {
			Map hitsMap = (Map) results.getValue("hits");
			if (hitsMap != null) {
				Number total = (Number) hitsMap.get("total");
				if (total != null) {
					returnMap.put("totalCount", total.intValue());
				}
			}
		}
		// System.out.println(lstBean);
		returnMap.put("items", lstBean);
		return returnMap;

	}

	public static List<Stap100PPI> getList(BoolQueryBuilder query, StockBasePageInfo page, String index, String type)
			throws Exception {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		if (!StringUtils.isEmpty(page.getSort())) {
			String[] sorts = page.getSort().split(",");
			for (int i = 0; i < sorts.length; i++) {
				String s = sorts[i];
				String order = s.split("\\.")[1];
				if (order.equalsIgnoreCase("desc")) {
					ssb.sort(s.split("\\.")[0], SortOrder.ASC);
				} else {
					ssb.sort(s.split("\\.")[0], SortOrder.DESC);
					
				}
			}
		}
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		System.out.println(searchSourceBuilder.toString());
		// System.out.println(page.getLimit());
		Search selResult = UtilEs.getSearch(searchSourceBuilder, index, type, (page.getPage() - 1) * page.getLimit(),
				page.getLimit());
		// final JestClient jestClient = BaseCommonConfig.clientConfig();
		JestResult results = jestClient.execute(selResult);
		List<Stap100PPI> lstBean = results.getSourceAsObjectList(Stap100PPI.class);
		return lstBean;
	}

	public static List<String> getAllyearsInx() {

		List<String> types = Lists.newArrayList();
		types.add("2017-09-30");
		types.add("2017-06-30");
		types.add("2017-03-31");

		types.add("2016-12-31");
		types.add("2016-09-30");
		types.add("2016-06-30");
		types.add("2016-03-31");

		types.add("2015-12-31");
		types.add("2015-09-30");
		types.add("2015-06-30");
		types.add("2015-03-31");

		types.add("2015-12-31");
		types.add("2015-09-30");
		types.add("2015-06-30");
		types.add("2015-03-31");

		types.add("2014-12-31");
		types.add("2014-09-30");
		types.add("2014-06-30");
		types.add("2014-03-31");

		types.add("2013-12-31");
		types.add("2013-09-30");
		types.add("2013-06-30");
		types.add("2013-03-31");

		types.add("2012-12-31");
		types.add("2012-09-30");
		types.add("2012-06-30");
		types.add("2012-03-31");

		types.add("2012-12-31");
		types.add("2012-09-30");
		types.add("2012-06-30");
		types.add("2012-03-31");

		types.add("2011-12-31");
		types.add("2011-09-30");
		types.add("2011-06-30");
		types.add("2011-03-31");

		types.add("2010-12-31");
		types.add("2010-09-30");
		types.add("2010-06-30");
		types.add("2010-03-31");

		types.add("2009-12-31");
		types.add("2009-09-30");
		types.add("2009-06-30");
		types.add("2009-03-31");

		types.add("2008-12-31");
		types.add("2008-09-30");
		types.add("2008-06-30");
		types.add("2008-03-31");

		types.add("2007-12-31");
		types.add("2007-09-30");
		types.add("2007-06-30");
		types.add("2007-03-31");
		return types;
	}
}
