package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

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
import com.gudeng.commerce.gd.admin.service.GrdProCallstatisticsToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.bi.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.bi.dto.GrdProCallstatisticsDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProCallstatisticsEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Controller
public class GrdProCallstatisticsController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdProCallstatisticsController.class);

	@Autowired
	private GrdProCallstatisticsToolService grdProCallstatisticsToolService;
	@Autowired
	public MarketManageService marketService;
	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProCallstatistics/main")
	public String list(HttpServletRequest request) {
		try {
			putModel("allMarket2", marketService.getAllByType("2"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "grdProCallstatistics/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProCallstatistics/query")
	@ResponseBody
	public String query(HttpServletRequest request, String startDate, String endDate, Integer  marketId,
			String grdUserName, Integer serviceType,Integer source,Integer callRole,
			String s_Mobile,String s_Name,String e_Mobile,String e_Name) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("marketId", marketId);
			map.put("grdUserName", grdUserName);
			map.put("serviceType", serviceType);
			map.put("source", source);
			map.put("callRole", callRole);
			map.put("s_Mobile", s_Mobile);
			map.put("s_Name", s_Name);
			map.put("e_Mobile", e_Mobile);
			map.put("e_Name", e_Name);
			
			// 记录数
			map.put("total", grdProCallstatisticsToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdProCallstatisticsDTO> list = grdProCallstatisticsToolService.getListPage(map);
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
	@RequestMapping(value = { "grdProCallstatistics/save" })
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, GrdProCallstatisticsEntity entity, GrdProCallstatisticsDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("createUserId");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUserId(user.getUserID());
				dto.setUpdateTime(new Date());
				i = grdProCallstatisticsToolService.update(dto);
			} else {
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				i = grdProCallstatisticsToolService.insert(entity);
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
	@RequestMapping("grdProCallstatistics/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "grdProCallstatistics/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProCallstatistics/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdProCallstatisticsDTO dto = grdProCallstatisticsToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("grdProCallstatistics/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProCallstatistics/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdProCallstatisticsDTO dto = grdProCallstatisticsToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("grdProCallstatistics/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdProCallstatistics/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (StringUtils.isEmpty(ids)) {
                map.put("msg", "未选中删除数据");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = grdProCallstatisticsToolService.deleteBatch(list);
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
	@RequestMapping(value = "grdProCallstatistics/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, String startDate, String endDate, Integer  marketId,
			String grdUserName, Integer serviceType,Integer source,Integer callRole,
			String s_Mobile,String s_Name,String e_Mobile,String e_Name) {
			
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("marketId", marketId);
			map.put("grdUserName", grdUserName);
			map.put("serviceType", serviceType);
			map.put("source", source);
			map.put("callRole", callRole);
			map.put("s_Mobile", s_Mobile);
			map.put("s_Name", s_Name);
			map.put("e_Mobile", e_Mobile);
			map.put("e_Name", e_Name);
			int total = grdProCallstatisticsToolService.getTotal(map);
			if (total > 50000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>50000条), 请缩减日期范围, 或者修改其他查询条件...");
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
	@RequestMapping(value = "grdProCallstatistics/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String startDate, String endDate, Integer  marketId,
			String grdUserName, Integer serviceType,Integer source,Integer callRole,
			String s_Mobile,String s_Name,String e_Mobile,String e_Name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("marketId", marketId);
		map.put("grdUserName", grdUserName);
		map.put("serviceType", serviceType);
		map.put("source", source);
		map.put("callRole", callRole);
		map.put("s_Mobile", s_Mobile);
		map.put("s_Name", s_Name);
		map.put("e_Mobile", e_Mobile);
		map.put("e_Name", e_Name);
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			SimpleDateFormat smDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
			String time=smDateFormat.format(new Date()); //获取当前时间
			String fileName="拨打电话统计"+time+".xlsx";
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<GrdProCallstatisticsDTO> list = grdProCallstatisticsToolService.getList(map);
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, GrdProCallstatisticsDTO.class);
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
	/*public static void main(String[] args) {
		
		SimpleDateFormat smDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=smDateFormat.format(new Date());
		System.out.println(time);
	}*/
}
