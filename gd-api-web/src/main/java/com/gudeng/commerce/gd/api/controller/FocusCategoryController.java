package com.gudeng.commerce.gd.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.dto.AppProductCategoryDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.commerce.gd.exception.BusinessException;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
/**
 * 商品种类
 * 
 * @author steven
 * 
 */
@Controller
@RequestMapping("focusCategory")
public class FocusCategoryController extends GDAPIBaseController {
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(FocusCategoryController.class);
	@Autowired
	public ProCategoryService productCategoryService;
	@Autowired
	public UsercollectProductCategoryToolService usercollectProductCategoryService;
	@Autowired
	public ProductToolService productToolService;
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;

	/**
	 * 获取关注种类列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listProductCategory")
	public void listProductCategory(HttpServletRequest request,
			HttpServletResponse response,ProductCategoryDTO productCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			/**
			 * Semon 2016/06/21 加入参数判断
			 */
			if(null==productCategoryDTO.getMarketId()){
				result.setMsg("参数异常");
				renderJson(result, request, response);
				return;
			}
			
			
			List<ProductCategoryDTO> list = productCategoryService
					.getProductCategory(productCategoryDTO.getMarketId());
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info("获取关注种类异常", e);
		} catch (Exception e) {
			logger.info("获取关注种类异常", e);
		}
		renderJson(result, request, response);
	}

	/**
	 * 获取顶级产品分类
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listTopProductCategory")
	public void listTopProductCategory(HttpServletRequest request,
			HttpServletResponse response,UsercollectProductCategoryDTO usercollectProductCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			List<AppProductCategoryDTO> list = productCategoryService
					.listTopProductCategory(usercollectProductCategoryDTO.getUserId(),
							usercollectProductCategoryDTO.getMarketId());
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info("获取关注种类异常", e);
		} catch (Exception e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info("获取关注种类异常", e);
		}
		renderJson(result, request, response);
	}

//	/**
//	 * 获取所有的产品分类，且带有子类
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("/listTopCateWithSub")
//	public void listTopCateWithSub(HttpServletRequest request,
//			HttpServletResponse response) {
//		ObjectResult result = new ObjectResult();
//		try {
//			List<AppProductCategoryDTO> list = productCategory
//					.listTopCateWithSub();
//			result.setObject(list);
//			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
//			result.setMsg("success");
//		} catch (BusinessException e) {
//			logger.info("获取关注种类异常", e);
//		} catch (Exception e) {
//			logger.info("获取关注种类异常", e);
//		}
//		renderJson(result, request, response);
//	}

	/**
	 * 根据类别ID或者子类别
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getChildProductCategory")
	public void getChildProductCategory(HttpServletRequest request,
			HttpServletResponse response, UsercollectProductCategoryDTO productCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			List<AppProductCategoryDTO> list = productCategoryService
					.getChildProductCategory(
							productCategoryDTO.getProductCategoryId(),
							productCategoryDTO.getUserId(),
							productCategoryDTO.getMarketId());
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info("类别ID或者子类别异常", e);
		} catch (Exception e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info("类别ID或者子类别异常", e);
		}
		renderJson(result, request, response);
	}

	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {

		System.out.println(gdProperties.getProperties().getProperty(
				"gd.encrypt.key"));

		System.out.println(gdProperties.getProperties().getProperty(
				"gd.encrypt.key"));
	}

	/**
	 * 用户添加关注类别
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addFocus")
	public void addFocus(HttpServletRequest request,
			HttpServletResponse response,
			UsercollectProductCategoryDTO usercollectProductCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			 usercollectProductCategoryService.focus(usercollectProductCategoryDTO.getProductCategoryId(),
							usercollectProductCategoryDTO.getUserId());
			 
			 List topFocusList=usercollectProductCategoryService.getFocusCategory(
					 usercollectProductCategoryDTO.getUserId(), 0, null);
			 
			 List secodnList=usercollectProductCategoryService.getFocusCategory(
					 usercollectProductCategoryDTO.getUserId(), 1, null);
			 Map<String, Object> retMap=new HashMap<String, Object>();
			 retMap.put("topFocusList", topFocusList);
			 retMap.put("secodnList", secodnList);
			result.setObject(retMap);
			 
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (BusinessException e) {
			logger.info(" 用户添加关注异常", e);
		} catch (Exception e) {
			logger.info(" 用户添加关注异常", e);
		}
		renderJson(result, request, response);
	}

	/**
	 * 批量添加关注分类
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/batchAddFocus")
	public void batchAddFocus(HttpServletRequest request,
			HttpServletResponse response,
			UsercollectProductCategoryDTO usercollectProductCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			 usercollectProductCategoryService.batchAddFocus( 
							usercollectProductCategoryDTO.getUserId(),usercollectProductCategoryDTO.getCategoryIds());
		
			 
			 List topFocusList=usercollectProductCategoryService.getFocusCategory(
					 usercollectProductCategoryDTO.getUserId(), 0, null);
			 
			 List secodnList=usercollectProductCategoryService.getFocusCategory(
					 usercollectProductCategoryDTO.getUserId(), 1, null);
			 Map<String, Object> retMap=new HashMap<String, Object>();
			 retMap.put("topFocusList", topFocusList);
			 retMap.put("secodnList", secodnList);
			 
			 result.setObject(retMap);
			 result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 用户添加关注异常", e);
		} catch (Exception e) {
			logger.info(" 用户添加关注异常", e);
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 用户取消关注
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cancelFocus")
	public void cancelFocus(HttpServletRequest request, HttpServletResponse response,
			UsercollectProductCategoryDTO usercollectProductCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Long userId = usercollectProductCategoryDTO.getUserId();
			Long categoryId= usercollectProductCategoryDTO.getProductCategoryId();
			if(userId == null){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			if(categoryId == null){
				setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return;
			}
			
			usercollectProductCategoryService.cancelFocus(categoryId, userId);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			result.setMsg("取消关注异常,请稍后再试");
			logger.info("取消关注异常", e);
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 用户批量取消关注
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/batchCancelFocus")
	public void batchCancelFocus(HttpServletRequest request, HttpServletResponse response,
			UsercollectProductCategoryDTO usercollectProductCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Long userId = usercollectProductCategoryDTO.getUserId();
			String categoryIds= usercollectProductCategoryDTO.getCategoryIds();
			if(userId == null){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			if(StringUtils.isBlank(categoryIds)){
				setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return;
			}
			
			usercollectProductCategoryService.batchCancelFocus(userId, categoryIds);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			result.setMsg("批量取消关注异常,请稍后再试");
			logger.info("用户批量取消关注异常", e);
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 获取用户关注的所有分类
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getByCateId")
	public void getByCateId(HttpServletRequest request,
			HttpServletResponse response,
			ProductDto productDto) {
		ObjectResult result = new ObjectResult();
		try {
			List retList= productToolService.getByCateId(productDto.getCateId());
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setObject(retList);
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		} catch (Exception e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		}
		renderJson(result, request, response);
	}
	
	@RequestMapping("/getFocusCategory")
	public void getFocusCategory(HttpServletRequest request,
			HttpServletResponse response,
			UsercollectProductCategoryDTO usercollectProductCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			List<UsercollectProductCategoryDTO> retList= usercollectProductCategoryService.getFocusCategory(
					usercollectProductCategoryDTO.getUserId(),
					usercollectProductCategoryDTO.getCurLevel(), usercollectProductCategoryDTO.getMarketId());
			//更改数据结构
			if(retList != null && retList.size() > 0){
				Map<Long, List<UsercollectProductCategoryDTO>> map = new HashMap<Long, List<UsercollectProductCategoryDTO>>();
				for(int i=0,len=retList.size(); i<len; i++){
					UsercollectProductCategoryDTO dto = retList.get(i);
					Long marketId = dto.getMarketId();
					if(map.containsKey(marketId)){
						List<UsercollectProductCategoryDTO> list = (List<UsercollectProductCategoryDTO>) map.get(marketId);
						list.add(dto);
						map.put(marketId, list);
					}else{
						List<UsercollectProductCategoryDTO> list = new ArrayList<UsercollectProductCategoryDTO>();
						list.add(dto);
						map.put(marketId, list);
					}
				}
				result.setObject(map.values());
			}
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		} catch (Exception e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		}
		renderJson(result, request, response);
	}
	
	
	@RequestMapping("/getTotalCateList")
	public void getTotalCateList(HttpServletRequest request,
			HttpServletResponse response,
			UsercollectProductCategoryDTO usercollectProductCategoryDTO) {
		ObjectResult result = new ObjectResult();
		try {
			List<AppProductCategoryDTO> retList= productCategoryService.getTotalCateList(
						usercollectProductCategoryDTO.getUserId(),
						usercollectProductCategoryDTO.getMarketId());
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setObject(retList);
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		} catch (Exception e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param usercollectProductCategoryDTO
	 */
	@RequestMapping("/getTotalCateList0929")
	public void getTotalCateList0929(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			
			String marketId = (String)GSONUtils.getJsonValueStr(jsonStr, "marketId");
			String userId = (String)GSONUtils.getJsonValueStr(jsonStr, "userId");
			String sortByCategory = (String)GSONUtils.getJsonValueStr(jsonStr, "sortByCategory");
			String businessId = (String)GSONUtils.getJsonValueStr(jsonStr, "businessId");
			Long uId = Long.parseLong(userId);
			Long mId = Long.parseLong(marketId);
			
			List<AppProductCategoryDTO> retList= productCategoryService.getTotalCateList(uId,mId);
			
			if ("1".equalsIgnoreCase(sortByCategory)){
				retList = sortByCategory(retList, businessId);
			}
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setObject(retList);
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		} catch (Exception e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		//renderJson(result, request, response);
	}	

	
	/**
	 * 
	 * @param categoryList
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	private List<AppProductCategoryDTO> sortByCategory(List<AppProductCategoryDTO> categoryList, String businessId) throws Exception{
		//如果businessId为空则令下面的list为空
		if (CommonUtils.isEmpty(businessId)){
			businessId = "-1";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//businessType 0-主营分类, 1-兼营分类
		map.put("sortName", "businessType");
		map.put("sortOrder", "a");
		map.put("businessId", businessId);
		map.put("startRow", 0);
		map.put("endRow", 10000000);
		//排序后主营为list第一个元素
		List<ReBusinessCategoryDTO> list = reBusinessCategoryToolService.getBySearch(map);
		List<AppProductCategoryDTO> prority = new ArrayList<AppProductCategoryDTO>();
		AppProductCategoryDTO current = null ;
		//遍历主营与兼营分类
		for(int i = 0, len = list.size(); i < len ; i++){
			Iterator<AppProductCategoryDTO> it = categoryList.iterator();
			while(it.hasNext()){
				current = it.next();
				if (list.get(i).getCategoryId().longValue() == current.getCategoryId().longValue()){
					prority.add(current);
					//将当前分类(主营或者兼营)从一级分类列表categoryList中移除
					it.remove();
					break ;
				}
			}
		}
		//添加一级分类列表categoryList中的其他元素
		prority.addAll(categoryList);
		return prority;
	}	

}
