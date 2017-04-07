package com.gudeng.commerce.gd.api.service.engj;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;

/**
 * 商铺终端号和
 * 商铺关联service类
 * @author TerryZhang
 */
public interface ReBusinessPosToolService {

	/**
	 * 根据设备号
	 * 查找商铺信息
	 * @param posDevNo
	 * @param businessNo 
	 * @param shopNo
	 * @return
	 * @throws Exception
	 */
	public BusinessBaseinfoDTO getByPosDevNo(String posDevNo, String businessNo) throws Exception;
	
	/**
	 * 根据商铺ID获取posNum
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ReBusinessPosDTO> getPostNumByBusId(String businessId) throws Exception;
}
