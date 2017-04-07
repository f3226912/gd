package com.gudeng.commerce.gd.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.ReWeighCarBusinessDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.commerce.gd.order.service.ShipperService;

public class ShipperServiceImpl implements ShipperService {
	private static Logger logger = LoggerFactory.getLogger(ShipperServiceImpl.class);
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	@SuppressWarnings("unchecked")
	@Override
	public Long saveWeightLog(WeighCarEntity weighCarEntity) {
		//Map<String, Object> map = new HashMap<String,Object>();
	if(weighCarEntity==null){
		logger.error("weighCarDTO is null");
		return null;
	}
//	map.put("memberId", weighCarDTO.getMemberId());//会员id
//	map.put("carId", weighCarDTO.getCarId());//车辆ID
//	map.put("type", weighCarDTO.getType());//类型（1、货主商，2、采购商）
//	map.put("createUserId", weighCarDTO.getCreateUserId());//创建人员
//	map.put("createTime", weighCarDTO.getCreateTime());//创建时间
//	map.put("totalWeight", weighCarDTO.getTotalWeight());//总重
//	map.put("status", weighCarDTO.getStatus());//过磅状态
//	map.put("tapWeight", weighCarDTO.getTapWeight());//称重标识符 1：皮重为空、2：总重为空、3：皮重/总重为空、4：皮总全过磅
//	map.put("totalUnit", weighCarDTO.getTotalUnit());//总重单位（预留）
//	map.put("totalMemberId", weighCarDTO.getTotalMemberId());//总重查验员ID
//	map.put("totalCreateTime", weighCarDTO.getTotalCreateTime());//总重称重时间
//	map.put("place", weighCarDTO.getPlace());//场地
//	map.put("quality", weighCarDTO.getQuality());//质量，品质 1:优,2:良,3:中,4:差
//	map.put("allWeigh", weighCarDTO.getAllWeigh());//满载 1:是,2:否
//	map.put("others", weighCarDTO.getOthers());//其他
//	map.put("carNumberImage", weighCarDTO.getCarNumberImage());//图片
//	map.put("marketId", weighCarDTO.getMarketId());//市场id
		//return baseDao.execute("WeighLog.saveWeightLog", map);
	return (Long)baseDao.persist(weighCarEntity, Long.class);
	}
	@Override
	public Integer addProduct(PreWeighCarDetailDTO preWeighCarDetail) {
		Map<String, Object> map = new HashMap<String,Object>();
		if(preWeighCarDetail==null){
			logger.error("weighCarDTO is null");
			return null;
		}
		map.put("weighCarId", preWeighCarDetail.getWeighCarId());//过磅记录id
		map.put("memberId", preWeighCarDetail.getMemberId());//货主ID
		map.put("cateId", preWeighCarDetail.getCateId());//商品类目ID
		map.put("productName", preWeighCarDetail.getProductName());//商品名称
		map.put("unit", preWeighCarDetail.getUnit());//单位
		map.put("type", preWeighCarDetail.getType());//默认
		map.put("weigh", preWeighCarDetail.getWeigh());//重量
		map.put("marginWeigh", preWeighCarDetail.getWeigh());//余量，货主剩余量默认为重量值
		return baseDao.execute("WeighLog.addProduct", map);
	}
	@Override
	public Integer updateProduct(PreWeighCarDetailDTO preWeighCarDetail) {
		Map<String, Object> map = new HashMap<String,Object>();
		if(preWeighCarDetail==null){
			logger.error("weighCarDTO is null");
			return null;
		}
		map.put("weighCarId", preWeighCarDetail.getWeighCarId());//过磅记录id
		map.put("memberId", preWeighCarDetail.getMemberId());//货主ID
		map.put("cateId", preWeighCarDetail.getCateId());//商品类目ID
		map.put("productName", preWeighCarDetail.getProductName());//商品名称
		//map.put("unit", preWeighCarDetail.getUnit());//单位不能修改
		map.put("weigh", preWeighCarDetail.getWeigh());//重量
		map.put("marginWeigh", preWeighCarDetail.getWeigh());//余量，货主剩余量默认为重量值
		map.put("pwdId", preWeighCarDetail.getPwdId());//id
		return baseDao.execute("WeighLog.updateProduct", map);
	}
	@Override
	public Integer delProduct(PreWeighCarDetailDTO preWeighCarDetail) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if(preWeighCarDetail==null){
			logger.error("preWeighCarDetail is null");
			return null;
		}
		paramMap.put("pwdId", preWeighCarDetail.getPwdId());
		return baseDao.execute("WeighLog.delProduct", paramMap);
	}
	@Override
	public List<PreWeighCarDetailDTO> getProductlist(PreWeighCarDetailDTO preWeighCarDetail) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if(preWeighCarDetail==null){
			logger.error("preWeighCarDetail is null");
			return null;
		}
		paramMap.put("weighCarId", preWeighCarDetail.getWeighCarId());
		return baseDao.queryForList("WeighLog.getProductlist", paramMap, PreWeighCarDetailDTO.class);
	}
	@Override
	public Integer submitWeightLog(List<ReWeighCarBusinessDTO> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lists", list);
		return baseDao.execute("WeighLog.submitWeightLog", map);
	}
	@Override
	public Integer submitWeightLogClass(ReWeighCarBusinessDTO reWeighCarBusinessDTO) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("weighCarId", reWeighCarBusinessDTO.getWeighCarId());
		paramMap.put("categoryId", reWeighCarBusinessDTO.getCategoryId());
		paramMap.put("createUserId", reWeighCarBusinessDTO.getCreateUserId());
		return baseDao.execute("WeighLog.submitWeightLogClass", paramMap);
	}
	@Override
	public Integer updateWeightCarStatus(Long weighCarId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weighCarId", weighCarId);
		return baseDao.execute("WeighLog.updateWeightCarStatus",map);
	}
	@Override
	@Transactional
	public Integer submit(ReWeighCarBusinessDTO reWeighCarBusinessDTO) {
		List<ReWeighCarBusinessDTO> list = new ArrayList<>();
		// 获取商铺id
		String businessIds = reWeighCarBusinessDTO.getBusinessIds();
		if (businessIds != null) {
			// 判断商铺选中的个数
			String[] businessid = businessIds.split(",");
			for (int i = 0; i < businessid.length; i++) {
				ReWeighCarBusinessDTO reWeighCar = new ReWeighCarBusinessDTO();
				reWeighCar.setWeighCarId(reWeighCarBusinessDTO.getWeighCarId());
				reWeighCar.setBusinessId(Long.parseLong(businessid[i]));
				reWeighCar.setCreateUserId(reWeighCarBusinessDTO.getCreateUserId());
				list.add(reWeighCar);
			}
			// 保存商铺
			 submitWeightLog(list);
		} else {
			// 保存类目
			submitWeightLogClass(reWeighCarBusinessDTO);
		}
		//更新weight_car状态
		Integer res =updateWeightCarStatus(reWeighCarBusinessDTO.getWeighCarId());
		return res;
	}

}
