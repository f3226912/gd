package com.gudeng.commerce.gd.api.thread;

import java.util.List;

import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemForCustDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;

public class AddReMemCustThread extends Thread {
	
	private MemberToolService memberToolService;
	
	private OrderBaseinfoEntity orderEntity;

	public AddReMemCustThread(MemberToolService memberToolService, OrderBaseinfoEntity orderEntity){
		this.memberToolService = memberToolService;
		this.orderEntity = orderEntity;
	}
		
	public void run(){
		Integer sellerId = orderEntity.getSellMemberId();
		Integer buyerId = orderEntity.getMemberId();
		if(sellerId == null || buyerId == null){
			return;
		}
		//如果存在真实买家
		if(sellerId != buyerId){
			MemberBaseinfoDTO memberDtoInput = new MemberBaseinfoDTO();
			try {
				//检查记录是否存在
				memberDtoInput.setBusiMemberId(Long.parseLong(sellerId+""));
				memberDtoInput.setMemberId(Long.parseLong(buyerId + ""));
				List<MemberBaseinfoDTO> dataList = memberToolService.queryMemberListByMap(memberDtoInput);
				//不存在则插入记录
				if(dataList == null || dataList.size() == 0){
					ReMemForCustDTO reMemForCust = new ReMemForCustDTO();
					reMemForCust.setBusiMemberId(Long.parseLong(sellerId+"")); // 商家ID（供应商或批发商）
					reMemForCust.setCustMemberId(Long.parseLong(buyerId + "")); // 买家会员ID
					reMemForCust.setType("2");
					memberToolService.addReMemCust(reMemForCust );
				} else {
					ReMemForCustDTO reMemForCust = new ReMemForCustDTO();
					reMemForCust.setBusiMemberId(memberDtoInput.getBusiMemberId());
					reMemForCust.setCustMemberId(memberDtoInput.getMemberId());
					memberToolService.updateReMemCust(reMemForCust);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
