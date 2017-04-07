package com.gudeng.commerce.gd.admin.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MarketBerthManageService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MarketBerthDTO;
import com.gudeng.commerce.gd.customer.service.MarketBerthService;

@Service
public class MarketBerthManageServiceImpl implements MarketBerthManageService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static MarketBerthService marketBerthService;
	
	
	/**
	 * 功能描述:街市管理接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MarketBerthService getHessianMarketBerthService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.marketBerth.url");
		if(marketBerthService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			marketBerthService = (MarketBerthService) factory.create(MarketBerthService.class, url);
		}
		return marketBerthService;
	}
	
	@Override
	public List<MarketBerthDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return  this.getHessianMarketBerthService().getByCondition(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().getTotal(map);
	}
	@Override
	public int queryMarketGroupTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().queryMarketGroupTotal(map);
	}

	@Override
	public List<MarketBerthDTO> getByConditionByDtl(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().getByConditionByDtl(map);
	}

	@Override
	public int getTotalByDtl(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().getTotalByDtl(map);
	}

	@Override
	public List<MarketBerthDTO> queryMarketGroup(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().queryMarketGroup(map);
	}

	@Override
	public int addMerketBerth(MarketBerthDTO marketBerthDto) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().addMerketBerth(marketBerthDto);
	}
	@Override
	public int updateMarketBerth(MarketBerthDTO marketBerthDto) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().updateMarketBerth(marketBerthDto);
	}
	@Override
	public int deleteMarketBerth(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.getHessianMarketBerthService().deleteMarketBerth(map);
	}

	/**
	 * 根据id修改市场铺位表
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author 李冬 dli@gdeng.cn
	 * @time 2016年8月18日 上午10:42:53
	 */
	@Override
	public int updateMarketBerthById(MarketBerthDTO dto) throws Exception {
		return this.getHessianMarketBerthService().updateMarketBerthById(dto);
	}

	@Override
	public int updateMarketBerthCodeById(MarketBerthDTO dto) throws Exception {
		return this.getHessianMarketBerthService().updateMarketBerthCodeById(dto);
	}

	@Override
	public int updateMarketBerthBacth(List<MarketBerthDTO> list)
			throws Exception {
		return getHessianMarketBerthService().updateMarketBerthBacth(list);
	}
}
