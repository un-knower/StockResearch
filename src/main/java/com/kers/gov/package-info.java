/**    
 * Copyright 2014-2018 artbulb Group.：    
 * Date：2018年1月26日    
 *    
 */
/**
 * 

 上海银行间同业拆放利率（Shanghai Interbank Offered Rate，简称Shibor），以位于上海的全国银行间同业拆借中心为技术平台计算、
 发布并命名，是由信用等级较高的银行组成报价团自主报出的人民币同业拆出利率计算确定的算术平均利率，是单利、无担保、批发性利率。
 目前，对社会公布的Shibor品种包括隔夜、1周、2周、1个月、3个月、6个月、9个月及1年。
  Shibor报价银行团现由18家商业银行组成。报价银行是公开市场一级交易商或外汇市场做市商，
  在中国货币市场上人民币交易相对活跃、信息披露比较充分的银行。中国人民银行成立Shibor工作小组，
  依据《上海银行间同业拆放利率（Shibor）实施准则》确定和调整报价银行团成员、监督和管理Shibor运行、
  规范报价行与指定发布人行为。全国银行间同业拆借中心受权Shibor的报价计算和信息发布。每个交易日根据各报价行的报价，
  剔除最高、最低各4家报价，对其余报价进行算术平均计算后，得出每一期限品种的Shibor，并于11:00对外发布。
Shibor的意义在于，这是一个了解银行资金是否充足的晴雨表：
Shibor ↓下行，意味着银行资金充足，市场偏宽松 。
Shibor ↑上升，意味着央行上调存准率或者有上调预期。

 * 
 */
package com.kers.gov;

/***
 * http://www.shibor.org/shibor/web/html/downLoad.html?nameNew=
 * Historical_Shibor_Data_2018.xls&downLoadPath=data&nameOld=Shibor数据2018.xls&
 * shiborSrc=http://www.shibor.org/shibor/
 * http://www.shibor.org/shibor/web/html/downLoad.html?nameNew=
 * Historical_Shibor_Tendency_Data_2018.xls&downLoadPath=data&nameOld=Shibor%E6%
 * 95%B0%E6%8D%AE2018.xls&shiborSrc=http://www.shibor.org/shibor/
 * 
 * 
 *    http://www.chinamoney.com.cn/fe-c/shiborHistoryChartAction.do?method=initPage&termId=100001

 * 
 * 
 * 
 * http://vip.stock.finance.sina.com.cn/q/go.php/vIR_RatingNewest/index.phtml
 * 
 * 
 *  *     http://www.drc.gov.cn/dcyjbg/
 *     国务院研究报告
 *     融资融券
 *      沪港通 数据
 *     http://data.eastmoney.com/hsgt/index.html
 *     http://data.eastmoney.com/zjlx/dpzjlx.html
 *     
 *     http://stock.eastmoney.com/news/1423,20110101117172217.html
 *     货币净投放与净回笼	 周/月
 *     http://www.chinamoney.com.cn/fe/Channel/35768
 *     央行公开市场操作			
 *     http://www.chinamoney.com.cn/fe/Channel/35687
 *     
 *     http://www.chinamoney.com.cn/fe-c/shiborHistoryChartAction.do?method=initPage&termId=100001
 *     
 *     https://baijiahao.baidu.com/s?id=1587370063588831169&wfr=spider&for=pc
 */