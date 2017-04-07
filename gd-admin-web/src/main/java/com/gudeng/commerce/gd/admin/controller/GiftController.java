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
import com.gudeng.commerce.gd.admin.service.GiftToolService;
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
@RequestMapping("gift")
public class GiftController extends AdminBaseController
{	
	@Autowired
	public GiftToolService giftToolService;
	
	@Autowired
	private ActivityToolService activityToolService;

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("list")
	public String gift(HttpServletRequest request){
		return "gift/gift_list";
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
			map.put("total", giftToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<GiftDTO> list = giftToolService.getBySearch(map);
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
    public String queryByName(HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			String giftName=request.getParameter("giftName");
			if(null != giftName && !giftName.equals("")){
				map.put("giftName", giftName);
			}
			
			String actName=request.getParameter("actName");
			if(null != actName && !actName.equals("")){
				map.put("actName", actName);
			}
			
			map.put("total", giftToolService.getTotal(map));

			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<GiftDTO> list = giftToolService.getBySearch(map);
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
    public String save(GiftDTO giftDTO, HttpServletRequest request){      
		try {
			String id=request.getParameter("id");
			
			GiftEntity giftEntity = new GiftEntity();
			
			
			
			if(StringUtils.isNotEmpty(""+id)){
				
				giftDTO.setUpdateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
				giftToolService.update(giftDTO);
				
				
			}
			else{
				if(giftDTO.getType()==1 || giftDTO.getType()==2 || giftDTO.getType()==3){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", giftDTO.getName());
					map.put("activityId",giftDTO.getActivityId());
					int i=giftToolService.getCountByName(map);
					System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiii:"+i);
					if(i>0){
						return "error1";
					}
				}else if(giftDTO.getType()==4){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", giftDTO.getType());
					map.put("activityId",giftDTO.getActivityId());
					int j=giftToolService.getCountByType(map);
					System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjj:"+j);
					if(j>0){
						return "error2";
					}
				}
				
				giftEntity.setName(giftDTO.getName());
				giftEntity.setIntegral(giftDTO.getIntegral());
				giftEntity.setType(giftDTO.getType());

				giftEntity.setCreateTime(new Date());
				giftEntity.setCreateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
				giftEntity.setActivityId(giftDTO.getActivityId());
			
				giftToolService.add(giftEntity);
				
				
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
		return "gift/gift_edit";
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
			GiftDTO dto=giftToolService.getById(id);
			
			putModel("dto",dto);
			return "gift/gift_edit";
		} catch (Exception e) {
			
		}
		return null;
	}
	
	@RequestMapping("activitySelect")
	public String activitySelect() {
		return "gift/activitySelect";
	}
	
	/**
	 * 活动列表
	 * @param request
	 * @return
	 */
	@RequestMapping("queryActivity")
	@ResponseBody
	public Object queryActivity(ActivityQueryBean queryBean, HttpServletRequest request){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(StringUtils.isNotBlank(queryBean.getName())){
				map.put("name", queryBean.getName());
			}
			if(StringUtils.isNotBlank(queryBean.getQueryStartDate())){
				map.put("queryStartDate", queryBean.getQueryStartDate());
			}
			if(StringUtils.isNotBlank(queryBean.getQueryEndDate())){
				map.put("queryEndDate", queryBean.getQueryEndDate());
			}
			
			map.put("total", activityToolService.getTotal(map));
			
			setCommParameters(request, map);
			List<ActivityEntity> list = activityToolService.getByCondition(map);
			map.put("rows", list);
			
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			
		}
		return request;
	}
}
