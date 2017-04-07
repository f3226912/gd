package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.IntegralScoreToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActActivityScoreRecordDTO;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.service.ActActivityScoreRecordService;

/**
 * 功能描述：
 */
@Service
public class IntegralScoreToolServiceImpl implements IntegralScoreToolService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static ActActivityScoreRecordService actActivityScoreRecord;

	/**
	 * 功能描述: memberBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ActActivityScoreRecordService getHessianIntegralScoreService() throws MalformedURLException {
		String url = gdProperties.getActivityScoreRecordServiceUrl();
		if (actActivityScoreRecord == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			actActivityScoreRecord = (ActActivityScoreRecordService) factory.create(ActActivityScoreRecordService.class, url);
		}
		return actActivityScoreRecord;
	}

	@Override
	public Long addIntegralEntity(ActActivityScoreRecordEntity entity) throws Exception {
		return getHessianIntegralScoreService().add(entity);
	}
	
	@Override
	public Integer updatentegralDto(ActActivityScoreRecordDTO dto) throws Exception {
		return getHessianIntegralScoreService().update(dto);
	}
	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		return getHessianIntegralScoreService().getTotal(map);
	}

	@Override
	public List<ActActivityScoreRecordDTO> queryPageByCondition(Map<String, Object> map) throws Exception {
		return getHessianIntegralScoreService().queryPageByCondition(map);
	}

	@Override
	public Long addIntegralRecord(ActActivityScoreRecordDTO dto)throws Exception {
		return getHessianIntegralScoreService().addIntegralRecord(dto);
	}

	@Override
	public List<ActActivityScoreRecordDTO> queryListByCondition(Map<String, Object> map) throws Exception {
		return getHessianIntegralScoreService().queryListByCondition(map);
	}

	@Override
	public ActActivityScoreRecordDTO getById(Integer id) throws Exception {
		return getHessianIntegralScoreService().getById(id);
	}

	@Override
	public int updateIntegralRecord(ActActivityScoreRecordDTO dto) throws Exception{
		return getHessianIntegralScoreService().updateIntegralRecord(dto);
	}
}
