package com.gudeng.commerce.gd.api.service.ditui;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftDetailEntity;

public interface GrdGiftDetailToolService extends BaseToolService<GrdGiftDetailDTO> {

	public Long insert(GrdGiftDetailEntity entity) throws Exception;
	
	
	/**
	 * 根据用户手机号，和type获取礼品详情
	 * 
	 * type=1 获取礼品详情。
	 * type=2 获取订单详情
	 * type=3 获取首次注册详情。
	 * type=0 获取 订单详情+首次注册详情
	 * type 为空或者null，获取当前用户的所有详情
	*/
	public List<GrdGiftDetailDTO> getDetailByMobileAndType(String mobile,String type) throws Exception ;
	
	/**
	 * 分页查询我的促成订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<GrdGiftDetailDTO> queryCreateOrderByConditionPage(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询促成订单的总条数  和总交易金额
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public GrdGiftDetailDTO getTotalByUserId(Map<String, Object> map) throws Exception;
	
	

}