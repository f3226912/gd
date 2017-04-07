package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActActivityScoreRecordDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;
import com.gudeng.commerce.gd.promotion.service.ActActivityScoreRecordService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class ActActivityScoreRecordServiceImpl implements ActActivityScoreRecordService{

	@Resource
	private BaseDao<?> baseDao;
	
	@Override
	public Long add(ActActivityScoreRecordEntity entity) {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<ActActivityScoreRecordDTO> queryPageByCondition(Map<String, Object> map) {
		System.out.println("========================"+map.get("type"));
		System.out.println("========================"+map.get("type"));
		System.out.println("========================"+map.get("type"));
		System.out.println("========================"+map.get("type"));
		return baseDao.queryForList("ActActivityScore.queryPageByCondition", map, ActActivityScoreRecordDTO.class);
	}

	@Override
	public Integer getTotal(Map<String, Object> map) {
		return baseDao.queryForObject("ActActivityScore.getTotal", map, Integer.class);
	}
	
	@Override
	public Integer update(ActActivityScoreRecordDTO dto) {
		return baseDao.execute("ActActivityScore.update", dto);
	}

	@Override
	public ActActivityScoreRecordDTO getById(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("ActActivityScore.getById", paramMap, ActActivityScoreRecordDTO.class);
	}

	@Override
	public List<ActActivityScoreRecordDTO> queryListByCondition(Map<String, Object> map) {
		return baseDao.queryForList("ActActivityScore.queryListByCondition", map, ActActivityScoreRecordDTO.class);
	}

	@Override
	@Transactional
	public Long addIntegralRecord(ActActivityScoreRecordDTO dto) {
		ActActivityScoreRecordEntity scoreRecordentity = new ActActivityScoreRecordEntity();
		scoreRecordentity.setActivityId(dto.getActivityId());
		scoreRecordentity.setUserid(dto.getUserid());
		scoreRecordentity.setScore(dto.getScore());
		scoreRecordentity.setReceiveTime(new Date());
		scoreRecordentity.setCreateTime(new Date());
		scoreRecordentity.setCreateUserId(dto.getCreateUserId());
		Long result =  baseDao.persist(scoreRecordentity, Long.class);
		
		//查找用户是否参与活动
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", dto.getActivityId());
		map.put("userid", dto.getUserid());
		ActReUserActivityDto reUserActivityDTO = baseDao.queryForObject("ReUserActivity.queryByIdentities", map, ActReUserActivityDto.class);
		if(reUserActivityDTO == null){
			//新增用户活动关联
			ActReUserActivityEntity reUserActivityEntity = new ActReUserActivityEntity();
			reUserActivityEntity.setUserid(dto.getUserid());
			reUserActivityEntity.setActivityId(dto.getActivityId());
			reUserActivityEntity.setAccount(dto.getAccount());
			reUserActivityEntity.setMobile(dto.getMobile());
			reUserActivityEntity.setScore(dto.getScore());
			
			ActActivityBaseinfoDTO activityDTO = getActivityById(dto.getActivityId());
			if(activityDTO != null){
				reUserActivityEntity.setJoinTimesLeft(activityDTO.getTimes());
			}else{
				reUserActivityEntity.setJoinTimesLeft(0);
			}
			reUserActivityEntity.setFirstJoin("1");
			baseDao.persist(reUserActivityEntity, Long.class);
		}else{
			//修改用户活动积分
			int beforeScore = reUserActivityDTO.getScore() == null ? 0 : reUserActivityDTO.getScore();
			int score = beforeScore + dto.getScore();
			map.clear();
			map.put("score", score);
			map.put("userid", reUserActivityDTO.getUserid());
			map.put("activityId", reUserActivityDTO.getActivityId());
			baseDao.execute("ReUserActivity.updateUserScore", map);
		}
		return result;
	}

	@Override
	@Transactional
	public int updateIntegralRecord(ActActivityScoreRecordDTO dto) {
		//修改前的积分记录
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", dto.getId());
		ActActivityScoreRecordDTO oldScoreRecordDTO = baseDao.queryForObject("ActActivityScore.getById", paramMap, ActActivityScoreRecordDTO.class);
		if(oldScoreRecordDTO == null){
			return -1;
		}
		
		//修改积分记录
		int result = baseDao.execute("ActActivityScore.update", dto);
		
		//修改前后为同一个活动
		if(oldScoreRecordDTO.getActivityId().toString().equals(dto.getActivityId().toString())){
			//获取用户活动积分
			ActReUserActivityDto reUserActivityDTO = getReUserActivity(dto.getActivityId(), dto.getUserid());
			//修改用户活动积分
			int differScore = dto.getScore() - oldScoreRecordDTO.getScore();
			int score = reUserActivityDTO.getScore() + differScore;
			updateActivityUserScore(reUserActivityDTO.getActivityId(), reUserActivityDTO.getUserid(), score);
		}
		else{
			ActReUserActivityDto afterReUserActivityDTO = getReUserActivity(dto.getActivityId(), dto.getUserid());
			//用户没有参与活动，新增活动记录
			if(afterReUserActivityDTO == null){
				ActReUserActivityEntity reUserActivityEntity = new ActReUserActivityEntity();
				reUserActivityEntity.setUserid(dto.getUserid());
				reUserActivityEntity.setActivityId(dto.getActivityId());
				reUserActivityEntity.setAccount(oldScoreRecordDTO.getAccount());
				reUserActivityEntity.setMobile(oldScoreRecordDTO.getMobile());
				reUserActivityEntity.setScore(0);
				
				ActActivityBaseinfoDTO activityDTO = getActivityById(dto.getActivityId());
				if(activityDTO != null){
					reUserActivityEntity.setJoinTimesLeft(activityDTO.getTimes());
				}else{
					reUserActivityEntity.setJoinTimesLeft(0);
				}
				reUserActivityEntity.setFirstJoin("1");
				baseDao.persist(reUserActivityEntity, Long.class);
			}
			
			//查找修改前的活动用户关联记录
			ActReUserActivityDto beforeReUserActivityDTO = getReUserActivity(oldScoreRecordDTO.getActivityId(), oldScoreRecordDTO.getUserid());
			
			//修改前的活动减用户积分（用户原来积分记录获得的积分）
			int beforeActScore = beforeReUserActivityDTO.getScore() - oldScoreRecordDTO.getScore();
			updateActivityUserScore(oldScoreRecordDTO.getActivityId(), oldScoreRecordDTO.getUserid(), beforeActScore);
			
			//修改后的活动加用户积分（修改的积分）
			if(afterReUserActivityDTO == null){
				afterReUserActivityDTO = getReUserActivity(dto.getActivityId(), dto.getUserid());
			}
			int afterActScore = afterReUserActivityDTO.getScore() + dto.getScore();
			updateActivityUserScore(dto.getActivityId(), dto.getUserid(), afterActScore);
		}
		return result;
	}

	@Override
	public Integer getTotal2(Map<String, Object> map) {
		return baseDao.queryForObject("ActActivityScore.getTotal2", map, Integer.class);
	}

	/**
	 * 修改活动用户积分
	 * @param activityId
	 * @param userId
	 * @param score
	 */
	private void updateActivityUserScore(int activityId, long userId, int score){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("score", score);
		map.put("userid", userId);
		map.put("activityId", activityId);
		baseDao.execute("ReUserActivity.updateUserScore", map);	
	}
	
	/**
	 * 获取用户参与活动记录
	 * @param activityId
	 * @param userId
	 * @return
	 */
	private ActReUserActivityDto getReUserActivity(int activityId, long userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		map.put("activityId", activityId);
		return baseDao.queryForObject("ReUserActivity.queryByIdentities", map, ActReUserActivityDto.class);
	}
	
	/**
	 * 活动详情
	 * @param activityId
	 * @return
	 */
	private ActActivityBaseinfoDTO getActivityById(int activityId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", activityId);
		return baseDao.queryForObject("ActActivity.getById", map, ActActivityBaseinfoDTO.class);
	}
}
