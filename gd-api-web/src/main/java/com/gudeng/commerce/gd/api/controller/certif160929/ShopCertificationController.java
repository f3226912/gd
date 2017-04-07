package com.gudeng.commerce.gd.api.controller.certif160929;

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

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.engj.ReBusinessPosToolService;
import com.gudeng.commerce.gd.api.service.sinxin.MemberInfoToolService;
import com.gudeng.commerce.gd.api.service.steelyard.ReBusinessSteelyardToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifShopToolService;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifShopDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifShopEntity;

/**
 * 实体商铺认证
 */
@Controller
@RequestMapping("v160929/shopCertif")
public class ShopCertificationController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ShopCertificationController.class);

	@Autowired
	public CertifShopToolService certifShopToolService;

	@Autowired
	public MemberInfoToolService memberInfoToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public MemberToolService memberToolService;
	
	@Autowired
	public ReBusinessPosToolService reBusinessPosToolService;
	
	@Autowired
	public ReBusinessSteelyardToolService reBusinessSteelyardToolService;
	
	@Autowired
	public GdProperties gdProperties;
	
	/**
	 * 实体商铺认证-获取
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryShopCertifInfo")
	public void queryShopCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try{
			String jsonStr = getParamDecodeStr(request);
			CertifShopEntity queryParams = (CertifShopEntity) GSONUtils.fromJsonToObject(jsonStr,
					CertifShopEntity.class);
			CertifShopDTO dto = certifShopToolService.getByUserId(queryParams.getMemberId().toString());
			if( null == dto ){
				setEncodeResult(result, ErrorCodeEnum.NO_CERTIFI, request, response);
				return;
			}
			String imgHost = gdProperties.getProperties().getProperty("gd.image.server");
			dto.setImgHost(imgHost);
			//结果对象
			result.setObject(dto);
		} catch (Exception e) {
			logger.info("queryShopCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	/**
	 * 实体商铺认证
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveShopCertifInfo")
	public void saveShopCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifShopEntity queryParams = (CertifShopEntity) GSONUtils.fromJsonToObject(jsonStr,
					CertifShopEntity.class);
			//判断是否已经存在认证记录
			Map<String,Object> map = new HashMap<>();
			map.put("memberId", queryParams.getMemberId());
			int count = certifShopToolService.getTotal(map);
			if(count > 0){
				setEncodeResult(result, ErrorCodeEnum.CERTIFI_EXIST, request, response);
				return;
			}
			if(queryParams.getRealShopName().length() > 36){
				setEncodeResult(result, ErrorCodeEnum.REALSHOPNAME_TOO_LOON, request, response);
				return;
			}
			if(queryParams.getOperatorName().length() > 36){
				setEncodeResult(result, ErrorCodeEnum.OPERATORNAME_TOO_LOON, request, response);
				return;
			}
			//根据id查找会员信息
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getById(queryParams.getMemberId().toString());
			queryParams.setAccount(memberBaseinfoDTO.getAccount());
			//获取线上商铺信息
			BusinessBaseinfoDTO  businessBaseinfoDTO = businessBaseinfoToolService.getById(queryParams.getBusinessId().toString());
			//线上商铺名称
			queryParams.setShopName(businessBaseinfoDTO.getShopsName());
			//获取线上商铺主营分类
			ReBusinessCategoryDTO reBusinessCategoryDTO = reBusinessCategoryToolService
					.getCateIdByBusId(queryParams.getBusinessId().toString());
			if(!CommonUtils.isEmpty(reBusinessCategoryDTO) && !CommonUtils.isEmpty(reBusinessCategoryDTO.getCategoryId())){
				//主营分类
				queryParams.setCateId(Integer.valueOf(reBusinessCategoryDTO.getCategoryId().toString()));
			}
			//pos终端号
			List<ReBusinessPosDTO> posNumList = reBusinessPosToolService.getPostNumByBusId(queryParams.getBusinessId().toString());
			if(posNumList.size() > 0){
				String posNum = "";
				for(ReBusinessPosDTO posNumdto : posNumList){
					posNum += posNumdto.getPosNumber() + ",";
				}
				posNum = posNum.substring(0,posNum.length() - 1);
				queryParams.setPosNo(posNum);
			}
			
			//智能秤（MAC地址）
			List<ReBusinessSteelyardDTO> macList = reBusinessSteelyardToolService.getMacByBusId(queryParams.getBusinessId().toString());
			if(macList.size() > 0){
				String mac = "";
				for(ReBusinessSteelyardDTO macDto : macList){
					mac += macDto.getMacAddr() + ",";
				}
				mac = mac.substring(0, mac.length() - 1);
				queryParams.setMacNo(mac);
			}
			
			//提交时间
			queryParams.setCommitTime(new Date());
			//状态(待审核)
			queryParams.setStatus(Constant.shopStatus.SHOP_STATUS_0);
			//记录创建时间
			queryParams.setCreateTime(new Date());
			//createUserId
			queryParams.setCreateUserId(queryParams.getMemberId().toString());
			Long res = certifShopToolService.insert(queryParams);

			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 结果对象
			resultMap.put("resultObj", res);
			// 编辑提交之后的状态, 0-待认证
			resultMap.put("certifStatus", 0);
			result.setObject(resultMap);
		} catch (Exception e) {
			logger.info("saveShopCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	/**
	 * 实体商铺认证-编辑
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("editShopCertifInfo")
	public void editShopCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifShopDTO certifShopDTO = (CertifShopDTO) GSONUtils.fromJsonToObject(jsonStr,
					CertifShopDTO.class);
			
			//判断是否有内容更新
			CertifShopDTO dto = certifShopToolService.getByUserId(certifShopDTO.getMemberId().toString());
			//如有内容更新并且状态为已认证时不做任何操作，依旧保持已认证状态
			if(!isUpdate(dto, certifShopDTO) && dto.getStatus().equals(Constant.shopStatus.SHOP_STATUS_1)){
				Map<String, Object> resultMap = new HashMap<String, Object>();
				// 结果对象
				resultMap.put("resultObj", 0);
				resultMap.put("certifStatus", dto.getStatus());
				result.setObject(resultMap);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			//根据id查找会员信息
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getById(certifShopDTO.getMemberId().toString());
			certifShopDTO.setAccount(memberBaseinfoDTO.getAccount());
			//获取线上商铺信息
			BusinessBaseinfoDTO  businessBaseinfoDTO = businessBaseinfoToolService.getById(certifShopDTO.getBusinessId().toString());
			//线上商铺名称
			certifShopDTO.setShopName(businessBaseinfoDTO.getShopsName());
			//获取线上商铺主营分类
			ReBusinessCategoryDTO reBusinessCategoryDTO = reBusinessCategoryToolService
					.getCateIdByBusId(certifShopDTO.getBusinessId().toString());
			if(!CommonUtils.isEmpty(reBusinessCategoryDTO) && !CommonUtils.isEmpty(reBusinessCategoryDTO.getCategoryId())){
				//主营分类
				certifShopDTO.setCateId(Integer.valueOf(reBusinessCategoryDTO.getCategoryId().toString()));
			}
			//pos终端号
			List<ReBusinessPosDTO> posNumList = reBusinessPosToolService.getPostNumByBusId(certifShopDTO.getBusinessId().toString());
			if(posNumList.size() > 0){
				String posNum = "";
				for(ReBusinessPosDTO posNumdto : posNumList){
					posNum += posNumdto.getPosNumber() + ",";
				}
				posNum = posNum.substring(0,posNum.length() - 1);
				certifShopDTO.setPosNo(posNum);
			}
			
			//智能秤（MAC地址）
			List<ReBusinessSteelyardDTO> macList = reBusinessSteelyardToolService.getMacByBusId(certifShopDTO.getBusinessId().toString());
			if(macList.size() > 0){
				String mac = "";
				for(ReBusinessSteelyardDTO macDto : macList){
					mac += macDto.getMacAddr() + ",";
				}
				mac = mac.substring(0, mac.length() - 1);
				certifShopDTO.setMacNo(mac);
			}
			
			//状态(待审核)
			certifShopDTO.setStatus(Constant.shopStatus.SHOP_STATUS_0);
			//更新时间
			//certifShopDTO.setUpdateTime(new Date());
			//更新人
			certifShopDTO.setUpdateUserId(certifShopDTO.getMemberId().toString());
			int res = certifShopToolService.update(certifShopDTO);

			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 结果对象
			resultMap.put("resultObj", res);
			// 编辑提交之后的状态, 0-待认证
			resultMap.put("certifStatus", 0);
			result.setObject(resultMap);
		} catch (Exception e) {
			logger.info("editShopCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	/**
	 * 判断编辑是否有内容改变
	 * @param dto1
	 * @param dto2
	 * @return
	 */
	private static boolean isUpdate(CertifShopDTO dto1,CertifShopDTO dto2){
		if(dto1.getMarketId().equals(dto2.getMarketId()) 
				&& dto1.getAddress().equals(dto2.getAddress())
				&& dto1.getRealShopName().equals(dto2.getRealShopName())
				&& dto1.getOperatorName().equals(dto2.getOperatorName())
				&& dto1.getContractImg().equals(dto2.getContractImg())){
			return false;
		}
		return true;
	}

}
