package com.gudeng.commerce.gd.api.controller.v161027;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.MemberInfoInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberPageStatisticToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;

@Controller
@RequestMapping("/v1/statis")
public class PageStatisticV01Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PageStatisticV01Controller.class);
	@Autowired
	private MemberToolService memberToolService;
	@Autowired
	public MemberPageStatisticToolService pageToolService;
	@Autowired
	public PvStatisticBusinessToolService pvStatisticBusinessToolService;
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	
	@RequestMapping("/browseGoldMedal")
	public void browseGoldMedal(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			MemberInfoInputDTO inputDTO = (MemberInfoInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberInfoInputDTO.class);
			Integer memberId = ParamsUtil.getIntFromString(inputDTO.getMemberId());
			
			Map<String, Object> map = new HashMap<>();
			Integer memberGrade = 0;
			if(memberId != 0){
				MemberBaseinfoDTO memberInfo = memberToolService.getById(memberId.toString());
				memberGrade = memberInfo.getMemberGrade() == null ? 0 : memberInfo.getMemberGrade();
				if(memberGrade == 1){
					map.put("expireTime", DateUtil.getDate(memberInfo.getExpireTime(), DateUtil.DATE_FORMAT_DATEONLY));
					map.put("validTime", DateUtil.getDate(memberInfo.getValidTime(), DateUtil.DATE_FORMAT_DATEONLY));
				}
				map.put("memberGrade", memberGrade);
			}
			
			if(memberGrade == 0){
				//插入用户浏览金牌会员统计
				PageStatisMemberEntity pageStatisMemberEntity = new PageStatisMemberEntity();
				pageStatisMemberEntity.setMemberId(memberId == 0 ? null : memberId.longValue());
				pageStatisMemberEntity.setPageType("1");
				pageToolService.addMemberPage(pageStatisMemberEntity);
			}
			result.setObject(map);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]增加浏览金牌供应商统计异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/browseShopDetail")
	public void browseShopDetail(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String businessId = (String)GSONUtils.getJsonValueStr(jsonStr, "businessId");
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			
			if(StringUtils.isBlank(businessId)){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getById(businessId);
			if(dto==null || dto.getBusinessId()==null){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			//增加商铺详情页浏览量
			if(StringUtils.isNotBlank(memberId) && memberId.equals(dto.getUserId().toString())){
				logger.info("User is browsing his own shop, memberId: " + memberId);
			}else{
				BusinessPvStatisDTO businessPvStatisDTO = new BusinessPvStatisDTO();
				businessPvStatisDTO.setAddPv(CommonUtils.getNumFromRange(1, 4));
				businessPvStatisDTO.setBusinessId(Long.parseLong(businessId));
				businessPvStatisDTO.setFromPage("3");
				pvStatisticBusinessToolService.addPv(businessPvStatisDTO);
			}
		}catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
}
