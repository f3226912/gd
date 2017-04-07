package com.gudeng.commerce.gd.api.controller.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.OrderCallbackInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.impl.order.OrderTool3ServiceImpl;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.exception.RefundException;

@Controller
@RequestMapping("/callbackOrder")
public class NstCallbackOrderController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(NstCallbackOrderController.class);

	@Autowired
	private OrderTool3ServiceImpl orderService;

	/**
	 * 农速通回调更新订单状态
	 * 
	 * @description 类型1 货源分配不成功 2 司机验货通过 3 司机验货不通过 4 司机3天未提交验货结果 5 司机确认送达 6
	 *              货源分配失败 7货运订单生产成功
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateStatus")
	public void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			OrderCallbackInputDTO paramValue = (OrderCallbackInputDTO) getDecryptedObject(request,
					OrderCallbackInputDTO.class);
			ErrorCodeEnum addResult = orderService.updateStatus(paramValue);
			setEncodeResult(result, addResult, request, response);
		} catch (RefundException e) {
			logger.warn("[ERROR]退预付款异常", "code:" + e.getCode() + " message:" + e.getMessage());
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]回调订单更新失败", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
