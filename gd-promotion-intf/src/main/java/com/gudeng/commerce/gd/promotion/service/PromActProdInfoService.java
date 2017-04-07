package com.gudeng.commerce.gd.promotion.service;

import java.util.List;

import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromFeeDTO;
import com.gudeng.commerce.gd.promotion.dto.PromOrderProminfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;

/**
 * 商品对应活动数据操作
 * @author Ailen
 *
 */
public interface PromActProdInfoService {
	
	/**
	 * 获取对应商品的活动信息
	 * @param productId
	 * @return
	 */
	public PromProdInfoDTO getPromProdInfo(Long productId);
	
	/**
	 * 获得基本活动对象
	 * @param actId
	 * @return
	 */
	public PromBaseinfoDTO getProdBaseinfo(Integer actId, Long productId);
	
	/**
	 * 获得对应活动的手续费
	 * @param actId
	 * @return
	 */
	public List<PromFeeDTO> getPromFees(Integer actId);

	/**
	 * 根据订单号获取
	 * 订单活动信息
	 * @param orderNo
	 * @return
	 */
	public PromOrderProminfoDTO getPromOrderInfoByOrderNo(Long orderNo);
}
