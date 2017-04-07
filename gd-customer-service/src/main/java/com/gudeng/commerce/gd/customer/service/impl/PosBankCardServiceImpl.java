package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;
import com.gudeng.commerce.gd.customer.entity.PosBankCardEntity;
import com.gudeng.commerce.gd.customer.service.PosBankCardService;


/**
 *功能描述：MemberBaseinfo增删改查实现类
 *
 */
@Service
public class PosBankCardServiceImpl implements PosBankCardService{
	
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private CacheBo cacheBo;

	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}

//	@Override
//	public Long addPosBankCardEntity(PosBankCardEntity posBankCardEntity) throws Exception {
//		return (Long)baseDao.persist(posBankCardEntity, Long.class);
//	}

	@Override
	public MemberBaseinfoDTO getByBankNo(String bankNo) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bankCardNo", bankNo);
		return baseDao.queryForObject("PosBankCard.getByBankNo", paramMap, MemberBaseinfoDTO.class);
	}

	@Override
	public int addPosBankCardDTO(PosBankCardDTO posBankCardDTO) throws Exception {
		// TODO Auto-generated method stub
		return (int)baseDao.execute("PosBankCard.insert", posBankCardDTO);
	}
	
	@Override
	public PosBankCardEntity getByMemberId(String memberId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memberId", memberId);
		return baseDao.queryForObject("PosBankCard.getByMemberId", paramMap, PosBankCardEntity.class);
	}
 
}
