package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketImageDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditWithMemInfoDTO;
import com.gudeng.commerce.gd.order.entity.SubAuditEntity;

/**
 * @description: TODO - 补贴查询和补贴状态相关操作
 * @author lmzhang@gdeng.cn
 *
 */
public interface SubAuditService {
	
	/**
	 * 提供“(订单)补贴”分页查询功能
	 * @param map, 需要查询的参数集
	 * @return List<SubAuditService>
	 * @throws Exception
	 */
	public List<SubAuditDTO> getBySearch(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取查询结果总数
	 * @param map, 需要查询的参数集
	 * @return int
	 * @throws Exception
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 补贴状态和备注信息更新
	 * (补贴状态： 1-待补贴； 2系统驳回； 3-审核通过； 4-审核不通过)
	 * @param map, 必须设置值(key)：auditId, subStatus, subComment,updateUserId,orderNo
	 * 				如果设置状态为4“审核不通过”，map中还应该有驳回原因(rejectReason)
	 * @return 成功返回"SUCCESS",否则返回逻辑错误的原因,比如在修改状态时原来的状态已经改变
	 * @throws Exception
	 */
	public String updateSubStatusById(Map<String, Object> map) throws Exception;
	
	/**
	 * 
	* @Title: getSubAuditById 
	* @Description: TODO(通过sub_audit.auditId获取一条记录) 
	* @param  map, 必须设置(key): auditId(Integer)
	* @return SubAuditDTO 返回类型 
	* @throws Exception
	 */
	public SubAuditEntity getSubAuditById(Integer auditId) throws Exception;
	
	
	/**
	 * 
	* @Title: getSubAuditDTOByOrderNo
	* @Description: TODO(通过订单号sub_audit.orderNo获取记录) 
	* @param 		map, 需要设置(key): orderNo(Long)    
	* @return 		List<SubAuditDTO>
	* @throws
	 */
	public List<SubAuditDTO> getSubAuditDTOByOrderNo(Map<String, Long> map) throws Exception;
	
	/**
	 * 通过多个id获取记录
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public List<SubAuditEntity> getSubAuditByIds(List<Integer> idList) throws Exception;
	
	/**
	 * 批量处理："待处理" ==> "已补贴"
	 * @param idList
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String batchUpdateSubStatusAsPass(List<Integer> idList, Map<String, Object> params) throws Exception;
	
	/**
	 * 返回当前剩余补贴总额数
	 * @author lmzhang@gdeng.cn
	 * @return 
	 */
	public String getCurrentTotalAmount() throws Exception;
	
	/**
	 * 查询订单补贴总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getSubListTotal(Map<String, Object> map) throws Exception;

	/**
	 * 查询订单补贴列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SubAuditDTO> getSubList(Map<String, Object> map) throws Exception;
	
	/**
	 * @Title: addSubAudit
	 * @Description: TODO(新增补贴审核记录)
	 * @param subAudit
	 * @throws ServiceException
	 */
	public void addSubAudit(SubAuditDTO subAudit) throws ServiceException;
	
	/**
	 * 
	 * @ToDo: 根据auditId查询补贴相关信息
	 * @author: lmzhang@gdeng.cn
	 * @param auditIdList
	 * @return
	 * @throws Exception
	 */
	public List<SubAuditWithMemInfoDTO> getSubAuditInfo(List<Integer> auditIdList) throws Exception;
	
	/**
	 * 
	 * @Title: getOutmarketImage
	 * @Description: TODO (根据orderNo查询车辆出岗时的车辆照片，和车牌好。一个订单号对应一个carNumberImage字段,和一个车牌号，其中carNumberImage可以是多张照片由"|"分割，最多4张)
	 * @param orderNo 订单号
	 * @return OrderOutmarketImageDTO
	 */
	public OrderOutmarketImageDTO getOutmarketImage(Long orderNo) throws Exception;
}
