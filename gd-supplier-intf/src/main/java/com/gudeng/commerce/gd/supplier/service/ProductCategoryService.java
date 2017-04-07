package com.gudeng.commerce.gd.supplier.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;

/**
 * 产品分类信息接口
 * @author Administrator
 *
 */
public interface ProductCategoryService {

	/**
	 * 功能描述:保存产品分类信息
	 * @param productCategoryDTO DTO对象
	 * @return DTO
	 */
	public Long persistProductCategory(ProductCategoryEntity productCategoryEntity)  throws Exception;
	
	/**
	 * 功能描述:删除产品分类信息
	 * @param id 产品分类
	 * @return String:Null:删除成功,否则返回相关提示
	 */
	public String deleteProductCategory(Long id);
	
	/**
	 * 功能描述:修改产品分类信息
	 * @param productCategoryDTO DTO对象
	 * @return DTO
	 */
	public ProductCategoryEntity updateProductCategory(ProductCategoryEntity productCategoryEntity);
	
	/**
	 * 功能描述:分页查询产品分类信息
	 * @param productCategoryDTO DTO对象
	 * @param page: Hn封装分页对象:可以些设置order pagesize等属性
	 * @return
	 */
	//public List<ProductCategoryDTO> queryProductCategoryPage(ProductCategoryDTO productCategoryDTO) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 查询产品分类id的所有父辈祖辈分类
	 * @param id
	 * @return
	 */
	public List<ProductCategoryDTO> getCategoryAncestors(Long id);
	
	/**
	 * 功能描述:查看产品分类信息
	 * @param id 产品分类id
	 * @return ProductCategory对象
	 */
	public ProductCategoryDTO getProductCategory(Long id);
	
	/**
	 * 功能描述:查询所有产品分类信息接口
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> listProductCategory(Long marketId);
	
	/**
	 * 功能描述:查询所有产品分类信息接口
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> listAllProductCategory();
	
	/**
	 * 功能描述:根据农批市场查询所有产品分类信息接口
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> listProductCategoryByMarketId(Long marketId);
	
	/**
	 * 功能描述:根据分类名称查询产品分类信息接口
	 * @param name 分类名称
	 * @return ProductCategory对象
	 */
	public ProductCategoryDTO getProductCategoryByName(String name);
	
	/**
	 * 功能描述:根据农批市场查询一级产品分类信息接口
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId);
	
	/**
	 * 功能描述:查询一级产品分类信息接口
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> listTopProductCategory();
	
	
	/**
	 * 功能描述:根据分类id查询子分类信息
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> getChildProductCategory(Long id);
	
	/**
	 * @Description listProductCategoryByLvAndMarketId 根据产品类别级别和市场ID查看产品分类信息 
	 * @param map
	 * @return
	 * @CreationDate 2015年10月29日 下午3:44:58
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public List<ProductCategoryDTO> listProductCategoryByLvAndMarketId(Map<String, Object> map);
	
	/**
	 * 功能描述:根据农批市场查询所有产品分类
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> listAllProductCategoryByMarketId(Long marketId);
	
	
	
	
	public List<ProductCategoryDTO> getChildProductCategoryByMarketId(Long id,Long marketId);
	
	
	/**
	 * 根据市场id查询多级分类列表
	 * @param imageHost
	 * @param marketId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> categoryList(String imageHost, String marketId) throws Exception ;
	/**
	 * 获取产地供应商首页商品列表
	 * @param option	0-全部, 1-出售中, 2-待审核, 3-已下架
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> getProductList(String option, String marketId, String businessId) throws Exception;
	
	/**
	 * 获取卖家-商品列表 
	 * @param stateList
	 * @param userId
	 * @param marketId
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> getProductListForSeller(List<String> stateList, String userId, String marketId, String businessId) throws Exception;
}
