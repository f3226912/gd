package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.dto.*;
import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.service.*;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.util.*;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.CanTakeOrderInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NstOrderStatus;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderBaseinfoEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import static com.gudeng.paltform.sms.lxt.LxtConfig.url;

/**
 * 农速通运单Controller
 *
 * @author xiaojun
 */
@Controller
@RequestMapping("nst2/order")
public class NstOrderBaseinfoController extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(NstOrderBaseinfoController.class);

	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;

	@Autowired
	private NstOrderNoToolService nstOrderNoToolService;

	@Autowired
	public PushNstMessageApiService pushNstMessageService;
	@Autowired
	private DeliveryAddressToolService deliveryAddressToolService;
	@Autowired
	private NstApiCommonService nstApiCommonService;
	@Autowired
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;

	@Autowired
	private OrderToolService orderToolService;
	@Autowired
	private OrderTool2Service orderTool2Service;

	@Autowired
	private GdProperties gdProperties;
	@Autowired
	private PaySerialnumberToolService paySerialnumberToolService;

	//orderProductDetailService
	/**
	 * 待接收订单状态
	 */
	private static final String confirmStatus = "1";

	/**
	 * 确认收货状态
	 */
	private static final String confirmGoodsStatus = "2";

	/**
	 * 投诉和评价状态
	 */
	private static final String complaintAndEvaluateStatus = "3";

	/**
	 * 投诉和评价状态的扩展处理(在确认收货后)
	 */
	private static final String complaintAndEvaluateStatusExtend = "7";

	/**
	 * 评价状态
	 */
	private static final String evaluateStatus = "4";

	/**
	 * 不处理状态
	 */
	private static final String notStatus = "5";

	/**
	 * 拒绝接单状态
	 */
	private static final String refuseStatus = "6";

	/**
	 * 订单采购清单
	 * 传入memberAddressId 货源id
	 * 找到订单，并查找订单下的产品信息
	 *
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/productlist")
	public void productlist(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			String memberAddressId = inputDTO.getMemberAddressId();
			if (memberAddressId == null) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("memberAddressId", memberAddressId);
			//查找产品出货详细表
			List<?> pdd = productDeliveryDetailToolService.getListByCondition(map);
			if (pdd != null && pdd.size() > 0) {
				ProductDeliverListAppDTO productDeliveryDetailDTO = (ProductDeliverListAppDTO) pdd.get(0);//产品出货详细
				Long orderNo = productDeliveryDetailDTO.getOrderNo();//订单号
				if (orderNo == null) {
					setEncodeResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
					return;
				}
				//查找订单
				OrderDetailAppDTO orderDetail = orderTool2Service.getOrderDetail(orderNo, null);
				if (orderDetail != null) {
					map.clear();
					map.put("orderNo", orderNo);//订单号
					map.put("serialType", 1);//款项类型：1：预付款流水
					//预付款支付流水信息
					List<PaySerialnumberDTO> paySerialnumberDTOList = paySerialnumberToolService.getListByCondition(map);
					if (paySerialnumberDTOList != null && paySerialnumberDTOList.size() > 0) {
						orderDetail.setPrepayTime(DateUtil.getDate(paySerialnumberDTOList.get(0).getPayTime(), "yyyy-MM-dd HH:mm:ss"));//预付款支付时间
						orderDetail.setPrepayStatus("支付成功");//预付款支付状态
					} else {
						orderDetail.setPrepayStatus("待支付");
					}
					map.put("serialType", 2);//款项类型：2：尾款流水
					//尾款支付流水信息
					paySerialnumberDTOList = paySerialnumberToolService.getListByCondition(map);
					if (paySerialnumberDTOList != null && paySerialnumberDTOList.size() > 0) {
						orderDetail.setRestpayTime(DateUtil.getDate(paySerialnumberDTOList.get(0).getPayTime(), "yyyy-MM-dd HH:mm:ss"));//尾款支付时间
						orderDetail.setRestpayStatus("支付成功");//尾款支付状态
					} else {
						orderDetail.setRestpayStatus("待支付");
					}
					result.setObject(orderDetail);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				} else {
					setEncodeResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
				}
				return;
			} else {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_NOT_EXISTED, request, response);
				return;
			}
		} catch (Exception e) {
			logger.warn("[ERROR]查看订单采购清单信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}


	/**
	 * 农速通订单分配物流不成功回调通知
	 * 传入memberAddressId 货源id
	 * 找到订单，并修改订单状态以及失败原因
	 *
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/logisticalNotify")
	public void logisticalNotify(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			String memberAddressId = inputDTO.getMemberAddressId();
			if (memberAddressId == null) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("memberAddressId", memberAddressId);
			//查找产品出货详细表
			List<ProductDeliverListAppDTO> pdd = (List<ProductDeliverListAppDTO>) productDeliveryDetailToolService.getListByCondition(map);
			if (pdd != null && pdd.size() > 0) {
				ProductDeliverListAppDTO productDeliveryDetailDTO = (ProductDeliverListAppDTO) pdd.get(0);//产品出货详细
				Long orderNo = productDeliveryDetailDTO.getOrderNo();//订单号
				//修改订单信息
				OrderBaseinfoDTO orderBaseinfoDTO = orderToolService.getOrderByOrderNo(orderNo);
				orderBaseinfoDTO.setOrderStatus("8");//订单状态已关闭
				orderBaseinfoDTO.setCancelReason("分配物流失败");//关闭原因
				orderBaseinfoDTO.setCloseTimeStr(DateUtil.getDate(new Date(),DateUtil.DATE_FORMAT_DATETIME));//关闭时间
				int id = orderToolService.updateByOrderNo(orderBaseinfoDTO);
				map.clear();
				map.put("orderNo",orderNo);
				orderToolService.resumeStock(map);//恢复订单商品库存
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			} else {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_NOT_EXISTED, request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("[ERROR]修改订单物流状态信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}


	/**
	 * 订单物流详情
	 * 根据订单号，查看订单物流详情
	 *
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/logisticaldetail")
	public void logisticaldetail(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			Long orderNo = inputDTO.getOrderNo();
			Long memberId = inputDTO.getMemberId();
			if (orderNo == null) {
				setEncodeResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			if (memberId == null) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("orderNo", orderNo);
			//查找订单收获地址
			List<DeliveryAddressDTO> list = deliveryAddressToolService.getList(map);
			if (list != null && list.size() > 0) {
				DeliveryAddressDTO deliveryAddressDTO = list.get(0);//订单收获地址记录
				String nstOrderNo = deliveryAddressDTO.getNstOrderNo();//农速通运单号
//				nstOrderNo = "42201603110001";//测试数据
				if (StringUtils.isNotEmpty(nstOrderNo)) {
					//调用农速通接口，获取订单物流详情信息
					//订单物流详情请求地址
					map.put("token", nstApiCommonService.getNstToken(memberId + ""));
					map.put("orderNo", nstOrderNo);
					String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.QUERY_ORDER_TRANSFER.getUri();
					String sNstApiResult = HttpClients.doPost(url, Des3.encode(new Gson().toJson(map)));
					String decode = Des3.decode(sNstApiResult);//物流详情解密后的json
					decode = decode.replace("code", "statusCode").replace("result", "object");//适配农速通-api数据结构
					result=(ObjectResult)GSONUtils.fromJsonToObject(decode, ObjectResult.class);//农速通json结构适配到api项目json结构
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
					//当以上适配代码无效的时候，使用下方的适配代码
					/*response.getWriter().write(Des3.encode(decode));
					response.getWriter().flush();
					response.getWriter().close();*/
					return;
				} else {
					//不成功则返回错误信息
					setEncodeResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
					return;
				}
			} else {
				setEncodeResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
				return;
			}
		} catch (Exception e) {
			logger.warn("[ERROR]获取订单物流详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}


	/**
	 * 根据货源下订单 confirmMemberId--createUserId(货主) memberId--当前登录人memberId(车主)
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/insertByAddress")
	public void insertByAddress(HttpServletRequest request, HttpServletResponse response,
								NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			// 成功返回1，失败返回0
			int number = shouldMemberIdAndS_provinceId(result, nstOrderBaseinfoDTO, request, response);
			if (number == 0) {
				return;
			}
			NstOrderBaseinfoEntity entity = getNstOrderBaseinfoEntity(nstOrderBaseinfoDTO);
			// 根据前台传过的接单ID和货源的MemberId 分别查出车主和车主手机号码，发货人和发货人手机号码，一起插入运单中
			entity = byAddress(entity, nstOrderBaseinfoDTO);
			// 设置运单类型
			entity.setOrderType("1");
			entity.setSourceType(nstOrderBaseinfoDTO.getSourceType());
			if (null == entity.getSourceType()) {
				entity.setSourceType(Byte.valueOf("0"));
			}
			//判断货源有没有接单
			if (entity.getSourceType() == 0) {
				boolean isa = isAccept(entity.getMemberAddressId(), (byte) 0);
				if (isa) {
					logger.debug("此货源已被接单");
					setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_DELIVER, request, response);
					return;
				}
			} else if (entity.getSourceType() == 1) {
				boolean isa = isAccept(entity.getSame_memberAddressId(), (byte) 1);
				if (isa) {
					logger.debug("此货源已被接单");
					setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_DELIVER, request, response);
					return;
				}
			}

			// 如果是接同城货源的单，则判断车主是否已经接了5单没有完成
			if (entity.getSourceType() != null && entity.getSourceType() == 1) {
				boolean b = isCanAccept(entity);
				if (!b) {
					logger.debug("您还有5条订单未处理哦");
					setResult(result, ErrorCodeEnum.ORDERS_NEED_HANDLE, request, response);
					return;
				}
			}
			Long id = nstOrderBaseinfoToolService.insertNstOrderBaseinfo(entity);
			if (id < 0) {
				logger.debug("返回运单主键为空");
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			// 将订单号返回给前台展示
			nstOrderBaseinfoDTO.setOrderNo(entity.getOrderNo());
			result.setObject(nstOrderBaseinfoDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("插入运单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 同城货源，判断是否可以接单
	 *
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private boolean isCanAccept(NstOrderBaseinfoEntity entity) throws Exception {
		// 根据货物接单，则直接判断当前登录人，也就是车主
		// 根据线路接单，则判断订单确认人 ，也就是车主
		Long memberId = null;
		if ("1".equals(entity.getOrderType())) {
			memberId = entity.getMemberId();
		} else {
			memberId = entity.getConfirmMemberId();
		}
		Integer count = nstOrderBaseinfoToolService.getMemberSameCityOrderCount(memberId);
		// 如果 控制车主最多接5条 待确认的订单
		if (count >= 5) {
			return false;
		}
		return true;
	}

	/**
	 * 接单时候判断 货源有没有被接
	 *
	 * @param id
	 * @param sourceType
	 * @return
	 * @throws Exception
	 */
	private boolean isAccept(Long id, Byte sourceType) throws Exception {
		Integer status = nstOrderBaseinfoToolService.addressOrderStatus(id, sourceType);
		if (status != null && status == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断根据货源找货，在线路管理中，我的接单
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/listNstOrderBaseInfoList")
	public void listNstOrderBaseInfoList(HttpServletRequest request, HttpServletResponse response,
										 NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if (nstOrderBaseinfoDTO.getMemberId() == null) {
				logger.debug("传入的memberId不能为空");
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = getMap(nstOrderBaseinfoDTO);
			// 做分页
			int total = nstOrderBaseinfoToolService.getNstOrderListByConditionTotal(map);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			List<NstOrderBaseinfoDTO> nstOrderList = nstOrderBaseinfoToolService.getNstOrderListByCondition(map);

			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(nstOrderList);

			result.setObject(pageDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("显示所有我接的单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 我接的单，根据orderNo显示详情
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/listNstOrderBaseInfoDetail")
	public void listNstOrderBaseInfoDetail(HttpServletRequest request, HttpServletResponse response,
										   NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if (nstOrderBaseinfoDTO.getCurrentMemberId() == null) {
				logger.debug("传入的memberId不能为空");
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}

			if (StringUtils.isEmpty(nstOrderBaseinfoDTO.getOrderNo())) {
				logger.debug("传入的orderNo不能为空");
				setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = getMap(nstOrderBaseinfoDTO);
			List<NstOrderBaseinfoDTO> nstOrderList = nstOrderBaseinfoToolService.getNstOrderListByCondition(map);
			if (nstOrderList.size() != 0) {
				// 拼接图片URL
				for (NstOrderBaseinfoDTO nstDto : nstOrderList) {
					concatImageUrl(nstDto);
				}
			}
			// 返回给前台需要显示的状态
			String status = showStatus(nstOrderBaseinfoDTO, nstOrderList);
			for (NstOrderBaseinfoDTO nstDto : nstOrderList) {
				nstDto.setState(status);
			}
			if ("".equals(status)) {
				logger.debug("返回显示状态出现问题");
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			result.setObject(nstOrderList);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg(status);
		} catch (Exception e) {
			logger.info("显示所有我接的单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 接收订单（状态变已提交）
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/acceptOrder")
	public void acceptOrder(HttpServletRequest request, HttpServletResponse response,
							NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = shouldOrderNoAndMemberId(result, nstOrderBaseinfoDTO, map, request, response);
			// 如果map长度为0，则return
			if (map.size() == 0) {
				return;
			}
			// 查出的订单状态为1是才可以被提交订单
			int orderNoStatus = nstOrderBaseinfoToolService.getStatusByOrderNo(nstOrderBaseinfoDTO.getOrderNo());

			if (orderNoStatus != 1) {
				setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_CANCEL, request, response);
				return;
			}
			Integer status = nstOrderBaseinfoToolService.acceptOrder(map);
			if (status <= 0) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("接收订单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 拒绝订单
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/refuseOrder")
	public void refuseOrder(HttpServletRequest request, HttpServletResponse response,
							NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = shouldOrderNoAndMemberId(result, nstOrderBaseinfoDTO, map, request, response);
			// 如果map长度为0，则return
			if (map.size() == 0) {
				return;
			}
			// 查出的订单状态为1是才可以被拒绝订单
			int orderNoStatus = nstOrderBaseinfoToolService.getStatusByOrderNo(nstOrderBaseinfoDTO.getOrderNo());
			if (orderNoStatus != 1) {
				setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_CANCEL, request, response);
				return;
			}
			Integer status = nstOrderBaseinfoToolService.refuseOrder(map);
			if (status <= 0) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("拒绝订单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 取消订单
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/cancelOrder")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response,
							NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = shouldOrderNoAndMemberId(result, nstOrderBaseinfoDTO, map, request, response);
			// 如果map长度为0，则return
			if (map.size() == 0) {
				return;
			}
			// 查出的订单状态为1是才可以被取消订单
			int orderNoStatus = nstOrderBaseinfoToolService.getStatusByOrderNo(nstOrderBaseinfoDTO.getOrderNo());
			if (orderNoStatus == 5) {
				setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_CANCEL, request, response);
				return;
			}
			if (orderNoStatus == 4) {
				setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_REFUSE, request, response);
				return;
			}
			if (orderNoStatus == 2) {
				setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_CONFIRM, request, response);
				return;
			}
			Integer status = nstOrderBaseinfoToolService.cancelOrder(map);
			if (status <= 0) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("取消接单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 删除订单
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/batchDeleteOrder")
	public void batchDeleteOrder(HttpServletRequest request, HttpServletResponse response,
								 NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isEmpty(nstOrderBaseinfoDTO.getOrderNoString())) {
				logger.debug("批量删除orderNo不能为空");
				setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			if (nstOrderBaseinfoDTO.getMemberId() == null) {
				logger.debug("传入memberId不能为空");
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// app端传入orderNoString,多个订单号以逗号隔开
			List<String> orderNoList = Arrays.asList(nstOrderBaseinfoDTO.getOrderNoString().split(","));
			map.put("orderNoList", orderNoList);
			map.put("memberId", nstOrderBaseinfoDTO.getMemberId());
			Integer status = nstOrderBaseinfoToolService.batchDeleteOrder(map);
			if (status <= 0) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("批量删除失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 确认收货，状态设置为已完成
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/confirmGoods")
	public void confirmGoods(HttpServletRequest request, HttpServletResponse response,
							 NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = shouldOrderNoAndMemberId(result, nstOrderBaseinfoDTO, map, request, response);
			// 如果map长度为0，则return
			if (map.size() == 0) {
				return;
			}
			Integer status = nstOrderBaseinfoToolService.confirmGoods(map);
			if (status <= 0) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("确认收货失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);

	}

	/**
	 * 根据线路接单，货主点击接单，弹到正式接单页面，选择货源
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/checkGoodsInfo")
	public void checkGoodsInfo(HttpServletRequest request, HttpServletResponse response,
							   NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if (nstOrderBaseinfoDTO.getCarLineId() == null) {
				logger.debug("线路Id不能为空");
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			NstOrderBaseinfoDTO nstDto = new NstOrderBaseinfoDTO();
			if (null == nstOrderBaseinfoDTO.getSourceType()) {
				nstOrderBaseinfoDTO.setSourceType(Byte.valueOf("0"));
			}
			if (nstOrderBaseinfoDTO.getSourceType() == 0) {
				nstDto = nstOrderBaseinfoToolService.getCarLineInfoByCarLineId(nstOrderBaseinfoDTO.getCarLineId());
			} else {
				nstDto = nstOrderBaseinfoToolService.getSameCarLineInfoByCarLineId(nstOrderBaseinfoDTO.getCarLineId());
			}
			if (nstDto != null) {
				// 拼接图片URL
				concatImageUrl(nstDto);
				// 查询出车主对应的信息
				NstOrderBaseinfoDTO nstDto2 = nstOrderBaseinfoToolService
						.getMemberInfoByMemberId(nstDto.getOwnerMemberId());
				nstDto.setOwnerName(nstDto2.getName());
				nstDto.setOwnerMobile(nstDto2.getMobile());
			}
			result.setObject(nstDto);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("选择货源失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 根据线路下订单 confirmMemberId--ownerMemberId(车主) memberId--当前登录人memberId(货主)
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/insertByCarline")
	public void insertByCarline(HttpServletRequest request, HttpServletResponse response,
								NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			// 成功返回1，失败返回0
			int number = shouldMemberIdAndS_provinceId(result, nstOrderBaseinfoDTO, request, response);
			if (number == 0) {
				return;
			}
			NstOrderBaseinfoEntity entity = getNstOrderBaseinfoEntity(nstOrderBaseinfoDTO);
			// 根据前台传过的接单ID和货源的MemberId 分别查出车主和车主手机号码，发货人和发货人手机号码，一起插入运单中
			entity = byCarline(entity, nstOrderBaseinfoDTO);
			// 设置运单类型
			entity.setOrderType("2");
			entity.setSourceType(nstOrderBaseinfoDTO.getSourceType());

			//判断货源有没有接单
			if (entity.getSourceType() == 0) {
				boolean isa = isAccept(entity.getMemberAddressId(), (byte) 0);
				if (isa) {
					logger.debug("此货源已被接单");
					setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_DELIVER, request, response);
					return;
				}
			} else if (entity.getSourceType() == 1) {
				boolean isa = isAccept(entity.getSame_memberAddressId(), (byte) 1);
				if (isa) {
					logger.debug("此货源已被接单");
					setResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_DELIVER, request, response);
					return;
				}
			}
			// 如果是接同城货源的单，则判断车主是否已经接了5单没有完成
			if (entity.getSourceType() == 1) {
				boolean b = isCanAccept(entity);
				if (!b) {
					logger.debug("车主的单超过接单限制");
					setResult(result, ErrorCodeEnum.ORDERS_NEED_HANDLE, request, response);
					return;
				}
			}
			Long id = nstOrderBaseinfoToolService.insertNstOrderBaseinfo(entity);
			if (id < 0) {
				logger.debug("返回运单主键为空");
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			// 将订单号返回给前台展示
			nstOrderBaseinfoDTO.setOrderNo(entity.getOrderNo());
			result.setObject(nstOrderBaseinfoDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("接单失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			e.printStackTrace();
		}
		renderJson(result, request, response);
	}

	/**
	 * 获取当前用户各种未读消息数量
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getNewCount")
	public void getNewCount(HttpServletRequest request, HttpServletResponse response,
							PushNstMessageDTO pushNstMessageDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if (pushNstMessageDTO.getMemberId() == null) {
				logger.debug("memberId不能为空");
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			int i = pushNstMessageService.getNewCount(pushNstMessageDTO);
			// carsDTO.setCarTotal(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("newCount", i);
			// 根据干线货源未提交运单
			int carLineCount = nstOrderBaseinfoToolService.getCount("1", pushNstMessageDTO.getMemberId(), "0");
			map.put("carLineCount", carLineCount);
			// 根据同城货源未提交运单
			int sameCarLineCount = nstOrderBaseinfoToolService.getCount("1", pushNstMessageDTO.getMemberId(), "1");
			map.put("sameCarLineCount", sameCarLineCount);
			// 根据干线线路未提交运单
			int goodsCount = nstOrderBaseinfoToolService.getCount("2", pushNstMessageDTO.getMemberId(), "0");
			map.put("goodsCount", goodsCount);
			// 根据同城线路未提交运单
			int sameGoodsCount = nstOrderBaseinfoToolService.getCount("2", pushNstMessageDTO.getMemberId(), "1");
			map.put("sameGoodsCount", sameGoodsCount);
			result.setObject(map);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("修改推送信息已读状态变失败", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

	/**
	 * 组装NstOrderBaseinfoEntity
	 *
	 * @param
	 * @return
	 */
	private NstOrderBaseinfoEntity getNstOrderBaseinfoEntity(NstOrderBaseinfoDTO nstOrderBaseinfoDTO) throws Exception {
		NstOrderBaseinfoEntity nstOrderBaseinfoEntity = new NstOrderBaseinfoEntity();
		// 生成订单号
		String orderNO = nstOrderNoToolService.getNstOrderNo(nstOrderBaseinfoDTO.getS_provinceId().toString());
		// 订单号
		nstOrderBaseinfoEntity.setOrderNo(orderNO);
		// 货源发货地
		nstOrderBaseinfoEntity.setF_address_detail(nstOrderBaseinfoDTO.getF_address_detail());
		// 货源收货地
		nstOrderBaseinfoEntity.setS_address_detail(nstOrderBaseinfoDTO.getS_address_detail());
		// 货源ID
		nstOrderBaseinfoEntity.setMemberAddressId(nstOrderBaseinfoDTO.getMemberAddressId());
		// 同城货源ID
		nstOrderBaseinfoEntity.setSame_memberAddressId(nstOrderBaseinfoDTO.getSame_memberAddressId());
		// 货源的memberId
		nstOrderBaseinfoEntity.setConfirmMemberId(nstOrderBaseinfoDTO.getConfirmMemberId());
		// 接单人员Id（当前登录人memberId）
		nstOrderBaseinfoEntity.setMemberId(nstOrderBaseinfoDTO.getMemberId());
		// 获取carId
		nstOrderBaseinfoEntity.setCarId(nstOrderBaseinfoDTO.getCarId());
		// 线路ID
		nstOrderBaseinfoEntity.setCarLineId(nstOrderBaseinfoDTO.getCarLineId());
		// 同城线路ID
		nstOrderBaseinfoEntity.setSame_carLineId(nstOrderBaseinfoDTO.getSame_carLineId());
		// 设置为待提交
		nstOrderBaseinfoEntity.setOrderStatus(1);
		// 接单时间
		nstOrderBaseinfoEntity.setOrderTime(new Date());
		// 创建人ID(当前登录人memberId)
		nstOrderBaseinfoEntity.setCreateUserId(
				nstOrderBaseinfoDTO.getMemberId() == null ? "" : nstOrderBaseinfoDTO.getMemberId().toString());
		// 创建时间
		nstOrderBaseinfoEntity.setCreateTime(new Date());
		// 修改人ID(当前登录人memberId)
		nstOrderBaseinfoEntity.setUpdateUserId(
				nstOrderBaseinfoDTO.getMemberId() == null ? "" : nstOrderBaseinfoDTO.getMemberId().toString());
		// 修改时间
		nstOrderBaseinfoEntity.setUpdateTime(new Date());
		return nstOrderBaseinfoEntity;
	}

	/**
	 * 组装查询map
	 *
	 * @param nstOrderBaseinfoDTO
	 * @return
	 */
	private Map<String, Object> getMap(NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		Map<String, Object> map = new HashMap<>();

		// 字段（sourceType） 判断是查询干线订单 还是同城订单 如果不传则查询干线和同城(0干线，1同城)
		map.put("sourceType", nstOrderBaseinfoDTO.getSourceType());

		map.put("id", nstOrderBaseinfoDTO.getId());
		map.put("memberId", nstOrderBaseinfoDTO.getMemberId());
		map.put("orderType", nstOrderBaseinfoDTO.getOrderType());
		map.put("orderNo", nstOrderBaseinfoDTO.getOrderNo());
		map.put("s_provinceId", nstOrderBaseinfoDTO.getS_provinceId());
		map.put("s_cityId", nstOrderBaseinfoDTO.getS_cityId());
		map.put("s_areaId", nstOrderBaseinfoDTO.getS_areaId());
		map.put("f_provinceId", nstOrderBaseinfoDTO.getF_provinceId());
		map.put("f_cityId", nstOrderBaseinfoDTO.getF_cityId());
		map.put("f_areaId", nstOrderBaseinfoDTO.getF_areaId());
		map.put("shipperName", nstOrderBaseinfoDTO.getShipperName());
		map.put("shipperMobile", nstOrderBaseinfoDTO.getShipperMobile());
		map.put("ownerName", nstOrderBaseinfoDTO.getOwnerName());
		map.put("ownerMobile", nstOrderBaseinfoDTO.getOwnerMobile());
		map.put("beginTime", nstOrderBaseinfoDTO.getBeginTime());
		map.put("endTime", nstOrderBaseinfoDTO.getEndTime());
		map.put("orderStatus", nstOrderBaseinfoDTO.getOrderStatus());
		map.put("canDelete", nstOrderBaseinfoDTO.getCanDelete());
		map.put("showRejectAndCal", nstOrderBaseinfoDTO.getShowRejectAndCal());
		return map;
	}

	/**
	 * 拼装Map ，必须有订单号和登录人ID
	 *
	 * @param result
	 * @param nstOrderBaseinfoDTO
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	private Map<String, Object> shouldOrderNoAndMemberId(ObjectResult result, NstOrderBaseinfoDTO nstOrderBaseinfoDTO,
														 Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(nstOrderBaseinfoDTO.getOrderNo())) {
			logger.debug("运单orderNo不能为空");
			setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
			return map;
		}
		if (nstOrderBaseinfoDTO.getMemberId() == null) {
			logger.debug("传入memberId不能为空");
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return map;
		}
		map.put("orderNo", nstOrderBaseinfoDTO.getOrderNo());
		map.put("memberId", nstOrderBaseinfoDTO.getMemberId());
		return map;
	}

	/**
	 * 需要参数 memberId(车主和货主) 和发货地省份Id
	 *
	 * @param result
	 * @param nstOrderBaseinfoDTO
	 * @param request
	 * @param response
	 */
	private int shouldMemberIdAndS_provinceId(ObjectResult result, NstOrderBaseinfoDTO nstOrderBaseinfoDTO,
											  HttpServletRequest request, HttpServletResponse response) {
		if (nstOrderBaseinfoDTO.getConfirmMemberId() == null || nstOrderBaseinfoDTO.getMemberId() == null) {
			logger.debug("传入货主memberId和车主memberId不能为空");
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return 0;
		}
		if (nstOrderBaseinfoDTO.getS_provinceId() == null) {
			logger.debug("传入发货地的省份Id不能为空");
			setResult(result, ErrorCodeEnum.SEND_PROVINCE_IS_NULL, request, response);
			return 0;
		}
		return 1;
	}

	/**
	 * 货主为当前登录人，车主为订单确认人
	 *
	 * @param entity
	 * @param nstOrderBaseinfoDTO
	 * @return
	 * @throws Exception
	 */
	private NstOrderBaseinfoEntity byCarline(NstOrderBaseinfoEntity entity, NstOrderBaseinfoDTO nstOrderBaseinfoDTO)
			throws Exception {
		NstOrderBaseinfoDTO nstDto = nstOrderBaseinfoToolService
				.getMemberInfoByMemberId(nstOrderBaseinfoDTO.getMemberId());
		if (nstDto != null) {
			entity.setShipperName(nstDto.getName());
			entity.setShipperMobile(nstDto.getMobile());
		}
		NstOrderBaseinfoDTO nstDto2 = nstOrderBaseinfoToolService
				.getMemberInfoByMemberId(nstOrderBaseinfoDTO.getConfirmMemberId());
		if (nstDto2 != null) {
			entity.setOwnerName(nstDto2.getName());
			entity.setOwnerMobile(nstDto2.getMobile());
		}
		return entity;
	}

	/**
	 * 车主为当前登录人，货主为订单确认
	 *
	 * @param entity
	 * @param nstOrderBaseinfoDTO
	 * @return
	 * @throws Exception
	 */
	private NstOrderBaseinfoEntity byAddress(NstOrderBaseinfoEntity entity, NstOrderBaseinfoDTO nstOrderBaseinfoDTO)
			throws Exception {
		NstOrderBaseinfoDTO nstDto = nstOrderBaseinfoToolService
				.getMemberInfoByMemberId(nstOrderBaseinfoDTO.getConfirmMemberId());
		if (nstDto != null) {
			entity.setShipperName(nstDto.getName());
			entity.setShipperMobile(nstDto.getMobile());
		}

		NstOrderBaseinfoDTO nstDto2 = nstOrderBaseinfoToolService
				.getMemberInfoByMemberId(nstOrderBaseinfoDTO.getMemberId());
		if (nstDto2 != null) {
			entity.setOwnerName(nstDto2.getName());
			entity.setOwnerMobile(nstDto2.getMobile());
		}
		return entity;
	}

	/**
	 * 拼接图片URL
	 *
	 * @param nstDto
	 */
	private void concatImageUrl(NstOrderBaseinfoDTO nstDto) {
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		if (StringUtils.isNotBlank(nstDto.getNst_cardPhotoUrl())) {
			nstDto.setNst_cardPhotoUrl(imageHost + nstDto.getNst_cardPhotoUrl());
		}
		if (StringUtils.isNotBlank(nstDto.getNst_driverPhotoUrl())) {
			nstDto.setNst_driverPhotoUrl(imageHost + nstDto.getNst_driverPhotoUrl());
		}
		if (StringUtils.isNotBlank(nstDto.getNst_vehiclePhotoUrl())) {
			nstDto.setNst_vehiclePhotoUrl(imageHost + nstDto.getNst_vehiclePhotoUrl());
		}
	}

	/**
	 * 前端显示状态返回
	 *
	 * @param nstOrderBaseinfoDTO
	 * @param nstOrderBaseinfoDTOs
	 * @return
	 */
	private String showStatus(NstOrderBaseinfoDTO nstOrderBaseinfoDTO, List<NstOrderBaseinfoDTO> nstOrderBaseinfoDTOs) {
		for (NstOrderBaseinfoDTO nstDTO : nstOrderBaseinfoDTOs) {
			if (("1".equals(nstDTO.getOrderType()) || "2".equals(nstDTO.getOrderType())) && nstDTO.getOrderStatus() == 1
					&& nstOrderBaseinfoDTO.getCurrentMemberId().longValue() == nstDTO.getConfirmMemberId()
					.longValue()) {
				return confirmStatus;
			}
			if (("1".equals(nstDTO.getOrderType()) || "2".equals(nstDTO.getOrderType())) && nstDTO.getOrderStatus() == 1
					&& nstOrderBaseinfoDTO.getCurrentMemberId().longValue() != nstDTO.getConfirmMemberId()
					.longValue()) {
				return refuseStatus;
			}
			if ("1".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 2 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() == nstDTO.getConfirmMemberId().longValue()) {
				return confirmGoodsStatus;
			}
			if ("1".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 2 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() != nstDTO.getConfirmMemberId().longValue()) {
				return complaintAndEvaluateStatus;
			}
			if ("1".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 3 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() == nstDTO.getConfirmMemberId().longValue()) {
				return evaluateStatus;
			}
			//
			if ("1".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 3 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() != nstDTO.getConfirmMemberId().longValue()) {
				return complaintAndEvaluateStatusExtend;
			}
			if (nstDTO.getOrderStatus() == 4 || nstDTO.getOrderStatus() == 5) {
				return notStatus;
			}
			if ("2".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 2 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() == nstDTO.getConfirmMemberId().longValue()) {
				return complaintAndEvaluateStatus;
			}
			if ("2".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 2 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() != nstDTO.getConfirmMemberId().longValue()) {
				return confirmGoodsStatus;
			}
			//
			if ("2".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 3 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() == nstDTO.getConfirmMemberId().longValue()) {
				return complaintAndEvaluateStatusExtend;
			}
			if ("2".equals(nstDTO.getOrderType()) && nstDTO.getOrderStatus() == 3 && nstOrderBaseinfoDTO
					.getCurrentMemberId().longValue() != nstDTO.getConfirmMemberId().longValue()) {
				return evaluateStatus;
			}
		}
		return "";
	}

	/**
	 * 判断是否可以接单
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("canTakeOrder")
	public void canTakeOrder(HttpServletRequest request, HttpServletResponse response, CanTakeOrderInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if (inputDTO.getMemberAddressId() == null) {
				logger.warn("memberAddressId参数不能为空");
				setResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			if (inputDTO.getUserId() == null) {
				logger.warn("userId参数不能为空");
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> resultObject = new HashMap<String, Object>();
			/*
			 * 返回是否可以接单:可以接单情况： 1、货源不在订单表中可以接单 2、货源在订单表但状态为未完成并且不是自己的货源可以接单
			 */
			NstOrderBaseinfoDTO nstOrderBaseinfoDTO = nstOrderBaseinfoToolService
					.getByMemberAddressId(inputDTO.getMemberAddressId());
			if (nstOrderBaseinfoDTO == null) {
				resultObject.put("canTakeOrder", "Y");
			} else {
				int orderStatus = nstOrderBaseinfoDTO.getOrderStatus() == null ? 0
						: nstOrderBaseinfoDTO.getOrderStatus();
				long shipperId = nstOrderBaseinfoDTO.getShipperId() == null ? 0 : nstOrderBaseinfoDTO.getShipperId();// 货主id
				long userId = inputDTO.getUserId() == null ? 0 : inputDTO.getUserId();// 登录用户id
				if (orderStatus == NstOrderStatus.NOT_DEAL.getCode() && userId != shipperId) {
					resultObject.put("canTakeOrder", "Y");
				} else {
					resultObject.put("canTakeOrder", "N");
				}
			}
			result.setObject(resultObject);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("[Error]获取是否可以接单状态异常：" + e.getMessage());
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

	/**
	 * 获取用户订单号
	 *
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("getOrderNoByCondition")
	public void getOrderNoByCondition(HttpServletRequest request, HttpServletResponse response,
									  NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if (nstOrderBaseinfoDTO.getMemberId() == null) {
				logger.warn("参数memberId不能为空");
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", nstOrderBaseinfoDTO.getMemberId());
			paramMap.put("orderStatus", nstOrderBaseinfoDTO.getOrderStatus());

			List<String> orderNoList = nstOrderBaseinfoToolService.getOrderNoByCondition(paramMap);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("list", orderNoList);

			result.setObject(resultMap);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("[Error]获取订单号异常：" + e.getMessage());
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

	/**
	 * 根据订单号查入车主实际发车时间
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveStartTimeByOrderNo")
	public void saveStartTimeByOrderNo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String orderNo = GSONUtils.getJsonValueStr(jsonStr, "orderNo");
			if (StringUtils.isEmpty(orderNo)) {
				logger.warn("请传orderNo");
				setEncodeResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			Integer status = nstOrderBaseinfoToolService.saveStartTimeByOrderNo(orderNo);
			if (status <= 0) {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("插入车主开始时间失败");
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}
}
