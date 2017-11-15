/**    
 * Copyright 2014-2017 artbulb Group.：    
 * Date：2017年11月8日    
 *    
 */
/**    
 *       
 * ClassName：package-info    
 * Description：    
 * author ：admin    
 * date ：2017年11月8日 下午9:55:42    
 * Modified   person：admin    
 * Modified date：2017年11月8日 下午9:55:42    
 * Modify remarks：    
 * @version     V1.0
 *     
 */
package com.cmal.stock.strage;

/**
 *
 *strange1 
 *macd 连续两天大于0
 *当天涨幅大于0
 *连续涨至少两天
 *
{"query":{"bool":{"must":[{"term":{"2017.date":"2017-11-08"}},{"range":{"2017.macd":{"gt":"0"}}},{"range":{"2017.rises":{"gt":"0"}}},{"range":{"2017.nextRises":{"gt":"3"}}},{"range":{"2017.upDateNum":{"gt":"2"}}},{"range":{"2017.macdNum":{"gt":"2"}}}],"must_not":[],"should":[]}},"from":0,"size":50,"sort":[],"facets":{}}

 *
 *
 *
 *{"query":{"bool":{"must":[{"term":{"2017.date":"2017-11-13"}},{"range":{"2017.macd":{"gt":"0"}}},{"range":{"2017.rises":{"gt":"0","to":"1"}}},{"range":{"2017.upDateNum":{"gt":"2"}}},{"range":{"2017.macdNum":{"gt":"2"}}},{"range":{"2017.j":{"gte":"70"}}},{"range":{"2017.nextRises":{}}},{"range":{"2017.upRises":{"gte":"3"}}}],"must_not":[],"should":[]}},"from":0,"size":50,"sort":[],"facets":{}}



 从下至上 macd转红    即DIFF<0  DEA<= 0  macd>0
 *
 *
 */
