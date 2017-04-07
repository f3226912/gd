package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.input.AreaAppInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO.MARKET_TYPE;
import com.gudeng.commerce.gd.customer.dto.MarketDTO.STATUS;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
import com.gudeng.commerce.gd.customer.service.MarketService;
import com.gudeng.commerce.gd.customer.service.ReMemberMarketService;

public class MarketToolServiceImpl implements MarketToolService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MarketToolServiceImpl.class);
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private AreaToolService areaToolService;

	private static MarketService marketService;

	private static ReMemberMarketService reMemberMarketService;

	protected MarketService getHessianMarketService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.market.url");
		logger.info("Market url: " + url);
		if (marketService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			marketService = (MarketService) factory.create(MarketService.class,
					url);
		}
		return marketService;
	}

	protected ReMemberMarketService getHessianReMemberMarketService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty(
				"gd.reMemberMarket.url");
		if (reMemberMarketService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reMemberMarketService = (ReMemberMarketService) factory.create(
					ReMemberMarketService.class, url);
		}
		return reMemberMarketService;
	}
	@Override
	public List<MarketDTO> getByCondition(String provinceId,
			String cityId, String marketType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("provinceId", provinceId);
		map.put("cityId", cityId);
		map.put("status", "0");
		map.put("marketType", marketType);
		return getHessianMarketService().getAllByCondition(map);
	}
	
	@Override
	public List<MarketDTO> getByCondition2_5(String provinceId, String cityId,
			String marketType) throws Exception {

		List<MarketDTO> listMarket=new ArrayList<>();
		
		MarketDTO marketDTOWH=new MarketDTO();
		marketDTOWH.setAddress("武汉白沙洲批发市场");
		marketDTOWH.setAreaId(420104l);
		marketDTOWH.setCityId(420100l);
		marketDTOWH.setId(1l);
		marketDTOWH.setMarketName("武汉白沙洲批发市场");
		marketDTOWH.setMarketType("2");
		marketDTOWH.setProvinceId(420000l);
		
		
		MarketDTO marketDTOYL=new MarketDTO();
		marketDTOYL.setAddress("广西玉林");
		marketDTOYL.setAreaId(0l);
		marketDTOYL.setCityId(440300l);
		marketDTOYL.setId(2l);
		marketDTOYL.setMarketName("广西玉林批发市场");
		marketDTOYL.setMarketType("2");
		marketDTOYL.setProvinceId(440000l);
		
		
		listMarket.add(marketDTOYL);
		listMarket.add(marketDTOWH);
		return listMarket;
//		return getHessianMarketService().getAllByCondition(map);
	}

	@Override
	public Map<String, Object> getByConditionV3(Long provinceId,
			Long cityId, String marketType) throws Exception {
		Map<String, Object> retMap = new HashMap<>();

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("provinceId", provinceId);
		mapParam.put("cityId", cityId);
		mapParam.put("status", STATUS.USED.getValue());
		mapParam.put("marketType", MARKET_TYPE.MARKET.getValue());
		List listOpenMarkets = getHessianMarketService().getAllByCondition(
				mapParam);
		retMap.put("listOpenMarkets", listOpenMarkets);

		mapParam.put("status", STATUS.CLOSE.getValue());
		List listNotOpenMarkets = getHessianMarketService().getAllByCondition(
				mapParam);
		retMap.put("listNotOpenMarkets", listNotOpenMarkets);

		return retMap;

	}

	@Override
	public int addReMemberMarket(Long memberId, Long marketId) throws Exception {
		ReMemberMarketDTO rb = new ReMemberMarketDTO();
		rb.setMarketId(marketId);
		rb.setMemberId(memberId);
		return getHessianReMemberMarketService().addReMemberMarketDTO(rb);
	}

	@Override
	public Long addMarket(MarketDTO m) throws Exception {
		return getHessianMarketService().addMarket(m);
	}

	@Override
	public int deleteReMemberMarket(Long memberId) throws Exception {
		ReMemberMarketDTO rb = new ReMemberMarketDTO();
		rb.setMemberId(memberId);
		return getHessianReMemberMarketService().deleteReMemberMarketDTO(rb);
	}

	@Override
	public List<ReMemberMarketDTO> getReMemberMarket(Long memberId)
			throws Exception {
		ReMemberMarketDTO rb = new ReMemberMarketDTO();
		rb.setMemberId(memberId);
		return getHessianReMemberMarketService().getByDTO(rb);
	}

	@Override
	public List<MarketDTO> getAllByStatus(String status) throws Exception {
		return getHessianMarketService().getAllByStatus(status);
	}

	@Override
	public List<MarketDTO> getAllBySearch(Map<String, Object> params) throws Exception {
		return getHessianMarketService().getAllBySearch(params);
	}

	@Override
	public ErrorCodeEnum getNearbyMarket(AreaAppInputDTO inputDTO,
			Map<String, Object> map) throws Exception {
		String cityName = inputDTO.getCityName(); 
		String cityLng = inputDTO.getCityLng(); //经度
		String cityLat = inputDTO.getCityLat(); //纬度
		//默认武汉白沙洲市场
		if (StringUtils.isBlank(cityName) 
				&& StringUtils.isBlank(cityLng) 
				&& StringUtils.isBlank(cityLat)) {
			map.put("marketId", 1);
			map.put("marketName", "武汉白沙洲批发市场");
			return ErrorCodeEnum.SUCCESS;
		}
		
		map.put("cityLng", cityLng);
		map.put("cityLat", cityLat);
		
		if(StringUtils.isNotBlank(cityName)){
			logger.info("输入城市名: " + cityName);
			AreaDTO area = areaToolService.getByAreaName(cityName);
			if (area == null) {
				return ErrorCodeEnum.AREA_NAME_NOT_FOUND;
			}
			
			map.put("cityLng", area.getLng());
			map.put("cityLat", area.getLat());
		}
		
		List<MarketDTO> marketList = getHessianMarketService().getNearbyMarket(map);
		if(marketList != null && marketList.size() > 0){
			map.clear();
			map.put("marketId", marketList.get(0).getId());
			map.put("marketName", marketList.get(0).getMarketName());
		}
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public MarketDTO getById(String id) throws Exception{
		
		return getHessianMarketService().getById(id);
	}
	@Override
	public MarketDTO getMarketById(String marketId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> parmsMap = new HashMap<String, Object>();
		parmsMap.put("marketId", marketId);
		MarketDTO dto = this.getHessianMarketService().getMarketById(parmsMap);
		return dto;
	}
	 
	
	/*@Override
	public List<MarketDTO> getMarketList() throws Exception {
		// TODO Auto-generated method stub 
		List<MarketDTO> list = this.getHessianMarketService().getMarketList(null);
		return list;
	}*/}
