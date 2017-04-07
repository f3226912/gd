package com.gudeng.commerce.gd.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.gudeng.commerce.gd.api.service.CarLineApiService;
import com.gudeng.commerce.gd.api.service.CarManagerApiService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.impl.MemberToolServiceImple;
import com.gudeng.commerce.gd.api.util.CarLineMessgaeThread;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.exception.BusinessException;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;
/**
 * 货车线路管理
 * 
 * @author yanghaoyu
 * 
 */
@Controller
@RequestMapping("carLineManager")
public class CarLineApiController extends GDAPIBaseController {
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(FocusCategoryController.class);
	@Autowired
	public CarLineApiService carLineApiService;
	@Autowired
	public MemberToolServiceImple memberToolServiceImple;
	@Autowired
	public MemberAddressApiService memberAddressApiService;
	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	public CarManagerApiService carManagerApiService;
	@Autowired
	private MemberCertifiApiService memberCertifiApiService;
	/**
	 * 获取用户的线路信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCarlineApiByCondition")
	public void getCarLineMessage(HttpServletRequest request,
			HttpServletResponse response,CarLineDTO carLineDTO) {
		ObjectResult result = new ObjectResult();
		try {
			if(carLineDTO.getId()==null||carLineDTO.getId()==0){
				if(carLineDTO.getCl_Id()!=null&&!"".equals(carLineDTO.getCl_Id())){
					carLineDTO.setId(Long.valueOf(carLineDTO.getCl_Id()));
				}
				if(carLineDTO.getCl_id()!=null&&!"".equals(carLineDTO.getCl_id())){
					carLineDTO.setId(Long.valueOf(carLineDTO.getCl_id()));
				}
			}
			String s_provinceId=carLineDTO.getS_provinceIdS();
			String s_cityId=carLineDTO.getS_cityIdS();
			String s_areaId=carLineDTO.getS_areaIdS();
			//carLineDTO.getS
			if(carLineDTO.getS_provinceId()==0){
				carLineDTO.setS_provinceId(!StringUtils.isEmpty(s_provinceId)?Long.parseLong(s_provinceId):0); 	
			}
			if(carLineDTO.getS_cityId()==0)  {
				carLineDTO.setS_cityId(!StringUtils.isEmpty(s_cityId)?Long.parseLong(s_cityId):0); 
			}
			if(carLineDTO.getS_areaId()==0){
				carLineDTO.setS_areaId(!StringUtils.isEmpty(s_areaId)?Long.parseLong(s_areaId):0);
			}
			                                        
			String e_provinceId=carLineDTO.getE_provinceIdS();
			String e_cityId=carLineDTO.getE_cityIdS();
			String e_areaId=carLineDTO.getE_areaIdS();
			if(carLineDTO.getE_provinceId()==0){
				carLineDTO.setE_provinceId(!StringUtils.isEmpty(e_provinceId)?Long.parseLong(e_provinceId):0);
			}
			if(carLineDTO.getE_cityId()==0){
				carLineDTO.setE_cityId(!StringUtils.isEmpty(e_cityId)?Long.parseLong(e_cityId):0); 	
			}
			if(carLineDTO.getE_areaId()==0){
				carLineDTO.setE_areaId(!StringUtils.isEmpty(e_areaId)?Long.parseLong(e_areaId):0); 
			}
			//获取手机GPS定位的时候的经纬度
			AreaDTO a=new AreaDTO();
			if(carLineDTO.getmCity()!=null&&!"".equals(carLineDTO.getmCity())){
				 a=memberAddressApiService.getArea(carLineDTO.getmCity());
				 carLineDTO.setMlng(a.getLng());
				 carLineDTO.setMlat(a.getLat());
			}else{
				carLineDTO.setMlng(Double.valueOf(0));
				carLineDTO.setMlat(Double.valueOf(0));
			}
			AreaDTO bj=memberAddressApiService.getArea("北京市");
			AreaDTO sh=memberAddressApiService.getArea("上海市");
			AreaDTO cq=memberAddressApiService.getArea("重庆市");
			AreaDTO tj=memberAddressApiService.getArea("天津市");
			
			carLineDTO.setBjlng(bj.getLng());
			carLineDTO.setBjlat(bj.getLat());
			
			
			carLineDTO.setShlng(sh.getLng());
			carLineDTO.setShlat(sh.getLat()); 
			
			
			carLineDTO.setTjlng(tj.getLng());
			carLineDTO.setTjlat(tj.getLat());
			
			
			carLineDTO.setCqlng(cq.getLng());
			carLineDTO.setCqlat(cq.getLat());
			List<CarLineDTO> list = carLineApiService.getCarlineApiByCondition(carLineDTO);
			//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
			for (int i = 0; i < list.size(); i++) {
//			    if("0".equals(list.get(i).getSendGoodsType())){
//					if(list.get(i).getPrice()==0){
//						list.get(i).setUnitTypeString("面议");
//					}
//			    }
//			    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
//			    if("1".equals(list.get(i).getSendGoodsType())){
//					if(list.get(i).getPrice()==0){
//						list.get(i).setUnitTypeString("面议");
//					}else{
//						list.get(i).setUnitTypeString("元");
//					}
//			    }
//			    //选择其他的时候 ,都为面议
//			    if("2".equals(list.get(i).getSendGoodsType())){
//						list.get(i).setUnitTypeString("面议");
//
//			    }
				//起始地一
			    if(list.get(i).getSendGoodsType()!=null){
				    
				    if(list.get(i).getPrice()!=null){
				    	if("0".equals(list.get(i).getSendGoodsType())){
				        if(list.get(i).getPrice()==null){
				        	list.get(i).setUnitTypeString("面议");
				        }else{
							if(list.get(i).getPrice()==0){
								list.get(i).setUnitTypeString("面议");
							}
				        }				    	
				    	}
				    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
				    if("1".equals(list.get(i).getSendGoodsType())){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setUnitTypeString("面议");
							}else{
								list.get(i).setUnitTypeString("元");
							}
				    	}

				    }
				    //选择其他的时候 ,都为面议
				    if("2".equals(list.get(i).getSendGoodsType())){
							list.get(i).setUnitTypeString("面议");

				        }
					}else{
					    	list.get(i).setUnitTypeString("面议");
					    }
				    

					}else{
						list.get(i).setUnitTypeString("面议");
					}
			    
			  //  String userId=""
			    		
			     if(list.get(i).getB_memberId()!=null){
						MemberBaseinfoDTO memberBaseinfoDTO=memberToolServiceImple.getById(list.get(i).getB_memberId().toString());
						
						 if(memberBaseinfoDTO.getUserType()!=null &&memberBaseinfoDTO.getUserType()==2){
							
							  if(memberBaseinfoDTO.getCompanyContact()!=null){
								  //list.get(i).setContact(memberBaseinfoDTO.getCompanyContact().substring(0, 1)+"先生");
								  list.get(i).setContact(memberBaseinfoDTO.getCompanyContact());
							  }
							 
						 }
			     }
			     
					if(list.get(i).getCreateTime()!=null){					
						String time=DateTimeUtils.getTimeBetween(list.get(i).getCreateTime());
						list.get(i).setTimeBefore(time);
					}	
					//起始地一
					if (list.get(i).getS_provinceId() != null
							&& list.get(i).getS_provinceId() > 1) {
						list.get(i).setS_provinceIdString(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_provinceId()));
					}
					if (list.get(i).getS_cityId() != null
							&& list.get(i).getS_cityId() > 1) {
						list.get(i).setS_cityIdString(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_cityId()));
					}
					if (list.get(i).getS_areaId() != null
							&& list.get(i).getS_areaId() > 1) {
						list.get(i).setS_areaIdString(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_areaId()));
					}
					//起始地二
					if (list.get(i).getS_provinceId2() != null
							&& list.get(i).getS_provinceId2() > 1) {
						list.get(i).setS_provinceIdString2(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_provinceId2()));
					}
					if (list.get(i).getS_cityId2() != null
							&& list.get(i).getS_cityId2() > 1) {
						list.get(i).setS_cityIdString2(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_cityId2()));
					}
					if (list.get(i).getS_areaId2() != null
							&& list.get(i).getS_areaId2() > 1) {
						list.get(i).setS_areaIdString2(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_areaId2()));
					}

					if (list.get(i).getS_provinceId3() != null
							&& list.get(i).getS_provinceId3() > 1) {
						list.get(i).setS_provinceIdString3(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_provinceId3()));
					}
					if (list.get(i).getS_cityId3() != null
							&& list.get(i).getS_cityId3() > 1) {
						list.get(i).setS_cityIdString3(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_cityId3()));
					}
					if (list.get(i).getS_areaId3() != null
							&& list.get(i).getS_areaId3() > 1) {
						list.get(i).setS_areaIdString3(
								memberAddressApiService.getAreaString(list.get(i)
										.getS_areaId3()));
					}
								
								
								
					if (list.get(i).getE_provinceId() != null
							&& list.get(i).getE_provinceId() > 1) {
						list.get(i).setE_provinceIdString(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_provinceId()));
					}
					if (list.get(i).getE_cityId() != null
							&& list.get(i).getE_cityId() > 1) {
						list.get(i).setE_cityIdString(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_cityId()));
					}
					if (list.get(i).getE_areaId() != null
							&& list.get(i).getE_areaId() > 1) {
						list.get(i).setE_areaIdString(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_areaId()));
					}
					
					if (list.get(i).getE_provinceId2() != null
							&& list.get(i).getE_provinceId2() > 1) {
						list.get(i).setE_provinceIdString2(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_provinceId2()));
					}
					if (list.get(i).getE_cityId2() != null
							&& list.get(i).getE_cityId2() > 1) {
						list.get(i).setE_cityIdString2(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_cityId2()));
					}
					if (list.get(i).getE_areaId2() != null
							&& list.get(i).getE_areaId2() > 1) {
						list.get(i).setE_areaIdString2(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_areaId2()));
					}
					if (list.get(i).getE_provinceId3() != null
							&& list.get(i).getE_provinceId3() > 1) {
						list.get(i).setE_provinceIdString3(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_provinceId3()));
					}
					if (list.get(i).getE_cityId3() != null
							&& list.get(i).getE_cityId3() > 1) {
						list.get(i).setE_cityIdString3(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_cityId3()));
					}
					if (list.get(i).getE_areaId3() != null
							&& list.get(i).getE_areaId3() > 1) {
						list.get(i).setE_areaIdString3(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_areaId3()));
					}
					if (list.get(i).getE_provinceId4() != null
							&& list.get(i).getE_provinceId4() > 1) {
						list.get(i).setE_provinceIdString4(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_provinceId4()));
					}
					if (list.get(i).getE_cityId4() != null
							&& list.get(i).getE_cityId4() > 1) {
						list.get(i).setE_cityIdString4(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_cityId4()));
					}
					if (list.get(i).getE_areaId4() != null
							&& list.get(i).getE_areaId4() > 1) {
						list.get(i).setE_areaIdString4(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_areaId4()));
					}
					if (list.get(i).getE_provinceId5() != null
							&& list.get(i).getE_provinceId5() > 1) {
						list.get(i).setE_provinceIdString5(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_provinceId5()));
					}
					if (list.get(i).getE_cityId5() != null
							&& list.get(i).getE_cityId5() > 1) {
						list.get(i).setE_cityIdString5(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_cityId5()));
					}
					if (list.get(i).getE_areaId5() != null
							&& list.get(i).getE_areaId5() > 1) {
						list.get(i).setE_areaIdString5(
								memberAddressApiService.getAreaString(list.get(i)
										.getE_areaId5()));
					}
				    if(list.get(i).getSendGoodsType()!=null){
					    
					    if(list.get(i).getPrice()!=null){
					    	if("0".equals(list.get(i).getSendGoodsType())){
					        if(list.get(i).getPrice()==null){
					        	list.get(i).setUnitTypeString("面议");
					        }else{
								if(list.get(i).getPrice()==0){
									list.get(i).setUnitTypeString("面议");
								}
					        }				    	
					    	}
					    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
					    if("1".equals(list.get(i).getSendGoodsType())){
					    	if(list.get(i).getPrice()==null){
					    		list.get(i).setUnitTypeString("面议");
					    	}else{
								if(list.get(i).getPrice()==0){
									list.get(i).setUnitTypeString("面议");
								}else{
									list.get(i).setUnitTypeString("元");
								}
					    	}

					    }
					    //选择其他的时候 ,都为面议
					    if("2".equals(list.get(i).getSendGoodsType())){
								list.get(i).setUnitTypeString("面议");

					        }
						}else{
						    	list.get(i).setUnitTypeString("面议");
						    }
					    

						}else{
						}
				    
				  //  String userId=""
				    		
				     if(list.get(i).getB_memberId()!=null){
							MemberBaseinfoDTO memberBaseinfoDTO=memberToolServiceImple.getById(list.get(i).getB_memberId().toString());
							
							 if(memberBaseinfoDTO.getUserType()!=null &&memberBaseinfoDTO.getUserType()==2){
								
								  if(memberBaseinfoDTO.getCompanyContact()!=null){
									  //list.get(i).setContact(memberBaseinfoDTO.getCompanyContact().substring(0, 1)+"先生");
									  list.get(i).setContact(memberBaseinfoDTO.getCompanyContact());
								  }
								 
							 }
				     }
				     
						if(list.get(i).getCreateTime()!=null){					
							String time=DateTimeUtils.getTimeBetween(list.get(i).getCreateTime());
							list.get(i).setTimeBefore(time);
						}	
						
						if(list.get(i).getS_areaId()!=null&&list.get(i).getS_provinceId()!=null&&list.get(i).getS_cityId()!=null&&list.get(i).getS_areaId()==0&&list.get(i).getS_provinceId()==0&&list.get(i).getS_cityId()==0){
							list.get(i).setS_provinceIdString("全国");
						}
						if(list.get(i).getS_cityId()!=null&&list.get(i).getS_cityId()!=0){
							if ("市辖区".equals(list.get(i).getS_cityIdString()) || "县".equals(list.get(i).getS_cityIdString())||"市".equals(list.get(i).getS_cityIdString())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString())){
								list.get(i).setS_cityIdString("");
							}
							
						}
				     
						
						if(list.get(i).getE_areaId()!=null&&list.get(i).getE_provinceId()!=null&&list.get(i).getE_cityId()!=null&&list.get(i).getE_areaId()==0&&list.get(i).getE_provinceId()==0&&list.get(i).getE_cityId()==0){
							list.get(i).setE_provinceIdString("全国");
						}
						
						if(list.get(i).getE_cityId()!=null&&list.get(i).getE_cityId()!=0){
							if ("市辖区".equals(list.get(i).getE_cityIdString()) || "县".equals(list.get(i).getE_cityIdString())||"市".equals(list.get(i).getE_cityIdString())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString())){
								list.get(i).setE_cityIdString("");
							}
						}
						if(list.get(i).getS_areaId()!=null&&list.get(i).getS_areaId()!=0){
							if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
								list.get(i).setS_areaIdString("");
							}
						}
						if(list.get(i).getE_areaId()!=null&&list.get(i).getE_areaId()!=0){
							if ("市辖区".equals(list.get(i).getE_areaIdString()) || "县".equals(list.get(i).getE_areaIdString())||"市".equals(list.get(i).getE_areaIdString())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString())){
								list.get(i).setE_areaIdString("");
							}
						}
						
						//地址2
						if(list.get(i).getS_areaId2()!=null&&list.get(i).getS_provinceId2()!=null&&list.get(i).getS_cityId2()!=null&&list.get(i).getS_areaId2()==0&&list.get(i).getS_provinceId2()==0&&list.get(i).getS_cityId2()==0){
							list.get(i).setS_provinceIdString2("全国");
						}
						if(list.get(i).getS_cityId2()!=null&&list.get(i).getS_cityId2()!=0){
							if ("市辖区".equals(list.get(i).getS_cityIdString2()) || "县".equals(list.get(i).getS_cityIdString2())||"市".equals(list.get(i).getS_cityIdString2())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString2())){
								list.get(i).setS_cityIdString2("");
							}
						}
				    
						
						if(list.get(i).getE_areaId2()!=null&&list.get(i).getE_provinceId2()!=null&&list.get(i).getE_cityId2()!=null&&list.get(i).getE_areaId2()==0&&list.get(i).getE_provinceId2()==0&&list.get(i).getE_cityId2()==0){
							list.get(i).setE_provinceIdString2("全国");
						}
						if(list.get(i).getE_cityId2()!=null&&list.get(i).getE_cityId2()!=0){
							if ("市辖区".equals(list.get(i).getE_cityIdString2()) || "县".equals(list.get(i).getE_cityIdString2())||"市".equals(list.get(i).getE_cityIdString2())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString2())){
								list.get(i).setE_cityIdString2("");
							}
						}
						if(list.get(i).getS_areaId2()!=null&&list.get(i).getS_areaId2()!=0){
							if ("市辖区".equals(list.get(i).getS_areaIdString2()) || "县".equals(list.get(i).getS_areaIdString2())||"市".equals(list.get(i).getS_areaIdString2())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString2())){
								list.get(i).setS_areaIdString2("");
							}
						}
						if(list.get(i).getE_areaId2()!=null&&list.get(i).getE_areaId2()!=0){
							if ("市辖区".equals(list.get(i).getE_areaIdString2()) || "县".equals(list.get(i).getE_areaIdString2())||"市".equals(list.get(i).getE_areaIdString2())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString2())){
								list.get(i).setE_areaIdString2("");
							}
						}
						
						//地址3
						if(list.get(i).getS_areaId3()!=null&&list.get(i).getS_provinceId3()!=null&&list.get(i).getS_cityId3()!=null&&list.get(i).getS_areaId3()==0&&list.get(i).getS_provinceId3()==0&&list.get(i).getS_cityId3()==0){
							list.get(i).setS_provinceIdString3("全国");
						}
						
						if(list.get(i).getS_cityId3()!=null&&list.get(i).getS_cityId3()!=0){
							if ("市辖区".equals(list.get(i).getS_cityIdString3()) || "县".equals(list.get(i).getS_cityIdString3())||"市".equals(list.get(i).getS_cityIdString3())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString3())){
								list.get(i).setS_cityIdString3("");
							}
						}
						
						if(list.get(i).getE_areaId3()!=null&&list.get(i).getE_provinceId3()!=null&&list.get(i).getE_cityId3()!=null&&list.get(i).getE_areaId3()==0&&list.get(i).getE_provinceId3()==0&&list.get(i).getE_cityId3()==0){
							list.get(i).setE_provinceIdString3("全国");
						}
						if(list.get(i).getE_cityId3()!=null&&list.get(i).getE_cityId3()!=0){
							if ("市辖区".equals(list.get(i).getE_cityIdString3()) || "县".equals(list.get(i).getE_cityIdString3())||"市".equals(list.get(i).getE_cityIdString3())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString3())){
								list.get(i).setE_cityIdString3("");
							}
						}
						if(list.get(i).getS_areaId3()!=null&&list.get(i).getS_areaId3()!=0){
							if ("市辖区".equals(list.get(i).getS_areaIdString3()) || "县".equals(list.get(i).getS_areaIdString3())||"市".equals(list.get(i).getS_areaIdString3())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString3())){
								list.get(i).setS_areaIdString3("");
							}
						}
						if(list.get(i).getE_areaId3()!=null&&list.get(i).getE_areaId3()!=0){
							if ("市辖区".equals(list.get(i).getE_areaIdString3()) || "县".equals(list.get(i).getE_areaIdString3())||"市".equals(list.get(i).getE_areaIdString3())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString3())){
								list.get(i).setE_areaIdString3("");
							}
						}
						
						//地址4
						if(list.get(i).getE_areaId4()!=null&&list.get(i).getE_provinceId4()!=null&&list.get(i).getE_cityId4()!=null&&list.get(i).getE_areaId4()==0&&list.get(i).getE_provinceId4()==0&&list.get(i).getE_cityId4()==0){
							list.get(i).setE_provinceIdString4("全国");
						}
						if(list.get(i).getE_cityId4()!=null&&list.get(i).getE_cityId4()!=0){
							if ("市辖区".equals(list.get(i).getE_cityIdString4()) || "县".equals(list.get(i).getE_cityIdString4())||"市".equals(list.get(i).getE_cityIdString4())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString4())){
								list.get(i).setE_cityIdString4("");
							}
						}
						if(list.get(i).getE_areaId4()!=null&&list.get(i).getE_areaId4()!=0){
							if ("市辖区".equals(list.get(i).getE_areaIdString4()) || "县".equals(list.get(i).getE_areaIdString4())||"市".equals(list.get(i).getE_areaIdString4())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString4())){
								list.get(i).setE_areaIdString4("");
							}
						}
						//地址5
						if(list.get(i).getE_areaId5()!=null&&list.get(i).getE_provinceId5()!=null&&list.get(i).getE_cityId5()!=null&&list.get(i).getE_areaId5()==0&&list.get(i).getE_provinceId5()==0&&list.get(i).getE_cityId5()==0){
							list.get(i).setE_provinceIdString5("全国");
						}
						if(list.get(i).getE_cityId5()!=null&&list.get(i).getE_cityId5()!=0){
							if ("市辖区".equals(list.get(i).getE_cityIdString5()) || "县".equals(list.get(i).getE_cityIdString5())||"市".equals(list.get(i).getE_cityIdString5())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString5())){
								list.get(i).setE_cityIdString5("");
							}
						}
						if(list.get(i).getE_areaId5()!=null&&list.get(i).getE_areaId5()!=0){
							if ("市辖区".equals(list.get(i).getE_areaIdString5()) || "县".equals(list.get(i).getE_areaIdString5())||"市".equals(list.get(i).getE_areaIdString5())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString5())){
								list.get(i).setE_areaIdString5("");
							}
						}
						//去除重复的数据
						List<String> list1  = new ArrayList<String>();
						List<String>  list2  = new ArrayList<String>();
						String s1="";
						if(list.get(i).getS_provinceId()!=null){
							s1=list.get(i).getS_provinceId().toString();
						}
						if(list.get(i).getS_cityId()!=null){
							s1=s1+list.get(i).getS_cityId().toString();
						}
						if(list.get(i).getS_areaId()!=null){
							s1=s1+list.get(i).getS_areaId().toString();
						}
						String s2="";
						if(list.get(i).getS_provinceId2()!=null){
							s2=list.get(i).getS_provinceId2().toString();
						}
						if(list.get(i).getS_cityId2()!=null){
							s2=s2+list.get(i).getS_cityId2().toString();
						}
						if(list.get(i).getS_areaId2()!=null){
							s2=s2+list.get(i).getS_areaId2().toString();
						}
						String s3="";
						if(list.get(i).getS_provinceId3()!=null){
							s3=list.get(i).getS_provinceId3().toString();
						}
						if(list.get(i).getS_cityId3()!=null){
							s3=s3+list.get(i).getS_cityId3().toString();
						}
						if(list.get(i).getS_areaId3()!=null){
							s3=s3+list.get(i).getS_areaId3().toString();
						}
							if(!list1.contains(s1)){
								list1.add(s1);
							}
							if(!list1.contains(s2)){
								list1.add(s2);
							}else{
								s2="no";
								list.get(i).setS_provinceIdString2("");
								list.get(i).setS_areaIdString2("");
								list.get(i).setS_cityIdString2("");
							}
							if(!list1.contains(s3)){
								list1.add(s3);
							}else{
								s3="no";
								list.get(i).setS_provinceIdString3("");
								list.get(i).setS_areaIdString3("");
								list.get(i).setS_cityIdString3("");
							}
							
							String e1="";
							if(list.get(i).getE_provinceId()!=null){
								e1=list.get(i).getE_provinceId().toString();
							}
							if(list.get(i).getE_cityId()!=null){
								e1=e1+list.get(i).getE_cityId().toString();
							}
							if(list.get(i).getE_areaId()!=null){
								e1=e1+list.get(i).getE_areaId().toString();
							}
							
							String e2="";
							if(list.get(i).getE_provinceId2()!=null){
								e2=list.get(i).getE_provinceId2().toString();
							}
							if(list.get(i).getE_cityId2()!=null){
								e2=e2+list.get(i).getE_cityId2().toString();
							}
							if(list.get(i).getE_areaId2()!=null){
								e2=e2+list.get(i).getE_areaId2().toString();
							}
							
							String e3="";
							if(list.get(i).getE_provinceId3()!=null){
								e3=list.get(i).getE_provinceId3().toString();
							}
							if(list.get(i).getE_cityId3()!=null){
								e3=e3+list.get(i).getE_cityId3().toString();
							}
							if(list.get(i).getE_areaId3()!=null){
								e3=e3+list.get(i).getE_areaId3().toString();
							}
							
							String e4="";
							if(list.get(i).getE_provinceId4()!=null){
								e4=list.get(i).getE_provinceId4().toString();
							}
							if(list.get(i).getE_cityId4()!=null){
								e4=e4+list.get(i).getE_cityId4().toString();
							}
							if(list.get(i).getE_areaId4()!=null){
								e4=e4+list.get(i).getE_areaId4().toString();
							}
							
							String e5="";
							if(list.get(i).getE_provinceId5()!=null){
								e5=list.get(i).getE_provinceId5().toString();
							}
							if(list.get(i).getE_cityId5()!=null){
								e5=e5+list.get(i).getE_cityId5().toString();
							}
							if(list.get(i).getE_areaId5()!=null){
								e5=e5+list.get(i).getE_areaId5().toString();
							}

							if(!list2.contains(e1)){
								list2.add(e1);
							}
							if(!list2.contains(e2)){
								list2.add(e2);
							}else{
								e2="no";
								list.get(i).setE_provinceIdString2("");
								list.get(i).setE_areaIdString2("");
								list.get(i).setE_cityIdString2("");
							}
							if(!list2.contains(e3)){
								list2.add(e3);
							}else{
								e3="no";
								list.get(i).setE_provinceIdString3("");
								list.get(i).setE_areaIdString3("");
								list.get(i).setE_cityIdString3("");
							}
							if(!list2.contains(e4)){
								list2.add(e4);
							}else{
								e4="no";
								list.get(i).setE_provinceIdString4("");
								list.get(i).setE_areaIdString4("");
								list.get(i).setE_cityIdString4("");
							}
							if(!list2.contains(e5)){
								list2.add(e5);
							}else{
								e5="no";
								list.get(i).setE_provinceIdString5("");
								list.get(i).setE_areaIdString5("");
								list.get(i).setE_cityIdString5("");
							}	
						
						

				
			}
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("获取线路列表异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("获取线路列表异常");
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 获取用户的线路信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addCarline")
	public void addCarMessage(HttpServletRequest request,
			HttpServletResponse response,CarLineDTO carLineDTO) {
		ObjectResult result = new ObjectResult();
		try {
//			Long s_provinceId=carLineDTO.getS_provinceId();
//			Long s_cityId=carLineDTO.getS_provinceId();
//			Long s_areaId=carLineDTO.getS_provinceId();
//			carLineDTO.setS_provinceId(!StringUtils.isEmpty(s_provinceId)?Long.parseLong(s_provinceId):0);                            
//			carLineDTO.setS_cityId(!StringUtils.isEmpty(s_cityId)?Long.parseLong(s_cityId):0);                                        
//			carLineDTO.setS_areaId(!StringUtils.isEmpty(s_areaId)?Long.parseLong(s_areaId):0);                                        
//			String e_provinceId =request.getParameter("end_provinceId");                                                                  
//			String e_cityId =request.getParameter("end_cityId");                                                                          
//			String e_areaId =request.getParameter("end_areaId");                                                                          
//			carLineDTO.setE_provinceId(!StringUtils.isEmpty(e_provinceId)?Long.parseLong(e_provinceId):0);                            
//			carLineDTO.setE_cityId(!StringUtils.isEmpty(e_cityId)?Long.parseLong(e_cityId):0);                                        
//			carLineDTO.setE_areaId(!StringUtils.isEmpty(e_areaId)?Long.parseLong(e_areaId):0); 
			
			if(carLineDTO.getCarId()==0){
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("CarId is null FAIL");
				return;
			}
			if(carLineDTO.getMemberId()==0){
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("用户不存在");
				return;
			}
			DateStringConverter  c= new DateStringConverter();
			java.util.Date nowdate=new java.util.Date(); 
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		    String dateString = formatter.format(nowdate); 
		    nowdate=DateUtil.formateDate(dateString, formatter);
			if(carLineDTO.getSentDate()!=null){
				if(carLineDTO.getSentDate().getTime()<nowdate.getTime()){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("发车时间不能早于当前时间");
					renderJson(result, request, response);
					return;
				}else{
					
					if(carLineDTO.getSentDateType()!=null&&Integer.parseInt(carLineDTO.getSentDateType())!=4&&carLineDTO.getSentDate().getTime()==nowdate.getTime()){
						int sentD=Integer.parseInt(carLineDTO.getSentDateType());
						Date d = new Date();
						int hour = d.getHours();
						if(sentD==0){
							if(hour>12){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("发车时间不能早于当前时间,上午时间段是6:00~12:00");
								renderJson(result, request, response);
								return;
							}

						}
						if(sentD==1){
							if(hour>14){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("发车时间不能早于当前时间,中午时间段是12:00~14:00");
								renderJson(result, request, response);
								return;
							}
						}
						if(sentD==2){
							if(hour>18){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("发车时间不能早于当前时间,下午时间段是14:00~18:00");
								renderJson(result, request, response);
								return;
							}
						}
					}
				}

				carLineDTO.setSendDateString(c.convert(carLineDTO.getSentDate()));
			}
			if(carLineDTO.getEndDate()!=null){
				carLineDTO.setEndDateString(c.convert(carLineDTO.getEndDate()));
			}
			if(carLineDTO.getEndDate()!=null&&carLineDTO.getSentDate()!=null){
				//boolean flag = carLineDTO.getEndDate().before(carLineDTO.getSentDate());
				if(carLineDTO.getEndDate().getTime()<carLineDTO.getSentDate().getTime()){
			//	if(flag){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("截止时间不能早于发车时间");
					renderJson(result, request, response);
					return;
				}else{
					if(carLineDTO.getEndDateType()!=null&&carLineDTO.getSentDateType()!=null&&carLineDTO.getEndDate().getTime()==carLineDTO.getSentDate().getTime()){
						int endT=Integer.parseInt(carLineDTO.getEndDateType());
						int sentT=Integer.parseInt(carLineDTO.getSentDateType());
						if(endT!=4&&sentT!=4){
							if(endT<=sentT){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("截止时间和发车时间同一天时,请注意选择上午,下午的时间");
								renderJson(result, request, response);
								return;
							}
						}
					}
				}
			}
			String startCity= "";
			
		    if(!StringUtils.isBlank(carLineDTO.getStartCity())){
		    	if(!StringUtils.isBlank(carLineDTO.getApp())){
			    	if("IOS".equals(carLineDTO.getApp())){
			    		startCity= new String(carLineDTO.getStartCity().getBytes("utf-8"),"utf-8");
			    	}
			    	}else{
			    		//startCity= new String(carLineDTO.getStartCity().getBytes("ISO-8859-1"),"utf-8");
			    		startCity=carLineDTO.getStartCity();
			    	}
		    	//startCity= new String(carLineDTO.getStartCity().getBytes("ISO-8859-1"),"utf-8");
		    	carLineDTO.setStartCity(startCity);
		    }
			String endCity= "";
			
		    if(!StringUtils.isBlank(carLineDTO.getEndCity())){
		    	if(!StringUtils.isBlank(carLineDTO.getApp())){
			    	if("IOS".equals(carLineDTO.getApp())){
			    		endCity= new String(carLineDTO.getEndCity().getBytes("utf-8"),"utf-8");
			    	}
			    	}else{
			    		//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
			    		endCity=carLineDTO.getEndCity();
			    	}
		    	//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
		    	carLineDTO.setEndCity(endCity);
		    } 
		    
			String remark= "";
			
		    if(!StringUtils.isBlank(carLineDTO.getRemark())){
		    	if(!StringUtils.isBlank(carLineDTO.getApp())){
			    	if("IOS".equals(carLineDTO.getApp())){
			    		remark= new String(carLineDTO.getRemark().getBytes("utf-8"),"utf-8");
			    	}
			    	}else{
			    		//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
			    		remark=carLineDTO.getRemark();
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
		        	carLineDTO.setRemark(remark);
		        }
		    } 
		    
		 //  carLineDTO.setSendDateString(c.convert(carLineDTO.getSentDate()));
		 //  carLineDTO.setEndDateString(c.convert(carLineDTO.getEndDate()));
		   // carLineDTO.setEndDateString(carLineDTO.getEndDate());
		    
		    carLineDTO.setSource("1");
		    
			//传默认的GPS定位城市  add by yanghaoyu ,2016/01/28 线路增加默认城市
			
			if(carLineDTO.getS_areaId()!=null&&carLineDTO.getS_cityId()!=null&&carLineDTO.getS_provinceId()!=null    && carLineDTO.getS_areaId()==1&&carLineDTO.getS_cityId()==1&&carLineDTO.getS_provinceId()==1&&carLineDTO.getmCity()!=null&&!"".equals(carLineDTO.getmCity())){
			
				AreaDTO ad=memberAddressApiService.getArea(carLineDTO.getmCity());
				if(ad!=null){
					if(ad.getAreaID()!=null){
						carLineDTO.setS_cityId(Long.valueOf(ad.getAreaID()));
						//可以直接获取上级目录Id不需要再查数据库
						//AreaDTO adf=memberAddressApiService.getArea(ad.getFather());
						//if(adf!=null){
							if(ad.getFather()!=null&&!"".equals(ad.getFather())){
								carLineDTO.setS_provinceId(Long.valueOf(ad.getFather()));
								carLineDTO.setS_areaId(Long.valueOf(0));
							}
						//}
					}
				}
				
				
				
				
			}
			int i = carLineApiService.addCarline(carLineDTO);

			result.setObject(null);
            if(i>0){
            	//如果操作成功了的
            	//获取当前记录的Id
            	Long id= carLineApiService.getCarLineId(carLineDTO.getMemberId());
    			//获取手机GPS定位的时候的经纬度
    			AreaDTO a=new AreaDTO();
				if(carLineDTO.getmCity()!=null&&!"".equals(carLineDTO.getmCity())){
					 a=memberAddressApiService.getArea(carLineDTO.getmCity());
					 carLineDTO.setMlng(a.getLng());
					 carLineDTO.setMlat(a.getLat());
					 carLineDTO.setApp(a.getAreaID());
				}else{
					carLineDTO.setMlng(Double.valueOf(0));
					carLineDTO.setMlat(Double.valueOf(0));
					 carLineDTO.setM_cityId(null);
				}
    			AreaDTO bj=memberAddressApiService.getArea("北京市");
    			AreaDTO sh=memberAddressApiService.getArea("上海市");
    			AreaDTO cq=memberAddressApiService.getArea("重庆市");
    			AreaDTO tj=memberAddressApiService.getArea("天津市");
 
    			
    			carLineDTO.setBjlng(bj.getLng());
    			carLineDTO.setBjlat(bj.getLat());
    			
    			
    			carLineDTO.setShlng(sh.getLng());
    			carLineDTO.setShlat(sh.getLat()); 
    			
    			
    			carLineDTO.setTjlng(tj.getLng());
    			carLineDTO.setTjlat(tj.getLat());
    			
    			
    			carLineDTO.setCqlng(cq.getLng());
    			carLineDTO.setCqlat(cq.getLat());
    			carLineDTO.setDistance(Double.valueOf(100));
    			carLineDTO.setId(id);
//            	List<MemberAddressDTO> list = memberAddressApiService.getMebApiMessage(carLineDTO);
//            	if(list!=null && list.size()>0){
//            		
//            		carLineApiService.setMebApiMessage(carLineDTO,list);
//            		MemberBaseinfoDTO memberDTO = memberToolService.getById(carLineDTO.getMemberId()+"");
//					UMengPushMessage pushMessage2 = new UMengPushMessage();
//					GdMessageDTO gdMessage2 = new GdMessageDTO();
//					gdMessage2.setSendApp("2");
//					gdMessage2.setSendType("1");
//					gdMessage2.setTicket("【农速通为您推送线路信息】");
//					gdMessage2.setTitle("农速通为您推送线路信息");
//					gdMessage2.setContent("根据你发布的货源信息,我们为你推荐了路线信息,请查收");
//					gdMessage2.setAfter_open("go_app");
//					//gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
//					//ios:9c6031b0c53f823498214ed8e9ba4ed65b2127633f6611836c2e93abdf0d2e8b
//					//android:AsfEx_AIdKOZuTfL55scurKF6PRdP3Klx0khdM3MmKM2
//					gdMessage2.setDevice_tokens(memberDTO.getDevice_tokens());
//					gdMessage2.setProduction_mode(true);
//					pushMessage2.pushMessage(gdMessage2);
//            	}
            	CarLineMessgaeThread myThread = new CarLineMessgaeThread(carLineDTO,memberAddressApiService,carLineApiService,memberToolService,carManagerApiService);  
            	Thread thread = new Thread(myThread); 
            	thread.start();  
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			}else{
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("FAIL");
			}
		}catch (Exception e) {
			logger.info("创建线路异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("创建线路异常" );
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 获取用户的线路信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delCarline")
	public void delCarMessage(HttpServletRequest request,
			HttpServletResponse response,CarLineDTO carLineDTO) {
		ObjectResult result = new ObjectResult();
		try {
			int i=0;
			if (carLineDTO.getId()!=null) {
				//对路线进行软删除
				i = carLineApiService.updateCarLineByid(carLineDTO.getId().toString());
			}
			result.setObject(null);
            if(i>0){
    			
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			}else{
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("FAIL");
			}
		}catch (Exception e) {
			logger.info("删除线路异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("删除线路异常");
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 获取用户的线路信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/repalyCarLine")
	public void repalyCarLine(HttpServletRequest request,
			HttpServletResponse response,CarLineDTO carLineDTO) {
		ObjectResult result = new ObjectResult();
		try {
			int i = carLineApiService.repalyCarLine(carLineDTO);
    
			result.setObject(null);
            if(i>0){
    			
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			}else{
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("FAIL");
			}
		}catch (Exception e) {
			logger.info("重新发布线路失败", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("重新发布线路失败");
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 获取用户的线路信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateCarLine")
	public void updateCarLine(HttpServletRequest request,
			HttpServletResponse response,CarLineDTO carLineDTO) {
		ObjectResult result = new ObjectResult();
		try {
			DateStringConverter  c= new DateStringConverter();
			java.util.Date nowdate=new java.util.Date(); 
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		    String dateString = formatter.format(nowdate); 
		    nowdate=DateUtil.formateDate(dateString, formatter);
			if(carLineDTO.getSentDate()!=null){
				if(carLineDTO.getSentDate().getTime()<nowdate.getTime()){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("发货时间不能早于当前时间");
					renderJson(result, request, response);
					return;
				}else{
					
					if(carLineDTO.getSentDateType()!=null&&Integer.parseInt(carLineDTO.getSentDateType())!=4&&carLineDTO.getSentDate().getTime()==nowdate.getTime()){
						int sentD=Integer.parseInt(carLineDTO.getSentDateType());
						Date d = new Date();
						int hour = d.getHours();
						if(sentD==0){
							if(hour>12){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("发车时间不能早于当前时间,上午时间段是6:00~12:00");
								renderJson(result, request, response);
								return;
							}

						}
						if(sentD==1){
							if(hour>14){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("发车时间不能早于当前时间,中午时间段是12:00~14:00");
								renderJson(result, request, response);
								return;
							}
						}
						if(sentD==2){
							if(hour>18){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("发车时间不能早于当前时间,下午时间段是14:00~18:00");
								renderJson(result, request, response);
								return;
							}
						}
					}
				}
				carLineDTO.setSendDateString(c.convert(carLineDTO.getSentDate()));
			}
			if(carLineDTO.getEndDate()!=null){
				carLineDTO.setEndDateString(c.convert(carLineDTO.getEndDate()));
			}
			if(carLineDTO.getEndDate()!=null&&carLineDTO.getSentDate()!=null){
				//boolean flag = carLineDTO.getEndDate().before(carLineDTO.getSentDate());
				if(carLineDTO.getEndDate().getTime()<carLineDTO.getSentDate().getTime()){
				//if(flag){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("截止时间不能早于发车时间");
					renderJson(result, request, response);
					return;
				}else{
					if(carLineDTO.getEndDateType()!=null&&carLineDTO.getSentDateType()!=null&&carLineDTO.getEndDate().getTime()==carLineDTO.getSentDate().getTime()){
						int endT=Integer.parseInt(carLineDTO.getEndDateType());
						int sentT=Integer.parseInt(carLineDTO.getSentDateType());
						if(endT!=4&&sentT!=4){
							if(endT<=sentT){
								result.setObject(null);
								result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
								result.setMsg("截止时间和发车时间同一天时,请注意选择上午,下午的时间");
								renderJson(result, request, response);
								return;
							}
						}
					}
				}
			}
			
			
			String startCity= "";
			
		    if(!StringUtils.isBlank(carLineDTO.getStartCity())){
		    	if(!StringUtils.isBlank(carLineDTO.getApp())){
			    	if("IOS".equals(carLineDTO.getApp())){
			    		startCity= new String(carLineDTO.getStartCity().getBytes("utf-8"),"utf-8");
			    	}
			    	}else{
			    		//startCity= new String(carLineDTO.getStartCity().getBytes("ISO-8859-1"),"utf-8");
			    		startCity= carLineDTO.getStartCity();
			    	}
		    	//startCity= new String(carLineDTO.getStartCity().getBytes("ISO-8859-1"),"utf-8");
		    	carLineDTO.setStartCity(startCity);
		    }
			String endCity= "";
			
		    if(!StringUtils.isBlank(carLineDTO.getEndCity())){
		    	if(!StringUtils.isBlank(carLineDTO.getApp())){
			    	if("IOS".equals(carLineDTO.getApp())){
			    		endCity= new String(carLineDTO.getEndCity().getBytes("utf-8"),"utf-8");
			    	}
			    	}else{
			    		//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
			    		endCity= carLineDTO.getEndCity();
			    	}
		    	//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
		    	carLineDTO.setEndCity(endCity);
		    } 
		    
	String remark= "";
			
		    if(!StringUtils.isBlank(carLineDTO.getRemark())){
		    	if(!StringUtils.isBlank(carLineDTO.getApp())){
			    	if("IOS".equals(carLineDTO.getApp())){
			    		remark= new String(carLineDTO.getRemark().getBytes("utf-8"),"utf-8");
			    	}
			    	}else{
			    		//endCity= new String(carLineDTO.getEndCity().getBytes("ISO-8859-1"),"utf-8");
			    		remark=carLineDTO.getRemark();
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
		        	carLineDTO.setRemark(remark);
		        }
		    }
			if (carLineDTO.getPrice() == null
					|| carLineDTO.getPrice() == 0) {
				carLineDTO.setPrice(0.00);
			} else {
				if (carLineDTO.getPrice().doubleValue() > 10000000) {
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("请输入一个小于10000000的价格");
					renderJson(result, request, response);
					return;
				}
				Pattern p = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
				Pattern p2 = Pattern.compile("^[1-9]\\d*$ ");
				Pattern p3 = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
				String price = carLineDTO.getPrice().toString();
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
			int i= carLineApiService.updateCarLine(carLineDTO);
			result.setObject(null);
            if(i>0){
			
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			}else{
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("FAIL");
			}
            
		}catch (Exception e) {
			logger.info("跟新线路异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("跟新线路异常");
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 获取用户的线路信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCarlineApiByConditionPage")
	public void getCarLineMessageNew(HttpServletRequest request,
			HttpServletResponse response,CarLineDTO carLineDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String s_provinceId=carLineDTO.getS_provinceIdS();
			String s_cityId=carLineDTO.getS_cityIdS();
			String s_areaId=carLineDTO.getS_areaIdS();
			//carLineDTO.getS
			if(carLineDTO.getS_provinceId()==0){
				carLineDTO.setS_provinceId(!StringUtils.isEmpty(s_provinceId)?Long.parseLong(s_provinceId):0); 	
			}
			if(carLineDTO.getS_cityId()==0)  {
				carLineDTO.setS_cityId(!StringUtils.isEmpty(s_cityId)?Long.parseLong(s_cityId):0); 
			}
			if(carLineDTO.getS_areaId()==0){
				carLineDTO.setS_areaId(!StringUtils.isEmpty(s_areaId)?Long.parseLong(s_areaId):0);
			}
			                                        
			String e_provinceId=carLineDTO.getE_provinceIdS();
			String e_cityId=carLineDTO.getE_cityIdS();
			String e_areaId=carLineDTO.getE_areaIdS();
			if(carLineDTO.getE_provinceId()==0){
				carLineDTO.setE_provinceId(!StringUtils.isEmpty(e_provinceId)?Long.parseLong(e_provinceId):0);
			}
			if(carLineDTO.getE_cityId()==0){
				carLineDTO.setE_cityId(!StringUtils.isEmpty(e_cityId)?Long.parseLong(e_cityId):0); 	
			}
			if(carLineDTO.getE_areaId()==0){
				carLineDTO.setE_areaId(!StringUtils.isEmpty(e_areaId)?Long.parseLong(e_areaId):0); 
			}
			Map<String, Object> map = new HashMap<String, Object>();
			CommonPageDTO pageDTO = getPageInfo(request, map);
			//获取手机GPS定位的时候的经纬度
			AreaDTO a=new AreaDTO();
			if(carLineDTO.getmCity()!=null&&!"".equals(carLineDTO.getmCity())){
				 a=memberAddressApiService.getArea(carLineDTO.getmCity());
				 carLineDTO.setMlng(a.getLng());
				 carLineDTO.setMlat(a.getLat());
			}else{
				carLineDTO.setMlng(Double.valueOf(0));
				carLineDTO.setMlat(Double.valueOf(0));
			}
			AreaDTO bj=memberAddressApiService.getArea("北京市");
			AreaDTO sh=memberAddressApiService.getArea("上海市");
			AreaDTO cq=memberAddressApiService.getArea("重庆市");
			AreaDTO tj=memberAddressApiService.getArea("天津市");
			carLineDTO.setMlng(a.getLng());
			carLineDTO.setMlat(a.getLat());
			
			carLineDTO.setBjlng(bj.getLng());
			carLineDTO.setBjlat(bj.getLat());
			
			
			carLineDTO.setShlng(sh.getLng());
			carLineDTO.setShlat(sh.getLat()); 
			
			
			carLineDTO.setTjlng(tj.getLng());
			carLineDTO.setTjlat(tj.getLat());
			
			
			carLineDTO.setCqlng(cq.getLng());
			carLineDTO.setCqlat(cq.getLat());
			
			if(carLineDTO.getId()==null||carLineDTO.getId()==0){
				if(carLineDTO.getCl_Id()!=null&&!"".equals(carLineDTO.getCl_Id())){
					carLineDTO.setId(Long.valueOf(carLineDTO.getCl_Id()));
				}
				if(carLineDTO.getCl_id()!=null&&!"".equals(carLineDTO.getCl_id())){
					carLineDTO.setId(Long.valueOf(carLineDTO.getCl_id()));
				}
			}
			int total =  carLineApiService.getCountByCondition(carLineDTO);
			carLineDTO.setStartRow(Integer.parseInt(map.get(START_ROW).toString()));
			carLineDTO.setEndRow(Integer.parseInt(map.get(END_ROW).toString()));

			List<CarLineDTO> list = carLineApiService.getCarlineApiByConditionNew(carLineDTO);
			//循环遍历集合,如果是零担 ,价格数为0 则是没有输入价格 ,选择的是面议
			for (int i = 0; i < list.size(); i++) {
				//获取发布者的认证状态
				if(list.get(i).getB_memberId()!=null){
					list.get(i).setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(list.get(i).getB_memberId())));
				}
				
//			    if("0".equals(list.get(i).getSendGoodsType())){
//					if(list.get(i).getPrice()==0){
//						list.get(i).setUnitTypeString("面议");
//					}
//			    }
//			    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
//			    if("1".equals(list.get(i).getSendGoodsType())){
//					if(list.get(i).getPrice()==0){
//						list.get(i).setUnitTypeString("面议");
//					}else{
//						list.get(i).setUnitTypeString("元");
//					}
//			    }
//			    //选择其他的时候 ,都为面议
//			    if("2".equals(list.get(i).getSendGoodsType())){
//						list.get(i).setUnitTypeString("面议");
//
//			    }
				//起始地一
				if (list.get(i).getS_provinceId() != null
						&& list.get(i).getS_provinceId() > 1) {
					list.get(i).setS_provinceIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_provinceId()));
				}
				if (list.get(i).getS_cityId() != null
						&& list.get(i).getS_cityId() > 1) {
					list.get(i).setS_cityIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_cityId()));
				}
				if (list.get(i).getS_areaId() != null
						&& list.get(i).getS_areaId() > 1) {
					list.get(i).setS_areaIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_areaId()));
				}
				//起始地二
				if (list.get(i).getS_provinceId2() != null
						&& list.get(i).getS_provinceId2() > 1) {
					list.get(i).setS_provinceIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_provinceId2()));
				}
				if (list.get(i).getS_cityId2() != null
						&& list.get(i).getS_cityId2() > 1) {
					list.get(i).setS_cityIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_cityId2()));
				}
				if (list.get(i).getS_areaId2() != null
						&& list.get(i).getS_areaId2() > 1) {
					list.get(i).setS_areaIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_areaId2()));
				}

				if (list.get(i).getS_provinceId3() != null
						&& list.get(i).getS_provinceId3() > 1) {
					list.get(i).setS_provinceIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_provinceId3()));
				}
				if (list.get(i).getS_cityId3() != null
						&& list.get(i).getS_cityId3() > 1) {
					list.get(i).setS_cityIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_cityId3()));
				}
				if (list.get(i).getS_areaId3() != null
						&& list.get(i).getS_areaId3() > 1) {
					list.get(i).setS_areaIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_areaId3()));
				}
							
							
							
				if (list.get(i).getE_provinceId() != null
						&& list.get(i).getE_provinceId() > 1) {
					list.get(i).setE_provinceIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId()));
				}
				if (list.get(i).getE_cityId() != null
						&& list.get(i).getE_cityId() > 1) {
					list.get(i).setE_cityIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId()));
				}
				if (list.get(i).getE_areaId() != null
						&& list.get(i).getE_areaId() > 1) {
					list.get(i).setE_areaIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId()));
				}
				
				if (list.get(i).getE_provinceId2() != null
						&& list.get(i).getE_provinceId2() > 1) {
					list.get(i).setE_provinceIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId2()));
				}
				if (list.get(i).getE_cityId2() != null
						&& list.get(i).getE_cityId2() > 1) {
					list.get(i).setE_cityIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId2()));
				}
				if (list.get(i).getE_areaId2() != null
						&& list.get(i).getE_areaId2() > 1) {
					list.get(i).setE_areaIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId2()));
				}
				if (list.get(i).getE_provinceId3() != null
						&& list.get(i).getE_provinceId3() > 1) {
					list.get(i).setE_provinceIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId3()));
				}
				if (list.get(i).getE_cityId3() != null
						&& list.get(i).getE_cityId3() > 1) {
					list.get(i).setE_cityIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId3()));
				}
				if (list.get(i).getE_areaId3() != null
						&& list.get(i).getE_areaId3() > 1) {
					list.get(i).setE_areaIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId3()));
				}
				if (list.get(i).getE_provinceId4() != null
						&& list.get(i).getE_provinceId4() > 1) {
					list.get(i).setE_provinceIdString4(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId4()));
				}
				if (list.get(i).getE_cityId4() != null
						&& list.get(i).getE_cityId4() > 1) {
					list.get(i).setE_cityIdString4(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId4()));
				}
				if (list.get(i).getE_areaId4() != null
						&& list.get(i).getE_areaId4() > 1) {
					list.get(i).setE_areaIdString4(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId4()));
				}
				if (list.get(i).getE_provinceId5() != null
						&& list.get(i).getE_provinceId5() > 1) {
					list.get(i).setE_provinceIdString5(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId5()));
				}
				if (list.get(i).getE_cityId5() != null
						&& list.get(i).getE_cityId5() > 1) {
					list.get(i).setE_cityIdString5(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId5()));
				}
				if (list.get(i).getE_areaId5() != null
						&& list.get(i).getE_areaId5() > 1) {
					list.get(i).setE_areaIdString5(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId5()));
				}
			    if(list.get(i).getSendGoodsType()!=null){
				    
				    if(list.get(i).getPrice()!=null){
				    	if("0".equals(list.get(i).getSendGoodsType())){
				        if(list.get(i).getPrice()==null){
				        	list.get(i).setUnitTypeString("面议");
				        }else{
							if(list.get(i).getPrice()==0){
								list.get(i).setUnitTypeString("面议");
							}
				        }				    	
				    	}
				    //如果是整车,没有输入价格,则是面议, 否则的话, 单位变成元 组合显示
				    if("1".equals(list.get(i).getSendGoodsType())){
				    	if(list.get(i).getPrice()==null){
				    		list.get(i).setUnitTypeString("面议");
				    	}else{
							if(list.get(i).getPrice()==0){
								list.get(i).setUnitTypeString("面议");
							}else{
								list.get(i).setUnitTypeString("元");
							}
				    	}

				    }
				    //选择其他的时候 ,都为面议
				    if("2".equals(list.get(i).getSendGoodsType())){
							list.get(i).setUnitTypeString("面议");

				        }
					}else{
					    	list.get(i).setUnitTypeString("面议");
					    }
				    

					}else{
						list.get(i).setUnitTypeString("面议");
					}
			    
			  //  String userId=""
			    		
			     if(list.get(i).getB_memberId()!=null){
						MemberBaseinfoDTO memberBaseinfoDTO=memberToolServiceImple.getById(list.get(i).getB_memberId().toString());
						
						 if(memberBaseinfoDTO.getUserType()!=null &&memberBaseinfoDTO.getUserType()==2){
							
							  if(memberBaseinfoDTO.getCompanyContact()!=null){
								  //list.get(i).setContact(memberBaseinfoDTO.getCompanyContact().substring(0, 1)+"先生");
								  list.get(i).setContact(memberBaseinfoDTO.getCompanyContact());
							  }
							 
						 }
			     }
			     
					if(list.get(i).getCreateTime()!=null){					
						String time=DateTimeUtils.getTimeBetween(list.get(i).getCreateTime());
						list.get(i).setTimeBefore(time);
					}	
					
					if(list.get(i).getS_areaId()!=null&&list.get(i).getS_provinceId()!=null&&list.get(i).getS_cityId()!=null&&list.get(i).getS_areaId()==0&&list.get(i).getS_provinceId()==0&&list.get(i).getS_cityId()==0){
						list.get(i).setS_provinceIdString("全国");
					}
					if(list.get(i).getS_cityId()!=null&&list.get(i).getS_cityId()!=0){
						if ("市辖区".equals(list.get(i).getS_cityIdString()) || "县".equals(list.get(i).getS_cityIdString())||"市".equals(list.get(i).getS_cityIdString())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString())){
							list.get(i).setS_cityIdString("");
						}else{
							list.get(i).setS_provinceIdString("");
						}
						
					}
					if(list.get(i).getS_areaId()!=null&&list.get(i).getS_areaId()!=0){
						if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
							list.get(i).setS_areaIdString("");
						}
					}
					if(list.get(i).getE_areaId()!=null&&list.get(i).getE_areaId()!=0){
						if ("市辖区".equals(list.get(i).getE_areaIdString()) || "县".equals(list.get(i).getE_areaIdString())||"市".equals(list.get(i).getE_areaIdString())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString())){
							list.get(i).setE_areaIdString("");
						}
					}
					
					if(list.get(i).getE_areaId()!=null&&list.get(i).getE_provinceId()!=null&&list.get(i).getE_cityId()!=null&&list.get(i).getE_areaId()==0&&list.get(i).getE_provinceId()==0&&list.get(i).getE_cityId()==0){
						list.get(i).setE_provinceIdString("全国");
					}
					
					if(list.get(i).getE_cityId()!=null&&list.get(i).getE_cityId()!=0){
						if ("市辖区".equals(list.get(i).getE_cityIdString()) || "县".equals(list.get(i).getE_cityIdString())||"市".equals(list.get(i).getE_cityIdString())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString())){
							list.get(i).setE_cityIdString("");
						}else{
							list.get(i).setE_provinceIdString("");
						}
					}
					//地址2
					if(list.get(i).getS_areaId2()!=null&&list.get(i).getS_provinceId2()!=null&&list.get(i).getS_cityId2()!=null&&list.get(i).getS_areaId2()==0&&list.get(i).getS_provinceId2()==0&&list.get(i).getS_cityId2()==0){
						list.get(i).setS_provinceIdString2("全国");
					}
					if(list.get(i).getS_cityId2()!=null&&list.get(i).getS_cityId2()!=0){
						if ("市辖区".equals(list.get(i).getS_cityIdString2()) || "县".equals(list.get(i).getS_cityIdString2())||"市".equals(list.get(i).getS_cityIdString2())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString2())){
							list.get(i).setS_cityIdString2("");
						}else{
							list.get(i).setS_provinceIdString2("");
						}
					}
					if(list.get(i).getS_areaId2()!=null&&list.get(i).getS_areaId2()!=0){
						if ("市辖区".equals(list.get(i).getS_areaIdString2()) || "县".equals(list.get(i).getS_areaIdString2())||"市".equals(list.get(i).getS_areaIdString2())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString2())){
							list.get(i).setS_areaIdString2("");
						}
					}
					
					if(list.get(i).getE_areaId2()!=null&&list.get(i).getE_provinceId2()!=null&&list.get(i).getE_cityId2()!=null&&list.get(i).getE_areaId2()==0&&list.get(i).getE_provinceId2()==0&&list.get(i).getE_cityId2()==0){
						list.get(i).setE_provinceIdString2("全国");
					}
					if(list.get(i).getE_cityId2()!=null&&list.get(i).getE_cityId2()!=0){
						if ("市辖区".equals(list.get(i).getE_cityIdString2()) || "县".equals(list.get(i).getE_cityIdString2())||"市".equals(list.get(i).getE_cityIdString2())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString2())){
							list.get(i).setE_cityIdString2("");
						}else{
							list.get(i).setE_provinceIdString2("");
						}
					}
					if(list.get(i).getE_areaId2()!=null&&list.get(i).getE_areaId2()!=0){
						if ("市辖区".equals(list.get(i).getE_areaIdString2()) || "县".equals(list.get(i).getE_areaIdString2())||"市".equals(list.get(i).getE_areaIdString2())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString2())){
							list.get(i).setE_areaIdString2("");
						}
					}
					//地址3
					if(list.get(i).getS_areaId3()!=null&&list.get(i).getS_provinceId3()!=null&&list.get(i).getS_cityId3()!=null&&list.get(i).getS_areaId3()==0&&list.get(i).getS_provinceId3()==0&&list.get(i).getS_cityId3()==0){
						list.get(i).setS_provinceIdString3("全国");
					}
					
					if(list.get(i).getS_cityId3()!=null&&list.get(i).getS_cityId3()!=0){
						if ("市辖区".equals(list.get(i).getS_cityIdString3()) || "县".equals(list.get(i).getS_cityIdString3())||"市".equals(list.get(i).getS_cityIdString3())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString3())){
							list.get(i).setS_cityIdString3("");
						}else{
							list.get(i).setS_provinceIdString3("");
						}
					}
					if(list.get(i).getS_areaId3()!=null&&list.get(i).getS_areaId3()!=0){
						if ("市辖区".equals(list.get(i).getS_areaIdString3()) || "县".equals(list.get(i).getS_areaIdString3())||"市".equals(list.get(i).getS_areaIdString3())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString3())){
							list.get(i).setS_areaIdString3("");
						}
					}
					if(list.get(i).getE_areaId3()!=null&&list.get(i).getE_provinceId3()!=null&&list.get(i).getE_cityId3()!=null&&list.get(i).getE_areaId3()==0&&list.get(i).getE_provinceId3()==0&&list.get(i).getE_cityId3()==0){
						list.get(i).setE_provinceIdString3("全国");
					}
					if(list.get(i).getE_cityId3()!=null&&list.get(i).getE_cityId3()!=0){
						if ("市辖区".equals(list.get(i).getE_cityIdString3()) || "县".equals(list.get(i).getE_cityIdString3())||"市".equals(list.get(i).getE_cityIdString3())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString3())){
							list.get(i).setE_cityIdString3("");
						}else{
							list.get(i).setE_provinceIdString3("");
						}
					}
					if(list.get(i).getE_areaId3()!=null&&list.get(i).getE_areaId3()!=0){
						if ("市辖区".equals(list.get(i).getE_areaIdString3()) || "县".equals(list.get(i).getE_areaIdString3())||"市".equals(list.get(i).getE_areaIdString3())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString3())){
							list.get(i).setE_areaIdString3("");
						}
					}
					//地址4
					if(list.get(i).getE_areaId4()!=null&&list.get(i).getE_provinceId4()!=null&&list.get(i).getE_cityId4()!=null&&list.get(i).getE_areaId4()==0&&list.get(i).getE_provinceId4()==0&&list.get(i).getE_cityId4()==0){
						list.get(i).setE_provinceIdString4("全国");
					}
					if(list.get(i).getE_cityId4()!=null&&list.get(i).getE_cityId4()!=0){
						if ("市辖区".equals(list.get(i).getE_cityIdString4()) || "县".equals(list.get(i).getE_cityIdString4())||"市".equals(list.get(i).getE_cityIdString4())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString4())){
							list.get(i).setE_cityIdString4("");
						}else{
							list.get(i).setE_provinceIdString4("");
						}
					}
					if(list.get(i).getE_areaId4()!=null&&list.get(i).getE_areaId4()!=0){
						if ("市辖区".equals(list.get(i).getE_areaIdString4()) || "县".equals(list.get(i).getE_areaIdString4())||"市".equals(list.get(i).getE_areaIdString4())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString4())){
							list.get(i).setE_areaIdString4("");
						}
					}
					//地址5
					if(list.get(i).getE_areaId5()!=null&&list.get(i).getE_provinceId5()!=null&&list.get(i).getE_cityId5()!=null&&list.get(i).getE_areaId5()==0&&list.get(i).getE_provinceId5()==0&&list.get(i).getE_cityId5()==0){
						list.get(i).setE_provinceIdString5("全国");
					}
					if(list.get(i).getE_cityId5()!=null&&list.get(i).getE_cityId5()!=0){
						if ("市辖区".equals(list.get(i).getE_cityIdString5()) || "县".equals(list.get(i).getE_cityIdString5())||"市".equals(list.get(i).getE_cityIdString5())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString5())){
							list.get(i).setE_cityIdString5("");
						}else{
							list.get(i).setE_provinceIdString5("");
						}
					}
					if(list.get(i).getE_areaId5()!=null&&list.get(i).getE_areaId5()!=0){
						if ("市辖区".equals(list.get(i).getE_areaIdString5()) || "县".equals(list.get(i).getE_areaIdString5())||"市".equals(list.get(i).getE_areaIdString5())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString5())){
							list.get(i).setE_areaIdString5("");
						}
					}
					//去除重复的数据
					List<String> list1  = new ArrayList<String>();
					List<String>  list2  = new ArrayList<String>();
					String s1="";
					if(list.get(i).getS_provinceId()!=null){
						s1=list.get(i).getS_provinceId().toString();
					}
					if(list.get(i).getS_cityId()!=null){
						s1=s1+list.get(i).getS_cityId().toString();
					}
					if(list.get(i).getS_areaId()!=null){
						s1=s1+list.get(i).getS_areaId().toString();
					}
					String s2="";
					if(list.get(i).getS_provinceId2()!=null){
						s2=list.get(i).getS_provinceId2().toString();
					}
					if(list.get(i).getS_cityId2()!=null){
						s2=s2+list.get(i).getS_cityId2().toString();
					}
					if(list.get(i).getS_areaId2()!=null){
						s2=s2+list.get(i).getS_areaId2().toString();
					}
					String s3="";
					if(list.get(i).getS_provinceId3()!=null){
						s3=list.get(i).getS_provinceId3().toString();
					}
					if(list.get(i).getS_cityId3()!=null){
						s3=s3+list.get(i).getS_cityId3().toString();
					}
					if(list.get(i).getS_areaId3()!=null){
						s3=s3+list.get(i).getS_areaId3().toString();
					}
						if(!list1.contains(s1)){
							list1.add(s1);
						}
						if(!list1.contains(s2)){
							list1.add(s2);
						}else{
							s2="no";
							list.get(i).setS_provinceIdString2("");
							list.get(i).setS_areaIdString2("");
							list.get(i).setS_cityIdString2("");
						}
						if(!list1.contains(s3)){
							list1.add(s3);
						}else{
							s3="no";
							list.get(i).setS_provinceIdString3("");
							list.get(i).setS_areaIdString3("");
							list.get(i).setS_cityIdString3("");
						}
						
						String e1="";
						if(list.get(i).getE_provinceId()!=null){
							e1=list.get(i).getE_provinceId().toString();
						}
						if(list.get(i).getE_cityId()!=null){
							e1=e1+list.get(i).getE_cityId().toString();
						}
						if(list.get(i).getE_areaId()!=null){
							e1=e1+list.get(i).getE_areaId().toString();
						}
						
						String e2="";
						if(list.get(i).getE_provinceId2()!=null){
							e2=list.get(i).getE_provinceId2().toString();
						}
						if(list.get(i).getE_cityId2()!=null){
							e2=e2+list.get(i).getE_cityId2().toString();
						}
						if(list.get(i).getE_areaId2()!=null){
							e2=e2+list.get(i).getE_areaId2().toString();
						}
						
						String e3="";
						if(list.get(i).getE_provinceId3()!=null){
							e3=list.get(i).getE_provinceId3().toString();
						}
						if(list.get(i).getE_cityId3()!=null){
							e3=e3+list.get(i).getE_cityId3().toString();
						}
						if(list.get(i).getE_areaId3()!=null){
							e3=e3+list.get(i).getE_areaId3().toString();
						}
						
						String e4="";
						if(list.get(i).getE_provinceId4()!=null){
							e4=list.get(i).getE_provinceId4().toString();
						}
						if(list.get(i).getE_cityId4()!=null){
							e4=e4+list.get(i).getE_cityId4().toString();
						}
						if(list.get(i).getE_areaId4()!=null){
							e4=e4+list.get(i).getE_areaId4().toString();
						}
						
						String e5="";
						if(list.get(i).getE_provinceId5()!=null){
							e5=list.get(i).getE_provinceId5().toString();
						}
						if(list.get(i).getE_cityId5()!=null){
							e5=e5+list.get(i).getE_cityId5().toString();
						}
						if(list.get(i).getE_areaId5()!=null){
							e5=e5+list.get(i).getE_areaId5().toString();
						}

						if(!list2.contains(e1)){
							list2.add(e1);
						}
						if(!list2.contains(e2)){
							list2.add(e2);
						}else{
							e2="no";
							list.get(i).setE_provinceIdString2("");
							list.get(i).setE_areaIdString2("");
							list.get(i).setE_cityIdString2("");
						}
						if(!list2.contains(e3)){
							list2.add(e3);
						}else{
							e3="no";
							list.get(i).setE_provinceIdString3("");
							list.get(i).setE_areaIdString3("");
							list.get(i).setE_cityIdString3("");
						}
						if(!list2.contains(e4)){
							list2.add(e4);
						}else{
							e4="no";
							list.get(i).setE_provinceIdString4("");
							list.get(i).setE_areaIdString4("");
							list.get(i).setE_cityIdString4("");
						}
						if(!list2.contains(e5)){
							list2.add(e5);
						}else{
							e5="no";
							list.get(i).setE_provinceIdString5("");
							list.get(i).setE_areaIdString5("");
							list.get(i).setE_cityIdString5("");
						}			
			}
			
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
//			if(total==0){
//				pageDTO.setRecordList(null);
//				result.setObject(pageDTO);
//			}
			//result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("获取线路列表异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("获取线路列表异常");
		}
		renderJson(result, request, response);
	}
	
	
	private boolean isCity(CarLineDTO carLineDTO)
    {
		List<String> list  = new ArrayList<String>();
		List<String>  list2  = new ArrayList<String>();
		String s1=carLineDTO.getS_provinceId().toString()+carLineDTO.getS_cityId()+carLineDTO.getS_areaId().toString();
		String s2=carLineDTO.getS_provinceId2().toString()+carLineDTO.getS_cityId2()+carLineDTO.getS_areaId2().toString();
		String s3=carLineDTO.getS_provinceId3().toString()+carLineDTO.getS_cityId3()+carLineDTO.getS_areaId3().toString();
			if(!list.contains(s1)){
				list.add(s1);
			}
			if(!list.contains(s2)){
				list.add(s2);
			}else{
				s2="no";
				carLineDTO.setS_provinceIdString2("");
				carLineDTO.setS_areaIdString2("");
				carLineDTO.setS_cityIdString2("");
			}
			if(!list.contains(s3)){
				list.add(s3);
			}else{
				s3="no";
				carLineDTO.setS_provinceIdString3("");
				carLineDTO.setS_areaIdString3("");
				carLineDTO.setS_cityIdString3("");
			}
			String e1=carLineDTO.getE_provinceId().toString()+carLineDTO.getE_cityId()+carLineDTO.getE_areaId().toString();
			String e2=carLineDTO.getE_provinceId2().toString()+carLineDTO.getE_cityId2()+carLineDTO.getE_areaId2().toString();
			String e3=carLineDTO.getE_provinceId3().toString()+carLineDTO.getE_cityId3()+carLineDTO.getE_areaId3().toString();
			String e4=carLineDTO.getE_provinceId4().toString()+carLineDTO.getE_cityId4()+carLineDTO.getE_areaId4().toString();
			String e5=carLineDTO.getE_provinceId5().toString()+carLineDTO.getE_cityId5()+carLineDTO.getE_areaId5().toString();
	        
			if(!list2.contains(e1)){
				list.add(e1);
			}
			if(!list2.contains(e2)){
				list.add(e2);
			}else{
				e2="no";
				carLineDTO.setE_provinceIdString2("");
				carLineDTO.setE_areaIdString2("");
				carLineDTO.setE_cityIdString2("");
			}
			if(!list2.contains(e3)){
				list.add(e3);
			}else{
				e3="no";
				carLineDTO.setE_provinceIdString3("");
				carLineDTO.setE_areaIdString3("");
				carLineDTO.setE_cityIdString3("");
			}
			if(!list2.contains(e4)){
				list.add(e4);
			}else{
				e4="no";
				carLineDTO.setE_provinceIdString4("");
				carLineDTO.setE_areaIdString4("");
				carLineDTO.setE_cityIdString4("");
			}
			if(!list2.contains(e5)){
				list.add(e5);
			}else{
				e5="no";
				carLineDTO.setE_provinceIdString5("");
				carLineDTO.setE_areaIdString5("");
				carLineDTO.setE_cityIdString5("");
			}
			return true;

    }
	/**
	 * 推送carLine消息插入
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/setCarMessage")
	public void setCarMessage(HttpServletRequest request,
			HttpServletResponse response, CarLineDTO carLineDTO) {
		ObjectResult result = new ObjectResult();
		try {
			AreaDTO a=new AreaDTO();
			if(carLineDTO.getmCity()!=null&&!"".equals(carLineDTO.getmCity())){
				 a=memberAddressApiService.getArea(carLineDTO.getmCity());
				 carLineDTO.setMlng(a.getLng());
				 carLineDTO.setMlat(a.getLat());
			}else{
				carLineDTO.setMlng(Double.valueOf(0));
				carLineDTO.setMlat(Double.valueOf(0));
			}
			AreaDTO bj = memberAddressApiService.getArea("北京市");
			AreaDTO sh = memberAddressApiService.getArea("上海市");
			AreaDTO cq = memberAddressApiService.getArea("重庆市");
			AreaDTO tj = memberAddressApiService.getArea("天津市");

			carLineDTO.setBjlng(bj.getLng());
			carLineDTO.setBjlat(bj.getLat());

			carLineDTO.setShlng(sh.getLng());
			carLineDTO.setShlat(sh.getLat());

			carLineDTO.setTjlng(tj.getLng());
			carLineDTO.setTjlat(tj.getLat());

			carLineDTO.setCqlng(cq.getLng());
			carLineDTO.setCqlat(cq.getLat());
			carLineDTO.setDistance(Double.valueOf(100));
			List<MemberAddressDTO> list = memberAddressApiService
					.getMebApiMessage(carLineDTO);
			if (list != null && list.size() > 0) {

				carLineApiService.setMebApiMessage(carLineDTO, list);
			}

			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");

		} catch (Exception e) {
			logger.info("推送线路消息异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("推送线路异常");
		}
		renderJson(result, request, response);
	}
    }
