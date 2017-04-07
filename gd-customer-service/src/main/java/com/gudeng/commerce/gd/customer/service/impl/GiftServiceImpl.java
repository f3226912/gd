package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.CallstatiSticsEntity;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.customer.service.GiftService;

public class GiftServiceImpl implements GiftService{
	@Autowired
	private BaseDao baseDao;

	@Override
	public int getTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("gift.getTotal", map, Integer.class);
	}
	
	@Override
	public int getCountByName(Map map) throws Exception {
		return (int) baseDao.queryForObject("gift.getCountByName", map, Integer.class);
	}
	
	@Override
	public int getCountByType(Map map) throws Exception {
		return (int) baseDao.queryForObject("gift.getCountByType", map, Integer.class);
	}
	
	@Override
	public List<GiftDTO> getBySearch(Map map) throws Exception {
		return  baseDao.queryForList("gift.getBySearch", map, GiftDTO.class);
	}
	
	//@SuppressWarnings("unchecked")
	@Override
	public int add(GiftEntity giftEntity){    
		return (int) baseDao.execute("gift.addGift", giftEntity);
		
		
		
	}
	
	@Override
	public GiftDTO getById(String id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (GiftDTO) baseDao.queryForObject("gift.getByGiftId", map, GiftDTO.class);
				
	}
	
	@Override
	public int update(GiftDTO giftDTO) throws Exception {
		return (int) baseDao.execute("gift.updateGift", giftDTO);
	}
}
