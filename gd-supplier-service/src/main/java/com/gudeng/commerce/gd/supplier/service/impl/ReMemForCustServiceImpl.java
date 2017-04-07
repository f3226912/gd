package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ReCustInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ReMemForCustDTO;
import com.gudeng.commerce.gd.supplier.service.ReMemForCustService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * 供应商客户（农批商）业务实现
 * 
 * @author 王为民
 * @time 2015年10月12日 下午6:00:13
 */
@Service(value="rememForCustService")
public class ReMemForCustServiceImpl implements ReMemForCustService {

	@Autowired
	private BaseDao baseDao;

	
	@Override
	@Transactional
	public int addCustomerMember(ReMemForCustDTO custDTO) throws Exception {
		if(custDTO.getBusiMemberId().longValue()==custDTO.getCustMemberId().longValue()){
			return 0;
		}
		//判断是否已经成为了对应的客户
		ReMemForCustDTO result = (ReMemForCustDTO)baseDao.queryForObject("ReMemForCust.queryByBusiIdAndCustId", custDTO, ReMemForCustDTO.class);
		
		//已经存在客户关系，则返回不添加数据
		//20160517 by Semon  已经存在客户关系,更新客户的更新时间
		if(result!=null) {
			ReMemForCustDTO dto = new ReMemForCustDTO();
			dto.setId(result.getId());
			dto.setUpdateTime(new Date());
			return baseDao.execute("ReMemForCust.update", dto);
		}
		
		return baseDao.execute("ReMemForCust.insert", custDTO);
	}

	@Override
	public ReMemForCustDTO getCustomerById(Long id) throws Exception {
		//获取客户关联数据
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		ReMemForCustDTO custDTO = (ReMemForCustDTO)baseDao.queryForObject("ReMemForCust.queryById", params, ReMemForCustDTO.class);
		
		/*
		 * 不为空
		 * 则加载其他数据
		 */
		if(custDTO!=null) {
			Map<String, Object> map = new HashMap<>();
			
			/*
			 * 加载手机号
			 */
			map.put("reCustId", custDTO.getId());
			map.put("type", 2);
			custDTO.setMobiles(queryCustInfo(map));
			
			/*
			 * 加载地址
			 */
			map.put("type", 1);
			custDTO.setAddresses(queryCustInfo(map));
			
			/*
			 * 加载经营范围
			 */
			Map paramsForCate = new HashMap<>();
			paramsForCate.put("businessId", custDTO.getBusinessId());
			
			custDTO.setCategorys((List<ProductCategoryDTO>)baseDao.queryForList("ReMemForCust.queryCategoriesBybusinessId", paramsForCate));
		}
		
		return (ReMemForCustDTO)custDTO;
	}

	@Override
	public List<ReMemForCustDTO> getCustomerByBusiId(Long busiMemberId, String type)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("busiMemberId", busiMemberId);
		params.put("type", type);
		
		return (List<ReMemForCustDTO>)baseDao.queryForList("ReMemForCust.queryByCondition", params);
	}

	@Override
	public List<ReMemForCustDTO> queryCustomer(Map<String, Object> map)
			throws Exception {
		return (List<ReMemForCustDTO>)baseDao.queryForList("ReMemForCust.queryByCondition", map);
	}

	@Override
	public List<ReMemForCustDTO> queryCustomerPage(Map<String, Object> map)
			throws Exception {
		return (List<ReMemForCustDTO>)baseDao.queryForList("ReMemForCust.queryByConditionPage", map);
	}

	@Override
	@Transactional
	public int addMobileOrAddressForCustomer(ReCustInfoDTO custInfoDTO) {
		return baseDao.execute("ReMemForCust.addCustInfo", custInfoDTO);
	}
	
	@Override
	@Transactional
	public int addMobileOrAddressForCustomerMor(List<ReCustInfoDTO> custInfoDTOs) {
		//设置操作结果集
		int result = 0;
		
		/*
		 * 删除就得关联数据
		 */
		Map<String, Object> params = new HashMap<>();
		params.put("reCustId", custInfoDTOs.get(0).getReCustId()); //设置关联客户表ID
		
		result = baseDao.execute("ReMemForCust.deleteCustInfo", params);
		
		//判断删除是否成功
		if(result!=-1) {
			
			//添加数据到数据库
			//循环添加数据
			result = addMobileOrAddressForCustomerMore(custInfoDTOs);
			
		}
		
		return result;
	}
	
	@Override
	public List<ReCustInfoDTO> queryCustInfo(Map<String, Object> map) {
		return (List<ReCustInfoDTO>)baseDao.queryForList("ReMemForCust.queryCustInfo", map);
	}

	@Override
	@Transactional
	public int updateReMemForCust(ReMemForCustDTO reMemForCustDTO) {
		return baseDao.execute("ReMemForCust.update", reMemForCustDTO);
	}
	
	@Transactional
	public int addMobileOrAddressForCustomerMore(List<ReCustInfoDTO> custInfoDTOs) {
		int count = 0;
		
		for (int i = 0; i < custInfoDTOs.size(); i++) {
			ReCustInfoDTO custInfoDTO = custInfoDTOs.get(i);
			count += addMobileOrAddressForCustomer(custInfoDTO);
		}
		return count;
	}

	@Override
	public List<ProductCategoryDTO> queryCategoriesBybusinessId(Long businessId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessId", businessId);
		return (List<ProductCategoryDTO>)baseDao.queryForList("ReMemForCust.queryByConditionPage", map);
	}

	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		return (Integer)baseDao.queryForObject("ReMemForCust.getTotal", map, Integer.class);
	}

	
	
}
