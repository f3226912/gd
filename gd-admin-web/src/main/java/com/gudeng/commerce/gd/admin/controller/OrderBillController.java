package com.gudeng.commerce.gd.admin.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.OrderBillToolService;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
/**
 * 银行交易流水Control
 * @author xiaojun
 */
@Controller
@RequestMapping("orderBill")
public class OrderBillController extends AdminBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(OrderBillController.class);
	
	@Autowired
	private OrderBillToolService orderBillToolService;

	@Autowired
	private  MarketManageService marketManageService;
	
	@RequestMapping("query")
	public String cashRequest(HttpServletRequest request){
		List<MarketDTO> list = null;
		try {
			list = marketManageService.getAllByType("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("marketList", list);
		return "orderBill/orderBillList";
	}
	
	/**
	 * 查询订单交易列表详情
	 * @param request
	 * @param orderBillDTO
	 * @return
	 * @author xiaojun
	 */
	@RequestMapping("orderBillList")
	@ResponseBody
	public String orderBillList(HttpServletRequest request,OrderBillDTO orderBillDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			map.put("businessNo",orderBillDTO.getBusinessNo());
			map.put("clientNo",orderBillDTO.getClientNo());
			map.put("billBeginTime",orderBillDTO.getBillBeginTime());
			map.put("billEndTime", orderBillDTO.getBillEndTime());
			map.put("sysRefeNo", orderBillDTO.getSysRefeNo());
			map.put("marketId", orderBillDTO.getMarketId());
			map.put("tradeMoney", orderBillDTO.getTradeMoney());
			//设置总记录数
			map.put("total", orderBillToolService.getOrderBillTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<OrderBillDTO> list = orderBillToolService.getOrderBillByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list 
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据条件查询交易总额
	 * @param request
	 * @param orderBillDTO
	 * @return
	 * @author xiaojun
	 */
	@RequestMapping("tradeMoneySum")
	@ResponseBody
	public String tradeMoneySum(HttpServletRequest request,OrderBillDTO orderBillDTO) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("businessNo",orderBillDTO.getBusinessNo());
			map.put("clientNo",orderBillDTO.getClientNo());
			map.put("billBeginTime",orderBillDTO.getBillBeginTime());
			map.put("billEndTime", orderBillDTO.getBillEndTime());
			map.put("sysRefeNo", orderBillDTO.getSysRefeNo());
			map.put("marketId", orderBillDTO.getMarketId());

			DecimalFormat df=new DecimalFormat("#.00");
			
			Double tradeMoneySum=orderBillToolService.getTradeMoneySum(map);
			map.put("tradeMoneySum", tradeMoneySum==null?0.00:df.format(tradeMoneySum));
			
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
