package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;


/**
 *功能描述：BusinessBaseinfo 增删改查实现类
 *
 */
@Service
public class BusinessBaseinfoServiceImpl implements BusinessBaseinfoService{
	
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BusinessBaseinfoDTO getById(String id) throws Exception {
		Map map=new HashMap();
		map.put("businessId", id);
		return (BusinessBaseinfoDTO)baseDao.queryForObject("BusinessBaseinfo.getByBusinessId", map, BusinessBaseinfoDTO.class);
	}

	@Override
	public BusinessBaseinfoDTO getByUserId(String userId) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		return (BusinessBaseinfoDTO)baseDao.queryForObject("BusinessBaseinfo.getByUserId", map, BusinessBaseinfoDTO.class);
	}
	@Override
	public BusinessBaseinfoDTO getBusinessInfoByUserId(Map<String,Object> map) throws Exception {
		BusinessBaseinfoDTO dto = null;
		try {
			 dto = baseDao.queryForObject("BusinessBaseinfo.getBusinessInfoByUserId", map, BusinessBaseinfoDTO.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dto;
	}
	
	@Override
	public BusinessBaseinfoDTO getByUserIdAndMarketId(String userId,String marketId) throws Exception {
		Map map=new HashMap();
		map.put("userId", userId);
		map.put("marketId", marketId);
		return (BusinessBaseinfoDTO)baseDao.queryForObject("BusinessBaseinfo.getByUserIdAndMarketId", map, BusinessBaseinfoDTO.class);
	}

	
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int)baseDao.queryForObject("BusinessBaseinfo.getTotal", map,Integer.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map map=new HashMap();
		map.put("businessId", id);
		return (int)baseDao.execute("BusinessBaseinfo.deleteBusinessBaseinfo", map );
	}

	@Override
	public int addBusinessBaseinfoByMap(Map<String, Object> map) throws Exception {
		return (int)baseDao.execute("BusinessBaseinfo.addBusinessBaseinfo", map);
	}

	@Override
	public int addBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception {
		return (int)baseDao.execute("BusinessBaseinfo.addBusinessBaseinfo", mc);
	}

	@Override
	public int updateBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception {
		return (int)baseDao.execute("BusinessBaseinfo.updateBusinessBaseinfo", mc);
	}

	@Override
	public List<BusinessBaseinfoDTO> getBySearch(Map map) throws Exception {
		return  (List<BusinessBaseinfoDTO>)baseDao.queryForList("BusinessBaseinfo.getBySearch", map,BusinessBaseinfoDTO.class);
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(isolation=Isolation.SERIALIZABLE)
	public synchronized Long addBusinessBaseinfoEnt(BusinessBaseinfoEntity be)
			throws Exception {
		Map map =new HashMap();
		map.put("userId", be.getUserId());
		BusinessBaseinfoDTO bdto= baseDao.queryForObject("BusinessBaseinfo.getByUserId", map, BusinessBaseinfoDTO.class);
		if(bdto!=null){
			return bdto.getBusinessId();
		}else{
			Date nowTime=new Date();
			be.setCreateTime(nowTime);
			be.setUpdateTime(nowTime);
			if(be.getOfflineStatus()==null){
				be.setOfflineStatus(2);//persist 的方法，为此字段设置为null插入数据库，会有问题，故默认置为2
			}
			Long id = (Long)baseDao.persist(be, Long.class);
			return id;
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BusinessBaseinfoDTO> getAll(int startRow,int endRow) throws Exception {
		Map map=new HashMap();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		return  (List<BusinessBaseinfoDTO>)baseDao.queryForList("BusinessBaseinfo.getAll", map,BusinessBaseinfoDTO.class);
	
	
	}

	@Override
	public List<BusinessBaseinfoDTO> getShops(Map<String, Object> map)
			throws Exception {
		return  (List<BusinessBaseinfoDTO>)baseDao.queryForList("BusinessBaseinfo.getShops", map,BusinessBaseinfoDTO.class);
	}

	@Override
	public int getShopsTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("BusinessBaseinfo.getShopsTotal", map, Integer.class);
	}

	@Override
	public void addBrowser(Long businessId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		baseDao.execute("BusinessBaseinfo.addBrowser", paramMap);
	}

	@Override
	public List<ReBusinessCategoryDTO> getCategoryList(Long businessId,int start, int size)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("businessId", businessId);
		map.put("startRow", start);
		map.put("endRow", size);
		
		return (List<ReBusinessCategoryDTO>)baseDao.queryForList("BusinessBaseinfo.getCategoryList", map,ReBusinessCategoryDTO.class);
	}

	@Override
	public List<BusinessBaseinfoDTO> getShop(BusinessBaseinfoDTO businessBaseinfoDTO)
			throws Exception {

		return (List<BusinessBaseinfoDTO>)baseDao.queryForList("BusinessBaseinfo.getShop", businessBaseinfoDTO,BusinessBaseinfoDTO.class);
	}

	@Override
	public BusinessBaseinfoDTO getByMap(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForObject("BusinessBaseinfo.getByMap", map, BusinessBaseinfoDTO.class);
	}

	@Override
	public BusinessBaseinfoDTO getByBusinessNo(String businessNo) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("businessNo", businessNo);
		return baseDao.queryForObject("BusinessBaseinfo.getByBusinessNo", map, BusinessBaseinfoDTO.class);
	}
	
	@Override
	public BusinessBaseinfoDTO getByPosNumber(String posNumber) throws Exception {
		Map map=new HashMap();
		map.put("posNumber", posNumber);
		return (BusinessBaseinfoDTO)baseDao.queryForObject("BusinessBaseinfo.getByPosNumber", map, BusinessBaseinfoDTO.class);
		}

	public BusinessBaseinfoDTO getByMacAddr(String macAddr) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("macAddr", macAddr);
		List<BusinessBaseinfoDTO> businessList = baseDao.queryForList("BusinessBaseinfo.getByMacAddr", map, BusinessBaseinfoDTO.class);
		if (CollectionUtils.isEmpty(businessList)) {
			return null;
		}
		return businessList.get(0);
	}

	@Override
	public BusinessBaseinfoDTO getBusinessShortInfoBySearch(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("BusinessBaseinfo.getBusinessShortInfoBySearch", params, BusinessBaseinfoDTO.class);
	}

	@Override
	public Map<String, Object> getCertifForBusinessBySearch(Map<String, Object> params) throws Exception {
		return baseDao.queryForMap("BusinessBaseinfo.getCertifForBusinessBySearch", params);
	}
	
	@Override
	public List<BusinessBaseinfoDTO> getSupplierShops(Map<String, String> map)
			throws Exception {
		return  (List<BusinessBaseinfoDTO>)baseDao.queryForList("BusinessBaseinfo.getSupplierShops", map,BusinessBaseinfoDTO.class);
	}
	
	@Override
	public Integer getTotalShops(Map<String, String> map)
			throws Exception {
		return baseDao.queryForObject("BusinessBaseinfo.getTotalShops", map, Integer.class);
	}

	@Override
	public List<BusinessBaseinfoDTO> getTradingDynamics(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return cacheBo.getTradingDynamics(map,baseDao);
	}

	@Override
	public List<BusinessBaseinfoDTO> getListForGdActivity(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("BusinessBaseinfo.getListForGdActivity", map, BusinessBaseinfoDTO.class);
	}
	
	
}
