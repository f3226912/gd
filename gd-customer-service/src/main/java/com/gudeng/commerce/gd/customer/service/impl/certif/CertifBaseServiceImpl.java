package com.gudeng.commerce.gd.customer.service.impl.certif;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.certif.CertifBaseDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifBaseEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifBaseService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 *
 * @author lidong
 *
 */
public class CertifBaseServiceImpl implements CertifBaseService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CertifBaseDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CertifBase.getById", map, CertifBaseDTO.class);
	}

	@Override
	public List<CertifBaseDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifBase.getList", map, CertifBaseDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CertifBase.deleteById", map);
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
		return baseDao.batchUpdate("CertifBase.deleteById", batchValues).length;
	}

	@Override
	public int update(CertifBaseDTO t) throws Exception {
		return baseDao.execute("CertifBase.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CertifBase.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CertifBaseEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CertifBaseDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifBase.getListPage", map, CertifBaseDTO.class);
	}

	@Override
	public CertifBaseDTO getOneBySearch(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("CertifBase.getOneBySearch", params, CertifBaseDTO.class);
	}

	@Override
	public Map<String, Object> getStatusCombination(Map<String, Object> params) throws Exception {
		return baseDao.queryForMap("CertifBase.getStatusCombination", params);
	}
}