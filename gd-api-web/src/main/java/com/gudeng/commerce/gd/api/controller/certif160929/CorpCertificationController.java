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
import com.gudeng.commerce.gd.api.params.CertifCorpParamsBean;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifCorpToolService;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCorpDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCorpEntity;

/**
 * 
 */
@Controller
@RequestMapping("v160929/corpCertif")
public class CorpCertificationController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CorpCertificationController.class);

	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	public ProductToolService productToolService;

	@Autowired
	public CertifCorpToolService certifCorpToolService;

	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public MarketToolService marketToolService;
	
	@Autowired
	public MemberToolService memberToolService ;
	
	
	@RequestMapping("queryCorpCertifInfo")
	public void queryCorpCertifInfo(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifCorpParamsBean queryParams = (CertifCorpParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifCorpParamsBean.class);
			
			Integer memberId = queryParams.getMemberId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			CertifCorpDTO certifCorpDTO = certifCorpToolService.getOneBySearch(params);
			String marketIds = certifCorpDTO.getMarkets();
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
				certifCorpDTO.setMarketNames(names);
			}
			
			String imgHost = gdProperties.getProperties().getProperty("gd.image.server");
			certifCorpDTO.setImgHost(imgHost);
			
			result.setObject(certifCorpDTO);
		} catch (Exception e) {
			logger.info("queryCorpCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	
	}
	
	@RequestMapping("saveCorpCertifInfo")
	public void saveCorpCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifCorpParamsBean queryParams = (CertifCorpParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifCorpParamsBean.class);

			// 商铺id
			Integer businessId = queryParams.getBusinessId();
			// 用户id
			Integer memberId = queryParams.getMemberId();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			CertifCorpDTO certifCorpDTO = certifCorpToolService.getOneBySearch(params);
			if (certifCorpDTO != null){
				result.setObject("您已经存在一条合作社认证记录了, 请勿重复添加..");
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
			logger.info("CertifCorp acount : {} ", mdto.getAccount());
			CertifCorpEntity entity = new CertifCorpEntity();
			Date now = new Date();
			//主营分类
			entity.setCateId(cateId);
			//用户账户
			entity.setAccount(mdto.getAccount());
			//会员Id
			entity.setMemberId(memberId);
			//商铺id
			entity.setBusinessId(businessId);
			
			// 合作社名称
			entity.setCorpName(queryParams.getCorpName());
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
			Long res = certifCorpToolService.insert(entity);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//结果对象
			resultMap.put("resultObj", res);
			//编辑提交之后的状态, 0-待认证
			resultMap.put("certifStatus", 0);
			result.setObject(resultMap);
		} catch (Exception e) {
			logger.info("saveSpProductCertifInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("updateCorpCertifInfo")
	public void updateCorpCertifInfo(HttpServletRequest request, HttpServletResponse response) {
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			CertifCorpParamsBean queryParams = (CertifCorpParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					CertifCorpParamsBean.class);

			Integer certifId = queryParams.getId();
			CertifCorpDTO dto = certifCorpToolService.getById(String.valueOf(certifId));
			if (dto == null || "0".equalsIgnoreCase( dto.getStatus())){
				result.setObject("不可编辑待认证的记录..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			CertifCorpDTO entity = new CertifCorpDTO();
/*			// 主营分类
			entity.setCateId(queryParams.getCateId());
			// 商铺id
			entity.setBusinessId(queryParams.getBusinessId());
			//会员账号
			entity.setAccount(queryParams.getAccount());
			entity.setMemberId(queryParams.getMemberId());*/
			
			//认证id-主键
			entity.setId(certifId);
			// 合作社名称
			entity.setCorpName(queryParams.getCorpName());
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
			int res = certifCorpToolService.update(entity);
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

}
