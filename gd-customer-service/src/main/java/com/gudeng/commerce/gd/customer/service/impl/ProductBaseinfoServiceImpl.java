package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.CartInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.customer.service.ProductBaseinfoService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class ProductBaseinfoServiceImpl implements ProductBaseinfoService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<ProductBaseinfoDTO> getSupplierProduct(Map<String, Object> param) throws Exception {
		return (List<ProductBaseinfoDTO>)baseDao.queryForList("ProductBaseinfo.getSupplierProduct", param,ProductBaseinfoDTO.class);
	}

	@Override
	public int getSupplierProductTotal(Map<String, Object> param) {
		return baseDao.queryForObject("ProductBaseinfo.getSupplierProductTotal", param, Integer.class);
	}

	@Override
	public List<ProductBaseinfoDTO> getSupplierProductByRecommend(Map<String, Object> param) throws Exception {
		return (List<ProductBaseinfoDTO>)baseDao.queryForList("ProductBaseinfo.getSupplierProductByRecommend", param,ProductBaseinfoDTO.class);
	}

	@Override
	public int getSupplierProductTotalByRecommend(Map<String, Object> param) throws Exception {
		return baseDao.queryForObject("ProductBaseinfo.getSupplierProductTotalByRecommend", param, Integer.class);
	}

	@Override
	public List<ProductBaseinfoDTO> getSupplierProductByPrecise(Map<String, Object> param) throws Exception {
		return (List<ProductBaseinfoDTO>)baseDao.queryForList("ProductBaseinfo.getSupplierProductByPrecise", param,ProductBaseinfoDTO.class);
	}

	@Override
	public int getSupplierProductTotalByPrecise(Map<String, Object> param) throws Exception {
		return baseDao.queryForObject("ProductBaseinfo.getSupplierProductTotalByPrecise", param, Integer.class);
	}

	@Override
	@Transactional
	public PageQueryResultDTO<ProductBaseinfoDTO> getListSearchAccurateProduct(
			Map<String, Object> paramMap) throws Exception {
		List<ProductBaseinfoDTO> dataList = baseDao.queryForList("ProductBaseinfo.getListSearchAccurateProduct", paramMap, ProductBaseinfoDTO.class);
		Integer totalNum = baseDao.queryForObject("ProductBaseinfo.getTotalSearch", paramMap, Integer.class);
		PageQueryResultDTO<ProductBaseinfoDTO> dto = new PageQueryResultDTO<ProductBaseinfoDTO>();
		dto.setDataList(dataList);
		dto.setTotalCount(totalNum == null ? 0 : totalNum); 
		return dto;
	}

	@Override
	@Transactional
	public PageQueryResultDTO<BusinessBaseinfoDTO> getListSearchAccurateShop(
			Map<String, Object> paramMap) throws Exception {
		List<BusinessBaseinfoDTO> dataList = baseDao.queryForList("ProductBaseinfo.getListSearchAccurateShop", paramMap, BusinessBaseinfoDTO.class);
		Integer totalNum = baseDao.queryForObject("ProductBaseinfo.getTotalSearch", paramMap, Integer.class);
		PageQueryResultDTO<BusinessBaseinfoDTO> dto = new PageQueryResultDTO<BusinessBaseinfoDTO>();
		dto.setDataList(dataList);
		dto.setTotalCount(totalNum == null ? 0 : totalNum); 
		return dto;
	}

	@Override
	public PageQueryResultDTO<ProductBaseinfoDTO> getListPage(Map<String, Object> paramsMap) {
		PageQueryResultDTO<ProductBaseinfoDTO> pageQueryResultDTO = new PageQueryResultDTO<>();
		pageQueryResultDTO.setDataList(baseDao.queryForList("ProductBaseinfo.getListPage", paramsMap, ProductBaseinfoDTO.class));
		
		Integer totalNum = getTotal(paramsMap);
		
		pageQueryResultDTO.setTotalCount(totalNum == null ? 0 : totalNum);
		
		return pageQueryResultDTO;
	}

	@Override
	public Integer getTotal(Map<String, Object> paramsMap) {
		return baseDao.queryForObject("ProductBaseinfo.getTotal", paramsMap, Integer.class);
	}

	@Override
	public ProductBaseinfoDTO getProductById(Long productId) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			return baseDao.queryForObject("ProductBaseinfo.getById", map, ProductBaseinfoDTO.class);
	}

	@Override
	public List<ProductBaseinfoDTO> getProductList(Map<String, Object> paramMap) {
		return (List<ProductBaseinfoDTO>)baseDao.queryForList("ProductBaseinfo.getProductList", paramMap,ProductBaseinfoDTO.class);
	}
	

}