/**
  * Copyright 2017 bejson.com 
  */
package com.kers.stock.bean;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2017-03-22 18:12:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class QuoteBarJsonData {

    @JsonProperty("totalPage")
    private int totalpage;
    @JsonProperty("curPage")
    private String curpage;
    private List<QuoteBarJsonResult> quoteBarJsonResult;
    public void setTotalpage(int totalpage) {
         this.totalpage = totalpage;
     }
     public int getTotalpage() {
         return totalpage;
     }

    public void setCurpage(String curpage) {
         this.curpage = curpage;
     }
     public String getCurpage() {
         return curpage;
     }

    public void setResult(List<QuoteBarJsonResult> quoteBarJsonResult) {
         this.quoteBarJsonResult = quoteBarJsonResult;
     }
     public List<QuoteBarJsonResult> getResult() {
         return quoteBarJsonResult;
     }

}