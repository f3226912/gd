package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.order.dto.AuditLogDTO;
import com.gudeng.commerce.gd.order.entity.AuditLogEntity;

public interface AuditLogToolService {
	/**
	 * 通过订单号获取AuditLog(审核记录)列表
	 * @param map, orderNo(Long) required, type(String)表示类型，1：补贴审核 2：提现审核 3订单审核. type如果没有，表示查出所有类型
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
	 * 根据订单号查询补贴订单的系统审核结果(其中type=='1' and auditName='SYS')
	 * @return
	 * @author lmzhang@gdeng.cn
	 * @throws Exception
	 */
	public List<AuditLogEntity> getSYSAuditLogByOrderNo(Long orderNo) throws Exception;
}
