package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.entity.AuditInfoEntity;
import com.gudeng.commerce.gd.customer.service.AuditInfoService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class AuditInfoServiceImpl implements AuditInfoService{
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(AuditInfoServiceImpl.class);

	@Autowired
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	@Override
	public int addAuditInfoDTO(AuditInfoDTO aid) throws Exception {
		return (int)baseDao.execute("AuditInfo.addAuditInfo", aid);
	}

	@Override
	public Long addAuditInfoEnt(AuditInfoEntity ae) throws Exception {
		return (Long)baseDao.persist(ae, Long.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (int)baseDao.execute("AuditInfo.deleteAuditInfo", map );
	}

//	@Override
//	public int updateAuditInfoDTO(AuditInfoDTO aid) throws Exception {
//		return (int)baseDao.execute("AuditInfo.updateAuditInfo", aid);
//	}

	@Override
	public AuditInfoDTO getById(String id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (AuditInfoDTO)baseDao.queryForObject("AuditInfo.getById", map, AuditInfoDTO.class);	}

	@Override
	public int getTotal(Map map) throws Exception {
		return (int)baseDao.queryForObject("AuditInfo.getTotal", map,Integer.class);
	}

	@Override
	public List<AuditInfoDTO> getAll(Map map) throws Exception {
		return  (List<AuditInfoDTO>)baseDao.queryForList("AuditInfo.getAll", map,AuditInfoDTO.class);
	}

	@Override
	public List<AuditInfoDTO> getBySearch(Map map) throws Exception {
		return  (List<AuditInfoDTO>)baseDao.queryForList("AuditInfo.getBySearch", map,AuditInfoDTO.class);
	}
	
	@Override
	public List<AuditInfoDTO> getAllSpecifiedRecord(Map map) throws Exception {
		return  (List<AuditInfoDTO>)baseDao.queryForList("AuditInfo.getAllSpecifiedRecord", map,AuditInfoDTO.class);
	}
	
	@Override
	public Map<String, AuditInfoDTO> getAuditInfos(List<String> mainIds, Map<String, Object> params) throws Exception {
		if (mainIds == null || mainIds.isEmpty()){
			return null ;
		}
		Map<String, AuditInfoDTO> map = new HashMap<String, AuditInfoDTO>();
		List<AuditInfoDTO> list = null;
		for(String id : mainIds){
			params.put("mainId", id);
			//审核信息,降序排列
			list = getAllSpecifiedRecord(params);
			if (list == null || list.isEmpty()){
				logger.info("产品状态为不通过, 但是未发现审核信息 , productId : {}", new Object[]{id});
//				throw new Exception("产品状态为不通过, 但是未发现审核信息");
			}else{
				//最新
				map.put(id, list.get(0));
			}
		}
		return map;
	}
	
}