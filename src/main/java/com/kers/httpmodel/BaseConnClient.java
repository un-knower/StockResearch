/**    
 * 文件名：BaseConnClient.java    
 *    
 * 版本信息：    
 * 日期：2015年4月3日    
 * Copyright Lightbulb Corporation 2015     
 * 版权所有    
 *    
 */
package com.kers.httpmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.Header;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 类名称：BaseConnClient 类描述： httpclient基础类 创建人：Administrator 创建时间：2015年4月3日
 * 上午11:11:23 修改人：Administrator 修改时间：2015年4月3日 上午11:11:23 修改备注：
 * 
 * @version
 * 
 */
@SuppressWarnings("all")
public class BaseConnClient {
	/**
	 * 
	 * @description post请求基础类
	 * @return
	 * @Exception 异常对象
	 */
	public static String basePostReq(String url, List<NameValuePair> parms)
			throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(url);
		
		post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
		
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parms, "utf-8");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		HttpEntity entity2 = response.getEntity();
		String entityBody = EntityUtils.toString(entity2, "utf-8");
		return entityBody;
	}

	/**
	 * 
	 * @description 请求基础类
	 * @return
	 * @Exception 异常对象
	 */
	public static String baseGetReq(String url) throws ClientProtocolException, IOException {

		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet getType = new HttpGet(regUrl(url));
		getType.addHeader("Content-Type", "text/html;charset=UTF-8");
		getType.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		getType.setHeader("X-Requested-With", "XMLHttpRequest");
		getType.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
		getType.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		getType.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		HttpResponse response = client.execute(getType);
		HttpEntity entity2 = response.getEntity();
		String entityBody = EntityUtils.toString(entity2, "utf-8");
		return entityBody;

	}

	/**
	 * 
	 * @description post请求基础类 raw
	 * @return
	 * @Exception 异常对象
	 */
	public static String basePostNoparamReq(String url, String rawStr) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(url);

		StringEntity entity = new StringEntity(rawStr, "utf-8");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		HttpEntity entity2 = response.getEntity();
		String entityBody = EntityUtils.toString(entity2, "utf-8");
		return entityBody;
	}

	public static  String RequestJsonPost(String url,String jsonContent) {
		String strresponse = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost hp = new HttpPost(url);
			// JSONObject jsonParam = new JSONObject();
			// jsonParam.put("user","admin");
			// jsonParam.put("password", "123456");
			// 设置数据为utf-8编码
			StringEntity entity = new StringEntity(jsonContent, "utf-8");
			// 设置请求编码
			entity.setContentEncoding("utf-8");
			// 设置请求类型
			entity.setContentType("application/json");
			hp.setEntity(entity);
			// 请求并得到结果
			HttpResponse result = client.execute(hp);
			strresponse = EntityUtils.toString(result.getEntity(), "utf-8").trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strresponse;
	}
	
	
	public static  String RequestMulJsonPost(String url,String jsonContent ) {
		String strresponse = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost hp = new HttpPost(url);
			// JSONObject jsonParam = new JSONObject();
			// jsonParam.put("user","admin");
			// jsonParam.put("password", "123456");
			// 设置数据为utf-8编码
			hp.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
			hp.setHeader("X-Requested-With", "XMLHttpRequest");
			hp.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
			
			//hp.setHeader( header);
//			hp.setHeader("Content-Type","application/x-www-form-urlencoded' -H 'Accept: application/json, text/javascript, */*; q=0.01");
			StringEntity entity = new StringEntity(jsonContent, "utf-8");
			// 设置请求编码
			entity.setContentEncoding("utf-8");
		 
			 
			// 设置请求类型
			entity.setContentType("application/x-www-form-urlencoded");
		 
			//entity.setContentType("X-Requested-With", "XMLHttpRequest");
			hp.setEntity(entity);
			// 请求并得到结果
			HttpResponse result = client.execute(hp);
//			System.out.println(result.getEntity());
			strresponse = EntityUtils.toString(result.getEntity(), "utf-8").trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strresponse;
	}
	 
	
	 public static void main(String[] args) {
		 
		
	}
	
//	public static void main(String[] args) {
//		
//		//curl -H 'Host: proxy.finance.qq.com' -H 'Accept: */*'  -H 'User-Agent: QQStock/17070314 CFNetwork/808.2.16 Darwin/16.3.0' -H 'Referer: http://zixuanguapp.finance.qq.com' -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' --compressed 'http://proxy.finance.qq.com/ifzqgtimg/appstock/news/noticeList/getBigEvent?symbol=sh600291&page=1&n=20&_rndtime=1500548369&_appName=ios&_dev=iPhone7,2&_devId=3123792af0e27a6d014bc62e8ad8bab897c4a6c2&_appver=5.6.3&_ifChId=&_isChId=1&_osVer=10.2&_uin=46698576&_wxuin=46698576'
//		Header header = new httphea
//		
//		
//		String content= RequestMulJsonPost("https://cn.investing.com/search/service/search", "search_text=mu&term=mu&country_id=0&tab_id=Stocks",null);
//		System.out.println(content);
//	}

	public static String regUrl(String url) {
		url = url.replaceAll("<br />", " %20").replaceAll("，", "%20 ").replace(" ", "%20")
				.replace("{", "%7B").replace("}", "%7D").replace(" ", "%20").replace(" ", "%20").replace(" ", "%20");
		//.replaceAll("  ", "").replaceAll(" ", "");
		return url;
	}
}
