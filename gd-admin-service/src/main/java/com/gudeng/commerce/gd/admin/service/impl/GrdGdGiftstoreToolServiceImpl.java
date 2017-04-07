package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftstoreToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftInStorageDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftOutStorageDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftstoreEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGdGiftstoreService;

public class GrdGdGiftstoreToolServiceImpl implements GrdGdGiftstoreToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdGdGiftstoreService grdGdGiftstoreService;

	protected GrdGdGiftstoreService getHessianGrdGdGiftstoreService() throws MalformedURLException {
		String url = gdProperties.getGrdGdGiftstoreServiceUrl();
		if (grdGdGiftstoreService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGdGiftstoreService = (GrdGdGiftstoreService) factory.create(GrdGdGiftstoreService.class, url);
		}
		return grdGdGiftstoreService;
	}

	@Override
	public GrdGdGiftstoreDTO getById(String id) throws Exception {
		return getHessianGrdGdGiftstoreService().getById(id);
	}

	@Override
	public List<GrdGdGiftstoreDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdGdGiftstoreService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdGdGiftstoreService().deleteBatch(list);
	}

	@Override
	public int update(GrdGdGiftstoreDTO t) throws Exception {
		return getHessianGrdGdGiftstoreService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getTotal(map);
	}

	@Override
	public Long insert(GrdGdGiftstoreEntity entity) throws Exception {
		return getHessianGrdGdGiftstoreService().insert(entity);
	}

	@Override
	public List<GrdGdGiftstoreDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getListPage(map);
	}

	@Override
	public List<GrdGdGiftInStorageDataDTO> getGiftInStorageData(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getGiftInStorageData(map);
	}

	@Override
	public Integer getGiftInStorageDataCount(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getGiftInStorageDataCount(map);
	}

	@Override
	public List<GrdGdGiftOutStorageDataDTO> getGiftOutStorageData(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getGiftOutStorageData(map);
	}

	@Override
	public Integer getGiftOutStorageDataCount(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getGiftOutStorageDataCount(map);
	}

	@Override
	public List<GrdGdGiftDataDTO> getGrdGdGiftData(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getGrdGdGiftData(map);
	}

	@Override
	public Integer getGrdGdGiftDataCount(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getGrdGdGiftDataCount(map);
	}

	@Override
	public Integer getGrdGdGiftDataSum(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getGrdGdGiftDataSum(map);
	}
}