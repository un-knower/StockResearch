package com.cmall.stock.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmal.stock.storedata.CommonBaseStockInfo;
import com.cmal.stock.strage.SelGetStock;
import com.cmall.staple.bean.Stap100PPI;
import com.cmall.stock.vo.StockBasePageInfo;
import com.kers.httpmodel.BaseConnClient;

@RestController
public class StapledayController  extends BaseController<Stap100PPI>{
	
	@RequestMapping("/stapleday/getList")
    public Map<String,Object> getList(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
        return SelGetStock.getCommonLstResult(query,page,CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY,type);
    }
	
	@RequestMapping("/stapleday/getListByName")
    public String[][] getListByName(StockBasePageInfo page, String type) throws Exception {
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	setQuery(query,page);
    	page.setPage(1);
    	List<Stap100PPI> list = SelGetStock.getList(query,page,CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY,type);
//    	List<Stap100PPI> list = (List<Stap100PPI>) map.get("items");
    	String[][] str = new String[list.size()][2];
    	int i = 0;
    	for (Stap100PPI stap100ppi : list) {
    		String[] s = new String[2];
    		s[0] = stap100ppi.getRq();
    		s[1] = String.valueOf(stap100ppi.getMonthYcPrice());
    		str[i] = s;
    		i++;
		}
        return str;
    }
    
    @RequestMapping("/stapleday/getClassNameList")
    public String[] getClassNameList() throws Exception {
    	
//    	return getClassNameList(Stap100PPI);
    	Stap100PPI info = new Stap100PPI();
    	java.lang.reflect.Field[] fields=info.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length-1];  
	    for(int i=1;i<fields.length;i++){  
	        fieldNames[i-1]=fields[i].getName();  
	    }  
        return fieldNames;
    }
    
    /**
     * 获取今日热点消息
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    @RequestMapping("/stapleday/getHotspotHtml")
    public String getHotspotHtml() throws ClientProtocolException, IOException{
    	String fan = "";
    	String con = BaseConnClient.baseGetReq("http://www.100ppi.com");
    	Document document = Jsoup.parse(con);
    	Elements eles = document.getElementsByClass("topnewslist");
    	if(eles.size() > 0){
    		Element ele = eles.get(0);
    		Elements lis = ele.getElementsByClass("fl");
    		for (int i = 0; i < lis.size(); i++) {
    			int s = lis.get(i).getElementsByClass("fl").size();
    			if(s == 1){
    				continue;
    			}
    			String ban = lis.get(i).getElementsByClass("fl").get(0).select("a").get(0).html();
    			Element as = lis.get(i).getElementsByClass("content1").get(0);
    			fan = fan + "<li class=\"list-group-item\" href=\"#\">" + ban;
    			String liStr = as.html().replace("href=\"/", "href=\"http://www.100ppi.com/");
    			fan = fan + liStr + "</li>";
			}
    	}
    	return fan;
    }
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	String fan = "";
    	String con = BaseConnClient.baseGetReq("http://www.100ppi.com");
    	Document document = Jsoup.parse(con);
    	Elements eles = document.getElementsByClass("topnewslist");
    	if(eles.size() > 0){
    		Element ele = eles.get(0);
    		Elements lis = ele.getElementsByClass("fl");
    		for (int i = 0; i < lis.size(); i++) {
    			int s = lis.get(i).getElementsByClass("fl").size();
    			if(s == 1){
    				continue;
    			}
    			String ban = lis.get(i).getElementsByClass("fl").get(0).select("a").get(0).html();
    			Element as = lis.get(i).getElementsByClass("content1").get(0);
    			fan = fan + "<li class=\"list-group-item\" href=\"#\">" + ban;
    			String liStr = as.html().replace("href=\"/", "href=\"http://www.100ppi.com/");
    			fan = fan + liStr + "</li>";
			}
    	}
    	System.out.println(fan);
	}
   
}