package com.gudeng.commerce.gd.api.controller.v161027;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.input.SearchInputDTO;
import com.gudeng.commerce.gd.api.dto.output.ProductOutputDetailDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;

/**
 * 农批商精准货源
 * 搜索相关接口服务
 * @date 2016年10月22日
 * @author TerryZhang
 */
@Controller
@RequestMapping("/v32/search")
public class SearchCdgysAccurateV32Controller extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SearchCdgysAccurateV32Controller.class);
	
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	private ProductBaseinfoToolService productBaseinfoToolService;
	
	/**
	 * 商铺,商品搜索接口
	 * （精准货源）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/accurateProduct")
	public void accurateProduct(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			SearchInputDTO inputDTO = (SearchInputDTO) getDecryptedObject(request, SearchInputDTO.class);
			if(inputDTO == null){
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_ERROR, request, response);
				return;
			}
			
			//用户id
			Integer memberId = ParamsUtil.getIntFromString(inputDTO.getMemberId(), null);
			// 查询类型 1商品 (默认) 2商铺
//			Integer optType = ParamsUtil.getIntFromString(inputDTO.getType(), 1);
			//经营类型，1表示种养大户，2表示合作社，3表示基地 0全部
			Integer managementType = ParamsUtil.getIntFromString(inputDTO.getManagementType(), 0);
			//价格排序 0默认 1升序 2降序
			Integer priceOdr = 0;
			//搜索字符串
			String searchStr = inputDTO.getKeyWord();
			
			if(StringUtils.isNotBlank(inputDTO.getPriceSort())){
				switch(inputDTO.getPriceSort().toUpperCase()){
					case "DESC" : 
						priceOdr = 2;
						break;
						
					case "ASC": 
						priceOdr = 1;
						break;
				}
			}
			
			if (memberId == null) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			if(StringUtils.isBlank(searchStr)){
				setEncodeResult(result, ErrorCodeEnum.SEARCH_STRING_IS_NULL, request, response);
				return;
			}
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("memberId", memberId);
			param.put("optType", 1);
			param.put("priceOdr", priceOdr);
			param.put("searchStr", searchStr);
			param.put("productProvinceId", inputDTO.getProductProvinceId());
			param.put("productCityId", inputDTO.getProductCityId());
			param.put("productAreaId", inputDTO.getProductAreaId());
			param.put("shopProvinceId", inputDTO.getShopProvinceId());
			param.put("shopCityId", inputDTO.getShopCityId());
			param.put("shopAreaId", inputDTO.getShopAreaId());
			if(managementType != 0){
				param.put("managementType", managementType);
			}
			CommonPageDTO pageDTO = getPageInfoEncript(request, param);
			PageQueryResultDTO<ProductOutputDetailDTO> pageResult = productBaseinfoToolService.getListSearchAccurateProduct(param);
			pageDTO.setRecordCount(pageResult.getTotalCount());
			pageDTO.initiatePage(pageResult.getTotalCount());
			pageDTO.setRecordList(pageResult.getDataList());
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[ERROR]精准货源搜索商品失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/accurateShop")
	public void accurateShop(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			SearchInputDTO inputDTO = (SearchInputDTO) getDecryptedObject(request, SearchInputDTO.class);
			if(inputDTO == null){
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_ERROR, request, response);
				return;
			}
			
			//用户id
			Integer memberId = ParamsUtil.getIntFromString(inputDTO.getMemberId(), null);
			//经营类型，1表示种养大户，2表示合作社，3表示基地 0全部
			Integer managementType = ParamsUtil.getIntFromString(inputDTO.getManagementType(), 0);
			String searchStr = inputDTO.getKeyWord();
			if (memberId == null) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			if(StringUtils.isBlank(searchStr)){
				setEncodeResult(result, ErrorCodeEnum.SEARCH_STRING_IS_NULL, request, response);
				return;
			}
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("memberId", memberId);
			param.put("searchStr", searchStr);
			param.put("shopProvinceId", inputDTO.getShopProvinceId());
			param.put("shopCityId", inputDTO.getShopCityId());
			param.put("shopAreaId", inputDTO.getShopAreaId());
			if(managementType != 0){
				param.put("managementType", managementType);
			}
			CommonPageDTO pageDTO = getPageInfoEncript(request, param);
			PageQueryResultDTO<BusinessAppDTO> pageResult = productBaseinfoToolService.getListSearchAccurateShop(param);
			pageDTO.setRecordCount(pageResult.getTotalCount());
			pageDTO.initiatePage(pageResult.getTotalCount());
			pageDTO.setRecordList(pageResult.getDataList());
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("[ERROR]精准货源搜索商铺失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
