package com.gudeng.commerce.gd.customer.service.impl;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.entity.PushNoticeEntity;
import com.gudeng.commerce.gd.customer.service.PushNoticeService;
import com.gudeng.commerce.gd.customer.util.DateUtil;
@Service
public class PushNoticeServiceImpl implements PushNoticeService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long insertEntity(PushNoticeEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("PushNotice.deleteById", map);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count=0;
		for(Long id:idList){
			count = deleteById(id);
			if(count<=0) {
				throw new Exception("批量删除记录失败！id:" + id);
			}
		}
		return count;
	}

	@Override
	public int updateDTO(PushNoticeDTO obj) throws Exception {
		int count = baseDao.execute("PushNotice.updateDTO", obj);
		if(count<=0){
			throw new Exception("发送失败！");
		}
		return count;
	}

	@Override
	public int updateNoticeInfo(PushNoticeDTO obj) throws Exception {
		int count = baseDao.execute("PushNotice.updatePushNoticeInfo", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}
	@Override
	public int batchUpdateDTO(List<PushNoticeDTO> objList) throws Exception {
		int count=0;
		for(PushNoticeDTO dto:objList){
			count = baseDao.execute("PushNotice.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PushNotice.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PushNoticeDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (PushNoticeDTO)this.baseDao.queryForObject("PushNotice.getById", map, PushNoticeDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PushNoticeDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<PushNoticeDTO> list= baseDao.queryForList("PushNotice.getListByConditionPage", map, PushNoticeDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PushNoticeDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<PushNoticeDTO> list= baseDao.queryForList("PushNotice.getListByCondition", map, PushNoticeDTO.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getUnReadMessageCount(PushNoticeDTO inputParamDTO)throws Exception {
		//查询出当前用户的注册时间，在注册时间之前发布的消息就没必要再让用户看到了。
		PushNoticeDTO pushUserRegisteInfo = getUserRegisteTime(inputParamDTO);
		if(null == pushUserRegisteInfo || null == pushUserRegisteInfo.getCreateTime()){
			throw new Exception("此用户不存在!");
		}
		
		inputParamDTO.setCreateTimeStr(DateUtil.toString(pushUserRegisteInfo.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
		Integer count = (Integer)baseDao.queryForObject("PushNotice.getUnReadMessageCount", inputParamDTO, Integer.class);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PushNoticeDTO getMessageDetail(PushNoticeDTO inputParamDTO) throws Exception{
		return (PushNoticeDTO)baseDao.queryForObject("PushNotice.getMessageDetail", inputParamDTO, PushNoticeDTO.class);
	}

	@Override
	public int updateMessageIsread(PushNoticeDTO inputParamDTO) throws Exception{
		 return baseDao.execute("PushNotice.updateMessageIsread", inputParamDTO);
	}
	
	@Override
	public int updateMessageIsdel(PushNoticeDTO inputParamDTO) throws Exception {
		return baseDao.execute("PushNotice.updateSysmessageIsdel", inputParamDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PushNoticeDTO> getMessageListByUser(PushNoticeDTO inputParamDTO) throws Exception{
		 Map<String,Object> paraMap = new HashMap<String,Object>();
		 paraMap.put("startRow",  inputParamDTO.getStartRow());
		 paraMap.put("endRow",   inputParamDTO.getEndRow());
		 paraMap.put("userID", inputParamDTO.getUserID());
		 paraMap.put("client", inputParamDTO.getClient());
		 paraMap.put("createTimeStr", inputParamDTO.getCreateTimeStr());
		 List<PushNoticeDTO> list = baseDao.queryForList("PushNotice.getMessageListByUser", paraMap, PushNoticeDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Integer getPushUserCount(PushNoticeDTO inputParamDTO)throws Exception {
		Integer count = (Integer)baseDao.queryForObject("PushNotice.getPushUserCount", inputParamDTO, Integer.class);
		return count;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PushNoticeDTO getUserRegisteTime(PushNoticeDTO inputParamDTO)
			throws Exception {
		return (PushNoticeDTO)baseDao.queryForObject("PushNotice.getUserRegisteTime", inputParamDTO, PushNoticeDTO.class);
	}
}
