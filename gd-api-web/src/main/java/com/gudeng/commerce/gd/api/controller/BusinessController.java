package com.gudeng.commerce.gd.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.ProductListAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.UserCollectShopToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;

/**
 * 农批商控制中心
 * @author TerryZhang
 */
@Controller
@RequestMapping("business")
public class BusinessController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(BusinessController.class);
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	@Autowired
	public ProductToolService productToolService;
	@Autowired
	public UserCollectShopToolService userCollectShopToolService;
	
	@RequestMapping("/getShopDetail")
	public void getShopDetail(HttpServletRequest request, HttpServletResponse response, BusinessBaseinfoDTO inputParamDTO){
		ObjectResult result = new ObjectResult();
		Long businessId = inputParamDTO.getBusinessId();
		if(businessId == null){
			setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", businessId);
			BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(businessId+"");
			result.setObject(businessDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取农批商详情异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getShopList")
	public void getShopList(HttpServletRequest request, HttpServletResponse response, BusinessBaseinfoDTO inputParamDTO){
		ObjectResult result = new ObjectResult();
		Long memberId = inputParamDTO.getUserId();
		Integer isFocus = inputParamDTO.getIsFocus();
		String marketId = inputParamDTO.getMarketId();
		Long categoryId = inputParamDTO.getCategoryId();
		if(memberId == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		if(categoryId == null && 1 != isFocus){
			setResult(result, ErrorCodeEnum.BUSINESS_CATEGORY_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("categoryId", categoryId);
			map.put("marketId", marketId);
			//查找已关注的列表
			if(1 == isFocus){
				map.put("isFocus", 1);
				
			}
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = businessBaseinfoToolService.getShopsTotal(map);
			List<BusinessAppDTO> businessList = businessBaseinfoToolService.getShops(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(businessList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取农批商列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getShopProductList")
	public void getShopProductList(HttpServletRequest request, HttpServletResponse response, BusinessBaseinfoDTO inputParamDTO){
		ObjectResult result = new ObjectResult();
		Long memberId = inputParamDTO.getUserId();
		Long businessId = inputParamDTO.getBusinessId();
		if(memberId == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		if(businessId == null){
			setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("businessId", businessId);
			
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = productToolService.getShopsProductTotal(map);
			List<ProductListAppDTO> productList =productToolService.getShopProductList(map);
			
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(productList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取农批商产品列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}	
	}
	
	
	/**
	 * 获取商铺的商品列表 （有商品产地）
	 * @param request
	 * @param response
	 * @param inputParamDTO
	 */
	@RequestMapping("/getShopProductListNew")
	public void getShopProductListNew(HttpServletRequest request, HttpServletResponse response, BusinessBaseinfoDTO inputParamDTO){
		ObjectResult result = new ObjectResult();
		Long memberId = inputParamDTO.getUserId();
		Long businessId = inputParamDTO.getBusinessId();
		if(memberId == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		if(businessId == null){
			setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("businessId", businessId);
			map.put("productId", request.getParameter("productId"));
			
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = productToolService.getShopsProductTotalNew(map);
			List<ProductListAppDTO> productList =productToolService.getShopProductListNew(map);
			
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(productList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取农批商产品列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}	
	}	
	
	
	
	@RequestMapping("/addFocus")
	public void addFocus(HttpServletRequest request, HttpServletResponse response, UsercollectShopDTO usercollectShopDTO) {
		ObjectResult result = new ObjectResult();
		try {
			userCollectShopToolService.focusShop(usercollectShopDTO.getUserId(), usercollectShopDTO.getBusinessId());
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("关注农批商异常",e);
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 取消关注农批商
	 * @param request
	 * @param response
	 * @param usercollectShopDTO
	 */
	@RequestMapping("/cancelFocus")
	public void cancelFocus(HttpServletRequest request, HttpServletResponse response, UsercollectShopDTO usercollectShopDTO) {
		ObjectResult result = new ObjectResult();
		try {
			userCollectShopToolService.blurShop(usercollectShopDTO.getUserId(), usercollectShopDTO.getBusinessId());
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("取消关注农批商异常",e);
		}
		renderJson(result, request, response);
	}
	
	
}
