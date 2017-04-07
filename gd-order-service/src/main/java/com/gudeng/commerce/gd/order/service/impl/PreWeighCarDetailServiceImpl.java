package com.gudeng.commerce.gd.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.entity.InStoreDetailEntity;
import com.gudeng.commerce.gd.order.service.InStoreDetailService;
import com.gudeng.commerce.gd.order.service.PreWeighCarDetailService;
import com.gudeng.commerce.gd.order.service.SalToshopsDetailService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class PreWeighCarDetailServiceImpl implements PreWeighCarDetailService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private InStoreDetailService inStoreDetailService;
	
	@Override
	public List<PreWeighCarDetailDTO> getByWeighCarId(Long weighCarId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weiCarId", weighCarId);
		return baseDao.queryForList("PreWeighCarDetail.getByWeighCarId", map, PreWeighCarDetailDTO.class);
	}

	@Override
	public PreWeighCarDetailDTO getById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("PreWeighCarDetail.getById", map, PreWeighCarDetailDTO.class);
	}

	@Override
	public List<PreWeighCarDetailDTO> getByBusinessUserId(Map map) {
		return baseDao.queryForList("PreWeighCarDetail.getByBusinessUserId", map, PreWeighCarDetailDTO.class);
	}
	
	@Override
	public int getCountByUserId(Long userId)  {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return (int)baseDao.queryForObject("PreWeighCarDetail.getCountByUserId", map, Integer.class);

	}
	@Override
	public int getCountByMobile(String mobile)  {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		return (int)baseDao.queryForObject("PreWeighCarDetail.getCountByMobile", map, Integer.class);
		
	}

	@Override
	public List<PreWeighCarDetailDTO> getByCategoryUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return baseDao.queryForList("PreWeighCarDetail.getByCategoryUserId", map, PreWeighCarDetailDTO.class);
	}
	
	@Override
	public List<PreWeighCarDetailDTO> getByMobile(Map map) {
		return baseDao.queryForList("PreWeighCarDetail.getByMobile", map, PreWeighCarDetailDTO.class);
	}

	@Override
	public int update(PreWeighCarDetailDTO preWeighCarDetailDTO) {
		return baseDao.execute("PreWeighCarDetail.updateDto", preWeighCarDetailDTO);
	}
	
	@Override
	public int deleteBusiness(Long weighCarId,Long businessId) {
		Map map1=new HashMap();
		map1.put("weighCarId", weighCarId);
		map1.put("businessId", businessId);
		return baseDao.execute("PreWeighCarDetail.deleteBusiness", map1);	
	}
	
	
	@Transactional
	@Override
	public int grapGoods(PreWeighCarDetailDTO pwd, Long inStoreNo,Long businessId) {
		PreWeighCarDetailDTO pwdNew=new PreWeighCarDetailDTO();
		pwdNew.setPwdId(pwd.getPwdId());
		pwdNew.setMarginWeigh(pwd.getMarginWeigh());
		int i = baseDao.execute("PreWeighCarDetail.updateDto", pwdNew);
		Map map1=new HashMap();
		//不管哪个商家参与抢货，或者购货后，直接删除此好货和此商铺的推送关联
		
//	      	 热门好货商铺推送，默认销售全部商品
//		   1 产地供应商有100吨，批发商取消购买，直接删除推送商铺的关联表，即此好货不再显示与批发商的好货列表
//		   2 产地供应商有100度，批发商购买了部分，如50吨，减去热门好货的库存后，删除推送商铺的关联表，此好货不再显示与批发商的好货列表
//		
		map1.put("weighCarId", pwd.getWeighCarId());
		map1.put("businessId", businessId);
		int deleteCount = baseDao.execute("PreWeighCarDetail.deleteBusiness", map1);
		
		Map map=new HashMap();
		map.put("userId", pwd.getMemberId());
	
		InStoreDetailEntity inStoreDetailEntity=new InStoreDetailEntity();
		//使用订单号的生成规则，生成入库单号
		inStoreDetailEntity.setInStoreNo(inStoreNo);
		inStoreDetailEntity.setBusinessId(businessId);
		inStoreDetailEntity.setPrice(pwd.getPrice());
		inStoreDetailEntity.setPrice(0d);
		inStoreDetailEntity.setPwdId(pwd.getPwdId());
		inStoreDetailEntity.setUnit(pwd.getUnit());
		inStoreDetailEntity.setWeigh(pwd.getWeigh());
		inStoreDetailEntity.setUpdateTime(new Date());
		inStoreDetailEntity.setCreateTime(new Date());
		Long l = (Long)baseDao.persist(inStoreDetailEntity,Long.class);
		return i;
	}

	@Override
	public int getTotalForAdmin(Map<String, Object> params) {
		return baseDao.queryForObject("PreWeighCarDetail.getTotalForAdmin", params, Integer.class);
	}
	
	@Override
	public List<PreWeighCarDetailDTO> getByConditionPageForAdmin(
			Map<String, Object> params) {
		List<PreWeighCarDetailDTO> list = baseDao.queryForList("PreWeighCarDetail.getByConditionPageForAdmin", params, PreWeighCarDetailDTO.class);
		Map<String, Object> map = new HashMap<>();
		try {
			for (int i = 0; i < list.size(); i++) {
				PreWeighCarDetailDTO pwd = list.get(i);
				map.put("pwdId", pwd.getPwdId());
				
				pwd.setInStores(inStoreDetailService.getInstoreProductList(map));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int batchUpdatePaymentStatus(String pwds,String updateUserId) {
		Map<String, Object> params = new HashMap<>();
		params.put("pwds", pwds);
		params.put("updateUserId", updateUserId);
		
		return baseDao.execute("PreWeighCarDetail.batchUpdatePaymentStatus", params);
	}
	
	
	

}
