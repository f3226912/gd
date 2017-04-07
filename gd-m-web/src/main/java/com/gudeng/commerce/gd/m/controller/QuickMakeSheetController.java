package com.gudeng.commerce.gd.m.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.QuickSheetCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityUserproductEntity;
import com.gudeng.commerce.gd.m.controller.act.GDAPIBaseController;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.service.QuickMakeSheetService;
import com.gudeng.commerce.gd.m.util.GSONUtils;
import com.gudeng.commerce.gd.m.util.JSONUtils;
import com.gudeng.commerce.gd.m.util.ObjectResult;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("quickMakeSheet")
public class QuickMakeSheetController extends GDAPIBaseController {
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(QuickMakeSheetController.class);
	@Autowired
	private QuickMakeSheetService quickMakeSheetService;
	
	/**
	 * 跳转页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUrl")
	public void getUrl(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			if(StringUtils.hasLength(memberId)==false){
				result.setMsg("用户id不能为空！");
				renderEncodeJson(result, request, response, null,true);
				return;
			}
			Map<String, Object> map = new  HashMap<>();
			map.put("memberId", memberId);
			int count =quickMakeSheetService.getQuickMakeSheetCount(map);
			if(count>0){
				result.setObject("quickMakeSheet/getStandardLibraryProductList");
			}else{
				result.setObject("quickMakeSheet/getQuickMakeSheetList");
			}
			result.setMsg(SUCCESS);
			result.setStatusCode(0);
		} catch (Exception e) {
			logger.info(e.getMessage());
			result.setMsg(ERROR);
			e.printStackTrace();
			result.setStatusCode(-1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		renderEncodeJson(result, request, response, null,true);
	}
	/**
	 * 快速制单引导接口-分类列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getQuickMakeSheetList")
	public void getQuickMakeSheetList(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			List<QuickSheetCategoryDTO> list =quickMakeSheetService.getQuickMakeSheetList();
			result.setObject(list);
			result.setMsg(SUCCESS);
			result.setStatusCode(10000);
		} catch (Exception e) {
			logger.info(e.getMessage());
			result.setMsg(ERROR);
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		
		renderEncodeJson(result, request, response, null,true);
	}

	
	/**
	 * 标准库-产品列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getStandardLibraryProductList")
	public void getStandardLibraryProductList(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
	
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			if(StringUtils.hasLength(memberId)==false){
				result.setMsg("用户id不能为空！");
				renderEncodeJson(result, request, response, null,true);
				return;
			}
			Map<String, Object> map = new  HashMap<>();
			map.put("memberId", memberId);
			List<QuickSheetCategoryDTO>  res =quickMakeSheetService.getStandardLibraryProductList(map);
				result.setObject(res);
				result.setStatusCode(10000);
				result.setMsg(SUCCESS);
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
			return;
		}
		renderEncodeJson(result, request, response, null,true);
	}
	/**
	 * 快速制单-标准库添加产品
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addProduct")
	public void addProduct(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
		String jsonStr = getParamDecodeStr(request);
		//jsonStr="[{\"memberId\" : \"281730\",\"categoryId\" : \"1586\",\"productId\" : \"85215\"}]";
		JSONArray jsonArr = JSONUtils.parseArray(jsonStr);
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObject = (JSONObject)jsonArr.get(i);
			ActivityUserproductEntity au=JSONObject.toJavaObject(jsonObject, ActivityUserproductEntity.class);
		Map<String, Object> map = new  HashMap<>();
		if(au.getMemberId()==null){
			result.setMsg("memberId不能为空！");
			result.setStatusCode(-1);
			renderEncodeJson(result, request, response, null,true);
			return;
		}
		if(au.getCategoryId()==null){
			result.setMsg("categoryId不能为空！");
			result.setStatusCode(-1);
			renderEncodeJson(result, request, response, null,true);
			return;
		}
		if(au.getProductId()==null){
			result.setMsg("productId不能为空！");
			result.setStatusCode(-1);
			renderEncodeJson(result, request, response, null,true);
			return;
		}
		
		
		map.put("memberId", au.getMemberId());
		map.put("categoryId", au.getCategoryId());
		map.put("productId",au.getProductId());
		int count =quickMakeSheetService.productCount(map);
		if(count==0){
			quickMakeSheetService.addProduct(map);
		}
		result.setStatusCode(10000);
		}
		} catch (Exception e) {
			logger.info(e.getMessage());
			result.setMsg(ERROR);
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		renderEncodeJson(result, request, response, null,true);
	}
	/**
	 * 快速制单-标准库移除产品
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delProduct")
	public void delProduct(HttpServletRequest request,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
		Map<String, Object> map = new  HashMap<>();
		String jsonStr = getParamDecodeStr(request);
		String id = GSONUtils.getJsonValueStr(jsonStr, "id");
		if(StringUtils.hasLength(id)==false){
			result.setMsg("商品id不能为空！");
			renderEncodeJson(result, request, response, null,true);
			return;
		}
		String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
		if(StringUtils.hasLength(memberId)==false){
			result.setMsg("用户id不能为空！");
			renderEncodeJson(result, request, response, null,true);
			return;
		}
		map.put("id", id);
		map.put("memberId", memberId);
			int res =quickMakeSheetService.delProduct(map);
			if(res>0){
				result.setStatusCode(10000);
				result.setMsg(SUCCESS);
			}else{
				result.setStatusCode(0);
				result.setMsg(SUCCESS);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			result.setMsg(ERROR);
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		renderEncodeJson(result, request, response, null,true);
	}
}
