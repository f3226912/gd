package com.gudeng.commerce.gd.report.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.report.dto.ChannelPhoneResult;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.UserPhoneDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * @Description: 电话统计服务类
 * @author mpan
 * @date 2016年6月13日 下午3:59:57
 */
public class PhoneDataServiceImpl extends ADataService {

	@Override
	public DataDTO consolidateDataDTO(List<DataDTO> list, DataServiceQuery dataQuery) throws ServiceException {
		UserPhoneDataDTO dataDTO = new UserPhoneDataDTO();
		List<ChannelPhoneResult> newlist = new ArrayList<>();
		/*
		 * 循环整合数据
		 */
		for (int i = 0; i < list.size(); i++) {
			UserPhoneDataDTO tempData = (UserPhoneDataDTO)list.get(i);
			if(tempData.getChannelPhoneResultList() != null && tempData.getChannelPhoneResultList().size() > 0) {
				newlist.addAll(tempData.getChannelPhoneResultList());
			}
		}
		
		//内部数据重复项处理相加
		dataDTO.setChannelPhoneResultList(consolidateList(newlist));
		return dataDTO;

	}
	
	/**
	 * 整合相加集合中所有的项
	 * @param list
	 * @return
	 */
	public List<ChannelPhoneResult> consolidateList(List<ChannelPhoneResult> list) {
		if(list.size() < 2)
			return list;
		
		Map<String, ChannelPhoneResult> maps = new HashMap<>();
		
		for (int i = 0; i < list.size(); i++) {
			ChannelPhoneResult phoneResult = list.get(i);
			ChannelPhoneResult phoneResultInMap = maps.get(phoneResult.getPageType());
			if(phoneResultInMap==null) {
				maps.put(phoneResult.getPageType(), phoneResult);
			} else {
				phoneResultInMap.setCount(phoneResultInMap.getCount() + phoneResult.getCount());
			}
		}
		List<ChannelPhoneResult> returnList = new ArrayList<>(maps.values());
		Collections.sort(returnList, Collections.reverseOrder(new PhoneDataComparetor()));
		//取前10名
		if(returnList.size() > 10){
			for(int i=returnList.size() - 1; i<10; i--){
				returnList.remove(i);
			}
		}
		return returnList;
	}

}
