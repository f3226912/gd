package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.OrderBillToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.dto.PosBankCardPayRecordDTO;
import com.gudeng.commerce.gd.order.service.OrderBillService;

/**
 * @Description 交易账单服务
 * @Project gd-admin-service
 * @ClassName OrderBillService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:59:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Service
public class OrderBillToolServiceImpl implements OrderBillToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static OrderBillService orderBillService;

	protected OrderBillService getHessianDetectionService() throws MalformedURLException {
		String url = gdProperties.getOrderBillUrl();
		if (orderBillService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBillService = (OrderBillService) factory.create(OrderBillService.class, url);
		}
		return orderBillService;
	}

	/**
	 * @Description AddBillDTO 交易账单添加
	 * @param dto
	 *            交易账单实体
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月14日 下午12:03:17
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Override
	public int addBillDTO(OrderBillDTO dto) throws Exception {
		return getHessianDetectionService().addBillDTO(dto);
	}

	/**
	 * @Description batchAddDTO 交易账单批量添加
	 * @param list
	 *            交易账单实体集合
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月14日 下午12:03:39
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Override
	public int batchAddDTO(List<OrderBillDTO> list) throws Exception {
		if (list != null && list.size() > 0) {
			return getHessianDetectionService().batchAddDTO(list);
		} else {
			return 0;
		}
	}

	@Override
	public List<OrderBillDTO> getOrderBillByCondition(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianDetectionService().getOrderBillByCondition(map);
	}

	@Override
	public Long getOrderBillTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianDetectionService().getOrderBillTotal(map);
	}

	@Override
	public Double getTradeMoneySum(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianDetectionService().getTradeMoneySum(map);
	}

	@Override
	public PageQueryResultDTO<PosBankCardPayRecordDTO> getPageQueryResultByCondition(
			Map<String, Object> map) throws Exception {
		PageQueryResultDTO<PosBankCardPayRecordDTO> pageDTO = getHessianDetectionService().getPageQueryResultByCondition(map);
		List<PosBankCardPayRecordDTO> dataList = pageDTO.getDataList();
		if(dataList != null && dataList.size() > 0){
			Integer type = (Integer) map.get("type");
			for(int i=0, len=dataList.size(); i < len; i++){
				PosBankCardPayRecordDTO dto = dataList.get(i);
				dto.setMatchType(getMatchType(type, dto));
			}
		}
		return pageDTO;
	}

	/**
	 * 设置匹配类型
	 * 1完全匹配 
	 * 2有流水无订单未升级 
	 * 3有流水无订单已升级 
	 * 4有订单无流水 延时
	 * @param type
	 * @param dto
	 * @return
	 */
	private String getMatchType(Integer type, PosBankCardPayRecordDTO dto) {
		String matchType = "-1";
		switch(type){
			case 0:
				if(StringUtils.isNotBlank(dto.getSysRefeNo()) && dto.getOrderNo() != null){
					matchType = "1";
				}else if(StringUtils.isNotBlank(dto.getSysRefeNo()) 
						&& dto.getOrderNo() == null 
						&& StringUtils.isNotBlank(dto.getShopPosNumber())){
					matchType = "2";
				}else if(StringUtils.isNotBlank(dto.getSysRefeNo()) 
						&& dto.getOrderNo() == null 
						&& StringUtils.isBlank(dto.getShopPosNumber())){
					matchType = "3";
				}else if(StringUtils.isBlank(dto.getSysRefeNo()) 
						&& dto.getOrderNo() != null){
					matchType = "4";
				}
				break;
			case 1:
				matchType = "1";
				break;
			case 2:
				matchType = "2";
				break;
			case 3:
				matchType = "3";
				break;
			case 4:
				matchType = "4";
				break;
			default:
				break;
		}
		return matchType;
	}

}
