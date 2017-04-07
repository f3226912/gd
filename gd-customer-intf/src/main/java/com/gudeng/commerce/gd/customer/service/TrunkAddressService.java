package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.TrunkAddressDTO;
/**
 * 干线货源物流service
 * @author xiaojun
 *
 */
public interface TrunkAddressService {
	/**
	 * 获取干线物流列表
	 * @return
	 * @throws Exception
	 */
	List<TrunkAddressDTO> getTrunkAddressList(Map<String, Object> paramMap) throws Exception;
	/**
	 * 获取干线物流列表总数
	 * @return
	 * @throws Exception
	 */
	Integer getTrunkAddressListCount(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 获取干线物流我发的货列表
	 * @return
	 * @throws Exception
	 */
	List<TrunkAddressDTO> getTrunkAddressListByMemberId(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 获取干线物流我发的货列表总数
	 * @return
	 * @throws Exception
	 */
	Integer getTrunkAddressListCountByMemberId(Map<String, Object> paramMap) throws Exception;
}
