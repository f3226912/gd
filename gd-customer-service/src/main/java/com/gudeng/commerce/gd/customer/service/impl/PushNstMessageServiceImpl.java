package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.service.PushNstMessageService;

public class PushNstMessageServiceImpl implements PushNstMessageService {
   
	@Autowired
	private BaseDao  baseDao;
	@Override
	public int setReadStatus(PushNstMessageDTO pushNstMessageDTO)
			throws Exception {
		// TODO Auto-generated method stub
		 return (int) baseDao.execute("PushNstMessage.setReadStatus", pushNstMessageDTO);
	}
	@Override
	public int getNewCount(PushNstMessageDTO pushNstMessageDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.queryForObject("PushNstMessage.getNewCount", pushNstMessageDTO, Integer.class);
	}
	@Override
	public int delNstPush(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		// baseDao.execute("PushNstMessage.setReadStatus", pushNstMessageDTO);
		 return (int) baseDao.execute("PushNstMessage.delNstPush", map);
	}

}
