package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.home.service.DetectionToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;
import com.gudeng.commerce.gd.supplier.service.DetectionService;

/**
 * 功能描述：
 */
@Service
public class DetectionToolServiceImpl implements DetectionToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static DetectionService detectionService;

	/*
	 * @Autowired private BaseDao baseDao;
	 */

	/**
	 * 功能描述:demo接口服务
	 * 
	 * @param
	 * @return
	 */
	protected DetectionService getHessianDetectionService()
			throws MalformedURLException {
		String url = gdProperties.getDetectionServiceUrl();
		if (detectionService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			detectionService = (DetectionService) factory.create(
					DetectionService.class, url);
		}
		return detectionService;
	}

	@Override
	public List<DetectionDTO> getDetectionList(Map<String, Object> map)
			throws Exception {
		return this.getHessianDetectionService().getDetectionList(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return this.getHessianDetectionService().getTotal(map);
	}

	@Override
	public DetectionDTO getById(String id) throws Exception {
		return this.getHessianDetectionService().getById(id);
	}

	@Override
	public int addDetectionDTO(DetectionDTO detectionDTO) throws Exception {
		return this.getHessianDetectionService().addDetectionDTO(detectionDTO);
	}

	@Override
	public int updateDetectionDTO(DetectionDTO detectionDTO) throws Exception {
		return this.getHessianDetectionService().updateDetectionDTO(
				detectionDTO);
	}

	@Override
	public int deleteDetection(List<String> list) throws Exception {
		return this.getHessianDetectionService().deleteDetection(list);
	}

	@Override
	public int checkExsit(Map<String, Object> map) throws Exception {
		return this.getHessianDetectionService().checkExsit(map);
	}
}
