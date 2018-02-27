package com.kers.stock.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kers.httpmodel.BaseConnClient;
import com.kers.staple.bean.Stap100PPI;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.storedata.CommonBaseStockInfo;
import com.kers.stock.strage.SelGetStock;
import com.kers.stock.vo.StockBasePageInfo;

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
    	page.setLimit(330);
    	List<Stap100PPI> list = SelGetStock.getList(query,page,CommonBaseStockInfo.ES_INDEX_STOCK_STAPLEDAY,type);
//    	List<Stap100PPI> list = (List<Stap100PPI>) map.get("items");
    	int j = 0;
    	String[][] str = new String[list.size()][2];
    	for (int i = list.size() - 1; i >= 0; i--) {
    		Stap100PPI stap100ppi = list.get(i);
    		String[] s = new String[2];
    		s[0] = stap100ppi.getRq();
    		s[1] = String.valueOf(stap100ppi.getMonthYcPrice());
    		str[j] = s;
    		j++;
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
    
    /**
     * 获取全球大盘数据
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    @RequestMapping("/stapleday/getAll")
    public List<StockBaseInfo> getAll() throws ClientProtocolException, IOException{
    	//泸深http://hq.sinajs.cn/rn=151730262502396&list=s_sh000001,s_sz399001,s_sh000300,CFF_IC0,s_sz399006
    	//亚太http://hq.sinajs.cn/rn=1517303034135&list=hkHSI,b_NKY,b_TWSE,b_AS30,b_FSSTI
    	//欧洲http://hq.sinajs.cn/rn=1517303057897&list=EURUSD,b_UKX,b_DAX,b_CAC,b_FTSEMIB
    	//美股http://hq.sinajs.cn/rn=1517303070978&list=gb_dji,gb_ixic,gb_inx,hf_DJS,hf_NAS
    	String[][] urlStr = {{"s_sh000001","0"},{"s_sz399006","0"},{"hkHSI","1"},{"b_NKY","0"},{"gb_dji","2"},{"gb_ixic","2"}};
    	String url = "http://hq.sinajs.cn/rn=151730262502396&list=";
    	for (int i = 0; i < urlStr.length; i++) {
    		url = url + urlStr[i][0] + ",";
		}
    	String con = BaseConnClient.baseGetReq(url);
    	List<StockBaseInfo> list = Lists.newArrayList();
    	con = con.replace("\"", "");
    	String[] datas = con.split("\\;");
    	int i = 0;
    	for (String string : datas) {
			if(string.split("\\=").length > 1){
				String[] ds = string.split("\\=")[1].split(",");
				if(ds.length > 3){
					StockBaseInfo info = new StockBaseInfo();
					if(urlStr[i][1].equals("0")){
						info.setStockName(ds[0]);
						info.setMb(Float.parseFloat(ds[1]));
						info.setPe(Double.parseDouble(ds[2]));
						info.setRises(Float.parseFloat(ds[3]));
					}
					if(urlStr[i][1].equals("2")){
						info.setStockName(ds[0]);
						info.setMb(Float.parseFloat(ds[1]));
						info.setRises(Float.parseFloat(ds[2]));
					}
					if(urlStr[i][1].equals("1")){
						info.setStockName(ds[1]);
						info.setMb(Float.parseFloat(ds[2]));
						info.setRises(Float.parseFloat(ds[8]));
					}
					list.add(info);
				}
			}
			i++;
		}
    	return list;
    }
    
    @RequestMapping("/stapleday/getShiborTable")
    public String getShiborTable(){
    	String html = "";
		try {
			String con = BaseConnClient.baseGetReq("http://www.chinamoney.com.cn/fe/static/html/column/basecurve/benchmarks/shibor/latestShibor.html");
			Document document = Jsoup.parse(con);
			Elements tables = document.select("table");
			Elements trs = tables.get(0).select("tr");
			for (int i = 4; i < 7; i++) {
				html = html + "<tr>";
				Elements tds = trs.get(i).select("td");
				html = html + "<td>" + tds.get(0).select("a").get(0).html() + "</td>";
				html = html + "<td>" + tds.get(1).html() + "</td>";
				if(Float.parseFloat(tds.get(2).select("span").get(0).html()) >= 1){
					html = html + "<td><span style=\"color: #b00\">" + tds.get(2).select("span").get(0).html() + "</span></td>";
				}else{
					html = html + "<td><span style=\"color: #0b0\">" + tds.get(2).select("span").get(0).html() + "</span></td>";
				}
				html = html + "</tr>";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html;
    }
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	
	}
   
}