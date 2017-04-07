package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
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
import com.gudeng.commerce.gd.admin.service.GrdGdGiftstoreToolService;
import com.gudeng.commerce.gd.admin.service.GrdGiftToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
//import com.gudeng.commerce.gd.promotion.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftstoreEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class GrdGdGiftstoreController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdGdGiftstoreController.class);

	@Autowired
	private GrdGdGiftstoreToolService grdGdGiftstoreToolService;

	/**
	 * 市场服务类
	 */
	@Autowired
	private MarketManageService marketManageService;

	/**
	 * 地推礼品
	 */
	@Autowired
	public GrdGiftToolService grdGiftService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("grdGdGiftstore/list")
	public String list(HttpServletRequest request) throws Exception {
		// 查询市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		request.setAttribute("marketDTOs", marketDTOs);
		return "grdGdGiftstore/list";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGiftstore/query")
	@ResponseBody
	public String query(HttpServletRequest request, GrdGdGiftstoreDTO dto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("marketId", dto.getMarketId());
			map.put("name", dto.getName());
			// 记录数
			map.put("total", grdGdGiftstoreToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdGdGiftstoreDTO> list = grdGdGiftstoreToolService.getListPage(map);
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
	@RequestMapping(value = { "grdGdGiftstore/save" })
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, GrdGdGiftstoreEntity entity,
			GrdGdGiftstoreDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			Map<String, Object> existMap = new HashMap<String, Object>();
			existMap.put("name", entity.getName());
			List<GrdGdGiftstoreDTO> giftstoreList = grdGdGiftstoreToolService.getList(existMap);
			int size = giftstoreList.size();

			if (StringUtils.isNotEmpty(id)) {
				if (size > 0) {
					GrdGdGiftstoreDTO grdGdGiftstoreDTO = giftstoreList.get(0);
					String storeId = String.valueOf(grdGdGiftstoreDTO.getId());
					if (!storeId.equals(id)) {
						map.put("msg", "仓库名称已存在");
						return map;
					}
				}
				dto.setUpdateUser(user.getUserID());
				dto.setUpdateUserName(user.getUserName());
				i = grdGdGiftstoreToolService.update(dto);

			} else {
				if (size > 0) {
					map.put("msg", "仓库名称已存在");
					return map;
				}
				entity.setCreateUser(user.getUserID());
				entity.setCreateUserName(user.getUserName());
				entity.setUpdateUserName(user.getUserName());
				entity.setUpdateUser(user.getUserID());
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				i = grdGdGiftstoreToolService.insert(entity);

			}
			if (i > 0) {
				map.put("msg", "success");
			} else {
				map.put("msg", "保存失败");
			}
		} catch (

		Exception e)

		{
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
	@RequestMapping("grdGdGiftstore/beforeSave")
	public ModelAndView addDto(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 查询市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		mv.addObject("marketDTOs", marketDTOs);
		mv.setViewName("grdGdGiftstore/add");
		return mv;
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGiftstore/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			// 查询市场
			List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
			mv.addObject("marketDTOs", marketDTOs);
			// 查询当前记录
			GrdGdGiftstoreDTO grdGdGiftstoreDTO = grdGdGiftstoreToolService.getById(id);
			mv.addObject("grdGdGiftstoreDTO", grdGdGiftstoreDTO);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("grdGdGiftstore/detail");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "grdGdGiftstore/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中删除数据");
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("giftstoreIds", list);
				int cnt = grdGiftService.getTotal(paramMap);
				if (cnt > 0) {
					map.put("msg", "仓库下有地推礼品，不允许删除！");
				} else {
					int i = grdGdGiftstoreToolService.deleteBatch(list);
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
	 */
	@ResponseBody
	@RequestMapping(value = "grdGdGiftstore/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int total = grdGdGiftstoreToolService.getTotal(map);
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
	 */
	@RequestMapping(value = "grdGdGiftstore/exportData")
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
			List<GrdGdGiftstoreDTO> list = grdGdGiftstoreToolService.getList(map);
			// XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list,
			// GrdGdGiftstoreDTO.class);
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

	/**
	 * 根据市场id查询仓库
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGiftstore/queryByMarketId")
	@ResponseBody
	public String queryByMarketId(HttpServletRequest request, Integer marketId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("marketId", marketId);
			// list
			List<GrdGdGiftstoreDTO> list = grdGdGiftstoreToolService.getList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
}
