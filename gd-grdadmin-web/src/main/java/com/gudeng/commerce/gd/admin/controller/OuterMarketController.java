package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.CarBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.CarWeighProToolService;
import com.gudeng.commerce.gd.admin.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.admin.service.SubHelpToolService;
import com.gudeng.commerce.gd.admin.service.SystemCodeToolService;
import com.gudeng.commerce.gd.admin.service.WeighCarToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("outer")
public class OuterMarketController extends AdminBaseController {

	@Autowired
	private WeighCarToolService carToolService;

	@Autowired
	private SubHelpToolService helpService;

	@Autowired
	private OrderProductDetailToolService detailToolService;

	@Autowired
	private CarWeighProToolService carWeighProToolService;

	@Autowired
	private SystemCodeToolService systemCodeToolService;

	@Autowired
	private CarBaseinfoToolService carBaseinfoToolService;

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(OuterMarketController.class);

	@RequestMapping("buyer/{type}")
	public String buyerOuterList(@PathVariable String type) {
		putModel("type", type);
		return "outer/buyerList";
	}


	@RequestMapping("querybysearch/{type}")
	@ResponseBody
	public String queryBySearch(WeighCarDTO weighCarDTO, @PathVariable String type) {
		try {

			putModel("type", type);

			// 获取参数
			Map<String, Object> map = carToolService.getParamsMap(weighCarDTO);

			if("1".equals(type)) {
				map.put("type", 2);
				map.put("status", 1);
			} else {
				map.put("type", 2);
				map.put("status", 2);
			}

			//设定分页,排序
			setCommParameters(request, map);

			//获取查询结果
			List<WeighCarDTO> list = carToolService.getByConditionPage(map);
			int total = carToolService.getTotal(map);

			map.put("total", total);
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return null;

	}

	@RequestMapping("buyer/changeCar")
	@ResponseBody
	public String changeCar(CarBaseinfoDTO carbase, ModelMap rmap) {
		try {
			int result = 0;

			//修改数据
			if(carbase.getCarId()!=null && !"".equals(carbase.getCarId())) {
				CarBaseinfoEntity carEntity = new CarBaseinfoEntity();
				carEntity.setCarId(carbase.getCarId());
				carEntity.setCarNumber(carbase.getCarNumber());
				carEntity.setUpdateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
				result = carBaseinfoToolService.update(carEntity);
			} else {
				//添加
				CarBaseinfoEntity carEntity = new CarBaseinfoEntity();
				carEntity.setCarNumber(carbase.getCarNumber());
				carEntity.setCreateTime(new Date());
				carEntity.setCreateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
				result = carBaseinfoToolService.addCarNumber(carEntity, carbase.getWcId());
			}

			if(result==1) {
				rmap.put("msg", "修改成功!");
			} else {
				rmap.put("msg", "添加失败!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "-1");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);

	}

	@RequestMapping("showEdit/{type}/{id}")
	public String queryById(WeighCarDTO weighCarDTO, @PathVariable String type, @PathVariable String id) {
		try {
			WeighCarDTO wc = carToolService.getByIdForAdmin(Long.parseLong(id));
			putModel("dto", wc);

			Map<String, Integer> counts = helpService.getSubOutMarket(wc.getCarNumber(), type, wc.getMarketId(), new Date());
			putModel("counts", counts);

			if(wc.getCarNumberImage() != null || wc.getCarNumberImageOut()!= null) {
				if("1".equals(type)) {
					putModel("carNumberImages", wc.getCarNumberImage().split("[|]"));
				} else {
					putModel("carNumberImages", wc.getCarNumberImageOut().split("[|]"));
				}
			} else {
				putModel("carNumberImages", "");
			}

			return "outer/buyer_detail";
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return null;

	}

	@RequestMapping("showProduct/{id}")
	public String showProductList(WeighCarDTO weighCarDTO, @PathVariable String id) {
		try {
			putModel("id", id);
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "outer/product_list";
	}

	@RequestMapping("showProductList/{id}")
	@ResponseBody
	public String queryProductList(WeighCarDTO weighCarDTO, @PathVariable String id) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("orderNo", id);
			//设定分页,排序
			setCommParameters(request, map);

			//获取查询结果
			List<OrderProductDetailDTO> list = detailToolService.getListByConditionPage(map);

			/*
			 * 修改单位字段
			 */

			List<SystemCode> units = systemCodeToolService.getListViaType("ProductUnit");

			for(int i = 0; i < list.size(); i++) {
				OrderProductDetailDTO opdd = list.get(i);
				for (int j = 0; j < units.size(); j++) {
					if(units.get(j).getCodeKey().equals(opdd.getUnit())) {
						opdd.setUnit(units.get(j).getCodeValue());
					}
				}
			}

			int total = detailToolService.getTotal(map);
			map.put("total", total);
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map);

		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return null;

	}

	/**
	 * 显示车辆信息
	 * @return
	 */
	@RequestMapping("showCar")
	public String showCarWeigh() {
		try {
			Map<String, Object> map = new HashMap<>();

			//获取查询结果
			List<CarWeighProDTO> list = carWeighProToolService.getCarWeighProList(map);

			putModel("cars", list);

		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "outer/car";
	}

	/**
	 * 修改车辆信息
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String updateCar(HttpServletRequest request,CarWeighProDTO dto,ModelMap rmap) {
		try {
			dto.setUpdateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
			//修改数据
			int result = carWeighProToolService.updateCarWeighPro(dto);

			if(result==1) {
				rmap.put("msg", "修改成功!");
			} else {
				rmap.put("msg", "添加失败!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "-1");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 添加车辆信息
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public String addCar(HttpServletRequest request,CarWeighProDTO dto,ModelMap rmap) {
		try {
			dto.setUpdateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
			dto.setCreateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
			//修改数据
			int result = carWeighProToolService.insertCarWeighPro(dto);

			if(result==1) {
				rmap.put("msg", "添加成功!");
			} else {
				rmap.put("msg", "添加失败!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams/{type}", produces="application/json; charset=utf-8")
	public String checkExportParams(WeighCarDTO weighCarDTO, @PathVariable("type") String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = weighCarDTO.getTareStartTime();
			String endDate = weighCarDTO.getTareEndTime();
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("account", weighCarDTO.getAccount());
			map.put("tareStartTime", weighCarDTO.getTareStartTime());
			map.put("tareEndTime", weighCarDTO.getTareEndTime());
			map.put("totalStartTime", weighCarDTO.getTotalStartTime());
			map.put("totalEndTime", weighCarDTO.getTotalEndTime());
			map.put("carNumber", weighCarDTO.getCarNumber());
			map.put("orderNo", weighCarDTO.getOrderNo());
			if(!"-1".equals(weighCarDTO.getTapWeight())) {
				map.put("tapWeight", weighCarDTO.getTapWeight());
			}
			map.put("type", 2);
			if("1".equals(type)) {
				map.put("status", 1);
			} else {
				map.put("status", 2);
			}
			int total = carToolService.getTotal(map);
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

	@RequestMapping(value = "exportData/{type}")
	public String exportData(HttpServletRequest request,HttpServletResponse response,@PathVariable String type, WeighCarDTO weighCarDTO){

		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {

			// 获取参数
			Map<String, Object> map = carToolService.getParamsMap(weighCarDTO);

			if("1".equals(type)) {
				map.put("type", 2);
				map.put("status", 1);
			} else {
				map.put("type", 2);
				map.put("status", 2);
			}

			int total = carToolService.getTotal(map);

			map.put("startRow", 0);
			map.put("endRow", total);
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=\"report\".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			//获取查询结果
			List<WeighCarDTO> list = carToolService.getByConditionPage(map);

			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("1".equals(type)?"采购商皮重入场":"采购商总重出场", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "皮重登记时间");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "订单号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "总重登记时间");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "用户名");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "姓名");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "出入场车辆车牌 ");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "皮重（吨）");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "总重（吨） ");// 填充第一行第八个单元格的内容
				Label label80 = new Label(8, 0, "净重（吨）");// 填充第一行第九个单元格的内容
				Label label90 = new Label(9, 0, "订单总重");// 填充第一行第十个单元格的内容
				Label label100 = new Label(10, 0, "误差率");// 填充第一行第十个单元格的内容
				Label label110 = new Label(11, 0, "过磅人员");// 填充第一行第十个单元格的内容
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
						WeighCarDTO wcDTO = list.get(i);
						Label label0 = new Label(0, i + 1, wcDTO.getTareCreateTime()==null?"":time.format(wcDTO.getTareCreateTime()));

						String orders = "";
						if(wcDTO.getOrders()!=null) {
							for (int j = 0; j < wcDTO.getOrders().size(); j++) {
								orders += wcDTO.getOrders().get(j).getOrderNo() + ",";
							}
						}
						Label label1 = new Label(1, i + 1, orders);

						Label label2 = new Label(2, i + 1, wcDTO.getTotalCreateTime()==null?"":time.format(wcDTO.getTotalCreateTime()));
						Label label3 = new Label(3, i + 1, wcDTO.getAccount());
						Label label4 = new Label(4, i + 1, wcDTO.getMemberName());
						Label label5 = new Label(5, i + 1, wcDTO.getCarNumber());
						Label label6 = new Label(6, i + 1, wcDTO.getTare()!=null?wcDTO.getTare().toString():"");
						Label label7 = new Label(7, i + 1, wcDTO.getTotalWeight()!=null?wcDTO.getTotalWeight().toString():"");
						Label label8 = new Label(8, i + 1, wcDTO.getNetWeight()!=null?wcDTO.getNetWeight().toString():"");
						Label label9 = new Label(9, i + 1, wcDTO.getOrderWeigh()!=null?wcDTO.getOrderWeigh().toString():"");
						Label label010 = new Label(10, i + 1, wcDTO.getNetWeight()!=null?(wcDTO.getNetWeight()-wcDTO.getNetWeight()*0.1) + "~" + (wcDTO.getNetWeight()+wcDTO.getNetWeight()*0.1):"");
						Label label011 = new Label(10, i + 1, "1".equals(type)?wcDTO.getTareMember():wcDTO.getTotalMember());

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
