package com.gudeng.commerce.info.home.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDetailDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.util.ReportsUtil;
import com.gudeng.commerce.info.home.service.ReportsToolService;
import com.gudeng.commerce.info.home.util.LoginUserUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@RequestMapping("reports")
@Controller
public class ReportsController extends AdminBaseController {
	
	private final GdLogger logger = GdLoggerFactory.getLogger(ReportsController.class);

	@Autowired
	private ReportsToolService reportsToolService;
	

	
	/**
	 * 图表详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getReports",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getReports(HttpServletRequest request){
		Map<Object, Object> map = new HashMap<>();
		Map<String, Object> parm = new HashMap<>();
		String parm1 =request.getParameter("startTime");//开始时间
		String parm2 =request.getParameter("endTime");//结束时间
		String menuID = request.getParameter("menuId");//分类id
		String reportsId =request.getParameter("reportsId");//报表id
		String markId = request.getParameter("markId");//市场id
		String userType = request.getParameter("usertype");//用户类型
		String userFrom = request.getParameter("userfrom");//用户来源
		String isMonth = request.getParameter("isMonth");//是否按月统计1是0否
		if(reportsId!=null){
			parm.put("reportsId", reportsId);
		}
		
		parm.put("menuID", menuID);
		parm.put("userCode", LoginUserUtil.getLoginUserCode(request));
		parm.put("markId", markId);
		parm.put("userType", userType);
		parm.put("userFrom", userFrom);
		if(isMonth!=null&&isMonth.equals("1")){
			parm.put("isMonth", isMonth);
		}

		if(parm1!=null&&parm2!=null){
			parm.put("parm1",parm1);
			parm.put("parm2",parm2);
		}else{
			if(isMonth!=null&&isMonth.equals("1")){
				//默认设置时间为最近七个月
				SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
				Date currentDate = new Date();
				Calendar calendar = Calendar.getInstance(); 
				calendar.setTime(currentDate);
				calendar.add(Calendar.MONTH, -6); 
				String startDate =dft.format(calendar.getTime());
				calendar.clear();
				calendar.setTime(currentDate);
				calendar.add(Calendar.MONTH, 0); 
				String endDate =dft.format(calendar.getTime());
				parm.put("parm1",startDate);
				parm.put("parm2",endDate);
			}else{
				//默认设置时间为最近七天
				SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				Calendar calendar = Calendar.getInstance(); 
				calendar.setTime(currentDate);
				calendar.add(Calendar.DAY_OF_MONTH, -7); 
				String startDate =dft.format(calendar.getTime());
				calendar.clear();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DAY_OF_MONTH, -1); 
				String endDate =dft.format(calendar.getTime());
				parm.put("parm1",startDate);
				parm.put("parm2",endDate);
				
			}
			
		}
		try {
		//获取当前用户当前类目下拥有的图表
		List<ReportsDTO>  reports=reportsToolService.getReportsList(parm);
		SimpleDateFormat format;
		if(isMonth!=null&&isMonth.equals("1")){
			 format = new SimpleDateFormat("YYMM");
		}else{
			 format = new SimpleDateFormat("MMdd");
		}
		
		//交易流水
		if(menuID.equals("16")){
			List<ProBszbankDTO>  proBszbank=reportsToolService.getProBszbankList(parm);
			 
			 if(proBszbank==null){
					logger.info("proBszbank is null");
					map.put("FAIL", "proBszbank is null");
					return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
				}
			 
				for (int i = 0; i < reports.size(); i++) {
					Long id =reports.get(i).getId();//id
					
					ProBszbankDTO proBszbankj;
					List<String> parms1=new ArrayList<>();
					List<Number> data1 = new ArrayList<>();
					List<Number> data2 = new ArrayList<>();
					List<Number> data3 = new ArrayList<>();
					List<Number> data4 = new ArrayList<>();
					String color1=ReportsUtil.Color.BLUE.value();//data1颜色
					String color2=ReportsUtil.Color.BLUE.value();//data2颜色
					String color3=ReportsUtil.Color.BLUE.value();//data3颜色
					String color4=ReportsUtil.Color.BLUE.value();//data4颜色
					String type1=ReportsUtil.Type.TYPE_1.value();//图表类型
					String type2=ReportsUtil.Type.TYPE_1.value();//图表类型
					String type3=ReportsUtil.Type.TYPE_1.value();//图表类型
					String type4=ReportsUtil.Type.TYPE_1.value();//图表类型
					String typeY1=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
					String typeY2=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
					String typeY3=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
					String typeY4=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
					Object sum = 0;
					for (int j = 0; j < proBszbank.size(); j++) {
						 proBszbankj =proBszbank.get(j);
						if(proBszbankj.getReportsID()==id){
							//日交易金额
							if(id==1){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number transactionAmount=proBszbankj.getTransactionAmount()==null?0:proBszbankj.getTransactionAmount();//日交易金额
								sum=Double.parseDouble(sum.toString())+Double.parseDouble(transactionAmount.toString());
								data1.add(transactionAmount);
								typeY1=ReportsUtil.TypeY.TYPEY_MONEY.value();
							}
							//日订单量
							if(id==2){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number orderNumbers=proBszbankj.getOrderNumbers()==null?0:proBszbankj.getOrderNumbers();	//日订单量
								sum=Integer.parseInt(sum.toString())+Integer.parseInt(orderNumbers.toString());
								data1.add(orderNumbers);
								color1=ReportsUtil.Color.ORANGE.value();//橙色
								
							}
							//日均订单量
							if(id==3){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number orderAmount=proBszbankj.getOrderAmount()==null?0:proBszbankj.getOrderAmount();//日均订单量
								sum=Double.parseDouble(sum.toString())+Double.parseDouble(orderAmount.toString());
								data1.add(orderAmount);
							}
							//日交易用户数
							if(id==4){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number transactionUsers=proBszbankj.getTransactionUsers()==null?0:proBszbankj.getTransactionUsers();//日交易用户数
								data1.add(transactionUsers);
								sum=Integer.parseInt(sum.toString())+Integer.parseInt(transactionUsers.toString());
								color1=ReportsUtil.Color.PURPLE.value();//紫色
							}
							//新老用户统计
							if(id==5){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number newUsers=proBszbankj.getNewUsers();
								Number oldUsers=proBszbankj.getOldUsers();
								data1.add(newUsers==null?0:newUsers);//新用户
								data2.add(oldUsers==null?0:oldUsers);//老用户
								color2=ReportsUtil.Color.GARNET.value();//老用户颜色
								type1=ReportsUtil.Type.TYPE_2.value();
								type2=ReportsUtil.Type.TYPE_2.value();
								sum=null;
							}
							//日均用户交易额
							if(id==6){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number usersAmount=proBszbankj.getUsersAmount()==null?0:proBszbankj.getUsersAmount();	//日均用户交易额
								sum=Double.parseDouble(sum.toString())+Double.parseDouble(usersAmount.toString());
								data1.add(usersAmount);
							}
							//每日订单交易情况
							if(id==7){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number transactionAmount =proBszbankj.getTransactionAmount();//当日交易总金额
								data1.add(transactionAmount==null?0:transactionAmount);
								type1=ReportsUtil.Type.TYPE_2.value();
								Number usersAmount =proBszbankj.getOrderAmount();//日均订单额
								data2.add(usersAmount==null?0:usersAmount);
								color2=ReportsUtil.Color.GARNET.value();
								Number orderNumbers =proBszbankj.getOrderNumbers();//日订单数
								data3.add(orderNumbers==null?0:orderNumbers);
								color3=ReportsUtil.Color.GREEN.value();
								sum=null;
							}
							//订单交易用户成交量走势
							if(id==8){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								Number orderNumbers =proBszbankj.getOrderNumbers();//日订单数
								data1.add(orderNumbers==null?0:orderNumbers);
							    
								Number transactionAmount=proBszbankj.getTransactionUsers();//日交易用户数
								data2.add(transactionAmount==null?0:transactionAmount);
								color2=ReportsUtil.Color.GARNET.value();
								Number userTradeAvg =proBszbankj.getUserTradeAvg();//日订均用户交易额
								data3.add(userTradeAvg==null?0:userTradeAvg);
								color3=ReportsUtil.Color.GREEN.value();
								sum=null;
							}
							//新老用户交易数据对比
							if(id==9){
								parms1.add(format.format(proBszbankj.getDatatimes()));//时间
								//当日新增用户平均单价
								data1.add(proBszbankj.getNewUserAvgUnitPrice()==null?0:proBszbankj.getNewUserAvgUnitPrice());
								color1=ReportsUtil.Color.GREEN.value();
								type1=ReportsUtil.Type.TYPE_2.value();
								data2.add(proBszbankj.getOldUserAvgUnitPrice()==null?0:proBszbankj.getOldUserAvgUnitPrice());//当日老用户平均单价
								color2=ReportsUtil.Color.ORANGE.value();
								type2=ReportsUtil.Type.TYPE_2.value();
								data3.add(proBszbankj.getNewUsers()==null?0:proBszbankj.getNewUsers());//newUsers
								color3=ReportsUtil.Color.GARNET.value();
								data4.add(proBszbankj.getOldUsers()==null?0:proBszbankj.getOldUsers());//oldUsers
								sum=null;
							}
						}
					}
					ReportDataDTO reportData =new ReportDataDTO(); 
					List<Map<String,ReportDataDetailDTO>> ReportDataDetailList = new ArrayList<>();
					Map<String,ReportDataDetailDTO> reportDataDetailLMap = new HashMap<>();
					//----------------------------------------------
					ReportDataDetailDTO data_1 = new ReportDataDetailDTO();
					data_1.setData(data1);
					data_1.setColor(color1);
					data_1.setType(type1);
					data_1.setTypeY(typeY1);
					data_1.setParms(reports.get(i).getParameter1());
					reportDataDetailLMap.put(ReportsUtil.DATA1,data_1); 
					if(id==5||id==7||id==8||id==9){
						ReportDataDetailDTO data_2 = new ReportDataDetailDTO();
						data_2.setData(data2);
						data_2.setColor(color2);
						data_2.setType(type2);
						data_2.setTypeY(typeY2);
						data_2.setParms(reports.get(i).getParameter2());
						reportDataDetailLMap.put(ReportsUtil.DATA2,data_2); 
						
						if(id==7||id==8||id==9){
							ReportDataDetailDTO data_3 = new ReportDataDetailDTO();
							data_3.setData(data3);
							data_3.setColor(color3);
							data_3.setType(type3);
							data_3.setTypeY(typeY3);
							data_3.setParms(reports.get(i).getParameter3());
							reportDataDetailLMap.put(ReportsUtil.DATA3,data_3); 
							if(id==9){
								ReportDataDetailDTO data_4 = new ReportDataDetailDTO();
								data_4.setData(data4);
								data_4.setColor(color4);
								data_4.setType(type4);
								data_4.setTypeY(typeY4);
								data_4.setParms(reports.get(i).getParameter4());
								reportDataDetailLMap.put(ReportsUtil.DATA4,data_4); 
							}
						}
					}
					
					//----------------------------------------------
					ReportDataDetailList.add(reportDataDetailLMap);
					reportData.setParms(parms1);
					reportData.setData(ReportDataDetailList);
					if(isMonth!=null&&isMonth.equals("1")){
						reports.get(i).setSumDate(ReportsUtil.monthBetween(parm.get("parm2").toString(),parm.get("parm1").toString()));//时间合计
					}else{
						reports.get(i).setSumDate(ReportsUtil.daysBetween(parm.get("parm1").toString(), parm.get("parm2").toString()));//时间合计
					}
					
					reports.get(i).setData(reportData);
				}	
		}
	    //百度统计
		if(menuID.equals("17")){
			List<ProBaiduEntityDTO>  proBaiduEntity=reportsToolService.getbaiduList(parm);
			if(proBaiduEntity==null){
				logger.info("proBaiduEntity is null");
				map.put("FAIL", "proBaiduEntity is null");
				return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			}
		 
			for (int i = 0; i < reports.size(); i++) {
				Long id =reports.get(i).getId();//id
				
				ProBaiduEntityDTO proBaidu;
				List<String> parms1=new ArrayList<>();
				List<Number> data1 = new ArrayList<>();
				List<Number> data2 = new ArrayList<>();
				List<Number> data3 = new ArrayList<>();
			
				String color1=ReportsUtil.Color.BLUE.value();//data1颜色
				String color2=ReportsUtil.Color.BLUE.value();//data2颜色
				String color3=ReportsUtil.Color.BLUE.value();//data3颜色
				String type1=ReportsUtil.Type.TYPE_1.value();//图表类型
				String type2=ReportsUtil.Type.TYPE_1.value();//图表类型
				String type3=ReportsUtil.Type.TYPE_1.value();//图表类型
				String typeY1=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
				String typeY2=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
				String typeY3=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
				Object sum = 0;
				for (int j = 0; j < proBaiduEntity.size(); j++) {
					 proBaidu =proBaiduEntity.get(j);
					if(proBaidu.getReportsID()==id){
						//用户访问统计报表
						if(id==10){
							parms1.add(format.format(proBaidu.getDatatimes()));//时间
							data1.add(proBaidu.getUVcount()==null?0:proBaidu.getUVcount());//访客数
							type1=ReportsUtil.Type.TYPE_2.value();
							data2.add(proBaidu.getPVcount()==null?0:proBaidu.getPVcount());//浏览量数据
							color2=ReportsUtil.Color.GARNET.value();
							type2=ReportsUtil.Type.TYPE_2.value();
							data3.add(proBaidu.getAvvisitsum()==null?0:proBaidu.getAvvisit());//访问深度
							color3=ReportsUtil.Color.GREEN.value();
							sum=null;
						}
						//新增用户访问量统计
						if(id==11){
							parms1.add(format.format(proBaidu.getDatatimes()));//时间
							data1.add(proBaidu.getUVcount()==null?0:proBaidu.getUVcount());//访客数
							data2.add(proBaidu.getNewuser()==null?0:proBaidu.getNewuser());//新增用户数
							color2=ReportsUtil.Color.GARNET.value();
							type1=ReportsUtil.Type.TYPE_2.value();
							type2=ReportsUtil.Type.TYPE_2.value();
							sum=null;
						}
						//IP访问统计
						if(id==12){
							parms1.add(format.format(proBaidu.getDatatimes()));//时间
							Number ip=proBaidu.getIPcount()==null?0:proBaidu.getIPcount();
							data1.add(ip);//IP数
							sum=Double.parseDouble(sum.toString())+Double.parseDouble(ip.toString());
						}
					}
				}
				ReportDataDTO reportData =new ReportDataDTO(); 
				List<Map<String,ReportDataDetailDTO>> ReportDataDetailList = new ArrayList<>();
				Map<String,ReportDataDetailDTO> reportDataDetailLMap = new HashMap<>();
				//----------------------------------------------
				ReportDataDetailDTO data_1 = new ReportDataDetailDTO();
				data_1.setData(data1);
				data_1.setColor(color1);
				data_1.setType(type1);
				data_1.setTypeY(typeY1);
				data_1.setParms(reports.get(i).getParameter1());
				reportDataDetailLMap.put(ReportsUtil.DATA1,data_1); 
				if(id==10||id==11){
					ReportDataDetailDTO data_2 = new ReportDataDetailDTO();
					data_2.setData(data2);
					data_2.setColor(color2);
					data_2.setType(type2);
					data_2.setTypeY(typeY2);
					data_2.setParms(reports.get(i).getParameter2());
					reportDataDetailLMap.put(ReportsUtil.DATA2,data_2); 
					if(id==10){
						ReportDataDetailDTO data_3 = new ReportDataDetailDTO();
						data_3.setData(data3);
						data_3.setColor(color3);
						data_3.setType(type3);
						data_3.setTypeY(typeY3);
						data_3.setParms(reports.get(i).getParameter3());
						reportDataDetailLMap.put(ReportsUtil.DATA3,data_3); 
					}
				}
				
				//----------------------------------------------
				ReportDataDetailList.add(reportDataDetailLMap);
				reportData.setParms(parms1);
				reportData.setData(ReportDataDetailList);

				if(isMonth!=null&&isMonth.equals("1")){
					reports.get(i).setSumDate(ReportsUtil.monthBetween(parm.get("parm2").toString(),parm.get("parm1").toString()));//时间合计
				}else{
					reports.get(i).setSumDate(ReportsUtil.daysBetween(parm.get("parm1").toString(), parm.get("parm2").toString()));//时间合计
				}
				reports.get(i).setData(reportData);
			}	
			
		}
		
		
		 //运营
		if(menuID.equals("13")){
			List<ProOperateDTO>  proOperateEntity=reportsToolService.getOperateList(parm);
			if(proOperateEntity==null){
				logger.info("ProOperate is null");
				map.put("FAIL", "proBszbank is null");
				return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			}
		 
			for (int i = 0; i < reports.size(); i++) {
				Long id =reports.get(i).getId();//id
				
				ProOperateDTO proOperate;
				List<String> parms1=new ArrayList<>();
				List<Number> data1 = new ArrayList<>();
				List<Number> data2 = new ArrayList<>();
			
				String color1=ReportsUtil.Color.BLUE.value();//data1颜色
				String color2=ReportsUtil.Color.BLUE.value();//data2颜色
				String type1 = ReportsUtil.Type.TYPE_1.value();//图表类型
				String type2=ReportsUtil.Type.TYPE_1.value();//图表类型
				String typeY1=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
				String typeY2=ReportsUtil.TypeY.TYPEY_NUMBER.value();//Y轴类型：1金额类型2：数字类型
				for (int j = 0; j < proOperateEntity.size(); j++) {
					 proOperate =proOperateEntity.get(j);
					if(proOperate.getReportsID()==id){
						//用户访问统计报表
						if(id==13){
							parms1.add(format.format(proOperate.getDatatimes()));//时间
							data1.add(proOperate.getComRegCount()==null?0:proOperate.getComRegCount());//当日平台注册用户数
							data2.add(proOperate.getGrowthRate()==null?0:proOperate.getGrowthRate());//环比增长率
							color2=ReportsUtil.Color.GARNET.value();
							type1=ReportsUtil.Type.TYPE_2.value();
						}
						//农商友用户注册数据分析
						if(id==14){
							parms1.add(format.format(proOperate.getDatatimes()));//时间
							data1.add(proOperate.getComRegCount()==null?0:proOperate.getNsyRegCount());//当日农商友注册用户
							data2.add(proOperate.getGrowthRate()==null?0:proOperate.getGrowNsyRegCount());//农商友环比增长率
							color2=ReportsUtil.Color.GARNET.value();
							type1=ReportsUtil.Type.TYPE_2.value();
						}
						//谷登农批用户注册数据分析
						if(id==15){
							parms1.add(format.format(proOperate.getDatatimes()));//时间
							data1.add(proOperate.getComRegCount()==null?0:proOperate.getShopRegCount());//当日商铺注册用户
							data2.add(proOperate.getGrowthRate()==null?0:proOperate.getGrowthRate());//环比增长率
							color2=ReportsUtil.Color.GARNET.value();
							type1=ReportsUtil.Type.TYPE_2.value();
						}
					}
				}
				ReportDataDTO reportData =new ReportDataDTO(); 
				List<Map<String,ReportDataDetailDTO>> ReportDataDetailList = new ArrayList<>();
				Map<String,ReportDataDetailDTO> reportDataDetailLMap = new HashMap<>();
				//----------------------------------------------
				ReportDataDetailDTO data_1 = new ReportDataDetailDTO();
				data_1.setData(data1);
				data_1.setColor(color1);
				data_1.setType(type1);
				data_1.setTypeY(typeY1);
				data_1.setParms(reports.get(i).getParameter1());
				reportDataDetailLMap.put(ReportsUtil.DATA1,data_1); 
				if(id==13||id==14||id==15){
					ReportDataDetailDTO data_2 = new ReportDataDetailDTO();
					data_2.setData(data2);
					data_2.setColor(color2);
					data_2.setType(type2);
					data_2.setTypeY(typeY2);
					data_2.setParms(reports.get(i).getParameter2());
					reportDataDetailLMap.put(ReportsUtil.DATA2,data_2); 
				
				}
				
				//----------------------------------------------
				ReportDataDetailList.add(reportDataDetailLMap);
				reportData.setParms(parms1);
				reportData.setData(ReportDataDetailList);
				if(isMonth!=null&&isMonth.equals("1")){
					reports.get(i).setSumDate(ReportsUtil.monthBetween(parm.get("parm2").toString(),parm.get("parm1").toString()));//时间合计
				}else{
					reports.get(i).setSumDate(ReportsUtil.daysBetween(parm.get("parm1").toString(), parm.get("parm2").toString()));//时间合计
				}
				reports.get(i).setData(reportData);
			}	
			
		}
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, -1); 
			map.put("currentTime", dft.format(calendar.getTime()));
			map.put("rows", reports);//报表数据
			if(reports!=null){
				map.put("size", reports.size());//报表个数
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("FAIL","FAIL");
			logger.warn(e.getMessage());
		}
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
	}

}
