package com.gudeng.commerce.gd.api.controller.v160630;

import java.util.HashMap;
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
import com.gudeng.commerce.gd.api.dto.output.SellerOrderList2DTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;

@Controller
@RequestMapping("/v31/order")
public class OrderAppV31Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppV31Controller.class);
	@Autowired
	private OrderTool2Service orderTool2Service;
	
	@RequestMapping("/search")
	public void searchOrderList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			MemberInfoInputDTO inputDTO = (MemberInfoInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberInfoInputDTO.class);
			Integer status = 0; //默认搜索全部订单
			Integer businessId = ParamsUtil.getIntFromString(inputDTO.getBusinessId(), null);
			Integer memberId = ParamsUtil.getIntFromString(inputDTO.getMemberId(), null);
			//按姓名, 手机号, 商品名称查询
			String searchStr = inputDTO.getSearchStr();
			if(businessId == null){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			
			if(StringUtils.isBlank(searchStr) && memberId == null){
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
			//如果是数字, 则只搜索订单号和电话号码
			if(Validator.isNumber(searchStr) && searchStr.length() > 10){
				map.put("orderNoStr", searchStr);
			}
			
			//搜索用户类型 1农批商 2供应商
			map.put("searchUserType", 1);
			//搜索订单类型 1销售 2采购
			map.put("searchOrderType", 1);
			
			PageQueryResultDTO<SellerOrderList2DTO> pageResult = orderTool2Service.searchSellerOrderList(map);
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
			logger.error("[ERROR]搜索卖家订单列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/addProduct")
	public void addProduct(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			ErrorCodeEnum addResult = orderTool2Service.addProductInfo(inputDTO);
			setEncodeResult(result, addResult, request, response);
		}catch (Exception e) {
			logger.error("[ERROR]补充订单商品信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/addBuyer")
	public void addBuyer(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			MemberInfoInputDTO inputDTO = (MemberInfoInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberInfoInputDTO.class);
			ErrorCodeEnum addResult = orderTool2Service.addBuyerInfo(inputDTO, false);
			setEncodeResult(result, addResult, request, response);
		}catch (Exception e) {
			logger.error("[ERROR]补充订单买家信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/addBuyerAndProduct")
	public void addBuyerAndProduct(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			MemberInfoInputDTO inputDTO = (MemberInfoInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberInfoInputDTO.class);
			ErrorCodeEnum addResult = orderTool2Service.addBuyerInfo(inputDTO, true);
			setEncodeResult(result, addResult, request, response);
		}catch (Exception e) {
			logger.error("[ERROR]补充订单买家和商品信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
