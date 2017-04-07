package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCountDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;
import com.gudeng.commerce.gd.m.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

public class NstOrderBaseinfoToolServiceImpl implements NstOrderBaseinfoToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private static NstOrderBaseinfoService nstOrderBaseinfoService;
	
	private NstOrderBaseinfoService getHessianService() throws MalformedURLException {
		String hessianUrl = gdProperties.getNstOrderBaseinfoServiceUrl();
		if(nstOrderBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderBaseinfoService = (NstOrderBaseinfoService) factory.create(NstOrderBaseinfoService.class, hessianUrl);
		}
		return nstOrderBaseinfoService;
	}
	
	@Override
	public List<NstOrderBaseinfoDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return getHessianService().getByCondition(map);
	}


	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianService().getTotal(map);
	}

	@Override
	public int updateNstOrderBaseinfoDTO(NstOrderBaseinfoDTO dto)
			throws Exception {
		return getHessianService().updateNstOrderBaseinfoDTO(dto);
	}



	@Override
	public List<NstOrderBaseinfoDTO> getCommentListByCondition(
			Map<String, Object> map) throws Exception {
		return getHessianService().getCommentListByCondition(map);
	}

	@Override
	public int getCommentTotal(Map<String, Object> map) throws Exception {
		return getHessianService().getCommentTotal(map);
	}

	@Override
	public List<NstOrderCountDTO> getOrderCountList(
			Map<String, Object> map) throws Exception {
		return getHessianService().getOrderCountList(map);
	}

	@Override
	public NstOrderCountDTO getOrderCountDetailByOrderNo(String orderNo)
			throws Exception {
		return getHessianService().getOrderCountDetailByOrderNo(orderNo);
	}

	@Override
	public Long getOrderCountListTotal(Map<String, Object> map)
			throws Exception {
		return getHessianService().getOrderCountListTotal(map);
	}

	@Override
	public Long insertNstOrderBaseinfo(NstOrderBaseinfoEntity nstOrderBaseinfoEntity) throws Exception {
		return getHessianService().insertNstOrderBaseinfo(nstOrderBaseinfoEntity);
	}

	@Override
	public NstOrderBaseinfoDTO getByMemberAddressId(Long memberAddressId,String source) throws Exception {
		return getHessianService().getByMemberAddressId(memberAddressId, source);
	}

	@Override
	public NstOrderBaseinfoDTO getByOrderNo(String orderNo) throws Exception {
		return getHessianService().getByOrderNo(orderNo);
	}

	@Override
	public int autoComfirmOrder() throws Exception {
		return getHessianService().autoComfirmOrder();
	}

	@Override
	public Integer acceptOrder(Map<String, Object> map) throws Exception {
		return getHessianService().acceptOrder(map);
	}

	@Override
	public Integer refuseOrder(Map<String, Object> map) throws Exception {
		return getHessianService().refuseOrder(map);
	}

	@Override
	public Integer cancelOrder(Map<String, Object> map) throws Exception {
		return getHessianService().cancelOrder(map);
	}

	@Override
	public Integer batchDeleteOrder(Map<String, Object> map) throws Exception {
		return getHessianService().batchDeleteOrder(map);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getNstOrderListByCondition(Map<String, Object> map) throws Exception {
		return getHessianService().getNstOrderListByCondition(map);
	}

	@Override
	public NstOrderBaseinfoDTO getMemberInfoByMemberId(Long memberId) throws Exception {
		return getHessianService().getMemberInfoByMemberId(memberId);
	}

	@Override
	public Integer confirmGoods(Map<String, Object> map) throws Exception {
		return getHessianService().confirmGoods(map);
	}

	@Override
	public NstOrderBaseinfoDTO getCarLineInfoByCarLineId(Long carLineId) throws Exception {
		return getHessianService().getCarLineInfoByCarLineId(carLineId);
	}

	@Override
	public int getNstOrderListByConditionTotal(Map<String, Object> map) throws Exception {
		return getHessianService().getNstOrderListByConditionTotal(map);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getOrderStatusCountByMemberId(Long memberId) throws Exception {
		return getHessianService().getOrderStatusCountByMemberId(memberId);
	}

	@Override
	public int getEvaluateTypeCountByMemberId(Long memberId) throws Exception {
		return getHessianService().getEvaluateTypeCountByMemberId(memberId);
	}

	@Override
	public int getCount(String orderType, Long memberId) throws Exception {
		return getHessianService().getCount(orderType, memberId,0+"");
	}

	@Override
	public int getOrderStatusCount(Long id) throws Exception {
		return getHessianService().getOrderStatusCount(id);
	}

	@Override
	public int getmemberCarlineCountByMemberId(Long id) throws Exception {
		return getHessianService().getmemberCarlineCountByMemberId(id);
	}

	@Override
	public List<CarsDTO> getCarsDTOByMemberId(Long id) throws Exception {
		return getHessianService().getCarsDTOByMemberId(id);
	}

	@Override
	public int getStatusByOrderNo(String orderNo) throws Exception {
		return getHessianService().getStatusByOrderNo(orderNo);
	}

	@Override
	public List<String> getOrderNoByCondition(Map<String, Object> map) throws Exception {
		return getHessianService().getOrderNoByCondition(map);
	}

	@Override
	public int updateDeliverStatusByMemberAddressId(Long memberAddressId, Integer status) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().updateDeliverStatusByMemberAddressId(memberAddressId, status);
	}
	
	
}
