package com.gudeng.commerce.gd.api.service.v160929;

import java.util.Map;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCorpDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCorpEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface CertifCorpToolService extends BaseToolService<CertifCorpDTO> {
	public Long insert(CertifCorpEntity entity) throws Exception;
	
	public CertifCorpDTO getOneBySearch(Map<String, Object> params) throws Exception;
}