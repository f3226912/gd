package com.gudeng.commerce.info.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.customer.dto.SysmessageDTO;
import com.gudeng.commerce.info.customer.util.CommonConstant;
import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.commerce.info.home.service.SysmessageHomeService;
import com.gudeng.commerce.info.home.service.SysmessageuserHomeService;
import com.gudeng.commerce.info.home.util.LoginUserUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("sysMessage")
public class SysmessageController extends AdminBaseController {
	private static final GdLogger logger = GdLoggerFactory.getLogger(SysmessageController.class);
	
	@Autowired
	private SysmessageHomeService sysmessageHomeService;

	@Autowired
	private SysmessageuserHomeService sysmessageuserHomeService;
	
	
	@RequestMapping("queryMessageList")
	public String queryMessageList(HttpServletRequest request)throws Exception{
		return "H5/message/infoList";
	}
	@RequestMapping(value="queryMessageListTotal", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMessageListTotal(HttpServletRequest request)throws Exception{
		List<SysmessageDTO> list =null;
		Map<String,Object> paraMap = new HashMap<String,Object>();
		try{
		
			//当前用户的消息列表
			paraMap.put("startRow",  Integer.valueOf(request.getParameter("startRow")));
			paraMap.put("endRow",   Integer.valueOf(request.getParameter("endRow")));
			paraMap.put("userID", LoginUserUtil.getLoginUserId(request));
			list= sysmessageHomeService.getMessageListByUser(paraMap);	
		}catch(Exception e){
			logger.trace(e.getMessage());
		}
		return JSONObject.toJSONString(list,SerializerFeature.WriteDateUseDateFormat);
		
	}
	@RequestMapping("queryUnreadTotal/{userID}")
	@ResponseBody
	public String queryUnreadTotal(@PathVariable("userID") String userID,HttpServletRequest request)throws Exception{
		//当前用户未读消息记总录数
		Integer count = sysmessageHomeService.getUnReadMessageCount(userID);
		return count.toString();
	}
	/**
	 * 查看详细信息且此消息未读修改为已读
	 * @author liufan
	 * @param request
	 * @return String
	 */
	@RequestMapping("viewMessageDetail")
	public String viewMessageDetail(HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			map.put("userID", LoginUserUtil.getLoginUserId(request));
			map.put("messageID", request.getParameter("messageID"));
			//根据userID和messageID查询出此消息的详细信息
			SysmessageDTO sysmessageDTO = sysmessageHomeService.getMessageDetail(map);
			//判断查询详细信息的是否已读状态，如果为未读，更新其为已读
			if(null != sysmessageDTO && Constant.MESSAGE_STATUS_UNREAD.equals(sysmessageDTO.getIsread())){
				sysmessageuserHomeService.updateMessageIsread(map);
			}
			putModel("sysmessageDTO",sysmessageDTO);
		}catch(Exception e){
			logger.trace(e.getMessage());
		}
		return "H5/message/infoContext";
	}
	
	 /**
     * 删除消息;
     * 
     * @param request
     * @return
     */
    @RequestMapping("deleteMessage")
    @ResponseBody
    public String deleteMessage(HttpServletRequest request) throws Exception {
    	Map<String,Object> map = new HashMap<String,Object>();
    	int result= 0;
        try {   
        	map.put("userID", LoginUserUtil.getLoginUserId(request));
			map.put("messageID", request.getParameter("messageID"));
			result = sysmessageuserHomeService.updateMessageIsdel(map);
        } catch (Exception e) {
        	logger.trace(e.getMessage());
        }
        return result>0?CommonConstant.COMMON_AJAX_SUCCESS:"fail";
    }

}
