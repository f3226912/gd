package com.gudeng.commerce.gd.m.controller.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.message.PushNoticeToolService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("sysMessage")
public class PushNoticeController extends MBaseController{
	private static final GdLogger logger = GdLoggerFactory.getLogger(PushNoticeController.class);
	@Autowired
	private PushNoticeToolService pushNoticeToolService;

	
	@RequestMapping("queryMessageList")
	public String queryMessageList(HttpServletRequest request,PushNoticeDTO inputParamDTO)throws Exception{
		try{
			if(StringUtils.isEmpty(inputParamDTO.getUserID()) || StringUtils.isEmpty(inputParamDTO.getClient())){
				return "";
			}
			//是否隐藏消息列头的标识,如果有传此参数，且参数值不为空时，需要隐藏此消息列表，如果没有传或者为空，则不需要隐藏消息列头
			String flag = request.getParameter("m");
			if(!StringUtils.isEmpty(flag)){
				putModel("displayFlag",flag);
			}
			putModel("pushNoticeDTO",inputParamDTO);
		}catch(Exception e){
			logger.trace(e.getMessage());
			return null;
		}
		return "H5/message/infoList";
	}
	/**
	 * 查询当前用户的创建时间，在当前用户还未注册之前的消息就不要发了。
	 * @return
	 */
	public Date getUserRegisteTime(PushNoticeDTO inputParamDTO) throws Exception{
		PushNoticeDTO pushUserRegisteInfo = pushNoticeToolService.getUserRegisteTime(inputParamDTO);
		if(null == pushUserRegisteInfo || null ==pushUserRegisteInfo.getCreateTime()){
			throw new Exception("此用户不存在!");
		}
		return pushUserRegisteInfo.getCreateTime();
	}
	@RequestMapping(value="queryMessageListTotal", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMessageListTotal(PushNoticeDTO inputParamDTO,HttpServletRequest request)throws Exception{
		List<PushNoticeDTO> list =null;
		//Map<String,Object> paraMap = new HashMap<String,Object>();
		//ObjectResult result = new ObjectResult();
		try{
			/*String jsonStr = getParamDecodeStr(request);
			//将json 字符串封装为对象
			PushNoticeDTO inputParamDTO=(PushNoticeDTO) GSONUtils.fromJsonToObject(jsonStr, PushNoticeDTO.class); 
			String userID = inputParamDTO.getUserID();
			if(StringUtils.isEmpty(userID)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "userID不能为空", request, response);
				return;
			}
			String client = inputParamDTO.getClient();
			if(StringUtils.isEmpty(client)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "client不能为空", request, response);
				return;
			}
			*/
			//查询出当前用户的注册时间，在注册时间之前发布的消息就没必要再让用户看到了。
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String registeTime =  sdf.format(getUserRegisteTime(inputParamDTO));
			inputParamDTO.setCreateTimeStr(registeTime);
			list= pushNoticeToolService.getMessageListByUser(inputParamDTO);	
			//result.setObject(list);
		}catch(Exception e){
			logger.trace(e.getMessage());
			//setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "获取分类异常...", request, response);
			return null;
		}
		//setEncodeResult(result, ErrorCodeEnum.SUCCESS.getValue(), "", request, response);
		return JSONObject.toJSONString(list,SerializerFeature.WriteDateUseDateFormat);
		
	}
	
	@RequestMapping(value = "queryUnreadTotal", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryUnreadTotal(HttpServletRequest request,PushNoticeDTO pushNoticeDTO,HttpServletResponse response)throws Exception{
		Integer count = 0;
		/*ObjectResult result = new ObjectResult();
		String jsonStr = getParamDecodeStr(request);
		//将json 字符串封装为对象
		PushNoticeDTO inputParamDTO=(PushNoticeDTO) GSONUtils.fromJsonToObject(jsonStr, PushNoticeDTO.class); 
		String userID = inputParamDTO.getUserID();
		if(StringUtils.isEmpty(userID)){
			setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "userID不能为空", request, response);
			return;
		}
		String client = inputParamDTO.getClient();
		if(StringUtils.isEmpty(client)){
			setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "client不能为空", request, response);
			return;
		}
		*/
		/*
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userID", request.getParameter("userID"));
		map.put("client", request.getParameter("client"));*/
		try{
			//当前用户未读消息记总录数
			count = pushNoticeToolService.getUnReadMessageCount(pushNoticeDTO);
			//result.setObject(count);
			//setEncodeResult(result, ErrorCodeEnum.SUCCESS.getValue(), "", request, response);
		}catch(Exception e){
			return "0";
		}
		return count.toString();
	}

	/**
	 * 查看详细信息且此消息未读修改为已读
	 * @author liufan
	 * @param request
	 * @return String
	 */
	@RequestMapping("viewMessageDetail")
	public String viewMessageDetail(HttpServletRequest request,PushNoticeDTO inputParamDTO,HttpServletResponse response) throws Exception{
		//Map<String,Object> map = new HashMap<String,Object>();
		//ObjectResult result = new ObjectResult();
		try{
		
			/*String jsonStr = getParamDecodeStr(request);
			//将json 字符串封装为对象
			PushNoticeDTO inputParamDTO=(PushNoticeDTO) GSONUtils.fromJsonToObject(jsonStr, PushNoticeDTO.class); 
			String userID = inputParamDTO.getUserID();
			if(StringUtils.isEmpty(userID)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "userID不能为空", request, response);
				return;
			}
			Long id = inputParamDTO.getId();
			if(id ==null){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "id不能为空", request, response);
				return;
			}
			map.put("id", request.getParameter("noticeID"));*/
			
			//根据noticeID查询出此消息的详细信息
			PushNoticeDTO pushNotice = pushNoticeToolService.getMessageDetail(inputParamDTO);
			synchronized (this) {
				Integer count = pushNoticeToolService.getPushUserCount(inputParamDTO);
				//根据id和userID查询消息用户关联表，是否存在记录，如果不存在才往表里插入数据。(因为只通过传入的参数来判断是否往表里插入记录的话，一直请求接口，会往表里多插入重复记录)
				
				//将已读的数据保存到push_notice_user关系表中,1表示未读，2表示已读
				if(null != pushNotice && "1".equals(inputParamDTO.getIsread()) && count<1){
					//设置消息为未删除
					inputParamDTO.setIsdel("1");
					pushNoticeToolService.updateMessageIsread(inputParamDTO);
				}
			}
			//result.setObject(pushNotice);
			
			pushNotice.setUserID(inputParamDTO.getUserID());
			//给具体的客户端设值
			pushNotice.setClient(inputParamDTO.getClient());
			//是否隐藏消息列头的标识,如果有传此参数，且参数值不为空时，需要隐藏此消息列表，如果没有传或者为空，则不需要隐藏消息列头
			String flag = request.getParameter("m");
			if(!StringUtils.isEmpty(flag)){
				putModel("displayFlag",flag);
			}
			putModel("sysmessageDTO",pushNotice);
		}catch(Exception e){
			logger.trace(e.getMessage());
			//setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), "获取分类异常...", request, response);
			return null;
		}
		//setEncodeResult(result, ErrorCodeEnum.SUCCESS.getValue(), "", request, response);
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
    public String deleteMessage(HttpServletRequest requestPush,PushNoticeDTO inputParamDTO) throws Exception {
    	//Map<String,Object> map = new HashMap<String,Object>();
    	int result= 0;
        try {
        	//如果是从列表页面删除的话，push_notice_user表中并无数据，因此需要插入一条已删除状态的数据到此表中，默认是已读过的数据，直接修改表中的状态为删除
        	if("1".equals(inputParamDTO.getIsread())){
        		//设置消息为已删除
        		inputParamDTO.setIsdel("2");
        		result = pushNoticeToolService.updateMessageIsread(inputParamDTO);
        	}else{
        		result = pushNoticeToolService.updateMessageIsdel(inputParamDTO);
        	}
        } catch (Exception e) {
        	logger.trace(e.getMessage());
        	return "fail";
        }
        return result>0?"success":"fail";
    }

}
