package com.gudeng.commerce.gd.api.service.v160929;

import java.util.Map;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.customer.dto.certif.CertifBaseDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifBaseEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface CertifBaseToolService extends BaseToolService<CertifBaseDTO> {
	public Long insert(CertifBaseEntity entity) throws Exception;
	
	public CertifBaseDTO getOneBySearch(Map<String, Object> params) throws Exception;
	
	public Map<String, Object> getStatusCombination(Map<String, Object> params) throws Exception;
}