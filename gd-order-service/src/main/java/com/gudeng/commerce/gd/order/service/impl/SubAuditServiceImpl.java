package com.gudeng.commerce.gd.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gudeng.commerce.gd.exception.SubAmountException;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.SubAuditException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketImageDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditWithMemInfoDTO;
import com.gudeng.commerce.gd.order.entity.SubAuditEntity;
import com.gudeng.commerce.gd.order.service.SubAmountService;
import com.gudeng.commerce.gd.order.service.SubAuditService;


/**
 * 
 * @description: TODO - (订单)补贴查询相关接口
 * @Classname: 
 * @author lmzhang@gdeng.cn
 *
 */
@SuppressWarnings(value={"rawtypes","unchecked"})
@Service
public class SubAuditServiceImpl implements SubAuditService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private SubAmountService subAmountService;
	
	@Override
	public List<SubAuditDTO> getBySearch(Map<String, Object> map) throws Exception {
		List<SubAuditDTO> result = (List<SubAuditDTO>)baseDao.queryForList("SubAudit.getBySearch", map, SubAuditDTO.class);
		return result;
	}

	
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("SubAudit.getTotal", map, Integer.class);
	}
	


	@Override
	@Transactional(rollbackForClassName={"RuntimeException","Exception"})
	public String updateSubStatusById(Map<String, Object> map) throws Exception {
		String result = null;
		String updateUserId = (String)map.get("updateUserId");

		// 获得当前数据库中 记录的信息(防止用户审批的记录状态已经被修改)
		Map<String, Object> qMap = new HashMap<String, Object>();
		qMap.put("auditId", map.get("auditId"));
		SubAuditWithMemInfoDTO sam = (SubAuditWithMemInfoDTO)baseDao.queryForObject("SubAudit.getSubAuditWithMemInfoById", map, SubAuditWithMemInfoDTO.class);
		
		Map<Long, Double> balAmountMap = this.subAmountService.getAllBalAmountBySubRuleId();	// 获取所有subRuleId的剩余余额; Map<subRuleId,amount>
		map.put("orderNo", sam.getOrderNo());
		
		
		// 修改补贴状态前的检查工作
		String preCheck = subAuditCheckForStatusChange(sam, map);
		if(null != preCheck){
			throw new SubAuditException(preCheck);
		}
		
		if(isOutOfRangOfTotalAmount(balAmountMap, sam.getSubAmount(), sam.getSubRuleId())){
			throw new SubAuditException("补贴剩余金额不足！");
		}
		// 计算出新的补贴状态
		String newStatus = generateNewSubStatus(sam.getSubStatus(), (String)map.get("flag"));
		map.put("subStatus", newStatus);
		
		// 插入log和更新补贴状态
		Map<String, Object> logMap = this.generateAudiLogMap(map);
		baseDao.execute("AuditLog.addAuditLog", logMap);
		baseDao.execute("SubAudit.updateSubStatusById", map);
		
		// *************如果是状态由（待补贴 ==> 已补贴）, 还需要给  ####用户账户算钱######
		if("1".equals(map.get("uiStauts")) && "3".equals(newStatus)){
			// 检查acc_info表中是否存在该memberId的记录，不存在则为该用户增加(所有字段均为默认值)
			//accInfoHandler(sam);														// 添加到acc_info
			
			Map<String,Object> totalAmountMap = new HashMap<String, Object>();
			totalAmountMap.put("subAmount", sam.getSubAmount());
			
			// 更新用户账户的金额(包括 账户余额，可用余额，已补贴金额，带补贴金额)
			Map[] accInfoMaps = converteToBatchValue(convertToAccInfo4BatchUpdate(sam,updateUserId));
			if(accInfoMaps.length > 0){
				//subAmountService.subductAmount(sam.getSubAmount(), sam.getMarketId(), updateUserId);
				subAmountService.subductAmountBySubRuleId(sam.getSubRuleId(), sam.getSubAmount(), updateUserId);
				
				// 插入资金流水信息(log)
				Map<Integer, BigDecimal> memberIdAndAmountMap = getBalTotalAmountWithBigDecimal(accInfoMaps);	// 获得账户总金额Map<memId, balTotal>, 因为acc_trans_info表中有个账户余额的字段
				List<SubAuditWithMemInfoDTO> samList = new ArrayList<SubAuditWithMemInfoDTO>(1);
				samList.add(sam);
				List<Map<String, Object>> accInfoTransList	= this.convertToAccTransInfo4BatchAdd(memberIdAndAmountMap, samList, updateUserId);
				Map<String, Object>[] accInfoTransMaps = converteToBatchValue(accInfoTransList);
				
				baseDao.batchUpdate("SubAudit.updateMemAmount", accInfoMaps);
				baseDao.batchUpdate("SubAudit.addAccTransInfo", accInfoTransMaps);
			}
		}
		return result;
	}
	
	
	
	@Override
	public SubAuditEntity getSubAuditById(Integer auditId) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("auditId", auditId);
		return (SubAuditEntity)baseDao.queryForObject("SubAudit.getSubAuditById", map, SubAuditEntity.class);
	}
	
	@Override
	public List<SubAuditEntity> getSubAuditByIds(List<Integer> idList) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("auditIds", idList);
		return baseDao.queryForList("SubAudit.getSubAuditByIds", map, SubAuditEntity.class);
	}
	
	
	@Override
	public List<SubAuditDTO> getSubAuditDTOByOrderNo(Map<String, Long> map) throws Exception {
		return (List<SubAuditDTO>)baseDao.queryForList("SubAudit.getSubAuditDTOByOrderNo", map, SubAuditDTO.class);
	}
	
	/**
	 * 
	 * @param idList 要更新的 auditId的集合
	 * @param params "updateUserId", "auditName"(审核人姓名)
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackForClassName={"RuntimeException","Exception"})
	public String batchUpdateSubStatusAsPass(List<Integer> idList, Map<String, Object> params) throws Exception {
		List<Map<String,Object>> toHandleList = new ArrayList<Map<String,Object>>();	// 要处理的数据集合(batch处理)
		List<Map<String,Object>> toHandleListLog = new ArrayList<Map<String,Object>>();	// 要处理的数据集合(batch处理)
		List<Long> nonHandleList = new ArrayList<Long>();								// 状态不是"待补贴"处理的订单号，不处理，但要返回用户
		String updateUserId = (String)params.get("updateUserId");
		String auditUserName = (String)params.get("auditUserName");
		
		// 根据id查询记录
		Map<String, Object> map = new HashMap<>();
		map.put("auditIds", idList);
		List<SubAuditWithMemInfoDTO> baseList = baseDao.queryForList("SubAudit.getSubAuditWithMemInfoByIds", map, SubAuditWithMemInfoDTO.class);
		//Map<Integer, Double> balAmountMap = this.subAmountService.getAllBalAmount();	// 获取所有市场的剩余余额
		Map<Long, Double> balAmountMap = this.subAmountService.getAllBalAmountBySubRuleId();	// 获取所有subRuleId的剩余余额; Map<subRuleId,amount>
		List<Long> errListOfSubAmount = getErrAmontList(baseList);
		
		// 如果补贴总金额和买家，卖家，供应商金额不符，则抛出错误
		if(errListOfSubAmount.size() > 0){
			throw new SubAuditException("订单号" + StringUtils.collectionToCommaDelimitedString(errListOfSubAmount) + "补贴金额有错误！");
		}
		
		
		// 对数据进行基本的校验，有错误就抛异常
		this.generateBatchAuditList(baseList, toHandleList, toHandleListLog, nonHandleList, updateUserId,auditUserName);
		if(nonHandleList.size() > 0){
			return "订单号" + StringUtils.collectionToCommaDelimitedString(nonHandleList) + "状态已经被更新！请刷新列表后重试！";
		}
		
		String errAccInfo = null;
		if(null != (errAccInfo = getMessingAccInfoStr(baseList))){
			throw new SubAuditException("用户账户信息缺失(memberId)：" + errAccInfo);
		}
		
		// 补贴金额没有超出总金额
		Map<Long, Double> subAmountMap= countTotalAmount(baseList);				// 每个活动需要补贴数额
		if(isOutOfRangOfTotalAmount(balAmountMap, subAmountMap)){		
			throw new SubAuditException("补贴剩余金额不足！");
		}
		
		
		
		
		// 将要处理的数据封装到map, 修改补贴状态并写入审核日志表
		Map[] batchValuesSub = converteToBatchValue(toHandleList);
		Map[] batchValuesSubLog = converteToBatchValue(toHandleListLog);
		
		// 获得更新补贴总额的Map[], 用作batch更新
		Map[] totalAmountMaps = convertToTotalAmountBatchValues(subAmountMap, updateUserId);
		
		if(batchValuesSub.length > 0){
			baseDao.batchUpdate("SubAudit.updateSubStatusById", batchValuesSub);	// 更改补贴状态
		}
		if(batchValuesSubLog.length >0){
			baseDao.batchUpdate("AuditLog.addAuditLog", batchValuesSubLog);			// 添加记录到audit_log
		}
		// =================== 扣除总金额，往用户账户转钱
		// 没有账号就添加账号 ---- 改由订单验证时完成模块完成
	
		// 给用户钱包增加钱
		List<Map<String, Object>> userAccInfoVals = new ArrayList<Map<String, Object>>();
		for(SubAuditWithMemInfoDTO sam: baseList){
			userAccInfoVals.addAll(convertToAccInfo4BatchUpdate(sam,updateUserId));	// 将买家/卖家/供应商的账号和需要补贴的金额转换成集合
		}
		Map[] accInfoMaps = converteToBatchValue(userAccInfoVals);					// 要修改的账户和金额
		
		if(accInfoMaps.length > 0){
			
			// 生成资金流水信息的集合
			// a. 获取所有账户在补贴操作前的金额Map<Integer(memberId), BigDecimal(balTotal)>, 在生成资金流水记录是要用到
			Map<Integer, BigDecimal> memberIdAndAmountMap = getBalTotalAmountWithBigDecimal(accInfoMaps);
			List<Map<String, Object>> accInfoTransList	= this.convertToAccTransInfo4BatchAdd(memberIdAndAmountMap, baseList, updateUserId);
			Map[] accInfoTranstMapAll = converteToBatchValue(accInfoTransList);
			
			subAmountService.subductAmountBatch(totalAmountMaps);					// 减去总金额
			baseDao.batchUpdate("SubAudit.updateMemAmount", accInfoMaps);
			baseDao.batchUpdate("SubAudit.addAccTransInfo", accInfoTranstMapAll);
			
		}
		// 处理返回的信息
		
		
		return "SUCCESS";
		
	}
	
	/**
	 *  查询accInfoMaps所包含的用户当前的金额数
	 * @param accInfoMaps
	 * @return
	 */
	private Map<Integer, BigDecimal> getBalTotalAmountWithBigDecimal(Map[] accInfoMaps){
		Map<Integer, BigDecimal> result = new HashMap<Integer, BigDecimal>();
		Set<Integer> memberSet = new HashSet<Integer>();							// 要获取的金额的账户信息(memberId集合)
		
		// 1. 获取账户当前的金额
		for(int i=0; i<accInfoMaps.length; i++){
			Integer memberId = (Integer)accInfoMaps[i].get("memberId");
			if(!memberSet.contains(memberId)){
				memberSet.add(memberId);		// 保存所有账户信息
			}
		}
		// 查询账户的金额
		Map<String, List<Integer>> qParams = new HashMap<String, List<Integer>>();	// 查询账户金额的参数
		List<Integer> mList = new ArrayList<Integer>(memberSet);
		qParams.put("memberIds", mList);
		List<Map<String,Object>> balTotalList = baseDao.queryForList("SubAudit.getBalToalByMemberIds", qParams);
		
		if(null != balTotalList && balTotalList.size()>0){
			for(Map m: balTotalList){
				result.put((Integer)m.get("memberId"), (BigDecimal)m.get("balTotal"));
			}
		}
		
		return result;
	}
	
	/**
	 * 计算补贴的总金额
	 * @param baseList
	 * @return Map<subRuleId, totalAmount> 返回每个补贴规则应该补贴的总数
	 */
	private Map<Long,Double> countTotalAmount(List<SubAuditWithMemInfoDTO> baseList){
		Map<Long, Double> result = new HashMap<Long, Double>();
		
		for(SubAuditWithMemInfoDTO sam: baseList){
			BigDecimal r = initBigDecimal(null);	// 初始化r==0
			if(isAmountValid(sam.getBuyerSubAmount())){
				r = r.add(initBigDecimal(sam.getBuyerSubAmount().toString()));
			}
			if(isAmountValid(sam.getSellSubAmount())){
				r = r.add(initBigDecimal(sam.getSellSubAmount().toString()));
			}
			if(isAmountValid(sam.getSuppSubAmount())){
				r = r.add(initBigDecimal(sam.getSuppSubAmount().toString()));
			}
			
			// 如果补贴规则没有在结果集中,则put进map中
			if(result.containsKey(sam.getSubRuleId())){
				// 如果结果集中已经存在补贴数,则加上现在的补贴数,最后在结果集中就是所有该市场的补贴总数
				BigDecimal addUp = initBigDecimal(result.get(sam.getSubRuleId()).toString());
				result.put(sam.getSubRuleId(), addUp.add(r).doubleValue());
			}else{
				// 如果map中没有该marketId的补贴金额，直接将该市场的补贴金额保存到map中
				result.put(sam.getSubRuleId(), r.doubleValue());
			}
		}
		return result;
	}
	
	
	private Map[] convertToTotalAmountBatchValues(Map<Long,Double> totalAmount, String updateUserId){
		Map[] result = new Map[totalAmount.size()];
		
		int index = 0;
		for(Map.Entry<Long, Double> entry: totalAmount.entrySet()){
			Map vm = new HashMap<String, Object>();
			vm.put("subRuleId", entry.getKey());
			vm.put("amount", entry.getValue());
			vm.put("updateUserId", updateUserId);
			result[index++] = vm;
		}
		return result;
	}
	
	/**
	 * 如果订单列表中存在的用户(包括买家/卖家/供应商)没有钱包账户(acc_info)
	 * @param baseList
	 * @return
	 * @throws Exception 
	 */
	private Map[] getBatchValues4AccInfo(List<SubAuditWithMemInfoDTO> baseList) throws Exception{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Set<Object> set=new HashSet<Object>();												// 保存有的在acc_info中不存在的memberId(去掉重复的)
		for(SubAuditWithMemInfoDTO sam: baseList){
			if(null == sam.getBuyerAccId() && isAmountValid(sam.getBuyerSubAmount())){ 		// 如果买家有补贴额，但没有accId
				set.add(sam.getBuyerMemberId());
			}
			if(null == sam.getSellAccId() && isAmountValid(sam.getSellSubAmount())){		// 如果卖有补贴额，但没有accId
				set.add(sam.getSellMemberId());
			}
			if(null == sam.getSuppAccId() && isAmountValid(sam.getSuppSubAmount())){		// 如果供应商有补贴额，但没有accId
				set.add(sam.getSuppMemberId());
			}
		}
		for(Iterator<Object> it=set.iterator(); it.hasNext();){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("memberId", it.next());
			list.add(map);
		}
		
		Map[] batchVal = converteToBatchValue(list);
		
		return batchVal;
	}
	
	/**
	 * 返回订单中用户信息没有 accId的用户(memeberId)
	 * @param baseList
	 * @return null:如果所有用户都有accId; <memberId1, ... memberIdn>:缺失accId的用户id(memberid)
	 * @throws Exception 
	 */
	private String getMessingAccInfoStr(List<SubAuditWithMemInfoDTO> baseList) throws Exception{
		StringBuffer result= new StringBuffer();
		Map[] acArr = getBatchValues4AccInfo(baseList);
		for(Map<String, Object> map: acArr){
			result.append(map.get("memberId")).append(",");
		}		
		return (0==result.length())?null:result.substring(0, result.length()-1);
	}
	
	/**
	 * 检查订单金额是否有问题
	 * @param baseList
	 * @return
	 * @throws SubAuditException 
	 */
	private List<Long> getErrAmontList(List<SubAuditWithMemInfoDTO> baseList) throws SubAuditException{
		List<Long> r = new ArrayList<Long>();
		for(SubAuditWithMemInfoDTO sa: baseList){
			if(!isEqualsSubAmount(sa)){
				r.add(sa.getOrderNo());
			}
		}
		return r;
	}
	
	/**
	 * 将list转换成Map[]
	 * @param valueList
	 * @return
	 */
	private Map[] converteToBatchValue(List<Map<String,Object>> valueList) throws Exception{
		Map[] batchValues = new Map[valueList.size()];
		for(int i=0; i<valueList.size();i++){
			batchValues[i] = valueList.get(i);
		}
		return batchValues;
	}
	

	/**
	 * @TODO 1.对基础表中的数据进行过滤，对于状态不符合要求的记录过滤出来，不能通过审批
	 * 		 2. 同时生产log对象
	 * @param baseList, 基础数据对象集合
	 * @param toHandleList, 当前补贴的状态是"待补贴"状态，可以进行通过审批的操作
	 * @param nonHandleList, 当前状态不是"待审批"状态，记录orderNo到该集合，告诉用户
	 * 
	 */
	private void generateBatchAuditList(List<SubAuditWithMemInfoDTO> baseList, List<Map<String,Object>> toHandleList, List<Map<String,Object>> toHandleListLog, List<Long> nonHandleList, String updateUserId, String auditUserName){
		for(SubAuditEntity sa: baseList){
			// 只对带补贴"待补贴"进行处理
			if("1".equals(sa.getSubStatus())){
				Map<String, Object> toHandleMap = new HashMap<String, Object>();		// 审核数据
				Map<String, Object> toHandleMapLog = new HashMap<String, Object>();		// 审核Log数据
				
				toHandleMap.put("auditId", sa.getAuditId());
				toHandleMap.put("subStatus", "3");						// 改为"已补贴"状态
				toHandleMap.put("subComment", "批量审批-通过");
				toHandleMap.put("updateUserId", updateUserId);
				
				toHandleMapLog.put("type", "1");						// 类型为审核补贴
				toHandleMapLog.put("orderNo", sa.getOrderNo());
				toHandleMapLog.put("auditUserId", updateUserId);		// 审核人Id
				toHandleMapLog.put("auditUserName", auditUserName);		// 审核人名
				toHandleMapLog.put("description", "批量审批-通过");
				toHandleMapLog.put("createUserId", updateUserId);
				toHandleMapLog.put("updateUserId", updateUserId);
				
				toHandleList.add(toHandleMap);
				toHandleListLog.add(toHandleMapLog);
			}else{
				nonHandleList.add(sa.getOrderNo());
			}
		}
	}
	
	/**
	 * 通过原来的补贴状态和当前用户的操作(通过/不通过)计算出新的补贴状态
	 * @param oStatus 原来的补贴状态
	 * @param flag 表示用户的操作状态，通过/不通过(0:不通过; 1:通过)
	 * @return 新的状态
	 */
	private String generateNewSubStatus(String oStatus, String flag) throws Exception{
		if(null == oStatus || null == flag){
			throw new SubAuditException("获取当前补贴信息状态有异常: oStauts or flag不能为null!");
		}
		
		String result = null;
		if("1".equals(oStatus)){			// subStatus==1, 表示待补贴状态; 经过用户操作后的状态应该为"已补贴"或"不予补贴"
			if("0".equals(flag)){
				result = "4";				// 不予补贴 
			}else if("1".equals(flag)){
				result = "3";				// 已补贴
			}
		} else if("2".equals(oStatus)){		// subStatus==2, 表示系统驳回状态; 经过用户操作后的状态应该为"待补贴"或"不予补贴"
			if("0".equals(flag)){
				result = "4";				// 不予补贴
			}else if("1".equals(flag)){
				result = "1";				// 待补贴
			}
		} else if("3".equals(oStatus)){		// subStatus==3, 表示已补贴订单; 用户不应该对该状态进行再次审批操作(页面也会控制)
			throw new SubAuditException("页面操作有误:不能对已补贴状态订单再次审批！");
		} else if("4".equals(oStatus)){		// subStatus==4, 表示不予补贴状态; 用户操作后(高权限)该状态为"待补贴"
			if("0".equals(flag)){
				throw new SubAuditException("页面操作有误:不能对不予补贴状态的订单进行审核不通过操作！");	// 页面应该对该按钮进行控制
			}else if("1".equals(flag)){
				result = "1";				// 待补贴
			}
		}

		return result;
	}
	
	private Map<String,Object> generateAudiLogMap(Map<String, Object> subAuditMap){
		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put("type", 1);
		logMap.put("orderNo", subAuditMap.get("orderNo"));					//订单号
		logMap.put("auditUserId", subAuditMap.get("auditUserId"));					//审核人id
		logMap.put("auditUserName", subAuditMap.get("auditUserName"));				//审核人
		logMap.put("description", subAuditMap.get("subComment"));			//描述
		logMap.put("createUserId", subAuditMap.get("updateUserId"));		//创建人id
		logMap.put("updateUserId", subAuditMap.get("updateUserId"));		//修改人员id
		return logMap;
	}
	
	/**
	 * 检查补贴记录的金额(sub_audit.subAmount) 是否等于 订单记录(order_baseinfo)里面各项补贴的和
	 * @param SubAuditWithMemInfoDTO sa
	 * @return
	 * @throws SubAuditException 
	 */
	private boolean isEqualsSubAmount(SubAuditWithMemInfoDTO sa) throws SubAuditException{
		BigDecimal subAmountTotal = initBigDecimal(null);
		BigDecimal subAmountUser = initBigDecimal(null);
		
		
		if(null != sa.getSubAmount() && sa.getSubAmount() >0){
			subAmountTotal = subAmountTotal.add(initBigDecimal(sa.getSubAmount().toString()));					// 该订单总额
		}else{
			// 补贴总额是空值是有可能的
			//throw new SubAuditException("异常：订单"+sa.getOrderNo()+"的补贴金额(subAmount)是空值(null)！");
		}
		
		if(null != sa.getBuyerSubAmount() && isAmountValid(sa.getBuyerSubAmount())){
			subAmountUser = subAmountUser.add(initBigDecimal(sa.getBuyerSubAmount().toString()));				// 买家补贴额
		}
		
		if(null != sa.getSellSubAmount() && isAmountValid(sa.getSellSubAmount())){
			subAmountUser = subAmountUser.add(initBigDecimal(sa.getSellSubAmount().toString()));				// 卖家补贴额
		}
		
		if(null != sa.getSuppSubAmount() && isAmountValid(sa.getSuppSubAmount())){
			subAmountUser = subAmountUser.add(initBigDecimal(sa.getSuppSubAmount().toString()));				// 供应商补贴额
		}
		
		return subAmountTotal.equals(subAmountUser);
	}
	
	/**
	 * 补贴状态更新前进行必要的检查
	 * @return 如果有错误，返回错误信息，没有则返回null
	 * @throws Exception 
	 */
	private String subAuditCheckForStatusChange(SubAuditWithMemInfoDTO sam, Map<String, Object> map) throws Exception{
		// 补贴记录里面的金额应 == 买家补贴额+卖家补贴额+供应商补贴额 ???
		if(!isEqualsSubAmount(sam)){
			return "订单"+sam.getOrderNo()+"的补贴总额不正确，请检查！";
		}
		
		if(null!=sam.getSubStatus() && "3".equals(sam.getSubStatus())){
			return "订单号"+sam.getOrderNo()+"的订单已经是已补贴状态，不能对其进行再次审批！";
		}
		
		if(!map.get("uiStauts").equals(sam.getSubStatus())){
			return "审批拒绝，补贴状态已经发生改变！";
		}
		
		if(null == sam.getMarketId()){
			return "订单"+sam.getOrderNo()+"的marketId为空(null),请检查!";
		}
		// 用户的accInfo必须存在
		List<SubAuditWithMemInfoDTO> baseList = new ArrayList<SubAuditWithMemInfoDTO>(1);
		baseList.add(sam);
		String errAccInfo = null;
		if(null != (errAccInfo = getMessingAccInfoStr(baseList))){
			return "用户账户信息缺失(memberId)：" + errAccInfo;
		}
		return null;
	}
	
	
	
	
	/**
	 * 将必要的信息转换成list,以备实现batch更新(acc_info)
	 * @param sam
	 * @param map
	 * @return 
	 */
	private List<Map<String,Object>> convertToAccInfo4BatchUpdate(SubAuditWithMemInfoDTO sam, String updateUserId){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		
		// 如果买家补贴数没有，则不用更新
		if(null != sam.getBuyerSubAmount() && isAmountValid(sam.getBuyerSubAmount())){
			Map<String, Object> buyerMap = new HashMap<String, Object>();
			buyerMap.put("memberId", sam.getBuyerMemberId());
			buyerMap.put("subAmount", sam.getBuyerSubAmount());
			buyerMap.put("updateUserId", updateUserId);
			list.add(buyerMap);
		}
		
		// 如果卖家补贴数没有，则不用更新
		if(null != sam.getSellSubAmount() && isAmountValid(sam.getSellSubAmount())){
			Map<String, Object> sellMap = new HashMap<String, Object>();
			sellMap.put("memberId", sam.getSellMemberId());
			sellMap.put("subAmount", sam.getSellSubAmount());
			sellMap.put("updateUserId", updateUserId);
			list.add(sellMap);
		}
		
		// 如果供应商补贴数没有，则不用更新
		if(null != sam.getSuppSubAmount() && isAmountValid(sam.getSuppSubAmount())){
			Map<String, Object> suppMap = new HashMap<String, Object>();
			suppMap.put("memberId", sam.getSuppMemberId());
			suppMap.put("subAmount", sam.getSuppSubAmount());
			suppMap.put("updateUserId", updateUserId);
			list.add(suppMap);
		}
			
		return list;
	}
	

	
	/**
	 * @Todo 对每个订单里面的买家，卖家，供应商，生成补贴变变动流水，获得 要插入到table acc_trans_info的对象集合
	 * @author lmzhang@gdeng.cn
	 * @param balTotalMap, 包含所有订单里面的用户id和用户变动前的金额:Map<memeberId, totalAmount>
	 * @param baseList, 要处理的订单集合，包括订单号，以及该订单的买家/卖家/供应商 Id(memberId)和他们的账户Id(accId)
	 * @param updateUserId
	 * @return
	 */
	private List<Map<String, Object>> convertToAccTransInfo4BatchAdd(Map<Integer, BigDecimal> balTotalMap, List<SubAuditWithMemInfoDTO> baseList, String updateUserId){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(SubAuditWithMemInfoDTO sam: baseList){
			// 如果买家有补贴才才需要插入log
			if(isAmountValid(sam.getBuyerSubAmount())){
				BigDecimal buyerBalTotal = (BigDecimal)balTotalMap.get(sam.getBuyerMemberId());								// 当前的账户余额
				BigDecimal buyerBalTotalAfter = buyerBalTotal.add(initBigDecimal(sam.getBuyerSubAmount().toString()));		// 加上补贴后的账户余额
				balTotalMap.put(sam.getBuyerMemberId(), buyerBalTotalAfter);												// 修改用户账户金额(批量账户操作时用到)
				
				Map<String, Object> buyerEntity = new HashMap<String, Object>();
				buyerEntity.put("accId", sam.getBuyerAccId());
				buyerEntity.put("memberId", sam.getBuyerMemberId());
				buyerEntity.put("orderNo", sam.getOrderNo());
				buyerEntity.put("tradeType", "2");										// 用户补贴
				buyerEntity.put("peType", "1");											// 收入
				buyerEntity.put("tradeAmount", sam.getBuyerSubAmount());				// 交易金额
				buyerEntity.put("balTotal", buyerBalTotalAfter.doubleValue());			// 账户金额
				buyerEntity.put("createUserId", updateUserId);
				buyerEntity.put("updateUserId", updateUserId);
				list.add(buyerEntity);
			}
			// 卖家的补贴log
			if(isAmountValid(sam.getSellSubAmount())){
				BigDecimal sellBalTotal = (BigDecimal)balTotalMap.get(sam.getSellMemberId());
				BigDecimal sellBalTotalAfter = sellBalTotal.add(initBigDecimal(sam.getSellSubAmount().toString()));
				balTotalMap.put(sam.getSellMemberId(), sellBalTotalAfter);
				
				Map<String, Object> sellerEntity = new HashMap<String, Object>();
				sellerEntity.put("accId", sam.getSellAccId());
				sellerEntity.put("memberId", sam.getSellMemberId());
				sellerEntity.put("orderNo", sam.getOrderNo());
				sellerEntity.put("tradeType", "2");										// 用户补贴
				sellerEntity.put("peType", "1");										// 收入
				sellerEntity.put("tradeAmount", sam.getSellSubAmount());				// 交易金额
				sellerEntity.put("balTotal", sellBalTotalAfter.doubleValue());			// 账户金额
				sellerEntity.put("createUserId", updateUserId);
				sellerEntity.put("updateUserId", updateUserId);
				list.add(sellerEntity);
			}
			// 供应商补贴 log
			if(isAmountValid(sam.getSuppSubAmount())){
				BigDecimal suppBalTotal = (BigDecimal)balTotalMap.get(sam.getSuppMemberId());
				BigDecimal suppBalTotalAfter = suppBalTotal.add(initBigDecimal(sam.getSuppSubAmount().toString()));
				balTotalMap.put(sam.getSuppMemberId(), suppBalTotalAfter);
				
				Map<String, Object> suppEntity = new HashMap<String, Object>();
				suppEntity.put("accId", sam.getSuppAccId());
				suppEntity.put("memberId", sam.getSuppMemberId());
				suppEntity.put("orderNo", sam.getOrderNo());
				suppEntity.put("tradeType", "2");									// 用户补贴
				suppEntity.put("peType", "1");										// 收入
				suppEntity.put("tradeAmount", sam.getSuppSubAmount());				// 交易金额
				suppEntity.put("balTotal", suppBalTotalAfter.doubleValue());		// 账户金额
				suppEntity.put("createUserId", updateUserId);
				suppEntity.put("updateUserId", updateUserId);
				list.add(suppEntity);
			}
		}
		
		return list;
	}
	
	/**
	 * 判断金额是否是有效的值(null 或 0 表示无效金额)
	 * @param v:金额
	 * @return true: 金额非空并且大于0
	 */
	private boolean isAmountValid(Double v){
		if(null == v){
			return false;
		}else if(v == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * @ToDo: 判断指定的补贴金额是否超出了总金额(本次补贴的全部总金额)
	 * @author: lmzhang@gdeng.cn
	 * @param balMap: 每个活动规则(subRuleId)剩余的补贴额; Map<subRuleId, amount>
	 * @param d:需要补贴的金额
	 * @param subRuleId: 需要检查的补贴金额所属的活动规则
	 * @return: 如果所需要补贴的金额(d)超出了所对应补贴(subRuleId)的剩余补贴额(balMap)则返回true, 没有超出返回false
	 * @throws Exception
	 */
	private boolean isOutOfRangOfTotalAmount(Map<Long, Double> balMap,Double d, Long subRuleId) throws Exception{
		if(d==null){
			return false;
		}
		
		Double balAmount = balMap.get(subRuleId);									// 获取当补贴总金额还剩下多少
		if(null == balAmount){
			throw new SubAmountException("无法获取subRuleId="+subRuleId+"的补贴总额信息！");
		}
		
		BigDecimal total = initBigDecimal(balAmount.toString());		
		BigDecimal v = initBigDecimal(d.toString());
		int cr = total.compareTo(v);
		if(1 == cr || 0 == cr){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @ToDo: 验证reqBalMap所包含的说有市场需要补贴的金额是否超出所对应市场剩余的补贴额
	 * @author: lmzhang@gdeng.cn
	 * @param balMap: 每个活动规则剩余的补贴额Map<subRuleId, amount>
	 * @param reqBalMap: 需要补贴的金额Map<subRuleId, amount>
	 * @return 如果(需要补贴的)所有市场的补贴额没有超出该市场的补贴额返回false, 否则返回true
	 * @throws Exception
	 */
	private boolean isOutOfRangOfTotalAmount(Map<Long, Double> balMap, Map<Long, Double> reqBalMap) throws Exception{
		for(Map.Entry<Long, Double> entry: reqBalMap.entrySet()){
			if(isOutOfRangOfTotalAmount(balMap, entry.getValue(), entry.getKey())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getCurrentTotalAmount() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> rMap = baseDao.queryForMap("SubAudit.getSubTotalAmount", param);
		
		return (String)rMap.get("codeKey");
	}
	
	@Override
	public int getSubListTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("SubAudit.getSubListTotal", map, Integer.class);
	}

	@Override
	public List<SubAuditDTO> getSubList(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("SubAudit.getSubList", map, SubAuditDTO.class);
	}

	@Override
	public void addSubAudit(SubAuditDTO subAudit) throws ServiceException {
		baseDao.execute("SubAudit.addSubAudit", subAudit);
	}
	
	/**
	 * @ToDo: 根据给出的字符串初始化一个BigDecimal,如果给定的字符串为空则初始化为0
	 * @author: lmzhang@gdeng.cn
	 * @param vStr
	 * @return
	 */
	private BigDecimal initBigDecimal(String vStr){
		BigDecimal r = null;
		if(null == vStr || vStr.isEmpty()){
			r = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		}else{
			r = new BigDecimal(vStr).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return r;
	}
	/**
	 * 根据用户(买家，卖家，供应商的memberId)判断acc_info表中是否有该用户的记录(是否有钱包) ---- 注释掉，由订单模块实现该检查工作
	 * 如果没有则增加，目的是确保可以给该用户发放补贴
	 * @param sam
	 */
/*	
	private void accInfoHandler(SubAuditWithMemInfoDTO sam) throws Exception{
		// 买家有补贴但没有钱包，需要增加一条记录
		if(sam.getBuyerSubAmount()>0 && null==sam.getBuyerAccId() && null!=sam.getBuyerMemberId()){
			Map<String, Object> buyerAccInfoMap = new HashMap<String, Object>();
			buyerAccInfoMap.put("memberId", sam.getBuyerMemberId());
			baseDao.execute("SubAudit.addAccInfo", buyerAccInfoMap);
			Map<String, Object> map = queryAccInfo(sam.getBuyerMemberId());
			
			sam.setBuyerAccId((Integer)map.get("accId"));						// 讲accId保存到对象中
		}
		// 卖家账号
		if(sam.getSellSubAmount()>0 && null==sam.getSellAccId() && null!=sam.getSellMemberId()){
			Map<String, Object> sellAccInfoMap = new HashMap<String, Object>();
			sellAccInfoMap.put("memberId", sam.getSellMemberId());
			baseDao.execute("SubAudit.addAccInfo", sellAccInfoMap);
			Map<String, Object> map = queryAccInfo(sam.getSellMemberId());
			
			sam.setSellAccId((Integer)map.get("accId"));					 	// 讲accId保存到对象中
		}
		// 供应商账号
		if(sam.getSuppSubAmount()>0 && null==sam.getSuppAccId() && null!=sam.getSuppMemberId()){
			Map<String, Object> suppAccInfoMap = new HashMap<String, Object>();
			suppAccInfoMap.put("memberId", sam.getSuppMemberId());
			baseDao.execute("SubAudit.addAccInfo", suppAccInfoMap);
			Map<String, Object> map = queryAccInfo(sam.getSuppMemberId());
			
			sam.setSuppAccId((Integer)map.get("accId"));						// 讲accId保存到对象中
		}
	}
*/
	
	
	/**
	 * 根据memberId查询到accId
	 * @param memId
	 * @return
	 */
/*	
	private Map<String, Object> queryAccInfo(Integer memId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memId);
		return baseDao.queryForMap("SubAudit.getAccInfoByMemId", map);
	}
*/
	
	@Override
	public List<SubAuditWithMemInfoDTO> getSubAuditInfo(List<Integer> auditIdList) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("auditIds", auditIdList);
		List<SubAuditWithMemInfoDTO> result = baseDao.queryForList("SubAudit.getSubAuditWithMemInfoByIds", map, SubAuditWithMemInfoDTO.class);
		return result;
	}
	
	
	@Override
	public OrderOutmarketImageDTO getOutmarketImage(Long orderNo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		OrderOutmarketImageDTO result = (OrderOutmarketImageDTO)baseDao.queryForObject("SubAudit.queryOutmartetImage", map, OrderOutmarketImageDTO.class);
		return result;
	}
}
