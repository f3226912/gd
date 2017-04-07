package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.BankInformationDTO;
import com.gudeng.commerce.gd.order.entity.BankInformationEntity;
import com.gudeng.commerce.gd.order.service.BankInformationService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class BankInformationServiceImpl implements BankInformationService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public BankInformationDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("BankInformationEntity.getById", map, BankInformationDTO.class);
	}

	@Override
	public List<BankInformationDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("BankInformationEntity.getList", map, BankInformationDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("BankInformationEntity.deleteById", map);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("BankInformationEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(BankInformationDTO t) throws Exception {
		return baseDao.execute("BankInformationEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("BankInformationEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(BankInformationEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<BankInformationDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("BankInformationEntity.getListPage", map, BankInformationDTO.class);
	}

	@Override
	public int updateBatch(Map<String, Object> param) throws Exception {
		List<String> list = (List<String>) param.get("idsList");
		int len = list.size();
		String status = (String) param.get("status");
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			map.put("status", status);
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("BankInformationEntity.update", batchValues).length;
	}

	@Override
	@Transactional
	public int batchInsert(List<BankInformationEntity> entityList) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		for (BankInformationEntity entity : entityList) {
			params.put("bankSignId", entity.getBankSignId());
			BankInformationDTO dto = this.getByBankSignId(params);
			if (dto == null) {
				this.insert(entity);
			} else {
				dto.setBankName(entity.getBankName());
				dto.setBankCode(entity.getBankCode());
				dto.setCardName(entity.getCardName());
				dto.setCardType(entity.getCardType());
				dto.setCardLength(entity.getCardLength());
				dto.setBankShortName(entity.getBankShortName());
				dto.setBankEShortName(entity.getBankEShortName());
				this.update(dto);
			}
		}
		return entityList.size();
	}

	@Override
	public BankInformationDTO getByBankSignId(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("BankInformationEntity.getBySignId", params, BankInformationDTO.class);
	}

}