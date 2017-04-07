package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.CustInfoDTO;
import com.gudeng.commerce.gd.customer.entity.CallstatiSticsEntity;
import com.gudeng.commerce.gd.customer.service.CallstatiSticsService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * 拨打电话记录操作实现类
 * @author Ailen
 *
 */
public class CallstatiSticsServiceImpl implements CallstatiSticsService{
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void insert(CallstatiSticsEntity callstatiSticsDTO) {
		callstatiSticsDTO.setCreateTime(new Date());
		baseDao.execute("callstatistics.add", callstatiSticsDTO);
	} ;

	@Override
	@Deprecated
	public int getTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("callstatistics.getTotal", map, Integer.class);
	}
	
	@Override
	@Deprecated
	public int getTotal2(Map map) throws Exception {
		return (int) baseDao.queryForObject("callstatistics.getTotal2", map, Integer.class);
	}
	
	@Override
	@Deprecated
	public List<CallstatiSticsDTO> getBySearch(Map map) throws Exception {
		return  baseDao.queryForList("callstatistics.getBySearch", map, CallstatiSticsDTO.class);
	}
	
	@Override
	@Deprecated
	public List<CallstatiSticsDTO> getBySearch2(Map map) throws Exception {
		return  baseDao.queryForList("callstatistics.getBySearch2", map, CallstatiSticsDTO.class);
	}

	@Override
	public List<CallstatiSticsDTO> getBySearchForSupplier(Map map)
			throws Exception {
		return  (List<CallstatiSticsDTO>)baseDao.queryForList("callstatistics.getBySearchForSupplier", map, CallstatiSticsDTO.class);
	}

	@Override
	@Deprecated
	public int getTotal3(Map map) throws Exception {
		return (int) baseDao.queryForObject("callstatistics.getTotal3", map, Integer.class);
	}
	@Override
	public List<CustInfoDTO> getCust(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("callstatistics.getCust", map,CustInfoDTO.class);
	}

	@Override
	public List<CustInfoDTO> getLinkman(Map<String, Object> param) throws Exception {
		return baseDao.queryForList("callstatistics.getLinkman", param,CustInfoDTO.class);
	}

	@Override
	public Long getLinkmanTotal(Map<String, Object> param) {
		return (Long) baseDao.queryForObject("callstatistics.getLinkmanTotal", param, Long.class);
	}

	@Override
	public Long getCustTotal(Map<String, Object> param) {
		return (Long) baseDao.queryForObject("callstatistics.getCustTotal", param, Long.class);
	}
	   /**
     * @Description 查询总数
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年5月17日 上午11:34:46
     * @Author lidong(dli@gdeng.cn)
    */
    public int getTotalCount(Map map)throws Exception{
        return (int) baseDao.queryForObject("callstatistics.getTotalCount", map, Integer.class);
    }
    
    /**
     * @Description 根据条件查询集合
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年5月17日 上午11:35:30
     * @Author lidong(dli@gdeng.cn)
    */
    public List<CallstatiSticsDTO> getList(Map map) throws Exception{
        return  baseDao.queryForList("callstatistics.getList", map, CallstatiSticsDTO.class);
    }
}
