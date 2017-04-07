package com.gudeng.commerce.gd.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.entity.ProductDeliveryDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.ProductDeliveryDetailService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

@Service
public class ProductDeliveryDetailServiceImpl implements
		ProductDeliveryDetailService {

	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private OrderProductDetailService orderProductDetailService;
	
	@Override
	public Long add(ProductDeliveryDetailEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<ProductDeliveryDetailDTO> getListByCondition(
			Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ProductDeliveryDetail.getListByCondition", map, ProductDeliveryDetailDTO.class);
	}

	@Override
	public int update(ProductDeliveryDetailDTO dto) throws Exception {
		return baseDao.execute("ProductDeliveryDetail.update", dto);
	}

	@Override
	public int batchAdd(List<ProductDeliveryDetailEntity> entityList) throws Exception {
		int len = entityList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			ProductDeliveryDetailEntity entity = entityList.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		return baseDao.batchUpdate("ProductDeliveryDetail.batchInsertEntity", batchValues).length;
	}

	@Transactional
	@Override
	public int batchAddAndUpdate(List<ProductDeliveryDetailEntity> entityList,
			List<OrderProductDetailDTO> productOrderList) throws Exception {
		int num1 = batchAdd(entityList);
		if(productOrderList != null){
			int num2 = orderProductDetailService.batchUpdate(productOrderList);
		}
		
		return num1;
	}

	@Override
	public List<ProductDeliveryDetailDTO> getProductByMemberAddressId(
			Long memberAddressId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberAddressId", memberAddressId);
		return baseDao.queryForList("ProductDeliveryDetail.getProductByMemberAddressId", paramMap, ProductDeliveryDetailDTO.class);
	}

	@Override
	public List<OrderDeliveryDetailDTO> getOrderByMemberAddressId(
			Long memberAddressId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberAddressId", memberAddressId);
		List<ProductDeliveryDetailDTO> productList = baseDao.queryForList("ProductDeliveryDetail.getProductByMemberAddressId", paramMap, ProductDeliveryDetailDTO.class);
		
		return processResult(productList);
	}

	@Override
	public Integer getTypeByMemberAddressId(Long memberAddressId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberAddressId", memberAddressId);
		return baseDao.queryForObject("ProductDeliveryDetail.getTypeByMemberAddressId", paramMap, Integer.class);
	}

	@Override
	public int deleteByCondition(Map<String, Object> map) throws Exception {
		return baseDao.execute("ProductDeliveryDetail.deleteByCondition", map);
	}

	@Override
	public List<OrderDeliveryDetailDTO> getOrderByMember(Map<String, Object> params) {		
		
		List<ProductDeliveryDetailDTO> productList =  baseDao.queryForList("ProductDeliveryDetail.getProductByMember", params, ProductDeliveryDetailDTO.class);
		
		return processResult(productList);
	}	

	@Override
	public List<ProductDeliveryDetailDTO> getProductByMember(Map<String, Object> params) {
		
		return baseDao.queryForList("ProductDeliveryDetail.getProductByMember", params, ProductDeliveryDetailDTO.class);
	}
	
	/**
	 * 处理后期结果
	 * @param productList
	 * @return
	 */
	private List<OrderDeliveryDetailDTO> processResult(List<ProductDeliveryDetailDTO> productList) {
		//订单Map
		Map<Long, OrderDeliveryDetailDTO> orderMap = new HashMap<Long, OrderDeliveryDetailDTO>();
		//商品列表Map
		Map<Long, List<ProductDeliveryDetailDTO>> productMap = new HashMap<Long, List<ProductDeliveryDetailDTO>>();
		for(ProductDeliveryDetailDTO dto : productList){
			Long orderNo = dto.getOrderNo();
			
			OrderDeliveryDetailDTO orderDTO = orderMap.get(orderNo);
			if(orderDTO == null){
				orderDTO = new OrderDeliveryDetailDTO();
				orderDTO.setOrderNo(dto.getOrderNo());
				orderDTO.setShopName(dto.getShopName());
				orderMap.put(orderNo, orderDTO);
			}
			
			List<ProductDeliveryDetailDTO> products = productMap.get(orderNo);
			if(products == null){
				products = new ArrayList<ProductDeliveryDetailDTO>();
				productMap.put(orderNo, products);
			}
			products.add(dto);
		}
		
		//将商品列表关联到对应的订单
		List<OrderDeliveryDetailDTO> orderList = new ArrayList<OrderDeliveryDetailDTO>();
		Set<Long> keySet = orderMap.keySet();
		for(Long key : keySet){
			OrderDeliveryDetailDTO orderDTO = orderMap.get(key);
			List<ProductDeliveryDetailDTO> products = productMap.get(key);
			orderDTO.setProductList(products);
			
			orderList.add(orderDTO);
		}

		return orderList;
	}

	@Override
	public Integer getSwitchStatusByType(int type) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		return baseDao.queryForObject("ProductDeliveryDetail.getSwitchStatusByType", paramMap, Integer.class);
	}
}
