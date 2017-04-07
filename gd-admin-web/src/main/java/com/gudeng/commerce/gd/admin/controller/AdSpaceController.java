package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AdMenuToolService;
import com.gudeng.commerce.gd.admin.service.AdSpaceToolService;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AdMenuDTO;
import com.gudeng.commerce.gd.customer.dto.AdSpaceDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 广告位
 * @author wind
 *
 */
@Controller
@RequestMapping("adSpace")
public class AdSpaceController extends AdminBaseController{

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(AdSpaceController.class);

	@Autowired
	private MarketManageService marketManageService;
	
	@Autowired
	private FileUploadToolService fileUploadToolService;
	
	@Autowired
	private AdSpaceToolService adSpaceToolService;
	
	@Autowired
	private AdMenuToolService adMenuToolService;
	
	@RequestMapping("list")
	public String list(){
		return "adSpace/adSpace_list";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			resultMap.put("total", adSpaceToolService.countByCondition(paramsMap));
			List<AdSpaceDTO> list = adSpaceToolService.queryByCondition(paramsMap);
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.warn("查询广告位列表异常", e);
		}
		return JSONObject.toJSONString(resultMap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	@RequestMapping("add")
	public String add(@RequestParam Long menuId, ModelMap modelMap){
		try{
			AdMenuDTO dto = adMenuToolService.getById(menuId);
			modelMap.put("menu", dto);
			modelMap.put("menuId", menuId);
		}catch(Exception e){
			logger.warn("新增广告位获取菜单信息异常", e);
		}
		return "adSpace/adSpace_add";
	}
	
	@RequestMapping(value="saveAdd",produces="application/json; charset=utf-8")
	@ResponseBody
	public String saveAdd(AdSpaceEntity entity){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			boolean exist = adSpaceToolService.isExist(entity.getMenuId(), entity.getSpaceSign());
			if(exist){
				resultMap.put("status",-1);
				resultMap.put("message", "该广告位已存在");
				return JSONObject.toJSONString(resultMap);
			}
			
			SysRegisterUser sysUser = getUser(request);
			if(sysUser != null){
				entity.setCreateUserId(sysUser.getUserID());
				entity.setCreateUserName(sysUser.getUserName());
			}
			entity.setState("1");
			entity.setCreateTime(new Date());
			Long id = adSpaceToolService.insert(entity);
			entity.setId(id);
			
			resultMap.put("status",0);
			resultMap.put("message", "success");
			resultMap.put("obj", entity);
		}catch(Exception e){
			logger.warn("广告位新增异常", e);
			resultMap.put("status",-1);
			resultMap.put("message", "新增失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("edit")
	public String edit(@RequestParam Long id, ModelMap modelMap){
		try{
			AdSpaceDTO adSpaceDTO = adSpaceToolService.getById(id);
			modelMap.put("adSpaceDTO", adSpaceDTO);
		}catch(Exception e){
			logger.warn("编辑广告位获取信息异常", e);
		}
		return "adSpace/adSpace_edit";
	}
	
	@RequestMapping(value="saveEdit",produces="application/json;charset=utf-8")
	@ResponseBody
	public String saveEdit(AdSpaceEntity entity){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			AdSpaceDTO dto = adSpaceToolService.getById(entity.getId());
			if(dto == null){
				resultMap.put("status",-1);
				resultMap.put("message", "编辑记录不存在");
				return JSONObject.toJSONString(resultMap);
			}
			
			if(!dto.getSpaceSign().equals(entity.getSpaceSign())){
				boolean exist = adSpaceToolService.isExist(entity.getMenuId(), entity.getSpaceSign());
				if(exist){
					resultMap.put("status",-1);
					resultMap.put("message", "该广告位已存在");
					return JSONObject.toJSONString(resultMap);
				}
			}
			
			SysRegisterUser sysUser = getUser(request);
			if(sysUser != null){
				entity.setUpdateUserId(sysUser.getUserID());
				entity.setUpdateUserName(sysUser.getUserName());
			}
			adSpaceToolService.update(entity);
			resultMap.put("status",0);
			resultMap.put("message", "success");
			resultMap.put("entity", entity);
		}catch(Exception e){
			logger.warn("编辑广告位异常", e);
			resultMap.put("status",-1);
			resultMap.put("message", "编辑失败");
		}
		return JSONObject.toJSONString(resultMap); 
	}
	
	/**
	 *  上传图片
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadPic")
	public String uploadPic(HttpServletRequest request, @RequestParam(value = "adPicture", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()){
				String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(file.getBytes(), fileName);
			}else {
				map.put("status", 0);
				map.put("message", "upload file failed!!");
				return JSONObject.toJSONString(map);
			}
		} catch (IllegalStateException e) {
			logger.warn("上传广告位图片异常", e);
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (IOException e) {
			logger.warn("上传广告位图片异常", e);
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (Exception e) {
			logger.warn("上传广告位图片异常", e);
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		}
		map.put("status", 1);
		map.put("message", masterPicPath);
		map.put("url", masterPicPath);
		return JSONObject.toJSONString(map);
	}
	
	@RequestMapping(value="queryMarket", produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryMarket(HttpServletRequest request){
		String adCanal = request.getParameter("adCanal");
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		try{
			List<String> types = new ArrayList<String>();
			
			List<MarketDTO> list = null;
			switch (adCanal) {
			case "1":
				list = marketManageService.getAllByTypes(types);
				break;
			
			case "2":
				types.add("1");
				types.add("2");
				types.add("3");
				list = marketManageService.getAllByTypes(types);
				break;
				
			case "3":
				types.add("0");
				list = marketManageService.getAllByTypes(types);
				break;
				
			default:
				break;
			}
			resultMap.put("rows", list);
			resultMap.put("status", 0);
			resultMap.put("message", "success");
		}catch(Exception e){
			logger.warn("新增广告位信息时获取市场列表异常", e);
			resultMap.put("status", -1);
			resultMap.put("message", "新增广告位信息时获取市场列表异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value="delete", produces="application/json;charset=utf-8")
	@ResponseBody
	public String delete(@RequestParam Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			boolean canDelete = adSpaceToolService.canDelete(id);
			if(!canDelete){
				resultMap.put("status", -1);
				resultMap.put("message", "目前有广告正在发布中，不允许删除广告");
				return JSONObject.toJSONString(resultMap);
			}
			String updateUserId = null;
			String updateUserName = null;
			SysRegisterUser user = getUser(request);
			if(user != null){
				updateUserId = user.getUserID();
				updateUserName = user.getUserName();
			}
			adSpaceToolService.deleteAdSpace(id, updateUserId, updateUserName);
			resultMap.put("status", 0);
			resultMap.put("message", "success");
		}catch(Exception e){
			logger.warn("删除广告位异常", e);
			resultMap.put("status", -1);
			resultMap.put("message", "删除广告位异常");
		}
		return JSONObject.toJSONString(resultMap); 
	}
	
	@RequestMapping("view")
	public String view(@RequestParam Long id, ModelMap modelMap){
		try{
			AdSpaceDTO adSpaceDTO = adSpaceToolService.getById(id);
			modelMap.put("adSpaceDTO", adSpaceDTO);
		}catch(Exception e){
			logger.warn("查看广告位获取信息异常", e);
		}
		return "adSpace/adSpace_view";
	}
}
