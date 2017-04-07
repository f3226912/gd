package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.entity.AccInfoEntity;
import com.gudeng.commerce.gd.order.service.AccInfoService;

public class AccInfoServiceImpl implements AccInfoService {

	@Autowired
	private BaseDao<?> baseDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public AccInfoDTO getWalletIndex(Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		AccInfoDTO accInfoDTO= (AccInfoDTO)this.baseDao.queryForObject("accInfo.getWalletIndexById", map, AccInfoDTO.class);
		return accInfoDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateAccInfo(AccInfoDTO accInfoDTO) {
		Map map = new HashMap();
		map.put("memberId", accInfoDTO.getMemberId());
		return  this.baseDao.execute("accInfo.updateAccInfo", accInfoDTO);
	}
	
	@Override
	public int addAccInfo(AccInfoDTO accInfoDTO) {
		return  this.baseDao.execute("accInfo.addAccInfo", accInfoDTO);
	}

	@Override
	public AccInfoDTO getTransPwd(AccInfoDTO accInfoDTO) {
		return (AccInfoDTO)baseDao.queryForObject("accInfo.validateTransPwd", accInfoDTO, AccInfoDTO.class);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Integer updateTransPwd(Long memberId, String transPwd)
			throws Exception {
		Map map = new HashMap();
		map.put("memberId",memberId);
		map.put("transPwd", transPwd);
		return  this.baseDao.execute("accInfo.updateTransPwd", map);
	
	}

	@Override
	public void updateMemAmount(AccInfoDTO accInfo) throws ServiceException {
		baseDao.execute("accInfo.updateMemAmount", accInfo);
	}

	@Override
	public Long addAccInfoEntity(AccInfoEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}
}
