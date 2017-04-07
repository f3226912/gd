package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("nst2/member")
public class MemberNst2Controller extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory
			.getLogger(MemberNst2Controller.class);
	@Autowired
	public MemberToolService memberToolService;

	@Autowired
	private AreaToolService areaToolService;

	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;
    
	@Autowired
	private MemberCertifiApiService memberCertifiApiService;
	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * 
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/getContacts")
	public void getContacts(HttpServletRequest request,
			HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long id = memberDtoInput.getMemberId();

		try {
			MemberBaseinfoDTO memberDTO = memberToolService
					.getContacts(id + "");
			// 判断用户是否存在
			if (null == memberDTO) {
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response); 
				return;
			}
			String imageHost = gdProperties.getProperties().getProperty(
					"gd.image.server");
			// 修改逻辑,个人中心的认证状态,只显示农速通的认证状态,否则,驳回原因无法显示
			if (memberDTO.getNstStatus() != null
					&& !"".equals(memberDTO.getNstStatus())) {
				memberDTO.setCertificationType(memberDTO.getNstStatus());
			} else {
				memberDTO.setCertificationType("-1");
			}
			memberDTO.setAndupurl(imageHost + memberDTO.getAndupurl());
			if (memberDTO.getCcityId() != null
					&& !"".equals(memberDTO.getCcityId())) {
				AreaDTO areaDto = areaToolService.getByAreaId(memberDTO
						.getCcityId());
				if (areaDto != null) {
					memberDTO.setCityName(areaDto.getArea());
				}
			} else {
				memberDTO.setCityName("");
			}
			//根据前台传入memberId 查询出订单各个状态的总和
			List<NstOrderBaseinfoDTO> nstOrderBaseinfoDTOs=nstOrderBaseinfoToolService.getOrderStatusCountByMemberId(id); 	
			
			// comment1 待提交  comment2 已提交 comment3 已完成  comment4 未完成
			HashMap<String, Long> commentMap=new HashMap<>();
			commentMap.put("comment1", 0l);
			commentMap.put("comment2", 0l);
			commentMap.put("comment3", 0l);
			commentMap.put("comment4", 0l);//拒绝订单
			commentMap.put("comment5", 0l);//取消订单
			for (NstOrderBaseinfoDTO nstOrderBaseinfoDTO : nstOrderBaseinfoDTOs) {
				String key="comment"+nstOrderBaseinfoDTO.getOrderStatus();
				commentMap.put(key, nstOrderBaseinfoDTO.getSubmitCount());
			}
			//将拒绝订单和取消订单的合在一起
			commentMap.put("comment4", commentMap.get("comment4")+commentMap.get("comment5"));
			memberDTO.setCommentCount(commentMap);
			//获取会员的好评总数
			int typeCount=nstOrderBaseinfoToolService.getEvaluateTypeCountByMemberId(id);
			memberDTO.setEvaluateTypeCount(typeCount);
			
			result.setObject(memberDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/checkActityNo")
	public void checkActityNo(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long id = memberDtoInput.getMemberId();
		String actityNo=memberDtoInput.getActityNo();
		///memberToolService.getById(id)
		
		MemberBaseinfoDTO mt = null;
		if(id == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		//判断用户是否存在, 如果不存在,则看是否是工作人员提供的推荐码,直接送积分,如果不是,提醒用户
		 int flag =0;
		if(memberDtoInput.getActityNo()!=null&&!"".equals(memberDtoInput.getActityNo())){
			
			try {
				//APP前端输入的
				mt = memberToolService.getByMobile(actityNo);
				if (null == mt ){
						flag=2;
				}else{
					flag=1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		try {
			MemberBaseinfoDTO memberDTO = memberToolService.getById(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}	
			//绑定推荐人 V1.4版本,增加积分系统
			Map<String,Object> map = new HashMap<String,Object>();
			
			Map<String,Object> map2 = new HashMap<String,Object>();
			//type  ,定义为3 ,3是绑定推荐人和被推荐的人的关系
			if(flag==1){
				map2.put("id",id);
				map2.put("edId",mt.getMemberId());
				int i=memberToolService.getRecordByMemberInfo(map2);
				if(i>0){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("您的账号不能被重复推荐" );
					renderJson(result, request, response);
					return;
				}
				
				map.put("type", 3);
				//当前登录人的id是被推荐
		        map.put("memberId_ed", memberDtoInput.getMemberId());
		        //推荐人的Id
		        map.put("memberId", mt.getMemberId());
		        map.put("createUserId", memberDtoInput.getMemberId());
		        map.put("updateUserId", memberDtoInput.getMemberId());
		        map.put("description", "推荐人绑定新用户,获取推荐绑定关系");	
		        memberToolService.getlink(map);
			}
			if(flag==2){
				map.put("type", 3);
				//当前登录人的id是被推荐
		        map.put("memberId_ed", memberDtoInput.getMemberId());
		        map.put("createUserId", memberDtoInput.getMemberId());
		        map.put("updateUserId", memberDtoInput.getMemberId());
		        map.put("description", "活动推广,使用推荐码获取积分,推荐码为"+actityNo);	
		       // memberToolService.getlink(map);
				map.put("a", actityNo);
				map.put("b", memberDtoInput.getMemberId());
		       int msg= memberToolService.addInt(map);
		       if(msg==2){
						setResult(result, ErrorCodeEnum.RECOMMEND_CODE_INCRECT, request, response);
						return;
		       }
		       if(msg==3){
						setResult(result, ErrorCodeEnum.RECOMMEND_CODE_ALREADY_USED, request, response);
						return;
		       }
			}
			result.setObject(null);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]推荐码检查异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	

	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * 
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/getLinkManInfo")
	public void getLinkManInfo(HttpServletRequest request,
			HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long id = memberDtoInput.getMemberId();

		try {
			MemberBaseinfoDTO memberDTO = memberToolService
					.getContacts(id + "");
			// 判断用户是否存在
			if (null == memberDTO) {
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			if(id!=null&&!"".equals(id.toString())){
				memberDTO.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(id)));
			}
			String imageHost = gdProperties.getProperties().getProperty(
					"gd.image.server");
			// 修改逻辑,个人中心的认证状态,只显示农速通的认证状态,否则,驳回原因无法显示
			if (memberDTO.getNstStatus() != null
					&& !"".equals(memberDTO.getNstStatus())) {
				memberDTO.setCertificationType(memberDTO.getNstStatus());
			} else {
				memberDTO.setCertificationType("-1");
			}
			memberDTO.setAndupurl(imageHost + memberDTO.getAndupurl());
			if (memberDTO.getCcityId() != null
					&& !"".equals(memberDTO.getCcityId())) {
				AreaDTO areaDto = areaToolService.getByAreaId(memberDTO
						.getCcityId());
				if (areaDto != null) {
					memberDTO.setCityName(areaDto.getArea());
				}
			} else {
				memberDTO.setCityName("");
			}
			//根据前台传入memberId 查询出订单各个状态的总和
			int orderCount=nstOrderBaseinfoToolService.getOrderStatusCount(id); 	
            memberDTO.setOrderCount(orderCount+"");

			//获取会员的好评总数
			int typeCount=nstOrderBaseinfoToolService.getEvaluateTypeCountByMemberId(id);
			memberDTO.setEvaluateTypeCount(typeCount);
			
			//获取会员的发布信息总数
			int memberCarlineCount=nstOrderBaseinfoToolService.getmemberCarlineCountByMemberId(id);
			memberDTO.setMemberCarlineCount(memberCarlineCount+"");
			
			//获取会员的发布信息总数
			List<CarsDTO> carsDTOs =nstOrderBaseinfoToolService.getCarsDTOByMemberId(id);
			int i=0;
			int j=0;
			if(carsDTOs==null ||carsDTOs.size()==0 ){
				memberDTO.setIfdriverPhotoUrl("0");
				memberDTO.setIfvehiclePhotoUrl("0");
			}else{
				 for (CarsDTO carDto : carsDTOs) {
					 if(carDto.getNst_driverPhotoUrl()!=null||!"".equals(carDto.getNst_driverPhotoUrl())){
						 i++;
					 }
					 if(carDto.getNst_vehiclePhotoUrl()!=null||!"".equals(carDto.getNst_vehiclePhotoUrl())){
						 j++;
					 }
				 }
					memberDTO.setIfdriverPhotoUrl(i+"");
					memberDTO.setIfvehiclePhotoUrl(j+"");
			}
			if(memberDTO.getNst_bzlPhotoUrl()!=null || !"".equals(memberDTO.getNst_bzlPhotoUrl())){
				memberDTO.setIfbzlPhotoUrl("1");
			}else{
				memberDTO.setIfbzlPhotoUrl("0");
			}
			
			if(memberDTO.getNst_cardPhotoUrl()!=null || !"".equals(memberDTO.getNst_cardPhotoUrl())){
				memberDTO.setIfcardPhotoUrl("1");
			}else{
				memberDTO.setIfcardPhotoUrl("0");
			}
			
			result.setObject(memberDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

}
