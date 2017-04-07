package com.gudeng.commerce.gd.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.task.service.OrderBaseToolService;
import com.gudeng.commerce.gd.task.service.ProductToolService;
import com.gudeng.commerce.gd.task.service.WalletToolService;
import com.gudeng.commerce.gd.task.util.DateUtil;
import com.gudeng.commerce.gd.task.util.MathUtil;

/**
 * Cancel unpaid orders
 * check every hour
 * @author TerryZhang
 */
public class CancelUnpaidOrderTaskImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(CancelUnpaidOrderTaskImpl.class);
	
	private static final Double MAX_PRODUCT_STOCK = 999999.99;
	
	@Autowired
	private OrderBaseToolService orderBaseToolService;
	@Autowired
	private WalletToolService accInfoToolService;
	@Autowired
	private ProductToolService productToolService;
	
	public  void invoke(){
		
		try {
			LOGGER.info("Start canceling unpaid order job.");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dateTime", DateUtil.getTimeBySubDay(-3));
			LOGGER.info("Expiration time: " + DateUtil.getTimeBySubDay(-3));
			List<OrderBaseinfoDTO> orderList = orderBaseToolService.getUnpaidOrderList(map);
			int orderListNum = (orderList == null ? 0 : orderList.size());
			LOGGER.info("Start to cancel normal order, order list size: " + orderListNum);
			//遍历未支付过期的销售和采购订单
			for (OrderBaseinfoDTO orderBaseinfoDTO : orderList) {
				if(!"4".equals(orderBaseinfoDTO.getOrderType())){
					//取消订单
					Long orderNo = orderBaseinfoDTO.getOrderNo();
					Map<String, Object> orderNoMap = new HashMap<String, Object>();
					orderNoMap.put("orderNo", orderNo);
					orderNoMap.put("status", "9");
					//组装库存数据， 增加产品库存
					List<OrderProductDetailDTO> productList = orderBaseToolService.getListByOrderNo(orderNoMap);
					LOGGER.info("Canceling order no: " + orderNo + ", type: " + orderBaseinfoDTO.getOrderType());
					if(productList != null){
						List<Map<String, Object>> stockList = new ArrayList<>();
						//产品id list， 用来查找校验产品信息
						List<Long> pIdList = new ArrayList<>();
						for(int i=0,len=productList.size();i<len;i++){
							OrderProductDetailDTO productDTO = productList.get(i);
							pIdList.add(Long.parseLong(productDTO.getProductId()+""));
						}
						
						//设置订单产品库存
						List<ProductDto> pList = productToolService.getListByIds(pIdList);
						for(int i=0,len=productList.size();i<len;i++){
							OrderProductDetailDTO productDTO = productList.get(i);
							Integer pId = productDTO.getProductId();
							Double purQuan = productDTO.getPurQuantity();
							Map<String, Object> tmpMap = new HashMap<>();
							for(int j=0,len2=pList.size(); j<len2; j++){
								ProductDto pDTO = pList.get(j);
								Double existedStock = pDTO.getStockCount();
								if(pId.equals(pDTO.getProductId().intValue())){
									tmpMap.put("productId", pId);
									Double newStock = MathUtil.add(existedStock, purQuan);
									//如果库存大于最大值， 则使用最大值
									if(newStock.compareTo(MAX_PRODUCT_STOCK) > 0){
										tmpMap.put("stockCount", MAX_PRODUCT_STOCK);
									}else{
										tmpMap.put("stockCount", newStock);
									}
									if(newStock.compareTo(purQuan) == 0){
										tmpMap.put("status", "3");  //产品上架
									}
									
									stockList.add(tmpMap);
								}
							}
						}
						map.put("stockList", stockList);
						
					}
					orderBaseToolService.cancelOrderByOrderNo(orderNoMap);
					LOGGER.info("Already canceled order no: " + orderNo);
				}
			}
			
			//遍历服务订单
			map.put("dateTime", DateUtil.getTimeBySubHour(-3));
			map.put("orderType", 4);
			LOGGER.info("Expiration time: " + DateUtil.getTimeBySubHour(-3));
			orderList = orderBaseToolService.getUnpaidOrderList(map);
			orderListNum = (orderList == null ? 0 : orderList.size());
			LOGGER.info("Start to cancel gold medal order, order list size: " + orderListNum);
			//遍历未支付过期的销售和采购订单
			for (OrderBaseinfoDTO orderBaseinfoDTO : orderList) {
				//取消订单
				Long orderNo = orderBaseinfoDTO.getOrderNo();
				Map<String, Object> orderNoMap = new HashMap<String, Object>();
				orderNoMap.put("orderNo", orderNo);
				orderNoMap.put("status", "9");
				LOGGER.info("Canceling order no: " + orderNo + ", type: " + orderBaseinfoDTO.getOrderType());
				orderBaseToolService.cancelOrderByOrderNo(orderNoMap);
				LOGGER.info("Already canceled order no: " + orderNo);
			}
			
			LOGGER.info("Finish canceling unpaid order job.");
		} catch (Exception e) {
			LOGGER.error("[ERROR]Cancel unpaid orders exception: ", e);
		}
	}
}
