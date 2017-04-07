package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.SubHelpToolService;
import com.gudeng.commerce.gd.admin.service.WeighCarToolService;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.SalToshopsDetailDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("/shipper")
public class ShipperWeighController  extends AdminBaseController{

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CashRequestController.class);
	
	@Autowired
	private WeighCarToolService weighCarToolService;
	
	@Autowired
	private SubHelpToolService helpService;
	
	@RequestMapping("entranceWeigh")
	public String entranceWeigh(HttpServletRequest request){
		//表示是货主入场过磅
		request.setAttribute("state", "1");
		//1:已进场 过磅表 入场
		request.setAttribute("status", "1");
		return "shipper/entranceWeighList";
	}
	
	@RequestMapping("marchOutTare")
	public String marchOutTare(HttpServletRequest request){
		//表示是货主出场去皮
		request.setAttribute("state", "2");
		//2:已出场
		request.setAttribute("status", "2");
		return "shipper/entranceWeighList";
	}
	
	@RequestMapping("entranceWeighList")
	@ResponseBody
	public String entranceWeighList(HttpServletRequest request,WeighCarDTO weighCarDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			//判读是货主入场过磅（1），还是货主出场去皮（2）
			map.put("state", request.getParameter("state"));
			//过磅状态 0:未进场,1:已进场,2:已出场
			map.put("status", weighCarDTO.getStatus());
			//会员账号
			map.put("account", weighCarDTO.getAccount());
			//车牌号码
			map.put("carNumber", weighCarDTO.getCarNumber());
			//皮重检测开始时间
			map.put("tareStartTime", weighCarDTO.getTareStartTime());
			//皮重检测结束时间
			map.put("tareEndTime", weighCarDTO.getTareEndTime());
			//总重检测开始时间
			map.put("totalStartTime", weighCarDTO.getTotalStartTime());
			//总重检测结束时间
			map.put("totalEndTime", weighCarDTO.getTotalEndTime());
			//称重标识符
			map.put("tapWeight", weighCarDTO.getTapWeight());
			
			//设置总记录数
			map.put("total", weighCarToolService.getEntranceWeighListTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<WeighCarDTO> list = weighCarToolService.getEntranceWeighList(map);
			
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("entranceWeighInfo")
	public String entranceWeighInfo(@RequestParam String weighCarId,@RequestParam String status,Map<String,Object> map ){
		
		try {
			WeighCarDTO wc=new WeighCarDTO();
			
			String weigh=request.getParameter("weigh");
			if (weighCarId!=null) {
				logger.info("过磅ID传来不能为空");
				//根据Id查出过磅详细信息
				wc=weighCarToolService.getEntranceWeighById(weighCarId);
				//从页面传出产品总重
				if (StringUtil.isNotEmpty(weigh)) {
					wc.setWeigh(Double.parseDouble(weigh));
				}
				//过磅详细信息,页面显示
				putModel("dto", wc);
				
				if ("1".equals(status)) {
					//如果status=1 查询入场的商品
					List<PreWeighCarDetailDTO> pcDtoList=new ArrayList<>();
					pcDtoList=weighCarToolService.getProductListByWeighCarId(weighCarId);
					//商品详细信息,页面显示
					putModel("pcDtoList", pcDtoList);
				}else if ("2".equals(status)) {
					//如果status=2查询出场的商品
					List<SalToshopsDetailDTO> pcDtoList=new ArrayList<>();
					pcDtoList=weighCarToolService.getOutProductListByWeighCarId(weighCarId);
					//商品详细信息,页面显示
					putModel("pcDtoList", pcDtoList);
				}
				
			}
			
			//根据车牌号 carNumber 查询车辆通过频率
			Map<String, Integer> counts = helpService.getSubOutMarket(wc.getCarNumber(),status, wc.getMarketId(), new Date());
			putModel("counts", counts);
			
			if (wc.getCarNumberImage()!=null) {
				//照片
				putModel("carNumberImages", wc.getCarNumberImage().split("[|]"));
			}
			
			return "shipper/entranceWeighInfo";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		}
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,HttpServletResponse response,WeighCarDTO weighCarDTO){
		Map<String, Object> map = new HashMap<>();
		//判读是货主入场过磅（1），还是货主出场去皮（2）
		map.put("state", request.getParameter("state"));
		
		map.put("status", request.getParameter("status"));
		
		map.put("account", weighCarDTO.getAccount());
		
		map.put("carNumber", weighCarDTO.getCarNumber());
		
		map.put("tareStartTime", weighCarDTO.getTareStartTime());
		
		map.put("tareEndTime", weighCarDTO.getTareEndTime());
		
		map.put("totalStartTime", weighCarDTO.getTotalStartTime());
		
		map.put("totalEndTime", weighCarDTO.getTotalEndTime());
		
		map.put("tapWeight", weighCarDTO.getTapWeight());
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
			List<WeighCarDTO> list = weighCarToolService.getEntranceWeighList(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
	
				WritableSheet sheet = wwb.createSheet("1".equals(weighCarDTO.getStatus())?"货主入场过磅列表":"货主出场去皮", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "车辆车牌");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "皮重登记时间");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "总重登记时间");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "用户账号");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "姓名");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "皮重(吨)");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "总重(吨)");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "净重(吨)");// 填充第一行第八个单元格的内容
				Label label80 = new Label(8, 0, "产品总重 ");// 填充第一行第九个单元格的内容
				Label label90 = new Label(9, 0, "结误差率 ");// 填充第一行第十个单元格的内容
				Label labe200 = new Label(10, 0, "过磅人员 ");// 填充第一行第十-个单元格的内容
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
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						WeighCarDTO wDto  = list.get(i);
						Label label0 = new Label(0, i + 1, wDto.getCarNumber());
						Label label2 = new Label(1, i + 1, wDto.getTareCreateTime()==null?"":time.format(wDto.getTareCreateTime()));
						Label label3 = new Label(2, i + 1, wDto.getTotalCreateTime()==null?"":time.format(wDto.getTotalCreateTime()));
						Label label5 = new Label(3, i + 1, wDto.getAccount());
						Label label1 = new Label(4, i + 1, wDto.getMemberName());
						Label label4 = new Label(5, i + 1, wDto.getTare()==null?"":wDto.getTare().toString());
						Label label6 = new Label(6, i + 1, wDto.getTotalWeight()==null?"":wDto.getTotalWeight().toString());
						Label label7 = new Label(7, i + 1, wDto.getNetWeight()==null?"":wDto.getNetWeight().toString());
						
						Label label8 = new Label(8, i + 1, wDto.getWeigh()==null?"":wDto.getWeigh().toString());
						Label label9 = new Label(9, i + 1, wDto.getRates()==null?"":wDto.getRates().toString());
						Label labe20 = new Label(10, i + 1, wDto.getWeighMember());
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
