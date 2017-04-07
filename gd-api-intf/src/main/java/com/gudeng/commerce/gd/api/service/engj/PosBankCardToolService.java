package com.gudeng.commerce.gd.api.service.engj;

import com.gudeng.commerce.gd.api.dto.output.MemberInfoAppDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;

public interface PosBankCardToolService {

	/**
	 * 通过银行卡号获取用户信息
	 * @param bankNo
	 * @return
	 * @throws Exception
	 */
	public MemberInfoAppDTO getByBankNo(String bankNo) throws Exception;
	
	/**
	 * 通过PosBankCardDTO对象插入数据库
	 * 
	 * @param PosBankCardEntity
	 * @return Long
	 */
	public int addPosBankCardDTO(PosBankCardDTO posBankCardDTO) throws Exception;
}
