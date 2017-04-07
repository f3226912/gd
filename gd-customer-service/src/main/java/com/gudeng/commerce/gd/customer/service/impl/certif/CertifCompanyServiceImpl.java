package com.gudeng.commerce.gd.customer.service.impl.certif;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCompanyDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCompanyEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifCompanyService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class CertifCompanyServiceImpl implements CertifCompanyService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CertifCompanyDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CertifCompanyEntity.getById", map, CertifCompanyDTO.class);
	}

	@Override
	public List<CertifCompanyDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifCompanyEntity.getList", map, CertifCompanyDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CertifCompanyEntity.deleteById", map);
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
		return baseDao.batchUpdate("CertifCompanyEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(CertifCompanyDTO t) throws Exception {
		return baseDao.execute("CertifCompanyEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CertifCompanyEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CertifCompanyEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CertifCompanyDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifCompanyEntity.getListPage", map, CertifCompanyDTO.class);
	}

	@Override
	public CertifCompanyDTO getOneBySearch(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("CertifCompanyEntity.getOneBySearch", params, CertifCompanyDTO.class);
	}
}