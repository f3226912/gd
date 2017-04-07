package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GdActActivityToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityDistributionModeService;
import com.gudeng.commerce.gd.promotion.service.GdActActivityService;
import com.gudeng.commerce.gd.promotion.service.GdActActivityUserRuleService;

public class GdActActivityToolServiceImpl implements GdActActivityToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static GdActActivityService gdActActivityService;
	
	private static GdActActivityDistributionModeService gdActActivityDistributionModeService ;

	private static GdActActivityUserRuleService gdActActivityUserRuleService ;
	
	protected GdActActivityService getHessianGdActActivityService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityServiceUrl();
		if (gdActActivityService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityService = (GdActActivityService) factory.create(GdActActivityService.class, url);
		}
		return gdActActivityService;
	}
	protected GdActActivityUserRuleService getHessianGdActActivityUserRuleService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityUserRuleServiceUrl();
		if (gdActActivityUserRuleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityUserRuleService = (GdActActivityUserRuleService) factory.create(GdActActivityUserRuleService.class, url);
		}
		return gdActActivityUserRuleService;
	}
	
	protected GdActActivityDistributionModeService getHessianGdActActivityDistributionModeService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityDistributionModeServiceUrl();
		if (gdActActivityDistributionModeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityDistributionModeService = (GdActActivityDistributionModeService) factory.create(GdActActivityDistributionModeService.class, url);
		}
		return gdActActivityDistributionModeService;
	}

	@Override
	public GdActActivityDTO getById(String id) throws Exception {
		return getHessianGdActActivityService().getById(id);
	}

	@Override
	public List<GdActActivityDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGdActActivityService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGdActActivityService().deleteBatch(list);
	}

	@Override
	public int update(GdActActivityDTO t) throws Exception {
		return getHessianGdActActivityService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityService().getTotal(map);
	}

	@Override
	public Long insert(GdActActivityEntity entity) throws Exception {
		return getHessianGdActActivityService().insert(entity);
	}

	@Override
	public List<GdActActivityDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityService().getListPage(map);
	}

	@Override
	public List<GdActActivityDTO> getActivityList(Map<String, Object> params) throws Exception {
		return getHessianGdActActivityService().getActivityList(params);
	}

	@Override
	public int getTotalForActivityList(Map<String, Object> params) throws Exception {
		return getHessianGdActActivityService().getTotalForActivityList(params);
	}

	@Override
	public GdActActivityDTO getSpecificActivityInfo(Map<String, Object> params) throws Exception {
		return getHessianGdActActivityService().getSpecificActivityInfo(params);
	}
	@Override
	public List<GdActActivityDistributionModeDTO> getDistributionModeByActivityId(Map<String, Object> params)
			throws Exception {
		return getHessianGdActActivityDistributionModeService().getList(params);
	}
	
	@Override
	public List<GdActActivityUserRuleDTO> getActivityMemberList(Map<String, Object> params) throws Exception {
		return getHessianGdActActivityUserRuleService().getListPage(params);
	}
	@Override
	public int getTotalForActMemberList(Map<String, Object> params) throws Exception {
		return getHessianGdActActivityUserRuleService().getTotal(params);
	}
	
	@Override
	public boolean isExistsActivityName(Map<String,Object> paraMap) throws Exception {
		//说明：校验活动名是否重复，分两种情况
		//1.新增：这个时候只要保证数据库表中不存在此记录即可。
		//2.变更：这个时候要要确保查询得到的数据是否是正在变更的数据，是则不能判断为重复数据，根据得到的数据Id来判断
		Object id=paraMap.remove("id");
		paraMap.remove("id");
		if (null==id || "".equals(id)) {
			paraMap.put("isNew", 1);
			return getHessianGdActActivityService().getTotal(paraMap)>0?true:false;
		}else {
			return false;
			/*
			List<GdActActivityDTO> resuts=getHessianGdActActivityService().getList(paraMap);
			
			if (resuts.isEmpty()&&resuts.size()<=0) {
				return false;
			}else{
				GdActActivityDTO entity=resuts.get(0);
				if (entity.getId().intValue()==Integer.parseInt(id.toString())) {
					return false;
				}else{
					return true;
				}
			}*/
		}
		
	}
	
	@Override
	public String getSequence() throws Exception {
		return getHessianGdActActivityService().getSequence();
		
	}
	
	@Override
	public void addRuleComm(GdActActivityCommDTO dto) throws Exception {
		getHessianGdActActivityService().addRuleComm(dto);
	}
	@Override
	public List<GdActActivityCommDTO> searchRuleCommForAct(Integer actId) throws Exception {
		return getHessianGdActActivityService().searchRuleCommForAct(actId);
	}
	@Override
	public List<GdActActivityDTO> getExistList(Map<String, Object> params) throws Exception {
		return getHessianGdActActivityService().getExistList(params);
	}
}