package com.gudeng.commerce.gd.api.controller.v161117;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

/**
 * @purpose 供应商获取基本信息
 * @date 2016年11月10日
 */
@Controller
@RequestMapping("/v161117/supplier")
public class SupplierV1117Controller extends GDAPIBaseController {
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SupplierV1117Controller.class);

	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	private MemberToolService memberToolService;
	
	@Autowired
	private ProductToolService productToolService;
	
	/**
	 * 会员基本信息查询
	 * 
	 * 包括，
	 * 商铺地址(省市区)
	 * 会员等级(有效时间)
	 * 商品个数(已经发布，且未删除的)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getInfo")
	public void getBusiness(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			if(StringUtils.isEmpty(memberId)){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			int count = 0;
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getById(memberId);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("memberGrade", memberBaseinfoDTO.getMemberGrade());
			param.put("validTime", DateUtil.toString(memberBaseinfoDTO.getValidTime(),DateUtil.DATE_FORMAT_DATEONLY));
			param.put("expireTime",  DateUtil.toString(memberBaseinfoDTO.getExpireTime(),DateUtil.DATE_FORMAT_DATEONLY));
			BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getByUserId(memberId);
			if (businessBaseinfoDTO != null) {
				Map map = new HashMap();
				map.put("businessId", businessBaseinfoDTO.getBusinessId());
				count = productToolService.getProductCountByBusinessId(map);
				param.put("areaId", businessBaseinfoDTO.getAreaId());
				param.put("cityId", businessBaseinfoDTO.getCityId());
				param.put("provinceId", businessBaseinfoDTO.getProvinceId());
				param.put("address", businessBaseinfoDTO.getProvince()+businessBaseinfoDTO.getCity()+businessBaseinfoDTO.getArea());
			}else{
				param.put("areaId", "");
				param.put("cityId", "");
				param.put("provinceId", "");
				param.put("address", "");
			}
			param.put("productCount", count);
			result.setObject(param);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询会员相关信息失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

}
