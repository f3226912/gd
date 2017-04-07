package com.gudeng.commerce.gd.customer.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReCategoryBanelImgDTO;

/**
 *功能描述：ReBusinessCategoryDTO 增删改查实现类
 *
 */
public interface ReCategoryBanelImgService {

   
	
	
	public List<ReCategoryBanelImgDTO>  getByCategoryId(Long categoryId) throws Exception;

	public int getCountByCategoryId(Long categoryId) throws Exception;
	
//	public int getCountByGroupId(Long grouId) throws Exception;
	
	public List<ReCategoryBanelImgDTO>   getAllByGroupId(Long grouId) throws Exception;
	
	public int getCount(Map map) throws Exception;
	
	public ReCategoryBanelImgDTO  getById(Long id) throws Exception;
	
	public List<ReCategoryBanelImgDTO>  getByAll() throws Exception;
	
	public List<ReCategoryBanelImgDTO>  getByAllByPage(Map map) throws Exception;
	
	public List<ReCategoryBanelImgDTO>  getAllGroups() throws Exception;




}