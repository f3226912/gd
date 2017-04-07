package com.gudeng.commerce.gd.api.service.v160929;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.customer.dto.certif.CertifShopDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifShopEntity;

/**
 * 实体商铺认证
 * @author xphou
 */
public interface CertifShopToolService extends BaseToolService<CertifShopDTO> {
	public Long insert(CertifShopEntity entity) throws Exception;
	
	/**
	 * 根据用户ID获取实体认证信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public CertifShopDTO getByUserId(String memberId) throws Exception;
}