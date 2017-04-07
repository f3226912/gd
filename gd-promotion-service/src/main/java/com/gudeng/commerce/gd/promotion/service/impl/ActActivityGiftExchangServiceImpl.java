package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActGiftExchangeApplyDto;
import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;
import com.gudeng.commerce.gd.promotion.service.ActActivityGiftExchangService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class ActActivityGiftExchangServiceImpl implements ActActivityGiftExchangService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<ActGiftExchangeApplyDto> getActivityExchangeRecord(Map<String, Object> params) throws Exception {
		return baseDao.queryForList("GiftExchange.getActivityExchangeRecord", params, ActGiftExchangeApplyDto.class);
	}

	@Override
	public Long insertActivityExchangeRecord(ActGiftExchangeApplyEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<ActGiftExchangeApplyDto> queryPageByCondition(Map<String, Object> map) {
		return baseDao.queryForList("GiftExchange.queryPageByCondition", map, ActGiftExchangeApplyDto.class);
	}

	@Override
	public int getTotalCountByCondtion(Map<String, Object> map) {
		return baseDao.queryForObject("GiftExchange.getTotalCountByCondition", map, Integer.class);
	}

	@Override
	public List<ActGiftExchangeApplyDto> queryListByCondition(Map<String, Object> map) {
		return baseDao.queryForList("GiftExchange.queryListByCondition", map, ActGiftExchangeApplyDto.class);

	}

	@Override
	@Transactional
	public Long addGiftExchangeRecord(ActGiftExchangeApplyDto dto) {
		//新增兑换记录
		ActGiftExchangeApplyEntity giftExchangeRecordEntity = new ActGiftExchangeApplyEntity();
		giftExchangeRecordEntity.setActivity_id(dto.getActivity_id());
		giftExchangeRecordEntity.setGift_id(dto.getGift_id());
		giftExchangeRecordEntity.setCreateTime(new Date());
		giftExchangeRecordEntity.setStatus("1");
		giftExchangeRecordEntity.setUserid(dto.getUserid());
		giftExchangeRecordEntity.setCreateUserId(dto.getCreateUserId());
		Long result = baseDao.persist(giftExchangeRecordEntity, Long.class);
		
		//修改用户积分
		ActReUserActivityDto reUserActivityDto = new ActReUserActivityDto();
		reUserActivityDto.setScore(dto.getScoreLeft());
		reUserActivityDto.setUserid(dto.getUserid());
		reUserActivityDto.setActivityId(dto.getActivity_id());
		baseDao.execute("ReUserActivity.updateUserScore", reUserActivityDto);
		
		//修改活动礼品库存
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("amount", dto.getCostLeft());
		paramMap.put("activityId", dto.getActivity_id());
		paramMap.put("giftId", dto.getGift_id());
		baseDao.execute("ReActivityGift.updateActivityGift", paramMap);
		
		//修改礼品当前库存
		ActGiftBaseinfoDTO giftDTO = getGiftById(dto.getGift_id());
		if(giftDTO != null){
			int stockTotal = giftDTO.getStockTotal() == null ? 0 : giftDTO.getStockTotal();
			updateGiftStockTotal(giftDTO.getId(), stockTotal-1);
		}
		return result;
	}
	
	/***
	 * 获取礼品详情
	 * @param giftId
	 * @return
	 */
	private ActGiftBaseinfoDTO getGiftById(int giftId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", giftId);
		return baseDao.queryForObject("ActGift.getById", map, ActGiftBaseinfoDTO.class);
	}
	
	/**
	 * 修改礼品当前库存
	 * @param giftId
	 * @param stockTotal
	 */
	private void updateGiftStockTotal(int giftId, int stockTotal){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", giftId);
		map.put("stockTotal", stockTotal);
		baseDao.execute("ActGift.updateStockTotal", map);
	}

	@Override
	public ActGiftExchangeApplyDto getById(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("GiftExchange.getById", paramMap, ActGiftExchangeApplyDto.class);
	}

	@Override
	@Transactional
	public int updateGiftExchangeRecord(ActGiftExchangeApplyDto dto) {
		int result = baseDao.execute("GiftExchange.update", dto);
		
		//修改用户积分
		ActReUserActivityDto reUserActivityDto = new ActReUserActivityDto();
		reUserActivityDto.setScore(dto.getScoreLeft());
		reUserActivityDto.setUserid(dto.getUserid());
		reUserActivityDto.setActivityId(dto.getActivity_id());
		baseDao.execute("ReUserActivity.updateUserScore", reUserActivityDto);
		
		//修改后的活动礼品库存减1
		updateReActivityGiftCost(dto.getActivity_id(), dto.getGift_id(), dto.getCostLeft());
		
		//修改后的礼品当前库存减1
		ActGiftBaseinfoDTO afterGiftDTO = getGiftById(dto.getGift_id());
		if(afterGiftDTO != null){
			int stockTotal = afterGiftDTO.getStockTotal() == null ? 0 : afterGiftDTO.getStockTotal();
			updateGiftStockTotal(dto.getGift_id(), stockTotal-1);
		}
		
		//修改前的活动礼品库存加1（归还库存）
		updateReActivityGiftCost(dto.getOldActivityId(), dto.getOldGiftId(), dto.getOldCostLeft());
		
		//修改前的礼品当前库存加1（归还库存）
		ActGiftBaseinfoDTO beforeGiftDTO = getGiftById(dto.getOldGiftId());
		if(beforeGiftDTO != null){
			int stockTotal = beforeGiftDTO.getStockTotal() == null ? 0 : beforeGiftDTO.getStockTotal();
			updateGiftStockTotal(dto.getOldGiftId(), stockTotal+1);
		}
		return result;
	}
	
	/**
	 * 修改活动礼品库存
	 * @param activityId
	 * @param giftId
	 * @param cost
	 */
	private void updateReActivityGiftCost(int activityId, int giftId, int cost){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", cost);
		map.put("activityId", activityId);
		map.put("giftId", giftId);
		baseDao.execute("ReActivityGift.updateActivityGift", map);
	}

	@Override
	@Transactional
	public int updateStatus(ActGiftExchangeApplyDto dto) {
		int result = baseDao.execute("GiftExchange.updateStatus", dto);
		
		//3:不予发放（选择不予发放，则直接返回库存）
		if("3".equals(dto.getStatus())){
			//活动礼品库存加1（归还库存）
			ActReActivitityGiftDto reActivityGiftDTO = getReActivityGift(dto.getActivity_id(), dto.getGift_id());
			if(reActivityGiftDTO != null){
				int cost = reActivityGiftDTO.getCost() == null ? 0 : reActivityGiftDTO.getCost();
				updateReActivityGiftCost(dto.getActivity_id(), dto.getGift_id(), cost+1);
			}
			
			//礼品库存加1（归还库存）
			ActGiftBaseinfoDTO giftDTO = getGiftById(dto.getGift_id());
			if(giftDTO != null){
				int stockTotal = giftDTO.getStockTotal() == null ? 0 : giftDTO.getStockTotal();
				updateGiftStockTotal(dto.getGift_id(), stockTotal+1);
			}
		}
		return result;
	}
	
	/**
	 * 获取某个活动的某个礼品
	 * @param activityId
	 * @param giftId
	 * @return
	 */
	private ActReActivitityGiftDto getReActivityGift(int activityId, int giftId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("giftId", giftId);
		return baseDao.queryForObject("ReActivityGift.getActivityGift", map, ActReActivitityGiftDto.class);
	}

	@Override
	public int hasExchangeGiftCount(Integer activityId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("userId", userId);
		int count = baseDao.queryForObject("GiftExchange.getUserExchangeCount", map, Integer.class);
		return count;
	}
	@Override
	public int getUserExchangeScore(String activityId, String userid)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("userid", userid);
		int count = baseDao.queryForObject("GiftExchange.getUserExchangeScore", map, Integer.class);
		return count;
	}

}
