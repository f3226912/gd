package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdGiftLogToolService;
import com.gudeng.commerce.gd.admin.service.GrdGiftToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftLogEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 地推礼品管理
 * 
 * @author lidong
 *
 */
@Controller
public class GrdGiftController extends AdminBaseController {
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdGiftController.class);

	/**
	 * 地推礼品
	 */
	@Autowired
	public GrdGiftToolService grdGiftService;
	/**
	 * 地推礼品操作日志
	 */
	@Autowired
	public GrdGiftLogToolService grdGiftLogService;
	@Autowired
	public MarketManageService marketManageService;

	@RequestMapping("grdgift/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("grdgift/index");
		return mv;
	}

	@RequestMapping("grdgift/query")
	@ResponseBody
	public String query(HttpServletRequest request, GrdGiftDTO dto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("status", 0);
			map.put("marketId", request.getParameter("marketId"));
			map.put("id", request.getParameter("id"));
			map.put("giftNo", request.getParameter("giftNo"));
			map.put("name", request.getParameter("name"));
			map.put("giftstoreId", request.getParameter("giftstoreId"));
			// 记录数
			map.put("total", grdGiftService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			List<GrdGiftDTO> list = new ArrayList<>();
			List<MarketDTO> markets = marketManageService.getAllByType("2");
			for (GrdGiftDTO dto2 : grdGiftService.getListPage(map)) {
				for (MarketDTO marketDTO : markets) {
					if ((long) marketDTO.getId() == (long) (dto2.getMarketId())) {
						dto2.setMarketName(marketDTO.getMarketName());
					}
				}
				list.add(dto2);
			}
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace("地推礼品列表查询出错", e);
			return null;
		}
	}

	@RequestMapping("grdgift/log/{giftId}")
	@ResponseBody
	public String queryLog(HttpServletRequest request, @PathVariable("giftId") String giftId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("giftId", giftId);
			List<GrdGiftLogDTO> list = grdGiftLogService.getList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace("地推礼品列表查询出错", e);
		}
		return null;
	}

	/**
	 * 打开新增页面
	 *
	 * @param request
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 下午5:02:22
	 */
	@RequestMapping("grdgift/add")
	public ModelAndView addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("option", "add");
		mv.setViewName("grdgift/add");
		return mv;
	}

	@RequestMapping("grdgift/edit/{id}")
	public ModelAndView editbyid(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdGiftDTO dto = grdGiftService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("礼品管理修改出错", e);
		}
		mv.setViewName("grdgift/update");
		return mv;
	}

	@RequestMapping(value = "grdgift/save")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, GrdGiftEntity entity, GrdGiftDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (!dto.getName().equals(dto.getOldName()) || dto.getId() == null) {
				map.put("name", dto.getName());
				map.put("marketId", dto.getMarketId());
				List<GrdGiftDTO> list = grdGiftService.getList(map);
				if (list != null && list.size() > 0) {
					map.put("msg", "请重新输入礼品名称，当前市场已存在相同的礼品！");
					return map;
				} else {
					map.clear();
				}
			}
			if (dto.getStockTotal() == null || dto.getStockTotal() < 0) {
				map.put("msg", "库存数量错误");
				return map;
			}
			SysRegisterUser userInfo = getUser(request);
			GrdGiftLogEntity entityLog = new GrdGiftLogEntity();
			entityLog.setCreateUserId(userInfo.getUserName());
			entityLog.setCreateTime(new Date());
			if (dto.getId() != null) {
				GrdGiftDTO temp = grdGiftService.getById(dto.getId().toString());
				Integer stockTotal = temp.getStockTotal();
				if ((long) stockTotal != (long) dto.getStockTotalOld()) {
					map.put("msg", "保存失败，库存已经发生变化，请刷新后重新调整！");
					return map;
				}
				Integer stockTotalChange = dto.getStockTotal() - dto.getStockTotalOld();
				Integer stockAvailable = dto.getStockAvailable() + stockTotalChange;
				if (stockAvailable < 0) {
					map.put("msg", "可用库存不足,意味着减少库存之后，当前可用库存会减少为负数");
					return map;
				} else {
					dto.setStockAvailable(stockAvailable);
				}
				dto.setUpdateUserId(userInfo.getUserID());
				int result = grdGiftService.update(dto);
				if (result > 0 && stockTotalChange != 0) {
					entityLog.setGiftId(Long.valueOf(dto.getId()));
					entityLog.setOrignValue(dto.getStockTotalOld());
					entityLog.setReason("库存调整");
					entityLog.setRealValue(dto.getStockTotal());
					grdGiftLogService.insert(entityLog);
				}
			} else {
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				entity.setCreateUserId(userInfo.getUserID());
				entity.setUpdateUserId(userInfo.getUserID());
				entity.setStockAvailable(entity.getStockTotal());
				entity.setStatus(0);
				Long id = grdGiftService.insert(entity);
				if (id != null && id > 0) {
					entityLog.setGiftId(id);
					entityLog.setOrignValue(0);
					entityLog.setReason("创建礼品");
					entityLog.setRealValue(entity.getStockTotal());
					grdGiftLogService.insert(entityLog);
				}
			}
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "保存失败");
			logger.trace("礼品信息保存失败", e);
		}
		return map;
	}

	@RequestMapping(value = "grdgift/delete")
	@ResponseBody
	public Map<String, Object> deleteById(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "操作失败");
				return map;
			}
			List<String> list = Arrays.asList(ids.split(","));
			//循环每个ID，判断其有没有发放记录，如果有，不允许
			int result = getGrdGiftRecordCount(list);
			if(result > 0){
				map.put("msg", "当前礼品有发放记录，不能删除!");
				return map;
			}
			grdGiftService.batchDelete(list);
			map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "操作失败");
			logger.trace("地推礼品管理删除操作失败", e);
		}
		return map;
	}
	public int getGrdGiftRecordCount(List<String> list)throws Exception{
		Map<String, Object> map = null;
		int result = 0;
		if(CollectionUtils.isNotEmpty(list)){
			for(String id : list){
				map = new HashMap<String, Object>();
				map.put("giftId", id);
				int count = grdGiftService.getGrdGiftRecordCount(map);
				if(count > 0){
					result = 1;
					break;
				}
			}
		}
		return result;
	}
	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "grdgift/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String marketId = request.getParameter("marketId");
			String name = request.getParameter("name");
			String giftNo = request.getParameter("giftNo");
			String giftstoreId =request.getParameter("giftstoreId");
			paramMap.put("giftstoreId", giftstoreId);
			paramMap.put("giftNo", giftNo);
			paramMap.put("id", id);
			paramMap.put("marketId", marketId);
			paramMap.put("name", name);
			paramMap.put("status", 0);
			int total = grdGiftService.getTotal(paramMap);
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
	 * 导出产品查询结果 注意 : 如果修改查询条件, 需要同步修改product/checkExportParams接口
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "grdgift/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String marketId = request.getParameter("marketId");
		String name = request.getParameter("name");
		String giftNo = request.getParameter("giftNo");
		String giftstoreId =request.getParameter("giftstoreId");
		params.put("giftstoreId", giftstoreId);
		params.put("giftNo", giftNo);
		params.put("id", id);
		params.put("marketId", marketId);
		params.put("name", name);
		params.put("status", 0);
		List<GrdGiftDTO> list = new ArrayList<>();
		try {

			// 记录数
			params.put("total", grdGiftService.getTotal(params));
			// 设定分页,排序
			setCommParameters(request, params);
			params.put("startRow", 0);
			params.put("endRow", 10000);
			// list
			List<MarketDTO> markets = marketManageService.getAllByType("2");
			for (GrdGiftDTO dto2 : grdGiftService.getListPage(params)) {
				for (MarketDTO marketDTO : markets) {
					if ((long) marketDTO.getId() == (long) (dto2.getMarketId())) {
						dto2.setMarketName(marketDTO.getMarketName());
					}
				}
				list.add(dto2);
			}

		} catch (Exception e) {
			logger.info("product exportData with ex : {} ", new Object[] { e });
		}

		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("地推礼品列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("地推礼品列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				Label label00 = new Label(0, 0, "礼品编码");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "礼品名称 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "所属仓库");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "所属市场");// 填充第一行第三个单元格的内容
				Label label40 = new Label(4, 0, "待发放数");// 填充第一行第四个单元格的内容
				Label label50 = new Label(5, 0, "库存 ");// 
				Label label60 = new Label(6, 0, "单位");// 
				Label label70 = new Label(7, 0, "备注");// 填充第一行第五个单元格的内容
			
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						GrdGiftDTO dto= list.get(i);
						//Label label0 = new Label(0, i + 1, String.valueOf(dto.getId()));
						Label label0 = new Label(0, i + 1, String.valueOf(dto.getGiftNo()==null?"":dto.getGiftNo()));
						Label label1 = new Label(1, i + 1, String.valueOf(dto.getName()==null?"":dto.getName()));
						Label label2 = new Label(2, i + 1, String.valueOf(dto.getGiftstoreName()==null?"":dto.getGiftstoreName()));
						Label label3 = new Label(3, i + 1, String.valueOf(dto.getMarketName()==null?"":dto.getMarketName()));
						String NoCount="";
						if(null!=dto.getNoCount()){
							NoCount=dto.getNoCount();
						}
						Label label4 = new Label(4, i + 1, String.valueOf(NoCount==null?"":NoCount));
						Label label5 = new Label(5, i + 1, String.valueOf(dto.getStockTotal()==null?"":dto.getStockTotal()));
						String Unit="";
						if(null!=dto.getUnit()){
							Unit=dto.getUnit();
						}
						Label label6 = new Label(6, i + 1, String.valueOf(Unit==null?"":Unit));
						Label label7 = new Label(7, i + 1, String.valueOf(dto.getRemark()==null?"":dto.getRemark()));
						
						sheet.addCell(label0);//将单元格加入表格
						sheet.addCell(label1);// 
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
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