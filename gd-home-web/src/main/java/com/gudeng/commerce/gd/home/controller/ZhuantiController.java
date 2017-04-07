package com.gudeng.commerce.gd.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.IndexToolService;
import com.gudeng.commerce.gd.home.service.ProCategoryToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

@Controller
@RequestMapping("/zhuanti")
public class ZhuantiController extends HomeBaseController  {
	
	private static Logger logger = LoggerFactory.getLogger(ZhuantiController.class);
	
	public static ConcurrentHashMap<String,List<ProductCategoryDTO>> CacheMap = new ConcurrentHashMap<String,List<ProductCategoryDTO>>();

	
	@Autowired
	public ProCategoryToolService proCategoryToolService;
	
	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;	
	
	@Autowired
	public IndexToolService indexToolService;

	@RequestMapping("/teseguan")
	public String teSeGuan(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String marketId = "1";
		try{
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
//			String type = request.getParameter("type");
//			if(null == type || !"html".equals(type)){
//					return "redirect:" + request.getScheme() + "://" + request.getServerName() + "/teseguan.html";
//			}
			
			//产品分类
			List<ProductCategoryDTO> pclist = CacheMap.get("pclist"+marketId);
			if(pclist==null){
				logger.info("------------------------>获取左侧列表");
				logger.debug("------------------------>获取左侧列表");
				pclist = this.getPcList(marketId);
				CacheMap.put("pclist"+marketId, pclist);
			}
			map.put("pclist", pclist);
			//map.put("pclistsize", pclist.size());
			putModel("pclistsize", pclist.size());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
		}
		
		
		return "zhuanti/teseguan";
	}
	
	@RequestMapping("/goldMedal")
	public String goldMedal(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String marketId = "1";
		try{
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
//			String type = request.getParameter("type");
//			if(null == type || !"html".equals(type)){
//					return "redirect:" + request.getScheme() + "://" + request.getServerName() + "/teseguan.html";
//			}
			
			//产品分类
			List<ProductCategoryDTO> pclist = CacheMap.get("pclist"+marketId);
			if(pclist==null){
				logger.info("------------------------>获取左侧列表");
				logger.debug("------------------------>获取左侧列表");
				pclist = this.getPcList(marketId);
				CacheMap.put("pclist"+marketId, pclist);
			}
			map.put("pclist", pclist);
			//map.put("pclistsize", pclist.size());
			putModel("pclistsize", pclist.size());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
		}
		return "zhuanti/goldMedal/index";
	}
	
	@RequestMapping("/cultural")
	public String cultural(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		return "zhuanti/cultural";
	}
	@RequestMapping("/dating")
	public String dating(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		return "zhuanti/dating";
	}
	@RequestMapping("/vip")
	public String vip(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		return "zhuanti/vip";
	}
	
	
	@RequestMapping("/tuiguang_{marketName}")
	public String tuiguang(@PathVariable("marketName")String marketName,HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		String marketId = "1";
		try{
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
			
			//产品分类
			List<ProductCategoryDTO> pclist = CacheMap.get("pclist"+marketId);
			if(pclist==null){
				logger.info("------------------------>获取左侧列表");
				logger.debug("------------------------>获取左侧列表");
				pclist = this.getPcList(marketId);
				CacheMap.put("pclist"+marketId, pclist);
			}
			map.put("pclist", pclist);
			//map.put("pclistsize", pclist.size());
			putModel("pclistsize", pclist.size());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
		}
		
		
		return "zhuanti/tuiguang-"+marketName;
	}	
	
	
	private List<ProductCategoryDTO> getPcList(String marketId) throws Exception{
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
		return pclist;
		
	}	
}
