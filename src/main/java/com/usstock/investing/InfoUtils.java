package com.usstock.investing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.cmall.stock.bean.CnInvRoot;
import com.google.gson.Gson;
import com.kers.httpmodel.BaseConnClient;

public class InfoUtils {
	
	public   static final String  stockCodeList="/opt/workspace/StockResearch/data/usmarket/needShareData";
	public   static final String  baseSiteUrl=	"https://cn.investing.com/search/service/search";
	
	public void parser() throws IOException{
		
		 List<String>  filePath=FileUtils.readLines(new File(stockCodeList));
		 for(String s :filePath){
//			  if(s.contains("餐厅")){
				  String source [] =s.split(" 	");
				 
				  
				  String stock=source[1];
				  CnInvRoot  cnvroot=  new Gson().fromJson(BaseConnClient.RequestMulJsonPost(baseSiteUrl, "search_text="+stock+"&term="+stock+"&country_id=0&tab_id=Stocks"),CnInvRoot.class);
                     if(!cnvroot.getAll().isEmpty())        
				  System.out.println(cnvroot.getAll().get(0).getLink());
				  
				  
//			  }
		 }
		 }

}
