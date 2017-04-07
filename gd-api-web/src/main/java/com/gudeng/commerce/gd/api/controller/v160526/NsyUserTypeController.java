package com.gudeng.commerce.gd.api.controller.v160526;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NsyUserTypeEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.NsyUserTypeDto;

/**
 * 农批商控制中心
 * @author TerryZhang
 */

@Controller
@RequestMapping("v160526/nsyUser")
public class NsyUserTypeController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(NsyUserTypeController.class);
	
	@Autowired
	public MemberToolService memberToolService;
	
	/**
	 *  1下游批发商,2生鲜超市,3学校食堂,4食品加工工厂,5社区门店,6餐厅,7垂直生鲜,8其它
	 * @param request
	 * @param response
	 * @param businessId
	 */
	@RequestMapping("/getNsyUserTypeList")
	public void getNsyUserTypeList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		List<NsyUserTypeDto> nsyUserTypeList = new ArrayList<NsyUserTypeDto>();
		for (NsyUserTypeEnum item :NsyUserTypeEnum.values()){
			NsyUserTypeDto dto = new NsyUserTypeDto();
			dto.setNsyUserTypeCode(item.getNsyUserType().toString());
			dto.setNsyUserTypeName(item.getNsyUserTypeName());
			nsyUserTypeList.add(dto);
		}
		result.setObject(nsyUserTypeList);
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}	
	
	@RequestMapping("/updateNsyUserType")
	public void updateShopInfo(HttpServletRequest request, HttpServletResponse response ) {
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			String nsyUserType = (String)GSONUtils.getJsonValueStr(jsonStr, "nsyUserType");
			if(StringUtils.isBlank(memberId)){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			if(StringUtils.isBlank(nsyUserType)){
				setEncodeResult(result, ErrorCodeEnum.NSYUSERTYPE_IS_EMPTY, request, response);
				return;
			}
			memberToolService.updateNsyUserType(Long.valueOf(memberId), Integer.valueOf(nsyUserType));
		
		} catch (Exception e) {
			logger.error("编辑店铺异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
}

