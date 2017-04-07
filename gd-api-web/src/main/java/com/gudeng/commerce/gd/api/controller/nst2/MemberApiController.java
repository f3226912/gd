package com.gudeng.commerce.gd.api.controller.nst2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/** 
 * 农速通改版，包括启用、禁用，修改手机号，修改密码，修改姓名等接口
 * 
* @author  bdhuang 
* @date 创建时间：2016年8月10日 上午11:00:33 
* @version 1.0 
* @parameter  
* @since  
* @return  
* 
*/
@Controller
@RequestMapping("memberapi")
public class MemberApiController  extends GDAPIBaseController {
	
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(MemberApiController.class);
	
	@Autowired
	public MemberToolService memberToolService;
	
	/**
	 * 修改手机号
	 * 
	 * */
	@RequestMapping("/updateMobile")
	public void updateMobile(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String mobile = (String)GSONUtils.getJsonValueStr(jsonStr, "mobile");
			Long memberId = Long.valueOf(GSONUtils.getJsonValueStr(jsonStr, "memberId"));
			if(StringUtils.isEmpty(mobile)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			if(memberId==null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getByMobile(mobile);
			if(memberBaseinfoDTO!=null&& memberBaseinfoDTO.getMemberId().longValue()>0){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "手机号码已被注册，修改手机号失败", request, response);
			}
			int i=memberToolService.updateMobile(memberId, mobile);
			if(i>0){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "修改手机号成功", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "修改手机号失败", request, response);
		}
	}
	
	/**
	 * 修改status,即启用禁用
	 * 
	 * */
	@RequestMapping("/updateStatus")
	public void updateStatus(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String status = (String)GSONUtils.getJsonValueStr(jsonStr, "status");
			Long memberId = Long.valueOf(GSONUtils.getJsonValueStr(jsonStr, "memberId"));
			if(StringUtils.isEmpty(status)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "status不能为空", request, response);
				return;
			}
			if(memberId==null){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "memberId不能为空", request, response);
				return;
			}
			int i=memberToolService.updateStatus(memberId, status);
			if(i>0){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "修改状态成功", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "修改status失败", request, response);
		}
	}
	
	/**
	 * 修改姓名
	 * 
	 * */
	@RequestMapping("/updateRealName")
	public void updateRealName(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String realName = (String)GSONUtils.getJsonValueStr(jsonStr, "realName");
			Long memberId = Long.valueOf(GSONUtils.getJsonValueStr(jsonStr, "memberId"));
			if(StringUtils.isEmpty(realName)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "姓名不能为空", request, response);
				return;
			}
			if(memberId==null){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "memberId不能为空", request, response);
				return;
			}
			int i=memberToolService.updateRealName(memberId, realName);
			if(i>0){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "修改姓名成功", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "修改姓名失败", request, response);
		}
	}
	
	/**
	 * 修改密码
	 * 
	 * */
	@RequestMapping("/updatePassword")
	public void updatePassword(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String password = (String)GSONUtils.getJsonValueStr(jsonStr, "password");
			Long memberId = Long.valueOf(GSONUtils.getJsonValueStr(jsonStr, "memberId"));
			if(StringUtils.isEmpty(password)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "password不能为空", request, response);
				return;
			}
			if(memberId==null){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "memberId不能为空", request, response);
				return;
			}
			int i=memberToolService.updatePassword(memberId, password);
			if(i>0){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "修改密码成功", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "修改密码失败", request, response);
		}
	}
	
	@RequestMapping("/updateMember")
	public void updateMember(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			Long memberId = Long.valueOf(GSONUtils.getJsonValueStr(jsonStr, "memberId"));
			if(memberId==null){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "memberId不能为空", request, response);
				return;
			}
			MemberBaseinfoDTO member = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
			int i=memberToolService.updateMember(member);
			if(i>0){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "修改用户信息成功", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "修改用户信息成功失败", request, response);
		}
	}
}
