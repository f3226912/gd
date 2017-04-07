package com.gudeng.commerce.gd.m.util;

import java.util.List;

import com.gudeng.commerce.gd.m.dto.CommonFeeAppDTO;
import com.gudeng.commerce.gd.promotion.dto.PromFeeDTO;

public class CountFeeUtil {

	/**
	 * 计算收费费用
	 * @param feeDTO
	 */
	public static void countFee(CommonFeeAppDTO feeDTO) {
		switch(feeDTO.getFeeType()){
		case 1: 
			countPromotionFee(feeDTO);
			break;
		default:
			feeDTO.setBuyerFee(0D);
			feeDTO.setSellerFee(0D);
		}
	}
	
	/**
	 * 计算促销费用
	 * @param feeDTO
	 */
	private static void countPromotionFee(CommonFeeAppDTO feeDTO) {
		@SuppressWarnings("unchecked")
		List<PromFeeDTO> feeRuleList = (List<PromFeeDTO>) feeDTO.getFeeRuleList();
		Double tradeAmt = feeDTO.getTradeAmount();
		Double buyerFee = 0D;
		Double sellerFee = 0D;
		boolean isBuyerFeePaid = feeDTO.isBuyerFeePaid();
		boolean isSellerFeePaid = feeDTO.isSellerFeePaid();
		
		if(feeRuleList != null && feeRuleList.size() > 0){
			
			for(PromFeeDTO feeRule : feeRuleList){
				//手续费来源 1 农批商，2 供应商
				String feeSource = feeRule.getFeeSource();
				switch(feeSource){
					case "1": //计算买家费用
						buyerFee = CountPromFee(buyerFee, tradeAmt, feeRule, isBuyerFeePaid);
						break;
					case "2": //计算卖家费用
						sellerFee = CountPromFee(sellerFee, tradeAmt, feeRule, isSellerFeePaid);
						break;
				}
			}
		}
		
		feeDTO.setBuyerFee(buyerFee);
		feeDTO.setSellerFee(sellerFee);
	}

	/**
	 * 计算促销费用
	 * @param fee
	 * @param tradeAmt
	 * @param feeRule
	 * @param isFeePaid 
	 * @return
	 */
	private static Double CountPromFee(Double promFee, Double tradeAmt, PromFeeDTO feeRule, boolean isFeePaid) {
		//收费类型 0 不收费 1 按活动 2 按订单 3 按成交额比例 4 按成交额阶梯
		String feeType = feeRule.getFeeType();
		//手续费，收费类型为1，2时是金额，为3时是金额百分比，为4时是收费额
		Double fee = feeRule.getFee();
		switch(feeType){
			case "0": 
				break;
			case "1":
				if(!isFeePaid){
					promFee = MathUtil.add(promFee, fee);
				}
				break;
			case "2":
				promFee = MathUtil.add(promFee, fee);
				break;
			case "3":
				Double percentFee = MathUtil.div(MathUtil.mul(tradeAmt, fee), 100D);
				promFee = MathUtil.add(promFee, percentFee);
				break;
			case "4":
				//成交界定额，收费类型为4
				Double orderPayAmt = feeRule.getOrderPayAmt();
				//默认加成交额之内的金额
				promFee = MathUtil.add(promFee, fee);
				//大于的部分按比例收取
				if(tradeAmt.compareTo(orderPayAmt) > 0){
					//手续费比例，收费类型为4
					Double overAmt = MathUtil.sub(tradeAmt, orderPayAmt);
					Double feeScale = feeRule.getFeeScale().doubleValue();
					Double overPercentFee = MathUtil.div(MathUtil.mul(overAmt, feeScale), 100D);
					promFee = MathUtil.add(promFee, overPercentFee);
				}
				break;
			default: 
				break;
		}
		return promFee;
	}
}
