package com.gudeng.commerce.gd.api.controller.appshare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.appshare.AppshareToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AppshareDTO;

/**
 * app分享管理
 * @author liufan
 *
 */
@Controller
@RequestMapping("/appShare")
public class AppshareController extends GDAPIBaseController{
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AppshareController.class);
	
	@Autowired
	public AppshareToolService appshareToolService;
	/**
	 * 添加app分享管理
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try{
			AppshareDTO inputDTO = (AppshareDTO) getDecryptedObject(request, AppshareDTO.class);
			if(inputDTO.getMemberId() == null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			if(inputDTO.getMarketId() == null){
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
				return;
			}
			//根据memberId和marketId查询是否已经有此分享数据，没有就新增，有就不需要再次记录分享数据
			AppshareDTO existsAppshare = appshareToolService.getAppShareByCondition(inputDTO);
			if( null == existsAppshare){
				int count = appshareToolService.addAppShare(inputDTO);
				if(count > 0){
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				}else{
					setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				}
			}else{
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
			
		}catch(Exception e){
			logger.warn("添加app分享异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}	
	}
	
	
}
