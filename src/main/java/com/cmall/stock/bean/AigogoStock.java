/**
  * Copyright 2017 bejson.com 
  */
package com.cmall.stock.bean;
import java.util.List;

/**
 * Auto-generated: 2017-07-28 11:16:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AigogoStock {

    private String success;
    private List<AigogoStockData> data;
    public void setSuccess(String success) {
         this.success = success;
     }
     public String getSuccess() {
         return success;
     }

    public void setData(List<AigogoStockData> data) {
         this.data = data;
     }
     public List<AigogoStockData> getData() {
         return data;
     }

}