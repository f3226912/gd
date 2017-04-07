package com.gudeng.commerce.gd.m.controller.transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class TransferDetailController extends MBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(TransferDetailController.class);

	@Resource
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;

	@RequestMapping("transferDetail/{memberAddressId}/{type}/{source}")
	public String transferDetail(@PathVariable("memberAddressId") Long memberAddressId, @PathVariable String type, @PathVariable Integer source, HttpServletRequest request, ModelMap modelMap) {

		try {

			// initialize query
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", type);
			params.put("memberAddressId", memberAddressId);
			params.put("source", source);

			// do query
			findDetail(params, modelMap);

			// put response result
			modelMap.put("pa", request.getParameter("pa"));
			modelMap.put("c", request.getParameter("c"));
			modelMap.put("memberAddressId", memberAddressId);
			modelMap.put("fromCode", request.getParameter("fromCode"));
			modelMap.put("memberId", request.getParameter("memberId"));
			modelMap.put("orderNo", request.getParameter("orderNo"));
			modelMap.put("nobId", request.getParameter("nobId"));
			modelMap.put("source", request.getParameter("source"));

		} catch (Exception e) {

			logger.warn("查询货物详情异常:" + e.getMessage(), e);
		}

		// deal view
		return getView(request.getParameter("fromCode"), type);
	}

	@RequestMapping("transferOrderDetail/{memberAddressId}")
	public String transferOrderDetail(@PathVariable("memberAddressId") Long memberAddressId, HttpServletRequest request, ModelMap modelMap) {
		try {
			List<OrderDeliveryDetailDTO> orderList = productDeliveryDetailToolService.getOrderByMemberAddressId(memberAddressId);
			modelMap.put("orderList", orderList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("查询货物详情-订单异常:" + e.getMessage());
		}

		String fromCode = request.getParameter("fromCode");
		switch (fromCode) {
		case "1":
			return "H5/transfer/nsy_transferOrderDetail";
		case "3":
			return "H5/transfer/nps_transferOrderDetail";
		default:
			return "H5/transfer/nsy_transferOrderDetail";
		}
	}

	@RequestMapping("transferProductDetail/{memberAddressId}")
	public String transferProductDetail(@PathVariable("memberAddressId") Long memberAddressId, HttpServletRequest request, ModelMap modelMap) {
		try {
			List<ProductDeliveryDetailDTO> productList = productDeliveryDetailToolService.getProductByMemberAddressId(memberAddressId);
			modelMap.put("productList", productList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("查询货物详情-商品异常:" + e.getMessage());
		}

		String fromCode = request.getParameter("fromCode");
		switch (fromCode) {
		case "1":
			return "H5/transfer/nsy_transferProductDetail";
		case "3":
			return "H5/transfer/nps_transferProductDetail";
		default:
			return "H5/transfer/nsy_transferProductDetail";
		}
	}

	/**
	 * 查询详细信息
	 * @param params
	 * @param modelMap
	 * @throws ServiceException
	 */
	private void findDetail(Map<String, Object> params, ModelMap modelMap) throws ServiceException {

		String type = MapUtils.getString(params, "type");

		if ("1".equals(params.get("type"))) {
			
			List<OrderDeliveryDetailDTO> orderList = productDeliveryDetailToolService.getOrderByMember(params);
			modelMap.put("orderList", orderList);

		} else if ("2".equals(type)) {
			
			List<ProductDeliveryDetailDTO> productList = productDeliveryDetailToolService.getProductByMember(params);
			modelMap.put("productList", productList);
		} else {
			logger.warn("【警告】未知参数边界！Type:" + type);
		}
	}

	/**
	 * 获取流转视图
	 * @param fromCode
	 * @param type 
	 * @return
	 */
	private String getView(String fromCode, String type) {
		
		Map<String, String> viewMap = new HashMap<String, String>();		
		
		if("1".equals(type)){
			
			viewMap.put("1", "H5/transfer/nsy_transferOrderDetail");
			viewMap.put("3", "H5/transfer/nps_transferOrderDetail");
			return MapUtils.getString(viewMap, fromCode, "H5/transfer/nsy_transferOrderDetail");
		}
		
		if("2".equals(type)){
			viewMap.put("1", "H5/transfer/nsy_transferProductDetail");
			viewMap.put("3", "H5/transfer/nps_transferProductDetail");
			return MapUtils.getString(viewMap, fromCode, "H5/transfer/nsy_transferProductDetail");
		}
		
		return "H5/transfer/nsy_transferOrderDetail";
	}
}
