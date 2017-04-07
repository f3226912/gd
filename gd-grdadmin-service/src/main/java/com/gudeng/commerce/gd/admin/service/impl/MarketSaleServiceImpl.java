package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MarketSaleService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.MarketSaleDTO;

/**
 * 市场价格操作接口实现
 * 
 * @author 李冬
 * @time 2015年10月16日 下午3:21:36
 */
@Service
public class MarketSaleServiceImpl implements MarketSaleService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static MarketSaleService marketSaleService;

	protected MarketSaleService getHessianPricesService() throws MalformedURLException {
		String url = gdProperties.getMarketSaleServiceUrl();
		if (marketSaleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			marketSaleService = (MarketSaleService) factory.create(MarketSaleService.class, url);
		}
		return marketSaleService;
	}

	/**
	 * @Description addMarketSale 新增市场交易额信息
	 * @param marketSaleDTO
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月10日 下午5:53:10
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public int addMarketSale(MarketSaleDTO marketSaleDTO) throws Exception {
		return getHessianPricesService().addMarketSale(marketSaleDTO);
	}

	/**
	 * @Description getMarketSaleDTOList 根据条件获取
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月10日 下午5:55:20
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<MarketSaleDTO> getMarketSaleDTOList(Map<String, Object> map) throws Exception {
		return getHessianPricesService().getMarketSaleDTOList(map);
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
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianPricesService().getTotal(map);
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
	public MarketSaleDTO getById(String id) throws Exception {
		return getHessianPricesService().getById(id);
	}

	/**
	 * 修改市场交易额信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	public int updateMarketSaleDTO(MarketSaleDTO MarketSaleDTO) throws Exception {
		return getHessianPricesService().updateMarketSaleDTO(MarketSaleDTO);
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
	public int deleteMarketSale(List<String> list) throws Exception {
		return getHessianPricesService().deleteMarketSale(list);
	}

}
