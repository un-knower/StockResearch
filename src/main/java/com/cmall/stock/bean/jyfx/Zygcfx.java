/**
  * Copyright 2017 bejson.com 
  */
package com.cmall.stock.bean.jyfx;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2017-12-21 12:50:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Zygcfx {

    private Date rq;
    private List<Hy> hy;
    private List<String> qy;
    private List<Cp> cp;
    public void setRq(Date rq) {
         this.rq = rq;
     }
     public Date getRq() {
         return rq;
     }

    public void setHy(List<Hy> hy) {
         this.hy = hy;
     }
     public List<Hy> getHy() {
         return hy;
     }

    public void setQy(List<String> qy) {
         this.qy = qy;
     }
     public List<String> getQy() {
         return qy;
     }

    public void setCp(List<Cp> cp) {
         this.cp = cp;
     }
     public List<Cp> getCp() {
         return cp;
     }

}