package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GdActActivityToolService extends BaseToolService<GdActActivityDTO> {
	public Long insert(GdActActivityEntity entity) throws Exception;
	
	/**
	 * 查询某个具体活动的信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public GdActActivityDTO getSpecificActivityInfo(Map<String, Object> params) throws Exception;
	/**
	 * 获取活动总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getTotalForActivityList(Map<String, Object> params) throws Exception;
	/**
	 * 获取活动列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityDTO> getActivityList(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取活动物流配送模式
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityDistributionModeDTO> getDistributionModeByActivityId(Map<String, Object> params) throws Exception;
	
	public int getTotalForActMemberList(Map<String, Object> params) throws Exception;
	/**
	 * 获取活动参与方列表(即买家列表或者卖家列表)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityUserRuleDTO> getActivityMemberList(Map<String, Object> params) throws Exception;
	
	/**
	 * 验证活动名是否存在
	 * @param validName
	 * @return
	 */
	public boolean isExistsActivityName(Map<String,Object> paraMap) throws Exception;
	
	/**
	 * 获取序列
	 * @param validName
	 * @return
	 */
	public String getSequence() throws Exception ;
	
	/**
	 * 添加佣金规则补贴规则等
	 * @param dto
	 */
	public void addRuleComm(GdActActivityCommDTO dto) throws Exception;
	
	/**
	 * 查询对应活动的所有ruleComm
	 * @param actId
	 * @return
	 */
	public List<GdActActivityCommDTO> searchRuleCommForAct(Integer actId) throws Exception;
	
	/**
	 * 获取条件下的活动信息
	 * 用于判断是否存在相同商品用户的活动 同类型
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityDTO> getExistList(Map<String, Object> params) throws Exception;
}