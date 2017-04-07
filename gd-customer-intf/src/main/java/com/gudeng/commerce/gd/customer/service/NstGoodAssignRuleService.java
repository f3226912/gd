package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstDeptGoodDTO;
import com.gudeng.commerce.gd.customer.dto.NstGoodAssignRuleDTO;

/**
 * 一手货源农速通货源分配规则服务
 * @author xiaojun
 */
public interface NstGoodAssignRuleService {

	/**
	 * 插入货源分配原则
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer insert(NstGoodAssignRuleDTO nstGoodAssignRuleDTO) throws Exception; 
	/**
	 * 更新货源分配原则
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer update(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询货源分配列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstGoodAssignRuleDTO> getAssignRuleDTOListByPage(Map<String, Object> map) throws Exception;
	/**
	 * 查询货源分配列表总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getAssignRuleDTOListByPageCount(Map<String, Object> map) throws Exception;
	/**
	 * 根据id查询货源分配详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public NstGoodAssignRuleDTO getById(Long id) throws Exception;
	
	/**
	 * 更新货源分配原则是否生效（批量和单个）
	 * @param idString
	 * @return
	 * @throws Exception
	 */
	public Integer updateStaus(String idString,Integer isEffective,String updateUserId) throws Exception;
	/**
	 * 根据城市id查询出信息部集合
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<NstGoodAssignRuleDTO> getDeptNameListByCityId(Long cityId) throws Exception;
	
	/**
	 * 插入规则开关
	 * @param nstGoodAssignRuleDTO
	 * @return
	 * @throws Exception
	 */
	public Integer insertRuleSwith(NstGoodAssignRuleDTO nstGoodAssignRuleDTO) throws Exception;
	
	/**
	 * 修改所有规则的状态
	 * @param isEffective
	 * @return
	 * @throws Exception
	 */
	public Integer updateEffective(Integer isEffective) throws Exception;
	
	/**
	 * 查询当前开关状态
	 * @return
	 * @throws Exception
	 */
	public NstGoodAssignRuleDTO selectRuleSwith() throws Exception;
	
	/**
	 * 根据城市id查询出信息部集合
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<NstGoodAssignRuleDTO> getQueryDeptNameListByCityId(Long cityId) throws Exception;
	
	/**
	 * 更新规则根据code
	 * @param value
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer updateRuleSwithByCode(String value,String code,String updateUserId) throws Exception;
	
	/**
	 * 分配给信息部货源列表（分页）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstDeptGoodDTO> queryDeptGoodPage(Map<String, Object> map);
	
	/**
	 * 分配给信息部货源总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getDeptGoodTotalCount(Map<String, Object> map);
}
