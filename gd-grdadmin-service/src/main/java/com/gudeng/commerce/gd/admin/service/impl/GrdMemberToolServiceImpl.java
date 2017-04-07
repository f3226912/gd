package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdMemberToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdMemberEntity;
import com.gudeng.commerce.gd.promotion.service.GrdMemberService;

public class GrdMemberToolServiceImpl implements GrdMemberToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdMemberService grdMemberService;

	protected GrdMemberService getHessianGrdMemberService() throws MalformedURLException {
		String url = gdProperties.getGrdMemberServiceUrl();
		if (grdMemberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdMemberService = (GrdMemberService) factory.create(GrdMemberService.class, url);
		}
		return grdMemberService;
	}

	@Override
	public GrdMemberDTO getById(String id) throws Exception {
		return getHessianGrdMemberService().getById(id);
	}

	@Override
	public List<GrdMemberDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdMemberService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdMemberService().deleteById(id);
	}

	@Override
	public int update(GrdMemberDTO t) throws Exception {
		return getHessianGrdMemberService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdMemberService().getTotal(map);
	}

	@Override
	public Long insert(GrdMemberEntity entity) throws Exception {
		return getHessianGrdMemberService().insert(entity);
	}

	@Override
	public List<GrdMemberDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdMemberService().getListPage(map);
	}

	@Override
	public List<GrdMemberDTO> queryBySearch(Map<String, Object> map) throws Exception {
		return getHessianGrdMemberService().queryBySearch(map);
	}

	@Override
	public int countBySearch(Map<String, Object> map) throws Exception {
		return getHessianGrdMemberService().countBySearch(map);
	}

	@Override
	public int deleteByIds(List<String> ids) throws Exception {
		return getHessianGrdMemberService().deleteByIds(ids);
	}

	@Override
	public int dynamicUpdate(GrdMemberEntity entity) throws Exception {
		return getHessianGrdMemberService().dynamicUpdate(entity);
	}

	@Override
	public Long save(GrdMemberEntity entity) throws Exception {
		return getHessianGrdMemberService().save(entity);
	}

	@Override
	public int resetPwdByIds(Map<String, Object> param) throws Exception {
		return getHessianGrdMemberService().resetPwdByIds(param);
	}
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int resetStatusByIds(Map<String, Object> param) throws Exception {
		return  getHessianGrdMemberService().resetStatusByIds(param);
	}

	@Override
	public List<GrdMemberDTO> getChildTeamInfo(Map<String, Object> param)
			throws Exception {
		return  getHessianGrdMemberService().getChildTeamInfo(param);
	}
}