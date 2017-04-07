package com.gudeng.commerce.gd.report.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.dto.ChannelPhoneResult;
import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.UserPhoneDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.PhoneService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:40:11
 */
public class PhoneDataBuilder implements DataBuilder {
	
	@Autowired
	private PhoneService phoneService;

	public void setPhoneService(PhoneService phoneService) {
		this.phoneService = phoneService;
	}

	@Override
	public UserPhoneDataDTO getData(DataDBQuery dataQuery) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<>();
		//时间格式yyyymmddhhmmss
		paramMap.put("startTime", dataQuery.getStartTime());
		paramMap.put("endTime", dataQuery.getEndTime());
		paramMap.put("memberId", dataQuery.getMemberId());
		UserPhoneDataDTO userPhoneData = new UserPhoneDataDTO();
		List<ChannelPhoneResult> dataList = phoneService.getPhoneCallResult(paramMap);
		userPhoneData.setChannelPhoneResultList(dataList);
		return userPhoneData;
	}

}
