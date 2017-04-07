package com.gudeng.commerce.gd.order.service;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.entity.AccInfoEntity;




public interface AccInfoService {
	
	public int addAccInfo(AccInfoDTO accInfoDTO);
	
	public Long addAccInfoEntity(AccInfoEntity entity) throws Exception;
	
	public AccInfoDTO getWalletIndex(Long memberId );
	public int updateAccInfo(AccInfoDTO accInfoDTO);
	
	public AccInfoDTO getTransPwd(AccInfoDTO accInfoDTO);
	public Integer updateTransPwd(Long memberId, String transPwd) throws Exception ;

	
	/**
	 * @Title: updateMemAmount
	 * @Description: TODO(更新账户余额)
	 * @param accInfo
	 * @throws ServiceException
	 */
	public void updateMemAmount(AccInfoDTO accInfo) throws ServiceException; 

}
