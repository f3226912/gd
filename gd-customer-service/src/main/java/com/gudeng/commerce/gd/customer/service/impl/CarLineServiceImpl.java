package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.PublishCountDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkCarLineDTO;
import com.gudeng.commerce.gd.customer.service.CarLineService;


/**
 *功能描述：车辆专线管理
 */
@Service
@SuppressWarnings("unchecked")
public class CarLineServiceImpl implements CarLineService{
	
	@Autowired
	private BaseDao  baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public CarLineDTO getById(String id) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("id", id);
		return (CarLineDTO)this.baseDao.queryForObject("CarLine.getCarLine", p, CarLineDTO.class);
	}

	@Override
	public List<CarLineDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getByCondition", map, CarLineDTO.class);
		return list;
	}
	
	
	@Override
	public List<CarLineDTO> getAllByType(String sendGoodsType)throws Exception {
		Map <String, String> map = new HashMap<String, String>();
		map.put("sendGoodsType", sendGoodsType);
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getCarLineList", map, CarLineDTO.class);
		return list;
	}
	

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("CarLine.getTotal", map, Integer.class);
	}

	@Override
	@Deprecated
	public int deleteById(String id) throws Exception {
		int len = id.split(",").length;
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(id.split(",")[i]));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("CarLine.deleteCarLineDTO", batchValues).length;
		
	}

	@Override
	public int addCarLineDTO(Map<String, Object> map) throws Exception {
		return (int) baseDao.execute("CarLine.addCarLineDTO", map);
	}
	
	@Override
	public int addCarLineDTO(CarLineDTO dto) throws Exception {
		return (int) baseDao.execute("CarLine.addCarLineDTO", dto);
	}

	@Override
	public int updateCarLineDTO(CarLineDTO dto) throws Exception {
		
		return (int) baseDao.execute("CarLine.updateCarLineDTO", dto);
	}

	@Override
	public List<CarLineDTO> getByName(Map<String, Object> map) {
		List<CarLineDTO> list= baseDao.queryForList("CarLine.queryByParameters", map, CarLineDTO.class);
		return list;
	}

	@Override
	public List<CarLineDTO> getCarlineApiByCondition(CarLineDTO carLineDTO)
			throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", carLineDTO.getId());
		p.put("startCity", carLineDTO.getStartCity());
		p.put("endCity", carLineDTO.getEndCity());
		p.put("maxLoad", carLineDTO.getMaxLoad());
		p.put("carLength", carLineDTO.getCarLength());
		p.put("carType", carLineDTO.getCarType());
		p.put("userId", carLineDTO.getMemberId());
		p.put("s_provinceId", carLineDTO.getS_provinceId());
		p.put("s_cityId", carLineDTO.getS_cityId());
		p.put("s_areaId", carLineDTO.getS_areaId());
		p.put("e_provinceId", carLineDTO.getE_provinceId());
		p.put("e_cityId", carLineDTO.getE_cityId());
		p.put("e_areaId", carLineDTO.getE_areaId());
		p.put("userId", carLineDTO.getUserId());
		p.put("mlng", carLineDTO.getMlng());
		p.put("mlat", carLineDTO.getMlat());
		p.put("bjlng", carLineDTO.getBjlng());
		p.put("bjlat", carLineDTO.getBjlat());
		p.put("cqlng", carLineDTO.getCqlng());
		p.put("cqlat", carLineDTO.getCqlat());
		p.put("shlng", carLineDTO.getShlng());
		p.put("shlat", carLineDTO.getShlat());
		p.put("tjlng", carLineDTO.getTjlng());
		p.put("tjlat", carLineDTO.getTjlat());
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getCarlineApiByCondition", p, CarLineDTO.class);
		return list;
	}

	@Override
	public int repalyCarLine(CarLineDTO carLineDTO) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", carLineDTO.getId());
		p.put("updateUserId", carLineDTO.getUpdateUserId());
		return (int) baseDao.execute("CarLine.repalyCarLine", p);
	}
	

	@Override
	public List<CarLineDTO> queryCarLineList(Map<String, Object> map) throws Exception {
		List<CarLineDTO> list= baseDao.queryForList("CarLine.queryCarLineList", map, CarLineDTO.class);
		return list;
	}
	
	
	@Override
	public List<CarLineDTO> getListByAreaId(Map<String, Object> map) throws Exception {
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getListByAreaId", map, CarLineDTO.class);
		return list;
	}

	@Override
	public int getCountByCondition(CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
			Map <String, Object> p = new HashMap<String, Object>();
			p.put("id", carLineDTO.getId());
			p.put("startCity", carLineDTO.getStartCity());
			p.put("endCity", carLineDTO.getEndCity());
			p.put("maxLoad", carLineDTO.getMaxLoad());
			p.put("carLength", carLineDTO.getCarLength());
			p.put("carType", carLineDTO.getCarType());
			p.put("userId", carLineDTO.getMemberId());
			p.put("s_provinceId", carLineDTO.getS_provinceId());
			p.put("s_cityId", carLineDTO.getS_cityId());
			p.put("s_areaId", carLineDTO.getS_areaId());
			p.put("e_provinceId", carLineDTO.getE_provinceId());
			p.put("e_cityId", carLineDTO.getE_cityId());
			p.put("e_areaId", carLineDTO.getE_areaId());
			p.put("userId", carLineDTO.getUserId());
			p.put("mlng", carLineDTO.getMlng());
			p.put("mlat", carLineDTO.getMlat());
			p.put("bjlng", carLineDTO.getBjlng());
			p.put("bjlat", carLineDTO.getBjlat());
			p.put("cqlng", carLineDTO.getCqlng());
			p.put("cqlat", carLineDTO.getCqlat());
			p.put("shlng", carLineDTO.getShlng());
			p.put("shlat", carLineDTO.getShlat());
			p.put("tjlng", carLineDTO.getTjlng());
			p.put("tjlat", carLineDTO.getTjlat());
			return (int)baseDao.queryForObject("CarLine.getCountCarlineApiByCondition", p, Integer.class);
		}

	@Override
	public List<CarLineDTO> getCarlineApiByConditionNew(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", carLineDTO.getId());
		p.put("startCity", carLineDTO.getStartCity());
		p.put("endCity", carLineDTO.getEndCity());
		p.put("maxLoad", carLineDTO.getMaxLoad());
		p.put("carLength", carLineDTO.getCarLength());
		p.put("carType", carLineDTO.getCarType());
		p.put("userId", carLineDTO.getMemberId());
		p.put("s_provinceId", carLineDTO.getS_provinceId());
		p.put("s_cityId", carLineDTO.getS_cityId());
		p.put("s_areaId", carLineDTO.getS_areaId());
		p.put("e_provinceId", carLineDTO.getE_provinceId());
		p.put("e_cityId", carLineDTO.getE_cityId());
		p.put("e_areaId", carLineDTO.getE_areaId());
		p.put("userId", carLineDTO.getUserId());
		p.put("startRow", carLineDTO.getStartRow());
		p.put("endRow", carLineDTO.getEndRow());
		p.put("mlng", carLineDTO.getMlng());
		p.put("mlat", carLineDTO.getMlat());
		p.put("bjlng", carLineDTO.getBjlng());
		p.put("bjlat", carLineDTO.getBjlat());
		p.put("cqlng", carLineDTO.getCqlng());
		p.put("cqlat", carLineDTO.getCqlat());
		p.put("shlng", carLineDTO.getShlng());
		p.put("shlat", carLineDTO.getShlat());
		p.put("tjlng", carLineDTO.getTjlng());
		p.put("tjlat", carLineDTO.getTjlat());
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getCarlineApiByConditionNew", p, CarLineDTO.class);
		return list;
	}

	@Override
	public List<CarLineDTO> getCarlineApiMessage(CarLineDTO carLineDTO)
			throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", carLineDTO.getId());
		p.put("startCity", carLineDTO.getStartCity());
		p.put("endCity", carLineDTO.getEndCity());
		p.put("maxLoad", carLineDTO.getMaxLoad());
		p.put("carLength", carLineDTO.getCarLength());
		p.put("carType", carLineDTO.getCarType());
		p.put("userId", carLineDTO.getMemberId());
		p.put("s_provinceId", carLineDTO.getS_provinceId());
		p.put("s_cityId", carLineDTO.getS_cityId());
		p.put("s_areaId", carLineDTO.getS_areaId());
		p.put("e_provinceId", carLineDTO.getE_provinceId());
		p.put("e_cityId", carLineDTO.getE_cityId());
		p.put("e_areaId", carLineDTO.getE_areaId());
		p.put("userId", carLineDTO.getUserId());
		p.put("startRow", carLineDTO.getStartRow());
		p.put("endRow", carLineDTO.getEndRow());
		p.put("mlng", carLineDTO.getMlng());
		p.put("mlat", carLineDTO.getMlat());
		p.put("bjlng", carLineDTO.getBjlng());
		p.put("bjlat", carLineDTO.getBjlat());
		p.put("cqlng", carLineDTO.getCqlng());
		p.put("cqlat", carLineDTO.getCqlat());
		p.put("shlng", carLineDTO.getShlng());
		p.put("shlat", carLineDTO.getShlat());
		p.put("tjlng", carLineDTO.getTjlng());
		p.put("tjlat", carLineDTO.getTjlat());
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getCarlineApiByConditionNew", p, CarLineDTO.class);
		return list;
	}

	@Override
	public void setMebApiMessage(CarLineDTO carLineDTO, List<MemberAddressDTO> list) throws Exception {
		// TODO Auto-generated method stub
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("clId", carLineDTO.getId());
		p.put("memberId", carLineDTO.getMemberId());
		p.put("type",2);
		p.put("createUserId", carLineDTO.getMemberId());
		p.put("updateUserId", carLineDTO.getMemberId());
		//新增当时所在GPS定位城市Id  2016-01-28,
		p.put("cityId", carLineDTO.getApp());
		p.put("source_type", "0");

		baseDao.execute("CarLine.addnstpushmessage", p);
		
		int id=(int)baseDao.queryForObject("CarLine.getnstpushmessage", p,  Integer.class);
		Map <String, Object> p2 = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			p2.put("messageId", id);
			p2.put("mb_Id", list.get(i).getId());
			p2.put("type",1);
			p2.put("createUserId", list.get(i).getUserId());
			p2.put("updateUserId", list.get(i).getUserId());
			p2.put("s_provinceId", list.get(i).getS_provinceId());
			p2.put("s_cityId", list.get(i).getS_cityId());
			p2.put("s_areaId", list.get(i).getS_areaId());
			p2.put("f_provinceId", list.get(i).getF_provinceId());
			p2.put("f_cityId", list.get(i).getF_cityId());
			p2.put("f_areaId", list.get(i).getF_areaId());
			p2.put("totalWeight",list.get(i).getTotalWeight());
			p2.put("hundredweight",list.get(i).getHundredweight());
			p2.put("carTypes","");
			p2.put("goodsName", list.get(i).getGoodsName());
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
			
			p2.put("distance", df.format(list.get(i).getDistance()));
			//p2.put("distance", list.get(i).getDistance());
			p2.put("mCity", carLineDTO.getmCity());
			baseDao.execute("CarLine.addnstpushmessageinfo", p2);
		}

		
		//return null;
	}

	@Override
	public Long getCarLineId(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("memberId", memberId);
		return (Long)baseDao.queryForObject("CarLine.getCarLineId", p, Long.class);
	}
	
	@Override
	public Integer updateCarLineByid(String carLineIds)
			throws Exception {
		
		Map<String, Object> paramMap=new HashMap<>();
		String[] str=carLineIds.split(",");
		List<String> ids=Arrays.asList(str);
		paramMap.put("carLineIds", ids);
	
		return baseDao.execute("CarLine.updateCarLineByid", paramMap);
	}

	@Override
	public CarLineDTO getCarLIneById(Long clId) throws Exception {
		// TODO Auto-generated method stub
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", clId);
		return (CarLineDTO)baseDao.queryForObject("CarLine.getCarLIneById", p, CarLineDTO.class);
	}

	@Override
	public List<CarLineDTO> getCarlineApiByConditionNewNst2(
			CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", carLineDTO.getId());
		p.put("startCity", carLineDTO.getStartCity());
		p.put("endCity", carLineDTO.getEndCity());
		p.put("maxLoad", carLineDTO.getMaxLoad());
		p.put("carLength", carLineDTO.getCarLength());
		p.put("carType", carLineDTO.getCarType());
		p.put("userId", carLineDTO.getUserId());
		p.put("s_provinceId", carLineDTO.getS_provinceId());
		p.put("s_cityId", carLineDTO.getS_cityId());
		p.put("s_areaId", carLineDTO.getS_areaId());
		p.put("e_provinceId", carLineDTO.getE_provinceId());
		p.put("e_cityId", carLineDTO.getE_cityId());
		p.put("e_areaId", carLineDTO.getE_areaId());
		p.put("startRow", carLineDTO.getStartRow());
		p.put("endRow", carLineDTO.getEndRow());
		p.put("mlng", carLineDTO.getMlng());
		p.put("mlat", carLineDTO.getMlat());
		p.put("bjlng", carLineDTO.getBjlng());
		p.put("bjlat", carLineDTO.getBjlat());
		p.put("cqlng", carLineDTO.getCqlng());
		p.put("cqlat", carLineDTO.getCqlat());
		p.put("shlng", carLineDTO.getShlng());
		p.put("shlat", carLineDTO.getShlat());
		p.put("tjlng", carLineDTO.getTjlng());
		p.put("tjlat", carLineDTO.getTjlat());
		p.put("carLengthCondition1", carLineDTO.getCarLengthCondition1());
		p.put("carLengthCondition2", carLineDTO.getCarLengthCondition2());
		p.put("weightCondition1", carLineDTO.getWeightCondition1());
		p.put("weightCondition2", carLineDTO.getWeightCondition2());
		p.put("closest", carLineDTO.getClosest());
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getCarlineApiByConditionNewNst2", p, CarLineDTO.class);
		return list;
	}

	@Override
	public int getCountByConditionNst2(CarLineDTO carLineDTO) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", carLineDTO.getId());
		p.put("startCity", carLineDTO.getStartCity());
		p.put("endCity", carLineDTO.getEndCity());
		p.put("maxLoad", carLineDTO.getMaxLoad());
		p.put("carLength", carLineDTO.getCarLength());
		p.put("carType", carLineDTO.getCarType());
		p.put("userId", carLineDTO.getUserId());
		p.put("s_provinceId", carLineDTO.getS_provinceId());
		p.put("s_cityId", carLineDTO.getS_cityId());
		p.put("s_areaId", carLineDTO.getS_areaId());
		p.put("e_provinceId", carLineDTO.getE_provinceId());
		p.put("e_cityId", carLineDTO.getE_cityId());
		p.put("e_areaId", carLineDTO.getE_areaId());
		p.put("startRow", carLineDTO.getStartRow());
		p.put("endRow", carLineDTO.getEndRow());
		p.put("mlng", carLineDTO.getMlng());
		p.put("mlat", carLineDTO.getMlat());
		p.put("bjlng", carLineDTO.getBjlng());
		p.put("bjlat", carLineDTO.getBjlat());
		p.put("cqlng", carLineDTO.getCqlng());
		p.put("cqlat", carLineDTO.getCqlat());
		p.put("shlng", carLineDTO.getShlng());
		p.put("shlat", carLineDTO.getShlat());
		p.put("tjlng", carLineDTO.getTjlng());
		p.put("tjlat", carLineDTO.getTjlat());
		p.put("carLengthCondition1", carLineDTO.getCarLengthCondition1());
		p.put("carLengthCondition2", carLineDTO.getCarLengthCondition2());
		p.put("weightCondition1", carLineDTO.getWeightCondition1());
		p.put("weightCondition2", carLineDTO.getWeightCondition2());
		return (int)baseDao.queryForObject("CarLine.getCountByConditionNst2", p, Integer.class);
		 
	}

	@Override
	public List<CarLineDTO> getCarlineApiByIdNst2(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", carLineDTO.getId());
		p.put("startCity", carLineDTO.getStartCity());
		p.put("endCity", carLineDTO.getEndCity());
		p.put("maxLoad", carLineDTO.getMaxLoad());
		p.put("carLength", carLineDTO.getCarLength());
		p.put("carType", carLineDTO.getCarType());
		p.put("s_provinceId", carLineDTO.getS_provinceId());
		p.put("s_cityId", carLineDTO.getS_cityId());
		p.put("s_areaId", carLineDTO.getS_areaId());
		p.put("e_provinceId", carLineDTO.getE_provinceId());
		p.put("e_cityId", carLineDTO.getE_cityId());
		p.put("e_areaId", carLineDTO.getE_areaId());
		p.put("mlng", carLineDTO.getMlng());
		p.put("mlat", carLineDTO.getMlat());
		p.put("bjlng", carLineDTO.getBjlng());
		p.put("bjlat", carLineDTO.getBjlat());
		p.put("cqlng", carLineDTO.getCqlng());
		p.put("cqlat", carLineDTO.getCqlat());
		p.put("shlng", carLineDTO.getShlng());
		p.put("shlat", carLineDTO.getShlat());
		p.put("tjlng", carLineDTO.getTjlng());
		p.put("tjlat", carLineDTO.getTjlat());
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getCarlineApiByCondition1", p, CarLineDTO.class);
		return list;
	}



	@Override
	public List<PublishCountDTO> memberPublishCarLine(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("CarLine.memberPublishCarLine", map, PublishCountDTO.class);
	}

	@Override
	public Integer getMemberPublishCarLineCount(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer) baseDao.queryForObject("CarLine.getMemberPublishCarLineCount", map, Integer.class);
	}

	@Override
	public List<PublishCountDTO> memberPublishCar(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("CarLine.memberPublishCar", map,PublishCountDTO.class);
	}
	
	@Override
	public List<PublishCountDTO> memberPublishCarSameCity(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("CarLine.memberPublishCarSameCity", map,PublishCountDTO.class);
	}

	@Override
	public Integer getMemberPublishCarCount(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer) baseDao.queryForObject("CarLine.getMemberPublishCarCount", map, Integer.class);
	}
	
	@Override
	public Integer getMemberPublishCarCountSameCity(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer) baseDao.queryForObject("CarLine.getMemberPublishCarCountSameCity", map, Integer.class);
	}

	@Override
	public List<CarLineDTO> getCarlineApiByConditionUserId(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("carId", carLineDTO.getCarId());
		p.put("userId", carLineDTO.getUserId());
		p.put("s_provinceId", carLineDTO.getS_provinceId());
		p.put("s_cityId", carLineDTO.getS_cityId());
		p.put("s_areaId", carLineDTO.getS_areaId());
		p.put("e_provinceId", carLineDTO.getE_provinceId());
		p.put("e_cityId", carLineDTO.getE_cityId());
		p.put("e_areaId", carLineDTO.getE_areaId());
		p.put("mlng", carLineDTO.getMlng());
		p.put("mlat", carLineDTO.getMlat());
		p.put("bjlng", carLineDTO.getBjlng());
		p.put("bjlat", carLineDTO.getBjlat());
		p.put("cqlng", carLineDTO.getCqlng());
		p.put("cqlat", carLineDTO.getCqlat());
		p.put("shlng", carLineDTO.getShlng());
		p.put("shlat", carLineDTO.getShlat());
		p.put("tjlng", carLineDTO.getTjlng());
		p.put("tjlat", carLineDTO.getTjlat());
		p.put("beginTime", carLineDTO.getBeginTime());
		p.put("endTime", carLineDTO.getEndTime());
		List<CarLineDTO> list= baseDao.queryForList("CarLine.getCarlineApiByConditionUser", p, CarLineDTO.class);
		return list;
	}
	
	
	@Override
	public int getTotalForConsole(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.queryForObject("CarLine.getTotalForConsole", map, Integer.class);
	}

	@Override
	public List<PublishCountDTO> memberPublishCarLineSameCity(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("CarLine.memberPublishCarLineSameCity", map, PublishCountDTO.class);
	}

	@Override
	public Integer getMemberPublishCarLineCountSameCity(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer) baseDao.queryForObject("CarLine.getMemberPublishCarLineCountSameCity", map, Integer.class);
	}

	@Override
	public Integer getCarLineCount(Map<String, Object> paramMap) throws Exception {
		return (Integer) baseDao.queryForObject("CarLine.getCarLineCount", paramMap, Integer.class);
	}

	@Override
	public List<TrunkCarLineDTO> getCarLineList(Map<String, Object> paramMap) throws Exception {
		return baseDao.queryForList("CarLine.getCarLinePageList", paramMap, TrunkCarLineDTO.class);
	}
	
}
