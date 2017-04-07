package com.gudeng.commerce.gd.m.controller.market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.enums.ResponseCodeEnum;
import com.gudeng.commerce.gd.m.service.MarketToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

/**
 * 市场列表Controller
 * @author caixu
 *
 */
@Controller
@RequestMapping("market")
public class MarketController extends MBaseController{

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MarketController.class);
	@Autowired
	private MarketToolService marketToolService;
	@Autowired
	public GdProperties gdProperties;
	/**
	 * 跳转市场列表页面
	 */
	@RequestMapping("financial")
	public String financial(HttpServletRequest request) throws Exception{
		return "H5/market/market_list";
	}
	  
	
	/**
	 * 获取市场列表
	 */
	@RequestMapping("/getMarketList")
	@ResponseBody
	public String getMarketListByMarket(HttpServletRequest request, HttpServletResponse response){
		try{
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("startRow", (Integer.valueOf(getRequest().getParameter("startRow")) - 1) * 10);
			paramsMap.put("endRow", 10);
			List<MarketDTO> marketList = marketToolService.getMarketList(paramsMap);
			String imgRootUrl = gdProperties.getProperties().getProperty("img.host.url");
			for (MarketDTO dto : marketList) {
				dto.setMarketImg(imgRootUrl+dto.getMarketImg());
			}
			return getResponseJson(marketList);
		}catch(Exception e){
			logger.error("我要贷款:", e); 
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
}
