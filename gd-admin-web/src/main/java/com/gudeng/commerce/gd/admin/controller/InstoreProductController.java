package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.InStoreDetailToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("instore")
public class InstoreProductController extends AdminBaseController{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(InstoreProductController.class);

	@Autowired
	private InStoreDetailToolService iStoreDetailToolService;
	/**
	 * 跳转入库管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("product")
	public String product(HttpServletRequest request){

		return "instore/productList";
	}

	/**
	 * 入库管理信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("productList")
	@ResponseBody
	public String productList(HttpServletRequest request,InStoreDetailDTO isDetailDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			map.put("supplierAccount",isDetailDTO.getSupplierAccount());
			map.put("specialAccount", isDetailDTO.getSpecialAccount());
			map.put("inStoreNo", isDetailDTO.getInStoreNo());
			map.put("createName", isDetailDTO.getCateName());
			map.put("productStartTime", isDetailDTO.getProductStartTime());
			map.put("productEndTime", isDetailDTO.getProductEndTime());
			map.put("istoreStatus", isDetailDTO.getIstoreStatus());

			//设置总记录数
			map.put("total", iStoreDetailToolService.getInstoreProductListTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<InStoreDetailDTO> list = iStoreDetailToolService.getInstoreProductList(map);

			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request,InStoreDetailDTO isDetailDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = isDetailDTO.getProductStartTime();
			String endDate = isDetailDTO.getProductEndTime();
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("supplierAccount",isDetailDTO.getSupplierAccount());
			map.put("specialAccount", isDetailDTO.getSpecialAccount());
			map.put("inStoreNo", isDetailDTO.getInStoreNo());
			map.put("createName", isDetailDTO.getCateName());
			map.put("productStartTime", isDetailDTO.getProductStartTime());
			map.put("productEndTime", isDetailDTO.getProductEndTime());
			map.put("istoreStatus", isDetailDTO.getIstoreStatus());

			//设置总记录数
			int total = iStoreDetailToolService.getInstoreProductListTotal(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("product checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}

	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,HttpServletResponse response,InStoreDetailDTO isDetailDTO){
		Map<String, Object> map = new HashMap<>();
		map.put("supplierAccount",isDetailDTO.getSupplierAccount());
		map.put("specialAccount", isDetailDTO.getSpecialAccount());
		map.put("inStoreNo", isDetailDTO.getInStoreNo());
		map.put("createName", isDetailDTO.getCateName());
		map.put("productStartTime", isDetailDTO.getProductStartTime());
		map.put("productEndTime", isDetailDTO.getProductEndTime());
		map.put("istoreStatus", isDetailDTO.getIstoreStatus());
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + StringUtil.GenerateRandomStr() + ".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			List<InStoreDetailDTO> list = iStoreDetailToolService.getInstoreProductList(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("入库产品管理列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "入库单");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "供应商账号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "供应商姓名");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "产品名称 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "商品类目");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "产品重量(吨)");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "产品创建时间");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "创建人");// 填充第一行第八个单元格的内容
				Label label80 = new Label(8, 0, "批发商账号 ");// 填充第一行第九个单元格的内容
				Label label90 = new Label(9, 0, "批发商姓名");// 填充第一行第十个单元格的内容
				Label labe200 = new Label(10, 0, "入库量(吨)");// 填充第一行第十一个单元格的内容
				Label labe210 = new Label(11, 0, "入库时间");// 填充第一行第十二个单元格的内容
				Label labe220 = new Label(12, 0, "入库状态");// 填充第一行第十三个单元格的内容
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
				sheet.addCell(labe200);
				sheet.addCell(labe210);
				sheet.addCell(labe220);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						InStoreDetailDTO inStoreDetailDTO = list.get(i);
						Label label0 = new Label(0, i + 1, inStoreDetailDTO.getInStoreNo()==null?"":inStoreDetailDTO.getInStoreNo().toString());
						Label label2 = new Label(1, i + 1, inStoreDetailDTO.getSupplierAccount());
						Label label3 = new Label(2, i + 1, inStoreDetailDTO.getSupplierName());
						Label label5 = new Label(3, i + 1, inStoreDetailDTO.getProductName());
						Label label1 = new Label(4, i + 1, inStoreDetailDTO.getCateName());
						Label label4 = new Label(5, i + 1, inStoreDetailDTO.getWeigh()==null?"":inStoreDetailDTO.getWeigh().toString());
						Label label6 = new Label(6, i + 1, inStoreDetailDTO.getCreateTime()==null?"":time.format(inStoreDetailDTO.getCreateTime()));
						Label label7 = new Label(7, i + 1, inStoreDetailDTO.getCreateName());
						Label label8 = new Label(8, i + 1, inStoreDetailDTO.getSpecialAccount());
						Label label9 = new Label(9, i + 1, inStoreDetailDTO.getSpecialName());
						Label labe20 = new Label(10, i + 1,inStoreDetailDTO.getPurQuantity()==null?"":inStoreDetailDTO.getPurQuantity().toString());
						Label labe21 = new Label(11, i + 1, inStoreDetailDTO.getInstoreTime());
						Label labe22=null;
						if (inStoreDetailDTO.getInStoreNo()!=null) {
							labe22 = new Label(12, i + 1,"已入库");
						}else {
							labe22 = new Label(12, i + 1,"未入库");
						}
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
						sheet.addCell(labe20);
						sheet.addCell(labe21);
						sheet.addCell(labe22);
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
