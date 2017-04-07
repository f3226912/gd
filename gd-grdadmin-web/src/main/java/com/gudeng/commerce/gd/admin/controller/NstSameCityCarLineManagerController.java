package com.gudeng.commerce.gd.admin.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.CarLineManageService;
import com.gudeng.commerce.gd.admin.service.CarsManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;


/**
 * 同城车辆专线管理
 * @author xiaodong
 */
@Controller
@RequestMapping("same_city_carLine")
public class NstSameCityCarLineManagerController extends AdminBaseController
{
	/** 记录日志 */
	//private static final GdLogger logger = GdLoggerFactory.getLogger(NstSameCityCarLineManagerController.class);
	
	@Autowired
	public CarLineManageService carLineManageService;
	
	
	@Autowired
	public CarsManageService carsManageService;
	
	@Autowired
	public QueryAreaService queryAreaService;
	
	@Autowired
	public MemberBaseinfoToolService  memberBaseinfoToolService;
	
	@Autowired
	public AreaSettingToolService areaSettingToolService;
	
	@Autowired
	public GdProperties gdProperties;

	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
			return "carLine/same_city_carline_list";
		} catch (Exception e) {
			return null;
		}

	}
	
	
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			// 设置查询参数
			// 记录数
			map.put("total", carLineManageService.getTotalForSameCityList(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<NstSameCityCarlineEntityDTO> list = carLineManageService.queryForSameCityList(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据检索条件，导出查询数据
	 */
	@RequestMapping("export")
	@ResponseBody
	public void export(String queryCarType,String mobile, String queryStartDate, String queryEndDate, String queryType, String  areaName, String isDel, HttpServletResponse response)  {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		map.put("carType", queryCarType);
		map.put("mobile", mobile);
		map.put("startDate", queryStartDate);
		map.put("endDate", queryEndDate);
		map.put("userType", queryType);
		map.put("areaName", areaName);
		map.put("startRow", 0);
		map.put("endRow", 100000000);
		//用来判断已进行软删除的的字段，导出已删除数据用
		map.put("isDel", isDel);
		try {
			List<NstSameCityCarlineEntityDTO> list = carLineManageService.queryForSameCityList(map);
			if (list != null && list.size() > 0) {
				convertPageList(list);
			    exportData(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void exportData(List<NstSameCityCarlineEntityDTO> list){
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + StringUtil.GenerateRandomStr() + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("线路信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "车牌号码");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "车辆类型 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "载重(吨)");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "车长(米)");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "用户类型 ");// 
				Label label50 = new Label(5, 0, "企业名称");// 
				Label label60 = new Label(6, 0, "联系人");// 填充第一行第五个单元格的内容
				Label label70 = new Label(7, 0, "电话号码 ");//
				Label label80 = new Label(8, 0, "发布时间 ");//
				Label label90 = new Label(9, 0, "发运方式 ");//
				Label label100 = new Label(10, 0, "价格");//
				Label label101 = new Label(11, 0, "出发地");//
				Label label102 = new Label(12, 0, "目的地");//
				Label label103 = new Label(13, 0, "所属区域");//
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
				sheet.addCell(label101);
				sheet.addCell(label102);
				sheet.addCell(label103);
				/*** 循环添加数据到工作簿 ***/
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NstSameCityCarlineEntityDTO dto = list.get(i);
						if(StringUtils.isEmpty(dto.getCarNumber()))
						{
						   continue;
						}
						Label label0 = new Label(0, i + 1, dto.getCarNumber());
						Label label1 = new Label(1, i + 1, dto.getTransportCarType());
						Label label2 = new Label(2, i + 1, String.valueOf(dto.getMaxLoad()));
						Label label3 = new Label(3, i + 1, String.valueOf(dto.getCarLength()));
						Label label4 = new Label(4, i + 1, dto.getUserType()!=null && dto.getUserType()==2 ? "企业" :"个人");
						Label label5 = new Label(5, i + 1, dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getRealName());
						Label label7 = new Label(7, i + 1, dto.getMobile());
						Label label8 = new Label(8, i + 1,  DateUtil.toString(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label9= new Label(9, i + 1, dto.getSendGoodsTypeString());
						Label _label10= new Label(10, i + 1, dto.getPriceString());
						Label _label11 = new Label(11, i + 1, dto.getS_detail());
						Label _label12 = new Label(12, i + 1, dto.getE_detail());
						Label _label13 = new Label(13, i + 1, dto.getAreaName());
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
						sheet.addCell(_label10);
						sheet.addCell(_label11);
						sheet.addCell(_label12);
						sheet.addCell(_label13);
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
	}

	/**
	 * 根据条件查询
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryByParameters" )
	@ResponseBody
    public String queryByParameters(@RequestParam String carType, @RequestParam String mobile, @RequestParam String startDate,@RequestParam String endDate, @RequestParam String queryType,   @RequestParam String  areaName,  HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("carType", carType);
			map.put("mobile", mobile);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("userType", queryType);
			map.put("areaName", areaName);
			// 记录数
			map.put("total", carLineManageService.getTotalForSameCityList(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<NstSameCityCarlineEntityDTO> list = carLineManageService.queryForSameCityList(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			String returnStr = JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
           e.printStackTrace();
		}
		return null;
	}
	

	private void convertPageList(List <NstSameCityCarlineEntityDTO> list) throws Exception {
		
		if (list != null && list.size() > 0) {
			for (NstSameCityCarlineEntityDTO dto : list) {
				 if (dto.getTransportCarTypeId()!= null) {
					switch (dto.getTransportCarTypeId()) {
					case "0":
						dto.setTransportCarType("小型面包");
						break;
					case "1":
						dto.setTransportCarType("金杯");
						break;
					case "2":
						dto.setTransportCarType("小型平板");
						break;
					case "3":
						dto.setTransportCarType("中型平板");
						break;
					case "4":
						dto.setTransportCarType("小型厢货");
						break;
					case "5":
						dto.setTransportCarType("大型厢货");
						break;
					}
				}
				
				NumberFormat nf = NumberFormat.getNumberInstance() ; 
				nf.setMaximumFractionDigits(2); 
				if (dto.getPrice() != null  && dto.getPrice()>0 ) {
					dto.setPriceString(dto.getPrice()+"元");
				}else{
					dto.setPriceString("面议");
				}

			}
		}
		
	
	}

	
	/**
	 * 新增路线中查询车辆列表页面
	 * @return
	 */
	@RequestMapping("carList")
	public String carSelect() {
		return "carLine/carList";
	}
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCar")
	@ResponseBody
	public String queryCarList(@RequestParam String carNumber,HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("carNumber", carNumber);
			// 设置查询参数
			//记录数
			map.put("total", carsManageService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<CarsDTO> list = carsManageService.getByCondition(map);
			if(list !=null && list.size() >0)
			{
				for(CarsDTO dto : list)
				{
					setCarTypeName(dto);
				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setCarTypeName(CarsDTO dto) {
		if (dto.getCarType() != null) {
			switch (dto.getCarType()) {
			case "0":
				dto.setCarTypeName("厢式货车");
				break;
			case "1":
				dto.setCarTypeName("敞车");
				break;
			case "2":
				dto.setCarTypeName("冷藏车");
				break;
			case "3":
				dto.setCarTypeName("保温车");
				break;
			case "4":
				dto.setCarTypeName("其他");
				break;
			case "5":
				dto.setCarTypeName("高栏车");
				break;
			case "6":
				dto.setCarTypeName("平板车");
				break;
			case "7":
				dto.setCarTypeName("活鲜水车");
				break; 
			case "8":
				dto.setCarTypeName("集装箱");
				break; 
			default:
				dto.setCarTypeName("其他");
				break;
			}
		}else if (dto.getTransportCarType()!= null) {
			switch (dto.getTransportCarType()) {
			case 0:
				dto.setCarTypeName("小型面包");
				break;
			case 1:
				dto.setCarTypeName("金杯");
				break;
			case 2:
				dto.setCarTypeName("小型平板");
				break;
			case 3:
				dto.setCarTypeName("中型平板");
				break;
			case 4:
				dto.setCarTypeName("小型厢货");
				break;
			case 5:
				dto.setCarTypeName("大型厢货");
				break;
			}
		}
			
	}
	
	
	
}
