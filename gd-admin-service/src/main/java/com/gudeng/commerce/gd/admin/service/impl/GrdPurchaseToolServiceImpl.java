package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdPurchaseToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GiftInstoreInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchaseDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchasegiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchaseEntity;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchasegiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdPurchaseService;

public class GrdPurchaseToolServiceImpl implements GrdPurchaseToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdPurchaseService grdPurchaseService;

	protected GrdPurchaseService getHessianGrdPurchaseService() throws MalformedURLException {
		String url = gdProperties.getGrdPurchaseServiceUrl();
		if (grdPurchaseService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdPurchaseService = (GrdPurchaseService) factory.create(GrdPurchaseService.class, url);
		}
		return grdPurchaseService;
	}

	@Override
	public GrdPurchaseDTO getById(String id) throws Exception {
		return getHessianGrdPurchaseService().getById(id);
	}

	@Override
	public List<GrdPurchaseDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdPurchaseService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdPurchaseService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdPurchaseService().deleteBatch(list);
	}

	@Override
	public int update(GrdPurchaseDTO t) throws Exception {
		return getHessianGrdPurchaseService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdPurchaseService().getTotal(map);
	}

	@Override
	public Long insert(GrdPurchaseEntity entity) throws Exception {
		return getHessianGrdPurchaseService().insert(entity);
	}

	@Override
	public List<GrdPurchaseDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdPurchaseService().getListPage(map);
	}

	@Override
	public List<GrdPurchaseDTO> getPurchaseByStatusTotal(Map<String, Object> map)
			throws Exception {
		return getHessianGrdPurchaseService().getPurchaseByStatusTotal(map);
	}

	@Override
	public int getPurchaseBatchTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdPurchaseService().getPurchaseBatchTotal(map);
	}

	@Override
	public List<GrdPurchaseDTO> getPurchaseBatch(Map<String, Object> map)
			throws Exception {
		return getHessianGrdPurchaseService().getPurchaseBatch(map);
	}

	@Override
	public List<GrdPurchaseDTO> queryPurchaseSelect(Map<String, Object> map)
			throws Exception {
		return getHessianGrdPurchaseService().queryPurchaseSelect(map);
	}

	@Override
	public int queryPurchasegiftListTotal(Map<String, Object> map)
			throws Exception {
		return getHessianGrdPurchaseService().queryPurchasegiftListTotal(map);
	}

	@Override
	public List<GrdPurchasegiftDTO> queryPurchasegiftList(Map<String, Object> map)
			throws Exception {
		return getHessianGrdPurchaseService().queryPurchasegiftList(map);
	}

	
	@Override
	public List<GrdPurchasegiftDTO> getPurchaseGiftList(Map<String, Object> map) throws Exception{
		return getHessianGrdPurchaseService().getPurchaseGiftList(map);
	}
	
	@Override
	public int closeBatch(List<String> list) throws Exception {
		return getHessianGrdPurchaseService().closeBatch(list);
	}

	@Override
	public int add(GrdPurchaseEntity entity, List<GrdPurchasegiftEntity> giftList) throws Exception {
		return getHessianGrdPurchaseService().add(entity,giftList);
	}
	
	@Override
	public List<GrdGdGiftDTO> getGiftByPurchaseNoList(Map<String,Object> map) throws Exception {
		return getHessianGrdPurchaseService().getGiftByPurchaseNoList(map);
	}
	@Override
	public int getGiftByPurchaseNoCount(Map<String,Object> map) throws Exception {
		return getHessianGrdPurchaseService().getGiftByPurchaseNoCount(map);
	}
	
	@Override
	public int edit(GrdPurchaseEntity entity, List<GrdPurchasegiftDTO> giftList) throws Exception {
		return getHessianGrdPurchaseService().edit(entity,giftList);
	}
	
	@Override
	public List<GiftInstoreInfoDTO> findGiftInstoreInfoList(Map<String,Object> map) throws Exception {
		return getHessianGrdPurchaseService().findGiftInstoreInfoList(map);
	}
	
	@Override
	public int findGiftInstoreInfoCount(Map<String,Object> map) throws Exception {
		return getHessianGrdPurchaseService().findGiftInstoreInfoCount(map);
	}
}