package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdUserCustomerToolService;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdUserCustomerEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author liufan
 *
 */
@Controller
public class GrdUserCustomerController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdUserCustomerController.class);

	@Autowired
	private GrdUserCustomerToolService grdUserCustomerToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdUserCustomer/main")
	public String list(HttpServletRequest request) {
		return "grdUserCustomer/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdUserCustomer/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map = getSearchMapParams(request);
			// 设置查询参数
			// 记录数
			map.put("total", grdUserCustomerToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdUserCustomerDTO> list = grdUserCustomerToolService.getListPage(map);
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
	 * @author liufan
	 */
	@RequestMapping(value = { "grdUserCustomer/save" }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, GrdUserCustomerEntity entity, GrdUserCustomerDTO dto) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			String memberIds=request.getParameter("memberIds");
			List<String> list = Arrays.asList(memberIds.split(","));
			SysRegisterUser user = getUser(request);
			dto.setCreateUserId(user.getUserID());
			dto.setCreateUserCode(user.getUserCode());
			dto.setCreateUserName(user.getUserName());
			grdUserCustomerToolService.batchUpdate(list,dto);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			resultMap.put("msg", "保存失败");
			logger.info("新增更新保存错误", e);
		}
		return JSONObject.toJSONString(resultMap);
	}
	/**
	 *地推人员 列表查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdUserCustomer/queryGrdMember")
	@ResponseBody
	public String queryGrdMember(HttpServletRequest request) {
		try {
			Map<String, Object> map = getSearchMapParams(request);
			// 设置查询参数
			// 记录数
			map.put("total", grdUserCustomerToolService.getGrdMemberTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdMemberDTO> list = grdUserCustomerToolService.getGrdMemberListPage(map);
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
		map.put("memberName", request.getParameter("memberName"));
		map.put("memberMobile", request.getParameter("memberMobile"));
		map.put("grdUserName", request.getParameter("grdUserName"));
		map.put("grdMobile", request.getParameter("grdMobile"));
		map.put("marketId", request.getParameter("marketId"));
		
		return map;
	}
	


	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdUserCustomer/edit/{memberIds}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("memberIds") String memberIds) {
		ModelAndView mv = new ModelAndView();
		try {
			List<String> list = Arrays.asList(memberIds.split(","));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberIds", list);
			List<GrdUserCustomerDTO> dtoList = grdUserCustomerToolService.getList(paramMap);
			mv.addObject("dtoList", dtoList);
			mv.addObject("memberIds", memberIds);
		} catch (Exception e) {
			logger.trace("进入修改页面错误", e);
		}
		mv.setViewName("grdUserCustomer/edit");
		return mv;
	}

	
	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@ResponseBody
	@RequestMapping(value = "grdUserCustomer/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getSearchMapParams(request);
			int total = grdUserCustomerToolService.getTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			} else if (total < 1) {
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
	@RequestMapping(value = "grdUserCustomer/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = getSearchMapParams(request);
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName  = new String("地推人员客户列表".getBytes(), "ISO8859-1") + RandomIdGenerator.random("yyyy-MM-dd-HH-");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<GrdUserCustomerDTO> list = grdUserCustomerToolService.getList(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("地推人员客户列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "客户姓名");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "客户手机");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "地推人员");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "地推人员手机");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "所属市场");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "备注");// 填充第一行第七个单元格的内容
			
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					//SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						GrdUserCustomerDTO item = list.get(i);
						Label label0 = new Label(0, i + 1, item.getMemberName());
						Label label1 = new Label(1, i + 1, item.getMemberMobile());
						Label label2 = new Label(2, i + 1, item.getGrdUserName());
						Label label3 = new Label(3, i + 1, item.getGrdMobile());
						Label label4 = new Label(4, i + 1, item.getMarketName());
						Label label5 = new Label(5, i + 1, item.getRemarks());

						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
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
	
	/**
	 * 根据memberId查看数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdUserCustomer/view/{memberId}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("memberId") String memberId) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdUserCustomerDTO dto = grdUserCustomerToolService.getById(memberId);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入指派记录页面错误", e);
		}
		mv.setViewName("grdUserCustomer/view");
		return mv;
	}
	/**
	 * 根据memberId查询指派记录列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdUserCustomer/log/{memberId}")
	@ResponseBody
	public String queryLog(HttpServletRequest request, @PathVariable("memberId") String memberId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			List<GrdUserCustomerLogDTO> list = grdUserCustomerToolService.getUserCustomerLogList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace("指派记录列表查询出错", e);
		}
		return null;
	}

}
