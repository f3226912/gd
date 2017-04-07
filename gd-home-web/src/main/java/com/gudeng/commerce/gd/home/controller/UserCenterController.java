package com.gudeng.commerce.gd.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.home.Constant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.UsercollectShopToolService;
import com.gudeng.commerce.gd.home.util.BusinessUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("userCenter")
public class UserCenterController  extends HomeBaseController{

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(UserCenterController.class);
	
	
	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public UsercollectShopToolService usercollectShopToolService;
	
	@RequestMapping("")
	public String userCenter(HttpServletRequest request){
		try {
			MemberBaseinfoEntity mbe=getUser(request);
			
			MemberBaseinfoDTO mbdto=memberBaseinfoToolService.getById(String.valueOf(mbe.getMemberId()));
			
			putModel("mdto",mbdto);

			BusinessBaseinfoDTO bbdto=BusinessUtil.getBusinessBaseinfo(request, businessBaseinfoToolService);
			String resaon=(String)request.getSession().getAttribute("reason");
//			BusinessBaseinfoDTO bbdto= businessBaseinfoToolService.getByUserId(String.valueOf(mbe.getMemberId()));
			if( ! BusinessUtil.isUserHaveBusiness(request,businessBaseinfoToolService)  ){
//				this.putModel("reason", "没有店铺，不能发布产品");
//				this.putModel("reason", "没有店铺，不能查看关注***");
				return "usercenter/userCenterNoBusiness";
			}
			
			Map cmap =new HashMap();
			cmap.put("businessId", bbdto.getBusinessId());
			cmap.put("state", 3);
			int count_3=productToolService.getCount(cmap);//已经上架产品个数
			
			cmap.remove("state");
			cmap.put("state", 1);
			int count_1=productToolService.getCount(cmap);//待审核产品个数
			cmap.remove("state");
			cmap.put("state", 2);
			int count_2=productToolService.getCount(cmap);//已驳回产品个数
			cmap.remove("state");
			cmap.put("state", 4);
			int count_4 = productToolService.getCount(cmap);//已驳回产品个数
			
			int count_attent=0;
			Map mc =new HashMap();
			mc.put("businessId", bbdto.getBusinessId());
			
			int count_att=usercollectShopToolService.getCount(mc);
			
//			usercollect_shop
//			关注数
			
			
			putModel("bdto",bbdto);
			putModel("countLine",count_3);
			putModel("countOffLine",count_1+count_2+count_4);
			putModel("countAttant",count_att);
			
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		
		
		return "usercenter/userCenterIndex";
	}
	
//	@RequestMapping("index")
//	public String userCenterIndex(){
//		return "usercenter/userCenterIndex";
//	}
	
	
}
