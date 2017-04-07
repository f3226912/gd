package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO.ACC_TRANS_TRADE_TYPE;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO.PE_TYPE;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO.CASH_REQUEST_STATUS;
import com.gudeng.commerce.gd.order.service.CashRequestService;
import com.gudeng.commerce.gd.order.util.MathUtil;

@Service
public class CashRequestServiceImpl implements CashRequestService {
	private static Object lock = new Object();
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private AccInfoServiceImpl accInfoServiceImpl;

	@Autowired
	private AccTransInfoServiceImpl accTransInfoServiceImpl;

	@Override
	public List<CashRequestDTO> getCashRequestInfo(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("CashRequest.getListByCondition", map,
				CashRequestDTO.class);
	}

	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) baseDao.queryForObject("CashRequest.getTatal", map,
				Integer.class);
	}

	@Override
	public List<CashRequestDTO> getAccountFlowInfo(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList(
				"CashRequest.getAccountFlowListByCondition", map,
				CashRequestDTO.class);
	}

	@Override
	public Integer getAccountFolwTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer) baseDao.queryForObject(
				"CashRequest.getAccountFlowTatal", map, Integer.class);
	}

	@Override
	public CashRequestDTO getCashRequestByCashReqId(String cashReqId)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("cashReqId", cashReqId);
		return (CashRequestDTO) baseDao.queryForObject(
				"CashRequest.getCashRequestByCashReqId", map,
				CashRequestDTO.class);
	}

	@Override
	public int insertAccTransinfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("CashRequest.insertAccTransinfo", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashRequestDTO getAccountAmtInfo(String accId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("accId", accId);
		return (CashRequestDTO) baseDao.queryForObject(
				"CashRequest.getAccountAmtInfo", map, CashRequestDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateAccountAmtInfo(Map<String, Object> map) throws Exception {
		return (int) baseDao.execute("CashRequest.updateAccountAmtInfo", map);
	}

	@Override
	public int updateCashRequestStatus(String cashRequestId,
			String accountflowId, String updateUserId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("cashReqId", cashRequestId);
		map.put("accountflowId", accountflowId);
		map.put("updateUserId", updateUserId);
		return (int) baseDao
				.execute("CashRequest.updateCashRequestStatus", map);
	}

	@Override
	public List<Long> getStatementIdList() throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("CashRequest.getStatementIdList", null,
				Long.class);
	}

	@Override
	@Transactional
	public String flowDisposeAmt(Map<String, Object> map) throws Exception {

		// 获取账号金额信息
		String accId = map.get("accId").toString();
		if (accId == null || "".equals(accId)) {
			return "error";
		}
		CashRequestDTO cashRequestDTO = getAccountAmtInfo(accId);
		// 获取这笔流水的交易金额
		double cashAmount = Double
				.parseDouble(map.get("cashAmount").toString());

		int success = -1;
		if (cashRequestDTO != null) {
			// 获取账号总金额
			double accountTotal = cashRequestDTO.getAccountTotal();
			// 获取账号的冻结金额
			double accountBlock = cashRequestDTO.getAccountBlock();

			// 总账号账号金额-交易金额
			accountTotal = MathUtil.sub(accountTotal, cashAmount);

			map.put("accountTotal", accountTotal);

			// 总账号冻结金额-交易金额
			accountBlock = MathUtil.sub(accountBlock, cashAmount);

			Map<String, Object> map2 = new HashMap<String, Object>();

			// 存入更新需要的字段
			map2.put("accId", map.get("accId").toString());
			map2.put("accountTotal", accountTotal);
			map2.put("accountBlock", accountBlock);

			// 更新金额
			if (accountTotal >= 0 && accountBlock >= 0) {
				success = updateAccountAmtInfo(map2);
			}
		}

		// 如果更新的金额成功，则插入流水
		if (success >= 0) {
			// 修改提现申请的状态为1（表示已经提现）并录入流水号
			String cashRequestId = map.get("cashRequestId").toString();
			String accountflowId = map.get("accountflowId").toString();
			String updateUserId = map.get("updateUserId").toString();
			updateCashRequestStatus(cashRequestId, accountflowId, updateUserId);

			// 插入账户交易流水信息记录
			insertAccTransinfo(map);

			return "success";
		}
		return "error";

	}

	@Override
	public int add(CashRequestDTO cashRequestDTO) {
		return this.baseDao.execute("CashRequest.add", cashRequestDTO);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getByMemberId(Map map) {

		return this.baseDao.queryForList("CashRequest.getByMemberId", map,
				CashRequestDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Transactional(isolation=Isolation.SERIALIZABLE)
	@Override
	public synchronized void withdraw(CashRequestDTO cashRequestDTO,
			AccInfoDTO accInfoDTOParam) throws Exception {

			Double withdrawAmount = cashRequestDTO.getCashAmount();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", cashRequestDTO.getMemberId());
			AccInfoDTO accInfoDTO = (AccInfoDTO) this.baseDao.queryForObject(
					"accInfo.getWalletIndexById", map, AccInfoDTO.class);
			Double totalFrozenAmount = accInfoDTO.getBalblock()
					+ withdrawAmount;

			Double available = accInfoDTO.getBalAvailable();


			accInfoDTO.setBalblock(totalFrozenAmount);
			accInfoDTO.setBalAvailable(available - withdrawAmount);
			this.baseDao.execute("accInfo.updateAccInfo", accInfoDTO);

			// 添加提现记录
			cashRequestDTO.setIdCard(accInfoDTO.getIdCard());
			cashRequestDTO.setReqUid(accInfoDTO.getAccId());
			cashRequestDTO.setStatus(CASH_REQUEST_STATUS.WAITTING_WITHDRAW
					.getValue());
			cashRequestDTO.setTransNo(null);
			cashRequestDTO.setBankCardNo(cashRequestDTO.getBankCardNo());
			// add(cashRequestDTO);
			this.baseDao.execute("CashRequest.add", cashRequestDTO);
	}

	@Override
	public Long getStatementId(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (Long) baseDao.queryForObject("CashRequest.getStatementId", map,
				Long.class);
	}

}
