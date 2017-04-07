package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ReBusinessPosToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.ReBusinessPosEntity;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.customer.service.ReBusinessPosService;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;

/**
 * 功能描述：
 */
@Service
public class ReBusinessPosToolServiceImpl implements ReBusinessPosToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static ReBusinessPosService reBusinessPosService;

	/**
	 * 功能描述: reBusinessPosService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ReBusinessPosService getHessianReBusinessPosService() throws MalformedURLException {
		String url = gdProperties.getReBusinessPosUrl();
		if (reBusinessPosService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessPosService = (ReBusinessPosService) factory.create(ReBusinessPosService.class, url);
		}
		return reBusinessPosService;
	}
	
	

	@Override
	public Long addReBusinessPosEntity(ReBusinessPosEntity reBusinessPosEntity) throws Exception {
		return getHessianReBusinessPosService().addReBusinessPosEntity(reBusinessPosEntity);
	}

	@Override
	public BusinessBaseinfoDTO getByPosDevNo(String posDevNo) throws Exception {
		return getHessianReBusinessPosService().getByPosDevNo(posDevNo);
	}

	@Override
	public int deleteByBusinessId(Long businessId) throws Exception {
		return getHessianReBusinessPosService().deleteByBusinessId(businessId);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return getHessianReBusinessPosService().deleteById(id);
	}


	@Override
	public List<ReBusinessPosDTO> getReBusinessPosByBusinessId(Long businessId) throws Exception {
		return getHessianReBusinessPosService().getReBusinessPosByBusinessId(businessId);
	}


	@Override
	public ReBusinessPosDTO getByBusinessIdAndPosNumber(Long businessId, String posNumber) throws Exception {
		return getHessianReBusinessPosService().getByBusinessIdAndPosNumber( businessId,  posNumber) ;
	}
	

}
