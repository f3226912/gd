package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GiftInstoreInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchaseDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchasegiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchaseEntity;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchasegiftEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdPurchaseToolService extends BaseToolService<GrdPurchaseDTO> {
	public Long insert(GrdPurchaseEntity entity) throws Exception;
	
	public int add(GrdPurchaseEntity entity,List<GrdPurchasegiftEntity> giftList) throws Exception;
	
	public List<GrdGdGiftDTO> getGiftByPurchaseNoList(Map<String,Object> map) throws Exception;
	
	public int getGiftByPurchaseNoCount(Map<String,Object> map) throws Exception;
	
	public int edit(GrdPurchaseEntity entity, List<GrdPurchasegiftDTO> giftList) throws Exception;
	
	public List<GiftInstoreInfoDTO> findGiftInstoreInfoList(Map<String,Object> map) throws Exception;
	
	public int findGiftInstoreInfoCount(Map<String,Object> map) throws Exception;
	public List<GrdPurchasegiftDTO> getPurchaseGiftList(Map<String, Object> map) throws Exception;
	public int closeBatch(List<String> list) throws Exception;
	
	public List<GrdPurchaseDTO> getPurchaseByStatusTotal(Map<String, Object> map)throws Exception;
	
	public int getPurchaseBatchTotal(Map<String, Object> map)throws Exception;
	
	public List<GrdPurchaseDTO> getPurchaseBatch(Map<String, Object> map)throws Exception;
	
	public List<GrdPurchaseDTO> queryPurchaseSelect(Map<String, Object> map)throws Exception;
	
	public int queryPurchasegiftListTotal(Map<String, Object> map)throws Exception;
	
	public List<GrdPurchasegiftDTO> queryPurchasegiftList(Map<String, Object> map)throws Exception;
	
}