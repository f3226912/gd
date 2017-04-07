package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.EGoodsType;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCountDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;
import com.thoughtworks.xstream.mapper.Mapper.Null;

public class NstOrderBaseinfoToolServiceimpl implements NstOrderBaseinfoToolService {

	@Autowired
	private GdProperties gdProperties;

	private static NstOrderBaseinfoService nstOrderBaseinfoService;

	private NstOrderBaseinfoService getHessianNstOrderBaseinfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getNstOrderBaseinfoUrl();
		if (nstOrderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderBaseinfoService = (NstOrderBaseinfoService) factory.create(NstOrderBaseinfoService.class,
					hessianUrl);
		}
		return nstOrderBaseinfoService;
	}

	@Override
	public Long insertNstOrderBaseinfo(NstOrderBaseinfoEntity nstOrderBaseinfoEntity) throws Exception {
		return getHessianNstOrderBaseinfoService().insertNstOrderBaseinfo(nstOrderBaseinfoEntity);
	}

	@Override
	public NstOrderBaseinfoDTO getByMemberAddressId(Long memberAddressId) throws Exception {
		return getHessianNstOrderBaseinfoService().getByMemberAddressId(memberAddressId);
	}

	@Override
	public NstOrderBaseinfoDTO getByOrderNo(String orderNo) throws Exception {
		return getHessianNstOrderBaseinfoService().getByOrderNo(orderNo);
	}

	@Override
	public Integer acceptOrder(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().acceptOrder(map);
	}

	@Override
	public Integer refuseOrder(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().refuseOrder(map);
	}

	@Override
	public NstOrderBaseinfoDTO getMemberInfoByMemberId(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getMemberInfoByMemberId(memberId);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getNstOrderListByCondition(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List<NstOrderBaseinfoDTO> list = getHessianNstOrderBaseinfoService().getNstOrderListByCondition(map);
		// ƴ�� �������͸�ǰ̨��ʾ
		if (list != null && list.size() != 0) {
			for (NstOrderBaseinfoDTO dto : list) {
				if (StringUtils.isNotEmpty(dto.getGoodsType())) {
					dto.setGoodsTypeString(EGoodsType.getValueByCode(Integer.parseInt(dto.getGoodsType())));
				}
				// ����ݿ����� ת����ʱ����
				if (dto.getOrderTime() != null) {
					dto.setOrderTimeString(DateUtil.getDate(dto.getOrderTime(), DateUtil.DATE_FORMAT_DATETIME));
				}
				dto.setSysTime(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME));
				if (StringUtils.isNotEmpty(dto.getStartTime())) {
					Date date=DateUtil.getDate(dto.getStartTime(), DateUtil.DATE_FORMAT_DATETIME);
					dto.setStartTime(DateUtil.getDate(date, DateUtil.DATE_FORMAT_DATETIME));
				}
			}
		}
		return list;
	}

	@Override
	public Integer confirmGoods(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().confirmGoods(map);
	}

	@Override
	public NstOrderBaseinfoDTO getCarLineInfoByCarLineId(Long carLineId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getCarLineInfoByCarLineId(carLineId);
	}
	
	@Override
	public NstOrderBaseinfoDTO getSameCarLineInfoByCarLineId(Long carLineId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getSameCarLineInfoByCarLineId(carLineId);
	}

	@Override
	public int getNstOrderListByConditionTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getNstOrderListByConditionTotal(map);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getOrderStatusCountByMemberId(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getOrderStatusCountByMemberId(memberId);
	}

	@Override
	public int getEvaluateTypeCountByMemberId(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getEvaluateTypeCountByMemberId(memberId);
	}

	@Override
	public List<String> getOrderNoByCondition(Map<String, Object> map) throws Exception {
		return getHessianNstOrderBaseinfoService().getOrderNoByCondition(map);
	}

	@Override
	public int getCount(String orderType, Long memberId,String sourceType) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getCount(orderType, memberId,sourceType);
	}

	@Override
	public int getOrderStatusCount(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getOrderStatusCount(id);
	}

	@Override
	public int getmemberCarlineCountByMemberId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getmemberCarlineCountByMemberId(id);
	}

	@Override
	public List<CarsDTO> getCarsDTOByMemberId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getCarsDTOByMemberId(id);
	}

	@Override
	public Integer cancelOrder(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().cancelOrder(map);
	}

	@Override
	public Integer batchDeleteOrder(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().batchDeleteOrder(map);
	}

	@Override
	public int getStatusByOrderNo(String orderNo) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getStatusByOrderNo(orderNo);
	}

@Override
	public int updateDeliverStatusByMemberAddressId(Long memberAddressId,
			Integer status) throws Exception {
		return getHessianNstOrderBaseinfoService().updateDeliverStatusByMemberAddressId(memberAddressId, status);
	}
	@Override
	public Integer getMemberSameCityOrderCount(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().getMemberSameCityOrderCount(memberId);
	}

	@Override
	public Integer saveStartTimeByOrderNo(String orderNo) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().saveStartTimeByOrderNo(orderNo);
	}
	@Override
	public Integer addressOrderStatus(Long id, Byte sourceType) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstOrderBaseinfoService().addressOrderStatus(id, sourceType);
	}

}
