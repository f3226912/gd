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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftstoreToolService;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftteamToolService;
import com.gudeng.commerce.gd.admin.service.GrdMemberToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
//import com.gudeng.commerce.gd.promotion.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftteamDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftteamEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class GrdGdGiftteamController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdGdGiftteamController.class);

	@Autowired
	private GrdGdGiftteamToolService grdGdGiftteamToolService;

	@Autowired
	private GrdGdGiftstoreToolService grdGdGiftstoreToolService;

	/**
	 * 市场服务类
	 */
	@Autowired
	private MarketManageService marketManageService;

	/**
	 * 地推人员服务类
	 */
	@Autowired
	public GrdMemberToolService grdMemberToolService;

	/**
	 * @Title: queryGiftstore
	 * @Description: 根据市场查询仓库列表
	 */
	@RequestMapping("grdGdGiftteam/queryGiftstore")
	@ResponseBody
	public String queryGiftstore(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", request.getParameter("marketId"));
			List<GrdGdGiftstoreDTO> list = grdGdGiftstoreToolService.getList(map);
			map.put("rows", list);
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("grdGdGiftteam/list")
	public String list(HttpServletRequest request) throws Exception {
		// 查询市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		request.setAttribute("marketDTOs", marketDTOs);
		// 查询仓库
		List<GrdGdGiftstoreDTO> grdGdGiftstoreDTOs = grdGdGiftstoreToolService.getList(null);
		request.setAttribute("grdGdGiftstoreDTOs", grdGdGiftstoreDTOs);
		return "grdGdGiftteam/list";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGiftteam/query")
	@ResponseBody
	public String query(HttpServletRequest request, GrdGdGiftteamDTO dto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("name", dto.getName());
			map.put("marketId", dto.getMarketId());
			map.put("giftstoreId", dto.getGiftstoreId());
			// 记录数
			map.put("total", grdGdGiftteamToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdGdGiftteamDTO> list = grdGdGiftteamToolService.getListPage(map);
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
	 */
	@RequestMapping(value = { "grdGdGiftteam/save" })
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, GrdGdGiftteamEntity entity,
			GrdGdGiftteamDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUser(user.getUserID());
				dto.setUpdateUserName(user.getUserName());
				i = grdGdGiftteamToolService.update(dto);
			} else {
				entity.setCreateUser(user.getUserID());
				entity.setCreateUserName(user.getUserName());
				entity.setCreateTime(new Date());
				entity.setUpdateUserName(user.getUserName());
				entity.setUpdateUser(user.getUserID());
				entity.setUpdateTime(new Date());
				i = grdGdGiftteamToolService.insert(entity);
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
	 * @throws Exception
	 */
	@RequestMapping("grdGdGiftteam/beforeSave")
	public String addDto(HttpServletRequest request) throws Exception {
		// 查询市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		request.setAttribute("marketDTOs", marketDTOs);
		// 查询仓库
		List<GrdGdGiftstoreDTO> grdGdGiftstoreDTOs = grdGdGiftstoreToolService.getList(null);
		request.setAttribute("grdGdGiftstoreDTOs", grdGdGiftstoreDTOs);
		return "grdGdGiftteam/add";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("grdGdGiftteam/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			GrdGdGiftteamDTO dto = grdGdGiftteamToolService.getById(id);
			mv.addObject("grdGdGiftteamDTO", dto);

			// 查询市场
			List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
			mv.addObject("marketDTOs", marketDTOs);
			// 查询仓库
			Map<String, Object> storeMap = new HashMap<String, Object>();
			storeMap.put("marketId", dto.getMarketId());
			List<GrdGdGiftstoreDTO> grdGdGiftstoreDTOs = grdGdGiftstoreToolService.getList(storeMap);
			mv.addObject("grdGdGiftstoreDTOs", grdGdGiftstoreDTOs);

			mv.setViewName("grdGdGiftteam/detail");
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGiftteam/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdGdGiftteamDTO dto = grdGdGiftteamToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("grdGdGiftteam/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "grdGdGiftteam/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中删除数据");
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("giftteamIds", list);
				//由于后来地推人员可以属于多个团队，此处未被处理到
				//int cnt = grdMemberToolService.getTotal(paramMap);
				int cnt = grdMemberToolService.getMember2TeamTotal(paramMap);
				if (cnt > 0) {
					map.put("msg", "团队下已有地推人员，不允许删除！");
				} else {
					grdGdGiftteamToolService.deleteBatch(list);
					map.put("msg", "success");
				}
			}
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return map;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "grdGdGiftteam/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int total = grdGdGiftteamToolService.getTotal(map);
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
	@RequestMapping(value = "grdGdGiftteam/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<GrdGdGiftteamDTO> list = grdGdGiftteamToolService.getList(map);
			// XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list,
			// GrdGdGiftteamDTO.class);
			// workBook.write(ouputStream);
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
