package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberSubsidyToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.MemberSubsidyDTO;
import com.gudeng.commerce.gd.order.service.MemberSubsidyService;

/**
 * @Description 会员补贴统计信息
 * @Project gd-admin-intf
 * @ClassName MemberSubsidyToolService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月9日 下午2:14:35
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class MemberSubsidyToolServiceImpl implements MemberSubsidyToolService {
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;

	private static MemberSubsidyService memberSubsidyService;

	private MemberSubsidyService gethessianMemberSubsidyService() throws MalformedURLException {
		String hessianUrl = gdProperties.getMemberSubsidyServiceUrl();
		if (memberSubsidyService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberSubsidyService = (MemberSubsidyService) factory.create(MemberSubsidyService.class, hessianUrl);
		}
		return memberSubsidyService;
	}

	/**
	 * @Description queryList 根据条件查询会员统计信息列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月9日 下午2:14:31
	 * @Author lidong(dli@gdeng.cn)
	 */
	public List<MemberSubsidyDTO> queryList(Map<String, Object> map) throws Exception {
		return gethessianMemberSubsidyService().queryList(map);
	}

	/**
	 * @Description queryTotal 根据条件查询会员信息列表记录条数
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月9日 下午2:15:10
	 * @Author lidong(dli@gdeng.cn)
	 */
	public int queryTotal(Map<String, Object> map) throws Exception {
		return gethessianMemberSubsidyService().queryTotal(map);
	}

	/**
	 * @Description queryStatistic 根据条件查询会员统计信息列表中的订单总数、补贴金额等总数统计信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月9日 下午2:15:27
	 * @Author lidong(dli@gdeng.cn)
	 */
	public MemberSubsidyDTO queryStatistic(Map<String, Object> map) throws Exception {
		return gethessianMemberSubsidyService().queryStatistic(map);
	}

}
