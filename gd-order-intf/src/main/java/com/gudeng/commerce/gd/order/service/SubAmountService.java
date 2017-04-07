package com.gudeng.commerce.gd.order.service;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.SubAmountDTO;
import com.gudeng.commerce.gd.order.entity.SubAmountEntity;

public interface SubAmountService {
	/**
	 * 
	 * @ToDo: 为某市场增加新的补贴额记录（第二期暂时未用到）
	 * 		***特别注意:同一个市场同一时间只可能有一个补贴额在使用，调用该方法后会把原来的所有记录(属于该记录的)更改为不可用状态，然后再插入新的记录
	 * @author: lmzhang@gdeng.cn
	 * @param subAmountEntity
	 * @return
	 * @throws Exception
	 */
	public int add(SubAmountEntity subAmountEntity) throws Exception;
	
	/**
	 * 
	 * @ToDo: 获取属于该市场的补贴额记录
	 * @author: lmzhang@gdeng.cn
	 * @param marketId
	 * @return
	 * @throws Exception
	 */
	public SubAmountEntity getByMarketId(Integer marketId) throws Exception;
	
	//public int updateByMarketId(Integer marketId) throws Exception;
	
	/**
	 * @ToDo: 减去该市场的补贴总额(sub_amount.subAmountBal),即更新该市场的剩余补贴额
	 * @author: lmzhang@gdeng.cn
	 * @param amount: 补贴额
	 * @param marketId: 市场ID
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public int subductAmount(Double amount, Integer marketId, String updateUserId) throws Exception;
	
	/**
	 * 
	 * @Title: subductAmountBySubRuleId
	 * @Description: TODO (根据subRuleId减去总金额)
	 * @param @param subRuleId
	 * @param @throws Exception
	 * @return int
	 */
	public int subductAmountBySubRuleId(Long subRuleId, Double amount, String updateUserId) throws Exception;
	
	/**
	 * 
	 * @ToDo: 减去该市场的补贴总额(sub_amount.subAmountBal),即更新该市场的剩余补贴额
	 * @author: lmzhang@gdeng.cn
	 * @param paramMap: map数组，其中每个map应该有key: marketId(属于哪个市场), updateUserId, amount
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int[] subductAmountBatch(Map[] paramMap) throws Exception;
	
	/**
	 * @ToDo: 设置某个市场的补贴额无效
	 * @author: lmzhang@gdeng.cn
	 * @param marketId: 市场Id
	 * @return
	 */
	public int setUnavailableByMarketId(Integer marketId, String updateUserId) throws Exception;
	
	
	
	/**
	 * @ToDo: 查出所有有效的剩余余额 
	 * @author: lmzhang@gdeng.cn
	 * @return: key=marketId; value=剩余补贴额
	 * @throws Exception
	 */
	@Deprecated
	public Map<Integer, Double> getAllBalAmount() throws Exception;
	
	/**
	 * 
	 * @Title: getAllBalAmountBy
	 * @Description: TODO (查询每个规则剩余补贴额,用作在补贴前判断是否超出总补贴额)
	 * @param Exception
	 * @return Map<Integer,Double>, key=SubRuleId; value=剩余补贴额
	 */
	public Map<Long, Double> getAllBalAmountBySubRuleId() throws Exception;
	
	/**
	 * @Title: updateSubAmount
	 * @Description: TODO(更新补贴金额)
	 * @param subAmount
	 * @return
	 * @throws ServiceException
	 */
	public int updateSubAmount(SubAmountDTO subAmount) throws ServiceException;
	
}
