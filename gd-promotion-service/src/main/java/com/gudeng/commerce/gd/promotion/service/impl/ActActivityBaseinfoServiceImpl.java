package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;
import com.gudeng.commerce.gd.promotion.dto.ActStatisticDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.entity.ActReActivitityGiftEntity;
import com.gudeng.commerce.gd.promotion.service.ActActivityBaseinfoService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class ActActivityBaseinfoServiceImpl implements ActActivityBaseinfoService{

	@Resource
	private BaseDao<?> baseDao;

	@Override
	public Long add(ActActivityBaseinfoEntity entity) {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<ActActivityBaseinfoDTO> queryPageByCondition(Map<String, Object> map) {
		return baseDao.queryForList("ActActivity.queryPageByCondition", map, ActActivityBaseinfoDTO.class);
	}

	@Override
	public Integer getTotalCountByCondition(Map<String, Object> map) {
		return baseDao.queryForObject("ActActivity.getTotalCountByCondition", map, Integer.class);
	}

	@Override
	public ActActivityBaseinfoDTO getById(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("ActActivity.getById", paramMap, ActActivityBaseinfoDTO.class);
	}

	@Override
	@Transactional
	public Long addActivity(ActActivityBaseinfoDTO dto) {
		ActActivityBaseinfoEntity actEntity = new ActActivityBaseinfoEntity();
		actEntity.setName(dto.getName());
		actEntity.setType(dto.getType());
		actEntity.setChannel(dto.getChannel());
		actEntity.setUserGroup(dto.getUserGroup());
		if(dto.getExchangeTime() == null){
			actEntity.setExchangeTime(ActActivityBaseinfoEntity.EXCHANGE_TIME_NO_LIMIT);
		}else{
			actEntity.setExchangeTime(dto.getExchangeTime());
		}
		actEntity.setEffectiveStartTime(dto.getEffectiveStartTime());
		actEntity.setEffectiveEndTime(dto.getEffectiveEndTime());
		actEntity.setLaunch("2");//等待
		actEntity.setTimes(dto.getTimes());
		actEntity.setCreateUserId(dto.getCreateUserId());
		actEntity.setCreateTime(new Date());
		actEntity.setPv(0);
		Long activityId = baseDao.persist(actEntity, Long.class);

		List<ActReActivitityGiftDto> reActivityGiftList = dto.getReActivityGiftList();
		if(reActivityGiftList != null){
			for(ActReActivitityGiftDto reActivityGift : reActivityGiftList){
				//保存礼品与活动关系
				ActReActivitityGiftEntity reActivityGiftEntity = new ActReActivitityGiftEntity();
				reActivityGiftEntity.setCost(reActivityGift.getCost());
				reActivityGiftEntity.setExchangeScore(reActivityGift.getExchangeScore());
				reActivityGiftEntity.setGiftId(reActivityGift.getGiftId());
				reActivityGiftEntity.setActivityId(activityId.intValue());
				baseDao.persist(reActivityGiftEntity, Long.class);
				
				//修改礼品可用库存
				updateGiftAvailableStock(reActivityGift);
			}
		}
		return activityId;
	}
	
	/**
	 * 修改礼品可用库存
	 * @param reActivityGift
	 */
	private void updateGiftAvailableStock(ActReActivitityGiftDto reActivityGift){
		Map<String, Object> giftAvailableMap = new HashMap<String, Object>();
		giftAvailableMap.put("id", reActivityGift.getGiftId());
		ActGiftBaseinfoDTO giftDTO = baseDao.queryForObject("ActGift.getById", giftAvailableMap, ActGiftBaseinfoDTO.class);
		if(giftDTO != null){
			int curAvailabelStock = giftDTO.getStockAvailable() == null ? 0 : giftDTO.getStockAvailable();
			int newAvailableStock = curAvailabelStock - reActivityGift.getCost();
			giftDTO.setStockAvailable(newAvailableStock);
			baseDao.execute("ActGift.updateGiftAvailableStock", giftDTO);
		}
	}

	@Override
	public int delete(ActActivityBaseinfoDTO dto) {
		return baseDao.execute("ActActivity.updateIsDeleted", dto);
	}

	@Override
	@Transactional
	public int updateActivity(ActActivityBaseinfoDTO dto) {
		if(dto.getExchangeTime() == null){
			dto.setExchangeTime(ActActivityBaseinfoEntity.EXCHANGE_TIME_NO_LIMIT);
		}
		int result = baseDao.execute("ActActivity.update", dto);
		
		List<ActReActivitityGiftDto> reActivityGiftList = dto.getReActivityGiftList();
		if(reActivityGiftList == null){
			return result;
		}
		
		for(ActReActivitityGiftDto reActivityGiftDto : reActivityGiftList){
			//修改活动礼品预算和兑换积分
			baseDao.execute("ReActivityGift.update", reActivityGiftDto);
			
			//修改礼品可用库存
			reActivityGiftDto.setCost(reActivityGiftDto.getDiffCost());
			updateGiftAvailableStock(reActivityGiftDto);
		}
		return result;
	}

	@Override
	public int updateLaunch(ActActivityBaseinfoDTO dto) {
		return baseDao.execute("ActActivity.updateLaunch", dto);
	}

	@Override
	public List<ActActivityBaseinfoDTO> queryListByCondition(Map<String, Object> map) {
		return baseDao.queryForList("ActActivity.queryListByCondition", map, ActActivityBaseinfoDTO.class);
	}

	@Override
	public List<ActActivityBaseinfoDTO> queryActivityStatisticPage(Map<String, Object> map) {
		return baseDao.queryForList("ActActivity.queryActivityStatisticPage", map, ActActivityBaseinfoDTO.class);
	}

	@Override
	public Integer getActivityStatisticTotalCount(Map<String, Object> map) {
		return baseDao.queryForObject("ActActivity.getActivityStatisticTotalCount", map, Integer.class);

	}

	@Override
	public List<ActActivityBaseinfoDTO> queryActStatisticListByCondition(Map<String, Object> map) {
		return baseDao.queryForList("ActActivity.queryActStatisticListByCondition", map, ActActivityBaseinfoDTO.class);
	}

    @Override
    public int updatePV(Map<String, Object> map) throws Exception {
        return baseDao.execute("ActActivity.updatePV", map);
    }

	@Override
	public Integer getStatisticTotalCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("ActActivity.getStatisticTotalCount", map, Integer.class);
	}

	@Override
	public List<ActStatisticDto> queryStatisticPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseDao.queryForList("ActActivity.queryStatisticPage", map, ActStatisticDto.class);
	}

	@Override
	public List<ActStatisticDto> queryExportDate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseDao.queryForList("ActActivity.queryExportDate", map, ActStatisticDto.class);
	}

}
