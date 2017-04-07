package com.gudeng.commerce.gd.api.controller.v160428;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.ServiceDeskDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.PushNoticeToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.CustInfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBusinessInfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductClassDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/** 
* @author  bdhuang 
* @date 创建时间：2016年4月18日 上午10:49:27 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Controller
@RequestMapping("/v28/desk")
public class ServiceDeskController extends GDAPIBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ServiceDeskController.class);
	
	@Autowired
	private MemberCertifiApiService memberCertifiApiService;
	
	@Autowired
	private PushNoticeToolService pushNoticeToolService;
	
	@Autowired
	public CallstatiSticsToolService callstatiSticsToolService;
	
	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	public ProductToolService productToolService;
	@RequestMapping("getByUserId")
	public void getByUserId(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "userId");
			String client = GSONUtils.getJsonValueStr(jsonStr, "client");
			if(null == memberId || "".equals(memberId.trim())){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			if(null == client || "".equals(client.trim())){
				setEncodeResult(result, ErrorCodeEnum.APP_NAME_IS_NULL, request, response);
				return;
			}
			ServiceDeskDTO sdDto=new ServiceDeskDTO();
			MemberCertifiDTO mc = memberCertifiApiService.getByUserId(Long.valueOf(memberId));
			if(mc==null || null == mc.getStatus()){
				sdDto.setStatus("");
				sdDto.setType("");
				sdDto.setStatusStr("未提交认证"); 
			}else{
				sdDto.setStatus(mc.getStatus());
				sdDto.setType(mc.getType());
				switch(mc.getStatus()){ 
				case "1": 
					sdDto.setStatusStr("已认证"); 
					break;
				case "2": 
					sdDto.setStatusStr("已驳回"); 
					break;
				case "0": 
					sdDto.setStatusStr("待认证"); 
					break;
				default: 
					sdDto.setStatusStr("未提交认证"); 
					break;
				}
			}
			
			//未读消息条数
			sdDto.setMsgCount(pushNoticeToolService.getUnReadMessageCount(memberId,client));
			result.setObject(sdDto);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		
	}
	/**
	 * 获取客户列表
	 * @return
	 */
	@RequestMapping("/getCust")
	public void getCust(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> param = new HashMap<>();
	try {
		String jsonStr = getParamDecodeStr(request);
//		String mobile = request.getParameter("mobile");
//		String type = request.getParameter("type");
		String userId=GSONUtils.getJsonValueStr(jsonStr, "userId");
		String type =GSONUtils.getJsonValueStr(jsonStr, "type");
		if(userId==null){
			setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		if(type==null){
			setEncodeResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
			return;
		}
		param.put("userId", userId);
		param.put("type", type);
		
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, param);
			Long total =callstatiSticsToolService.getCustTotal(param);
			List<CustInfoDTO> rememList =callstatiSticsToolService.getCust(param);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total.intValue());
			pageDTO.setRecordList(rememList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			logger.info("###########获取信息出错"+e.getMessage());
		}
	}
	
	/**
	 * 获取联系人列表
	 * @return
	 */
	@RequestMapping("/getLinkman")
	public void getLinkman(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> param = new HashMap<>();
		
		try {
		//String mobile = request.getParameter("mobile");
		String jsonStr = getParamDecodeStr(request);
		String userId=GSONUtils.getJsonValueStr(jsonStr, "userId");
		if(userId==null){
			setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		param.put("userId", userId);
		
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, param);
			Long total =callstatiSticsToolService.getLinkmanTotal(param);
			List<CustInfoDTO> rememList =callstatiSticsToolService.getLinkman(param);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total.intValue());
			pageDTO.setRecordList(rememList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			logger.info("###########获取信息出错"+e.getMessage());
		}
	}
	/**
	 * 获取客户信息
	 * @return
	 */
	@RequestMapping("/getCustDetail")
	public void getCustDetail(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		//String businessId = request.getParameter("businessId");
		try { 
		String jsonStr = getParamDecodeStr(request);
		String businessId=GSONUtils.getJsonValueStr(jsonStr, "businessId");
		if(businessId==null|| businessId.equals("") || businessId.equals("null")){
			setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
			return;
		}
		Map<String, Object> param = new HashMap<>();
		param.put("businessId", businessId);
		//获取会员商铺信息
		
			MemberBusinessInfoDTO memberBaseinfo =memberToolService.getMemberBusinessInfo(param);
			//获取兼营分类
			List<ProductClassDTO> productCategory=productToolService.getCateNameByBusinessId(param);
			memberBaseinfo.setCategoryList(productCategory);
			result.setObject(memberBaseinfo);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	
		
	}
	
}
