package com.gudeng.commerce.gd.supplier.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ReCustInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ReMemForCustDTO;

/**
 * 供应商客户（农批商）业务接口
 * 
 * @author 王为民
 * @time 2015年10月12日 下午6:00:13
 */
public interface ReMemForCustService {
	
	
	/**
	 * 添加供应商客户关联信息
	 * @param custDTO
	 * @return
	 * @throws Exception
	 */
	public int addCustomerMember(ReMemForCustDTO custDTO) throws Exception;
	
	/**
	 * 查询对应的客户信息，根据客户关联表ID
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ReMemForCustDTO getCustomerById(Long id) throws Exception;

	/**
	 * 查询客户信息
	 * @param map
	 * @return
	 */
	public List<ReCustInfoDTO> queryCustInfo(Map<String, Object> map);
	
	/**
	 * 添加客户电话号码和地址 批量
	 * @param custInfoDTOs
	 * @return
	 */
	public int addMobileOrAddressForCustomerMor(List<ReCustInfoDTO> custInfoDTOs) throws Exception;
	
	/**
	 * 查询对应供应商或经销商客户
	 * @param busiMemberId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<ReMemForCustDTO> getCustomerByBusiId(Long busiMemberId, String type) throws Exception;
	
	/**
	 * 根据条件查询
	 * @param custDTO
	 * @return
	 * @throws Exception
	 */
	public List<ReMemForCustDTO> queryCustomer(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询 分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ReMemForCustDTO> queryCustomerPage(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加用户 电话或地址
	 * 根据对应类的type区别
	 * @param custInfoDTO
	 * @return
	 */
	public int addMobileOrAddressForCustomer(ReCustInfoDTO custInfoDTO) throws Exception;
	
	/**
	 * 修改客户信息
	 * @param reMemForCustDTO
	 * @return
	 */
	public int updateReMemForCust(ReMemForCustDTO reMemForCustDTO) throws Exception;

	/**
	 * 查询商铺标签
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryDTO> queryCategoriesBybusinessId(Long businessId) throws Exception;
	
	/**
	 * 统计总数 用于分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getTotal(Map<String, Object> map) throws Exception;
	
	
	
}
