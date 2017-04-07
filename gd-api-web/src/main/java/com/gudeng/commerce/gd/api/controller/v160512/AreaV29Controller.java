package com.gudeng.commerce.gd.api.controller.v160512;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.AreaInfoEnum;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;

/**
 * 功能描述：省市区信息控制中心
 * 
 */
@Controller
@RequestMapping("/v29/area")
public class AreaV29Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AreaV29Controller.class);
	@Autowired
	private AreaToolService areaToolService;
	
	@RequestMapping("/getAllProvince")
	public void getAllProvince(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try{
			String jsonStr = getParamDecodeStr(request);
			String filter = (String)GSONUtils.getJsonValueStr(jsonStr, "filter");
			List<AreaDTO> provinceList = areaToolService.getAllProvince();
			if("1".equals(filter)){//过滤港澳台
				for(int i = provinceList.size() - 1; i >=0 ; i--){
					AreaDTO area = provinceList.get(i);
					for (AreaInfoEnum item :AreaInfoEnum.values()){
						if (item.getkey().equals(area.getAreaID())){
							provinceList.remove(i);
						}
					}
				}
			} 
			result.setObject(provinceList);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取所有省份信息异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
