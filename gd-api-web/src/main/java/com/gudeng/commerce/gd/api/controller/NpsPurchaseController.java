package com.gudeng.commerce.gd.api.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
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
import com.gudeng.commerce.gd.api.service.v160929.CertifBaseToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsPurchaseEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 农批商采购Controller
 * 
 * @author cai.x
 *
 */
@Controller
@RequestMapping("npsPurchase")
public class NpsPurchaseController extends GDAPIBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(NpsPurchaseController.class);
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public NpsPurchaseToolService npsPurchaseToolService;
	@Autowired
	public NpsOfferPriceToolService npsOfferPriceToolService;
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	@Autowired
    public CertifBaseToolService certifBaseToolService;
	/**
	 * 获取采购信息值列表
	 */
	@RequestMapping("/getList")
	public void getNpsPurchaseList(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> parsMap = getDecodeMap(request);
			//Map<String, Object> parsMap = new HashMap<String, Object>();
			//String page = request.getParameter("pageNum");
			//String rows = request.getParameter("pageSize");
			String page = (String) parsMap.get("pageNum");
			String rows = (String) parsMap.get("pageSize");
			CommonPageDTO pageDTO = getPageInfo(request, parsMap);
			pageDTO.setPageSize(Integer.valueOf(rows));
			
			parsMap.put("startRow",(Integer.valueOf(page) - 1) * Integer.valueOf(rows));
			parsMap.put("endRow", Integer.valueOf(rows));
			//parsMap.put("status", request.getParameter("status"));
			
			// 总条数
			int total = npsPurchaseToolService.getTotal(parsMap);
			pageDTO.setRecordCount(total);
			pageDTO.setCurrentPage(Integer.valueOf(page));
			pageDTO.initiatePage(total);

			List<NpsPurchaseDTO> npsPurchaseList = npsPurchaseToolService.getList(parsMap);
			for (NpsPurchaseDTO dto : npsPurchaseList) {
				// 查询最低价
				NpsOfferPriceDTO priceDto = npsPurchaseToolService.getPriceById(dto.getId());
				if (priceDto != null) {
					dto.setLowPrice(priceDto.getLowPrice());
					dto.setNewPrice(priceDto.getNewPrice());
				}
				if(null != dto.getUserCancelTime()){
					dto.setEndTime(dto.getUserCancelTime());
				}
				if(null != dto.getUserEndTime()){
					dto.setEndTime(dto.getUserEndTime());
				}
				// 最新价
				dto.setImg(dto.getGoodsImg());
				if(null != dto.getGoodsImg() && !"".equals(dto.getGoodsImg())){
					String imgRootUrl = gdProperties.getProperties().getProperty("gd.image.server");
					dto.setGoodsImg(imgRootUrl + dto.getGoodsImg());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("purchaseId", dto.getId());
				int offerTotal = npsOfferPriceToolService.getOfferPriceTotal(map);
				if ("2".equals(dto.getStatus())) {
					dto.setEndStr("审核已通过");
				} else if ("4".equals(dto.getStatus())) {
					dto.setEndStr("手动撤销");
				} else if ("3".equals(dto.getStatus())) {
					dto.setEndStr(dto.getRejectReason());
				} else if ("6".equals(dto.getStatus()) && offerTotal == 0 && dto.getUserEndTime() == null) {
					dto.setEndStr("供应商未报价");
				} else if ("6".equals(dto.getStatus()) && null != dto.getUserEndTime()) {
					dto.setEndStr("");
				} else if ("6".equals(dto.getStatus()) && null == dto.getUserEndTime()) {
					dto.setEndStr("");
				}else {
					dto.setEndStr("");
				}
				dto.setPriceCount(offerTotal);
				// 如果是待审核的 需要计算预计结束时间：发布时间+采购时效
				if ("1".equals(parsMap.get("status"))) {
					/*Calendar calendar = new GregorianCalendar();
					calendar.setTime(dto.getCreateTime());
					calendar.add(calendar.DATE, dto.getPurchaseCycle() + 1);// 把日期往后增加一天.整数往后推,负数往前移动
					dto.setEndTime(calendar.getTime());*/
					Date d = dto.getCreateTime();
				    Calendar ca = Calendar.getInstance();  
				    ca.setTime(d);
				    ca.add(Calendar.DATE, dto.getPurchaseCycle());// 30为增加的天数，可以改变的  
				    dto.setEndTime(ca.getTime());
				}else if ("2".equals(parsMap.get("status"))) {
					/*Calendar calendar = new GregorianCalendar();
					calendar.setTime(dto.getCreateTime());
					calendar.add(calendar.DATE, dto.getPurchaseCycle() + 1);// 把日期往后增加一天.整数往后推,负数往前移动
					dto.setEndTime(calendar.getTime());*/
					Date d = dto.getReleaseTime();
					Calendar ca = Calendar.getInstance();  
				    ca.setTime(d);
				    ca.add(Calendar.DATE, dto.getPurchaseCycle());// 30为增加的天数，可以改变的  
				    dto.setEndTime(ca.getTime());
			    }
			}
			pageDTO.setRecordList(npsPurchaseList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取采购信息值列表异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 获取采购信息详情
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getInfo")
	public void getNpsPurchaseById(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> parsMap = getDecodeMap(request);
			NpsPurchaseDTO dto = npsPurchaseToolService.getNpsPurchaseById(parsMap);
			parsMap.put("purchaseId", request.getParameter("id"));
			int offerTotal = npsOfferPriceToolService.getOfferPriceTotal(parsMap);
			if (dto != null) {
				dto.setImg(dto.getGoodsImg());
				if(null != dto.getGoodsImg() && !"".equals(dto.getGoodsImg())){
					String imgRootUrl = gdProperties.getProperties().getProperty("gd.image.server");
					dto.setGoodsImg(imgRootUrl + dto.getGoodsImg());
				}
				if ("2".equals(dto.getStatus())) {
					dto.setEndStr("审核已通过");
				} else if ("4".equals(dto.getStatus())) {
					dto.setEndStr("手动撤销");
				} else if ("3".equals(dto.getStatus())) {
					dto.setEndStr(dto.getRejectReason());
				} else if ("6".equals(dto.getStatus()) && offerTotal == 0 && dto.getUserEndTime() == null && dto.getUserCancelTime() == null) {
					dto.setEndStr("供应商未报价");
				} else if ("6".equals(dto.getStatus()) && null != dto.getUserEndTime() && null == dto.getUserCancelTime()) {
					dto.setEndStr("用户结束报价");
				} else if ("6".equals(dto.getStatus()) && null == dto.getUserEndTime() && null != dto.getUserCancelTime()) {
					dto.setEndStr("用户撤回");
				}else {
					dto.setEndStr("");
				}
				if("6".equals(dto.getStatus()) || "4".equals(dto.getStatus())){
					if(null != dto.getUserCancelTime()){
						dto.setEndTime(dto.getUserCancelTime());
					}
					if(null != dto.getUserEndTime()){
						dto.setEndTime(dto.getUserEndTime());
					}
				}else{
					dto.setEndTime(null);
				}
				
			}
			/**
			 * 1 自动到期结束且供应商未报价的显示 ：供应商未报价 2 手动撤销的显示：手动撤销 3 采购时效过期的显示：采购时效已过期 4
			 * 如该信息被运营后台驳回，则显示：已驳回 驳回原因:XXXX；驳回原因全部显示
			 */

			result.setObject(dto);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取采购信息详情异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 添加采购信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addNpsPurchase")
	public void addNpsPurchase(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			NpsPurchaseEntity entity = (NpsPurchaseEntity) getDecryptedObject(
					request, NpsPurchaseEntity.class);
			
			 if (0>entity.getPurchaseCount()||99999999.99<entity.getPurchaseCount()) {
               setEncodeResult(result, ErrorCodeEnum.SIZE_FAIL, request, response);
               return;
             }
			 if (null==entity.getMemberId()) {
	              setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
	              return;
	            }
			 if(null!=entity.getMinPrice()){
			   if(99999999.99 < entity.getMinPrice()||0 > entity.getMinPrice()){
			     setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
                 return;
			   }
			 }
			 if(null!=entity.getMaxPrice()){
               if(99999999.99 < entity.getMaxPrice()||0 > entity.getMaxPrice()){
                 setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
                 return;
               }
             }
			 if(null!=entity.getMaxPrice()&&entity.getMaxPrice()!=null){
               if(entity.getMaxPrice()<entity.getMinPrice()){
                 setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
                 return;
               }
             }
			Map<String, Object> parsMap = new HashMap<String,Object>();
			parsMap.put("memberId", entity.getMemberId());
			// cpstatus;//个人认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
            // comstatus;//企业认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
            // shopstatus;//实体商铺认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	        Map<String, Object> res = certifBaseToolService.getStatusCombination(parsMap); 
	        String cpstatus= (String) res.get("cpstatus");
	        String comstatus= (String)res.get("comstatus");
	        String shopstatus=(String)res.get("shopstatus");
            if(!"1".equals(cpstatus)&&!"1".equals(comstatus)&&!"1".equals(shopstatus)){
              setEncodeResult(result, ErrorCodeEnum.NOT_CERTIFIED, request, response);
              return;
            }
			entity.setStatus(NpsPurchaseStatusEnum.DSH.getCode() + "");
			entity.setCreateTime(new Date());
			/*
			 * Calendar now = Calendar.getInstance();
			 * now.setTime(entity.getCreateTime()); now.set(Calendar.DATE,
			 * now.get(Calendar.DATE) + entity.getPurchaseCycle());
			 * entity.setEndTime(now.getTime());
			 */
			npsPurchaseToolService.insert(entity);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]添加采购信息异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}

	}

	/**
	 * 修改采购信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateNpsPurchase")
	public void updateNpsPurchase(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
		    NpsPurchaseEntity entity = (NpsPurchaseEntity) getDecryptedObject(
                request, NpsPurchaseEntity.class);
		    if (null== entity.getMemberId()) {
              setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
              return;
            }
		    Map<String, Object> parsMap = new HashMap<String,Object>();
            parsMap.put("memberId", entity.getMemberId());
		    parsMap.put("id", entity.getId());
            NpsPurchaseDTO dto = npsPurchaseToolService.getNpsPurchaseById(parsMap);
            if (null == dto) {
                setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
                return;
            }
            if (!dto.getMemberId().equals(entity.getMemberId())) {
                setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
                return;
            }
            if (Integer.parseInt(dto.getStatus()) != NpsPurchaseStatusEnum.YBH
                    .getCode()) {
                setEncodeResult(result, ErrorCodeEnum.NPSPURCHASE_UPDATE_FAIL,
                        request, response);
                return;
            }
		    if (0>entity.getPurchaseCount()||99999999.99<entity.getPurchaseCount()) {
              setEncodeResult(result, ErrorCodeEnum.SIZE_FAIL, request, response);
              return;
            }
		    if(null!=entity.getMinPrice()){
              if(99999999.99<entity.getMinPrice()||0>entity.getMinPrice()){
                setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
                return;
              }
            }
            if(null!=entity.getMaxPrice()){
              if(99999999.99<entity.getMaxPrice()||0>entity.getMaxPrice()){
                setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
                return;
              }
            }
            if(null!=entity.getMaxPrice()&&entity.getMaxPrice()!=null){
              if(entity.getMaxPrice()<entity.getMinPrice()){
                setEncodeResult(result, ErrorCodeEnum.OFFERPRICE_FAIL, request, response);
                return;
              }
            }
		 
			// cpstatus;//个人认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
            // comstatus;//企业认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
            // shopstatus;//实体商铺认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
            Map<String, Object> res = certifBaseToolService.getStatusCombination(parsMap); 
            String cpstatus= (String) res.get("cpstatus");
            String comstatus= (String)res.get("comstatus");
            String shopstatus=(String)res.get("shopstatus");
            if(!"1".equals(cpstatus)&&!"1".equals(comstatus)&&!"1".equals(shopstatus)){
              setEncodeResult(result, ErrorCodeEnum.NOT_CERTIFIED, request, response);
              return;
            }
           
			entity.setStatus(NpsPurchaseStatusEnum.DSH.getCode()+"");
			npsPurchaseToolService.update(entity);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]修改采购信息异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 修改采购信息状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateNpsPurchaseStatus")
	public void updateNpsPurchaseStatus(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = getDecodeMap(request);
			 if ("".equals((String) map.get("memberId"))||null== map.get("memberId")) {
	              setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
	              return;
	            }
			// cpstatus;//个人认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
			// comstatus;//企业认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
			// shopstatus;//实体商铺认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	           Map<String, Object> res = certifBaseToolService.getStatusCombination(map); 
	           String cpstatus= (String) res.get("cpstatus");
	            String comstatus= (String)res.get("comstatus");
	            String shopstatus=(String)res.get("shopstatus");
	            if(!"1".equals(cpstatus)&&!"1".equals(comstatus)&&!"1".equals(shopstatus)){
	              setEncodeResult(result, ErrorCodeEnum.NOT_CERTIFIED, request, response);
	              return;
	            }
			int status = Integer.parseInt((String) map.get("status"));
			NpsPurchaseDTO dto = npsPurchaseToolService.getNpsPurchaseById(map);
			if (null == dto) {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			if (!dto.getMemberId().toString().equals((String) map.get("memberId"))) {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			if (status == NpsPurchaseStatusEnum.YHCX.getCode()) {
				if (Integer.parseInt(dto.getStatus()) == NpsPurchaseStatusEnum.YBH
						.getCode()) {
					map.put("userCancelTime", 1);
					npsPurchaseToolService.updateStatus(map);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request,
							response);
					return;
				} else {
					setEncodeResult(result,
							ErrorCodeEnum.NPSPURCHASE_UPDATE_FAIL, request,
							response);
					return;
				}
			}
			if (status == NpsPurchaseStatusEnum.YJS.getCode()) {
				if (Integer.parseInt(dto.getStatus()) == NpsPurchaseStatusEnum.SHTG
						.getCode()) {
					map.put("userEndTime", 1);
					npsPurchaseToolService.updateStatus(map);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request,
							response);
					return;
				} else {
					setEncodeResult(result,
							ErrorCodeEnum.NPSPURCHASE_UPDATE_FAIL, request,
							response);
					return;
				}
			} else {
				// 不是做用户撤销打操作不能修改状态
				setEncodeResult(result, ErrorCodeEnum.NPSPURCHASE_UPDATE_FAIL,
						request, response);
				return;
			}
		} catch (Exception e) {
			logger.warn("[ERROR]修改采购信息状态异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 删除采购信息状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteNpsPurchase")
	public void deleteNpsPurchase(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = getDecodeMap(request);
			 if ("".equals((String) map.get("memberId"))||null== map.get("memberId")) {
	              setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
	              return;
	            }
	            Map<String, Object> res = certifBaseToolService.getStatusCombination(map); 
			NpsPurchaseDTO dto = npsPurchaseToolService.getNpsPurchaseById(map);
			if (!dto.getMemberId().toString().equals((String) map.get("memberId"))) {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			if ((Integer.parseInt(dto.getStatus()) != NpsPurchaseStatusEnum.YJS.getCode()&&Integer.parseInt(dto.getStatus()) != NpsPurchaseStatusEnum.YHCX.getCode())||dto.getIsDelete()!=0) {
				setEncodeResult(result, ErrorCodeEnum.NPSPURCHASE_UPDATE_FAIL,request, response);
				return;
			}
			npsPurchaseToolService.delete(map);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]修改采购信息状态异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 获取未报价采购信息列表
	 */
	@RequestMapping("/getNoPurchaseList")
	public void getNoPurchaseList(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> parsMap = getDecodeMap(request);
			String page = (String) parsMap.get("pageNum");
			String rows = (String) parsMap.get("pageSize");
		
			/*Map<String, Object> parsMap = new HashMap<String, Object>();
			parsMap.put("userAcc", request.getParameter("userAcc"));
			
			String page = request.getParameter("pageNum");
			String rows = request.getParameter("pageSize");*/
			
			CommonPageDTO pageDTO = getPageInfo(request, parsMap);
			parsMap.put("startRow", (Integer.valueOf(page) - 1) * Integer.valueOf(rows));
			parsMap.put("endRow", Integer.valueOf(rows));
			pageDTO.setPageSize(Integer.valueOf(rows));
			// 总条数
			int total = npsPurchaseToolService.getNoPurchaseListTotal(parsMap);
			pageDTO.setRecordCount(total);
			pageDTO.setCurrentPage(Integer.valueOf(page));
			pageDTO.initiatePage(total);

			List<NpsPurchaseDTO> npsPurchaseList = npsPurchaseToolService.getNoPurchaseList(parsMap);
			for (NpsPurchaseDTO dto : npsPurchaseList) {
				// 剩余天数
				Date date = new Date();// 当前时间
				long startTime = date.getTime();// 当前时间的毫秒数
				long endTime = dto.getEndTime().getTime(); 
				long time = endTime - startTime;
				dto.setSurplusDays((long) Math.ceil((double) time / 1000 / 60 / 60 / 24) + 1);
				parsMap.put("userId", dto.getMemberId());
				BusinessBaseinfoDTO businessBaseinfoDto = businessBaseinfoToolService.getBusinessInfoByUserId(parsMap);
				if(null != businessBaseinfoDto){
					dto.setShopsName(businessBaseinfoDto.getShopsName());
				}else{
					MemberBaseinfoDTO memberDto = memberBaseinfoToolService.getById(dto.getMemberId().toString());
					if(memberDto != null){
						dto.setShopsName(memberDto.getRealName());
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("purchaseId", dto.getId());
				int offerTotal = npsOfferPriceToolService.getOfferPriceTotal(map);
				dto.setPriceCount(offerTotal);
			}
			pageDTO.setRecordList(npsPurchaseList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取采购信息值列表异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 获取已报价采购信息列表
	 */
	@RequestMapping("/getPurchaseList")
	public void getPurchaseList(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> parsMap = getDecodeMap(request);
			String page = (String) parsMap.get("pageNum");
			String rows = (String) parsMap.get("pageSize");
			/*
			 * Map<String, Object> parsMap = new HashMap<String, Object>();
			 * parsMap.put("userAcc", request.getParameter("userAcc"));
			 */

			CommonPageDTO pageDTO = getPageInfo(request, parsMap);
			parsMap.put("startRow", (Integer.valueOf(page) - 1) * Integer.valueOf(rows));
			parsMap.put("endRow", Integer.valueOf(rows));
			pageDTO.setPageSize(Integer.valueOf(rows));
			pageDTO.setPageSize(Integer.valueOf(rows));
			// 总条数
			int total = npsPurchaseToolService.getPurchaseListTotal(parsMap);
			pageDTO.setRecordCount(total);
			pageDTO.setCurrentPage(Integer.valueOf(page));
			pageDTO.initiatePage(total);

			List<NpsPurchaseDTO> npsPurchaseList = npsPurchaseToolService.getPurchaseList(parsMap);
			for (NpsPurchaseDTO dto : npsPurchaseList) {
				// 剩余天数
				if("6".equals(dto.getStatus())){
					dto.setSurplusDays(0);
				}else{
					Date date = new Date();// 当前时间
					long startTime = date.getTime();// 当前时间的毫秒数
					long endTime = dto.getEndTime().getTime();
					long time = endTime - startTime;
					dto.setSurplusDays((long) Math.ceil((double) time / 1000 / 60 / 60 / 24) + 1);
				}
			}
			pageDTO.setRecordList(npsPurchaseList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取采购信息值列表异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 获取已结束采购信息列表
	 */
	@RequestMapping("/getEndPurchaseList")
	public void getEndPurchaseList(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> parsMap = getDecodeMap(request);
			String page = (String) parsMap.get("pageNum");
			String rows = (String) parsMap.get("pageSize");
			/*Map<String, Object> parsMap = new HashMap<String, Object>();
			parsMap.put("userAcc", request.getParameter("userAcc"));*/
			 

			CommonPageDTO pageDTO = getPageInfo(request, parsMap);
			parsMap.put("startRow", (Integer.valueOf(page) - 1) * Integer.valueOf(rows));
			parsMap.put("endRow", Integer.valueOf(rows));
			pageDTO.setPageSize(Integer.valueOf(rows));
			// 总条数
			int total = npsPurchaseToolService.getEndPurchaseListTotal(parsMap);
			pageDTO.setRecordCount(total);
			pageDTO.setCurrentPage(Integer.valueOf(page));
			pageDTO.initiatePage(total);

			List<NpsPurchaseDTO> npsPurchaseList = npsPurchaseToolService.getEndPurchaseList(parsMap);
			for (NpsPurchaseDTO dto : npsPurchaseList) {
				if ("6".equals(dto.getStatus())) {
					dto.setEndStr("报价已截止");
				}
				if(null != dto.getUserCancelTime()){
					dto.setEndTime(dto.getUserCancelTime());
				}
				if(null != dto.getUserEndTime()){
					dto.setEndTime(dto.getUserEndTime());
				}
			}
			pageDTO.setRecordList(npsPurchaseList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取采购信息值列表异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
