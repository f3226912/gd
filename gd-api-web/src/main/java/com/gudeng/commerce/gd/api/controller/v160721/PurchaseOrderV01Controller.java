package com.gudeng.commerce.gd.api.controller.v160721;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.input.MemberInfoInputDTO;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.v160721.PurchaseOrderToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.JsonpUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;

/**
 * 采购订单controller类
 * 农批商<->供应商
 * @author TerryZhang
 */
@Controller
@RequestMapping("/purchOrder")
public class PurchaseOrderV01Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PurchaseOrderV01Controller.class);
	@Autowired
	private PurchaseOrderToolService purchaseOrderToolService;
	
	/**
	 * 采购订单列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		OrderAppInputDTO inputDTO = (OrderAppInputDTO) getDecryptedObject(request, OrderAppInputDTO.class);
		Integer status = inputDTO.getOrderStatus(); //订单状态
		Integer isBuyer = inputDTO.getIsBuyer();  //是否买家: 0为否, 1为是
		
		if(isBuyer == null){
			setEncodeResult(result, ErrorCodeEnum.ORDER_USERTYPE_IS_NULL, request, response);
			return;
		}
		if(isBuyer != 0 && isBuyer != 1){
			setEncodeResult(result, ErrorCodeEnum.ORDER_USERTYPE_ERROR, request, response);
			return;
		}
		//供应商请求
		if(isBuyer == 0 && inputDTO.getBusinessId() == null){
			setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
			return;
		}
		//农批商请求
		if(isBuyer == 1 && inputDTO.getMemberId() == null){
			setEncodeResult(result, ErrorCodeEnum.ORDER_BUYER_IS_NULL, request, response);
			return;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessId", inputDTO.getBusinessId());	
		map.put("memberId", inputDTO.getMemberId());
		map.put("orderStatus", status);
		
		try {
			CommonPageDTO pageDTO = getPageInfoEncript(getParamDecodeStr(request), map);
			int total = purchaseOrderToolService.getPurchaseOrderTotal(map);
			List<PurchaseOrderListDTO> orderList = purchaseOrderToolService.getPurchaseOrderList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]查询采购订单列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 添加采购订单
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		OrderAppInputDTO inputDTO = (OrderAppInputDTO) getDecryptedObject(request, OrderAppInputDTO.class);
		
		try {
			StatusCodeEnumWithInfo statusCodeInfo = purchaseOrderToolService.addPurchaseOrder(inputDTO);
			ErrorCodeEnum statusCode = statusCodeInfo.getStatusCodeEnum();
			if( ErrorCodeEnum.SUCCESS == statusCode){
				String[] addResult = ((String)statusCodeInfo.getObj()).split("#_#");
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult[0]); 
				map.put("orderStatus", addResult[1]);
				map.put("prepayAmt", addResult[2]);
				result.setObject(map);
			}
				
			setEncodeResult(result, statusCode, request, response);
		}catch (Exception e) {
			logger.error("[ERROR]添加采购订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 添加采购订单
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/addCall")
	public void addCall(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		ObjectResult result = new ObjectResult();
		OrderAppInputDTO inputDTO = (OrderAppInputDTO) getDecryptedObject(request, OrderAppInputDTO.class);
		
		try {
			StatusCodeEnumWithInfo statusCodeInfo = purchaseOrderToolService.addPurchaseOrder(inputDTO);
			ErrorCodeEnum statusCode = statusCodeInfo.getStatusCodeEnum();
			if( ErrorCodeEnum.SUCCESS == statusCode){
				String[] addResult = ((String)statusCodeInfo.getObj()).split("#_#");
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult[0]); 
				map.put("orderStatus", addResult[1]);
				map.put("prepayAmt", addResult[2]);
				result.setObject(map);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, statusCode, request, response);
			}
		}catch (Exception e) {
			logger.error("[ERROR]添加采购订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		PrintWriter out = response.getWriter();
		out.println(JsonpUtil.jsonpCallback(request, JSONUtils.toJSONString(result)));
		out.flush();
//		out.close();
	}
	
	/**
	 * 采购订单详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		OrderAppInputDTO inputDTO = (OrderAppInputDTO) getDecryptedObject(request, OrderAppInputDTO.class);
		try {
			Long orderNo = inputDTO.getOrderNo();
			if(orderNo == null){
				setEncodeResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
		
			PurchaseOrderDetailDTO orderDetail = purchaseOrderToolService.getPurchaseOrderDetail(orderNo);
			if(orderDetail != null){
				result.setObject(orderDetail);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
			}
		}catch (Exception e) {
			logger.error("[ERROR]获取采购订单详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 修改采购订单
	 * 主要是修改价格和状态
	 * @param request
	 * @param response
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		OrderAppInputDTO inputDTO = (OrderAppInputDTO) getDecryptedObject(request, OrderAppInputDTO.class);
		try {
			ErrorCodeEnum statusCode = purchaseOrderToolService.updatePurchaseOrder(inputDTO);
			setEncodeResult(result, statusCode, request, response);
		}catch (Exception e) {
			logger.error("[ERROR]修改采购订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			MemberInfoInputDTO inputDTO = (MemberInfoInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberInfoInputDTO.class);
			Integer status = 0; //默认搜索全部订单
			Integer businessId = ParamsUtil.getIntFromString(inputDTO.getBusinessId(), null);
			Integer memberId = ParamsUtil.getIntFromString(inputDTO.getMemberId(), null);
			//按姓名, 手机号, 商品名称查询
			String searchStr = inputDTO.getSearchStr();
			
			if(businessId == null && memberId == null){
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
			
			if(StringUtils.isBlank(searchStr)){
				setEncodeResult(result, ErrorCodeEnum.SEARCH_STRING_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			
			//当前第几页
			String page = (String)GSONUtils.getJsonValueStr(jsonStr, "pageNum");
			//每页显示的记录数
			String rows = (String)GSONUtils.getJsonValueStr(jsonStr, "pageSize");
			boolean isLastestVersion = true;
			if(StringUtils.isBlank(page) && StringUtils.isBlank(rows)){
				isLastestVersion = false;
				map.put(END_ROW, 1000);
			}
			
			map.put("businessId", businessId);
			map.put("memberId", memberId);
			map.put("orderStatus", status);
			map.put("searchStr", StringUtils.isBlank(searchStr) ? "" : searchStr);
			//如果是数字, 则加入搜索订单号
			if(Validator.isNumber(searchStr) && searchStr.length() > 10){
				map.put("orderNoStr", searchStr);
			}
			//搜索订单类型 1销售 2采购
			map.put("searchOrderType", 2);
			if(memberId != null){
				//搜索用户类型 1农批商 2供应商
				map.put("searchUserType", 1);
			}else{
				//搜索用户类型 1农批商 2供应商
				map.put("searchUserType", 2);
			}
			
			PageQueryResultDTO<PurchaseOrderListDTO> pageResult = purchaseOrderToolService.searchPruchaseOrderList(map);
			pageDTO.setRecordCount(pageResult.getTotalCount());
			pageDTO.initiatePage(pageResult.getTotalCount());
			pageDTO.setRecordList(pageResult.getDataList());
			if(isLastestVersion){
				result.setObject(pageDTO);
			}else{
				result.setObject(pageResult.getDataList());
			}
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]搜索采购订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
