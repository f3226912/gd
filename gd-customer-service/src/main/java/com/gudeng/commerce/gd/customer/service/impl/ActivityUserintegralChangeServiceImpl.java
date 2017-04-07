package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ActivityUserIntegralChangeDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityUserIntegralChangeEntity;
import com.gudeng.commerce.gd.customer.service.ActivityUserIntegralChangeService;
import com.gudeng.commerce.gd.customer.entity.ActivityUserintegral;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 *
 */
public class ActivityUserintegralChangeServiceImpl implements ActivityUserIntegralChangeService {
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private BaseDao<ActivityUserintegral> userIntegralDao;

	@Override
	public Long persist(ActivityUserIntegralChangeEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("ActivityUserintegralChange.deleteById", map);
	}
	
	@Override
	@Transactional
	public Integer insert(ActivityUserIntegralChangeDTO dto) throws Exception 
	{
		int result = 0;

		int memberId = dto.getMemberId();
		String mobile = dto.getMobile();

		String type = dto.getIntegralType();
		int integral = dto.getIntegral();

		//变更用户活动积分
		
		//根据memberId查询用户是否产生过活动积分
		Map<String,Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("status", "1");
		ActivityUserintegral userIntegral = userIntegralDao.queryForObject("ActivityUserintegral.queryByCondition", params, ActivityUserintegral.class);
		if(userIntegral == null)
		{
			//无用户活动积分记录存在，则新增一条数据
			userIntegral = new ActivityUserintegral();
			userIntegral.setMemberId(memberId);
			userIntegral.setActivityType(dto.getActivityType());
			userIntegral.setCreateTime(new Date());
			userIntegral.setStatus("1");//正常
			//增加积分
			if("1".equals(type))
			{
				userIntegral.setDoIntegral(integral);
				userIntegral.setTotalIntegral(integral);
			}
			//扣减积分
			else if("2".equals(type))
			{
				// 用户无活动积分，该变更记录无法完成
				return -1;
			}

			//保存到数据库
			result = userIntegralDao.persist(userIntegral).intValue();
		}else
		{
			//有用户活动积分记录存在，则修改该用户的积分值
			int totalIntegral = userIntegral.getTotalIntegral();
			int doIntegral = userIntegral.getDoIntegral();
			//增加积分
			if("1".equals(type))
			{
				totalIntegral += integral;
				doIntegral += integral;
			}
			//扣减积分
			else if("2".equals(type))
			{
				if(doIntegral < integral)
				{
					//用户积分小于扣减积分，操作无法提交成功
					return -1;
				}
				doIntegral -= integral;
			}

			userIntegral.setTotalIntegral(totalIntegral);
			userIntegral.setDoIntegral(doIntegral);

			//修改用户活动积分
			result = userIntegralDao.execute("ActivityUserintegral.update", userIntegral);
		}

		//保存积分变更数据
		ActivityUserIntegralChangeEntity entity = new ActivityUserIntegralChangeEntity();
		entity.setMemberId(memberId);
		entity.setMobile(mobile);
		entity.setActivityId(dto.getActivityId());
		entity.setIntegralType(type);
		entity.setIntegral(integral);
		entity.setCreateUser(dto.getCreateUser());
		entity.setCreateTime(new Date());
		persist(entity);
		return result;
	}
	/**
	 * 根据 memberId和createTime 来获取当前用户在当前日期已使用的积分
	 */
	@Override
	public Integer getTotalIntegralByMemberId(Map<String, Object> param) throws Exception {

		return baseDao.queryForObject("ActivityUserintegralChange.getTotalIntegralByMemberId", param, Integer.class);
	}

	@Override
	public List<ActivityUserIntegralChangeDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ActivityUserintegralChange.queryByCondition", map,ActivityUserIntegralChangeDTO.class);
	}
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("ActivityUserintegralChange.getTotal", map, Integer.class);
	}
	
	@Override
	public ActivityUserIntegralChangeDTO getById(String id) throws Exception {
		Map<String,Object> params = new HashMap<>();
		params.put("id", id);
		return baseDao.queryForObject("ActivityUserintegralChange.queryByCondition", params, ActivityUserIntegralChangeDTO.class);
	}


	@Override
	public List<ActivityUserIntegralChangeDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ActivityUserintegralChange.queryByConditionPage", map, ActivityUserIntegralChangeDTO.class);
	}


	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int update(ActivityUserIntegralChangeDTO t) throws Exception {
		 return baseDao.execute("ActivityUserIntegralChange.update", ActivityUserIntegralChangeDTO.class);
	}

} 