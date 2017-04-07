package com.gudeng.commerce.gd.customer.service.certif;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.certif.CertifCompanyDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCompanyEntity;
import com.gudeng.commerce.gd.customer.service.BaseService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface CertifCompanyService extends BaseService<CertifCompanyDTO> {
	public Long insert(CertifCompanyEntity entity) throws Exception;
	
	public CertifCompanyDTO getOneBySearch(Map<String, Object> params) throws Exception;
}