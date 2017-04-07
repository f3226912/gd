package com.gudeng.commerce.gd.notify.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.notify.service.PayResultToolService;
import com.gudeng.commerce.gd.notify.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.PayResultService;

/**
 * 支付结果数据操作
 * @author Ailen
 *
 */
public class PayResultToolServiceImpl implements PayResultToolService {
	@Autowired
	public GdProperties gdProperties;
	
	private static PayResultService payResultService;
	
	/*@Autowired
	private BaseDao baseDao;*/

	/**
	 * 功能描述:demo接口服务
	 * 
	 * @param
	 * @return
	 */
	protected PayResultService getHessianPayResultService() throws MalformedURLException {
		String url = gdProperties.getPayResultServiceUrl();
		if(payResultService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			payResultService = (PayResultService) factory.create(PayResultService.class, url);
		}
		return payResultService;
	}
	
	
	/**
	 * 修改订单结果
	 * @param OrderNo
	 * @param status
	 * @throws MalformedURLException 
	 */
	public void updateOrderStatus(String orderNo, String status) throws MalformedURLException  {
		getHessianPayResultService().updateOrderStatus(orderNo, status);
	}
	
	/**
	 * 添加退款信息
	 */
	public void addOrderRefundRecord(OrderRefundRecordEntity orderRefundRecordEntity) throws MalformedURLException {
		getHessianPayResultService().addOrderRefundRecord(orderRefundRecordEntity);
	}
	
	@Override
	public void addPaySerialnumber(PaySerialnumberEntity paySerialnumberEntity) throws Exception {
		getHessianPayResultService().addPaySerialnumber(paySerialnumberEntity);
		
	}
	
	@Override
	public List<OrderBaseinfoDTO> getDinJinPayOuter() throws Exception {
		return getHessianPayResultService().getDinJinPayOuter();
	}
	
	@Override
	public void updateOrderStatus(String orderNo, String status, String cancelReason) throws MalformedURLException {
		getHessianPayResultService().updateOrderStatus(orderNo, status, cancelReason);
	}
	@Override
	public void updateMemberGrade(Long memberId) throws Exception {
		getHessianPayResultService().updateMemberGrade(memberId);
	}
	@Override
	public PaySerialnumberEntity getByStatementId(String statementId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianPayResultService().getByStatementId(statementId);
	}
	@Override
	public void updateOrderbase(OrderBaseinfoEntity entity) throws MalformedURLException {
		// TODO Auto-generated method stub
		getHessianPayResultService().updateOrderBase(entity);
	}

    /**
     * 获取api值
     */
	@Override
	public String getApiUrl() {
		return gdProperties.getApiUrl();
	}
    
}
