package com.gudeng.commerce.gd.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.exception.RefundException;
import com.gudeng.commerce.gd.order.bo.OrderCacheBo;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.ClearingLogDTO;
import com.gudeng.commerce.gd.order.dto.MemberDTO;
import com.gudeng.commerce.gd.order.dto.MsgCons;
import com.gudeng.commerce.gd.order.dto.NstCallbackDTO;
import com.gudeng.commerce.gd.order.dto.OrderAdvanceAndPaymentDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoSendnowDTO;
import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderRefundRecordDTO;
import com.gudeng.commerce.gd.order.dto.OrderSinxinDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO.PAY_STATUS;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO.PAY_TYPE;
import com.gudeng.commerce.gd.order.dto.RefundResponseDTO;
import com.gudeng.commerce.gd.order.dto.RefundResponseLogResult;
import com.gudeng.commerce.gd.order.dto.ServiceResponseDTO;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.EOrderSource;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EPaySubType4Stament;
import com.gudeng.commerce.gd.order.entity.AuditLogEntity;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.AccInfoService;
import com.gudeng.commerce.gd.order.service.CartInfoService;
import com.gudeng.commerce.gd.order.service.OrderActRelationService;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderBillService;
import com.gudeng.commerce.gd.order.service.OrderDeliveryAddressService;
import com.gudeng.commerce.gd.order.service.OrderFeeItemDetailService;
import com.gudeng.commerce.gd.order.service.OrderNoService;
import com.gudeng.commerce.gd.order.service.OrderOutmarketinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.OrderRefundRecordService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.order.service.PromotionInfoService;
import com.gudeng.commerce.gd.order.service.TaskService;
import com.gudeng.commerce.gd.order.util.AccessSysSignUtil;
import com.gudeng.commerce.gd.order.util.Base64;
import com.gudeng.commerce.gd.order.util.Constant;
import com.gudeng.commerce.gd.order.util.DateUtil;
import com.gudeng.commerce.gd.order.util.Des3;
import com.gudeng.commerce.gd.order.util.GSONUtils;
import com.gudeng.commerce.gd.order.util.GdProperties;
import com.gudeng.commerce.gd.order.util.HttpClients;
import com.gudeng.commerce.gd.order.util.RsaUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

@Service
public class OrderBaseinfoServiceImpl implements OrderBaseinfoService {

	private final GdLogger logger = GdLoggerFactory.getLogger(OrderBaseinfoServiceImpl.class);
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private OrderCacheBo orderCacheBo;
	@Autowired
	private OrderProductDetailService orderProductDetailService;
	@Autowired
	private PaySerialnumberService paySerialnumberService;
	@Autowired
	private OrderOutmarketinfoService orderOutmarketinfoService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private AccInfoService accInfoService;
	@Autowired
	private PromotionInfoService promotionInfoService;
	@Autowired
	private OrderBillService orderBillService;
	@Autowired
	private OrderActRelationService orderActRelationService;
	@Autowired
	private OrderFeeItemDetailService orderFeeItemDetailService;
	@Autowired
	private CartInfoService cartInfoService;

	@Autowired
	private OrderRefundRecordService orderRefundRecordService;
	@Autowired
	private OrderDeliveryAddressService orderDeliveryAddressService;
	@Autowired
	private OrderNoService orderNoService;

	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Long insertEntity(OrderBaseinfoEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("persaleId", id);
		return (int) baseDao.execute("OrderBaseinfo.deleteById", map);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count = 0;
		for (Long id : idList) {
			count = deleteById(id);
			if (count <= 0) {
				throw new Exception("批量删除记录失败！id:" + id);
			}
		}
		return count;
	}

	@Override
	public int updateDTO(OrderBaseinfoDTO obj) throws Exception {
		int count = baseDao.execute("OrderBaseinfo.updateDTO", obj);
		if (count <= 0) {
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int updateByOrderNo(OrderBaseinfoDTO obj) throws Exception {
		// orderTime 转 sqlTime
		if (obj.getOrderTime() != null) {
			obj.setOrderTime(DateUtil.getNow(obj.getOrderTime()));
		}
		int count = baseDao.execute("OrderBaseinfo.updateByOrderNo", obj);
		if (count <= 0) {
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<OrderBaseinfoDTO> objList) throws Exception {
		int count = 0;
		for (OrderBaseinfoDTO dto : objList) {
			count = baseDao.execute("OrderBaseinfo.deleteById", dto);
			if (count <= 0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("OrderBaseinfo.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public OrderBaseinfoDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("persaleId", id);
		OrderBaseinfoDTO obd = (OrderBaseinfoDTO) this.baseDao.queryForObject("OrderBaseinfo.getById", map,
				OrderBaseinfoDTO.class);
		//
		// List<String> orderNos = new ArrayList<String>();
		// orderNos.add(obd.getOrderNo().toString());
		//
		// map.put("orderNos", orderNos);
		// map.put("orderType", obd.getOrderType());
		//
		// List<OrderBaseinfoDTO> orders =
		// baseDao.queryForList("OrderBaseinfo.getFirstOrder", map,
		// OrderBaseinfoDTO.class);
		// if (orders.size() == 1) {
		// obd.setIsFirst(1);
		// } else {
		// obd.setIsFirst(0);
		// }
		return obd;
	}

	@Override
	public List<OrderBaseinfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		/*
		 * baseDao.persist(new OrderBaseinfoEntity(), Long.class); Map map1 =
		 * new HashMap(); map1.put("persaleId", 25l);
		 * baseDao.execute("OrderBaseinfo.deleteById", map1);
		 */
		List<OrderBaseinfoDTO> list = baseDao.queryForList("OrderBaseinfo.getListByConditionPage", map,
				OrderBaseinfoDTO.class);
		return list;
	}

	@Override
	public List<OrderBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<OrderBaseinfoDTO> list = baseDao.queryForList("OrderBaseinfo.getListByCondition", map,
				OrderBaseinfoDTO.class);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public int orderExamine(Integer payId, Long orderNo, String type, String description, String statementId,
			String updateUserId, String userName) throws Exception {
		OrderBaseinfoDTO odto = getByOrderNo(orderNo);
		if ("1".equals(type) || "2".equals(type)) {
			if (null != odto) {
				if ("1".equals(odto.getExamineStatus()) || "2".equals(odto.getExamineStatus())) {
					return -2;
				}
			} else {
				return 0;
			}
		}
		if ("1".equals(type)) {
			if (null != payId && !"".equals(payId)) {
				if (statementId.indexOf(",") > 0) {
					String statemp[] = statementId.split(",");
					for (String stemp : statemp) {
						Map smap = new HashMap();
						smap.put("statementId", stemp);
						int sint = (int) baseDao.queryForObject("PaySerialnumber.getTotalByStatementId", smap,
								Integer.class);
						if (sint > 0) {
							return -1;
						}
					}
				} else {
					Map smap = new HashMap();
					smap.put("statementId", statementId);
					int sint = (int) baseDao.queryForObject("PaySerialnumber.getTotalByStatementId", smap,
							Integer.class);
					if (sint > 0) {
						return -1;
					}
				}
				Map map = new HashMap();
				map.put("orderNo", orderNo);
				map.put("examineStatus", "1");
				map.put("examineTime", "1");
				baseDao.execute("OrderBaseinfo.updateByOrderNo", map);
				Map map2 = new HashMap();
				map2.put("statementId", statementId);
				map2.put("payId", payId);
				baseDao.execute("PaySerialnumber.updateStatementId", map2);
				AuditLogEntity ae = new AuditLogEntity();
				ae.setType("3");
				ae.setOrderNo(orderNo);
				// ae.setAuditId(updateUserId);
				ae.setAuditUserId(updateUserId);
				// ae.setAuditName(userName);
				ae.setAuditUserName(userName);
				ae.setDescription("订单审核通过");
				ae.setAuditTime(new Date());
				ae.setCreateTime(new Date());
				ae.setUpdateTime(new Date());
				ae.setCreateUserId(updateUserId);
				Long aelong = (Long) baseDao.persist(ae, Long.class);
//				// 交易凭证客服审核完，添加计算补贴金额任务待出岗计算补贴
//				taskService.addTask(TaskDTO.getCalcSubAmtTask(orderNo.toString()));
				if (aelong > 0) {
					AccInfoDTO adto = accInfoService.getWalletIndex(Long.parseLong(odto.getMemberId().toString()));
					if (null == adto) {
						AccInfoDTO adtotemp = new AccInfoDTO();
						adtotemp.setMemberId(odto.getMemberId());
						adtotemp.setAccStatus("1");
						adtotemp.setCreateuserId(updateUserId);
						accInfoService.addAccInfo(adtotemp);
					}
					AccInfoDTO adto2 = accInfoService.getWalletIndex(Long.parseLong(odto.getSellMemberId().toString()));
					if (null == adto2) {
						AccInfoDTO adtotemp2 = new AccInfoDTO();
						adtotemp2.setMemberId(odto.getSellMemberId());
						adtotemp2.setAccStatus("1");
						adtotemp2.setCreateuserId(updateUserId);
						accInfoService.addAccInfo(adtotemp2);
					}
				}
				return 1;
			}
		} else if ("2".equals(type)) {
			Map map = new HashMap();
			map.put("orderNo", orderNo);
			map.put("examineStatus", "2");
			int count = baseDao.execute("OrderBaseinfo.updateByOrderNo", map);
			if (count > 0) {
				AuditLogEntity ae = new AuditLogEntity();
				ae.setType("3");
				ae.setOrderNo(orderNo);
				// ae.setAuditId(updateUserId);
				ae.setAuditUserId(updateUserId);
				// ae.setAuditName(userName);
				ae.setAuditUserName(userName);
				ae.setDescription(description);
				ae.setAuditTime(new Date());
				ae.setCreateTime(new Date());
				ae.setUpdateTime(new Date());
				ae.setCreateUserId(updateUserId);
				Long aelong = (Long) baseDao.persist(ae, Long.class);
				if (aelong > 0) {
					return 1;
				}
			}
		} else if ("3".equals(type)) {
			if (null != payId && !"".equals(payId)) {
				if (statementId.indexOf(",") > 0) {
					String statemp[] = statementId.split(",");
					for (String stemp : statemp) {
						Map smap = new HashMap();
						smap.put("statementId", stemp);
						int sint = (int) baseDao.queryForObject("PaySerialnumber.getTotalByStatementId", smap,
								Integer.class);
						if (sint > 0) {
							return -1;
						}
					}
				} else {
					Map smap = new HashMap();
					smap.put("statementId", statementId);
					int sint = (int) baseDao.queryForObject("PaySerialnumber.getTotalByStatementId", smap,
							Integer.class);
					if (sint > 0) {
						return -1;
					}
				}
				Map map2 = new HashMap();
				map2.put("statementId", statementId);
				map2.put("payId", payId);
				baseDao.execute("PaySerialnumber.updateStatementId", map2);
			}
			AuditLogEntity ae = new AuditLogEntity();
			ae.setType("3");
			ae.setOrderNo(orderNo);
			// ae.setAuditId(updateUserId);
			ae.setAuditUserId(updateUserId);
			// ae.setAuditName(userName);
			ae.setAuditUserName(userName);
			ae.setDescription("运营人员修改银行流水号");
			ae.setAuditTime(new Date());
			ae.setCreateTime(new Date());
			ae.setUpdateTime(new Date());
			ae.setCreateUserId(updateUserId);
			Long aelong = (Long) baseDao.persist(ae, Long.class);
			if (aelong > 0) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public OrderBaseinfoDTO getByOrderNo(Long orderNo) throws Exception {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("orderNo", orderNo);
		return (OrderBaseinfoDTO) this.baseDao.queryForObject("OrderBaseinfo.getByOrderNo", map,
				OrderBaseinfoDTO.class);
		// return orderCacheBo.getByOrderNo(orderNo, baseDao);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean addOrder(Map<String, Object> map) throws Exception {
		// 插入订单基本信息
		OrderBaseinfoEntity orderEntity = (OrderBaseinfoEntity) map.get("orderBase");
		insertEntity(orderEntity);

		try {// 确认订单时候，平台配送的，走这个代码
			if ("1".equals(orderEntity.getDistributeMode())) {
				OrderFeeItemDetailEntity detailEntity = new OrderFeeItemDetailEntity();
				detailEntity.setOrderNo(orderEntity.getOrderNo());
				detailEntity.setAmount((Double) map.get("prepayAmt"));
				detailEntity.setCreateTime(new Date());
				detailEntity.setDescription("预付款");
				detailEntity.setPayerType("nsy");
				detailEntity.setPayeeType("nps");
				detailEntity.setFeeType("3");// 类型为预付款
				orderFeeItemDetailService.insert(detailEntity);
			}
		} catch (Exception e) {
			// TODO : nothing
		}

		// 插入订单产品信息
		List<OrderProductDetailEntity> entityList = (List<OrderProductDetailEntity>) map.get("orderProductList");
		if (entityList != null && entityList.size() > 0) {
			orderProductDetailService.batchInsertEntity(entityList);
		}

		// 产品减去库存
		List<Map<String, Object>> stockList = (List<Map<String, Object>>) map.get("stockList");
		if (stockList != null && stockList.size() > 0) {
			int len = stockList.size();
			Map<String, Object>[] batchParams = new Map[len];
			for (int i = 0; i < len; i++) {
				batchParams[i] = stockList.get(i);
			}
			baseDao.batchUpdate("OrderBaseinfo.batchUpdateStockCount", batchParams);
		}

		// 插入订单活动信息
		List<OrderActRelationEntity> orderActList = (List<OrderActRelationEntity>) map.get("orderActList");
		if (orderActList != null && orderActList.size() > 0) {
			orderActRelationService.batchInsertEntity(orderActList);
		}

		// 插入订单活动费用信息
		List<OrderFeeItemDetailDTO> orderActFeeList = (List<OrderFeeItemDetailDTO>) map.get("orderActFeeList");
		if (orderActFeeList != null && orderActFeeList.size() > 0) {
			orderFeeItemDetailService.batchInsertEntity(orderActFeeList);
		}

		// 插入余额流水记录
		PaySerialnumberEntity payRecordEntity = (PaySerialnumberEntity) map.get("paySerialNumEntity");
		if (payRecordEntity != null) {
			paySerialnumberService.insertEntity(payRecordEntity);
		}

		// 插入现金流水记录
		PaySerialnumberEntity cashRecordEntity = (PaySerialnumberEntity) map.get("cashRecordEntity");
		if (cashRecordEntity != null) {
			paySerialnumberService.insertEntity(cashRecordEntity);
		}

//		// 非刷卡支付需要插入补贴记录
//		String payType = orderEntity.getPayType();
//		if (StringUtils.isNotBlank(payType) && !"2".equals(payType) && !"12".equals(payType)) {
//			taskService.addTask(TaskDTO.getCalcSubAmtTask(orderEntity.getOrderNo() + ""));
//		}

		// 更新用户余额
		if (map.get("acctInfo") != null) {
			baseDao.execute("OrderBaseinfo.updateAccInfo", map.get("acctInfo"));
		}

		// 插入用户流水记录
		if (map.get("acctTranInfo") != null) {
			baseDao.execute("OrderBaseinfo.addTransInfo", map.get("acctTranInfo"));
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean addSinXinOrder(Map<String, Object> map) throws Exception {
		// 插入订单基本信息
		OrderBaseinfoEntity orderEntity = (OrderBaseinfoEntity) map.get("orderBase");
		insertEntity(orderEntity);

		// 插入订单产品信息
		List<OrderProductDetailEntity> entityList = (List<OrderProductDetailEntity>) map.get("orderProductList");
		if (entityList != null && entityList.size() > 0) {
			orderProductDetailService.batchInsertEntity(entityList);
		}
		return true;
	}

	@Override
	public List<OrderBaseinfoDTO> getOderInfoList(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception {
		List<OrderBaseinfoDTO> orderList = baseDao.queryForList("OrderBaseinfo.getOrderInfo", orderBaseinfoDTO,
				OrderBaseinfoDTO.class);
		// 将查询的产品详细清加入每个订单号
		for (OrderBaseinfoDTO order : orderList) {
			List<OrderProductDetailDTO> orderProductList = baseDao.queryForList("OrderBaseinfo.getOrderProductInfo",
					order, OrderProductDetailDTO.class);
			order.setDetailList(orderProductList);
		}
		return orderList;
	}

	@Override
	@Transactional
	public Integer cancelByOrderNo(Map<String, Object> map) throws Exception {
		// 更新订单状态为已取消
		OrderBaseinfoDTO obj = new OrderBaseinfoDTO();
		Long orderNo = (Long) map.get("orderNo");
		obj.setOrderNo(orderNo);
		obj.setCloseTime(DateUtil.getNow());
		obj.setCloseUserId((String) map.get("closeUserId"));
		obj.setOrderStatus(StringUtils.isBlank((String) map.get("status")) ? "8" : (String) map.get("status"));
		Integer userType = (Integer) map.get("cancelUserType"); // 取消用户类型 1买家
																// 2卖家 3超时
		userType = userType == null ? 0 : userType;
		switch (userType) {
		case 1:
			obj.setCancelReason("我已取消");
			break;
		case 2:
			obj.setCancelReason("卖家已取消");
			break;
		default:
			obj.setCancelReason("付款超时");
			break;
		}
		int updateNum = updateByOrderNo(obj);

		// 更新产品库存
		List<Map<String, Object>> stockList = (List<Map<String, Object>>) map.get("stockList");
		if (stockList != null && stockList.size() > 0) {
			int len = stockList.size();
			Map<String, Object>[] batchParams = new Map[len];
			for (int i = 0; i < len; i++) {
				batchParams[i] = stockList.get(i);
			}
			baseDao.batchUpdate("OrderBaseinfo.batchUpdateStockCount", batchParams);
		}

		// 更新用户余额
		if (map.get("acctInfo") != null) {
			baseDao.execute("OrderBaseinfo.updateAccInfo", map.get("acctInfo"));
		}

		// 插入用户流水记录
		if (map.get("acctTranInfo") != null) {
			baseDao.execute("OrderBaseinfo.addTransInfo", map.get("acctTranInfo"));
		}
		return updateNum;
	}

	@Override
	public OrderBaseinfoDTO queryOrderDetail(Long orderNo) throws Exception {
		OrderBaseinfoDTO orderBase = getByOrderNo(orderNo);

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("orderNo", orderNo);
		List<OrderProductDetailDTO> detailList = orderProductDetailService.getListByOrderNo(queryMap);
		orderBase.setDetailList(detailList);

		List<PaySerialnumberDTO> payment = paySerialnumberService.getListByCondition(queryMap);
		orderBase.setPayments(payment);

		List<OrderOutmarketInfoDTO> outmarketInfos = orderOutmarketinfoService.getListByMap(queryMap);
		if (CollectionUtils.isNotEmpty(outmarketInfos)) {
			orderBase.setOutmarketInfo(outmarketInfos.get(0));
		}
		return orderBase;
	}

	@Override
	public int getTotalByStatusPage(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("OrderBaseinfo.getTotalByStatusPage", map, Integer.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getListByStatusPage(Map<String, Object> map) throws Exception {
		List<OrderBaseinfoDTO> list = baseDao.queryForList("OrderBaseinfo.getListByStatusPage", map,
				OrderBaseinfoDTO.class);
		return list;
	}

	@Override
	public Long queryOrderAmountByMemberId(Long memberId, String type) throws Exception {
		String startDate = "";
		String endDate = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if ("day".equals(type)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String s = df.format(new Date());
			StringBuffer strStartDate = new StringBuffer().append(s).append(" 00:00:00");
			StringBuffer strEndDate = new StringBuffer().append(s).append(" 23:59:59");
			startDate = strStartDate.toString();
			endDate = strEndDate.toString();
		} else if ("week".equals(type)) {
			startDate = DateUtil.getMondayOfThisWeek();
			endDate = DateUtil.getSundayOfThisWeek();
		} else if ("month".equals(type)) {
			startDate = DateUtil.getFirstDay();
			endDate = DateUtil.getLastDay();
		}
		map.put("memberId", memberId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return (Long) baseDao.queryForObject("OrderBaseinfo.queryOrderAmountByMemberId", map, Long.class);
	}

	@Override
	@Transactional
	public int confirmPay(Map<String, Object> totalMap) throws Exception {
		// 更新订单状态
		OrderBaseinfoDTO orderDTO = (OrderBaseinfoDTO) totalMap.get("orderBaseDTO");
		int updateNum = updateByOrderNo(orderDTO);

		// 插入流水记录
		PaySerialnumberEntity payRecordEntity = (PaySerialnumberEntity) totalMap.get("paySerialNumEntity");
		if (payRecordEntity != null) {
			Long pId = paySerialnumberService.insertEntity(payRecordEntity);
			System.out.println("[INFO]支付流水记录插入成功: " + pId);
		}
		//
		// //非刷卡支付需要插入补贴记录
		// if(!"2".equals(payRecordEntity.getPayType())){
		// taskService.addTask(TaskDTO.getCalcSubAmtTask(orderDTO.getOrderNo()+""));
		// }

		return updateNum;
	}

	@Override
	@Transactional
	public int uploadVoucherAgain(Map<String, Object> totalMap) throws Exception {
		// 更新订单状态
		OrderBaseinfoDTO orderDTO = (OrderBaseinfoDTO) totalMap.get("orderBaseDTO");
		int updateNum = updateByOrderNo(orderDTO);

		// 插入流水记录
		PaySerialnumberDTO payRecordDTO = (PaySerialnumberDTO) totalMap.get("paySerialNumDTO");
		if (payRecordDTO != null) {
			int num = paySerialnumberService.updateDTO(payRecordDTO);
			System.out.println("[INFO]支付流水记录更新成功: " + num);
		}

		return updateNum;
	}

	@Override
	public List<OrderBaseinfoDTO> getOverOrderInfoList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderBaseinfo.getOverOrderInfoList", map, OrderBaseinfoDTO.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getTwoAlreadyPayOrderList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderBaseinfo.getTwoAlreadyPayOrderList", map, OrderBaseinfoDTO.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getOutmarketOrderList(OrderBaseinfoDTO orderBaseinfoDTO) {
		List<OrderBaseinfoDTO> orderList = baseDao.queryForList("OrderBaseinfo.getOutmarketOrderList", orderBaseinfoDTO,
				OrderBaseinfoDTO.class);
		// 将查询的产品详细清加入每个订单号
		for (OrderBaseinfoDTO order : orderList) {
			List<OrderProductDetailDTO> orderProductList = baseDao.queryForList("OrderBaseinfo.getOrderProductInfo",
					order, OrderProductDetailDTO.class);
			order.setDetailList(orderProductList);
		}
		return orderList;
	}

	@Override
	public List<OrderBaseinfoDTO> getUnpaidOrderList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderBaseinfo.getUnpaidOrderList", map, OrderBaseinfoDTO.class);
	}

	@Override
	@Transactional
	public void confirmReceive(Map<String, Object> totalMap) throws Exception {
		OrderBaseinfoDTO obj = (OrderBaseinfoDTO) totalMap.get("orderBase");
		int updateNum = updateByOrderNo(obj);

		// 插入余额流水记录
		PaySerialnumberEntity payRecordEntity = (PaySerialnumberEntity) totalMap.get("paySerialNumEntity");
		if (payRecordEntity != null) {
			paySerialnumberService.insertEntity(payRecordEntity);
		}
	}

	@Override
	public int getPageTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("OrderBaseinfo.getPageTotal", map, Integer.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getListByPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderBaseinfo.getListByPage", map, OrderBaseinfoDTO.class);
	}

	@Override
	public List<OrderSinxinDTO> queryOrderForSinxin(OrderSinxinDTO queryDTO) throws Exception {
		Map<String, Object> map = DalUtils.convertToMap(queryDTO);
		if (CollectionUtils.isNotEmpty(queryDTO.getOrderNos())) {
			map.put("orderNos", queryDTO.getOrderNos());
		}
		return baseDao.queryForList("OrderBaseinfo.queryOrderForSinxin", map, OrderSinxinDTO.class);
	}

	@Override
	public int getDeliveredProductTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("OrderBaseinfo.getDeliveredProductTotal", map, Integer.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getDeliveredProductList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderBaseinfo.getDeliveredProductList", map, OrderBaseinfoDTO.class);
	}

	@Override
	public int getOrderTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("OrderBaseinfo.getOrderTotal", map, Integer.class);
	}

	@Override
	public int getSuppOrderTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("OrderBaseinfo.getSuppOrderTotal", map, Integer.class);
	}

	@Override
	public int getServiceOrderTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("OrderBaseinfo.getServiceOrderTotal", map, Integer.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getOrderListByConditionPage(Map<String, Object> map) throws Exception {
		List<OrderBaseinfoDTO> list = baseDao.queryForList("OrderBaseinfo.getOrderListByConditionPage", map,
				OrderBaseinfoDTO.class);

		/*
		 * 添加isFirst
		 */
		Map<String, Object> param = new HashMap<>();
		List<String> orderNos = new ArrayList<>();

		/*
		 * 当有数据，且集合中的IsFirst有值为1 代表查询的是首单，所有数据都是首单，则不需要再查询是否为首单 判断条件 isFirst为空
		 * 或者不为1
		 */
		if (list.size() > 0 && (list.get(0).getIsFirst() == null || (list.get(0).getIsFirst() != 1 && list.get(0).getIsFirst() !=2))) {
			for (int i = 0; i < list.size(); i++) {
				OrderBaseinfoDTO obd = list.get(i);
				orderNos.add(obd.getOrderNo().toString());
			}
			// 将所有查询到的订单号查询有哪些是首单
			param.put("orderNos", orderNos);
			param.put("orderType", "1");
			List<OrderBaseinfoDTO> firstOrders = baseDao.queryForList("OrderBaseinfo.getFirstOrder", param,
					OrderBaseinfoDTO.class);

			/*
			 * 循环对比那些单是首单并标识
			 */
			for (int i = 0; i < list.size(); i++) {
				OrderBaseinfoDTO obd = list.get(i);
				obd.setIsFirst(0);
				for (int j = 0; j < firstOrders.size(); j++) {
					if (obd.getOrderNo().equals(firstOrders.get(j).getOrderNo())) {
						obd.setIsFirst(1);
						obd = null;
						break;
					}
				}
			}
		}

		return list;
	}

	@Override
	@Transactional
	public PageQueryResultDTO<OrderBaseinfoDTO> searchSellerOrderList(Map<String, Object> map) throws Exception {
		List<OrderBaseinfoDTO> dataList = baseDao.queryForList("OrderBaseinfo.searchSellerOrderList", map,
				OrderBaseinfoDTO.class);
		Integer totalNum = baseDao.queryForObject("OrderBaseinfo.getSearchSellerOrdersTotal", map, Integer.class);
		PageQueryResultDTO<OrderBaseinfoDTO> dto = new PageQueryResultDTO<OrderBaseinfoDTO>();
		dto.setDataList(dataList);
		dto.setTotalCount(totalNum == null ? 0 : totalNum);
		return dto;
	}

	@Override
	public List<OrderBaseinfoDTO> getByOrderNoList(List<Long> orderNoList) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNoList", orderNoList);
		return baseDao.queryForList("OrderBaseinfo.getByOrderNoList", map, OrderBaseinfoDTO.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getAbleGiftOrder(Long memberId) throws Exception {
		Map map = new HashMap();
		map.put("memberId", memberId);
		return baseDao.queryForList("OrderBaseinfo.getAbleGiftOrder", map, OrderBaseinfoDTO.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getAbleGiftOrder(Map map) throws Exception {
		return baseDao.queryForList("OrderBaseinfo.getAbleGiftOrder", map, OrderBaseinfoDTO.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getSuppOrderListByConditionPage(Map<String, Object> map) {
		List<OrderBaseinfoDTO> list = baseDao.queryForList("OrderBaseinfo.getSuppOrderListByConditionPage", map,
				OrderBaseinfoDTO.class);

		return list;
	}

	@Override
	public List<OrderBaseinfoDTO> getServiceOrderListByConditionPage(Map<String, Object> map) {
		List<OrderBaseinfoDTO> list = baseDao.queryForList("OrderBaseinfo.getServiceOrderListByConditionPage", map,
				OrderBaseinfoDTO.class);

		return list;
	}

	@Override
	@Transactional
	public int addPromOrder(Map<String, Object> map) throws Exception {
		// 插入订单基本信息
		addOrder(map);

		// 促销活动信息
		OrderBaseinfoEntity orderEntity = (OrderBaseinfoEntity) map.get("orderBase");
		String hasPromAct = orderEntity.getPromType();
		if ("1".equals(hasPromAct)) {
			// 插入促销订单信息
			promotionInfoService.addPromOrderEntity(map.get("promOrderEntity"));
			// 插入促销活动商品信息
			promotionInfoService.addPromOrderProductEntity(map.get("promOrderProductEntity"));
		}
		return 0;
	}

	@Override
	public List<OrderBaseinfoDTO> getPurchaseOrderListByPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderBaseinfo.getPurchaseOrderListByPage", map, OrderBaseinfoDTO.class);
	}

	@Override
	public int getPurchaseOrderTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("OrderBaseinfo.getPurchaseOrderTotal", map, Integer.class);
	}

	@Override
	public List<OrderBaseinfoSendnowDTO> getSendnowOrderListByConditionPage(Map<String, Object> map) {
		return baseDao.queryForList("OrderBaseinfo.getSendnowOrderListByConditionPage", map,
				OrderBaseinfoSendnowDTO.class);
	}

	@Override
	public int supplementOrderMessage(OrderBaseinfoDTO orderDTO) throws Exception {
		int count = baseDao.execute("OrderBaseinfo.updateCustomer", orderDTO);
		return count;
	}

	@Override
	public String queryOderSameStatement(String persaleId) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("persaleId", persaleId);
		String statementId = baseDao.queryForObject("OrderBaseinfo.queryOderSameStatement", param, String.class);
		return statementId;
	}

	@Override
	@Transactional
	public void saveSupplementOrder(String userId, String persaleId, String statementId) throws Exception {
		// 校验
		// 1. 流水号不存在，提示“该流水号不存在”
		// 2. 订单中心“终端号”与银行流水“终端号”不同，提示“终端号与现有商铺名称不符“
		// 3. 流水号重复，提示“该流水号已存在”
		OrderBillDTO bill = orderBillService.getOrderBillBySysRefeNo(statementId);
		if (bill == null) {
			throw new RuntimeException("该流水[" + statementId + "]不存在");
		}
		OrderBaseinfoDTO order = getById(Long.valueOf(persaleId));
		List<String> posNumbers = queryPosNumberByBusinessId(order.getBusinessId());
		boolean find = false;
		for (String posNo : posNumbers) {
			if (StringUtils.equals(posNo, bill.getClientNo())) {
				find = true;
				break;
			}
		}
		if (!find) {
			throw new RuntimeException("订单中心终端号" + posNumbers + "与银行流水号的终端号[" + bill.getClientNo() + "]不相同");
		}
		int total = paySerialnumberService.getTotalByStatementId(statementId);
		if (total > 0) {
			throw new RuntimeException("该流水号[" + statementId + "]已存在");
		}
		// 构建流水
		PaySerialnumberEntity ps = new PaySerialnumberEntity();
		ps.setOrderNo(order.getOrderNo());
		ps.setStatementId(statementId);
		ps.setPayTime(bill.getTradeDay());
		ps.setPayType(PAY_TYPE.XXSK.getValue());
		ps.setPayStatus(PAY_STATUS.PAY_SUCCESS.getValue());
		ps.setPaymentAcc(bill.getCardNo());
		ps.setTradeAmount(bill.getTradeMoney());
		ps.setPosNumber(bill.getClientNo());
		ps.setCreateTime(new Date());
		ps.setUpdatetime(new Date());
		ps.setCreateuserid(userId);
		paySerialnumberService.insertEntity(ps);

		// 更新订单
		OrderBaseinfoDTO neworder = new OrderBaseinfoDTO();
		neworder.setPersaleId(Integer.valueOf(persaleId));
		neworder.setOrderNo(order.getOrderNo());
		neworder.setPayAmount(bill.getTradeMoney());
		neworder.setPayType(PAY_TYPE.XXSK.getValue());
		neworder.setOrderStatus("3");// 已付款
		neworder.setCloseTime(null);// 关闭时间删除
		neworder.setCloseUserId(null);
		neworder.setHasCustomer("1");// 补充订单
		long td = bill.getTradeDay().getTime();
		Random rand = new Random();
		int rt = rand.nextInt(40);
		Date ort = new Date();
		ort.setTime(td - (rt + 60) * 1000);// 比交易时间早60-100秒
		neworder.setOrderTime(DateUtil.getNow(ort));
		baseDao.execute("OrderBaseinfo.updateSupplementOrder", neworder);
		// 插入pay_statementid,防止E农重复发送
		try {
			paySerialnumberService.insertPayStatementId(statementId);
		} catch (Exception e) {
			// 失败不做处理
		}

	}

	private List<String> queryPosNumberByBusinessId(Integer businessId) {
		Map<String, Object> param = new HashMap<>();
		param.put("businessId", businessId);
		List<String> list = baseDao.queryForList("OrderBaseinfo.queryPosNumberByBusiness", param, String.class);
		if (list == null || list.size() == 0) {
			throw new RuntimeException("商户号[" + businessId + "]不存在对应的终端号");
		}
		return list;
	}

	public int getSendnowOrderListTotal(Map<String, Object> map) {
		return baseDao.queryForObject("OrderBaseinfo.getSendnowOrderTotal", map, Integer.class);
	}

	@Override
	@Transactional
	public int batchAddOrder(List<Map<String, Object>> totalMapList, Map<String, Object> cartInfoMap) throws Exception {
		int i = 0;
		boolean isAddSuccessfully = true;
		// 批量插入订单
		for (Map<String, Object> totalMap : totalMapList) {
			if (addOrder(totalMap)) {
				i++;
			} else {
				isAddSuccessfully = false;
			}
		}

		// 删除购物车商品
		if (isAddSuccessfully && cartInfoMap != null) {
			cartInfoService.updateCartItemByProductId(cartInfoMap);
		}
		return i;
	}

	@Override
	public int updateStatus(OrderBaseinfoDTO dto) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(dto.getMemberAddressId())) {
			params.put("memberAddressId", dto.getMemberAddressId());
		}
		if (StringUtils.isNotEmpty(dto.getOrderStatus())) {
			params.put("orderStatus", dto.getOrderStatus());
		}
		if (StringUtils.isNotEmpty(dto.getUpdateUserId())) {
			params.put("updateUserId", dto.getUpdateUserId());
		}
		if (StringUtils.isNotEmpty(dto.getCancelReason())) {
			params.put("cancelReason", dto.getCancelReason());
		}
		if (StringUtils.isNotEmpty(dto.getDeliverTimeStr())) {
			params.put("deliverTimeStr", dto.getDeliverTimeStr());
		}
		if (StringUtils.isNotEmpty(dto.getCloseTimeStr())) {
			params.put("closeTimeStr", dto.getCloseTimeStr());
		}
		return baseDao.execute("OrderBaseinfo.callBackUpdateStatus", params);
	}

	/**
	 * 退款并写入退款订单记录
	 * 
	 * @param params 退款参数
	 * @param payUrl 退款地址
	 * @param isManage 退款来源
	 * @return
	 * @throws RefundException
	 */
	@Override
	public RefundResponseDTO orderRefund(Map<String, String> params,boolean isManage) throws RefundException {
		RefundResponseDTO dto = new RefundResponseDTO();
		// 添加退款记录
		addRefundRecord(params,isManage);
		// 更新订单为关闭
		updateOrderStatus(params, EOrderStatus.CANNEL.getCode(),isManage);
		// 退预付款
		dto = prepayRefund(params, gdProperties.getPayCenterRefundUrl());
		// 更新退款记录
		updateRefundRecord(params, dto);
		// 成功或走清分
		if (dto.getCode().equals(MsgCons.C_10000) || dto.getCode().equals(MsgCons.R_30000)) {
			// 管理后台退款需通知农速通
			if (isManage) {
				callBackStatusToNst(params, dto, gdProperties.getNstCallbackStatusUrl());
			}
			dto.setCode(MsgCons.C_10000);
		}
		return dto;
	}

	/**
	 * 更新订单状态
	 * @param params 退款参数
	 * @param status 订单状态
	 * @param isManage 是否为管理后台退款
	 * @throws RefundException
	 */
	private void updateOrderStatus(Map<String, String> params, String status,boolean isManage) throws RefundException {
		try {
			OrderBaseinfoDTO entity = new OrderBaseinfoDTO();
			entity.setOrderNo(Long.parseLong(params.get("orderNo")));
			entity.setOrderStatus(status);
			// 如果状态为8关闭 添加关闭时间
			if (EOrderStatus.CANNEL.getCode().equals(status)) {
				entity.setCloseTimeStr(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME));
			}
			if (isManage) {
				entity.setCancelReason("已拒收");
			} else {
				entity.setCancelReason(params.get("refundReason"));
			}
			baseDao.execute("OrderBaseinfo.updateByOrderNo", entity);
		} catch (Exception e) {
			logger.info("更新订单状态失败：" + e.getMessage() + "");
			throw new RefundException(MsgCons.C_20010, MsgCons.M_20010);
		}
	}

	/**
	 * 更新退款记录
	 * 
	 * @param params
	 * @param dto
	 * @throws RefundException
	 */
	private void updateRefundRecord(Map<String, String> params, RefundResponseDTO dto) throws RefundException {
		OrderRefundRecordDTO refundDto = new OrderRefundRecordDTO();
		try {
			refundDto.setRefundNo(dto.getRefundNo());
			refundDto.setOrderNo(Long.parseLong(params.get("orderNo")));
			refundDto.setUpdateUserId(params.get("refundUserId"));
			refundDto.setAmount(Double.valueOf(params.get("refundAmt")));
			if (dto.getCode().equals(MsgCons.C_10000) || dto.getCode().equals(MsgCons.R_30000)) {
				refundDto.setStatus("3");
				refundDto.setRefundTimeStr(dto.getRefundTime());
			} else {
				refundDto.setStatus("4");
			}
			orderRefundRecordService.update(refundDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("更新退款记录失败：" + e + "");
			throw new RefundException(MsgCons.C_20005, MsgCons.M_20005);
		}
	}

	/**
	 * 退预付款
	 * 
	 * @throws RefundException
	 */
	private RefundResponseDTO prepayRefund(Map<String, String> params, String payUrl) throws RefundException {
		String result = "";
		try {

			RefundResponseDTO dto = new RefundResponseDTO();
			// String result="";
			logger.info("退款参数：" + params + " payUrl:" + payUrl);
			result = HttpClients.doPost(payUrl, params);
			logger.info("退款结果：" + result);
			if (StringUtils.isNotEmpty(result)) {
				dto = (RefundResponseDTO) GSONUtils.fromJsonToObject(result, RefundResponseDTO.class);
				return dto;
			} else {
				throw new RefundException(MsgCons.C_20006, MsgCons.M_20006);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("退款参数：" + params + " payUrl:" + payUrl + " 退款结果：" + result + " 退预付款失败：" + e + "");
			throw new RefundException(MsgCons.C_20006, MsgCons.M_20006);
		}
	}

	/**
	 * 添加退款记录
	 * 
	 * @param param
	 * @param type
	 * @throws Exception
	 */
	private void addRefundRecord(Map<String, String> params,boolean isManage) throws RefundException {
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderNo", params.get("orderNo"));
		int count;
		try {
			count = orderRefundRecordService.getTotal(totalMap);

			if (count <= 0) {
				OrderRefundRecordEntity entity = new OrderRefundRecordEntity();
				entity.setAmount(Double.parseDouble(params.get("refundAmt")));
				entity.setOrderNo(Long.parseLong(params.get("orderNo")));
				entity.setType("1");// 原路返回
				String userId = params.get("refundUserId");

				if (isManage) {
					entity.setCreateUserId(userId);// 管理后台存创建人
				} else {
					entity.setMemberId(userId);// APP退款存该字段，不存创建人
				}
				entity.setReason(params.get("refundReason"));
				entity.setStatus("1");// 待退款

				entity.setCreateTime(new Date());
				orderRefundRecordService.insert(entity);
			}
		} catch (Exception e) {
			logger.info("添加退款记录失败：" + e.getMessage() + "");
			throw new RefundException(MsgCons.C_20004, MsgCons.M_20004);
		}
	}

	/**
	 * 状态通知农速通
	 * 
	 * @param dto
	 * @throws RefundException
	 */
	private void callBackStatusToNst(Map<String, String> params, RefundResponseDTO dto, String nstStatusUrl)
			throws RefundException {
		try {
			// 获取运单号
			OrderDeliveryAddressDTO deliveryDTO = orderDeliveryAddressService.getByOrderNo(params.get("orderNo"));
			String paramValue = null;
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderNo", deliveryDTO.getNstOrderNo());
			logger.info("调用农速通接口参数：" + paramMap + " url:" + nstStatusUrl);
			paramValue = Des3.encode(new Gson().toJson(paramMap));
			String apiResult = HttpClients.doPost(nstStatusUrl, paramValue);
			// String sNstApiResult = "1";
			if (StringUtils.isNotBlank(apiResult)) {
				// 得到农速通接口结果
				String resultJson = Des3.decode(apiResult);
				logger.info("调用农速通接口结果：" + resultJson + "");
				NstCallbackDTO callbackDTO = (NstCallbackDTO) GSONUtils.fromJsonToObject(resultJson,
						NstCallbackDTO.class);
				if (callbackDTO.getCode() != 10000) {
					throw new RefundException(MsgCons.C_20007, MsgCons.M_20007);
				}
			}
			// 调用农速通接口
		} catch (Exception e) {
			logger.info("调用农速通接口失败：" + e + "");
			throw new RefundException(MsgCons.C_20007, MsgCons.M_20007);
		}
	}

	@Override
	@Transactional
	public int updateExpireOrderAdvance() throws Exception {
		int result = 0;
		Map<String, Object> map = new HashMap<>();
		List<OrderDeliveryAddressDTO> totalList = baseDao.queryForList("OrderBaseinfo.getExpireOrderAdvance", map,
				OrderDeliveryAddressDTO.class);
		Map<String, List<String>> totalMap = getOrderNoOrNstOrderNo(totalList);
		List<String> list = totalMap.get("orderNoList");
		List<String> nstOrderNoList = totalMap.get("nstOrderNoList");
		// 更新订单表
		if (CollectionUtils.isNotEmpty(list)) {
			map.put("orderNos", list);
			map.put("orderStatus", 8); // 8代表关闭
			map.put("cancelReason", "预付款支付超时");
			map.put("updateUserId", "system");
			map.put("closeUserId", "system");
			// 更新订单表的状态为已取消
			result = (int) baseDao.execute("OrderBaseinfo.callExpireOrderAdvance", map);
			// 更新产品库存
			updateProductStockCount(list);
		}
		// 将运单号传给农速通
		if (CollectionUtils.isNotEmpty(nstOrderNoList)) {
			map.clear();
			map.put("orderNos", StringUtils.join(nstOrderNoList.toArray(), ","));
			String paramsValue = Des3.encode(new Gson().toJson(map));
			// 调用农速通接口，更新货运订单接口
			String resultStr = HttpClients.doPost(gdProperties.getNstPayPrePaymenOverdueUrl(), paramsValue);
			logger.info("===============调用农速通接口返回信息" + Des3.decode(resultStr));

		}
		return result;
	}

	/**
	 * 循环获取订单号和运单号
	 */
	public Map<String, List<String>> getOrderNoOrNstOrderNo(List<OrderDeliveryAddressDTO> totalList) throws Exception {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> orderNoList = new ArrayList<String>();
		List<String> nstOrderNoList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(totalList)) {
			for (OrderDeliveryAddressDTO dto : totalList) {
				if (dto.getOrderNo() != null) {
					orderNoList.add(dto.getOrderNo().toString());
				}
				if (StringUtils.isNotBlank(dto.getNstOrderNo())) {
					nstOrderNoList.add(dto.getNstOrderNo());
				}
			}
		}
		map.put("orderNoList", orderNoList);
		map.put("nstOrderNoList", nstOrderNoList);
		return map;
	}

	/**
	 * 根据订单号退回产品库存
	 */
	public void updateProductStockCount(List<String> orderNoList) throws Exception {
		Map<String, Object> map = null;
		for (String orderNo : orderNoList) {
			// 根据订单号查询当前订单所有的产品详细记录
			map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			List<OrderProductDetailDTO> orderProductList = baseDao.queryForList("OrderBaseinfo.queryOrderProductInfo",
					map, OrderProductDetailDTO.class);
			if (CollectionUtils.isNotEmpty(orderProductList)) {
				for (OrderProductDetailDTO dto : orderProductList) {
					if (dto.getProductId() != null && dto.getPurQuantity() != null) {
						map.put("stockCount", dto.getPurQuantity());
						map.put("productId", dto.getProductId());
						map.put("maxCount", Constant.MAX_PRODUCT_STOCK);
						// 将关闭的订单中的产品数量加到原产品库存上面,如果超过最大库存则设置为最大库存
						baseDao.execute("OrderBaseinfo.updateStockCount", map);
					}
				}
			}
		}
	}

	/**
	 * 退预付款参数处理
	 * 
	 * @param params
	 * @return
	 * @throws RefundException
	 */
	@Override
	public RefundResponseDTO preOrderRefund(Map<String, String> params) throws RefundException {
		boolean isManage = false;
		if (StringUtils.isNotEmpty(params.get("source"))) {
			isManage = true;
			params.remove("source");
		}
		String link = AccessSysSignUtil.createLinkString(AccessSysSignUtil.paraFilter(params));
		String sign = AccessSysSignUtil.sign(link, RsaUtil.KEYTYPE, gdProperties.getPayCenterKey());
		String encodeSign = Base64.encode(sign.getBytes());
		params.put("sign", encodeSign);
		logger.info("调用退款接口参数：" + params.toString() + "");
		RefundResponseDTO result = orderRefund(params,isManage);
		logger.info(result.getRefundNo() + "调用退款接口结果：code:" + result.getCode() + " msg:" + result.getMsg());
		return result;
	}

	/**
	 * 支付中心退款记录查询
	 */
	@Override
	public RefundResponseLogResult getOrderRefundLog(Map<String, String> params) throws Exception {
		try {
			RefundResponseLogResult dto = new RefundResponseLogResult();
			logger.info("退预付款日志查询参数：" + params + "");
			String result = HttpClients.doPost(gdProperties.getPayCenterRefundSearchUrl(), params);
			logger.info("退预付款日志查询结果：" + result + "");
			if (StringUtils.isNotEmpty(result)) {
				ServiceResponseDTO serviceResult = (ServiceResponseDTO) GSONUtils.fromJsonToObject(result,
						ServiceResponseDTO.class);
				if (serviceResult.getCode().equals(MsgCons.C_10000)) {
					dto = serviceResult.getResult();
				}
				return dto;
			}
		} catch (Exception e) {
			logger.info("退预付款日志查询失败：" + e + "");
			throw new RefundException(MsgCons.C_20009, MsgCons.M_20009);
		}
		return null;
	}

	@Override
	@Transactional
	public String updateOrderForCheckBill(Map<String, Object> params) throws Exception {
		/*
		 * 修改对应订单的状态
		 */
		OrderBaseinfoDTO entity = new OrderBaseinfoDTO();
		entity.setOrderNo(Long.valueOf(String.valueOf(params.get("orderNo"))));
		entity.setPayType("2");// 线下刷卡
		entity.setOrderStatus(EOrderStatus.PAYED.getCode());
		updateByOrderNo(entity);
		addPaySerialnumberForCheckBill(params);
		return "success";
	}

	@Override
	@Transactional
	public String addOrderForCheckBill(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("bankCardNo", params.get("bankCardNo"));
		// 根据付款卡号查询会员信息
		MemberDTO memberDTO = baseDao.queryForObject("MemberInfo.queryPosBankCard", map, MemberDTO.class);
		map.clear();
		if (memberDTO == null) {
			String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			map.put("account", uuid);
			map.put("mobile", uuid);
			map.put("level", "3");
			map.put("status", "1");
			map.put("regetype", "7");
			map.put("nsyUserType", "0");
			map.put("memberGrade", "0");
			map.put("shopRecommend", "1");
			map.put("createUserId", params.get("updateUserId"));
			baseDao.execute("MemberInfo.insertMember", map);
			memberDTO = baseDao.queryForObject("MemberInfo.queryMember", map, MemberDTO.class);
			map.clear();
			map.put("bankCardNo", params.get("bankCardNo"));
			map.put("memberId", memberDTO.getMemberId());
			map.put("createUserId", params.get("updateUserId"));
			baseDao.execute("MemberInfo.insertPosBankCard", map);
		}
		Long buyerMemberId = memberDTO.getMemberId();
		map.clear();
		map.put("posNumber", params.get("posNumber"));
		memberDTO = baseDao.queryForObject("MemberInfo.queryBusinessInfo", map, MemberDTO.class);
		Long orderNo = new Long(orderNoService.getOrderNo());
		// 订单信息实体类
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		// 新增订单
		orderEntity.setOrderStatus("3");// 已付款
		orderEntity.setOrderNo(orderNo);
		// 订单来源 3 POS机
		orderEntity.setOrderSource(EOrderSource.PAY_ORDER.getCode());
		orderEntity.setChannel("4");// 渠道 1android 2ios 3pc 4pos
		orderEntity.setOrderAmount(Double.valueOf(String.valueOf(params.get("payAmt"))));
		orderEntity.setDiscountAmount(0d);
		orderEntity.setPayAmount(Double.valueOf(String.valueOf(params.get("payAmt"))));
		orderEntity.setTotalPayAmt(Double.valueOf(String.valueOf(params.get("payAmt"))));
		// 需要计算拥金 调用爱兵写的接口

		orderEntity.setPayType("2");// 线下刷卡
		orderEntity.setSellMemberId(memberDTO.getSellerMemberId().intValue());// 卖家id
		// 订单中心POS刷卡来源的订单，创建时间需比成交时间早60~100秒（随机增加）
		long td = DateUtils.parseDate(String.valueOf(params.get("payTime")), "yyyy-MM-dd HH:mm:ss").getTime();
		Random rand = new Random();
		int rt = rand.nextInt(40);
		Date orderTime = new Date();
		orderTime.setTime(td - (rt + 60) * 1000); // 交易时间

		orderEntity.setOrderTime(orderTime);
		orderEntity.setShopName(memberDTO.getShopsName());
		orderEntity.setBusinessId(memberDTO.getBusinessId().intValue());
		orderEntity.setMarketId(memberDTO.getMarketId().intValue());

		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");

		orderEntity.setOrderType("1");// 农商友采购订单
		orderEntity.setPromType("0");
		orderEntity.setMemberId(buyerMemberId.intValue());// 买家id
		orderEntity.setCreateUserId(buyerMemberId.intValue() + "");
		map.clear();
		map.put("businessId", memberDTO.getBusinessId());
		List<Integer> list = baseDao.queryForList("MemberInfo.queryBusinessIds", map, Integer.class);
		if (list != null && list.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (Integer id : list) {
				sb.append(id).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			orderEntity.setValidPosNum(sb.toString());
		}
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderBase", orderEntity);
		// 增加订单
		addOrder(totalMap);
		return String.valueOf(orderNo);
	}

	@Override
	public String addPaySerialnumberForCheckBill(Map<String, Object> params) throws Exception {
		PaySerialnumberEntity paySerialnumberEntity = new PaySerialnumberEntity();
		paySerialnumberEntity.setCreateTime(new Date());
		paySerialnumberEntity.setUpdatetime(new Date());
		paySerialnumberEntity.setOrderNo(Long.valueOf(String.valueOf(params.get("orderNo"))));
		paySerialnumberEntity.setPayStatus("1");
		paySerialnumberEntity.setStatementId(String.valueOf(params.get("payCenterNumber")));
		paySerialnumberEntity.setTradeAmount(Double.valueOf(String.valueOf(params.get("payAmt"))));
		paySerialnumberEntity.setPayType("2"); // 线下刷卡
		if (StringUtils.equals(String.valueOf(params.get("payType")), EPaySubType4Stament.GXRCB.getChannel())) {
			paySerialnumberEntity.setPaySubType(EPaySubType4Stament.GXRCB.getCode());
			paySerialnumberEntity.setPayAreaId("450000");
		} else if (StringUtils.equals(String.valueOf(params.get("payType")), EPaySubType4Stament.NNCCB.getChannel())) {
			paySerialnumberEntity.setPaySubType(EPaySubType4Stament.NNCCB.getCode());
			paySerialnumberEntity.setPayAreaId("450100");
		}
		paySerialnumberEntity.setPaymentAcc(String.valueOf(params.get("thirdPayerAccount")));
		paySerialnumberEntity.setRecipientAcc(String.valueOf(params.get("thirdPayeeAccount")));
		paySerialnumberEntity
				.setPayTime(DateUtils.parseDate(String.valueOf(params.get("payTime")), "yyyy-MM-dd HH:mm:ss"));
		paySerialnumberEntity.setThirdStatementId(String.valueOf(params.get("thirdPayNumber")));
		paySerialnumberEntity.setPosNumber(String.valueOf(params.get("posNumber")));
		if (params.get("payerUserId") != null) {
			paySerialnumberEntity.setMemberId(Integer.valueOf(String.valueOf(params.get("payerUserId"))));
		}
		/*
		 * 添加支付流水信息
		 */
		paySerialnumberService.insertEntity(paySerialnumberEntity);
		return "success";
	}

	@Override
	@Transactional
	public String dealAdvanceAndPayment(List<OrderAdvanceAndPaymentDTO> list) throws Exception {
		Map<String, Object> map = null;
		int result = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			for (OrderAdvanceAndPaymentDTO dto : list) {
				logger.info("支付中心传过来的对象：" + dto);
				map = new HashMap<>();
				map.put("type", dto.getType());
				map.put("orderNo", dto.getOrderNo());
				map.put("payAmt", dto.getPayAmt());
				map.put("updateUserId", dto.getUpdateUserId());
				// 如果是预付款，需要更新order_baseinfo表和order_fee_item_detail表,如果是尾款只更新订单表
				// 如果是金牌供应商更新实收金额和状态
				if (Constant.ORDER_TYPE_ADVANCE.equals(dto.getType())) {
					map.put("feeType", "3"); // feetype：3代表预付款
					map.put("payerType", "nsy"); // 农商友
					map.put("payeeType", "platfm"); // 平台
					baseDao.execute("OrderBaseinfo.updateAdvance", map);
				}
				map.put("orderStatus", "10"); // 订单状态：10已完成
				result += baseDao.execute("OrderBaseinfo.updateAdvanceAndPayment", map);
				/*
				 * 添加支付流水信息
				 */
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("statementId", dto.getPayCenterNumber());
				paramMap.put("orderNo", dto.getOrderNo());
				int count = paySerialnumberService.getTotal(paramMap);
				if (count == 0) {
					PaySerialnumberEntity paySerialnumberEntity = new PaySerialnumberEntity();
					paySerialnumberEntity.setCreateTime(new Date());
					paySerialnumberEntity.setUpdatetime(new Date());
					paySerialnumberEntity.setOrderNo(Long.valueOf(dto.getOrderNo()));
					paySerialnumberEntity.setPayStatus("1");
					paySerialnumberEntity.setStatementId(dto.getPayCenterNumber());
					paySerialnumberEntity.setTradeAmount(dto.getPayAmt());
					paySerialnumberEntity.setPayType(dto.getPayType());
					paySerialnumberEntity.setPaymentAcc(dto.getThirdPayerAccount());
					paySerialnumberEntity.setRecipientAcc(dto.getThirdPayeeAccount());
					paySerialnumberEntity.setPayTime(dto.getPayTime());
					paySerialnumberEntity.setThirdStatementId(dto.getThirdPayNumber());
					paySerialnumberEntity.setMemberId(Integer.parseInt(dto.getPayerUserId()));
					if (StringUtils.isBlank(dto.getType()) || !"0".equals(dto.getType())) {
						paySerialnumberEntity.setPayBank(dto.getPayBank());
						paySerialnumberEntity.setSerialType(dto.getType());
					}
					paySerialnumberService.insertEntity(paySerialnumberEntity);
				} else {
					paramMap.clear();
					paramMap.put("updateuserid", dto.getUpdateUserId());
					paramMap.put("tradeAmount", dto.getPayAmt());
					paramMap.put("orderNo", Long.valueOf(dto.getOrderNo()));
					paramMap.put("statementId", dto.getPayCenterNumber());
					paySerialnumberService.updateTradeAmount(paramMap);
				}
			}
		}
		return result > 0 ? "success" : "fail";
	}

	@Override
	public ClearingLogDTO getSellerFeeDetail(Map<String, String> params) {
		String result = null;
		String url = gdProperties.getPayCenterClearUrl();
		ClearingLogDTO dto = new ClearingLogDTO();
		try {
			logger.info("清分查询请求参数：" + params + " url:" + url);
			result = HttpClients.doPost(url, params);
			logger.info("清分查询返回结果：" + result);
			if (StringUtils.isNotEmpty(result)) {
				dto = (ClearingLogDTO) GSONUtils.fromJsonToObject(result, ClearingLogDTO.class);
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("清分查询请求参数：" + params + " url:" + url + " 查询结果：" + result + " 失败原因：" + e + "");
		}
		return dto;
	}

	@Override
	public int batchUpdateStockCount(List<Map<String, Object>> stockList) {
		// 更新产品库存
		if (stockList != null && stockList.size() > 0) {
			int len = stockList.size();
			Map<String, Object>[] batchParams = new Map[len];
			for (int i = 0; i < len; i++) {
				batchParams[i] = stockList.get(i);
			}
			return baseDao.batchUpdate("OrderBaseinfo.batchUpdateStockCount", batchParams).length;
		}
		return 0;
	}
    /**
     * 根据订单号获取订单基本信息和详情信息
     */
	@Override
	public OrderBaseinfoDTO getBaseOrderInfoById(Long orderNo) throws Exception {
		Map<String,Object> map=new HashMap<>();
		map.put("orderNo", orderNo);
		OrderBaseinfoDTO  obd = (OrderBaseinfoDTO) this.baseDao.queryForObject("OrderBaseinfo.queryOrderBaseInfoByCondition", map,
				OrderBaseinfoDTO.class);
		if (null!=obd) {
			List<OrderProductDetailDTO> orderProductList = baseDao.queryForList("OrderBaseinfo.queryOrderProductInfo",
					map, OrderProductDetailDTO.class);
			obd.setDetailList(orderProductList);
		}
		
		return obd;
	}

	@Override
	public List<OrderBaseinfoDTO> getOrderByCondition(Map<String, Object> pareMap) throws Exception {
		
		List<OrderBaseinfoDTO> orders = baseDao.queryForList("OrderBaseinfo.queryOrderBaseInfoByCondition", pareMap,
				OrderBaseinfoDTO.class);
		return orders;
	}
}
