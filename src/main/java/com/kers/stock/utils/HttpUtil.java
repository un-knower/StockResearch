package com.kers.stock.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

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
    
    
    /**
     * post方式请求数据
     * @param url 请求链接
     * @param data post数据体
     * @return
     */
    public static String post(String url, Map<String,String> data){
        StringBuffer sb = new StringBuffer();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
        if(null != data) {
            for (String key : data.keySet()) {
                valuePairs.add(new BasicNameValuePair(key, data.get(key)));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(valuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            BufferedInputStream bis = new BufferedInputStream(httpEntity.getContent());
            byte [] buffer;
            while (0<bis.read(buffer=new byte[128])){
                sb.append(new String(buffer,"utf-8"));
            }
        }catch (UnsupportedEncodingException e){//数据格式有误
            e.printStackTrace();
        }catch (IOException e){//请求出错
            e.printStackTrace();
        }finally {
            httpPost.releaseConnection();
        }
        return sb.toString();
    }
}
