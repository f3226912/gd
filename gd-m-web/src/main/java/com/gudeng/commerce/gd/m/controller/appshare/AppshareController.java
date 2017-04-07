package com.gudeng.commerce.gd.m.controller.appshare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.AppshareToolService;


/**
 * @Description H5的活动
 * @Project gd-m-web
 * @ClassName AppshareController.java
 * @Author liufan
 * @CreationDate 2016年8月9日 下午5:14:22
 * @Version 
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 * 
 */
@Controller
@RequestMapping("activityAppshare")
public class AppshareController extends MBaseController {
	  private static Logger logger = LoggerFactory.getLogger(AppshareController.class);
	  
	  @Autowired
	  public AppshareToolService appshareToolService;
	 /**
     * @Description 跳转到活动页面
     * @param 
     * @param request
     * @return
     * @CreationDate 2016年8月9日 下午5:26:11
     * @Author liufan
     */
    @RequestMapping(value = "getActivityAppshare")
    public String getActivityAppshare( HttpServletRequest request,String userId,String memberId,String marketId) {
    	String marketName = request.getParameter("marketName");
    	putModel("memberId", memberId);
    	putModel("marketId", marketId);
    	return "H5/appshare/activity_"+marketName;
    }
    
    /**
     * @Description 跳转到规则页面
     * @param 
     * @param request
     * @return
     * @CreationDate 2016年8月9日 下午5:26:11
     * @Author liufan
     */
    @RequestMapping(value = "getActivitySpecification")
    public String getActivitySpecification( HttpServletRequest request) {
    	return "H5/appshare/specification";
    }
    /**
	 * 修改点击数量
	 * @param request
	 * @param response
	 */
    @ResponseBody
	@RequestMapping("/updateViewCount")
	public String updateViewCount(HttpServletRequest request, HttpServletResponse response,AppshareDTO inputDTO) {
    	String result = "";
		try {
			if(inputDTO.getMemberId() == null || inputDTO.getMarketId() == null){
				logger.info("memberId or marketId is null");
				result = "fail";
				return result;
			}
			String ip = getIpAddr(request);
			inputDTO.setVisitorIp(ip);
			logger.info("enter method updateViewCount,the ip is:"+ip);
			//先通过memberId和marketId查询是否有分享过此记录，有分享后才去执行后续操作。没有分享就不操作了(防止直接输入分享地址而执行了此方法)
			AppshareDTO appshareDTO = appshareToolService.getAppShareByCondition(inputDTO);
			if(appshareDTO != null){
				//根据memberId.marketId和ip查询IP记录表,如果表中无此用户和市场的数据，那么需要增加一条访问IP数据，同时修改分享表中的访问次数
				int existsIpCount = appshareToolService.getVisitorIpCount(inputDTO);
				if(existsIpCount < 1){
					appshareToolService.updateAppShareAndVisitorIp(inputDTO);
				}
			}
			
			result = "success";
		} catch (Exception e) {
			logger.warn("修改访问次数异常", e);
			result = "fail";
		}
		return result;
	}
}