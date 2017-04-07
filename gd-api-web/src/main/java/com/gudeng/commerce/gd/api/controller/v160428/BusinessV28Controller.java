package com.gudeng.commerce.gd.api.controller.v160428;

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

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.input.BusinessInputDTO;
import com.gudeng.commerce.gd.api.dto.output.BusinessDetailAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;

/**
 * 农批商控制中心
 * @author TerryZhang
 */
@Controller
@RequestMapping("v28/business")
public class BusinessV28Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(BusinessV28Controller.class);
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	@Autowired
	private AreaToolService areaToolService;
	@Autowired
	private ProductToolService productToolService;
	
	@RequestMapping("/getShopDetail")
	public void getShopDetail(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			String jsonStr = getParamDecodeStr(request);
			//将json 字符串封装为对象
			BusinessInputDTO inputParamDTO=(BusinessInputDTO) GSONUtils.fromJsonToObject(jsonStr, BusinessInputDTO.class); 
			Long businessId = inputParamDTO.getBusinessId();
			Long memberId = inputParamDTO.getMemberId();
			if(businessId == null){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			
			if(memberId == null){
				memberId = 0L;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", businessId);
			map.put("memberId", memberId);
			BusinessDetailAppDTO businessDTO = businessBaseinfoToolService.getByMap(map);
			if(businessDTO != null){
				//设置省份城市区县信息
				AreaDTO areaDTO = areaToolService.getAreaDetail(businessDTO.getProvinceId(), 
						businessDTO.getCityId(), businessDTO.getCountyId());
				if(areaDTO != null){
					businessDTO.setProvinceName(areaDTO.getpParentName());
					businessDTO.setCityName(areaDTO.getParentName());
					businessDTO.setCountyName(areaDTO.getArea());
				}
				//设置经营分类
				map.put("startRow", 0);
				map.put("endRow", 9999);
				List<ReBusinessCategoryDTO> categoryIds=businessBaseinfoToolService.getReBusinessCategoryBySearch(map);
				if(categoryIds!=null && categoryIds.size() > 0){
					String[] businessScope = new String[categoryIds.size()];
					int count=0;
					for(ReBusinessCategoryDTO category:categoryIds){
						businessScope[count] = category.getCateName();
						count++;
					}
					businessDTO.setBusinessScope(businessScope);
				}
				result.setObject(businessDTO);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_IS_NOT_EXISTED, request, response);
			}
		}catch(Exception e){
			logger.warn("[ERROR]获取农批商详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getShopList")
	public void getShopList(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			
			String jsonStr = getParamDecodeStr(request);
			//将json 字符串封装为对象
			BusinessBaseinfoDTO inputParamDTO=(BusinessBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, BusinessBaseinfoDTO.class); 
			Long memberId = inputParamDTO.getUserId();
			Integer isFocus = inputParamDTO.getIsFocus();
			String marketId = inputParamDTO.getMarketId();
			Long categoryId = inputParamDTO.getCategoryId();
			if(memberId == null){
				memberId = 0L;
			}
			
			if(categoryId == null && 1 != isFocus){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_CATEGORY_ID_IS_NULL, request, response);
				return;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("categoryId", categoryId);
			map.put("marketId", marketId);
			//查找已关注的列表
			if(1 == isFocus){
				map.put("isFocus", 1);
				
			}
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = businessBaseinfoToolService.getShopsTotal(map);
			List<BusinessAppDTO> businessList = businessBaseinfoToolService.getShops(map);
			//设置商铺产品数量
			if(businessList != null && businessList.size() > 0){
				for(BusinessAppDTO dto : businessList){
					ProductQueryBean productQueryBean = new ProductQueryBean();
					productQueryBean.setBusinessId(dto.getBusinessId());
					ProductSearchResultDTO resultDTO = productToolService.getByProductQueryBean(productQueryBean);
					dto.setProductCount(resultDTO == null ? 0 : resultDTO.getCount().intValue());
				}
			}
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(businessList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取农批商列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
