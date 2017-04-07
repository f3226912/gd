package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PricesToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.commerce.gd.supplier.service.PricesService;

/**
 * 市场价格操作接口实现
 * 
 * @author 李冬
 * @time 2015年10月16日 下午3:21:36
 */
@Service
public class PricesToolServiceImpl implements PricesToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static PricesService pricesService;

	protected PricesService getHessianPricesService() throws MalformedURLException {
		String url = gdProperties.getPricesUrl();
		if (pricesService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pricesService = (PricesService) factory.create(PricesService.class, url);
		}
		return pricesService;
	}

	/**
	 * 根据条件查找市场价格列表
	 * 
	 * @param map
	 *            查找条件
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月12日 下午8:01:37
	 */
	@Override
	public List<PricesDTO> getByCondition(Map<String, Object> map) throws Exception {
		return getHessianPricesService().getPricesList(map);
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
	public PricesDTO getById(String id) throws Exception {
		return getHessianPricesService().getById(id);
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
		return getHessianPricesService().getTotal(map);
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
	public int deletePrices(List<String> list) throws Exception {
		return getHessianPricesService().deletePrices(list);
	}

	/**
	 * 新增市场价格信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:03
	 */
	@Override
	public int addPricesDTO(PricesDTO priceDTO) throws Exception {
		return getHessianPricesService().addPricesDTO(priceDTO);
	}

	/**
	 * @Description addPricesBacth 批量添加市场价格信息，主要用于Excel导入
	 * @param List
	 *            <PricesDTO> list
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月6日 下午3:43:00
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@Override
	public int addPricesBacth(List<PricesDTO> list) throws Exception {
		return getHessianPricesService().addPricesBacth(list);
	}

	/**
	 * 修改市场价格信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	@Override
	public int updatePricesDTO(PricesDTO priceDTO) throws Exception {
		return getHessianPricesService().updatePricesDTO(priceDTO);
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
				i = getHessianPricesService().checkExsit(map);
			}
		}
		return i > 0;
	}
}
