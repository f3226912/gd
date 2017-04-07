package com.gudeng.commerce.gd.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.exception.OrderOutmarketException;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.enm.ESubStatus;
import com.gudeng.commerce.gd.order.enm.ETaskStatus;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;
import com.gudeng.commerce.gd.order.entity.ReOrderOutmarkEntity;
import com.gudeng.commerce.gd.order.entity.SalToshopsDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderOutmarketinfoService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.order.service.TaskService;
import com.gudeng.commerce.gd.order.util.MathUtil;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class OrderOutmarketinfoServiceImpl implements OrderOutmarketinfoService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private PaySerialnumberService paySerialnumberService;
	
	@Override
	public Long insertEntity(OrderOutmarketinfoEntity obj) throws Exception {
		return (Long)baseDao.persist(obj, Long.class);
	}
	
	@Override
	public Integer insertSQL(OrderOutmarketinfoEntity obj) throws Exception {
		return (Integer)baseDao.execute("OrderOutmarketinfo.insert", obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("omId", id);
		return (Integer)baseDao.execute("OrderOutmarketinfo.delete", param);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count=0;
		Map<String, Object> map = new HashMap<>();
		map.put("idList", idList);
		count = baseDao.execute("OrderOutmarketinfo.batchDeleteById", map);
		if(count<=0) {
			throw new Exception("批量更新记录失败！dto:" + idList);
		}
		return count;
	}

	@Override
	public int updateDTO(OrderOutmarketInfoDTO obj) throws Exception {
		return (Integer)baseDao.execute("OrderOutmarketinfo.updateDTO", obj);
	}

	@Override
	public int batchUpdateDTO(List<OrderOutmarketInfoDTO> objList) throws Exception {
		int count=0;
		for(OrderOutmarketInfoDTO dto:objList){
			count = baseDao.execute("OrderOutmarketinfo.updateDTO", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (Integer)baseDao.queryForObject("OrderOutmarketinfo.getTotal", map,Integer.class);
	}

	@Override
	public OrderOutmarketInfoDTO getById(Long id) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("omId", id);
		return (OrderOutmarketInfoDTO)baseDao.queryForObject("OrderOutmarketinfo.getById", param,OrderOutmarketInfoDTO.class);
	}

	@Override
	public List<OrderOutmarketInfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return (List<OrderOutmarketInfoDTO>)baseDao.queryForList("OrderOutmarketinfo.getListByConditionPage", map);
	}

	
	@Override
	@Transactional
	public int insertOrderOutmark(String orderNoList, String omId) throws Exception {
		if(StringUtils.isBlank(orderNoList)){
			return -1;
		}
		String[] orderList=orderNoList.split(",");
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> orderMap = new HashMap<String, Object>();
		for (String orderNo : orderList) {
			map.put("orderNo", Long.parseLong(orderNo));
			map.put("omId", omId);
			baseDao.execute("OrderOutmarketinfo.insertOrderOutmark", map);
			
			/*更改order_baseinfo表订单出场状态*/
			orderMap.put("orderNo", Long.parseLong(orderNo));
			orderMap.put("outmarketStatus", 1);//出场
			baseDao.execute("OrderOutmarketinfo.updateOrderOutmarkStatus", orderMap);
		}
		return 1;
	}

	@Override
	public List<OrderBaseinfoDTO> getOrderInfoByOmId(Long omId)
			throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("omId", omId);
		return (List<OrderBaseinfoDTO>)baseDao.queryForList("OrderOutmarketinfo.getOrdersByOmId", param,OrderBaseinfoDTO.class);
	}
	
	@Override
	@Transactional
	public Long shipperOutMarket(OrderOutmarketinfoEntity outmarketEntity, List<String> productIdList) {
		Long outmarketId = (Long) baseDao.persist(outmarketEntity, Long.class);
		if(productIdList != null){
			for(String productId : productIdList){
				/**出场产品*/
				Map<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("id", Long.parseLong(productId));
				PreWeighCarDetailDTO dto = (PreWeighCarDetailDTO) baseDao.queryForObject("PreWeighCarDetail.getById", productMap, PreWeighCarDetailDTO.class);
				if(dto != null){
					SalToshopsDetailEntity detailEntity = new SalToshopsDetailEntity();
					detailEntity.setOutmarketId(outmarketId);
					detailEntity.setProductId(Long.parseLong(productId));
					detailEntity.setProductName(dto.getProductName());
					detailEntity.setPurQuantity(dto.getPurQuantity());
					/*计算卖出产品重量*/
					double weigh = dto.getWeigh() == null ? 0 : dto.getWeigh();
					double marginWeigh = dto.getMarginWeigh() == null ? 0 : dto.getMarginWeigh();
					detailEntity.setWeigh(MathUtil.sub(weigh, marginWeigh));
					detailEntity.setCreateTime(new Date());
					detailEntity.setUpdateTime(new Date());
					baseDao.persist(detailEntity, Long.class);
				}
			}
		}
		/**过磅记录状态修改为已出场*/
		Map<String, Object> weighCarMap = new HashMap<String, Object>();
		weighCarMap.put("weighCarId", outmarketEntity.getWeighCarId());
		weighCarMap.put("status", "2");
		baseDao.execute("WeighCar.updateStatusByWeighCarId", weighCarMap);
		return outmarketId;
	}

	@Override
	public OrderOutmarketInfoDTO queryOrderOutmarkCarInfoByOrderNo(OrderOutmarketInfoDTO outmarketInfo) throws ServiceException {
		return (OrderOutmarketInfoDTO) baseDao.queryForObject("OrderOutmarketinfo.queryOrderOutmarkCarInfoByOrderNo", outmarketInfo, OrderOutmarketInfoDTO.class);
	}

	@Override
	public List<OrderOutmarketInfoDTO> queryOrderOutmarkCarInfoByCarNumber(OrderOutmarketInfoDTO outmarketInfo) throws ServiceException {
		return (List<OrderOutmarketInfoDTO>) baseDao.queryForList("OrderOutmarketinfo.queryOrderOutmarkCarInfoByCarNumber", outmarketInfo, OrderOutmarketInfoDTO.class);
	}

	@Override
	public int updateCarNumberImage(OrderOutmarketinfoEntity entity) {
		return baseDao.execute("OrderOutmarketinfo.updateCarNumberImage", entity);
	}
	
	@Override
	@Transactional
	public Long purchaserOutMarket(OrderOutmarketinfoEntity outMarketEntity,List<String> orderNoList) throws Exception{
		Long outMarketId = (Long) baseDao.persist(outMarketEntity, Long.class);
		if(orderNoList != null){
			Map<String, Object> orderStatusMap = new HashMap<String, Object>();
			Map<String, Long> orderMap = new HashMap<String, Long>();
			for(String orderNo : orderNoList){
				orderMap.put("orderNo", Long.parseLong(orderNo));
				OrderBaseinfoDTO orderinfoDTO = (OrderBaseinfoDTO)this.baseDao.queryForObject("OrderBaseinfo.getByOrderNo", orderMap, OrderBaseinfoDTO.class);
				/**0:未出场订单;1:已出场订单*/
				if (orderinfoDTO!=null && "1".equals(orderinfoDTO.getOutmarkStatus())) {
					throw new OrderOutmarketException("出场失败，订单号："+orderNo+"订单已出场");
				}
				
				if(orderinfoDTO != null){
					/**出场记录与出场订单关联*/
					ReOrderOutmarkEntity reOrderOutmarketEnt = new ReOrderOutmarkEntity(); 
					reOrderOutmarketEnt.setOmId(outMarketId);
					reOrderOutmarketEnt.setOrderNo(Long.parseLong(orderNo));
					baseDao.execute("ReOrderOutmarket.insertEntity", reOrderOutmarketEnt);
					
					/**修改order_baseinfo表订单出场状态*/
					orderStatusMap.put("orderNo", Long.parseLong(orderNo));
					orderStatusMap.put("outmarketStatus", 1);//出场
					baseDao.execute("OrderOutmarketinfo.updateOrderOutmarkStatus", orderStatusMap);
					
//					/**1、加计算补贴金额任务; 2、添加补贴限制规则任务） */
//					if(ESubStatus.WAIT_OUT_CAR.getCode().equals(orderinfoDTO.getBuySubStatus()) || ESubStatus.WAIT_OUT_CAR.getCode().equals(orderinfoDTO.getSellSubStatus())){
//						taskService.addTask(TaskDTO.getCalcSubAmtTask(orderNo));
//					}
//					if(!(ESubStatus.NOT_SUB.getCode().equals(orderinfoDTO.getBuySubStatus()) && ESubStatus.NOT_SUB.getCode().equals(orderinfoDTO.getSellSubStatus()))) {
//						TaskDTO task = TaskDTO.getSubLimitRuleTask(orderNo);
//						//支付方式
//						String payType = orderinfoDTO.getPayType();
//						if(EPayType.OFFLINE_CARD.getCode().equals(payType) || EPayType.ACC_BALANCE_AND_OFFLINE_CARD.getCode().equals(payType)){
//							task.setTaskStatus(ETaskStatus.LOCK.getCode());
//							PaySerialnumberDTO paySerialnumberDTO = paySerialnumberService.getByOrderNoAndPayType(Long.parseLong(orderNo));
//							if(paySerialnumberDTO != null){
//								task.setPayTime(paySerialnumberDTO.getUpImageTime());
//							}
//						}
//						taskService.addTask(task);
//					}
				}
			}
		}
		/**过磅记录状态修改为已出场*/
		if(outMarketEntity.getWeighCarId() != null){
			Map<String, Object> weighCarMap = new HashMap<String, Object>();
			weighCarMap.put("weighCarId", outMarketEntity.getWeighCarId());
			weighCarMap.put("status", "2");
			baseDao.execute("WeighCar.updateStatusByWeighCarId", weighCarMap);
		}
		return outMarketId;
	}
	
	@Override
	@Transactional
	public Long purchaserOutMarketV2(OrderOutmarketinfoEntity outMarketEntity,List<OrderBaseinfoDTO> orderList) throws Exception{
		if(CollectionUtils.isEmpty(orderList)){
			return 0L;
		}
		
		//插入出场记录
		Long outMarketId = (Long) baseDao.persist(outMarketEntity, Long.class);
		
		Map<String, Object> orderStatusMap = new HashMap<String, Object>();
		for(OrderBaseinfoDTO orderinfoDTO : orderList){
			if(orderinfoDTO == null){
				continue;
			}
			/**修改order_baseinfo表订单出场状态*/
			orderStatusMap.put("orderNo", orderinfoDTO.getOrderNo());
			orderStatusMap.put("outmarketStatus", 1);//出场
			baseDao.execute("OrderOutmarketinfo.updateOrderOutmarkStatus", orderStatusMap);
			
			//出场记录与出场订单关联
			ReOrderOutmarkEntity reOrderOutmarketEnt = new ReOrderOutmarkEntity(); 
			reOrderOutmarketEnt.setOmId(outMarketId);
			reOrderOutmarketEnt.setOrderNo(orderinfoDTO.getOrderNo());
			baseDao.execute("ReOrderOutmarket.insertEntity", reOrderOutmarketEnt);
			
//			//补贴：1、加计算补贴金额任务; 2、添加补贴限制规则任务
			String orderNo = orderinfoDTO.getOrderNo() == null ? "" : String.valueOf(orderinfoDTO.getOrderNo());
//			if(ESubStatus.WAIT_OUT_CAR.getCode().equals(orderinfoDTO.getBuySubStatus()) || ESubStatus.WAIT_OUT_CAR.getCode().equals(orderinfoDTO.getSellSubStatus())){
//				taskService.addTask(TaskDTO.getCalcSubAmtTask(orderNo));
//			}
//			if(!(ESubStatus.NOT_SUB.getCode().equals(orderinfoDTO.getBuySubStatus()) && ESubStatus.NOT_SUB.getCode().equals(orderinfoDTO.getSellSubStatus()))) {
//				TaskDTO task = TaskDTO.getSubLimitRuleTask(orderNo);
//				//支付方式
//				String payType = orderinfoDTO.getPayType();
//				if(EPayType.OFFLINE_CARD.getCode().equals(payType) || EPayType.ACC_BALANCE_AND_OFFLINE_CARD.getCode().equals(payType)){
//					task.setTaskStatus(ETaskStatus.LOCK.getCode());
//					PaySerialnumberDTO paySerialnumberDTO = paySerialnumberService.getByOrderNoAndPayType(Long.parseLong(orderNo));
//					if(paySerialnumberDTO != null){
//						task.setPayTime(paySerialnumberDTO.getUpImageTime());
//					}
//				}
//				taskService.addTask(task);
//			}
		}
		/**过磅记录状态修改为已出场*/
		Map<String, Object> weighCarMap = new HashMap<String, Object>();
		weighCarMap.put("weighCarId", outMarketEntity.getWeighCarId());
		weighCarMap.put("status", "2");
		baseDao.execute("WeighCar.updateStatusByWeighCarId", weighCarMap);
		
		return outMarketId;
	}


	@Override
	public List<OrderOutmarketInfoDTO> getListByMap(Map<String, Object> queryMap) throws ServiceException {
		return baseDao.queryForList("OrderOutmarketinfo.getListByMap", queryMap, OrderOutmarketInfoDTO.class);
	}

	@Override
	public List<OrderOutmarketInfoDTO> getPageByCreateUserId(Map<String, Object> query) {
		List<OrderOutmarketInfoDTO> list = baseDao.queryForList("OrderOutmarketinfo.getPageByCreateUserId", query, OrderOutmarketInfoDTO.class);
		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		for(OrderOutmarketInfoDTO dto : list){
			paramMap.put("outmarketId", dto.getOmId());
			List<Long> orderNoList = baseDao.queryForList("OrderOutmarketinfo.getOrderNoListByOutmarketId", paramMap, Long.class);
			dto.setOrderNoList(orderNoList);
		}
		return list;
	}

	@Override
	public Long getTotalCountByCreateUserId(Map<String, Object> query) {
		return  (Long) baseDao.queryForObject("OrderOutmarketinfo.getTotalCountByCreateUserId", query, Long.class);
	}
	
}
