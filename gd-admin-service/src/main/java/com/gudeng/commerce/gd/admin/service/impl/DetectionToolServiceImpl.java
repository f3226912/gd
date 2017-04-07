package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.DetectionToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;
import com.gudeng.commerce.gd.supplier.service.DetectionService;

/**
 * 监测信息操作接口实现
 * 
 * @author 李冬
 * @time 2015年10月16日 下午3:22:19
 */
@Service
public class DetectionToolServiceImpl implements DetectionToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static DetectionService detectionService;

	protected DetectionService getHessianDetectionService() throws MalformedURLException {
		String url = gdProperties.getDetectionUrl();
		if (detectionService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			detectionService = (DetectionService) factory.create(DetectionService.class, url);
		}
		return detectionService;
	}

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
	public List<DetectionDTO> getByCondition(Map<String, Object> map) throws Exception {
		return getHessianDetectionService().getDetectionList(map);
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
		return getHessianDetectionService().getById(id);
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
		return getHessianDetectionService().getTotal(map);
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
	public int deleteDetection(List<String> list) throws Exception {
		return getHessianDetectionService().deleteDetection(list);
	}

	/**
	 * 新增市场价格信息
	 * 
	 * @param detectionDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:03
	 */
	@Override
	public int addDetectionDTO(DetectionDTO detectionDTO) throws Exception {
		return getHessianDetectionService().addDetectionDTO(detectionDTO);
	}

	/**
	 * @Description addDetectionBacth 批量添加检测信息，主要用于Excel导入
	 * @param List
	 *            <DetectionDTO> list
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月6日 下午3:43:00
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public int addDetectionBacth(List<DetectionDTO> list) throws Exception {
		return getHessianDetectionService().addDetectionBacth(list);
	}

	/**
	 * 修改市场价格信息
	 * 
	 * @param detectionDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	@Override
	public int updateDetectionDTO(DetectionDTO detectionDTO) throws Exception {
		return getHessianDetectionService().updateDetectionDTO(detectionDTO);
	}

	/**
	 * 判断数据是否存在
	 * 
	 * @param map
	 * @return 存在为 true，不存在为false
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午2:37:35
	 */
	public boolean checkExsit(Map<String, Object> map) throws Exception {
		int i = 0;
		if (map == null) {
			i = 0;
		} else {
			if (map.get("id") == null || StringUtils.isEmpty(map.get("id").toString())) {
				i = 0;
			} else {
				i = getHessianDetectionService().checkExsit(map);
			}
		}
		return i > 0;
	}

}
