package com.gudeng.commerce.gd.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.PushAdAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AdToolService;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.PushAdInfoToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.RequestParamUtils;
import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;

@Controller
@RequestMapping("pushAdInfo")
public class PushAdInfoController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PushAdInfoController.class);
	
	@Autowired
	public PushAdInfoToolService pushAdInfoToolService;
	@Autowired
	public UsercollectProductToolService usercollectProductService;
	
	@Autowired
	private AdToolService adToolService;
	
	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	public ProductBaseinfoToolService productBaseinfoToolService;
	
	@RequestMapping("/adList")
	public void adList(HttpServletRequest request, HttpServletResponse response, PushAdInfoDTO inputPushAdInfoDTO) {
		String adCanal = inputPushAdInfoDTO.getAdCanal(); //渠道(01农批web 02农批宝 03农速通)
		String marketId = inputPushAdInfoDTO.getMarketId(); //市场id
		Integer number = RequestParamUtils.getIntegerValue(request, "number", 8);
		
		Map<String, Object> map = new HashMap<String, Object>();
		//默认农批宝
		if(StringUtils.isBlank(adCanal)){
			adCanal = "02";
		}else if("3".equals(adCanal)){
			adCanal = "03";
		}

		if(StringUtils.isNotBlank(marketId)){
			map.put("marketId", marketId);
		}
		
		ObjectResult result = new ObjectResult();
		try{
			map.put("adCanal", adCanal);
			map.put("adType", "01");
			map.put("startRow", 0);
			map.put("endRow", number);
			map.put("state", "01");  //可用的状态
			Long start = System.currentTimeMillis();
			/**
			 * 增加公告
			 */
			List<NstNoticeEntityDTO> nstList=pushAdInfoToolService.getNoticeList();
			StringBuffer sb=new StringBuffer();
//			String str="";
//			for (NstNoticeEntityDTO nstNoticeEntityDTO : nstList) {
//				sb.append(nstNoticeEntityDTO.getNotice()+",");
//			}
			for(int i=0;i<nstList.size();i++){
				if(i<nstList.size()-1){
					sb.append(nstList.get(i).getNotice()+"                  【公告】：");	
				}else{
					sb.append(nstList.get(i).getNotice());
				}
				
			}
			//去电最后面，号
//			if (sb!=null) {
//				str=sb.toString().substring(0, sb.toString().length()-1);
//			}
			List<PushAdAppDTO> pushAdInfoList = pushAdInfoToolService.getAdList(map);
			//设置进第一个广告的noticeString
			if(sb!=null){
				pushAdInfoList.get(0).setNoticeString(sb.toString());
			}
			
			logger.debug("adList cost time: " + (System.currentTimeMillis() - start));
			result.setObject(pushAdInfoList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取推送广告异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/productList")
	public void productList(HttpServletRequest request, HttpServletResponse response, PushAdInfoDTO inputPushAdInfoDTO) {
		ObjectResult result = new ObjectResult();
		String adCanal = inputPushAdInfoDTO.getAdCanal(); //渠道(01农批web 02农批宝 03农速通)
		String marketId = inputPushAdInfoDTO.getMarketId(); //市场id
		
		if(StringUtils.isBlank(marketId)){
			setResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
			return;
		}
		
		//默认农批宝
		if(StringUtils.isBlank(adCanal)){
			adCanal = "02";
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", marketId);
			map.put("adCanal", adCanal);
			map.put("adType", "02");
			map.put("state", "01");  //可用的状态
			
			CommonPageDTO pageDTO = getPageInfo(request, map);
//			Long start = System.currentTimeMillis();
			int pushTotal = pushAdInfoToolService.getProductTotal(map);
//			logger.debug("productList total cost time: " + (System.currentTimeMillis() - start));
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(pushTotal);
			pageDTO.initiatePage(pushTotal);
//			start = System.currentTimeMillis();
			List<PushProductDTO> pushAdInfoList = pushAdInfoToolService.getProductList(map);
			if(pushAdInfoList!=null && pushAdInfoList.size()>0){
				for(PushProductDTO dto:pushAdInfoList){
					//TODO hbd
					int platform = productBaseinfoToolService.checkProductActivity(dto.getProductId(),
							inputPushAdInfoDTO.getMemberId(), null, dto.getMarketId()); 
					dto.setPlatform(platform);
				}
			}
//			logger.debug("productList detail cost time: " + (System.currentTimeMillis() - start));
			pageDTO.setRecordList(pushAdInfoList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取推送产品异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getFarmersImg")
	public void getFarmersImg(HttpServletRequest request, HttpServletResponse response, PushAdInfoDTO inputPushAdInfoDTO) {
		ObjectResult result = new ObjectResult();
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startRow", 0);
			map.put("endRow", 1);
			map.put("adType", "04");
			map.put("state", "01");  //可用的状态
			List<PushAdAppDTO> pushAdInfoList = pushAdInfoToolService.getAdList(map);
			result.setObject(pushAdInfoList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取全国农贸市场图异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	
	
	@RequestMapping("/newAdList")
	public void newAdList(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String marketIdStr = (String)GSONUtils.getJsonValueStr(jsonStr, "marketId");
			String adSignHead = (String)GSONUtils.getJsonValueStr(jsonStr, "adSignHead");
//			String marketIdStr = request.getParameter("marketId");
			if(StringUtils.isBlank(marketIdStr)){
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
				return;
			}

			String Sign = MarketSign.getSignByMarketId(marketIdStr);
			if(StringUtils.isBlank(Sign)){
				//为了防止后面每上一个市场都要加一个枚举，所以菜单规则改成   市场ID 加 -01（01表示轮播图）20161012
				Sign = marketIdStr+"-01";
				//setEncodeResult(result, ErrorCodeEnum.MARKET_NO_AD, request, response);
				//return;
			}
			
			if(StringUtils.isNotBlank(adSignHead)){
				Sign = marketIdStr+"-02";
			}
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("menuSign", Sign);
			params.put("marketId", 3);
			params.put("ImgHost", gdProperties.getProperties().get("gd.image.server"));
			List<AdAdvertiseDTO> adList = adToolService.getAdBySignAndMarketId(params);
			result.setObject(adList);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取广告异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}	
	
	}	
	
	/**
	 * 市场菜单枚举
	 * @author Semon
	 *	3级菜单——武汉白沙洲批发市场首页（WHBSZHOME01）
		3级菜单——广西玉林批发市场首页（GXYLHOME01）
		3级菜单——黄石农批大市场首页（HSHOME01）
		3级菜单——钦州农批大市场首页（QZHOME01）
		3级菜单——开封农批大市场首页（KFHOME01）
		3级菜单——黄冈农批大市场首页（HGHOME01）
		3级菜单——濮阳农批大市场首页（PYHOME01）	
		3级菜单——徐州农批大市场首页（XZHOME01）
		3级菜单——洛阳农批大市场首页（LYHOME01）
		3级菜单——淮安农批大市场首页（HAHOME01）
		3级菜单——盘锦宏进农批大市场首页（PJHOME01）
		3级菜单——南阳中商农产品批发市场首页（NYZSHOME01）
		//20160706 新增一个市场
		3级菜单——恩施华硒生态农产品批发市场首页（HXSTHOME01）
		//20161011 新增一个市场
		3级菜单—— 十堰市神定河批发市场(SYSDHHOME01);

	 */
	private enum MarketSign{
		
		/*
		 * 白沙洲
		 */
		BaiShaZhou("1","WHBSZHOME01"),
		
		/*
		 * 广西玉林
		 */
		YuLin("2","GXYLHOME01"),
		
		/*
		 *黄石 
		 */
		HuangShi("413","HSHOME01"),
		
		/*
		 * 开封
		 */
		KaiFeng("415","KFHOME01"),
		
		/*
		 * 濮阳
		 */
		PuYang("417","PYHOME01"),
		
		/*
		 * 洛阳
		 */
		LuoYang("419","LYHOME01"),
		
		/*
		 * 盘锦
		 */
		PanJin("438","PJHOME01"),
		
		/*
		 * 钦州
		 */
		QinZhou("414","QZHOME01"),
		
		/*
		 * 黄冈
		 */
		HuangGang("416","HGHOME01"),
		
		/*
		 * 徐州
		 */
		XuZhou("418","XZHOME01"),
		
		/*
		 * 淮安
		 */
		HuaiAn("420","HAHOME01"),
		
		/*
		 * 华硒生态
		 */
		HuaXi("470","HXSTHOME01"),
		
		/*
		 * 南阳
		 */
		NanYang("459","NYZSHOME01"),
		
		/*
		 * 十堰神定河
		 */
		ShiYan("501","SYSDHHOME01");
		
		MarketSign(String marketId,String Sign){
			this.marketId = marketId;
			this.Sign = Sign;
		}
		
		private String marketId;
		
		private String Sign;

		public String getMarketId() {
			return marketId;
		}

		@SuppressWarnings("unused")
		public void setMarketId(String marketId) {
			this.marketId = marketId;
		}

		public String getSign() {
			return Sign;
		}
		@SuppressWarnings("unused")
		public void setSign(String sign) {
			Sign = sign;
		}
		
		/**
		 * 根据marketId获取对象
		 * @param marketId
		 * @return
		 */
		public static MarketSign getEnumByMarketId(String marketId) {
			for (MarketSign enm : MarketSign.values()) {
				if (enm.getMarketId().equals(marketId)) {
					return enm;
				}
			}
			return null;
		}
		public static String getSignByMarketId(String marketId) {
			MarketSign enm = getEnumByMarketId(marketId);
			return enm == null ? null : enm.getSign();
		}
		
		
		
	}
	
	
	
}
