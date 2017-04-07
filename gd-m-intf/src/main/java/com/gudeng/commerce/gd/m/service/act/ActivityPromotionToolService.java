package com.gudeng.commerce.gd.m.service.act;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.PictureRefDTO;
import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromMarketDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;

public interface ActivityPromotionToolService {
	/**
	 * 获取促销活动列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	 List<PromBaseinfoDTO> queryPromoteActivitys(Map<String,Object> map) throws Exception;
	 /**
	  * 获取促销活动总条数
	  * @param map
	  * @return
	  * @throws Exception
	  */
	 int getTotal(Map<String,Object> map) throws Exception;
	 /**
	  * 获取单个促销活动图片
	  * @param promBaseinfoId
	  * @return
	  * @throws Exception
	  */
	 List<PictureRefDTO> getPictures(int promBaseinfoId)throws Exception;
	 /**
	  * 获取单个促销活动详情
	  * @param map
	  * @return
	  * @throws Exception
	  */
	 PromBaseinfoDTO getDetailActivity(Map<String,Object> map) throws Exception;
	 /**
	  * 获取用户可以参加活动商品
	  * @param paramMap
	  * @return
	 * @throws Exception 
	  */
	 List<ProductBaseinfoDTO> getUserProducts(Map<String, Object> paramMap) throws Exception;
	 /**
	  * 获取单个用户商品列表
	  * @param paramMap
	  * @return
	 * @throws Exception 
	  */
	 int getProductTotalByUser(Map<String, Object> paramMap) throws Exception;
	 /**
	  * 供应商申请活动（即将商品添加到活动）
	  * @param paramMap
	 * @throws Exception 
	  */
	 int addActivityProducts(Map<String, Object> paramMap) throws Exception;
	 /**
	  * 获取参加某个活动的商品个数
	  * @param paramMap
	  * @return
	 * @throws Exception 
	  */
	 int getProductTotalByParticipants(Map<String, Object> paramMap) throws Exception;
	 /**
	  * 获取参加某个活动的商品列表
	  * @param paramMap
	  * @return
	 * @throws Exception 
	  */
	 List<ProductBaseinfoDTO> getUserParticipantsProducts(Map<String, Object> paramMap) throws Exception;
	 /**
	  * 获取活动市场
	  * @param actId
	  * @return
	 * @throws Exception 
	  */
	 List<PromMarketDTO> getMarkets(Integer actId) throws Exception;
	 /**
	  * 取消某个活动
	  * @param paramMap
	  * @return
	 * @throws Exception 
	  */
	 int cancelPromotionActivity(Map<String, Object> paramMap) throws Exception;
}
