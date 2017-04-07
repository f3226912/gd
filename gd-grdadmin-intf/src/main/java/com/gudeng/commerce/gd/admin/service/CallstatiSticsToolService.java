package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.entity.CallstatiSticsEntity;

/**
 * 电话记录操作类
 * @author Ailen
 *
 */
public interface CallstatiSticsToolService {
	
	public void insert(CallstatiSticsEntity callstatiSticsDTO) throws Exception;
    /**
     * @Description 查询总数
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年5月17日 上午11:34:46
     * @Author lidong(dli@gdeng.cn)
    */
    public int getTotalCount(Map map)throws Exception;
    
    /**
     * @Description 根据条件查询集合
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年5月17日 上午11:35:30
     * @Author lidong(dli@gdeng.cn)
    */
    public List<CallstatiSticsDTO> getList(Map map) throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal(Map map)throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal2(Map map)throws Exception;
	
	/**
	 * 根据多条件查询
	 *  
	 * @return list
	 * 
	 */
	public List<CallstatiSticsDTO> getBySearch(Map map) throws Exception;
	
	/**
	 * 根据多条件查询
	 *  
	 * @return list
	 * 
	 */
	public List<CallstatiSticsDTO> getBySearch2(Map map) throws Exception;
	
	/**
	 * 根据多条件查询
	 * 供应商电话信息
	 * @return list
	 * 
	 */
	public List<CallstatiSticsDTO> getBySearchForSupplier(Map map) throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 * 供应商电话信息
	 *  
	 * @return int
	 * 
	 */
	public int getTotal3(Map map)throws Exception;
}
