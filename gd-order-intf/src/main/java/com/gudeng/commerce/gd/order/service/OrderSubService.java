/**
 * @Title: OrderSubService.java
 * @Package com.gudeng.commerce.gd.order.service.sub
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年12月4日 下午6:45:56
 * @version V1.0
 */ 
package com.gudeng.commerce.gd.order.service;

import java.util.List;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;

/**
 * @Description: TODO(订单补贴服务接口类)
 * @author mpan
 * @date 2015年12月4日 下午6:45:56
 */
public interface OrderSubService {
	
	/**
	 * @Title: handleOrderSubAmt
	 * @Description: TODO(处理订单补贴金额，并更新到库)
	 * @param orderNo
	 * @throws ServiceException
	 */
	public void handleOrderSubAmtToDB(Long orderNo) throws ServiceException;
	
	/**
	 * @Title: queryProductSubList
	 * @Description: TODO(查询产品是否有补贴)
	 * @return
	 * @throws ServiceException
	 */
	public List<OrderProductDetailDTO> queryProductSubList(List<OrderProductDetailDTO> orderProductDetails) throws Exception;
	
	/**
	 * @Title: checkOrderSubRuleToDB
	 * @Description: TODO(补贴限制规则检查，并更新到库)
	 * @param orderNo
	 * @throws ServiceException
	 */
	public void checkOrderSubRuleToDB(Long orderNo) throws ServiceException;
	
	/**
	 * @Title: subTotalAmountCheckRule
	 * @Description: TODO(市场总补贴规则检查)
	 * @param orderNo
	 * @throws ServiceException
	 */
	public void subTotalAmountCheckRule(Long orderNo) throws ServiceException;

}
