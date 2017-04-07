package com.gudeng.commerce.gd.customer.service.impl.certif;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.certif.CertifLogDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifLogEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifLogService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 *
 * @author lidong
 *
 */
public class CertifLogServiceImpl implements CertifLogService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CertifLogDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CertifLog.getById", map, CertifLogDTO.class);
	}

	@Override
	public List<CertifLogDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifLog.getList", map, CertifLogDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CertifLog.deleteById", map);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("CertifLog.deleteById", batchValues).length;
	}

	@Override
	public int update(CertifLogDTO t) throws Exception {
		return baseDao.execute("CertifLog.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CertifLog.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CertifLogEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CertifLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifLog.getListPage", map, CertifLogDTO.class);
	}
}