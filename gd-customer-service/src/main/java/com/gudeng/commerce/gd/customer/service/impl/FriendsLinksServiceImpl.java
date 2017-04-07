package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;
import com.gudeng.commerce.gd.customer.service.FriendsLinksService;

public class FriendsLinksServiceImpl implements FriendsLinksService{

	@Autowired
	private BaseDao baseDao;

	@Override
	public int getCount(Map<String, Object> map) {
		return (int) baseDao.queryForObject("friendslinks.getCount", map, Integer.class);
	}
	
	@Override
	public List<FriendsLinksDTO> getFriendsLinksList(Map<String, Object> map)
			throws Exception {
		List<FriendsLinksDTO> list = baseDao.queryForList("friendslinks.getFriendsLinksList",
				map, FriendsLinksDTO.class);
		return list;
	}
	
	@Override
	public int add(FriendsLinksDTO friendsLinksDTO){
		return (int) baseDao.execute("friendslinks.addFriendsLinks", friendsLinksDTO);		
	}
	
	@Override
	public FriendsLinksDTO getById(String id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (FriendsLinksDTO) baseDao.queryForObject("friendslinks.getByFriendsLinksId", map, FriendsLinksDTO.class);
				
	}
	
	@Override
	public int update(FriendsLinksDTO friendsLinksDTO) throws Exception {
		return (int) baseDao.execute("friendslinks.updateFriendsLinks", friendsLinksDTO);
	}
	
	@Override
	public int view(FriendsLinksDTO friendsLinksDTO) throws Exception {
		return (int) baseDao.execute("friendslinks.viewFriendsLinks", friendsLinksDTO);
	}
	
	@Override
	public int delete(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
	
		return baseDao.execute("friendslinks.deleteFriendsLinks", map);
	}

	@Override
	public List<FriendsLinksDTO> viewFriendsAll() throws Exception {
		List<FriendsLinksDTO> list = baseDao.queryForList("friendslinks.viewFriendsAll",FriendsLinksDTO.class);
		return list;
	}

	@Override
	public List<FriendsLinksDTO> viewMediaLinksAll() throws Exception {
		List<FriendsLinksDTO> list = baseDao.queryForList("friendslinks.viewMediaLinksAll",FriendsLinksDTO.class);
		return list;
	}

	@Override
	public Integer applyFriendsUrl(FriendsLinksDTO friendsLinks) throws Exception {
		return  baseDao.execute("friendslinks.applyFriendsUrl", friendsLinks);	
	}
}
