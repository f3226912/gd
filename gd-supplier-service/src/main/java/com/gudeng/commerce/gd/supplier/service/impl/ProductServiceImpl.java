package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.supplier.bo.CacheBo;
import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.ProductClassDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.dto.ProductSinxinDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.service.ProductService;
import com.gudeng.commerce.gd.supplier.util.NullUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private CacheBo cacheBo;

	@Override
	public ProductDto getById(String productId) throws Exception {

		/*
		 * Map<String, String> map = new HashMap<String, String>();
		 * map.put("productId", productId);
		 * 
		 * ProductDto productDto=(ProductDto)
		 * this.baseDao.queryForObject("product.getById", map,
		 * ProductDto.class); return productDto;
		 */
		return cacheBo.getProductById(productId, baseDao);
	}

	@Override
	public ProductDto getOneProduct(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("product.getOneProduct", params, ProductDto.class);
	}
	
	@Override
	public List<ProductDto> getProductsByIds(String[] productIds) throws Exception {
		List<ProductDto> list = new ArrayList<ProductDto>();
		for (String current : productIds) {
			list.add(getById(current));
		}
		return list;
	}

	@Override
	public ProductDto getByName(String productName) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productName", productName);
		return (ProductDto) this.baseDao.queryForObject("product.getByName", map, ProductDto.class);
	}

	@Override
	public List<ProductDto> getProductList(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getProductList", map, ProductDto.class);
		return list;
	}

	@Override
	public List<ProductDto> getProductListAll(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getProductListAll", map, ProductDto.class);
		return list;
	}

	@Override
	public int getCount(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("product.getCount", map, Integer.class);
	}

	@Override
	public int deleteProduct(String productId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		// 清除缓存
		cacheBo.deleteProductCacheById(Long.valueOf(productId));
		return baseDao.execute("product.deleteProduct", map);
	}

	@Override
	public Long persistProductViaEntity(ProductEntity productEntity) {
		productEntity.setUpdatePriceTime(productEntity.getCreateTime());
		Long productId = (Long) baseDao.persist(productEntity, Long.class);
		productEntity.setProductId(productId);
		return productId;
	}

	@Override
	public ProductDto persistProduct(ProductDto productDto) {
		long result = baseDao.execute("product.persistProduct", productDto);
		productDto.setProductId(result);
		return productDto;
	}

	@Override
	public int updateProduct(ProductDto productDto) {
		// 清除缓存
		cacheBo.deleteProductCacheById(productDto.getProductId());
		return baseDao.execute("product.updateProduct", productDto);
	}

	@Override
	public int updateProductStatus(Map map) throws Exception {
		String productId = String.valueOf(map.get("productId"));
		if (null != productId && !"".equals(productId)) {
			// 清除缓存
			cacheBo.deleteProductCacheById(Long.valueOf(productId));
		}
		return baseDao.execute("product.updateProductStatus", map);
	}

	@Override
	public int[] batchDeleteProduct(String[] productIds) {
		if (NullUtil.isNull(productIds)) {
			return new int[] {};
		}
		int len = productIds.length;
		Map<String, Object>[] batchValues = new Map[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", StringUtils.trim(productIds[i]));
			batchValues[i] = map;
			// 清除缓存
			cacheBo.deleteProductCacheById(Long.valueOf(productIds[i]));
		}
		return baseDao.batchUpdate("product.deleteProduct", batchValues);
	}

	@Override
	public List<ProductDto> getList(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getList", map, ProductDto.class);
		return list;
	}

	/*
	 * @Override public int auditProduct(Map<String, Object> map) throws
	 * Exception { return baseDao.execute("product.auditProduct", map); }
	 */

	@Override
	public List<ProductPriceDto> getLadderPriceByProductId(String productId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		List<ProductPriceDto> list = baseDao.queryForList("product.getLadderPriceByProductId", map,
				ProductPriceDto.class);
		return list;
	}

	@Override
	public List<ProductDto> getByCateId(Long cateId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("cateId", cateId);
		List<ProductDto> list = baseDao.queryForList("product.getByCateId", map, ProductDto.class);
		return list;
	}

	/**
	 * @Description batchUpdate 批量添加商品价格区间
	 * @param priceDtoList
	 * @CreationDate 2015年10月21日 下午2:00:38
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@Override
	public void batchUpdatePrices(List<ProductPriceDto> priceDtoList) {
		int len = priceDtoList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", priceDtoList.get(i).getProductId());
			map.put("buyCountStart", priceDtoList.get(i).getBuyCountStart());
			map.put("buyCountEnd", priceDtoList.get(i).getBuyCountEnd());
			map.put("price", priceDtoList.get(i).getPrice());
			map.put("createUserId", priceDtoList.get(i).getCreateUserId());
			batchValues[i] = map;
			// 清除缓存
			cacheBo.deleteProductCacheById(Long.valueOf(priceDtoList.get(i).getProductId()));
		}
		baseDao.batchUpdate("product.batchUpdatePrice", batchValues);
	}

	/**
	 * @Description batchDelete 删除商品价格区间信息
	 * @param productId
	 * @return
	 * @CreationDate 2015年10月21日 下午2:46:38
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public int batchDeletePrices(String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		int result = baseDao.execute("product.batchDeletePrice", map);
		// 清除缓存
		cacheBo.deleteProductCacheById(Long.valueOf(productId));
		return result;
	}

	/**
	 * @Description getProductPriceList 获取商品的价格区间信息
	 * @param productId
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年10月21日 下午3:14:15
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<ProductPriceDto> getProductPriceList(String productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		List<ProductPriceDto> list = baseDao.queryForList("product.getPrices", map, ProductPriceDto.class);
		return list;
	}

	@Override
	public List<ProductDto> getShopProductList(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getShopProductList", map, ProductDto.class);
		return list;
	}

	@Override
	public List<ProductDto> getShopProductListNew(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getShopProductListNew", map, ProductDto.class);
		return list;
	}

	@Override
	public List<ProductDto> getShopProductTotalList(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getShopProductTotalList", map, ProductDto.class);
		return list;
	}

	@Override
	public int getShopsProductTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("product.getShopsProductTotal", map, Integer.class);
	}

	@Override
	public int getShopsProductTotalNew(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("product.getShopProductListCountNew", map, Integer.class);
	}

	@Override
	public ProductDto getByIdWithoutPicType(String productId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		ProductDto productDto = (ProductDto) this.baseDao.queryForObject("product.getByIdWithoutPicType", map,
				ProductDto.class);
		return productDto;

	}

	@Override
	public List<ProductDto> getByStateAndBusinessId(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("product.getByStateAndBusinessId", map, ProductDto.class);
	}

	@Override
	public int countByStateAndBusinessId(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("product.countByStateAndBusinessId", map, Integer.class);
	}
	
	@Override
	public int validProductAliveByBusinessId(String productId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		return (int) baseDao.queryForObject("product.validProductAlive", map, Integer.class);
	}

	@Override
	public int updateProduct(Map map) throws Exception {
		String productId = String.valueOf(map.get("productId"));
		if (null != productId && !"".equals(productId)) {
			// 清除缓存
			cacheBo.deleteProductCacheById(Long.valueOf(productId));
		}
		return baseDao.execute("product.updateProduct", map);
	}

	@Override
	public int[] batchUpdateProduct(List<ProductDto> productList) throws Exception {
		int len = productList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			ProductDto dto = productList.get(i);
			map.put("productId", dto.getProductId());
			map.put("state", dto.getState());
			map.put("updateUserId", dto.getUpdateUserId());
			map.put("updateTimeString", dto.getUpdateTimeString());
			map.put("expirationDateString", dto.getExpirationDateString());
			map.put("productSign", dto.getProductSign());
			batchValues[i] = map;
			// 清除缓存
			cacheBo.deleteProductCacheById(dto.getProductId());
		}
		return baseDao.batchUpdate("product.updateProduct", batchValues);
	}

	@Override
	public int[] batchUpdateProductStatus(List<Map<String, Object>> params) throws Exception {
		int len = params.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			batchValues[i] = params.get(i);
			// 清除缓存
			cacheBo.deleteProductCacheById(Long.valueOf(params.get(i).get("productId").toString()));
		}
		return baseDao.batchUpdate("product.updateProductStatus", batchValues);
	}

	@Override
	public List<ProductDto> getExpireProduct(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("product.findExpireProduct", map, ProductDto.class);
	}

	@Override
	public int[] updateProductState(List<Long> productIds) throws Exception {
		Map<String, Object>[] batchValues = new HashMap[productIds.size()];
		for (int i = 0; i < productIds.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productIds.get(i));
			batchValues[i] = map;
			// 清除缓存
			cacheBo.deleteProductCacheById(Long.valueOf(productIds.get(i)));
		}
		return baseDao.batchUpdate("product.updateProductState", batchValues);
	}

	@Override
	public int[] batchUpdateProductState(String[] productIds, String state) {
		if (NullUtil.isNull(productIds)) {
			return new int[] {};
		}
		int len = productIds.length;
		Map<String, Object>[] batchParams = new Map[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", StringUtils.trim(productIds[i]));
			map.put("state", state);
			batchParams[i] = map;
			// 清除缓存
			cacheBo.deleteProductCacheById(Long.valueOf(productIds[i]));
		}
		return baseDao.batchUpdate("product.batchUpdateProductStatus", batchParams);
	}

	@Override
	public int batchUpdateStockCount(List<Map<String, Object>> stockList) throws Exception {
		int len = stockList.size();
		Map<String, Object>[] batchParams = new Map[len];
		for (int i = 0; i < len; i++) {
			batchParams[i] = stockList.get(i);
		}
		return baseDao.batchUpdate("product.batchUpdateStockCount", batchParams).length;
	}

	@Override
	public List<ProductDto> getListByIds(List<Long> productIdList) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productIdsList", productIdList);
		return baseDao.queryForList("product.getListByIds", map, ProductDto.class);
	}

	@Override
	public List<ProductDto> getProductsForOriPlaceVen(Map<?, ?> map) throws Exception {
		return baseDao.queryForList("product.getProductsForOriPlaceVen", map, ProductDto.class);
	}

	@Override
	public List<ProductSinxinDTO> queryProductForSinxin(ProductSinxinDTO queryDTO) throws Exception {
		return baseDao.queryForList("product.queryProductForSinxin", queryDTO, ProductSinxinDTO.class);
	}

	@Override
	public List<ProductDto> getProductListForSeller(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("product.getProductListForSeller", map, ProductDto.class);
	}

	@Override
	public List<ProductClassDTO> getCateNameByBusinessId(Map<String, Object> param) throws Exception {
		return baseDao.queryForList("product.getCateNameByBusinessId", param, ProductClassDTO.class);
	}

	@Override
	public ProductDto getProdDetailByProdId(String productId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		return (ProductDto) this.baseDao.queryForObject("product.getProdDetailByProdId", map, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAdProduct(Map<String, Object> param) throws Exception {
		return baseDao.queryForList("product.getAdProduct", param, ProductDto.class);
	}

	@Override
	public int getAdProductCount(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("product.getAdProductCount", map, Integer.class);
	}

	@Override
	public int updateProductUpdateTime(Map<String, Object> map) throws Exception {

		return (int) baseDao.execute("product.updateProductUpdateTime", map);
	}

	@Override
	public List<ProductDto> querySpProductListForCertif(Map<String, Object> param) throws Exception {
		return baseDao.queryForList("product.querySpProductListForCertif", param, ProductDto.class);
	}

	@Override
	public Integer querySpProductTotalForCertif(Map<String, Object> param) throws Exception {
		return (Integer) baseDao.queryForObject("product.querySpProductTotalForCertif", param, Integer.class);
	}
	
	@Override
	public int getProductCountByBusinessId(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("product.getProductCountByBusinessId", map, Integer.class);
	}

	@Override
	public List<ProductDto> getFastProductList(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getFastProductList", map, ProductDto.class);
		return list;
	}

	@Override
	public int getCountForFast(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("product.getCountForFast", map, Integer.class);
	}
	
	@Override
	public List<ProductDto> getFastPListForALl(Map<String, Object> map) throws Exception {
		List<ProductDto> list = baseDao.queryForList("product.getFastPListForALl", map, ProductDto.class);
		return list;
	}

	@Override
	public List<ProductDto> getFastPListByIds(String[] productIds) throws Exception {
		List<ProductDto> list = new ArrayList<ProductDto>();
		Map<String, Object> params = new HashMap<String, Object>();
		for (String current : productIds) {
			params.put("productId", current);
			list.add(getOneProduct(params));
		}
		return list;
	}


}
