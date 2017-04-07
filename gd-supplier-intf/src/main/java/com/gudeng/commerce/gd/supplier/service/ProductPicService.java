package com.gudeng.commerce.gd.supplier.service;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;

/**
 *  产品图片
 * @author yzzhang
 *
 */
public interface ProductPicService {

	
	
	/**
	 * 更新图片信息
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public int updateProductPic(ProductPictureDto pictureDto) throws Exception;
	
	/**
	 * 新增产品图片记录
	 * @param pictureDto
	 * @return
	 * @throws Exception
	 */
	public ProductPictureDto addProductPic(ProductPictureDto pictureDto) throws Exception;
	
	/**
	 * 新增产品图片记录 (via 实体类)
	 * @param pictureDto
	 * @return
	 * @throws Exception 
	 */
	public long addProductPicViaEntity(ProductPictureEntity pictureEntity) throws Exception;
	
	/**
	 * 更新图片记录
	 * @param pictureEntity
	 * @return
	 */
	public long mergeProductPic(ProductPictureEntity pictureEntity);
	
	/**动态更新图片记录
	 * @param pictureEntity
	 * @return
	 */
	public long dynamicMergeProductPic(ProductPictureEntity pictureEntity);
	
	/**
	 * 根据产品ID、图片类型 查询图片记录
	 * @param productId
	 * @param picType (1:主图;2:多角度图3:web.4:app)
	 * @return
	 * @throws Exception
	 */
	public ProductPictureDto getPicture(String productId, String picType) throws Exception;
	
	/**
	 * 根据产品ID查询图片记录
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductPictureDto> getPictureByProductId(String productId) throws Exception;
	
	/**
	 * 根据产品id列表查询图片列表
	 * @param productIdList
	 * @return
	 * @throws Exception
	 */
	public List<ProductPictureDto> getPictureListByProductId(List<String> productIdList) throws Exception ;
	
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
	 * 删除同一产品的各种类型的图
	 * @param pictureTypes
	 * @param productId
	 * @return
	 */
	int[] batchDeleteProductPic(String[] pictureTypes, String productId);
	
/*	*//**
	 * 删除同一产品的各种类型的图
	 * @param pictureTypes
	 * @param productId
	 * @return
	 *//*
	int[] batchDeleteProductPic(String productId);*/
	
	public List<ProductPictureDto> getPictureByProdIdAndType(String productId,String productType)throws Exception;
	
}
