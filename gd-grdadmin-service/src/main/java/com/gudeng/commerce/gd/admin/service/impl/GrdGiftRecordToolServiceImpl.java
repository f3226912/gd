package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdGiftRecordToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordExportDTO;
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
	public List<GrdGiftRecordDTO> queryBySearch(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().queryBySearch(map);
	}
	@Override
	public List<GrdGiftRecordExportDTO> queryBySearchExport(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().queryBySearchExport(map);
	}
	@Override
	public List<GrdGiftRecordExportDTO> queryBySearchGroup(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().queryBySearchGroup(map);
	}

	@Override
	public int countBySearch(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().countBySearch(map);
	}
	@Override
	public int countBySearchExport(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftRecordService().countBySearchExport(map);
	}

	@Override
	public int dynamicUpdate(GrdGiftRecordEntity entity) throws Exception {
		return getHessianGrdGiftRecordService().dynamicUpdate(entity);
	}

	@Override
	public int countByGrantOrCreateUserIds(List<String> userIds) throws Exception {
		return getHessianGrdGiftRecordService().countByGrantOrCreateUserIds(userIds);
	}

	@Override
	public List<GrdGiftRecordDTO> queryInviteRegUserInfoByUserId(Map<String, Object> param) throws Exception {
		return getHessianGrdGiftRecordService().queryInviteRegUserInfoByUserId(param);
	}

	@Override
	public int countInviteRegUserInfoByUserId(Map<String, Object> param) throws Exception {
		return getHessianGrdGiftRecordService().countInviteRegUserInfoByUserId(param);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GrdGiftRecordDTO> getChildStoreInfo(Map<String, Object> param)
			throws Exception {
		return getHessianGrdGiftRecordService().getChildStoreInfo(param);
	}

}