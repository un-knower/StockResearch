package com.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	public static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	/** 
     * 向指定URL发送GET方法的请求 
     *  
     */  
    public static String get(String url) {  
        BufferedReader in = null;  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            connection.setConnectTimeout(5000);  
            connection.setReadTimeout(5000);  
            // 建立实际的连接  
            connection.connect();  
            // 定义 BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
            StringBuffer sb = new StringBuffer();  
            String line;  
            while ((line = in.readLine()) != null) {  
                sb.append(line);  
            }  
            return sb.toString();  
        } catch (Exception e) {  
            //LOG.error("Exception occur when send http get request!", e);  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return null;  
    }  
    
    public static String post(String url,Map<String,String> map){    
        HttpClient httpClient = null;    
        HttpPost httpPost = null;    
        String result = null;    
        try{    
            httpClient = new SSLClient();    
            httpPost = new HttpPost(url);    
            //设置参数    
            List<NameValuePair> list = new ArrayList<NameValuePair>();    
            Iterator iterator = map.entrySet().iterator();    
            while(iterator.hasNext()){    
                Entry<String,String> elem = (Entry<String, String>) iterator.next();    
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));    
            }    
            if(list.size() > 0){    
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");    
                httpPost.setEntity(entity);    
            }    
            HttpResponse response = httpClient.execute(httpPost);    
            if(response != null){    
                HttpEntity resEntity = response.getEntity();    
                if(resEntity != null){    
                    result = EntityUtils.toString(resEntity,"UTF-8");    
                }    
            }    
        }catch(Exception ex){    
            ex.printStackTrace();    
        }    
        return result;    
    }    
}
