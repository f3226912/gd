package com.gudeng.commerce.gd.api.controller.pos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.ProductListAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.ws.utils.StringUtil;

/**
 * 商铺产品管理v2.1
 * @author TerryZhang
 */
@Controller
@RequestMapping("v21/product/")
public class ProductV21Controller extends GDAPIBaseController {

		private static final GdLogger logger = GdLoggerFactory.getLogger(ProductV21Controller.class);
		
		@Autowired
		public ProductToolService productToolService;
		@Autowired
		public ProductBaseinfoToolService productBaseinfoToolService;
		
		
		@RequestMapping("getShopProductList")
		public void getShopProductList(String userId, String businessId, String productId, 
				HttpServletRequest request, HttpServletResponse response){
			ObjectResult result = new ObjectResult();
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(StringUtils.isEmpty(userId)){
				userId = "0";
			}
			
			if(StringUtils.isEmpty(businessId)){
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			try {
				map.put("memberId", userId);
				map.put("businessId", businessId);
				map.put("productId", productId);
				CommonPageDTO pageDTO = getPageInfo(request, map);
				int total = productToolService.getShopsProductTotal(map);
				List<ProductListAppDTO> productList = productToolService.getShopProductList(map);
				if(productList!=null && productList.size()>0){
					for(ProductListAppDTO productDTO : productList){
						productDTO.setFormattedPrice(MoneyUtil.formatMoney(productDTO.getPrice()));
						Long customerId=null;
						if(!com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(userId)){
							customerId = Long.valueOf(userId);
							int platform = productBaseinfoToolService.checkProductActivity(productDTO.getProductId(),
									customerId, null, productDTO.getMarketId()); 
							productDTO.setPlatform(platform);
						}
					}
				}
				
				//根据总数设置pageDTO信息
				pageDTO.setRecordCount(total);
				pageDTO.initiatePage(total);
				pageDTO.setRecordList(productList);
				result.setObject(pageDTO);
			} catch (Exception e) {
				logger.warn("[ERROR]加载产品列表异常", e);
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
	}

