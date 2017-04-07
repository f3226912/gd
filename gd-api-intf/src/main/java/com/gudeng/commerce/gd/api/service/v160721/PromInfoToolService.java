package com.gudeng.commerce.gd.api.service.v160721;

import java.util.Map;

import com.gudeng.commerce.gd.api.dto.CommonFeeAppDTO;
import com.gudeng.commerce.gd.promotion.dto.PromOrderProminfoDTO;

public interface PromInfoToolService {

	/**
	 * 获取商品活动信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getActProductInfo(Map<String, Object> map) throws Exception;

	/**
	 * 根据订单号获取活动信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public PromOrderProminfoDTO getPromInfoByOrderNo(Long orderNo) throws Exception;

	/**
	 * 检查按活动收费类型
	 * 是否已经支付费用
	 * @param buyerId
	 * @param sellId
	 * @param actId 
	 * @param feeDTO
	 * @throws Exception
	 */
	public CommonFeeAppDTO getIsFeePaidByAct(Integer buyerId, Integer sellId,
			Integer actId, CommonFeeAppDTO feeDTO) throws Exception;
}
