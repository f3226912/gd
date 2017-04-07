package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.ProductCateResultDTO;
import com.gudeng.commerce.gd.api.dto.ProductListAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductSolrDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductClassDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;

public interface ProductToolService {

	
	
	/**
	 * 产品审核
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int auditProduct(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据产品分类信息、商品信息、会员信息查询商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getList(Map<String, Object> map) throws Exception;
	
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
	
	/**
	 *  新增产品
	 * @param ProductDto
	 * @return
	 * @throws Exception
	 */
	public ProductDto addProduct(ProductDto productDto) throws Exception;
	
	/**
	 * 编辑商品信息
	 * @param productDto
	 * @return
	 * @throws Exception
	 */
	public int updateProduct(ProductDto productDto) throws Exception;
	
	/**
	 * 编辑商品信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateProduct(Map map) throws Exception ;
	
	/**
	 * 更新产品状态
	 * @param map
	 * @return
	 */
	public int updateProductStatus(Map map) throws Exception;
	
	/**
	 * 根据ProductId查询
	 * 
	 * @param productId
	 * @return ProductDto
	 * 
	 */
	public ProductDto getByProductId(String productId) throws Exception;
	/**
	 * 根据ProductId 验证商品有效性
	 * 
	 * @param productId
	 * @return ProductDto
	 * 
	 */
	
	public int validProductAliveByBusinessId(String productId) throws Exception;
	
	/**	根据产品名称查询
	 * @param productName
	 * @return
	 * @throws Exception
	 */
	public ProductDto getByProductName(String productName) throws Exception;
	
	
	/**
	 * 根据ProductId查询
	 * 
	 * @param productId
	 * @return ProductDto
	 * 
	 */
	public ProductDto getProductById(String productId) throws Exception;
	/**
	 * 根据条件查询产品list(分页查询)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<ProductDto> getProductList(Map<String, Object> map) throws Exception;
	

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
	 * 根据分类ID查询产品
	 * @param cateId
	 * @return
	 */
	public List<?> getByCateId(Long cateId);

	/**
	 * 按条件获取农批商产品列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductListAppDTO> getShopProductList(Map<String, Object> map) throws Exception;
	
	public List<ProductListAppDTO> getShopProductListNew(Map<String, Object> map) throws Exception;	
	/**
	 * 获取商品产品列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getShopProducts(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件获取农批商产品列表(不分页)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getShopProductTotalList(Map<String, Object> map) throws Exception ;

	/**
	 * 按条件获取农批商产品总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getShopsProductTotal(Map<String, Object> map) throws Exception;
	
	public int getShopsProductTotalNew(Map<String, Object> map) throws Exception;
	
	
	
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
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	public ProductSearchResultDTO getByProductQueryBean(ProductQueryBean productQueryBean) throws Exception;

	public List<ProductCateResultDTO> getProductCateResult(ProductQueryBean productQueryBean) throws Exception;
	
	public List<ProductCateResultDTO> getProductAreaResult(ProductQueryBean productQueryBean) throws Exception;
	
	public List<PushProductDTO> translate(List<ProductSolrDTO> list,
			List<PushProductDTO> pushAdInfoList) throws Exception;
	
	
	
	/**
	 * 功能描述:根据农批市场查询一级产品分类信息接口
	 * @return ProductCategory集合
	 * @throws Exception 
	 */
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId) throws Exception;
	
	/**根据产品ID删除图片记录
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public int deletePicByProductId(String productId) throws Exception;

	/**
	 * 批量更新产品库存
	 * @param stockList
	 * @throws Exception
	 */
	public int batchUpdateStockCount(List<Map<String, Object>> stockList) throws Exception;
	
	/**
	 * 根据产品ID、图片类型 查询图片记录
	 * @param productId
	 * @param picType (1:主图;2:多角度图3:web.4:app)
	 * @return
	 * @throws Exception
	 */
	public ProductPictureDto getPicture(String productId, String picType) throws Exception;
	
	/**
	 * 查询产品分类id的所有父辈祖辈分类
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<ProductCategoryDTO> getCategoryAncestors(Long id) throws Exception;

	/**
	 * 根据产品id列表查找产品信息
	 * @param pIdList
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getListByIds(List<Long> pIdList) throws Exception;

	/**
	 * 查询某个产品的区间价格
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductPriceDto> getLadderPriceByProductId(String productId) throws Exception;
	
	/**
	 * 获取农商友-卖家-店铺的所有商品(List<一级分类<List<产品>,..>) 
	 * @param stateList
	 * @param userId
	 * @param marketId
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> getCategoryAndProductListForSeller(List<String> stateList, String userId, String marketId, String businessId) throws Exception;
	
	/**
	 * 根据产品id列表获取产品图片
	 * @param productIdList
	 * @return
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public List<ProductPictureDto> getPicturesByProductId(List<String> productIdList) throws MalformedURLException, Exception;
	
	/**
	 * 根据mainIds查询审核信息, mainId(productId, businessId)
	 * @param mainIds
	 * @return
	 * @throws Exception
	 */
	public Map<String, AuditInfoDTO> getAuditInfos(List<String> mainIds, Map<String, Object> params) throws Exception;
	
	/**
	 * 产品分类列表
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> categoryList(String marketId) throws Exception;
	
	
	/**
	 * 商品上下架-(农批商)
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public ErrorCodeEnum productUpAndDown(Map<String,Object> map) throws MalformedURLException, Exception;
	
	public List<ProductCategoryDTO> getListTopProductCategoryByMarketId(Long marketId) throws Exception;

	public List<ProductClassDTO> getCateNameByBusinessId(
			Map<String, Object> param) throws Exception;

	/**
	 * 获取商铺中的商品个数，仅仅排除已删除的
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getProductCountByBusinessId(Map<String, Object> map) throws Exception;

}
