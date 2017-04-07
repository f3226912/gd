package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.entity.ProductDeliveryDetailEntity;

/**
 * 产品出货信息服务
 * @author TerryZhang
 */
public interface ProductDeliveryDetailService {

	/**
	 * 新增产品出货信息表
	 * 返回主键
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Long add(ProductDeliveryDetailEntity entity) throws Exception;
	
	/**
	 * 批量新增产品出货信息表
	 * 返回增加个数
	 * @param entityList
	 * @return
	 * @throws Exception
	 */
	public int batchAdd(List<ProductDeliveryDetailEntity> entityList) throws Exception;
	
	/**
	 * 根据条件查找产品出货信息列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ProductDeliveryDetailDTO> getListByCondition(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据发货物流id
	 * 更新产品出货信息表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int update(ProductDeliveryDetailDTO dto) throws Exception;

	/**
	 * 批量新增产品出货信息表
	 * 并更新订单产品状态
	 * 返回增加个数
	 * @param entityList
	 * @param orderNoMap
	 * @return
	 * @throws Exception
	 */
	public int batchAddAndUpdate(List<ProductDeliveryDetailEntity> entityList,
			List<OrderProductDetailDTO> productOrderList) throws Exception;
	
	/**
	 * 根据发货物流id获取商品列表
	 * @param memberAddressId
	 * @return
	 */
	public List<ProductDeliveryDetailDTO> getProductByMemberAddressId(Long memberAddressId);
	
	/**
	 * 根据发货物流id获取订单及订单对应商品列表
	 * @param memberAddressId
	 * @return
	 */
	public List<OrderDeliveryDetailDTO> getOrderByMemberAddressId(Long memberAddressId);
	
	/**
	 * 根据发货物流id获取货物来源
	 * @param memberAddressId
	 * @return
	 */
	public Integer getTypeByMemberAddressId(Long memberAddressId);

	/**
	 * 根据条件删除
	 * 商品出货信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteByCondition(Map<String, Object> map) throws Exception;	
	
	/**
	 *  根据发货物流信息获取订单及订单对应商品列表
	 * @param params
	 * @return
	 */
	List<OrderDeliveryDetailDTO> getOrderByMember(Map<String, Object> params);
	
	/**
	 * 根据发货物流信息获取订单及订单对应商品列表
	 * @param params
	 * @return
	 */
	List<ProductDeliveryDetailDTO> getProductByMember(Map<String, Object> params);
	
	/**
	 * 查询类型的使用状态
	 * 0禁用 1启用
	 * @return
	 * @throws Exception
	 */
	public Integer getSwitchStatusByType(int type) throws Exception;	
}
