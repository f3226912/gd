package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;
import com.gudeng.commerce.gd.customer.service.CarsService;


/**
 *功能描述：车辆管理
 */
@Service
@SuppressWarnings("unchecked")
public class CarsServiceImpl implements CarsService{
	
	@Autowired
	private BaseDao  baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public CarsDTO getById(String id) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("id", id);
		return (CarsDTO)this.baseDao.queryForObject("Car.getCar", p, CarsDTO.class);
	}

	@Override
	public List<CarsDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		List<CarsDTO> list= baseDao.queryForList("Car.getByCondition", map, CarsDTO.class);
		return list;
	}
	
	
	@Override
	public List<CarsDTO> getAllByType(String type) 	throws Exception {
		
		Map <String, String> map = new HashMap<String, String>();
		map.put("carType", type);
		List<CarsDTO> list= baseDao.queryForList("Car.getAllByType", map, CarsDTO.class);
		return list;
	}
	
	

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Car.getTotal", map, Integer.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		int len = id.split(",").length;
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(id.split(",")[i]));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("Car.deleteCarsDTO", batchValues).length;
		
	}

	@Override
	public int addCarsDTO(Map<String, Object> map) throws Exception {
		return (int) baseDao.execute("Car.addCarsDTO", map);
	}
	
	@Override
	public int addCarsDTO(CarsDTO dto) throws Exception {
		return (int) baseDao.execute("Car.addCarsDTO", dto);
	}

	@Override
	public int updateCarsDTO(CarsDTO dto) throws Exception {
		
		return (int) baseDao.execute("Car.updateCarsDTO", dto);
	}

	
	@Override
	public List<CarsDTO> queryByParameters(Map<String, Object> map) {
		List<CarsDTO> list= baseDao.queryForList("Car.queryByParameters", map, CarsDTO.class);
		return list;
	}

	@Override
	public List<CarsDTO> listCarByUserId(Long userId) throws Exception {
		Map <String, Long> p = new HashMap<String, Long>();
		//String userIds=Long.toString(userId);
		p.put("userId", userId);
		List<CarsDTO> list= baseDao.queryForList("Car.getByUserId", p, CarsDTO.class);
		return list;
	}
	
	@Override
	public List<CarsDTO>  listCarNumber(CarsDTO carsDTO) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		//String userIds=Long.toString(userId);
		p.put("userId", carsDTO.getUserId());
		p.put("interfaceType", carsDTO.getInterfaceType());
		List<CarsDTO> list= baseDao.queryForList("Car.getlistCarNumber", p, CarsDTO.class);
		return list;
	}

	@Override
	public CarsDTO getCarTotal(Long userId) throws Exception {
				Map <String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		return (CarsDTO) baseDao.queryForObject("Car.getlistCarNumberTotal", p,CarsDTO.class);
	}
	/**
	 * 根据车牌号查询车
	 */
	@Override
	public CarsDTO getCarByCarNumber(String carNumber) throws Exception {
		Map<String, Object> map =new HashMap<>();
		map.put("carNumber", carNumber);
		
		return (CarsDTO) baseDao.queryForObject("Car.selectCarByCarNumber", map, CarsDTO.class);
	}
	
	
	/**
	 * 根据entUserId查询
	 */
	@Override
	public CarsDTO getByEntUserId(String entUserId) throws Exception {
		Map<String, Object> map =new HashMap<>();
		map.put("entUserId", entUserId);
		
		return (CarsDTO) baseDao.queryForObject("Car.getByEntUserId", map, CarsDTO.class);
	}


	@Override
	public int addActity(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Car.addActity", map, Integer.class);
	}

	@Override
	public List<RecommendedUserDTO> getRecommendedUserList(
			Map<String, Object> map) throws Exception {
		List<RecommendedUserDTO> list= baseDao.queryForList("Car.getRecommendedUserList", map, RecommendedUserDTO.class);
		return list;
	}

	@Override
	public int getRecommendedUserTotal(Map<String, Object> map)
			throws Exception {
		return (int) baseDao.queryForObject("Car.getRecommendedUserTotal", map, Integer.class);

	}

	@Override
	public List<RecommendedUserDTO> getNotRelationUserList(
			Map<String, Object> map) throws Exception {
		List<RecommendedUserDTO> list= baseDao.queryForList("Car.getNotRelationUserList", map, RecommendedUserDTO.class);
		return list;
	}

	@Override
	public int getNotRelationUserTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.queryForObject("Car.getNotRelationUserTotal", map, Integer.class);
	}
	
	@Override
	public RecommendedUserDTO getUserInfoCount(String mobile,
			String carStartDate, String carEndDate, String carLineStartDate,
			String carLineEndDate)
			throws Exception {
		Map<String, Object> map =new HashMap<>();
		map.put("mobile", mobile);
		map.put("carStartDate", carStartDate);
		map.put("carEndDate", carEndDate);
		map.put("carLineStartDate", carLineStartDate);
		map.put("carLineEndDate", carLineEndDate);
		return (RecommendedUserDTO)this.baseDao.queryForObject("Car.getUserInfoCount", map, RecommendedUserDTO.class);
	
	}
	

	@Override
	public int delTotalCars(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> map =new HashMap<>();
		map.put("carId", id);
		return (int) baseDao.queryForObject("Car.delTotalCars", map, Integer.class);
	}
	
	
	@Override
	public List<RecommendedUserDTO> getRecommendedUserListByCallTime(
			Map<String, Object> map) throws Exception {
		List<RecommendedUserDTO> list= baseDao.queryForList("Car.getUserListByCallTime", map, RecommendedUserDTO.class);
		return list;
	}

	@Override
	public int getTotalByCallTime(Map<String, Object> map)
			throws Exception {
		return (int) baseDao.queryForObject("Car.getTotalByCallTime", map, Integer.class);

	}

	@Override
	public int addCarMessageVity(CarsDTO carsDTO) {
		// TODO Auto-generated method stub
		Map<String, Object> map =new HashMap<>();
		map.put("carNumber", carsDTO.getCarNumber());
		return (int) baseDao.queryForObject("Car.addCarMessageVity", map, Integer.class);
	}

	@Override
	public List<CarsDTO> queryByCarNumber(String carNumber, boolean isDeleted) throws Exception {
		Map<String, Object> paramMap =new HashMap<>(2);
		paramMap.put("carNumber", carNumber);
		//对应数据库的字段isDeleted（1表示已经删除，0表示未删除）。
		if(isDeleted) {
			paramMap.put("isDeleted", 1);
		} else {
			paramMap.put("isDeleted", 0);
		}
		return baseDao.queryForList("Car.queryByCarNumber", paramMap, CarsDTO.class);
	}
	
	
}
