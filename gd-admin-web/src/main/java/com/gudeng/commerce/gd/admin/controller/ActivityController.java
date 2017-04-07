package com.gudeng.commerce.gd.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.ActivityQueryBean;
import com.gudeng.commerce.gd.admin.dto.ArgTelStatQueryBean;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.admin.service.ActivityToolService;
import com.gudeng.commerce.gd.customer.dto.ActivityDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityEntity;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

@Controller
@RequestMapping("activity")
public class ActivityController extends AdminBaseController
{	
	@Autowired
	public ActivityToolService activityToolService;

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("list")
	public String activity(HttpServletRequest request){
		return "activity/activity_list";
	}
	
	
	/**
	 * 默认查询
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
			if(null==id || id.equals("")){
			}else{
				map.put("id", id);
			}
			// 设置查询参数
			//记录数
			map.put("total", activityToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<ActivityEntity> list = activityToolService.getByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 根据多个条件查询
	 * 
	 * @param giftName
	 * @param actName
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="querybysearch" )
	@ResponseBody
    public String queryByName(ActivityQueryBean activityQueryBean,HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", activityQueryBean.getName());
			
			if (StringUtil.isNotEmpty(activityQueryBean.getQueryStartDate())) {
				map.put("queryStartDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(activityQueryBean.getQueryStartDate())));
			}
			if (StringUtil.isNotEmpty(activityQueryBean.getQueryEndDate())) {
				map.put("queryEndDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(activityQueryBean.getQueryEndDate())));
			}
			
			map.put("total", activityToolService.getTotal(map));

			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<ActivityEntity> list = activityToolService.getByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	
	/**
	 * 增加修改同一个方法
	 * 
	 * @return
	 */
	
	@RequestMapping(value="save" )
	@ResponseBody
    public String save(ActivityDTO activityDTO, HttpServletRequest request){      
		try {	
			String id=request.getParameter("id");
			
			activityDTO.setStrStartTime(request.getParameter("startTimeStr"));
			activityDTO.setStrEndTime(request.getParameter("endTimeStr"));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startTimeStr", request.getParameter("startTimeStr"));
			
			
			
			if(StringUtils.isNotEmpty(""+id)){			
				activityToolService.update(activityDTO);		
			}
			else{
				int i=activityToolService.getLastEndTimeByStartTime(map);
				
				System.out.println("iiiiiiiiiiiiiiiiiiiiiiiii:"+i);
				
				if(i>0){
					return "error1";
				}
				
				activityToolService.add(activityDTO);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "success";
	}
	
	
	/**
	 * 增加修改同一个页面
	 * 
	 * @param request
	 * @return
	 */
	
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request){
		return "activity/activity_edit";
	}
	
	
	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="editbyid/{id}")
    public String editbyid(@PathVariable("id") String id, HttpServletRequest request){      
		try {
			ActivityDTO dto=activityToolService.getById(id);
			
			dto.setStrStartTime(DateUtil.toString(dto.getStartTime(), DateUtil.DATE_FORMAT_DATETIME));
			dto.setStrEndTime(DateUtil.toString(dto.getEndTime(), DateUtil.DATE_FORMAT_DATETIME));
			
			List<GiftDTO> dto2=activityToolService.getByActivityId(id);
			
			putModel("dto",dto);
			putModel("dto2",dto2);
			return "activity/activity_edit";
		} catch (Exception e) {
			
		}
		return null;
	}
	
}
