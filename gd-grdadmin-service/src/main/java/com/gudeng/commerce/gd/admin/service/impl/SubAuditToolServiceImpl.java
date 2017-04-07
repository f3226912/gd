package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.dto.SubAuditQueryBean;
import com.gudeng.commerce.gd.admin.service.SubAuditToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketImageDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.entity.SubAuditEntity;
import com.gudeng.commerce.gd.order.service.SubAuditService;


/** 
 *功能描述：
 */
@Service
public class SubAuditToolServiceImpl implements SubAuditToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static SubAuditService subAuditService;

	/**
	 * 功能描述: SubAuditService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected SubAuditService getHessianSubAuditService() throws MalformedURLException {
		String url = gdProperties.getSubAuditServiceUrl();
		if(subAuditService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			subAuditService = (SubAuditService) factory.create(SubAuditService.class, url);
		}
		return subAuditService;
	}

	@Override
	public List<SubAuditDTO> getBySearch(Map<String, Object> map)
			throws Exception {
		return getHessianSubAuditService().getBySearch(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianSubAuditService().getTotal(map);
	}
	
	
	@Override
	public SubAuditEntity getSubAuditById(Integer auditId) throws Exception {
		return subAuditService.getSubAuditById(auditId);
	}
	
	@Override
	public String updateSubStatusById(Map<String, Object> map) throws Exception {
		return subAuditService.updateSubStatusById(map);
	}
	
	
	/**
	 * @Todo 对“待补贴”状态的补贴记录进行批量审核通过
	 * -- 从数据库查出要更新状态的这些记录, 如果这些记录的当前状态都是“待补贴状态”， 则全部进行更新，其状态由"待补贴" ==> "已补贴",并返回"SUCCESS"
	 * -- 如果当前的这些记录有不是"待补贴"的，将这些记录的订单号以字符串的形式返回给用户
	 * -- 如果当前的总金额不足，返回错误信息给用户
	 * 
	 */
	public String batchUpdateSubStatusAsPass(List<Integer> idList, Map<String,Object> params) throws Exception {
		String result = subAuditService.batchUpdateSubStatusAsPass(idList, params);
		return result;
	}
	
	
	@Override
	public Map<String, Object> getParamsMap(SubAuditQueryBean sqb) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String subStatus =  sqb.getSubStatus();
		// 空值或0表示查询“全部补贴验证订单”
		if(subStatus!=null && !"".equals(subStatus) && !"0".equals(subStatus)) {
			map.put("subStatus", subStatus);
		}
		
		map.put("orderNo", sqb.getOrderNo().trim());
		map.put("orderAmount", sqb.getOrderAmount());
		map.put("payType", sqb.getPayType().trim());
		map.put("buyerName", sqb.getBuyerName().trim());
		map.put("buyerShop", sqb.getBuyerShop().trim());
		map.put("orderLike", sqb.getOrderLike().trim());
		return map;
	}
	
	@Override
	public OrderBaseinfoDTO convertorOfUI(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception {
		//翻译订单状态:订单状态 1待付款 2部分付款 3已付款 4已出场 8已取消 9已作废 10已完成
		if("1".equals(orderBaseinfoDTO.getOrderStatus())){
			orderBaseinfoDTO.setOrderStatus("待付款");
		}else if("2".equals(orderBaseinfoDTO.getOrderStatus())){
			orderBaseinfoDTO.setOrderStatus("部分付款");
		}else if("3".equals(orderBaseinfoDTO.getOrderStatus())){
			orderBaseinfoDTO.setOrderStatus("已付款");
		}else if("4".equals(orderBaseinfoDTO.getOrderStatus())){
			orderBaseinfoDTO.setOrderStatus("已出场");
		}else if("8".equals(orderBaseinfoDTO.getOrderStatus())){
			orderBaseinfoDTO.setOrderStatus("已取消");
		}else if("9".equals(orderBaseinfoDTO.getOrderStatus())){
			orderBaseinfoDTO.setOrderStatus("已作废");
		}else if("10".equals(orderBaseinfoDTO.getOrderStatus())){
			orderBaseinfoDTO.setOrderStatus("已完成");
		}
		
		//翻译付款方式:支付方式 1钱包余额 2线下刷卡 3现金 12钱包余额+线下刷卡 13钱包余额+现金
		if("1".equals(orderBaseinfoDTO.getPayType())){
			orderBaseinfoDTO.setPayType("钱包余额");
		}else if("2".equals(orderBaseinfoDTO.getPayType())){
			orderBaseinfoDTO.setPayType("线下刷卡");
		}else if("3".equals(orderBaseinfoDTO.getPayType())){
			orderBaseinfoDTO.setPayType("现金");
		}else if("12".equals(orderBaseinfoDTO.getPayType())){
			orderBaseinfoDTO.setPayType("钱包余额+线下刷卡");
		}else if("13".equals(orderBaseinfoDTO.getPayType())){
			orderBaseinfoDTO.setPayType("钱包余额+现金");
		}
		return orderBaseinfoDTO;
	}
	
	
	@Override
	public OrderOutmarketImageDTO getOutmarketImage(Long orderNo) throws Exception {
		return subAuditService.getOutmarketImage(orderNo);
	}
	
}
