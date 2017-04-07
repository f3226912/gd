package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.BankInformationDTO;
import com.gudeng.commerce.gd.order.entity.BankInformationEntity;

/**
 * 银行信息管理服务类
 * @date 2016年11月9日
 */
public interface BankInformationToolService extends BaseToolService<BankInformationDTO> {
	public Long insert(BankInformationEntity entity) throws Exception;

	public int updateBatch(Map<String, Object> param) throws Exception;

	public int batchInsert(List<BankInformationEntity> entityList) throws Exception;
}