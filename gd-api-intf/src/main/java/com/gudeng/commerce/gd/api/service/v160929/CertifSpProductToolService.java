package com.gudeng.commerce.gd.api.service.v160929;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.customer.dto.certif.CertifSpProductDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifSpProductEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface CertifSpProductToolService extends BaseToolService<CertifSpProductDTO> {
	public Long insert(CertifSpProductEntity entity) throws Exception;
}