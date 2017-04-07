package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;

public interface ActReUserActivityService {

	/**
	 * 查询用户-活动 关联信息
	 *
	 * @param activityId
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ActReUserActivityDto getReUserActivityInfo(String activityId, String userid) throws Exception;

	/**
	 * 更新用户-活动 关联信息
	 *
	 * @param activityId
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int updateReUserActivityInfo(ActReUserActivityDto actReUserActivityDto) throws Exception;

	/**
	 * 初始化用户-活动关系数据
	 *
	 * @param actReUserActivityEntity
	 * @return
	 * @throws Exception
	 */
	public Long insertReUserActivityInfo(ActReUserActivityEntity actReUserActivityEntity) throws Exception;

	/**
	 * 查询当前用户的活动信息及排名情况
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ActReUserActivityDto getUserActivityByUserid(Map<String, Object> params) throws Exception;

	/**
	 * 查询当前活动的所有用户信息及排名情况
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<ActReUserActivityDto> getWechatUserInfoByUserid(Map<String, Object> params) throws Exception;

	/**
	 * 获取用户总积分
	 *
	 * @param userId 用户id
	 * @param actId  活动id
	 * @return
	 * @throws Exception
	 */
	public Integer getUserScore(Integer userId, Integer actId) throws Exception;

}
