package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.customer.service.SystemCodeService;

/**
 * @Description 数据字典操作服务接口类
 * @Project gd-home-intf
 * @ClassName SystemCodeService.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月22日 下午2:49:01
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Service
public class SystemCodeServiceImpl implements SystemCodeService {

	@Autowired
	private BaseDao<?> baseDao;

	/**
	 * @Description showValueByCode 根据编码类型以及key值从数据字典中获取value值
	 * @param codeType
	 *            数据类型
	 * @param codeKey
	 *            键值
	 * @return
	 * @CreationDate 2015年10月22日 下午2:50:30
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public String showValueByCode(String codeType, String codeKey) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("codeKey", codeKey);
		map.put("type", codeType);
		SystemCode systemCode = baseDao.queryForObject("SystemCode.getValueByCodeAndType", map, SystemCode.class);
		if (systemCode != null) {
			return systemCode.getCodeValue();
		}
		return null;
	}

	@Override
	public List<SystemCode> getListViaType(String type) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		return baseDao.queryForList("SystemCode.getListViaType", map, SystemCode.class);
	}
}
