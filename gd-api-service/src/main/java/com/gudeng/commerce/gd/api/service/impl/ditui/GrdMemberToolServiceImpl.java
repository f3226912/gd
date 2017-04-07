package com.gudeng.commerce.gd.api.service.impl.ditui;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ditui.GrdMemberToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
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
	public GrdMemberDTO getMemberDTO(Map<String, Object> map) throws Exception {
		return getHessianGrdMemberService().getMemberByParams(map);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getUserType(Integer id) throws  Exception {
		return getHessianGrdMemberService().getUserType(id);
	}
}