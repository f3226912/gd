package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdInstorageDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdInstoragedetailDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;
import com.gudeng.commerce.gd.promotion.exception.GrdInstorageException;

import com.gudeng.commerce.gd.promotion.service.GrdInstorageService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class GrdInstorageServiceImpl implements GrdInstorageService {
	
	@Autowired
	private BaseDao<?> baseDao;


	@Override
	public int getTodayTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdInstorage.getTodayTotal", map, Integer.class);
	}

	@Override
	@Transactional
	public Integer insert(GrdInstorageDTO dto,
			List<GrdInstoragedetailDTO> batchDetailDTO) throws Exception {
		//A.生成入库单
		Integer result = baseDao.execute("GrdInstorage.insertInstorage", dto);
	
		//Map<String, Object> mapDetail = new HashMap<String, Object>();
		//循环采购单中要入库的礼品数
		for (int i = 0; i < batchDetailDTO.size(); i++) {
			//mapDetail.clear();
			GrdInstoragedetailDTO entity = getGrdInstoragedetailDTO(dto,batchDetailDTO.get(i));
			//mapDetail = DalUtils.convertToMap(entity);
			/*先判断当前礼品是否在采购单中还存在，因为有可能在入库界面打开时，前端显示存在，可是其他人在采购单管理处删除了此礼品
			Integer purchaseGiftCount = baseDao.queryForObject("GrdInstorage.getPurchaseGiftCount", entity, Integer.class);
			
			if(purchaseGiftCount == 0){
				throw new Exception("礼品编码"+entity.getGiftNo()+"在原采购单中已被删除,入库失败！");
			}*/
			//根据采购单编码和礼品编码查询当前礼品的待入库数量
			GrdInstoragedetailDTO needInstorageCountInfo = getPurchaseGift2InstorageInfo(entity);
			
			if(null == needInstorageCountInfo || null == needInstorageCountInfo.getCount() || 0 == needInstorageCountInfo.getCount()){
				throw new GrdInstorageException("礼品编码"+entity.getGiftNo()+"已被删除,或者已被其他人入库完成,请重新选择礼品入库！");
			}
			if(needInstorageCountInfo.getCount().intValue() < entity.getInstockcount().intValue()){
				throw new GrdInstorageException("礼品编码"+entity.getGiftNo()+"已被其他人入库,当前待入库数量为"+needInstorageCountInfo.getCount().intValue()+",请重新入库！");
			}
			if(needInstorageCountInfo.getPurchaseGiftId().intValue()!=entity.getPurchaseGiftId().intValue()){
				throw new GrdInstorageException("礼品编码"+entity.getGiftNo()+"的礼品信息在入库单中已经变更，请刷新页面后，重新入库前待入库。");
			}
			//B.生成入库详情表
			baseDao.execute("GrdInstorage.insertInstorageDetail", entity);
			
			//根据市场ID，礼品编码和仓库ID查询仓库信息,如果有仓库记录，需要更新调整记录表和仓库库存，如果没有，则需要增加库存信息和调整记录
			GrdInstoragedetailDTO instockInfo = getGrdGiftStockInfo(entity);
			if(instockInfo != null){
				entity.setReason("礼品入库(采购单号:"+dto.getPurchaseNO()+",入库单号:"+dto.getInStorageId()+")");
				entity.setGiftId(instockInfo.getGiftId());
				//1代表修改
				entity.setFlag("1");
				//C.更新库存数据中的库存记录
				baseDao.execute("GrdInstorage.updateGrdGiftStockInfo", entity);
			}else{
				entity.setReason("创建礼品(礼品入库;采购单号:"+dto.getPurchaseNO()+",入库单号:"+dto.getInStorageId()+")");
				//封装grd_gift表所需要的保存参数进行保存并返回保存后的ID
				GrdGiftEntity grdGiftEntity = getGrdGiftDTO(dto,entity);
				Long grdgiftId = baseDao.persist(grdGiftEntity, Long.class);
				//将礼品库存返回的giftId保存到调整记录表中
				entity.setGiftId(grdgiftId);
			}
			//D.插入调整记录表
			//设置采购单号，入库单号，入库数量，和type类型为1
			entity.setPurchaseNO(dto.getPurchaseNO());//
			entity.setInStorageId(dto.getInStorageId());
			entity.setBalance(entity.getInstockcount());
			entity.setType("1");
			baseDao.execute("GrdInstorage.insertGrdGiftLog", entity);
			//修改采购详情表中的状态
			if(needInstorageCountInfo.getCount().intValue() == entity.getInstockcount().intValue()){
				entity.setStatus("2");
			}else{
				entity.setStatus("1");
			}
			//E.更新调整采购详情记录表
			baseDao.execute("GrdInstorage.updatePurchasegiftStatus", entity);
		}
		//F.更新采购主表中的状态,先查询出此采购单下是否还有未入库完的礼品，如果有就修改采购单主表的状态为入库中，如果没有则修改采购主表的状态为入库完
		Integer purchaseGift2InstorageCount = baseDao.queryForObject("GrdInstorage.getPurchaseGift2InstorageCount", dto, Integer.class);
		if(purchaseGift2InstorageCount >0){
			dto.setStatusFlag("0");
		}else{
			dto.setStatusFlag("1");
		}
		baseDao.execute("GrdInstorage.updatePurchaseStatus", dto);
		return result;
	}
	
	/**
	 * 设置grd_gift参数
	 * @param dto
	 * @param entity
	 * @return
	 */
	public GrdGiftEntity getGrdGiftDTO(GrdInstorageDTO dto,GrdInstoragedetailDTO entity)throws Exception {
		GrdGiftEntity grdGiftEntity = new GrdGiftEntity();
		grdGiftEntity.setGiftNo(entity.getGiftNo());
		grdGiftEntity.setName(entity.getName());
		grdGiftEntity.setStockTotal(entity.getInstockcount());
		grdGiftEntity.setStockAvailable(entity.getInstockcount());
		grdGiftEntity.setMarketId(entity.getMarketId());
		grdGiftEntity.setGiftstoreId(entity.getWarehouseId());
		grdGiftEntity.setStatus(0);
		grdGiftEntity.setCreateUserId(entity.getCreateUserId());
		grdGiftEntity.setCreateTime(new Date());
		grdGiftEntity.setUpdateUserId(entity.getCreateUserId());
		grdGiftEntity.setUpdateTime(new Date());
		grdGiftEntity.setRemark(dto.getRemarks());
		grdGiftEntity.setUnit(entity.getUnit());
		return grdGiftEntity;
	}
	/**
	 * 设置参数
	 * @param dto
	 * @param entity
	 * @return
	 */
	public GrdInstoragedetailDTO getGrdInstoragedetailDTO(GrdInstorageDTO dto,GrdInstoragedetailDTO entity)throws Exception {
		entity.setInStorageId(dto.getInStorageId());
		entity.setMarketId(dto.getMarketId());
		entity.setWarehouseId(dto.getWarehouseId());
		entity.setCreateUserId(dto.getCreateUser());
		entity.setUpdateUserId(dto.getUpdateUser());
		entity.setCreateUserName(dto.getCreateUserName());
		entity.setUpdateUserName(dto.getUpdateUserName());
		entity.setPurchaseNO(dto.getPurchaseNO());
		return entity;
	}
	
	@Override
	public GrdInstoragedetailDTO queryGrdGiftStockInfo(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdInstorage.queryGrdGiftStockInfo", map, GrdInstoragedetailDTO.class);
	}
	
	public GrdInstoragedetailDTO getGrdGiftStockInfo(GrdInstoragedetailDTO dto)throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("giftNo", dto.getGiftNo());
		map.put("warehouseId", dto.getWarehouseId());
		map.put("marketId", dto.getMarketId());
		GrdInstoragedetailDTO grdInstoragedetailDTO = queryGrdGiftStockInfo(map);
		return grdInstoragedetailDTO;
	}

	@Override
	public GrdInstoragedetailDTO queryPurchaseGift2InstorageInfo(
			Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdInstorage.queryPurchaseGift2InstorageInfo", map, GrdInstoragedetailDTO.class);
	}
	public GrdInstoragedetailDTO getPurchaseGift2InstorageInfo(GrdInstoragedetailDTO dto)throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("purchaseNO", dto.getPurchaseNO());
		map.put("giftNo", dto.getGiftNo());
		GrdInstoragedetailDTO grdInstoragedetailDTO = queryPurchaseGift2InstorageInfo(map);
		return grdInstoragedetailDTO;
	}
}