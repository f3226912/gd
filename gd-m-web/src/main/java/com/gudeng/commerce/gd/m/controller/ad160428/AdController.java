package com.gudeng.commerce.gd.m.controller.ad160428;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.m.service.AdToolService;
import com.gudeng.commerce.gd.m.service.PromChainToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.web.controller.BaseController;

/**
 * 
 * @author Semon
 *
 */
@Controller
@RequestMapping("/ad")
public class AdController extends BaseController {

	@Autowired
	private AdToolService adToolService;
	
	@Autowired
	public GdProperties gdProperties;
	
    @Autowired
    public PromChainToolService promChainToolService;

	@RequestMapping("/getNpsAdIndex")
	public String getAdList(HttpServletRequest request,Model model) {
//		List<Object> l = new ArrayList();
		// 获取商铺id
		String businessStr = request.getParameter("businessId")!=null?request.getParameter("businessId"):"0";
		String memberIdStr = request.getParameter("userId")!=null?request.getParameter("userId"):"0";
		try {
			if(StringUtils.isBlank(businessStr))businessStr="0";
			if(StringUtils.isBlank(memberIdStr))memberIdStr="0";
			// 获取所有的广告位以及对应的广告内容
			//menuId 菜单id
			List<AdAdvertiseDTO> list = adToolService.getAdByMenuId(45);

			// 获取所有的产地供应商的分类
			List<ProductCategoryDTO> plist = adToolService.getTopCategoryDTO(3l);

			String imgHost = gdProperties.getProperties().getProperty("img.host.url");
			List<String> lunbocn = new ArrayList<String>();
			Map<String, AdAdvertiseDTO> adMap = new HashMap<String, AdAdvertiseDTO>();
			for (AdAdvertiseDTO ad : list) {

				if(StringUtils.isNotBlank(ad.getAdUrl())){
					ad.setAdUrl(imgHost+ad.getAdUrl());
				}
				if(StringUtils.isNotBlank(ad.getReplaceImg())){
					ad.setReplaceImg(imgHost+ad.getReplaceImg());
				}
				adMap.put(ad.getSpaceSign(), ad);
				String ads = ad.getSpaceSign();
				if(ads.contains("A")){
					lunbocn.add(ads);
				}
			}
			// 要显示的分类Map
			Map<Long, ProductCategoryDTO> cateMap = new HashMap<Long, ProductCategoryDTO>();

			List<Long> shopCateIdList = new ArrayList<Long>();
			Long businessId = Long.parseLong(businessStr);
			// 如果有商铺id，获取商铺的主营分类
			if (businessId>0) {
				List<ReBusinessCategoryDTO> recateList = adToolService.getShopCategoryList(businessId, -1, -1);
				// 商铺的主营分类是否为空
				if (CollectionUtils.isNotEmpty(recateList)) {
					for (ReBusinessCategoryDTO tempCateId : recateList) {
						shopCateIdList.add(tempCateId.getCategoryId());
					}
					// 获取 商铺相应 的地产供应商的分类
					if (shopCateIdList.size() > 0) {
						Long[] gysCateIds = adToolService.getGysCateByShopCate(shopCateIdList);
						List<Long> tempList = Arrays.asList(gysCateIds);
						if(CollectionUtils.isEmpty(tempList)){//如果没有分类关联关系，展示全部分类
							for (ProductCategoryDTO p : plist) {
								cateMap.put(p.getCategoryId(), p);
							}
						}else{
							for(ProductCategoryDTO p : plist) {
								// 过滤出主营分类的分类信息
								if(tempList.contains(p.getCategoryId())) 
								{//如果有分类关联关系，只展示相应分类
								cateMap.put(p.getCategoryId(), p);
								}
							}
							
						}

					}
				}else {// 如果没有关注分类，显示所有分类
					for (ProductCategoryDTO p : plist) {
						cateMap.put(p.getCategoryId(), p);
					}
				}
			} else {// 如果没有商铺，显示所有分类
				for (ProductCategoryDTO p : plist) {
					cateMap.put(p.getCategoryId(), p);
				}
			}

//			l.add(adMap);
//			l.add(cateMap);
			model.addAttribute("adMap", adMap);
			model.addAttribute("memberId",memberIdStr);
			model.addAttribute("lunbocn",lunbocn);
			
			
			//20160526 由于前段展示规则有变动，所以以下代码是满足届时的需求，不影响前一期
			model.addAttribute("cateCount", cateMap.size());
			model.addAttribute("keyMap", getKeyMap());
			// 如果只关联了一个分类
			
			if(cateMap.size()==1){
				Map<Long, ProductCategoryDTO> oneCateMap = new HashMap<Long, ProductCategoryDTO>();
				
		        Collection<ProductCategoryDTO> coll=cateMap.values();  
		        Iterator<ProductCategoryDTO> iterator = coll.iterator();
		        ProductCategoryDTO tempPc = (ProductCategoryDTO)iterator.next(); 
		        oneCateMap.put(tempPc.getCategoryId(), tempPc);
		        cateMap.clear();
		        
				for (ProductCategoryDTO p : plist) {
					if(p.getCategoryId()==tempPc.getCategoryId()) continue;
					//放入剩余没有关联的分类
					cateMap.put(p.getCategoryId(), p);
				}
				model.addAttribute("oneCateMap", oneCateMap);
				model.addAttribute("gzCateId", tempPc.getCategoryId());
			}
			model.addAttribute("cateMap", cateMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "advertise/npsadindex";
		
	}
	
	/**
	 * 2016-07-11 为了不影响老版本的，重新复制一个方法
	 * @param request
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/getNpsAdIndex0721")
	public String getAdList0721(HttpServletRequest request,Model model) {
//		List<Object> l = new ArrayList();
		// 获取商铺id
		String businessStr = request.getParameter("businessId")!=null?request.getParameter("businessId"):"0";
		String memberIdStr = request.getParameter("userId")!=null?request.getParameter("userId"):"0";
		try {
			if(StringUtils.isBlank(businessStr))businessStr="0";
			if(StringUtils.isBlank(memberIdStr))memberIdStr="0";
			// 获取所有的广告位以及对应的广告内容
			//menuId 菜单id
			List<AdAdvertiseDTO> list = adToolService.getAdByMenuId(45);

			// 获取所有的产地供应商的分类
			List<ProductCategoryDTO> plist = adToolService.getTopCategoryDTO(3l);

			String imgHost = gdProperties.getProperties().getProperty("img.host.url");
			List<String> lunbocn = new ArrayList<String>();
			Map<String, AdAdvertiseDTO> adMap = new HashMap<String, AdAdvertiseDTO>();
			for (AdAdvertiseDTO ad : list) {
                //20160711 获取产品活动价
/*				if(ad.getProductId()!=null&& ad.getProductId()!=""){
	                PromProdInfoDTO promProdInfoDTO = promChainToolService.getPromotionProduct(Long.valueOf(ad.getProductId()));

	                if(promProdInfoDTO != null && promProdInfoDTO.getActPrice() > 0){
	                	ad.setPrice(promProdInfoDTO.getActPrice());
	                	ad.setPromotion(1);
	                }
				}*/

				if(StringUtils.isNotBlank(ad.getAdUrl())){
					ad.setAdUrl(imgHost+ad.getAdUrl());
				}
				if(StringUtils.isNotBlank(ad.getReplaceImg())){
					ad.setReplaceImg(imgHost+ad.getReplaceImg());
				}
				adMap.put(ad.getSpaceSign(), ad);
				String ads = ad.getSpaceSign();
				if(ads.contains("A")){
					lunbocn.add(ads);
				}
			}
			// 要显示的分类Map
			Map<Long, ProductCategoryDTO> cateMap = new HashMap<Long, ProductCategoryDTO>();

			List<Long> shopCateIdList = new ArrayList<Long>();
			Long businessId = Long.parseLong(businessStr);
			// 如果有商铺id，获取商铺的主营分类
			if (businessId>0) {
				List<ReBusinessCategoryDTO> recateList = adToolService.getShopCategoryList(businessId, -1, -1);
				// 商铺的主营分类是否为空
				if (CollectionUtils.isNotEmpty(recateList)) {
					for (ReBusinessCategoryDTO tempCateId : recateList) {
						shopCateIdList.add(tempCateId.getCategoryId());
					}
					// 获取 商铺相应 的地产供应商的分类
					if (shopCateIdList.size() > 0) {
						Long[] gysCateIds = adToolService.getGysCateByShopCate(shopCateIdList);
						List<Long> tempList = Arrays.asList(gysCateIds);
						if(CollectionUtils.isEmpty(tempList)){//如果没有分类关联关系，展示全部分类
							for (ProductCategoryDTO p : plist) {
								cateMap.put(p.getCategoryId(), p);
							}
						}else{
							for(ProductCategoryDTO p : plist) {
								// 过滤出主营分类的分类信息
								if(tempList.contains(p.getCategoryId())) 
								{//如果有分类关联关系，只展示相应分类
								cateMap.put(p.getCategoryId(), p);
								}
							}
							
						}

					}
				}else {// 如果没有关注分类，显示所有分类
					for (ProductCategoryDTO p : plist) {
						cateMap.put(p.getCategoryId(), p);
					}
				}
			} else {// 如果没有商铺，显示所有分类
				for (ProductCategoryDTO p : plist) {
					cateMap.put(p.getCategoryId(), p);
				}
			}

//			l.add(adMap);
//			l.add(cateMap);
			model.addAttribute("adMap", adMap);
			model.addAttribute("memberId",memberIdStr);
			model.addAttribute("lunbocn",lunbocn);
			
			
			//20160526 由于前段展示规则有变动，所以以下代码是满足届时的需求，不影响前一期
			model.addAttribute("cateCount", cateMap.size());
			model.addAttribute("keyMap", getKeyMap());
			// 如果只关联了一个分类
			
			/*if(cateMap.size()==1){
				Map<Long, ProductCategoryDTO> oneCateMap = new HashMap<Long, ProductCategoryDTO>();
				
		        Collection<ProductCategoryDTO> coll=cateMap.values();  
		        Iterator<ProductCategoryDTO> iterator = coll.iterator();
		        ProductCategoryDTO tempPc = (ProductCategoryDTO)iterator.next(); 
		        oneCateMap.put(tempPc.getCategoryId(), tempPc);
		        cateMap.clear();
		        
				for (ProductCategoryDTO p : plist) {
					if(p.getCategoryId()==tempPc.getCategoryId()) continue;
					//放入剩余没有关联的分类
					cateMap.put(p.getCategoryId(), p);
				}
				model.addAttribute("oneCateMap", oneCateMap);
				model.addAttribute("gzCateId", tempPc.getCategoryId());
			}*/
			model.addAttribute("cateMap", cateMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "advertise/new_npsadindex";
		
	}	
	
	
	private Map<Long,String> getKeyMap(){
		Map<Long,String> map = new HashMap<Long,String>();
		
		map.put(1042l, "D");
		map.put(1100l, "E");
		map.put(1124l, "F");
		map.put(1125l, "G");
		map.put(1126l, "H");
		map.put(913l, "I");
		map.put(957l, "J");
		map.put(1127l, "K");
		
		return map;
	}
	
	

}
