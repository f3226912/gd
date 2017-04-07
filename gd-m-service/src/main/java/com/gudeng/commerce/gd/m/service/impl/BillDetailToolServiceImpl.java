package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BillDetailDTO;
import com.gudeng.commerce.gd.customer.dto.BillDetailStatDTO;
import com.gudeng.commerce.gd.customer.service.BillDetailService;
import com.gudeng.commerce.gd.m.service.BillDetailToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.MathUtil;


/** 
 *功能描述：
 */
@Service
public class BillDetailToolServiceImpl implements BillDetailToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static BillDetailService billDetailService;
	
	/**
	 * billDetailService接口服务
	 * @return
	 */
	protected BillDetailService getHessianBillDetailService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.billDetailService.url");
		if(billDetailService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			billDetailService = (BillDetailService) factory.create(BillDetailService.class, url);
		}
		return billDetailService;
	}
	
	/**
	 * 按月份查询账单列表
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailDTO> 账单列表
	 */
	@Override
	public List<BillDetailDTO> queryBillDetailList(String account, String payTime, String channelType) throws Exception {
		List<BillDetailDTO> billDetails = getHessianBillDetailService().queryBillDetailList(account, payTime, channelType);
		if (CollectionUtils.isEmpty(billDetails)) {
			return null;
		}
		List<Long> orderNos = new ArrayList<Long>();
		for (BillDetailDTO billDetail : billDetails) {
			if (StringUtils.isBlank(billDetail.getSubject())) {
				orderNos.add(billDetail.getOrderNo());
			}
		}
		// 从订单获取补充商品后的订单主题
		if (CollectionUtils.isNotEmpty(orderNos)) {
			List<BillDetailDTO> orderSubjects = queryOrderSubjectList(orderNos);
			Map<Long, String> map = new HashMap<Long, String>();
			if (CollectionUtils.isNotEmpty(orderSubjects)) {
				for (BillDetailDTO orderSubject : orderSubjects) {
					map.put(orderSubject.getOrderNo(), orderSubject.getSubject());
				}
			}
			for (BillDetailDTO billDetail : billDetails) {
				if (StringUtils.isBlank(billDetail.getSubject())) {
					if (map.containsKey(billDetail.getOrderNo())) {
						billDetail.setSubject(map.get(billDetail.getOrderNo()));
					} else {
						billDetail.setSubject("");
					}
				}
			}
		}
		return billDetails;
	}
	
	/**
	 * 按月份查询采购金额
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailStatDTO> 月采购金额列表
	 */
	@Override
	public List<BillDetailStatDTO> queryMonthAmountList(String account, String payTime, String channelType) throws Exception {
		List<BillDetailStatDTO> statDTOList = getHessianBillDetailService().queryMonthAmountList(account, payTime, channelType);
		Double sumAmount = 0D;
		for(BillDetailStatDTO statDTO : statDTOList){
			sumAmount = MathUtil.add(sumAmount, statDTO.getSumAmount());
		}
		statDTOList.add(0, new BillDetailStatDTO(0, sumAmount));
		return statDTOList;
	}
	
	/**
	 * 按月份查询订单量
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailStatDTO> 月订单量列表
	 */
	@Override
	public List<BillDetailStatDTO> queryMonthOrderList(String account, String payTime, String channelType) throws Exception {
		List<BillDetailStatDTO> statDTOList = getHessianBillDetailService().queryMonthOrderList(account, payTime, channelType);
		Integer sumOrder = new Integer(0);
		for(BillDetailStatDTO statDTO : statDTOList){
			sumOrder += statDTO.getSumOrder();
		}
		statDTOList.add(0, new BillDetailStatDTO(0, sumOrder));
		return statDTOList;
	}

	@Override
	public Double countTradeAmount(Long memberId) throws Exception {
		return getHessianBillDetailService().countTradeAmount(memberId);
	}

	@Override
	public List<BillDetailDTO> queryOrderSubjectList(List<Long> orderNos) throws Exception {
		return getHessianBillDetailService().queryOrderSubjectList(orderNos);
	}
}
