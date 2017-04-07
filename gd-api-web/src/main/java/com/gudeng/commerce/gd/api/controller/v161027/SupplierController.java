package com.gudeng.commerce.gd.api.controller.v161027;

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
import com.gudeng.commerce.gd.api.dto.input.SupplierInputDTO;
import com.gudeng.commerce.gd.api.dto.output.ProductOutputDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.SupplierBusinessBaseinfo;
import com.gudeng.commerce.gd.api.dto.output.TradingDynamicsOutDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.StringUtils;

/**
 * @purpose 供应商相关接口服务
 * @date 2016年10月18日
 */
@Controller
@RequestMapping("/v161027/supplier")
public class SupplierController extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SupplierController.class);
	private static final String BUSINESS_TYPE_TJMJ = "3"; // 类型3 推荐买家不用登录
	private static final String BUSINESS_TYPE_JZMJ = "2"; // 类型2 精准买家
	private static final String BUSINESS_TYPE_MJ = "1"; // 类型1 买家

	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private ProductBaseinfoToolService productBaseinfoToolService;
	/**
	 * 商铺查询接口（找买家、精准买家、推荐买家）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("business")
	public void getBusiness(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			SupplierInputDTO inputDTO = (SupplierInputDTO) getDecryptedObject(request, SupplierInputDTO.class);

			if (StringUtils.isEmpty(inputDTO.getType())) {
				setEncodeResult(result, ErrorCodeEnum.TYPE_ID_IS_NULL, request, response);
				return;
			}
			if (StringUtils.isEmpty(inputDTO.getMemberId()) && !BUSINESS_TYPE_TJMJ.equals(inputDTO.getType())) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}

			if (StringUtils.isNotEmpty(inputDTO.getMarketId())) {
				param.put("marketId", inputDTO.getMarketId());
			}
			if (StringUtils.isNotEmpty(inputDTO.getMemberId())) {
				param.put("userId", inputDTO.getMemberId());
			}
			param.put("level", "4");              // 排除供应商商铺
			param.put("shopRecommend", "1");      // 商铺推荐(null:不推荐，1:推荐)
			param.put("type", inputDTO.getType());// 查询类型
			param.put("keyWord", inputDTO.getKeyWord());// 查询类型

			CommonPageDTO pageDTO = getPageInfoEncript(request, param);
			int total = businessBaseinfoToolService.getTotalBusiness(param);
			List<SupplierBusinessBaseinfo> businessList = null;
			if (total > 0) {
				businessList = businessBaseinfoToolService.getBusiness(param);
			}
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(businessList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询商铺失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 获取交易动态接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("dynamic")
	public void getTradingDynamicss(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String,Object> param=new HashMap<String,Object>();
			List<TradingDynamicsOutDTO> tradingList=businessBaseinfoToolService.getTradingDynamics(param);
			result.setObject(tradingList);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询商铺交易动态失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 获取供应商商品行情接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("product")
	public void getProductQuotation(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			ProductInputDTO inputDTO = (ProductInputDTO) getDecryptedObject(request, ProductInputDTO.class);

			if (StringUtils.isNotEmpty(inputDTO.getMemberId())) {
				param.put("userId", inputDTO.getMemberId());
			}
			if (StringUtils.isNotEmpty(inputDTO.getCategoryId())) {
				param.put("cateId", inputDTO.getCategoryId());
			}
			if (StringUtils.isNotEmpty(inputDTO.getDistrict1())) {
				param.put("provinceId", inputDTO.getDistrict1());
			}
			if (StringUtils.isNotEmpty(inputDTO.getDistrict1())) {
				param.put("cityId", inputDTO.getDistrict2());
			}
			if (StringUtils.isNotEmpty(inputDTO.getDistrict1())) {
				param.put("areaId", inputDTO.getDistrict3());
			}
			param.put("level", "4");              // 供应商商铺
			param.put("shopRecommend", "1");      // 商铺推荐(null:不推荐，1:推荐)

			CommonPageDTO pageDTO = getPageInfoEncript(request, param);
			int total = productBaseinfoToolService.getSupplierProductTotal(param);
			List<ProductOutputDetailDTO> productList = null;
			if (total > 0) {
				productList = productBaseinfoToolService.getSupplierProduct(param);
			}
			// 根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(productList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询商品失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
