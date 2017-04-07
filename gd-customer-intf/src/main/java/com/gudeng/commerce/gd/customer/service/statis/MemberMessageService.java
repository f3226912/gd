package com.gudeng.commerce.gd.customer.service.statis;

import com.gudeng.commerce.gd.customer.dto.MemberMessageDTO;

/**
 * 短信内容服务业务
 * 
 * @author Ailen
 *
 */
public interface MemberMessageService {

	/**
	 * 插入短信记录
	 * 
	 * @return
	 */
	public void add(MemberMessageDTO memberMessageDTO);

}
