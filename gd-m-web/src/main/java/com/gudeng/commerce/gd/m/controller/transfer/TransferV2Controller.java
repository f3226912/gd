package com.gudeng.commerce.gd.m.controller.transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.dto.NstBaseResponseDTO;
import com.gudeng.commerce.gd.m.dto.NstRequestParamDTO;
import com.gudeng.commerce.gd.m.dto.NstTransferCompanyDetailDTO;
import com.gudeng.commerce.gd.m.dto.NstTransferDeliverDetailDTO;
import com.gudeng.commerce.gd.m.dto.NstTransferDetailOutputDTO;
import com.gudeng.commerce.gd.m.dto.NstTransferDriverDetailDTO;
import com.gudeng.commerce.gd.m.dto.NstTransferGoodsDetailDTO;
import com.gudeng.commerce.gd.m.dto.NstTransferListOutputDTO;
import com.gudeng.commerce.gd.m.dto.ProCommonPageDTO;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.m.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.m.service.NstApiCommonService;
import com.gudeng.commerce.gd.m.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.m.service.OrderTool2Service;
import com.gudeng.commerce.gd.m.service.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.m.util.DateTimeUtils;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.MoneyUtil;
import com.gudeng.commerce.gd.m.util.ObjectResult;
import com.gudeng.commerce.gd.m.util.StringUtil;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 物流页面的所有H5处理controller
 * @author TerryZhang
 */
@Controller
@RequestMapping("/v2/transfer")
public class TransferV2Controller extends MBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(TransferV2Controller.class);

	@Resource
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;
	@Autowired
	private NstApiCommonService nstApiCommonService;
	@Autowired
	private CallstatiSticsToolService callService;
	@Autowired
	private OrderProductDetailToolService orderProductDetailToolService;
	@Autowired
	private OrderTool2Service orderTool2Service;
	
	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;
	
	@Autowired
	public GdProperties gdProperties;
	/**
	 * 显示物流列表 分页数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListPage")
	public String getListPage(String memberId, Integer status) {
		if(status == null){
			status = NstApiRequestV1Enum.QUERY_PUBLISH_TRANSFER.getCode();
		}
		putModel("memberId", memberId);
		return "/H5/transfer_v2/list";
	}

	/**
	 * 获取页面数据方法
	 * @param request
	 * @param response
	 * @param memberId
	 * @param pageDTO
	 */
	@RequestMapping("/getListData/{status}")
	@ResponseBody
	public void getListData(HttpServletRequest request, HttpServletResponse response, 
			String memberId, String pageSize, String pageNum, @PathVariable Integer status) {
		ObjectResult result = new ObjectResult();
		try {
			if (StringUtils.isBlank(memberId)) {
				result.setStatusCode(ErrorCodeEnum.MEMBER_ID_IS_NULL.getStatusCode());
				result.setMsg(ErrorCodeEnum.MEMBER_ID_IS_NULL.getStatusMsg());
				renderJson(result, request, response);
				return;
			} 
			
			if (status == null || NstApiRequestV1Enum.getByCode(status) == null) {
				result.setStatusCode(ErrorCodeEnum.PARAM_IS_ERROR.getStatusCode());
				result.setMsg(ErrorCodeEnum.PARAM_IS_ERROR.getStatusMsg());
				renderJson(result, request, response);
				return;
			}
			
			if(StringUtils.isBlank(pageSize)){
				pageSize = "10";
			}
			
			if(StringUtils.isBlank(pageNum)){
				pageNum = "1";
			}
				
			Map<String, Object> queryMap = new HashMap<>();

			/*
			 * 设置查询参数
			 */
			queryMap.put("memberId", memberId);
			queryMap.put("pageSize", pageSize);
			queryMap.put("pageNum", pageNum);
			if(NstApiRequestV1Enum.getByCode(status) == NstApiRequestV1Enum.QUERY_WAIT_ACCEPT_TRANSFER){
				queryMap.put("orderStatus", "1");
				queryMap.put("shipperMemberId", memberId);
			}
			if(NstApiRequestV1Enum.getByCode(status) == NstApiRequestV1Enum.QUERY_FINISH_TRANSFER){
				queryMap.put("orderStatus", "2");
				queryMap.put("shipperMemberId", memberId);
			}

			queryMap.put("token", nstApiCommonService.getNstToken(memberId));
			queryMap.put("nonShowPlatform", true);
			
			NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(queryMap, getUrl(NstApiRequestV1Enum.getByCode(status)));
			
			//是否token错误
			if(nstResponse.getCode().intValue() == ErrorCodeEnum.NST_TOKEN_FAIL.getStatusCode()){
				if(StringUtils.isBlank(nstApiCommonService.setNstToken(memberId+""))){
					result.setStatusCode(ErrorCodeEnum.NST_TOKEN_FAIL.getStatusCode());
					result.setMsg(ErrorCodeEnum.NST_TOKEN_FAIL.getStatusMsg());
					renderJson(result, request, response);
					return;
				}else{
					nstResponse = nstApiCommonService.sendNstRequest(queryMap, getUrl(NstApiRequestV1Enum.getByCode(status)));
				}
			}
			
			if(nstResponse.getCode().intValue() != 10000){
				result.setStatusCode(nstResponse.getCode());
				result.setMsg(nstResponse.getMsg());
				renderJson(result, request, response);
				return;
			}
			
			// 填充结果集
			result.setObject(setNstTransferList(nstResponse.getResult(), status));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg(ErrorCodeEnum.SUCCESS.getStatusMsg());
		} catch (Exception e) {
			logger.info("[ERROR]"+NstApiRequestV1Enum.getDescByCode(status)+"异常", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 显示物流详情页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDetailPage")
	public String getDetailPage(String transferId, String memberId, Integer status, Integer orderId) {
		Integer oprtcode = NstApiRequestV1Enum.QUERY_TRANSFER_DETAIL.getCode();
		if(status != null){
			if(status.intValue() == NstApiRequestV1Enum.QUERY_WAIT_ACCEPT_TRANSFER.getCode() 
					|| status.intValue() == NstApiRequestV1Enum.QUERY_FINISH_TRANSFER.getCode()){
				oprtcode = NstApiRequestV1Enum.QUERY_ORDER_DETAIL.getCode();
			}
		}
		if(orderId == null){
			orderId = 0;
		}
		putModel("transferId", transferId);
		putModel("memberId", memberId);
		putModel("orderId", orderId);
		putModel("oprtcode", oprtcode);
		return "/H5/transfer_v2/detail";
	}

	/**
	 * 获取物流详情数据
	 * @param request
	 * @param response
	 * @param memberId
	 * @param fromCode
	 * @param pageDTO
	 */
	@RequestMapping("/getDetailData/{transferId}")
	@ResponseBody
	public void getDetailData(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String transferId, Integer oprtCode, Integer orderId, String memberId) {
		ObjectResult result = new ObjectResult();
		try {
			if (StringUtils.isBlank(memberId)) {
				result.setStatusCode(ErrorCodeEnum.MEMBER_ID_IS_NULL.getStatusCode());
				result.setMsg(ErrorCodeEnum.MEMBER_ID_IS_NULL.getStatusMsg());
				renderJson(result, request, response);
				return;
			} 
			
			if (StringUtils.isBlank(transferId)) {
				result.setStatusCode(ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL.getStatusCode());
				result.setMsg(ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL.getStatusMsg());
				renderJson(result, request, response);
				return;
			}
			
			if (oprtCode == null || NstApiRequestV1Enum.getByCode(oprtCode) == null) {
				result.setStatusCode(ErrorCodeEnum.PARAM_IS_ERROR.getStatusCode());
				result.setMsg(ErrorCodeEnum.PARAM_IS_ERROR.getStatusMsg());
				renderJson(result, request, response);
				return;
			}
			
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("id", transferId);
			/*
			 * 设置订单查询参数
			 */
			if(NstApiRequestV1Enum.getByCode(oprtCode).getCode() 
					== NstApiRequestV1Enum.QUERY_ORDER_DETAIL.getCode()){
				if(orderId == null){
					orderId = 0;
				}
				
				queryMap.put("orderBeforeId", orderId);
				queryMap.put("sourceId", transferId);
				queryMap.put("needLog", "Y");
				queryMap.remove("id");
			}

			queryMap.put("token", nstApiCommonService.getNstToken(memberId));
			NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(queryMap, getUrl(NstApiRequestV1Enum.getByCode(oprtCode)));
			if(nstResponse.getCode().intValue() != 10000){
				result.setStatusCode(nstResponse.getCode());
				result.setMsg(nstResponse.getMsg());
				renderJson(result, request, response);
				return;
			}
			
			// 填充结果集
			NstTransferDetailOutputDTO detailDTO = setNstTransferDetail(nstResponse.getResult(), NstApiRequestV1Enum.getByCode(oprtCode));
			//设置是否有货物信息
			List<ProductDeliveryDetailDTO>  productList = productDeliveryDetailToolService.getProductByMemberAddressId(Long.parseLong(transferId));
			if(productList != null && productList.size() > 0){
				detailDTO.getGoodsInfo().setHasGoods(1);
			}
			
			result.setObject(detailDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg(ErrorCodeEnum.SUCCESS.getStatusMsg());
		} catch (Exception e) {
			logger.info("[ERROR]获取物流详情数据异常", e);
			e.printStackTrace();
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 物流操作管理
	 * 删除， 拒绝， 接受， 确认
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping(value = "/editOrder/{oprt}")
	@ResponseBody
	public void editOrder(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Integer oprt, NstRequestParamDTO requestDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if (requestDTO.getMemberId() == null) {
				result.setStatusCode(ErrorCodeEnum.MEMBER_ID_IS_NULL.getStatusCode());
				result.setMsg(ErrorCodeEnum.MEMBER_ID_IS_NULL.getStatusMsg());
				renderJson(result, request, response);
				return;
			} 
			
			if (requestDTO.getTransferId() == null) {
				result.setStatusCode(ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL.getStatusCode());
				result.setMsg(ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL.getStatusMsg());
				renderJson(result, request, response);
				return;
			}
			
			if (StringUtils.isBlank(requestDTO.getVersion())) {
				result.setStatusCode(ErrorCodeEnum.VERSION_IS_NULL.getStatusCode());
				result.setMsg(ErrorCodeEnum.VERSION_IS_NULL.getStatusMsg());
				renderJson(result, request, response);
				return;
			}
			
			if (oprt == null || NstApiRequestV1Enum.getByCode(oprt) == null) {
				result.setStatusCode(ErrorCodeEnum.PARAM_IS_ERROR.getStatusCode());
				result.setMsg(ErrorCodeEnum.PARAM_IS_ERROR.getStatusMsg());
				renderJson(result, request, response);
				return;
			}
			
			Map<String, Object> queryMap = new HashMap<>();

			queryMap.put("token", nstApiCommonService.getNstToken(requestDTO.getMemberId()+""));
			// 设置修改参数
			if(setEditQueryMap(queryMap, oprt, requestDTO) != ErrorCodeEnum.SUCCESS){
				result.setStatusCode(ErrorCodeEnum.PARAM_IS_ERROR.getStatusCode());
				result.setMsg(ErrorCodeEnum.PARAM_IS_ERROR.getStatusMsg());
				renderJson(result, request, response);
				return;
			}

			NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(queryMap, getUrl(NstApiRequestV1Enum.getByCode(oprt)));
			if(nstResponse.getCode().intValue() != 10000){
				result.setStatusCode(nstResponse.getCode());
				result.setMsg(nstResponse.getMsg());
				renderJson(result, request, response);
				return;
			}
			
			// 删除货源，重置商品为未出货
			if (NstApiRequestV1Enum.DELETE_GOODS_TRANSFER.getCode() == oprt) {
				nstOrderBaseinfoToolService.updateDeliverStatusByMemberAddressId(requestDTO.getTransferId().longValue(), 0);
			}else if(NstApiRequestV1Enum.CONFIRM_GOODS_TRANSFER.getCode() == oprt){//更新订单商品状态为已出货
				nstOrderBaseinfoToolService.updateDeliverStatusByMemberAddressId(requestDTO.getTransferId().longValue(), 2);
			}
			// 填充结果集
			result.setObject(nstResponse.getResult());
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg(ErrorCodeEnum.SUCCESS.getStatusMsg());
		} catch (Exception e) {
			logger.info("接收订单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		// 发送JSON数据
		renderJson(result, request, response);
	}
	
	@RequestMapping("getGoodsDetail/{memberAddressId}")
	public String getGoodsDetail(@PathVariable("memberAddressId") Long memberAddressId, ModelMap modelMap) {
		String pageName = "/H5/transfer_v2/orderDetail";
		try {
			// initialize query
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberAddressId", memberAddressId);
			
			// do query
			Integer type = productDeliveryDetailToolService.getTypeByMemberAddressId(memberAddressId);
			switch(type){
				case 1:
					List<OrderDeliveryDetailDTO> orderList = productDeliveryDetailToolService.getOrderByMember(params);
					modelMap.put("orderList", orderList);
					break;
				case 2:
					pageName = "/H5/transfer_v2/productDetail";
					List<ProductDeliveryDetailDTO> productList = productDeliveryDetailToolService.getProductByMember(params);
					modelMap.put("productList", productList);
					break;
				default:
						logger.warn("[WARNING]货物类型不正确!!");
						break;
			}

		} catch (Exception e) {
			logger.warn("查询货物详情异常:" + e.getMessage(), e);
		}

		return pageName;
	}
	
	@RequestMapping("addCall")
	@ResponseBody
	public void addCall(HttpServletRequest request, HttpServletResponse response, CallstatiSticsDTO callstatiSticsDTO) {
		ObjectResult result = new ObjectResult();

		try {
			callstatiSticsDTO.setSource("WLXQ");
			callService.insert(callstatiSticsDTO);
			// 填充结果集
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("添加电话记录失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}
	
	/**
	 * 发送农速通请求
	 * 公共方法
	 * @param paramMap
	 * @param uriEnum
	 * @return
	 * @throws Exception
	 */
	public String getUrl(NstApiRequestV1Enum uriEnum) throws Exception{
		return gdProperties.getNstApiUrl() + uriEnum.getUri();
	}
	
	private ErrorCodeEnum setEditQueryMap(Map<String, Object> queryMap,
			Integer status, NstRequestParamDTO requestDTO) {
		NstApiRequestV1Enum requestEnum = NstApiRequestV1Enum.getByCode(status);
		switch(requestEnum){
			case DELETE_GOODS_TRANSFER: //删除货源
				queryMap.put("id", requestDTO.getTransferId());
				queryMap.put("version", requestDTO.getVersion());
				queryMap.put("updateUserId", requestDTO.getMemberId());
				break;
			case REFUSE_GOODS_TRANSFER: //拒绝货源
				if(requestDTO.getOrderId() == null){
					return ErrorCodeEnum.FAIL;
				}
				queryMap.put("orderBeforeId", requestDTO.getOrderId());
				queryMap.put("sourceShipperId", requestDTO.getTransferId());
				queryMap.put("version", requestDTO.getVersion());
				queryMap.put("updateUserId", requestDTO.getMemberId());
				break;
			case ACCEPT_GOODS_TRANSFER: //接受货源
				if(requestDTO.getOrderId() == null){
					return ErrorCodeEnum.FAIL;
				}
				queryMap.put("sourceId", requestDTO.getTransferId());
				queryMap.put("version", requestDTO.getVersion());
				queryMap.put("orderBeforeId", requestDTO.getOrderId());
				break;
			case CONFIRM_GOODS_TRANSFER: //确认收货
				if(requestDTO.getOrderId() == null){
					return ErrorCodeEnum.FAIL;
				}
				queryMap.put("orderBeforeId", requestDTO.getOrderId());
				queryMap.put("sourceId", requestDTO.getTransferId());
				queryMap.put("version", requestDTO.getVersion());
				queryMap.put("updateUserId", requestDTO.getMemberId());
				break;
			default:
				return ErrorCodeEnum.FAIL;
		}
		
		return ErrorCodeEnum.SUCCESS;
	}
	

	private ProCommonPageDTO setNstTransferList(Map<String, Object> resultMap, Integer status) {
		ProCommonPageDTO pageDTO = new ProCommonPageDTO();
		List<NstTransferListOutputDTO> nstTransferList = null;
		if(resultMap == null){
			pageDTO.initiatePage(0);
			return pageDTO;
		}
		
		Integer recordCount = getIntegerValue(resultMap, "recordCount");
		if(recordCount != null && recordCount > 0){
			//设置分页参数
			setPageParams(pageDTO, resultMap, recordCount);
			
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> recordList = (List<Map<String, Object>>) resultMap.get("recordList");
			if(recordList != null && recordList.size() > 0){
				nstTransferList = new ArrayList<>();
				for(Map<String, Object> dataMap : recordList){
					NstTransferListOutputDTO listDTO = setTransferListData(dataMap);
					listDTO.setStatus(status);
					nstTransferList.add(listDTO);
				}
			}
			pageDTO.setRecordList(nstTransferList);
		}
		return pageDTO;
	}
	
	public int getIntegerValue(Map<String, Object> map, String key){
		String sIntValue = map.get(key) == null ? "0.0" : map.get(key).toString();
		BigDecimal intValue = new BigDecimal(sIntValue);
		return intValue.intValue();
	}

	private NstTransferListOutputDTO setTransferListData(Map<String, Object> dataMap) {
		NstTransferListOutputDTO listDTO = new NstTransferListOutputDTO();
		//是否物流订单
		if(dataMap.get("orderNo") != null){
			listDTO.setOrderNo(StringUtil.toString(dataMap.get("orderNo")));
			listDTO.setStartAddr(StringUtil.toString(dataMap.get("srcFullAddr")));
			listDTO.setEndAddr(StringUtil.toString(dataMap.get("desFullAddr")));
			listDTO.setTransferId(getIntegerValue(dataMap, "sourceId"));
			listDTO.setMobile(StringUtil.toString(dataMap.get("driverMobile")));
			listDTO.setOrderId(getIntegerValue(dataMap, "orderBeforeId"));
			listDTO.setPayStatus(getIntegerValue(dataMap, "payStatus"));
			listDTO.setDriverMemberId(getIntegerValue(dataMap, "driverMemberId") + "");
		}else{
			listDTO.setGoodsDate(dataMap.get("sendDate") == null ? "" : dataMap.get("sendDate") .toString().substring(0, 11));
			listDTO.setStartAddr(StringUtil.toString(dataMap.get("srcFullAddr")));
			listDTO.setEndAddr(StringUtil.toString(dataMap.get("desFullAddr")));
			boolean isCanDispose = Boolean.parseBoolean(dataMap.get("isCanDispose").toString());
			listDTO.setIsProxy(isCanDispose ? 0 : 1);
			listDTO.setTransferId(getIntegerValue(dataMap, "id"));
			listDTO.setVersion(getIntegerValue(dataMap, "version"));
			listDTO.setOrderId(getIntegerValue(dataMap, "orderBeforeId"));
			//代发设置物流公司电话
			if(isCanDispose){
				listDTO.setMobile(StringUtil.toString(dataMap.get("driverMobile")));
			}else{//直发设置司机电话
				listDTO.setMobile(StringUtil.toString(dataMap.get("assignMemberMobile")));
			}
		}
		return listDTO;
	}

	private void setPageParams(ProCommonPageDTO pageDTO, Map<String, Object> map, Integer recordCount) {
		pageDTO.setCurrentPage(getIntegerValue(map, "currentPage"));
		pageDTO.setNextPage(getIntegerValue(map, "nextPage"));
		pageDTO.setPageCount(getIntegerValue(map, "pageCount"));
		pageDTO.setPageSize(getIntegerValue(map, "pageSize"));
		pageDTO.setPrePage(getIntegerValue(map, "prePage"));
		pageDTO.setHasNextPage(Boolean.parseBoolean(map.get("hasNextPage").toString()));
		pageDTO.setHasPrePage(Boolean.parseBoolean(map.get("hasPrePage").toString()));
		pageDTO.setRecordCount(recordCount);
		pageDTO.initiatePage(recordCount);
	}

	private NstTransferDetailOutputDTO setNstTransferDetail(Map<String, Object> map, NstApiRequestV1Enum requestEnum) {
		NstTransferDetailOutputDTO detialDTO = new NstTransferDetailOutputDTO();
		switch(requestEnum){
			case QUERY_TRANSFER_DETAIL:
				boolean isCanDispose = Boolean.parseBoolean(map.get("isCanDispose").toString());
				
				if(map.get("driverAcceptDate") != null){
					Calendar calendar = DateTimeUtils.pars2Calender(map.get("driverAcceptDate").toString());
					calendar.add(Calendar.MINUTE, 10);
					detialDTO.setAcceptTime(DateTimeUtils.formatCalendar(calendar, "yyyy-MM-dd HH:mm:ss"));
				}else{
					detialDTO.setAcceptTime("");
				}
				
				detialDTO.setIsProxy(isCanDispose ? 0 : 1);
				detialDTO.setOrderId(getIntegerValue(map, "orderBeforeId"));
				int status = getStatus(getIntegerValue(map, "goodsStatusCode"), false);
				detialDTO.setStatus(status);
				detialDTO.setTransferId(getIntegerValue(map, "id"));
				detialDTO.setVersion(getIntegerValue(map, "version"));
				detialDTO.setCompanyInfo(getCompanyInfo(map, false, status));
				detialDTO.setDriverInfo(getDriverInfo(map, false, status));
				detialDTO.setGoodsInfo(getGoodsInfo(map, false));
				detialDTO.setDeliverInfo(getDeliverInfo(map, false));
				break;
			case QUERY_ORDER_DETAIL:
				detialDTO.setOrderNo(StringUtil.toString(map.get("orderInfoNo")));
				//1 直发 2 代发 3分配中
				int nstRule = getIntegerValue(map, "nstRule");
				detialDTO.setIsProxy(nstRule == 2 ? 1 : 0);
				detialDTO.setOrderId(getIntegerValue(map, "orderBeforeId"));
				int status2 = getStatus(getIntegerValue(map, "orderInfoStatus"), true);
				detialDTO.setStatus(status2);
				//付款状态:1待付款 2已付款
				int payStatus = getIntegerValue(map, "orderInfoPayStatus");
				detialDTO.setPayStatus(payStatus);
				if(payStatus == 2){
					detialDTO.setFreight(MoneyUtil.formatMoney((Double)map.get("orderInfoPayMoney")));
					detialDTO.setPaySerialNo(StringUtil.toString(map.get("orderInfoPlatformPayWater")));
				}
				detialDTO.setTransferId(getIntegerValue(map, "sourceId"));
				detialDTO.setVersion(0);
				detialDTO.setCompanyInfo(getCompanyInfo(map, true, status2));
				detialDTO.setDriverInfo(getDriverInfo(map, true, status2));
				detialDTO.setGoodsInfo(getGoodsInfo(map, true));
				detialDTO.setDeliverInfo(getDeliverInfo(map, true));
				break;
			default: 
				detialDTO = null;
				break;
		}
		return detialDTO;
	}

	private NstTransferGoodsDetailDTO getGoodsInfo(Map<String, Object> map,
			boolean isOrderTransfer) {
		NstTransferGoodsDetailDTO goodsDetial = new NstTransferGoodsDetailDTO();
		if(isOrderTransfer){
			String money = MoneyUtil.format(Double.parseDouble(map.get("freight") == null ? "0":map.get("freight").toString()));
			goodsDetial.setPrice(money.equals("面议") ?  "面议" :  money + "元");
			goodsDetial.setStartAddr(StringUtil.toString(map.get("srcFullAddr")));
			goodsDetial.setEndAddr(StringUtil.toString(map.get("desFullAddr")));
			goodsDetial.setDeliverer(StringUtil.toString(map.get("shipperName")));
			goodsDetial.setGoodsTypeName(StringUtil.toString(map.get("goodsTypeStr")));
			goodsDetial.setCarTypeName(StringUtil.toString(map.get("carTypeStr")));
		}else{
			goodsDetial.setPrice(StringUtil.toString(map.get("freightName")));
			goodsDetial.setStartAddr(StringUtil.toString(map.get("srcFullAddr")));
			goodsDetial.setEndAddr(StringUtil.toString(map.get("desFullAddr")));
			goodsDetial.setDeliverer(StringUtil.toString(map.get("shipperName"))); 
			goodsDetial.setGoodsTypeName(StringUtil.toString(map.get("goodsTypeName")));
			goodsDetial.setCarTypeName(StringUtil.toString(map.get("carTypeName")));
		}
		
		goodsDetial.setHasGoods(0);
		goodsDetial.setWeight(StringUtil.toString(map.get("totalWeight")));
		goodsDetial.setSize(getIntegerValue(map, "totalSize")+"");
		String sendDate = StringUtil.toString(map.get("sendDate"));
		goodsDetial.setGoodsDate(StringUtils.isBlank(sendDate) ? "不限" : sendDate.substring(0, 16));
		goodsDetial.setRemark(StringUtil.toString(map.get("remark")));
		return goodsDetial;
	}

	@SuppressWarnings("unchecked")
	private List<NstTransferDeliverDetailDTO> getDeliverInfo(
			Map<String, Object> map, boolean isOrderTransfer) {
		List<NstTransferDeliverDetailDTO> deliverList = new ArrayList<>();
		List<Map<String, Object>> dataList;
		if(isOrderTransfer){
			dataList = (List<Map<String, Object>>) map.get("operateLogVo");
		}else{
			dataList = (List<Map<String, Object>>) map.get("sourceLogs");
		}
		
		for(Map<String, Object> tmpMap : dataList){
			NstTransferDeliverDetailDTO deliver = new NstTransferDeliverDetailDTO();
			deliver.setDateTime(StringUtil.toString(tmpMap.get("createTime")));
			deliver.setDescription(StringUtil.toString(tmpMap.get("description")));
			deliverList.add(deliver);
		}
		return deliverList;
	}

	private NstTransferDriverDetailDTO getDriverInfo(Map<String, Object> map,
			boolean isOrderTransfer, int status) {
		NstTransferDriverDetailDTO driverDTO = null;
		if(isOrderTransfer){
			driverDTO = new NstTransferDriverDetailDTO();
			driverDTO.setPhotoUrl(StringUtil.toString(map.get("driverIconUrl")));
			driverDTO.setDriverMemberId(StringUtil.toString(map.get("driverMemberId")));
			driverDTO.setCarLength(StringUtil.toString(map.get("driverCarLength")));
			driverDTO.setCarLoad(StringUtil.toString(map.get("driverCarLoad")));
			driverDTO.setCarNo(StringUtil.toString(map.get("driverCarNumber")));
			driverDTO.setCarTypeName(StringUtil.toString(map.get("driverCarTypeStr")));
			int isIdentify = getIntegerValue(map, "driverCerStatus");
			driverDTO.setIsIdentify(isIdentify==1 ? 1 : 0);
			driverDTO.setMobile(StringUtil.toString(map.get("driverMobile")));
			driverDTO.setName(StringUtil.toString(map.get("driverRealName")));
		}else if(status == NstApiRequestV1Enum.QUERY_WAIT_CONFIRM_TRANSFER.getCode()){
			//待确认状态且没有物流公司信息才有司机信息
			if(map.get("assignMemberId") == null){
				driverDTO = new NstTransferDriverDetailDTO();
				driverDTO.setPhotoUrl(StringUtil.toString(map.get("driverIconUrl")));
				driverDTO.setCarLength(StringUtil.toString(map.get("driverCarLength")));
				driverDTO.setCarLoad(StringUtil.toString(map.get("driverLoad")));
				driverDTO.setCarNo(StringUtil.toString(map.get("driverCarNumber")));
				driverDTO.setCarTypeName(StringUtil.toString(map.get("driverCarTypeName")));
				boolean isIdentify = Boolean.parseBoolean(map.get("driverAuth").toString());
				driverDTO.setIsIdentify(isIdentify ? 1 : 0);
				driverDTO.setMobile(StringUtil.toString(map.get("driverMobile")));
				driverDTO.setName(StringUtil.toString(map.get("driverName")));
			}
		}
		return driverDTO;
	}
	
	private NstTransferCompanyDetailDTO getCompanyInfo(Map<String, Object> map,
			boolean isOrderTransfer, int status) {
		NstTransferCompanyDetailDTO companyDTO = null;
		if(isOrderTransfer){
			return companyDTO;
		}else if(status == NstApiRequestV1Enum.QUERY_WAIT_CONFIRM_TRANSFER.getCode()){
			//待确认状态才有物流公司信息
			if(map.get("assignMemberId") != null){
				companyDTO = new NstTransferCompanyDetailDTO();
				companyDTO.setPhotoUrl(StringUtil.toString(map.get("assignMemberIconUrl")));
				boolean isIdentify = Boolean.parseBoolean(map.get("assignMemberAuth").toString());
				companyDTO.setIsIdentify(isIdentify ? 1 : 0);
				companyDTO.setMobile(StringUtil.toString(map.get("assignMemberMobile")));
				companyDTO.setName(StringUtil.toString(map.get("assignMemberName")));
			}
		}
		return companyDTO;
	}

	private Integer getStatus(int nstStatus, boolean isOrderTransfer) {
		Integer status = 0;
		switch(nstStatus){
			case 1:
				//待收货状态
				if(isOrderTransfer){
					status = NstApiRequestV1Enum.QUERY_WAIT_ACCEPT_TRANSFER.getCode();
				}else{
					//分配中状态
					status = NstApiRequestV1Enum.QUERY_PUBLISHING_TRANSFER.getCode();
				}
				break;
			case 2:
				//已完成状态
				if(isOrderTransfer){
					status = NstApiRequestV1Enum.QUERY_FINISH_TRANSFER.getCode();
				}else{
					//待确认状态
					status = NstApiRequestV1Enum.QUERY_WAIT_CONFIRM_TRANSFER.getCode();
				}
				break;
			case 3:
				//已发布状态
				status = NstApiRequestV1Enum.QUERY_PUBLISH_TRANSFER.getCode();
				break;
			case 5:
				//已过期状态
				status = NstApiRequestV1Enum.QUERY_OVERDUE_TRANSFER.getCode();
				break;
			default:
				break;
		}
		return status;
	}
}
