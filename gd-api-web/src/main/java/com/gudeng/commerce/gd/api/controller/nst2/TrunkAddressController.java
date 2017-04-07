package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.nst.TrunkAddressToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkAddressDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 干线物流
 * 
 * @author xiaojun
 *
 */
@Controller
@RequestMapping("V2/nst")
public class TrunkAddressController extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(NstOrderBaseinfoController.class);
	@Autowired
	private TrunkAddressToolService trunkAddressToolService;
	@Autowired
	private AreaToolService AreaToolService;

	/**
	 * 干线物流货源列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getTrunkAddressList")
	public void getTrunkAddressList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			TrunkAddressDTO trunkAddressDTO = (TrunkAddressDTO) GSONUtils.fromJsonToObject(jsonStr,
					TrunkAddressDTO.class);
			Map<String, Object> paramMap = getMap(trunkAddressDTO);
			// 做分页
			int total = trunkAddressToolService.getTrunkAddressListCount(paramMap);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, paramMap);
			List<TrunkAddressDTO> listByPage = trunkAddressToolService.getTrunkAddressList(paramMap);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(listByPage);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("查询干线货源列表异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 干线物流我发的货
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getTrunkAddressListByMemberId")
	public void getTrunkAddressListByMemberId(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			TrunkAddressDTO trunkAddressDTO = (TrunkAddressDTO) GSONUtils.fromJsonToObject(jsonStr,
					TrunkAddressDTO.class);
			if (trunkAddressDTO.getMemberId()==null) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("memberId", trunkAddressDTO.getMemberId());
			paramMap.put("userType", trunkAddressDTO.getUserType());
			// 做分页
			int total = trunkAddressToolService.getTrunkAddressListCountByMemberId(paramMap);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, paramMap);
			List<TrunkAddressDTO> listByPage = trunkAddressToolService.getTrunkAddressListByMemberId(paramMap);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(listByPage);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("查询干线货源列表异常");
			e.printStackTrace();
		}
	}
	/**
	 * 拼装Map
	 * 
	 * @param trunkAddressDTO
	 * @return
	 */
	private Map<String, Object> getMap(TrunkAddressDTO dto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		// app端传入默认查询起始城市
		if (dto.getLocationCity() != null) {
			AreaDTO area = AreaToolService.getByAreaName(dto.getLocationCity());
			if ("北京市".equals(dto.getLocationCity()) || "上海市".equals(dto.getLocationCity())
					|| "天津市".equals(dto.getLocationCity()) || "重庆市".equals(dto.getLocationCity())) {
				dto.setS_provinceId(Long.parseLong(area.getAreaID()));
			} else {
				dto.setS_cityId(Long.parseLong(area.getAreaID()));
			}
		}
		map.put("s_provinceId", dto.getS_provinceId());
		map.put("s_cityId", dto.getS_cityId());
		map.put("s_areaId", dto.getS_areaId());
		map.put("f_provinceId", dto.getF_provinceId());
		map.put("f_cityId", dto.getF_cityId());
		map.put("f_areaId", dto.getF_areaId());
		map.put("carType", dto.getCarType());
		map.put("carlength", dto.getCarLength());
		map.put("clat", dto.getClat());
		map.put("clng", dto.getClng());
		map.put("goodsType", dto.getGoodsType());
		map.put("minCarLength", dto.getMinCarLength());
		map.put("maxCarLength", dto.getMaxCarLength());
		map.put("minTotalWeight", dto.getMinTotalWeight());
		map.put("maxTotalWeight", dto.getMaxTotalWeight());
		map.put("minTotalSize", dto.getMinTotalSize());
		map.put("maxTotalSize", dto.getMaxTotalSize());
		if (dto.getClat() != null && dto.getClng() != null) {
			map.put("closest", "Y");
		}
		return map;
	}
}
