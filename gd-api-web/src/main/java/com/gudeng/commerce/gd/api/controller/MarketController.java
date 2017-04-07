package com.gudeng.commerce.gd.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 功能描述：市场信息控制中心
 * 
 */
@Controller
@RequestMapping("market")
public class MarketController extends GDAPIBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MarketController.class);
	@Autowired
	private MarketToolService marketToolService;
	@Autowired
	private AreaToolService areaToolService;
	@Autowired
	public GdProperties gdProperties;
	
	@RequestMapping("/getOne")
	public void getOne(HttpServletRequest request, HttpServletResponse response, AreaDTO inputAreaDTO) {
		ObjectResult result = new ObjectResult();
		String city = inputAreaDTO.getArea();  //城市名
		if(StringUtils.isBlank(city)){
			setResult(result, ErrorCodeEnum.AREA_NAME_IS_NULL, request, response);
			return;
		}
		
		try{
			AreaDTO area = areaToolService.getByAreaName(city);
			List<MarketDTO> marketList = null;
			if(area != null){
				marketList = marketToolService.getByCondition(area.getFather(), area.getAreaID(), "2");
			}
			
			//如果找不到市场, 则默认武汉白沙洲
			if(marketList == null || marketList.size() == 0){
				marketList = marketToolService.getByCondition("420000", "420100", "2");
			}
			
			MarketDTO marketInfo = marketList.get(0);
			result.setObject(marketInfo);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取市场信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getList")
	public void getList(HttpServletRequest request, HttpServletResponse response, AreaDTO inputAreaDTO) {
		ObjectResult result = new ObjectResult();
		String city = inputAreaDTO.getArea();  //城市名
		try{
			AreaDTO area = inputAreaDTO;
			if(StringUtils.isNotBlank(city)){
				logger.info("输入城市名: " + city);
				area = areaToolService.getByAreaName(city);
				if(area == null){
					setResult(result, ErrorCodeEnum.AREA_NAME_NOT_FOUND, request, response);
					return;
				}
			}
			
			logger.info("城市名: " + area.getArea());
			logger.info("城市名ID: " + area.getAreaID());
			logger.info("城市名父Id: " + area.getFather());
			
			List<MarketDTO> marketList = null;
			if(area != null){
				marketList = marketToolService.getByCondition(area.getFather(), area.getAreaID(), "1");  //自营市场
				marketList.addAll(marketToolService.getByCondition(area.getFather(), area.getAreaID(), "3")); //用户增加市场
			}
			
			result.setObject(marketList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取市场列表异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/addReMarket")
	public void addReMarket(HttpServletRequest request, HttpServletResponse response, ReMemberMarketDTO inputReMemberMarketDTO){
		ObjectResult result = new ObjectResult();
		Long memberId = inputReMemberMarketDTO.getMemberId();
		Long marketId = inputReMemberMarketDTO.getMarketId();
		if(memberId == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		if(marketId == null){
			setResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			marketToolService.deleteReMemberMarket(memberId);
			marketToolService.addReMemberMarket(memberId, marketId);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]关注运营场所异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/addMarket")
	public void addMarket(HttpServletRequest request, HttpServletResponse response, MarketDTO inputMarketDTO){
		ObjectResult result = new ObjectResult();
		Long memberId = inputMarketDTO.getMemberId();
		String cityName = inputMarketDTO.getCityName();
		String marketName = inputMarketDTO.getMarketName();
		if(StringUtils.isBlank(cityName)){
			setResult(result, ErrorCodeEnum.AREA_NAME_IS_NULL, request, response);
			return;
		}
		
		if(StringUtils.isBlank(marketName)){
			setResult(result, ErrorCodeEnum.MARKET_NAME_IS_NULL, request, response);
			return;
		}
		
		if(memberId == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			logger.info("输入城市名: " + cityName + ",市场名: " + marketName);
			AreaDTO area = areaToolService.getByAreaName(cityName);
			if(area == null){
				setResult(result, ErrorCodeEnum.AREA_NAME_NOT_FOUND, request, response);
				return;
			}
			
			MarketDTO marketDTO = new MarketDTO();
			//无父级
			if(StringUtils.isBlank(area.getFather())){
				marketDTO.setProvinceId(Long.parseLong(area.getAreaID()));
			}else{
				marketDTO.setProvinceId(Long.parseLong(area.getFather()));
				marketDTO.setCityId(Long.parseLong(area.getAreaID()));
			}
			
			marketDTO.setMarketName(marketName);
			marketDTO.setMarketType("3");//用户自定义市场
			marketDTO.setStatus("0");
			Long marketId = marketToolService.addMarket(marketDTO);
			marketToolService.deleteReMemberMarket(memberId);
			marketToolService.addReMemberMarket(memberId, marketId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", marketId);
			map.put("marketName", marketName);
			result.setObject(map);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]添加运营场所异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getOurOwnMarket")
	public void getOurOwnMarket(HttpServletRequest request, HttpServletResponse response, MarketDTO inputMarketDTO){
		ObjectResult result = new ObjectResult();
		try{
			List<MarketDTO> marketList = marketToolService.getByCondition2_5("", "", "2");
			result.setObject(marketList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]查找我们的批发市场异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 根据市场ID获取市场信息
	 */
	@RequestMapping("/getMarket")
	public void getMarketListByMarket(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			MarketDTO inputDTO = (MarketDTO) getDecryptedObject(request, MarketDTO.class);
			//MarketDTO inputDTO = new MarketDTO();
			//inputDTO.setId(Long.valueOf(request.getParameter("id")));
			MarketDTO marketDto = null;
			if(null != inputDTO.getId() && !"".equals(inputDTO.getId())){
				marketDto = marketToolService.getMarketById(String.valueOf(inputDTO.getId()));
			} 
			if(null != marketDto){
				String imgRootUrl = gdProperties.getProperties().getProperty("gd.image.server");
				marketDto.setMarketImg(imgRootUrl+marketDto.getMarketImg());
				marketDto.setBusinessAmount(marketDto.getBusinessAmount()+"个");
				marketDto.setAddress(marketDto.getProvinceName()+" "+marketDto.getCityName()+" "+marketDto.getAreaName());
			}
			result.setObject(marketDto);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]根据市场ID获取市场信息异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
