package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdProPersonalAuthToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.JxlsExcelUtil;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
//import com.gudeng.commerce.gd.bi.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.bi.dto.GrdProPersonalAuthDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProPersonalAuthEntity;
import com.gudeng.commerce.gd.bi.util.ExcelUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Controller
public class GrdProPersonalAuthController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdProPersonalAuthController.class);

	/**
	 * 配置文件
	 */
	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	private GrdProPersonalAuthToolService grdProPersonalAuthToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProPersonalAuth/main")
	public String list(HttpServletRequest request) {
		return "grdProPersonalAuth/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProPersonalAuth/query")
	@ResponseBody
	public String query(HttpServletRequest request, String marketId, String teamId, String grdUserName, String grdMobile, 
			String status, String applyStartDate, String applyEndDate, String auditStartDate, String auditEndDate) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("grdUserName", grdUserName);
			map.put("grdMobile", grdMobile);
			map.put("status", status);
			map.put("applyStartDate", applyStartDate);
			map.put("applyEndDate", applyEndDate);
			map.put("auditStartDate", auditStartDate);
			map.put("auditEndDate", auditEndDate);
			// 记录数
			map.put("total", grdProPersonalAuthToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdProPersonalAuthDTO> list = grdProPersonalAuthToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}

	/**
	 * 保存数据（新增、修改）
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "grdProPersonalAuth/save" })
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, GrdProPersonalAuthEntity entity, GrdProPersonalAuthDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUserId(user.getUserID());
				dto.setUpdateTime(new Date());
				i = grdProPersonalAuthToolService.update(dto);
			} else {
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				i = grdProPersonalAuthToolService.insert(entity);
			}
			if (i > 0) {
				map.put("msg", "success");
			} else {
				map.put("msg", "保存失败");
			}
		} catch (Exception e) {
			map.put("msg", "保存失败");
			logger.trace("新增保存错误", e);
		}
		return JSONObject.toJSONString(map);
	}


	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProPersonalAuth/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "grdProPersonalAuth/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProPersonalAuth/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdProPersonalAuthDTO dto = grdProPersonalAuthToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("grdProPersonalAuth/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProPersonalAuth/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdProPersonalAuthDTO dto = grdProPersonalAuthToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("grdProPersonalAuth/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdProPersonalAuth/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (StringUtils.isEmpty(ids)) {
                map.put("msg", "未选中删除数据");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = grdProPersonalAuthToolService.deleteBatch(list);
				map.put("msg", "success");
            }
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "grdProPersonalAuth/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, String marketId, String teamId, String grdUserName, String grdMobile, 
			String status, String applyStartDate, String applyEndDate, String auditStartDate, String auditEndDate) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("grdUserName", grdUserName);
			map.put("grdMobile", grdMobile);
			map.put("status", status);
			map.put("applyStartDate", applyStartDate);
			map.put("applyEndDate", applyEndDate);
			map.put("auditStartDate", auditStartDate);
			map.put("auditEndDate", auditEndDate);
			int total = grdProPersonalAuthToolService.getTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("product checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * 导出Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdProPersonalAuth/exportData")
	public String exportData(HttpServletRequest request, String marketId, String teamId, String grdUserName, String grdMobile, 
			String status, String applyStartDate, String applyEndDate, String auditStartDate, String auditEndDate, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream out = null;
		try {
			// 设置查询参数
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("grdUserName", grdUserName);
			map.put("grdMobile", grdMobile);
			map.put("status", status);
			map.put("applyStartDate", applyStartDate);
			map.put("applyEndDate", applyEndDate);
			map.put("auditStartDate", auditStartDate);
			map.put("auditEndDate", auditEndDate);
		
			// 查询数据
			List<GrdProPersonalAuthDTO> list = grdProPersonalAuthToolService.getList(map);
			
			//获取模板文件路径
			String templatePath = gdProperties.getProperties().getProperty("GRDPROPERSONALAUTH_TEMPLETE");
			String srcFilePath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/" + templatePath);
	
			// 设置文件名和头信息
			String destFileName = new String("个人认证统计".getBytes(), "ISO8859-1") + RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls";			//目标文件名
			response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);	// 设置响应
			response.setContentType("application/vnd.ms-excel");
	
			// 设置列表数据
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("list", list);
	
			//获取输出流
			out = response.getOutputStream();
			JxlsExcelUtil.exportReportFromAbsolutePath(srcFilePath, beans, out);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
