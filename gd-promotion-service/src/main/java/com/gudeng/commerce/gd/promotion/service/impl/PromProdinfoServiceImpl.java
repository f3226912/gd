package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.PromActSupplierRefDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.entity.PromActSupplierRefEntity;
import com.gudeng.commerce.gd.promotion.entity.PromProdInfoEntity;
import com.gudeng.commerce.gd.promotion.entity.PromReqAuditEntity;
import com.gudeng.commerce.gd.promotion.service.PromProdinfoService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

public class PromProdinfoServiceImpl implements PromProdinfoService {

	@Resource
	private BaseDao<?> baseDao;

	@Override
	public List<PromProdInfoDTO> querySupplerPageByCondition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseDao.queryForList("PromProdinfo.querySupplerPageByCondition", map, PromProdInfoDTO.class);
	}

	@Override
	public Integer getSupplerTotalCountByCondition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("PromProdinfo.getSupplerTotalCountByCondition", map, Integer.class);
	}

	@Override
	public List<PromProdInfoDTO> queryProductPageByCondition(Map<String, Object> map) {
		return baseDao.queryForList("PromProdinfo.queryProductPageByCondition", map, PromProdInfoDTO.class);
	}

	@Override
	public Integer getProductTotalCountByCondition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("PromProdinfo.getProductTotalCountByCondition", map, Integer.class);
	}

	@Override
	@Transactional
	public Integer updatePromProdInfo(List<PromProdInfoDTO> plist) {
		if (null != plist) {
			int len = plist.size();
			Map<String, Object>[] batchValues = new HashMap[len];
			for (int i = 0; i < len; i++) {
				PromProdInfoDTO pp = plist.get(i);
				Map<String, Object> params = new HashMap<>();
				params.put("auditId", pp.getAuditId());
				params.put("audtiStatus", pp.getAuditStatus());
				params.put("updateUserId", pp.getUpdateUserId());
				params.put("updateTime", pp.getUpdateTime());
				params.put("memberId", pp.getMemberId());
				params.put("actPrice", pp.getActPrice());
				batchValues[i] = params;
			}
			baseDao.batchUpdate("PromProdinfo.updatePromProdInfo", batchValues);
			baseDao.batchUpdate("PromProdinfo.updatePromReqAudit", batchValues);
			return len;
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Transactional
	@Override
	public Integer addProducts(Map<String, Object> paramMap) {

		List<PromProdInfoDTO> promList = (List<PromProdInfoDTO>) paramMap.get("productList");
		int len = promList.size();

		// 添加活动以及活动商品审核记录
		for (int i = 0; i < len; i++) {
			PromProdInfoDTO prom = promList.get(i);

			// 防重复判断该商品是否参加了其他活动
			Map<String, Object> repeatMap = new HashMap<String, Object>();
			repeatMap.put("prodId", prom.getProdId());
			repeatMap.put("supplierId", prom.getSupplierId());
			repeatMap.put("actId", prom.getActId());
			int k = baseDao.queryForObject("PromProdinfo.queryGoddsApplyActivityRecord", repeatMap, Integer.class);
			if (k == 0) {
				if (i == 0) {
					// 添加或更新活动参加记录
					PromActSupplierRefEntity refEntity = getActSupplierRef(prom.getActId(), prom.getSupplierId());
					if (refEntity == null) {
						refEntity = new PromActSupplierRefEntity();
						refEntity.setActId(prom.getActId());
						refEntity.setSupplierId(prom.getSupplierId());
						refEntity.setStatus("1");
						refEntity.setSupplierName(prom.getSupplierName());
						refEntity.setCreateTime(new Date());
						refEntity.setCreateUserId(String.valueOf(prom.getSupplierId()));
						baseDao.persist(refEntity, Long.class);
					} else {
						// 如果状态为2是取消，需要更新成1已参加
						if ("2".equals(refEntity.getStatus())) {
							Map<String, Object> updateMap = new HashMap<String, Object>();
							updateMap.put("refStatus", "1");
							updateMap.put("id", refEntity.getId());
							baseDao.execute("PromActSupplierRef.updateStatus", updateMap);
						}
					}
				}
				// 判断某个商品是否参加了这个活动
				PromProdInfoDTO prodInfoDTO = getProdInfoRecord(prom.getProdId(), prom.getSupplierId(),
						prom.getActId());

				// 参加了活动状态为3（取消）改成待审
				if (prodInfoDTO != null) {
					if (prodInfoDTO.getAuditStatus().equals(3)) {
						Map<String, Object> cancelMap = new HashMap<String, Object>();
						cancelMap.put("auditId", prodInfoDTO.getAuditId());
						cancelMap.put("audtiStatus", "0");
						updateCancleProdInfo(cancelMap);
					}
				// 未参加活动
				} else {
					// todo:新增
					// 添加活动商品及商品审核记录
					PromReqAuditEntity auditEntity = new PromReqAuditEntity();
					auditEntity.setAuditStatus("0");
					auditEntity.setCreateTime(new Date());
					Long auditKey = baseDao.persist(auditEntity, Long.class);

					prom.setAuditId(Integer.parseInt(auditKey + ""));
					PromProdInfoEntity prodEntity = new PromProdInfoEntity();
					BeanUtils.copyProperties(prom, prodEntity);
					prodEntity.setCreateTime(new Date());
					baseDao.persist(prodEntity, Long.class);

				}
			}
		}
		return len;

	}

	/**
	 * 将取消过的审核记录状态3更新成待审核0
	 * 
	 * @param auditId
	 * @return
	 */
	private int updateCancleProdInfo(Map map) {
		return baseDao.execute("PromProdinfo.updateCancelPromReqAudit", map);
	}
	
	

	/**
	 * 获取某供应商中某商品是否参加过某活动
	 * 
	 * @param prodId
	 * @param supplierId
	 * @return
	 */
	@SuppressWarnings("unused")
	private PromProdInfoDTO getProdInfoRecord(Long prodId, Integer supplierId, Integer actId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prodId", prodId);
		map.put("supplierId", supplierId);
		map.put("actId", actId);
		return baseDao.queryForObject("PromProdinfo.queryProdInfo", map, PromProdInfoDTO.class);
	}

	/***
	 * 获取活动参加记录
	 * 
	 * @param giftId
	 * @return
	 */
	@SuppressWarnings("unused")
	private PromActSupplierRefEntity getActSupplierRef(Integer actId, Integer supplierId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actId", actId);
		map.put("supplierId", supplierId);
		return baseDao.queryForObject("PromActSupplierRef.queryRefRecord", map, PromActSupplierRefEntity.class);
	}

	@Transactional
	@Override
	public int cancelPromotionActivity(Map<String, Object> paramMap) {

		baseDao.execute("PromReqAudit.updateReqAuditStatus", paramMap);
		return baseDao.execute("PromActSupplierRef.cancelActivityStatus", paramMap);

	}

}
