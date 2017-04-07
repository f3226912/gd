package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;

/**
 * 检测信息操作方法接口类
 * 
 * @author 李冬
 * @time 2015年10月12日 下午6:00:13
 */
public interface DetectionToolService {
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
	public List<DetectionDTO> getDetectionList(Map<String, Object> map)
			throws Exception;

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
	 * 根据ID查找数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午3:44:43
	 */
	public DetectionDTO getById(String id) throws Exception ;
	
	/**
	 * 新增检测信息数据信息
	 * 
	 * @param detectionDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:03
	 */
	public int addDetectionDTO(DetectionDTO detectionDTO) throws Exception;

	/**
	 * 修改检测信息数据
	 * 
	 * @param detectionDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	public int updateDetectionDTO(DetectionDTO detectionDTO) throws Exception;
	

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
	 * 判断数据是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午2:37:35
	 */
	public int checkExsit(Map<String, Object> map) throws Exception;
}
