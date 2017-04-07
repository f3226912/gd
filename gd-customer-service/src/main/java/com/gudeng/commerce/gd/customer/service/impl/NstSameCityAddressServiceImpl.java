package com.gudeng.commerce.gd.customer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstMemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
import com.gudeng.commerce.gd.customer.service.NstSameCityAddressService;

public class NstSameCityAddressServiceImpl implements NstSameCityAddressService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public Long insert(NstSameCityAddressEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<NstSameCityAddressDTO> getAssaginCompanyList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("NstSameCityAddress.getAssaginCompanyList", map, NstSameCityAddressDTO.class);
	}

	@Override
	public NstSameCityAddressDTO getAssaginCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("NstSameCityAddress.getAssaginCount", map, NstSameCityAddressDTO.class);
	}

	@Override
	public synchronized boolean assign(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		// 查询出对应城市可以分配的物流公司
		List<NstSameCityAddressDTO> list = getAssaginCompanyList(map);
		// 当没有查询出时，直接是直发返回false
		if (list.size() == 0) {
			direct(map);
			return false;
		} else {
			for (int i = 0; i < list.size(); i++) {
				map.put("assignMemberId", list.get(i).getAssignMemberId());
				NstSameCityAddressDTO dto = getAssaginCount(map);
				// 如果分配人对应的同城货源没有分配给他，或者分配的日上限和月上限都没有达到，则代发分配，然后直接跳出循环
				if (dto == null || (dto != null && list.get(i).getDayAssignMax() > dto.getDayCount()
						&& list.get(i).getMonthAssignMax() > dto.getMonthCount())) {
					baseDao.execute("NstSameCityAddress.assign", map);
					break;
				}
			}
			return true;
		}

	}

	@Override
	public Integer direct(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("NstSameCityAddress.direct", map);
	}

	@Override
	public List<NstSameCityAddressDTO> getNstSameCityAddressListByPage(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("NstSameCityAddress.getNstSameCityAddressListByPage", map,
				NstSameCityAddressDTO.class);
	}

	@Override
	public Integer getNstSameCityAddressListByPageCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("NstSameCityAddress.getNstSameCityAddressListByPageCount", map, Integer.class);
	}

	@Override
	public List<NstSameCityAddressDTO> matchGoodsByCarline(NstSameCityCarlineEntityDTO dto) throws Exception {
		List<NstSameCityAddressDTO> list = null;
		//查询出满足 同城线路发货地(城市和区域) 和 线路出发地(城市和区域) 收货地(城市和区域) 和 线路目的地(城市和区域) 完全匹配的(第一匹配)
		Map<String, Object> map = new HashMap<>();
		map.put("s_cityId", dto.getS_cityId());
		map.put("s_areaId", dto.getS_areaId());
		map.put("f_cityId", dto.getE_cityId());
		map.put("f_areaId", dto.getE_areaId());
		
		getMap(map,dto);
		
		list = getNstSameCityAddressListByPage(map);
		//查询出满足 同城线路发货地(城市和区域) 和 线路出发地(城市和区域) 完全匹配的(第二匹配)
		Map<String, Object> map1 = new HashMap<>();
		if (list.size() != 0) {
			//过滤第一匹配的数据
			List<String> idList = new ArrayList<>();
			for (NstSameCityAddressDTO nscad : list) {
				idList.add(nscad.getId() + "");
			}
			map1.put("idList", idList);
		}
		map1.put("s_cityId", dto.getS_cityId());
		map1.put("s_areaId", dto.getS_areaId());
		getMap(map1,dto);
		List<NstSameCityAddressDTO> list2 = getNstSameCityAddressListByPage(map1);
		list.addAll(list2);
		//查询出 满足 收货地(城市和区域) 和 线路目的地(城市和区域) 完全匹配的(第三匹配)
		Map<String, Object> map2 = new HashMap<>();
		if (list.size() != 0) {
			//过滤第二匹配的数据
			List<String> idList = new ArrayList<>();
			for (NstSameCityAddressDTO nscad : list) {
				idList.add(nscad.getId() + "");
			}
			map2.put("idList", idList);
		}
		map2.put("f_cityId", dto.getE_cityId());
		map2.put("f_areaId", dto.getE_areaId());
		
		getMap(map2,dto);
		List<NstSameCityAddressDTO> list3 = getNstSameCityAddressListByPage(map2);
		list.addAll(list3);
		return list;
	}
	
	/**
	 * 匹配所需公共的map
	 * @param map
	 * @param dto
	 */
	private void getMap(Map<String, Object> map,NstSameCityCarlineEntityDTO dto) {
		map.put("clat", dto.getClat());
		map.put("clng", dto.getClng());
		map.put("closest", "closest");
		map.put("memberId", dto.getMemberId());
	}

	@Override
	public List<NstSameCityAddressDTO> getMemberNSCAList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		MemberBaseinfoDTO mdto=baseDao.queryForObject("MemberBaseinfo.getByMemberId", map, MemberBaseinfoDTO.class);
		if (mdto!=null) {
			map.put("userType", mdto.getUserType());
		}
		return baseDao.queryForList("NstSameCityAddress.getMemberNSCAList", map, NstSameCityAddressDTO.class);
	}

	@Override
	public Integer getMemberNSCAListCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("NstSameCityAddress.getMemberNSCAListCount", map,Integer.class);
	}

	@Override
	public Integer updateMemberNSCA(NstSameCityAddressEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("NstSameCityAddress.updateMemberNSCA", entity);
	}

	@Override
	public Integer deleteMemberNSCA(NstSameCityAddressEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("NstSameCityAddress.deleteMemberNSCA", entity);
	}

	@Override
	public int getTotalForConsole(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("NstSameCityAddress.getTotalForConsole", map, Integer.class);
	}

	@Override
	public List<NstSameCityAddressDTO> queryListForConsole(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("NstSameCityAddress.queryListForConsole", map, NstSameCityAddressDTO.class);

	}

	@Override
	public List<NstMemberAddressDTO> getDistributeAddressList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("NstSameCityAddress.getDistributeAddressList", map, NstMemberAddressDTO.class);

	}

	@Override
	public int getDistributeAddressTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("NstSameCityAddress.getDistributeAddressTotal", map, Integer.class);
	}
	

	
}
