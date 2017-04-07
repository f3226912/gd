package com.gudeng.commerce.gd.customer.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.OfferPriceDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;
import com.gudeng.commerce.gd.customer.service.NpsOfferPriceService;
import com.gudeng.commerce.gd.customer.util.DateUtil;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
@Service
public class NpsOfferPriceServiceImpl implements NpsOfferPriceService {
	
	@Autowired
	private BaseDao<?>  baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public Integer updateInfo(Map<String, Object> map) throws Exception {
		return baseDao.execute("NpsOfferPrice.updateOfferPrice", map);
	}

	
	
	@Override
	public Long insert(Map<String, Object> map) throws Exception {
		return baseDao.persist(map, Long.class);
	}

	@Override
	public Integer findNpsOfferPriceById(Map<String, Object> map) throws Exception {
		return baseDao.execute("", map);
	}


	@Override
	public NpsOfferPriceDTO getById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("NpsOfferPrice.getById", map, NpsOfferPriceDTO.class);
	}

	@Override
	public int deleteById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("NpsOfferPrice.deleteById", map);
	}

	@Override
	public int deleteById(List<String> list) {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("NpsOfferPrice.deleteById", batchValues).length;
	}

	@Override
	public int update(NpsOfferPriceDTO t) throws Exception{
		return baseDao.execute("NpsOfferPrice.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("NpsOfferPrice.getTotal", map, Integer.class);
	}

	@Override
	@Transactional
	public Long insert(NpsOfferPriceEntity entity) {
	  BigDecimal bd=new BigDecimal(entity.getOfferPriceStr());
	  entity.setOfferPrice(bd);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", entity.getPurchaseId());
		baseDao.execute("NpsPurchase.updatePriceCount", map);
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<NpsOfferPriceDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("NpsOfferPrice.getListPage", map, NpsOfferPriceDTO.class);
	}


	@Override
	public List<NpsOfferPriceDTO> getList(Map<String, Object> map) {
		return null;
	}


	@Override
	public NpsOfferPriceDTO getOfferPriceId(Map<String, Object> paramMap)
			throws Exception {
		return baseDao.queryForObject("NpsOfferPrice.getOfferPriceId", paramMap, NpsOfferPriceDTO.class);
	}


	@Override
	public List<NpsOfferPriceDTO> getOfferPriceList(Map<String, Object> parsMap)
			throws Exception {
		return baseDao.queryForList("NpsOfferPrice.getOfferPriceList", parsMap, NpsOfferPriceDTO.class);
	}


	@Override
	public int getOfferPriceTotal(Map<String, Object> parsMap) throws Exception {
		return baseDao.queryForObject("NpsOfferPrice.getOfferPriceTotal", parsMap, Integer.class);
	}


	@Override
	public int updateStatus(NpsOfferPriceDTO t) throws Exception {
		int count = baseDao.execute("NpsOfferPrice.updateStatus", t);
		//修改状态的同时，需要修改累计报价数
		baseDao.execute("NpsPurchase.updateBackGroundPriceCount", t);
		return count;
	}


	@Override
	public int getUserAndOfferPriceCount(NpsOfferPriceEntity entity)
			throws Exception {
		return baseDao.queryForObject("NpsOfferPrice.getUserOfferPriceCount",entity, Integer.class);
	}


  @Override
  public List<OfferPriceDTO> everyDayMinPriceList() throws Exception{
    Map<String ,Object> map=new HashMap<String, Object>();
    String end=DateUtil.getDate(new Date(), "yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date date = calendar.getTime();
    String begin=DateUtil.getDate(date, "yyyy-MM-dd");
    map.put("begin", begin+"18:00:00");
    map.put("end", end+" 18:00:01");
    return baseDao.queryForList("NpsOfferPrice.getEveryDayMinPriceList", map, OfferPriceDTO.class);
  }

	
}
