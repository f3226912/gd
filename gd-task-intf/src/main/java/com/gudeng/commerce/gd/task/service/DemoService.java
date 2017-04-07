package com.gudeng.commerce.gd.task.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.task.dto.DictDTO;

public interface DemoService {

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return DictDTO
	 * 
	 */
	public DictDTO getById(String id)throws Exception;
	
	
}