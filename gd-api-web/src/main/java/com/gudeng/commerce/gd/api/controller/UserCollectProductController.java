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

import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.ProductListAppDTO;
import com.gudeng.commerce.gd.api.dto.PushProductAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.api.service.impl.UsercollectProductServiceImpl;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.exception.BusinessException;

@Controller
@RequestMapping("userCollectProduct")
public class UserCollectProductController extends GDAPIBaseController {
	private static Logger logger = LoggerFactory.getLogger(UsercollectProductServiceImpl.class);
	@Autowired
	public UsercollectProductCategoryToolService usercollectProductCategoryService;

	@Autowired
	UsercollectProductToolService usercollectProductService;
	
	@Autowired
	ProductBaseinfoToolService productBaseinfoToolService;
	/**
	 * 获取关注的产品
	 * 详细信息列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getList")
	public void getList(HttpServletRequest request,
			HttpServletResponse response,UsercollectProductDTO usercollectProductDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			CommonPageDTO pageDTO = getPageInfo(request, map);
			usercollectProductDTO.setStartRow(pageDTO.getOffset());
			usercollectProductDTO.setEndRow(pageDTO.getPageSize());
			
			Long memberId = usercollectProductDTO.getUserId();
			Long businessId = usercollectProductDTO.getBusinessId();
			Long marketId = usercollectProductDTO.getMarketId();
			Long cateId = usercollectProductDTO.getCateId();
			
			int total = usercollectProductService.getTotal(memberId, businessId, marketId, cateId);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			
			List<PushProductAppDTO> list = usercollectProductService.getList(memberId,	businessId, marketId, cateId, 
					usercollectProductDTO.getStartRow(), usercollectProductDTO.getEndRow());
			if (list != null && list.size() > 0) {
				for (PushProductAppDTO productDTO : list) {
					int platform = productBaseinfoToolService.checkProductActivity(productDTO.getProductId(), memberId,
							null, productDTO.getMarketId());
					productDTO.setPlatform(platform);
				}
			}
			
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (BusinessException e) {
			logger.info("获取关注的产品列表异常", e);
		} catch (Exception e) {
			logger.info("获取关注的产品列表异常", e);
		}
		renderJson(result, request, response);
	}
	
	/*
	 * 添加关注单品
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addFocus")
	public void addFocus(HttpServletRequest request,
			HttpServletResponse response,UsercollectProductDTO usercollectProductDTO) {
		ObjectResult result = new ObjectResult();
		try {
			usercollectProductService.addFocus(
					usercollectProductDTO.getUserId(),
					usercollectProductDTO.getProductId());
			 List topFocusList=usercollectProductCategoryService.getFocusCategory(
					 usercollectProductDTO.getUserId(), 0, null);
			 
			 List secodnList=usercollectProductCategoryService.getFocusCategory(
					 usercollectProductDTO.getUserId(), 1, null);
			 Map<String, Object> retMap=new HashMap<String, Object>();
			 retMap.put("topFocusList", topFocusList);
			 retMap.put("secodnList", secodnList);
			 result.setObject(retMap);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} 
		catch (BusinessException e) {
			logger.info("添加关注单品异常", e);
		} catch (Exception e) {
			logger.info("添加关注单品异常", e);
		}
		renderJson(result, request, response);
	}

	
	@RequestMapping("/batAddFocus")
	public void batAddFocus(HttpServletRequest request,
			HttpServletResponse response,UsercollectProductDTO usercollectProductDTO) {
		ObjectResult result = new ObjectResult();
		try {
			usercollectProductService.batAddFocus(
					usercollectProductDTO.getUserId(),
					usercollectProductDTO.getProductIds());
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} 
		catch (BusinessException e) {
			logger.info("添加关注单品异常", e);
		} catch (Exception e) {
			logger.info("添加关注单品异常", e);
		}
		renderJson(result, request, response);
	}
	
	
	
	/*
	 * 取消关注單品
	 */
	@RequestMapping("/cancelFocus")
	public void cancelFocus(HttpServletRequest request,
			HttpServletResponse response,UsercollectProductDTO usercollectProductDTO) {
		ObjectResult result = new ObjectResult();
		try {
			usercollectProductService.cancelFocus(
					usercollectProductDTO.getUserId(),
					usercollectProductDTO.getProductId() );
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} 
		catch (BusinessException e) {
			logger.info("取消关注單品异常", e);
		} catch (Exception e) {
			logger.info("取消关注單品异常", e);
		}
		renderJson(result, request, response);
	}
	
	
}
