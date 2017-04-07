package com.gudeng.commerce.gd.api.controller.engj;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.Constant.PAY_SERIALNUMBER;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.input.ENongPayNotifyDTO;
import com.gudeng.commerce.gd.api.dto.input.ENongQueryOrderParamsDTO;
import com.gudeng.commerce.gd.api.dto.input.ENongRequestDTO;
import com.gudeng.commerce.gd.api.dto.output.ENongResponseDTO;
import com.gudeng.commerce.gd.api.enums.ENongResponseCodeEnum;
import com.gudeng.commerce.gd.api.enums.ENongTransTypeEnum;
import com.gudeng.commerce.gd.api.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.api.service.engj.EnPostLogToolService;
import com.gudeng.commerce.gd.api.service.engj.PosBankCardToolService;
import com.gudeng.commerce.gd.api.service.engj.ReBusinessPosToolService;
import com.gudeng.commerce.gd.api.service.order.OrderActivityToolService;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.service.pos.PosOrderToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.engj.SignUtil;
import com.gudeng.commerce.gd.api.util.engj.UtilDES;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.order.enm.EPaySubType;
@Controller
@RequestMapping("/enong")
public class ENongController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ENongController.class);
	
	@Autowired
	private OrderTool2Service orderTool2Service;
	@Autowired
	private PosBankCardToolService posBankCardToolService;
	@Autowired
	private ReBusinessPosToolService reBusinessPosToolService;
	
	@Autowired
	private PosOrderToolService posOrderToolService;
	
	@Autowired
	private PaySerialnumberToolService paySerialnumberToolService;
	
	@Autowired
	private EnPostLogToolService enPostLogToolService;
	
	@Autowired
    private OrderActivityToolService orderActivityToolService;
	
	@Resource
	private GdProperties gdProperties;
	
	private boolean isSign;
	
	@PostConstruct
	private void init(){
		String sign = gdProperties.getProperties().getProperty("gateway.sign");
		if(StringUtils.equals(sign, "false")){
			isSign = false;
		} else {
			isSign = true;
		}
		
	}
	
	@RequestMapping("/orderList")
	public void orderList(HttpServletRequest request, HttpServletResponse response, ENongRequestDTO inputDTO) {
		try {
			if(StringUtils.isBlank(inputDTO.getReqdata())){
				setErrorResponse(response, "请求数据不能为空");
				return;
			}
			if(isSign){
				if(StringUtils.isBlank(inputDTO.getSignmsg())){
					setErrorResponse(response, "数据签名不能为空");
					return;
				}
				//先验证签名
				if(!SignUtil.verify(inputDTO.getReqdata(), inputDTO.getSignmsg())){
					logger.error("[ERROR]请求数据签名不通过: " + inputDTO.getReqdata());
					setErrorResponse(response, "验证签名失败");
					return;
				}
			}
			
			
			ENongQueryOrderParamsDTO paramsDTO = JSONUtils.parseObject(inputDTO.getReqdata(), ENongQueryOrderParamsDTO.class);
			String machinenum = paramsDTO.getMachinenum();    //POS终端号，不为空
			String merchantnum = paramsDTO.getMerchantnum();  //POS商户号，不为空
			
			BusinessBaseinfoDTO businessDTO = reBusinessPosToolService.getByPosDevNo(machinenum, merchantnum);
			if(businessDTO == null){
				logger.error("=================== [ERROR] =====================");
				logger.error("[ERROR]POS终端号找不到对应商铺信息: " + machinenum);
				logger.error("=================== [ERROR] =====================");
				setErrorResponse(response, "POS终端号错误");
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", businessDTO.getBusinessId());
			List<Map<String, String>> eNongOrderList = orderTool2Service.getENongOrderList(map );
			ENongResponseDTO eNongRespons = new ENongResponseDTO();
			eNongRespons.setResultcode(ENongResponseCodeEnum.OPERATION_SUCCESS.getResultCode());
			eNongRespons.setResultmsg(ENongResponseCodeEnum.OPERATION_SUCCESS.getResultMsg());
			eNongRespons.setDatajson(eNongOrderList);
			//签名
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(SignUtil.signReJson(eNongRespons));
		} catch (Exception e) {
			logger.warn("[ERROR]查询E农订单列表异常", e);
			try {
				setErrorResponse(response, "查询E农订单列表异常");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 订单支付结果通知
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/payNotify")
	public void payNotify(HttpServletRequest request, HttpServletResponse response, ENongRequestDTO inputDTO) {
		String message = "";
		ENongPayNotifyDTO payNotifyDTO = null;
		try{
			logger.info("请求参数：reqdata=" + inputDTO.getReqdata() + "&signmsg=" + inputDTO.getSignmsg());
			if(StringUtils.isBlank(inputDTO.getReqdata())){
				setErrorResponse(response, "请求数据不能为空");
				return;
			}
			if(isSign){
				if(StringUtils.isBlank(inputDTO.getSignmsg())){
					setErrorResponse(response, "数据签名不能为空");
					return;
				}
				//先验证签名
				if(!SignUtil.verify(inputDTO.getReqdata(), inputDTO.getSignmsg())){
					setErrorResponse(response, "验证签名失败");
					return;
				}
			}
			
			Long logId = enPostLogToolService.writeRequestInfo(inputDTO, null);
			payNotifyDTO = JSONUtils.parseObject(inputDTO.getReqdata(), ENongPayNotifyDTO.class);
			enPostLogToolService.writeRequestInfo(payNotifyDTO, logId);
			if(StringUtils.isBlank(payNotifyDTO.getTransype())){
				message = "交易类型不能为空";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			if(StringUtils.isBlank(payNotifyDTO.getMachinenum())){
				message = "pos终端号不能为空";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			if(ENongTransTypeEnum.TRANSTYPE_ORDER.getKey().equals(payNotifyDTO.getTransype()) && 
					StringUtils.isBlank(payNotifyDTO.getOrderno())){
				message = "订单支付，订单编号不能为空";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			if(StringUtils.isBlank(payNotifyDTO.getPaycardno())){
				message = "付款卡号不能为空";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			if(StringUtils.isBlank(payNotifyDTO.getTransseqno())){
				message = "支付流水不能为空";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			int total = paySerialnumberToolService.getTotalByStatementId(payNotifyDTO.getTransseqno());
			if(total > 0){
				message = "支付流水已存在";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			//防止支付流水重复
			try{
				paySerialnumberToolService.insertPayStatementId(payNotifyDTO.getTransseqno());
			} catch(SQLIntegrityConstraintViolationException e){
				message = "支付流水已存在";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			BusinessBaseinfoDTO businessDTO = reBusinessPosToolService.getByPosDevNo(payNotifyDTO.getMachinenum(), payNotifyDTO.getMerchantnum());
			if(businessDTO == null){
				message = "POS终端号错误";
				enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, message);
				setErrorResponse(response, message);
				return;
			}
			OrderAppInputDTO orderAppInputDTO = new OrderAppInputDTO();
			orderAppInputDTO.setStatementId(payNotifyDTO.getTransseqno());
			orderAppInputDTO.setOrderAmount(MathUtil.div(Double.valueOf(payNotifyDTO.getOrderfee()), 100.0));
			orderAppInputDTO.setPayAmount(MathUtil.div(Double.valueOf(payNotifyDTO.getPayfee()), 100.0));
			orderAppInputDTO.setPayType(PAY_SERIALNUMBER.PAYTYPE_BANKCARD);
			orderAppInputDTO.setPaySubType(EPaySubType.ENGJ.getCode());
			orderAppInputDTO.setPayTime(DateUtils.parseDate(payNotifyDTO.getTransdate() + payNotifyDTO.getTranstime(), 
					"yyyyMMddHHmmss"));
			orderAppInputDTO.setPayInfo(payNotifyDTO.getPayresultcode() + "-" + payNotifyDTO.getPayresultmsg());
			if(isSign){
				payNotifyDTO.setPaycardno(UtilDES.decryptDES(payNotifyDTO.getPaycardno()));//在这里统一改掉
			} 
			
			orderAppInputDTO.setPaymentAccount(payNotifyDTO.getPaycardno());
			orderAppInputDTO.setPosNumber(payNotifyDTO.getMachinenum());
			String result = "OK";
			if(ENongTransTypeEnum.TRANSTYPE_ORDER.getKey().equals(payNotifyDTO.getTransype())){
				//订单支付
				orderAppInputDTO.setOrderNo(Long.valueOf(payNotifyDTO.getOrderno()));
				result = posOrderToolService.confirmEnongReceive(orderAppInputDTO);
			} else if(ENongTransTypeEnum.TRANSTYPE_CARD.getKey().equals(payNotifyDTO.getTransype())){
				//刷卡消费
				orderAppInputDTO.setBusinessId(businessDTO.getBusinessId());
				orderAppInputDTO.setMarketId(Long.valueOf(businessDTO.getMarketId()));
				orderAppInputDTO.setShopName(businessDTO.getShopsName());
				result = posOrderToolService.payByCard(orderAppInputDTO);
				payNotifyDTO.setOrderno(String.valueOf(orderAppInputDTO.getOrderNo()));
			}
			logger.info("订单支付结果通知结果：" + result);
			enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, result);
			if("OK".equals(result)){
				/***********支付完成，同步积分数据到积分表和订单信息表 begin weiwenke******************/
				orderActivityToolService.IntegralRateForPayFinish(orderAppInputDTO.getOrderNo());
				/**************end**************/
				
				
				ENongResponseDTO eNongRespons = new ENongResponseDTO();
				eNongRespons.setResultcode(ENongResponseCodeEnum.OPERATION_SUCCESS.getResultCode());
				eNongRespons.setResultmsg(ENongResponseCodeEnum.OPERATION_SUCCESS.getResultMsg());
				//签名
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().print(SignUtil.signReJson(eNongRespons));
			} else {
				setErrorResponse(response, result);
			}
		} catch (Exception e) {
			logger.error("[ERROR]E农订单支付结果通知异常", e);
			enPostLogToolService.writeResponseInfo(inputDTO, payNotifyDTO, e.getMessage());
			try {
				setErrorResponse(response, "E农订单支付结果通知异常");
			} catch (Exception e1) {
				logger.error("[ERROR]E农订单支付结果通知异常", e1);
			}
		}
	}
	
	/**
	 * 设置错误回复信息
	 * @param response
	 * @param msg
	 * @throws Exception
	 */
	private void setErrorResponse(HttpServletResponse response, String msg) throws Exception{
		logger.info(msg);
		ENongResponseDTO eNongRespons = new ENongResponseDTO();
		eNongRespons.setResultcode(ENongResponseCodeEnum.OPERATION_FAIL.getResultCode());
		eNongRespons.setResultmsg(msg);
		//签名
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print(SignUtil.signReJson(eNongRespons));
	}
}
