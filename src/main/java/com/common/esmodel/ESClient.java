package com.common.esmodel;


import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
/**    
 *       
 * 类名称：ESClient    
 * 类描述：  ESClient
 * 创建人：jke    
 * 创建时间：2015年3月27日 下午5:06:49    
 * 修改人：Administrator    
 * 修改时间：2015年3月27日 下午5:06:49    
 * 修改备注：    
 * @version     
 *     
 */
public class ESClient {
	
	 public static void main(String[] args) {
		ESClient es = new ESClient();
		es.clientConfig();
	}
    private String hostUrl;
    private JestClient jestClient;
    public JestClient clientConfig() {
    	if(jestClient==null){
    		// Configuration
    		ClientConfig clientConfig = new ClientConfig.Builder(hostUrl).multiThreaded(true).build();
    		
    		// Construct a new Jest client according to configuration via factory
    		JestClientFactory factory = new JestClientFactory();
    		factory.setClientConfig(clientConfig);
    		jestClient = factory.getObject();
    	}
    	 return jestClient;
    }


    public void closeJestClient(){
        if(null != jestClient)
        	jestClient.shutdownClient();
    }


	public String getHostUrl() {
		return hostUrl;
	}

	public JestClient getJestClient() {
		return jestClient;
	}


	public void setJestClient(JestClient jestClient) {
		this.jestClient = jestClient;
	}


	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}


	public ESClient() {
		
		super();
		// TODO Auto-generated constructor stub
		
	}


	public ESClient(String hostUrl) {
		super();
		this.hostUrl = hostUrl;
	}
	
}