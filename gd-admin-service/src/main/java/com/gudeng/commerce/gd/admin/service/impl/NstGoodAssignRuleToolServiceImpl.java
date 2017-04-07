package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.NstGoodAssignRuleToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NstDeptGoodDTO;
import com.gudeng.commerce.gd.customer.dto.NstGoodAssignRuleDTO;
import com.gudeng.commerce.gd.customer.service.NstGoodAssignRuleService;
/**
 * 一手货源农速通货源分配规则服务
 * @author xiaojun
 */
public class NstGoodAssignRuleToolServiceImpl implements NstGoodAssignRuleToolService{
	
	@Autowired
	public GdProperties gdProperties;

	private static NstGoodAssignRuleService nstGoodAssignRuleService;

	private NstGoodAssignRuleService getHessianGoodAssignRuleService() throws MalformedURLException {
		String hessianUrl = gdProperties.getnstGoodAssignRuleUrl();
		if (nstGoodAssignRuleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstGoodAssignRuleService = (NstGoodAssignRuleService) factory.create(NstGoodAssignRuleService.class, hessianUrl);
		}
		return nstGoodAssignRuleService;
	}
	@Override
	public Integer insert(NstGoodAssignRuleDTO nstGoodAssignRuleDTO) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().insert(nstGoodAssignRuleDTO);
	}

	@Override
	public Integer update(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().update(map);
	}

	@Override
	public List<NstGoodAssignRuleDTO> getAssignRuleDTOListByPage(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().getAssignRuleDTOListByPage(map);
	}

	@Override
	public Integer getAssignRuleDTOListByPageCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().getAssignRuleDTOListByPageCount(map);
	}

	@Override
	public NstGoodAssignRuleDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().getById(id);
	}

	@Override
	public Integer updateStaus(String idString,Integer isEffective,String updateUserId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().updateStaus(idString,isEffective,updateUserId);
	}
	@Override
	public List<NstGoodAssignRuleDTO> getDeptNameListByCityId(Long cityId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().getDeptNameListByCityId(cityId);
	}
	@Override
	public Integer insertRuleSwith(NstGoodAssignRuleDTO nstGoodAssignRuleDTO) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().insertRuleSwith(nstGoodAssignRuleDTO);
	}
	@Override
	public Integer updateEffective(Integer isEffective) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().updateEffective(isEffective);
	}
	@Override
	public NstGoodAssignRuleDTO selectRuleSwith() throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().selectRuleSwith();
	}
	@Override
	public List<NstGoodAssignRuleDTO> getQueryDeptNameListByCityId(Long cityId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().getQueryDeptNameListByCityId(cityId);
	}
	@Override
	public Integer updateRuleSwithByCode(String value,String code,String updateUserId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianGoodAssignRuleService().updateRuleSwithByCode(value, code,updateUserId);
	}
	@Override
	public List<NstDeptGoodDTO> queryDeptGoodPage(Map<String, Object> map) throws Exception {
		return getHessianGoodAssignRuleService().queryDeptGoodPage(map);
	}
	@Override
	public int getDeptGoodTotalCount(Map<String, Object> map) throws Exception {
		return getHessianGoodAssignRuleService().getDeptGoodTotalCount(map);
	}
}
