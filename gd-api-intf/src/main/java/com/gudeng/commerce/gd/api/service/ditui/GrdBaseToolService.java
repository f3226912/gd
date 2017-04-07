package com.gudeng.commerce.gd.api.service.ditui;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.ditui.GiftNstOrderDTO;
import com.gudeng.commerce.gd.api.dto.ditui.GiftOrderDTO;
import com.gudeng.commerce.gd.api.dto.ditui.GrandGiftInputDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;

public interface GrdBaseToolService{

	/**
	 * 根据手机号和memberId
	 * 获取当前可以领取礼品的订单
	 * 
	 * */
	public List<GiftOrderDTO> getGiftOrderList(MemberBaseinfoDTO memberBaseinfoDTO) throws Exception;
	
	/**
	  *
	  *手机号为空，设置memberId=-1
	  *
	  *手机号不符合规则,设置memberId=-2
	  *
	  *用户不存在则,设置memberId=-3
	  *
	 */
	public MemberBaseinfoDTO checkMobile(String mobile) throws Exception;

	
	/**
	 * 将复杂的OrderBaseinfoDTO 转换为 简单的 GiftOrderDTO 共app使用
	 * 
	 * */
	public void translate(List<OrderBaseinfoDTO> orderList, List<GiftOrderDTO> giftOrderDTOs) throws Exception;

	

	/**
	 * 根据手机号和状态查找giftrecord记录
	 * 
	 * status=0,查询已经产生记录，但是未集中领取的记录
	 * status=1，查询已经领取礼品的记录。
	 * 
	 * status不传，则查询当前mobile下的所有的记录
	 * 
	 * */                         
	public List<GrdGiftRecordDTO> getRecordByMemberIdAndStatus(Map queryMap) throws Exception ;
	
	public int getRecordTotalByMemberIdAndStatus(Map queryMap) throws Exception ;
	
	/**
	 * 根据marketId获取当前市场下的所有礼品
	 * @param queryMap 
	 * 
	 * */
	public List<GrdGiftDTO> getGiftList(String marketId, Map queryMap) throws Exception ;

	public int getGiftTotal(String marketId) throws Exception ;
	public int getGiftTotal(Map<String, Object> map) throws Exception ;
	
	public String grantGift(GrandGiftInputDTO grandGiftInputDTO) throws Exception ;
	
	public String grantGiftNst(GrandGiftInputDTO grandGiftInputDTO) throws Exception ;

	public String centralized(String recordIds, String userId)throws Exception ;

	public List<GrdGiftDTO> getGiftListPage(Map<String, Object> queryMap)throws Exception ;
	
	public List<GrdGiftDTO> getGiftListPage2(Map<String, Object> queryMap)throws Exception ;

	public int getTotal2(Map<String, Object> map) throws Exception;
	
	public List<GiftNstOrderDTO> getGiftNstOrderList(Map map) throws Exception;
	
	public List<GrdGdGiftstoreDTO> getStoreByUserAndType(Map<String, Object> map) throws Exception;

	
}