package com.gudeng.commerce.gd.api.controller.certif160929;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.PictureTypeEmum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.params.CertifSpProductParamsBean;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifSpProductToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifSpProductDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifSpProductEntity;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

/**
 * 
 */
@Controller
@RequestMapping("v160929/prodCertif")
public class SpProductCertificationController extends GDAPIBaseController {
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SpProductCertificationController.class);

	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	public CertifSpProductToolService certifSpProductToolService;
	
	@Autowired
	public MemberToolService memberToolService ;
	
	@RequestMapping("querySpProductForCertif")
	public void querySpProductForCertif(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String productName = (String)GSONUtils.getJsonValueStr(jsonStr, "productName");
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("productName", productName);
			param.put("memberId", memberId);
			param.put("pictureType", PictureTypeEmum.APP.getValue());
			param.put("excludeFromCertif", 1);
			List<String> stateList = new ArrayList<String>();
			stateList.add(ProductStatusEnum.ON.getkey());
			stateList.add(ProductStatusEnum.OFF.getkey());
			param.put("stateList", stateList);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, param);
			int total = productToolService.querySpProductTotalForCertif(param);
			List<ProductDto> productList = productToolService.querySpProductListForCertif(param);
			String imgHost = gdProperties.getProperties().getProperty("gd.image.server");
			for(ProductDto item : productList){
				item.setImgHost(imgHost);
			}
			
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(productList);
			
			result.setObject(pageDTO);
		} catch (Exception e) {
			logger.info("querySpProductForCertif with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("querySpProdCertifList")
	public void querySpProdCertifList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			String temp = GSONUtils.getJsonValueStr(jsonStr, "stateList");
			
			Map<String, Object> param = new HashMap<String, Object>();
			if (!CommonUtils.isEmpty(temp)){
				List<String> stateList = new ArrayList<String>();
				String[] tempList = temp.split(",");
				for (String item : tempList){
					stateList.add(item.trim());
				}
				param.put("stateList", stateList);
			}
			param.put("memberId", memberId);
			param.put("sortName", "updateTime");
			param.put("sortOrder", "d");
			//查询驳回原因时使用
//			param.put("logType", CertifTypeEnum.CERTIF_SP_PRODUCT.getKey());
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, param);
			int total = certifSpProductToolService.getTotal(param);
			List<CertifSpProductDTO> certifList = certifSpProductToolService.getListPage(param);
			String imgHost = gdProperties.getProperties().getProperty("gd.image.server");
			for(CertifSpProductDTO item : certifList){
				item.setImgHost(imgHost);
			}
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(certifList);
			
			result.setObject(pageDTO);
		} catch (Exception e) {
			logger.info("querySpProductForCertif with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("saveSpProductCertifInfo")
	public void saveSpProductCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifSpProductParamsBean queryParams = (CertifSpProductParamsBean) GSONUtils
					.fromJsonToObject(jsonStr, CertifSpProductParamsBean.class);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", queryParams.getProductId());
			map.put("startRow", 0);
			map.put("endRow", 10);
			List<CertifSpProductDTO> list = certifSpProductToolService.getListPage(map);
			if(!CommonUtils.isEmpty(list)){
				setEncodeResult(result, ErrorCodeEnum.FAIL_FOR_SP_PRODUCT_REAT, request, response);
				return;
			}
			Integer memberId = queryParams.getMemberId() ;
			MemberBaseinfoDTO mdto = memberToolService.getById(String.valueOf(memberId));
			logger.info("CertifSpProduct acount : {} ", mdto.getAccount());
			CertifSpProductEntity entity = new CertifSpProductEntity();
			Date now = new Date();
			//产品id
			entity.setProductId(queryParams.getProductId());
			//会员id
			entity.setMemberId(queryParams.getMemberId());
			//会员账号
			entity.setAccount(mdto.getAccount());
			//商品名称
			entity.setProductName(queryParams.getProductName());
			//商铺名称
			entity.setShopsName(queryParams.getShopsName());
			//产地
			entity.setProvince(queryParams.getProvince());
			entity.setCity(queryParams.getCity());
			entity.setArea(queryParams.getArea());
			//商品图片
			entity.setProductImg(queryParams.getProductImg());
			//产能-对应库表供应量字段
			entity.setUnits(queryParams.getUnits());
			//认证机构
			entity.setCertifOrg(queryParams.getCertifOrg());
			entity.setSigns(queryParams.getSigns());
			entity.setCertifNo(queryParams.getCertifNo());
			entity.setSpecialImg(queryParams.getSpecialImg());
			//企业名称
			entity.setCompanyName(queryParams.getCompanyName());
			entity.setBrand(queryParams.getBrand());
			//认证状态, 0:待审核
			entity.setStatus("0");
			//申请认证时间
			entity.setCommitTime(now);
			entity.setCreateTime(now);
			entity.setCreateUserId(String.valueOf(queryParams.getMemberId()));
			entity.setUpdateTime(now);
			entity.setUpdateUserId(String.valueOf(queryParams.getMemberId()));
			Long res = certifSpProductToolService.insert(entity);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//结果对象
			resultMap.put("resultObj", res);
			//编辑提交之后的状态, 0-待认证
			resultMap.put("certifStatus", 0);
			result.setObject(resultMap);			
		} catch (Exception e) {
			logger.info("saveSpProductCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("updateSpProductCertifInfo")
	public void updateSpProductCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifSpProductParamsBean queryParams = (CertifSpProductParamsBean) GSONUtils
					.fromJsonToObject(jsonStr, CertifSpProductParamsBean.class);
			
			Integer id = queryParams.getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("startRow", 0);
			map.put("endRow", 10);
			List<CertifSpProductDTO> oldList = certifSpProductToolService.getListPage(map);
			if (!CommonUtils.isEmpty(oldList) && "0".equalsIgnoreCase(oldList.get(0).getStatus())){
				setEncodeResult(result, ErrorCodeEnum.FAIL_FOR_EDIT_RECORD_UNDER_AUDIT, request, response);
				return ;
			}
			CertifSpProductDTO entity = new CertifSpProductDTO();
			//认证id-主键
			entity.setId(queryParams.getId());
/*			entity.setProductId(queryParams.getProductId());
			entity.setMemberId(queryParams.getMemberId());
			entity.setAccount(queryParams.getAccount());
			//商品名称
			entity.setProductName(queryParams.getProductName());
			//产地
			entity.setProvince(queryParams.getProvince());
			entity.setCity(queryParams.getCity());
			entity.setArea(queryParams.getArea());
			//商铺名称
			entity.setShopsName(queryParams.getShopsName());
			//商品图片
			entity.setProductImg(queryParams.getProductImg());*/
			//产能-对应库表供应量
			entity.setUnits(queryParams.getUnits());
			//认证机构
			entity.setCertifOrg(queryParams.getCertifOrg());
			//产品标识名称
			entity.setSigns(queryParams.getSigns());
			//证书编号
			entity.setCertifNo(queryParams.getCertifNo());
			//专用标识图片
			entity.setSpecialImg(queryParams.getSpecialImg());
			//企业名称
			entity.setCompanyName(queryParams.getCompanyName()==null ? "": queryParams.getCompanyName());
			//商标
			entity.setBrand(queryParams.getBrand()==null ? "": queryParams.getBrand());
			if (isCertifInfoChanged(queryParams, oldList.get(0))){
				//认证状态, 0:待审核
				entity.setStatus("0");
				entity.setCommitTimeString(DateUtil.sdfDateTime.format(new Date()));
				//编辑提交之后的状态, 0-待认证
				resultMap.put("certifStatus", 0);
			}else{
				resultMap.put("certifStatus", oldList.get(0).getStatus());
			}
//			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(String.valueOf(queryParams.getMemberId()));
			int res = certifSpProductToolService.update(entity);
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
	public boolean isCertifInfoChanged(CertifSpProductParamsBean queryParams, CertifSpProductDTO old){
		//产能
		if ( !CommonUtils.isEmpty(old.getUnits()) && !old.getUnits().equalsIgnoreCase(queryParams.getUnits())){
			logger.info("Units变更 ， older value : {},  new value : {}" , old.getUnits(), queryParams.getUnits());
			return true ;
		}else if (CommonUtils.isEmpty(old.getUnits()) && !CommonUtils.isEmpty(queryParams.getUnits())){
			logger.info("Units变更 ， older value : {},  new value : {}" , old.getUnits(), queryParams.getUnits());
			return true;
		} 
		//认证机构
		if ( !CommonUtils.isEmpty(old.getCertifOrg()) && !old.getCertifOrg().equalsIgnoreCase(queryParams.getCertifOrg())){
			logger.info("CertifOrg变更 ， older value : {},  new value : {}" , old.getCertifOrg(), queryParams.getCertifOrg());
			return true ;
		}else if (CommonUtils.isEmpty(old.getCertifOrg()) && !CommonUtils.isEmpty(queryParams.getCertifOrg())){
			logger.info("CertifOrg变更 ， older value : {},  new value : {}" , old.getCertifOrg(), queryParams.getCertifOrg());
			return true;
		} 
		//产品标识名称
		if ( !CommonUtils.isEmpty(old.getSigns()) && !old.getSigns().equalsIgnoreCase(queryParams.getSigns())){
			logger.info("Signs变更 ， older value : {},  new value : {}" , old.getSigns(), queryParams.getSigns());
			return true ;
		}else if (CommonUtils.isEmpty(old.getSigns()) && !CommonUtils.isEmpty(queryParams.getSigns())){
			logger.info("Signs变更 ， older value : {},  new value : {}" , old.getSigns(), queryParams.getSigns());
			return true;
		} 
		//证书编号
		if ( !CommonUtils.isEmpty(old.getCertifNo()) && !old.getCertifNo().equalsIgnoreCase(queryParams.getCertifNo())){
			logger.info("CertifNo变更 ， older value : {},  new value : {}" , old.getCertifNo(), queryParams.getCertifNo());
			return true ;
		}else if (CommonUtils.isEmpty(old.getCertifNo()) && !CommonUtils.isEmpty(queryParams.getCertifNo())){
			logger.info("CertifNo变更 ， older value : {},  new value : {}" , old.getCertifNo(), queryParams.getCertifNo());
			return true;
		} 		
		//专用标识图片
		if ( !CommonUtils.isEmpty(old.getSpecialImg()) && !old.getSpecialImg().equalsIgnoreCase(queryParams.getSpecialImg())){
			logger.info("SpecialImg变更 ， older value : {},  new value : {}" , old.getSpecialImg(), queryParams.getSpecialImg());
			return true ;
		}else if (CommonUtils.isEmpty(old.getSpecialImg()) && !CommonUtils.isEmpty(queryParams.getSpecialImg())){
			logger.info("SpecialImg变更 ， older value : {},  new value : {}" , old.getSpecialImg(), queryParams.getSpecialImg());
			return true;
		} 		
		return false;
	}
}
