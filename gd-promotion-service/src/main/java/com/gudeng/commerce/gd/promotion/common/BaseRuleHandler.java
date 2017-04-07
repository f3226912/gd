package com.gudeng.commerce.gd.promotion.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityInfoRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public abstract class BaseRuleHandler {
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(BaseRuleHandler.class);
	
	private BaseRuleHandler nextHandler;

	public BaseRuleHandler getNextHandler() {
		return nextHandler;
	}


	public void setNextHandler(BaseRuleHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public abstract void doHandler(GdOrderActivityQueryDTO queryDTO, 
			GdOrderActivityDetailDTO actInfo, GdActivityRedisDTO activityRedisDTO, 
			boolean isBuyer) throws Exception;


	public abstract void doHandler(GdProductActInfoDTO productInfo,
			GdOrderActivityDetailDTO productActInfo, GdActivityRedisDTO activityRedisDTO, 
			GdOrderActivityQueryDTO queryDTO, boolean isBuyer) throws Exception;
	
	public boolean isActEnd(GdActActivityDTO actInfo) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date dt1 = df.parse(actInfo.getEndTime());
            Date dt2 = new Date();
            logger.info("Check out date result: Act id: " + actInfo.getId() + ", result:" + (dt1.compareTo(dt2) > 0));
        return dt1.compareTo(dt2) < 0;
	}
	
	public Integer checkProductActInfo(GdOrderActivityQueryDTO queryDTO, GdActivityInfoRedisDTO actInfoDTO, 
			GdProductActInfoDTO productInfo, int actType, boolean isBuyer) throws Exception{
		Integer actId = null;
		if(actInfoDTO != null){
			Integer productActId = null;
			//商品是否已经有活动
			if(productInfo.getActInfo() != null && queryDTO.isOrdered()){
				//活动类型actType（1刷卡补贴，2市场，3平台，4预付款违约金，5物流）
				productActId = getActIdByType(productInfo.getActInfo().getProductActInfo().get(productInfo.getProductId()), actType);
				
				if(actType != 1){
					return productActId;
				}
			}
			//没有活动则查找最新的活动
			if(productActId == null && actInfoDTO.getProductActMap() != null){
				productActId = actInfoDTO.getProductActMap().get(productInfo.getProductId());
			}
			//没有最新活动则查找有无市场类型活动
			if(productActId == null && actInfoDTO.getNoLimitSellerActIdMap() != null){
				productActId = actInfoDTO.getNoLimitSellerActIdMap().get(queryDTO.getMarketId());
			}
			
			if(isBuyer){
				List<Integer> buyerActIdList = new ArrayList<>();
				//是否已存在限制买家的活动
				if(actInfoDTO.getBuyerActMap()!=null 
						&& actInfoDTO.getBuyerActMap().containsKey(queryDTO.getBuyerId())){
					logger.info("Has limit buyer act infos: " + actInfoDTO.getBuyerActMap().get(queryDTO.getBuyerId()));
					buyerActIdList.addAll(actInfoDTO.getBuyerActMap().get(queryDTO.getBuyerId()));
				}
				
				//是否已存在不限制买家的活动
				if(actInfoDTO.getNoLimitBuyerActIdMap() != null){
					List<Integer> noLimitBuyerActIdList = actInfoDTO.getNoLimitBuyerActIdMap().get(queryDTO.getMarketId());
					if(noLimitBuyerActIdList != null && noLimitBuyerActIdList.size() > 0){
						buyerActIdList.addAll(noLimitBuyerActIdList);
						logger.info("No limit buyer act id: " + noLimitBuyerActIdList);
					}
				}
				
				//判断买家是否有活动
				if(productActId != null && buyerActIdList.size() > 0){
					for(Integer id : buyerActIdList){
						if(id.intValue() == productActId.intValue()){
							actId = productActId;
						}
					}
				}
			}
			
			if(productActId != null && !isBuyer){
				//判断卖家活动是否有指定买家
				if(actInfoDTO.getActBuyerMap() != null 
						&& actInfoDTO.getActBuyerMap().get(productActId) != null
						&& actInfoDTO.getActBuyerMap().get(productActId).size() > 0){
					logger.info("Limit buyer ids: " + actInfoDTO.getActBuyerMap().get(productActId));
					if(queryDTO.getBuyerId() != null){
						for(Integer buyerId : actInfoDTO.getActBuyerMap().get(productActId)){
							if(buyerId.intValue() == queryDTO.getBuyerId().intValue()){
								actId = productActId;
								break;
							}
						}
					}
				}else{
					actId = productActId;
				}
			}
			
			//判断活动是否过期(未下单)
			if(actId != null && !queryDTO.isOrdered() && actInfoDTO.getActInfoMap() != null 
					&& isActEnd(actInfoDTO.getActInfoMap().get(productActId))){
				logger.info("Product act is already outdate, actId: " + productActId);
				actId = null;
			}
		}
		return actId;
	}
	
	public Integer checkBusinessActInfo(GdOrderActivityQueryDTO queryDTO, GdActivityInfoRedisDTO actInfoDTO, 
			int actType, boolean isBuyer) throws Exception{
		Integer actId = null;
		if(actInfoDTO != null){
			//是否已有商铺活动
			Integer businessActId = null;
			
			if(queryDTO.getActIdList() != null && queryDTO.getActIdList().size() > 0){
				businessActId = getActIdByType(queryDTO.getActIdList(), actType);
				return businessActId;
			}
			
			if(actInfoDTO.getBusinessActMap() != null){
				businessActId = actInfoDTO.getBusinessActMap().get(queryDTO.getBusinessId());
			}
			
			//没有商铺活动则查找有无市场类型活动
			if(businessActId == null && actInfoDTO.getNoLimitSellerActIdMap() != null ){
				businessActId = actInfoDTO.getNoLimitSellerActIdMap().get(queryDTO.getMarketId());
			}
			
			if(businessActId != null && !isBuyer){
				//判断卖家活动是否有指定买家
				if(actInfoDTO.getActBuyerMap() != null 
						&& actInfoDTO.getActBuyerMap().get(businessActId) != null
						&& actInfoDTO.getActBuyerMap().get(businessActId).size() > 0){
					logger.info("Limit buyer ids: " + actInfoDTO.getActBuyerMap().get(businessActId));
					if(queryDTO.getBuyerId() != null){
						for(Integer buyerId : actInfoDTO.getActBuyerMap().get(businessActId)){
							if(buyerId.intValue() == queryDTO.getBuyerId().intValue()){
								actId = businessActId;
								break;
							}
						}
					}
				}else{
					actId = businessActId;
				}
			}
			
			//判断活动是否支持逆向 0不支持逆向刷卡，1支持
			if(actId != null && "0".equals(actInfoDTO.getActInfoMap().get(actId).getIsReverse())){
				logger.info("Not support reverse order, actId:  " + businessActId);
				actId = null;
			}
			
			//判断活动是否过期(未下单)
			if(actId != null && !queryDTO.isOrdered() && actInfoDTO.getActInfoMap() != null 
					&& isActEnd(actInfoDTO.getActInfoMap().get(businessActId))){
				logger.info("Business act is already outdate, actId:  " + businessActId);
				actId = null;
			}
		}
		return actId;
	}
	
	public Integer getActIdByType(List<GdOrderActivityDTO> list, int type) {
		if(list != null && list.size() > 0){
			for(GdOrderActivityDTO actInfo : list){
				if(actInfo.getActType() != null && actInfo.getActType().intValue() == type){
					return actInfo.getActId();
				}
			}
		}
		return null;
	}
	
	public void setProductActInfo(GdOrderActivityDetailDTO productActInfo,
			int productId, int actId, int actType) {
		//活动类型（1刷卡补贴，2市场，3平台，4预付款违约金，5物流）
		GdOrderActivityDTO actInfo = new GdOrderActivityDTO();
		actInfo.setActId(actId);
		actInfo.setActType(actType);
		List<GdOrderActivityDTO> list = productActInfo.getProductActInfo().get(productId);
		if(list == null){
			list = new ArrayList<>();
			productActInfo.getProductActInfo().put(productId, list);
		}
		list.add(actInfo);
	}
}
