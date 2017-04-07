package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.GrdProOrderRecievedToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.bi.dto.GrdProOrderRecievedDTO;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 * 
 * @author liufan
 *
 */
@Controller
public class GrdProOrderRecievedController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdProOrderRecievedController.class);

	@Autowired
	private GrdProOrderRecievedToolService grdProOrderRecievedToolService;
	
	@Autowired
	private AreaToolService areaToolService;
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
	 * @author liufan
	 */
	@RequestMapping("grdProOrderRecieved/main")
	public String list(HttpServletRequest request) {
		request(request);
		return "grdProOrderRecieved/main";
	}
	
	/**
	 * 获取省份
	 * @param request
	 */
	private void request(HttpServletRequest request){
		List<AreaDTO> provinceList=null;
		try {
			provinceList=areaToolService.findProvince();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("provinceList", provinceList);
	}
	/**
	 * 获取城市
	 * @param e_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/getCity/{id}",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCity(@PathVariable("id") String provinceId,Map<String, Object> map){
		try {
			List<AreaDTO> cityList=areaToolService.findCity(provinceId);
			map.put("cityList", cityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}
	/**
	 * 获取区域
	 * @param s_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping("/getArea/{id}")
	@ResponseBody
	public String getArea(@PathVariable("id") String cityId,Map<String, Object> map){
		try {
			List<AreaDTO> areaList=areaToolService.findCounty(cityId);
			map.put("areaList", areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdProOrderRecieved/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map =getSearchMapParams(request);
			// 记录数
			map.put("total", grdProOrderRecievedToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdProOrderRecievedDTO> list = grdProOrderRecievedToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	/**
	 * 查询列表参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchMapParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		map.put("marketId", request.getParameter("marketId"));
		//map.put("teamId", request.getParameter("teamId"));
		map.put("grdUserName", request.getParameter("grdUserName"));
		map.put("grdMobile", request.getParameter("grdMobile"));
		map.put("confirmStatus", request.getParameter("confirmStatus"));
		map.put("startPublisherTime", request.getParameter("startPublisherTime"));
		map.put("endPublisherTime", request.getParameter("endPublisherTime"));
		map.put("startRecieveTime", request.getParameter("startRecieveTime"));
		map.put("endRecieveTime", request.getParameter("endRecieveTime"));
		map.put("startConfirmTime", request.getParameter("startConfirmTime"));
		map.put("endConfirmTime", request.getParameter("endConfirmTime"));
		map.put("sourceType", request.getParameter("sourceType"));
		map.put("s_provinceId", request.getParameter("s_provinceId"));
		map.put("s_cityId", request.getParameter("s_cityId"));
		map.put("s_areaId", request.getParameter("s_areaId"));
		map.put("e_provinceId", request.getParameter("e_provinceId"));
		map.put("e_cityId", request.getParameter("e_cityId"));
		map.put("e_areaId", request.getParameter("e_areaId"));
		/*if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
		}
		*/
		return map;
	}
	
	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@ResponseBody
	@RequestMapping(value = "grdProOrderRecieved/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map =getSearchMapParams(request);
			int total = grdProOrderRecievedToolService.getTotal(map);
			if (total > 50000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>50000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}else if (total < 1) {
				result.put("status", 0);
				result.put("message", "导出的结果集无任何数据，请重新修改查询条件...");
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
	 * @author liufan
	 */
	@RequestMapping(value = "grdProOrderRecieved/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		
		OutputStream out = null;
		WritableWorkbook wwb = null;
		try {
			Map<String, Object> map =getSearchMapParams(request);
			// 查询数据
			List<GrdProOrderRecievedDTO> list = grdProOrderRecievedToolService.getList(map);
	
			//获取模板文件路径
			//String templatePath = gdProperties.getProperties().getProperty("GRDPROORDERRECIEVED_TEMPLETE");
			//String srcFilePath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/" + templatePath);
	
			// 设置文件名和头信息
			String destFileName = new String("接单记录统计列表".getBytes(), "ISO8859-1") + RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls";			//目标文件名
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
							WritableSheet sheet = wwb.createSheet("接单记录统计表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
							Label label00 = new Label(0, 0, "所属市场");// 填充第一行第一个单元格的内容
							Label label10 = new Label(1, 0, "地推姓名 ");// 填充第一行第二个单元格的内容
							Label label20 = new Label(2, 0, "地推手机");// 填充第一行第三个单元格的内容
							Label label30 = new Label(3, 0, "发布人姓名");// 填充第一行第三个单元格的内容
							Label label40 = new Label(4, 0, "发货地");// 填充第一行第四个单元格的内容
							Label label50 = new Label(5, 0, "目的地 ");// 
							Label label60 = new Label(6, 0, "线路类型");// 
							Label label70 = new Label(7, 0, "重量");// 填充第一行第五个单元格的内容
							Label label80 = new Label(8, 0, "发布时间");// 填充第一行第五个单元格的内容
							Label label90 = new Label(9, 0, "接单时间");
							Label label100 = new Label(10, 0, "司机姓名");
							Label label110 = new Label(11, 0, "确认时间");
							Label label120 = new Label(12, 0, "确认状态");
							
							sheet.addCell(label00);// 将单元格加入表格
							sheet.addCell(label10);// 将单元格加入表格
							sheet.addCell(label20);
							sheet.addCell(label30);
							sheet.addCell(label40);
							sheet.addCell(label50);
							sheet.addCell(label60);
							sheet.addCell(label70);
							sheet.addCell(label80);
							sheet.addCell(label90);
							sheet.addCell(label100);
							sheet.addCell(label110);
							sheet.addCell(label120);
							/*** 循环添加数据到工作簿 ***/
							if (list != null && list.size() > 0) {
								for (int i = 0, lenght = list.size(); i < lenght; i++) {
									GrdProOrderRecievedDTO dto= list.get(i);
									//Label label0 = new Label(0, i + 1, String.valueOf(dto.getId()));
									Label label0 = new Label(0, i + 1, String.valueOf(dto.getMarketName() == null ? "" : dto.getMarketName()));
									Label label1 = new Label(1, i + 1, String.valueOf(dto.getGrdUserName() == null ? "" : dto.getGrdUserName()));
									Label label2 = new Label(2, i + 1, String.valueOf(dto.getGrdMobile() == null ? "" : dto.getGrdMobile()));
									Label label3 = new Label(3, i + 1, String.valueOf(dto.getPublisher() == null ? "" : dto.getPublisher()));
									Label label4 = new Label(4, i + 1, String.valueOf(dto.getS_detail() == null ? "" : dto.getS_detail()));
									Label label5 = new Label(5, i + 1, String.valueOf(dto.getE_detail() == null ? "" : dto.getE_detail()));
									Label label6 = new Label(6, i + 1, String.valueOf(dto.getSourceTypeStr() == null ? "" : dto.getSourceTypeStr()));
									Label label7 = new Label(7, i + 1, String.valueOf(dto.getTotalWeightStr() == null ? "" : dto.getTotalWeightStr()));
									Label label8 = new Label(8, i + 1, String.valueOf(dto.getPublisherTimeString() == null ? "" : dto.getPublisherTimeString()));
									Label label9 = new Label(9, i + 1, String.valueOf(dto.getRecieveTimeString() == null ? "" : dto.getRecieveTimeString()));
									Label label010 = new Label(10, i + 1, String.valueOf(dto.getDriverName() == null ? "" : dto.getDriverName()));
									Label label11 = new Label(11, i + 1, String.valueOf(dto.getConfirmTimeString() == null ? "" : dto.getConfirmTimeString()));
									Label label12 = new Label(12, i + 1, String.valueOf(dto.getConfirmStatusStr() == null ? "" : dto.getConfirmStatusStr()));
									
									sheet.addCell(label0);//将单元格加入表格
									sheet.addCell(label1);// 
									sheet.addCell(label2);
									sheet.addCell(label3);
									sheet.addCell(label4);
									sheet.addCell(label5);
									sheet.addCell(label6);
									sheet.addCell(label7);
									sheet.addCell(label8);
									sheet.addCell(label9);
									sheet.addCell(label010);
									sheet.addCell(label11);
									sheet.addCell(label12);
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