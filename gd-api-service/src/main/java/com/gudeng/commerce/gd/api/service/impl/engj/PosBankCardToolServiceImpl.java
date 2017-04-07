package com.gudeng.commerce.gd.api.service.impl.engj;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.output.MemberInfoAppDTO;
import com.gudeng.commerce.gd.api.service.engj.PosBankCardToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;
import com.gudeng.commerce.gd.customer.service.PosBankCardService;

public class PosBankCardToolServiceImpl implements PosBankCardToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PosBankCardToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	private PosBankCardService posBankCardService;

	private PosBankCardService getHessianPosBankCardService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.posBankCardService.url");
		if (posBankCardService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			posBankCardService = (PosBankCardService) factory.create(PosBankCardService.class, hessianUrl);
		}
		return posBankCardService;
	}


	@Override
	public MemberInfoAppDTO getByBankNo(String bankNo) throws Exception {
		MemberInfoAppDTO memberDTO = null;
		MemberBaseinfoDTO baseDTO = getHessianPosBankCardService().getByBankNo(bankNo);
		if(baseDTO != null){
			memberDTO = new MemberInfoAppDTO();
			memberDTO.setAccount(baseDTO.getAccount());
			memberDTO.setMemberId(baseDTO.getMemberId());
			memberDTO.setMobile(baseDTO.getMobile());
			memberDTO.setRealName(baseDTO.getRealName());
		}
		return memberDTO;
	}
	
	/**
	 * 通过PosBankCardDTO对象插入数据库
	 * 
	 * @param posBankCardDTO
	 * @return Long
	 */
	public int addPosBankCardDTO(PosBankCardDTO posBankCardDTO) throws Exception{
		return getHessianPosBankCardService().addPosBankCardDTO(posBankCardDTO);
	}
}
