package com.gudeng.commerce.gd.api.controller.v161121;

import java.util.ArrayList;
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
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.input.ProductInputDTO;
import com.gudeng.commerce.gd.api.dto.output.ProductOutputDetailDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;

/**
 * @purpose 农批商相关接口服务
 * @date 2016年11月15日
 * 版本变更说明：新增经营范围筛选
 */
@Controller
@RequestMapping("/v161121/agricultural")
public class AgriculturalLotControllerv161121 extends GDAPIBaseController {

	private static final String TYPE_JZ = "1";// 精准货源
	private static final String TYPE_TJ = "2";// 推荐货源
	@Autowired
	private ProductBaseinfoToolService productBaseinfoToolService;
	
	@Autowired 
	BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	ProductToolService productToolService;
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AgriculturalLotControllerv161121.class);

	/**
	 * 获取精准货源、推荐货源
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("proudct")
	public void getSourceGoods(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			ProductInputDTO inputDTO = (ProductInputDTO) getDecryptedObject(request, ProductInputDTO.class);

			if (StringUtils.isEmpty(inputDTO.getType())) {
				setEncodeResult(result, ErrorCodeEnum.TYPE_ID_IS_NULL, request, response);
				return;
			}
			String type = inputDTO.getType();
			if (StringUtils.isEmpty(inputDTO.getMemberId())) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			param.put("userId", inputDTO.getMemberId());
			if (StringUtils.isEmpty(inputDTO.getQueryMode()) && inputDTO.getType().equals(TYPE_JZ)) {
				setEncodeResult(result, ErrorCodeEnum.TYPE_ID_IS_NULL, request, response);
				return;
			}
			if (StringUtils.isNotEmpty(inputDTO.getQueryMode())) {
				param.put("queryMode", inputDTO.getQueryMode());
			}
			if (StringUtils.isNotEmpty(inputDTO.getArea1())) {
				param.put("area1", inputDTO.getArea1());
			}
			if (StringUtils.isNotEmpty(inputDTO.getArea2())) {
				param.put("area2", inputDTO.getArea2());
			}
			if (StringUtils.isNotEmpty(inputDTO.getArea3())) {
				param.put("area3", inputDTO.getArea3());
			}
			if (StringUtils.isNotEmpty(inputDTO.getDistrict1())) {
				param.put("provinceId", inputDTO.getDistrict1());
			}
			if (StringUtils.isNotEmpty(inputDTO.getDistrict2())) {
				param.put("cityId", inputDTO.getDistrict2());
			}
			if (StringUtils.isNotEmpty(inputDTO.getDistrict3())) {
				param.put("areaId", inputDTO.getDistrict3());
			}
			if (StringUtils.isNotEmpty(inputDTO.getManageType())) {
				param.put("manageType", inputDTO.getManageType());
			}
			if (StringUtils.isNotEmpty(inputDTO.getPriceSort())) {
				param.put("priceSort", inputDTO.getPriceSort());
			}
			if (StringUtils.isNotEmpty(inputDTO.getCategoryId())) {
				List<String> categoryIds = new ArrayList<>();
				String [] categoryIdAry = inputDTO.getCategoryId().split(",");
				for(String categoryId:categoryIdAry){
					categoryIds.add(categoryId);
				}
				param.put("categoryIds", categoryIds);
			}
			param.put("level", "4");         // 供应商商铺
			param.put("shopRecommend", "1"); // 商铺推荐(null:不推荐，1:推荐)
			CommonPageDTO pageDTO = getPageInfoEncript(request, param);
			int total = 0;
			List<ProductOutputDetailDTO> productList = null;
			boolean isHaveBusiness = false;//当前会员是否有商铺
			boolean isHaveProduct = false;//当前会员是否有商品
			//查看当前用户是否有商铺
			BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getByUserId(inputDTO.getMemberId());
			if(null != businessBaseinfoDTO){
				isHaveBusiness = true;
				//如有商铺则判断是否有商铺
				Map<String, Object> map = new HashMap<>();
				map.put("businessId", businessBaseinfoDTO.getBusinessId());
				if(productToolService.getProductCountByBusinessId(map) > 0 ){
					isHaveProduct = true;
				}
			}
			param.put("isHaveBusiness", isHaveBusiness);
			switch (type) {
			case TYPE_JZ:
				total = productBaseinfoToolService.getSupplierProductTotalByPrecise(param);
				if (total > 0) {
					productList = productBaseinfoToolService.getSupplierProductByPrecise(param);
					pageDTO.setRecordCount(total);
					pageDTO.initiatePage(total);
					pageDTO.setRecordList(productList);
				}
				break;
			case TYPE_TJ:
				total = productBaseinfoToolService.getSupplierProductTotalRecommend(param);
				if (total > 0) {
					productList = productBaseinfoToolService.getSupplierProductByRecommend(param);
					pageDTO.setRecordCount(total);
					pageDTO.initiatePage(total);
					pageDTO.setRecordList(productList);
				}
				break;
			}
			Map<String ,Object> res = new HashMap<>();
			res.put("result", pageDTO);
			res.put("isHaveBusiness", isHaveBusiness);//当前会员是否有商铺
			res.put("isHaveProduct", isHaveProduct);//当前会员是否有商品
			result.setObject(res);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (

		Exception e)

		{
			logger.warn("[ERROR]查询商品失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
