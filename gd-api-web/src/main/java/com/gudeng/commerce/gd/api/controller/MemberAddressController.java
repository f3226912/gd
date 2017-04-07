package com.gudeng.commerce.gd.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.converter.DateStringConverter;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.util.AddCarLineMessageThread;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.exception.BusinessException;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

/**
 * 货源发布管理
 * 
 * @author yanghaoyu
 * 
 */
@Controller
@RequestMapping("memberAddress")
public class MemberAddressController extends GDAPIBaseController {

		private static final GdLogger logger = GdLoggerFactory
				.getLogger(FocusCategoryController.class);
		@Autowired
		public ProCategoryService productCategory;
		@Autowired
		public UsercollectProductCategoryToolService usercollectProductCategoryService;
		@Autowired
		public MemberToolService memberToolService;
		@Autowired
		public GdProperties gdProperties;
		@Autowired
		public MemberAddressApiService memberAddressApiService;
		@Autowired
		private MemberCertifiApiService memberCertifiApiService;
		@Autowired
		private AreaToolService areaToolService;
		/**
		 * 获取用户发布货源信息列表
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/listMemberAddressByUserId")
		public void listProductCategory(HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				memberAddressDTO.setEndRow(1000);
				List<MemberAddressDTO> list = memberAddressApiService.getMemberAddressByUserId(memberAddressDTO);
				//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getPrice()!=null&&list.get(i).getSendGoodsType()!=null){
				    if(list.get(i).getSendGoodsType()==0){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}
				    	}

				    }
				    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
				    if(list.get(i).getSendGoodsType()==1){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}else{
								list.get(i).setPriceUnitTypeString("元");
							}
				    	}

				    }
				    //选择其他的时候 ,都为面议
				    if(list.get(i).getSendGoodsType()==2){
							list.get(i).setPriceUnitTypeString("面议");

				    }
					if(list.get(i).getF_cityId()!=null&&list.get(i).getF_cityId()!=0){
						if ("市辖区".equals(list.get(i).getF_cityIdString()) || "县".equals(list.get(i).getF_cityIdString())||"市".equals(list.get(i).getF_cityIdString())|| "省直辖行政单位".equals(list.get(i).getF_cityIdString())){
							list.get(i).setF_cityIdString("");
						}
						
					}
					if(list.get(i).getS_cityId()!=null&&list.get(i).getS_cityId()!=0){
						if ("市辖区".equals(list.get(i).getS_cityIdString()) || "县".equals(list.get(i).getS_cityIdString())||"市".equals(list.get(i).getS_cityIdString())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString())){
							list.get(i).setS_cityIdString("");
						}
						
					}
					if(list.get(i).getF_areaId()!=null&&list.get(i).getF_areaId()!=0){
						if ("市辖区".equals(list.get(i).getF_areaIdString()) || "县".equals(list.get(i).getF_areaIdString())||"市".equals(list.get(i).getF_areaIdString())|| "省直辖行政单位".equals(list.get(i).getF_areaIdString())){
							list.get(i).setF_areaIdString("");
						}
						
					}
					if(list.get(i).getS_areaId()!=null&&list.get(i).getS_areaId()!=0){
						if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
							list.get(i).setS_areaIdString("");
						}
						
					}

					
				}
					if(list.get(i).getPrice()==null){
						
						list.get(i).setPriceUnitTypeString("面议");
					}
				}
				
				result.setObject(list);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
			} catch (BusinessException e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			} catch (Exception e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			}
			renderJson(result, request, response);
		}
		
		/**
		 * 用户增加货源信息列表
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/addMemberAddress")
		public void addMemberAddress(HttpServletRequest request,
				HttpServletResponse response, MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			int usertype = memberAddressDTO.getUserType();
			String createUserId = memberAddressDTO.getCreateUserId();
			try {
				memberAddressDTO.getCreateUserId();
				MemberBaseinfoDTO memberDTO = memberToolService
						.getById(memberAddressDTO.getUserId() + "");
				// 判断用户是否存在
				if (null == memberDTO) {
					setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
					return;
				} else {
					// 存的当前的登录人的手机号码
					memberAddressDTO.setUserMobile(memberDTO.getMobile());
					//如果个人用户不是武汉的常用城市,则判断是否是个人用户且userId和createUserId相等
					if(memberDTO.getCcityId()!=420100&&memberDTO.getUserType()==1){
						if(memberAddressDTO.getUserId()==Long.parseLong(memberAddressDTO.getCreateUserId())){
							setResult(result, ErrorCodeEnum.GOODS_COMPANY_IS_NULL, request, response);
							return;
						}
					}
				}

				if (memberAddressDTO.getCarLength() != null) {
					Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
					Matcher m = p.matcher(memberAddressDTO.getCarLength()
							.toString());
					if (!m.matches()) {

						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("车长只能输入一位小数点的正数");
						renderJson(result, request, response);
						return;
					}
				}
				DateStringConverter c = new DateStringConverter();
				java.util.Date nowdate = new java.util.Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = formatter.format(nowdate);
				nowdate = DateUtil.formateDate(dateString, formatter);
				if (memberAddressDTO.getSendDate() != null) {
					if (memberAddressDTO.getSendDate().getTime() < nowdate
							.getTime()) {
						// if(memberAddressDTO.getSendDate().before(nowdate)){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("装车时间不能早于当前时间");
						renderJson(result, request, response);
						return;
					} else {

						if (memberAddressDTO.getSendDate() != null
								&& memberAddressDTO.getSendDateType() != 4
								&& memberAddressDTO.getSendDate().getTime() == nowdate
										.getTime()) {
							int sentD = memberAddressDTO.getSendDateType();
							Date d = new Date();
							int hour = d.getHours();
							if (sentD == 0) {
								if (hour > 12) {
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL
											.getStatusCode());
									result.setMsg("装车时间不能早于当前时间,上午时间段是6:00~12:00");
									renderJson(result, request, response);
									return;
								}

							}
							if (sentD == 1) {
								if (hour > 14) {
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL
											.getStatusCode());
									result.setMsg("装车时间不能早于当前时间,中午时间段是12:00~14:00");
									renderJson(result, request, response);
									return;
								}
							}
							if (sentD == 2) {
								if (hour > 18) {
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL
											.getStatusCode());
									result.setMsg("装车时间不能早于当前时间,下午时间段是14:00~18:00");
									renderJson(result, request, response);
									return;
								}
							}
						}
					}
					memberAddressDTO.setSendDateString(c.convert(memberAddressDTO
							.getSendDate()));
				}
				if (memberAddressDTO.getEndDate() != null) {
					memberAddressDTO.setEndDateString(c.convert(memberAddressDTO
							.getEndDate()));
				}
				if (memberAddressDTO.getSendDate() != null
						&& memberAddressDTO.getEndDate() != null) {
					// boolean flag =
					// memberAddressDTO.getEndDate().before(memberAddressDTO.getSendDate());
					// if(flag){
					if (memberAddressDTO.getEndDate().getTime() < memberAddressDTO
							.getSendDate().getTime()) {
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("到达时间不能早于装车时间");
						renderJson(result, request, response);
						return;
					} else {
						if (memberAddressDTO.getEndDateType() != null
								&& memberAddressDTO.getSendDateType() != null
								&& memberAddressDTO.getEndDate().getTime() == memberAddressDTO
										.getSendDate().getTime()) {
							int endT = memberAddressDTO.getEndDateType();
							int sentT = memberAddressDTO.getSendDateType();
							if (endT != 4 && sentT != 4) {
								if (endT <= sentT) {
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL
											.getStatusCode());
									result.setMsg("到达时间和装车时间同一天时,请注意选择上午,下午的时间");
									renderJson(result, request, response);
									return;
								}
							}
						}
					}
				}
				// 更改真名,进行判断是否传空
				// 更改真名,进行判断是否传空
				String f_detail = "";
				String s_detail = "";
				if (!StringUtils.isBlank(memberAddressDTO.getF_detail())) {
					if (!StringUtils.isBlank(memberAddressDTO.getApp())) {
						if ("IOS".equals(memberAddressDTO.getApp())) {
							f_detail = new String(memberAddressDTO.getF_detail()
									.getBytes("utf-8"), "utf-8");
						}
					} else {
						// f_detail = new
						// String(memberAddressDTO.getF_detail().getBytes("ISO-8859-1"),"utf-8");
						f_detail = memberAddressDTO.getF_detail();
					}

				}
				if (!StringUtils.isBlank(f_detail)) {
					memberAddressDTO.setF_detail(f_detail);
				}
				// 传默认的GPS定位城市

				if (memberAddressDTO.getS_areaId() != null
						&& memberAddressDTO.getS_cityId() != null
						&& memberAddressDTO.getS_provinceId() != null
						&& memberAddressDTO.getS_areaId() == 1
						&& memberAddressDTO.getS_cityId() == 1
						&& memberAddressDTO.getS_provinceId() == 1
						&& memberAddressDTO.getmCity() != null
						&& !"".equals(memberAddressDTO.getmCity())) {

					AreaDTO ad = memberAddressApiService.getArea(memberAddressDTO
							.getmCity());
					if (ad != null) {
						if (ad.getAreaID() != null) {
							memberAddressDTO.setS_cityId(Long.valueOf(ad
									.getAreaID()));
							// 可以直接获取上级目录Id不需要再查数据库
							// AreaDTO
							// adf=memberAddressApiService.getArea(ad.getFather());
							// if(adf!=null){
							if (ad.getFather() != null
									&& !"".equals(ad.getFather())) {
								memberAddressDTO.setS_provinceId(Long.valueOf(ad
										.getFather()));
								memberAddressDTO.setS_areaId(Long.valueOf(0));
							}
							// }
						}
					}

				}
				if (!StringUtils.isBlank(memberAddressDTO.getS_detail())) {
					if (!StringUtils.isBlank(memberAddressDTO.getApp())) {
						if ("IOS".equals(memberAddressDTO.getApp())) {
							s_detail = new String(memberAddressDTO.getS_detail()
									.getBytes("utf-8"), "utf-8");
						}
					} else {
						// s_detail = new
						// String(memberAddressDTO.getS_detail().getBytes("ISO-8859-1"),"utf-8");
						s_detail = memberAddressDTO.getS_detail();
					}

				}
				if (!StringUtils.isBlank(s_detail)) {
					memberAddressDTO.setS_detail(s_detail);
				}
				String goodsName = "";

				if (!StringUtils.isBlank(memberAddressDTO.getGoodsName())) {

					if (!StringUtils.isBlank(memberAddressDTO.getApp())) {
						if ("IOS".equals(memberAddressDTO.getApp())) {
							goodsName = new String(memberAddressDTO.getGoodsName()
									.getBytes("utf-8"), "utf-8");
						}
					} else {
						// goodsName= new
						// String(memberAddressDTO.getGoodsName().getBytes("ISO-8859-1"),"utf-8");
						goodsName = memberAddressDTO.getGoodsName();
					}
					memberAddressDTO.setGoodsName(goodsName);
				}
				if (memberAddressDTO.getUserId() != 0) {

					memberAddressDTO.setUpdateUserId(Long.toString(memberAddressDTO
							.getUserId()));
				}
				// 当前是企业用户的ID
				if (memberAddressDTO.getCreateUserId() != null) {
					memberAddressDTO.setCreateUserId(memberAddressDTO
							.getCreateUserId());
				}
				if (memberAddressDTO.getPrice() == null
						|| memberAddressDTO.getPrice() == 0) {
					memberAddressDTO.setPrice(0.00);
				} else {
					Pattern p = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
					Pattern p2 = Pattern.compile("^[1-9]\\d*$ ");
					Pattern p3 = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
					String price = memberAddressDTO.getPrice().toString();
					Matcher m = p.matcher(price);
					Matcher m2 = p2.matcher(price);
					Matcher m3 = p3.matcher(price);
					if (!m.matches()) {
						if (!m3.matches()) {

							if (!m2.matches()) {
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("价格只能输入两位小数点的正数");
								renderJson(result, request, response);
								return;
							}
						}
					} else {
						if (memberAddressDTO.getPrice() > 10000000) {
							result.setObject(null);
							result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
							result.setMsg("请输入一个小于10000000的价格");
							renderJson(result, request, response);
							return;
						}
					}
				}
				String remark = "";

				if (!StringUtils.isBlank(memberAddressDTO.getRemark())) {
					if (!StringUtils.isBlank(memberAddressDTO.getApp())) {
						if ("IOS".equals(memberAddressDTO.getApp())) {
							remark = new String(memberAddressDTO.getRemark()
									.getBytes("utf-8"), "utf-8");
						}
					} else {
						// endCity= new
						// String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
						remark = memberAddressDTO.getRemark();
					}
					// endCity= new
					// String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");

					int valueLength = 0;
					String chinese = "[\u0391-\uFFE5]";
					/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
					for (int i = 0; i < remark.length(); i++) {
						/* 获取一个字符 */
						String temp = remark.substring(i, i + 1);
						/* 判断是否为中文字符 */
						if (temp.matches(chinese)) {
							/* 中文字符长度为2 */
							valueLength += 2;
						} else {
							/* 其他字符长度为1 */
							valueLength += 1;
						}
					}

					if (valueLength != 0 && valueLength > 50) {
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("您输入的备注信息,超过规定的长度,请重新编辑");
						renderJson(result, request, response);
						return;
					} else {
						memberAddressDTO.setRemark(remark);
					}
				}

				// memberAddressDTO.getCreateUserId()
				int i = memberAddressApiService.addMemberAddress(memberAddressDTO);
				// 判断是都插入成功,>0位成功,否则则失败
				if (i > 0) {
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");

					// **推送消息给企业
					MemberBaseinfoDTO memberDTO2 = memberToolService
							.getById(createUserId);
					UMengPushMessage pushMessage = new UMengPushMessage();
					GdMessageDTO gdMessage = new GdMessageDTO();
					gdMessage.setSendApp("2");
					gdMessage.setSendType("1");
					gdMessage.setTicket("【农速通告诉您有新的货源信息】");
					gdMessage.setTitle("农速通告诉您有新的货源信息");
					gdMessage.setContent("有个人用户委托货源给您,请查收");
					gdMessage.setAfter_open("go_app");
					// gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
					// ios:9c6031b0c53f823498214ed8e9ba4ed65b2127633f6611836c2e93abdf0d2e8b
					// android:AsfEx_AIdKOZuTfL55scurKF6PRdP3Klx0khdM3MmKM2
					//gdMessage.setDevice_tokens(memberDTO2.getDevice_tokens());
					gdMessage.setProduction_mode(true);
					pushMessage.pushMessage(gdMessage);

					// 如果操作成功了的
					// 获取当前记录的Id
					Long id = memberAddressApiService
							.getmemberAddressId(memberAddressDTO.getUserId());
					// 获取手机GPS定位的时候的经纬度
					AreaDTO a = new AreaDTO();

					if (memberAddressDTO.getmCity() != null
							&& !"".equals(memberAddressDTO.getmCity())) {
						a = memberAddressApiService.getArea(memberAddressDTO
								.getmCity());
						memberAddressDTO.setMlng(a.getLng());
						memberAddressDTO.setMlat(a.getLat());
						//设置增加货源的开始城市地址ID
						memberAddressDTO.setCityId(a.getAreaID());
					} else {
						memberAddressDTO.setMlng(Double.valueOf(0));
						memberAddressDTO.setMlat(Double.valueOf(0));
					}
					AreaDTO bj = memberAddressApiService.getArea("北京市");
					AreaDTO sh = memberAddressApiService.getArea("上海市");
					AreaDTO cq = memberAddressApiService.getArea("重庆市");
					AreaDTO tj = memberAddressApiService.getArea("天津市");

					memberAddressDTO.setBjlng(bj.getLng());
					memberAddressDTO.setBjlat(bj.getLat());

					memberAddressDTO.setShlng(sh.getLng());
					memberAddressDTO.setShlat(sh.getLat());

					memberAddressDTO.setTjlng(tj.getLng());
					memberAddressDTO.setTjlat(tj.getLat());

					memberAddressDTO.setCqlng(cq.getLng());
					memberAddressDTO.setCqlat(cq.getLat());
					memberAddressDTO.setDistance(Double.valueOf(100));
					memberAddressDTO.setId(id);

					/*
					 * List<CarLineDTO> list =
					 * memberAddressApiService.getCarlineApiMessage
					 * (memberAddressDTO); if(list!=null && list.size()>0){
					 * 
					 * memberAddressApiService.setCarLApiMessage(memberAddressDTO,list
					 * ); UMengPushMessage pushMessage2 = new UMengPushMessage();
					 * GdMessageDTO gdMessage2 = new GdMessageDTO();
					 * gdMessage2.setSendApp("2"); gdMessage2.setSendType("1");
					 * gdMessage2.setTicket("【农速通为您推送线路信息】");
					 * gdMessage2.setTitle("农速通为您推送线路信息");
					 * gdMessage2.setContent("根据你发布的货源信息,我们为你推荐了路线信息,请查收");
					 * gdMessage2.setAfter_open("go_app"); //gdMessage.setActivity(
					 * "com.gudeng.smallbusiness.activity.MainActivity"); //ios:9
					 * c6031b0c53f823498214ed8e9ba4ed65b2127633f6611836c2e93abdf0d2e8b
					 * //android:AsfEx_AIdKOZuTfL55scurKF6PRdP3Klx0khdM3MmKM2
					 * gdMessage2.setDevice_tokens(memberDTO.getDevice_tokens());
					 * gdMessage2.setProduction_mode(true);
					 * pushMessage2.pushMessage(gdMessage2);
					 * 
					 * }
					 */

					// 创建增加推荐路线信息线程
					Runnable thread = new AddCarLineMessageThread(memberDTO,
							memberAddressDTO, memberAddressApiService);
					Thread acmThread = new Thread(thread);
					acmThread.start();

				} else {
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("failed");
				}

			} catch (BusinessException e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("新增货源发布失败");
			} catch (Exception e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("新增货源发布失败");
			}
			renderJson(result, request, response);
		}

		
		/**
		 * 用户修改货源信息列表
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/updateMemberAddress")
		public void updateMemberAddress (HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			int userType=memberAddressDTO.getUserType();
			String createUserId=memberAddressDTO.getCreateUserId();
			String oldCreateUserId=memberAddressDTO.getOldCreateUserId();
			
			ObjectResult result = new ObjectResult();
			try {
				DateStringConverter  c= new DateStringConverter();
				java.util.Date nowdate=new java.util.Date(); 
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			    String dateString = formatter.format(nowdate); 
			    nowdate=DateUtil.formateDate(dateString, formatter);
			    memberAddressDTO.setUpdateUserId(memberAddressDTO.getUserId()+"");
				if(memberAddressDTO.getCarLength()!=null){
			    	Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
			    	Matcher m = p.matcher(memberAddressDTO.getCarLength().toString());
			    	if (!m.matches()) {
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("车长只能输入一位小数点的正数" );
						renderJson(result, request, response);
						return;
			    	}
				}
				if(memberAddressDTO.getSendDate()!=null){
					//if(memberAddressDTO.getSendDate()<(nowdate)){
					if(memberAddressDTO.getSendDate().getTime()<nowdate.getTime()){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("装车时间不能早于当前时间");
						renderJson(result, request, response);
						return;
					}else{
						
						if(memberAddressDTO.getSendDate()!=null&&memberAddressDTO.getSendDateType()!=4&&memberAddressDTO.getSendDate().getTime()==nowdate.getTime()){
							int sentD=memberAddressDTO.getSendDateType();
							Date d = new Date();
							int hour = d.getHours();
							if(sentD==0){
								if(hour>12){
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
									result.setMsg("装车时间不能早于当前时间,上午时间段是6:00~12:00");
									renderJson(result, request, response);
									return;
								}

							}
							if(sentD==1){
								if(hour>14){
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
									result.setMsg("装车时间不能早于当前时间,中午时间段是12:00~14:00");
									renderJson(result, request, response);
									return;
								}
							}
							if(sentD==2){
								if(hour>18){
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
									result.setMsg("装车时间不能早于当前时间,下午时间段是14:00~18:00");
									renderJson(result, request, response);
									return;
								}
							}
						}
					}
					memberAddressDTO.setSendDateString(c.convert(memberAddressDTO.getSendDate()));
				}
				if(memberAddressDTO.getEndDate()!=null){
					memberAddressDTO.setEndDateString(c.convert(memberAddressDTO.getEndDate()));
				}
				if(memberAddressDTO.getSendDate()!=null&&memberAddressDTO.getEndDate()!=null){
				//	boolean flag = memberAddressDTO.getEndDate().before(memberAddressDTO.getSendDate());
				//	if(flag){
					if(memberAddressDTO.getEndDate().getTime()<memberAddressDTO.getSendDate().getTime()){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("到达时间不能早于装车时间");
						renderJson(result, request, response);
						return;
					}else{
						if(memberAddressDTO.getEndDateType()!=null&&memberAddressDTO.getSendDateType()!=null&&memberAddressDTO.getEndDate().getTime()==memberAddressDTO.getSendDate().getTime()){
							int endT=memberAddressDTO.getEndDateType();
							int sentT=memberAddressDTO.getSendDateType();
							if(endT!=4&&sentT!=4){
								if(endT<=sentT){
									result.setObject(null);
									result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
									result.setMsg("到达时间和装车时间同一天时,请注意选择上午,下午的时间");
									renderJson(result, request, response);
									return;
								}
							}
						}
					}
				}
				//更改真名,进行判断是否传空
				String f_detail ="";
				String s_detail="";
				if(!StringUtils.isBlank(memberAddressDTO.getF_detail())){
			    	if(!StringUtils.isBlank(memberAddressDTO.getApp())){
				    	if("IOS".equals(memberAddressDTO.getApp())){
				    		f_detail =  new String(memberAddressDTO.getF_detail().getBytes("utf-8"),"utf-8");
				    	}
				    	}else{
				    		//f_detail =  new String(memberAddressDTO.getF_detail().getBytes("ISO-8859-1"),"utf-8");
				    		f_detail =  memberAddressDTO.getF_detail();
				    	}
					
				}
				if(!StringUtils.isBlank(f_detail)){
					memberAddressDTO.setF_detail(f_detail);
				}
				if(!StringUtils.isBlank(memberAddressDTO.getS_detail())){
			    	if(!StringUtils.isBlank(memberAddressDTO.getApp())){
				    	if("IOS".equals(memberAddressDTO.getApp())){
				    		s_detail =  new String(memberAddressDTO.getS_detail().getBytes("utf-8"),"utf-8");
				    	}
				    	}else{
				    		//s_detail =  new String(memberAddressDTO.getS_detail().getBytes("ISO-8859-1"),"utf-8");
				    		s_detail =  memberAddressDTO.getS_detail();
				    	}
					
				}
				if(!StringUtils.isBlank(s_detail)){
					memberAddressDTO.setS_detail(s_detail);
				}
				String goodsName= "";
				
			    if(!StringUtils.isBlank(memberAddressDTO.getGoodsName())){
			    	
			    	if(!StringUtils.isBlank(memberAddressDTO.getApp())){
				    	if("IOS".equals(memberAddressDTO.getApp())){
				    		goodsName= new String(memberAddressDTO.getGoodsName().getBytes("utf-8"),"utf-8");
				    	}
				    	}else{
				    		//goodsName= new String(memberAddressDTO.getGoodsName().getBytes("ISO-8859-1"),"utf-8");
				    		goodsName= memberAddressDTO.getGoodsName();
				    	}
			    	memberAddressDTO.setGoodsName(goodsName);
			    }
			    if(memberAddressDTO.getPrice()==null||memberAddressDTO.getPrice()==0){
			    	memberAddressDTO.setPrice(0.00);
			    }else{
			    	Pattern p = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
			    	Pattern p2 = Pattern.compile("^[1-9]\\d*$ ");
			    	Pattern p3 = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
			    	String price=memberAddressDTO.getPrice().toString();
			    	Matcher m = p.matcher(price);
			    	Matcher m2 = p2.matcher(price);
			    	Matcher m3 = p3.matcher(price);
			    	if (!m.matches()) {
			    		if(!m3.matches()){
			    			
			    		
			           if(!m2.matches()){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("价格只能输入两位小数点的正数" );
						renderJson(result, request, response);
						return;
			    		     }
			    		}
			    	}else{
			    		if(memberAddressDTO.getPrice()>10000000){
							result.setObject(null);
							result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
							result.setMsg("请输入一个小于10000000的价格" );
							renderJson(result, request, response);
							return;
			    		}
			    	}
			    }
			    
				String remark= "";
				
			    if(!StringUtils.isBlank(memberAddressDTO.getRemark())){
			    	if(!StringUtils.isBlank(memberAddressDTO.getApp())){
				    	if("IOS".equals(memberAddressDTO.getApp())){
				    		remark= new String(memberAddressDTO.getRemark().getBytes("utf-8"),"utf-8");
				    	}
				    	}else{
				    		//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
				    		remark=memberAddressDTO.getRemark();
				    	}
			    	//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");

					int valueLength = 0;
			        String chinese = "[\u0391-\uFFE5]";
			        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
			        for (int i = 0; i < remark.length(); i++) {
			            /* 获取一个字符 */
			            String temp = remark.substring(i, i + 1);
			            /* 判断是否为中文字符 */
			            if (temp.matches(chinese)) {
			                /* 中文字符长度为2 */
			                valueLength += 2;
			            } else {
			                /* 其他字符长度为1 */
			                valueLength += 1;
			            }
			        }
			        
			        if(valueLength!=0&&valueLength>50){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("您输入的备注信息,超过规定的长度,请重新编辑");
						renderJson(result, request, response);
						return;
			        }else{
			        	memberAddressDTO.setRemark(remark);
			        }
			    } 
				int i = memberAddressApiService.updateMemberAddress(memberAddressDTO);
				result.setObject(null);
				//判断是都插入成功,>0位成功,否则则失败
				if(i>0){
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");
					UMengPushMessage pushMessage = new UMengPushMessage();
					GdMessageDTO gdMessage = new GdMessageDTO();
					gdMessage.setSendApp("2");
					gdMessage.setSendType("1");
					gdMessage.setTicket("【农速通告诉您有新的货源信息】");
					gdMessage.setTitle("农速通告诉您有新的货源信息");
					gdMessage.setAfter_open("go_app");
					gdMessage.setProduction_mode(true);
					MemberBaseinfoDTO memberDTO2 = memberToolService.getById(createUserId);
					//MemberBaseinfoDTO memberDTO4 = memberToolService.getById(memberAddressDTO.getUserId()+"");
					if(userType==1){
						
						
						if(Long.parseLong(oldCreateUserId)!=Long.parseLong(createUserId)){
						//**推送消息给企业
							MemberBaseinfoDTO memberDTO3 = memberToolService.getById(oldCreateUserId);


							gdMessage.setContent("有个人用户委托货源给您,请查收");

							gdMessage.setDevice_tokens(memberDTO2.getDevice_tokens());

							pushMessage.pushMessage(gdMessage);
				            gdMessage.setDevice_tokens(memberDTO3.getDevice_tokens());
				            gdMessage.setContent("有个人用户委托货源已经从您名下转移到其他企业名下,请查收");
							pushMessage.pushMessage(gdMessage);

						}else{
							gdMessage.setContent("有个人用户对委托货源进行修改您,请查收");

							gdMessage.setDevice_tokens(memberDTO2.getDevice_tokens());

							pushMessage.pushMessage(gdMessage);
						}
					}
//					else if(userType==2){
//						gdMessage.setContent("您委托的货源信息,被委托企业进行修改,请查收");
//
//						gdMessage.setDevice_tokens(memberDTO4.getDevice_tokens());
//
//						pushMessage.pushMessage(gdMessage);
//					}
				}
			else{
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("failed");
				}

			} catch (BusinessException e) {
				logger.info("更新货源发布失败", e);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("更新货源发布失败");
			} catch (Exception e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("更新货源发布失败");
			}
			renderJson(result, request, response);
		}
		
		/**
		 * 用户删除货源信息列表
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/delMemberAddress")
		public void delMemberAddress (HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			int userType=memberAddressDTO.getUserType();
			
			try {
				int i=0;
				if (memberAddressDTO.getId()!=null) {
					//将货源进行软删除
					i = memberAddressApiService.updateMemberAdressStatusByid(memberAddressDTO.getId().toString());
				}
				result.setObject(null);
				//判断是都插入成功,>0位成功,否则则失败
				if(i>0){
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");
					
					MemberBaseinfoDTO memberDTO2 = memberToolService.getById(memberAddressDTO.getCreateUserId());
					MemberBaseinfoDTO memberDTO4 = memberToolService.getById(memberAddressDTO.getUserId()+"");
					UMengPushMessage pushMessage = new UMengPushMessage();
					GdMessageDTO gdMessage = new GdMessageDTO();
					gdMessage.setSendApp("2");
					gdMessage.setSendType("1");
					gdMessage.setTicket("【农速通告诉您有新的货源信息】");
					gdMessage.setTitle("农速通告诉您有新的货源信息");
					gdMessage.setAfter_open("go_app");
					gdMessage.setProduction_mode(true);

					if(userType==1){
						//**推送消息给企业



							gdMessage.setContent("个人用户委托给您的货源信息已经取消,请查收");

							gdMessage.setDevice_tokens(memberDTO2.getDevice_tokens());

							pushMessage.pushMessage(gdMessage);
					}else if(userType==2){
						gdMessage.setContent("您委托给企业的货源信息,已经被删除,请查收");

						gdMessage.setDevice_tokens(memberDTO4.getDevice_tokens());

						pushMessage.pushMessage(gdMessage);
					}
				}else{
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("failed");
				}

			} catch (BusinessException e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("删除货源发布失败");
				e.printStackTrace();
			} catch (Exception e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("删除货源发布失败");
				e.printStackTrace();
			}
			renderJson(result, request, response);
		}
		
		/**
		 * 用户重新发布货源信息列表
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/replayMemberAddress")
		public void replayMemberAddress (HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				int i = memberAddressApiService.replayMemberAddress(memberAddressDTO);
				result.setObject(null);
				//判断是都插入成功,>0位成功,否则则失败
				if(i>0){
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");
				}else{
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("failed");
				}

			} catch (BusinessException e) {
				logger.info("更新货源发布失败", e);
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("更新货源发布失败");
			} catch (Exception e) {
				logger.info("重新货源发布失败", e);
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("重新货源发布失败");
			}
			renderJson(result, request, response);
		}
		
		/**
		 * 货源详情by 货源Id
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/getMaById")
		public void getMemberAddressById (HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				MemberAddressDTO ma = memberAddressApiService.getMemberAddressById(memberAddressDTO);
				if(ma==null){
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("此条信息刚刚已被删除!");
				}else{
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");
				}
				result.setObject(ma);

			}  catch (Exception e) {
				logger.info("获取货源发布详情失败", e);
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源发布详情失败");
			}
			renderJson(result, request, response);
		}
		
		
		/**
		 * 获取车主找货的列表 ,不带分页
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/listMemberAddressByCondition")
		public void listMemberAddressByCondition(HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				
				String s_provinceId=memberAddressDTO.getS_provinceIdS();
				String s_cityId=memberAddressDTO.getS_cityIdS();
				String s_areaId=memberAddressDTO.getS_areaIdS();
				String e_provinceId=memberAddressDTO.getE_provinceIdS();
				String e_cityId=memberAddressDTO.getE_cityIdS();
				String e_areaId=memberAddressDTO.getE_areaIdS(); 
				//区分IOS还是安卓传的参数,
				if(memberAddressDTO.getF_provinceId()==null||memberAddressDTO.getF_provinceId()==0){
				memberAddressDTO.setF_provinceId(!StringUtils.isEmpty(e_provinceId)?Long.parseLong(e_provinceId):0);
				}
				if(memberAddressDTO.getF_cityId()==null||memberAddressDTO.getF_cityId()==0){
					memberAddressDTO.setF_cityId(!StringUtils.isEmpty(e_cityId)?Long.parseLong(e_cityId):0); 	
				}
				if(memberAddressDTO.getF_areaId()==null||memberAddressDTO.getF_areaId()==0){
					memberAddressDTO.setF_areaId(!StringUtils.isEmpty(e_areaId)?Long.parseLong(e_areaId):0);  
				}
				                                      

				if(memberAddressDTO.getS_provinceId()==null||memberAddressDTO.getS_provinceId()==0){
					memberAddressDTO.setS_provinceId(!StringUtils.isEmpty(s_provinceId)?Long.parseLong(s_provinceId):0);
				}
				if(memberAddressDTO.getS_cityId()==null ||memberAddressDTO.getS_cityId()==0)   {
					memberAddressDTO.setS_cityId(!StringUtils.isEmpty(s_cityId)?Long.parseLong(s_cityId):0); 
				}
				if(memberAddressDTO.getS_areaId()==null ||memberAddressDTO.getS_areaId()==0){
					memberAddressDTO.setS_areaId(!StringUtils.isEmpty(s_areaId)?Long.parseLong(s_areaId):0);
				}
						
				List<MemberAddressDTO> list = memberAddressApiService.getMemberAddressByCondition(memberAddressDTO);			
				//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getSendGoodsType()!=null){
				    if(list.get(i).getSendGoodsType()==0){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}
				    	}

				    }
				    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
				    if(list.get(i).getSendGoodsType()==1){
				    	
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}else{
								list.get(i).setPriceUnitTypeString("元");
							}
				    	}
				    }
				    //选择其他的时候 ,都为面议
				    if(list.get(i).getSendGoodsType()==2){
							list.get(i).setPriceUnitTypeString("面议");							

				    }
					}else{
					    	list.get(i).setPriceUnitTypeString("面议");
					    }
					
					if(list.get(i).getCreateTime()!=null){					
						String time=DateTimeUtils.getTimeBetween(list.get(i).getCreateTime());
						list.get(i).setTimebefore(time);
					}
					if(list.get(i).getF_cityId()!=null&&list.get(i).getF_cityId()!=0){
						if ("市辖区".equals(list.get(i).getF_cityIdString()) || "县".equals(list.get(i).getF_cityIdString())||"市".equals(list.get(i).getF_cityIdString())|| "省直辖行政单位".equals(list.get(i).getF_cityIdString())){
							list.get(i).setF_cityIdString("");
						}
						
					}
					if(list.get(i).getS_cityId()!=null&&list.get(i).getS_cityId()!=0){
						if ("市辖区".equals(list.get(i).getS_cityIdString()) || "县".equals(list.get(i).getS_cityIdString())||"市".equals(list.get(i).getS_cityIdString())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString())){
							list.get(i).setS_cityIdString("");
						}
						
					}
					if(list.get(i).getF_areaId()!=null&&list.get(i).getF_areaId()!=0){
						if ("市辖区".equals(list.get(i).getF_areaIdString()) || "县".equals(list.get(i).getF_areaIdString())||"市".equals(list.get(i).getF_areaIdString())|| "省直辖行政单位".equals(list.get(i).getF_areaIdString())){
							list.get(i).setF_areaIdString("");
						}
						
					}
					if(list.get(i).getS_areaId()!=null&&list.get(i).getS_areaId()!=0){
						if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
							list.get(i).setS_areaIdString("");
						}
						
					}
//					if(list.get(i).getCarLength()==null||list.get(i).getCarLength()==0){
//						if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
//							list.get(i).setS_areaIdString("");
//						}
//						
//					}
				}
				
				result.setObject(list);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
			} catch (BusinessException e) {
				logger.info("获取货源列表异常", e);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			} catch (Exception e) {
				logger.info("获取货源列表异常", e);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			}
			renderJson(result, request, response);
		}
		
		
		/**
		 * 发布货源界面,公司下拉框
		 * 
		 * @param request
		 * @param response
		 */
		
		@RequestMapping("/listCompany")
		public void getCompany(HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				List<MemberBaseinfoDTO> list  = memberToolService.getCompany();
				result.setObject(list);
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");
			}  catch (Exception e) {
				logger.info("获取货源发布详情失败", e);
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源发布详情失败");
			}
			renderJson(result, request, response);
		}

		
		
		/**
		 * 获取车主找货的列表总数
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/getCountByCondition")
		public void getCountByCondition(HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				int totol = memberAddressApiService.getCountByCondition(memberAddressDTO);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("totol", totol);
				result.setObject(map);
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");

			}  catch (Exception e) {
				logger.info("获取货源发布详情失败", e);
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源发布详情失败");
			}
			renderJson(result, request, response);
		}
		
		
		
		/**
		 * 获取车主找货的列表,带分页
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/listMemberAddressByConditionPage")
		public void listMemberAddressByConditionNew(HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				
				String s_provinceId=memberAddressDTO.getS_provinceIdS();
				String s_cityId=memberAddressDTO.getS_cityIdS();
				String s_areaId=memberAddressDTO.getS_areaIdS();
				String e_provinceId=memberAddressDTO.getE_provinceIdS();
				String e_cityId=memberAddressDTO.getE_cityIdS();
				String e_areaId=memberAddressDTO.getE_areaIdS(); 
				//区分IOS还是安卓传的参数,
				if(memberAddressDTO.getF_provinceId()==null||memberAddressDTO.getF_provinceId()==0){
				memberAddressDTO.setF_provinceId(!StringUtils.isEmpty(e_provinceId)?Long.parseLong(e_provinceId):0);
				}
				if(memberAddressDTO.getF_cityId()==null||memberAddressDTO.getF_cityId()==0){
					memberAddressDTO.setF_cityId(!StringUtils.isEmpty(e_cityId)?Long.parseLong(e_cityId):0); 	
				}
				if(memberAddressDTO.getF_areaId()==null||memberAddressDTO.getF_areaId()==0){
					memberAddressDTO.setF_areaId(!StringUtils.isEmpty(e_areaId)?Long.parseLong(e_areaId):0);  
				}
				                                      

				if(memberAddressDTO.getS_provinceId()==null||memberAddressDTO.getS_provinceId()==0){
					memberAddressDTO.setS_provinceId(!StringUtils.isEmpty(s_provinceId)?Long.parseLong(s_provinceId):0);
				}
				if(memberAddressDTO.getS_cityId()==null ||memberAddressDTO.getS_cityId()==0)   {
					memberAddressDTO.setS_cityId(!StringUtils.isEmpty(s_cityId)?Long.parseLong(s_cityId):0); 
				}
				if(memberAddressDTO.getS_areaId()==null ||memberAddressDTO.getS_areaId()==0){
					memberAddressDTO.setS_areaId(!StringUtils.isEmpty(s_areaId)?Long.parseLong(s_areaId):0);
				}
				//
				Map<String, Object> map = new HashMap<String, Object>();
				CommonPageDTO pageDTO = getPageInfo(request, map);
				//获取手机GPS定位的时候的经纬度
				AreaDTO a = new AreaDTO();
				AreaDTO bj=memberAddressApiService.getArea("北京市");
				AreaDTO sh=memberAddressApiService.getArea("上海市");
				AreaDTO cq=memberAddressApiService.getArea("重庆市");
				AreaDTO tj=memberAddressApiService.getArea("天津市");
				if(memberAddressDTO.getmCity()!=null&&!"".equals(memberAddressDTO.getmCity())){
					 a=memberAddressApiService.getArea(memberAddressDTO.getmCity());
					memberAddressDTO.setMlng(a.getLng());
					memberAddressDTO.setMlat(a.getLat());
				}else{
					memberAddressDTO.setMlng(Double.valueOf(0));
					memberAddressDTO.setMlat(Double.valueOf(0));
				}

				
				memberAddressDTO.setBjlng(bj.getLng());
				memberAddressDTO.setBjlat(bj.getLat());
				
				
				memberAddressDTO.setShlng(sh.getLng());
				memberAddressDTO.setShlat(sh.getLat());
				
				
				memberAddressDTO.setTjlng(tj.getLng());
				memberAddressDTO.setTjlat(tj.getLat());
				
				
				memberAddressDTO.setCqlng(cq.getLng());
				memberAddressDTO.setCqlat(cq.getLat());
				int total =  memberAddressApiService.getCountByCondition(memberAddressDTO);
				memberAddressDTO.setStartRow(Integer.parseInt(map.get(START_ROW).toString()));
				memberAddressDTO.setEndRow(Integer.parseInt(map.get(END_ROW).toString()));

				List<MemberAddressDTO> list = memberAddressApiService.getMemberAddressByConditionNew(memberAddressDTO);
				
				//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getSendGoodsType()!=null){
				    if(list.get(i).getSendGoodsType()==0){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}
				    	}

				    }
				    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
				    if(list.get(i).getSendGoodsType()==1){
				    	
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}else{
								list.get(i).setPriceUnitTypeString("元");
							}
				    	}
				    }
				    //选择其他的时候 ,都为面议
				    if(list.get(i).getSendGoodsType()==2){
							list.get(i).setPriceUnitTypeString("面议");							

				    }
					}else{
					    	list.get(i).setPriceUnitTypeString("面议");
					    }
					
					if(list.get(i).getCreateTime()!=null){					
						String time=DateTimeUtils.getTimeBetween(list.get(i).getCreateTime());
						list.get(i).setTimebefore(time);
					}
					
					if(list.get(i).getF_provinceId()!=null&&list.get(i).getF_provinceId()>1){
						list.get(i).setF_provinceIdString(memberAddressApiService.getAreaString(list.get(i).getF_provinceId()));  
						}
						if(list.get(i).getF_cityId()!=null&&list.get(i).getF_cityId()>1){
							list.get(i).setF_cityIdString(memberAddressApiService.getAreaString(list.get(i).getF_cityId()));
						}
						if(list.get(i).getF_areaId()!=null&&list.get(i).getF_areaId()>1){
							list.get(i).setF_areaIdString(memberAddressApiService.getAreaString(list.get(i).getF_areaId()));
						}
						                                      

						if(list.get(i).getS_provinceId()!=null&&list.get(i).getS_provinceId()>1){
							list.get(i).setS_provinceIdString(memberAddressApiService.getAreaString(list.get(i).getS_provinceId()));
						}
						if(list.get(i).getS_cityId()!=null &&list.get(i).getS_cityId()>1){
							list.get(i).setS_cityIdString(memberAddressApiService.getAreaString(list.get(i).getS_cityId()));
						}
						if(list.get(i).getS_areaId()!=null &&list.get(i).getS_areaId()>1){
							list.get(i).setS_areaIdString(memberAddressApiService.getAreaString(list.get(i).getS_areaId()));
						}
						if(list.get(i).getF_cityId()!=null&&list.get(i).getF_cityId()!=0){
							if ("市辖区".equals(list.get(i).getF_cityIdString()) || "县".equals(list.get(i).getF_cityIdString())||"市".equals(list.get(i).getF_cityIdString())|| "省直辖行政单位".equals(list.get(i).getF_cityIdString())){
								list.get(i).setF_cityIdString("");
							}else{
								list.get(i).setF_provinceIdString("");
							}
							
						}
						if(list.get(i).getS_cityId()!=null&&list.get(i).getS_cityId()!=0){
							if ("市辖区".equals(list.get(i).getS_cityIdString()) || "县".equals(list.get(i).getS_cityIdString())||"市".equals(list.get(i).getS_cityIdString())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString())){
								list.get(i).setS_cityIdString("");
							}else{
								list.get(i).setS_provinceIdString("");
							}
							
						}
						if(list.get(i).getF_areaId()!=null&&list.get(i).getF_areaId()!=0){
							if ("市辖区".equals(list.get(i).getF_areaIdString()) || "县".equals(list.get(i).getF_areaIdString())||"市".equals(list.get(i).getF_areaIdString())|| "省直辖行政单位".equals(list.get(i).getF_areaIdString())){
								list.get(i).setF_areaIdString("");
							}
							
						}
						if(list.get(i).getS_areaId()!=null&&list.get(i).getS_areaId()!=0){
							if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
								list.get(i).setS_areaIdString("");
							}
							
						}
				}
				//根据总数设置pageDTO信息
				pageDTO.setRecordCount(total);
				pageDTO.initiatePage(total);
				pageDTO.setRecordList(list);
				result.setObject(pageDTO);
				//result.setObject(list);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
			} catch (BusinessException e) {
				logger.info("获取货源列表异常", e);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			} catch (Exception e) {
				logger.info("获取货源列表异常", e);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			}
			renderJson(result, request, response);
		}
		
		
		/**
		 * 获取用户发布货源信息列表,带分页
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/listMemberAddressByUserIdPage")
		public void listProductCategoryNew(HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				List<MemberAddressDTO> list = memberAddressApiService.getMemberAddressByUserId(memberAddressDTO);
				//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getPrice()!=null&&list.get(i).getSendGoodsType()!=null){
				    if(list.get(i).getSendGoodsType()==0){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}
				    	}

				    }
				    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
				    if(list.get(i).getSendGoodsType()==1){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setPriceUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setPriceUnitTypeString("面议");
							}else{
								list.get(i).setPriceUnitTypeString("元");
							}
				    	}

				    }
				    //选择其他的时候 ,都为面议
				    if(list.get(i).getSendGoodsType()==2){
							list.get(i).setPriceUnitTypeString("面议");

				    }


					
				}
					if(list.get(i).getPrice()==null){
						
						list.get(i).setPriceUnitTypeString("面议");
					}
				}
				
				result.setObject(list);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
			} catch (BusinessException e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			} catch (Exception e) {
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源列表异常");
			}
			renderJson(result, request, response);
		}
		
		
		/**
		 * 发布货源的时候,下拉框公司列表,用定位城市过滤
		 * 
		 * @param request
		 * @param response
		 */
		
		@RequestMapping("/listCompanybyMcity")
		public void getCompanyByMcity(HttpServletRequest request,
				HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
				String s="";
				AreaDTO areaDto= areaToolService.getByAreaName(memberAddressDTO.getmCity());
				
				memberAddressDTO.setCcityId(Long.valueOf(areaDto.getAreaID()));
				List<MemberBaseinfoDTO> list  = memberToolService.getCompanyByMcity(memberAddressDTO);
				result.setObject(list);
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");

			}  catch (Exception e) {
				logger.info("获取货源发布公司列表失败", e);
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("获取货源发布公司列表失败");
			}
			renderJson(result, request, response);
		}
		/**
		 * 推送货源信息
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping("/setMadMessage")
		public void setCarMessage(HttpServletRequest request,
				HttpServletResponse response, MemberAddressDTO memberAddressDTO) {
			ObjectResult result = new ObjectResult();
			try {
			 	//如果操作成功了的
            	//获取当前记录的Id
            	Long id= memberAddressApiService.getmemberAddressId(memberAddressDTO.getUserId());
    			//获取手机GPS定位的时候的经纬度
    			AreaDTO a=new AreaDTO();
				if(memberAddressDTO.getmCity()!=null&&!"".equals(memberAddressDTO.getmCity())){
					 a=memberAddressApiService.getArea(memberAddressDTO.getmCity());
					memberAddressDTO.setMlng(a.getLng());
					memberAddressDTO.setMlat(a.getLat());
				}else{
					memberAddressDTO.setMlng(Double.valueOf(0));
					memberAddressDTO.setMlat(Double.valueOf(0));
				}
    			AreaDTO bj=memberAddressApiService.getArea("北京市");
    			AreaDTO sh=memberAddressApiService.getArea("上海市");
    			AreaDTO cq=memberAddressApiService.getArea("重庆市");
    			AreaDTO tj=memberAddressApiService.getArea("天津市");

    			
    			memberAddressDTO.setBjlng(bj.getLng());
    			memberAddressDTO.setBjlat(bj.getLat());
    			
    			
    			memberAddressDTO.setShlng(sh.getLng());
    			memberAddressDTO.setShlat(sh.getLat()); 
    			
    			
    			memberAddressDTO.setTjlng(tj.getLng());
    			memberAddressDTO.setTjlat(tj.getLat());
    			
    			
    			memberAddressDTO.setCqlng(cq.getLng());
    			memberAddressDTO.setCqlat(cq.getLat());
    			memberAddressDTO.setDistance(Double.valueOf(100));
    			memberAddressDTO.setId(id);
            	List<CarLineDTO> list = memberAddressApiService.getCarlineApiMessage(memberAddressDTO);
            	if(list!=null && list.size()>0){
            		
            		memberAddressApiService.setCarLApiMessage(memberAddressDTO,list);
            	}

				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");

			} catch (Exception e) {
				logger.info("推送货源异常", e);
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("推送货源异常");
			}
			renderJson(result, request, response);
		}
		
		
		/**
		 * 再次获取推送信息
		 * @param request
		 * @param response
		 * @param pushNstMessageDTO
		 */
		@RequestMapping("/getAgainCarLines")
		public void getAgainCarLines(HttpServletRequest request, HttpServletResponse response,
				PushNstMessageDTO pushNstMessageDTO) {
			ObjectResult result = new ObjectResult();
			if (pushNstMessageDTO.getId()==null) {
				logger.warn("传入推送信息的Id不能为空");
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("传入推送信息的Id不能为空");
				renderJson(result, request, response);
				return;
			}
			try {
				
				//根据传入推送信息的ID查询当前nstpush_message_info中存在的线路推送信息,取出cl_Id集合
				List<Long> carLineIds=getCarlineIds(pushNstMessageDTO);
				
				//拼接memberAddressDTO条件，供推荐线路使用
				MemberAddressDTO memberAddressDTO=getMemberAddressDTO(pushNstMessageDTO,carLineIds);
				
				//获取会员信息
				MemberBaseinfoDTO memberDTO = memberToolService
						.getById(memberAddressDTO.getUserId() + "");
				
				List<CarLineDTO> list=memberAddressApiService.excutePush(memberDTO, memberAddressDTO, memberAddressApiService);
				
				if (list!=null && list.size()==0) {
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("暂无新消息推荐！");
					renderJson(result, request, response);
					return;
				}
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("您好,成功匹配新的线路消息给你.");
				
			} catch (Exception e) {
				logger.warn("再次推送信息失败");
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("再次推送信息失败");
				e.printStackTrace();
			}
			
			renderJson(result, request, response);
		}

		/**
		 * 拼接memberAddressDTO条件，供推荐线路使用
		 * @param pushNstMessageDTO
		 * @return
		 * @throws Exception
		 */
		private MemberAddressDTO getMemberAddressDTO(PushNstMessageDTO pushNstMessageDTO,List<Long> carLineIds) throws Exception{
			
			MemberAddressDTO memberAddressDTO=memberAddressApiService.getNstpushMessageById(pushNstMessageDTO.getId());
			
			if (StringUtils.isNotEmpty(memberAddressDTO.getCityId())) {
				AreaDTO areaDTO=areaToolService.getLngAndLatByCityId(memberAddressDTO.getCityId());
				memberAddressDTO.setMlat(areaDTO.getLat());
				memberAddressDTO.setMlng(areaDTO.getLng());
			}
			AreaDTO bj = memberAddressApiService.getArea("北京市");
			AreaDTO sh = memberAddressApiService.getArea("上海市");
			AreaDTO cq = memberAddressApiService.getArea("重庆市");
			AreaDTO tj = memberAddressApiService.getArea("天津市");

			memberAddressDTO.setBjlng(bj.getLng());
			memberAddressDTO.setBjlat(bj.getLat());

			memberAddressDTO.setShlng(sh.getLng());
			memberAddressDTO.setShlat(sh.getLat());

			memberAddressDTO.setTjlng(tj.getLng());
			memberAddressDTO.setTjlat(tj.getLat());

			memberAddressDTO.setCqlng(cq.getLng());
			memberAddressDTO.setCqlat(cq.getLat());
			
			if (StringUtils.isNotEmpty(memberAddressDTO.getMbId())) {
				//nstpush_message 表中对应的mbId 为member_address中的id
				memberAddressDTO.setId(Long.parseLong(memberAddressDTO.getMbId()));
				MemberAddressDTO mDto=memberAddressApiService.getMemberAddressById(memberAddressDTO);
				memberAddressDTO.setS_provinceId(mDto.getS_provinceId());
				memberAddressDTO.setS_cityId(mDto.getS_cityId());
				memberAddressDTO.setS_areaId(mDto.getS_areaId());
				memberAddressDTO.setF_provinceId(mDto.getF_provinceId());
				memberAddressDTO.setF_cityId(mDto.getF_cityId());
				memberAddressDTO.setF_areaId(mDto.getF_areaId());
				memberAddressDTO.setUserId(mDto.getUserId());
				memberAddressDTO.setUserType(mDto.getUserType());
				memberAddressDTO.setEndRow(mDto.getEndRow());
				memberAddressDTO.setStartRow(mDto.getStartRow());
				memberAddressDTO.setCarType(mDto.getCarType());
				memberAddressDTO.setCarLength(mDto.getCarLength());
			}
			memberAddressDTO.setCarLineIds(carLineIds);
			return memberAddressDTO;
		}
		
		/**
		 * 根据货源mbid 查询农速通推送消息子表的记录 cl_id集合
		 * @param pushNstMessageDTO
		 * @return
		 * @throws Exception
		 */
		private List<Long> getCarlineIds(PushNstMessageDTO pushNstMessageDTO) throws Exception{
			MemberAddressDTO memberAddressDTO=memberAddressApiService.getNstpushMessageById(pushNstMessageDTO.getId());
			List<PushNstMessageInfoDTO> pmInfoDtoLists=new ArrayList<>();
			if (StringUtils.isNotEmpty(memberAddressDTO.getMbId())) {
				//得到农速通推送消息表中mbid的值，排除表中已经对货源推荐过的线路
				Long mbid=Long.parseLong(memberAddressDTO.getMbId());
				pmInfoDtoLists=memberAddressApiService.getCarLinesBymessageId(mbid);
			}
			List<Long> list =new ArrayList<>();
			if (pmInfoDtoLists!=null && pmInfoDtoLists.size()!=0) {
				for (PushNstMessageInfoDTO p : pmInfoDtoLists) {
					list.add(p.getCl_Id());
				}
			}
			return list;
		}
		
		
		
	}

