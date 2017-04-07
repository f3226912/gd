package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import com.gudeng.commerce.gd.admin.service.NpsOfferPriceToolService;
import com.gudeng.commerce.gd.admin.service.NpsPurchaseToolService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/**
 * 供应商报价信息管理
 * @author zlb
 * @date 2017年1月4日
 */
@Controller
public class NpsOfferPriceController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(NpsOfferPriceController.class);

	@Autowired
	private NpsOfferPriceToolService npsOfferPriceToolService;
	
	@Autowired
	private NpsPurchaseToolService npsPurchaseToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("npsOfferPrice/main")
	public String list(HttpServletRequest request) {
		return "npsOfferPrice/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("npsOfferPrice/query")
	@ResponseBody
	public String query(HttpServletRequest request,NpsOfferPriceDTO dto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("mobilePhone", dto.getMobilePhone());
			map.put("goodsName", dto.getGoodsName());
			map.put("purchaseStatus", dto.getPurchaseStatus());
			map.put("offerTimeStart", request.getParameter("offerTimeStart"));
			map.put("offerTimeEnd", request.getParameter("offerTimeEnd"));
			map.put("purchaseId", dto.getPurchaseId());
			map.put("status", dto.getStatus());
			//map.put("statuss", Arrays.asList("1","3"));

			// 记录数
			map.put("total", npsOfferPriceToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<NpsOfferPriceDTO> list = npsOfferPriceToolService.getListPage(map);
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
	@RequestMapping(value = { "npsOfferPrice/save" })
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, NpsOfferPriceEntity entity, NpsOfferPriceDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
//				dto.setUpdateUserId(user.getUserID());
//				dto.setUpdateTime(new Date());
				i = npsOfferPriceToolService.update(dto);
			} else {
//				entity.setCreateUserId(user.getUserID());
//				entity.setCreateTime(new Date());
				i = npsOfferPriceToolService.insert(entity);
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
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("npsOfferPrice/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			NpsOfferPriceDTO offerDTO = npsOfferPriceToolService.getById(id);
			if(null!=offerDTO){
				NpsPurchaseDTO purchaseDTO=npsPurchaseToolService.getById(offerDTO.getPurchaseId().toString());
				mv.addObject("purchaseDTO", purchaseDTO);
			}
			mv.addObject("offerDTO", offerDTO);
			
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("npsOfferPrice/edit");
		return mv;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "npsOfferPrice/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request,NpsOfferPriceDTO dto) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobilePhone", dto.getMobilePhone());
			map.put("goodsName", dto.getGoodsName());
			map.put("purchaseStatus", dto.getPurchaseStatus());
			String startTime= request.getParameter("offerTimeStart");
			String endTime=request.getParameter("offerTimeEnd");
			map.put("offerTimeStart",startTime);
			map.put("offerTimeEnd", endTime);
			map.put("purchaseId", dto.getPurchaseId());
			map.put("status", dto.getStatus());
			/*if (DateUtil.isDateIntervalOverFlow(startTime, endTime, 31)) {
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}*/
			int total = npsOfferPriceToolService.getTotal(map);
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
	@RequestMapping(value = "npsOfferPrice/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response,NpsOfferPriceDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			map.put("mobilePhone", dto.getMobilePhone());
			map.put("goodsName", dto.getGoodsName());
			map.put("purchaseStatus", dto.getPurchaseStatus());
			map.put("offerTimeStart", request.getParameter("offerTimeStart"));
			map.put("offerTimeEnd", request.getParameter("offerTimeEnd"));
			map.put("purchaseId", dto.getPurchaseId());
			map.put("status", dto.getStatus());
			map.put("startRow", 0);
			map.put("endRow", 10000);
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName = null;
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
				fileName = URLEncoder.encode("供应商报价列表", "UTF-8");
			} else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
				fileName = new String("供应商报价列表".getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName  + ".xls");
			ouputStream = response.getOutputStream();
			WritableWorkbook wwb = null;
			// 查询数据
			List<NpsOfferPriceDTO> list = npsOfferPriceToolService.getList(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("报价列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				int col = 0;
				//Label label0 = new Label(col++, 0, "用户账号");
				Label label8 = new Label(col++, 0, "报价编号");
				Label label1 = new Label(col++, 0, "手机号码");
				Label label9 = new Label(col++, 0, "姓名");
				//Label label2 = new Label(col++, 0, "询价信息编号");
				Label label3 = new Label(col++, 0, "商品名称");
				Label label10 = new Label(col++, 0, "报价(上车价)");
				Label label11 = new Label(col++, 0, "单位");
				Label label4 = new Label(col++, 0, "报价时间");
				//Label label5 = new Label(col++, 0, "询价状态");
				Label label6 = new Label(col++, 0, "报价状态");
				//Label label7 = new Label(col++, 0, "询价结束时间");
				Label label12 = new Label(col++, 0, "询价信息编号");
				
				//sheet.addCell(label0);
				sheet.addCell(label8);
				sheet.addCell(label1);
				//sheet.addCell(label2);
				sheet.addCell(label9);
				sheet.addCell(label3);
				sheet.addCell(label10);
				sheet.addCell(label11);
				sheet.addCell(label4);
				//sheet.addCell(label5);
				sheet.addCell(label6);
				//sheet.addCell(label7);
				sheet.addCell(label12);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NpsOfferPriceDTO npsOfferPriceDTO = list.get(i);
						col = 0;
						
						//Label labelA0 = new Label(col++, i + 1, npsOfferPriceDTO.getAccount());
						Label labelA8 = new Label(col++, i + 1, npsOfferPriceDTO.getId().toString());
						Label labelA1 = new Label(col++, i + 1, npsOfferPriceDTO.getMobilePhone());
						Label labelA9 = new Label(col++, i + 1, npsOfferPriceDTO.getRealName());
						//Label labelA2 = new Label(col++, i + 1, npsOfferPriceDTO.getPurchaseId().toString());
						Label labelA3 = new Label(col++, i + 1,npsOfferPriceDTO.getGoodsName());
						Label labelA10 = new Label(col++, i + 1,npsOfferPriceDTO.getOfferPriceStr()==null?"":npsOfferPriceDTO.getOfferPriceStr());
						Label labelA11 = new Label(col++, i + 1,npsOfferPriceDTO.getPurchaseUnit()==null?"":npsOfferPriceDTO.getPurchaseUnit());
						Label labelA4 = new Label(col++, i + 1,npsOfferPriceDTO.getOfferTime()==null?"": sdf.format(npsOfferPriceDTO.getOfferTime()));
						//Label labelA5 = new Label(col++, i + 1,setPurchaseStatus(npsOfferPriceDTO.getPurchaseStatus()));
						Label labelA6 = new Label(col++, i + 1, npsOfferPriceDTO.getStatus()==null?"":setOfferStatus(npsOfferPriceDTO.getStatus()));
						//Label labelA7 = new Label(col++, i + 1, npsOfferPriceDTO.getPurchaseEndTime());
						Label labelA12 = new Label(col++, i + 1, npsOfferPriceDTO.getPurchaseId().toString());
						sheet.addCell(labelA8);
						//sheet.addCell(labelA0);
						sheet.addCell(labelA1);
						//sheet.addCell(labelA2);
						sheet.addCell(labelA9);
						sheet.addCell(labelA3);
						sheet.addCell(labelA10);
						sheet.addCell(labelA11);
						sheet.addCell(labelA4);
						//sheet.addCell(labelA5);
						sheet.addCell(labelA6);
						//sheet.addCell(labelA7);
						sheet.addCell(labelA12);
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
	
	 /* 状态词
	 * @param status
	 * @return
	 */
	private String setPurchaseStatus(String status){
		String statusStr="";
		switch(status){
		case "1":
			statusStr="待审核";
			break;
		case "2":
			statusStr="审核通过";
			break;
		case "3":
			statusStr="已驳回";
			break;
		case "4":
			statusStr="用户撤销";
			break;
		/*case "5":
			statusStr="已关闭";
			break;
			*/
		case "6":
			statusStr="已结束";
			break;
		}
		return statusStr;
	}
	
	/**
	 * 状态词
	 * @param status
	 * @return
	 */
	private String setOfferStatus(String status){
		String statusStr="";
		switch(status){
		case "1":
			statusStr="显示";
			break;
		case "2":
			statusStr="已删除";
			break;
		case "3":
			statusStr="隐藏";
			break;
		}
		return statusStr;
	}
}
