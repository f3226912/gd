package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ComplaintToolService;
import com.gudeng.commerce.gd.admin.service.DemoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ComplaintEntityDTO;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.customer.service.ComplaintService;
import com.gudeng.commerce.gd.customer.service.DemoService;


/**
 *功能描述：
 */
@Service
public class ComplaintToolServiceImpl implements ComplaintToolService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static ComplaintService complaintService;

	/**
	 * 功能描述 投诉建议接口服务
	 *
	 * @param
	 * @return
	 */
	protected ComplaintService getHessianComplaintService() throws MalformedURLException {
		String url = gdProperties.getComplaintUrl();
		if(complaintService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			complaintService = (ComplaintService) factory.create(ComplaintService.class, url);
		}
		return complaintService;
	}



	@Override
	public List<ComplaintEntityDTO> getComplaintList(Map<String, Object> map) throws MalformedURLException {
		return getHessianComplaintService().getComplaintList(map);
	}



	@Override
	public ComplaintEntityDTO getComplaint(Long id) throws MalformedURLException {
		return getHessianComplaintService().getComplaint(id);

	}
	@Override
	public Integer replyComplaintSave(ComplaintEntityDTO complaintEntityDTO) throws MalformedURLException {
		return getHessianComplaintService().replyComplaintSave(complaintEntityDTO);
	}



	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianComplaintService().getTotal(map);
	}
}
