package com.kers.fund;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.collect.Sets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.kers.httpmodel.BaseConnClient;
import com.kers.stock.utils.MathsUtils;

public class FundHand {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		List<String> types = Lists.newArrayList();
		types.add("2017-09-30");
		types.add("2017-12-31");
		 Map<String,FundBean> mapSource = Maps.newConcurrentMap();

		Set<String> sets=Sets.newConcurrentHashSet();
		
		for (int i = 0; i <types.size(); i++) {
			 String typeYear = types.get(i);
			 
			 for (int j = 1; j <=43; j++) {
				 
					String path="http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=ZLSJ&sty=ZLCC&stat=1&st=2&sr=-1&p="+j+"&ps=50&js={data:[(x)]}&cmd=1&fd="+typeYear+"&rt=50648127";
					
					
					 
					
					
					String data = BaseConnClient.baseGetReq(path);
					JSONObject obj = (JSONObject) JSONObject.parse(data);
					JSONArray arr = obj.getJSONArray("data");

					
					for (int nn = 0; nn < arr.size(); nn++) {
						String bean[] = arr.get(nn).toString().split(",");
						String   jjcgNum =bean[2];
						FundBean  fundBean = new FundBean();
						fundBean.setStockCode(bean[0]);
						fundBean.setStockName(bean[1]);
						fundBean.setJjcgNum(MathsUtils.parseDouble(jjcgNum));
						if(mapSource.get(fundBean.getStockCode())!=null){
							 
							
							if(fundBean.getStockCode()==null)
							System.out.println(fundBean.getStockCode()+"\t"+fundBean.getStockName()+"\t"+mapSource.get(fundBean.getStockCode()).getJjcgNum()+"\t"+fundBean.getJjcgNum()+"\t"+((fundBean.getJjcgNum()-mapSource.get(fundBean.getStockCode()).getJjcgNum())/(mapSource.get(fundBean.getStockCode()).getJjcgNum())));
							sets.add(fundBean.getStockCode());
						}
						else mapSource.put(fundBean.getStockCode(), fundBean);
					}
				
			}
			 
			 
			 
			
		}
		
		
	
		}
		

}
