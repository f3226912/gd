package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderBaseinfoEntity;

/**
 * 农速通运单信息服务
 * @author xiaojun
 */
public interface NstOrderBaseinfoToolService {

	/**
	 * 插入农速通运单信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Long insertNstOrderBaseinfo(NstOrderBaseinfoEntity nstOrderBaseinfoEntity) throws Exception;

	/**
	 * 根据货源id获取订单
	 * @param memberAddressId
	 * @return
	 * @throws Exception
	 */
	public NstOrderBaseinfoDTO getByMemberAddressId(Long memberAddressId) throws Exception;
	
	/**
	 * 生产nst订单号
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public NstOrderBaseinfoDTO getByOrderNo(String orderNo) throws Exception;
	

	
	/**
	 * 接收运单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer acceptOrder(Map<String, Object> map) throws Exception;
	
	/**
	 * 拒绝运单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer refuseOrder(Map<String, Object> map) throws Exception;
	
	/**
	 * 取消接单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer cancelOrder(Map<String, Object> map) throws Exception;
	
	/**
	 * 删除订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer batchDeleteOrder(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据memberId获取会员的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public NstOrderBaseinfoDTO getMemberInfoByMemberId(Long memberId) throws Exception;
	
	
	/**
	 * 条件查询我接的单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderBaseinfoDTO> getNstOrderListByCondition(Map<String, Object> map) throws Exception;
	
	/**
	 * 确认收货
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer confirmGoods(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据线路id查询出 线路信息 
	 * @param carLineId
	 * @return
	 * @throws Exception
	 */
	public NstOrderBaseinfoDTO getCarLineInfoByCarLineId(Long carLineId) throws Exception;
	
	/**
	 * 根据同城线路id查询出 线路信息 
	 * @param carLineId
	 * @return
	 * @throws Exception
	 */
	public NstOrderBaseinfoDTO getSameCarLineInfoByCarLineId(Long carLineId) throws Exception;
	
	/**
	 * 查询出条件查询订单总条数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getNstOrderListByConditionTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据memberId查询各个状态的总数
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderBaseinfoDTO> getOrderStatusCountByMemberId(Long memberId) throws Exception;
	
	/**
	 * 根据memberId查询 各查询对他的好评数量
	 * @return
	 * @throws Exception
	 */
	public int getEvaluateTypeCountByMemberId(Long memberId) throws Exception;

	/**
	 * 获取订单号
	 * @param map
	 * @return
	 */
	public List<String> getOrderNoByCondition(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取不同类型的待提交总数
	 * @return
	 * @throws Exception
	 */
	public int getCount(String orderType,Long memberId,String sourceType) throws Exception;
    
	/*
	 * 获取订单总数
	 */
	public int getOrderStatusCount(Long id)throws Exception;
    
	/**
	 * 获取货源 线路总数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int getmemberCarlineCountByMemberId(Long id)throws Exception;
    
	/**
	 * 获取认证车辆图片信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<CarsDTO> getCarsDTOByMemberId(Long id)throws Exception;
	
	/**
	 * 根据订单号获取当前订单状态
	 * @return
	 * @throws Exception
	 */
	public int getStatusByOrderNo(String orderNo) throws Exception;
	
/**
	 * 更新农商友
	 * 订单商品出货状态
	 * @param memberAddressId 
	 * @param status 0未出货 1正在出货 2已出货
	 * @return
	 * @throws Exception
	 */
	public int updateDeliverStatusByMemberAddressId(Long memberAddressId, Integer status) throws Exception;
	/**
	 /* 获取车主 所接同城货源的总共单数(待成交)
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberSameCityOrderCount(Long memberId) throws Exception;
	
	/**
	 * 根据订单号  插入车主实际发车时间
	 * @return
	 * @throws Exception
	 */
	public Integer saveStartTimeByOrderNo(String orderNo) throws Exception;
	/**
	 * 根据货源ID查询 订单的最新状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer addressOrderStatus(Long id,Byte sourceType) throws Exception;
}
