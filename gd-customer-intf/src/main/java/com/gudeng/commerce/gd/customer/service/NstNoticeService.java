package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;

/**
 * 农速通公告service
 * @author yourname
 */
public interface NstNoticeService {
	/**
	 * 插入公告
	 * @param map
	 * @return
	 */
	public int insert(Map<String, Object> map) throws Exception;
	/**
	 * 根据id更新公告
	 * @param id
	 * @return
	 */
	public int update(Long id) throws Exception;
	/**
	 * 查询公告列表
	 * @param Map
	 * @return
	 */
	public List<NstNoticeEntityDTO> queryNstNoticeListByPage(Map<String, Object> map) throws Exception;
	/**
	 * 查询公告列表总数
	 * @param map
	 * @return
	 */
	public Long queryNstNoticeListByPageCount(Map<String, Object> map) throws Exception;
	/**
	 * 根据id删除公告
	 * @param id
	 * @return
	 */
	public int delete(Long id) throws Exception;
}
