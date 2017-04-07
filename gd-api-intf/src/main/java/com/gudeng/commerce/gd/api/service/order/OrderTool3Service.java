package com.gudeng.commerce.gd.api.service.order;


import java.util.List;

import com.gudeng.commerce.gd.api.dto.input.OrderBatchAddInputDTO;
import com.gudeng.commerce.gd.api.dto.input.OrderCallbackInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;


public interface OrderTool3Service<RestultEntity> {
	
	/**
	 * 批量插入订单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public RestultEntity batchAddOrder(OrderBatchAddInputDTO inputDTO) throws Exception;
	public ErrorCodeEnum updateStatus(OrderCallbackInputDTO paramValue)throws Exception;
	public RestultEntity miningSalesAddOrder(String jsonStr) throws Exception; //H5活动 现场采销订单添加  add by weiwenke
	public int updateByOrderNo(OrderBaseinfoDTO obj) throws Exception; 
	public OrderBaseinfoDTO getproductDetailListByOrderNo(long orderNo) throws Exception;
	
}
