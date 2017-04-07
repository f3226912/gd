package com.gudeng.commerce.gd.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.Area;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.FriendsLinksToolService;
import com.gudeng.commerce.gd.home.service.IndexToolService;
import com.gudeng.commerce.gd.home.service.MarketSaleService;
import com.gudeng.commerce.gd.home.service.MarketToolService;
import com.gudeng.commerce.gd.home.service.PricesToolService;
import com.gudeng.commerce.gd.home.service.ProCategoryToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.home.service.TransportationService;
import com.gudeng.commerce.gd.home.util.DateUtil;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.commerce.gd.supplier.dto.MarketSaleDTO;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class IndexController extends HomeBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	public IndexToolService indexToolService;
	
	@Autowired
	public MarketToolService marketToolService;
	
	@Autowired
	public ProCategoryToolService proCategoryToolService;
	
	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public TransportationService transportationService;
	
	@Autowired                                                                                                                      
	public QueryAreaToolService queryAreaService;
	
	@Autowired
	public PricesToolService pricesToolService;
	
	@Autowired
	public MarketSaleService marketSaleService;
	
	@Autowired
	public FriendsLinksToolService friendsLinksToolService;

	@RequestMapping("/index")
	public String initIndex(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		String marketId = "1";
		try {
			//判断市场ID
			String marketIdtmep = request.getParameter("marketId");
			if(null != marketIdtmep && !"".equals(marketIdtmep)){
				marketId = marketIdtmep;
				addCookie(response,"marketId",marketId,60*60*24*365);
			}else{
				marketId = getCookieByMarketId(request);
				if(null == marketId){
					marketId = "1";
					addCookie(response,"marketId",marketId,60*60*24*365);
				}
			}
			String type = request.getParameter("type");
			putModel("marketId",marketId);
			if(null == type || !"html".equals(type)){
				if("1".equals(marketId)){
					return "redirect:" + request.getScheme() + "://" + request.getServerName() + "/baishazhou.html";
				}else if("2".equals(marketId)){
					return "redirect:" + request.getScheme() + "://" + request.getServerName() + "/yulin.html";
				}
			}
			/*map.put("indexmarketid", marketId);
			
			//判断是否登录
			MemberBaseinfoDTO dto = getUser(request);
			String islogin = "false";
			if(null != dto){
				islogin = "true";
			}
			map.put("islogin", islogin);*/
			
			//公告数据
			/*Map<String, Object> queryNotice = new HashMap<String, Object>();
			queryNotice.put("state", "01");
			queryNotice.put("startRow", 0);
			queryNotice.put("endRow", 10);
			List<PushNoticeDTO> noticeList = indexToolService.getListByPushNoticePage(queryNotice);
			map.put("noticeList", noticeList);*/
			
			//市场行情
			Map<String, Object> queryPrices = new HashMap<String, Object>();
			queryPrices.put("maketId", marketId);
			queryPrices.put("startRow", 0);
			queryPrices.put("endRow", 50);
			List<PricesDTO> pricesList = pricesToolService.getByCondition(queryPrices);
			map.put("pricesList", pricesList);
			
			//市场动态
			MarketSaleDTO msdto = marketSaleService.getMarketSaleDTO();
			map.put("msdto", msdto);
			Map<String, Object> countProduct = new HashMap<String, Object>();
			Integer pint = productToolService.getCount(countProduct);
			//map.put("pint", pint);
			putModel("pint", pint);
			
			
			//产品分类
			List<ProductCategoryDTO> pclist = proCategoryToolService.listAllProductCategoryByMarketId(Long.parseLong(marketId));
			if(null != pclist){
				for(ProductCategoryDTO pcdto : pclist){
					if(null != pcdto){
						if(null != pcdto.getChildList() && pcdto.getChildList().size() > 0){
							List<ProductCategoryDTO> pclist3 = new ArrayList<ProductCategoryDTO>();
							for(ProductCategoryDTO pccdto : pcdto.getChildList()){
								if(null != pccdto.getChildList() && pccdto.getChildList().size() > 0){
									for(ProductCategoryDTO pcccdto : pccdto.getChildList()){
										pclist3.add(pcccdto);
										Map<String, Object> queryProduct = new HashMap<String, Object>();
										queryProduct.put("categoryId", pcccdto.getCategoryId());
										queryProduct.put("sortName", "createTime");
										queryProduct.put("state", "3");
										queryProduct.put("sortOrder", "d");
										queryProduct.put("startRow", 0);
										queryProduct.put("endRow", 20);
										
										List<ProductDto> ptemplist = productToolService.getProductList(queryProduct);
										if(null != ptemplist && ptemplist.size() > 0){
											for(int i = 0; i < ptemplist.size(); i ++){
												ProductDto pd = ptemplist.get(i);
												if(null == pd || "4".equals(pd.getRoleType())){
													ptemplist.remove(pd);
												}
											}
										}
										pcccdto.setProductList(ptemplist);
									}
								}
							}
							pcdto.setChildList3(pclist3);
						}
						//商铺
						Map<String, Object> queryBusiness = new HashMap<String, Object>();
						queryBusiness.put("categoryId", pcdto.getCategoryId());
						queryBusiness.put("startRow", 0);
						queryBusiness.put("endRow", 20);
						List<ReBusinessCategoryDTO> rbcdtolist = reBusinessCategoryToolService.getBySearch(queryBusiness);
						if(null != rbcdtolist && rbcdtolist.size() > 0){
							List<BusinessBaseinfoDTO> bbdtolist = new ArrayList<>();
							for(ReBusinessCategoryDTO rbcdto : rbcdtolist){
								BusinessBaseinfoDTO bbdto = businessBaseinfoToolService.getById(rbcdto.getBusinessId().toString());
								if(null != bbdto && "1".equals(bbdto.getMemberStatus())){
									bbdtolist.add(bbdto);
								}
							}
							pcdto.setBusinessList(bbdtolist);
						}
						//产品推送
						Map<String, Object> queryAdInfoF2 = new HashMap<String, Object>();
						queryAdInfoF2.put("adType", "02");
						queryAdInfoF2.put("adCanal", "01");
						queryAdInfoF2.put("marketId", marketId);
						queryAdInfoF2.put("categoryId", pcdto.getCategoryId());
						queryAdInfoF2.put("state", "01");
						queryAdInfoF2.put("startRow", 0);
						queryAdInfoF2.put("endRow", 2);
						List<PushAdInfoDTO> adInfoFList2 = indexToolService.getListByPushAdInfoShow(queryAdInfoF2);
						if(null != adInfoFList2 && adInfoFList2.size() > 0){
							pcdto.setAdInfoList(adInfoFList2);
						}
					}
				}
			}
			map.put("pclist", pclist);
			//map.put("pclistsize", pclist.size());
			putModel("pclistsize", pclist.size());
			
			//轮播图
			Map<String, Object> queryAdInfo = new HashMap<String, Object>();
			queryAdInfo.put("adType", "01");
			queryAdInfo.put("adCanal", "01");
			queryAdInfo.put("marketId", marketId);
			queryAdInfo.put("state", "01");
			queryAdInfo.put("startRow", 0);
			queryAdInfo.put("endRow", 10);
			List<PushAdInfoDTO> adInfoList = indexToolService.getListByPushAdInfoShow(queryAdInfo);
			map.put("adInfoList", adInfoList);
			
			//副图
			Map<String, Object> queryAdInfoF = new HashMap<String, Object>();
			queryAdInfoF.put("adType", "03");
			queryAdInfoF.put("adCanal", "01");
			queryAdInfoF.put("marketId", marketId);
			queryAdInfoF.put("state", "01");
			queryAdInfoF.put("startRow", 0);
			queryAdInfoF.put("endRow", 10);
			List<PushAdInfoDTO> adInfoFList = indexToolService.getListByPushAdInfoShow(queryAdInfoF);
			map.put("adInfoFList", adInfoFList);
			
			//货主找车
			Map<String, Object> queryCarLine = new HashMap<String, Object>();
			queryCarLine.put("startRow", 0);
			queryCarLine.put("endRow", 5);
			List<CarLineDTO> cllist = transportationService.getByCondition(queryCarLine);
			if(cllist !=null && cllist.size() >0){
				for (CarLineDTO cdto : cllist) {
					if (StringUtils.isEmpty(cdto.getCarType())) {
						cdto.setCarType("其他");
					}else {
						switch (cdto.getCarType()) {
						case "0":
							cdto.setCarType("厢式货车");
							break;
						case "1":
							cdto.setCarType("敞车");
							break;
						case "2":
							cdto.setCarType("冷藏车");
							break;
						case "3":
							cdto.setCarType("保温车");
							break;
						case "4":
							cdto.setCarType("其他");
							break;
						default:
							break;
						}
					}
					if (StringUtils.isEmpty(cdto.getSendGoodsType())) {
						cdto.setSendGoodsTypeString("其他");
					}else {
						switch (cdto.getSendGoodsType()) {
						case "0":
							cdto.setSendGoodsTypeString("零担");
							break;
						case "1":
							cdto.setSendGoodsTypeString("整车");
							break;
						case "2":
							cdto.setSendGoodsTypeString("其他");
							break;
						default:
							break;
						}
					}
					if (StringUtils.isEmpty(cdto.getSentDateType())) {
						cdto.setSentDateTypeString("不限");
					}else {
						switch (cdto.getSentDateType()) {
						case "0":
							cdto.setSentDateTypeString("上午");
							break;
						case "1":
							cdto.setSentDateTypeString("中午");
							break;
						case "2":
							cdto.setSentDateTypeString("下午");
							break;
						case "3":
							cdto.setSentDateTypeString("晚上");
							break;
						default:
							break;
						}
					}
					if(null != cdto.getSentDate()){
						cdto.setSendDateString(DateUtil.toString(cdto.getSentDate(), "MM-dd"));
					}else{
						cdto.setSendDateString("不限");
					}
					cdto.setCreateTimeString(DateUtil.toString(cdto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));	
					cdto.setEndDateString(DateUtil.toString(cdto.getEndDate(), DateUtil.DATE_FORMAT_DATEONLY));
					// 始发地省市县
					Area city = queryAreaService.getArea(String.valueOf(cdto.getS_cityId()));
					// Area area =
					// queryAreaService.getArea(String.valueOf(dto.getS_areaId()));
					if (null == city || "".equals(city.getArea())
							|| "市辖区".equals(city.getArea())
							|| "县".equals(city.getArea())
							|| "市".equals(city.getArea())) {
						Area province = queryAreaService.getArea(String.valueOf(cdto.getS_provinceId()));
						cdto.setStartPlace(province != null ? province.getArea() : "全国");
					} else {
						cdto.setStartPlace(city.getArea());
					}
					// 目的地省市县
					// Area e_province =
					// queryAreaService.getArea(String.valueOf(dto.getE_provinceId()));
					Area e_city = queryAreaService.getArea(String.valueOf(cdto
							.getE_cityId()));
					// Area e_area =
					// queryAreaService.getArea(String.valueOf(dto.getE_areaId()));
					if (null == e_city || "".equals(e_city.getArea())
							|| "市辖区".equals(e_city.getArea())
							|| "县".equals(e_city.getArea())
							|| "市".equals(e_city.getArea())) {
						Area province = queryAreaService.getArea(String
								.valueOf(cdto.getE_provinceId()));
						cdto.setEndPlace(province != null ? province.getArea() : "全国");
					} else {
						cdto.setEndPlace(e_city.getArea());
					}
				}
			}
			map.put("cllist", cllist);
			
			
			//司机找货
			Map<String, Object> queryGoods = new HashMap<String, Object>();                                                                    
			queryGoods.put("startRow", 0);
			queryGoods.put("endRow", 5);
			List<MemberAddressDTO> goodsList = transportationService.getGoodsListByCondition(queryGoods);
			convertPageList(goodsList);
			map.put("goodsList", goodsList);
			
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			//System.out.println(e);
		}
		
		//友情链接
		putModel("friendsLinksList",viewfriendsAll());
		
		return "index";
	}
	
	/**
	 * 初始化公告列表
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("notice-list")
	public String noticeList(HttpServletRequest request,ModelMap map){
		try {
			PageDTO<PushNoticeDTO> pageDto = PageUtil.create(PushNoticeDTO.class, 10);
			//查询条件
			Map<String, Object> query = new HashMap<String, Object>();
			//获取总数 通过query提供的参数 此处有service层提供方法
			query.put("state", "1");
			query.put("client", "5");
			int size = indexToolService.getPushNoticeTotal(query);
			//设置总数据
			pageDto.setTotalSize(size);
			//设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, query);
			//获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(indexToolService.getListByPushNoticePage(query));
			//数据添加到model 前台准备显示 
			putPagedata(pageDto);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "index/notice-list";
	}
	
	/**
	 * 初始化公告详情
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("notice-cont/{id}")
	public String noticeCont(@PathVariable("id") String id,HttpServletRequest request,ModelMap map){
		try {
			if(null != id){
				PushNoticeDTO dto = indexToolService.getByPushNotice(Long.parseLong(id));
				map.put("dto", dto);
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			map.put("msg", "系统维护中!");
		}
		return "index/notice-cont";
	}
	
	@ResponseBody
	@RequestMapping("index/getMarketList")
	public String getMarketList(){
		List<MarketDTO> list = null;
		try {
			list = marketToolService.getAllByStatusAndType("0", "2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}
	
	private void convertPageList(List<MemberAddressDTO> list) throws Exception {
		if (list != null && list.size() > 0) {
			for (MemberAddressDTO dto : list) {
				Area province = transportationService.getArea(String
						.valueOf(dto.getS_provinceId()));
				dto.setS_provinceName(province != null ? province.getArea()
						: "全国");
				Area city = transportationService.getArea(String.valueOf(dto
						.getS_cityId()));
				dto.setS_cityName(city != null ? city.getArea() : "");
				Area area = transportationService.getArea(String.valueOf(dto
						.getS_areaId()));
				dto.setS_areaName(area != null ? area.getArea() : "");
				Area f_province = transportationService.getArea(String
						.valueOf(dto.getF_provinceId()));
				dto.setF_provinceName(f_province != null ? f_province.getArea()
						: "全国");
				Area f_city = transportationService.getArea(String.valueOf(dto
						.getF_cityId()));
				dto.setF_cityName(f_city != null ? f_city.getArea() : "");
				Area f_area = transportationService.getArea(String.valueOf(dto
						.getF_areaId()));
				dto.setF_areaName(f_area != null ? f_area.getArea() : "");

				if (dto.getCarType()==null) {
					dto.setCarTypeString("其他");
				}else {
					switch (dto.getCarType()) {                                                                                               
					case 0:                                                                                                                   
						dto.setCarTypeString("厢式货车");                                                                                       
						break;                                                                                                                  
					case 1:                                                                                                                   
						dto.setCarTypeString("敞车");                                                                                           
						break;                                                                                                                  
					case 2:                                                                                                                   
						dto.setCarTypeString("冷藏车");  
						break; 
					case 3:                                                                                                                   
						dto.setCarTypeString("保温车");                                                                                         
						break;                                                                                                                  
					case 4:                                                                                                                   
						dto.setCarTypeString("其他");                                                                                           
						break;                                                                                                                  
					default:                                                                                                                  
						break;                                                                                                                  
					}                                                                                                                         
				}
				if (dto.getSendGoodsType()==null) {
					dto.setSendGoodsTypeString("其他"); 
				}else {
					switch (dto.getSendGoodsType()) {                                                                                         
					case 0:                                                                                                                   
						dto.setSendGoodsTypeString("零担");                                                                                     
						break;                                                                                                                  
					case 1:                                                                                                                   
						dto.setSendGoodsTypeString("整车");                                                                                     
						break;                                                                                                                  
					case 2:                                                                                                                   
						dto.setSendGoodsTypeString("其他");                                                                                     
						break;                                                                                                                  
					default:                                                                                                                  
						break;                                                                                                                  
					}  
				}
				if (dto.getGoodsType()==null) {
					dto.setGoodsTypeString("其他");
				}else {
					switch (dto.getGoodsType()) {                                                                                         
					case 0:                                                                                                                   
						dto.setGoodsTypeString("常温货");                                                                                     
						break;                                                                                                                  
					case 1:                                                                                                                   
						dto.setGoodsTypeString("冷藏");                                                                                     
						break;                                                                                                                  
					case 2:                                                                                                                   
						dto.setGoodsTypeString("鲜活水产");                                                                                     
						break; 
					case 3:                                                                                                                   
						dto.setGoodsTypeString("其他");                                                                                     
						break;           
					default:                                                                                                                  
						break;                                                                                                                  
					}           
					
				}
				if (dto.getSendDateType()==null) {
					dto.setSendDateTypeString("不限");
				}else {
					switch (dto.getSendDateType()) {                                                                                         
					case 0:                                                                                                                   
						dto.setSendDateTypeString("上午");                                                                                     
						break;                                                                                                                  
					case 1:                                                                                                                   
						dto.setSendDateTypeString("中午");                                                                                     
						break;                                                                                                                  
					case 2:                                                                                                                   
						dto.setSendDateTypeString("下午");                                                                                     
						break; 
					case 3:                                                                                                                   
						dto.setSendDateTypeString("晚上");                                                                                     
						break;           
					default:                                                                                                                  
						break;                                                                                                                  
					}           
				}
				dto.setPriceString(dto.getPrice() != null ? String.valueOf(dto
						.getPrice()) : "面议");
				
				if(null != dto.getSendDate()){
					dto.setSendDateString(DateUtil.toString(dto.getSendDate(),
							"MM-dd"));
				}else{
					dto.setSendDateString("不限");
				}
				dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(),
						DateUtil.DATE_FORMAT_DATEONLY));
			}
		}
	}
	
	//全国农贸市场
	@RequestMapping("marketMap")
	public String marketMap(HttpServletRequest request,ModelMap map){
		return "index/market-map";
	}
	
	//全国农贸市场
	@RequestMapping("jjMap")
	public String jjMap(HttpServletRequest request,ModelMap map){
		return "index/jjmap";
	}
	//网站地图
	@RequestMapping("siteMap")
	public String siteMap(HttpServletRequest request,ModelMap map){
		return "index/market-bdumap";
	}
	/**
	 * 查询审核通过的友情链接
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
    public List<FriendsLinksDTO> viewfriendsAll(){ 
		List<FriendsLinksDTO> friendsLinksList=null;
		try {
			friendsLinksList=friendsLinksToolService.viewFriendsAll();
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("viewfriendsAll error"+e.getMessage());
		}
		return friendsLinksList;
	}
}
