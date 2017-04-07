package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;

/**
 * 监测信息操作接口
 * 
 * @author 李冬
 * @time 2015年10月16日 下午3:22:19
 */
public interface DetectionToolService {

	/**
	 * 根据ID查找数据
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午3:44:43
	 */
	public DetectionDTO getById(String id) throws Exception;

	/**
	 * 根据条件查找检测信息列表
	 * 
	 * @param map
	 *            查找条件
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月12日 下午8:01:37
	 */
	public List<DetectionDTO> getByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 查询记录条数
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:05:39
	 */
	public int getTotal(Map<String, Object> map) throws Exception;

	/**
	 * 根据ID删除数据
	 * 
	 * @param id集合
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午4:52:12
	 */
	public int deleteDetection(List<String> list) throws Exception;

	/**
	 * 持久化检测信息数据
	 * 
	 * @param t
	 *            市场价格实体类
	 * @return 影响行数
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月12日 下午8:15:30
	 */
	public int addDetectionDTO(DetectionDTO detectionDTO) throws Exception;

	/**
	 * @Description addDetectionBacth 批量添加检测信息，主要用于Excel导入
	 * @param List
	 *            <DetectionDTO> list
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月6日 下午3:43:00
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public int addDetectionBacth(List<DetectionDTO> list) throws Exception;

	/**
	 * 合并市场价格实例数据（不存在则添加，存在则更新）
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月12日 下午8:17:07
	 */
	public int updateDetectionDTO(DetectionDTO detectionDTO) throws Exception;

	/**
	 * 判断数据是否存在
	 * 
	 * @param map
	 * @return 存在为 true，不存在为false
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午2:37:35
	 */
	public boolean checkExsit(Map<String, Object> map) throws Exception;

}