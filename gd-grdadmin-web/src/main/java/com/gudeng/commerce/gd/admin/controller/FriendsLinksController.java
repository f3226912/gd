package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.FriendsLinksParamsBean;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.FriendsLinksToolService;
import com.gudeng.commerce.gd.admin.service.ProductToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class FriendsLinksController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(FriendsLinksController.class);
	
	@Autowired
	public FriendsLinksToolService friendsLinksToolService;
	
	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public FileUploadToolService fileUploadToolService;

	/**
	 * 跳转友情链接管理列表页
	 * 
	 * @return
	 */
	@RequestMapping("friendslinks/jumpToList")
	public String toList(HttpServletRequest request) {
		return "friendslinks/friendsLinksList";
	}
	
	/**
	 * 友情链接管理列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("friendslinks/list")
	public String list(HttpServletRequest request, FriendsLinksParamsBean params) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			if (StringUtil.isNotEmpty(params.getLinkCate())) {
				map.put("linkCate", params.getLinkCate());
			}
			if (StringUtil.isNotEmpty(params.getLinkType())) {
				map.put("linkType", params.getLinkType());
			}
			if (StringUtil.isNotEmpty(params.getLinkName())) {
				map.put("linkName", params.getLinkName());
			}
			if (StringUtil.isNotEmpty(params.getLinkUrl())) {
				map.put("linkUrl", params.getLinkUrl());
			}
			if (StringUtil.isNotEmpty(params.getStatus())) {
				map.put("status", params.getStatus());
			}

			// 记录数
			map.put("total", friendsLinksToolService.getCount(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<FriendsLinksDTO> list = friendsLinksToolService.getFriendsLinksList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加友情链接管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("friendslinks/addPage")
	public String addPage(HttpServletRequest request){
		return "friendslinks/friendsLinks_add";
	}
	
	/**
	 * 添加友情链接管理
	 * 
	 * @return
	 */
	
	@RequestMapping(value="friendslinks/add" )
	@ResponseBody
    public String add(FriendsLinksDTO friendsLinksDTO, HttpServletRequest request){      
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			friendsLinksDTO.setCreateUserId(regUser.getUserName());
			//savePicture(regUser.getUserID(), friendsLinksDTO.getLinkImage(), 1, 0L);
			friendsLinksToolService.add(friendsLinksDTO);			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "success";
	}
	
	/**
	 * 编辑友情链接管理页面
	 * 
	 * 
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="friendslinks/editbyid/{id}")
    public String editbyid(@PathVariable("id") String id, HttpServletRequest request){      
		try {
			FriendsLinksDTO dto=friendsLinksToolService.getById(id);
			
			putModel("dto",dto);
			return "friendslinks/friendsLinks_edit";
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 编辑友情链接管理
	 * 
	 * @return
	 */
	
	@RequestMapping(value="friendslinks/edit" )
	@ResponseBody
    public String save(FriendsLinksDTO friendsLinksDTO, HttpServletRequest request){      
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			friendsLinksDTO.setUpdateUserId(regUser.getUserName());
			friendsLinksToolService.update(friendsLinksDTO);				
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "success";
	}
	
	/**
	 * 删除某个友情链接
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("friendslinks/delete")
	public String delete(String id) {
		int result = 0;
		try {
			result = friendsLinksToolService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(result);
	}
	
	/**
	 * 根据多个ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="friendslinks/deletes" )
	@ResponseBody
	public String deletes(@RequestParam String id, HttpServletRequest request) {
		try {
			String[] ids = id.split(",");
			for(String ss : ids){
				friendsLinksToolService.delete(ss);
			}
			return "success";
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查看友情链接管理页面
	 * 
	 * 
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="friendslinks/viewbyid/{id}")
    public String viewbyid(@PathVariable("id") String id, HttpServletRequest request){      
		try {
			FriendsLinksDTO dto=friendsLinksToolService.getById(id);
			
			putModel("dto",dto);
			return "friendslinks/friendsLinks_view";
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 查看友情链接管理
	 * 
	 * @return
	 */
	
	@RequestMapping(value="friendslinks/view" )
	@ResponseBody
    public String view(FriendsLinksDTO friendsLinksDTO, HttpServletRequest request){      
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			friendsLinksDTO.setExamineUserId(regUser.getUserID());
			friendsLinksToolService.view(friendsLinksDTO);				
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("friendslinks/uploadProductPic")
	public String uploadProductPic(
			HttpServletRequest request,
			@RequestParam(value = "linkImage", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				String fileName = CommonUtil.generateSimpleFileName(file
						.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(
						file.getBytes(), fileName);
			} else {
				map.put("status", 0);
				map.put("message", "upload file failed!!");
				return JSONObject.toJSONString(map);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		}

		map.put("status", 1);
		map.put("message", "upload file success");
		map.put("url", masterPicPath);
		return JSONObject.toJSONString(map);
	}
}
