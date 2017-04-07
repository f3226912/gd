package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.BankInformationDTO;
import com.gudeng.commerce.gd.order.entity.BankInformationEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface BankInformationService extends BaseService<BankInformationDTO> {
	public Long insert(BankInformationEntity entity) throws Exception;

	public int updateBatch(Map<String, Object> param) throws Exception;

	public int batchInsert(List<BankInformationEntity> entityList) throws Exception;
	
	public BankInformationDTO getByBankSignId(Map<String,Object> params) throws Exception;
}