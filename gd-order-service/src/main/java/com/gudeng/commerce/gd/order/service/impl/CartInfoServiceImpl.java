package com.gudeng.commerce.gd.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.CartInfoDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.entity.CartInfoEntity;
import com.gudeng.commerce.gd.order.service.CartInfoService;
import com.gudeng.commerce.gd.order.util.Constant;
import com.gudeng.commerce.gd.order.util.Constant.Cart;
import com.gudeng.commerce.gd.order.util.DateUtil;
import com.gudeng.commerce.gd.order.util.MathUtil;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class CartInfoServiceImpl implements CartInfoService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CartInfoDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CartInfoEntity.getById", map, CartInfoDTO.class);
	}

	@Override
	public List<CartInfoDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CartInfoEntity.getList", map, CartInfoDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CartInfoEntity.deleteById", map);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("CartInfoEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(CartInfoDTO t) throws Exception {
		return baseDao.execute("CartInfoEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CartInfoEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CartInfoEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CartInfoDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CartInfoEntity.getListPage", map, CartInfoDTO.class);
	}

	@Override
	public CartInfoDTO getCartItemByParam(Long memberId, Long productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("productId", productId);
		return baseDao.queryForObject("CartInfoEntity.getCartItemByParam", map, CartInfoDTO.class);
	}

	@Override
	public int deleteCartItemByParam(Long memberId, Long productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("productId", productId);
		return baseDao.execute("CartInfoEntity.deleteCartItemByParam", map);
	}

	@Override
	@Transactional
	public PageQueryResultDTO<CartInfoDTO> getCartItemByParam(Map<String, Object> paramMap) throws Exception {
		List<CartInfoDTO> cartList = baseDao.queryForList("CartInfoEntity.getCartItems", paramMap, CartInfoDTO.class);
		Integer totalCount = baseDao.queryForObject("CartInfoEntity.getTotalSearch", paramMap, Integer.class);
		PageQueryResultDTO<CartInfoDTO> queryResult = new PageQueryResultDTO<CartInfoDTO>();
		queryResult.setDataList(cartList);
		queryResult.setTotalCount(totalCount == null ? 0 : totalCount);
		return queryResult;
	}

	@Override
	public int updateShoppingItem(Map<String, Object> paramMap) {
		return baseDao.execute("CartInfoEntity.updateShoppingItem", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public int addCartInfoList(Map<String, Object> paramMap) throws Exception {
		int addResult = 1;
		List<CartInfoEntity> entityList = (List<CartInfoEntity>) paramMap.get("cartInfoList");
		for (CartInfoEntity entity : entityList) {
			// 判断购物车是否存在该商品，如果存在在原有基础上添加数量
			CartInfoDTO cartInfo = this.getCartItemByParam(entity.getMemberId(), entity.getProductId());

			if (cartInfo == null) {
				entity.setCreateTime(new Date());
				entity.setCreateUserId(entity.getMemberId() + "");
				this.insert(entity);
			} else {
				if (StringUtils.equals(cartInfo.getState(), Cart.STATE_DELETE)) {
					cartInfo.setPurQuantity(entity.getPurQuantity());
					cartInfo.setTradingPrice(entity.getTradingPrice());

				} else {
					cartInfo.setPurQuantity(cartInfo.getPurQuantity() + entity.getPurQuantity());
					cartInfo.setTradingPrice(
							MathUtil.round(MathUtil.mul(entity.getPrice(), cartInfo.getPurQuantity()), 2));
				}
				cartInfo.setPrice(entity.getPrice());
				String timeStr = DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME);
				cartInfo.setUpdateTimeStr(timeStr);
				cartInfo.setCreateTimeStr(timeStr);
				cartInfo.setUpdateUserId(entity.getMemberId() + "");
				cartInfo.setState(Cart.STATE_NORMAL);
				this.update(cartInfo);
			}
		}
		return addResult;
	}

	@Override
	public int updateCartItemByProductId(Map<String, Object> paramMap) throws Exception {
		return baseDao.execute("CartInfoEntity.updateByProductId", paramMap);
	}

	@Override
	public int updateCartItemByItemId(Map<String, Object> paramMap) throws Exception {
		return baseDao.execute("CartInfoEntity.updateByShoppingItemId", paramMap);
	}

	@Override
	@Transactional
	public int updateCartItemList(List<CartInfoDTO> dtoList) {
		int len = dtoList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			CartInfoDTO dto = dtoList.get(i);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", dto.getId());
			params.put("purQuantity", dto.getPurQuantity());
			params.put("selected", dto.getSelected());
			params.put("updateUserId", dto.getUpdateUserId());
			params.put("updateTimeStr", dto.getUpdateTimeStr());
			if (dto.getTradingPrice() != null) {
				params.put("tradingPrice", dto.getTradingPrice());
			}
			batchValues[i] = params;
		}
		baseDao.batchUpdate("CartInfoEntity.updateByShoppingItemId", batchValues);
		return len;
	}

	@Transactional
	@Override
	public PageQueryResultDTO<CartInfoDTO> getUserBusiness(Map<String, Object> paramMap) throws Exception {
		List<CartInfoDTO> businessList= baseDao.queryForList("CartInfoEntity.getUserBusiness", paramMap, CartInfoDTO.class);
		Integer totalCount = baseDao.queryForObject("CartInfoEntity.getUserBusinessTotal", paramMap, Integer.class);
		PageQueryResultDTO<CartInfoDTO> queryResult = new PageQueryResultDTO<CartInfoDTO>();
		queryResult.setDataList(businessList);
		queryResult.setTotalCount(totalCount == null ? 0 : totalCount);
		return queryResult;
	}

	@Override
	public List<CartInfoDTO> getItemByBusiness(Map<String, Object> paramMap) throws Exception {
		return baseDao.queryForList("CartInfoEntity.getBusinessProduct", paramMap, CartInfoDTO.class);
	}
}