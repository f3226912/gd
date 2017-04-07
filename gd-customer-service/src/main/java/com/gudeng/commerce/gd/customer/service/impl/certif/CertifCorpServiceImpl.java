package com.gudeng.commerce.gd.customer.service.impl.certif;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCorpDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCorpEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifCorpService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 *
 * @author lidong
 *
 */
public class CertifCorpServiceImpl implements CertifCorpService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CertifCorpDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CertifCorp.getById", map, CertifCorpDTO.class);
	}

	@Override
	public List<CertifCorpDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifCorp.getList", map, CertifCorpDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CertifCorp.deleteById", map);
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
		return baseDao.batchUpdate("CertifCorp.deleteById", batchValues).length;
	}

	@Override
	public int update(CertifCorpDTO t) throws Exception {
		return baseDao.execute("CertifCorp.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CertifCorp.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CertifCorpEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CertifCorpDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifCorp.getListPage", map, CertifCorpDTO.class);
	}

	@Override
	public CertifCorpDTO getOneBySearch(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("CertifCorp.getOneBySearch", params, CertifCorpDTO.class);
	}
}