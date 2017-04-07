package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.admin.dto.SubAuditQueryBean;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketImageDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.entity.SubAuditEntity;

public interface SubAuditToolService {

	/**
	 * 提供“(订单)补贴”分页查询功能
	 * 
	 * @param map
	 *            , 需要查询的参数集
	 * @return List<SubAuditService>
	 * @throws Exception
	 */
	public List<SubAuditDTO> getBySearch(Map<String, Object> map)
			throws Exception;

	/**
	 * 获取查询结果总数
	 * 
	 * @param map
	 *            , 需要查询的参数集
	 * @return int
	 * @throws Exception
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	
	
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
	 * 补贴状态和备注信息更新(手动)
	 * (补贴状态： 1-待补贴； 2系统驳回； 3-已补贴； 4-不予补贴)
	 * @param map, 必须设置值(key)：auditId, subStatus, subComment,updateUserId,orderNo
	 * 				如果设置状态为4“审核不通过”，map中还应该有驳回原因(rejectReason)
	 * @return int
	 * @throws Exception
	 */
	public String updateSubStatusById(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 
	 * @Title: batchUpdateSubStatusAsPass
	 * @Description: TODO (批量审批通过)
	 * @param idList
	 * @param 
	 * @param 
	 * @return String
	 */
	public String batchUpdateSubStatusAsPass(List<Integer> idList, Map<String, Object> params) throws Exception;
	
	/**
	 * 
	 * @Title: getOutmarketImage
	 * @Description: TODO (根据orderNo查询车辆出岗时的车辆照片，和车牌好。一个订单号对应一个carNumberImage字段,和一个车牌号，其中carNumberImage可以是多张照片由"|"分割，最多4张)
	 * @param orderNo
	 * @param 
	 * @return OrderOutmarketImageDTO
	 */
	public OrderOutmarketImageDTO getOutmarketImage(Long orderNo) throws Exception;
	
	
	
	
	
	
	/**
	 * 将参数实例转换成map，并返回
	 * @param SubAuditQueryBean sqb, 是参数实例
	 */
	public Map<String, Object> getParamsMap(SubAuditQueryBean sqb) throws Exception;
	
	/**
	 * 翻译订单状态
	 */
	public OrderBaseinfoDTO convertorOfUI(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception;
	
	
	
}
