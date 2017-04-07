package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.dto.TransferListDTO;
import com.gudeng.commerce.gd.admin.service.OrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoSendnowDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class OrderBaseinfoToolServiceImpl implements OrderBaseinfoToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static OrderBaseinfoService orderBaseinfoService;
	
	private OrderBaseinfoService gethessianOrderBaseinfoService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getOrderBaseinfoUrl();
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, hessianUrl);
		}
		return orderBaseinfoService;
	}

	@Override
	public Long insertEntity(OrderBaseinfoEntity obj) throws Exception {
		return gethessianOrderBaseinfoService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianOrderBaseinfoService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianOrderBaseinfoService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(OrderBaseinfoDTO obj) throws Exception {
		return gethessianOrderBaseinfoService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<OrderBaseinfoDTO> objList) throws Exception {
		return gethessianOrderBaseinfoService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getTotal(map);
	}

	@Override
	public OrderBaseinfoDTO getById(Long id) throws Exception {
		return gethessianOrderBaseinfoService().getById(id);
	}

	@Override
	@Transactional
	public List<OrderBaseinfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		/*gethessianOrderBaseinfoService().insertEntity(new OrderBaseinfoEntity());
		gethessianOrderBaseinfoService().deleteById(27l);*/
		return gethessianOrderBaseinfoService().getListByConditionPage(map);
	}

	@Override
	public List<OrderBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getListByCondition(map);
	}

	@Override
	@Transactional
	public int orderExamine(Integer payId, Long orderNo, String type,
			String description, String statementId, String updateUserId,
			String userName) throws Exception {
		return gethessianOrderBaseinfoService().orderExamine(payId, orderNo, type, description, statementId, updateUserId, userName);
	}

	@Override
	public OrderBaseinfoDTO getByOrderNo(Long orderNo) throws Exception {
		return gethessianOrderBaseinfoService().getByOrderNo(orderNo);
	}

	@Override
	public int getPageTotal(Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getPageTotal(map);
	}

	@Override
	public List<TransferListDTO> getListByPage(Map<String, Object> map)
			throws Exception {
		List<TransferListDTO> returnList = new ArrayList<>();
		List<OrderBaseinfoDTO> dataList = gethessianOrderBaseinfoService().getListByPage(map);
		if(dataList != null && dataList.size() > 0){
			Long memberId = (Long) map.get("memberId");
			for(int i=0, len=dataList.size(); i<len; i++){
				OrderBaseinfoDTO dataDTO = dataList.get(i);
				TransferListDTO returnDTO = new TransferListDTO();
				//卖家 收入
				if(dataDTO.getSellMemberId() == memberId.intValue()){
					returnDTO.setIncome(dataDTO.getPayAmount());
				}else{//买家 支出
					returnDTO.setExpense(dataDTO.getPayAmount());
				}
				returnDTO.setCreateFlowTime(dataDTO.getUpdateTime());
				returnList.add(returnDTO);
			}
		}
		return returnList;
	}

	@Override
	public int getOrderTotal(Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getOrderTotal(map); 
	}
	
	@Override
	public int getSuppOrderTotal(Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getSuppOrderTotal(map); 
	}
	
	@Override
	@Transactional
	public List<OrderBaseinfoDTO> getOrderListByConditionPage(
			Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getOrderListByConditionPage(map);
	}
	
	@Override
	@Transactional
	public List<OrderBaseinfoDTO> getSuppOrderListByConditionPage(
			Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getSuppOrderListByConditionPage(map);
	}

	@Override
	public List<OrderBaseinfoSendnowDTO> getSendnowOrderListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianOrderBaseinfoService().getSendnowOrderListByConditionPage(map);
	}

	@Override
	public String queryOderSameStatement(String persaleId) throws Exception {

		return gethessianOrderBaseinfoService().queryOderSameStatement(persaleId);
	}

	@Override
	public void saveSupplementOrder(String userId,String persaleId,String statementId) throws Exception {
		
		gethessianOrderBaseinfoService().saveSupplementOrder(userId, persaleId, statementId);
		
	}
	
}
