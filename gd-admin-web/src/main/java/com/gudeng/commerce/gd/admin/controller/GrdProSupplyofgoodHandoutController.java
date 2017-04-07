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

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdProSupplyofgoodHandoutToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
//import com.gudeng.commerce.gd.bi.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.bi.dto.GrdProSupplyofgoodHandoutDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProSupplyofgoodHandoutEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Controller
public class GrdProSupplyofgoodHandoutController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdProSupplyofgoodHandoutController.class);

	@Autowired
	private GrdProSupplyofgoodHandoutToolService grdProSupplyofgoodHandoutToolService;

	/**
	 * 配置文件
	 */
	@Autowired
	public GdProperties gdProperties;
	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProSupplyofgoodHandout/main")
	public String list(HttpServletRequest request) {
		return "grdProSupplyofgoodHandout/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProSupplyofgoodHandout/query")
	@ResponseBody
	public String query(HttpServletRequest request, String marketId, String teamId, String grdUserName, String grdMobile,
			 String status, String publisher, String mobile, String startDate, String endDate,String isDeleted,String sourceType) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("grdUserName", grdUserName);
			map.put("grdMobile", grdMobile);
			map.put("status", status);
			map.put("publisher", publisher);
			map.put("mobile", mobile);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("isDeleted", isDeleted);
			map.put("sourceType", sourceType);
			// 设置查询参数
			// 记录数
			map.put("total", grdProSupplyofgoodHandoutToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdProSupplyofgoodHandoutDTO> list = grdProSupplyofgoodHandoutToolService.getListPage(map);
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
	@RequestMapping(value = { "grdProSupplyofgoodHandout/save" })
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, GrdProSupplyofgoodHandoutEntity entity, GrdProSupplyofgoodHandoutDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUserId(user.getUserID());
				dto.setUpdateTime(new Date());
				i = grdProSupplyofgoodHandoutToolService.update(dto);
			} else {
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				i = grdProSupplyofgoodHandoutToolService.insert(entity);
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
	@RequestMapping("grdProSupplyofgoodHandout/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "grdProSupplyofgoodHandout/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProSupplyofgoodHandout/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdProSupplyofgoodHandoutDTO dto = grdProSupplyofgoodHandoutToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("grdProSupplyofgoodHandout/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProSupplyofgoodHandout/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdProSupplyofgoodHandoutDTO dto = grdProSupplyofgoodHandoutToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("grdProSupplyofgoodHandout/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdProSupplyofgoodHandout/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (StringUtils.isEmpty(ids)) {
                map.put("msg", "未选中删除数据");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = grdProSupplyofgoodHandoutToolService.deleteBatch(list);
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
	@RequestMapping(value = "grdProSupplyofgoodHandout/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, String marketId, String teamId, String grdUserName, String grdMobile,
			 String status, String publisher, String mobile, String startDate, String endDate,String isDeleted,String sourceType) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("grdUserName", grdUserName);
			map.put("grdMobile", grdMobile);
			map.put("status", status);
			map.put("publisher", publisher);
			map.put("mobile", mobile);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("isDeleted", isDeleted);
			map.put("sourceType", sourceType);
			int total = grdProSupplyofgoodHandoutToolService.getTotal(map);
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
	@RequestMapping(value = "grdProSupplyofgoodHandout/exportData", method=RequestMethod.POST)
	public String exportData(HttpServletRequest request, String marketId, String teamId, String grdUserName, String grdMobile,String sourceType,
			 String status, String publisher, String mobile, String startDate, String endDate, String isDeleted,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream out = null;
		WritableWorkbook wwb = null;
		try {
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("grdUserName", grdUserName);
			map.put("grdMobile", grdMobile);
			map.put("status", status);
			map.put("publisher", publisher);
			map.put("mobile", mobile);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
		    map.put("isDeleted", isDeleted);
		    map.put("sourceType", sourceType);
			// 查询数据
			List<GrdProSupplyofgoodHandoutDTO> list = grdProSupplyofgoodHandoutToolService.getList(map);
			
			
			//获取模板文件路径
			//String templatePath = gdProperties.getProperties().getProperty("GRDPROSUPPLYOFGOODHANDOUT_TEMPLETE");
			//String srcFilePath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/" + templatePath);
	
			// 设置文件名和头信息
			String destFileName = new String("发布货源统计".getBytes(), "ISO8859-1") + RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls";			//目标文件名
			response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);	// 设置响应
			response.setContentType("application/vnd.ms-excel");
	
			// 设置列表数据
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("list", list);
	
			//获取输出流
			out = response.getOutputStream();
			//JxlsExcelUtil.exportReportFromAbsolutePath(srcFilePath, beans, out);
			// 在输出流中创建一个新的写入工作簿
						wwb = Workbook.createWorkbook(out);
						if (wwb != null) {
							WritableSheet sheet = wwb.createSheet("发布货源统计表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
							Label label00 = new Label(0, 0, "所属市场");// 填充第一行第一个单元格的内容
							Label label10 = new Label(1, 0, "地推姓名 ");// 填充第一行第二个单元格的内容
							Label label20 = new Label(2, 0, "地推手机");// 填充第一行第三个单元格的内容
							Label label30 = new Label(3, 0, "货源ID");// 填充第一行第三个单元格的内容
							Label label40 = new Label(4, 0, "线路类型");// 填充第一行第四个单元格的内容
							Label label50 = new Label(5, 0, "发布人姓名");// 
							Label label60 = new Label(6, 0, "发布人手机");// 
							Label label70 = new Label(7, 0, "发布时间");// 填充第一行第五个单元格的内容
							Label label80 = new Label(8, 0, "货源状态");// 填充第一行第五个单元格的内容
						
							sheet.addCell(label00);// 将单元格加入表格
							sheet.addCell(label10);// 将单元格加入表格
							sheet.addCell(label20);
							sheet.addCell(label30);
							sheet.addCell(label40);
							sheet.addCell(label50);
							sheet.addCell(label60);
							sheet.addCell(label70);
							sheet.addCell(label80);
							/*** 循环添加数据到工作簿 ***/
							if (list != null && list.size() > 0) {
								for (int i = 0, lenght = list.size(); i < lenght; i++) {
									GrdProSupplyofgoodHandoutDTO dto= list.get(i);
									//Label label0 = new Label(0, i + 1, String.valueOf(dto.getId()));
									Label label0 = new Label(0, i + 1, String.valueOf(dto.getMarketName() == null ? "" : dto.getMarketName()));
									Label label1 = new Label(1, i + 1, String.valueOf(dto.getGrdUserName()== null ? "" : dto.getGrdUserName()));
									Label label2 = new Label(2, i + 1, String.valueOf(dto.getGrdMobile() == null ? "" : dto.getGrdMobile()));
									Label label3 = new Label(3, i + 1, String.valueOf(dto.getGoodsId() == null ? "" : dto.getGoodsId()));
									Label label4 = new Label(4, i + 1, String.valueOf(dto.getSourceTypeStr() == null ? "" : dto.getSourceTypeStr()));
									Label label5 = new Label(5, i + 1, String.valueOf(dto.getPublisher() == null ? "" : dto.getPublisher()));
									Label label6 = new Label(6, i + 1, String.valueOf(dto.getMobile() == null ? "" : dto.getMobile()));
									Label label7 = new Label(7, i + 1, String.valueOf(dto.getPublisherTimeString() == null ? "" : dto.getPublisherTimeString()));
									Label label8 = new Label(8, i + 1, String.valueOf(dto.getStatusString() == null ? "" : dto.getStatusString()));
									
									sheet.addCell(label0);//将单元格加入表格
									sheet.addCell(label1);// 
									sheet.addCell(label2);
									sheet.addCell(label3);
									sheet.addCell(label4);
									sheet.addCell(label5);
									sheet.addCell(label6);
									sheet.addCell(label7);
									sheet.addCell(label8);
								}
							}
							wwb.write();// 将数据写入工作簿
						}
						wwb.close();// 关闭
					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {
						try {
							out.flush();
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					return null;
				}
}
