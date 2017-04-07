package com.gudeng.commerce.gd.api.controller;

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

import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.api.service.ReMemForCustToolService;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.supplier.dto.ReMemForCustDTO;

/**
 * 农批商控制中心
 * 
 * @author TerryZhang
 */
@Controller
@RequestMapping("callstatiStics")
public class CallStatisticsController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CallStatisticsController.class);

	@Autowired
	public CallstatiSticsToolService callstatiSticsToolService;

	@Autowired
	public ReMemForCustToolService custToolService;

	@Autowired
	public BusinessBaseinfoToolService baseinfoToolService;
	
	@Autowired
	private GdProperties gdProperties;
	@Autowired
	private NstApiCommonService nstApiCommonService;

	@RequestMapping("/addCallstatiStics")
	public void add(HttpServletRequest request, HttpServletResponse response, CallstatiSticsDTO callstatiSticsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			// 如果不是农速通的拨打来源,需要更新客户关系
			if (!"2".equals(callstatiSticsDTO.getSysCode())) {
				addReMemForCust(callstatiSticsDTO);
			}

			if ("1".equals(callstatiSticsDTO.getSysCode()) && null != callstatiSticsDTO.getBusinessId()) {
				BusinessBaseinfoDTO baseinfoDTO = baseinfoToolService
						.getById(callstatiSticsDTO.getBusinessId().toString());
				if (baseinfoDTO != null
						&& callstatiSticsDTO.getMemberId().longValue() == baseinfoDTO.getUserId().longValue()) {
					// 自己拨打自己的
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				}
			}

			ErrorCodeEnum statusCode = callstatiSticsToolService.insert(callstatiSticsDTO);
			result.setStatusCode(statusCode.getStatusCode());
			result.setMsg(statusCode.getStatusMsg());

			//封装数据调用农速通拨打电话统计(当source为订单物流详情页面才需要)
			if("PTPSWLXQ".equals(callstatiSticsDTO.getSource())){
				String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.CALLSTATISTICS_NST_SUCC.getUri();
				Map<String, String> requestParamMap = new HashMap<String,String>();
				String memberId = callstatiSticsDTO.getMemberId().toString();
				String callRole = "";
				String source ="";
				requestParamMap.put("token", nstApiCommonService.getNstToken(memberId));
				//gudeng系统中1农商友，3农村批商  nst系统中 农商友5,农批商3
				if("1".equals(callstatiSticsDTO.getSysCode())){
					callRole = "5";
					source = "9"; //9代表农商友订单详情
				}else if("3".equals(callstatiSticsDTO.getSysCode())){
					callRole = "3";
					source = "11"; //11:农批商订单详情
				}
				requestParamMap.put("callRole", callRole);
				requestParamMap.put("source", source);
				requestParamMap.put("fromCode", "1");
				requestParamMap.put("s_Mobile", callstatiSticsDTO.getS_Mobile());
				requestParamMap.put("b_memberid", callstatiSticsDTO.getB_memberId().toString());
				requestParamMap.put("s_Name", callstatiSticsDTO.getS_Name());
				requestParamMap.put("memberid", memberId);
				requestParamMap.put("e_Mobile", callstatiSticsDTO.getE_Mobile());
				requestParamMap.put("e_Name", callstatiSticsDTO.getE_Name());
				NstBaseResponseDTO dto=nstApiCommonService.sendNstRequest(requestParamMap, url);
				logger.info(dto.getMsg());
			}
		} catch (Exception e) {
			logger.error("添加拨打电话记录异常", e);
		}
		renderJson(result, request, response);
	}

	/**
	 * 更新re_mem_for_cust表数据
	 * 
	 * @throws Exception
	 * 
	 * @by semon
	 */
	private void addReMemForCust(CallstatiSticsDTO callstatiSticsDTO) throws Exception {
		ReMemForCustDTO custDTO = new ReMemForCustDTO();

		if (StringUtils.isBlank(callstatiSticsDTO.getSysCode()))
			return;

		if (callstatiSticsDTO.getBusinessId() == null) {
			logger.error("拨打电话 农商友打给农批商----------------------->商家Id为空");
			return;
		}
		BusinessBaseinfoDTO baseinfoDTO = null;
		String tempCode = callstatiSticsDTO.getSysCode();
		baseinfoDTO = baseinfoToolService.getById(callstatiSticsDTO.getBusinessId().toString());
		
		/*
		 * 如果存在b_memberId 则用b_memberId查询
		 */
		Long b_meberId = callstatiSticsDTO.getB_memberId();
		if (b_meberId != null && baseinfoDTO == null) {
			baseinfoDTO = baseinfoToolService.getByUserId(b_meberId.toString());
			
			if (baseinfoDTO == null) {
				logger.error("拨打电话接口 供应商打给农批商对应用户不存在----------------------->不存在对应的农批商");
				return;
			}
		}
		
		if (baseinfoDTO == null) {
			logger.error("拨打电话接口 农商友打给农批商----------------------->不存在此商铺");
			return;
		}
		// 如果syscode=1,农商友打给农批商，属于农批商客户
		if ("1".equals(tempCode)) {
			// 自己拨打自己的
			if (!callstatiSticsDTO.getMemberId().equals(baseinfoDTO.getUserId())) {
				// 设置主叫
				custDTO.setCustMemberId(callstatiSticsDTO.getMemberId());
				custDTO.setBusiMemberId(baseinfoDTO.getUserId());
				custDTO.setType("2");
				custDTO.setCreateUserId(callstatiSticsDTO.getMemberId().toString());
				custDTO.setUpdateUserId(callstatiSticsDTO.getMemberId().toString());
				// 插入数据时做判断，是否存在该客户，如果存在，就更新更新时间
				custToolService.addCustomerMember(custDTO);
			}

		}
		// 如果syscode=3,两种情况（农批商打给供应商，农批商打给农商友）
		if ("3".equals(tempCode)) {
			// 农批商打给供应商，相互属于对方的客户，所以插入两次
			if ("1".equals(callstatiSticsDTO.getFromCode())) {
				// 设置客户Id
				custDTO.setCustMemberId(callstatiSticsDTO.getMemberId());
				// 设置
				custDTO.setBusiMemberId(baseinfoDTO.getUserId());
				custDTO.setType("1");
				custDTO.setCreateUserId(callstatiSticsDTO.getMemberId().toString());
				custDTO.setUpdateUserId(callstatiSticsDTO.getMemberId().toString());
				// 插入数据时做判断，是否存在该客户，如果存在，就更新更新时间
				custToolService.addCustomerMember(custDTO);

				// 设置客户Id
				custDTO.setCustMemberId(baseinfoDTO.getUserId());
				// 设置
				custDTO.setBusiMemberId(callstatiSticsDTO.getMemberId());
				custDTO.setType("1");
				custDTO.setCreateUserId(callstatiSticsDTO.getMemberId().toString());
				custDTO.setUpdateUserId(callstatiSticsDTO.getMemberId().toString());
				// 插入数据时做判断，是否存在该客户，如果存在，就更新更新时间
				custToolService.addCustomerMember(custDTO);

			} else {// 农批商打给农商友
					// 设置客户Id
				custDTO.setCustMemberId(callstatiSticsDTO.getB_memberId());
				// 设置主叫ID
				custDTO.setBusiMemberId(baseinfoDTO.getUserId());
				custDTO.setType("2");
				custToolService.addCustomerMember(custDTO);
			}

		}

		// 如果syscode=4,供应商打给农批商，属于供应商客户也属于农批商客户
		if ("4".equals(tempCode)) {
			// 设置主叫
			custDTO.setCustMemberId(callstatiSticsDTO.getMemberId());
			// 设置被叫ID
			custDTO.setBusiMemberId(baseinfoDTO.getUserId());
			custDTO.setType("1");
			custDTO.setCreateUserId(callstatiSticsDTO.getMemberId().toString());
			custDTO.setUpdateUserId(callstatiSticsDTO.getMemberId().toString());
			// 插入数据时做判断，是否存在该客户，如果存在，就更新更新时间
			custToolService.addCustomerMember(custDTO);

			// 设置主叫
			custDTO.setCustMemberId(baseinfoDTO.getUserId());
			// 设置被叫ID
			custDTO.setBusiMemberId(callstatiSticsDTO.getMemberId());
			custDTO.setType("1");
			custDTO.setCreateUserId(callstatiSticsDTO.getMemberId().toString());
			custDTO.setUpdateUserId(callstatiSticsDTO.getMemberId().toString());
			// 插入数据时做判断，是否存在该客户，如果存在，就更新更新时间
			custToolService.addCustomerMember(custDTO);
		}

	}

	@RequestMapping("/addNstCallstatiStics")
	public void addNst(HttpServletRequest request, HttpServletResponse response, CallstatiSticsDTO callstatiSticsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			callstatiSticsToolService.insertNst(callstatiSticsDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("添加拨打电话记录异常", e);
		}
		renderJson(result, request, response);
	}
}
