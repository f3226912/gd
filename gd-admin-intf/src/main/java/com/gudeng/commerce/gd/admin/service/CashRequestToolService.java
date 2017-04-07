package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.CashRequestDTO;


/**
 * 提现信息查询服务
 * @author xiaojun
 */
public interface CashRequestToolService {
	/**
	 * 分页查询出提现申请表的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CashRequestDTO> getCashRequestInfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询出总记录数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 分页查询账号流水信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CashRequestDTO> getAccountFlowInfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询出账号流水总的记录数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getAccountFolwTotal(Map<String, Object> map) throws Exception;

	/**
	 * 根据提现申请号查询单个申请详情
	 * @param cashReqId
	 * @return
	 * @throws Exception
	 */
	public CashRequestDTO getCashRequestByCashReqId(String cashReqId) throws Exception;
	
	/**
	 * 插入账号信息
	 * @return
	 */
	public int insertAccTransinfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 流水与总账户金额处理服务，方便事务管理
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String flowDisposeAmt(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询出账号交易流水的所有流水号
	 * @return
	 * @throws Exception
	 */
	public List<Long> getStatementIdList() throws Exception;
	
	
	/**
	 * 根据流水号查询是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long getStatementId(Map<String, Object> map) throws Exception;
}
