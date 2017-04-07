package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdGiftDetailToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftDetailEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftDetailService;

public class GrdGiftDetailToolServiceImpl implements GrdGiftDetailToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static GrdGiftDetailService giftDetailService;

	protected GrdGiftDetailService getHessianGrdGiftDetailService() throws MalformedURLException {
		String url = gdProperties.getGiftDetailServiceUrl();
		if (giftDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			giftDetailService = (GrdGiftDetailService) factory.create(GrdGiftDetailService.class, url);
		}
		return giftDetailService;
	}

	@Override
	public GrdGiftDetailDTO getById(String id) throws Exception {
		return getHessianGrdGiftDetailService().getById(id);
	}

	@Override
	public List<GrdGiftDetailDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftDetailService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdGiftDetailService().deleteById(id);
	}

	@Override
	public int update(GrdGiftDetailDTO t) throws Exception {
		return getHessianGrdGiftDetailService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftDetailService().getTotal(map);
	}

	@Override
	public Long insert(GrdGiftDetailEntity entity) throws Exception {
		return getHessianGrdGiftDetailService().insert(entity);
	}

	@Override
	public List<GrdGiftDetailDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftDetailService().getListPage(map);
	}

	@Override
	public List<GrdGiftDetailDTO> queryByRecordId(Integer recordId) throws Exception {
		return getHessianGrdGiftDetailService().queryByRecordId(recordId);
	}

	@Override
	public List<GrdGiftDetailDTO> queryOrderInfoByUserId(Map<String, Object> param) throws Exception {
		return getHessianGrdGiftDetailService().queryOrderInfoByUserId(param);
	}

	@Override
	public int countOrderInfoByUserId(Map<String, Object> param) throws Exception {
		return getHessianGrdGiftDetailService().countOrderInfoByUserId(param);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return 0;
	}

	@Override
	public int getGrdOrderTotal(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGrdGiftDetailService().getGrdOrderTotal(param);
	}

	@Override
	public List<GrdGiftDetailDTO> getGrdOrderList(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGrdGiftDetailService().getGrdOrderList(param);
	}

}