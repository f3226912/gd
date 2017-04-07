package com.gudeng.commerce.gd.customer.service;

import com.gudeng.commerce.gd.customer.dto.SensitiveLogDTO;
import com.gudeng.commerce.gd.customer.entity.SensitiveLogEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface SensitiveLogService extends BaseService<SensitiveLogDTO> {
	public Long insert(SensitiveLogEntity entity) throws Exception;
	
	public SensitiveLogDTO replaceAllSensitiveWords(String str) throws Exception;
	
}