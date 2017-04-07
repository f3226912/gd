package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;



/**
 * 分页查询出提现申请信息服务
 * @author xiaojun
 */
public interface CashRequestService {
	/**
	 * 分页查询出提现申请表的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CashRequestDTO> getCashRequestInfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询出总的记录数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getTotal(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 获取账号流水信息查询
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
	 * 查询总账户的账户余额与冻结余额
	 * @param accId
	 * @return
	 * @throws Exception
	 */
	public CashRequestDTO getAccountAmtInfo(String accId) throws Exception;
	
	/**
	 * 更新账户信息金额
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateAccountAmtInfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改提现申请的状态,需要把流水号插入进去
	 * @param cashReqId
	 * @return
	 * @throws Exception
	 */
	public int updateCashRequestStatus(String cashReqId,String accountflowId,String updateUserId) throws Exception;
	
	/**
	 * 查询出账号交易流水的所有流水号
	 * @return
	 * @throws Exception
	 */
	public List<Long> getStatementIdList() throws Exception;
	
	/**
	 * 流水与总账户金额处理服务，方便事务管理
	 * @return
	 */
	public String flowDisposeAmt(Map<String, Object> map) throws Exception;
	
	
	
	
	
	
	
	public int	 add(CashRequestDTO cashRequestDTO) ;
	public  List<CashRequestDTO>	 getByMemberId(Map map) ;
	
	/**
	 * 提现
	 * @param cashRequestDTO
	 * @param memberBaseinfoDTO
	 * @throws Exception
	 */
	public void withdraw(CashRequestDTO cashRequestDTO,AccInfoDTO accInfoDTO) throws Exception;

	/**
	 * 根据流水号查询是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long getStatementId(Map<String, Object> map) throws Exception;
}
