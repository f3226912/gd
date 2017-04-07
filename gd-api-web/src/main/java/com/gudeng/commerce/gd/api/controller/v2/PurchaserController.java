package com.gudeng.commerce.gd.api.controller.v2;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.GateOrderDTO;
import com.gudeng.commerce.gd.api.dto.GateOrderDetailDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.OrderOutmarketBean;
import com.gudeng.commerce.gd.api.params.WeighCarParamsBean;
import com.gudeng.commerce.gd.api.service.CarBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.OrderToolService;
import com.gudeng.commerce.gd.api.service.OrderoutmarketinfoToolService;
import com.gudeng.commerce.gd.api.service.ReCarMemberToolService;
import com.gudeng.commerce.gd.api.service.TaskToolService;
import com.gudeng.commerce.gd.api.service.WeighCarToolService;
import com.gudeng.commerce.gd.api.service.impl.FileUploadToolServiceImpl;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.JavaMd5;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.exception.OrderOutmarketException;
import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;
import com.gudeng.commerce.gd.order.entity.ReCarMemberEntity;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 门岗采购商
 * @author wind
 *
 */
@Controller
@RequestMapping("/v2/purchaser")
public class PurchaserController extends GDAPIBaseController{

	private Logger logger = LoggerFactory.getLogger(WeighCarController.class);
	
	@Autowired
	private WeighCarToolService weighCarToolService;
	
	@Autowired
	private CarBaseinfoToolService carBaseinfoToolService;
	
	@Autowired
	private ReCarMemberToolService reCarMemberToolService;
	
	@Autowired
	private MemberToolService memberToolService;
	
	@Autowired
	private OrderoutmarketinfoToolService orderoutmarketToolService;
	
	@Autowired
	private OrderToolService orderToolService;

	@Autowired
	private TaskToolService taskToolService;
	
	@Autowired
	public FileUploadToolServiceImpl fileUploadService; 
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	/**
	 * 采购商空车入场过磅
	 * @param request
	 * @param response
	 */
	@RequestMapping("enterMarketWeigh")
	public void enterMarket(WeighCarParamsBean paramsBean, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			if(paramsBean.getMobile() == null){
				setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			Pattern pattern = Pattern.compile("1[0-9]{10}");
			Matcher matcher = pattern.matcher(paramsBean.getMobile());
			if(!matcher.matches()){
				setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
				return;
			}
			if(StringUtils.isBlank(paramsBean.getCarNumber())){
				setResult(result, ErrorCodeEnum.CAR_NO_IS_NULL, request, response);
				return;
			}
			if(paramsBean.getTareMemberId() == null){
				setResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
	
			//会员（采购商）不存在， 则创建新会员
			MemberBaseinfoDTO memberDTO = new MemberBaseinfoDTO();
			memberDTO.setMobile(paramsBean.getMobile());
			memberDTO.setRealName(paramsBean.getRealName());
			boolean isNewMember = saveOrUpdateMember(memberDTO);
			Long memberId = memberDTO.getMemberId();
			if(isNewMember){
				result.setMsg("成功注册新用户");
			}
			
			//车牌号码不存在，则创建新的车辆记录
			Long carId = saveOrUpdateCar(paramsBean.getCarNumber(), null, paramsBean.getTareMemberId());
			
			//保存会员与车辆关联关系
			saveReCarMember(carId, memberId);
	
			//保存过磅信息
			WeighCarEntity weighCarEntity = new WeighCarEntity();
			weighCarEntity.setMemberId(memberId);
			weighCarEntity.setCarId(carId);
			weighCarEntity.setType("2");//采购商
			weighCarEntity.setStatus("1");//已进场
			weighCarEntity.setTare(paramsBean.getTareWeight());
			weighCarEntity.setTapWeight("2");//总重为空
			weighCarEntity.setTareMemberId(paramsBean.getTareMemberId());
			weighCarEntity.setTareCreateTime(new Date());
			weighCarEntity.setCreateUserId(String.valueOf(paramsBean.getTareMemberId()));
			weighCarEntity.setCreateTime(new Date());
			weighCarEntity.setUpdateTime(new Date());
			weighCarEntity.setMarketId(paramsBean.getMarketId() == null ? 1 : paramsBean.getMarketId());
			weighCarToolService.insertWeighCar(weighCarEntity);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.info("采购商入场过磅异常", e);
			result.setMsg("采购商入场过磅异常");
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 采购商出场过磅
	 * @param request
	 * @param response
	 */
	@RequestMapping("outMarketWeigh")
	public void outMarket(WeighCarParamsBean paramsBean, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		if(paramsBean.getTotalMemberId() == null){
			setResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
			return;
		}
		String mobile = paramsBean.getMobile();
		if(StringUtils.isBlank(mobile)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(mobile);
		if(!matcher.matches()){
			setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}

		try {
			MemberBaseinfoDTO memberDTO = new MemberBaseinfoDTO();
			memberDTO.setMobile(paramsBean.getMobile());
			memberDTO.setRealName(paramsBean.getRealName());
			
			//会员（采购商）不存在， 则创建新会员
			boolean isNewMember = saveOrUpdateMember(memberDTO);
			Long memberId = memberDTO.getMemberId();
			if(isNewMember){
				result.setMsg("成功注册新用户");
			}
			
			Long weighCarId = paramsBean.getWeighCarId();
			WeighCarDTO weighCarDTO = null;
			if(weighCarId != null){
				weighCarDTO = weighCarToolService.getById(weighCarId);
				
				//判断当前手机号码所属用户跟过磅记录的所属用户是否一样
				if(weighCarDTO != null){
					Long weighCarMemberId = weighCarDTO.getMemberId();
					if(memberId != null && weighCarMemberId != null && !memberId.equals(weighCarMemberId)){
						setResult(result, ErrorCodeEnum.CAR_NO_NOT_MATCH_MOBILE, request, response);
						return;
					}
				}
			}
			
			//新增或修改过磅记录：如果过磅记录status=1（状态为已进场）的，则修改存在的过磅记录，否则增加一条新的过磅记录
			if(weighCarId != null && weighCarDTO != null && "1".equals(weighCarDTO.getStatus())){
				WeighCarEntity entity = new WeighCarEntity();
				entity.setWeighCarId(weighCarDTO.getWeighCarId());
				entity.setTotalWeight(paramsBean.getTotalWeight());
				entity.setTapWeight("4");//皮重总重全过磅
				if(weighCarDTO.getTare() != null && paramsBean.getTotalWeight() != null){
					entity.setNetWeight(MathUtil.sub(paramsBean.getTotalWeight(), weighCarDTO.getTare()));
				}
				entity.setTotalMemberId(paramsBean.getTotalMemberId());
				entity.setUpdateUserId(String.valueOf(paramsBean.getTotalMemberId()));
				weighCarToolService.updateTotalWeight(entity);
				
			}else{
				
				if(StringUtils.isBlank(paramsBean.getCarNumber())){
					setResult(result, ErrorCodeEnum.CAR_NO_IS_NULL, request, response);
					return;
				}
				//车牌号码不存在，则创建新的车辆记录
				Long carId = saveOrUpdateCar(paramsBean.getCarNumber(), paramsBean.getCwpId(), paramsBean.getTotalMemberId());
				
				//保存会员与车辆关联关系
				saveReCarMember(carId, memberId);
				
				//保存过磅信息
				WeighCarEntity weighCarEntity = new WeighCarEntity();
				weighCarEntity.setMemberId(memberId);
				weighCarEntity.setCarId(carId);
				weighCarEntity.setType("2");//采购商
//				weighCarEntity.setStatus("1");//已进场
				weighCarEntity.setTare(paramsBean.getTareWeight());//皮重
				weighCarEntity.setTotalWeight(paramsBean.getTotalWeight());//总重
				if(paramsBean.getTotalWeight() != null && paramsBean.getTareWeight() != null){
					weighCarEntity.setNetWeight(MathUtil.sub(paramsBean.getTotalWeight(), paramsBean.getTareWeight()));
				}
				weighCarEntity.setTapWeight("4");//皮重总重全过磅
				weighCarEntity.setTareMemberId(paramsBean.getTotalMemberId());
				weighCarEntity.setTareCreateTime(new Date());
				weighCarEntity.setTotalMemberId(paramsBean.getTotalMemberId());
				weighCarEntity.setTotalCreateTime(new Date());
				weighCarEntity.setCreateTime(new Date());
				weighCarEntity.setCreateUserId(String.valueOf(paramsBean.getTotalMemberId()));
				weighCarEntity.setUpdateTime(new Date());
				weighCarEntity.setMarketId(paramsBean.getMarketId() == null ? 1 : paramsBean.getMarketId());
				weighCarEntity.setWeighType(paramsBean.getWeighType());
				weighCarId = weighCarToolService.insertWeighCar(weighCarEntity);
			}
			
			//如果采购商没有订单，则直接出场
			OrderBaseinfoDTO orderBaseinfoDTO = new OrderBaseinfoDTO();
			orderBaseinfoDTO.setMobile(mobile);
			List<OrderBaseinfoDTO> orderBaseinfolist=orderoutmarketToolService.getOderInfoList(orderBaseinfoDTO);
			if(orderBaseinfolist == null || orderBaseinfolist.isEmpty()){
				weighCarToolService.updateStatusByWeiCarId("2", weighCarId);
			}
			
			//返回过磅记录id
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("weighCarId", weighCarId);
			result.setObject(resultMap);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.info("采购商出场过磅异常", e);
			result.setMsg("采购商出场过磅异常");
		}
		renderJson(result, request, response);
	}
	
	/***
	 * 修改或更新会员信息
	 * @param mobile
	 * @param realName
	 * @return
	 * @throws Exception 
	 */
	public boolean saveOrUpdateMember(MemberBaseinfoDTO memberDTO) throws Exception{
		boolean isNewMember = false; //是否是新增会员
		String mobile = memberDTO.getMobile();
		String realName = memberDTO.getRealName();
		
		MemberBaseinfoDTO member = memberToolService.getByMobile(mobile);
		if(member != null){
			memberDTO.setMemberId(member.getMemberId());
			//修改会员姓名
			if(StringUtils.isNotBlank(realName) && !realName.equals(member.getRealName())){
				memberToolService.updateMember(memberDTO);
			}
		}else{
			MemberBaseinfoEntity memberEntity = new MemberBaseinfoEntity();
			memberEntity.setAccount(mobile);
			memberEntity.setMobile(mobile);
			memberEntity.setRealName(realName);
			memberEntity.setLevel(3);//农批商
			memberEntity.setStatus("1");
			memberEntity.setCreateTime(new Date());
			memberEntity.setRegetype("5");//注册来源门岗
			//随机生成8位默认密码
			String password = CommonUtils.getEightCode();
			memberEntity.setPassword(JavaMd5.getMD5(password+UserSettingPropertyUtils.getEncrytphrase("gd.encrypt.key")).toUpperCase());
			Long memberId = memberToolService.addMemberBaseinfoEnt(memberEntity);
			
			memberDTO.setMemberId(memberId);
			isNewMember = true;
			
			/*
			 * 发送短信:已经帮您注册成功农批商app,默认密码为XXXXXX您可以登录后自行修改，下载地址：XXXXXXX【谷登科技】
			 */
			//取redis缓存,获取通道号
			String channel = "";
			try{
				Object obj = redisClient.get("GDSMS_CHANNEL");
				System.out.println("redis channel:###############"+ obj);
				channel = obj==null? Constant.Alidayu.DEFAULTNO:obj.toString();
				System.out.println("redis channel:###############"+ channel);
			}catch(Exception e){
				//处理redis服务器异常
				e.printStackTrace();
				logger.info("获取redis 消息通道出错!");
			}
			String content=null;
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE4,password);
				
			}else{
				content=password;
			}
			CommonUtils.autoRegistSendMsg(channel,content, mobile);
		}
		return isNewMember;
	}
	
	/***
	 * 修改或保存车辆信息
	 * @param carNumber
	 * @param cwpId 车型id
	 * @param operatorId 操作用户id
	 * @return
	 * @throws Exception 
	 */
	public Long saveOrUpdateCar(String carNumber, Long cwpId, Long operatorId) throws Exception{
		CarBaseinfoDTO carBaseinfoDTO = carBaseinfoToolService.getByCarNumber(carNumber);
		Long carId = null;
		if(carBaseinfoDTO != null){
			carId = carBaseinfoDTO.getCarId();
			//修改车型
			if(cwpId != null){
				CarBaseinfoEntity carEntity = new CarBaseinfoEntity();
				carEntity.setCarId(carId);
				if(operatorId != null){
					carEntity.setUpdateUserId(String.valueOf(operatorId));
				}
				carEntity.setCwpId(cwpId);
				carBaseinfoToolService.update(carEntity);
			}
		}else{
			CarBaseinfoEntity carBaseinfoEntity = new CarBaseinfoEntity();
			carBaseinfoEntity.setCarNumber(carNumber);
			carBaseinfoEntity.setCreateTime(new Date());
			if(operatorId != null){
				carBaseinfoEntity.setCreateUserId(String.valueOf(operatorId));
			}
			carBaseinfoEntity.setCwpId(cwpId);
			carId = carBaseinfoToolService.insertEntity(carBaseinfoEntity);
		}
		return carId;
	}
	
	/**
	 * 保存car_baseinfo与member_baseinfo关联关系
	 * @param carId
	 * @param memberId
	 * @throws Exception 
	 */
	public void saveReCarMember(Long carId, Long memberId) throws Exception{
		boolean exist = reCarMemberToolService.isExist(carId, memberId);
		if(!exist){
			ReCarMemberEntity reCarMemberEntity = new ReCarMemberEntity();
			reCarMemberEntity.setCarID(carId);
			reCarMemberEntity.setMemberId(memberId);
			reCarMemberEntity.setCreateUserId(String.valueOf(memberId));
			reCarMemberToolService.addEntity(reCarMemberEntity);
		}
	}
	
	/**
	 * 获取未出场订单
	 * @param request
	 * @param response
	 * @param orderBaseinfoDTO
	 */
	@RequestMapping("/getOutMarketOrder")
	public void getOutMarketOrder(HttpServletRequest request, HttpServletResponse response, OrderBaseinfoDTO orderBaseinfoDTO){
		ObjectResult result = new ObjectResult();
		
		Integer memberId = orderBaseinfoDTO.getMemberId();
		String memberIdStr = orderBaseinfoDTO.getMemberIdStr();
		String mobile = orderBaseinfoDTO.getMobile();
		if(memberId == null && StringUtils.isBlank(memberIdStr) && StringUtils.isBlank(mobile)){
			setResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
			return;
		}
		try{
			List<OrderBaseinfoDTO> orderList=orderoutmarketToolService.getOderInfoList(orderBaseinfoDTO);
			if(orderList == null){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			//格式化返回结果
			List<GateOrderDTO> gateOrderList = new ArrayList<GateOrderDTO>();
			for(OrderBaseinfoDTO order : orderList){
				GateOrderDTO gateOrder = new GateOrderDTO();
				gateOrder.setMemberId(order.getMemberId());
				gateOrder.setMobile(order.getMobile());
				gateOrder.setShopName(order.getShopName());
				gateOrder.setRealName(order.getRealName());
				gateOrder.setOrderNo(order.getOrderNo()+"");
				gateOrder.setOrderTime(order.getOrderTime());
				
				List<OrderProductDetailDTO> orderDetailList = order.getDetailList();
				if(orderDetailList != null){
					List<GateOrderDetailDTO> detailList = new ArrayList<GateOrderDetailDTO>();
					for(OrderProductDetailDTO orderDetail : orderDetailList){
						GateOrderDetailDTO detail = new GateOrderDetailDTO();
						detail.setProductName(orderDetail.getProductName());
						
						Double purQuantity = orderDetail.getPurQuantity() == null ? 0 : orderDetail.getPurQuantity();
						String unitName = orderDetail.getUnitName() == null ? "" : orderDetail.getUnitName();
						
						BigDecimal v1 = new BigDecimal(purQuantity.toString());
						BigDecimal v2 = new BigDecimal("1");
						switch (unitName) {
						case "克":
							v2 = new BigDecimal("1000000");
							detail.setPurQuantity(v1.divide(v2).toPlainString());
							detail.setUnitName("吨");
							break;

						case "千克":
							v2 = new BigDecimal("1000");
							detail.setPurQuantity(v1.divide(v2).toPlainString());
							detail.setUnitName("吨");
							break;
							
						case "公斤":
							v2 = new BigDecimal("1000");
							detail.setPurQuantity(v1.divide(v2).toPlainString());
							detail.setUnitName("吨");
							break;
							
						default:
							detail.setPurQuantity(String.valueOf(purQuantity));
							detail.setUnitName(unitName);
							break;
						}
						detailList.add(detail);
					}
					gateOrder.setDetailList(detailList);
				}
				gateOrderList.add(gateOrder);
			}
			result.setObject(gateOrderList);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			logger.warn("[ERROR]查找订单商品信息失败：", e);
			result.setMsg("查找订单商品信息失败");
		}
		renderJson(result, request, response, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 版本V2：获取出场订单
	 * @param request
	 * @param response
	 * @param orderBaseinfoDTO
	 */
	@RequestMapping("/getOutMarketOrderV2")
	public void getOutMarketOrderV2(HttpServletRequest request, HttpServletResponse response, OrderBaseinfoDTO orderBaseinfoDTO){
		ObjectResult result = new ObjectResult();
		
		Integer memberId = orderBaseinfoDTO.getMemberId();
		String memberIdStr = orderBaseinfoDTO.getMemberIdStr();
		String mobile = orderBaseinfoDTO.getMobile();
		if(memberId == null && StringUtils.isBlank(memberIdStr) && StringUtils.isBlank(mobile)){
			setResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
			return;
		}
		try{
			List<OrderBaseinfoDTO> orderList=orderoutmarketToolService.getOutMarketOrderList(orderBaseinfoDTO);
			if(orderList == null){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			//格式化返回结果
			List<GateOrderDTO> gateOrderList = new ArrayList<GateOrderDTO>();
			for(OrderBaseinfoDTO order : orderList){
				GateOrderDTO gateOrder = new GateOrderDTO();
				gateOrder.setMemberId(order.getMemberId());
				gateOrder.setMobile(order.getMobile());
				gateOrder.setShopName(order.getShopName());
				gateOrder.setRealName(order.getRealName());
				gateOrder.setOrderNo(order.getOrderNo()+"");
				gateOrder.setOrderTime(order.getOrderTime());
				
				List<OrderProductDetailDTO> orderDetailList = order.getDetailList();
				
				if(CollectionUtils.isEmpty(orderDetailList)){
					continue;
				}
			
				List<GateOrderDetailDTO> detailList = new ArrayList<GateOrderDetailDTO>();
				Iterator<OrderProductDetailDTO> it = orderDetailList.iterator();
				while(it.hasNext()){
					OrderProductDetailDTO orderDetail = it.next();
				
					GateOrderDetailDTO detail = new GateOrderDetailDTO();
					detail.setProductName(orderDetail.getProductName());
					Double purQuantity = orderDetail.getPurQuantity() == null ? 0 : orderDetail.getPurQuantity();
					String unitName = orderDetail.getUnitName() == null ? "" : orderDetail.getUnitName();
					
					BigDecimal v1 = new BigDecimal(purQuantity.toString());
					BigDecimal v2 = new BigDecimal("1");
					switch (unitName) {
					case "克":
						v2 = new BigDecimal("1000000");
						detail.setPurQuantity(v1.divide(v2).toPlainString());
						detail.setUnitName("吨");
						break;

					case "千克":
						v2 = new BigDecimal("1000");
						detail.setPurQuantity(v1.divide(v2).toPlainString());
						detail.setUnitName("吨");
						break;
						
					case "公斤":
						v2 = new BigDecimal("1000");
						detail.setPurQuantity(v1.divide(v2).toPlainString());
						detail.setUnitName("吨");
						break;
						
					case "吨":
						detail.setPurQuantity(String.valueOf(purQuantity));
						detail.setUnitName("吨");
						break;
						
					default:
						detail.setPurQuantity(String.valueOf(purQuantity));
						detail.setUnitName("");
						break;
					}
					detailList.add(detail);
				}
				gateOrder.setDetailList(detailList);
				gateOrderList.add(gateOrder);
			}
			
			MemberBaseinfoDTO member = null;
			if(StringUtils.isNotBlank(mobile)){
				 member = memberToolService.getByMobile(mobile);
			}
			if(member == null && StringUtils.isNotBlank(memberIdStr)){
				member = memberToolService.getById(memberIdStr);
			}
			if(member == null && memberId != null){
				member = memberToolService.getById(String.valueOf(memberId));
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("orderList", gateOrderList);
			
			if(member != null){
				resultMap.put("realName", member.getRealName());
				resultMap.put("mobile", member.getMobile());
				resultMap.put("account", member.getAccount());
			}else{
				resultMap.put("realName", "");
				resultMap.put("mobile", "");
				resultMap.put("account", "");
			}
	
			result.setObject(resultMap);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			logger.warn("[ERROR]查找订单商品信息失败：", e);
			result.setMsg("查找订单商品信息失败");
		}
		renderJson(result, request, response, "yyyy-MM-dd HH:mm:ss");
	}
	
	
	/**
	 * 确认出场（出场提交）
	 * @param request
	 * @param response
	 * @param orderOutmarketBean
	 */
	@RequestMapping("/outMarket")
	public void outMarket(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="file",required=false) MultipartFile[] files, OrderOutmarketBean orderOutmarketBean){
		ObjectResult result = new ObjectResult();
		if(StringUtils.isBlank(orderOutmarketBean.getOrderNoList())){
			setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
			return;
		}
		
		OrderOutmarketinfoEntity orderOutmarketEntity = new OrderOutmarketinfoEntity();
		//车牌图片上传
		if(files != null){
			StringBuilder sb = new StringBuilder();
			for(MultipartFile file : files){
				String masterPicPath;
				try {
					masterPicPath = fileUploadService.dataToPic(file);
					sb.append(masterPicPath).append("|");
				} catch (MalformedURLException e) {
					logger.error("上传图片失败",e);
				} catch (Exception e) {
					logger.error("上传图片失败",e);
				
				}
				
			}
			orderOutmarketEntity.setCarNumberImage(sb.toString());
		}
		if(StringUtils.isNotBlank(orderOutmarketBean.getWeighCarId())){
			orderOutmarketEntity.setWeighCarId(Long.parseLong(orderOutmarketBean.getWeighCarId()));
		}
		orderOutmarketEntity.setOrderWeigh(orderOutmarketBean.getOrderWeigh());
		orderOutmarketEntity.setCreateTime(new Date());
		orderOutmarketEntity.setUpdateTime(new Date());
		
		//订单号
		String orderNoStr = orderOutmarketBean.getOrderNoList();
		String[] orderNoStrArr = orderNoStr.split(",");
		List<String> orderNoList = null;
		if(orderNoStrArr != null){
			orderNoList = Arrays.asList(orderNoStrArr);
		}
		try{
			Long ourMarketId = orderoutmarketToolService.purchaserOutmarket(orderOutmarketEntity, orderNoList);

			//返回出场id
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("outMarketId", ourMarketId);
			result.setObject(resultMap);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			Throwable cause = getDeepestCause(e);
			if(cause instanceof OrderOutmarketException){
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}else{
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
			logger.warn("[ERROR]插入订单信息出场失败：", e);
		}
	}
	
	/**
	 * V2版本：确认出场（出场提交）
	 * @param request
	 * @param response
	 * @param orderOutmarketBean
	 */
	@RequestMapping("/outMarketV2")
	public void outMarketV2(HttpServletRequest request, HttpServletResponse response,  @RequestParam(value="file",required=false) MultipartFile[] files, OrderOutmarketBean orderOutmarketBean){
		ObjectResult result = new ObjectResult();
		
		String orderNoStr = orderOutmarketBean.getOrderNoList();
		if(StringUtils.isBlank(orderNoStr)){
			setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
			return;
		}

		try{
			//订单
			List<OrderBaseinfoDTO> orderList = new ArrayList<OrderBaseinfoDTO>();
			String[] orderNoStrArr = orderNoStr.split(",");
			for(String orderNo : orderNoStrArr){
				OrderBaseinfoDTO orderDTO = orderToolService.getOrderByOrderNo(Long.parseLong(orderNo));
				orderList.add(orderDTO);
			}
			
			for(OrderBaseinfoDTO orderDTO : orderList){
				if("1".equals(orderDTO.getOutmarkStatus())){
					setResult(result, ErrorCodeEnum.ORDER_STATUS_ERROR, request, response);
					return;
				}
			}	
			
			//图片上传
			StringBuilder sb = new StringBuilder();
			for(MultipartFile file : files){
				String masterPicPath = fileUploadService.dataToPic(file);
				sb.append(masterPicPath).append("|");
			}
			
			//新增默认的出场过磅记录
			WeighCarEntity weighCarEntity = new WeighCarEntity();
			weighCarEntity.setType("2");//采购商
			weighCarEntity.setMemberId(orderOutmarketBean.getMemberId());
			String loginUserId = orderOutmarketBean.getLoginUserId();
			if(StringUtils.isNotBlank(loginUserId)){
				weighCarEntity.setTotalMemberId(Long.parseLong(loginUserId));
			}
			weighCarEntity.setTareCreateTime(new Date());
			weighCarEntity.setTotalCreateTime(new Date());
			weighCarEntity.setCreateTime(new Date());
			weighCarEntity.setUpdateTime(new Date());
			weighCarEntity.setCreateUserId(loginUserId);
			Long weighCarId = weighCarToolService.insertWeighCar(weighCarEntity);
			
			//新增出场记录
			OrderOutmarketinfoEntity orderOutmarketEntity = new OrderOutmarketinfoEntity();
			orderOutmarketEntity.setCarNumberImage(sb.toString());
			orderOutmarketEntity.setWeighCarId(weighCarId);
			orderOutmarketEntity.setOrderWeigh(orderOutmarketBean.getOrderWeigh());
			orderOutmarketEntity.setCreateTime(new Date());
			orderOutmarketEntity.setCreateUserId(orderOutmarketBean.getLoginUserId());
			orderOutmarketEntity.setUpdateTime(new Date());
			Long outMarketId = orderoutmarketToolService.purchaserOutmarketV2(orderOutmarketEntity, orderList);
			
			//返回出场id
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("outMarketId", outMarketId);

			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			result.setObject(resultMap);
		}catch(Exception e){
			result.setMsg("订单出场失败");
			logger.warn("[ERROR]订单出场失败：", e);
		}
		renderJson(result, request, response);
	}
	
	/** 
	 * 返回此异常的根本原因
	 */
	private Throwable getDeepestCause(Throwable throwable) {
		if (throwable == null)
			return null;
		Throwable cause = throwable.getCause();
		if (cause == null)
			return throwable;
		return getDeepestCause(cause);
	}
}
