package com.gudeng.commerce.gd.promotion.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GiftInstoreInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchaseDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchasegiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchaseEntity;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchasegiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdPurchaseService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdPurchaseServiceImpl implements GrdPurchaseService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdPurchaseDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdPurchaseEntity.getById", map, GrdPurchaseDTO.class);
	}

	@Override
	public List<GrdPurchaseDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.getList", map, GrdPurchaseDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdPurchaseEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdPurchaseEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdPurchaseDTO t) throws Exception {
		return baseDao.execute("GrdPurchaseEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdPurchaseEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdPurchaseEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdPurchaseDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.getListPage", map, GrdPurchaseDTO.class);
	}
	
	@Override
	public List<GrdPurchaseDTO> getPurchaseByStatusTotal(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.getPurchaseByStatusTotal", map, GrdPurchaseDTO.class);
	}

	@Override
	public int getPurchaseBatchTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdPurchaseEntity.getPurchaseBatchTotal", map, Integer.class);
	}

	@Override
	public List<GrdPurchaseDTO> getPurchaseBatch(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.getPurchaseBatch", map, GrdPurchaseDTO.class);
	}

	@Override
	public List<GrdPurchaseDTO> queryPurchaseSelect(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.queryPurchaseSelect", map, GrdPurchaseDTO.class);
	}

	@Override
	public int queryPurchasegiftListTotal(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForObject("GrdPurchaseEntity.queryPurchasegiftListTotal", map, Integer.class);
	}

	@Override
	public List<GrdPurchasegiftDTO> queryPurchasegiftList(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.queryPurchasegiftList", map, GrdPurchasegiftDTO.class);
	}
	
	@Override
	public List<GrdPurchasegiftDTO> getPurchaseGiftList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.getPurchaseGiftList", map, GrdPurchasegiftDTO.class);
	}

	@Override
	@Transactional 
	public int add(GrdPurchaseEntity entity, List<GrdPurchasegiftEntity> giftList) throws Exception {
		int result = 0;
		try {
			
			int count = baseDao.queryForObject("GrdPurchaseEntity.findCountByDay", null, Integer.class);
			count = count+1;
			String purcheaseNo = new DecimalFormat("0000").format(count);
			SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
			purcheaseNo = format.format(new Date()) + purcheaseNo;
			entity.setPurchaseNO(purcheaseNo);
			result = baseDao.persist(entity, Long.class)>0?1:0;
			if(result<=0){
				return result;
			}

			for (GrdPurchasegiftEntity gift : giftList) {
				gift.setPurchaseNO(purcheaseNo);
				baseDao.execute("GrdPurchasegiftEntity.insert", gift);
				baseDao.execute("GrdPurchasegiftEntity.updateGiftPrice", gift);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public List<GrdGdGiftDTO> getGiftByPurchaseNoList(Map<String,Object> map) throws Exception {
		return baseDao.queryForList("GrdPurchasegiftEntity.findGiftByPurchaseNoList", map, GrdGdGiftDTO.class);
	}
	@Override
	public int getGiftByPurchaseNoCount(Map<String,Object> map) throws Exception {
		return baseDao.queryForObject("GrdPurchasegiftEntity.findGiftByPurchaseNoCount", map, Integer.class);
	}
	
	/**
	 * 修改采购单
	 * 先删除采购单之前的礼品记录
	 * 然后添加本次的
	 * 然后查询出先关礼品的单价
	 * 对比，如果不一样就修改成最新的
	 */
	@Override
	@Transactional 
	public int edit(GrdPurchaseEntity entity, List<GrdPurchasegiftDTO> giftList) throws Exception {
		int result = 0;
		try {
			Map paramMap=new HashMap();
			paramMap.put("purchaseNO", entity.getPurchaseNO());
			GrdPurchaseDTO dto = baseDao.queryForObject("GrdPurchaseEntity.getList", paramMap,GrdPurchaseDTO.class);
			if(!"1".equals(dto.getStatus()))return -100;
			result = baseDao.execute("GrdPurchaseEntity.updateByPurchaseNO", entity);		
			if(result<=0)return result;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("purchaseNo", entity.getPurchaseNO());
			result = baseDao.execute("GrdPurchasegiftEntity.deleteByPurchaseNo", map);		
			if(result<=0)return result;
			map.clear();

			for (GrdPurchasegiftDTO gift : giftList) {
				baseDao.execute("GrdPurchasegiftEntity.insert", gift);
				if(gift.getUnitPrice()!=gift.getPrototypePrice()){
					baseDao.execute("GrdPurchasegiftEntity.updateGiftPrice", gift);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
		
	}	
	
	@Override
	public List<GiftInstoreInfoDTO> findGiftInstoreInfoList(Map<String,Object> map) throws Exception {
		return baseDao.queryForList("GrdPurchaseEntity.findGiftInstoreInfoList", map, GiftInstoreInfoDTO.class);
	}
	
	@Override
	public int findGiftInstoreInfoCount(Map<String,Object> map) throws Exception {
		return baseDao.queryForObject("GrdPurchaseEntity.findGiftInstoreInfoCount", map, Integer.class);
	}
	
	@Override
	public int closeBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GrdPurchaseEntity.closeById", batchValues).length;
	}

	@Override
	public Integer insert(GrdPurchaseDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}