package com.gudeng.commerce.gd.admin.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 */
@Controller
@RequestMapping("areaSetting")
public class AreaSettingController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(AreaSettingController.class);

	@Autowired
	public AreaSettingToolService areaSettingToolService;

	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;

	/**
	 * 列表页面
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String areaSetting(HttpServletRequest request) {
		return "areaSetting/areaSettingList";
	}

	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("areaName", request.getParameter("queryName"));
			map.put("mobile",  request.getParameter("queryMobile"));
			// 记录数
			map.put("total", areaSettingToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<AreaSettingDTO> list = areaSettingToolService.getByCondition(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "deletebyid")
	@ResponseBody
	public String deleteById(@RequestParam String id, HttpServletRequest request) {
		try {
			if (null == id || id.equals("")) {
				return "faild";
			}
			areaSettingToolService.deleteById(id);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request) {
		return "areaSetting/addAreaSetting";
	}

	/**
	 * 增加修改同一个方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request , AreaSettingDTO dto) {
		try {

			String id = request.getParameter("id");
			AreaSettingDTO areaSettingDTO = areaSettingToolService.getById(id);
			MemberBaseinfoDTO mb =memberBaseinfoToolService.getByMobile(dto.getMobile());
			if(mb != null){
			dto.setMemberId(mb.getMemberId());
			}else{
			  return  "not exist";
			}
			int i = 0;
			// 根据id判断是否存在，存在即为更新，不存在即为增加
			if (areaSettingDTO != null) {
				dto.setId(!StringUtils.isEmpty(id) ? Long.parseLong(id) : 0L);
				i = areaSettingToolService.updateAreaSettingDTO(dto);
			} else {
				
			    i = areaSettingToolService.addAreaSettingDTO(dto);
			}
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.debug("Exception is :"+e);
			e.printStackTrace();
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
	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request) {
		try {

			String id = request.getParameter("id");
			AreaSettingDTO dto = areaSettingToolService.getById(id);
			putModel("id", dto.getId());
			putModel("areaName", dto.getAreaName());
			putModel("mobile", dto.getMobile());
			return "areaSetting/addAreaSetting";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 打开导入对话框
	 * @return
	 */
	@RequestMapping("toImportData")
	public ModelAndView toImportData() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("areaSetting/importData");
		return mv;
	}

	
    /**
     * 批量导入
     * @param request
     * @param file
     * @return
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "importData")
	@ResponseBody
	public String importData(HttpServletRequest request, @RequestParam(value = "datafile", required = false) MultipartFile file) {
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			try {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile fileTemp = multiRequest.getFile((String) iter.next());
					if (fileTemp != null) {
						try {
							/****** 导入文件数据到数据库中 *******/
							InputStream input = fileTemp.getInputStream(); // 建立输入流
							Workbook wb = Workbook.getWorkbook(input);
							int numberOfSheets = wb.getNumberOfSheets();// 表单个数
							// // 遍历各表单
							for (int i = 0; i < numberOfSheets; i++) {
								List<AreaSettingDTO> list = new ArrayList<AreaSettingDTO>();
								Sheet sheet = wb.getSheet(i);// 当前表单
								// 遍历表中的行，0行为表头，不遍历，从1开始
								for (int j = 1; j < sheet.getRows(); j++) {
									Cell[] row = sheet.getRow(j);// 获取行对象
									if (row == null) {// 如果为空，不处理
										continue;
									}
									// 每一行为一个对象，创建对象
									AreaSettingDTO areaSetting = new AreaSettingDTO();
									if (StringUtils.isNotEmpty(row[0].getContents())) {
										areaSetting.setAreaName(row[0].getContents().trim());// 获取第一列数据，区域名称
									} else {
										break;
									}
									if (StringUtils.isNotEmpty(row[1].getContents())) {
										areaSetting.setMobile(row[1].getContents().trim());// 获取第二列数据，手机号码
										//查询会员ID
										MemberBaseinfoDTO mb =memberBaseinfoToolService.getByMobile(row[1].getContents().trim());
										if(mb != null){
										 areaSetting.setMemberId(mb.getMemberId());//
										}else{
											break;
										}
									} else {
										break;
									}
									list.add(areaSetting);
								}
								if (list.size() > 0) {
									areaSettingToolService.batchAddAreaSetting(list);
								}
							}
							wb.close();
						} catch (Exception e) {
							e.printStackTrace();
							return "false";
						} finally {
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "false";
			}
		}
		return "success";
	}

}
