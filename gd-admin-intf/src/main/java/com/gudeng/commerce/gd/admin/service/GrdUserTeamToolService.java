package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdUserTeamDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdUserTeamEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdUserTeamToolService extends BaseToolService<GrdUserTeamDTO> {
	
	/**
	 * 新增(使用sql)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insert(Map<String, Object> map) throws Exception;
	
	public Long insert(GrdUserTeamEntity entity) throws Exception;
	
	
	public int deleteByGrdUserId(String grdUserId) throws Exception;
	
	public List<GrdUserTeamDTO> getReUserTeamList(Map<String, Object> map) throws Exception ;
	
}