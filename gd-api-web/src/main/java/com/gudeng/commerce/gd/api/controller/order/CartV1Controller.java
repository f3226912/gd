package com.gudeng.commerce.gd.api.controller.order;


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

import com.gudeng.commerce.gd.api.Constant.Cart;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.input.ShoppingCartInputDTO;
import com.gudeng.commerce.gd.api.dto.output.BusinessCartItemDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.ShoppingCartItem;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.v161128.CartInfoToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;


@Controller
@RequestMapping("/v1/cart")
public class CartV1Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CartV1Controller.class);
	@Autowired
	private CartInfoToolService cartInfoToolService;
	@Autowired
	private ProductBaseinfoToolService productBaseinfoToolService;
	

	/**
	 * 购物车添加商品
	 * @param request
	 * @param response
	 */
	@RequestMapping("add")
	public void addProduct(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			ShoppingCartInputDTO cartInput = (ShoppingCartInputDTO) getDecryptedObject(request, ShoppingCartInputDTO.class);
			ErrorCodeEnum addResult = cartInfoToolService.addProduct(cartInput);
			setEncodeResult(result, addResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]添加购物车商品失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 购物车删除商品
	 * @param request
	 * @param response
	 */
	@RequestMapping("delete")
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			ShoppingCartInputDTO cartInput = (ShoppingCartInputDTO) getDecryptedObject(request, ShoppingCartInputDTO.class);
			ErrorCodeEnum deleteResult = cartInfoToolService.deleteProduct(cartInput);
			setEncodeResult(result, deleteResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]删除购物车商品失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	
	/**
	 * 购物车修改商品及属性
	 * @param request
	 * @param response
	 */
	@RequestMapping("modify")
	public void modifyProduct(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			ShoppingCartInputDTO cartInput = (ShoppingCartInputDTO) getDecryptedObject(request, ShoppingCartInputDTO.class);
			ErrorCodeEnum modifyResult = cartInfoToolService.modifyProduct(cartInput);
			setEncodeResult(result, modifyResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]修改购物车商品失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 查询购物车商品
	 * @param request
	 * @param response
	 */
	@RequestMapping("query")
	public void getProducts(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			ShoppingCartInputDTO cartInput = (ShoppingCartInputDTO) getDecryptedObject(request, ShoppingCartInputDTO.class);
            Long memberId=cartInput.getMemberId();
            if(memberId==null){
            	setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
            }
        	Map<String, Object> param = new HashMap<String, Object>();
			param.put("memberId", memberId);
			param.put("state", Cart.STATE_DELETE);
			CommonPageDTO pageDTO = getPageInfoEncript(request, param);
			
			PageQueryResultDTO<BusinessCartItemDetailDTO> pageResult= cartInfoToolService.getItemsByBusiness(param);
			
			if( pageResult.getDataList()!=null &&  pageResult.getDataList().size()>0){
				for(BusinessCartItemDetailDTO dto : pageResult.getDataList()){
					for(ShoppingCartItem item : dto.getShoppingItems()){
						Integer platform=0;
						platform = productBaseinfoToolService.checkProductActivity(item.getProductId(), memberId, null, null);
						item.setPlatform(platform); 
					}
				}
			}
			
			pageDTO.setRecordCount(pageResult.getTotalCount());
			pageDTO.initiatePage(pageResult.getTotalCount());
			pageDTO.setRecordList(pageResult.getDataList());
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询购物车商品失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
