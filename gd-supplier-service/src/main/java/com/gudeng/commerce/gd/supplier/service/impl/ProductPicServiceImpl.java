package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.service.ProductPicService;
import com.gudeng.commerce.gd.supplier.util.NullUtil;

public class ProductPicServiceImpl implements ProductPicService {

	@Autowired
	private BaseDao baseDao;
	
	
	@Override
	public ProductPictureDto getPicture(String productId, String picType)
			throws Exception {
		Map <String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		map.put("pictureType", picType);
		return (ProductPictureDto) baseDao.queryForObject("productPic.getPicture", map, ProductPictureDto.class);
	}

	@Override
	public int deletePicByProductId(String productId) throws Exception {
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return baseDao.execute("productPic.deletePicByProductId", map);
	}

	@Override
	public int[] batchDeleteProductPic(String[] pictureTypes, String productId) {
		if (NullUtil.isNull(pictureTypes)) {
			return new int[] {};
		}
		int len = pictureTypes.length;
		Map<String, Object>[] batchValues = new Map[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pictureType", StringUtils.trim(pictureTypes[i]));
			map.put("productId", StringUtils.trim(productId));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("productPic.deletePicByProductId", batchValues);
	}
	
	@Override
	public ProductPictureDto addProductPic(ProductPictureDto pictureDto)
			throws Exception {
		long pictureId = (long) baseDao.execute("productPic.persistProductPic", pictureDto);
		pictureDto.setId(pictureId);
		return pictureDto;
	}

	@Override
	public int updateProductPic(ProductPictureDto pictureDto) throws Exception {
		return baseDao.execute("productPic.updateProductPic", pictureDto);
	}

	@Override
	public long addProductPicViaEntity(ProductPictureEntity pictureEntity) throws Exception{
		return (long) baseDao.persist(pictureEntity, Long.class);
	}

	@Override
	public List<ProductPictureDto> getPictureByProductId(String productId)	throws Exception {
		Map <String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		return baseDao.queryForList("productPic.getPictureByProductId", map, ProductPictureDto.class);
	}

	@Override
	public int deletePicByProductIdAndType(String productId, String pictureType)
			throws Exception {
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("pictureType", pictureType);
		return baseDao.execute("productPic.deletePicByProductIdAndType", map);
	}

	@Override
	public long mergeProductPic(ProductPictureEntity pictureEntity) {
		return baseDao.merge(pictureEntity);
	}

	@Override
	public long dynamicMergeProductPic(ProductPictureEntity pictureEntity) {
		return baseDao.dynamicMerge(pictureEntity);
	}

	@Override
	public List<ProductPictureDto> getPictureListByProductId(List<String> productIdList) throws Exception {
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("productIdList", productIdList);
		return baseDao.queryForList("productPic.getPictureListByProductId", map, ProductPictureDto.class);
	}
	@Override
	public List<ProductPictureDto> getPictureByProdIdAndType(String productId,String pictureType)throws Exception {
		Map <String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		map.put("pictureType", pictureType);
		return baseDao.queryForList("productPic.getPictureByProdIdAndType", map, ProductPictureDto.class);
	}

	
}
