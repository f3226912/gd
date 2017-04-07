package com.gudeng.commerce.gd.api.controller.v160913;

import java.util.ArrayList;
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
import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.enums.NstCarTypeEnum;
import com.gudeng.commerce.gd.api.enums.NstGoodTypeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;

@Controller
@RequestMapping("/v30/deliver")
public class ProductDeliveryV30Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ProductDeliveryV30Controller.class);
	
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;
	@Autowired
	private NstApiCommonService nstApiCommonService;
	@Autowired
	private AreaToolService areaToolService;
	/**
	 * 发布货源通用
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Long memberAddressId = null;
		try {
			String reqJsonStr = getParamDecodeStr(request);
			MemberAddressInputDTO inputDTO = (MemberAddressInputDTO) GSONUtils.fromJsonToObject(reqJsonStr, MemberAddressInputDTO.class);
			
			if(inputDTO.getMemberId() == null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			//增加货源请求
			String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.ADD_GOODS_TRANSFER.getUri();
			Map<String, String> requestParamMap = GSONUtils.fromJsonToMapStr(reqJsonStr);
			setCityLngAndLat(requestParamMap);
			requestParamMap.put("carLength", "-2");
			requestParamMap.put("token", nstApiCommonService.getNstToken(inputDTO.getMemberId() +""));
			Long time1 = System.currentTimeMillis();
			NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(requestParamMap, url);
			Long time2 = System.currentTimeMillis();
			System.out.println("[INFO]Add delivery cost time: " + (time2 - time1));
			
			//不成功则返回错误信息
			if(nstResponse.getCode().intValue() != 10000){
				setEncodeResult(result, nstResponse.getCode(), nstResponse.getMsg(), request, response);
				return;
			}
			//获取物流id
			memberAddressId = Long.parseLong(nstResponse.getResult().get("id"));
					
			if(StringUtils.isNotBlank(inputDTO.getSelectedList())){
				//增加出货信息
				ErrorCodeEnum addResult2 = productDeliveryDetailToolService.add(inputDTO, memberAddressId, false);
				if(ErrorCodeEnum.SUCCESS != addResult2){
					setEncodeResult(result, addResult2, request, response);
					delete(memberAddressId);
					return;
				}
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("memberAddressId", memberAddressId);
			result.setObject(map);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]添加货源异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			if(memberAddressId != null){
				try {
					delete(memberAddressId);
				} catch (Exception e1) {
					logger.error("[ERROR]删除增加货源异常", e);
				}
			}
		}
	}
	
	private void setCityLngAndLat(Map<String, String> requestParamMap) throws Exception {
		//发货城市经纬度
		if(StringUtils.isBlank(requestParamMap.get("sLng")) 
				|| StringUtils.isBlank(requestParamMap.get("sLat"))){
			if(StringUtils.isNotBlank(requestParamMap.get("sCityName"))){
				AreaDTO sendCity = areaToolService.getByAreaName(requestParamMap.get("sCityName"));
				logger.info("Send city info: " + sendCity);
				if(sendCity != null){
					requestParamMap.put("sLng", sendCity.getLng().toString());
					requestParamMap.put("sLat", sendCity.getLat().toString());
				}
			}
			
		}
		
		//收货城市经纬度
		if(StringUtils.isBlank(requestParamMap.get("eLng")) 
				|| StringUtils.isBlank(requestParamMap.get("eLat"))){
			if(StringUtils.isNotBlank(requestParamMap.get("eCityName"))){
				AreaDTO receiveCity = areaToolService.getByAreaName(requestParamMap.get("eCityName"));
				logger.info("Receive city info: " + receiveCity);
				if(receiveCity != null){
					requestParamMap.put("eLng", receiveCity.getLng().toString());
					requestParamMap.put("eLat", receiveCity.getLat().toString());
				}
			}
		}
	}

	@RequestMapping("/getType")
	public void getType(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, List<Map<String, String>>> totalMap = new HashMap<>();
		List<Map<String, String>> goodTypeList = new ArrayList<>();
		List<Map<String, String>> carTypeList = new ArrayList<>();
		//货物类型
		for (NstGoodTypeEnum item :NstGoodTypeEnum.values()){
			Map<String, String> map = new HashMap<>();
			map.put("goodsType", item.getGoodsType()+"");
			map.put("goodsTypeName", item.getGoodsTypeName());
			goodTypeList.add(map);
		}
		//车辆类型
		for (NstCarTypeEnum item :NstCarTypeEnum.values()){
			Map<String, String> map = new HashMap<>();
			map.put("carType", item.getCarType()+"");
			map.put("carTypeName", item.getCarTypeName());
			carTypeList.add(map);
		}
		totalMap.put("goodTypeList", goodTypeList);
		totalMap.put("carTypeList", carTypeList);
		result.setObject(totalMap);
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	/**
	 * 删除货源
	 * @param memberAddressId
	 * @throws Exception
	 */
	private void delete(Long memberAddressId) throws Exception{
		logger.info("[INFO]Delete memberAddressId : " + memberAddressId);
		Map<String, Object> map = new HashMap<>();
		map.put("id", memberAddressId);
		map.put("version", 0);
		map.put("updateUserId", "admin");
		Long time1 = System.currentTimeMillis();
		NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(map, gdProperties.getNstApiUrl() + NstApiRequestV1Enum.DELETE_GOODS_TRANSFER.getUri());
		Long time2 = System.currentTimeMillis();
		System.out.println("[INFO]Delete delivery cost time: " + (time2 - time1));
		logger.info("[INFO]Delete memberAddressId result: " + nstResponse.getMsg());
	}
}
