package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.CarsManageService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;
import com.gudeng.commerce.gd.customer.service.CarsService;

@Service
public class CarsManageServiceImpl implements  CarsManageService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static CarsService carsService;
	
	/**
	 * 功能描述:车辆管理接口服务
	 * 
	 * @param
	 * @return
	 */
	protected CarsService getHessianCarsService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.cars.url");
		if(carsService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carsService = (CarsService) factory.create(CarsService.class, url);
		}
		return carsService;
	}
	
	@Override
	public CarsDTO getById(String id) throws Exception {
		return getHessianCarsService().getById(id);
	}

	@Override
	public List<CarsDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return  getHessianCarsService().getByCondition(map);
	}

	@Override
	public List<CarsDTO> queryByParameters(Map<String, Object> map) throws Exception {
		return   getHessianCarsService().queryByParameters(map);
	}
	
	
	@Override
	public List<CarsDTO> getAllByType(String sendGoodsType) throws Exception {
		return getHessianCarsService().getAllByType(sendGoodsType);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCarsService().getTotal(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		 return getHessianCarsService().deleteById(id);
	}

	@Override
	public int addCarsDTO(Map<String,Object> map)throws Exception{
		 return getHessianCarsService().addCarsDTO(map);
	}
	@Override
	public int addCarsDTO(CarsDTO car) throws Exception {
		 return getHessianCarsService().addCarsDTO(car);
	}
	@Override
	public int updateCarsDTO(CarsDTO car) throws Exception {
		 return getHessianCarsService().updateCarsDTO(car);
	}
	@Override
	public CarsDTO selectCarByCarNumber(String carNumber) throws Exception {
	
		return getHessianCarsService().getCarByCarNumber(carNumber);
	}

	@Override
	public List<RecommendedUserDTO> getRecommendedUserList(
			Map<String, Object> map) throws Exception {
		return getHessianCarsService().getRecommendedUserList(map);
	}

	@Override
	public int getRecommendedUserTotal(Map<String, Object> map)
			throws Exception {
		return getHessianCarsService().getRecommendedUserTotal(map);
	}

	@Override
	public CarsDTO getByEntUserId(String entUserId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarsService().getByEntUserId(entUserId);
	}

	@Override
	public List<RecommendedUserDTO> getNotRelationUserList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarsService().getNotRelationUserList(map);
	}

	@Override
	public int getNotRelationUserTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarsService().getNotRelationUserTotal(map);
		
	}

	@Override
	public List<RecommendedUserDTO> getRecommendedUserListByCallTime(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarsService().getRecommendedUserListByCallTime(map);
	}

	@Override
	public int getTotalByCallTime(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarsService().getTotalByCallTime(map);
	}
	
	@Override
	public RecommendedUserDTO getUserInfoCount(String mobile,
			String carStartDate, String carEndDate, String carLineStartDate,
			String carLineEndDate)
			throws Exception {
		return getHessianCarsService().getUserInfoCount(mobile, carStartDate , carEndDate , carLineStartDate, carLineEndDate);
	}
	
	
	
}
	
