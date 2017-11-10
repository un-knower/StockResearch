/**    
 * 文件名：ResponseHanlderModel.java    
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
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

/**    
 *       
 * 类名称：ResponseHanlderModel    
 * 类描述：    
 * 创建人：Administrator    
 * 创建时间：2015年9月2日 上午10:27:39    
 * 修改人：Administrator    
 * 修改时间：2015年9月2日 上午10:27:39    
 * 修改备注：    
 * @version     
 *     
 */
public class MuResponseHanlder {
	
	@SuppressWarnings("rawtypes")
	public static ResponseHandler responseHandler(final String filePath)  throws Exception{
		ResponseHandler handler = new ResponseHandler() {

		@Override
			public Object handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
			StatusLine  statusLine = response.getStatusLine();
			if(statusLine==null||statusLine.getStatusCode()!=HttpStatus.SC_OK){
				return null;
			}
				
				InputStream is = response.getEntity().getContent();
			 
					try {
						FileUtils.writeByteArrayToFile(new File(filePath),
								NetStreamHanlder.readInputStream(is));
					} catch (Exception e) {
						e.printStackTrace();
					}
				return null;
			}

		};
		return handler;

	}

}

