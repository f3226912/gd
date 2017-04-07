package com.gudeng.commerce.gd.report.data;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.UserAllTradeDataDTO;
import com.gudeng.commerce.gd.report.dto.UserTradeDataDTO;
import com.gudeng.commerce.gd.report.enm.EOrderStatus;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.TradeService;
import com.gudeng.commerce.gd.report.util.MathUtil;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:39:31
 */
public class TradeDataBuilder implements DataBuilder {
	
	@Autowired
	private TradeService tradeService;
	
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@Override
	public DataDTO getData(DataDBQuery dataQuery) throws ServiceException {
		List<UserAllTradeDataDTO> userAllTradeDatas = tradeService.queryAllTradeInfo(dataQuery);
		
		UserTradeDataDTO rtTradeData = UserTradeDataDTO.generate();
		if (CollectionUtils.isEmpty(userAllTradeDatas)) {
			return rtTradeData;
		}
		long buyerNum = 0L; // 交易买家
		long goodsNum = 0L; // 成交件数
		long orderNum = 0L; // 订单量
		double tradeAmt = 0D; // 交易额
		for (UserAllTradeDataDTO tradeData : userAllTradeDatas) {
			if (EOrderStatus.WAIT_PAY.getCode().equals(tradeData.getOrderStatus())) {
				rtTradeData.setNonPayOrderNum(rtTradeData.getNonPayOrderNum() + tradeData.getOrderNum());
				rtTradeData.setNonPayTradeAmt(MathUtil.add(rtTradeData.getNonPayTradeAmt(), tradeData.getTradeAmt()));
				if (tradeData.getHasGoods() == 0) {
					rtTradeData.setNogNonPayOrderNum(tradeData.getOrderNum());
					rtTradeData.setNogNonPayTradeAmt(tradeData.getTradeAmt());
				}
			} else if (EOrderStatus.PAYED.getCode().equals(tradeData.getOrderStatus())) {
				rtTradeData.setPayedOrderNum(rtTradeData.getPayedOrderNum() + tradeData.getOrderNum());
				rtTradeData.setPayedTradeAmt(MathUtil.add(rtTradeData.getPayedTradeAmt(), tradeData.getTradeAmt()));
				if (tradeData.getHasGoods() == 0) {
					rtTradeData.setNogPayedOrderNum(tradeData.getOrderNum());
					rtTradeData.setNogPayedTradeAmt(tradeData.getTradeAmt());
				}
				buyerNum += tradeData.getBuyerNum();
				goodsNum += tradeData.getGoodsNum();
			} else if (EOrderStatus.INVALID.getCode().equals(tradeData.getOrderStatus())) {
				rtTradeData.setCloseOrderNum(rtTradeData.getCloseOrderNum() + tradeData.getOrderNum());
				rtTradeData.setCloseTradeAmt(MathUtil.add(rtTradeData.getCloseTradeAmt(), tradeData.getTradeAmt()));
				if (tradeData.getHasGoods() == 0) {
					rtTradeData.setNogCloseOrderNum(tradeData.getOrderNum());
					rtTradeData.setNogCloseTradeAmt(tradeData.getTradeAmt());
				}
			}
			orderNum += tradeData.getOrderNum();
			tradeAmt = MathUtil.add(tradeData.getTradeAmt(), tradeAmt);
		}
		rtTradeData.setOrderNum(orderNum);
		rtTradeData.setTradeAmt(tradeAmt);
		rtTradeData.setBuyerNum(buyerNum);
		rtTradeData.setGoodsNum(goodsNum);
		if (rtTradeData.getPayedOrderNum() > 0) {
			rtTradeData.setOrderAvgTradeAmt(MathUtil.div(rtTradeData.getPayedTradeAmt(), rtTradeData.getPayedOrderNum()));
		}
		if (rtTradeData.getBuyerNum() > 0) {
			rtTradeData.setBuyerAvgTradeAmt(MathUtil.div(rtTradeData.getPayedTradeAmt(), rtTradeData.getBuyerNum()));
		}
		
		List<Long> buyerList = tradeService.queryBuyerNum(dataQuery);
		rtTradeData.setBuyerList(buyerList);
		
		// 交易额集合
//		List<TradeResult> tradeResultList = tradeService.queryTradeResultList(dataQuery);
//		if (CollectionUtils.isNotEmpty(tradeResultList)) {
//			rtTradeData.setTradeResultList(tradeResultList);
//		}
		return rtTradeData;
	}

}
