package com.gudeng.commerce.gd.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.ArgTelStatQueryBean;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.PushRecToolService;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

@Controller
@RequestMapping("pushrec")
public class PushRecController extends AdminBaseController
{	
	@Autowired
	public PushRecToolService pushRecToolService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("")
	public String pushrec(HttpServletRequest request){
		return "pushrec/pushRec_list";
	}
	
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			String id=request.getParameter("id");
            
			Map<String, Object> map = new HashMap<String, Object>();
			//记录数
			map.put("total", pushRecToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<PushRecordDTO> list = pushRecToolService.getBySearch(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			
		}
		return null;
	}

	/**
	 * 根据多个条件查询
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybysearch" )
	@ResponseBody
    public String queryByName(ArgTelStatQueryBean atsqb, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			String title=request.getParameter("title");
			if(null != title && !title.equals("")){
				map.put("title", title);
			}
			if (StringUtil.isNotEmpty(atsqb.getStartDate())) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(atsqb.getStartDate())));
			}
			if (StringUtil.isNotEmpty(atsqb.getEndDate())) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(atsqb.getEndDate())));
			}	
			
			map.put("total", pushRecToolService.getTotal(map));

			//设定分页,排序
			setCommParameters(request, map);
			
			//list
			List<PushRecordDTO> list = pushRecToolService.getBySearch(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 根据多个条件查询
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save" )
	@ResponseBody
    public String save(PushRecordDTO pushDto, HttpServletRequest request){      
		try {
			if(null == pushDto.getType() || pushDto.getType().trim().equals("")){
				return "type";//广播  单播
			}
			if(null == pushDto.getTitle() || pushDto.getTitle().trim().equals("")){
				return "title";
			}
			if(null == pushDto.getContent() || pushDto.getContent().trim().equals("")){
				return "content";
			}
			if(null == pushDto.getReceiveType() || pushDto.getReceiveType().trim().equals("")){
				return "receiveType"; //接收类型
			}
			if( pushDto.getType().trim().equals("1") && null == pushDto.getMemberId() ){
				return "memberId";//单播时的接收人
			}
			
			
			
			UMengPushMessage pushMessage = new UMengPushMessage();
			GdMessageDTO gdMessage = new GdMessageDTO();
			gdMessage.setSendType(pushDto.getType());//单推or广播
			gdMessage.setTicket(pushDto.getTitle());//直接使用title
			gdMessage.setTitle(pushDto.getTitle());
			gdMessage.setContent(pushDto.getContent());
			gdMessage.setAfter_open("go_activity");
			if( pushDto.getReceiveType().equals("2")){//农商友推送
				gdMessage.setSendApp("1");//*sendApp:推送对象:1:农批宝,2:农速通
				gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
			}else if( pushDto.getReceiveType().equals("3")){//农速通推送
				gdMessage.setSendApp("2");//*sendApp:推送对象:1:农批宝,2:农速通
				gdMessage.setActivity("com.gudeng.nongst.ui.activity.HomeActivity");
			}
//			  点击"通知"的后续行为，默认为打开app
//			  "go_app": 打开应用
//		       "go_url": 跳转到URL
//		       "go_activity": 打开特定的activity
//		       "go_custom": 用户自定义内容。

			gdMessage.setProduction_mode(true);
			Map<String,String> extraMap = new HashMap<String,String>();
			
			
			PushRecordEntity prEntity = new PushRecordEntity();
			prEntity.setTitle(pushDto.getTitle());
			prEntity.setContent(pushDto.getContent());
			prEntity.setType(pushDto.getType());
			prEntity.setReceiveType(pushDto.getReceiveType());
			
			if(pushDto.getType().equals("2")){
				prEntity.setMemberId(null);//广播的时候，直接设置为null
			}else{
				prEntity.setMemberId(pushDto.getMemberId());
				MemberBaseinfoDTO mbdto=memberBaseinfoToolService.getById(String.valueOf(pushDto.getMemberId()));

				gdMessage.setDevice_tokens(mbdto.getDevice_tokens());
				
			}
			if(pushDto.getReceiveType().equals("2")){//农批宝
				String url1=request.getParameter("url1");
				prEntity.setRedirectUrl(url1);
				extraMap.put("openmenu", url1);
			}
			
			if(pushDto.getReceiveType().equals("3")){//农速通
				String url2=request.getParameter("url2");
				prEntity.setRedirectUrl(url2);
				extraMap.put("openmenu", url2);
			}
			
			if(pushDto.getType().equals("1")){
				extraMap.put("sendType", "1");//单播
			}else if(pushDto.getType().equals("2")){
				extraMap.put("sendType", "2");//广播
			}
			
			gdMessage.setExtraMap(extraMap);

			prEntity.setState("0");//新增均为未读
			prEntity.setCreateTime(new Date());
			prEntity.setOrigin(1);//管理后台发布，写死

			
			Long recordId=pushRecToolService.add(prEntity);

			if(null != recordId && recordId>0L){
				pushMessage.pushMessage(gdMessage);
				return "success";
			}else{
				return "faild";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 增加修改同一个页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request){
		return "pushrec/pushRec_edit";
	}
	
}
