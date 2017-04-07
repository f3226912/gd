package com.gudeng.commerce.gd.customer.service.certif;

import com.gudeng.commerce.gd.customer.dto.certif.CertifPersonalDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifPersonalEntity;
import com.gudeng.commerce.gd.customer.service.BaseService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface CertifPersonalService extends BaseService<CertifPersonalDTO> {
	public Long insert(CertifPersonalEntity entity) throws Exception;
}