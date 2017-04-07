package com.gudeng.commerce.gd.api.controller.ditui;

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
import com.gudeng.commerce.gd.api.dto.ditui.GiftNstOrderDTO;
import com.gudeng.commerce.gd.api.dto.ditui.GiftOrderDTO;
import com.gudeng.commerce.gd.api.dto.ditui.GrandGiftInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ditui.GrdBaseToolService;
import com.gudeng.commerce.gd.api.service.ditui.GrdGiftDetailToolService;
import com.gudeng.commerce.gd.api.service.ditui.GrdGiftRecordToolService;
import com.gudeng.commerce.gd.api.service.ditui.GrdMemberToolService;
import com.gudeng.commerce.gd.api.service.ditui.GrdProPertenToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.bi.dto.GrdProPertenDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;

/**
 * 地推基础Controller
 * 
 */
@Controller
@RequestMapping("ditui")
public class GroundController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(GroundController.class);
	
	@Autowired
	public GrdGiftRecordToolService grdGiftRecordToolService;
	@Autowired
	public GrdGiftDetailToolService grdGiftDetailToolService;
	@Autowired
	public GrdBaseToolService grdBaseToolService;
	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	public GrdProPertenToolService grdProPertenToolService;
	@Autowired
	private GrdMemberToolService grdMemberToolService;
	
	/**
	 * 地推扫码，决定跳转哪个页面
	 * 
	 * 2016年8月27日 废弃此方法
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param usercollectShopDTO
	 */
	@RequestMapping("/scan")
	public void scan(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String mobile=GSONUtils.getJsonValueStr(jsonStr, "mobile");
			String type=GSONUtils.getJsonValueStr(jsonStr, "type");
			String userId=GSONUtils.getJsonValueStr(jsonStr, "customerId");
			String giftstoreId=GSONUtils.getJsonValueStr(jsonStr, "giftstoreId");
			MemberBaseinfoDTO memberBaseinfoDTO =null;
			if(type==null ||(!type.equals("1") && !type.equals("2"))){
				setEncodeResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
				return;
			}else if("2".equals(type)){
				memberBaseinfoDTO = grdBaseToolService.checkMobile(mobile);
				if(memberBaseinfoDTO.getMemberId().longValue()==-1){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
					return;
				}
				if(memberBaseinfoDTO.getMemberId().longValue()==-2){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
					return;
				}
				if(memberBaseinfoDTO.getMemberId().longValue()==-3){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
				userId=String.valueOf(memberBaseinfoDTO.getMemberId());
			}else if("1".equals(type)){
				if(StringUtils.isEmpty(userId)){
					setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
					return;
				}else{
					memberBaseinfoDTO = memberToolService.getById(userId);
					if(memberBaseinfoDTO==null || memberBaseinfoDTO.getMemberId().longValue()<=0){
						setEncodeResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
						return;
					}
				}
				mobile=memberBaseinfoDTO.getMobile();
			}
			
			
			Map<String,Object> queryMap=new HashMap<>();
			queryMap.put("mobile", mobile);
			queryMap.put("giftstoreId", giftstoreId);
			queryMap.put("status", 0);
			int total =grdGiftRecordToolService.getTotal(queryMap);
//			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, queryMap);
//			List<GrdGiftRecordDTO> giftRecordDTOs=grdBaseToolService.getRecordByMobileAndStatus(mobile,"0",queryMap);
			
			//根据手机号和用户Id，获取可领取礼品的订单记录
			List<GiftOrderDTO> returnGiftOrderDTOs=grdBaseToolService.getGiftOrderList(memberBaseinfoDTO);
			Map resultMap = new HashMap<>();
			resultMap.put("mobile", memberBaseinfoDTO.getMobile());
			resultMap.put("realName", memberBaseinfoDTO.getRealName());
			int countOrder=returnGiftOrderDTOs.size();//order有初始化，默认是空几乎可以获取size
			if(total==0){
				if(countOrder==0){//两者都为空
					resultMap.put("page", 1);
					result.setObject(resultMap);
//					setEncodeResult(result, ErrorCodeEnum.SUCCESS.getValue(), "您无可领取的礼品", request, response);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
					return;
				}else{//只有可领取礼品的订单 
					resultMap.put("page", 2);
					result.setObject(resultMap);
//					setEncodeResult(result, ErrorCodeEnum.SUCCESS.getValue(), "只有可领取礼品的订单，当前界面打开 “礼品发放－选择订单界面”界面。", request, response);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
					return;
				}
			}else{
				if(countOrder==0){//只有可领取礼品记录
					resultMap.put("page", 3);
					result.setObject(resultMap);
//					setEncodeResult(result, ErrorCodeEnum.SUCCESS.getValue(), "只有待领取礼品的记录，当前界面打开“集中发放礼品_确认发放礼品”界面", request, response);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
					return;
				}else{
					resultMap.put("page", 4);
					result.setObject(resultMap);
//					setEncodeResult(result, ErrorCodeEnum.SUCCESS.getValue(), "有待领取礼品的记录，也有可领取礼品的订单，则当前界面打开“发放礼品（选择）”界面", request, response);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
					return;
				}
			}
		} catch (Exception e) {
			logger.error("地推扫描获取跳转页面异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 地推获取:可领取礼品的记录
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/getAbleGiftRecord")
	public void getAbleGiftRecord(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			MemberBaseinfoDTO memberBaseinfoDTO = getMember(request);
			if(memberBaseinfoDTO==null||memberBaseinfoDTO.getMemberId().longValue()<=0){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}			
			String grdUserId=GSONUtils.getJsonValueStr(jsonStr, "grdUserId");
			List<Integer> userType = grdMemberToolService.getUserType(Integer.valueOf(grdUserId));
			boolean nsyUser=userType.contains(1);
			boolean nstUser=userType.contains(2);
			
			Long memberId=memberBaseinfoDTO.getMemberId();
			
			List<GrdGiftRecordDTO> returnRecords=new ArrayList<GrdGiftRecordDTO>() ;
			Map<String,Object> queryMap=new HashMap<>();
//			queryMap.put("mobile", mobile); //改为memberId
			queryMap.put("memberId", memberId); 
			queryMap.put("status", 0);
			if(nsyUser&&nstUser){
//				农商友以及农批商的地推人员，可以查看所有的record记录
			}else if(nsyUser && !nstUser){
//				农商友地推人员，可以查看农商友的record记录
				queryMap.put("type", 1);
			}else if(nstUser&&!nsyUser){
//				农速通地推人员，可以查看农速通的record记录
				queryMap.put("type", 2);
			}
			GrdMemberDTO grdMember = grdMemberToolService.getById(grdUserId);
			queryMap.put("marketId", grdMember.getMarketId());
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, queryMap);
			int total =grdBaseToolService.getRecordTotalByMemberIdAndStatus(queryMap);
			List<GrdGiftRecordDTO> giftRecordDTOs=grdBaseToolService.getRecordByMemberIdAndStatus(queryMap);
//			List<GrdGiftRecordDTO> giftRecordDTOs=grdGiftRecordToolService.getListPage(queryMap);

			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			
			Map map=new HashMap();
			for(GrdGiftRecordDTO grdGiftRecordDTO:giftRecordDTOs){
				map.clear();
				map.put("recordId", grdGiftRecordDTO.getId());
				map.put("type", 1);//仅仅获取礼品
				List<GrdGiftDetailDTO> detailDTOs = grdGiftDetailToolService.getList(map); 
				grdGiftRecordDTO.setDetailDTOs(detailDTOs);
				grdGiftRecordDTO.setCreateTimeStr(DateUtil.toString(grdGiftRecordDTO.getCreateTime(),DateUtil.DATE_FORMAT_DATETIME));
				returnRecords.add(grdGiftRecordDTO);
			}
			pageDTO.setRecordList(giftRecordDTOs);
			Map returnMap=new HashMap();
			returnMap.put("customerId", memberId);
			returnMap.put("mobile", memberBaseinfoDTO.getMobile());
			returnMap.put("realName", memberBaseinfoDTO.getRealName());
			returnMap.put("data", pageDTO);
			
			result.setObject(returnMap);
			
			
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("获取可发奖品订单异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	
	
	
	/**
	 * 地推获取:可领取礼品的订单
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/getAbleGiftOrder")
	public void getAbleGiftOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			MemberBaseinfoDTO memberBaseinfoDTO = getMember(request);
			if(memberBaseinfoDTO==null||memberBaseinfoDTO.getMemberId().longValue()<=0){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			String jsonStr = getParamDecodeStr(request);
			String grdUserId=GSONUtils.getJsonValueStr(jsonStr, "grdUserId");
			if(grdUserId==null||"".equals(grdUserId)){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			GrdMemberDTO grdMember = grdMemberToolService.getById(grdUserId);
			memberBaseinfoDTO.setMarketId(grdMember.getMarketId());
			
			List<GiftOrderDTO> returnGiftOrderDTOs=grdBaseToolService.getGiftOrderList(memberBaseinfoDTO);
			Map returnMap=new HashMap();
			returnMap.put("customerId", memberBaseinfoDTO.getMemberId());
			returnMap.put("mobile", memberBaseinfoDTO.getMobile());
			returnMap.put("realName", memberBaseinfoDTO.getRealName());
			returnMap.put("data", returnGiftOrderDTOs);
			
			result.setObject(returnMap);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("获取可发奖品订单异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	

	/**
	 * 地推获取:可领取礼品的"农速通订单"
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/getAbleGiftNstOrder")
	public void getAbleGiftNstOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			MemberBaseinfoDTO memberBaseinfoDTO = getMember(request);
			if(memberBaseinfoDTO==null||memberBaseinfoDTO.getMemberId().longValue()<=0){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			Long memberId=memberBaseinfoDTO.getMemberId();
			Date date=new Date();
			String startCode = DateUtil.getCode(DateUtil.getDateByMonthCount(date, -3));
			String endCode = DateUtil.getCode(DateUtil.getFirstDayLastTen(date));
			Map map =new HashMap();
			map.put("status", 0);
			map.put("memberId", memberId);
			map.put("startCode", startCode);
			map.put("endCode", endCode);
			List<GiftNstOrderDTO> list=grdBaseToolService.getGiftNstOrderList(map);
			Map returnMap=new HashMap();
			returnMap.put("customerId", memberId);
			returnMap.put("mobile", memberBaseinfoDTO.getMobile());
			returnMap.put("realName", memberBaseinfoDTO.getRealName());
			returnMap.put("data", list);
			result.setObject(returnMap);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("获取可发奖品农速通订单异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	
	/**
	 * 地推获取:礼品列表
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/getGiftList")
	public void getGiftList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String grdUserId=GSONUtils.getJsonValueStr(jsonStr, "grdUserId");
			String sourceType=GSONUtils.getJsonValueStr(jsonStr, "sourceType");
			
			if(StringUtils.isEmpty(grdUserId)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(),"grdUserId不能为空", request, response);
				return;
			}
			if(StringUtils.isEmpty(sourceType)){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(),"sourceType不能为空", request, response);
				return;
			}
			
			Map<String, Object> queryMap=new HashMap();
			queryMap.put("grdUserId", grdUserId);
			queryMap.put("sourceType", sourceType);
			List<GrdGdGiftstoreDTO> list = grdBaseToolService.getStoreByUserAndType(queryMap);
			if(list==null || list.size()==0){
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(),"地推用户无所属团队", request, response);
				return;
			}
			if(checkPageParameters(jsonStr)==-1){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGENUM_IS_NULL, request, response);
				return;
			}
			if(checkPageParameters(jsonStr)==-2){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGESIZE_IS_NULL, request, response);
				return;
			}
			queryMap.clear();
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, queryMap);
			queryMap.put("giftstoreId", list.get(0).getId());
			queryMap.put("status", "0");//只查有效的礼品
			queryMap.put("sourceType", sourceType);//农速通只查农速通的礼品，农批只查农批的礼品
			int total = grdBaseToolService.getTotal2(queryMap); 
			List<GrdGiftDTO> giftList = grdBaseToolService.getGiftListPage2(queryMap);
			List<GrdGiftDTO> giftListRetrun=new ArrayList<GrdGiftDTO>();
			if(giftList!=null){
				for(GrdGiftDTO giftDto:giftList){
					Integer stockTotal=giftDto.getStockTotal();
					Integer noCount=giftDto.getNoCount()==null?0:Integer.valueOf(giftDto.getNoCount());
					if(giftDto.getStockTotal()>0 && giftDto.getStockTotal()>noCount){//当库存大于0，且库存大于待发放数量时，才返回
						giftDto.setStockAvailable(stockTotal-noCount);//设置列表中，可领取的数量为“库存-待领数量”
						giftDto.setStockTotal(stockTotal-noCount);
						giftListRetrun.add(giftDto);
					}
				}
			}
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(giftListRetrun); 
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);

		} catch (Exception e) {
			logger.error("获取礼品列表异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	
	/**
	 * 选中订单后，选中礼品，发放礼品接口
	 * 
	 * 含集中发放和现场发放
	 * 
	 * @param request
	 * @param response
	 * @param 
	 * 
	 */
	@RequestMapping("/grantGift")
	public void grantGift(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			GrandGiftInputDTO grandGiftInputDTO = (GrandGiftInputDTO) GSONUtils.fromJsonToObject(jsonStr, GrandGiftInputDTO.class);
			String[] addResult = grdBaseToolService.grantGift(grandGiftInputDTO).split("#_#");
			if("OK".equals(addResult[0])){
				Map<String, String> map = new HashMap<String, String>();
				map.put("recordId", addResult[1]);
				result.setObject(map );
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
			else{
//				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), addResult[0], request, response);
			}
		} catch (Exception e) {
			logger.error("发放礼品异常",e);
//			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	

	/**
	 * 选中订单后，选中礼品，发放礼品接口
	 * 
	 * 含集中发放和现场发放
	 * 
	 * @param request
	 * @param response
	 * @param 
	 * 
	 */
	@RequestMapping("/grantGiftNst")
	public void grantGiftNst(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			GrandGiftInputDTO grandGiftInputDTO = (GrandGiftInputDTO) GSONUtils.fromJsonToObject(jsonStr, GrandGiftInputDTO.class);
			String[] addResult = grdBaseToolService.grantGiftNst(grandGiftInputDTO).split("#_#");
			if("OK".equals(addResult[0])){
				Map<String, String> map = new HashMap<String, String>();
				map.put("recordId", addResult[1]);
				result.setObject(map );
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
			else{
//				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), addResult[0], request, response);
			}
		} catch (Exception e) {
			logger.error("发放礼品异常",e);
//			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 多个record记录，集中发放
	 * 
	 * */
	@RequestMapping("/centralized")
	public void centralized(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			
			String recordIds=GSONUtils.getJsonValueStr(jsonStr, "recordIds");
			String userId=GSONUtils.getJsonValueStr(jsonStr, "grdUserId");

			if(StringUtils.isEmpty(recordIds)){
				setEncodeResult(result, ErrorCodeEnum.RECORDIDS_IS_NULL, request, response);
				return;
			}
			if(StringUtils.isEmpty(userId)){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			String[] addResult = grdBaseToolService.centralized(recordIds,userId).split("#_#");

			if("OK".equals(addResult[0])){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			} else{
//				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), addResult[0], request, response);
			}
		} catch (Exception e) {
			logger.error("集中发放礼品异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
//			setEncodeResult(result, ErrorCodeEnum.FAIL.getValue(), e.getMessage(), request, response);
		}
	}
	private MemberBaseinfoDTO getMember(HttpServletRequest request) throws Exception{
		String jsonStr = getParamDecodeStr(request);
		String mobile=GSONUtils.getJsonValueStr(jsonStr, "mobile");
		String type=GSONUtils.getJsonValueStr(jsonStr, "type");
		String userId=GSONUtils.getJsonValueStr(jsonStr, "customerId");
		MemberBaseinfoDTO memberBaseinfoDTO =null;
		if(type==null ||(!type.equals("1") && !type.equals("2"))){
			return null;
		}else if("2".equals(type)){
			memberBaseinfoDTO = grdBaseToolService.checkMobile(mobile);
		}else if("1".equals(type)){
			memberBaseinfoDTO = memberToolService.getById(userId);
		}
		return memberBaseinfoDTO;
	}
	
	
}
