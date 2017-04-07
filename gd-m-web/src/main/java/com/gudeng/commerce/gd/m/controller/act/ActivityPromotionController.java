package com.gudeng.commerce.gd.m.controller.act;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.m.dto.ProCommonPageDTO;
import com.gudeng.commerce.gd.m.dto.PromotionActivityInputDTO;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.service.act.ActivityPromotionToolService;
import com.gudeng.commerce.gd.m.util.DateUtil;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.JSONUtils;
import com.gudeng.commerce.gd.m.util.ObjectResult;
import com.gudeng.commerce.gd.promotion.dto.PictureRefDTO;
import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromMarketDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;

/**
 * 产销会活动
 */
@Controller
@RequestMapping("activityPromotion")
public class ActivityPromotionController extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ActivityPromotionController.class);
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private ActivityPromotionToolService activityPromotionToolService;

	@RequestMapping("/activity/list")
	public void activityList(HttpServletRequest request, HttpServletResponse response,
			PromotionActivityInputDTO paramDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			ProCommonPageDTO pageDTO = getPageInfo(request, map);
			String userId = paramDTO.getUserId();
			if (userId != null && !"".equals(userId)) {
				map.put("supplierId", userId);
			}
			int total = activityPromotionToolService.getTotal(map);
			if (total > 0) {
				List<PromBaseinfoDTO> activityList = activityPromotionToolService.queryPromoteActivitys(map);
				if (activityList != null && activityList.size() > 0) {
					for (PromBaseinfoDTO promDTO : activityList) {
						int promBaseinfoId = promDTO.getActId();
						List<PictureRefDTO> pictureRefList = activityPromotionToolService.getPictures(promBaseinfoId);
						promDTO.setPictureRefList(pictureRefList);
					}
				}
				pageDTO.setRecordList(activityList);
			}

			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			result.setObject(pageDTO);

			setResultFormat(result, ErrorCodeEnum.SUCCESS, request, response);

		} catch (Exception e) {
			logger.warn("[ERROR]查询活动信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/activity/detail")
	public void activityDetail(HttpServletRequest request, HttpServletResponse response,
			PromotionActivityInputDTO paramDTO) {
		ObjectResult result = new ObjectResult();
		try {

			Integer actId = paramDTO.getActId();
			if (actId == null || actId <= 0) {
				setResult(result, ErrorCodeEnum.PROM_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("actId", actId);
			
			String userId=paramDTO.getUserId();
			if(userId!=null&&!"".equals(userId)){
				paramMap.put("supplierId", userId);
			}
			PromBaseinfoDTO promDTO = activityPromotionToolService.getDetailActivity(paramMap);
			if (promDTO != null) {
				List<PictureRefDTO> pictureRefList = activityPromotionToolService.getPictures(actId);
				promDTO.setPictureRefList(pictureRefList);

				List<PromMarketDTO> promMarketList = activityPromotionToolService.getMarkets(actId);
				promDTO.setMarketList(promMarketList);
			} else {
				setResult(result, ErrorCodeEnum.PROM_ACT_NOT_EXISTED, request, response);
				return;
			}
			result.setObject(promDTO);
			setResultFormat(result, ErrorCodeEnum.SUCCESS, request, response);
			// setEncodeResultFormat(result, ErrorCodeEnum.SUCCESS.getValue(),
			// "", request, response);

		} catch (Exception e) {
			logger.warn("[ERROR]查询活动详细信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/activity/cancel")
	public void activityCancel(HttpServletRequest request, HttpServletResponse response,
			PromotionActivityInputDTO paramDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Integer actId = paramDTO.getActId();
			if (actId == null || "".equals(actId)) {
				setResult(result, ErrorCodeEnum.PROM_ID_IS_NULL, request, response);
				return;
			}
			String userId=paramDTO.getUserId();
			if (userId == null ||"".equals(userId)) {
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("actId", actId);
			paramMap.put("supplierId", userId);
			paramMap.put("refStatus", "2");
			paramMap.put("auditOpinion", "已取消");
			paramMap.put("auditStatus", "3");
			activityPromotionToolService.cancelPromotionActivity(paramMap);
			setResultFormat(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]取消活动信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/product/list")
	public void productList(HttpServletRequest request, HttpServletResponse response,
			PromotionActivityInputDTO paramDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String userId = paramDTO.getUserId();
			if (userId == null || "".equals(userId)) {
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			String productType = paramDTO.getProductType();
			if (productType == null || "".equals(productType)) {
				setResult(result, ErrorCodeEnum.PRODUCT_TYPE_IS_NULL, request, response);
				return;
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);

			int total = 0;
			ProCommonPageDTO pageDTO = getPageInfo(request, paramMap);
			List<ProductBaseinfoDTO> productList = null;
			// 获取可以参加活动的商品
			if ("1".equals(productType)) {
				String marketId = paramDTO.getMarketId();
				if (marketId != null && !"".equals(marketId)) {
					String[] marketArr=marketId.split(",");
					List<String> marketList=Arrays.asList(marketArr);
					paramMap.put("marketIds", marketList);
				}

				total = activityPromotionToolService.getProductTotalByUser(paramMap);
				if (total > 0) {
					productList = activityPromotionToolService.getUserProducts(paramMap);
					pageDTO.setRecordList(productList);
				}
			}

			// 获取参加某个活动的商品
			if ("2".equals(productType)) {
				Integer actId = paramDTO.getActId();
				if (actId == null) {
					setResult(result, ErrorCodeEnum.PROM_ID_IS_NULL, request, response);
					return;
				}
				paramMap.put("actId", actId);
				total = activityPromotionToolService.getProductTotalByParticipants(paramMap);
				if (total > 0) {
					productList = activityPromotionToolService.getUserParticipantsProducts(paramMap);
					pageDTO.setRecordList(productList);
				}
			}

			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			result.setObject(pageDTO);

			setResultFormat(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询商品列表信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}


	
	@RequestMapping("/product/add")
	public void productAdd(HttpServletRequest request, HttpServletResponse response,
			PromotionActivityInputDTO paramDTO) {
		ObjectResult result = new ObjectResult();
		try {

			String activityId = paramDTO.getActivityId();
			if (activityId == null || "".equals(activityId)) {
				setResult(result, ErrorCodeEnum.PROM_ID_IS_NULL, request, response);
				return;
			}

			String supplierId = paramDTO.getSupplierId();
			if (supplierId == null || "".equals(supplierId)) {
				setResult(result, ErrorCodeEnum.GYS_ID_IS_NULL, request, response);
				return;
			}

			String supplierName = paramDTO.getSupplierName();
			if (supplierName == null || "".equals(supplierName)) {
				setResult(result, ErrorCodeEnum.GYS_NAME_IS_NULL, request, response);
				return;
			}

			String productDetails = paramDTO.getProductDetails();
			if (productDetails == null || "".equals(productDetails)) {
				setResult(result, ErrorCodeEnum.PRODUCT_INFO_IS_NULL, request, response);
				return;
			}

			JSONArray jsonArr = JSONUtils.parseArray(productDetails);
			List<PromProdInfoDTO> prodList = new ArrayList<PromProdInfoDTO>();
			for (int i = 0, len = jsonArr.size(); i < len; i++) {
				JSONObject jsonObject = (JSONObject) jsonArr.get(i);

				Long productId = jsonObject.getLong("productId");
				if (productId == null || "".equals(productId)) {
					setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
					return;
				}
				String productName = jsonObject.getString("productName");
				if (productName == null || "".equals(productName)) {
					setResult(result, ErrorCodeEnum.PRODUCT_NAME_IS_NULL, request, response);
					return;
				}

				Double actPrice = jsonObject.getDouble("actPrice");
				Double zero = new Double(0);
				if (actPrice == null || actPrice.compareTo(zero) <= 0) {
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}

				Double supplyQty = jsonObject.getDouble("supplyQty");
				if (supplyQty == null || supplyQty.compareTo(zero) <= 0) {
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}

				PromProdInfoDTO prodDto = new PromProdInfoDTO();

				prodDto.setProdId(productId);
				prodDto.setProdName(productName);
				prodDto.setActPrice(actPrice);
				prodDto.setSupplyQty(supplyQty);

				prodDto.setActId(Integer.parseInt(activityId));
				prodDto.setSupplierId(Integer.parseInt(supplierId));
				prodDto.setSupplierName(supplierName);
				prodDto.setCreateTime(DateUtil.getNow());
				prodDto.setCreateUserId(supplierId);
				prodDto.setUpdateTime(DateUtil.getNow());
				prodList.add(prodDto);
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("productList", prodList);
			activityPromotionToolService.addActivityProducts(paramMap);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]添加活动商品信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
