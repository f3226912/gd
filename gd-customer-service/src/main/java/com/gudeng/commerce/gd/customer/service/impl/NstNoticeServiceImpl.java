package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;
import com.gudeng.commerce.gd.customer.service.NstNoticeService;
/**
 * 农速通公告服务实现类
 * @author xiaojun
 */
public class NstNoticeServiceImpl implements NstNoticeService{
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public int insert(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("nstNotice.insert", map);
	}

	@Override
	public int update(Long id) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.execute("nstNotice.update", Long.class);
	}

	@Override
	public List<NstNoticeEntityDTO> queryNstNoticeListByPage(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("nstNotice.queryNstNoticeListByPage", map, NstNoticeEntityDTO.class);
	}

	@Override
	public Long queryNstNoticeListByPageCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("nstNotice.queryNstNoticeListByPageCount", map, Long.class);
	}

	@Override
	public int delete(Long id)throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
