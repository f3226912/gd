package com.gudeng.commerce.gd.api.controller.v2;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.OrderOutmarketBean;
import com.gudeng.commerce.gd.api.service.OrderToolService;
import com.gudeng.commerce.gd.api.service.OrderoutmarketinfoToolService;
import com.gudeng.commerce.gd.api.service.WeighCarToolService;
import com.gudeng.commerce.gd.api.service.impl.FileUploadToolServiceImpl;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

@Controller
@RequestMapping("/v2/orderOutmarket")
public class OrderOutmarketController extends GDAPIBaseController {

	private Logger logger = LoggerFactory.getLogger(OrderOutmarketController.class);
	
	@Autowired
	private OrderoutmarketinfoToolService orderOutmarketToolService;
	
	@Autowired
	private OrderToolService orderToolService;
	
	@Autowired
	public FileUploadToolServiceImpl fileUploadService; 
	
	@Autowired
	private WeighCarToolService weighCarToolService;
	
	/**
	 * 出场记录列表
	 * @param loginUserId
	 * @param request
	 * @param response
	 */
	@RequestMapping("getPage")
	public void getWeighCarOrderPage(String loginUserId, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createUserId", loginUserId);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			
			Long total = orderOutmarketToolService.getTotalCountByCreateUserId(map);
			List<OrderOutmarketInfoDTO> list = orderOutmarketToolService.getPageByCreateUserId(map);

			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total == null ? 0 : total.intValue());
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.warn("[ERROR]获取出场记录异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	@RequestMapping("totalCount")
	public void getTotalCount(String loginUserId, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createUserId", loginUserId);
			
			Long total = orderOutmarketToolService.getTotalCountByCreateUserId(map);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("count", total);
			result.setObject(resultMap);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch (Exception e) {
			logger.warn("[ERROR]获取出场数异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 出场车辆图片上传（多文件上传）
	 * @param files
	 */
	@RequestMapping("uploadCarImage")
	public void uploadCarImage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="file",required=false) MultipartFile[] files){
		ObjectResult result = new ObjectResult();
		if(files == null){
			setResult(result, ErrorCodeEnum.UPLOAD_IMAGE_IS_NULL, request, response);
			return;
		}
		
		try{
			StringBuilder sb = new StringBuilder();
			for(MultipartFile file : files){
				String masterPicPath = fileUploadService.dataToPic(file);
				sb.append(masterPicPath).append("|");
			}
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("carNumberImage", sb.toString());
			
			result.setObject(resultMap);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch(Exception e){
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			logger.warn("[ERROR]订单出场失败：", e);
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 确认出场（出场提交）
	 * @param request
	 * @param response
	 * @param orderOutmarketBean
	 */
	@RequestMapping("/outMarket")
	public void outMarket(HttpServletRequest request, HttpServletResponse response, OrderOutmarketBean orderOutmarketBean){
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
				if(orderDTO != null && "1".equals(orderDTO.getOutmarkStatus())){
					setResult(result, ErrorCodeEnum.ORDER_STATUS_ERROR, request, response);
					return;
				}
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
			orderOutmarketEntity.setCarNumberImage(orderOutmarketBean.getCarNumberImage());
			orderOutmarketEntity.setWeighCarId(weighCarId);
			orderOutmarketEntity.setOrderWeigh(orderOutmarketBean.getOrderWeigh());
			orderOutmarketEntity.setCreateTime(new Date());
			orderOutmarketEntity.setCreateUserId(orderOutmarketBean.getLoginUserId());
			orderOutmarketEntity.setUpdateTime(new Date());
			Long outMarketId = orderOutmarketToolService.purchaserOutmarketV2(orderOutmarketEntity, orderList);
			
			//返回出场id
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("outMarketId", outMarketId);

			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			result.setObject(resultMap);
		}catch(Exception e){
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			logger.warn("[ERROR]订单出场失败：", e);
		}
		renderJson(result, request, response);
	}
	
}
