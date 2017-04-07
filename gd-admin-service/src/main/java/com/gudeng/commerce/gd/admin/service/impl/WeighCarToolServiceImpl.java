package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.WeighCarToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.SalToshopsDetailDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.commerce.gd.order.service.WeighCarService;


/** 
 *功能描述：
 */
@Service
public class WeighCarToolServiceImpl implements WeighCarToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static WeighCarService weighCarService;

	/**
	 * 功能描述: businessBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected WeighCarService getHessianWeighCarService() throws MalformedURLException {
		String url = gdProperties.getWeighCarServiceUrl();
		if(weighCarService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			weighCarService = (WeighCarService) factory.create(WeighCarService.class, url);
		}
		return weighCarService;
	}

	@Override
	public WeighCarDTO getById(String id) throws Exception {
		Long idL = null;
		if(id!=null && "".equals(id)) {
			idL = Long.parseLong(id);
		} else {
			return null;
		}
		return getHessianWeighCarService().getById(idL);
	}

	@Override
	public List<WeighCarDTO> getByConditionPage(Map<String, Object> params)
			throws Exception {
		return getHessianWeighCarService().getWeighCarPageForAdmin(params);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianWeighCarService().getTotal(map);
	}

	@Override
	public int updateWeighCarEntity(WeighCarEntity wc) throws Exception {
		return getHessianWeighCarService().updateById(wc);
	}

	@Override
	public Map<String, Object> getParamsMap(WeighCarDTO weighCarDTO) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("account", weighCarDTO.getAccount());
		params.put("tareStartTime", weighCarDTO.getTareStartTime());
		params.put("tareEndTime", weighCarDTO.getTareEndTime());
		params.put("totalStartTime", weighCarDTO.getTotalStartTime());
		params.put("totalEndTime", weighCarDTO.getTotalEndTime());
		params.put("carNumber", weighCarDTO.getCarNumber());
		params.put("orderNo", weighCarDTO.getOrderNo());
		if(!"-1".equals(weighCarDTO.getTapWeight())) {
			params.put("tapWeight", weighCarDTO.getTapWeight());
		}
		return params;
	}

	@Override
	public List<WeighCarDTO> getEntranceWeighList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianWeighCarService().getEntranceWeighList(map);
	}

	@Override
	public int getEntranceWeighListTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianWeighCarService().getEntranceWeighListTotal(map);
	}

	@Override
	public WeighCarDTO getByIdForAdmin(Long weighCarId) throws Exception {
		return getHessianWeighCarService().getByIdForAdmin(weighCarId);
	}

	@Override
	public WeighCarDTO getEntranceWeighById(String weighCarId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianWeighCarService().getEntranceWeighById(weighCarId);
	}

	@Override
	public List<PreWeighCarDetailDTO> getProductListByWeighCarId(String weighCarId)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianWeighCarService().getProductListByWeighCarId(weighCarId);
	}

	@Override
	public List<SalToshopsDetailDTO> getOutProductListByWeighCarId(
			String weighCarId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianWeighCarService().getOutProductListByWeighCarId(weighCarId);
	}

}
