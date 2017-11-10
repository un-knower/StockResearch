/**    
 * 文件名：NetStream.java    
 *    
 * 版本信息：    
 * 日期：2015年9月2日    
 * Copyright Lightbulb Corporation 2015     
 * 版权所有    
 *    
 */
package com.kers.httpmodel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 
 * 类名称：NetStream 类描述： 创建人：Administrator 创建时间：2015年9月2日 上午10:29:00
 * 修改人：Administrator 修改时间：2015年9月2日 上午10:29:00 修改备注：
 * 
 * @version
 * 
 */
public class NetStreamHanlder {
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}
