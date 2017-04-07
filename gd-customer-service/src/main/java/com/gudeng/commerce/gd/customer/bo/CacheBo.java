package com.gudeng.commerce.gd.customer.bo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.SpecialcharacterDTO;


public class CacheBo {
	/***********************地区分类缓存**********************/
	
	/**
	 * 取一级地区
	 * @param baseDao
	 * @return
	 */
	public List<AreaDTO> listTopArea(BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		return baseDao.queryForList("Area.listTopArea", params ,AreaDTO.class);
	}
	
	/**
	 * 根据Id查询地区 
	 * @param areaID
	 * @param baseDao
	 * @return
	 */
	public AreaDTO getAreaById(String areaID,BaseDao baseDao){
		Map <String, String> p = new HashMap<String, String>();
		p.put("areaID", areaID);
		return (AreaDTO)baseDao.queryForObject("Area.getArea", p, AreaDTO.class);
	}
	
	/**
	 * 根据地区名查询地区 
	 * @param area
	 * @param baseDao
	 * @return
	 */
	public AreaDTO getAreaByName(String area,BaseDao<?> baseDao) {
		Map <String, String> p = new HashMap<String, String>();
		p.put("area", area);
		return (AreaDTO)baseDao.queryForObject("Area.getAreaByName", p ,AreaDTO.class);
	}

	/**
	 * 根据id查下级类
	 * @param id
	 * @param baseDao
	 * @return
	 */
	public List<AreaDTO> listChildArea(String id,BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("father", id);
		return baseDao.queryForList("Area.listChildArea", params ,AreaDTO.class);
	}
	
	/**
	 * 根据id查询下级树
	 * @param father
	 * @param baseDao
	 * @return
	 */
	public List<AreaDTO> getAreaChildTree(String father,BaseDao baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("father", father);
		return baseDao.queryForList("Area.getChildTree", params ,AreaDTO.class);
	}

	/**
	 * 删除地区缓存
	 * @param id
	 */
	public void deleteAreaCacheById(String id){
		
	}
	
	/**
	 * 获取所有省份和城市
	 * @param baseDao
	 * @return
	 */
	public List<AreaDTO> getAllProvinceCity(BaseDao<?> baseDao) {
		Map<String,Object> params = new HashMap<String,Object>();
		return baseDao.queryForList("Area.getAllProvinceCity", params, AreaDTO.class);
	}
	
	/***********************地区分类缓存End**********************/
	
	/**********************会员缓存Start************************/
	/**
	 * 根据ID查询会员
	 * @param memberId
	 * @param baseDao
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getMemberById(String memberId,BaseDao baseDao) throws Exception {
		Map map=new HashMap();
		map.put("memberId", memberId);
		return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getByMemberId", map, MemberBaseinfoDTO.class);
	}
	
	/**
	 * 根据mobile查询会员
	 * @param mobile
	 * @param baseDao
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getMemberByMobile(String mobile,BaseDao baseDao) throws Exception {
		Map map=new HashMap();
		map.put("mobile", mobile);
		return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getByMobile", map, MemberBaseinfoDTO.class);
	}
	
	/**
	 * 根据账号查询会员
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getMemberByAccount(String account,BaseDao baseDao) throws Exception {
		Map map=new HashMap();
		map.put("account", account);
		return  (MemberBaseinfoDTO)baseDao.queryForObject("MemberBaseinfo.getByAccount", map, MemberBaseinfoDTO.class);
	}
	
	/**
	 * 删除会员缓存
	 * @param id
	 */
	public void deleteMemberCacheById(String id){
		
	}
	
	/**********************会员缓存End**************************/
	public List<SpecialcharacterDTO> getCharacterList(Map<String, Object> map,BaseDao baseDao) throws Exception {
		return baseDao.queryForList("Specialcharacter.getList", map, SpecialcharacterDTO.class);
	}
	
	/**
	 * 商铺交易动态缓存
	 * @param arg0
	 * @return
	 */
	public List<BusinessBaseinfoDTO> getTradingDynamics(Map<String, Object> map,BaseDao baseDao) {
		// TODO Auto-generated method stub
		List<BusinessBaseinfoDTO>  businessList= baseDao.queryForList("BusinessBaseinfo.getTradingDynamics", map, BusinessBaseinfoDTO.class);
		return businessList;
	}
}
