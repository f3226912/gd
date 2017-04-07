package com.gudeng.commerce.gd.report.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.TradeResult;
import com.gudeng.commerce.gd.report.dto.UserTradeDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.util.MathUtil;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:59:57
 */
public class TradeDataServiceImpl extends ADataService {

	@Override
	public DataDTO consolidateDataDTO(List<DataDTO> list,DataServiceQuery dataQuery) throws ServiceException {
		UserTradeDataDTO dataDTO = null;
		
		/*
		 * 循环整合数据
		 */
		for (int i = 0; i < list.size(); i++) {
			UserTradeDataDTO tempData = (UserTradeDataDTO)list.get(i);
			if (tempData == null) {
				continue;
			}
			if(dataDTO == null) {
				dataDTO = tempData;
				if (CollectionUtils.isNotEmpty(dataDTO.getBuyerList())) {
					Map<String, Object> buyerMap = dataDTO.getBuyerMap();
					for (Long memberId : dataDTO.getBuyerList()) {
						buyerMap.put(memberId.toString(), memberId);
					}
				}
			} else {
				dataDTO.setOrderNum        (addLong(dataDTO.getOrderNum          () , tempData.getOrderNum         ()));
				dataDTO.setPayedOrderNum    (addLong(dataDTO.getPayedOrderNum    () , tempData.getPayedOrderNum    ()));
				dataDTO.setNonPayOrderNum   (addLong(dataDTO.getNonPayOrderNum   () , tempData.getNonPayOrderNum   ()));
				dataDTO.setCloseOrderNum    (addLong(dataDTO.getCloseOrderNum    () , tempData.getCloseOrderNum    ()));
				dataDTO.setNogPayedOrderNum (addLong(dataDTO.getNogPayedOrderNum () , tempData.getNogPayedOrderNum ()));
				dataDTO.setNogNonPayOrderNum(addLong(dataDTO.getNogNonPayOrderNum() , tempData.getNogNonPayOrderNum()));
				dataDTO.setNogCloseOrderNum (addLong(dataDTO.getNogCloseOrderNum () , tempData.getNogCloseOrderNum ()));
				dataDTO.setTradeAmt        (addDouble(dataDTO.getTradeAmt        () , tempData.getTradeAmt         ()));
				dataDTO.setPayedTradeAmt    (addDouble(dataDTO.getPayedTradeAmt    () , tempData.getPayedTradeAmt    ()));
				dataDTO.setNonPayTradeAmt   (addDouble(dataDTO.getNonPayTradeAmt   () , tempData.getNonPayTradeAmt   ()));
				dataDTO.setCloseTradeAmt    (addDouble(dataDTO.getCloseTradeAmt    () , tempData.getCloseTradeAmt    ()));
				dataDTO.setNogPayedTradeAmt (addDouble(dataDTO.getNogPayedTradeAmt () , tempData.getNogPayedTradeAmt ()));
				dataDTO.setNogNonPayTradeAmt(addDouble(dataDTO.getNogNonPayTradeAmt() , tempData.getNogNonPayTradeAmt()));
				dataDTO.setNogCloseTradeAmt (addDouble(dataDTO.getNogCloseTradeAmt () , tempData.getNogCloseTradeAmt ()));
				dataDTO.setGoodsNum         (addLong(dataDTO.getGoodsNum         () , tempData.getGoodsNum         ()));
				if (dataDTO.getPayedOrderNum() != null && dataDTO.getPayedOrderNum() > 0) {
					dataDTO.setOrderAvgTradeAmt (MathUtil.div(dataDTO.getPayedTradeAmt(), dataDTO.getPayedOrderNum()));
				} else {
					dataDTO.setOrderAvgTradeAmt (0D);
				}
				
				// 交易买家去重复
				if (CollectionUtils.isNotEmpty(tempData.getBuyerList())) {
					Map<String, Object> buyerMap = dataDTO.getBuyerMap();
					List<Long> buyerList = dataDTO.getBuyerList();
					for (Long memberId : tempData.getBuyerList()) {
						if (!buyerMap.containsKey(memberId.toString())) {
							buyerMap.put(memberId.toString(), memberId);
							// 修复本月交易买家统计错误
							buyerList.add(memberId);
						}
					}
				}
				dataDTO.setBuyerNum(Long.valueOf(dataDTO.getBuyerMap().size()));
				
				if (dataDTO.getBuyerNum() != null && dataDTO.getBuyerNum() > 0) {
					dataDTO.setBuyerAvgTradeAmt (MathUtil.div(dataDTO.getPayedTradeAmt(), dataDTO.getBuyerNum()));
				} else {
					dataDTO.setBuyerAvgTradeAmt (0D);
				}
			}
		}
		
		return dataDTO;
	}
	
	public Map<String, TradeResult> getTradeResultMap(List<TradeResult> tradeResults) {
		Map<String, TradeResult> map = new HashMap<String, TradeResult>();
		if (CollectionUtils.isEmpty(tradeResults)) {
			return map;
		}
		for (TradeResult tradeResult : tradeResults) {
			if (map.containsKey(tradeResult.getTime())) {
				TradeResult tempTrade = map.get(tradeResult.getTime());
				tempTrade.setOrderNum(tempTrade.getOrderNum() + tradeResult.getOrderNum());
				tempTrade.setTradeAmt(tempTrade.getTradeAmt() + tradeResult.getTradeAmt());
			} else {
				map.put(tradeResult.getTime(), tradeResult);
			}
		}
		return map;
	}
	
	/**
	 * 计算两个日期相隔天数
	 */
	public static int daysOfTwo(Date fDate, Date oDate) throws ServiceException {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}
	
	public double addDouble(Double num1 , Double num2) {
		return MathUtil.add(num1 == null ? 0 : num1 , num2 == null ? 0 : num2);
	}
	

	public long addLong(Long num1 , Long num2) {
		return (num1 == null ? 0 : num1) + (num2 == null ? 0 : num2);
	}

}
