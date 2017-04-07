package com.gudeng.commerce.gd.home.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.entity.SystemCode;


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
public interface SystemCodeService {
	/**
	 * @Description showValueByCode 根据编码类型以及key值从数据字典中获取value值
	 * @param codeType 数据类型
	 * @param codeKey 键值
	 * @return
	 * @CreationDate 2015年10月22日 下午2:50:30
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public String showValueByCode(String codeType,String codeKey)  throws Exception;
	
	/**
	 * 根据类型获取列表数据
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	public List<SystemCode> getListViaType(String type) throws Exception;
}
