package com.gudeng.commerce.gd.home.util;

import javax.servlet.http.HttpServletRequest;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.Constant;
import com.gudeng.commerce.gd.home.controller.HomeBaseController;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;


public class BusinessUtil {
	
	public static boolean isUserHaveBusiness(HttpServletRequest request,BusinessBaseinfoToolService businessBaseinfoToolService) throws Exception{
		BusinessBaseinfoDTO bdto=getBusinessBaseinfo(request,businessBaseinfoToolService);
		if(null!=bdto){
			return true;
		}
		return false;

	}
	
	
	public static Long getBusinessId(HttpServletRequest request,BusinessBaseinfoToolService businessBaseinfoToolService) throws Exception{
		BusinessBaseinfoDTO bdto=getBusinessBaseinfo(request,businessBaseinfoToolService);
		if(null!=bdto){
			return bdto.getBusinessId();
		}
		return null;
	}
	
	
	public static BusinessBaseinfoDTO getBusinessBaseinfo(HttpServletRequest request,BusinessBaseinfoToolService businessBaseinfoToolService) throws Exception{
		MemberBaseinfoDTO mbe =	(MemberBaseinfoDTO) request.getSession().getAttribute(Constant.SYSTEM_LOGINUSER);
		Long userId=mbe.getMemberId();
		String marketId=HomeBaseController.getCookieByMarketId(request);
		if(null ==userId || userId==0L){ 			
			request.getSession().removeAttribute(Constant.BUSINESSID);
			return null;
		}
		BusinessBaseinfoDTO bbdto=null;
//		if(null != mbe.getLevel() && mbe.getLevel()==4){//产地供应商，直接查店铺
//			bbdto=	businessBaseinfoToolService.getByUserId(String.valueOf(userId));
//		}else{//批发商，根据市场id和用户id查店铺，为第二期考虑
//			bbdto=  businessBaseinfoToolService.getByUserIdAndMarketId(String.valueOf(userId), marketId);
//		}
		//第一期，只要有此用户关联的店铺，直接返回true
		bbdto=	businessBaseinfoToolService.getByUserId(String.valueOf(userId));

		if(null==bbdto  ){
			request.getSession().removeAttribute(Constant.BUSINESSID);
			return null;
		}
		request.getSession().setAttribute(Constant.BUSINESSID, bbdto.getBusinessId());
		return bbdto;
	}
	
}
