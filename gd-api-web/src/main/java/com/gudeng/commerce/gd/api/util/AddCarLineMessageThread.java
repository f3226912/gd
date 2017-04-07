package com.gudeng.commerce.gd.api.util;

import java.util.ArrayList;
import java.util.List;

import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

/**
 * 插入推送的线路信息
 * 
 * @author xiaojun
 */
public class AddCarLineMessageThread implements Runnable {

	private static final GdLogger logger = GdLoggerFactory
			.getLogger(AddCarLineMessageThread.class);

	private MemberAddressApiService memberAddressApiService;

	private MemberBaseinfoDTO memberDTO;

	private MemberAddressDTO memberAddressDTO;

	/**
	 * 最大推送线路信息数
	 */
	private static final int MAX_MESSAGE = 5;

	public AddCarLineMessageThread() {

	}

	public AddCarLineMessageThread(MemberBaseinfoDTO memberDTO,
			MemberAddressDTO memberAddressDTO,
			MemberAddressApiService memberAddressApiService) {
		this.memberDTO = memberDTO;
		this.memberAddressDTO = memberAddressDTO;
		this.memberAddressApiService = memberAddressApiService;
	}

	/**
	 * 执行推送线路信息
	 */
	@Override
	public void run() {
		try {
			
			List<CarLineDTO> lineDTOs=memberAddressApiService.excutePush(memberDTO, memberAddressDTO, memberAddressApiService);
			if (lineDTOs!=null && lineDTOs.size()==0) {
				logger.info("没有推送线路信息");
			}
		} catch (Exception e) {
			logger.warn("推送线路信息失败");
			e.printStackTrace();
		}
	}
}
