package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductClassDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;

public interface ProductToolService {

	
	/**
	 * 查询某个产品的梯度价格(即区间价格)
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductPriceDto> getLadderPriceByProductId(String productId) throws Exception;

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
	 * 功能描述:根据农批市场查询一级产品分类信息接口
	 * @return ProductCategory集合
	 * @throws Exception 
	 */
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId) throws Exception;
	/**
	 * 列出顶层分类
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> listTopProductCategory() throws Exception;
	
	/**
	 * 查询产品分类id的所有父辈祖辈分类
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<ProductCategoryDTO> getCategoryAncestors(Long id) throws Exception;
	
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
	 *  更新产品信息
	 * @param productDto
	 * @return
	 * @throws Exception 
	 */
	public int updateProduct(Map map) throws Exception;
	
	/**
	 * 根据ProductId查询
	 * 
	 * @param productId
	 * @return ProductDto
	 * 
	 */
	public ProductDto getByProductId(String productId) throws Exception;
	
	/**	根据产品名称查询
	 * @param productName
	 * @return
	 * @throws Exception
	 */
	public ProductDto getByProductName(String productName) throws Exception;
	
	/**根据产品ID删除图片记录
	 * @param productId
	 * @param picType
	 * @return
	 * @throws Exception
	 */
	public int deletePicByProductId(String productId) throws Exception;
	
	/**
	 * 批量更新产品状态
	 * @param productIds
	 * @return
	 * @throws Exception 
	 */
	public int[] batchUpdateProductState(String[] productIds, String state) throws Exception;
	
	/**
	 * 删除同一产品的各种类型的图
	 * @param pictureType
	 * @param productId
	 * @return
	 * @throws Exception 
	 */
	int[] batchDeleteProductPic(String[] pictureType, String productId) throws Exception;
	
	/**
	 * 根据条件查询产品list(分页查询)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getProductList(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询产品list
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getProductListAll(Map<String, Object> map) throws Exception;
	
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
	
	
	/**根据产品ID、图片类型删除图片记录
	 * @param productId
	 * @param pictureType
	 * @return
	 * @throws Exception
	 */
	public int deletePicByProductIdAndType(String productId, String pictureType) throws Exception;
	
	/**
	 * 根据多个产品id获取对应的产品集合
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getProductsByIds(String[] productIds) throws Exception;
	
	/**
	 * 批量更新产品
	 * @param productList
	 * @throws Exception
	 */
	public int[] batchUpdateProduct(List<ProductDto> productList) throws Exception;

	/**
	 * 批量更新产品状态
	 * @param productList
	 * @return
	 * @throws Exception
	 */
	public int[] batchUpdateProductStatus(List<Map<String, Object>> params) throws Exception ;
	
	/**
	 * 修改产品状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateProductStatus(Map map) throws Exception;
	
	
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
	public int getAdProductCount(Map<String, Object> param) throws Exception;

	/**
	 * 广告商品信息修改为当前时间
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateProductUpdateTime(Map<String, Object> map) throws Exception;
	
}
