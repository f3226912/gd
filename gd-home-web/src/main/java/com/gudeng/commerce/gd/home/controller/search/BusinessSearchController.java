package com.gudeng.commerce.gd.home.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gudeng.commerce.gd.home.controller.HomeBaseController;
import com.gudeng.commerce.gd.home.dto.SearchPageDTO;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.BusinessProducttypeToolService;
import com.gudeng.commerce.gd.home.service.ProCategoryToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.home.service.UsercollectShopToolService;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * @Description 商铺搜索功能模块
 * @Project gd-home-web
 * @ClassName ProductSearchController.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月22日 上午11:51:09
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Controller
@RequestMapping("business")
public class BusinessSearchController extends HomeBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ProductSearchController.class);

	private static final int PAGE_SIZE = 10;
	@Autowired
	public ProductToolService productToolService;
	@Autowired
	public QueryAreaToolService queryAreaService;
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	public BusinessProducttypeToolService businessProducttypeToolService;
	@Autowired
	public UsercollectShopToolService usercollectShopToolService;
	@Autowired
	public UsercollectProductToolService usercollectProductToolService;
	@Autowired
	public ProCategoryToolService proCategoryToolService;

	/**
	 * @Description getBusinessList 搜索商铺列表
	 * @param request
	 * @param productQueryBean
	 * @param mv
	 * @return
	 * @CreationDate 2015年10月27日 下午2:22:33
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping(value = { "list", "list/{categoryId}", "{marketId}/list", "list/query/{marketId}/{categoryId}-{pageNow}" }, produces = "text/html;charset=UTF-8")
	public ModelAndView getBusinessList(HttpServletRequest request, ModelAndView mv, BusinessQueryBean businessQueryBean, SearchPageDTO searchPage) {
		String pageNow = StringUtils.isEmpty(searchPage.getPageNow()) ? getRequest().getParameter("pageNow") : searchPage.getPageNow();
		String marketId = getCookieByMarketId(getRequest());// 市场ID
		String keyWord = null;// 搜索关键词
		keyWord = getRequest().getParameter("keyWord");// 搜索关键词
		if (businessQueryBean.getMarketId() != null) {
			marketId = businessQueryBean.getMarketId().toString();
		}
		businessQueryBean.setShopsName(keyWord);// 搜索关键词
		businessQueryBean.setMainProduct(keyWord);// 搜索关键词
		/*** 分页当前页 ***/
		int pageNum = 1;
		if (pageNow != null && !"".equals(pageNow.trim())) {
			pageNum = Integer.valueOf(pageNow);
		}
		/** 商品总数 **/
		Integer size = 0;
		BusinessSearchResultDTO businessSearchResultDTO = null;// 搜索结果集合,包含商铺集合和商铺总数
		/** 商品集合 ***/
		List<BusinessSolrDTO> businessSolrDTOList = null;
		/** 分页页面大小 **/
		businessQueryBean.setPageSize(PAGE_SIZE);
		/*** 分页返回结果，起始行数数 **/
		businessQueryBean.setStartRow((pageNum - 1) * PAGE_SIZE);
		/*** 分页总数 ***/
		int pageTotal = 1;

		// 搜索结果集合
		try {
			/**** 产品类型名称 ***/
			String cateName = null;
			if (businessQueryBean.getCategoryId() != null && businessQueryBean.getCategoryId() > 0) {
				ProductCategoryDTO productCategory = proCategoryToolService.getProductCategoryById(Long.valueOf(businessQueryBean.getCategoryId()));
				cateName = productCategory.getCateName();
				marketId = productCategory.getMarketId().toString();
			} else {
				businessQueryBean.setCategoryId(null);
			}
			if (StringUtils.isNumeric(marketId)) {
				businessQueryBean.setMarketId(Integer.valueOf(marketId));// 市场Id
				// 产品分类
				List<ProductCategoryDTO> pclist = proCategoryToolService.listAllProductCategoryByMarketId(Long.parseLong(marketId));
				if (null != pclist) {
					for (ProductCategoryDTO pcdto : pclist) {
						if (null != pcdto) {
							if (null != pcdto.getChildList() && pcdto.getChildList().size() > 0) {
								List<ProductCategoryDTO> pclist3 = new ArrayList<ProductCategoryDTO>();
								for (ProductCategoryDTO pccdto : pcdto.getChildList()) {
									if (null != pccdto.getChildList() && pccdto.getChildList().size() > 0) {
										for (ProductCategoryDTO pcccdto : pccdto.getChildList()) {
											pclist3.add(pcccdto);
										}
									}
								}
								pcdto.setChildList3(pclist3);
							}
						}
					}
				}
				putModel("pclistsize", pclist.size());
				mv.addObject("pclist", pclist);
			}

			businessSearchResultDTO = businessBaseinfoToolService.getByBusinessQueryBean(businessQueryBean);// 根据查询条件得到结果集
			if (businessSearchResultDTO != null) {
				size = Integer.valueOf(String.valueOf(businessSearchResultDTO.getCount()));// 商铺总数
				businessSolrDTOList = businessSearchResultDTO.getList();// 商铺集合
			}
			mv.addObject("cateName", cateName);
			marketId = businessQueryBean.getMarketId().toString();
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("marketId", marketId);
		map.put("curLevel", 0);
		List<ProductCategoryDTO> categories = null;
		try {
			categories = productToolService.listProductCategoryByLvAndMarketId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageTotal = size % PAGE_SIZE == 0 ? size / PAGE_SIZE : size / PAGE_SIZE + 1;
		mv.addObject("businessSolrDTOList", businessSolrDTOList);
		mv.addObject("totalCount", size);// 记录总数
		mv.addObject("businessQueryBean", businessQueryBean);
		mv.addObject("pageNow", pageNum);// 当前页
		mv.addObject("pageTotal", pageTotal);// 总页数
		mv.addObject("keyWord", keyWord);// 搜索关键字
		mv.addObject("searchModel", 1);// 搜索模式，奇数标识搜索商铺
		mv.addObject("marketId", marketId);// 市场Id
		mv.addObject("categories", categories);// 商铺类别
		mv.setViewName("search/business/market-company");
		return mv;
	}
}
