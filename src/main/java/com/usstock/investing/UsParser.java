package com.usstock.investing;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kers.httpmodel.BaseConnClient;
import com.kers.stock.bean.CnInvRoot;
import com.kers.stock.bean.StockBaseInfo;
import com.tuchaoshi.base.utils.TimeUtils;

public class UsParser {
	
	

//		String path="/Users/admin/Downloads/aaa.htm";
//		List<String> lst = FileUtils.readLines(new File(path));
//		for(String s :lst){
//			System.out.println(s);
//			String savePath=s.replace("https://image.cmall.com/imgsrv/", "");
//			
//			MutilDownloadUtils.getByteContentByHttpclient(s, "/opt/savepath2/"+savePath);
////			HttpClientEx.downLoad(httpClient, url, savePah);
////			 HttpClientEx.downLoad(new http, url, savePah); 
//		}
//		
		 
	
//		Document document = 	Jsoup.parse(new File(path), "utf-8");
//		Elements elements =document.getElementsByTag("input");
//		
//		for(Element element :elements){
//			if(element.attr("value").startsWith("imgsrv/pre/impdata/skuColor/goodsColor"))
//			System.out.println(element.attr("value"));
//		}
//		
//		System.out.println();
		
//	}
	//@Test
	public void testbase() throws ClientProtocolException, IOException{
//		 Map<String,NasStockHis> mapSource= stockHistory("1y|false|MU");
//	        System.out.println(mapSource);
//		   Date date=new Date();
	
				//System.out.println();;
//				 System.out.println(sb.`);
				 
//		 System.out.println(TimeUtils.addDay(new Date(jtime), -1).);
	}
	

	
	public Map<String,StockBaseInfo>   stockHistory(String  jsonContent,String stock) throws ClientProtocolException, IOException{
		//"6y|false|MU"
		stock=stock.toLowerCase();
		String url="http://www.nasdaq.com/symbol/"+stock+"/historical";
//		System.out.println(url);
		String  scontent = BaseConnClient.RequestJsonPost(url,jsonContent);
//		FileUtils.write(new File("/opt/stock/mustock"), scontent);
		Document doc = Jsoup.parse(scontent);
	   //System.out.println(doc);
		Element element = doc.getElementById("quotes_content_left_pnlAJAX");
		//System.out.println(element);
//		syso
		Elements trs = element.select("table").select("tr");
		Map<String,StockBaseInfo> mapSource = Maps.newConcurrentMap();
		for (int i = 2; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
			String rises ="0";
			if(i<trs.size()-1){
				 double  currdayClose= Double.valueOf(tds.get(4).text());
				 double  lastdayClose= Double.valueOf(trs.get((i+1)).select("td").get(4).text());
				 double rrs =((currdayClose-lastdayClose)/currdayClose)*100;
				
				 
				 BigDecimal b = new BigDecimal(rrs);
				 rises =String.valueOf( b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())+"%";
//				 System.out.println(lastdayClose+"====="+currdayClose+"======="+tds.get(0).text()+"======"+rrs+"======"+rises);
				//rises = rrs.setScale(2, BigDecimal.ROUND_HALF_UP));
   		}
			//for (int j = 0; j < tds.size(); j++) {
//				String text = tds.get(j).text();
				//Date       	Open       	High       	Low       	Close / Last       		Volume
				String  date=tds.get(0).text();
				String  open=tds.get(1).text();
				String  high=tds.get(2).text();
				String  low=tds.get(3).text();
				String  close=tds.get(4).text();
				String  volume=tds.get(5).text();
				
							
				StockBaseInfo stockBaseInfo  =  new StockBaseInfo(date, open, high, low, close, volume,rises);
//		 	System.out.println(date+"====="+nasStockHis);
				mapSource.put(date, stockBaseInfo);
				
//				System.out.print(text+"\t");
		//	}
//			System.out.println();
			}

		return mapSource;
		 // System.out.println();
//		String path=" curl 'http://www.nasdaq.com/symbol/mu/historical'  -H 'Content-Type: application/json'   --data-binary '9y|false|MU' --compressed";
//		System.out.println(BaseConnClient.basePostNoparamReq("http://www.nasdaq.com/symbol/mu/historical", "9y|false|MU")
//				);

	
	}
//	//@Test
//	public void ttt() throws ClientProtocolException, IOException{
//		 stockHistory("1y|false|"+"yumc","yumc");
//	}

	  public String getNgTime(boolean flag,String time ,Map mapSource,int days){
		  // true +   false -
		  SimpleDateFormat  sb = new SimpleDateFormat("MM/dd/YYYY");
		   int n =0;
		   String result ="";
		  for (int i = 1; i < 10; i++) {
			  Date date = new Date(time);
			  String lastTime = sb.format(TimeUtils.addDay(date, i));
			  if(!flag)
				   lastTime = sb.format(TimeUtils.addDay(date,( -i)));
			  
			    Object mapObj=mapSource.get(lastTime);
				  if(mapObj!=null){
					  n++;
//					  System.out.println(flag+"       "+n +"====="+(-i)+"   "+lastTime);
					  if(days>0&&n<=days){
//						  String sss =mapObj+result;
						  result+=mapObj+"\r\n";// mapObj+"\r\n";
					  }
					  else
						 return mapSource.get(lastTime).toString()+"\r\n";
				  }
		}
		  
		  return result;
		  
		 
	  }
	  
	  public   String  getStockParserInfo(String stock) throws ClientProtocolException, IOException{
		  String url = "http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?seqid=21&subview=all";
			url = "http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?subview=10Q";
	        String url2="http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?seqid=21&subview=10Q";
//	        String url3="http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?seqid=61&subview=10Q";
	        String [] urls = new String []{url,url2};
	        // Filing Date	Document Date	Type	Category	Amended	
//	        TreeSet<String> setSource  = Sets.newTreeSet();
	        Map<String,StockBaseInfo> mapSource= stockHistory("10y|false|"+stock,stock);
	        String outputLine ="";
	        for (int k= 0; k < urls.length;k++) {
	        	String content = BaseConnClient.baseGetReq(urls[k]);
	    		Document doc = Jsoup.parse(content);
	    		Element element = doc.getElementById("Table2");
	    		Elements trs = element.select("table").select("tr");
	    		for (int i = 1; i < trs.size()-1; i++) {
	    			Elements tds = trs.get(i).select("td");
	    				String reportDate = tds.get(0).text();
//	    				 String lstStr= ;
	    				if(mapSource.get(reportDate)!=null){
	    					  outputLine += getNgTime(false, reportDate, mapSource,0)+
	    							"****"+mapSource.get(reportDate)+"****"+"\r\n"+
	    							getNgTime(true, reportDate, mapSource,7)+
	    							"======"+reportDate+"======="+"\r\n\r\n";
	    				}
	    		}
	  }
	      
	        return outputLine;
	        }
	  
	  
	  
	  //@Test
	  public   void  getStockCnParserInfo( String  stock) throws ClientProtocolException, IOException{
//		  String stock ="QSR";
//		  String url = "http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?seqid=21&subview=all";
//			url = "http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?subview=10Q";
//	        String url2="http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?seqid=21&subview=10Q";
////	        String url3="http://www.marketwatch.com/investing/Stock/"+stock+"/SecFilings?seqid=61&subview=10Q";
//	        String [] urls = new String []{url,url2};
	        // Filing Date	Document Date	Type	Category	Amended	
//	        TreeSet<String> setSource  = Sets.newTreeSet();
	        Map<String,StockBaseInfo> mapSource= stockHistory("10y|false|"+stock,stock);
	        String outputLine ="";
//	        for (int k= 0; k < urls.length;k++) {
			String url="https://cn.investing.com/search/service/search";
	        	CnInvRoot  cnvroot=  new Gson().fromJson(BaseConnClient.RequestMulJsonPost(url, "search_text="+stock+"&term="+stock+"&country_id=0&tab_id=Stocks"),CnInvRoot.class);
                if(!cnvroot.getAll().isEmpty()) {    
                	
                String fullLink="https://cn.investing.com"+cnvroot.getAll().get(0).getLink()+"-earnings";
                System.out.println(fullLink);
	        	String content = BaseConnClient.baseGetReq(fullLink);
	    		Document doc = Jsoup.parse(content);
//	    		System.out.println(doc);
	    		Element element = doc.getElementsByClass("earningsPageTbl").get(0);
//	    		System.out.println(element);
	    		Elements trs = element.select("table").select("tr");
	    		for (int i = 1; i < trs.size(); i++) {
//	    			System.out.println(trs.get(i));
	    			Elements tds = trs.get(i).select("td");
//	    				String reportDate = tds.get(0).text();
	    				 SimpleDateFormat  sb = new SimpleDateFormat("MM/dd/YYYY");
//	    				 System.out.println(tds.get(0).text());
	    				 String reportDate = sb.format(new Date(tds.get(0).text().replace("年", "//").replace("月", "//").replace("日", "//")));
//	    				 System.out.println(reportDate);
////	    				 String lstStr= ;
	    				if(mapSource.get(reportDate)!=null){
	    					  outputLine += getNgTime(false, reportDate, mapSource,0)+
	    							"****"+mapSource.get(reportDate)+"****"+"\r\n"+
	    							getNgTime(true, reportDate, mapSource,7)+
	    							"======"+reportDate+"======="+"\r\n\r\n";
	    				}
//	    		}
	  }
                }
	      
//	        return outputLine;
//	    		return null;
	        }
	  
	  
//		public static void main(String[] args) {
//			String content= RequestMulJsonPost("https://cn.investing.com/search/service/search", "search_text=mu&term=mu&country_id=0&tab_id=Stocks");
//			System.out.println(content);
//		}
	 
//		//@Test
		 public void parser() throws IOException{
			String path="/opt/workspace/splider/src/test/java/com/cmall/stock/data/needShareData";
			String url="https://cn.investing.com/search/service/search";
			
			 List<String>  filePath=FileUtils.readLines(new File(path));
			 for(String s :filePath){
//				  if(s.contains("餐厅")){
					  String source [] =s.split(" 	");
					 
					  
					  String stock=source[1];
					  CnInvRoot  cnvroot=  new Gson().fromJson(BaseConnClient.RequestMulJsonPost(url, "search_text="+stock+"&term="+stock+"&country_id=0&tab_id=Stocks"),CnInvRoot.class);
                         if(!cnvroot.getAll().isEmpty())        
					  System.out.println(cnvroot.getAll().get(0).getLink());
					  
					  //				
					  
//					  stockHistory("1y|false|"+stock,stock);
//					  System.out.println(stockHistory("1y|false|"+stock,stock));
//					  System.out.println(source.length);
//					  System.out.println(stock);
//					  System.out.println(stock);
//					  FileUtils.write(new File("/opt/stock/stockInfo_"+stock+".txt"), getStockParserInfo(stock),true);
//					  System.out.println(stock);
					  System.out.println(getStockParserInfo(stock));
					  
//				  }
			 }
			 }
//	//@Test
//	@SuppressWarnings("all")
//	public void testA() throws ClientProtocolException, IOException {
//		  
//		
//			
//		}
        
//        Map<String,NasStockHis> mapSource= stockHistory("10y|false|MU");
//        System.out.println(mapSource);
//        SimpleDateFormat  sb = new SimpleDateFormat("MM/dd/YYYY");
// 	   SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
//	    System.out.println( dateFm.format(date));;
//        for(String s :setSource){
        	
        	
//        	Date date = new Date(s);
//        	String lastTime ="";
//        	if(dateFm.format(date).equals("星期一"))
//        		 lastTime = sb.format(TimeUtils.addDay(date, -3));
//        	else
//        		 lastTime = sb.format(TimeUtils.addDay(date, -1));
        	
        	
//        	String nextTime= "";
//        	if(dateFm.format(date).equals("星期五"))
//        		nextTime=sb.format(TimeUtils.addDay(date, 3));
//        	else 
//        		nextTime=sb.format(TimeUtils.addDay(date, 1));
        	
        	
//        	String nextTime=  sb.format(TimeUtils.addDay(date, 1));
//        	 System.out.println(getNgTime(false, s, mapSource));//mapSource.get(lastTime)+"\r\n"+getNgTime(false, s, mapSource));
//        	 System.out.println("****"+mapSource.get(s)+"****");
//        	 System.out.println(getNgTime(true, s, mapSource));//mapSource.get(nextTime)+"\r\n"+getNgTime(true, s, mapSource));
//        	 System.out.println("======"+s+"=======");
//        	 System.out.println("                    ");
//        }
          
		
//	}
}