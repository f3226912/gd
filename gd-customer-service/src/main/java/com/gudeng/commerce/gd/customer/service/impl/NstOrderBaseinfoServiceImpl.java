package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCountDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;

public class NstOrderBaseinfoServiceImpl implements NstOrderBaseinfoService {

	@Autowired
	private BaseDao<?> baseDao;

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public Long insertNstOrderBaseinfo(
			NstOrderBaseinfoEntity nstOrderBaseinfoEntity) throws Exception {
		
		return baseDao.persist(nstOrderBaseinfoEntity, Long.class);
	}

	@Override
	public NstOrderBaseinfoDTO getByMemberAddressId(Long memberAddressId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberAddressId", memberAddressId);		
		return baseDao.queryForObject("NstOrderBaseinfo.getOrderBaseByMemberAddressId",
				paramMap, NstOrderBaseinfoDTO.class);
	}
	
	@Override
	public NstOrderBaseinfoDTO getByMemberAddressId(Long memberAddressId, String source) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberAddressId", memberAddressId);
		paramMap.put("source", source);
		return baseDao.queryForObject("NstOrderBaseinfo.getOrderBaseByMemberAddressId", paramMap, NstOrderBaseinfoDTO.class);
	}

	@Override
	public NstOrderBaseinfoDTO getByOrderNo(String orderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", orderNo);
		return baseDao.queryForObject("NstOrderBaseinfo.getByOrderNo",
				paramMap, NstOrderBaseinfoDTO.class);
	}

	@Override
	public int autoComfirmOrder() {
		return baseDao.execute("NstOrderBaseinfo.autoComfirmOrder", null);
	}

	@Override
	public Integer acceptOrder(Map<String, Object> map) throws Exception {
		String orderNo=(String) map.get("orderNo");
		Long id=getMemberAddressIdByOrderNo(orderNo);
		if (id!=null && id>0) {
			updateDeliverStatusByMemberAddressId(id, 2);
		}
		return baseDao.execute("NstOrderBaseinfo.acceptOrder", map);
	}

	@Override
	public Integer refuseOrder(Map<String, Object> map) throws Exception {
		String orderNo=(String) map.get("orderNo");
		Long id=getMemberAddressIdByOrderNo(orderNo);
		if (id!=null && id>0) {
			//修改商品状态   供其他app使用
			updateDeliverStatusByMemberAddressId(id, 1);
		}
		return baseDao.execute("NstOrderBaseinfo.refuseOrder", map);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getNstOrderListByCondition(
			Map<String, Object> map) throws Exception {
		return baseDao.queryForList(
				"NstOrderBaseinfo.getNstOrderListByCondition", map,
				NstOrderBaseinfoDTO.class);
	}

	@Override
	public NstOrderBaseinfoDTO getMemberInfoByMemberId(Long memberId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		return baseDao.queryForObject(
				"NstOrderBaseinfo.getMemberInfoByMemberId", map,
				NstOrderBaseinfoDTO.class);
	}

	@Override
	public Integer confirmGoods(Map<String, Object> map) throws Exception {
		return baseDao.execute("NstOrderBaseinfo.confirmGoods", map);
	}

	@Override
	public NstOrderBaseinfoDTO getCarLineInfoByCarLineId(Long carLineId)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("carLineId", carLineId);
		return baseDao.queryForObject("NstOrderBaseinfo.getCarLineInfoByCarLineId", paramMap,NstOrderBaseinfoDTO.class);
	}
	@Override
	public NstOrderBaseinfoDTO getSameCarLineInfoByCarLineId(Long carLineId)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("carLineId", carLineId);
		return baseDao.queryForObject("NstOrderBaseinfo.getSameCarLineInfoByCarLineId", paramMap,NstOrderBaseinfoDTO.class);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getByCondition(Map<String, Object> map)
			throws Exception {

		List<NstOrderBaseinfoDTO> list = baseDao.queryForList(
				"NstOrderBaseinfo.getByCondition", map,
				NstOrderBaseinfoDTO.class);
		return list;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("NstOrderBaseinfo.getTotal", map,
				Integer.class);

	}

	@Override
	public int updateNstOrderBaseinfoDTO(NstOrderBaseinfoDTO dto)
			throws Exception {
		return (int) baseDao.execute(
				"NstOrderBaseinfo.updateNstOrderBaseinfoDTO", dto);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getCommentListByCondition(
			Map<String, Object> map) throws Exception {
		List<NstOrderBaseinfoDTO> list = baseDao.queryForList(
				"NstOrderBaseinfo.getCommentListByCondition", map,
				NstOrderBaseinfoDTO.class);
		return list;
	}

	@Override
	public int getCommentTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("NstOrderBaseinfo.getCommentTotal",
				map, Integer.class);
	}

	@Override
	public int getNstOrderListByConditionTotal(Map<String, Object> map)
			throws Exception {
		return (int) baseDao.queryForObject(
				"NstOrderBaseinfo.getNstOrderListByConditionTotal", map,
				Integer.class);
	}

	@Override
	public List<String> getOrderNoByCondition(Map<String, Object> map) {
		return baseDao.queryForList("NstOrderBaseinfo.getOrderNoByCondition",
				map, String.class);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getOrderStatusCountByMemberId(Long memberId)
			throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		return baseDao.queryForList(
				"NstOrderBaseinfo.getOrderStatusCountByMemberId", map,
				NstOrderBaseinfoDTO.class);
	}

	@Override
	public int getEvaluateTypeCountByMemberId(Long memberId) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		return baseDao.queryForObject(
				"NstOrderBaseinfo.getEvaluateTypeCountByMemberId", map,
				Integer.class);
	}

	@Override
	public int getCount(String orderType,Long memberId,String sourceType) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("orderType", orderType);
		map.put("memberId", memberId);
		map.put("sourceType", sourceType);
		Integer count = baseDao.queryForObject("NstOrderBaseinfo.getCount",
				map, Integer.class);
		return count == null ? 0 : count;
	}

	@Override
	public int getOrderStatusCount(Long id) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberId", id);
		return baseDao.queryForObject(
				"NstOrderBaseinfo.getOrderStatusCount", map,
				Integer.class);
	}

	@Override
	public int getmemberCarlineCountByMemberId(Long id) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberId", id);
		int mc= (Integer) baseDao.queryForObject(
				"NstOrderBaseinfo.getMemberadressCount", map,
				Integer.class);
		 int cc=(Integer)baseDao.queryForObject(
					"NstOrderBaseinfo.getCarLineCount", map,
					Integer.class);
		 return mc+cc;
	}

	@Override
	public List<CarsDTO> getCarsDTOByMemberId(Long id) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberId", id);
		return baseDao.queryForList(
				"NstOrderBaseinfo.getCarsDTOByMemberId", map,
				CarsDTO.class);
	}

	@Override
	public Integer cancelOrder(Map<String, Object> map) throws Exception {
		String orderNo=(String) map.get("orderNo");
		Long id=getMemberAddressIdByOrderNo(orderNo);
		if (id!=null && id>0) {
			//修改商品状态   供其他app使用
			updateDeliverStatusByMemberAddressId(id, 1);
		}
		return baseDao.execute("NstOrderBaseinfo.cancelOrder", map);
	}

	@Override
	public Integer batchDeleteOrder(Map<String, Object> map) throws Exception {
		return baseDao.execute("NstOrderBaseinfo.batchDeleteOrder", map);
	}

	@Override
	public int getStatusByOrderNo(String orderNo) throws Exception {
		Map<String, Object> map =new HashMap<>();
		map.put("orderNo", orderNo);
		return baseDao.queryForObject("NstOrderBaseinfo.getStatusByOrderNo", map,Integer.class);
	}

	@Override
	public List<NstOrderCountDTO> getOrderCountList(
			Map<String, Object> map) throws Exception {
		return baseDao.queryForList("NstOrderBaseinfo.getOrderCountList", map, NstOrderCountDTO.class);
	}

	@Override
	public NstOrderCountDTO getOrderCountDetailByOrderNo(String orderNo)
			throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("orderNo", orderNo);
		return baseDao.queryForObject("NstOrderBaseinfo.getOrderCountDetailByOrderNo", paramMap, NstOrderCountDTO.class);
	}

	@Override
	public Long getOrderCountListTotal(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForObject("NstOrderBaseinfo.getOrderCountListTotal", map, Long.class);
	}

	@Override
	public Long getMemberAddressIdByOrderNo(String orderNo) throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("orderNo", orderNo);
		return baseDao.queryForObject("NstOrderBaseinfo.getMemberAddressIdByOrderNo", paramMap, Long.class);
	}
	
	@Override
	public int updateDeliverStatusByMemberAddressId(Long memberAddressId,
			Integer status) throws Exception {
		int num = 0;
		if(status == null){
			return num;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberAddressId", memberAddressId);
		if (status.intValue()==-1) {
			map.put("isSameAdress", status);
			status=0;
		}
		List<NstOrderBaseinfoDTO> productList = baseDao.queryForList("NstOrderBaseinfo.getByMemberAddressId", map, NstOrderBaseinfoDTO.class);
		if(productList != null && productList.size() > 0){
			int len = productList.size();
			Map<String, Object>[] batchParams = new Map[len];
			for (int i = 0; i < len; i++) {
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("orderNo", productList.get(i).getOrderNo());
				paramMap.put("productId", productList.get(i).getProductId());
				paramMap.put("deliverStatus", status);
				batchParams[i] = paramMap;
			}
			num = baseDao.batchUpdate("NstOrderBaseinfo.updateDeliverStatus", batchParams).length;
		}
		return num;
	}

	@Override
	public List<NstOrderBaseinfoDTO> getMemberAddressIdListByOrderNo(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("NstOrderBaseinfo.getMemberAddressIdListByOrderNo", map, NstOrderBaseinfoDTO.class);
	}
@Override
	public Integer getMemberSameCityOrderCount(Long memberId) throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("memberId", memberId);
		return baseDao.queryForObject("NstOrderBaseinfo.getMemberSameCityOrderCount", paramMap, Integer.class);
	}

	@Override
	public Integer autoCancelOrderBySameCity() throws Exception {
		return baseDao.execute("NstOrderBaseinfo.autoCancelOrderBySameCity", null);
	}

	@Override
	public List<NstOrderCountDTO> getSameCityOrderList(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("NstOrderBaseinfo.getSameCityOrderList", map, NstOrderCountDTO.class);
	}

	@Override
	public Long getSameCityOrderTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("NstOrderBaseinfo.getSameCityOrderTotal", map, Long.class);
	}

	@Override
	public NstOrderCountDTO getSameCityOrderDetailByOrderNo(String orderNo)
			throws Exception {

		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("orderNo", orderNo);
		return baseDao.queryForObject("NstOrderBaseinfo.getSameCityOrderDetailByOrderNo", paramMap, NstOrderCountDTO.class);
	}

	@Override
	public Integer saveStartTimeByOrderNo(String orderNo) throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("orderNo", orderNo);
		return baseDao.execute("NstOrderBaseinfo.saveStartTimeByOrderNo", paramMap);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getSameCityOrdersByCondition(
			Map<String, Object> map) throws Exception {
		return baseDao.queryForList("NstOrderBaseinfo.getSameCityOrdersByCondition", map, NstOrderBaseinfoDTO.class);

	}

	@Override
	public int getSameCityOrdersTotal(Map<String, Object> map) throws Exception {
   	return baseDao.queryForObject("NstOrderBaseinfo.getSameCityOrdersTotal", map, Integer.class);

	}
	@Override
	public Integer addressOrderStatus(Long id,Byte sourceType) throws Exception {
		Map<String , Object> paramMap=new HashMap<>();
		paramMap.put("id", id);
		Integer status=null;
		//如果传入的sourceType=0 表示干线，sourceType=1表示同城
		if (sourceType==0) {
			status=baseDao.queryForObject("NstOrderBaseinfo.memberAddressOrderStatus", paramMap, Integer.class);
		}else if (sourceType==1) {
			status=baseDao.queryForObject("NstOrderBaseinfo.sameMemberAddressOrderStatus", paramMap, Integer.class);
		}
		return status;
	}
	

}
