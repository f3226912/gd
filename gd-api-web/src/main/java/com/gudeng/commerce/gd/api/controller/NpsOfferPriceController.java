package com.gudeng.commerce.gd.api.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NpsPurchaseStatusEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.NpsOfferPriceToolService;
import com.gudeng.commerce.gd.api.service.NpsPurchaseToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 供应商报价Controller
 * 
 * @author cai.x
 *
 */
@Controller
@RequestMapping("npsOfferPrice")
public class NpsOfferPriceController extends GDAPIBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(NpsOfferPriceController.class);

	@Autowired
	public NpsOfferPriceToolService npsOfferPriceToolService;
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public NpsPurchaseToolService npsPurchaseToolService;
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	/**
	 * 获取报价信息详情
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getInfo")
	public void getOfferPriceInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			/*
			 * Map<String, Object> parsMap = new HashMap<String, Object>();
			 * parsMap.put("purchaseId", request.getParameter("purchaseId"));
			 * parsMap.put("userAcc", request.getParameter("userAcc"));
			 */
			Map<String, Object> parsMap = getDecodeMap(request);
			NpsOfferPriceDTO dto = npsOfferPriceToolService.getOfferPriceId(parsMap);
			if(dto != null){
				dto.setOfferPrice(new BigDecimal(dto.getOfferPriceStr()));
			}
			
			result.setObject(dto);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取报价信息详情异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

  /**
   * 添加报价信息
   * 
   * @param request
   * @param response
   */
  @RequestMapping("/addOfferPrice")
  public void addNpsPurchase(HttpServletRequest request, HttpServletResponse response) {
    ObjectResult result = new ObjectResult();
    try {
      NpsOfferPriceEntity entity =
          (NpsOfferPriceEntity) getDecryptedObject(request, NpsOfferPriceEntity.class);
      if (npsOfferPriceToolService.getUserAndOfferPriceCount(entity) > 0) {
        setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_COUNT_FAIL, request, response);
        return;
      }
      if (null == entity.getUserAcc()) {
        setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
        return;
      }
      if (null != entity.getRemark()) {
        if (entity.getRemark().length() > 200) {
          setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_REMARK_FAIL, request, response);
          return;
        }
      }
      if (entity.getOfferPrice().compareTo(BigDecimal.ZERO)==-1) {
        setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
        return;
      }//吐糟下：奇葩的需求 （最多允许输入8位数字和小数点；） 的产品确认过就是长度！！ (--!)
      if ((entity.getOfferPrice()+"").length()>8) {
        setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
        return;
      }
      Map<String, Object> parsMap = new HashMap<String, Object>();
      parsMap.put("id", entity.getPurchaseId());
      NpsPurchaseDTO dto = npsPurchaseToolService.getNpsPurchaseById(parsMap);
      if (null == dto) {
        setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
        return;
      }
      if (Integer.parseInt(dto.getStatus()) != NpsPurchaseStatusEnum.SHTG.getCode()) {
        setEncodeResult(result, ErrorCodeEnum.NPSPURCHASE_UPDATE_FAIL, request, response);
        return;
      }
      entity.setGoodsName(dto.getGoodsName());
      entity.setOfferTime(new Date());
      String str=entity.getOfferPrice().toString();
      entity.setOfferPriceStr(str);
      npsOfferPriceToolService.insert(entity);
      setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
    } catch (Exception e) {
      logger.warn("[ERROR]添加报价信息：", e);
      setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
    }

	}

	/**
	 * 获取报价列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getList")
	public void getOfferPriceList(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> parsMap = getDecodeMap(request);
			/*
			 * Map<String, Object> parsMap = new HashMap<String, Object>();
			 * parsMap.put("purchaseId", request.getParameter("purchaseId"));
			 * parsMap.put("priceSort", request.getParameter("priceSort"));
			 * parsMap.put("cityId", request.getParameter("cityId"));
			 * parsMap.put("areaId", request.getParameter("areaId"));
			 */
			String page = (String) parsMap.get("pageNum");
			String rows = (String) parsMap.get("pageSize");
			CommonPageDTO pageDTO = getPageInfo(request, parsMap);
			pageDTO.setPageSize(Integer.valueOf(rows));
			parsMap.put("startRow",(Integer.valueOf(page) - 1) * Integer.valueOf(rows));
			parsMap.put("endRow", Integer.valueOf(rows));
			// 总条数
			int total = npsOfferPriceToolService.getOfferPriceTotal(parsMap);
			pageDTO.setRecordCount(total);
			pageDTO.setCurrentPage(Integer.valueOf(page));
			pageDTO.initiatePage(total);
			List<NpsOfferPriceDTO> list = npsOfferPriceToolService.getOfferPriceList(parsMap);
			if (null != list && list.size() != 0) {
				//Map<String, Object> map = new HashMap<String, Object>();
				//map.put("id", parsMap.get("purchaseId"));
				//NpsPurchaseDTO purchaseDto = npsPurchaseToolService.getNpsPurchaseById(map);
				
				for (NpsOfferPriceDTO dto : list) {
					Integer memberId = dto.getUserAcc();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", memberId);
					BusinessBaseinfoDTO businessBaseinfoDto = businessBaseinfoToolService.getBusinessInfoByUserId(map);
					if (businessBaseinfoDto != null) {
						dto.setBusinessId(businessBaseinfoDto.getBusinessId());
						dto.setRealName(businessBaseinfoDto.getContact());
						dto.setAreaName(businessBaseinfoDto.getAreaName());
						dto.setProvinceName(businessBaseinfoDto.getProvinceName());
						dto.setCityName(businessBaseinfoDto.getCityName());
						dto.setMemberGrade(Integer.valueOf(businessBaseinfoDto.getMemberGrade()));
					}
					if(dto != null){
						dto.setOfferPrice(new BigDecimal(dto.getOfferPriceStr()));
					}
				}
			}
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取报价信息详情异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

  	/**
	 * 修改报价信息 offerPrice,remark,id
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateOfferPrice")
	public void updateOfferPrice(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			NpsOfferPriceEntity entity = (NpsOfferPriceEntity) getDecryptedObject(
					request, NpsOfferPriceEntity.class);
			Map<String, Object> parsMap = new HashMap<String, Object>();
			parsMap.put("id", entity.getId());
			NpsOfferPriceDTO pDto = npsOfferPriceToolService.getOfferPriceId(parsMap);
			if (!pDto.getUserAcc().equals(entity.getUserAcc())) {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			if (null != entity.getRemark()) {
				if (entity.getRemark().length() > 200) {
					setEncodeResult(result,
							ErrorCodeEnum.OFFERPRICE_REMARK_FAIL, request,
							response);
					return;
				}
			}
			 if (entity.getOfferPrice().compareTo(BigDecimal.ZERO)==-1) {
		        setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
		        return;
		      }
		      if ((entity.getOfferPrice()+"").length()>8) {
		        setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
		        return;
		      }
			parsMap.put("id", entity.getPurchaseId());
			NpsPurchaseDTO dto = npsPurchaseToolService
					.getNpsPurchaseById(parsMap);
			if (null == dto) {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			if (Integer.parseInt(dto.getStatus()) != NpsPurchaseStatusEnum.SHTG
					.getCode()) {
				setEncodeResult(result, ErrorCodeEnum.NPSPURCHASE_UPDATE_FAIL,
						request, response);
				return;
			}
			parsMap.put("id", entity.getId());
			parsMap.put("offerPrice", entity.getOfferPrice()+"");
			parsMap.put("remark", entity.getRemark());
			entity.setOfferTime(new Date());
			npsOfferPriceToolService.updateInfo(parsMap);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]添加报价信息：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}

	}

	/**
	 * 获取交易详情
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getTradeInfo")
	public void getTradeInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> parsMap = getDecodeMap(request);
			/*
			 * Map<String, Object> parsMap = new HashMap<String, Object>();
			 * parsMap.put("purchaseId", request.getParameter("purchaseId"));
			 * parsMap.put("userAcc", request.getParameter("userAcc"));
			 */
			// 报价信息
			Map<String, Object> priceMap = new HashMap<String, Object>();
			priceMap.put("purchaseId",  parsMap.get("purchaseId"));
			priceMap.put("userAcc",  parsMap.get("userAcc"));
			NpsOfferPriceDTO offerPriceDto = npsOfferPriceToolService.getOfferPriceId(priceMap);
			if(offerPriceDto != null){
				offerPriceDto.setOfferPrice(new BigDecimal(offerPriceDto.getOfferPriceStr()));
			}
			resultMap.put("offerPrice", offerPriceDto);
			// 采购信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", parsMap.get("purchaseId"));
			NpsPurchaseDTO purchaseDto = npsPurchaseToolService.getNpsPurchaseById(map);
			if (purchaseDto != null) {
				purchaseDto.setImg(purchaseDto.getGoodsImg());
				if(null != purchaseDto.getGoodsImg() && !"".equals(purchaseDto.getGoodsImg())){
					String imgRootUrl = gdProperties.getProperties().getProperty("gd.image.server");
					purchaseDto.setGoodsImg(imgRootUrl + purchaseDto.getGoodsImg());
				}
				if("6".equals(purchaseDto.getStatus())){
					purchaseDto.setSurplusDays(0);
				}else{
					// 剩余天数
					Date date = new Date();// 当前时间
					long startTime = date.getTime();// 当前时间的毫秒数
					long endTime = purchaseDto.getEndTime().getTime();
					long time = endTime - startTime;
					purchaseDto.setSurplusDays((long) Math.ceil((double) time / 1000 / 60 / 60 / 24) + 1);
				}
			}
			//更新浏览次数 
			try {
				npsPurchaseToolService.updateSeeCount(map);
			} catch (Exception e) {
				// TODO: handle exception
			}
			Integer memberId = null;
			if (purchaseDto != null) {
				memberId = purchaseDto.getMemberId();
			}
			resultMap.put("purchase", purchaseDto);
			// 商铺信息
			map.put("userId", memberId);
			BusinessBaseinfoDTO businessBaseinfoDto = businessBaseinfoToolService.getBusinessInfoByUserId(map);
			if(null == businessBaseinfoDto){
				MemberBaseinfoDTO memberDto = memberBaseinfoToolService.getById(memberId.toString());
				businessBaseinfoDto =  new BusinessBaseinfoDTO();
				businessBaseinfoDto.setBusinessId(null);
				if(memberDto != null){
					businessBaseinfoDto.setContact(memberDto.getRealName());
					businessBaseinfoDto.setMobile(memberDto.getMobile());
					//businessBaseinfoDto.setShopsName(memberDto.getRealName());
				}
			}
			resultMap.put("businessBaseinfo", businessBaseinfoDto);
			result.setObject(resultMap);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取报价信息详情异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 查询是否为新春特惠时间段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/isActivity")
	public void isActivity(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Date activity = new SimpleDateFormat("yyyy-MM-dd")
					.parse("2017-02-28");
			String nowDateTemp = new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date());
			Date nowDate = new SimpleDateFormat("yyyy-MM-dd")
					.parse(nowDateTemp);
			if (activity.getTime() >= nowDate.getTime()) {
				map.put("isActivity", true);
			} else if (activity.getTime() < nowDate.getTime()) {
				System.out.println("优惠活动已结束 ");
				map.put("isActivity", false);
			}
			result.setObject(map);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]添加报价信息：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}

	}
}
