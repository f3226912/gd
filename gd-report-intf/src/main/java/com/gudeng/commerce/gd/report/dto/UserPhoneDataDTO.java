package com.gudeng.commerce.gd.report.dto;

import java.util.List;

/**
 * @Description: 返回给h5页面的数据集合
 * @author mpan
 * @date 2016年6月13日 下午4:19:36
 */
public class UserPhoneDataDTO extends DataDTO {

	private static final long serialVersionUID = -7121038771141951053L;

	/**
	 * 每个页面的电话统计
	 * 按次数倒序排列
	 */
	private List<ChannelPhoneResult> channelPhoneResultList;

	public List<ChannelPhoneResult> getChannelPhoneResultList() {
		return channelPhoneResultList;
	}

	public void setChannelPhoneResultList(List<ChannelPhoneResult> channelPhoneResultList) {
		this.channelPhoneResultList = channelPhoneResultList;
	}
}
