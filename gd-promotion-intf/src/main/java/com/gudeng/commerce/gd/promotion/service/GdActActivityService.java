package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActProductDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GdActActivityService extends BaseService<GdActActivityDTO> {
	
	public Long insert(GdActActivityEntity entity) throws Exception;
	
	/**
	 * 查询某个具体活动的信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public GdActActivityDTO getSpecificActivityInfo(Map<String, Object> params) throws Exception;
	/**
	 * 获取活动总数(管理后台使用)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getTotalForActivityList(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取活动列表(管理后台使用)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityDTO> getActivityList(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取活动列表信息(供农速通使用)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityDTO> getActivityInfo(Map<String, Object> params) throws Exception ;
	
	/**
	 * 获取用户可以参加的活动列表(供订单中心使用)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityDTO> getEffectiveActivityList(Map<String, Object> params) throws Exception ;
	
	/**
	 * 获取活动相关的买家或者卖家列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GdActActivityUserRuleDTO> getActivityUserList(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取序列值，来产生活动编号
	 * @return
	 * @throws Exception
	 */
	public String getSequence() throws Exception ;

	/**
	 * 添加佣金规则补贴规则等
	 * @param dto
	 */
	public void addRuleComm(GdActActivityCommDTO dto);
	
	/**
	 * 查询对应活动的所有ruleComm
	 * @param actId
	 * @return
	 */
	public List<GdActActivityCommDTO> searchRuleCommForAct(Integer actId);

	/**
	 * 查找所有活动商品
	 * @param map
	 * @return
	 */
	public List<ActProductDTO> getActivityProductInfo(Map<String, Object> map);
	
	/**
	 * 获取条件下的活动信息
	 * 用于判断是否存在相同商品用户的活动 同类型
	 * @param map
	 * @return
	 */
	public List<GdActActivityDTO> getExistList(Map<String, Object> map);
	
}