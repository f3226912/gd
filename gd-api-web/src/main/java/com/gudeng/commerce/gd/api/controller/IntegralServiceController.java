package com.gudeng.commerce.gd.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.IntegralToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
/**
 * 积分流水接口
 * @author xiaojun
 *
 */
@Controller
@RequestMapping("integral")
public class IntegralServiceController extends GDAPIBaseController {
	/** 日志*/
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(FocusCategoryController.class);

	@Autowired
	private IntegralToolService integralToolService;
	/**
	 *积分流水记录查询
	 */
	@RequestMapping("flow")
	public void integralFlow(HttpServletRequest request,HttpServletResponse response,IntegralDTO integralDTO){
		ObjectResult result = new ObjectResult();
		try {
			List<IntegralDTO> listIdto=integralToolService.selectIntegralFlow(integralDTO.getMemberId());
			result.setObject(listIdto);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("获取积分列表异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("获取积分列表异常: " + e.getMessage());
		}
		renderJson(result, request, response);
	}
}
