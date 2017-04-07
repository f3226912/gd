package com.gudeng.commerce.gd.m.controller.finance;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.dto.CreditInfo;
import com.gudeng.commerce.gd.m.enums.ResponseCodeEnum;
import com.gudeng.commerce.gd.m.service.BillDetailToolService;
import com.gudeng.commerce.gd.m.service.CreditToolService;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;

@Controller
@RequestMapping("credit")
public class CreditController extends MBaseController{
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CreditController.class);
	
	@Autowired
	private CreditToolService creditToolService;
	
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	private BillDetailToolService billDetailToolService;
	
	/**
	 * 显示我要贷款页面
	 * 
	 * @param memberId 会员ID
	 */
	@RequestMapping("financial")
	public String financial(HttpServletRequest request, String memberId) throws Exception{
		logger.info("memberId=" + memberId);
		request.setAttribute("memberId", memberId);
		return "H5/credit/financial-serve";
	}
	
	/**
	 * 我要贷款页面
	 */
	@RequestMapping("getInfo")
	@ResponseBody
	public String getInfo(HttpServletRequest request, Long memberId) throws Exception{
		try{
			logger.info("memberId=" + memberId);
			if(memberId == null){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(memberId));
			if (member == null) {
				return getResponseJson(ResponseCodeEnum.ACCOUNT_ERROR);
			}
			Double tradeAmount = billDetailToolService.countTradeAmount(memberId);
			CreditInfo creditInfo = new CreditInfo();
			creditInfo.setTradeAmount(tradeAmount);
			creditInfo.setLevel(getLevel(tradeAmount));
			return getResponseJson(creditInfo);
		}catch(Exception e){
			logger.error("我要贷款:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
	
	/**
	 * 我要贷款页面
	 */
	@RequestMapping("applyCredit")
	@ResponseBody
	public String applyCredit(HttpServletRequest request, Long memberId, String creditQuotaRange) throws Exception{
		try{
			logger.info("memberId=" + memberId + ",creditQuotaRange=" + creditQuotaRange);
			if(memberId == null){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(memberId));
			if (member == null) {
				return getResponseJson(ResponseCodeEnum.ACCOUNT_ERROR);
			}
			if(StringUtils.isBlank(creditQuotaRange)){
				return getResponseJson(ResponseCodeEnum.CREDIT_IS_NULL);
			}
			Double tradeAmount = billDetailToolService.countTradeAmount(memberId);
			
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getByAccountNoCer(member.getAccount());
			FinanceCreditEntity creditEntity = new FinanceCreditEntity();
			creditEntity.setMemberId(memberId);
			creditEntity.setMemberAccount(member.getAccount());
			creditEntity.setMarketId(memberBaseinfoDTO.getMarketId().toString());
			creditEntity.setOrderAmount(tradeAmount);
			creditEntity.setUserStar(Integer.toString(getLevel(tradeAmount)));
			creditEntity.setCreditQuotaRange(creditQuotaRange);
			creditEntity.setCreateUserId(String.valueOf(memberId));
			creditEntity.setCreateTime(new Date());
			creditEntity.setUpdateUserId(String.valueOf(memberId));
			creditEntity.setUpdateTime(new Date());
			creditToolService.saveFinanceCredit(creditEntity);
			return getResponseJson(ResponseCodeEnum.OPERATION_SUCCESS);
		}catch(Exception e){
			logger.error("我要贷款:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
	
	private int getLevel(Double tradeAmount) {
		int level;
		if (tradeAmount < 10000000) {
			level = 0;
		} else if (tradeAmount < 15000000) {
			level = 1;
		} else if (tradeAmount < 20000000) {
			level = 2;
		} else if (tradeAmount < 30000000) {
			level = 3;
		} else if (tradeAmount < 40000000) {
			level = 4;
		} else {
			level = 5;
		}
		return level;
	}
	
}
