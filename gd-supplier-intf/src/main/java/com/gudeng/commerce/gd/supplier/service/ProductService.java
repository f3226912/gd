package com.gudeng.commerce.gd.supplier.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.ProductClassDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.dto.ProductSinxinDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;

/**
 *	功能描述：产品服务接口
 */
public interface ProductService {
	
	/**
	 * 新增产品记录
	 * @param productDto
	 * @return
	 */
	public ProductDto persistProduct(ProductDto productDto);
	
	/**
	 * 新增产品记录(via 实体类)
	 * @param productDto
	 * @return
	 */
	public Long persistProductViaEntity(ProductEntity productEntity);
	
	/**
	 *  更新产品记录
	 * @param productDto
	 * @return
	 */
	public int updateProduct(ProductDto productDto);
	
	/**
	 *  更新产品信息
	 * @param productDto
	 * @return
	 */
	public int updateProduct(Map map) throws Exception;
	
	/**
	 * 查询某个产品的梯度价格
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductPriceDto> getLadderPriceByProductId(String productId) throws Exception;
	
	
	/**
	 * 根据产品ID查询对象
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ProductDto getById(String productId) throws Exception;
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ProductDto getOneProduct(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据多个产品id获取对应的产品集合
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getProductsByIds(String[] productIds) throws Exception;
	
	/**
	 * 根据产品ID查询对象
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ProductDto getByIdWithoutPicType(String productId) throws Exception;
	
	
	
	
	/**根据产品名称查询对象
	 * @param productName
	 * @return
	 * @throws Exception
	 */
	public ProductDto getByName(String productName) throws Exception ;
	
	/**
	 * 根据条件查询list(分页查询)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getProductList(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询list
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getProductListAll(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据产品分类信息、商品信息、会员信息查询商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getCount(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据产品状态、商铺id查询商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getByStateAndBusinessId(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int countByStateAndBusinessId(Map<String,Object> map) throws Exception;
	
/*	*//**
	 * 产品审核
	 * @param map
	 * @return
	 * @throws Exception
	 *//*
	public int auditProduct(Map<String,Object> map) throws Exception;*/
	
	
	/** 逻辑删除记录
	 * @param productId
	 * @return
	 */
	public int deleteProduct(String productId);
	
	/**
	 *  批量删除产品
	 * @param productIds
	 * @return
	 */
	public int[] batchDeleteProduct(String[] productIds);
	
	/**
	 * 批量更新产品状态
	 * @param productIds
	 * @return
	 */
	public int[] batchUpdateProductState(String[] productIds, String state);
	
	/**
	 *  批量删除产品
	 * @param productIds
	 * @return
	 */
	public List<ProductDto> getByCateId(Long  cateId);

	/**
	 * @Description batchUpdate 批量添加商品价格区间
	 * @param priceDtoList
	 * @CreationDate 2015年10月21日 下午2:00:38
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public void batchUpdatePrices(List<ProductPriceDto> priceDtoList);
	
	/**
	 * @Description batchDelete 删除商品价格区间信息
	 * @param productId
	 * @return
	 * @CreationDate 2015年10月21日 下午2:46:38
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public int batchDeletePrices(String productId) ;
	
	/**
	 * @Description getProductPriceList 获取商品的价格区间信息
	 * @param productId
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年10月21日 下午3:14:15
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public List<ProductPriceDto> getProductPriceList(String productId) throws Exception ;

	/**
	 * 根据条件获取农批商产品列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getShopProductList(Map<String, Object> map) throws Exception ;
	
	
	public List<ProductDto> getShopProductListNew(Map<String, Object> map) throws Exception ;
	
	/**
	 * 根据条件获取农批商产品列表(不分页)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getShopProductTotalList(Map<String, Object> map) throws Exception ;
	
	/**
	 * 根据条件获取农批商产品列表(不分页)-V_160428
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getProductListForSeller(Map<String, Object> map) throws Exception ;
	

	/**
	 * 根据条件获取农批商产品总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getShopsProductTotal(Map<String, Object> map) throws Exception ;

	/**
	 * 批量更新产品
	 * @param productList
	 * @return
	 * @throws Exception
	 */
	public int[] batchUpdateProduct(List<ProductDto> productList) throws Exception ;
	
	/**
	 * 批量更新产品状态
	 * @param productList
	 * @return
	 * @throws Exception
	 */
	public int[] batchUpdateProductStatus(List<Map<String, Object>> params) throws Exception ;
	
	
	/**
	 * 获取已上架而且过期的产品
	 * @param map
	 * @return 
	 * @throws Exception
	 */
	public List<ProductDto> getExpireProduct(Map<String,Object> map) throws Exception ;
	
	/**
	 * 批量下架过期产品
	 * @param productList
	 * @return
	 * @throws Exception
	 */
	public int[] updateProductState(List<Long> productIds) throws Exception ;

	/**
	 * 批量更新产品库存
	 * @param stockList
	 * @return
	 * @throws Exception
	 */
	public int batchUpdateStockCount(List<Map<String, Object>> stockList) throws Exception;

	/**
	 * 根据产品id列表查找产品
	 * @param productIdList
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getListByIds(List<Long> productIdList) throws Exception;

	/**
	 * 更新产品状态
	 * @param map
	 * @return
	 */
	public int updateProductStatus(Map map) throws Exception;
	
	/**
	 * 查询产品列表(产地供应商)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getProductsForOriPlaceVen(Map<?,?> map) throws Exception;
	
	
	public int getShopsProductTotalNew(Map<String, Object> map) throws Exception ;
	
	/**
	 * 商品查询
	 */
	public List<ProductSinxinDTO> queryProductForSinxin(ProductSinxinDTO queryDTO) throws Exception;

	public List<ProductClassDTO> getCateNameByBusinessId(Map<String, Object> param) throws Exception;
	
	public ProductDto getProdDetailByProdId(String productId) throws Exception;
	

	/**
	 * 广告商品信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getAdProduct(Map<String, Object> param) throws Exception;
	/**
	 * 广告商品信息总条数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int getAdProductCount(Map<String, Object> map) throws Exception ;
	
	/**
	 * 广告商品信息修改为当前时间
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateProductUpdateTime(Map<String, Object> map) throws Exception ;
	
	/**
	 * 查询商品信息列表(地理标志产品认证-专用)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> querySpProductListForCertif(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询商品信息列表记录总数(地理标志产品认证-专用)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer querySpProductTotalForCertif(Map<String, Object> param) throws Exception;

	/**
	 * 获取商铺中的商品个数，仅仅排除已删除的
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getProductCountByBusinessId(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 根据条件查询list(分页查询)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getFastProductList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getCountForFast(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 根据条件查询list(
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getFastPListForALl(Map<String, Object> map) throws Exception;
	
	/**
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getFastPListByIds(String[] productIds) throws Exception;
	
	/**
	 * 验证当前商品是否有效
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int validProductAliveByBusinessId(String productId) throws Exception ;
	
}
