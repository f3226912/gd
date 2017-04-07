package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.FocusCategoryController;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.CarLineApiService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkCarLineDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/*
 * 农速通2.1.3我要找车分页查询优化
 * @date 20160707
 * @author zhangnf
 */
@Controller
@RequestMapping("nst213/carLineManager")
public class CarLineController extends GDAPIBaseController {
	private static final GdLogger logger = GdLoggerFactory.getLogger(FocusCategoryController.class);
	@Autowired
	public CarLineApiService carLineApiService;
	@Autowired
	private AreaToolService areaToolService;
	/**
	 * 获取用户的线路信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCarlineApiByPage")
	public void getCarLineMessageNew(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			TrunkCarLineDTO trunkCarLineDTO = (TrunkCarLineDTO) GSONUtils.fromJsonToObject(jsonStr,TrunkCarLineDTO.class);
			Map<String, Object> paramMap = getMap(trunkCarLineDTO);
			// 做分页
			int total = carLineApiService.getCarLineCount(paramMap);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, paramMap);
			logger.info("---------------"+paramMap.toString());
			List<TrunkCarLineDTO> listByPage = carLineApiService.getCarLineList(paramMap);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(listByPage);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("获取线路列表异常",e);
		}
	}

	/**
	 * 拼装Map
	 * @param TrunkCarLineDTO
	 * @return
	 */
	private Map<String, Object> getMap(TrunkCarLineDTO dto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		// app端传入默认查询起始城市
		if (dto.getLocationCity() != null) {
			AreaDTO area = areaToolService.getByAreaName(dto.getLocationCity());
			if ("北京市".equals(dto.getLocationCity()) || "上海市".equals(dto.getLocationCity())
					|| "天津市".equals(dto.getLocationCity()) || "重庆市".equals(dto.getLocationCity())) {
				dto.setS_provinceId(Long.parseLong(area.getAreaID()));
			} else {
				dto.setS_cityId(Long.parseLong(area.getAreaID()));
			}
		}
		map.put("e_provinceId", dto.getE_provinceId());
		map.put("e_cityId", dto.getE_cityId());
		map.put("e_areaId", dto.getE_areaId());
		map.put("s_provinceId", dto.getS_provinceId());
		map.put("s_cityId", dto.getS_cityId());
		map.put("s_areaId", dto.getS_areaId());
		map.put("carType", dto.getCarType());
		map.put("carLength", dto.getCarLength());
		map.put("carLengthFrom", dto.getCarLengthFrom());
		map.put("carLengthTo", dto.getCarLengthTo());
		map.put("maxLoadFrom", dto.getMaxLoadFrom());
		map.put("maxLoadTo", dto.getMaxLoadTo());
		map.put("mlat", dto.getMlat());
		map.put("mlng", dto.getMlng());
		if (dto.getMlat() != null && dto.getMlng() != null) {
			map.put("closest", "Y");
		}
		return map;
	}
}
