package com.gudeng.commerce.gd.api.service.impl.order;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant.Cart;
import com.gudeng.commerce.gd.api.dto.input.ShoppingCartInputDTO;
import com.gudeng.commerce.gd.api.dto.output.BusinessCartItemDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.ShoppingCartItem;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.v161128.CartInfoToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.CartInfoDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.entity.CartInfoEntity;
import com.gudeng.commerce.gd.order.service.CartInfoService;

public class CartInfoToolServiceImpl implements CartInfoToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CartInfoToolServiceImpl.class);
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CartInfoService cartInfoService;
	@Autowired
	private ProductBaseinfoToolService productBaseinfoToolService;

	protected CartInfoService getHessianCartInfoService() throws MalformedURLException {
		String url = gdProperties.getCartInfoServiceUrl();
		if (cartInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			cartInfoService = (CartInfoService) factory.create(CartInfoService.class, url);
		}
		return cartInfoService;
	}

	@Override
	public CartInfoDTO getById(String id) throws Exception {
		return getHessianCartInfoService().getById(id);
	}

	@Override
	public List<CartInfoDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCartInfoService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCartInfoService().deleteById(id);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCartInfoService().deleteBatch(list);
	}

	@Override
	public int update(CartInfoDTO t) throws Exception {
		return getHessianCartInfoService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCartInfoService().getTotal(map);
	}

	@Override
	public Long insert(CartInfoEntity entity) throws Exception {
		return getHessianCartInfoService().insert(entity);
	}

	@Override
	public List<CartInfoDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCartInfoService().getListPage(map);
	}

	@Override
	public ErrorCodeEnum addProduct(ShoppingCartInputDTO cartInput) throws Exception {
		Long memberId = cartInput.getMemberId();
		if (memberId == null) {
			return ErrorCodeEnum.MEMBER_ID_IS_NULL;
		}

		String productDetails = cartInput.getProductDetails();
		if (StringUtils.isBlank(productDetails)) {
			return ErrorCodeEnum.PRODUCT_ID_IS_NULL;
		}

		JSONArray jsonArr = JSONUtils.parseArray(productDetails);
		List<Long> productIdList = new LinkedList<Long>();
		List<CartInfoDTO> dtoList = new LinkedList<CartInfoDTO>();
		for (int i = 0, len = jsonArr.size(); i < len; i++) {
			JSONObject jsonObject = (JSONObject) jsonArr.get(i);
			Long productId = jsonObject.getLong("productId");
			Double quantity = jsonObject.getDouble("quantity");

			if (productId == null) {
				return ErrorCodeEnum.PRODUCT_ID_IS_NULL;
			}
			if (quantity == null || quantity.intValue() <= 0) {
				return ErrorCodeEnum.CART_QUANTITY_IS_NULL;
			}
			productIdList.add(productId);
			CartInfoDTO dto = new CartInfoDTO();
			dto.setProductId(productId);
			dto.setPurQuantity(quantity);
			dtoList.add(dto);
		}

		List<ProductBaseinfoDTO> productList = productBaseinfoToolService.getProductList(productIdList);
		List<CartInfoEntity> cartInfoList = new LinkedList<CartInfoEntity>();
		for (CartInfoDTO cartDto : dtoList) {
			ProductBaseinfoDTO product = null;
			for (ProductBaseinfoDTO productDto : productList) {
				if (productDto.getProductId().equals(cartDto.getProductId())) {
					product = productDto;
				}
			}
			if (product == null) {
				logger.warn("addProduct productId:" + cartDto.getProductId() + " not exist");
				return ErrorCodeEnum.CART_PRODUCT_NOT_EXIST;
			}

			Double price = product.getPrice();
			if (price == null || price.compareTo(new Double(0)) <= 0) {
				return ErrorCodeEnum.CART_PRICE_IS_NULL;
			}
			Double tradingPrice = MathUtil.round(MathUtil.mul(price, cartDto.getPurQuantity()), 2);

			if (!"3".equals(product.getState())) {
				return ErrorCodeEnum.CART_PRODUCT_NOT_ONSALE;
			}

			if (product.getStockCount().compareTo(new Double(0)) <= 0) {
				return ErrorCodeEnum.CART_NO_STOCK_COUNT;
			}

			CartInfoEntity entity = new CartInfoEntity();
			entity.setBusinessId(product.getBusinessId());
			entity.setMemberId(memberId);
			entity.setShopsName(product.getBusinessName());
			entity.setProductName(product.getName());
			entity.setProductId(product.getProductId());
			entity.setPrice(product.getPrice());
			entity.setPurQuantity(cartDto.getPurQuantity());
			entity.setTradingPrice(tradingPrice);
			entity.setSelected(Cart.SELECTED);
			entity.setState(Cart.STATE_NORMAL);
			cartInfoList.add(entity);
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cartInfoList", cartInfoList);
		if (getHessianCartInfoService().addCartInfoList(paramMap) > 0) {
			return ErrorCodeEnum.CART_ADD_PRODUCT_SUCCESS;
		} else {
			return ErrorCodeEnum.CART_ADD_PRODUCT_FAIL;
		}

	}

	@Override
	public ErrorCodeEnum deleteProduct(ShoppingCartInputDTO cartInput) throws Exception {
		Long memberId = cartInput.getMemberId();
		if (memberId == null) {
			return ErrorCodeEnum.MEMBER_ID_IS_NULL;
		}
		String shoppingItemIds = cartInput.getShoppingItemIds();
		if (shoppingItemIds == null) {
			return ErrorCodeEnum.CART_SHOPPINGITEMID_IS_NULL;
		}
		String[] idsArr = shoppingItemIds.split(",");
		if (idsArr.length == 0) {
			return ErrorCodeEnum.CART_SHOPPINGITEMID_IS_NULL;
		}

		List<String> idsList = Arrays.asList(idsArr);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (idsList.size() == 1) {
			paramMap.put("id", idsArr[0]);
		} else {
			paramMap.put("shoppingItemIds", idsList);
		}

		// 删除购物车商品状态置为2
		paramMap.put("updateUserId", memberId);
		paramMap.put("updateTimeStr", DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME));
		paramMap.put("state", Cart.STATE_DELETE);
		int deleleResult = getHessianCartInfoService().updateCartItemByItemId(paramMap);
		if (deleleResult > 0) {
			return ErrorCodeEnum.CART_DELETE_PRODUCT_SUCCESS;
		} else {
			return ErrorCodeEnum.CART_DELETE_PRODUCT_FAIL;
		}
	}

	@Override
	public PageQueryResultDTO<BusinessCartItemDetailDTO> getShoppingItems(Map<String, Object> param) throws Exception {
		PageQueryResultDTO<CartInfoDTO> pageResult = getHessianCartInfoService().getCartItemByParam(param);
		List<CartInfoDTO> cartList = pageResult.getDataList();
		List<BusinessCartItemDetailDTO> detailList = null;
		if (pageResult.getTotalCount() > 0) {
			detailList = setOutputDetail(cartList);
		}
		PageQueryResultDTO<BusinessCartItemDetailDTO> returnPageResult = new PageQueryResultDTO<BusinessCartItemDetailDTO>();
		returnPageResult.setDataList(detailList);
		returnPageResult.setTotalCount(pageResult.getTotalCount());
		return returnPageResult;
	}

	private List<BusinessCartItemDetailDTO> setOutputDetail(List<CartInfoDTO> cartList) {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		List<Long> businessIds = new ArrayList<Long>();
		for (CartInfoDTO dto : cartList) {
			if (!businessIds.contains(dto.getBusinessId())) {
				businessIds.add(dto.getBusinessId());
			}
		}

		List<BusinessCartItemDetailDTO> detailList = new ArrayList<BusinessCartItemDetailDTO>(businessIds.size());
		for (Long businessId : businessIds) {
			detailList.add(new BusinessCartItemDetailDTO(businessId));
		}
		for (CartInfoDTO dto : cartList) {
			for (BusinessCartItemDetailDTO detail : detailList) {
				if (dto.getBusinessId().equals(detail.getBusinessId())) {
					detail.setBusinessName(dto.getShopsName());
					List<ShoppingCartItem> cartItems = detail.getShoppingItems();
					if (cartItems == null) {
						cartItems = new LinkedList<ShoppingCartItem>();
						detail.setShoppingItems(cartItems);
					}
					ShoppingCartItem cartItem = new ShoppingCartItem();
					cartItem.setAmount(dto.getTradingPrice());
					cartItem.setPrice(dto.getPrice());
					cartItem.setNewPrice(dto.getNewPrice());
					cartItem.setProductId(dto.getProductId());
					cartItem.setProductName(dto.getProductName());
					cartItem.setQuantity(dto.getPurQuantity());
					cartItem.setUnitName(dto.getUnitName());
					cartItem.setSelected(dto.getSelected());
					cartItem.setStatus(dto.getNewState());
					cartItem.setStockCount(dto.getStockCount());
					cartItem.setImgUrl(imageHost+dto.getUrl170());
					cartItem.setShoppingItemId(dto.getId());
					cartItems.add(cartItem);
				}
			}
		}
		return detailList;
	}

	@Override
	public ErrorCodeEnum modifyProduct(ShoppingCartInputDTO cartInput) throws Exception {
		Long memberId = cartInput.getMemberId();
		if (memberId == null) {
			return ErrorCodeEnum.MEMBER_ID_IS_NULL;
		}

		String itemDetails = cartInput.getShoppingItemDetails();
		if (StringUtils.isBlank(itemDetails)) {
			return ErrorCodeEnum.PRODUCT_ID_IS_NULL;
		}

		JSONArray jsonArr = JSONUtils.parseArray(itemDetails);
		if (jsonArr.size() > 0) {
			List<CartInfoDTO> dtoList = new LinkedList<CartInfoDTO>();
			for (int i = 0, len = jsonArr.size(); i < len; i++) {
				JSONObject jsonObject = (JSONObject) jsonArr.get(i);
				Long itemId = jsonObject.getLong("shoppingItemId");
				if (itemId == null) {
					return ErrorCodeEnum.CART_SHOPPINGITEMID_IS_NULL;
				}

				Double quantity = jsonObject.getDouble("quantity");
				if (quantity != null && quantity.intValue() <= 0) {
					return ErrorCodeEnum.CART_QUANTITY_IS_NULL;
				}
				String selected = jsonObject.getString("selected");
				
				CartInfoDTO dto = new CartInfoDTO();
				if (quantity != null) {
					if (quantity.intValue() < 0) {
						return ErrorCodeEnum.CART_QUANTITY_IS_NULL;
					} else {
						CartInfoDTO existItem = getHessianCartInfoService().getById(itemId + "");
						dto.setTradingPrice(MathUtil.round(MathUtil.mul(existItem.getPrice(),quantity), 2));
					}
				}

				dto.setId(itemId);
				dto.setPurQuantity(quantity);
				dto.setSelected(selected);
				dto.setUpdateUserId(memberId + "");
				dto.setUpdateTimeStr(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME));
				dtoList.add(dto);
			}

			if (getHessianCartInfoService().updateCartItemList(dtoList) > 0) {
				return ErrorCodeEnum.CART_MODIFY_PRODUCT_SUCCESS;
			} else {
				return ErrorCodeEnum.CART_MODIFY_PRODUCT_FAIL;
			}
		} else {
			return ErrorCodeEnum.CART_MODIFY_PRODUCT_FAIL;
		}
	}

	@Override
	public PageQueryResultDTO<BusinessCartItemDetailDTO> getItemsByBusiness(Map<String, Object> param)
			throws Exception {
		PageQueryResultDTO<CartInfoDTO> pageResult = getHessianCartInfoService().getUserBusiness(param);
		List<CartInfoDTO> cartList = pageResult.getDataList();
		List<BusinessCartItemDetailDTO> detailList = null;

		if (pageResult.getTotalCount() > 0) {
			detailList=new ArrayList<BusinessCartItemDetailDTO>(cartList.size()) ;
			setOutDetailByBusiness(cartList,detailList);
		}
		PageQueryResultDTO<BusinessCartItemDetailDTO> returnPageResult = new PageQueryResultDTO<BusinessCartItemDetailDTO>();
		returnPageResult.setDataList(detailList);
		returnPageResult.setTotalCount(pageResult.getTotalCount());
		return returnPageResult;
	}
	
	/**
	 * 通过商铺分页，通过商铺ID查询商品，并返回商品总数
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public void setOutDetailByBusiness(List<CartInfoDTO> cartList,List<BusinessCartItemDetailDTO> detailList) throws MalformedURLException, Exception{
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		/**
		 * 商品总数
		 */
		//int productQuantity=0;
		for(CartInfoDTO dto :cartList){
			BusinessCartItemDetailDTO detail=new BusinessCartItemDetailDTO(dto.getBusinessId(),dto.getShopsName());
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("businessId", dto.getBusinessId());
			map.put("memberId", dto.getMemberId());
			map.put("state","2");
			/**
			 * 查询每个商铺下的商品
			 */
			List<CartInfoDTO>  cartInfoList=getHessianCartInfoService().getItemByBusiness(map);
			if(CollectionUtils.isNotEmpty(cartInfoList)){
				//productQuantity+=cartInfoList.size();
				List<ShoppingCartItem> cartItems = new ArrayList<ShoppingCartItem>(cartInfoList.size());
				for(CartInfoDTO item :cartInfoList){
					ShoppingCartItem cartItem = new ShoppingCartItem();
					
					//cartItem.setAmount(item.getTradingPrice());
					cartItem.setAmount(MathUtil.round(MathUtil.mul(item.getNewPrice(), item.getPurQuantity()), 2));
					cartItem.setPrice(item.getPrice());
					cartItem.setNewPrice(item.getNewPrice());
					cartItem.setProductId(item.getProductId());
					cartItem.setProductName(item.getProductName());
					cartItem.setQuantity(item.getPurQuantity());
					cartItem.setUnitName(item.getUnitName());
					cartItem.setSelected(item.getSelected());
					cartItem.setStatus(item.getNewState());
					cartItem.setStockCount(item.getStockCount());
					cartItem.setImgUrl(imageHost+item.getUrl170());
					cartItem.setShoppingItemId(item.getId());
					cartItems.add(cartItem);
				}
				detail.setShoppingItems(cartItems);
				
			}
			detailList.add(detail);
		}
		//return productQuantity;
	}
}