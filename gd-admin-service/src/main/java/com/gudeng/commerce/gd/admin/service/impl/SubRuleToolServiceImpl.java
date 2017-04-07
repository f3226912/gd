package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.SubRuleToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.CategoryTreeDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleParamDTO;
import com.gudeng.commerce.gd.order.service.SubPayRuleService;

public class SubRuleToolServiceImpl implements SubRuleToolService {

	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static SubPayRuleService subPayRuleService;

	protected SubPayRuleService getHessianSubPayRuleService() throws MalformedURLException {
		String url = gdProperties.getSubPayRuleServiceUrl();
		if (subPayRuleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			subPayRuleService = (SubPayRuleService) factory.create(SubPayRuleService.class, url);
		}
		return subPayRuleService;
	}
	
	
	@Override
	public long addSubRule(SubPayRuleParamDTO subruleDTO) throws Exception {
		return getHessianSubPayRuleService().addRule(subruleDTO);
	}


	@Override
	public SubPayRuleDTO getRuleInfo(int ruleId) throws Exception {
		return getHessianSubPayRuleService().getRuleInfo(ruleId);
	}


	@Override
	public int getRuleTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianSubPayRuleService().getRuleTotal(map);
	}


	@Override
	public List<SubPayRuleDTO> getRuleList(Map<String, Object> map)
			throws Exception {
		return getHessianSubPayRuleService().getRuleList(map);
	}


	@Override
	public int updateRuleStatus(Map<String, Object> map) 
			throws Exception {
		return getHessianSubPayRuleService().updateRuleStatus(map);
	}


	@Override
	public CategoryTreeDTO validateCate(Set<CategoryTreeDTO> list,Map<String,Object> map)  throws Exception{
		
		return getHessianSubPayRuleService().validateCate(list, map);
	}


	@Override
	public SubPayRuleDTO getRuleInfo(Map<String, Object> map) throws Exception {
		return getHessianSubPayRuleService().getRuleInfo(map);
	}


	@Override
	public long modifyRule(SubPayRuleParamDTO subruleDTO) throws Exception {
		return getHessianSubPayRuleService().modifyRule(subruleDTO);
	}

}
