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
import com.gudeng.commerce.gd.admin.service.MemberAddressManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.MemberCertifiToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.EOrderStatusType;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;

/**
 *同城货源管理Controller
 * 
 * @author
 */
@Controller
@RequestMapping("same_city_address")
public class NstSameCtiyAddressManagerController extends AdminBaseController {
	/** 记录日志 */
	//private static final GdLogger logger = GdLoggerFactory.getLogger(NstSameCtiyAddressManagerController.class);

	@Autowired
	public MemberAddressManageService memberAddressManageService;

	@Autowired
	public QueryAreaService queryAreaService;

	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired                                                                                                                      
	public MemberCertifiToolService memberCertifiToolService;

	/**
	 * memberAddress
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String memberAddress(HttpServletRequest request) {
		return "memberAddress/same_city_address_list";
	}

	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			// 设置查询参数
			// 记录数
			map.put("total", memberAddressManageService.getTotalForConsole(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<NstSameCityAddressDTO> list = memberAddressManageService.queryListForConsole(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据条件查询
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryByParams")
	@ResponseBody
	public String queryByParams(@RequestParam String realName,@RequestParam String startDate,@RequestParam String endDate, @RequestParam String queryType,
			@RequestParam String orderStatus, @RequestParam String cityName, @RequestParam String isOrderDeleted, @RequestParam String clients, HttpServletRequest request) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotEmpty(cityName)) {
				AreaDTO area = queryAreaService.getAreaByName(cityName);
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			map.put("realName", realName);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("userType", queryType);
			map.put("orderStatus", orderStatus);
			map.put("isOrderDeleted", isOrderDeleted);
			map.put("clients", clients);
			// 记录数
			map.put("total", memberAddressManageService.getTotalForConsole(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<NstSameCityAddressDTO> list = memberAddressManageService.queryListForConsole(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			String returnStr = JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void convertPageList(List<NstSameCityAddressDTO> list) throws Exception {

		if (list != null && list.size() > 0) {
			for (NstSameCityAddressDTO dto : list) {
				if (dto.getGoodsType() != null) {
					switch (dto.getGoodsType()) {
					case 0:
						dto.setGoodsTypeString("普货");
						break;
					case 1:
						dto.setGoodsTypeString("冷藏");
						break;
					case 2:
						dto.setGoodsTypeString("鲜活水产");
						break;
					case 3:
						dto.setGoodsTypeString("其他");
						break;
					case 4:
						dto.setGoodsTypeString("重货");
						break;
					case 5:
						dto.setGoodsTypeString("抛货");
						break;
					case 6:
						dto.setGoodsTypeString("蔬菜");
						break;
					case 7:
						dto.setGoodsTypeString("水果");
						break;
					case 8:
						dto.setGoodsTypeString("农副产品");
						break;
					case 9:
						dto.setGoodsTypeString("日用品");
						break;
					case 10:
						dto.setGoodsTypeString("纺织");
						break;
					case 11:
						dto.setGoodsTypeString("木材");
						break;
					case 101:
						dto.setGoodsTypeString("蔬菜水果");
						break;
					case 102:
						dto.setGoodsTypeString("干调粮油");
						break;
					case 103:
						dto.setGoodsTypeString("活鲜水产");
						break;
					case 104:
						dto.setGoodsTypeString("食品饮料");
						break;
					case 105:
						dto.setGoodsTypeString("冷冻商品");
						break;
					case 106:
						dto.setGoodsTypeString("化肥种子");
						break;
					case 107:
						dto.setGoodsTypeString("农资农具");
						break;
					case 108:
						dto.setGoodsTypeString("日用品");
						break;
					case 109:
						dto.setGoodsTypeString("建材设备");
						break;
					case 110:
						dto.setGoodsTypeString("其他商品");
						break;
					default:
						break;
					}
				}
				
				NumberFormat weightFormat = NumberFormat.getNumberInstance();
				weightFormat.setMaximumFractionDigits(1);
				if (dto.getHundredweight() != null) {
					if (dto.getTotalWeight() != null && dto.getTotalWeight() > 0) {
						switch (dto.getHundredweight()) {
						case 0:
							dto.setTotalWeightString(weightFormat.format(dto.getTotalWeight()) + "吨");
							break;
						case 1:
							dto.setTotalWeightString(weightFormat.format(dto.getTotalWeight()) + "公斤");
							break;
						default:
							break;
						}
					}
				}else if(dto.getTotalWeight() != null && dto.getTotalWeight() > 0) {
				 dto.setTotalWeightString(weightFormat.format(dto.getTotalWeight()) + "吨");
				}
				//订单状态
				dto.setShowGoodsStatus(dto.getOrderStatus()!=null ? EOrderStatusType.getValueByCode(dto.getOrderStatus()) :"待接单");

				if(MemberBaseinfoEnum.USER_TYPE_2.getValue().equals(dto.getUserType()) && StringUtils.isNotEmpty(dto.getCreateUserId()) )
				{
					MemberCertifiDTO  mc =memberCertifiToolService.getByUserId(dto.getCreateUserId());
					if(mc!=null)
					{
						dto.setCompanyName(mc.getCompanyName());
						dto.setCompanyMobile(mc.getMobile());
					}
				}
			}
		}
	
	}



	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String realName,String startDate,
			String endDate, String queryType, String cityName, String isDel, String orderStatus , String isOrderDeleted, String clients) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		if (StringUtils.isNotEmpty(cityName)) {
			try {
				AreaDTO area = queryAreaService.getAreaByName(cityName);
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("realName", realName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("userType", queryType);
		map.put("orderStatus", orderStatus);
		map.put("isOrderDeleted", isOrderDeleted);
		map.put("clients", clients);

		// 记录数
		// list
		map.put("startRow", 0);
		map.put("endRow", 100000000);
	
		//用来判断已进行软删除的的字段，导出已删除数据用
		map.put("isDel", isDel);
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + StringUtil.GenerateRandomStr() + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<NstSameCityAddressDTO> list = memberAddressManageService.queryListForConsole(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("同城货源信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "始发地");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "目的地 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "姓名");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "手机号码 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "用户类型 ");// 
				Label label50 = new Label(5, 0, "企业名称");// 
				Label label60 = new Label(6, 0, "企业联系电话");// 
				Label label70 = new Label(7, 0, "总重量 ");//
				Label label80 = new Label(8, 0, "总体积 ");//
				Label label90 = new Label(9, 0, "货物类型");//
				Label label100 = new Label(10, 0, "价格(元)");//
				Label label101 = new Label(11, 0, "发布时间");//
				Label label102 = new Label(12, 0, "发布来源");//
				Label label103= new Label(13, 0, "常用城市");//
				Label label104= new Label(14, 0, "订单状态");//
				Label label105= new Label(15, 0, "订单是否删除");//
				
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
				sheet.addCell(label104);
				sheet.addCell(label105);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					convertPageList(list);
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NstSameCityAddressDTO dto = list.get(i);
/*						String fbly = null;
						switch(dto.getClients()){
						case 1:fbly="谷登农批";
						break;
						case 2:fbly="农速通";
						break;
						case 3:fbly="农商友";
						break;
						case 4:fbly="产地供应商";
						break;
						case 5:fbly="农商友-农批商";
						break;
						}*/
						Label label0 = new Label(0, i + 1, dto.getS_name());
						Label label1 = new Label(1, i + 1, dto.getF_name());
						Label label2 = new Label(2, i + 1, dto.getLinkMan());
						Label label3 = new Label(3, i + 1, dto.getMobile());
						Label label4 = new Label(4, i + 1, dto.getUserType());
						Label label5 = new Label(5, i + 1, dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getCompanyMobile());
						Label label7 = new Label(7, i + 1, dto.getTotalWeightString());
						Label label8= new Label(8, i + 1, dto.getTotalSize()==null?"":String.valueOf(dto.getTotalSize()));
						Label label9= new Label(9, i + 1, String.valueOf(dto.getGoodsTypeString()));
						Label label100_= new Label(10, i + 1,  dto.getPrice()==null || dto.getPrice()==0 ? "面议" :dto.getPrice()+"元");
						Label label101_ = new Label(11, i + 1, DateUtil.toString(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label102_ = new Label(12, i + 1, getPublishType(dto.getClients()==null?null:dto.getClients().toString()));
						Label label103_ = new Label(13, i + 1, dto.getMcity());
						Label label104_ = new Label(14, i + 1, dto.getOrderStatus()!=null ? EOrderStatusType.getValueByCode(dto.getOrderStatus()) :"待接单");
						Label label105_ = new Label(15, i + 1, "1".equals(dto.getIsOrderDeleted())? "已删除":"未删除");

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
						sheet.addCell(label100_);
						sheet.addCell(label101_);
						sheet.addCell(label102_);
						sheet.addCell(label103_);
						sheet.addCell(label104_);
						sheet.addCell(label105_);
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
	
	private String getPublishType(String client) {
		//'1谷登农批，2农速通，3农商友，4产地供应商，5农商友-农批商',
		String type = null;
		if ("1".equals(client)) {
			type = "谷登农批";
		} else if ("2".equals(client) || client==null ) {
			type = "农速通";
		} else if ("3".equals(client)) {
			type = "农商友";
		} else if ("4".equals(client)) {
			type = "产地供应商";
		}else if ("5".equals(client)) {
			type = "农商友-农批商";
		}
		return type;
	}
	
	
	
}
