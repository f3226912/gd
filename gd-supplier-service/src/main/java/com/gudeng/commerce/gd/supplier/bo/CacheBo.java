package com.gudeng.commerce.gd.supplier.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.RefCateSupNpsDTO;


public class CacheBo {
	/***********************产品分类缓存**********************/
	
	/**
	 * 根据Id查询产吕分类
	 * @param id
	 * @param baseDao
	 * @return
	 */
	public ProductCategoryDTO getProductCategory(Long id,BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return (ProductCategoryDTO) baseDao.queryForObject("ProductCategory.getProductCategory", params, ProductCategoryDTO.class);
	}

	/**
	 * 根据市场id查夜所有产品分类
	 * @param marketId
	 * @return
	 */
	public List<ProductCategoryDTO> listProductCategory(Long marketId,BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("marketId", marketId);
		return baseDao.queryForList("ProductCategory.listProductCategory", params ,ProductCategoryDTO.class);
	}

	/**
	 * 根据父id查询子产品分类
	 * @param id
	 * @return
	 */
	public List<ProductCategoryDTO> getChildProductCategory(Long id,BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId",id);
		return baseDao.queryForList("ProductCategory.listChildProductCategory", params ,ProductCategoryDTO.class);
	}

	/**
	 * 根据父id及市场id查询子产品分类
	 * @param id
	 * @param marketId
	 * @return
	 */
	public List<ProductCategoryDTO> getChildProductCategoryByMarketId(Long id,Long marketId,BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId",id);
		params.put("marketId",marketId);
		return baseDao.queryForList("ProductCategory.listChildProductCategory", params ,ProductCategoryDTO.class);
	}
	
	/**
	 * 查询一级产品分类
	 * @param baseDao
	 * @return
	 */
	public List<ProductCategoryDTO> listTopProductCategory(BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		return baseDao.queryForList("ProductCategory.listTopProductCategory", params ,ProductCategoryDTO.class);
	}
	
	/**
	 * 根据marketid查询一级产品分类
	 * @param marketId
	 * @param baseDao
	 * @return
	 */
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId,BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("marketId", marketId);
		return baseDao.queryForList("ProductCategory.listTopProductCategory", params ,ProductCategoryDTO.class);
	}
	
	/**
	 * 删除产品分类缓存
	 * @param id
	 */
	public void deleteProductCategoryCacheById(Long id){
		
	}
	
	/***********************产品分类缓存End**********************/
	
	/**********************产品缓存开始Start*********************/
	
	/**
	 * 根据产品ID查询产品
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ProductDto getProductById(String productId,BaseDao baseDao) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		return (ProductDto)baseDao.queryForObject("product.getById", map,
				ProductDto.class);
	}
	
	/**
	 * 删除产品缓存
	 * @param id
	 */
	public void deleteProductCacheById(Long id){
		
	}
	
	/**********************关联分类开始End*********************/
	
	/**********************产品缓存Start*********************/
	public List<RefCateSupNpsDTO> getRefCateSupNpsByNpsCateId(Long npsCateId,String type, BaseDao baseDao) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("cateId", npsCateId);
		paramMap.put("type", type);
		
		return (List<RefCateSupNpsDTO>)baseDao.queryForList("RefCateSupNps.getRefCateSupNpsByNpsCateId", paramMap,RefCateSupNpsDTO.class);
	
	}
	/**
	 * 删除关联缓存
	 * @param id
	 */
	public void deleteRefCateSupNpsByCateId(Long id, String type){
		
	}
	/**********************关联分类End*********************/
}
