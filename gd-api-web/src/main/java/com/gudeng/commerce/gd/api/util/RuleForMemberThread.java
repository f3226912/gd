package com.gudeng.commerce.gd.api.util;

import java.util.List;

import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class RuleForMemberThread implements Runnable {
   
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(RuleForMemberThread.class);

	private MemberAddressApiService memberAddressApiService;

	private MemberBaseinfoDTO memberDTO;

	private MemberAddressDTO memberAddressDTO;
	
	public RuleForMemberThread(MemberBaseinfoDTO memberDTO,
			MemberAddressDTO memberAddressDTO,
			MemberAddressApiService memberAddressApiService) {
		this.memberDTO = memberDTO;
		this.memberAddressDTO = memberAddressDTO;
		this.memberAddressApiService = memberAddressApiService;
	}
	/**
	 * 进行货源轮播规则方法
	 * @param memberAddressDTO
	 */
    public synchronized void sale(MemberAddressDTO memberAddressDTO){  
    	ObjectResult result = new ObjectResult();
    	try {
    		//获取当前发货地的规则设置信息部,满足条件的,按照分配时间的最后顺序的来排序,
			List<MemberAddressDTO> list = memberAddressApiService.getCompanyToMb(memberAddressDTO);
			if(list.size()<=0){
				  memberAddressApiService.updatenstRule(memberAddressDTO.getId());
				  return;
			}
			for(int i=0;i<list.size();i++){
			   MemberAddressDTO emberAddressD = memberAddressApiService.getCompanyToMbgetL(list.get(i));
			   //如果同时满足小于,日上限和月上限 则,break跳出训话
			   if(emberAddressD!=null){
				   if(list.get(i).getDayLimit()>emberAddressD.getDayCount()&&list.get(i).getMonthLimit()>emberAddressD.getMonthCount()){
					   
					   memberAddressApiService.updateCreateUserId(list.get(i).getCreateUserId(),memberAddressDTO.getClients(),memberAddressDTO.getId());
					   break;
				   }  
			   }else{
				   memberAddressApiService.updateCreateUserId(list.get(i).getCreateUserId(),memberAddressDTO.getClients(),memberAddressDTO.getId());
				   break;
			   }

			}
		} catch (Exception e) {
			logger.info("获取货源列表异常", e);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("获取货源列表异常");
		}

    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.sale(memberAddressDTO);
	}

}
