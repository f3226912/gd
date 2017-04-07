package com.gudeng.commerce.gd.api.controller.v2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.CarManagerApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.v160929.CertifCompanyToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCompanyDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCompanyEntity;

/**
 * 农批商控制中心
 * @author TerryZhang
 */
@Controller
@RequestMapping("v2/certifi")
public class MemberCeitifiController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberCeitifiController.class);
	
	@Autowired
	private MemberCertifiApiService memberCertifiApiService;
	
	@Autowired
	public CarManagerApiService carManagerApiService;
	
	@Autowired
	public CertifCompanyToolService certifCompanyToolService;
	
	@Autowired
	public GdProperties gdProperties;
	
	@RequestMapping("/getCertifiById")
	public void getCertifiById(HttpServletRequest request, HttpServletResponse response, String id) {
		ObjectResult result = new ObjectResult();
		try {
			if(id==null || id.trim().equals("")){
				setResult(result, ErrorCodeEnum.CERTIFY_ID_IS_NULL, request, response);
				return ;
			}
			MemberCertifiDTO dto = memberCertifiApiService.getById(Long.valueOf(id));
			if(dto!=null && com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(dto.getStatus())){
				CertifCompanyDTO res = certifCompanyToolService.getById(id);
				if(res!=null){//将新的企业认证信息，对应到旧接口的企业认证信息中
					MemberCertifiDTO cdto =new MemberCertifiDTO();
					cdto=translate(res, cdto);
					result.setObject(cdto);
				}else{
					result.setObject(null);
				}
			}else{
				result.setObject(dto);
			}
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("/getCertifiByUserId")
	public void getCertifiByUserId(HttpServletRequest request, HttpServletResponse response, String userId) {
		ObjectResult result = new ObjectResult();
		try {
			if(userId==null || userId.trim().equals("")){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return ;
			}
			MemberCertifiDTO dto = memberCertifiApiService.getByUserId(Long.valueOf(userId));
			if(dto!=null && !com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(dto.getStatus())){
				result.setObject(dto);
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("memberId", userId);
				CertifCompanyDTO res = certifCompanyToolService.getOneBySearch(params);
				if(res!=null){//将新的企业认证信息，对应到旧接口的企业认证信息中
					MemberCertifiDTO cdto =new MemberCertifiDTO();
					cdto=translate(res, cdto);
					result.setObject(cdto);
				}else{
					result.setObject(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("/addCertifi")
	public void addCertifi(HttpServletRequest request, HttpServletResponse response, MemberCertifiDTO params) {
		ObjectResult result = new ObjectResult();
		try {
			if(params.getType()==null ||params.getType().equals("")){
				setResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
				return ;
			}
//			if(params.getAccount()==null ||params.getAccount().trim().equals("")){
//				setResult(result, ErrorCodeEnum.FAIL.getValue(), "用户账号不能为空.....", request, response);
//				return ;
//			}
			if(params.getMemberId()==null ){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return ;
			}
//			由于农速通保存个人、企业信息时，会往certifi 表写一个记录，所以不能简单的判断存在记录，就不插入。应该用判断有记录则更新，无记录则新增。			
//			MemberCertifiDTO dto = memberCertifiApiService.getByUserId(params.getMemberId());
//			if(null!=dto){
//				setResult(result, ErrorCodeEnum.FAIL.getValue(), "用户已经提交过认证，请勿重复提交.....", request, response);
//				return ;
//			}
		    params.setStatus("0");
		    params.setCommitTime(new Date());
			if(params.getType().equals("1")){
				if(params.getLinkMan() ==null || params.getLinkMan().trim().equals("")){
					setResult(result, ErrorCodeEnum.USERNAME_IS_NULL, request, response);
					return ;
				}
				if(params.getLinkMan()!=null && params.getLinkMan().length()>14){
					setResult(result, ErrorCodeEnum.USERNAME_OVER_LENGTH, request, response);
					return ;
				}
				if(params.getIdCard()==null || params.getIdCard().trim().equals("")){
					setResult(result, ErrorCodeEnum.ID_CARD_IS_NULL, request, response);
					return ;
				}
				if(!Validator.isIDCard(params.getIdCard())){
					setResult(result, ErrorCodeEnum.ID_CARD_ERROR, request, response);
					return ;
				}
				if(params.getCardPhotoUrl() ==null||params.getCardPhotoUrl().trim().equals("")){
					setResult(result, ErrorCodeEnum.ID_CARD_IMAGE_IS_NULL, request, response);
					return ;
				}
				if(!params.getCardPhotoUrl().contains(",")){
					setResult(result, ErrorCodeEnum.ID_CARD_IMAGE_ERROR, request, response);
					return ;
				}
				
				 MemberCertifiDTO mc =  memberCertifiApiService.getByUserId(params.getMemberId());
				//认证信息存在 更新，不存在新增
			    if(null != mc){
			       params.setCertifiId(mc.getCertifiId());
				   memberCertifiApiService.updateMemberCertifiDTO(params);
			    }
			    else{
			    	memberCertifiApiService.addMemberCertifiDTO(params);
			    }
			}else if(params.getType().equals("2")){
				if(params.getCompanyName()==null || params.getCompanyName().equals("")){
					setResult(result, ErrorCodeEnum.COMPANY_NAME_IS_NULL, request, response);
					return ;
				}
				if(params.getLinkMan() ==null || params.getLinkMan().trim().equals("")){
					setResult(result, ErrorCodeEnum.LINKMAN_IS_NULL, request, response);
					return ;
				}
				if(params.getLinkMan()!=null && params.getLinkMan().length()>14){
					setResult(result, ErrorCodeEnum.LINKMAN_OVER_LENGTH, request, response);
					return ;
				}
				if(params.getBzl()==null || params.getBzl().trim().equals("")){
					setResult(result, ErrorCodeEnum.BZL_IS_NULL, request, response);
					return ;
				}
				if(params.getBzlPhotoUrl() ==null||params.getBzlPhotoUrl().trim().equals("")){
					setResult(result, ErrorCodeEnum.BZL_PHOTO_IS_NULL, request, response);
					return ;
				}
				Map<String, Object> par = new HashMap<String, Object>();
				par.put("memberId", params.getMemberId());
				CertifCompanyDTO res = certifCompanyToolService.getOneBySearch(par);
				
				if (null != res) {
					setResult(result, ErrorCodeEnum.ALREADY_CERTIFIED, request, response);
					return ;
				} else {
					CertifCompanyEntity entity=new CertifCompanyEntity();
					certifCompanyToolService.insert(translate(params, entity));
				}
			}
		} catch (Exception e) {
			logger.error("新增认证异常",e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/updateCertifi")
	public void updateCertifi(HttpServletRequest request, HttpServletResponse response, MemberCertifiDTO params) {
		ObjectResult result = new ObjectResult();
		try {
			if(null == params.getCertifiId()){
				setResult(result, ErrorCodeEnum.CERTIFY_ID_IS_NULL, request, response);
				return ;
			}
			if(params.getType()==null ||params.getType().equals("")){
				setResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
				return ;
			}
			if(params.getAccount()==null ||params.getAccount().trim().equals("")){
				setResult(result, ErrorCodeEnum.ACCOUNT_IS_NULL, request, response);
				return ;
			}
			if(params.getMemberId()==null ){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return ;
			}
			if(params.getType().equals("1")){
				if(params.getLinkMan() ==null || params.getLinkMan().trim().equals("")){
					setResult(result, ErrorCodeEnum.USERNAME_IS_NULL, request, response);
					return ;
				}
				if(params.getLinkMan()!=null && params.getLinkMan().length()>30){
					setResult(result, ErrorCodeEnum.USERNAME_OVER_LENGTH30, request, response);
					return ;
				}
				if(params.getIdCard()==null || params.getIdCard().trim().equals("")){
					setResult(result, ErrorCodeEnum.ID_CARD_IS_NULL, request, response);
					return ;
				}
				if(!Validator.isIDCard(params.getIdCard())){
					setResult(result, ErrorCodeEnum.ID_CARD_ERROR, request, response);
					return ;
				}
				if(params.getCardPhotoUrl() ==null||params.getCardPhotoUrl().trim().equals("")){
					setResult(result, ErrorCodeEnum.ID_CARD_IMAGE_IS_NULL, request, response);
					return ;
				}
				if(!params.getCardPhotoUrl().contains(",")){
					setResult(result, ErrorCodeEnum.ID_CARD_IMAGE_ERROR, request, response);
					return ;
				}
			}else if(params.getType().equals("2")){
				if(params.getLinkMan() ==null || params.getLinkMan().trim().equals("")){
					setResult(result, ErrorCodeEnum.LINKMAN_IS_NULL, request, response);
					return ;
				}
				if(params.getLinkMan()!=null && params.getLinkMan().length()>30){
					setResult(result, ErrorCodeEnum.USERNAME_OVER_LENGTH30, request, response);
					return ;
				}
				if(params.getBzl()==null || params.getBzl().trim().equals("")){
					setResult(result, ErrorCodeEnum.BZL_IS_NULL, request, response);
					return ;
				}
				if(params.getBzlPhotoUrl() ==null||params.getBzlPhotoUrl().trim().equals("")){
					setResult(result, ErrorCodeEnum.BZL_PHOTO_IS_NULL, request, response);
					return ;
				}
			}
			if(params.getType()!=null && params.getType().equals("1")){
				MemberCertifiDTO dto = memberCertifiApiService.getByUserId(params.getMemberId());
				if(null!=dto && !dto.getType().equals(params.getType())){
					setResult(result, ErrorCodeEnum.ALREADY_CERTIFIED, request, response);
					return ;
				}
				params.setStatus("0");//修改的时候，相当于重新提交认证，由驳回状态2，改为提交（待审核状态）0
				params.setCommitTime(new Date());//重新提交认证，认证时间需要修改。
				params.setMemberId(null);//更新的时候，不修改memberId
				memberCertifiApiService.updateMemberCertifiDTO(params);
			}else{
				Map<String, Object> par = new HashMap<String, Object>();
				par.put("memberId", params.getMemberId());
				CertifCompanyDTO entity = new CertifCompanyDTO();
				entity=(CertifCompanyDTO) translate(params, entity);
				entity.setStatus("0");			
				entity.setUpdateUserId(String.valueOf(params.getMemberId()));
				certifCompanyToolService.update(entity);
			}
		} catch (Exception e) {
			logger.error("编辑认证异常",e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	
	/**
	 * 农速通认证
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/addNSTCertifi")
	public void addNSTCertifi(HttpServletRequest request, HttpServletResponse response, MemberCertifiDTO params) {

		ObjectResult result = new ObjectResult();
		try {
			/*if(params.getType()==null ||params.getType().equals("")){
				setResult(result, ErrorCodeEnum.FAIL.getValue(), "请设置认证类型，1为个人，2位企业.....", request, response);
				return ;
			}*/
			if(params.getAccount()==null ||params.getAccount().trim().equals("")){
				setResult(result, ErrorCodeEnum.ACCOUNT_IS_NULL, request, response);
				return ;
			}
			  if(params.getMemberId()==null ){
					setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return ;
			  }
				if(params.getNst_linkMan() ==null || params.getNst_linkMan().trim().equals("")){
					setResult(result, ErrorCodeEnum.USERNAME_IS_NULL, request, response);
					return ;
				}
				if(params.getNst_idCard()==null || params.getNst_idCard().trim().equals("")){
					setResult(result, ErrorCodeEnum.ID_CARD_IS_NULL, request, response);
					return ;
				}
				if(!Validator.isIDCard(params.getNst_idCard())){
					setResult(result, ErrorCodeEnum.ID_CARD_ERROR, request, response);
					return ;
				}
				
				if (!StringUtils.isEmpty(params.getType())) {
					 if( "1".equals(params.getType()) && StringUtils.isEmpty(params.getNst_cardPhotoUrl())){
							setResult(result, ErrorCodeEnum.ID_CARD_IMAGE_IS_NULL, request, response);
							return ;
					 }
					 else if( "2".equals(params.getType()) && StringUtils.isEmpty(params.getNst_bzlPhotoUrl())){
							setResult(result, ErrorCodeEnum.BZL_PHOTO_IS_NULL, request, response);
							return ;
					 }
				}else{
					if(StringUtils.isEmpty(params.getNst_cardPhotoUrl()) && StringUtils.isEmpty(params.getNst_bzlPhotoUrl())){
						setResult(result, ErrorCodeEnum.ID_CARD_IMAGE_IS_NULL, request, response);
						return ;
					}
				}
				/*if(StringUtils.isEmpty(request.getParameter("carNumber"))){
					setResult(result, ErrorCodeEnum.FAIL.getValue(), "请选择车辆信息.....", request, response);
					return ;
				}*/
				//认证必须上传“行驶证”
				if(StringUtils.isEmpty(params.getNst_vehiclePhotoUrl()) && !StringUtils.isEmpty(request.getParameter("carNumber"))){
					setResult(result, ErrorCodeEnum.VEHICLE_PHOTO_URL_ID_IS_NULL, request, response);
					return ;
				}
				
			 //更新车辆行驶证和驾驶证信息
			  CarsDTO car = carManagerApiService.getCarByCarNumber(request.getParameter("carNumber"));
			  if(car !=null){
			  car.setEntUserId(params.getMemberId());//标识关联车辆已经认证
			  car.setNst_vehiclePhotoUrl(params.getNst_vehiclePhotoUrl());
			  car.setNst_driverPhotoUrl(params.getNst_driverPhotoUrl());
              carManagerApiService.updateCars(car)	;
			  }
              
			 MemberCertifiDTO mc =  memberCertifiApiService.getByUserId(params.getMemberId());
			 //农速通认证certificationType为1
			  params.setCertificationType("1");
			  params.setNstStatus("0");
			 /* if (StringUtils.isEmpty(params.getType())) {
				
			  }*/
			   if (StringUtils.isNotEmpty(params.getNst_bzlPhotoUrl())) {
					params.setType("2");
				} else {
					params.setType("1");
				}
			//认证信息存在 更新，不存在新增
		    if(null != mc){
		      //如果已经存在非农速通认证信息，CertificationType 不更新为1，后台通过c.nst_linkMan is not null查询记录
		       params.setCertifiId(mc.getCertifiId());
			   memberCertifiApiService.updateMemberCertifiDTO(params);
		    }
		    else{
				memberCertifiApiService.addMemberCertifiDTO(params);
		    }
		} catch (Exception e) {
			logger.error("新增农速通认证异常",e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	
	}
	
	private CertifCompanyEntity translate(MemberCertifiDTO dto ,CertifCompanyEntity entity){
		entity.setAccount(dto.getAccount());
		entity.setBzl(dto.getBzl());
		entity.setBzlPhotoUrl(dto.getBzlPhotoUrl());
		entity.setMemberId(dto.getMemberId().intValue());
//		entity.setAppType(appType);
		entity.setCommitTime(dto.getCommitTime());
		entity.setCompanyName(dto.getCompanyName());
		entity.setCardPhotoUrl(dto.getCardPhotoUrl());
		entity.setCreateTime(dto.getCreateTime());
		entity.setCreateUserId(dto.getCreateUserId());
//		entity.setIcon(dto.getIcon());
//		entity.setId(dto.getCertifiId());
		entity.setIdCard(dto.getIdCard());
//		entity.setOptionUser(optionUser);
		entity.setRealName(dto.getLinkMan());
		entity.setStatus(dto.getStatus());
		entity.setUpdateTime(dto.getUpdateTime());
		entity.setUpdateUserId(dto.getUpdateUserId());
		return entity;
	}
	
	private MemberCertifiDTO translate(CertifCompanyEntity entity ,MemberCertifiDTO dto){
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");

		dto.setBzlPhotoUrl_relative(entity.getBzlPhotoUrl());
		dto.setCardPhotoUrl_relative(entity.getCardPhotoUrl());
//		dto.setOrgCodePhotoUrl_relative(entity.getBzlPhotoUrl());//新版没有组织机构代码照片，直接用营业执照照片
		
		dto.setBzlPhotoUrl(imageHost+entity.getBzlPhotoUrl());
		dto.setCardPhotoUrl(imageHost+entity.getCardPhotoUrl());//最前面加上imageHost
		if(entity.getCardPhotoUrl()!=null){
			dto.setCardPhotoUrl(entity.getCardPhotoUrl().replace(",", ","+imageHost )) ;//所有的","后面，加上imageHost
		}
//		dto.setOrgCodePhotoUrl(imageHost+entity.getBzlPhotoUrl());//新版没有组织机构代码照片，直接用营业执照照片
		
		dto.setAccount(entity.getAccount());
		dto.setBzl(entity.getBzl());
//		dto.setBzlPhotoUrl(entity.getBzlPhotoUrl());
		dto.setMemberId(entity.getMemberId().longValue());
//		dto.setAppType(appType);
		dto.setCommitTime(entity.getCommitTime());
		dto.setCompanyName(entity.getCompanyName());
//		dto.setCardPhotoUrl(entity.getCardPhotoUrl());
		dto.setCreateTime(entity.getCreateTime());
		dto.setCreateUserId(entity.getCreateUserId());
//		dto.setIcon(entity.getIcon());
		dto.setCertifiId(entity.getId().longValue());
		dto.setIdCard(entity.getIdCard());
//		dto.setOptionUser(optionUser);
		dto.setLinkMan(entity.getRealName());
		dto.setStatus(entity.getStatus());
		dto.setUpdateTime(entity.getUpdateTime());
		dto.setUpdateUserId(entity.getUpdateUserId());
		dto.setType("2");
		return dto;
	}
	
	
}
