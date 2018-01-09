package com.cmall.stock.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmall.stock.vo.StockBasePageInfo;

@Controller
@RequestMapping("/")  
public class HtmlController{
	
	@RequestMapping("/")
    public String  home(Model model) throws Exception {
        return "/index";
    }
	
	@RequestMapping("/datas/stockinfo")
    public String  dataStockInfo(Model model , StockBasePageInfo info) throws Exception {
		model.addAttribute("info", info);
        return "/datas/stockinfo";
    }
	
	@RequestMapping("/datas/storetrailer")
    public String  dataStoretrailer(Model model) throws Exception {
        return "/datas/storetrailer";
    }
	
	@RequestMapping("/datas/jyfx")
    public String  dataJyfx(Model model) throws Exception {
        return "/datas/jyfx";
    }
    
	@RequestMapping("/datas/dzsj")
    public String  dataDzsj(Model model) throws Exception {
        return "/datas/dzsj";
    }
	
	@RequestMapping("/login")
    public String  login(Model model) throws Exception {
        return "/login";
    }
   
}