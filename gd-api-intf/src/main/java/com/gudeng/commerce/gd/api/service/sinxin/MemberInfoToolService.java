package com.gudeng.commerce.gd.api.service.sinxin;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.MemberSinxinDTO;


public interface MemberInfoToolService {
	
	/**
	 * 会员查询
	 */
	public List<MemberSinxinDTO> queryMember(MemberSinxinDTO queryDTO) throws Exception;

}