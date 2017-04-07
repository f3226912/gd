package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.order.dto.AccInfoDTO;

/**
 * 钱包信息服务
 * @author xiaojun
 *
 */
public interface AccInfoToolService {
	/**
	 * 根据memberId获取账号（钱包）信息
	 * @return
	 * @throws Exception
	 */
	public AccInfoDTO getWalletIndex(Long memberId) throws Exception;
}
