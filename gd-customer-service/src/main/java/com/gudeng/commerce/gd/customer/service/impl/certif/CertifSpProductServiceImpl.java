package com.gudeng.commerce.gd.customer.service.impl.certif;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.certif.CertifSpProductDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifSpProductEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifSpProductService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 *
 * @author lidong
 *
 */
public class CertifSpProductServiceImpl implements CertifSpProductService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CertifSpProductDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CertifSpProduct.getById", map, CertifSpProductDTO.class);
	}

	@Override
	public List<CertifSpProductDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifSpProduct.getList", map, CertifSpProductDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CertifSpProduct.deleteById", map);
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
		return baseDao.batchUpdate("CertifSpProduct.deleteById", batchValues).length;
	}

	@Override
	public int update(CertifSpProductDTO t) throws Exception {
		return baseDao.execute("CertifSpProduct.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CertifSpProduct.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CertifSpProductEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CertifSpProductDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifSpProduct.getListPage", map, CertifSpProductDTO.class);
	}
}