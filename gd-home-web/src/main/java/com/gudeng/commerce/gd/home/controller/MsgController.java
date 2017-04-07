package com.gudeng.commerce.gd.home.controller;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.service.impl.PushRecordToolServiceImpl;
import com.gudeng.commerce.gd.home.util.ErrorCodeEnum;
import com.gudeng.commerce.gd.home.util.ObjectResult;
import com.gudeng.commerce.gd.home.util.PageUtil;


@Controller
@RequestMapping("userCenter/msg")
public class MsgController  extends HomeBaseController{
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MsgController.class);
	
	@Autowired
	private	PushRecordToolServiceImpl pushRecordToolServiceImpl;
	
	@SuppressWarnings({  "unchecked" })
	@RequestMapping("getList")
	public String  getList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//获取未读消息数
			Integer msgUnRead=pushRecordToolServiceImpl.getCount(0,getUser(request).getMemberId());
			putModel("msgUnRead",msgUnRead);
			PageDTO<PushRecordDTO> pageDto = PageUtil.create(PushRecordDTO.class, 5);
			//查询条件
			//获取数据库数据 并设置到pageDTO中
			//数据添加到model 前台准备显示 
			putPagedata(getList(getUser(request).getMemberId(), null));
		} catch (MalformedURLException e) {
			logger.error("获取推送记录失败",e);
		}catch (Exception e) {
			logger.error("获取推送记录失败",e);
		}
		return "usercenter/member/member_msg";
	}
	
	@RequestMapping("delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response ,PushRecordDTO pushRecordDTO) {
		List retList=new ArrayList<>();
		try {
			pushRecordToolServiceImpl.delete(pushRecordDTO.getId());
		} catch (MalformedURLException e) {
			logger.error("删除推送记录失败",e);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("batchDel")
	public void batchDel(HttpServletRequest request,
			HttpServletResponse response ,PushRecordDTO pushRecordDTO) {
		try {
			pushRecordToolServiceImpl.batchDel(pushRecordDTO.getIds());
		} catch (MalformedURLException e) {
			logger.error("删除推送记录失败",e);
		}
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("update")
	public String update(HttpServletRequest request,
			HttpServletResponse response ,PushRecordDTO pushRecordDTO) {
		Integer msgUnRead=0;
		List readList=new ArrayList<>();
		Map<String, Object> retMap=new HashMap<>();
		try {
			pushRecordToolServiceImpl.update(pushRecordDTO);
			msgUnRead=pushRecordToolServiceImpl.getCount(0,getUser(request).getMemberId());
			readList=getList(getUser(request).getMemberId(), 1).getPageData();
			retMap.put("msgUnRead", msgUnRead);
			retMap.put("readList", readList);
		} catch (MalformedURLException e) {
			logger.error("更新推送记录失败",e);
		} catch (Exception e) {
			logger.error("更新推送记录失败",e);
		}
		return JSONObject.toJSONString(retMap);
		
	}
	
	@ResponseBody
	@RequestMapping("getMsgCount")
	public String getMsgCount(HttpServletRequest request,
			HttpServletResponse response ,PushRecordDTO pushRecordDTO) {
		Integer msgUnRead=0;	
		Integer readCount=0;
		Map<String, Object> retMap=new HashMap<>();
		try {
			msgUnRead=pushRecordToolServiceImpl.getCount(0,getUser(request).getMemberId());
			readCount=pushRecordToolServiceImpl.getCount(1,getUser(request).getMemberId());
			retMap.put("msgUnRead", msgUnRead);
			retMap.put("readCount", readCount);
		} catch (MalformedURLException e) {
			logger.error("获取未读消息数异常",e);
		}catch (Exception e) {
			logger.error("获取未读消息数异常",e);}
		return JSONObject.toJSONString(retMap);
		
	}
	
	
	
	@SuppressWarnings({ "rawtypes",   "unchecked" })
	private PageDTO getList(Long memberId,Integer state){
		List retList=new ArrayList<>();
		PageDTO<PushRecordDTO> pageDto = PageUtil.create(PushRecordDTO.class, 5);
		Integer totalSize=0;
		try {
			totalSize = pushRecordToolServiceImpl.getCount(null,memberId);
			pageDto.setTotalSize(totalSize);
			//查询条件
			Map<String, Object> query = new HashMap<String, Object>();
			//获取总数 通过query提供的参数 此处有service层提供方法
			setCommParameters(pageDto, query);
			//获取数据库数据 并设置到pageDTO中
			query.put("memberId", memberId);
			query.put("state", state);
			retList=pushRecordToolServiceImpl.getList(query);
			pageDto.setPageData(retList);
		} catch (MalformedURLException e) {
			logger.error("获取消息数异常",e);
		}
		return pageDto;
	}
	
	
	
	
}
