package com.gudeng.commerce.gd.api.controller.v3;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.MemberAppInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NsyUserTypeEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

/**
 * 功能描述：会员信息控制中心v3.0
 * 
 */
@Controller
@RequestMapping("v30/member")
public class MemberV30Controller extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberV30Controller.class);

	@Autowired
	public MemberToolService memberToolService;
	
	@RequestMapping("/setPwd")
	public void setPwd(HttpServletRequest request, HttpServletResponse response, 
			MemberAppInputDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String jsonStr = "";
		try {
			jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, 
					MemberAppInputDTO.class);
		} catch (Exception e) {
			logger.error("[ERROR]jsonStr: " + jsonStr);
			logger.error("[ERROR]设置密码解密异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		String username = memberDtoInput.getAccount();
		String password = memberDtoInput.getPassword();
		String sType = memberDtoInput.getType();
		if(StringUtils.isBlank(username)){
			setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(username)){
			setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		if(StringUtils.isBlank(password)){
			setEncodeResult(result, ErrorCodeEnum.PASSWORD_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(sType)){
			setEncodeResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
			return;
		}
		
		int iType = Integer.parseInt(sType);
		if(iType != 0 && iType != 1){
			setEncodeResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
			return;
		}
		
		try {	
			if(iType == 0){ //注册操作
				String regetype = memberDtoInput.getRegetype();
				String nsyUserType = memberDtoInput.getNsyUserType();
				Integer level =  ParamsUtil.getIntFromString(memberDtoInput.getLevel(), 999);
				//0管理后台，1谷登农批网，2农速通，3农商友，4农商友-农批商，5农批友, 6供应商,7POS刷卡
				if(StringUtils.isBlank(regetype)){
					setEncodeResult(result, ErrorCodeEnum.USER_RESOURCE_IS_EMPTY, request, response);
					return;
				}
				//老版农速通不允许注册
				if("2".equals(regetype)){
					setEncodeResult(result, ErrorCodeEnum.NST_APP_IS_NOT_USED, request, response);
					return;
				}
				if(iType == 0 && level == null){
					setEncodeResult(result, ErrorCodeEnum.USER_LEVEL_IS_EMPTY, request, response);
					return;
				}
				
				//农商友用户注册需要提交农商友用户类型 start
				Integer iNsyUserType = null;
				if("3".equals(regetype)){
					if(StringUtils.isBlank(nsyUserType)){
						setEncodeResult(result, ErrorCodeEnum.NSYUSERTYPE_IS_EMPTY, request, response);
						return;
					}
					
					iNsyUserType = Integer.parseInt(nsyUserType);
					if(iType == 0 && NsyUserTypeEnum.getByNsyUserType(iNsyUserType) == null){
						setEncodeResult(result, ErrorCodeEnum.NSYUSERTYPE_INCORRECT, request, response);
						return;
					}
				}
				//农商友用户注册需要提交农商友用户类型 end
				
				if (memberToolService.isAccExisted(username)){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
					return;
				}
				
				//插入新用户
				MemberBaseinfoEntity memberEntity = new MemberBaseinfoEntity();
				memberEntity.setAccount(username);
				memberEntity.setPassword(password);
				memberEntity.setNsyUserType(iNsyUserType);
				memberEntity.setLevel(level);
				memberEntity.setRegetype(regetype);
				memberEntity.setStatus("1");
				memberEntity.setIp(request.getRemoteAddr());
				memberEntity.setMobile(username);
				memberEntity.setCreateTime(new Date());
				memberEntity.setUpdateTime(new Date());
				memberEntity.setMemberGrade(0);
				//2016-10-20 系统生成账户名
				String account="gd"+UUID.randomUUID().toString().replace("-", "").toUpperCase(); //由系统生成帐号：gd+UUID
				memberEntity.setAccount(account);
				memberToolService.addMemberBaseinfoEnt(memberEntity);
			}else{  //修改密码操作
				//检查手机号是否存在
				MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(username);
				if (null == memberDTO){
					//检查账号是否存在
					memberDTO = memberToolService.getByAccount(username);
					if(null == memberDTO){
						setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
						return;
					}
				}
				//更改密码
				memberDTO.setPassword(password);
				memberDTO.setUpdateTime_string(DateTimeUtils.getTimeString());;
				memberDTO.setUpdateUserId(memberDTO.getMemberId()+"");
				memberToolService.updateMember(memberDTO);
			}
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]User info: " + memberDtoInput.toString());
			logger.error("[ERROR]设置密码异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
