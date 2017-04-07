package com.gudeng.commerce.gd.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AppactivitystatToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.ditui.GrdMemberToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.AppactivitystatEntity;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;

@Controller
@RequestMapping("appactivitystat")
public class AppActivityStatController  extends GDAPIBaseController {
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AppActivityStatController.class);
	
	@Autowired
	private AppactivitystatToolService appactivitystatToolService;
	@Autowired
	public MemberToolService memberToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	
	@Autowired
	private GrdMemberToolService grdMemberToolService;
	
	@Autowired
	public ProCategoryService productCategoryService;	
	
	@RequestMapping("/startup")
	public void appStartupStat(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			String jsonStr = getParamDecodeStr(request);
			
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");			
			String UUID = GSONUtils.getJsonValueStr(jsonStr, "UUID");
			String MEID = GSONUtils.getJsonValueStr(jsonStr, "MEID");
			String IMEI = GSONUtils.getJsonValueStr(jsonStr, "IMEI");
			String ICCID = GSONUtils.getJsonValueStr(jsonStr, "ICCID");
			String cellphoneModel = GSONUtils.getJsonValueStr(jsonStr, "cellphoneModel");
			String cellphoneRAM = GSONUtils.getJsonValueStr(jsonStr, "cellphoneRAM");
			String cellphoneROM = GSONUtils.getJsonValueStr(jsonStr, "cellphoneROM");
			String system = GSONUtils.getJsonValueStr(jsonStr, "system");
			String systemVersion = GSONUtils.getJsonValueStr(jsonStr, "systemVersion");			
			String appVersion = GSONUtils.getJsonValueStr(jsonStr, "appVersion");
			String appChannel = GSONUtils.getJsonValueStr(jsonStr, "appChannel");
			String isLogin = GSONUtils.getJsonValueStr(jsonStr, "isLogin");
			String appType = GSONUtils.getJsonValueStr(jsonStr, "appType");
			
			
//			String memberId = request.getParameter("memberId");			
//			String UUID = request.getParameter("UUID");
//			String MEID = request.getParameter("MEID");
//			String IMEI = request.getParameter("IMEI");
//			String ICCID = request.getParameter("ICCID");
//			String cellphoneModel = request.getParameter("cellphoneModel");
//			String cellphoneRAM = request.getParameter("cellphoneRAM");
//			String cellphoneROM = request.getParameter("cellphoneROM");
//			String system = request.getParameter("system");
//			String systemVersion = request.getParameter("systemVersion");			
//			String appVersion = request.getParameter("appVersion");
//			String appChannel = request.getParameter("appChannel");
//			String isLogin = request.getParameter("isLogin");
//			String appType = request.getParameter("appType");
			
			
			AppactivitystatEntity entity = new AppactivitystatEntity();
			if(StringUtils.isNotBlank(memberId)){
				entity.setMemberId(Long.parseLong(memberId));
			}
			entity.setDeviceUUID(UUID);
			entity.setDeviceIMEI(IMEI);
			entity.setDeviceMEID(MEID);
			entity.setDeviceICCID(ICCID);
			entity.setCellphoneModel(cellphoneModel);
			entity.setCellphoneRAM(cellphoneRAM);
			entity.setCellphoneROM(cellphoneROM);
			entity.setSystem(system);
			entity.setSystemVersion(systemVersion);
			entity.setAppVersion(appVersion);
			entity.setAppChannel(appChannel);
			entity.setIsLogin(isLogin.equals("1")?true:false);
			entity.setAppType(appType);
			entity.setCreateTime(new Date());
			entity.setType("1");
			//1农商友，2农批商，3供应商，4铁军，5车主，6货主，7物流公司
			int app = Integer.parseInt(appType);
			if(StringUtils.isNotBlank(memberId)){
				if(app==4){//铁军
					GrdMemberDTO  grdMember = 	grdMemberToolService.getById(memberId);
					entity.setUserCreateTime(grdMember.getCreateTime());
					entity.setMobile(grdMember.getMobile());
					entity.setAccount(grdMember.getName());
					entity.setMarketId(grdMember.getMarketId()==null?0:grdMember.getMarketId().intValue());
					entity.setMarketName(grdMember.getMarket());
					
					entity.setTeamId(grdMember.getGiftteamId()==null?0:grdMember.getGiftteamId().intValue());
					entity.setTeamName(grdMember.getGiftteamName());
					
				}else{
					MemberBaseinfoDTO  memberDto = memberToolService.getById(memberId);
					if(memberDto!=null){
						entity.setUserCreateTime(memberDto.getCreateTime());
						entity.setMobile(memberDto.getMobile());
						entity.setAccount(memberDto.getAccount());
						entity.setRegetype(memberDto.getRegetype());
						entity.setNsyUserType((byte)(memberDto.getNsyUserType()==null?0:memberDto.getNsyUserType().intValue()));
						if(app==3||app==2){
							BusinessBaseinfoDTO business = businessBaseinfoToolService.getByUserId(memberId);
							if(business!=null){
								//entity.setMainProduct(business.getMainProduct());
								entity.setManagementtype((byte)(business.getManagementType()==null?0:business.getManagementType().intValue()));
								entity.setMarketId(business.getMarketId()==null?0:Integer.parseInt(business.getMarketId()));
								entity.setMarketName(business.getMarketName());
								//找出主营分类
								if(business.getMarketId()!=null){
									StringBuffer sbf=new StringBuffer("");
									Map<String,Object> map =new HashMap<String,Object>();
									map.put("businessId", business.getBusinessId());
									map.put("startRow", 0);
									map.put("endRow", 9999);
									List<ReBusinessCategoryDTO>  categoryIds=businessBaseinfoToolService.getReBusinessCategoryBySearch(map);
									if(categoryIds!=null && categoryIds.size()>0){
										for(ReBusinessCategoryDTO category:categoryIds){
											if(sbf.length()==0){
												sbf.append(category.getCategoryId());
											}else{
												sbf.append(",");
												sbf.append(category.getCategoryId());
											}
										}
									}
									
									List<ProductCategoryDTO> retList = productCategoryService.getListTopProductCategoryByMarketId(Long.valueOf(business.getMarketId()));
									for(ProductCategoryDTO cdto:retList){
										if(categoryIds!=null && categoryIds.size()>0){
											for(ReBusinessCategoryDTO category:categoryIds){
												//主营设置选中
												if(category.getCategoryId().longValue()==cdto.getCategoryId().longValue()&&"0".equals(category.getBusinessType())){
													entity.setCategoryId(cdto.getCategoryId().intValue());
													entity.setCategoryName(cdto.getCateName());
													break;
												}
											}
										}
									}
								}
							}
						
					}

					}
				}				
			}
			
			
			Long insert = appactivitystatToolService.insert(entity);
			logger.info("---------->统计结果："+insert);
//			result.setObject(map);
//			renderJson(result, request, response);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			
		}catch(Exception e){
			e.printStackTrace();
//			renderJson(result, request, response);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		
	}
	
	

}
