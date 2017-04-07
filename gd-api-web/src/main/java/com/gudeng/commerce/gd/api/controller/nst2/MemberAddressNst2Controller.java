package com.gudeng.commerce.gd.api.controller.nst2;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.api.controller.FocusCategoryController;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.converter.DateStringConverter;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.CarLineApiService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.util.AddCarLineMessageThread;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MapUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.RuleForMemberThread;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.exception.BusinessException;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("nst2/memberaddress")
public class MemberAddressNst2Controller extends GDAPIBaseController {

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
	@Autowired
	public CarLineApiService carLineApiService;
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
			
			if(memberAddressDTO.getApp()!=null&&!"".equals(memberAddressDTO.getApp())&&"ad".equals(memberAddressDTO.getApp())){
				String s_provinceId=memberAddressDTO.getS_provinceIdS();
				String s_cityId=memberAddressDTO.getS_cityIdS();
				String s_areaId=memberAddressDTO.getS_areaIdS();
				String e_provinceId=memberAddressDTO.getE_provinceIdS();
				String e_cityId=memberAddressDTO.getE_cityIdS();
				String e_areaId=memberAddressDTO.getE_areaIdS(); 
				if(memberAddressDTO.getCarLengthString()!=null && !"".equals(memberAddressDTO.getCarLengthString())){
					memberAddressDTO.setCarLength(Double.parseDouble(memberAddressDTO.getCarLengthString()));
				}
				if(memberAddressDTO.getCarTypeStringCondition()!=null && !"".equals(memberAddressDTO.getCarTypeStringCondition())){
					memberAddressDTO.setCarType(Integer.parseInt(memberAddressDTO.getCarTypeStringCondition()));
				}
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
			
			
			//新增查询条件,判断合法
			if(memberAddressDTO.getWeightCondition1()!=null&&!"".equals(memberAddressDTO.getWeightCondition1())){
				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
				Matcher m = p.matcher(memberAddressDTO.getWeightCondition1()
						.toString());
				if (!m.matches()) {

					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物重量只能输入一位小数点的正数");
					renderJson(result, request, response);
					return;
				}
			}
			if(memberAddressDTO.getWeightCondition2()!=null&&!"".equals(memberAddressDTO.getWeightCondition2())){
				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
				Matcher m = p.matcher(memberAddressDTO.getWeightCondition2()
						.toString());
				if (!m.matches()) {

					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物重量只能输入一位小数点的正数");
					renderJson(result, request, response);
					return;
				}
			}
			if(memberAddressDTO.getWeightCondition2()!=null&&memberAddressDTO.getWeightCondition1()!=null&&!"".equals(memberAddressDTO.getWeightCondition2())&&!"".equals(memberAddressDTO.getWeightCondition1())&&Double.parseDouble(memberAddressDTO.getWeightCondition2())!=0){
				if(Double.parseDouble(memberAddressDTO.getWeightCondition2())<Double.parseDouble(memberAddressDTO.getWeightCondition1())){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物重量前面输入的数字不要大于后面输入的数字");
					renderJson(result, request, response);
					return;
				}
			}
			
			if(memberAddressDTO.getSizeCondition1()!=null&&!"".equals(memberAddressDTO.getSizeCondition1())){
				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
				Matcher m = p.matcher(memberAddressDTO.getSizeCondition1()
						.toString());
				if (!m.matches()) {

					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物体积只能输入一位小数点的正数");
					renderJson(result, request, response);
					return;
				}
			}
			if(memberAddressDTO.getSizeCondition2()!=null&&!"".equals(memberAddressDTO.getSizeCondition2())){
				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
				Matcher m = p.matcher(memberAddressDTO.getSizeCondition2()
						.toString());
				if (!m.matches()) {

					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物体积只能输入一位小数点的正数");
					renderJson(result, request, response);
					return;
				}
			}
			if(memberAddressDTO.getSizeCondition2()!=null&&memberAddressDTO.getSizeCondition1()!=null&&!"".equals(memberAddressDTO.getSizeCondition1())&&!"".equals(memberAddressDTO.getSizeCondition2())&&Double.parseDouble(memberAddressDTO.getSizeCondition2())!=0){
				if(Double.parseDouble(memberAddressDTO.getSizeCondition2())<Double.parseDouble(memberAddressDTO.getSizeCondition1())){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物体积前面输入的数字不要大于后面输入的数字");
					renderJson(result, request, response);
					return;
				}
			}
			
			if(memberAddressDTO.getCarLengthCondition1()!=null&&memberAddressDTO.getCarLengthCondition2()!=null&&!"".equals(memberAddressDTO.getCarLengthCondition2())&&!"".equals(memberAddressDTO.getCarLengthCondition1())&&Double.parseDouble(memberAddressDTO.getCarLengthCondition2())!=0){
				if(Double.parseDouble(memberAddressDTO.getCarLengthCondition2())<Double.parseDouble(memberAddressDTO.getCarLengthCondition1())){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("车辆长度前面输入的数字不要大于后面输入的数字");
					renderJson(result, request, response);
					return;
				}
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
			Integer userType=null;
			if(memberAddressDTO.getUserType()!=null){
				userType=memberAddressDTO.getUserType();
			}
			//int userType=memberAddressDTO.getUserType();
			Long memberId=null;
			if(memberAddressDTO.getUserId()!=null){
				memberId=memberAddressDTO.getUserId();;
			}else{
				//memberId=memberAddressDTO.getMemberId();;
			}
		//传默认的GPS定位城市  add by yanghaoyu ,2016/01/28 线路增加默认城市
			
			if(memberAddressDTO.getS_areaId()!=null&&memberAddressDTO.getS_cityId()!=null&&memberAddressDTO.getS_provinceId()!=null    && memberAddressDTO.getS_areaId()==1&&memberAddressDTO.getS_cityId()==1&&memberAddressDTO.getS_provinceId()==1&&memberAddressDTO.getmCity()!=null&&!"".equals(memberAddressDTO.getmCity())){
			
				AreaDTO ad=memberAddressApiService.getArea(memberAddressDTO.getmCity());
				if(ad!=null){
					if(ad.getAreaID()!=null){
						memberAddressDTO.setS_cityId(Long.valueOf(ad.getAreaID()));
						//可以直接获取上级目录Id不需要再查数据库
						//AreaDTO adf=memberAddressApiService.getArea(ad.getFather());
						//if(adf!=null){
							if(ad.getFather()!=null&&!"".equals(ad.getFather())){
								memberAddressDTO.setS_provinceId(Long.valueOf(ad.getFather()));
								memberAddressDTO.setS_areaId(Long.valueOf(0));
							}
						//}
					}
				}		
			}
	        //end 
			int total =  memberAddressApiService.getCountByConditionNst2(memberAddressDTO);
			memberAddressDTO.setStartRow(Integer.parseInt(map.get(START_ROW).toString()));
			memberAddressDTO.setEndRow(Integer.parseInt(map.get(END_ROW).toString()));
			memberAddressDTO.setUserId(memberId);
			memberAddressDTO.setUserType(userType);
			List<MemberAddressDTO> list = memberAddressApiService.getMemberAddressByConditionNewNst2(memberAddressDTO);
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSendGoodsType()!=null){
			   // if(list.get(i).getSendGoodsType()==0){
			    	if(list.get(i).getPrice()==null||list.get(i).getPrice()==0){
			    		list.get(i).setPriceUnitTypeString("面议");
			    	}else{
//						if(list.get(i).getPrice()==0){
//							list.get(i).setPriceUnitTypeString("面议");
//						}
			    		list.get(i).setPriceUnitTypeString("元");
			    	}
				

			    }
			    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
//			    if(list.get(i).getSendGoodsType()==1){
//			    	
//			    	if(list.get(i).getPrice()==null){
//			    		list.get(i).setPriceUnitTypeString("面议");
//			    	}else{
//						if(list.get(i).getPrice()==0){
//							list.get(i).setPriceUnitTypeString("面议");
//						}else{
//							list.get(i).setPriceUnitTypeString("");
//						}
//			    	}
//			    }
			    //选择其他的时候 ,都为面议
//			    if(list.get(i).getSendGoodsType()==2){
//						list.get(i).setPriceUnitTypeString("面议");							
//
//			    }
//				}else{
//				    	list.get(i).setPriceUnitTypeString("面议");
//				    }
//				
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
					//增加头像
					//判断是否可以接单  ,如果userId或者createUserId等于当前登录人都不能接单
					list.get(i).setIfOrder("Y");
					if(list.get(i).getCreateUserId()!=null&& !"".equals(list.get(i).getCreateUserId()) && memberId!=null && Long.parseLong(list.get(i).getCreateUserId())==memberId.longValue()){
						list.get(i).setIfOrder("N");
					}
					if(list.get(i).getUserId()!=null && memberId!=null && list.get(i).getUserId().longValue()==memberId.longValue()){
					list.get(i).setIfOrder("N");
				}
					if(list.get(i).getOrderStatus()!=null&&!"".equals(list.get(i).getOrderStatus())&&!"0".equals(list.get(i).getOrderStatus())){
						list.get(i).setIfOrder("N");
						if("4".equals(list.get(i).getOrderStatus())||"5".equals(list.get(i).getOrderStatus())){
							list.get(i).setIfOrder("Y");
						}
					}
					//增加头像
					list.get(i).setAndupurl(imageHost+list.get(i).getAndupurl());
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
	 * 用户增加货源信息列表
	 * 2.0版本增加里程
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addMemberAddress")
	public void addMemberAddress(HttpServletRequest request,
			HttpServletResponse response, MemberAddressDTO memberAddressDTO) {
		ObjectResult result = new ObjectResult();
		//int usertype = memberAddressDTO.getUserType();

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
			}
            //农速2.0新增逻辑,个人用户,是维护城市的可以不用选择公司进行发布

			if(memberAddressDTO.getApp()!=null&&!"".equals(memberAddressDTO.getApp())&&"ad".equals(memberAddressDTO.getApp())){

				if (memberAddressDTO.getTotalWeightString() != null) {
					Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
					Matcher m = p.matcher(memberAddressDTO.getTotalWeightString());
					if (!m.matches()) {

						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("重量只能输入一位小数点的正数");
						renderJson(result, request, response);
						return;
					}
				}
				if(memberAddressDTO.getTotalWeightString()!=null && !"".equals(memberAddressDTO.getTotalWeightString())){
					memberAddressDTO.setTotalWeight(Double.parseDouble(memberAddressDTO.getTotalWeightString()));
				}
			}else{
				if (memberAddressDTO.getTotalWeight() != null) {
					Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
					Matcher m = p.matcher(memberAddressDTO.getTotalWeight().toString());
					if (!m.matches()) {

						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("重量只能输入一位小数点的正数");
						renderJson(result, request, response);
						return;
					}
				}
			}
			if(memberAddressDTO.getUserType()!=null && memberAddressDTO.getUserType()==1){
			  int i=	this.checkCity(memberAddressDTO.getmCity());
			  if(i<1){
				  if(memberAddressDTO.getCreateUserId()==null||"".equals(memberAddressDTO.getCreateUserId())){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("请选择公司发布货源");
						renderJson(result, request, response);
						return;
				  }

			  }
			}
			if(memberAddressDTO.getUserType()!=null && memberAddressDTO.getUserType()==2){
				  if(memberAddressDTO.getCreateUserId()==null||"".equals(memberAddressDTO.getCreateUserId())){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("请选择公司发布货源");
						renderJson(result, request, response);
						return;
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
							&& memberAddressDTO.getEndDate().getTime() == memberAddressDTO.getSendDate().getTime()) {
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
					}else{
						f_detail = memberAddressDTO.getF_detail();
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
					}else{
						s_detail = memberAddressDTO.getS_detail();
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
					}else{
						goodsName = memberAddressDTO.getGoodsName();
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
			if (memberAddressDTO.getCreateUserId() != null && !"".equals(memberAddressDTO.getCreateUserId())) {
				memberAddressDTO.setCreateUserId(memberAddressDTO
						.getCreateUserId());
			}else{
				memberAddressDTO.setCreateUserId(memberAddressDTO
						.getUserId()+"");
			}
			if (memberAddressDTO.getPrice() == null
					|| memberAddressDTO.getPrice() == 0) {
				memberAddressDTO.setPrice(0.00);
			} else {
				if (memberAddressDTO.getPrice().doubleValue() > 10000000) {
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("请输入一个小于10000000的价格");
					renderJson(result, request, response);
					return;
				}
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
				} 

				
			}
			String remark = "";

			if (!StringUtils.isBlank(memberAddressDTO.getRemark())) {
				if (!StringUtils.isBlank(memberAddressDTO.getApp())) {
					if ("IOS".equals(memberAddressDTO.getApp())) {
						remark = new String(memberAddressDTO.getRemark()
								.getBytes("utf-8"), "utf-8");
					}else{
						remark = memberAddressDTO.getRemark();
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
			//begin 新增加驾车里程计算 add By 杨浩宇  2016-03-10版本
			String startCity="";
			String endCity="";
			if(memberAddressDTO.getS_provinceId()!=null&&memberAddressDTO.getS_provinceId().longValue()!=0&&memberAddressDTO.getS_provinceId().longValue()!=-1){
				if(memberAddressDTO.getS_provinceId().longValue()==110000||memberAddressDTO.getS_provinceId().longValue()==120000||memberAddressDTO.getS_provinceId().longValue()==310000||memberAddressDTO.getS_provinceId().longValue()==500000){
								startCity=areaToolService.getByAreaId(memberAddressDTO.getS_provinceId()).getArea();	
					
				}else{
					if(memberAddressDTO.getS_cityId()!=null&&memberAddressDTO.getS_cityId().longValue()!=0&&memberAddressDTO.getS_cityId().longValue()!=-1){
						startCity=areaToolService.getByAreaId(memberAddressDTO.getS_cityId()).getArea();
					}
					
				}
					
			}
			if(memberAddressDTO.getF_provinceId()!=null&&memberAddressDTO.getF_provinceId().longValue()!=0&&memberAddressDTO.getF_provinceId().longValue()!=-1){
				
			
					
			if(	memberAddressDTO.getF_provinceId().longValue()==110000||memberAddressDTO.getF_provinceId().longValue()==120000||memberAddressDTO.getF_provinceId().longValue()==310000||memberAddressDTO.getF_provinceId().longValue()==500000){
					endCity=areaToolService.getByAreaId(memberAddressDTO.getF_provinceId()).getArea();				
				
			}else{
				if(memberAddressDTO.getF_cityId()!=null&&memberAddressDTO.getF_cityId().longValue()!=0&&memberAddressDTO.getF_cityId().longValue()!=-1){
					
				endCity=areaToolService.getByAreaId(memberAddressDTO.getF_cityId()).getArea();
				}
			}
			}
			if(!"".equals(startCity)&&!"".equals(endCity)){
				String mileage=MapUtil.getDsitanceByArea(startCity,endCity);
				memberAddressDTO.setMileage(mileage);
			}else{
				memberAddressDTO.setMileage("-1");
			}
			
			if (memberAddressDTO.getCreateUserId() != null) {
				memberAddressDTO.setCreateUserId(memberAddressDTO
						.getCreateUserId());
			}else{
				memberAddressDTO.setCreateUserId(memberAddressDTO
						.getUserId()+"");
				createUserId=memberAddressDTO
						.getUserId()+"";
			}
		
			//如果来源是农速通,参数 clients 设置成空, 由线程去决定是否增加clients参数,其他客户端直接插入参数, 以分辨是一手货源还是二手
			String clients=memberAddressDTO.getClients();
			if(memberAddressDTO.getClients()!=null&&!"".equals(memberAddressDTO.getClients())&&"2".equals(memberAddressDTO.getClients())){
				memberAddressDTO.setClients(null);
			}
			//end 新增加驾车里程计算 add By 杨浩宇  2016-03-10版本
			int i = memberAddressApiService.addMemberAddress(memberAddressDTO);
			// 判断是都插入成功,>0位成功,否则则失败
			if (i > 0) {
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");

				// **推送消息给企业
//				MemberBaseinfoDTO memberDTO2 = memberToolService.getById(createUserId);
//				UMengPushMessage pushMessage = new UMengPushMessage();
//				GdMessageDTO gdMessage = new GdMessageDTO();
//				gdMessage.setSendApp("2");
//				gdMessage.setSendType("1");
//				gdMessage.setTicket("【农速通告诉您有新的货源信息】");
//				gdMessage.setTitle("农速通告诉您有新的货源信息");
//				gdMessage.setContent("有个人用户委托货源给您,请查收");
//				gdMessage.setAfter_open("go_app");
				// gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
				// ios:9c6031b0c53f823498214ed8e9ba4ed65b2127633f6611836c2e93abdf0d2e8b
				// android:AsfEx_AIdKOZuTfL55scurKF6PRdP3Klx0khdM3MmKM2
				//gdMessage.setDevice_tokens(memberDTO2.getDevice_tokens());
//				gdMessage.setProduction_mode(true);
//				pushMessage.pushMessage(gdMessage);

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

				// 创建增加推荐路线信息线程
				Runnable thread = new AddCarLineMessageThread(memberDTO,
						memberAddressDTO, memberAddressApiService);
				Thread acmThread = new Thread(thread);
				acmThread.start();
				//add by yanghaoyu 2016-04-20  如果是农速通客户端,且userType为个人的话
				memberAddressDTO.setClients(clients);
				if(memberAddressDTO.getClients()!=null&&!"".equals(memberAddressDTO.getClients())&&"2".equals(memberAddressDTO.getClients())&&memberAddressDTO.getUserType()==1){
					int flag= memberAddressApiService
						.getRule(memberAddressDTO.getClients());
					if(flag==1){
						Runnable thread2 = new RuleForMemberThread(memberDTO,
								memberAddressDTO, memberAddressApiService);
						Thread rfmThread = new Thread(thread2);
						rfmThread.start();
					}
					

				}
				if(memberAddressDTO.getClients()!=null&&!"".equals(memberAddressDTO.getClients())&&!"2".equals(memberAddressDTO.getClients())){
					int flag= memberAddressApiService
							.getRule(memberAddressDTO.getClients());
						if(flag==1){
							Runnable thread2 = new RuleForMemberThread(memberDTO,
									memberAddressDTO, memberAddressApiService);
							Thread rfmThread = new Thread(thread2);
							rfmThread.start();
						}
				}


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
            //农速2.0新增逻辑,个人用户,是维护城市的可以不用选择公司进行发布
			if(memberAddressDTO.getApp()!=null&&!"".equals(memberAddressDTO.getApp())&&"ad".equals(memberAddressDTO.getApp())){

				if (memberAddressDTO.getTotalWeightString() != null) {
					Pattern p2 = Pattern.compile("^[1-9]\\d*$ ");
					Pattern p3 = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
					
					Matcher m2 = p2.matcher(memberAddressDTO.getTotalWeightString());
					Matcher m3 = p3.matcher(memberAddressDTO.getTotalWeightString());
						if (!m3.matches()) {

							if (!m2.matches()) {
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("重量只能输入一位小数点的正数");
								renderJson(result, request, response);
								return;
							}
						}
				}
				if(memberAddressDTO.getTotalWeightString()!=null && !"".equals(memberAddressDTO.getTotalWeightString())){
					memberAddressDTO.setTotalWeight(Double.parseDouble(memberAddressDTO.getTotalWeightString()));
				}
			}else{
				if (memberAddressDTO.getTotalWeight() != null) {
					Pattern p2 = Pattern.compile("^[1-9]\\d*$ ");
					Pattern p3 = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
					
					Matcher m2 = p2.matcher(memberAddressDTO.getTotalWeight().toString());
					Matcher m3 = p3.matcher(memberAddressDTO.getTotalWeight().toString());
						if (!m3.matches()) {

							if (!m2.matches()) {
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("重量只能输入一位小数点的正数");
								renderJson(result, request, response);
								return;
							}
						}
				}
			}
			if(memberAddressDTO.getUserType()!=null && memberAddressDTO.getUserType()==1){
			  int i=	this.checkCity(memberAddressDTO.getmCity());
			  if(i<1){
				  if(memberAddressDTO.getCreateUserId()==null||"".equals(memberAddressDTO.getCreateUserId())){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("请选择公司发布货源");
						renderJson(result, request, response);
						return;
				  }

			  }
			}
			if(memberAddressDTO.getUserType()!=null && memberAddressDTO.getUserType()==2){
				  if(memberAddressDTO.getCreateUserId()==null||"".equals(memberAddressDTO.getCreateUserId())){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("请选择公司发布货源");
						renderJson(result, request, response);
						return;
				  }
				}
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
				if (memberAddressDTO.getPrice().doubleValue() > 10000000) {
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("请输入一个小于10000000的价格");
					renderJson(result, request, response);
					return;
				}
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
			//begin 新增加驾车里程计算 add By 杨浩宇  2016-03-10版本
			String startCity="";
			String endCity="";
			if(memberAddressDTO.getS_provinceId()!=null&&memberAddressDTO.getS_provinceId().longValue()!=0&&memberAddressDTO.getS_provinceId().longValue()!=-1){
				if(memberAddressDTO.getS_provinceId().longValue()==110000||memberAddressDTO.getS_provinceId().longValue()==120000||memberAddressDTO.getS_provinceId().longValue()==310000||memberAddressDTO.getS_provinceId().longValue()==500000){
								startCity=areaToolService.getByAreaId(memberAddressDTO.getS_provinceId()).getArea();	
					
				}else{
					if(memberAddressDTO.getS_cityId()!=null&&memberAddressDTO.getS_cityId().longValue()!=0&&memberAddressDTO.getS_cityId().longValue()!=-1){
						startCity=areaToolService.getByAreaId(memberAddressDTO.getS_cityId()).getArea();
					}
					
				}
					
			}
			if(memberAddressDTO.getF_provinceId()!=null&&memberAddressDTO.getF_provinceId().longValue()!=0&&memberAddressDTO.getF_provinceId().longValue()!=-1){
				
			
					
			if(	memberAddressDTO.getF_provinceId().longValue()==110000||memberAddressDTO.getF_provinceId().longValue()==120000||memberAddressDTO.getF_provinceId().longValue()==310000||memberAddressDTO.getF_provinceId().longValue()==500000){
					endCity=areaToolService.getByAreaId(memberAddressDTO.getF_provinceId()).getArea();				
				
			}else{
				if(memberAddressDTO.getF_cityId()!=null&&memberAddressDTO.getF_cityId().longValue()!=0&&memberAddressDTO.getF_cityId().longValue()!=-1){
					
				endCity=areaToolService.getByAreaId(memberAddressDTO.getF_cityId()).getArea();
				}
			}
			}
			if(!"".equals(startCity)&&!"".equals(endCity)){
				String mileage=MapUtil.getDsitanceByArea(startCity,endCity);
				memberAddressDTO.setMileage(mileage);
			}else{
				memberAddressDTO.setMileage("-1");
			}
			// 当前是企业用户的ID
			if (memberAddressDTO.getCreateUserId() != null) {
				memberAddressDTO.setCreateUserId(memberAddressDTO
						.getCreateUserId());
			}else{
				memberAddressDTO.setCreateUserId(memberAddressDTO
						.getUserId()+"");
				createUserId=memberAddressDTO
						.getUserId()+"";
			}
		
			
			
			//end 新增加驾车里程计算 add By 杨浩宇  2016-03-10版本
			int i = memberAddressApiService.updateMemberAddress(memberAddressDTO);
			result.setObject(null);
			//判断是都插入成功,>0位成功,否则则失败
			if(i>0){
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
//				UMengPushMessage pushMessage = new UMengPushMessage();
//				GdMessageDTO gdMessage = new GdMessageDTO();
//				gdMessage.setSendApp("2");
//				gdMessage.setSendType("1");
//				gdMessage.setTicket("【农速通告诉您有新的货源信息】");
//				gdMessage.setTitle("农速通告诉您有新的货源信息");
//				gdMessage.setAfter_open("go_app");
//				gdMessage.setProduction_mode(true);
//				MemberBaseinfoDTO memberDTO2 = memberToolService.getById(createUserId);
				//MemberBaseinfoDTO memberDTO4 = memberToolService.getById(memberAddressDTO.getUserId()+"");
				if(userType==1){
					
					
					if(Long.parseLong(oldCreateUserId)!=Long.parseLong(createUserId)){
					//**推送消息给企业
//						MemberBaseinfoDTO memberDTO3 = memberToolService.getById(oldCreateUserId);


//						gdMessage.setContent("有个人用户委托货源给您,请查收");
//
//						gdMessage.setDevice_tokens(memberDTO2.getDevice_tokens());
//
//						pushMessage.pushMessage(gdMessage);
//			            gdMessage.setDevice_tokens(memberDTO3.getDevice_tokens());
//			            gdMessage.setContent("有个人用户委托货源已经从您名下转移到其他企业名下,请查收");
//						pushMessage.pushMessage(gdMessage);

					}else{
//						gdMessage.setContent("有个人用户对委托货源进行修改您,请查收");
//
//						gdMessage.setDevice_tokens(memberDTO2.getDevice_tokens());
//
//						pushMessage.pushMessage(gdMessage);
					}
				}
//				else if(userType==2){
//					gdMessage.setContent("您委托的货源信息,被委托企业进行修改,请查收");
//
//					gdMessage.setDevice_tokens(memberDTO4.getDevice_tokens());
//
//					pushMessage.pushMessage(gdMessage);
//				}
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
	 * 根据线路下订单的时候,
	 * @param request
	 * @param response
	 */
	@RequestMapping("getMemberAddressByCarLine")
	@ResponseBody
	public void getCarWeighProList(CarLineDTO carLineDTO, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			
			
			List<CarLineDTO> list = carLineApiService.getCarlineApiByConditionNew(carLineDTO);
			if(list!=null &&list.size()>0){
				list.get(0).setMemberId(carLineDTO.getMemberId());
				List<MemberAddressDTO> list2 = memberAddressApiService.getMemberAddressByCarLine(list.get(0));
				//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
				for (int i = 0; i < list2.size(); i++) {
					if(list2.get(i).getCreateTime()!=null){					
						String time=DateTimeUtils.getTimeBetween(list2.get(i).getCreateTime());
						list2.get(i).setTimebefore(time);
					}
					list2.get(i).getId();
					if(list2.get(i).getS_areaId()!=null&&list2.get(i).getS_provinceId()!=null&&list2.get(i).getS_cityId()!=null&&list2.get(i).getS_areaId()==0&&list2.get(i).getS_provinceId()==0&&list2.get(i).getS_cityId()==0){
						list2.get(i).setS_provinceIdString("全国");

					}
					if(list2.get(i).getF_areaId()!=null&&list2.get(i).getF_provinceId()!=null&&list2.get(i).getF_cityId()!=null&&list2.get(i).getF_areaId()==0&&list2.get(i).getF_provinceId()==0&&list2.get(i).getF_cityId()==0){
						list2.get(i).setF_provinceIdString("全国");

					}
					if(list2.get(i).getF_provinceId()!=null&&list2.get(i).getF_provinceId()>1){
						list2.get(i).setF_provinceIdString(memberAddressApiService.getAreaString(list2.get(i).getF_provinceId()));  
						}
						if(list2.get(i).getF_cityId()!=null&&list2.get(i).getF_cityId()>1){
							list2.get(i).setF_cityIdString(memberAddressApiService.getAreaString(list2.get(i).getF_cityId()));
						}
						if(list2.get(i).getF_areaId()!=null&&list2.get(i).getF_areaId()>1){
							list2.get(i).setF_areaIdString(memberAddressApiService.getAreaString(list2.get(i).getF_areaId()));
						}
						                                      

						if(list2.get(i).getS_provinceId()!=null&&list2.get(i).getS_provinceId()>1){
							list2.get(i).setS_provinceIdString(memberAddressApiService.getAreaString(list2.get(i).getS_provinceId()));
						}
						if(list2.get(i).getS_cityId()!=null &&list2.get(i).getS_cityId()>1){
							list2.get(i).setS_cityIdString(memberAddressApiService.getAreaString(list2.get(i).getS_cityId()));
						}
						if(list2.get(i).getS_areaId()!=null &&list2.get(i).getS_areaId()>1){
							list2.get(i).setS_areaIdString(memberAddressApiService.getAreaString(list2.get(i).getS_areaId()));
						}
						if(list2.get(i).getF_cityId()!=null&&list2.get(i).getF_cityId()!=0){
							if ("市辖区".equals(list2.get(i).getF_cityIdString()) || "县".equals(list2.get(i).getF_cityIdString())||"市".equals(list2.get(i).getF_cityIdString())|| "省直辖行政单位".equals(list2.get(i).getF_cityIdString())){
								list2.get(i).setF_cityIdString("");
							}else{
								list2.get(i).setF_provinceIdString("");
							}
							
						}
						if(list2.get(i).getS_cityId()!=null&&list2.get(i).getS_cityId()!=0){
							if ("市辖区".equals(list2.get(i).getS_cityIdString()) || "县".equals(list2.get(i).getS_cityIdString())||"市".equals(list2.get(i).getS_cityIdString())|| "省直辖行政单位".equals(list2.get(i).getS_cityIdString())){
								list2.get(i).setS_cityIdString("");
							}else{
								list2.get(i).setS_provinceIdString("");
							}
							
						}
						if(list2.get(i).getF_areaId()!=null&&list2.get(i).getF_areaId()!=0){
							if ("市辖区".equals(list2.get(i).getF_areaIdString()) || "县".equals(list2.get(i).getF_areaIdString())||"市".equals(list2.get(i).getF_areaIdString())|| "省直辖行政单位".equals(list2.get(i).getF_areaIdString())){
								list2.get(i).setF_areaIdString("");
							}
							
						}
						if(list2.get(i).getS_areaId()!=null&&list2.get(i).getS_areaId()!=0){
							if ("市辖区".equals(list2.get(i).getS_areaIdString()) || "县".equals(list2.get(i).getS_areaIdString())||"市".equals(list2.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list2.get(i).getS_areaIdString())){
								list2.get(i).setS_areaIdString("");
							}
							
						}

				
				}
				result.setObject(list2);
				result.setMsg("success");
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			}

		}catch(Exception e){
			logger.info("获取线路订单的货源列表异常", e);
			result.setMsg("获取线路订单的货源列表异常,请稍后再试");
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 获取车主找货的列表 ,不带分页
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getMemberAddressById")
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
			Long memberId=	memberAddressDTO.getUserId();	
			List<MemberAddressDTO> list = memberAddressApiService.getMemberAddressByIdNst2(memberAddressDTO);	
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSendGoodsType()!=null){
			 //   if(list.get(i).getSendGoodsType()==0){
			    	if(list.get(i).getPrice()==null||list.get(i).getPrice()==0){
			    		list.get(i).setPriceUnitTypeString("面议");
			    	}else{
//						if(list.get(i).getPrice()==0){
//							list.get(i).setPriceUnitTypeString("面议");
//						}
			 //   	}
			    		list.get(i).setPriceUnitTypeString("元");	
			    	}
			    }
			    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
//			    if(list.get(i).getSendGoodsType()==1){
//			    	
//			    	if(list.get(i).getPrice()==null){
//			    		list.get(i).setPriceUnitTypeString("面议");
//			    	}else{
//						if(list.get(i).getPrice()==0){
//							list.get(i).setPriceUnitTypeString("面议");
//						}else{
//							list.get(i).setPriceUnitTypeString("元/车");
//						}
//			    	}
//			    }
//			    //选择其他的时候 ,都为面议
//			    if(list.get(i).getSendGoodsType()==2){
//						list.get(i).setPriceUnitTypeString("面议");							
//
//			    }
//				}else{
//				    	list.get(i).setPriceUnitTypeString("面议");
//				    }
				
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
//				if(list.get(i).getCarLength()==null||list.get(i).getCarLength()==0){
//					if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
//						list.get(i).setS_areaIdString("");
//					}
//					
//				}
				//判断是否可以接单,如果当前登录用户的ID等于货源的CreateUserId 则,为N ,如果userId等于当前登录用户的Id也为N
				list.get(i).setIfOrder("Y");
				if(list.get(i).getCreateUserId()!=null && !"".equals(list.get(i).getCreateUserId()) && memberId!=null && Long.parseLong(list.get(i).getCreateUserId())==memberId.longValue()){
					list.get(i).setIfOrder("N");
				}
				if(list.get(i).getUserId()!=null && memberId!=null && list.get(i).getUserId().longValue()==memberId.longValue()){
				list.get(i).setIfOrder("N");
			}
				if(list.get(i).getOrderStatus()!=null&&!"".equals(list.get(i).getOrderStatus())&&!"0".equals(list.get(i).getOrderStatus())){
					list.get(i).setIfOrder("N");
					if("4".equals(list.get(i).getOrderStatus())||"5".equals(list.get(i).getOrderStatus())){
						list.get(i).setIfOrder("Y");
					}
				}
				//增加头像

				list.get(i).setAndupurl(imageHost+list.get(i).getAndupurl());
			}
			
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			
			if(list!=null&&list.size()==0&&memberAddressDTO!=null&&memberAddressDTO.getId()!=null){
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("此条信息刚刚已被删除!");
			}
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
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * 
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/checkCity")
	public int checkCity(String mcity) {
		ObjectResult result = new ObjectResult();
		int i=0;
		try {
			 i=memberAddressApiService.checkCity(mcity);
			
		} catch (Exception e) {
			logger.info("发布货源时候检查是否需求选择公司的城市失败", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("发布货源时候检查是否需求选择公司的城市失败");
		}
		return i;
	}
    
	
	
	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * 
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/getMemberAddressByUserCondition")
	public void listMemberAddressByUserCondition(HttpServletRequest request,
			HttpServletResponse response,MemberAddressDTO memberAddressDTO) {
		ObjectResult result = new ObjectResult();
	
		try {
			if(memberAddressDTO.getApp()!=null&&!"".equals(memberAddressDTO.getApp())&&"ad".equals(memberAddressDTO.getApp())){
				String s_provinceId=memberAddressDTO.getS_provinceIdS();
				String s_cityId=memberAddressDTO.getS_cityIdS();
				String s_areaId=memberAddressDTO.getS_areaIdS();
				String e_provinceId=memberAddressDTO.getE_provinceIdS();
				String e_cityId=memberAddressDTO.getE_cityIdS();
				String e_areaId=memberAddressDTO.getE_areaIdS(); 
				if(memberAddressDTO.getCarLengthString()!=null && !"".equals(memberAddressDTO.getCarLengthString())){
					memberAddressDTO.setCarLength(Double.parseDouble(memberAddressDTO.getCarLengthString()));
				}
				if(memberAddressDTO.getCarTypeStringCondition()!=null && !"".equals(memberAddressDTO.getCarTypeStringCondition())){
					memberAddressDTO.setCarType(Integer.parseInt(memberAddressDTO.getCarTypeStringCondition()));
				}
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
			}
			
			//
			Map<String, Object> map = new HashMap<String, Object>();
			CommonPageDTO pageDTO = null;
			if(null != request.getParameter("pageNum") && !"".equals(request.getParameter("pageNum"))){
				pageDTO = getPageInfo(request, map);
			}
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
			
			
			//新增查询条件,判断合法
			if(memberAddressDTO.getWeightCondition1()!=null&&!"".equals(memberAddressDTO.getWeightCondition1().toString())){
				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
				Matcher m = p.matcher(memberAddressDTO.getWeightCondition1()
						.toString());
				if (!m.matches()) {

					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物重量只能输入一位小数点的正数");
					renderJson(result, request, response);
					return;
				}
			}
			if(memberAddressDTO.getWeightCondition2()!=null&&!"".equals(memberAddressDTO.getWeightCondition2())){
				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
				Matcher m = p.matcher(memberAddressDTO.getWeightCondition2()
						.toString());
				if (!m.matches()) {

					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物重量只能输入一位小数点的正数");
					renderJson(result, request, response);
					return;
				}
			}
			if(memberAddressDTO.getWeightCondition2()!=null&&memberAddressDTO.getWeightCondition1()!=null&&!"".equals(memberAddressDTO.getWeightCondition2())&&!"".equals(memberAddressDTO.getWeightCondition1())&&Double.parseDouble(memberAddressDTO.getWeightCondition2())!=0){
				if(Double.parseDouble(memberAddressDTO.getWeightCondition2())<Double.parseDouble(memberAddressDTO.getWeightCondition1())){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("货物重量前面输入的数字不要大于后面输入的数字");
					renderJson(result, request, response);
					return;
				}
			}
			
//			if(memberAddressDTO.getSizeCondition1()!=null&&!"".equals(memberAddressDTO.getSizeCondition1())){
//				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
//				Matcher m = p.matcher(memberAddressDTO.getSizeCondition1()
//						.toString());
//				if (!m.matches()) {
//
//					result.setObject(null);
//					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
//					result.setMsg("货物体积只能输入一位小数点的正数");
//					renderJson(result, request, response);
//					return;
//				}
//			}
//			if(memberAddressDTO.getSizeCondition2()!=null&&!"".equals(memberAddressDTO.getSizeCondition2())){
//				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
//				Matcher m = p.matcher(memberAddressDTO.getSizeCondition2()
//						.toString());
//				if (!m.matches()) {
//
//					result.setObject(null);
//					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
//					result.setMsg("货物体积只能输入一位小数点的正数");
//					renderJson(result, request, response);
//					return;
//				}
//			}
//			if(memberAddressDTO.getSizeCondition2()!=null&&memberAddressDTO.getSizeCondition1()!=null&&!"".equals(memberAddressDTO.getSizeCondition1())&&!"".equals(memberAddressDTO.getSizeCondition2())&&Double.parseDouble(memberAddressDTO.getSizeCondition2())!=0){
//				if(Double.parseDouble(memberAddressDTO.getSizeCondition2())<Double.parseDouble(memberAddressDTO.getSizeCondition1())){
//					result.setObject(null);
//					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
//					result.setMsg("货物体积前面输入的数字不要大于后面输入的数字");
//					renderJson(result, request, response);
//					return;
//				}
//			}
			
//			if(memberAddressDTO.getCarLengthCondition1()!=null&&memberAddressDTO.getCarLengthCondition2()!=null&&!"".equals(memberAddressDTO.getCarLengthCondition2())&&!"".equals(memberAddressDTO.getCarLengthCondition1())&&Double.parseDouble(memberAddressDTO.getCarLengthCondition2())!=0){
//				if(Double.parseDouble(memberAddressDTO.getCarLengthCondition2())<Double.parseDouble(memberAddressDTO.getCarLengthCondition1())){
//					result.setObject(null);
//					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
//					result.setMsg("车辆长度前面输入的数字不要大于后面输入的数字");
//					renderJson(result, request, response);
//					return;
//				}
//			}
			
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
			Integer userType=null;
			if(memberAddressDTO.getUserType()!=null){
				userType=memberAddressDTO.getUserType();
			}
			//int userType=memberAddressDTO.getUserType();
			Long memberId=null;
			if(memberAddressDTO.getUserId()!=null){
				memberId=memberAddressDTO.getUserId();;
			}else{
				//memberId=memberAddressDTO.getMemberId();;
			}
		//传默认的GPS定位城市  add by yanghaoyu ,2016/01/28 线路增加默认城市
			
			if(memberAddressDTO.getS_areaId()!=null&&memberAddressDTO.getS_cityId()!=null&&memberAddressDTO.getS_provinceId()!=null    && memberAddressDTO.getS_areaId()==1&&memberAddressDTO.getS_cityId()==1&&memberAddressDTO.getS_provinceId()==1&&memberAddressDTO.getmCity()!=null&&!"".equals(memberAddressDTO.getmCity())){
			
				AreaDTO ad=memberAddressApiService.getArea(memberAddressDTO.getmCity());
				if(ad!=null){
					if(ad.getAreaID()!=null){
						memberAddressDTO.setS_cityId(Long.valueOf(ad.getAreaID()));
						//可以直接获取上级目录Id不需要再查数据库
						//AreaDTO adf=memberAddressApiService.getArea(ad.getFather());
						//if(adf!=null){
							if(ad.getFather()!=null&&!"".equals(ad.getFather())){
								memberAddressDTO.setS_provinceId(Long.valueOf(ad.getFather()));
								memberAddressDTO.setS_areaId(Long.valueOf(0));
							}
						//}
					}
				}		
			}
			if(memberAddressDTO.getBeginTime()!=null&&!"".equals(memberAddressDTO.getBeginTime())&&memberAddressDTO.getEndTime()!=null&&!"".equals(memberAddressDTO.getEndTime())){
				//	boolean flag = memberAddressDTO.getEndDate().before(memberAddressDTO.getSendDate());
				//	if(flag){
				Date beginTime=DateUtil.formateDate(memberAddressDTO.getBeginTime());
				Date endTime=DateUtil.formateDate(memberAddressDTO.getEndTime());
					if(endTime.getTime()<beginTime.getTime()){
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("到达时间不能早于装车时间");
						renderJson(result, request, response);
						return;
					}
				}
	        //end 
			int total =  memberAddressApiService.getMemberAddressByUserConditionCount(memberAddressDTO);

			memberAddressDTO.setUserId(memberId);
			memberAddressDTO.setUserType(userType);
			memberAddressDTO.setStartRow((Integer)map.get("startRow"));
			memberAddressDTO.setEndRow((Integer)map.get("endRow"));
			List<MemberAddressDTO> list = memberAddressApiService.getMemberAddressByUserCondition(memberAddressDTO);
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
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
							list.get(i).setPriceUnitTypeString("元/车");
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
					//增加头像
					//判断是否可以接单  ,如果userId或者createUserId等于当前登录人都不能接单
					list.get(i).setIfOrder("Y");
					if(list.get(i).getCreateUserId()!=null&& !"".equals(list.get(i).getCreateUserId()) && memberId!=null && Long.parseLong(list.get(i).getCreateUserId())==memberId.longValue()){
						list.get(i).setIfOrder("N");
					}
					if(list.get(i).getUserId()!=null && memberId!=null && list.get(i).getUserId().longValue()==memberId.longValue()){
					list.get(i).setIfOrder("N");
				}
					if(list.get(i).getOrderStatus()!=null&&!"".equals(list.get(i).getOrderStatus())&&!"0".equals(list.get(i).getOrderStatus())){
						list.get(i).setIfOrder("N");
						if("4".equals(list.get(i).getOrderStatus())||"5".equals(list.get(i).getOrderStatus())){
							list.get(i).setIfOrder("Y");
						}
					}
					//增加头像
					list.get(i).setAndupurl(imageHost+list.get(i).getAndupurl());
			}
			//根据总数设置pageDTO信息
			if(null != request.getParameter("pageNum") && !"".equals(request.getParameter("pageNum"))){
				pageDTO.setRecordCount(total);
				pageDTO.initiatePage(total);
				pageDTO.setRecordList(list);
				result.setObject(pageDTO);
			}else{
				result.setObject(list);
			}
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
	
  
}


