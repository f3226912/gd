package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordExportDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftLogEntity;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.GrdUserCustomerEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftDetailService;
import com.gudeng.commerce.gd.promotion.service.GrdGiftLogService;
import com.gudeng.commerce.gd.promotion.service.GrdGiftRecordService;
import com.gudeng.commerce.gd.promotion.service.GrdGiftService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class GrdGiftRecordServiceImpl implements GrdGiftRecordService {
	
	@Autowired
	private BaseDao<?> baseDao;

	@Autowired
	private GrdGiftDetailService grdGiftDetailService;

	@Autowired
	private GrdGiftService grdGiftService;
	
	@Autowired
	private GrdGiftLogService grdGiftLogService;
	
	@Override
	public GrdGiftRecordDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdGiftRecord.getByCondition", map, GrdGiftRecordDTO.class);
	}

	@Override
	public List<GrdGiftRecordDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftRecord.getByCondition", map, GrdGiftRecordDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdGiftRecord.deleteById", map);
	}

	@Override
	public int update(GrdGiftRecordDTO t) throws Exception {
		return baseDao.execute("GrdGiftRecord.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGiftRecord.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdGiftRecordEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdGiftRecordDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftRecord.queryByConditionPage", map, GrdGiftRecordDTO.class);
	}

	@Override
	public List<GrdGiftRecordDTO> queryBySearch(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftRecord.queryBySearch", map, GrdGiftRecordDTO.class);
	}
	@Override
	public List<GrdGiftRecordExportDTO> queryBySearchExport(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftRecord.queryBySearchExport", map, GrdGiftRecordExportDTO.class);
	}
	@Override
	public List<GrdGiftRecordExportDTO> queryBySearchGroup(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftRecord.queryBySearchGroup", map, GrdGiftRecordExportDTO.class);
	}

	@Override
	public int countBySearch(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGiftRecord.countBySearch", map, Integer.class);
	}
	@Override
	public int countBySearchExport(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGiftRecord.countBySearchExport", map, Integer.class);
	}

	@Override
	public int dynamicUpdate(GrdGiftRecordEntity entity) throws Exception {
		return baseDao.dynamicMerge(entity);
	}
	
	@Override
	public List<GrdGiftRecordDTO> getRecordByMemberIdAndStatus(Map queryMap) throws Exception {
		return baseDao.queryForList("GrdGiftRecord.getRecordByMemberIdAndStatus", queryMap, GrdGiftRecordDTO.class);
	}

	@Override
	public int getRecordTotalByMemberIdAndStatus(Map queryMap) {
		return  (int) baseDao.queryForObject("GrdGiftRecord.getCountByMemberIdAndStatus", queryMap,Integer.class);
	}
	
	@Override
	@Transactional
	public synchronized Long addRecord(Map<String, Object> map) throws Exception {
		GrdGiftRecordEntity giftRecordEntity=(GrdGiftRecordEntity)map.get("record");
		String type=(String) map.get("type");
		
		List<GrdGiftDetailDTO> giftDetails = (List<GrdGiftDetailDTO>) map.get("giftDetail");
		List<GrdGiftDetailDTO> orderDetails = (List<GrdGiftDetailDTO>) map.get("orderDetail");
		Map queryOrder=new HashMap<>();
		for(GrdGiftDetailDTO rdGiftDetailDTO:orderDetails){
			queryOrder.clear();
			if(rdGiftDetailDTO.getType()==null ){
				return 0L;
			} else if(rdGiftDetailDTO.getType().equals("2")){
				queryOrder.put("orderNo", rdGiftDetailDTO.getOrderNo());
				List<GrdGiftDetailDTO> orderDto  = baseDao.queryForList("GrdGiftDetail.getByCondition", queryOrder, GrdGiftDetailDTO.class);
				if(orderDto!=null && orderDto.size()>0){
					return -2L;
				}
			}else if(rdGiftDetailDTO.getType().equals("3")){
				queryOrder.clear();
				queryOrder.put("memberId", giftRecordEntity.getMemberId());
				queryOrder.put("type", 3);
				List<GrdGiftDetailDTO> giftDetailDTOs = baseDao.queryForList("GrdGiftDetail.getDetailByMap", queryOrder, GrdGiftDetailDTO.class);
				if(giftDetailDTOs!=null && giftDetailDTOs.size()>0){
					return -3L;
				}
			}
		}
		for(GrdGiftDetailDTO rdGiftDetailDTO:giftDetails){
			queryOrder.clear();
			if(rdGiftDetailDTO.getType()==null ){
				return 0L;
			} else if(rdGiftDetailDTO.getType().equals("1")){
				queryOrder.put("id", rdGiftDetailDTO.getGiftId());
				GrdGiftDTO grdGiftDTO = baseDao.queryForObject("GrdGift.getByCondition", queryOrder, GrdGiftDTO.class);
				queryOrder.clear();
				queryOrder.put("giftId", rdGiftDetailDTO.getGiftId());
				int noCount =  baseDao.queryForObject("GrdGift.getNoCount", queryOrder, Integer.class);
				if(rdGiftDetailDTO.getCountNo()>grdGiftDTO.getStockAvailable()-noCount){
					return -4L;
				}
			}
		}
		
		
		Long recordId = (Long)baseDao.persist(giftRecordEntity, Long.class);//插入发放记录
		
		List<GrdGiftDetailDTO> batchDetailDTO=new ArrayList<GrdGiftDetailDTO>();
		for(GrdGiftDetailDTO grdGiftDetailEntity:giftDetails){//为礼品明细设置发放记录ID
			grdGiftDetailEntity.setRecordId(String.valueOf(recordId));
			batchDetailDTO.add(grdGiftDetailEntity);
		}
		for(GrdGiftDetailDTO grdGiftDetailEntity:orderDetails){//为订单明细设置发放记录ID
			grdGiftDetailEntity.setRecordId(String.valueOf(recordId));
			batchDetailDTO.add(grdGiftDetailEntity);
		}
		
		if(batchDetailDTO != null && batchDetailDTO.size() > 0){//发放记录的礼品和订单明细
			grdGiftDetailService.batchInsertEntity(batchDetailDTO);
		}
		
		if(type!=null && type.equals("1")){//现场发放
			List<Map<String, Object>> stockCountList=(List<Map<String, Object>>)map.get("stockCountList");
			for(Map<String, Object> mapStock:stockCountList){
				grdGiftService.updateStock(mapStock);//减少库存
				GrdGiftLogEntity entity=new GrdGiftLogEntity();
				entity.setCreateTime(new Date());
				entity.setCreateUserId((String)mapStock.get("createUserId"));
				entity.setGiftId(Long.valueOf((Integer)mapStock.get("id")));
				entity.setOrignValue((Integer) mapStock.get("orignValue"));
				entity.setRealValue((Integer) mapStock.get("realVale"));
				entity.setReason("礼品发放("+recordId+")");
				entity.setType("2");
				entity.setBalance((Integer) mapStock.get("orignValue")-(Integer) mapStock.get("realVale"));
				grdGiftLogService.insert(entity);//记录日志
			}
		}
		return recordId;
	}
	@Override
	@Transactional
	public synchronized Long addRecordNst(Map<String, Object> map) throws Exception {
		GrdGiftRecordEntity giftRecordEntity=(GrdGiftRecordEntity)map.get("record");
		String type=(String) map.get("type");
		
		List<GrdGiftDetailDTO> giftDetails = (List<GrdGiftDetailDTO>) map.get("giftDetail");
		List<GrdGiftDetailDTO> nstOrderDetails = (List<GrdGiftDetailDTO>) map.get("nstOrderDetail");
		Map queryOrder=new HashMap<>();
		for(GrdGiftDetailDTO rdGiftDetailDTO:nstOrderDetails){
			queryOrder.clear();
			queryOrder.put("type", rdGiftDetailDTO.getType());
			queryOrder.put("memberId", giftRecordEntity.getMemberId());
			if(rdGiftDetailDTO.getType()==null ){
				return 0L;
			} else if(rdGiftDetailDTO.getType().equals("4")){
				List<GrdGiftDetailDTO> giftDetailDTOs = baseDao.queryForList("GrdGiftDetail.getDetailByMap", queryOrder, GrdGiftDetailDTO.class);
				if(giftDetailDTOs!=null && giftDetailDTOs.size()>0){
					return -3L;
				}else{
					Map query=new HashMap();
					query.put("memberId", giftRecordEntity.getMemberId());
					//产生农速通和地推用户关联关系
					GrdUserCustomerDTO grdUserCustomerDTO = baseDao.queryForObject("GrdUserCustomerEntity.getUserCustomerCount", query, GrdUserCustomerDTO.class);
					if(grdUserCustomerDTO==null){
						GrdUserCustomerEntity grdUserCustomerEntity=new GrdUserCustomerEntity();
						grdUserCustomerEntity.setGrdUserId(Integer.valueOf(giftRecordEntity.getCreateUserId()));
						grdUserCustomerEntity.setMemberId(Integer.valueOf(giftRecordEntity.getMemberId()));
						baseDao.execute("GrdUserCustomerEntity.insert", grdUserCustomerEntity);
					}
				}
			}else{
				queryOrder.put("code", rdGiftDetailDTO.getCode());
				List<GrdGiftDetailDTO> nstOrderDto  = baseDao.queryForList("GrdGiftDetail.getDetailByMap", queryOrder, GrdGiftDetailDTO.class);
				if(nstOrderDto!=null && nstOrderDto.size()>0){
					return -2L;
				}
			}
		}
		for(GrdGiftDetailDTO rdGiftDetailDTO:giftDetails){
			queryOrder.clear();
			if(rdGiftDetailDTO.getType()==null ){
				return 0L;
			} else if(rdGiftDetailDTO.getType().equals("1")){
				queryOrder.put("id", rdGiftDetailDTO.getGiftId());
				GrdGiftDTO grdGiftDTO = baseDao.queryForObject("GrdGift.getByCondition", queryOrder, GrdGiftDTO.class);
				queryOrder.clear();
				queryOrder.put("giftId", rdGiftDetailDTO.getGiftId());
				int noCount =  baseDao.queryForObject("GrdGift.getNoCount", queryOrder, Integer.class);
				if(rdGiftDetailDTO.getCountNo()>grdGiftDTO.getStockAvailable()-noCount){
					return -4L;
				}
			}
		}
		Long recordId = (Long)baseDao.persist(giftRecordEntity, Long.class);//插入发放记录
		List<GrdGiftDetailDTO> batchDetailDTO=new ArrayList<GrdGiftDetailDTO>();
		for(GrdGiftDetailDTO grdGiftDetailEntity:giftDetails){//为礼品明细设置发放记录ID
			grdGiftDetailEntity.setRecordId(String.valueOf(recordId));
			grdGiftDetailEntity.setMobile(giftRecordEntity.getMobile());
			grdGiftDetailEntity.setRealName(giftRecordEntity.getRealName());
			batchDetailDTO.add(grdGiftDetailEntity);
		}
		for(GrdGiftDetailDTO grdGiftDetailEntity:nstOrderDetails){//为订单明细设置发放记录ID
			grdGiftDetailEntity.setRecordId(String.valueOf(recordId));
			grdGiftDetailEntity.setCountNo(Integer.valueOf(grdGiftDetailEntity.getCount()));//农速通的订单数量，区别 countNo,但是数据库中公用一个countNo字段
			grdGiftDetailEntity.setMobile(giftRecordEntity.getMobile());
			grdGiftDetailEntity.setRealName(giftRecordEntity.getRealName());
			batchDetailDTO.add(grdGiftDetailEntity);
		}
		
		if(batchDetailDTO != null && batchDetailDTO.size() > 0){//发放记录的礼品和订单明细
			grdGiftDetailService.batchInsertEntity(batchDetailDTO);
		}
		
		if(type!=null && type.equals("1")){//现场发放
			List<Map<String, Object>> stockCountList=(List<Map<String, Object>>)map.get("stockCountList");
			for(Map<String, Object> mapStock:stockCountList){
				grdGiftService.updateStock(mapStock);//减少库存
				GrdGiftLogEntity entity=new GrdGiftLogEntity();
				entity.setCreateTime(new Date());
				entity.setCreateUserId((String)mapStock.get("createUserId"));
				entity.setGiftId(Long.valueOf((Integer)mapStock.get("id")));
				entity.setOrignValue((Integer) mapStock.get("orignValue"));
				entity.setRealValue((Integer) mapStock.get("realVale"));
				entity.setReason("礼品发放("+recordId+")");
				entity.setType("2");
				entity.setBalance((Integer) mapStock.get("orignValue")-(Integer) mapStock.get("realVale"));
				grdGiftLogService.insert(entity);//记录日志
			}
		}
		return recordId;
	}

	@Override
	@Transactional
	public synchronized int centralized(List<Map<String, Object>> list) throws Exception {
		int count=0;
		for(Map<String, Object> map:list){
			Integer recordId=(Integer)map.get("recordId");
			
			Map recordMap=new HashMap();
			recordMap.put("id", recordId);
			GrdGiftRecordDTO record = baseDao.queryForObject("GrdGiftRecord.getByCondition", recordMap, GrdGiftRecordDTO.class);
			
			int i = baseDao.execute("GrdGiftRecord.centralized", map);
			if(i>0){
				baseDao.execute("GrdGiftRecord.updateGrantUser", map);
				List<Map<String, Object>> stockCountList=(List<Map<String, Object>>)map.get("stockCountList");
				for(Map<String, Object> mapStock:stockCountList){
					GrdGiftDTO grdGiftDTO = grdGiftService.getById(String.valueOf(mapStock.get("id")));
					int nowCount = grdGiftDTO.getStockTotal().intValue()-((Integer)mapStock.get("count")).intValue();
					if(nowCount>=0){
						mapStock.put("stockTotal", nowCount);
						grdGiftService.updateStock(mapStock);//减少库存
						GrdGiftLogEntity entity=new GrdGiftLogEntity();
						entity.setCreateTime(new Date());
						entity.setCreateUserId((String)mapStock.get("createUserId"));
						entity.setGiftId(Long.valueOf((Integer)mapStock.get("id")));
//						entity.setOrignValue((Integer) mapStock.get("orignValue"));
						entity.setOrignValue(grdGiftDTO.getStockTotal());
//						entity.setRealValue((Integer) mapStock.get("realVale"));
						entity.setRealValue(nowCount);
						entity.setReason("礼品发放("+recordId+")");
						entity.setType("2");
						entity.setBalance(grdGiftDTO.getStockTotal()-nowCount);
						grdGiftLogService.insert(entity);//记录日志
					}else{
						throw new Exception("发放礼品时库存不足，请刷新后重试。");
					}
				}
			}else{//某个记录重复发放，整个事务失败
//				return 0;
				throw new Exception("其他地推人员可能已经发放礼品，请刷新后重试。");
			}
			count+=i;
		}
		return count;
	}

	@Override
	public int countByGrantOrCreateUserIds(List<String> userIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("userIds", userIds);
		return baseDao.queryForObject("GrdGiftRecord.countByGrantOrCreateUserIds", map, Integer.class);
	}

	@Override
	public List<GrdGiftRecordDTO> queryInviteRegUserInfoByUserId(Map<String, Object> param) throws Exception {
		return baseDao.queryForList("GrdGiftRecord.queryInviteRegUserInfoByUserId", param, GrdGiftRecordDTO.class);
	}

	@Override
	public int countInviteRegUserInfoByUserId(Map<String, Object> param) throws Exception {
		return baseDao.queryForObject("GrdGiftRecord.countInviteRegUserInfoByUserId", param, Integer.class);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GrdGiftRecordDTO> getChildStoreInfo(Map<String, Object> param)
			throws Exception {
		return baseDao.queryForList("GrdGiftRecord.getChildStoreInfo", param, GrdGiftRecordDTO.class);
	}

	@Override
	public Integer insert(GrdGiftRecordDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}