package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.commerce.gd.supplier.service.DetectionService;

/**
 * 检测信息服务接口实现
 * 
 * @author 李冬
 * @time 2015年10月12日 下午7:22:44
 */
@Service(value = "detectionService")
public class DetectionServiceImpl implements DetectionService {
	@Autowired
	private BaseDao<DetectionDTO> baseDao;

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
	@Override
	public List<DetectionDTO> getDetectionList(Map<String, Object> map)
			throws Exception {
		List<DetectionDTO> list = baseDao.queryForList(
				"Detection.getDetectionList", map, DetectionDTO.class);
		return list;
	}

	/**
	 * 查询记录条数
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:05:39
	 */
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Detection.getTotal", map,
				Integer.class);
	}

	/**
	 * 根据ID查找数据
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午3:44:43
	 */
	@Override
	public DetectionDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		return baseDao.queryForObject("Detection.getDetectionById", map,
				DetectionDTO.class);
	}

	/**
	 * 新增检测信息数据信息
	 * 
	 * @param detectionDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:03
	 */
	@Override
	public int addDetectionDTO(DetectionDTO detectionDTO) throws Exception {
		int i = baseDao.execute("Detection.addDetectionDTO", detectionDTO);
		return i;
	}

	/**
	 * @Description addDetectionBacth 批量添加检测信息，主要用于Excel导入
	 * @param List<DetectionDTO> list
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月6日 下午3:43:00
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public int addDetectionBacth(List<DetectionDTO> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			DetectionDTO detectionDTO = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productName", detectionDTO.getProductName());
			map.put("origin", detectionDTO.getOrigin());
			map.put("unitName", detectionDTO.getUnitName());
			map.put("inspection", detectionDTO.getInspection());
			map.put("rate", detectionDTO.getRate());
			map.put("pass", detectionDTO.getPass());
			map.put("detectTime_str", detectionDTO.getDetectTime_str());
			map.put("createUserId", detectionDTO.getCreateUserId());
			map.put("createTime_str", detectionDTO.getCreateTime_str());
			map.put("publishTime_str", detectionDTO.getCreateTime_str());
			map.put("maketId", detectionDTO.getMaketId());
			map.put("detail", null);
			map.put("status", null);
			map.put("description", null);
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("Detection.addDetectionDTO", batchValues).length;
	}
	
	/**
	 * 修改检测信息数据
	 * 
	 * @param detectionDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	@Override
	public int updateDetectionDTO(DetectionDTO detectionDTO) throws Exception {
		int i = baseDao.execute("Detection.updateDetectionDTO", detectionDTO);
		return i;
	}

	/**
	 * 根据ID删除数据
	 * 
	 * @param id集合
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午4:52:12
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteDetection(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("Detection.deleteDetectionDTO", batchValues).length;
	}

	/**
	 * 判断数据是否存在
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午2:37:35
	 */
	@Override
	public int checkExsit(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Detection.checkExsit", map,
				Integer.class);
	}

}
