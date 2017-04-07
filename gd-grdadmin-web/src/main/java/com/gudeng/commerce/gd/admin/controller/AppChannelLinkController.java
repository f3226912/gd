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
import com.gudeng.commerce.gd.admin.service.AppChannelLinkToolService;
import com.gudeng.commerce.gd.admin.service.SystemCodeToolService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AppChannelLinkDTO;
import com.gudeng.commerce.gd.customer.entity.AppChannelLink;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Controller
public class AppChannelLinkController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(AppChannelLinkController.class);

	@Autowired
	private AppChannelLinkToolService appChannelLinkToolService;

	@Autowired
	private SystemCodeToolService systemCodeService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("appChannelLink/main")
	public String list(HttpServletRequest request) {
		return "appChannelLink/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("appChannelLink/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String channelName = request.getParameter("channelName");
			map.put("channelName", channelName);
			map.put("state", 1);
			// 设置查询参数
			// 记录数
			map.put("total", appChannelLinkToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<AppChannelLinkDTO> list = appChannelLinkToolService.getListPage(map);
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
	@RequestMapping(value = { "appChannelLink/save" })
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, AppChannelLink entity, AppChannelLinkDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("channelName", dto.getChannelName());
			map.put("state", 1);
			map.put("clientChannel", dto.getClientChannel());
			List<AppChannelLinkDTO> list = appChannelLinkToolService.getList(map);
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				if (list != null && list.size() > 0) {
					for (AppChannelLinkDTO appChannelLinkDTO : list) {
						if (!dto.getId().equals(appChannelLinkDTO.getId())) {
							if (appChannelLinkDTO.getClientChannel().equals(dto.getClientChannel())) {
								map.put("msg", "当前客户端下已存在该渠道，请更换渠道名称");
								return map;
							}
						}
					}
				}
				dto.setUpdateUserId(user.getUserID());
				i = appChannelLinkToolService.update(dto);
			} else {
				if (list != null && list.size() > 0) {
					for (AppChannelLinkDTO appChannelLinkDTO : list) {
						if (appChannelLinkDTO.getClientChannel().equals(dto.getClientChannel())) {
							map.put("msg", "当前客户端下已存在该渠道，请更换渠道名称");
							return map;
						}
					}
				}
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				entity.setState(1);
				i = appChannelLinkToolService.insert(entity);
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
		return map;
	}

	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("appChannelLink/beforeSave")
	public String addDto() {
		return "appChannelLink/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("appChannelLink/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			AppChannelLinkDTO dto = appChannelLinkToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("appChannelLink/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("appChannelLink/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			AppChannelLinkDTO dto = appChannelLinkToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("appChannelLink/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "appChannelLink/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中删除数据");
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				int i = appChannelLinkToolService.deleteBatch(list, getUser(request).getUserID());
				map.put("msg", "success");
			}
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return map;
	}

	/**
	 * 获取客户端名称
	 * 
	 * @return
	 * @author lidong
	 * @time 2016年8月19日 上午11:01:50
	 */
	@RequestMapping(value = "appChannelLink/clientChannel")
	@ResponseBody
	public String clientChannel() {
		List<SystemCode> listViaType = null;
		try {
			listViaType = systemCodeService.getListViaType("clientChannel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(listViaType);
	}

	/**
	 * 获取平台信息
	 * 
	 * @return
	 * @author lidong
	 * @time 2016年8月19日 上午11:08:33
	 */
	@RequestMapping(value = "appChannelLink/platform")
	@ResponseBody
	public String platform() {
		List<SystemCode> listViaType = null;
		try {
			listViaType = systemCodeService.getListViaType("platform");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(listViaType);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "appChannelLink/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelName", request.getParameter("channelName"));
			map.put("state", 1);
			int total = appChannelLinkToolService.getTotal(map);
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
	@RequestMapping(value = "appChannelLink/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			map.put("channelName", request.getParameter("channelName"));
			map.put("state", 1);
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("app渠道链接.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<AppChannelLinkDTO> list = appChannelLinkToolService.getList(map);
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, AppChannelLinkDTO.class);
			workBook.write(ouputStream);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
