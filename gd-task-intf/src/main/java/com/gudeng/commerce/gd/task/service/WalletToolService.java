package com.gudeng.commerce.gd.task.service;

import java.net.MalformedURLException;

import com.gudeng.commerce.gd.order.dto.AccInfoDTO;

public interface WalletToolService {
	
	public AccInfoDTO  getWalletIndex(Long memberId ) throws MalformedURLException;
}
