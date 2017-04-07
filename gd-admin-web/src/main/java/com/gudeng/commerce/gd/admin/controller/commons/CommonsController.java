package com.gudeng.commerce.gd.admin.controller.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.dto.KeyValuePair;
import com.gudeng.commerce.gd.admin.service.GrdMemberToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserTeamDTO;

@Controller
@RequestMapping("commons")
public class CommonsController extends AdminBaseController {

	@Autowired
	public MarketManageService marketManageService;
	
	/**
	 * 地推人员服务类
	 */
	@Autowired
	public GrdMemberToolService grdMemberToolService;

	private static final String SIGNAL_HAS_ALL = "1" ;
	private static final String CHANGE_TEXT_YES = "1" ;
	
	@ResponseBody
	@RequestMapping("marketPairs")
	public String queryProvince(String signal, String changeText, String text) {
		List<MarketDTO> list = null;
		List<KeyValuePair> pairs = new ArrayList<KeyValuePair>();
		try {
			list = marketManageService.getAllByStatus("0");
			KeyValuePair pair = null ;
			if (StringUtils.isEmpty(signal) || SIGNAL_HAS_ALL.equalsIgnoreCase(signal) ){
				pair = new KeyValuePair();
				pair.setKeyString("");
				if (CHANGE_TEXT_YES.equalsIgnoreCase(changeText) && StringUtils.isNotEmpty(text)){
					pair.setValueString(text);
				}else {
					pair.setValueString("全部"); 
				}
				pairs.add(pair);
			}
			for(MarketDTO item : list){
				pair = new KeyValuePair();
				pair.setKeyString(String.valueOf(item.getId()));
				pair.setValueString(item.getMarketName());
				pairs.add(pair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(pairs);
	}

	@ResponseBody
	@RequestMapping("userteams")
	public String transferToEditorPage(String marketId, String signal, String changeText, String text) throws Exception{
		//查询市场下所有的团队
		Map<String, Object> params = new HashMap<String, Object>();
		List<KeyValuePair> pairs = new ArrayList<KeyValuePair>();
		try {
			params.put("marketId", marketId);
			List<GrdMemberDTO> giftteamList = grdMemberToolService.getChildTeamInfo(params);
			KeyValuePair pair = null ;
			if (StringUtils.isEmpty(signal) || SIGNAL_HAS_ALL.equalsIgnoreCase(signal) ){
				pair = new KeyValuePair();
				pair.setKeyString("");
				if (CHANGE_TEXT_YES.equalsIgnoreCase(changeText) && StringUtils.isNotEmpty(text)){
					pair.setValueString(text);
				}else {
					pair.setValueString("全部"); 
				}
				pairs.add(pair);
			}
			for(GrdMemberDTO item : giftteamList){
				pair = new KeyValuePair();
				pair.setKeyString(String.valueOf(item.getGiftteamId()));
				pair.setValueString(item.getGiftteamName());
				pairs.add(pair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(pairs);
	}
	
	
}
