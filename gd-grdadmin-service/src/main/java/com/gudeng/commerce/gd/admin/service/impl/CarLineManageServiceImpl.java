package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.CarLineManageService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PublishCountDTO;
import com.gudeng.commerce.gd.customer.service.CarLineService;
import com.gudeng.commerce.gd.customer.service.NstSameCityCarlineService;

@Service
public class CarLineManageServiceImpl implements  CarLineManageService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static CarLineService carLineService;
	
	
	private static NstSameCityCarlineService nstSameCityCarlineService;
	/**
	 * 功能描述:干线线路接口服务
	 * 
	 * @param
	 * @return
	 */
	protected CarLineService getHessianCarLineService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.carLine.url");
		if(carLineService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carLineService = (CarLineService) factory.create(CarLineService.class, url);
		}
		return carLineService;
	}
	
	
	/**
	 * 功能描述:同城线路接口服务
	 * @return
	 * @throws MalformedURLException
	 */
	protected NstSameCityCarlineService getHessianSameCityCarLineService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.sameCity.carLine.url");
		if(nstSameCityCarlineService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstSameCityCarlineService = (NstSameCityCarlineService) factory.create(NstSameCityCarlineService.class, url);
		}
		return nstSameCityCarlineService;
	}
	
	@Override
	public CarLineDTO getById(String id) throws Exception {
		return getHessianCarLineService().getById(id);
	}

	@Override
	public List<CarLineDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return  getHessianCarLineService().getByCondition(map);
	}

	@Override
	public List<CarLineDTO> getByName(Map<String, Object> map) throws Exception {
		return   getHessianCarLineService().getByName(map);
	}

	@Override
	public List<CarLineDTO> getAllByType(String sendGoodsType) throws Exception {
		return getHessianCarLineService().getAllByType(sendGoodsType);
	}
	
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCarLineService().getTotal(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		 return getHessianCarLineService().deleteById(id);
	}

	@Override
	public int addCarLineDTO(Map<String,Object> map)throws Exception{
		 return getHessianCarLineService().addCarLineDTO(map);
	}
	@Override
	public int addCarLineDTO(CarLineDTO car) throws Exception {
		 return getHessianCarLineService().addCarLineDTO(car);
	}
	@Override
	public int updateCarLineDTO(CarLineDTO car) throws Exception {
		 return getHessianCarLineService().updateCarLineDTO(car);
	}
	
	@Override
	public List<CarLineDTO> queryCarLineList(Map<String, Object> map) throws Exception {
		return getHessianCarLineService().queryCarLineList(map);
	}

	@Override
	public Integer updateCarLineByid(String carLineIds) throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().updateCarLineByid(carLineIds);
	}

	@Override
	public List<PublishCountDTO> memberPublishCarLine(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().memberPublishCarLine(map);
	}

	@Override
	public Integer getMemberPublishCarLineCount(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().getMemberPublishCarLineCount(map);
	}

	@Override
	public List<PublishCountDTO> memberPublishCar(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().memberPublishCar(map);
	}

	@Override
	public Integer getMemberPublishCarCount(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().getMemberPublishCarCount(map);
	}
	
	@Override
	public int getTotalForConsole(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return  getHessianCarLineService().getTotalForConsole(map);
	}

	@Override
	public int getTotalForSameCityList(Map<String, Object> map)
			throws Exception {
		return  this.getHessianSameCityCarLineService().getTotalForConsole(map);
	}

	@Override
	public List<NstSameCityCarlineEntityDTO> queryForSameCityList(
			Map<String, Object> map) throws Exception {
		return this.getHessianSameCityCarLineService().queryListForConsole(map);
	}


	@Override
	public List<PublishCountDTO> memberPublishCarSameCity(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().memberPublishCarSameCity(map);
	}


	@Override
	public Integer getMemberPublishCarCountSameCity(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().getMemberPublishCarCountSameCity(map);
	}


	@Override
	public List<PublishCountDTO> memberPublishCarLineSameCity(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().memberPublishCarLineSameCity(map);
	}


	@Override
	public Integer getMemberPublishCarLineCountSameCity(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianCarLineService().getMemberPublishCarLineCountSameCity(map);
	}
}
	
