/**
  * Copyright 2017 bejson.com 
  */
package com.kers.stock.bean;

/**
 * Auto-generated: 2017-03-22 18:12:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class QuoteBarBean {

    private String ret;
    private String msg;
    private QuoteBarJsonData quoteBarJsonData;
    public void setRet(String ret) {
         this.ret = ret;
     }
     public String getRet() {
         return ret;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setData(QuoteBarJsonData quoteBarJsonData) {
         this.quoteBarJsonData = quoteBarJsonData;
     }
     public QuoteBarJsonData getData() {
         return quoteBarJsonData;
     }

}