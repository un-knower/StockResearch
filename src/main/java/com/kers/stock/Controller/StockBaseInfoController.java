package com.kers.stock.Controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.list.TreeList;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.SelEsRelt;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockOptionalInfo;
import com.kers.stock.bean.StockTag;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.storedata.ZxzbHand;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.strage.StrategySet;
import com.kers.stock.utils.TimeUtils;
import com.kers.stock.vo.StockBasePageInfo;

import io.searchbox.client.JestClient;

@RestController
public class StockBaseInfoController extends BaseController<StockBaseInfo> {

	@RequestMapping("/getList")
	public Map<String, Object> getList(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		return SelGetStock.getLstResult(query, page);
	}
	
	/**
	 * 
	 * @description
	 * 	1.当日涨幅>=10% 或0 2.两天涨幅超过15% 3.5天涨幅超过3天4.成交量大于前一日交易量2倍5.j值=0   》=2

				次新股除外（上市时间超过6个月）   三点前查前一天数据（注意周六周日 向前减1,2天）		
						每天五点执行一次 七点检查 早上九点检查 
1.新加一张表 新导入数据（cvs只要近20天的数据）
在导入的是做判断只需 三天涨幅超22%或者5天涨幅超33%数据（开板新股除外）
成交量放大10倍并且rise>0
	 */
	@RequestMapping("/getListLonghu")
	public Map<String, Object> getListLonghu(StockBasePageInfo page) throws Exception {
		//获取查询时间
		String date = TimeUtils.getStockDate();
		String dateJson = "{\"must\":\"must\",\"name\":\"date\",\"type\":\"=\",\"value\":\""+date+"\"},";
		dateJson += "{\"must\":\"must_not\",\"name\":\"stockCode\",\"type\":\"prefix\",\"value\":\""+3+"\"},";
		  dateJson+= "{\"must\":\"must\",\"name\":\"lsImp\",\"type\":\"in\",\"value\":\""+"1,2,22"+"\"}]";
		String json = page.getDatas();
		if(json.length() > 10){
			dateJson = "," + dateJson;
		}
		json = json.replace("]", dateJson);
		page.setDatas(json);
//		int pageStart = page.getPage();
//		int limit = page.getLimit();
		page.setLimit(50);
//		page.setPage(0);
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		 Map<String, Object>  mapRes= SelGetStock.getLstResult(query, page);
		List<StockBaseInfo>   lstRes=(List<StockBaseInfo>)SelGetStock.getLstResult(query, page).get("items");
		for(StockBaseInfo  baseInfo:lstRes){
		 
			StockOptionalInfo stockOptionalInfo = ZxzbHand.mapsNdzData.get(baseInfo.getStockCode());
			    if(baseInfo.getSbType().equals("rise2Up")){
			    	 System.out.println(stockOptionalInfo);
			    	if(stockOptionalInfo!=null&&stockOptionalInfo.getPercent()<3){
			    		lstRes.remove(baseInfo);
			    		System.err.println("Strong rise lose :"+baseInfo.getStockName());
			    	}
			    }
			    baseInfo.setPercent(stockOptionalInfo==null?0:stockOptionalInfo.getPercent());
		}
		mapRes.put("items", lstRes);
		return mapRes;
	}
	
	@RequestMapping("/getListLonghuTab2")
	@SuppressWarnings("all")
	public Map<String, Object> getListLonghuTab2(StockBasePageInfo page) throws Exception {
		SelEsRelt sel = new SelEsRelt(new  StockBaseInfo());
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.should(QueryBuilders.inQuery("lsImp",  "1"));
		query.should(QueryBuilders.inQuery("lsImp",  "2"));
		query.should(QueryBuilders.inQuery("lsImp",  "22"));
		
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		List<StockBaseInfo> lstSource = sel.getResultFromQuery(searchSourceBuilder,"", CommonBaseStockInfo.ES_INDEX_STOCK_STOCKPCSE, 0, 6000);
	
		Map  mapRes = Maps.newConcurrentMap();
		Map  mapRes2 = Maps.newConcurrentMap();
	for(StockBaseInfo  stockBaseInfo:lstSource){
		String stockCode = stockBaseInfo.getStockCode();            
		SelEsRelt sel2 = new SelEsRelt(new  StockTag());
		  ssb = new SearchSourceBuilder();
		  query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.queryString(stockCode).field("stockCode"));
		  searchSourceBuilder = ssb.query(query);
		List<StockTag> lstSource2 = sel2.getResultFromQuery(searchSourceBuilder,"", CommonBaseStockInfo.ES_INDEX_STOCK_STOCKTAG, 0, 6000);
		
	
		if(lstSource2!=null&&(!lstSource2.isEmpty())){
		 for(StockTag tag:lstSource2){
			  Object obj =mapRes.get(tag.getTagName());
			  if(obj!=null)
			 mapRes.put(tag.getTagName(), 1);
			 else 
				 mapRes.put(tag.getTagName(), (Integer.getInteger(obj+"")+1));
			  
			  
			 
		 }
		 
		 
		 Object obj2 =mapRes.get(lstSource2.get(0).getTagName());
		 String tagName=lstSource2.get(0).getTagName();
		 if(obj2!=null)
		  mapRes2.put(tagName, stockBaseInfo.getStockName());
		 else{
			 String sss=obj2+","+stockBaseInfo.getStockName();
			 mapRes2.put(tagName, sss);
			 
		 }
			 
		}
	}
	}
	
	
	/**
	 * 指标榜
	 *     1. kdj 金叉  (kdj)
	 *     2.macd 金叉
	 *    3.5日内涨幅》-2  十日内涨幅》=-4且 macd大于0趋势向上
	 */
	@RequestMapping("/getListZhibiao")
	public Map<String, Object> getListZhibiao(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		return SelGetStock.getLstResult(query, page);
	}
	
	/**
	 * 查询风险指数(待实现逻辑)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFx")
	public Map<String, Object> getFx(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		return SelGetStock.getLstResult(query, page);
	}
	

	@RequestMapping("/getClassNameList")
	public String[] getClassNameList() throws Exception {
		StockBaseInfo info = new StockBaseInfo();
		java.lang.reflect.Field[] fields = info.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length - 1];
		for (int i = 1; i < fields.length; i++) {
			fieldNames[i - 1] = fields[i].getName();
		}
		return fieldNames;
	}

	@RequestMapping("/focDays/getList")
	public Map<String, Object> getfocDaysList(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();

		setQuery(query, page);
		return SelGetStock.getfocDaysLstResult(query, page);
	}

	@RequestMapping("/focDays/getGraphCompare")
	public Object[] getGraphCompare(StockBasePageInfo page) throws Exception {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
		setQuery(query, page);
	
		List<StockBaseInfo> list = (List<StockBaseInfo>) SelGetStock.getfocDaysLstResult(query, page).get("items");

		Set<String> legendData = Sets.newTreeSet();
		Set<String> xAxisData = Sets.newTreeSet();
		Map<String, SeriesBean> maps = Maps.newTreeMap();
		Map<String, StockBaseInfo> m = Maps.newHashMap();
		int i=0;
		//System.out.println(list);
		 Map<String,StockBaseInfo> mapSource = Maps.newConcurrentMap();
		 double nn=0;
		 double mm=0;
		 double  hhh=0;
		 String name="";
		for (StockBaseInfo bean : list) {
			String stockName =bean.getStockName();
			if(stockName.startsWith("XD")||stockName.startsWith("DR")){
				 stockName=list.get(i-1).getStockName();
				 bean.setStockName(stockName);
			}
			if(stockName.startsWith("N")){
				 stockName=list.get(i+1).getStockName();
				 bean.setStockName(stockName);
			}
			
  			legendData.add(bean.getStockName());
			xAxisData.add(bean.getDate());
			m.put(bean.getStockName().trim() + bean.getDate(), bean);
			i++;
			StockBaseInfo  beanNw = mapSource.get(bean.getDate());
			if(beanNw!=null&&beanNw.getOpen()>0){
				mm++;
				
				if(beanNw.getRises()>bean.getRises()){
					name=beanNw.getStockName();
				nn++;	
				}
				if((beanNw.getRises()>0&&bean.getRises()<0)||(beanNw.getRises()<0&&bean.getRises()>0)){
					if(true){//Math.abs(beanNw.getRises()+bean.getRises())>1
				//	System.out.println(beanNw.getRises()+" ==>"+bean.getRises());
					hhh++;
					}
				}
			}
			mapSource.put(bean.getDate(), bean);
		}
		  String  rise1=String.format("%.2f",(nn/mm)*100);
		  String rise2=String.format("%.2f",((hhh/mm)*100));
		  String lout="";
		  if(Double.parseDouble(rise1)<50)
			  lout="(弱势)";
		  else
			  lout="(强势)";
		  if(Double.parseDouble(rise2)<=8.8) {//在同一成长周期
		  }else
			  rise2+="(不同周期)";
		  
		System.out.println("总数为:"+mm+"	"+name+"涨幅超过天数为:"+nn+"  占比:"+rise1+lout+"  差异占比为:"+rise2);
		
		for (String string : legendData) {
			for (String x : xAxisData) {
				String key2 = string + x;
				StockBaseInfo bean = m.get(key2);
				float ri = 0;
				if(null == bean){
					//System.out.println("日期："+key2);
					bean = new StockBaseInfo();
					bean.setStockName(string);
				}else{
					ri = bean.getRises();
				}
				String key=bean.getStockName().trim();
				if ( maps.get(key)== null) {
					SeriesBean beanss = new SeriesBean();
					  TreeList sets= new TreeList();
					  sets.add(ri);
					beanss.setData(sets );
					beanss.setName(bean.getStockName());
					beanss.setType("line");
					maps.put(key, beanss);
				} else {
					SeriesBean beanss = maps.get(key);
					beanss.getData().add(ri);
					maps.put(key, beanss);
				}
			}
		}
		Object[] str = new String[3];
		str[0] = "data:\""+legendData.toString()+"\"";
		str[1] = xAxisData.toString().replace("[", "").replace("]", "");
		str[2] = maps.values().toString();
		//System.out.println(str[2]);
		//System.out.println(maps);
		return str;

	}

	@RequestMapping("/focDays/getClassNameList")
	public String[] getClassfocDaysNameList() throws Exception {
		StockBaseInfo info = new StockBaseInfo();
		java.lang.reflect.Field[] fields = info.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length - 1];
		for (int i = 1; i < fields.length; i++) {
			fieldNames[i - 1] = fields[i].getName();
		}
		return fieldNames;
	}
	
	
	@RequestMapping("/getKDJ")
	public Map<String, Object> getKDJ(StockBasePageInfo page) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		setQuery(query, page);
		List<StockBaseInfo> list = (List<StockBaseInfo>) SelGetStock.getLstResult(query, page).get("items");
		String[] dates = new String[list.size()];
		Float[] ks = new Float[list.size()];
		Float[] ds = new Float[list.size()];
		Float[] js = new Float[list.size()];
		Float[] diffs = new Float[list.size()];
		Float[] daes = new Float[list.size()];
		Object[][] object = new Object[list.size()][5];
		int j = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			Object[] ob = new Object[5];
			StockBaseInfo bean = list.get(i);
			dates[j] = bean.getDate();
			ks[j] = bean.getK();
			ds[j] = bean.getD();
			js[j] = bean.getJ()>100?100:bean.getJ();
			diffs[j] = bean.getDiff();
			daes[j] = bean.getDea();
			ob[0] = bean.getDate();
			ob[1] = bean.getOpen();
			ob[2] = bean.getClose();
			ob[3] = bean.getLow();
			ob[4] = bean.getHigh();
			object[j] = ob;
			j++;
		}
		map.put("date", dates);
		map.put("K", ks);
		map.put("D", ds);
		map.put("J", js);
		map.put("DIFF", diffs);
		map.put("DAE", daes);
		map.put("obj", object);
		return map;

	}
	
	
	@RequestMapping("/savecl")
	public String savecl(StockBasePageInfo page) throws Exception {
		List<StockBasePageInfo> list = Lists.newArrayList();
		JestClient jestClient = BaseCommonConfig.clientConfig();
		page.setDate(TimeUtils.getDate());
		list.add(page);
		StrategySet.insBatchEs(list, jestClient, CommonBaseStockInfo.SAVE_CN);
		return "OK";
	}
	
	@RequestMapping("/getcl")
    public Map<String,Object> getcl(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.SAVE_CN,type);
    }

}

class SeriesBean {
	private String name;
	private String type;
	private List data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "{name: '" + name + "', type:'" + type + "', data:" + data + "}";
	}
	
	
}