package com.gudeng.commerce.gd.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.util.MapUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;

/**
 * 功能描述：省市区信息控制中心
 * 
 */
@Controller
@RequestMapping("area")
public class AreaController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaToolService areaToolService;
	
	@RequestMapping("/getAllProvince")
	public void getAllProvince(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try{
			List<AreaDTO> provinceList = areaToolService.getAllProvince();
			result.setObject(provinceList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取所有省份信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 2016-03-02
	 * 根据地域类型获取省份
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getAllProvinceByType")
	public void getAllProvinceByType(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		String areaType = request.getParameter("areaType");
		try{
			List<AreaDTO> provinceList = areaToolService.getAllProvinceByType(areaType);
			result.setObject(provinceList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取所有省份信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}	
	
	@RequestMapping("/getChild")
	public void getChild(HttpServletRequest request, HttpServletResponse response, AreaDTO inputAreaDTO) {
		ObjectResult result = new ObjectResult();
		String parentId = inputAreaDTO.getFather();
		String parentName = inputAreaDTO.getArea(); //根据名字来查找下级节点信息
		
		try{
			if(StringUtils.isNotBlank(parentName)){
				AreaDTO area = areaToolService.getByAreaName(parentName);
				if(area == null){
					setResult(result, ErrorCodeEnum.AREA_NAME_NOT_FOUND, request, response);
					return;
				}
				
				parentId = area.getAreaID();
			}
			
			if(StringUtils.isBlank(parentId)){
				setResult(result, ErrorCodeEnum.AREA_ID_IS_NULL, request, response);
				return;
			}
			
			List<AreaDTO> children = areaToolService.getChildrenByParentId(parentId);
			result.setObject(children);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取所有子节点信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	
	/**
	 * 获取area表中,城市级别的经纬度
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getJW")
	public void getJw(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		
	     URL url = new URL("http://api.map.baidu.com/geocoder?" + 1 + "=您的密钥" + 
	        		"&callback=renderReverse&location=" + 1
	                        + "," + 1 + "&output=json");
	        URLConnection connection = url.openConnection();
	        /**
	         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
	         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
	         */
	        connection.setDoOutput(true);
	        OutputStreamWriter out = new OutputStreamWriter(connection
	                .getOutputStream(), "utf-8");
//	        remember to clean up
	        out.flush();
	        out.close();
//	        一旦发送成功，用以下方法就可以得到服务器的回应：
	        String res;
	        InputStream l_urlStream;
	        l_urlStream = connection.getInputStream();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                l_urlStream,"UTF-8"));
	        StringBuilder sb = new StringBuilder("");
	        while ((res = in.readLine()) != null) {
	            sb.append(res.trim());
	        }
	        String str = sb.toString();
	        System.out.println(str);
	        Map<String,String> map = null;
	    if(StringUtils.isNotEmpty(str)) {
	      int addStart = str.indexOf("formatted_address\":");
	      int addEnd = str.indexOf("\",\"business");
	      if(addStart > 0 && addEnd > 0) {
	        String address = str.substring(addStart+20, addEnd);
	        map = new HashMap<String,String>();
	        map.put("address", address);
	       // return map;		
	      }
	    }
	    
	 //   return null;
		ObjectResult result = new ObjectResult();
		try{
			List<AreaDTO> provinceList = areaToolService.getAllProvince();
			if(provinceList!=null){
				
			}

		}catch(Exception e){
			logger.warn("[ERROR]获取所有省份信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getLngLat")
	public void getLngLat(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		  
		try{
			Map<String,String> map = null;
			List<AreaDTO> provinceList = areaToolService.getAllProvince();
            for (int i = 0; i < provinceList.size(); i++) {
            	
            	if(provinceList.get(i).getArea()!=null){
            		if("北京市".equals(provinceList.get(i).getArea())||"天津市".equals(provinceList.get(i).getArea())||"重庆市".equals(provinceList.get(i).getArea())||"上海市".contains(provinceList.get(i).getArea())){
            			map=MapUtil.testPost(provinceList.get(i).getArea());
            			map.put("areaID", provinceList.get(i).getAreaID());
            			areaToolService.setLngLat(map);
            		}else{
            			List<AreaDTO> children = areaToolService.getChildrenByParentId(provinceList.get(i).getAreaID());
            			
            			for (int j = 0; j < children.size(); j++) {
            				if(children.get(j).getArea()!=null){
            					if ("市辖区".equals(children.get(j).getArea()) || "县".equals(children.get(j).getArea())||"市".equals(children.get(j).getArea())){
            						
            					}else{
            						map=MapUtil.testPost(children.get(j).getArea());
            						map.put("areaID", children.get(j).getAreaID());
            						areaToolService.setLngLat(map);
            					}
            				}
            				
						}
            		}
            	}
            	
			}
            
		}catch(Exception e){
			logger.warn("[ERROR]获取所有省份信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
    
	
	@RequestMapping("/getFarth")
	public void getFarth(HttpServletRequest request, HttpServletResponse response, AreaDTO inputAreaDTO) {
		ObjectResult result = new ObjectResult();
		String parentId = "";
		String parentName = inputAreaDTO.getArea(); //根据名字来查找下级节点信息
		
		try{
			if(StringUtils.isNotBlank(parentName)){
				AreaDTO area = areaToolService.getByAreaName(parentName);
				if(area == null){
					setResult(result, ErrorCodeEnum.AREA_NAME_NOT_FOUND, request, response);
					return;
				}
				if("北京市".equals(parentName)||"天津市".equals(parentName)||"重庆市".equals(parentName)||"上海市".equals(parentName)){
					parentId= area.getAreaID();
				}else{
					parentId= area.getFather();
				}
				
			}
			
			if(StringUtils.isBlank(parentId)){
				setResult(result, ErrorCodeEnum.AREA_ID_IS_NULL, request, response);
				return;
			}
			
			AreaDTO children = areaToolService.getByAreaId(Long.valueOf(parentId));
			result.setObject(children);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取所有子节点信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
