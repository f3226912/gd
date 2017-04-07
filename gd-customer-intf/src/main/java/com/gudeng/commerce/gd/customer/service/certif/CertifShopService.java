package com.gudeng.commerce.gd.customer.service.certif;

import com.gudeng.commerce.gd.customer.dto.certif.CertifShopDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifShopEntity;
import com.gudeng.commerce.gd.customer.service.BaseService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface CertifShopService extends BaseService<CertifShopDTO> {
	public Long insert(CertifShopEntity entity) throws Exception;
	
	/**
	 * 根据用户ID获取实体认证信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public CertifShopDTO getByUserId(String memberId) throws Exception;
}