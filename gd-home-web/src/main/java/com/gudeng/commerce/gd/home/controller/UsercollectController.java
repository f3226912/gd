package com.gudeng.commerce.gd.home.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.BusinessProducttypeToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.home.service.UsercollectShopToolService;
import com.gudeng.commerce.gd.home.util.BusinessUtil;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("userCenter/collect")
public class UsercollectController extends HomeBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(UsercollectController.class);

	@Autowired
	public BusinessBaseinfoToolService baseinfoService;

	@Autowired
	public BusinessProducttypeToolService businessProducttypeToolService;

	@Autowired
	public ProductToolService productToolService;

	@Autowired
	public UsercollectShopToolService usercollectShopToolService;

	@Autowired
	public UsercollectProductToolService usercollectProductToolService;
	
	/**
	 * 收藏产品列表
	 * 
	 * @return
	 */
	@RequestMapping("product")
	public String collectProducts() {
		try {
			/*
			 * 判断是否开通商铺
			 */
			BusinessUtil.isUserHaveBusiness(getRequest(),baseinfoService);
			
			/*
			 * 测试分页 query 查询条件 需要的页面参数 page 页码 size 页大小
			 */
			Map<String, Object> query = new HashMap<>();
			
			Long userId = getUser(getRequest()).getMemberId();
			
			query.put("userId", userId);

			// 获取总数 通过query提供的参数 此处有service层提供方法
			int size = usercollectProductToolService.getCount(query);

			/*
			 * 默认使用创建pageDTO对象 页大小=10
			 */
			PageDTO<PushProductDTO> pageDto = PageUtil.create(
					PushProductDTO.class, 5);

			// 设置总数据
			pageDto.setTotalSize(size);

			// 设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, query);

			// 获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(usercollectProductToolService.getCollectList(
					userId, null, null, null,
					pageDto.getIndex(), pageDto.getPageSize()));

			// 数据添加到model 前台准备显示
			putPagedata(pageDto);
			/*
			 * pageDTO中数据 getPageTotal 总页数 pageData 显示数据
			 */
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "usercenter/product/collectproduct";
	}
	
	/**
	 * 收藏产品列表
	 * 
	 * @return
	 */
	@RequestMapping("shops")
	public String collectShops() {
		try {
			/*
			 * 判断是否开通商铺
			 */
			BusinessUtil.isUserHaveBusiness(getRequest(),baseinfoService);
			/*
			 * 测试分页 query 查询条件 需要的页面参数 page 页码 size 页大小
			 */
			Map<String, Object> query = new HashMap<>();
			
			Long userId = getUser(getRequest()).getMemberId();
			
			query.put("userId", userId);

			// 获取总数 通过query提供的参数 此处有service层提供方法
			int size = usercollectShopToolService.getCount(query);

			/*
			 * 默认使用创建pageDTO对象 页大小=10
			 */
			PageDTO<UsercollectShopDTO> pageDto = PageUtil.create(
					UsercollectShopDTO.class, 5);

			// 设置总数据
			pageDto.setTotalSize(size);

			// 设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, query);

			// 获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(usercollectShopToolService.getCollectList(query));

			// 数据添加到model 前台准备显示
			putPagedata(pageDto);
			/*
			 * pageDTO中数据 getPageTotal 总页数 pageData 显示数据
			 */
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "usercenter/product/collectshops";
	}
	
	/**
	 * 关注自己的列表
	 * 
	 * @return
	 */
	@RequestMapping("focusme")
	public String collectFocusMe() {
		try {
			
			/*
			 * 判断是否开通的店铺
			 */
			if( ! BusinessUtil.isUserHaveBusiness(getRequest(),baseinfoService)  ){
//				this.putModel("reason", "没有店铺，不能发布产品");
				this.putModel("reason", "没有店铺，不能查看关注自己的客户");
				return "usercenter/userCenterNoBusiness";
			}

			/*
			 * 测试分页 query 查询条件 需要的页面参数 page 页码 size 页大小
			 */
			Map<String, Object> query = new HashMap<>();
			
			Long userId = getUser(getRequest()).getMemberId();
			
			query.put("userId", userId);

			// 获取总数 通过query提供的参数 此处有service层提供方法
			int size = usercollectShopToolService.getFocusMeCount(query);

			/*
			 * 默认使用创建pageDTO对象 页大小=5
			 */
			PageDTO<UsercollectShopDTO> pageDto = PageUtil.create(
					UsercollectShopDTO.class, 3);

			// 设置总数据
			pageDto.setTotalSize(size);

			// 设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, query);

			// 获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(usercollectShopToolService.getFocusMeCollectList(query));

			// 数据添加到model 前台准备显示
			putPagedata(pageDto);
			/*
			 * pageDTO中数据 getPageTotal 总页数 pageData 显示数据
			 */
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "usercenter/product/collectfocusme";
	}
	
	/**
	 * 批量取消收藏产品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("bathblurpro")
	public String batchBlurCollectProduct(String productIds) throws Exception {
		Integer result = -1;
		
		MemberBaseinfoEntity user = getUser(getRequest());
		
		if(user == null) {
			result = -2;
			return result.toString();
		}
		
		try {
			productIds = productIds.substring(0, productIds.length()-1);
		
		result = usercollectProductToolService.cancelMoreFocus(user.getMemberId() ,productIds);
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		
		return result.toString();
	}
	
	/**
	 * 批量取消收藏产品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("bathblurshop")
	public String batchBlurCollectShop(String bids) throws Exception {
		Integer result = -1;
		
		MemberBaseinfoEntity user = getUser(getRequest());
		
		if(user == null) {
			result = -2;
			return result.toString();
		}
		
		if(bids==null||"".equals(bids)) {
			return result.toString();
		}
		try {
			bids = bids.substring(0, bids.length()-1);
		
		result = usercollectShopToolService.blurMoreShop(user.getMemberId() ,bids);
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		
		return result.toString();
	}
	
}
