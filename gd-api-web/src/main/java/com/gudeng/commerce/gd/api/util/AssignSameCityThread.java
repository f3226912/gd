package com.gudeng.commerce.gd.api.util;
import java.util.List;

import com.gudeng.commerce.gd.api.service.NstSameCityCarlineApiService;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 同城车货匹配  发布线路的时候匹配货源
 * @author sunl
 *
 */
public class AssignSameCityThread implements Runnable{

	private static final GdLogger logger = GdLoggerFactory.getLogger(AssignSameCityThread.class);
	private NstSameCityCarlineApiService nstSameCityCarlineApiService; 
	private NstSameCityCarlineEntityDTO nstSameCityCarlineEntityDTO;
	private MemberBaseinfoDTO memberDTO;
	
	public AssignSameCityThread(){
		
	}
	
	public AssignSameCityThread(MemberBaseinfoDTO memberDTO,NstSameCityCarlineEntityDTO dto,
		   NstSameCityCarlineApiService carlineApiService){
		this.memberDTO = memberDTO;
		this.nstSameCityCarlineEntityDTO=dto;
		this.nstSameCityCarlineApiService=carlineApiService;
	}
	
	
	@Override
	public void run() {
		
		try {
			List<NstSameCityAddressDTO> lineDTOs=nstSameCityCarlineApiService.excutePushCity(memberDTO,nstSameCityCarlineEntityDTO,nstSameCityCarlineApiService);
			if (lineDTOs!=null && lineDTOs.size()==0) {
				logger.info("没有推送货源信息");
			}
		} catch (Exception e) {
			logger.warn("推送货源信息失败");
			e.printStackTrace();
		}
		
	}

}
