package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;
import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;

/**
 * 活动service
 */
public interface ActivityToolService {

    /**
     * 根据活动id查询活动信息
     *
     * @param activityId
     */
    public ActActivityBaseinfoDTO getActivityInfo(String activityId) throws Exception;

	/**
	 * 更新礼品信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateGiftBaseInfo(Map<String, Object> params) throws Exception;

	/**
	 * 获取礼品信息
	 * @param id
	 * @return
	 */
	public ActGiftBaseinfoDTO getByGiftBaseinfoId(Integer id) throws Exception ;

    /**
     * 查询用户-活动 关联信息
     *
     * @param activityId
     * @param userid
     * @return
     */
    public ActReUserActivityDto getReUserActivityInfo(String activityId, String userid) throws Exception;

    /**
     * 更新用户-活动 关联信息
     *
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
     * 更新活动浏览次数
     *
     * @param map
     * @return
     * @throws Exception
     * @author lidong
     */
    public int updatePV(Map<String, Object> map) throws Exception;

    /**
     * 新增积分获取记录
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public Long insertReActivityScoreRecoreInfo(ActActivityScoreRecordEntity entity) throws Exception;

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
	 * 获取用户活动积分记录数量
	 * @return
	 * @throws Exception
	 */
	public Integer getScoreRecordTotal(Map<String, Object> condition) throws Exception;

	Integer getScoreRecordTotal2(Map<String, Object> map) throws Exception;
	/**
	 * 处理所有的兑换操作
	 * @return
	 * @throws Exception
	 */
	public void updateAllActivityInfo(ActGiftExchangeApplyEntity actGiftExchangeApply,
			Map<String, Object> params1, Map<String, Object> params2)
			throws Exception;


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
