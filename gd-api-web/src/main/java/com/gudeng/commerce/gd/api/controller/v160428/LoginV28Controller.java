package com.gudeng.commerce.gd.api.controller.v160428;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.LoginService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

/**
 * 功能描述：会员登录退出控制中心
 * 
 */
@Controller
@RequestMapping("v28/member")
public class LoginV28Controller extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(LoginV28Controller.class);

	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private LoginService loginService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
			//将json 字符串封装为对象
			MemberBaseinfoDTO memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, 
					MemberBaseinfoDTO.class);
			String username = memberDtoInput.getAccount();
			String password = memberDtoInput.getPassword();
			Integer level = memberDtoInput.getLevel();
			Integer device = memberDtoInput.getDevice();// 登录设备
			String deviceTokens = memberDtoInput.getDevice_tokens();
			if (StringUtils.isBlank(username)) {
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}

			if (StringUtils.isBlank(password)) {
				setEncodeResult(result, ErrorCodeEnum.PASSWORD_IS_EMPTY, request, response);
				return;
			}

			if (!Validator.isMobile(username)) {
				setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
				return;
			}
			
			MemberBaseinfoDTO memberDTO = null;

			// 判断是否是登录门岗
			if (level != null && level == Constant.Member.MEMBER_LEVEL_FIVE.intValue()) {
				memberDTO = memberToolService.getByMobileAndLevel(username, 
						Constant.Member.MEMBER_LEVEL_FIVE);
			} else {
				memberDTO = memberToolService.getByMobile(username);
			}
			// 判断用户是否存在, 根据手机号查询

			if (null == memberDTO) {
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			// 判断是否是产地供应商(device=4)登录卖家版(level=4)，是拒绝登录
			if (device != null && memberDTO.getLevel() == 4 && device == 4) {
				setEncodeResult(result, ErrorCodeEnum.GYS_ACCOUNT_NOT_PERMIT, request, response);
				return;
			}

			// 判断用户是否禁用
			String status = memberDTO.getStatus();
			if (!"1".equals(status)) {
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_IS_DISABLE, request, response);
				return;
			}

			// 判断密码是否正确
			if (!password.equals(memberDTO.getPassword())) {
				setEncodeResult(result, ErrorCodeEnum.PASSWORD_ERROR, request, response);
				return;
			}

			// 更新deviceTokens
			String existedDeviceTokens = memberDTO.getDevice_tokens();
			if(StringUtils.isBlank(existedDeviceTokens) && StringUtils.isNotBlank(deviceTokens)){
				memberDTO.setDevice_tokens(deviceTokens);
				memberToolService.updateMember(memberDTO);
			}else if(StringUtils.isNotBlank(deviceTokens) && !deviceTokens.equals(existedDeviceTokens)){
				memberDTO.setDevice_tokens(deviceTokens);
				memberToolService.updateMember(memberDTO);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			// 会员id
			Long memberId = memberDTO.getMemberId();
			// add by yanghaoyu ,新增加参数,userType,如果当前用户是 公司,还是个人
			if (memberDTO.getUserType() == null) {
				map.put("userType", 0);
			} else {
				map.put("userType", memberDTO.getUserType());
			}

			map.put("realName", memberDTO.getRealName());
			map.put("sex", memberDTO.getSex());
			map.put("companyName", memberDTO.getCompanyName());
			map.put("companyContact", memberDTO.getCompanyContact());

			// 获取商铺信息
			BusinessBaseinfoDTO dto = businessBaseinfoToolService
					.getByUserId(memberId.toString());
			if (dto == null) {
				map.put("marketId", "");
				map.put("businessId", "");
				map.put("shopName", "");
			} else {
				map.put("marketId", dto.getMarketId());
				map.put("businessId", dto.getBusinessId());
				map.put("shopName", dto.getShopsName());
			}
			result.setObject(map);
			request.getSession().setAttribute("mobile", username);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]查找用户信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 退出系统
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			HttpSession session = request.getSession();
			String mobile = (String) session.getAttribute("mobile");
			logger.info("Logout mobile:" + mobile);
			session.removeAttribute("mobile");
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]注销失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

}
