package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.NstNoticeToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;
import com.gudeng.commerce.gd.customer.service.NstNoticeService;
/**
 * 农速通实现类
 * @author xiaojun
 */
public class NstNoticeToolServiceImpl implements NstNoticeToolService {

	//农速通(customer)服务
	private NstNoticeService nstNoticeService;
	
	@Autowired
	private GdProperties gdProperties;
	
	protected NstNoticeService getHessianNstNoticService() throws MalformedURLException {
		String url =  gdProperties.getNstNoticeUrl();
		if(nstNoticeService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstNoticeService = (NstNoticeService) factory.create(NstNoticeService.class, url);
		}
		return nstNoticeService;
	}
	
	@Override
	public int insert(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstNoticService().insert(map);
	}

	@Override
	public int update(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstNoticService().update(id);
	}

	@Override
	public List<NstNoticeEntityDTO> queryNstNoticeListByPage(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstNoticService().queryNstNoticeListByPage(map);
	}

	@Override
	public Long queryNstNoticeListByPageCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstNoticService().queryNstNoticeListByPageCount(map);
	}

}
