package com.gudeng.commerce.gd.api.thread;

import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 获取农速通token线程
 * @author TerryZhang
 */
public class GetNstTokenThread implements Runnable{
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(GetNstTokenThread.class);

	private NstApiCommonService nstApiCommonService;
	
	private Integer memberId;
	
	public GetNstTokenThread(NstApiCommonService nstApiCommonService, 
			Integer memberId){
		this.nstApiCommonService = nstApiCommonService;
		this.memberId = memberId;
	}
	
	@Override
	public void run() {
		try {
			nstApiCommonService.setNstToken(memberId+"");
		} catch (Exception e) {
			logger.warn("[ERROR]获取农速通token异常: ", e);
			e.printStackTrace();
		}
		
	}
}
