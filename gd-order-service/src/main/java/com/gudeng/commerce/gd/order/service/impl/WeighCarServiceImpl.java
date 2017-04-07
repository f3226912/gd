package com.gudeng.commerce.gd.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.SalToshopsDetailDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarOrderDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.commerce.gd.order.service.OrderOutmarketinfoService;
import com.gudeng.commerce.gd.order.service.WeighCarService;

/**
 * 过磅数据操作
 * @author Ailen
 */
@Service
public class WeighCarServiceImpl implements WeighCarService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private OrderOutmarketinfoService orderOutmarketinfoService;

	@Override
	public Long insertEntity(WeighCarEntity carEntity){
		return (Long)baseDao.persist(carEntity, Long.class);
	}

	@Override
	public int updateById(WeighCarEntity carEntity){
		return baseDao.execute("WeighCar.updateDTO", carEntity);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer)baseDao.queryForObject("WeighCar.getTotal", params, Integer.class);
	}
	
	@Override
	public WeighCarDTO getById(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("weighCarId", id);
		return (WeighCarDTO)baseDao.queryForObject("WeighCar.getById", param, WeighCarDTO.class);
	}

	@Override
	public List<WeighCarDTO> getWeighCar(Map<String, Object> map) {
		return baseDao.queryForList("WeighCar.getWeighCar", map, WeighCarDTO.class);
	}

	@Override
	public int udateTotalWeight(WeighCarEntity entity) {
		return baseDao.execute("WeighCar.updateTotalWeight", entity);
	}

	@Override
	public int updateTareWeight(WeighCarEntity entity) {
		return baseDao.execute("WeighCar.updateTareWeight", entity);
	}
	
	
	@Override
	public WeighCarDTO getLastWeighCar(Map<String, Object> map) {
		return baseDao.queryForObject("WeighCar.getLastWeighCar", map, WeighCarDTO.class);
	}
	

	@Override
	public List<WeighCarDTO> getLastFiveWeighCar(Map<String, Object> map) {
		return baseDao.queryForList("WeighCar.getLastFiveWeighCar", map, WeighCarDTO.class);
	}

	@Override
	public int getWeighCarNum(String carNumber, Date startDate, Date endDate) {
		Map<String, Object> params = new HashMap<>();
		
		/*
		 * init the base data
		 */
		String createTimeStart = null;
		String createTimeEnd = null;
		
		//formater init
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * format the date to sql matching
		 */
		createTimeStart = sdf.format(startDate);
		createTimeEnd = sdf.format(endDate);
		
		/*
		 * put the params
		 */
		params.put("createTimeEnd", createTimeEnd);
		params.put("createTimeStart", createTimeStart);
		params.put("carNumber", carNumber);
		
		return (Integer)baseDao.queryForObject("WeighCar.getNumWeighCarByCarNumberAndTime", params, Integer.class);
	}

	@Override
	public List<WeighCarDTO> getWeighCarPageForAdmin(Map<String, Object> map)
			throws Exception {
		List<WeighCarDTO> list = baseDao.queryForList("WeighCar.getListByConditionPageForAdmin", map, WeighCarDTO.class);
		
		/*
		 * 添加订单数据到对应的过磅表中
		 * reference orderOutmarketInfo
		 */
		for (int i = 0; i < list.size(); i++) {
			WeighCarDTO wcd = list.get(i);
			wcd.setOrders(orderOutmarketinfoService.getOrderInfoByOmId(wcd.getOmId()));
		}
		
		return list;
	}
	
	@Override
	public WeighCarDTO getByIdForAdmin(Long weighCarId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("weighCarId", weighCarId);
		WeighCarDTO wcd = baseDao.queryForObject("WeighCar.getByIdForAdmin", map, WeighCarDTO.class);
		
		/*
		 * 获得订单数据
		 */
		if(wcd!=null)
			wcd.setOrders(orderOutmarketinfoService.getOrderInfoByOmId(wcd.getOmId()));
		
		return wcd;
	}
	
	@Override
	public List<WeighCarDTO> getEntranceWeighList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("WeighCar.getEntranceWeighList", map, WeighCarDTO.class);
	}

	@Override
	public int getEntranceWeighListTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("WeighCar.getEntranceWeighListTotal", map, Integer.class);
	}

	@Override
	public WeighCarDTO getEntranceWeighById(String weighCarId) throws Exception {
		
		Map<String,Object> map=new HashMap<>();
		map.put("weighCarId", weighCarId);

		return baseDao.queryForObject("WeighCar.getEntranceWeighById", map,WeighCarDTO.class);
	}

	@Override
	public List<PreWeighCarDetailDTO> getProductListByWeighCarId(String weighCarId)
			throws Exception {
		Map<String, Object> map=new HashMap<>();
		map.put("weighCarId", weighCarId);
		return baseDao.queryForList("WeighCar.getProductListByWeighCarId", map,PreWeighCarDetailDTO.class);
	}

	@Override
	public List<WeighCarOrderDTO> getLastTwoDayWeighCarOrder(Long memberId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		return baseDao.queryForList("WeighCar.getLastTwoDayWeighCarOrder", paramMap, WeighCarOrderDTO.class);
	}

	@Override
	public int getLastTwoDayWeighCarOrderTotal(Long memberId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		return baseDao.queryForObject("WeighCar.getLastTwoDayWeighCarOrderTotal", paramMap, Integer.class);
	}

	@Override
	public List<SalToshopsDetailDTO> getOutProductListByWeighCarId(
			String weighCarId) throws Exception {
		Map<String, Object> map=new HashMap<>();
		map.put("weighCarId", weighCarId);
		return baseDao.queryForList("WeighCar.getOutProductListByWeighCarId", map,SalToshopsDetailDTO.class);
	}

	@Override
	public int updateStatus(WeighCarEntity weighCarEntity) {
		return baseDao.execute("WeighCar.updateStatus", weighCarEntity);
	}

	@Override
	public int updateStatusByCarId(String status, Long carId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("carId", carId);
		return baseDao.execute("WeighCar.updateStatusByCarId", map);
	}

	@Override
	public int updateStatusByWeighCarId(String status, Long weighCarId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("weighCarId", weighCarId);
		return baseDao.execute("WeighCar.updateStatusByWeighCarId", map);
	}

	@Override
	public List<WeighCarOrderDTO> getWeighCarOrderPage(Map<String, Object> map) {
		return baseDao.queryForList("WeighCar.getWeighCarOrderPage", map, WeighCarOrderDTO.class);
	}

	@Override
	public int getWeighCarOrderTotal(Map<String, Object> map) {
		return baseDao.queryForObject("WeighCar.getWeighCarOrderTotal", map, Integer.class);
	}
}
