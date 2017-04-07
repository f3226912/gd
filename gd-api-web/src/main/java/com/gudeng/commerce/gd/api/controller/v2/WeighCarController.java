package com.gudeng.commerce.gd.api.controller.v2;



import java.util.HashMap;
import java.util.List;
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
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.WeighCarParamsBean;
import com.gudeng.commerce.gd.api.service.CarBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ReCarMemberToolService;
import com.gudeng.commerce.gd.api.service.WeighCarToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarOrderDTO;

/**
 * 过磅
 * @author wind
 *
 */
@Controller
@RequestMapping("/v2/weighCar")
public class WeighCarController extends GDAPIBaseController {

	private Logger logger = LoggerFactory.getLogger(WeighCarController.class);
	
	@Autowired
	private WeighCarToolService weighCarToolService;
	
	@Autowired
	private CarBaseinfoToolService carBaseinfoToolService;
	
	@Autowired
	private ReCarMemberToolService reCarMemberToolService;
	
	@Autowired
	private MemberToolService memberToolService;
	
	/**
	 * 根据手机号码获取最新一条过磅记录
	 * @param paramsBean
	 * @param request
	 * @param response
	 */
	@RequestMapping("getLast")
	public void getLastWeighCar(WeighCarParamsBean paramsBean, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		if(StringUtils.isBlank(paramsBean.getMobile())){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		try {
			MemberBaseinfoDTO memberDTO = memberToolService.getByAccount(paramsBean.getMobile());
			if(memberDTO == null){
				memberDTO = memberToolService.getByMobile(paramsBean.getMobile());
			}
			if(memberDTO == null){
				setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", paramsBean.getMobile());
			if(StringUtils.isNotBlank(paramsBean.getStatus())){
				map.put("status", paramsBean.getStatus());
			}
			if(StringUtils.isNotBlank(paramsBean.getType())){
				map.put("type", paramsBean.getType());
			}
			WeighCarDTO weighCar = weighCarToolService.getLastWeighCar(map);
			
			//返回用户姓名，用户最新一条过磅记录
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("weighCar", weighCar);
			resultMap.put("memberId", memberDTO.getMemberId());
			resultMap.put("memberName", memberDTO.getRealName());
			result.setObject(resultMap);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.info("获取过磅记录异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response, "yyyy-MM-dd HH:mm:ss");
	}
	
	@RequestMapping("getFiveCarNumber")
	public void getFiveCarNumber(WeighCarParamsBean paramsBean, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		if(StringUtils.isBlank(paramsBean.getMobile())){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", paramsBean.getMobile());
			if(StringUtils.isNotBlank(paramsBean.getType())){
				map.put("type", paramsBean.getType());
			}
			List<WeighCarDTO> list = weighCarToolService.getLastFiveWeighCar(map);
			result.setObject(list);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.info("获取用户车牌列表异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 根据手机号码获取状态为已进场的过磅记录
	 * 采购商：皮重已过磅的记录
	 * 货主：总重已过磅的记录 
	 * @param carNumber
	 * @param request
	 * @param response
	 */
	@RequestMapping("getEnterMarketWeighCar")
	public void getWeighCar(WeighCarParamsBean paramsBean, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		if(StringUtils.isBlank(paramsBean.getMobile())){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 1);
			map.put("mobile", paramsBean.getMobile());
			if(StringUtils.isNotBlank(paramsBean.getType())){
				map.put("type", paramsBean.getType());
			}
			
			List<WeighCarDTO> list = weighCarToolService.getWeighCar(map);
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.info("获取过磅记录异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response, "yyyy-MM-dd HH:mm:ss");
	}	
	
	
	/**
	 * 获取两天内过磅订单列表
	 * @param memberId
	 * @param request
	 * @param response
	 */
	@RequestMapping("getWeighCarOrder")
	public void getWeighCarOrder(Long memberId, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			
			List<WeighCarOrderDTO> list = weighCarToolService.getLastTwoDayWeighCarOrder(memberId);
			
			result.setObject(list);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.warn("[ERROR]获取出场订单异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 获取两天内过磅订单数
	 * @param memberId
	 * @param request
	 * @param response
	 */
	@RequestMapping("getWeighCarOrderCount")
	public void getWeighCarOrderCount(Long memberId, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("count", weighCarToolService.getLastTwoDayWeighCarOrderTotal(memberId));
			
			result.setObject(resultMap);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.warn("[ERROR]获取出场订单数异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 分页获取过磅订单列表
	 * @param memberId
	 * @param request
	 * @param response
	 */
	@RequestMapping("getWeighCarOrderPage")
	public void getWeighCarOrderPage(Long memberId, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			
			int total = weighCarToolService.getWeighCarOrderTotal(map);
			List<WeighCarOrderDTO> list = weighCarToolService.getWeighCarOrderPage(map);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.warn("[ERROR]获取出场订单异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 过磅订单数
	 * @param memberId
	 * @param request
	 * @param response
	 */
	@RequestMapping("getWeighCarOrderTotal")
	public void getWeighCarOrderTotal(Long memberId, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			int total = weighCarToolService.getWeighCarOrderTotal(map);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("count", total);
			
			result.setObject(resultMap);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.warn("[ERROR]获取出场订单数异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
}
