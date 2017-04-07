package com.gudeng.commerce.gd.home.controller;

import java.io.IOException;
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
import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.service.FileUploadToolService;
import com.gudeng.commerce.gd.home.service.FriendsLinksToolService;
import com.gudeng.commerce.gd.home.util.CommonUtil;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class AboutUsController extends HomeBaseController {
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(StoreController.class);
	@Autowired
	public FriendsLinksToolService friendsLinksToolService;
	
	
	@Autowired
	public FileUploadToolService fileUploadToolService;
	@RequestMapping("about/index")
	public String aboutUs() {
		return "aboutus/aboutus";
	}
	
	@RequestMapping("about/contactus")
	public String contactus() {
		return "aboutus/contactus";
	}
	
	@RequestMapping("service/index")
	public String servicecenter() {
		return "aboutus/servicecenter";
	}
	
	@RequestMapping("about/marketlink")
	public String marketlink() {
		//合作媒体
		
		putModel("mediaLinksList",viewMediaLinksAll());
		//友情链接
		putModel("friendsLinksList",viewfriendsAll());
		return "aboutus/marketlink";
	}
	
	

	@RequestMapping("nsy")
	public String app() {
		return "aboutus/app";
	}
	
	@RequestMapping("nst")
	public String app2() {
		return "aboutus/app2";
	}

	@RequestMapping("finance/index")
	public String financial() {
		return "aboutus/marketfinancial";
	}
	
	@RequestMapping("legalstatement")
	public String legalstatement() {
		return "aboutus/legalstatement";
	}
	
	@RequestMapping("nopage")
	public String noPage() {
		return "404";
	}
	
	@ResponseBody
    public List<FriendsLinksDTO> viewfriendsAll(){ 
		List<FriendsLinksDTO> friendsLinksList=null;
		try {
			friendsLinksList=friendsLinksToolService.viewFriendsAll();
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("viewfriendsAll error"+e.getMessage());
		}
		return friendsLinksList;
	}
	
	
	 public List<FriendsLinksDTO> viewMediaLinksAll(){ 
			List<FriendsLinksDTO> friendsLinksList=null;
			try {
				friendsLinksList=friendsLinksToolService.viewMediaLinksAll();
			
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("viewfriendsAll error"+e.getMessage());
			}
			return friendsLinksList;
		}
	 @RequestMapping("about/applyFriendsUrl")
		public String applyFriendsUrl(HttpServletRequest request,ModelMap map){
		 FriendsLinksDTO friendsLinks = new FriendsLinksDTO();
		 String linkCate = request.getParameter("linkCate");
		 String linkType = request.getParameter("linkType");
		 String linkName = request.getParameter("linkName");
		 String linkUrl = request.getParameter("linkUrl");
		 String linkImage = request.getParameter("linkImagehi");
		 String description = request.getParameter("description");
		 friendsLinks.setLinkCate(linkCate);
		 friendsLinks.setLinkType(linkType);
		 friendsLinks.setLinkName(linkName);
		 friendsLinks.setLinkUrl(linkUrl);
		 friendsLinks.setDescription(description);
		 friendsLinks.setLinkImage(linkImage);
		 friendsLinks.setStatus("2");//默认状态待审核
		 try {
			int res =friendsLinksToolService.applyFriendsUrl(friendsLinks);
			if(res==1){
				//成功
				return "pub/success";
			}else{
				//失败
				return "404";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		}
	 
	 @ResponseBody
		@RequestMapping("about/uploadProductPic")
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
