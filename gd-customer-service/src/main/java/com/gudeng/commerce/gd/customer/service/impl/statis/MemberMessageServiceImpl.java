package com.gudeng.commerce.gd.customer.service.impl.statis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberMessageDTO;
import com.gudeng.commerce.gd.customer.service.statis.MemberMessageService;
import com.gudeng.framework.dba.util.DalUtils;

public class MemberMessageServiceImpl implements MemberMessageService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public void add(MemberMessageDTO memberMessageDTO) {
		Map<String, Object> paramMap = DalUtils.convertToMap(memberMessageDTO);

		baseDao.execute("MemberMessage.addMemberMessage", paramMap);
	}

}
