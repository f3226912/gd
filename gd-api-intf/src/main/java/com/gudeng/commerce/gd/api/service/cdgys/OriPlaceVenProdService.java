package com.gudeng.commerce.gd.api.service.cdgys;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;

/**
 *	产地供应商-产品服务
 */
public interface OriPlaceVenProdService {
	
	
	/**
	 * 产地供应商分类列表
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> categoryList() throws Exception;
	
	/**
	 * 更新产品状态
	 * @param map
	 * @return
	 */
	public int updateProductStatus(Map map) throws Exception;
	
	/**
	 * 获取首页商品列表
	 * @param option	1-出售中, 2-仓库, 3-卖完
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> getProductList(String option, String marketId, String businessId) throws Exception;
	
	/**
	 * 商品上下架
	 * @param option
	 * @param productId
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public ErrorCodeEnum productUpAndDown(Map<String,Object> map) throws MalformedURLException, Exception;
	
	/**
	 * 商品批量上下架
	 * @param map
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public int[] productBatchUpAndDown(Map<String,Object> map) throws MalformedURLException, Exception;
	
	/**新增产品(via 实体类)
	 * @param productEntity
	 * @return
	 */
	public Long persistProductViaEntity(ProductEntity productEntity) throws Exception;
	
	/**
	 * 保存产品图片信息(via实体类)
	 * @param pictureDto
	 * @return
	 */
	public long addProductPicViaEntity(ProductPictureEntity pictureEntity) throws Exception;
	
	/**
	 * 根据ProductId查询
	 * 
	 * @param productId
	 * @return ProductDto
	 * 
	 */
	public ProductDto getProductById(String productId) throws Exception;

	/**
	 * 编辑商品信息
	 * @param productDto
	 * @return
	 * @throws Exception
	 */
	public int updateProduct(ProductDto productDto) throws Exception;
	
	/**
	 * 根据产品ID查询图片记录
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductPictureDto> getPictureByProductId(String productId) throws Exception;
	
	/**
	 * 根据产品id列表获取产品图片
	 * @param productIdList
	 * @return
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public List<ProductPictureDto> getPicturesByProductId(List<String> productIdList) throws MalformedURLException, Exception;
	
	/**根据产品ID删除图片记录
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public int deletePicByProductId(String productId) throws Exception;
	
	/**
	 * 根据mainIds查询审核信息, mainId(productId, businessId)
	 * @param mainIds
	 * @return
	 * @throws Exception
	 */
	public Map<String, AuditInfoDTO> getAuditInfos(List<String> mainIds, Map<String, Object> params) throws Exception;
}
