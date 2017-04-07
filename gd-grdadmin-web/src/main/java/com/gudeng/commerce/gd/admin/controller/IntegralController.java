package com.gudeng.commerce.gd.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.ActivityQueryBean;
import com.gudeng.commerce.gd.admin.dto.IntegralQueryBean;
import com.gudeng.commerce.gd.admin.service.ActivityToolService;
import com.gudeng.commerce.gd.admin.service.IntegralToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityEntity;
import com.gudeng.commerce.gd.customer.entity.IntegralEntity;

@Controller
@RequestMapping("integral")
public class IntegralController extends AdminBaseController{

	/** 分页参数：起始页 */
	protected final String START_ROW = "startRow";
	/** 分页参数：结束页 */
	protected final String END_ROW = "endRow";
	/** 总记录数 */
	protected final String ALL_RECORDERS = "allRecorders";
	/** 总页数 */
	protected final String PAGE_COUNT = "pageCount";
	/** 当前页 */
	protected final String PAGE_NO = "pageNo";
	/** 每页数据条数 */
	protected final String PAGE_SIZE = "pageSize";
	
	@Autowired
	private IntegralToolService integralToolService;
	
	@Autowired
	private ActivityToolService activityToolService;
	
	@Autowired
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	
	/**
	 * 进入列表页面
	 * @return
	 */
	@RequestMapping("list")
	public String index(HttpServletRequest request){
		return "integral/integralList";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public Object query(IntegralQueryBean queryBean, HttpServletRequest request){
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(queryBean.getActivityId() != null){
				map.put("activityId", queryBean.getActivityId());
			}
			if(StringUtils.isNotBlank(queryBean.getMemberAccount())){
				map.put("memberAccount", queryBean.getMemberAccount());
			}
			if(queryBean.getUserType() != null){
				map.put("userType", queryBean.getUserType());
			}
			if(queryBean.getType() != null){
				map.put("type", queryBean.getType());
			}
			if(StringUtils.isNotBlank(queryBean.getQueryStartDate())){
				map.put("queryStartDate", queryBean.getQueryStartDate());
			}
			if(StringUtils.isNotBlank(queryBean.getQueryEndDate())){
				map.put("queryEndDate", queryBean.getQueryEndDate());
			}
			
			map.put("total", integralToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			
			List<IntegralDTO> list = integralToolService.getByCondition(map);
			
			for(IntegralDTO dto : list){
				if(StringUtils.isNotBlank(dto.getCreateUserId())){
					SysRegisterUser createUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
					if(createUser != null){
						dto.setCreateUserAccount(createUser.getUserName());
					}
				}
			}
			
			map.put("rows", list);//rows键 存放每页记录 list 
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			
		}
		return null;
	}
	
	/**
	 * 进入活动选择页
	 * @return
	 */
	@RequestMapping("activitySelect")
	public String activitySelect(){
		return "integral/activitySelect";
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
	
	/**
	 * 积分回退
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("return")
	@ResponseBody
	public String integralReturn(@RequestParam Long id, HttpServletRequest request){
		
		try {
			IntegralEntity integralEntity = integralToolService.getIntegralEntityById(id);
			if(integralEntity != null){
				
				if(integralEntity.getType() != 2){
					//只能对积分兑换类型的积分记录进行积分回退
					return "typeErr";
				}
				
				if(integralEntity.getIsReturn() != null && integralEntity.getIsReturn() == 1){
					//该记录已经进行过回退操作
					return "isReturnErr";
				}
				
				int resultNum = integralToolService.updateMemberIntegral(integralEntity.getMemberId(), integralEntity.getIntegral());
				if(resultNum > 0){
					IntegralEntity entity = new IntegralEntity();
					entity.setType(4);//管理员管理积分
					entity.setMemberId(integralEntity.getMemberId());
					entity.setIntegral(integralEntity.getIntegral() == null ? 0 : Math.abs(integralEntity.getIntegral()));
					entity.setDescription("管理员对错误积分兑换记录进行回退操作");
					//创建人
					SysRegisterUser registerUser = getUser(request);
					if(registerUser != null){
						entity.setCreateUserId(registerUser.getUserID());
					}
					integralToolService.addIntegral(entity);
					integralToolService.updateIntegralIsReturn(integralEntity.getId(), 1, registerUser.getUserID());
				}
			}
			return "success";
		} catch (Exception e) {
		}
		return null;
	}
}
