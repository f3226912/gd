package com.gudeng.commerce.gd.report.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.dao.BaseDao;
import com.gudeng.commerce.gd.report.dto.ChannelPhoneResult;
import com.gudeng.commerce.gd.report.enm.PhonePageCallEnum;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.PhoneService;

/**
 * @Description: 电话统计服务
 * @author mpan
 * @date 2016年6月13日 下午4:48:02
 */
public class PhoneServcieImpl implements PhoneService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<ChannelPhoneResult> getPhoneCallResult(
			Map<String, Object> paramMap)  throws ServiceException {
		String mobile = baseDao.queryForObject("UserPhone.getMobileById", paramMap, String.class);
		if(StringUtils.isBlank(mobile)){
			return null;
		}
		paramMap.put("mobile", mobile);
		List<ChannelPhoneResult> list = baseDao.queryForList("UserPhone.getPhoneCallResult", paramMap, ChannelPhoneResult.class);
		if(list != null && list.size() > 0){
			for(ChannelPhoneResult result : list){
				result.setPageName(PhonePageCallEnum.getPageNameByShortName(result.getPageType()));
			}
		}
		return list;
	}
	
	
}
