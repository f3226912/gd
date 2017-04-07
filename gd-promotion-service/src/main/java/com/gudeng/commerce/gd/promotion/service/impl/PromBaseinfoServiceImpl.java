package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.PictureRefDTO;
import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromMarketDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.entity.PromBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.entity.PromReqAuditEntity;
import com.gudeng.commerce.gd.promotion.service.PromBaseinfoService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

public class PromBaseinfoServiceImpl implements PromBaseinfoService {

	@Resource
	private BaseDao<?> baseDao;

	@Override
	public List<PromBaseinfoDTO> queryPageByCondition(Map<String, Object> map) {

		return baseDao.queryForList("PromBaseinfo.queryPageByCondition", map, PromBaseinfoDTO.class);
	}

	@Override
	public Integer getTotalCountByCondition(Map<String, Object> map) {
		return baseDao.queryForObject("PromBaseinfo.getTotalCountByCondition", map, Integer.class);
	}

	@Override
	public void updatePromBaseinfo(PromBaseinfoEntity entity) {
		baseDao.execute("PromBaseinfo.updatePromBaseinfo", entity);
	}

	@Override
	public List<PromMarketDTO> queryPromMarketByActId(String actId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actId", actId);
		return baseDao.queryForList("PromBaseinfo.queryPromMarketByActId", map, PromMarketDTO.class);
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		return (int) baseDao.queryForObject("PromBaseinfo.getTotal", map, Integer.class);

	}

	@Override
	public List<PictureRefDTO> getPictures(int promBaseinfoId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("refId", promBaseinfoId);
		return baseDao.queryForList("PictureRef.getPictures", paramMap, PictureRefDTO.class);
	}

	@Override
	public PromBaseinfoDTO getDetail(Map<String, Object> map) {
		return (PromBaseinfoDTO) baseDao.queryForObject("PromBaseinfo.getDetail", map, PromBaseinfoDTO.class);

	}

	@Override
	@Transactional
	public Integer savePromBaseinfo(PromBaseinfoDTO dto) {
		Integer actId = dto.getActId();
		if(null == actId){
			actId = addPromBaseinfo(dto);
		} else {
			//修改
			updatePromBaseinfo(dto);
		}
		return actId;
	}
	
	private Integer addPromBaseinfo(PromBaseinfoDTO dto){
		PromBaseinfoEntity entity = new PromBaseinfoEntity();
		BeanUtils.copyProperties(dto, entity);
		long actId = baseDao.persist(entity, Long.class);
		List<PictureRefDTO> plist = dto.getPictureRefList();
		int len = plist.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			PictureRefDTO en = plist.get(i);
			en.setRefId(actId+"");
			batchValues[i] = DalUtils.convertToMap(en);
			batchValues[i].put("createTime", en.getCreateTime());
			batchValues[i].put("updateTime", en.getUpdateTime());
		}
		baseDao.batchUpdate("PromBaseinfo.insertPictureRef", batchValues);
		List<PromMarketDTO> mlist = dto.getMarketList();
		len = mlist.size();
		batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			PromMarketDTO en = mlist.get(i);
			en.setActId((int)actId);
			batchValues[i] = DalUtils.convertToMap(en);
		}
		baseDao.batchUpdate("PromBaseinfo.insertPromMarkte", batchValues);
		return (int)actId;
	}
	
	private void updatePromBaseinfo(PromBaseinfoDTO dto){
		Map<String,Object> pa = DalUtils.convertToMap(dto);
		pa.put("startTime", dto.getStartTime());
		pa.put("endTime",dto.getEndTime());
		baseDao.execute("PromBaseinfo.updatePromBaseinfo", pa);
		List<PictureRefDTO> plist = dto.getPictureRefList();
		//删除图片
		Map<String,Object> map = new HashMap<>();
		map.put("refId", dto.getActId());
		baseDao.execute("PromBaseinfo.deletePictureRef", map);
		int len = plist.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			PictureRefDTO en = plist.get(i);
			en.setRefId(dto.getActId()+"");
			batchValues[i] = DalUtils.convertToMap(en);
			batchValues[i].put("createTime", en.getCreateTime());
			batchValues[i].put("updateTime", en.getUpdateTime());
		}
		baseDao.batchUpdate("PromBaseinfo.insertPictureRef", batchValues);
		//删除市场
		map.put("actId", dto.getActId());
		baseDao.execute("PromBaseinfo.deletePromMarkte", map);
		List<PromMarketDTO> mlist = dto.getMarketList();
		len = mlist.size();
		batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			PromMarketDTO en = mlist.get(i);
			en.setActId(dto.getActId());
			batchValues[i] = DalUtils.convertToMap(en);
		}
		baseDao.batchUpdate("PromBaseinfo.insertPromMarkte", batchValues);
	}


	@Override
	public List<PromBaseinfoDTO> queryPromoteActivitys(Map<String, Object> map) {
		return baseDao.queryForList("PromBaseinfo.queryPromoteActivitys", map, PromBaseinfoDTO.class);
	}

	

}