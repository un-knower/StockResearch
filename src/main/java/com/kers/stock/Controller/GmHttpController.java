package com.kers.stock.Controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kers.stock.utils.HttpUtil;
import com.kers.stock.utils.TextUtil;

@RestController
public class GmHttpController {
	public final String goodsListUrl ="https://apimerch.cmall.com/restwsapis/open/goods/goodsList";
	public final String getModelShow ="https://apimerch.cmall.com/restwsapis/goods/getModelShow";
	public final String getSkuList = "https://apimerch.cmall.com/restwsapis/open/goods/getLevelSkuList";
	public final String cartCount = "https://apimerch.cmall.com/restwsapis/open/order/cartCount";
	public final String queryShoppingCartListByIds = "https://apimerch.cmall.com/restwsapis/open/order/queryShoppingCartListByIds";
	public final String creativeWritingList = "https://android.cmall.com/goodsSite/creativeWriting/creativeWritingList";

	
	public final String queryOrderByMchOrId = "https://apimerch.cmall.com/restwsapis/order/queryOrderByMchOrId";
	public final String saveShoppingCart = "https://apimerch.cmall.com/restwsapis/open/order/saveShoppingCart";
	public final String queryShoppingCartList = "https://apimerch.cmall.com/restwsapis/open/order/queryShoppingCartList";
	public final String editCartCount = "https://apimerch.cmall.com/restwsapis/open/order/editCartCount";
	public final String delCart = "https://apimerch.cmall.com/restwsapis/open/order/delCart";
	public final String getFreight = "https://apimerch.cmall.com/restwsapis/order/getFreight";
	public final String saveAddress = "https://apimerch.cmall.com/restwsapis/saveAddress";
	public final String deleteAddress = "https://apimerch.cmall.com/restwsapis/deleteAddress";
	public final String queryList = "https://apimerch.cmall.com/restwsapis/queryList";
	public final String editAddressDefault = "https://apimerch.cmall.com/restwsapis/editAddressDefault";
	public final String orderCheckSdk = "https://apimerch.cmall.com/restwsapis/orderCheckSdk";
	public final String orderPaybackSdk = "https://apimerch.cmall.com/restwsapis/orderPaybackSdk";
	public final String getWechatSign = "https://apimerch.cmall.com/restwsapis/getWechatSign";
	public final String getAlipaySign = "https://apimerch.cmall.com/restwsapis/getAlipaySign";
	public final String queryOrderDetailByOrNo = "https://apimerch.cmall.com/restwsapis/order/queryOrderDetailByOrNo";
	public final String orderDelete = "https://apimerch.cmall.com/restwsapis/order/orderDelete";
	
	@RequestMapping("/restwsapis/open/goods/goodsList")
    public String goodsList(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.get(getUrl(request , goodsListUrl));
        return json;
    }
	
	@RequestMapping("/restwsapis/goods/getModelShow")
    public String getModelShow(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.get(getUrl(request,getModelShow));
        return json;
    }
	
	@RequestMapping("/restwsapis/open/goods/getLevelSkuList")
    public String getLevelSkuList(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.get(getUrl(request,getSkuList));
        return json;
    }
	
	@RequestMapping("/restwsapis/open/order/cartCount")
    public String cartCount(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.get(getUrl(request,cartCount));
        return json;
    }
	
	@RequestMapping("/restwsapis/open/order/queryShoppingCartListByIds")
    public String queryShoppingCartListByIds(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.get(getUrl(request,queryShoppingCartListByIds));
        return json;
    }
	
	@RequestMapping("/goodsSite/creativeWriting/creativeWritingList")
    public String creativeWritingList(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.get(getUrl(request,creativeWritingList));
        return json;
    }
	
	@RequestMapping("/restwsapis/order/queryOrderByMchOrId")
    public String queryOrderByMchOrId(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(queryOrderByMchOrId, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/open/order/saveShoppingCart")
    public String saveShoppingCart(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(saveShoppingCart, postUrl(request));
		System.out.println(json);
        return json;
    }
	
	@RequestMapping("/restwsapis/open/order/queryShoppingCartList")
    public String queryShoppingCartList(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(queryShoppingCartList, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/open/order/editCartCount")
    public String editCartCount(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(editCartCount, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/open/order/delCart")
    public String delCart(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(delCart, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/order/getFreight")
    public String getFreight(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(getFreight, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/saveAddress")
    public String saveAddress(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(saveAddress, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/deleteAddress")
    public String deleteAddress(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(deleteAddress, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/queryList")
    public String queryList(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(queryList, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/editAddressDefault")
    public String editAddressDefault(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(editAddressDefault, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/orderCheckSdk")
    public String orderCheckSdk(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(orderCheckSdk, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/orderPaybackSdk")
    public String orderPaybackSdk(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(orderPaybackSdk, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/getWechatSign")
    public String getWechatSign(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(getWechatSign, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/getAlipaySign")
    public String getAlipaySign(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(getAlipaySign, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/order/queryOrderDetailByOrNo")
    public String queryOrderDetailByOrNo(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(queryOrderDetailByOrNo, postUrl(request));
        return json;
    }
	
	@RequestMapping("/restwsapis/order/orderDelete")
    public String orderDelete(HttpServletRequest request) throws Exception {
		String json = "";
		json = HttpUtil.post(orderDelete, postUrl(request));
        return json;
    }

	public String getUrl(HttpServletRequest request , String hurl){
		Map<String, Object> map = getMapByRequest(request);
		String url = "?";
		int i = 0;
		for(Map.Entry<String,Object> entry:map.entrySet()){
			if(i == 0){
				url = url+entry.getKey()+"="+entry.getValue();
			}else{
				url = url + "&" +entry.getKey()+"="+entry.getValue();
			}
			i++;
		}
		url = hurl+url;
		url=url.replaceAll(" ", "%20");
		System.out.println(url);
		return url;
	}
	
	public Map<String, String> postUrl(HttpServletRequest request){
		Map<String, Object> map = getMapByRequest(request);
		Map<String, String> returnMap = Maps.newHashMap();
		String ss= "";
		for(Map.Entry<String,Object> entry:map.entrySet()){
			ss= ss + "&"+entry.getKey()+"="+String.valueOf(entry.getValue());
			returnMap.put(entry.getKey(), String.valueOf(entry.getValue()));
		}
		System.out.println(ss);
		return returnMap;
	}
	
	public Map<String, Object> getMapByRequest(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> mapSource = new HashMap<String, Object>();
		if (parameterMap.size() > 0 && parameterMap != null) {
			for (String pakey : parameterMap.keySet()) {
				String[] value = parameterMap.get(pakey);
				// int partAddParamLevel = 0;
				for (int i = 0; i < value.length; i++) {
					String valueParam = value[i];
					mapSource.put(pakey, valueParam);
					// System.out.println(pakey+"======"+valueParam);
				}
			}
		}
		return mapSource;
	}
	
	public static void main(String[] args) {
		String url = "http://192.168.1.108:8080/cus/restwsapis/open/order/saveShoppingCart";
		Map<String, String> map = Maps.newHashMap();
		List<String> sss = TextUtil.readTxtFile("/Users/chensl/Downloads/ttt.txt");
		String cc = "";
		for (String string : sss) {
			cc = cc + string;
		}
		String[] a = cc.split("&");
		for (int i = 0; i < a.length; i++) {
			String[] b = a[i].split("=");
			if(b.length == 1){
				map.put(b[0], "");
			}else{
				map.put(b[0], b[1]);
			}
			
		}
		String json = HttpUtil.post(url, map);
		JSONObject obj = JSONObject.parseObject(json);
		String code = obj.getString("code");
		long result = obj.getLongValue("result");
		System.out.println(code);
		System.out.println(result);
				
	}
	
}
