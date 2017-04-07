package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.service.MemberCertifiService;


/**
 *功能描述：MemberCertifi 增删改查实现类
 *
 */
@Service
public class MemberCertifiServiceImpl implements MemberCertifiService{
	
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private CacheBo cacheBo;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public MemberCertifiDTO getById(String id) throws Exception {
		Map map=new HashMap();
		map.put("certifiId", id);
		return (MemberCertifiDTO)baseDao.queryForObject("MemberCertifi.getByCertifiId", map, MemberCertifiDTO.class);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int)baseDao.queryForObject("MemberCertifi.getTotal", map,Integer.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map map=new HashMap();
		map.put("certifiId", id);
		return (int)baseDao.execute("MemberCertifi.deleteMemberCertifi", map );
	}

	@Override
	public int addMemberCertifiByMap(Map<String, Object> map) throws Exception {
		return (int)baseDao.execute("MemberCertifi.addMemberCertifi", map);
	}

	@Override
	public int addMemberCertifiDTO(MemberCertifiDTO mc) throws Exception {
		//删除缓存
		if(null !=mc.getMemberId()){
			MemberBaseinfoDTO dto = cacheBo.getMemberById(String.valueOf(mc.getMemberId()), baseDao);
			if(null != dto){
				if(null != dto.getMobile()){
					cacheBo.deleteMemberCacheById(dto.getMobile());
				}
				if(null != dto.getAccount()){
					cacheBo.deleteMemberCacheById(dto.getAccount());
				}
			}
			//最后删除id
			cacheBo.deleteMemberCacheById(String.valueOf(mc.getMemberId()));
		}
		return (int)baseDao.execute("MemberCertifi.addMemberCertifi", mc);
	}

	@Override
	public int updateMemberCertifiDTO(MemberCertifiDTO mc) throws Exception {
		//删除缓存
		MemberCertifiDTO mcNew=this.getById(String.valueOf(mc.getCertifiId()));
		if(null !=mcNew){
			MemberBaseinfoDTO dto = cacheBo.getMemberById(String.valueOf(mcNew.getMemberId()), baseDao);
			if(null != dto){
				if(null != dto.getMobile()){
					cacheBo.deleteMemberCacheById(dto.getMobile());
				}
				if(null != dto.getAccount()){
					cacheBo.deleteMemberCacheById(dto.getAccount());
				}
			}
			//最后删除id
			cacheBo.deleteMemberCacheById(String.valueOf(mcNew.getMemberId()));
		}
		return (int)baseDao.execute("MemberCertifi.updateMemberCertifi", mc);
	}

	@Override
	public List<MemberCertifiDTO> getBySearch(Map map) throws Exception {
		return  (List<MemberCertifiDTO>)baseDao.queryForList("MemberCertifi.getBySearch", map,MemberCertifiDTO.class);
	}

	@Override
	public MemberCertifiDTO getByUserId(String userId) throws Exception {
		Map map=new HashMap();
		map.put("memberId", userId);
		return (MemberCertifiDTO)baseDao.queryForObject("MemberCertifi.getByMemberId", map, MemberCertifiDTO.class);
	}

	@Override
	public int getMcId(Long id) throws Exception {
		Map map=new HashMap();
		map.put("memberId", id);
		return (int)baseDao.queryForObject("MemberCertifi.getMcId", map, Integer.class);
	
	}
	
	/*
	 * 查询农速通认证列表
	 */
	@Override
	public List<MemberCertifiDTO> getNstListBySearch(Map map) throws Exception {
		return  (List<MemberCertifiDTO>)baseDao.queryForList("MemberCertifi.getNstListBySearch", map,MemberCertifiDTO.class);
	}

	@Override
	public int getNstTotal(Map<String, Object> map) throws Exception {
		return (int)baseDao.queryForObject("MemberCertifi.getNstTotal", map,Integer.class);
	}
	
	@Override
	public List<MemberCertifiDTO> getList(Map map) throws Exception {
		return  (List<MemberCertifiDTO>)baseDao.queryForList("MemberCertifi.getList", map,MemberCertifiDTO.class);
	}
	
	
}
