package com.kers.stock.Controller;

import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chuanglan.demo.SmsSendDemo;
import com.kers.esmodel.BaseCommonConfig;
import com.kers.esmodel.UtilEs;
import com.kers.stock.utils.TimeUtils;
import com.kers.stock.vo.IphoneCodeVo;
import com.kers.stock.vo.UserVo;
import com.kers.stock.vo.ValuesVo;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

@RestController
public class GmCusUserController extends BaseController{
	public final  String ES_INDEX_CUS_IPHONE="iphonecode";
	public final  String ES_INDEX_CUS_IPHONE_TYPE="code";
	
	@RequestMapping("/kv/login")
    public Map<String,Object> login(IphoneCodeVo info) throws Exception {
		Map<String,Object> map = Maps.newHashMap();
		map.put("code", "1000");
		if(null == info.getIphoneNo() || info.getIphoneNo().equals("")){
			map.put("message", "手机号不能为空");
			return map;
		}
		if(null == info.getCode() || info.getCode().equals("")){
			map.put("message", "验证码不能为空");
			return map;
		}
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		IphoneCodeVo vo = getBreakPointDatas(jestClient , info.getIphoneNo());
		if(null == vo || null == vo.getCode() || !vo.getCode().equals(info.getCode())){
			map.put("message", "验证码错误");
			return map;
		}
		String d = TimeUtils.getDate();
		long hm = TimeUtils.diffTime(d,vo.getTime());
		double fz = (double)(hm/1000/60);
		if((fz) > 15){
			map.put("message", "验证码失效");
			return map;
		}
		//判断验证码时间是否超过15分钟
		UserVo user = new UserVo();
		user.setPhoneNo(vo.getIphoneNo());
		map.put("code", "200");
		map.put("message", "OK");
		map.put("result", user);
        return map;
    }

	@RequestMapping("/kv/sendVerification")
    public Map<String,Object> sendVerification(IphoneCodeVo code) throws Exception {
		Map<String,Object> map = Maps.newHashMap();
		map.put("code", "1000");
		if(null == code.getIphoneNo() || code.getIphoneNo().equals("")){
			map.put("message", "手机号不能为空");
			return map;
		}
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		IphoneCodeVo vo = getBreakPointDatas(jestClient , code.getIphoneNo());
		String d = TimeUtils.getDate();
		if(null != vo && null!= vo.getTime()){
			long hm = TimeUtils.diffTime(d,vo.getTime());
			double m = hm / 1000;
			if(m < 60){
				map.put("message", "60内不能重复获取验证码");
				return map;
			}
		}
		//判断两个时间的差值
		//生产随机6位码
		String sj = (int)((Math.random()*9+1)*100000) + "";
		code.setCode(sj);
		//发送验证码
		
		String f = SmsSendDemo.sendM(code.getIphoneNo(), sj);
		
		if(null == f || "".equals(f)){
			map.put("message", "短信发送失败");
			return map;
		}else{
			JSONObject obj = (JSONObject) JSON.parse(f);
			if(!obj.get("code").equals("0")){
				map.put("message", obj.get("errorMsg"));
				return map;
			}
		}
		code.setTime(d);
		insBatchBean(code, jestClient, ES_INDEX_CUS_IPHONE);
		map.put("code", "200");
		map.put("message", "验证码发送成功");
        return map;
    }
	
	public void insBatchBean(IphoneCodeVo bean, JestClient jestClient, String indexIns) throws Exception {
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		Index index = new Index.Builder(bean).index(indexIns).type(ES_INDEX_CUS_IPHONE_TYPE)
					.id(bean.getIphoneNo()).build();// type("walunifolia").build();
		bulkBuilder.addAction(index);
		jestClient.execute(bulkBuilder.build());
		bulkBuilder = new Bulk.Builder();
		jestClient.execute(bulkBuilder.build());
	}
	
	public IphoneCodeVo getBreakPointDatas(JestClient jestClient , String key){
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.termQuery("iphoneNo",key));
		SearchSourceBuilder searchSourceBuilder = ssb.query(query);
		Search selResult = UtilEs.getSearch(searchSourceBuilder, ES_INDEX_CUS_IPHONE, ES_INDEX_CUS_IPHONE_TYPE,
				0, 1);
		System.out.println(searchSourceBuilder.toString());
		IphoneCodeVo bean = new IphoneCodeVo();
		try {
			JestResult results = jestClient.execute(selResult);
			if(results.isSucceeded()){
				bean = results.getSourceAsObject(IphoneCodeVo.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	@RequestMapping("/kv/sendVerificationTest")
    public Map<String,Object> sendVerificationTest(IphoneCodeVo code) throws Exception {
		Map<String,Object> map = Maps.newHashMap();
		map.put("code", "1000");
		if(null == code.getIphoneNo() || code.getIphoneNo().equals("")){
			map.put("message", "手机号不能为空");
			return map;
		}
		final JestClient jestClient = BaseCommonConfig.clientConfig();
		IphoneCodeVo vo = getBreakPointDatas(jestClient , code.getIphoneNo());
		String d = TimeUtils.getDate();
		if(null != vo && null!= vo.getTime()){
			long hm = TimeUtils.diffTime(d,vo.getTime());
			double m = hm / 1000;
			if(m < 60){
				map.put("message", "60内不能重复获取验证码");
				return map;
			}
		}
		//判断两个时间的差值
		//生产随机6位码
		String sj = (int)((Math.random()*9+1)*100000) + "";
		code.setCode(sj);
		//发送验证码
		
		String f = SmsSendDemo.sendM(code.getIphoneNo(), sj);
		
		if(null == f || "".equals(f)){
			map.put("message", "短信发送失败");
			return map;
		}else{
			JSONObject obj = (JSONObject) JSON.parse(f);
			if(!obj.get("code").equals("200")){
				map.put("message", obj.get("errorMsg"));
				return map;
			}
		}
		code.setTime(d);
		//insBatchBean(code, jestClient, ES_INDEX_CUS_IPHONE);
		map.put("code", "200");
		map.put("message", "验证码发送成功");
        return map;
    }
	
}
