/**
 * @Title: SubLimitRuleServiceImpl.java
 * @Package com.gudeng.commerce.gd.admin.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午3:54:57
 * @version V1.0
 */
package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.SubLimitRuleToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.service.SubLimitRuleService;

/**
 * @ClassName: SubLimitRuleServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年11月30日 下午3:54:57
 */
public class SubLimitRuleToolServiceImpl implements SubLimitRuleToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static SubLimitRuleService subLimitRuleService;

	protected SubLimitRuleService getHessianService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.subLimitRule.url");
		if (subLimitRuleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			subLimitRuleService = (SubLimitRuleService) factory.create(SubLimitRuleService.class, url);
		}
		return subLimitRuleService;
	}

	@Override
	public List<SubLimitRuleDTO> getSubLimitRuleDetail(Map<String, Object> map) throws Exception {
		return getHessianService().getSubLimitRuleDetail(map);
	}

	@Override
	public void saveOrUpdateSubLimitRule(SubLimitRuleDTO subLimitRule) throws Exception {
		getHessianService().saveOrUpdateSubLimitRule(subLimitRule);
	}

	@Override
	public void delWhiteList(Map<String, Object> map) throws Exception  {
		getHessianService().delWhiteList(map);
		
	}

	@Override
	public void updateSubLimitRuleStatus(Map<String, Object> map)
			throws Exception {
		 getHessianService().updateSubLimitRuleStatus(map);
		
	}

	@Override
	public List<SubLimitRuleDTO> searchLimitRuleList(Map<String, Object> map)
			throws Exception {
		return getHessianService().SearchLimitRuleList(map);
	}

	@Override
	public int getSearchLimitRuleListCount(Map<String, Object> map)
			throws Exception {
		return getHessianService().searchLimitRuleListCount(map);
	}

}
