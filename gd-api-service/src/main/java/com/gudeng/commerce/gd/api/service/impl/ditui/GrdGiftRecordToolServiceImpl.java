package com.gudeng.commerce.gd.api.service.impl.ditui;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ditui.GrdGiftRecordToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftRecordEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftRecordService;

public class GrdGiftRecordToolServiceImpl implements GrdGiftRecordToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdGiftRecordService grdGiftRecordService;

	protected GrdGiftRecordService getHessianGrdGiftRecordService() throws MalformedURLException {
		String url = gdProperties.getGrdGiftRecordServiceUrl();
		if (grdGiftRecordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGiftRecordService = (GrdGiftRecordService) factory.create(GrdGiftRecordService.class, url);
		}
		return grdGiftRecordService;
	}

	@Override
	public GrdGiftRecordDTO getById(String id) throws Exception {
		return getHessianGrdGiftRecordService().getById(id);
	}

	@Override
	public List<GrdGiftRecordDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().getList(map);
	}

	@Override
	public List<GrdGiftRecordDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().getListPage(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdGiftRecordService().deleteById(id);
	}

	@Override
	public int update(GrdGiftRecordDTO t) throws Exception {
		return getHessianGrdGiftRecordService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().getTotal(map);
	}

	@Override
	public Long insert(GrdGiftRecordEntity entity) throws Exception {
		return getHessianGrdGiftRecordService().insert(entity);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}