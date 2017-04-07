package com.gudeng.commerce.gd.customer.service.impl.statis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.PageStatisMemberDTO;
import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;
import com.gudeng.commerce.gd.customer.service.statis.MemberPageStatisticService;
import com.gudeng.framework.dba.util.DalUtils;

public class MemberPageStatisticServiceImpl implements MemberPageStatisticService  {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	public void addMemberPage(PageStatisMemberEntity pageStatisMemberEntity) {
		Map<String, Object> paramMap = DalUtils.convertToMap(pageStatisMemberEntity);
		
		baseDao.execute("MemberPageStatistic.addPageStatisMember", paramMap);
	}
	
	@Override
	public int getTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("MemberPageStatistic.getTotal", map, Integer.class);
	}
	
	@Override
	public List<PageStatisMemberDTO> getBySearch(Map map) throws Exception {
	//	System.out.println(map.get("endDate")+"==================="+map.get("startDate"));
		return  baseDao.queryForList("MemberPageStatistic.getBySearch", map, PageStatisMemberDTO.class);
	}

}
