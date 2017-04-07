package com.gudeng.commerce.gd.pay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.pay.dao.BaseDao;
import com.gudeng.commerce.gd.pay.dto.ResultDTO;
import com.gudeng.commerce.gd.pay.dto.WangPosPayNotifyDTO;
import com.gudeng.commerce.gd.pay.enm.OrderBaseinfoEnum;
import com.gudeng.commerce.gd.pay.enm.PaySerialnumberEnum;
import com.gudeng.commerce.gd.pay.enm.SystemLogTypeEnum;
import com.gudeng.commerce.gd.pay.enm.WangPosPayNotifyEnum;
import com.gudeng.commerce.gd.pay.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.pay.entity.SystemLogEntity;
import com.gudeng.commerce.gd.pay.service.DataToolService;
import com.gudeng.commerce.gd.pay.service.SystemLogService;
import com.gudeng.commerce.gd.pay.service.WangPosService;
import com.gudeng.commerce.gd.pay.util.DateUtil;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.framework.dba.transaction.annotation.Transactional;


/**
 *功能描述：旺pos支付后，通知执行服务类，处理后续的业务
 */
@Service
public class WangPosServiceImpl implements WangPosService{
	private final static Logger logger = LoggerFactory.getLogger(WangPosServiceImpl.class);
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private DataToolService dataToolService;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * 服务入口
	 * 
	 * @param wangPosPayNotifyDTO
	 * @return 
	 * 
	 */
	@Override
	@Transactional
	public ResultDTO execute(WangPosPayNotifyDTO wangPosPayNotifyDTO) throws Exception{
		ResultDTO result = new ResultDTO();
		//查询订单，判断是否存在和状态是否待付款
		OrderBaseinfoEntity orderBaseinfoEntity = queryOrderBaseinfo(wangPosPayNotifyDTO.getOrderNo());
		if(orderBaseinfoEntity == null || 
				!OrderBaseinfoEnum.ORDER_STATUS_1.getKey().equals(orderBaseinfoEntity.getOrderStatus())){
			result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_1004.getKey()));
			result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_1004.getValue());
			return result;
		}
		//更新支付状态
		logger.debug("新增订单" + wangPosPayNotifyDTO.getOrderNo() + "的支付记录");
		int count = insertPaySerialNumber(wangPosPayNotifyDTO.getOrderNo(), wangPosPayNotifyDTO.getTradeStatus(), 
				orderBaseinfoEntity.getPayAmount(), wangPosPayNotifyDTO.getCashierTradeNo(), wangPosPayNotifyDTO.getPayInfo());
		if(count == 0){
			logger.debug("新增订单" + wangPosPayNotifyDTO.getOrderNo() + "的支付记录失败，订单支付记录已存在");
			result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_2003.getKey()));
			result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_2003.getValue());
			return result;
		} else {
			logger.debug("新增订单" + wangPosPayNotifyDTO.getOrderNo() + "的支付记录成功");
		}
		if(WangPosPayNotifyEnum.TRADE_STATUS_PAY.getKey().equals(wangPosPayNotifyDTO.getTradeStatus())){
			//更新订单状态
			logger.debug("更新订单" + wangPosPayNotifyDTO.getOrderNo() + "的订单状态");
			count = updateOrderStatus(wangPosPayNotifyDTO.getOrderNo(), wangPosPayNotifyDTO.getPayType());
			if(count == 0){
				logger.debug("更新订单" + wangPosPayNotifyDTO.getOrderNo() + "的订单状态失败，找不到订单或者订单不是待付款状态");
				result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_1004.getKey()));
				result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_1004.getValue());
				return result;
			} else {
				logger.debug("更新订单" + wangPosPayNotifyDTO.getOrderNo() + "的订单状态成功");
				String content ="系统SYS自动为订单表订单号是" + wangPosPayNotifyDTO.getOrderNo() + "状态设置已完成";
				SystemLogEntity entity = new SystemLogEntity();
				entity.setChennel("1");
				entity.setContent(content);
				entity.setCreateTime(new Date());
				entity.setCreateUserId("SYS");
				entity.setOperationId(wangPosPayNotifyDTO.getOrderNo());
				entity.setType(SystemLogTypeEnum.ORDER.getKey());
				systemLogService.insertLog(entity);
				logger.debug("更新订单" + wangPosPayNotifyDTO.getOrderNo() + "的订单状态，写日志成功");
				
				// 清空缓存
				try {
					if (orderBaseinfoEntity.getSellMemberId() != null) {
						// 撤单时，需要根据下单时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
						dataToolService.cleanTradeCacheSpecial(orderBaseinfoEntity.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
						dataToolService.cleanOldTradeCacheSpecial(orderBaseinfoEntity.getSellMemberId().longValue(), orderBaseinfoEntity.getOrderTime());
						dataToolService.cleanGoodsCacheSpecial(orderBaseinfoEntity.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
					}
				} catch (Exception e) {
					logger.error("清空缓存失败", e);
				}
			}
		}
		return result;
	}

	/**
	 * 更新订单状态
	 * 
	 * @param orderNo 订单编号 
	 * @return int
	 * 
	 */
	public int updateOrderStatus(Long orderNo, String payType) throws Exception{
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("orderStatus", OrderBaseinfoEnum.ORDER_STATUS_3.getKey());
		paramMap.put("payType", payType);
		if (PaySerialnumberEnum.PAY_TYPE_2.getKey().equals(payType)) {
			paramMap.put("paySubType", PaySerialnumberEnum.PAY_SUB_TYPE_1.getKey());
		}
		paramMap.put("updateUserId", "SYS");
		paramMap.put("updateTime", DateUtil.getNow());
		return baseDao.execute("orderBaseinfo.updateOrderStatus", paramMap);
	}
	
	/**
	 * 插入支付记录
	 * 
	 * @param orderNo 订单编号 
	 * @param tradeStatus 交易状态
	 * @param payAmount 交易金额
	 * @param cashierTradeNo 交易流水号
	 * @param payInfo 支付结果信息
	 * @return int
	 * 
	 */
	public int insertPaySerialNumber(Long orderNo, String tradeStatus, Double payAmount, String cashierTradeNo, String payInfo) throws Exception{
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("payType", PaySerialnumberEnum.PAY_TYPE_2.getKey());
		int count = (int)baseDao.queryForObject("paySerialnumber.selectCount", paramMap, Integer.class);
		if(count > 0){
			return 0;
		}
		paramMap.put("payStatus", PaySerialnumberEnum.PAY_STATUS_1.getKey());
		paramMap.put("tradeAmount", payAmount);
		paramMap.put("statementId", cashierTradeNo);
		paramMap.put("remark", payInfo);
		paramMap.put("createUserId", "SYS");
		return baseDao.execute("paySerialnumber.insert", paramMap);
	}
	
	/**
	 * 查询订单记录
	 * 
	 * @param orderNo 订单编号 
	 * @return OrderBaseinfoEntity
	 * 
	 */
	public OrderBaseinfoEntity queryOrderBaseinfo(Long orderNo) throws Exception{
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("payType", PaySerialnumberEnum.PAY_TYPE_2.getKey());
		return (OrderBaseinfoEntity) baseDao.queryForObject("orderBaseinfo.selectOne", paramMap, OrderBaseinfoEntity.class);
	}
}
