package com.gudeng.commerce.gd.order.service;

import java.util.List;

import com.gudeng.commerce.gd.order.dto.AuditLogDTO;
import com.gudeng.commerce.gd.order.entity.AuditLogEntity;

/**
 * 
 * @description: TODO - 审核日志服务类
 * @Classname: 
 * @author lmzhang@gdeng.cn
 *
 */
public interface AuditLogService {
	
	/**
	 * 
	* @Title: addAuditLog
	* @Description: TODO(通过DTO对象插入一条记录到表"audit_log") 
	* @param @param AuditLogDTO 类型的实例
	* @throws
	 */
	public int addAuditLog(AuditLogEntity al) throws Exception;
	
	/**
	 * 通过订单号获取AuditLog(审核记录)列表
	 * @param map, orderNo(Long) required, type(String)表示类型，1：补贴审核 2：提现审核 3订单审核. 
	 * @return List<AuditLogDTO>
	 * @throws Exception
	 */
	public List<AuditLogDTO> getListByOrderNo(Long orderNo, String type) throws Exception;
	
	/**
	 * 添加
	 * 
	 * @param OrderBaseinfoEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(AuditLogEntity obj) throws Exception;
	
	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<AuditLogDTO> getListByOrderNo(Long orderNo) throws Exception;
	
	/**
	 * 根据orderNo查询系统审核补贴结果(auditTime DESC)
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @author lmzhang@gdeng.cn
	 */
	public List<AuditLogEntity> getSYSAuditLogByOrderNo(Long orderNo) throws Exception;  
}
