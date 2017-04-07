package com.gudeng.commerce.gd.admin.controller;

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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.IntegralQueryBean;
import com.gudeng.commerce.gd.admin.service.IntegralToolService;
import com.gudeng.commerce.gd.admin.service.MemberIntegralConversionToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.commerce.gd.customer.dto.MemberIntegralConversionDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 会员积分兑换礼物Controller
 * 
 * @author xiaojun
 */
@Controller
@RequestMapping("conversion")
public class MemberIntegralConversionController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(MemberIntegralConversionController.class);

	@Autowired
	private MemberIntegralConversionToolService memberIntegralConversionToolService;
	
	@Autowired
	private IntegralToolService integralToolService;
	
	@Autowired
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	
	@RequestMapping("list")
	public String integralConversion(){
		return "conversion/memberIntegralInfolist";
	}
	
	/**
	 * 查询会员兑换积分列表
	 * @param request
	 * @return
	 */
	@RequestMapping("memberinfo")
	@ResponseBody
	public String query(HttpServletRequest request) {

		try {
			Map<String, Object> map = new HashMap<>();
			/** 获取用户的传入查询条件的用户类型*/
			String userType=request.getParameter("userType");
			/** 用户账号*/
			String account=request.getParameter("account");
			map.put("account", account);
			map.put("userType", userType);
			
			//设置总记录数
			map.put("total", memberIntegralConversionToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			
			List<MemberIntegralConversionDTO> list = memberIntegralConversionToolService
					.getMemberIntegralConversion(map);
			
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转到礼物列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("giftselect")
	public String giftSelect(HttpServletRequest request,Map<String, Object> map) {
		/** 将会员的memberId和总积分intergral保存到giftlist页面中*/
		
		String memberId=request.getParameter("memberId");
		String integral=request.getParameter("integral");
		map.put("memberId", memberId);
		map.put("integral", integral);
		
		return "conversion/giftlist";
	}
	
	/**
	 * 获取礼物积分列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("giftlist")
	@ResponseBody
	public String giftList(HttpServletRequest request,Map<String, Object> map) {

		//设置总记录数
		try {
			/** integral表示会员的总积分，只查询可积分可兑换的礼物*/
			String integral=request.getParameter("integral");
			
			/** giftName表示礼品名字*/
			String giftName=request.getParameter("giftName");
			map.put("integral", integral);
			map.put("giftName", giftName);
			
			map.put("total", memberIntegralConversionToolService.getGiftTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			
			List<MemberIntegralConversionDTO> list = memberIntegralConversionToolService
					.getGiftIntegralList(map);
			
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 更新会员的总积分，同时插入一条记录到积分流水表integral中
	 * @param request
	 * @return
	 */
	@RequestMapping("updateintegralinfo")
	@ResponseBody
	public String updateintegralinfo(HttpServletRequest request){
		//会员memberId
		String memberId=request.getParameter("memberId");
		//礼物giftId
		String giftId=request.getParameter("giftId");
		//各个礼物对应的giftIntegral积分
		Integer giftIntegral=Integer.parseInt(request.getParameter("giftIntegral"));
		//礼物giftName名字
		String giftName=request.getParameter("giftName");
		//会员总积分
		Integer integral=Integer.parseInt(request.getParameter("integral"));
		//会员总积分减去礼物对应的积分所得
		Integer subintegral=integral-giftIntegral;
		//活动activityId
		String activityId=request.getParameter("activityId");
		
		try {
			Map<String, Object> map=new HashMap<>();
			map.put("memberId", memberId);
			map.put("subintegral", subintegral);
			//更新会员积分
			int success=memberIntegralConversionToolService.updateMemberIntegral(map);
			//客户积分更新后，插入流水表
			if (success>=0) {
				Map<String, Object> map2=new HashMap<>();
				SysRegisterUser user=getUser(request);
				String loginName=user.getUserID();
				map2.put("loginName", loginName);
				map2.put("memberId", memberId);
				map2.put("giftIntegral", 0-giftIntegral);
				map2.put("giftId", giftId);
				map2.put("activityId", activityId);
				//兑换giftName花了giftIntegral积分				
				map2.put("description", "兑换"+giftName+"花了"+giftIntegral+"积分");
				//插入流水
				memberIntegralConversionToolService.insertIntegral(map2);
				
				return "success";
			}
			return "error";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping("queryintegral")
	@ResponseBody
	public Object query(IntegralQueryBean queryBean, HttpServletRequest request){
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(queryBean.getActivityId() != null){
				map.put("activityId", queryBean.getActivityId());
			}
			if(StringUtils.isNotBlank(queryBean.getMemberAccount())){
				map.put("memberAccount", queryBean.getMemberAccount());
			}
			if(queryBean.getUserType() != null){
				map.put("userType", queryBean.getUserType());
			}
			if(queryBean.getType() != null){
				map.put("type", queryBean.getType());
			}
			if(StringUtils.isNotBlank(queryBean.getQueryStartDate())){
				map.put("queryStartDate", queryBean.getQueryStartDate());
			}
			if(StringUtils.isNotBlank(queryBean.getQueryEndDate())){
				map.put("queryEndDate", queryBean.getQueryEndDate());
			}
			
			map.put("total", integralToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			
			List<IntegralDTO> list = integralToolService.getByCondition(map);
			
			List<IntegralDTO> list2 = integralToolService.getByCondition2(map);
			
			for(IntegralDTO dto : list){
				if(StringUtils.isNotBlank(dto.getCreateUserId())){
					SysRegisterUser createUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
					if(createUser != null){
						dto.setCreateUserAccount(createUser.getUserName());
					}
				}
			}
			
			map.put("rows", list);//rows键 存放每页记录 list 
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			
		}
		return null;
	}
}
