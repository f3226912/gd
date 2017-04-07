package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftDetailEntity;

public interface GrdGiftDetailToolService extends BaseToolService<GrdGiftDetailDTO> {

	public Long insert(GrdGiftDetailEntity entity) throws Exception;
	
	/**根据礼品记录id查询
	 * @param recordId
	 * @return
	 * @throws Exception
	 */
	public List<GrdGiftDetailDTO> queryByRecordId(Integer recordId)  throws Exception;
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
}