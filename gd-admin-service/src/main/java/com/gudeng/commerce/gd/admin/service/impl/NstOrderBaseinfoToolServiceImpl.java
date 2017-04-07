package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderComplaintDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCountDTO;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;
import com.gudeng.commerce.gd.customer.service.NstOrderCommentService;
import com.gudeng.commerce.gd.customer.service.NstOrderComplaintService;

public class NstOrderBaseinfoToolServiceImpl implements NstOrderBaseinfoToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private static NstOrderBaseinfoService nstOrderBaseinfoService;
	
	private static NstOrderCommentService nstOrderCommentService;
	
	private static NstOrderComplaintService nstOrderComplaintService;
	
	private NstOrderBaseinfoService getHessianService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.nstOrderBaseinfo.url");
		if(nstOrderBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderBaseinfoService = (NstOrderBaseinfoService) factory.create(NstOrderBaseinfoService.class, hessianUrl);
		}
		return nstOrderBaseinfoService;
	}
	
	private NstOrderCommentService getNstOrderCommentHessianService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.nstOrderComment.url");
		if(nstOrderCommentService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderCommentService = (NstOrderCommentService) factory.create(NstOrderCommentService.class, hessianUrl);
		}
		return nstOrderCommentService;
	}
	
	
	private NstOrderComplaintService getNstOrderComplaintHessianService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.nstOrderComplaint.url");
		if(nstOrderComplaintService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderComplaintService = (NstOrderComplaintService) factory.create(NstOrderComplaintService.class, hessianUrl);
		}
		return nstOrderComplaintService;
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
		// TODO Auto-generated method stub
		return getHessianService().getCommentListByCondition(map);
	}

	@Override
	public int getCommentTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getCommentTotal(map);
	}

	@Override
	public NstOrderCommentDTO getById(String id) throws Exception {
		// TODO Auto-generated method stub
		return getNstOrderCommentHessianService().getById(id);
	}

	
	@Override
	public List<NstOrderBaseinfoDTO> getOrderComplaintListByCondition(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getNstOrderComplaintHessianService().getOrderComplaintListByCondition(map);
	}

	@Override
	public int getOrderComplaintTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getNstOrderComplaintHessianService().getOrderComplaintTotal(map);
	}

	@Override
	public NstOrderBaseinfoDTO getNstOrderComplaintById(String id)
			throws Exception {
		return getNstOrderComplaintHessianService().getById(id);
	}

	@Override
	public int updateStatus(NstOrderComplaintDTO dto) throws Exception {
		return getNstOrderComplaintHessianService().updateStatus(dto);
	}

	@Override
	public List<NstOrderCountDTO> getOrderCountList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getOrderCountList(map);
	}

	@Override
	public NstOrderCountDTO getOrderCountDetailByOrderNo(String orderNo)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getOrderCountDetailByOrderNo(orderNo);
	}

	@Override
	public Long getOrderCountListTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getOrderCountListTotal(map);
	}

	@Override
	public List<NstOrderCountDTO> getSameCityOrderList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getSameCityOrderList(map);
	}

	@Override
	public Long getSameCityOrderTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getSameCityOrderTotal(map);
	}

	@Override
	public NstOrderCountDTO getSameCityOrderDetailByOrderNo(String orderNo)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getSameCityOrderDetailByOrderNo(orderNo);
	}

	@Override
	public NstOrderBaseinfoDTO getSameCityOrderById(String id) throws Exception {
		// TODO Auto-generated method stub
		return getNstOrderComplaintHessianService().getSameCityOrderById(id);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getSameCityOrdersByCondition(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getSameCityOrdersByCondition(map);
	}

	@Override
	public int getSameCityOrdersTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getSameCityOrdersTotal(map);
	}
	
}
