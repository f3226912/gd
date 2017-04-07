package com.gudeng.commerce.gd.admin.controller;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.DemoToolService;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.commerce.gd.admin.util.DateUtil;

@Controller
@RequestMapping("demo")
public class DemoController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	public DemoToolService demoToolService;

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("")
	public String demo(HttpServletRequest request){
		return "demo/demo";
	}
	/**
	 * 分页查询
	 * @param request
	 * @return
	 */
	
	
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			String id=request.getParameter("id");
            
			Map<String, Object> map = new HashMap<String, Object>();
			if(null==id || id.equals("")){
//				map.put("id", "1");
			}else{
				map.put("id", id);
			}
			// 设置查询参数
			//记录数
			map.put("total", demoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<DictDTO> list = demoToolService.getByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			
		}
		return null;
	}
	

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybyid" )
	@ResponseBody
    public String queryById(@RequestParam String id, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(null==id ){
				id="1";
			}
			// 设置查询参数
			map.put("id", id);
			//记录数
			map.put("total", demoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<DictDTO> list = demoToolService.getByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			System.out.println( "returnStr>>>>>>>"+returnStr); 
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	

	/**
	 * 根据name查询
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybyname" )
	@ResponseBody
    public String queryByName(@RequestParam String name,@RequestParam String startDate,@RequestParam String endDate, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(null==name ){
				name="";
			}
			// 设置查询参数
			map.put("name", name);
			map.put("startdate", startDate);
			map.put("enddate", endDate);
			//记录数
			map.put("total", "10");
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<DictDTO> list = demoToolService.getByName(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	
	
	

	/**
	 * 	  根据birthday查询
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybybirthday" )
	@ResponseBody
    public String queryByBirthday(@RequestParam String startDate,@RequestParam String endDate, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Date start = null,end = null;
			if(null==startDate || startDate.equals("") ){
				start=DateUtil.formateDate("1970/01/01");
			}else{
				start=DateUtil.formateDate(startDate);
			}
			if(null==endDate || endDate.equals("") ){
				end=DateUtil.getNow();
			}else{
				end=DateUtil.formateDate(endDate);
			}
			map.put("startdate", start);
			map.put("enddate", end);
			setCommParameters(request, map);

			
			List<DictDTO> list = demoToolService.getByBirthday(map);
			map.put("rows", list);
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deletebyid" )
	@ResponseBody
    public String deleteById(@RequestParam String id, HttpServletRequest request){      
		try {
			if(null==id || id.equals("")){
			return "faild";
			}
			int i=demoToolService.deleteById(id);
			String returnStr="success";
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}

	
	/**
	 * 增加修改同一个页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request){
		return "demo/addDto";
	}
	
	
	
	/**
	 * 	  增加修改同一个方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save" )
	@ResponseBody
    public String saveOrUpdate( HttpServletRequest request){      
		try {
			DictDTO dto=new DictDTO();
			String id=request.getParameter("id");
			if(null==id ||id.equals("") ){
				return "failed";
			}
			dto.setId(id);
			dto.setName(request.getParameter("name"));
			Map map=new HashMap();
			map.put("id", id);
			Date birthday=DateUtil.formateDate(request.getParameter("birthday"));
			dto.setBirthday(birthday);
			//在DTO类中，加上一个String类型的birthday，以此实现mybaties保存或者更新到mysql时，Date类型的数据无法insert的问题。
			dto.setBirthday_string(request.getParameter("birthday"));
			int count= demoToolService.getTotal(map);
			int i=0;
			if(count>0){//根据id判断是否存在，存在即为更新，不存在即为增加
				i=demoToolService.updateDictDTO(dto);
			}else{
				i=demoToolService.addDictDTO(dto);
			}
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit" )
	@ResponseBody
    public String edit( HttpServletRequest request) {     
		try {
			String id=request.getParameter("id");
			DictDTO dto=demoToolService.getById(id);
			putModel("id",dto.getId());
			putModel("name",dto.getName());
			String birthday=DateUtil.toString(dto.getBirthday());
			putModel("birthday",birthday);
			return "demo/addDto";
		} catch (Exception e) {
			
		}
		return null;
	}
	
	
	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("editbyid")
	public String editbyid(HttpServletRequest request){
		try {
			String id=request.getParameter("id");
			DictDTO dto=demoToolService.getById(id);
			putModel("id",dto.getId());
			putModel("name",dto.getName());
			String birthday=DateUtil.toString(dto.getBirthday());
			putModel("birthday",birthday);
			return "demo/addDto";
		} catch (Exception e) {
			
		}
		return null;
	}
	
}
