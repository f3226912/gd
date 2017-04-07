package com.gudeng.commerce.info.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.entity.SysUserBoard;
import com.gudeng.commerce.info.customer.service.SysUserBoardService;
import com.gudeng.commerce.info.customer.util.CommonConstant;
import com.gudeng.commerce.info.customer.util.IdCreater;
@Service
public class SysUserBoardServiceImpl implements SysUserBoardService {
	  @Autowired
	  private BaseDao<?> baseDao;

	@Override
	public List<SysUserBoard> getUserBoardList(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("SysUserBoard.getUserBoardList", map,SysUserBoard.class);
	}

	@Override
	public String insert(SysUserBoard sysUserBoard) throws Exception{
		 sysUserBoard.setRmID(IdCreater.newId());
		 int i = baseDao.execute("SysUserBoard.insertUserBoard", sysUserBoard);
	     return i > 0 ? CommonConstant.COMMON_AJAX_SUCCESS : "fail";
	}

	@Override
	public int delete(Map<String,Object> map) throws Exception {
		return (int) baseDao.execute("SysUserBoard.deleteUserBoard", map);
	}

}
