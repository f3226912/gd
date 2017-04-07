package com.gudeng.commerce.gd.report.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.report.dto.ChannelPhoneResult;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * @Description: 用户电话统计服务
 * @author mpan
 * @date 2016年6月13日 下午4:47:38
 */
public interface PhoneService {

	public List<ChannelPhoneResult> getPhoneCallResult(Map<String, Object> paramMap)  throws ServiceException;
}
