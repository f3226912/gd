package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PreWeighCarDetailToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.service.PreWeighCarDetailService;

/**
 * 货主补贴
 * @author Ailen
 *
 */
@Service
public class PreWeighCarDetailToolServiceImpl implements PreWeighCarDetailToolService{
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;

	private static PreWeighCarDetailService preWeighCarDetailService;
	
	/**
	 * 功能描述:接口服务
	 * 
	 * @param
	 * @return
	 */
	protected PreWeighCarDetailService getHessianPreWeighCarDetailService() throws MalformedURLException {
		String url =  gdProperties.getPreWeighCarDetailServiceUrl();
		if(preWeighCarDetailService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			preWeighCarDetailService = (PreWeighCarDetailService) factory.create(AreaService.class, url);
		}
		return preWeighCarDetailService;
	}

	@Override
	public List<PreWeighCarDetailDTO> getByWeighCarId(Long weighCarId) throws Exception {
		return getHessianPreWeighCarDetailService().getByWeighCarId(weighCarId);
	}

	@Override
	public PreWeighCarDetailDTO getById(Long id) throws Exception {
		return getHessianPreWeighCarDetailService().getById(id);
	}

	@Override
	public List<PreWeighCarDetailDTO> getByBusinessUserId(Map map) throws Exception {
		return getHessianPreWeighCarDetailService().getByBusinessUserId(map);
	}

	@Override
	public int getCountByUserId(Long userId) throws Exception {
		return getHessianPreWeighCarDetailService().getCountByUserId(userId);
	}

	@Override
	public int getCountByMobile(String mobile) throws Exception {
		return getHessianPreWeighCarDetailService().getCountByMobile(mobile);
	}

	@Override
	public List<PreWeighCarDetailDTO> getByCategoryUserId(Long userId) throws Exception {
		return getHessianPreWeighCarDetailService().getByCategoryUserId(userId);
	}

	@Override
	public List<PreWeighCarDetailDTO> getByMobile(Map map) throws Exception {
		return getHessianPreWeighCarDetailService().getByMobile(map);
	}

	@Override
	public int update(PreWeighCarDetailDTO preWeighCarDetailDTO) throws Exception {
		return getHessianPreWeighCarDetailService().update(preWeighCarDetailDTO);
	}

	@Override
	public int deleteBusiness(Long weighCarId, Long businessId) throws Exception {
		return getHessianPreWeighCarDetailService().deleteBusiness(weighCarId, businessId);
	}

	@Override
	public int grapGoods(PreWeighCarDetailDTO pwd, Long inStoreNo,
			Long businessId) throws Exception {
		return getHessianPreWeighCarDetailService().grapGoods(pwd,inStoreNo,businessId);
	}

	@Override
	public int getTotalForAdmin(Map<String, Object> params) throws Exception {
		return getHessianPreWeighCarDetailService().getTotalForAdmin(params);
	}

	@Override
	public List<PreWeighCarDetailDTO> getByConditionPageForAdmin(
			Map<String, Object> params) throws Exception {
		return getHessianPreWeighCarDetailService().getByConditionPageForAdmin(params);
	}

	@Override
	public Map<String, Object> getParamsMap(PreWeighCarDetailDTO dto) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("totalStartTime", dto.getTotalStartTime());
		params.put("totalEndTime", dto.getTotalEndTime());
		params.put("tareStartTime", dto.getTareStartTime());
		params.put("tareEndTime", dto.getTareEndTime());
		params.put("paymentStatus", dto.getPaymentStatus());
		params.put("account", dto.getAccount());
		params.put("inStoreNo", dto.getInStoreNo());
		
		return params;
	}

	@Override
	public int batchUpdatePaymentStatus(String pwds,String updateUserId) throws Exception {
		return getHessianPreWeighCarDetailService().batchUpdatePaymentStatus(pwds,updateUserId);
	}
	

}
