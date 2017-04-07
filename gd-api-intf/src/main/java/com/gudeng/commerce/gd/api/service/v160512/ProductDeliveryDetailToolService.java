package com.gudeng.commerce.gd.api.service.v160512;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;

public interface ProductDeliveryDetailToolService {

	public List<?> getListByCondition(Map<String, Object> map) throws Exception;
	
	public ErrorCodeEnum add(MemberAddressInputDTO inputDTO, Long memberAddressId, boolean isUpdate) throws Exception;

	public int deleteByMemberAddressId(Long memberAddressId) throws Exception;
	
	public Integer isOpenForAddDelivery() throws Exception;
	
	public List<ProductDeliveryDetailDTO> getByMap(Map<String, Object> map) throws Exception;

}
