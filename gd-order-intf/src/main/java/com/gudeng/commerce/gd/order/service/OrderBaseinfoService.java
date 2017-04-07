package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.exception.RefundException;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.ClearingLogDTO;
import com.gudeng.commerce.gd.order.dto.OrderAdvanceAndPaymentDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoSendnowDTO;
import com.gudeng.commerce.gd.order.dto.OrderSinxinDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.dto.RefundResponseDTO;
import com.gudeng.commerce.gd.order.dto.RefundResponseLogResult;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;

public interface OrderBaseinfoService {
	/**
	 * 添加
	 * 
	 * @param OrderBaseinfoEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(OrderBaseinfoEntity obj) throws Exception;
	
	public boolean addOrder(Map<String, Object> map) throws Exception;
	
	public boolean addSinXinOrder(Map<String, Object> map) throws Exception;

	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(Long id) throws Exception;

	/**
	 * 批量通过ID删除对象
	 * 
	 * @param idList
	 * @return void
	 * 
	 */
	public int batchDeleteById(List<Long> idList) throws Exception;
	
	/**
	 * 通过对象更新数据库
	 * 
	 * @param OrderBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateDTO(OrderBaseinfoDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param OrderBaseinfoDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<OrderBaseinfoDTO> objList) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getOrderTotal(Map<String, Object> map) throws Exception; 
	
	/**
	 * 查询供应商记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getSuppOrderTotal(Map<String, Object> map) throws Exception; 
	
	/**
	 * 查询供应商记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getServiceOrderTotal(Map<String, Object> map) throws Exception; 

	/**
	 * 根据ID查询最基本的订单信息
	 * 
	 * @param id
	 * @return OrderBaseinfoDTO
	 * 
	 */
	public OrderBaseinfoDTO getBaseOrderInfoById(Long id) throws Exception;
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return OrderBaseinfoDTO
	 * 
	 */
	public OrderBaseinfoDTO getById(Long id) throws Exception;
	
	
	/**
	 * 根据订单条件查询订单
	 * @param pareMap
	 * @return
	 * @throws Exception
	 */
	
	public List<OrderBaseinfoDTO> getOrderByCondition(Map<String, Object> pareMap) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getOrderListByConditionPage(Map<String, Object> map) throws Exception;

	public int getTotalByStatusPage(Map<String, Object> map) throws Exception;

	/**
	 * 通过产品状态来查找分页list
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getListByStatusPage(Map<String, Object> map) throws Exception;
	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 更新订单状态
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int updateByOrderNo(OrderBaseinfoDTO dto) throws Exception;

	/**
	 * 根据订单号获取订单详细信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public OrderBaseinfoDTO getByOrderNo(Long orderNo) throws Exception;
	
	/**
	 * 订单审核
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int orderExamine(Integer payId,Long orderNo,String type,String description,String statementId,String updateUserId,String userName) throws Exception;
	
	/**
	 * 查询订单详情：供app接口调用
	 * @param orderBaseinfoDTO
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getOderInfoList(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception;

	/**
	 * 根据订单号取消订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer cancelByOrderNo(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据订单号取消订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long queryOrderAmountByMemberId(Long memberId,String type) throws Exception;
	
	/**
	 * @Title: queryOrderDetail
	 * @Description: 
	 * @param orderNo
	 * @return
	 * @throws ServiceException
	 */
	public OrderBaseinfoDTO queryOrderDetail(Long orderNo) throws Exception;

	/**
	 * 确认支付完成
	 * @param totalMap
	 * @throws Exception
	 */
	public int confirmPay(Map<String, Object> totalMap) throws Exception;

	/**
	 * 上传凭证
	 * @param totalMap
	 * @return
	 * @throws Exception
	 */
	public int uploadVoucherAgain(Map<String, Object> totalMap) throws Exception;
	
	/**
	 * 查询超过48小时的订单号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getOverOrderInfoList(Map<String, Object> map) throws Exception;

	/**
	 * 查询2小时后已经确认付款，审核通过，但是卖家没有确认
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getTwoAlreadyPayOrderList(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取出场有补贴订单列表
	 * @param orderBaseinfoDTO
	 * @return
	 */
	public List<OrderBaseinfoDTO> getOutmarketOrderList(OrderBaseinfoDTO orderBaseinfoDTO);

	/**
	 * 获取所有未支付订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getUnpaidOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 卖家确认现金收款
	 * @param totalMap
	 * @throws Exception
	 */
	public void confirmReceive(Map<String, Object> totalMap) throws Exception;

	/**
	 * 查询个人交易记录总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getPageTotal(Map<String, Object> map) throws Exception;

	/**
	 * 分页查询个人交易记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getListByPage(Map<String, Object> map) throws Exception;
	
	/**
	 * 订单查询
	 */
	public List<OrderSinxinDTO> queryOrderForSinxin(OrderSinxinDTO queryDTO) throws Exception;

	/**
	 * 获取已付款的
	 * 产品出货总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getDeliveredProductTotal(Map<String, Object> map) throws Exception;

	/**
	 * 获取已付款的
	 * 产品出货列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getDeliveredProductList(
			Map<String, Object> map) throws Exception;

	/**
	 * 根据用户姓名或手机号
	 * 搜索订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageQueryResultDTO<OrderBaseinfoDTO> searchSellerOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 根据订单号列表
	 * 查找订单信息
	 * @param orderNoList
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getByOrderNoList(List<Long> orderNoList) throws Exception;

	/** 
	 *  获取可以发放礼品的订单，条件如下：
	 *  1系统当前时间向前，取72小时内成功支付 ，POS支付
	 *  2订单内包含商品记录  
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getAbleGiftOrder(
			Long memberId) throws Exception;
	
	public List<OrderBaseinfoDTO> getAbleGiftOrder(
			Map map) throws Exception;

	/**
	 * 根据条件查询list(分页查询) 供应商
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getSuppOrderListByConditionPage(Map<String, Object> map);
	
	/**
	 * 根据条件查询list(分页查询) 服务订单
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getServiceOrderListByConditionPage(Map<String, Object> map);

	/**
	 * 添加促销订单
	 * @param totalMap
	 * @return
	 * @throws Exception
	 */
	public int addPromOrder(Map<String, Object> map) throws Exception;

	/**
	 * 分页获取
	 * 采购订单列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getPurchaseOrderListByPage(
			Map<String, Object> map) throws Exception;

	/**
	 * 获取采购订单列表总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getPurchaseOrderTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询list(分页查询) 鲜农
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoSendnowDTO> getSendnowOrderListByConditionPage(Map<String, Object> map);

	/**
	 * 更新订单为补充订单及总额
	 */
	public int supplementOrderMessage(OrderBaseinfoDTO orderDTO) throws Exception;
	
	/**
	 * 补单需求，根据订单查询银行相似流水
	 * @param persaleId
	 * @return
	 * @throws Exception
	 */
	public String queryOderSameStatement(String persaleId) throws Exception;
	
	/**
	 * 9月2 补单需求 保存补单
	 * @throws Exception
	 */
	public void saveSupplementOrder(String userId,String persaleId,String statementId) throws Exception;

	/**
	 * 获取鲜农订单总数
	 * @param map
	 * @return
	 */
	public int getSendnowOrderListTotal(Map<String, Object> map);

	/**
	 * 批量插入订单
	 * @param totalMapList
	 * @param cartInfoMap 
	 * @throws Exception
	 */
	public int batchAddOrder(List<Map<String, Object>> totalMapList, Map<String, Object> cartInfoMap) throws Exception;

	/**
	 * 更新订单状态以及取消原因
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int updateStatus(OrderBaseinfoDTO dto) throws Exception;

	/**
	 * 预付款退款
	 * @param params
	 * @return
	 * @throws RefundException
	 */
	public RefundResponseDTO orderRefund(Map<String, String> params,boolean isManage) throws RefundException;

	/**
	 * 查询订单时间超过三天未付预付款的数据
	 * @return
	 * @throws Exception
	 */
	public int updateExpireOrderAdvance() throws Exception;
	
	/**
	 * 预付款参数处理
	 * @param params
	 * @return
	 * @throws RefundException
	 */
	public RefundResponseDTO preOrderRefund(Map<String, String> params) throws RefundException;
	/**
	 * 退款记录查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RefundResponseLogResult getOrderRefundLog(Map<String, String> params) throws Exception;

	/**
	 * 修改对账失败记录，更新订单状态和生成支付流水
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updateOrderForCheckBill(Map<String, Object> params) throws Exception;
	
	/**
	 * 修改对账失败记录，增加空订单
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String addOrderForCheckBill(Map<String, Object> params) throws Exception;
	
	/**
	 * 修改对账失败记录，生成支付流水
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String addPaySerialnumberForCheckBill(Map<String, Object> params) throws Exception;
	
	/**
	 * 处理尾款和预付款
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String dealAdvanceAndPayment(List<OrderAdvanceAndPaymentDTO> list) throws Exception;

	/**
	 * 卖家清分明细
	 * @param params
	 * @return
	 */
	public ClearingLogDTO getSellerFeeDetail(Map<String, String> params);

	/**
	 * 更新产品库存
	 * @param stockList
	 * @return
	 */
	int batchUpdateStockCount(List<Map<String,Object>> stockList);
}