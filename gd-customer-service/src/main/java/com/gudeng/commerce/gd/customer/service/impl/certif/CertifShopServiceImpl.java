package com.gudeng.commerce.gd.customer.service.impl.certif;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.certif.CertifShopDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifShopEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifShopService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class CertifShopServiceImpl implements CertifShopService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CertifShopDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CertifShopEntity.getById", map, CertifShopDTO.class);
	}

	@Override
	public List<CertifShopDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifShopEntity.getList", map, CertifShopDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CertifShopEntity.deleteById", map);
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
		return baseDao.batchUpdate("CertifShopEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(CertifShopDTO t) throws Exception {
		return baseDao.execute("CertifShopEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CertifShopEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CertifShopEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CertifShopDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CertifShopEntity.getListPage", map, CertifShopDTO.class);
	}

	/**
	 * 根据用户ID获取实体认证信息
	 */
	@Override
	public CertifShopDTO getByUserId(String memberId) throws Exception {
		Map<String ,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		return baseDao.queryForObject("CertifShopEntity.getByUserId", map, CertifShopDTO.class);
	}
}