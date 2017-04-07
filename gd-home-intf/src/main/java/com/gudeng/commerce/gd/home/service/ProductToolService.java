package com.gudeng.commerce.gd.home.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductSolrDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;

public interface ProductToolService {

	
	/**
	 * 查询某个产品的梯度价格
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductPriceDto> getLadderPriceByProductId(String productId) throws Exception;
	
	/**
	 * 查询产品分类id的所有父辈祖辈分类
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<ProductCategoryDTO> getCategoryAncestors(Long id) throws Exception;
	
/*	*//**
	 * 产品审核
	 * @param map
	 * @return
	 * @throws Exception
	 *//*
	public int auditProduct(Map<String,Object> map) throws Exception;*/
	
	/**
	 * 根据产品分类信息、商品信息、会员信息查询商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getList(Map<String, Object> map) throws Exception;
	/**
	 * 列出顶层分类
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> listTopProductCategory() throws Exception;;
	/**
	 * 功能描述:根据商品分类id查询子分类信息
	 * @return ProductCategory集合
	 * @throws Exception 
	 */
	public List<ProductCategoryDTO> getChildProductCategory(Long id) throws Exception;
	
	/**
	 * 根据产品ID查询图片记录
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductPictureDto> getPictureByProductId(String productId) throws Exception;
	/**
	 *  保存产品图片信息
	 * @param pictureDto
	 * @return
	 * @throws Exception
	 */
	public ProductPictureDto addProductPic(ProductPictureDto pictureDto) throws Exception;
	
	/**
	 * 保存产品图片信息(via实体类)
	 * @param pictureDto
	 * @return
	 */
	public long addProductPicViaEntity(ProductPictureEntity pictureEntity) throws Exception;
	
	/** 更新图片信息
	 * @param pictureDto
	 * @return
	 * @throws Exception
	 */
	public int updateProductPic(ProductPictureDto pictureDto) throws Exception;
	
	
	/**新增产品(via 实体类)
	 * @param productEntity
	 * @return
	 */
	public Long persistProductViaEntity(ProductEntity productEntity) throws Exception;
	
/*	*//**
	 *  新增产品
	 * @param ProductDto
	 * @return
	 * @throws Exception
	 *//*
	public ProductDto addProduct(ProductDto productDto) throws Exception;*/
	
	/**
	 * 编辑商品信息
	 * @param productDto
	 * @return
	 * @throws Exception
	 */
	public int updateProduct(ProductDto productDto) throws Exception;
	
	/**
	 *  更新产品状态
	 * @param productDto
	 * @return
	 * @throws Exception 
	 */
	public int updateProductStatus(Map map) throws Exception;
	
	/**
	 *  更新产品信息
	 * @param productDto
	 * @return
	 */
	public int updateProduct(Map map) throws Exception;;
	
	/**
	 * 根据ProductId查询
	 * 
	 * @param productId
	 * @return ProductDto
	 * 
	 */
	public ProductDto getByProductId(String productId) throws Exception;
	
	/**
	 * 根据多个产品id获取对应的产品集合
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getProductsByIds(String[] productIds) throws Exception;
	
	/**
	 * @Description getProductPriceList 获取商品的价格区间信息
	 * @param productId
	 * @return
	 * @throws Exception
	*/
	public List<ProductPriceDto> getProductPriceList(String productId)	throws Exception ;
	
	/**
	 * @Description batchDelete 删除商品价格区间信息
	 * @param productId
	 * @return
	*/
	public int batchDeletePrices(String productId) throws Exception ;
	
	/**
	 * @Description batchUpdatePrice 批量添加商品价格区间
	 * @param priceDtoList
	 * @throws Exception 
	*/
	public void batchUpdatePrice(List<ProductPriceDto> priceDtoList) throws Exception;
	
	/**	根据产品名称查询
	 * @param productName
	 * @return
	 * @throws Exception
	 */
	public ProductDto getByProductName(String productName) throws Exception;
	
	
	/**
	 * 根据条件查询产品list(分页查询)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getProductList(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据商品状态和商铺id查找商品信息
	 * @param state 商品状态
	 * @param businessId 商铺id
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getByStateAndBusinessId(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据商品状态和商铺id查找商品总数
	 * @param state
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public int countByStateAndBusinessId(Map<String, Object> map) throws Exception;
	/**
	 * 查询产品记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getCount(Map<String,Object> map) throws Exception;
	
	/** 逻辑删除记录产品
	 * @param productId
	 * @return
	 * @throws Exception 
	 */
	public int deleteProduct(String productId) throws Exception;
	
	/**
	 *  批量删除产品
	 * @param productIds
	 * @return
	 * @throws Exception 
	 */
	public int[] batchDeleteProduct(String[] productIds) throws Exception;
	
	/**
	 *  根据市场Id查询商品分类
	 * @param marketId
	 * @return
	 * @throws Exception 
	 */
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId) throws Exception;
	
	/**
	 * @Description listProductCategoryByLvAndMarketId 根据产品类别级别和市场ID查看产品分类信息 
	 * @param map
	 * @return
	 * @CreationDate 2015年10月29日 下午3:44:58
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public List<ProductCategoryDTO> listProductCategoryByLvAndMarketId(Map<String, Object> map) throws Exception;

	/**
	 * 根据店铺名称搜索 
	 * 
	 * @param string 
	 * @return ProductSolrDTO
	 * 
	 */
	public List<ProductSolrDTO> getBySearch(String pName)throws Exception;
	
	
	
	/**
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	public List<ProductSolrDTO> getByQueryBean(ProductQueryBean productQueryBean)throws Exception;

	/**
	 * 获取当前分类的所有子类Solr搜索
	 * 
	 * @param long 
	 * @return ProductCategoryDTO
	 * 
	 */
	List<com.gudeng.commerce.gd.search.dto.ProductCategoryDTO> getChildProductCategoryBySolr(Long id) throws Exception;
	
	/**
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	public ProductSearchResultDTO getByProductQueryBean(ProductQueryBean productQueryBean) throws Exception;
	
	
	/**根据产品ID删除图片记录
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public int deletePicByProductId(String productId) throws Exception;
	
	/**根据产品ID、图片类型删除图片记录
	 * @param productId
	 * @param pictureType
	 * @return
	 * @throws Exception
	 */
	public int deletePicByProductIdAndType(String productId, String pictureType) throws Exception;

	/**
	 * 批量更新产品
	 * @param productList
	 * @throws Exception
	 */
	public int[] batchUpdateProduct(List<ProductDto> productList) throws Exception;
	
	/**
	 * 批量更新产品状态
	 * @param productList
	 * @throws Exception
	 */
	public int[] batchUpdateProductStatus(List<Map<String, Object>> params) throws Exception;
	
}

