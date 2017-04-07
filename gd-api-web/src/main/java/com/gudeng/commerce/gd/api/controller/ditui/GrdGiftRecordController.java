package com.gudeng.commerce.gd.api.controller.ditui;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ditui.GrdGiftDetailToolService;
import com.gudeng.commerce.gd.api.service.ditui.GrdGiftRecordToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
/**
 * 地面推广-礼物表-发放管理Controller
 * @author Cai.x
 *
 */
@Controller
@RequestMapping("/ditui")
public class GrdGiftRecordController extends GDAPIBaseController{
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(GrdGiftRecordController.class);	
	
	@Resource
	private GrdGiftRecordToolService grdGiftRecordToolService;
	@Resource
	private GrdGiftDetailToolService grdGiftDetailToolService;
	
	@Resource
	private GdProperties gd;
	/**
	 * 查询邀请的注册客户
	 * @param request
	 * @param response
	 */
	@RequestMapping("/inviteCustomer")
	public void findInviteCustomer(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String userIdStr = (String)GSONUtils.getJsonValueStr(jsonStr, "grdUserId");
			String dateStr = (String)GSONUtils.getJsonValueStr(jsonStr, "date");
			if(StringUtils.isBlank(userIdStr)){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createUserId", userIdStr);
			map.put("type", 3);
			map.put("orderTime", "orderTime");
			//默认查询当前月
			if("".equals(dateStr) || dateStr == null){
				map.put("date", DateUtil.getDate(new Date(), "yyy-MM"));
			}else if(!"0".equals(dateStr) && !"".equals(dateStr)){
				map.put("date", dateStr);
			}
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			
			GrdGiftDetailDTO dto = grdGiftDetailToolService.getTotalByUserId(map);
			List<GrdGiftDetailDTO> record = grdGiftDetailToolService.queryCreateOrderByConditionPage(map);
			//客户注册时间取错值  为了不让android重新改代码  由这边处理
			if(null != record && record.size() !=0){
				for (GrdGiftDetailDTO listDto : record) {
					if(null != listDto.getOrderTime()){
						listDto.setCreateTime(listDto.getOrderTime());
					}
				}
			}
			Map<String, Object> extra = new HashMap<String, Object>();
//			extra.put("nowMonth", DateUtil.getDate(new Date(), "MM"));
			if("".equals(dateStr) || dateStr == null){
				extra.put("nowMonth", DateUtil.getDate(new Date(), "MM"));//没有传入Date时，默认返回当前月
			}else if(!"0".equals(dateStr) && !"".equals(dateStr)){
				extra.put("nowMonth", dateStr.split("-")[1]);//有传入Date时，返回传入的月份
			}else if("0".equals(dateStr)){
				extra.put("nowMonth", "ALL");//全部时，直接传回ALL供APP展示
			}
			
			pageDTO.setRecordCount(dto.getTotalCount());
			pageDTO.initiatePage(dto.getTotalCount());
			pageDTO.setRecordList(record);
			extra.put("pageDto", pageDTO);
			result.setObject(extra);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response, "yyyy-MM-dd HH:mm:ss");
		}catch(Exception e){
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 我的促成订单
	 * @param request
	 * @param response
	 */
	@RequestMapping("/createOrder")
	public void createOrder(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String userIdStr = (String)GSONUtils.getJsonValueStr(jsonStr, "grdUserId");
			String dateStr = (String)GSONUtils.getJsonValueStr(jsonStr, "date");
			if(StringUtils.isBlank(userIdStr)){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createUserId", userIdStr);
			map.put("type", 2);
			map.put("orderTime", "orderTime");
			//默认查询当前月
			if("".equals(dateStr) || dateStr == null){
				map.put("date", DateUtil.getDate(new Date(), "yyy-MM"));
			}else if(!"0".equals(dateStr) && !"".equals(dateStr)){
				map.put("date", dateStr);
			}
			
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			GrdGiftDetailDTO dto = grdGiftDetailToolService.getTotalByUserId(map);
			List<GrdGiftDetailDTO> record = grdGiftDetailToolService.queryCreateOrderByConditionPage(map);
			
			pageDTO.setRecordCount(dto.getTotalCount());
			pageDTO.initiatePage(dto.getTotalCount());
			pageDTO.setRecordList(record);
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("totalCount", dto.getTotalCount());
			extra.put("totalMoney", dto.getTotalMoney());
			
//			extra.put("nowMonth", DateUtil.getDate(new Date(), "MM"));
			if("".equals(dateStr) || dateStr == null){
				extra.put("nowMonth", DateUtil.getDate(new Date(), "MM"));//没有传入Date时，默认返回当前月
			}else if(!"0".equals(dateStr) && !"".equals(dateStr)){
				extra.put("nowMonth", dateStr.split("-")[1]);//有传入Date时，返回传入的月份
			}else if("0".equals(dateStr)){
				extra.put("nowMonth", "ALL");//全部时，直接传回ALL供APP展示
			}	
			extra.put("pageDTO", pageDTO);
			result.setObject(extra);
			
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response, "yyyy-MM-dd HH:mm:ss");
		}catch(Exception e){
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
}
