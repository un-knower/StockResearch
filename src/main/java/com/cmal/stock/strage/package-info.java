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
 *002016
 *http://quotes.money.163.com/f10/dbfx_000513.html
{"query":{"bool":{"must":[{"term":{"2017.date":"2017-11-08"}},{"range":{"2017.macd":{"gt":"0"}}},{"range":{"2017.rises":{"gt":"0"}}},{"range":{"2017.nextRises":{"gt":"3"}}},{"range":{"2017.upDateNum":{"gt":"2"}}},{"range":{"2017.macdNum":{"gt":"2"}}}],"must_not":[],"should":[]}},"from":0,"size":50,"sort":[],"facets":{}}

 *
 *
 *
 *{"query":{"bool":{"must":[{"term":{"2017.date":"2017-11-13"}},{"range":{"2017.macd":{"gt":"0"}}},{"range":{"2017.rises":{"gt":"0","to":"1"}}},{"range":{"2017.upDateNum":{"gt":"2"}}},{"range":{"2017.macdNum":{"gt":"2"}}},{"range":{"2017.j":{"gte":"70"}}},{"range":{"2017.nextRises":{}}},{"range":{"2017.upRises":{"gte":"3"}}}],"must_not":[],"should":[]}},"from":0,"size":50,"sort":[],"facets":{}}



 从下至上 macd转红    即DIFF<0  DEA<= 0  macd>0
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *主力流入分析
 *http://data.eastmoney.com/zjlx/300038.html
 *
 *
 *
 *板块财报信息
 *http://quotes.money.163.com/data/caibao/bkyjyl_20170930.html
 
 *行业信息
 *http://quotes.money.163.com/old/#query=hy001000&DataType=HS_RANK&sort=PERCENT&order=desc&count=24&page=0
 *
 *
 *
 *
 *辅助数据
 *
 *
 *http://ac.sci99.com/dynamic/
 *http://ac.sci99.com/dynamic/api/getalldata.ashx?isexport=1&type=1&date=2017-12-14&name=
 *
 *
 *大宗商品关联股份
 *http://www.yuncaijing.com/search/s.html
 *
 *
 *
 *大蓝筹
 *
 *蓝筹股也乘机拉升，许多蓝筹再创新高，比如复星医药（600196），中国巨石（600176），新城控股（601155），华鲁恒升(600426),华海药业（600521），利尔化学（002258），晨光文具（603899），科伦药业（002422），海大集团（002311）
 *
 *
 *
 */
