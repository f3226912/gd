package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.home.service.PricesToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.commerce.gd.supplier.service.PricesService;

/**
 * 市场价格
 */
@Service
public class PricesToolServiceImpl implements PricesToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static PricesService pricesService;

	protected PricesService getHessianPricesService()
			throws MalformedURLException {
		String url = gdProperties.getPricesUrl();
		if (pricesService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pricesService = (PricesService) factory.create(PricesService.class,
					url);
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
	public List<PricesDTO> getByCondition(Map<String, Object> map)
			throws Exception {
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


}
