package com.gudeng.commerce.gd.api.controller.v2;

 
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
import com.gudeng.commerce.gd.api.redis.RedisKeys.RedisKey;
import com.gudeng.commerce.gd.api.service.WalletToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.SyncUtil;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;
import com.innovane.win9008.exception.BusinessException;

@Controller
@RequestMapping("/v2/wallet")
public class WalletController extends GDAPIBaseController{
	@Autowired
	public WalletToolService walletToolService;

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	@RequestMapping("/getWalletIndex")
	public void walletIndex(HttpServletRequest request, HttpServletResponse response, AccInfoDTO accInfoDTO) {
		ObjectResult result=new ObjectResult();
		try {
			result.setObject(walletToolService.getWalletIndex(Long.valueOf(accInfoDTO.getMemberId())));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("获取账户首页信息出错",e);
		}
		renderJson(result, request, response);
	}
	
	
	@RequestMapping("/addAccInfo")
	public void addAccInfo(HttpServletRequest request, HttpServletResponse response, AccInfoDTO accInfoDTO) {
		ObjectResult result=new ObjectResult();
		try {
			Map<String, Object> map = redisClient.get(RedisKey.VERIFY_CODE_KEY.value + accInfoDTO.getAccount());
			walletToolService.addAccInfo(accInfoDTO, map);
			//验证完成，将换成移除
			SyncUtil.removeVerifyCode(accInfoDTO.getAccount(), redisClient);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}
		catch (BusinessException e) {
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			logger.error("添加账户信息异常",e);
		}
		catch (Exception e) {
			logger.error("添加账户信息异常",e);
		}
		renderJson(result, request, response);
	}
	
	
	@RequestMapping("/updateTransPwd")
	public void updateTransPwd(HttpServletRequest request, HttpServletResponse response, AccInfoDTO accInfoDTO) {
		ObjectResult result=new ObjectResult();
		try {	
			Map<String, Object> map = redisClient.get(RedisKey.VERIFY_CODE_KEY.value + accInfoDTO.getAccount());
			walletToolService.updateTransPwd(accInfoDTO,map);
			SyncUtil.removeVerifyCode(accInfoDTO.getAccount(), redisClient);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch (BusinessException e) {
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			logger.error("添加账户信息异常",e);
		}
		catch (Exception e) {
			logger.error("修改交易密码异常",e);
		}
		renderJson(result, request, response);
	}
	
	
	
	
}
