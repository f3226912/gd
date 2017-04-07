package com.gudeng.commerce.gd.api.util;

import java.util.List;
import com.gudeng.commerce.gd.api.service.NstSameCityCarlineApiService;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 同城车货匹配 发布货源的时候匹配线路
 * @author sunl
 *
 */
public class AssignSameCityLineThread implements Runnable{

	private static final GdLogger logger = GdLoggerFactory.getLogger(AssignSameCityLineThread.class);
	private NstSameCityCarlineApiService nstSameCityCarlineApiService; 
	private MemberBaseinfoDTO memberDTO;
	private NstSameCityAddressDTO nstSameCityAddressDTO;
	
	public AssignSameCityLineThread(){
		
	}
	
	public AssignSameCityLineThread(MemberBaseinfoDTO memberDTO,NstSameCityAddressDTO dto,
			NstSameCityCarlineApiService carlineApiService){
		
		this.memberDTO = memberDTO;
		this.nstSameCityAddressDTO=dto;
		this.nstSameCityCarlineApiService=carlineApiService;
		
	}
	
	@Override
	public void run() {
		try {
			List<NstSameCityCarlineEntityDTO> goodsDTOs =nstSameCityCarlineApiService.excutePushLine(memberDTO,nstSameCityAddressDTO, nstSameCityCarlineApiService);
			if (goodsDTOs!=null && goodsDTOs.size()==0) {
				logger.info("没有推送线路信息");
			}
		} catch (Exception e) {
			logger.warn("推送线路信息失败");
			e.printStackTrace();
		}
	}
	
}
