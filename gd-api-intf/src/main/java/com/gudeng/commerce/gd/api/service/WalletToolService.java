package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;
import java.util.Map;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;



public interface WalletToolService {
	
	public AccInfoDTO  getWalletIndex(Long memberId )  throws MalformedURLException;
	
	public int addAccInfo(AccInfoDTO accInfoDTO, Map map) throws Exception;
	
	public int addAccInfo(AccInfoDTO accInfoDTO, MemberBaseinfoDTO memberBaseinfoDTO) throws Exception;

	public ErrorCodeEnum validateTransPwd(Long memberId, String transPwd) throws Exception ;
	
	public Integer updateTransPwd(AccInfoDTO accInfoDTO,Map<String,Object> map) throws Exception ;
	
	public Integer updateTransPwd(AccInfoDTO accInfoDTO,MemberBaseinfoDTO memberBaseinfoDTO) throws Exception ;

}
