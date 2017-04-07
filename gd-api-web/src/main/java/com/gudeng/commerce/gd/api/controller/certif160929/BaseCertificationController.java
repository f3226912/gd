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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.CertifBaseParamsBean;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifBaseToolService;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifBaseDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifBaseEntity;

/**
 * 
 */
@Controller
@RequestMapping("v160929/baseCertif")
public class BaseCertificationController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(BaseCertificationController.class);

	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	public ProductToolService productToolService;

	@Autowired
	public CertifBaseToolService certifBaseToolService;

	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public MarketToolService marketToolService;
	
	@Autowired
	public MemberToolService memberToolService ;
	
	@RequestMapping("getStatusCombination")
	public void getStatusCombination(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			Map<String, Object> res = certifBaseToolService.getStatusCombination(params); 
			result.setObject(res);
		} catch (Exception e) {
			logger.info("getStatusCombination with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	
	}
	
	@RequestMapping("queryBaseCertifInfo")
	public void queryBaseCertifInfo(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifBaseParamsBean queryParams = (CertifBaseParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifBaseParamsBean.class);
			
			Integer memberId = queryParams.getMemberId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			CertifBaseDTO res = certifBaseToolService.getOneBySearch(params);
			String marketIds = res.getMarkets();
			if (!CommonUtils.isEmpty(marketIds)){
				params.clear();
				params.put("status", 0);
				/*params.put("marketType", 2);*/
				params.put("marketIdList", StringUtils.commaDelimitedListToSet(marketIds));
				List<MarketDTO> markets = marketToolService.getAllBySearch(params);
				String names = "";
				for(MarketDTO item: markets){
					names += item.getMarketName() + ",";
				}
				names = names.substring(0, names.lastIndexOf(","));
				res.setMarketNames(names);
			}
			String imgHost = gdProperties.getProperties().getProperty("gd.image.server");
			res.setImgHost(imgHost);
			//
			result.setObject(res);
		} catch (Exception e) {
			logger.info("queryBaseCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	
	}
	
	@RequestMapping("saveBaseCertifInfo")
	public void saveBaseCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifBaseParamsBean queryParams = (CertifBaseParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifBaseParamsBean.class);

			// 商铺id
			Integer businessId = queryParams.getBusinessId();
			Integer memberId = queryParams.getMemberId();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			CertifBaseDTO old = certifBaseToolService.getOneBySearch(params);
			if (old != null){
				result.setObject("您已经存在一条基地认证记录了, 请勿重复添加..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			// 主营分类
			Integer cateId = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessType", 0);
			map.put("businessId", businessId);
			map.put("startRow", 0);
			map.put("endRow", 10);
			List<ReBusinessCategoryDTO> cateIds = reBusinessCategoryToolService.getBySearch(map);
			if (CommonUtils.isEmpty(cateIds) || cateIds.size() > 1) {
				result.setObject("请完善商铺主营分类信息，再提交认证");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}else{
				cateId = cateIds.get(0).getCategoryId().intValue();
			}
			
			MemberBaseinfoDTO mdto = memberToolService.getById(String.valueOf(memberId));
			logger.info("CertifBase acount : {} ", mdto.getAccount());
			CertifBaseEntity entity = new CertifBaseEntity();
			Date now = new Date();
			//主营分类
			entity.setCateId(cateId);
			//用户账户
			entity.setAccount(mdto.getAccount());
			//会员Id
			entity.setMemberId(memberId);
			//商铺id
			entity.setBusinessId(businessId);
			
			// 基地名称
			entity.setBaseName(queryParams.getBaseName());
			//所在地区-省
			entity.setProvince(queryParams.getProvince());
			//所在地区-市
			entity.setCity(queryParams.getCity());
			//所在地区-区
			entity.setArea(queryParams.getArea());
			//详细地址
			entity.setAddress(queryParams.getAddress());
			
			//临近市场- ids
			entity.setMarkets(queryParams.getMarkets());
			// 产能
			entity.setUnits(queryParams.getUnits());
			// 营业执照号码
			entity.setBzl(queryParams.getBzl());
			// 营业执照照片
			entity.setBzlPhotoUrl(queryParams.getBzlPhotoUrl());
			//认证状态,0-待审核
			entity.setStatus("0");
			//申请认证时间
			entity.setCommitTime(now);
			entity.setCreateTime(now);
			entity.setCreateUserId(String.valueOf(queryParams.getMemberId()));
			entity.setUpdateTime(now);
			entity.setUpdateUserId(String.valueOf(queryParams.getMemberId()));
			//商铺名称
//			entity.setShopName(queryParams.getShopName());
			Long res = certifBaseToolService.insert(entity);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//结果对象
			resultMap.put("resultObj", res);
			//编辑提交之后的状态, 0-待认证
			resultMap.put("certifStatus", 0);
			result.setObject(resultMap);
		} catch (Exception e) {
			logger.info("saveBaseCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("updateBaseCertifInfo")
	public void updateBaseCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifBaseParamsBean queryParams = (CertifBaseParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifBaseParamsBean.class);

			Integer certifId = queryParams.getId();
			CertifBaseDTO dto = certifBaseToolService.getById(String.valueOf(certifId));
			if (dto == null || "0".equalsIgnoreCase( dto.getStatus())){
				result.setObject("不可编辑待认证的记录..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			CertifBaseDTO entity = new CertifBaseDTO();
/*			// 主营分类
			entity.setCateId(queryParams.getCateId());
			// 商铺id
			entity.setBusinessId(queryParams.getBusinessId());
			//会员账号
			entity.setAccount(queryParams.getAccount());
			entity.setMemberId(queryParams.getMemberId());*/
			
			//认证id-主键
			entity.setId(certifId);
			// 基地名称
			entity.setBaseName(queryParams.getBaseName());
			//地区-省
			entity.setProvince(queryParams.getProvince());
			//地区-市
			entity.setCity(queryParams.getCity());
			//地区-区
			entity.setArea(queryParams.getArea());
			//详细地址
			entity.setAddress(queryParams.getAddress());
			//临近市场
			entity.setMarkets(queryParams.getMarkets());
			// 产能
			entity.setUnits(queryParams.getUnits());
			// 营业执照号码
			entity.setBzl(queryParams.getBzl());
			// 营业执照照片
			entity.setBzlPhotoUrl(queryParams.getBzlPhotoUrl());
//			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(String.valueOf(queryParams.getMemberId()));
			entity.setStatus("0");
			int res = certifBaseToolService.update(entity);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//结果对象
			resultMap.put("resultObj", res);
			//编辑提交之后的状态, 0-待认证
			resultMap.put("certifStatus", 0);
			result.setObject(resultMap);
		} catch (Exception e) {
			logger.info("updateBaseCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

}
