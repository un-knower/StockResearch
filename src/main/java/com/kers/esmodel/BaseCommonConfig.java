/**    
 * 文件名：BaseConfig.java    
 *    
 * 版本信息：    
 * 日期：2015年9月2日    
 * Copyright Lightbulb Corporation 2015     
 * 版权所有    
 *    
 */
package com.kers.esmodel;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;


/**    
 *       
 * 类名称：BaseConfig    
 * 类描述：    
 * 创建人：Administrator    
 * 创建时间：2015年9月2日 下午4:14:13    
 * 修改人：Administrator    
 * 修改时间：2015年9月2日 下午4:14:13    
 * 修改备注：    
 * @version     
 *     
 */
public class BaseCommonConfig {
	public  static JestClient clientConfig() {
//		String hostUrl = "http://192.168.100.48:9200/";
		String hostUrl = "http://192.168.1.219:9200/";
		hostUrl="http://127.0.0.1:9200/";
		//ESClient esClient = new ESClient(hostUrl);

		ClientConfig clientConfig = new ClientConfig.Builder(hostUrl).multiThreaded(true).build();
		// Construct a new Jest client according to configuration via factory
		JestClientFactory factory = new JestClientFactory();
		factory.setClientConfig(clientConfig);
		JestClient jestClient = factory.getObject();
		return jestClient;
	}

}

