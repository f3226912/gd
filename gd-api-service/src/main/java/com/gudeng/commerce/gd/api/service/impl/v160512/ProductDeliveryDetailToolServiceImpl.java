package com.gudeng.commerce.gd.api.service.impl.v160512;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.ProductDeliverListAppDTO;
import com.gudeng.commerce.gd.api.dto.SellerProductDeliverAppDTO;
import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.ProductDeliverStatusEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.entity.ProductDeliveryDetailEntity;
import com.gudeng.commerce.gd.order.service.ProductDeliveryDetailService;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public class ProductDeliveryDetailToolServiceImpl implements
		ProductDeliveryDetailToolService {

	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private OrderTool2Service orderTool2Service;
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private MemberToolService memberToolService;
	@Autowired
	private ProductToolService productToolService;
	
	private ProductDeliveryDetailService productDeliveryDetailService;
	
	private ProductDeliveryDetailService getProductDeliveryDetailService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.productDeliveryDetailService.url");
		if (productDeliveryDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productDeliveryDetailService = (ProductDeliveryDetailService) factory.create(ProductDeliveryDetailService.class, hessianUrl);
		}
		return productDeliveryDetailService;
	}

	@Override
	public List<?> getListByCondition(
			Map<String, Object> map) throws Exception {
		List<?> appDataList = null;
		List<ProductDeliveryDetailDTO> dataList = getProductDeliveryDetailService().getListByCondition(map);
		if(dataList != null && dataList.size() > 0){
			appDataList = new ArrayList<>();
			//货物来源 1订单 2商品
			Integer productDeliverType = dataList.get(0).getType();
			switch(productDeliverType){
			case 1:
				//设置订单产品出货信息
				setOrderProductDeliverList(appDataList, dataList);
				break;
			default:
				//设置卖家产品出货信息
				setSellerProductDeliverList(appDataList, dataList);
				break;
			}
		}
		return appDataList;
	}

	private List setSellerProductDeliverList(List appDataList,
			List<ProductDeliveryDetailDTO> dataList) {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		for(ProductDeliveryDetailDTO dto : dataList){
			SellerProductDeliverAppDTO appDTO = new SellerProductDeliverAppDTO();
			appDTO.setPicture(imageHost + dto.getImageUrl());
			appDTO.setPrice(dto.getPrice());
			appDTO.setProductId(dto.getProductId());
			appDTO.setProductName(dto.getProductName());
			appDTO.setShowedPrice(MoneyUtil.format(dto.getPrice()));
			appDTO.setStockCount(dto.getPurQuantity());
			appDTO.setUnit(dto.getUnit());
			appDTO.setUnitName(dto.getUnitName());
			appDTO.setOrderNo(dto.getOrderNo());
			appDataList.add(appDTO);
		}
		return appDataList;
	}

	private List setOrderProductDeliverList(List appDataList, List<ProductDeliveryDetailDTO> dataList) {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		Map<Long, List<OrderProductDTO>> orderNoMap = new HashMap<>();
		for(ProductDeliveryDetailDTO dto : dataList){
			Long deliverOrderNo = dto.getOrderNo();
			boolean notHasOrderNo = true;
			for (Long orderNo : orderNoMap.keySet()) {  
				if(deliverOrderNo.equals(orderNo)){
					notHasOrderNo = false;
					break;
				}
			}
			if(notHasOrderNo){
				//放入订单信息
				ProductDeliverListAppDTO appDTO = new ProductDeliverListAppDTO();
				appDTO.setBusinessId(dto.getBusinessId());
				appDTO.setBuyerName(StringUtils.isBlank(dto.getBuyerName()) ? "农商友用户": dto.getBuyerName());
				appDTO.setCreateTime(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
				if(dto.getBuyerId() != null){
					appDTO.setMemberId(dto.getBuyerId().intValue());
				}
				appDTO.setOrderNo(deliverOrderNo);
				appDTO.setShopName(dto.getShopName());
				appDTO.setStatus(ProductDeliverStatusEnum.IS_DELIVERING.getkey());
				appDataList.add(appDTO);
				//放入产品信息
				List<OrderProductDTO> productList = new ArrayList<OrderProductDTO>();
				OrderProductDTO productDTO = new OrderProductDTO();
				productDTO.setOrderNo(deliverOrderNo);
				productDTO.setFormattedPrice(MoneyUtil.formatMoney(dto.getPrice()));
				productDTO.setImageUrl(imageHost + dto.getImageUrl());
				productDTO.setNeedToPayAmount(dto.getSubTotal());
				productDTO.setPrice(dto.getPrice());
				productDTO.setProductId(dto.getProductId().intValue());
				productDTO.setProductName(dto.getProductName());
				productDTO.setPurQuantity(dto.getPurQuantity());
				productDTO.setTradingPrice(dto.getSubTotal());
				productDTO.setUnitName(dto.getUnitName());
				productDTO.setStatus(ProductDeliverStatusEnum.IS_DELIVERING.getkey());
				productList.add(productDTO);
				orderNoMap.put(deliverOrderNo, productList);
			}else{
				OrderProductDTO productDTO = new OrderProductDTO();
				productDTO.setOrderNo(deliverOrderNo);
				productDTO.setFormattedPrice(MoneyUtil.formatMoney(dto.getPrice()));
				productDTO.setImageUrl(imageHost + dto.getImageUrl());
				productDTO.setNeedToPayAmount(dto.getSubTotal());
				productDTO.setPrice(dto.getPrice());
				productDTO.setProductId(dto.getProductId().intValue());
				productDTO.setProductName(dto.getProductName());
				productDTO.setPurQuantity(dto.getPurQuantity());
				productDTO.setTradingPrice(dto.getSubTotal());
				productDTO.setUnitName(dto.getUnitName());
				productDTO.setStatus(ProductDeliverStatusEnum.IS_DELIVERING.getkey());
				orderNoMap.get(deliverOrderNo).add(productDTO);
			}
		}
		
		for (Long orderNo : orderNoMap.keySet()) {
			for(int i=0,len=appDataList.size();i<len;i++){
				ProductDeliverListAppDTO pDto = (ProductDeliverListAppDTO) appDataList.get(i);
				if(orderNo.equals(pDto.getOrderNo())){
					pDto.setProductDetails(orderNoMap.get(orderNo));
				}
			}
		}
		
		return appDataList;
	}

	@Override
	public ErrorCodeEnum add(MemberAddressInputDTO inputDTO, Long memberAddressId, boolean isUpdate)
			throws Exception {
		String[] productArr = inputDTO.getSelectedList().split(",");
		//productDetail格式: O1_P1-P2(订单货源)或#B1_P1-P2 (商品货源)
		String productDetail = productArr[0];
		System.out.println("货源货物格式: " + productDetail);
		if(productDetail.indexOf("_")<0){
			return ErrorCodeEnum.DELIVER_GOODS_FORMAT_ERROR;
		}
		
		int productCount = 0;
		int productCount2 = 0;
		List<OrderProductDetailDTO> productOrderList = null;
		List<ProductDeliveryDetailEntity> entityList = new ArrayList<>();
		if(productDetail.startsWith("#")){//商品货源
			String businessId = productDetail.split("_")[0].replace("#", "");
			BusinessBaseinfoDTO businessInfo = businessBaseinfoToolService.getById(businessId);
			MemberBaseinfoDTO memberInfo = memberToolService.getById(inputDTO.getMemberId()+"");
			String[] productIdArr = productDetail.split("_")[1].split("-");
			for(int j=0,len2=productIdArr.length;j<len2;j++){
				Long productId = Long.parseLong(productIdArr[j]);
				ProductDeliveryDetailEntity entity = new ProductDeliveryDetailEntity();
				// 当 为 '1' 时表示同城
				if (StringUtils.isNotEmpty(inputDTO.getSourceType()) && "1".equals(inputDTO.getSourceType())) {
					entity.setSame_memberAddressId(memberAddressId);
				} else {
					entity.setMemberAddressId(memberAddressId);
				}
				entity.setType(2);
				entity.setSellerId(businessInfo.getUserId());
				entity.setBusinessId(businessInfo.getBusinessId());
				entity.setShopName(businessInfo.getShopsName());
				entity.setBuyerId(inputDTO.getMemberId());
				entity.setBuyerName(memberInfo.getRealName());
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				entity.setProductId(productId);
				entityList.add(entity);
				productCount++;
			}
			
			//设置商品信息
			Map<String, Object> map = new HashMap<>();
			map.put("memberId", inputDTO.getMemberId());
			map.put("businessId", businessId);
			map.put("startRow", 0);
			map.put("endRow", 9999);
			List<ProductDto> productList = productToolService.getShopProducts(map);
			if(productList != null && productList.size() > 0){
				Double zero = 0.00;
				for(int i=0,len1=productList.size();i<len1;i++){
					ProductDto productDTO = productList.get(i);
					for(int j=0,len2=entityList.size();j<len2;j++){
						ProductDeliveryDetailEntity entity2 = entityList.get(j);
						if(entity2.getProductId().equals(productDTO.getProductId())){
							entity2.setProductName(productDTO.getProductName());
							entity2.setPrice(productDTO.getPrice());
							entity2.setUnit(productDTO.getUnit());
							entity2.setPurQuantity(productDTO.getStockCount());
							entity2.setSubTotal(zero);
							entity2.setImageUrl(productDTO.getUrlOrg());
							entity2.setUnit(productDTO.getUnit());
							productCount2++;
						}
					}
				}
			}
		}else{   //订单货源
			List<Long> orderNoList = new ArrayList<>();
			for(int i=0,len=productArr.length;i<len;i++){
				String[] productDetailArr = productArr[i].split("_");
				Long orderNo = Long.parseLong(productDetailArr[0]);  //订单号
				OrderBaseinfoDTO orderDTO = orderTool2Service.getOrderByOrderNo(orderNo);
				String[] productIdArr = productDetailArr[1].split("-");
				for(int j=0,len2=productIdArr.length;j<len2;j++){
					Long productId = Long.parseLong(productIdArr[j]);
					ProductDeliveryDetailEntity entity = new ProductDeliveryDetailEntity();
					// 当 为 '1' 时表示同城
					if (StringUtils.isNotEmpty(inputDTO.getSourceType()) && "1".equals(inputDTO.getSourceType())) {
						entity.setSame_memberAddressId(memberAddressId);
					} else {
						entity.setMemberAddressId(memberAddressId);
					}
					entity.setType(1);
					entity.setOrderNo(orderNo);
					entity.setSellerId(orderDTO.getSellMemberId().longValue());
					entity.setBusinessId(orderDTO.getBusinessId().longValue());
					entity.setShopName(orderDTO.getShopName());
					if(orderDTO.getMemberId() != null){
						entity.setBuyerId(orderDTO.getMemberId().longValue());
					}
					entity.setBuyerName(orderDTO.getRealName());
					entity.setCreateTime(new Date());
					entity.setUpdateTime(new Date());
					entity.setProductId(productId);
					entityList.add(entity);
				}
				orderNoList.add(orderNo);
			}
			
			productOrderList = orderTool2Service.getListByOrderNoList(orderNoList);
			if(productOrderList != null && productOrderList.size() > 0){
				for(int i=0,len1=productOrderList.size();i<len1;i++){
					OrderProductDetailDTO productDTO = productOrderList.get(i);
					for(int j=0,len2=entityList.size();j<len2;j++){
						ProductDeliveryDetailEntity entity = entityList.get(j);
						if(entity.getOrderNo().equals(productDTO.getOrderNo())
								&& entity.getProductId().intValue() == productDTO.getProductId()){
							if(!isUpdate && productDTO.getHasDelivered() == ProductDeliverStatusEnum.IS_DELIVERING.getkey()){
								return ErrorCodeEnum.GOODS_ALREADY_DELIVERED;
							}
							entity.setProductName(productDTO.getProductName());
							entity.setPrice(productDTO.getPrice());
							entity.setPurQuantity(productDTO.getPurQuantity());
							entity.setSubTotal(productDTO.getNeedToPayAmount());
							entity.setImageUrl(productDTO.getImageUrl());
							entity.setUnit(productDTO.getUnit());
							productDTO.setHasDelivered(ProductDeliverStatusEnum.IS_DELIVERING.getkey());
						}
					}
				}
			}
			
			//更新已选择的发货状态
//			if(isUpdate){
//				updateExsitedDeliveredStatus(productOrderList, entityList, memberAddressId);
//			}
		}
		
		if(productCount2 != productCount){
			return ErrorCodeEnum.DELIVER_GOODS_NUM_ERROR;
		}
		
		if(isUpdate){
			deleteByMemberAddressId(memberAddressId);
		}
		getProductDeliveryDetailService().batchAddAndUpdate(entityList, productOrderList);
		return ErrorCodeEnum.SUCCESS;
	}

	private void updateExsitedDeliveredStatus(
			List<OrderProductDetailDTO> productOrderList,
			List<ProductDeliveryDetailEntity> entityList, Long memberAddressId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("memberAddressId", memberAddressId);
		List<ProductDeliveryDetailDTO> dataList = getProductDeliveryDetailService().getListByCondition(map);
		if(dataList == null || dataList.size() == 0){
			return;
		}
		//遍历订单的所有商品
		for(int i=0,len1=productOrderList.size();i<len1;i++){
			OrderProductDetailDTO productDTO = productOrderList.get(i);
			//设置以前选中的为 未出货
			for(int j=0,len2=dataList.size();j<len2;j++){
				ProductDeliveryDetailDTO productDeliver = dataList.get(j);
				if(productDeliver.getOrderNo().equals(productDTO.getOrderNo())
						&& productDeliver.getProductId().intValue() == productDTO.getProductId()){
					productDTO.setHasDelivered(ProductDeliverStatusEnum.NOT_DELIVER.getkey());
				}
			}
			
			//设置新选中的为 正在出货
			for(int j=0,len2=entityList.size();j<len2;j++){
				ProductDeliveryDetailEntity productDeliver = entityList.get(j);
				if(productDeliver.getOrderNo().equals(productDTO.getOrderNo())
						&& productDeliver.getProductId().intValue() == productDTO.getProductId()){
					productDTO.setHasDelivered(ProductDeliverStatusEnum.IS_DELIVERING.getkey());
				}
			}
		}
	}

	@Override
	public int deleteByMemberAddressId(Long memberAddressId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("memberAddressId", memberAddressId);
		return getProductDeliveryDetailService().deleteByCondition(map);
	}

	@Override
	public Integer isOpenForAddDelivery() throws Exception {
		return getProductDeliveryDetailService().getSwitchStatusByType(1);
	}

	@Override
	public List<ProductDeliveryDetailDTO> getByMap(Map<String, Object> map) throws Exception {
		return getProductDeliveryDetailService().getListByCondition(map);
	}
}
