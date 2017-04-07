package com.gudeng.commerce.gd.admin.service.certif;

import com.gudeng.commerce.gd.admin.service.BaseToolService;
import com.gudeng.commerce.gd.customer.dto.certif.CertifPersonalDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifPersonalEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface CertifPersonalToolService extends BaseToolService<CertifPersonalDTO> {
	public Long insert(CertifPersonalEntity entity) throws Exception;
}