package com.gudeng.commerce.gd.home.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.service.FileUploadToolService;
import com.gudeng.commerce.gd.home.service.MarketToolService;
import com.gudeng.commerce.gd.home.service.MemberCerifiToolService;
import com.gudeng.commerce.gd.home.util.CommonUtil;
import com.gudeng.commerce.gd.home.util.DateUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("userCenter/attest")
public class AttestController extends HomeBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(UsercollectController.class);
	
	@Autowired
	public MarketToolService marketToolService;
	
	@Autowired
	public MemberCerifiToolService cerifiToolService;
	
	@Autowired
	public FileUploadToolService fileUploadToolService;
	
	/**
	 * 显示认证页面总控
	 * @return
	 */
	@RequestMapping("")
	public String showAttest() {
		try {
			
		/*
		 * 获得认证对象
		 */
		MemberCertifiDTO cerifi = cerifiToolService.getByUserId(getUser(getRequest()).getMemberId().toString());
		if(cerifi==null) {
			return "redirect:/userCenter/attest/per";
		}
		/*
		 * 判断是否认证
		 */
		if("1".equals(cerifi.getType())) {
			return "redirect:/userCenter/attest/per";
		} else {
			return "redirect:/userCenter/attest/ent";
		}
		}catch(Exception e) {
			logger.trace(e.getMessage());
		}
		return "redirect:/userCenter/attest/per";
	}
	
	/**
	 * 显示个人认证页面
	 * @return
	 */
	@RequestMapping("per")
	public String showPerAttest() {
		try {
			
			/*
			 * 获得认证对象
			 */
			MemberCertifiDTO cerifi = cerifiToolService.getByUserId(getUser(getRequest()).getMemberId().toString());
			
			//如果已经做了企业认证，不能再做个人
			if(cerifi!=null&&"2".equals(cerifi.getType())) {
				return "redirect:/userCenter/attest/ent";
			}
			
			/*
			 * 判断是否认证
			 */
			if(cerifi==null || !"1".equals(cerifi.getStatus())) {
				putModel("cerifiable", false);
			} else {
				putModel("cerifiable", true);
			}
			
			//添加到前端使用
			putModel("cerifi", cerifi);
			
			//获得农批市场数据
			putModel("markets", marketToolService.getAllByStatusAndType("0","2"));
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "usercenter/attest/attestpre";
	}
	
	/**
	 * 显示企业认证页面
	 * @return
	 */
	@RequestMapping("ent")
	public String showEntAttest() {
		try {
			
			/*
			 * 获得认证对象
			 */
			MemberCertifiDTO cerifi = cerifiToolService.getByUserId(getUser(getRequest()).getMemberId().toString());
			
			//如果已经做了个人认证，不能再做企业
			if(cerifi!=null&&"1".equals(cerifi.getType())) {
				return "redirect:/userCenter/attest/per";
			}
			
			/*
			 * 判断是否认证
			 */
			if(cerifi==null || !"1".equals(cerifi.getStatus())) {
				putModel("cerifiable", false);
			} else {
				putModel("cerifiable", true);
			}
			
			//添加到前端使用
			putModel("cerifi", cerifi);
			
			//获得农批市场数据
			putModel("markets", marketToolService.getAllByStatusAndType("0","2"));
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "usercenter/attest/attestent";
	}
	
	/**
	 * 修改个人认证页面
	 * @return
	 */
	@RequestMapping(value="addPer", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addPerAttest(MemberCertifiDTO certifiDTO) {
		ModelMap retMap = new ModelMap();		
		try {
			
			MemberBaseinfoEntity user = getUser(getRequest());
			//获得认证对象
			MemberCertifiDTO cerifi = cerifiToolService.getByUserId(user.getMemberId().toString());
			//String nowTimeString=DateUtil.toString(new Date(),DateUtil.DATE_FORMAT_DATETIME);
			/*
			 * 添加要修改的数据的基本数据
			 */
			certifiDTO.setStatus("0");
			certifiDTO.setCommitTime(new Date());
			
			/*
			 * 判断是否存在验证对象
			 * 存在则修改
			 * 不存在添加
			 */
			if (cerifi==null) {
				certifiDTO.setAccount(user.getAccount());
				certifiDTO.setMobile(user.getMobile());
				certifiDTO.setMemberId(user.getMemberId());
				certifiDTO.setStatus("0");
				certifiDTO.setType("1");
				cerifiToolService.addMemberCertifiDTO(certifiDTO);
			} else {
				certifiDTO.setCertifiId(cerifi.getCertifiId());
				cerifiToolService.updateMemberCertifiDTO(certifiDTO);
			}
			
			retMap.put("msg", "已提交验证");
			retMap.put("status", 1);
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			retMap.put("msg", e.getMessage());
			retMap.put("status", 0);
		}
		
		return JSONObject.toJSONString(retMap,SerializerFeature.WriteDateUseDateFormat);
		//"redirect:/userCenter/attest/per";
	}
	
	/**
	 * 修企业认证页面
	 * @return
	 */
	@RequestMapping(value="addEnt", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addEntAttest(MemberCertifiDTO certifiDTO) {
		ModelMap retMap = new ModelMap();		
		try {
			MemberBaseinfoEntity user = getUser(getRequest());
			
			//获得认证对象
			MemberCertifiDTO cerifi = cerifiToolService.getByUserId(user.getMemberId().toString());

			/*
			 * 如果已经设置企业名，不能修改
			 * if(cerifi!=null&&cerifi.getCompanyName()!=null) {
				certifiDTO.setCompanyName(null);
				}
			 */
			
			//String nowTimeString=DateUtil.toString(new Date(),DateUtil.DATE_FORMAT_DATETIME);
			/*
			 * 添加要修改的数据
			 */
			certifiDTO.setStatus("0");
			certifiDTO.setCommitTime(new Date());
			
			/*
			 * 判断是否存在验证对象
			 * 存在则修改
			 * 不存在添加
			 */
			if(cerifi == null) {
				certifiDTO.setAccount(user.getAccount());
				certifiDTO.setMobile(user.getMobile());
				certifiDTO.setMemberId(user.getMemberId());
				certifiDTO.setType("2");
				cerifiToolService.addMemberCertifiDTO(certifiDTO);
			} else {
				certifiDTO.setCertifiId(cerifi.getCertifiId());
				cerifiToolService.updateMemberCertifiDTO(certifiDTO);
			}
			retMap.put("msg", "已提交验证");
			retMap.put("status", 1);
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			retMap.put("msg", e.getMessage());
			retMap.put("status", 0);
		}
		return JSONObject.toJSONString(retMap,SerializerFeature.WriteDateUseDateFormat);
		//return "redirect:/userCenter/attest/ent";
	}

	@ResponseBody
	@RequestMapping("uploadAttestPic")
	public String uploadAttestPic(
			HttpServletRequest request,
			@RequestParam(value = "attestPicture", required = false) MultipartFile file) {
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
