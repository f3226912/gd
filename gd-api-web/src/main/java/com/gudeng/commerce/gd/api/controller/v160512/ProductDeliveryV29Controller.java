package com.gudeng.commerce.gd.api.controller.v160512;

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
import com.gudeng.commerce.gd.api.dto.output.MemberAddressOutputDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;

@Controller
@RequestMapping("/v29/deliver")
public class ProductDeliveryV29Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ProductDeliveryV29Controller.class);
	@Autowired
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;
	@Autowired
	private MemberAddressApiService memberAddressApiService;
	@Autowired
	private AreaToolService areaToolService;
	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;
	
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, MemberAddressInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long memberAddressId = null;
		try {
			//判断是否开启发货开关
			Integer deliverStatus = productDeliveryDetailToolService.isOpenForAddDelivery();
			if(deliverStatus == null || deliverStatus == 0){
				setEncodeResult(result, ErrorCodeEnum.VERSION_NOT_CORRECT, request, response);
				return;
			}
			
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (MemberAddressInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberAddressInputDTO.class);
			
			//增加货源
			StatusCodeEnumWithInfo addResult = memberAddressApiService.addMemberAddress(inputDTO, false);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				if(StringUtils.isNotBlank(inputDTO.getSelectedList())){
					memberAddressId = Long.parseLong(addResult.getObj().toString());
					//增加出货信息
					ErrorCodeEnum addResult2 = productDeliveryDetailToolService.add(inputDTO, memberAddressId, false);
					if(ErrorCodeEnum.SUCCESS != addResult2){
						MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
						memberAddressDTO.setId(memberAddressId);
						memberAddressApiService.delMemberAddress(memberAddressDTO);
						setEncodeResult(result, addResult2, request, response);
						return;
					}
				}
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
			}
		} catch (Exception e) {
			logger.error("[ERROR]添加货源异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			if(memberAddressId != null){
				MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
				memberAddressDTO.setId(memberAddressId);
				try {
					memberAddressApiService.delMemberAddress(memberAddressDTO);
				} catch (Exception e1) {
					logger.error("[ERROR]删除货源异常", e);
				}
			}
		}
	}
	
	@RequestMapping("/getInfo")
	public void getInfo(HttpServletRequest request, HttpServletResponse response, MemberAddressInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (MemberAddressInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberAddressInputDTO.class);
			Long memberAddressId = inputDTO.getMemberAddressId();
			if(memberAddressId == null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			
			MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
			memberAddressDTO.setId(memberAddressId);
			memberAddressDTO = memberAddressApiService.getMemberAddressById(memberAddressDTO);
			if(memberAddressDTO == null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_NOT_EXISTED, request, response);
				return;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberAddressId", memberAddressId);
			List<?> productDetailList = productDeliveryDetailToolService.getListByCondition(map);
			
			MemberAddressOutputDTO output = new MemberAddressOutputDTO();
			setInfoIntoMemberAddressOutputDTO(memberAddressDTO, output);
			output.setProductDetailList(productDetailList);
			result.setObject(output);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]查询货源详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, MemberAddressInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (MemberAddressInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberAddressInputDTO.class);
			Long memberAddressId = inputDTO.getMemberAddressId();
			if(memberAddressId == null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			
			MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
			memberAddressDTO.setId(memberAddressId);
			memberAddressDTO = memberAddressApiService.getMemberAddressById(memberAddressDTO);
			if(memberAddressDTO == null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_NOT_EXISTED, request, response);
				return;
			}
			
			NstOrderBaseinfoDTO orderDTO = nstOrderBaseinfoToolService.getByMemberAddressId(memberAddressId);
			if(orderDTO != null){
				setEncodeResult(result, ErrorCodeEnum.NST_ORDER_ALREADY_DELIVER, request, response);
				return;
			}
			//更新货源
			StatusCodeEnumWithInfo addResult = memberAddressApiService.addMemberAddress(inputDTO, true);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				Map<String, Object> map = new HashMap<>();
				map.put("memberAddressId", memberAddressId);
				List<?> existedProductList = productDeliveryDetailToolService.getListByCondition(map );
				//删除已选择的商品
				if(existedProductList != null && existedProductList.size() > 0){
					nstOrderBaseinfoToolService.updateDeliverStatusByMemberAddressId(memberAddressId, 0);
				}
				
				if(StringUtils.isNotBlank(inputDTO.getSelectedList())){
					//修改出货信息
					ErrorCodeEnum updateResult = productDeliveryDetailToolService.add(inputDTO, memberAddressId, true);
					if(ErrorCodeEnum.SUCCESS != updateResult){
						setEncodeResult(result, updateResult, request, response);
						return;
					}
				}else if(existedProductList != null && existedProductList.size() > 0){
					productDeliveryDetailToolService.deleteByMemberAddressId(memberAddressId);
				}
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
			}
		} catch (Exception e) {
			logger.error("[ERROR]更新货源详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	private void setInfoIntoMemberAddressOutputDTO(
			MemberAddressDTO memberAddressDTO, MemberAddressOutputDTO output) {
		try {
			AreaDTO f_info = areaToolService.getAreaDetail(memberAddressDTO.getF_provinceId(), memberAddressDTO.getF_cityId(), memberAddressDTO.getF_areaId());
			AreaDTO s_info = areaToolService.getAreaDetail(memberAddressDTO.getS_provinceId(), memberAddressDTO.getS_cityId(), memberAddressDTO.getS_areaId());
			output.setCarType(ParamsUtil.getStringFromInt(memberAddressDTO.getCarType(), ""));
			output.setContactName(memberAddressDTO.getContactName());
			output.setF_areaId(memberAddressDTO.getF_areaId());
			output.setF_area(f_info.getArea());
			output.setF_cityId(memberAddressDTO.getF_cityId());
			output.setF_city(f_info.getParentName());
			output.setF_provinceId(memberAddressDTO.getF_provinceId());
			output.setF_province(f_info.getpParentName());
			output.setF_detailed_address(memberAddressDTO.getF_detailed_address());
			output.setGoodsType(ParamsUtil.getStringFromInt(memberAddressDTO.getGoodsType(), ""));
			output.setMemberAddressId(memberAddressDTO.getId());
			output.setPrice(ParamsUtil.getStringFromDouble(memberAddressDTO.getPrice(),"0.0"));
			output.setRemark(memberAddressDTO.getRemark());
			output.setS_areaId(memberAddressDTO.getS_areaId());
			output.setS_area(s_info.getArea());
			output.setS_cityId(memberAddressDTO.getS_cityId());
			output.setS_city(s_info.getParentName());
			output.setS_provinceId(memberAddressDTO.getS_provinceId());
			output.setS_province(s_info.getpParentName());
			output.setS_detailed_address(memberAddressDTO.getS_detailed_address());
			output.setSendDate(DateUtil.getDate(memberAddressDTO.getSendDate(), DateUtil.DATE_FORMAT_DATEONLY));
			output.setSendDateType(ParamsUtil.getStringFromInt(memberAddressDTO.getSendDateType(),""));
			output.setTotalSize(ParamsUtil.getStringFromInt(memberAddressDTO.getTotalSize(),""));
			output.setTotalWeight(ParamsUtil.getStringFromDouble(memberAddressDTO.getTotalWeight(), "0.0"));
		} catch (Exception e) {
			logger.error("[ERROR]查找出货收货地址异常", e);
		}
	}
}
