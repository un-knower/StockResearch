/**
  * Copyright 2017 bejson.com 
  */
package com.cmall.stock.bean;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2017-03-22 18:12:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class QuoteBarJsonResult {

    @JsonProperty("DATE")
    private Date date;
    @JsonProperty("ZQDM")
    private String zqdm;
    @JsonProperty("ZQJC")
    private String zqjc;
    @JsonProperty("TYPE")
    private String type;
    @JsonProperty("CLOSE_PRICE")
    private String closePrice;
    @JsonProperty("ZF")
    private String zf;
    @JsonProperty("VOLUMN")
    private String volumn;
    @JsonProperty("ZLW_PRICE")
    private String zlwPrice;
    @JsonProperty("ZCW_PRICE")
    private String zcwPrice;
    @JsonProperty("HSL")
    private String hsl;
    @JsonProperty("DP")
    private String dp;
    public void setDate(Date date) {
         this.date = date;
     }
     public Date getDate() {
         return date;
     }

    public void setZqdm(String zqdm) {
         this.zqdm = zqdm;
     }
     public String getZqdm() {
         return zqdm;
     }

    public void setZqjc(String zqjc) {
         this.zqjc = zqjc;
     }
     public String getZqjc() {
         return zqjc;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setClosePrice(String closePrice) {
         this.closePrice = closePrice;
     }
     public String getClosePrice() {
         return closePrice;
     }

    public void setZf(String zf) {
         this.zf = zf;
     }
     public String getZf() {
         return zf;
     }

    public void setVolumn(String volumn) {
         this.volumn = volumn;
     }
     public String getVolumn() {
         return volumn;
     }

    public void setZlwPrice(String zlwPrice) {
         this.zlwPrice = zlwPrice;
     }
     public String getZlwPrice() {
         return zlwPrice;
     }

    public void setZcwPrice(String zcwPrice) {
         this.zcwPrice = zcwPrice;
     }
     public String getZcwPrice() {
         return zcwPrice;
     }

    public void setHsl(String hsl) {
         this.hsl = hsl;
     }
     public String getHsl() {
         return hsl;
     }

    public void setDp(String dp) {
         this.dp = dp;
     }
     public String getDp() {
         return dp;
     }

}