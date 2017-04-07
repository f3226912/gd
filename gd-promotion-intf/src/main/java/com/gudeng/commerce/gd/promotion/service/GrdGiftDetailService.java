package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftDetailEntity;

public interface GrdGiftDetailService extends BaseService<GrdGiftDetailDTO> {
	public Long insert(GrdGiftDetailEntity entity) throws Exception;
	
	
	/**
	 * 根据用户手机号获取礼品详情
	 * 
	 * type=1 获取礼品详情。
	 * type=2 获取订单详情
	 * type=3 获取首次注册详情。
	 * type=0 获取 订单详情+首次注册详情
	 * type 为空或者null，获取当前用户的所有详情
	*/
	public List<GrdGiftDetailDTO> getDetailByMobileAndType(String mobile,String type) throws Exception ;
	/**
	 * 根据Map获取礼品详情
	 *  memberId type code
	*/
//	public List<GrdGiftDetailDTO> getDetailByMemberIdAndType(String memberId,String type) throws Exception ;
	public List<GrdGiftDetailDTO> getDetailByMap(Map map) throws Exception ;



	public int batchInsertEntity(List<GrdGiftDetailDTO> batchDetailDTO) throws Exception ;

	
	/**根据礼品记录id查询
	 * @param recordId
	 * @return
	 * @throws Exception
	 */
	public List<GrdGiftDetailDTO> queryByRecordId(Integer recordId)  throws Exception;
	
	/**
	 * 分页查询我的促成订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<GrdGiftDetailDTO> queryCreateOrderByConditionPage(Map<String, Object> map)  throws Exception;
	
	/**
	 * 查询促成订单的总条数  和总交易金额
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public GrdGiftDetailDTO getTotalByUserId(Map<String, Object> map)  throws Exception;
	
	/**根据地推人员id分页查询促成订单信息。查询结果按订单交易时间倒序。
	 * @param param 参数集合，key包含有: 
	 * 					userId 地推人员id,
	 * 					startRow 起始索引行号,
	 * 					endRow  结束索引号
	 * @return
	 * @throws Exception
	 */
	public List<GrdGiftDetailDTO> queryOrderInfoByUserId(Map<String, Object> param)  throws Exception;
	/**根据地推人员id统计促成订单信息
	 * @param param 参数集合，key包含有: 
	 * 					userId 地推人员id,
	 * 					startRow 起始索引行号,
	 * 					endRow  结束索引号
	 * @return
	 * @throws Exception
	 */
	public int countOrderInfoByUserId(Map<String, Object> param)  throws Exception;
	
	public int getGrdOrderTotal(Map<String, Object> map) throws Exception;
	public List<GrdGiftDetailDTO> getGrdOrderList(Map<String, Object> param)  throws Exception;

}