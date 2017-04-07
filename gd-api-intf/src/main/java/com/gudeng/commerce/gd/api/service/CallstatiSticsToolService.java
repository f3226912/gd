package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.CustInfoDTO;

public interface CallstatiSticsToolService {
	/**
	 * 增加电话记录
	 * @param callstatiSticsDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum insert (CallstatiSticsDTO callstatiSticsDTO) throws  Exception ;

	public void insertNst(CallstatiSticsDTO callstatiSticsDTO)throws   Exception ;
	
	/**
	 * 服务台获取客户列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CustInfoDTO> getCust(Map<String, Object> map) throws Exception;
	/**
	 * 获取联系人列表
	 * @param param
	 * @return
	 */
	public List<CustInfoDTO> getLinkman(Map<String, Object> param)  throws Exception;

	/**
	 * 获取联系人记录数
	 * @param param
	 * @return
	 */
	public Long getLinkmanTotal(Map<String, Object> param) throws Exception;
	
	/**
	 *  获取客户总数
	 * @param param
	 * @return
	 */
	public Long getCustTotal(Map<String, Object> param)  throws Exception;

}
