package com.gudeng.commerce.gd.api.service.V3;

import java.net.MalformedURLException;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;



public interface WalletToolServiceV3 {
	public AccInfoDTO  getWalletIndex(Long memberId )  throws MalformedURLException;
	public int addAccInfo(AccInfoDTO accInfoDTO,Map map) throws Exception;
	public int addAccInfo(AccInfoDTO accInfoDTO,MemberBaseinfoDTO memberBaseinfoDTO) throws Exception;


	public Integer validateTransPwd(Long memberId, String transPwd) throws Exception ;
	public Integer updateTransPwd(AccInfoDTO accInfoDTO,Map<String,Object> map) throws Exception ;
	public Integer updateTransPwd(AccInfoDTO accInfoDTO,MemberBaseinfoDTO memberBaseinfoDTO) throws Exception ;

}
