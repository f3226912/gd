package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdUserCustomerEntity;
import com.gudeng.commerce.gd.promotion.service.GrdUserCustomerService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdUserCustomerServiceImpl implements GrdUserCustomerService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdUserCustomerDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", id);
		return baseDao.queryForObject("GrdUserCustomerEntity.getById", map, GrdUserCustomerDTO.class);
	}

	@Override
	public List<GrdUserCustomerDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdUserCustomerEntity.getList", map, GrdUserCustomerDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return baseDao.execute("GrdUserCustomerEntity.deleteById", map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GrdUserCustomerEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdUserCustomerDTO t) throws Exception {
		return baseDao.execute("GrdUserCustomerEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdUserCustomerEntity.getTotal", map, Integer.class);
	}

	@Override
	public int insert(GrdUserCustomerEntity entity) throws Exception {
		return baseDao.execute("GrdUserCustomerEntity.insert", entity);
	}

	@Override
	public List<GrdUserCustomerDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdUserCustomerEntity.getListPage", map, GrdUserCustomerDTO.class);
	}

	@Override
	public int getGrdMemberTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdUserCustomerEntity.getGrdMemberTotal", map, Integer.class);
	}

	@Override
	public List<GrdMemberDTO> getGrdMemberListPage(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("GrdUserCustomerEntity.getGrdMemberListPage", map, GrdMemberDTO.class);
	}

	@Override
	public GrdUserCustomerDTO getUserCustomerCount(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdUserCustomerEntity.getUserCustomerCount", map, GrdUserCustomerDTO.class);
	}

	@Override
	public int insertUserCustomerLog(GrdUserCustomerDTO dto) throws Exception {
		return baseDao.execute("GrdUserCustomerEntity.insertUserCustomerLog", dto);
	}

	@Override
	@Transactional
	public void batchUpdate(List<String> list,GrdUserCustomerDTO dto) throws Exception {
		Map<String, Object> paraMap = null;
		for (int i = 0; i < list.size(); i++) {
			paraMap = new HashMap<String, Object>();
			int count = 0;
			String memberId = StringUtils.trim(list.get(i));
			paraMap.put("memberId", memberId);
			dto.setMemberId(Integer.valueOf(memberId));
			//根据memberId查询客户与地推用户关系表,如果有数据，就更新关联关系，没有就新增
			GrdUserCustomerDTO grdUserCustomerDTO =getUserCustomerCount(paraMap);
			if (null != grdUserCustomerDTO && null != grdUserCustomerDTO.getGrdOldUserId()) {
				//需要插入指派日志表
				dto.setGrdOldUserId(grdUserCustomerDTO.getGrdOldUserId());
				count = update(dto);
			} else {
				dto.setGrdOldUserId(null);
				count = insert(dto);
			}
			if (count > 0) {
				//如果是更新指派地推人员，当更新后的值和原值相同时，不再记录日志表，如果是首次指派，则需要记录日志
				if((null != dto.getGrdOldUserId() && (dto.getGrdUserId().intValue() != dto.getGrdOldUserId().intValue()))
						|| null == dto.getGrdOldUserId()){
					insertUserCustomerLog(dto);
				}
				
			} 
		}
	}

	@Override
	public List<GrdUserCustomerLogDTO> getUserCustomerLogList(
			Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdUserCustomerEntity.getUserCustomerLogList", map, GrdUserCustomerLogDTO.class);
	}

	@Override
	public Integer insert(GrdUserCustomerDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}