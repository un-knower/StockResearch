/**    
 * 文件名：HttpClientEx.java    
 *    
 * 版本信息：    
 * 日期：2015年9月2日    
 * Copyright Lightbulb Corporation 2015     
 * 版权所有    
 *    
 */
package com.kers.httpmodel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * 类名称：HttpClientEx 类描述： 创建人：Administrator 创建时间：2015年9月2日 上午10:36:36
 * 修改人：Administrator 修改时间：2015年9月2日 上午10:36:36 修改备注：
 * 
 * @version
 * 
 */

@SuppressWarnings("all")
public class HttpClientEx {

	private static Logger log = Logger.getLogger(HttpClientEx.class);

	public static void setHeader(HttpRequestBase request) {

		String ua = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)";
		// 防止禁用爬虫
		if ((new Random()).nextInt(3) == 0) {
			ua = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
		} else if ((new Random()).nextInt(3) == 1) {
			ua = "msnbot/1.1 (+http://search.msn.com/msnbot.htm)";

		} else {
			ua = "Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)";
		}
		request.setHeader("User-Agent", ua);
		request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		// request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		request.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");

	}

	@SuppressWarnings("unchecked")
	public static void httpHandler(HttpHost httpHost, HttpHost proxy,
			HttpRequestBase httpReq, String filesavePath) throws Exception {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		/*
		 * credsProvider.setCredentials( new AuthScope("localhost", 8080), new
		 * UsernamePasswordCredentials("username", "password"));
		 */
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();
		try {
			// https://us.fotolia.com/ //https://us.fotolia.com/id/35642224
			// HttpHost proxy = new HttpHost("117.185.124.77", 8088);
			// HttpHost httpHost = new HttpHost("www.fotolia.com", 443,
			// "https");
			// HttpHost proxy = new HttpHost("153.121.75.130",8080);
			// HttpGet httpget = new HttpGet("/id/35642224");

			RequestConfig config = RequestConfig.custom().setProxy(proxy)
					.build();

			httpReq.setConfig(config);
			setHeader(httpReq);
			System.out.println("Executing request " + httpReq.getRequestLine()
					+ " to " + httpHost + " via " + proxy);
			httpclient.execute(httpHost, httpReq,
					MuResponseHanlder.responseHandler(filesavePath));
		} finally {
			httpclient.close();
		}
	}

	public static String getContentByUrl(HttpHost proxy, HttpRequestBase httpReq) {

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("localhost", 8080),
				new UsernamePasswordCredentials("username", "password"));
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();
		try {
			// https://us.fotolia.com/ //https://us.fotolia.com/id/35642224
			// HttpHost proxy = new HttpHost("117.185.124.77", 8088);
			// HttpHost httpHost = new HttpHost("www.fotolia.com", 443,
			// "https");
			// HttpHost proxy = new HttpHost("153.121.75.130",8080);
			// HttpGet httpget = new HttpGet("/id/35642224");

			if (null != proxy) {
				RequestConfig config = RequestConfig.custom().setProxy(proxy)
						.build();
				httpReq.setConfig(config);
			}
			setHeader(httpReq);
			System.out.println("Executing request " + httpReq.getRequestLine()
					+ " to " + httpReq.getURI() + " via " + proxy);
			// httpclient.execute(httpHost,
			// httpReq,MuResponseHanlder.responseHandler(filesavePath));

			HttpResponse response = httpclient.execute(httpReq);
			HttpEntity entity2 = response.getEntity();
			String entityBody = EntityUtils.toString(entity2, "utf-8");//
			return entityBody;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static StatusLine getStatusByUrl(HttpHost proxy,
			HttpRequestBase httpReq) {

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();
		try {

			if (null != proxy) {
				RequestConfig config = RequestConfig.custom().setProxy(proxy)
						.build();
				httpReq.setConfig(config);
			}
			setHeader(httpReq);
			System.out.println("Executing request " + httpReq.getRequestLine()
					+ " to " + httpReq.getURI() + " via " + proxy);

			HttpResponse response = httpclient.execute(httpReq);
			StatusLine statusLine = response.getStatusLine();
			return statusLine;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String post(String url, Map<String, String> params) {

		HttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String get(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		String body = null;
		log.info("create httppost:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	private static String invoke(HttpClient httpclient, HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	public static byte[] invokeToByte(HttpClient httpclient,
			HttpUriRequest httpost) {
		HttpResponse response = sendRequest(httpclient, httpost);
		byte[] body = paseResponsetoByte(response);

		return body;
	}

	private static byte[] paseResponsetoByte(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);

		byte[] body = null;
		try {
			body = EntityUtils.toByteArray(entity);
			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);

		String body = null;
		try {
			body = EntityUtils.toString(entity);
			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static HttpResponse sendRequest(HttpClient httpclient,
			HttpUriRequest httpost) {
		log.info("execute post...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}
	
	public static void downLoad(HttpClient httpClient,String url,String savePah){
		try{
			HttpGet get = new HttpGet(url);  
			byte[] bs = HttpClientEx.invokeToByte(httpClient, get);
			FileUtils.writeByteArrayToFile(new File(savePah), bs);
			log.info(url + "下载成功");
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
	}
	
	  public static URLConnection urlConnection(String urlStr){
		  try{
			  URL url = new URL(urlStr);
			  URLConnection urlConnection = url.openConnection();
			  return urlConnection;
		  }catch(Exception e){
			  log.error("urlConnection Erorr Message:",e);
		  }
		  return null;
		 
	  }

}
