package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdUserTeamToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdUserTeamDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdUserTeamEntity;
import com.gudeng.commerce.gd.promotion.service.GrdUserTeamService;

public class GrdUserTeamToolServiceImpl implements GrdUserTeamToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdUserTeamService grdUserTeamService;

	protected GrdUserTeamService getHessianGrdUserTeamService() throws MalformedURLException {
		String url = gdProperties.getGrdUserTeamServiceUrl();
		if (grdUserTeamService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdUserTeamService = (GrdUserTeamService) factory.create(GrdUserTeamService.class, url);
		}
		return grdUserTeamService;
	}

	@Override
	public GrdUserTeamDTO getById(String id) throws Exception {
		return getHessianGrdUserTeamService().getById(id);
	}

	@Override
	public List<GrdUserTeamDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdUserTeamService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdUserTeamService().deleteById(id);
	}
	/**
	 * 删除地推用户所属团队
	 * @param grdUserId
	 * @return
	 * @throws Exception
	 */
	public int deleteByGrdUserId(String grdUserId) throws Exception{
		return getHessianGrdUserTeamService().deleteByGrdUserId(grdUserId);
	}

	@Override
	public int update(GrdUserTeamDTO t) throws Exception {
		return getHessianGrdUserTeamService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdUserTeamService().getTotal(map);
	}

	@Override
	public Long insert(GrdUserTeamEntity entity) throws Exception {
		return getHessianGrdUserTeamService().insert(entity);
	}

	@Override
	public List<GrdUserTeamDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdUserTeamService().getListPage(map);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdUserTeamService().deleteBatch(list);
	}

	@Override
	public int insert(Map<String, Object> map) throws Exception {
		return getHessianGrdUserTeamService().insert(map);
	}

	@Override
	public List<GrdUserTeamDTO> getReUserTeamList(Map<String, Object> map) throws Exception {
		return getHessianGrdUserTeamService().getReUserTeamList(map);
	}

}