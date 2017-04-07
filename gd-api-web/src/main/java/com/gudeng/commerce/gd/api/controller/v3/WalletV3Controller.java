package com.gudeng.commerce.gd.api.controller.v3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.impl.V3.WalletToolServiceImpl_V3;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.innovane.win9008.exception.BusinessException;

@Controller
@RequestMapping("/v3/wallet")
public class WalletV3Controller extends GDAPIBaseController {
	@Autowired
	public WalletToolServiceImpl_V3 walletToolServiceV3;

	/** 记录日志 */
	private static Logger logger = LoggerFactory
			.getLogger(WalletV3Controller.class);

	@RequestMapping("/getWalletIndex")
	public void walletIndex(HttpServletRequest request,
			HttpServletResponse response, AccInfoDTO accInfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String sid = request.getParameter("sid");
			result.setObject(walletToolServiceV3.getWalletIndex(Long
					.valueOf(getUser(sid).getMemberId())));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("获取账户首页信息出错", e);
		}
		renderJson(result, request, response);
	}

	@RequestMapping("/addAccInfo")
	public void addAccInfo(HttpServletRequest request,
			HttpServletResponse response, AccInfoDTO accInfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String sid= request.getParameter("sid");
			walletToolServiceV3.addAccInfo(accInfoDTO,getUser(sid));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (BusinessException e) {
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			logger.error("添加账户信息异常", e);
		} catch (Exception e) {
			logger.error("添加账户信息异常", e);
		}
		renderJson(result, request, response);
	}

	@RequestMapping("/updateTransPwd")
	public void updateTransPwd(HttpServletRequest request,
			HttpServletResponse response, AccInfoDTO accInfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String sid= request.getParameter("sid");
			walletToolServiceV3.updateTransPwd(accInfoDTO,getUser(sid));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());

		} catch (BusinessException e) {
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			logger.error("添加账户信息异常", e);
		} catch (Exception e) {
			logger.error("修改交易密码异常", e);
		}
		renderJson(result, request, response);
	}

}
