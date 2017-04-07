package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NstDeptGoodDTO;
import com.gudeng.commerce.gd.customer.dto.NstGoodAssignRuleDTO;
import com.gudeng.commerce.gd.customer.service.NstGoodAssignRuleService;
/**
 * 一手货源农速通货源分配规则服务
 * @author xiaojun
 */
public class NstGoodAssignRuleServiceImpl implements NstGoodAssignRuleService {
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public Integer insert(NstGoodAssignRuleDTO nstGoodAssignRuleDTO) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("nstGoodAssignRule.insert", nstGoodAssignRuleDTO);
	}

	@Override
	public Integer update(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("nstGoodAssignRule.update", map);
	}

	@Override
	public List<NstGoodAssignRuleDTO> getAssignRuleDTOListByPage(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("nstGoodAssignRule.geAssignRuleDTOListByPage", map,NstGoodAssignRuleDTO.class);
	}

	@Override
	public Integer getAssignRuleDTOListByPageCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("nstGoodAssignRule.geAssignRuleDTOListByPageCount", map, Integer.class);
	}

	@Override
	public NstGoodAssignRuleDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map =new HashMap<>();
		map.put("id", id);
		return baseDao.queryForObject("nstGoodAssignRule.getById", map, NstGoodAssignRuleDTO.class);
	}

	@Override
	public Integer updateStaus(String idString,Integer isEffective,String updateUserId) throws Exception {
		Map<String, Object> map=new HashMap<>();
		String[] ids=idString.split(",");
		List<String> idList=Arrays.asList(ids);
		map.put("idList", idList);
		map.put("isEffective", isEffective);
		map.put("updateUserId", updateUserId);
		return baseDao.execute("nstGoodAssignRule.updateStaus", map);
	}

	@Override
	public List<NstGoodAssignRuleDTO> getDeptNameListByCityId(Long cityId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		map.put("cityId", cityId);
		return baseDao.queryForList("nstGoodAssignRule.getDeptNameListByCityId", map, NstGoodAssignRuleDTO.class);
	}

	@Override
	public Integer insertRuleSwith(NstGoodAssignRuleDTO nstGoodAssignRuleDTO) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("nstGoodAssignRule.insertRuleSwith", nstGoodAssignRuleDTO);
	}

	@Override
	public Integer updateEffective(Integer isEffective) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map =new HashMap<>();
		map.put("isEffective", isEffective);
		return baseDao.execute("nstGoodAssignRule.updateEffective", map);
	}

	@Override
	public NstGoodAssignRuleDTO selectRuleSwith() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map =new HashMap<>();
		return baseDao.queryForObject("nstGoodAssignRule.selectRuleSwith", map, NstGoodAssignRuleDTO.class);
	}

	@Override
	public List<NstGoodAssignRuleDTO> getQueryDeptNameListByCityId(Long cityId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		map.put("cityId", cityId);
		return baseDao.queryForList("nstGoodAssignRule.getQueryDeptNameListByCityId", map, NstGoodAssignRuleDTO.class);
	}

	@Override
	public Integer updateRuleSwithByCode(String value,String code,String updateUserId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		map.put("value", value);
		map.put("code", code);
		map.put("updateUserId", updateUserId);
		return baseDao.execute("nstGoodAssignRule.updateRuleSwithByCode", map);
	}

	@Override
	public List<NstDeptGoodDTO> queryDeptGoodPage(Map<String, Object> map) {
		return baseDao.queryForList("nstGoodAssignRule.queryDeptGoodPage", map, NstDeptGoodDTO.class);
	}

	@Override
	public int getDeptGoodTotalCount(Map<String, Object> map) {
		return baseDao.queryForObject("nstGoodAssignRule.getDeptGoodTotalCount", map, Integer.class);
	}
}
