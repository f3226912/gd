package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.PreWeighCarDetailToolService;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("supSub")
public class SupplierSubController extends AdminBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(SupplierSubController.class);
	
	private PreWeighCarDetailToolService detailToolService;
	
	@RequestMapping("list/{type}")
	private String showSublist(@PathVariable String type) {
		putModel("type", type);
		if("1".equals(type)) {
			return "supplier/supSubList";
		} else {
			return "supplier/supSubOkList";
		}
	}
	
	@RequestMapping("querybysearch/{type}")
	@ResponseBody
	public String queryBySearch(PreWeighCarDetailDTO detailDTO, @PathVariable String type) {
		try {
			
			// 获取参数
			Map<String, Object> map = detailToolService.getParamsMap(detailDTO);
			
			if("1".equals(type)) {
				map.put("paymentStatus", 1);
			} else {
				map.put("paymentStatus", 2);
			}
			
			//设定分页,排序
			setCommParameters(request, map);
			
			//获取查询结果
			List<PreWeighCarDetailDTO> list = detailToolService.getByConditionPageForAdmin(map);
			int total = detailToolService.getTotalForAdmin(map);
			
			map.put("total", total);
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return null;
		
	}
	
	@RequestMapping("updateStatus")
	@ResponseBody
	public String updateStatus(ModelMap rmap) {
		try {
			String pwds = request.getParameter("ides");
			//修改数据
			int result = detailToolService.batchUpdatePaymentStatus(pwds, getUser(request)!=null?getUser(request).getUserID():"1");
			
			if(result==1) {
				rmap.put("msg", "SUCCESS");
			} else {
				rmap.put("msg", "ERROR");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "-1");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
		
	}
	
	@RequestMapping(value = "exportData/{type}")
	public String exportData(HttpServletRequest request,HttpServletResponse response,@PathVariable String type, PreWeighCarDetailDTO detailDTO){
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			
			// 获取参数
			Map<String, Object> map = detailToolService.getParamsMap(detailDTO);
			
			if("1".equals(type)) {
				map.put("paymentStatus", 1);
			} else {
				map.put("paymentStatus", 2);
			}

			int total = detailToolService.getTotalForAdmin(map);

			map.put("startRow", 0);
			map.put("endRow", total);
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=\"report\".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			//获取查询结果
			List<PreWeighCarDetailDTO> list = detailToolService.getByConditionPageForAdmin(map);
			
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("1".equals(type)?"采购商皮重入场":"采购商总重出场", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "入库单");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "产品名称");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "商品类目");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "入场产品重量（吨）");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "出场重量（吨）");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "产品净重");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "供应商用户名");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "供应商姓名 ");// 填充第一行第八个单元格的内容
				Label label80 = new Label(8, 0, "入场时间");// 填充第一行第九个单元格的内容
				Label label90 = new Label(9, 0, "出场时间");// 填充第一行第十个单元格的内容
				Label label100 = new Label(10, 0, "补贴金额");// 填充第一行第十个单元格的内容
				Label label110 = new Label(11, 0, "结算状态");// 填充第一行第十个单元格的内容
				sheet.addCell(label00);
				sheet.addCell(label10);
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
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PreWeighCarDetailDTO pDto = list.get(i);
						
						String orders = "";
						if(pDto.getInStores()!=null) {
							for (int j = 0; j < pDto.getInStores().size(); j++) {
								orders += pDto.getInStores().get(j).getInStoreNo() + ",";
							}
						}
						Label label0 = new Label(0, i + 1, orders);
						
						Label label1 = new Label(1, i + 1, pDto.getProductName());
						
						Label label2 = new Label(2, i + 1, pDto.getCateName());
						
						Label label3 = new Label(3, i + 1, ""+pDto.getWeigh());
						Label label4 = new Label(4, i + 1, ""+pDto.getOutWeigh());
						Label label5 = new Label(5, i + 1, ""+(pDto.getWeigh()-pDto.getOutWeigh()));
						Label label6 = new Label(6, i + 1, pDto.getAccount());
						Label label7 = new Label(7, i + 1, pDto.getMemberName());
						Label label8 = new Label(8, i + 1, time.format(pDto.getTotalCreateTime()));
						Label label9 = new Label(9, i + 1, time.format(pDto.getTareCreateTime()));
						Label label010 = new Label(10, i + 1, ""+pDto.getSubMoney());
						Label label011 = new Label(11, i + 1, pDto.getPaymentStatus());
						
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label010);
						sheet.addCell(label011);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
