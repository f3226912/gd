package com.gudeng.commerce.gd.admin.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 *功能描述：地区维护
 */
@Controller
@RequestMapping("area")
public class AreaController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(AreaController.class);
	
	public static final String PARAM_KEY_RETURN_MESSAGE = "returnMsg";
	
	@Autowired
	public AreaToolService areaToolService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request){
		String areaId = request.getParameter("areaId");
		try {
			putModel("tree", getAreaTreeStr(areaId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "area/arealist";
	}
	
	@RequestMapping("loc")
	public String showLoc(String areaId,HttpServletRequest request) {
		try {
			
			AreaDTO area = areaToolService.getArea(areaId);
			putModel("area", area);
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.getMessage();
		}
		
		return "area/areaLoc";
	
	}
	
	@RequestMapping("changeLoc")
	@ResponseBody
	public String changeLoc(AreaDTO area,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(); 
		try {
			
			result.put("code", areaToolService.updateArea(area));
			
			if(result.get("code").equals(1)) {
				result.put("msg", "成功");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.getMessage();
			result.put("msg", "error");
		}
		return JSONArray.toJSONString(result);
	
	}
	
	/**
	 * 新增地区
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public String saveArea(AreaDTO areaDTO,HttpServletRequest request){
		
		try {
			
				int pid = areaToolService.addArea(areaDTO);
				return String.valueOf(areaDTO.getAreaID());
			
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.getMessage();
			return "false";
		}
		
	}
	
	/**
	 * 更新地区
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit2")
	public String saveArea2(AreaDTO areaDTO,HttpServletRequest request){
		
		//如果主键ID为空则新增数据,否则更新数据
		try {
			
				
				areaToolService.updateArea(areaDTO);
				return "true";
			
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.getMessage();
			return "false";
		}
		
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public String delArea(AreaDTO areaDTO){
		 
		try {
		
		  String result = areaToolService.deleteArea(areaDTO.getAreaID());
		  if(null ==result ){
			  return "true";
		  }else{
			  return StringUtil.changeCharset(result, "utf-8");
		  }
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			return "false";
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private String getAreaTreeStr(String areaId){
		//所有分类树
		List<AreaDTO> areaList = null;
		try {
			areaList = areaToolService.geAreaByAreaId(areaId);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		String result = "";
		sb.append("[{id:\"-1\",pId:\"\",name:\"地区管理\",open:\"true\"},");
		if(null!=areaList && areaList.size()!=0){
			for(AreaDTO a : areaList){
				sb.append("{id:\""+a.getAreaID()+"\","
							+"pId:\""+StringUtils.defaultString(a.getFather(), "-1") +"\","
							+"name:\""+a.getArea()+"\"},"
						);
			}
		}
		result = sb.toString();
		if(result.length()!=1){
			result = result.substring(0,result.length()-1);
		}
		return result+"]";
	}
	
	
	@RequestMapping("showArea")
	public String showArea(String areaId,HttpServletRequest request) {
		try {
			if(StringUtil.isNotEmpty(areaId) && !"-1".equals(areaId)){
				AreaDTO area = areaToolService.getArea(areaId);
				putModel("area", area);
			}else{
				putModel("area", null);
			}

		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.getMessage();
		}
		return "area/areaShow";
	
	}

}
