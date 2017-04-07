package com.gudeng.commerce.gd.api.controller.certif160929;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.CertifCompanyParamsBean;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifCompanyToolService;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCompanyDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCompanyEntity;

/**
 * 
 */
@Controller
@RequestMapping("v160929/companyCertif")
public class CompanyCertificationController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CompanyCertificationController.class);

	@Autowired
	public ProductToolService productToolService;

	@Autowired
	public CertifCompanyToolService certifCompanyToolService;

	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public MemberToolService memberToolService ;
	
	@Autowired
	public GdProperties gdProperties;
	
	@RequestMapping("queryCompanyCertifInfo")
	public void queryCompanyCertifInfo(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifCompanyParamsBean queryParams = (CertifCompanyParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifCompanyParamsBean.class);
			
			Integer memberId = queryParams.getMemberId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			CertifCompanyDTO res = certifCompanyToolService.getOneBySearch(params);
			String imgHost = gdProperties.getProperties().getProperty("gd.image.server");
			res.setImgHost(imgHost);
			//结果对象
			result.setObject(res);
		} catch (Exception e) {
			logger.info("queryCompanyCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	
	}
	
	@RequestMapping("saveCompanyCertifInfo")
	public void saveCompanyCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifCompanyParamsBean queryParams = (CertifCompanyParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifCompanyParamsBean.class);

			//会员id
			Integer memberId = queryParams.getMemberId();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			CertifCompanyDTO old = certifCompanyToolService.getOneBySearch(params);
			if (old != null){
				result.setObject("您已经存在一条认证记录了, 请勿重复添加..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			
			MemberBaseinfoDTO mdto = memberToolService.getById(String.valueOf(memberId));
			logger.info("CertifCompany acount : {} ", mdto.getAccount());
			CertifCompanyEntity entity = new CertifCompanyEntity();
			Date now = new Date();
			//认证来源: 1为农批商，2为农商友，3为农速通，4为供应商，5为谷登农批web
			entity.setAppType(queryParams.getAppType().byteValue());
			//会员Id
			entity.setMemberId(memberId);
			//用户账户
			entity.setAccount(mdto.getAccount());
			// 企业名称
			entity.setCompanyName(queryParams.getCompanyName());
			// 注册号-即营业执照号码
			entity.setBzl(queryParams.getBzl());
			//法定代表人姓名
			entity.setRealName(queryParams.getRealName());
			// 营业执照照片
			entity.setBzlPhotoUrl(queryParams.getBzlPhotoUrl());
			
			//法定代表人身份证号
			entity.setIdCard(queryParams.getIdCard());
			// 法人身份证图片, 逗号分隔
			entity.setCardPhotoUrl(queryParams.getCardPhotoUrl());
			//认证状态,0-待审核
			entity.setStatus("0");
			//申请认证时间
			entity.setCommitTime(now);
			entity.setCreateTime(now);
			entity.setCreateUserId(String.valueOf(queryParams.getMemberId()));
			entity.setUpdateTime(now);
			entity.setUpdateUserId(String.valueOf(queryParams.getMemberId()));
			Long res = certifCompanyToolService.insert(entity);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//结果对象
			resultMap.put("resultObj", res);
			//编辑提交之后的状态, 0-待认证
			resultMap.put("certifStatus", 0);
			result.setObject(resultMap);
		} catch (Exception e) {
			logger.info("saveCompanyCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("updateCompanyCertifInfo")
	public void updateCompanyCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifCompanyParamsBean queryParams = (CertifCompanyParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifCompanyParamsBean.class);
			logger.info("queryParams : {}", queryParams);
			Integer certifId = queryParams.getId();
			CertifCompanyDTO dto = certifCompanyToolService.getById(String.valueOf(certifId));
			if (dto == null || "0".equalsIgnoreCase( dto.getStatus())){
				setEncodeResult(result, ErrorCodeEnum.FAIL_FOR_EDIT_RECORD_UNDER_AUDIT, request, response);
				return;
			}
			CertifCompanyDTO entity = new CertifCompanyDTO();
			//认证id-主键
			entity.setId(certifId);
			// 企业名称
			entity.setCompanyName(queryParams.getCompanyName());
			// 注册号-即营业执照号码
			entity.setBzl(queryParams.getBzl());
			//法定代表人姓名
			entity.setRealName(queryParams.getRealName());
			// 营业执照照片
			entity.setBzlPhotoUrl(queryParams.getBzlPhotoUrl());
			
			//法定代表人身份证号
			entity.setIdCard(queryParams.getIdCard()==null ? "" : queryParams.getIdCard());
			// 法人身份证图片, 逗号分隔
			entity.setCardPhotoUrl(queryParams.getCardPhotoUrl()==null ? "" : queryParams.getCardPhotoUrl());
			if(isCertifInfoChanged(queryParams, dto)){
				//认证状态,0-待审核
				entity.setStatus("0");	
				entity.setCommitTime(new Date());
				//编辑提交之后的状态, 0-待认证.., 供前端使用 
				resultMap.put("certifStatus", 0);
			}else{
				resultMap.put("certifStatus", dto.getStatus());
			}
			entity.setUpdateUserId(String.valueOf(queryParams.getMemberId()));
			int res = certifCompanyToolService.update(entity);
			//结果对象
			resultMap.put("resultObj", res);
			result.setObject(resultMap);
		} catch (Exception e) {
			logger.info("saveSpProductCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	public boolean isCertifInfoChanged(CertifCompanyParamsBean queryParams, CertifCompanyDTO dto){
		//企业名称
		if ( !CommonUtils.isEmpty(dto.getCompanyName()) && !dto.getCompanyName().equalsIgnoreCase(queryParams.getCompanyName())){
			logger.info("CompanyName变更 ， older value : {},  new value : {}" , dto.getCompanyName(), queryParams.getCompanyName());
			return true ;
		}else if (CommonUtils.isEmpty(dto.getCompanyName()) && !CommonUtils.isEmpty(queryParams.getCompanyName())){
			logger.info("CompanyName变更 ， older value : {},  new value : {}" , dto.getCompanyName(), queryParams.getCompanyName());
			return true;
		} 
		//注册号(营业执照号码)
		if ( !CommonUtils.isEmpty(dto.getBzl()) && !dto.getBzl().equalsIgnoreCase(queryParams.getBzl())){
			logger.info("Bzl变更 ， older value : {},  new value : {}" , dto.getBzl(), queryParams.getBzl());
			return true ;
		}else if (CommonUtils.isEmpty(dto.getBzl()) && !CommonUtils.isEmpty(queryParams.getBzl())){
			logger.info("Bzl变更 ， older value : {},  new value : {}" , dto.getBzl(), queryParams.getBzl());
			return true;
		} 
		//法定代表人姓名
		if ( !CommonUtils.isEmpty(dto.getRealName()) && !dto.getRealName().equalsIgnoreCase(queryParams.getRealName())){
			logger.info("法定代表人姓名变更 ， older value : {},  new value : {}" , dto.getRealName(), queryParams.getRealName());
			return true ;
		}else if (CommonUtils.isEmpty(dto.getRealName()) && !CommonUtils.isEmpty(queryParams.getRealName())){
			logger.info("法定代表人姓名变更 ， older value : {},  new value : {}" , dto.getRealName(), queryParams.getRealName());
			return true;
		} 
		//营业执照图片
		if ( !CommonUtils.isEmpty(dto.getBzlPhotoUrl()) && !dto.getBzlPhotoUrl().equalsIgnoreCase(queryParams.getBzlPhotoUrl())){
			logger.info("BzlPhotoUrl变更 ， older value : {},  new value : {}" , dto.getBzlPhotoUrl(), queryParams.getBzlPhotoUrl());
			return true ;
		}else if (CommonUtils.isEmpty(dto.getBzlPhotoUrl()) && !CommonUtils.isEmpty(queryParams.getBzlPhotoUrl())){
			logger.info("BzlPhotoUrl变更 ， older value : {},  new value : {}" , dto.getBzlPhotoUrl(), queryParams.getBzlPhotoUrl());
			return true;
		}
		return false;
	}

}
