package com.gudeng.commerce.gd.home.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReCategoryBanelImgDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.Constant;
import com.gudeng.commerce.gd.home.controller.HomeBaseController;
import com.gudeng.commerce.gd.home.dto.SearchPageDTO;
import com.gudeng.commerce.gd.home.dto.StoreLeftDTO;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.BusinessProducttypeToolService;
import com.gudeng.commerce.gd.home.service.ProCategoryToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.home.service.ReCategoryBanelImgToolService;
import com.gudeng.commerce.gd.home.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.home.service.UsercollectShopToolService;
import com.gudeng.commerce.gd.home.util.BusinessUtil;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductSolrDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * @Description 产品搜索功能模块
 * @Project gd-home-web
 * @ClassName ProductSearchController.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月22日 上午11:51:09
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Controller
@RequestMapping("")
public class ProductSearchController extends HomeBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ProductSearchController.class);

	private static final int PAGE_SIZE = 16;
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
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	@Autowired
	public ReCategoryBanelImgToolService reCategoryBanelImgToolService;

	/**
	 * @Description getProduct 搜索单个产品详细信息,目前搜索条件不确定，暂默认以产品ID来查找
	 * @param ProductDto
	 *            productDto
	 * @param ModelAndView
	 *            mv
	 * @return ModelAndView
	 * @CreationDate 2015年10月22日 上午11:52:26
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping("product/{productId}")
	public ModelAndView getProduct(HttpServletRequest request, @PathVariable String productId, ModelAndView mv) {
		ProductDto productDto = null;
		try {
			if (productId != null) {
				// 获取产品基本信息,如果产品价格是多价区间，则一起查出多价列表
				productDto = productToolService.getByProductId(productId);
				if (productDto != null) {
					int visitCount = 0;
					if (productDto.getVisitCount() != null) {
						visitCount = productDto.getVisitCount();
					}
					productDto.setVisitCount(visitCount + 1);
					try {
						productToolService.updateProduct(productDto);// 增加点击数
					} catch (Exception e) {
					}
					// 省市区
					if (productDto.getProvinceId() != null) {
						productDto.setProvinceName(queryAreaService.getArea(String.valueOf(productDto.getProvinceId())).getArea());
					}
					if (productDto.getCityId() != null) {
						productDto.setCityName(queryAreaService.getArea(String.valueOf(productDto.getCityId())).getArea());
					}
					if (productDto.getAreaId() != null) {
						productDto.setAreaName(queryAreaService.getArea(String.valueOf(productDto.getAreaId())).getArea());
					}
					// 查找商品图片列表
					List<ProductPictureDto> pictureList = productToolService.getPictureByProductId(String.valueOf(productDto.getProductId()));
					/*
					 * 获取对应商铺的产品
					 */
					Map<String, Object> query = new HashMap<String, Object>();
					String businessId = String.valueOf(productDto.getBusinessId());
					/*
					 * 加载右边商铺信息数据
					 */
					initStoreLeft(businessId);
					mv.addObject("busiInfoUrl", getStoreImgUrl(businessId));
					query.put("businessId", businessId);
					// 产品总数
					mv.addObject("busiProductTotal", productToolService.getCount(query));
					/*
					 * 限制显示产品数量
					 */
					query.put(Constant.START_ROW, 0);
					query.put(Constant.ROW_SIZE, 4);
					query.put("state", 3);// 已上架的产品
					/*
					 * 查询是否收藏此店铺
					 */
					mv.addObject("isLogin", checkLogin(getRequest()));
					/*
					 * 查询是否收藏此店铺
					 */
					MemberBaseinfoEntity user = getUser(getRequest());

					// 判断是否登录
					if (user == null) {
						putModel("isFocus", false);
					} else {
						UsercollectProductDTO usDTO = usercollectProductToolService.getCollect(user.getMemberId(), productDto.getProductId());
						if (usDTO == null) {
							putModel("isFocus", false);

							// usercollectShopToolService.focusShop(1L, Long.valueOf(bid));
						} else {
							putModel("isFocus", true);
						}
					}
					// 获得对应商铺对象
					mv.addObject("busiInfo", businessBaseinfoToolService.getById(businessId));
					// 获得对应商铺自定义类别
					mv.addObject("busiProducttypeList", businessProducttypeToolService.getByBusinessId(businessId));
					mv.addObject("productList", productToolService.getProductList(query));// 最新商品
					mv.addObject("pictureList", pictureList);
					mv.addObject("product", productDto);
					mv.addObject("bid", businessId);
					mv.setViewName("search/product/products-detail");
				} else {
					mv.setViewName("search/noResult");
				}
			} else {
				mv.setViewName("search/noResult");
			}
		} catch (Exception e) {
			mv.setViewName("search/noResult");
			logger.trace(e.getMessage());
		}
		return mv;
	}

	/**
	 * @Description getProductList 搜索产品列表,根据产品名称，搜索封装条件查询
	 * @param ProductQueryBean
	 *            productQueryBean搜索条件集合
	 * @param mv
	 * @return
	 * @CreationDate 2015年10月23日 上午10:02:43
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping(value = { "product/list", "product/list/{marketId}/{cateId}", "product/list/{cateId}", "product/{marketId}/list",
			"product/list/query/{marketId}/{cateId}-{pageNow}" }, produces = "text/html;charset=UTF-8")
	public ModelAndView getProductList(HttpServletRequest request, ModelAndView mv, ProductQueryBean productQueryBean,SearchPageDTO searchPage) {
		// 排序方式
		String priceOrder = StringUtils.isEmpty(getRequest().getParameter("priceOrder")) ? searchPage.getPriceOrder() : getRequest().getParameter("priceOrder");
		// 当前页
		String pageNow = StringUtils.isEmpty(getRequest().getParameter("pageNow")) ? searchPage.getPageNow() : getRequest().getParameter("pageNow");
		String isBig = getRequest().getParameter("isBig");
		String cateId1 = getRequest().getParameter("cateId1");// 产品分类1
		String cateId2 = getRequest().getParameter("cateId2");// 产品分类2
		String cateId3 = getRequest().getParameter("cateId3");// 产品分类3
		String marketId = getCookieByMarketId(getRequest());// 市场Id
		String keyWord = null;// 搜索关键词
		keyWord = getRequest().getParameter("keyWord");// 搜索关键词
		if (productQueryBean.getMarketId() != null) {
			marketId = productQueryBean.getMarketId().toString();
		}
		/*** 分页当前页 ***/
		int pageNum = 1;
		if (!StringUtils.isEmpty(pageNow)) {
			pageNum = Integer.valueOf(pageNow);
		}
		/** 排序 **/
		Map<String, String> order = new HashMap<String, String>();
		if (StringUtils.isEmpty(priceOrder)) {
			order.put("createTime", "desc");
		} else {
			order.put("price", priceOrder);
		}
		productQueryBean.setOrder(order);// 排序规则
		/** 商品总数 **/
		Integer size = 0;
		ProductSearchResultDTO productSearchResultDTO = null;// 搜索结果集合,包含商品集合和商品总数
		/** 商品集合 ***/
		List<ProductSolrDTO> productSolrDTOList = null;
		/** 分页页面大小 **/
		productQueryBean.setPageSize(PAGE_SIZE);
		/*** 分页返回结果，起始行数数 **/
		productQueryBean.setStartRow((pageNum - 1) * PAGE_SIZE);
		/*** 分页总数 ***/
		int pageTotal = 1;
		/**** 一、二、 三级菜单 ****/
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProductCategoryDTO> topCate = null, secondCate = null, thirdCate = null;
		// 搜索结果集合
		try {
			/**** 产品类型名称 ***/
			String cateName = null;
			/*** 产品类型 ***/
			if (productQueryBean.getCateId() != null && productQueryBean.getCateId() > 0) {

			} else if (productQueryBean.getCateId() == null || productQueryBean.getCateId() == 0) {
				productQueryBean.setCateId(null);
			} else {
				if (StringUtils.isNumeric(cateId3)) {
					productQueryBean.setCateId(Long.valueOf(cateId3));// 第三级产品类型
				} else if (StringUtils.isNumeric(cateId2)) {
					productQueryBean.setCateId(Long.valueOf(cateId2));// 第二级产品类型
				} else if (StringUtils.isNumeric(cateId1)) {
					productQueryBean.setCateId(Long.valueOf(cateId1));// 第一级产品类型
				}
			}
			cateName = productQueryBean.getCateId() == null ? null : proCategoryToolService.getProductCategoryById(productQueryBean.getCateId()).getCateName();
			ProductCategoryDTO productCategory = null;
			if (productQueryBean.getCateId() != null && productQueryBean.getCateId() > 0) {
				productCategory = proCategoryToolService.getProductCategoryById(productQueryBean.getCateId());
				cateName = productCategory.getCateName();
				marketId = productCategory.getMarketId().toString();
			}
			mv.addObject("cateName", cateName);
			mv.addObject("productCategory", productCategory);
			if (!StringUtils.isEmpty(keyWord)) {
				productQueryBean.setName(keyWord);// 搜索关键词
			}
			map.put("marketId", marketId);
			map.put("curLevel", 0);// 一级菜单
			topCate = productToolService.listProductCategoryByLvAndMarketId(map);
			map.put("curLevel", 1);// 二级菜单
			secondCate = productToolService.listProductCategoryByLvAndMarketId(map);
			map.put("curLevel", 2);// 三级菜单
			thirdCate = productToolService.listProductCategoryByLvAndMarketId(map);
			/*** 市场Id **/
			if (StringUtils.isNumeric(marketId)) {
				productQueryBean.setMarketId(Long.valueOf(marketId));// 市场Id
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
			productSearchResultDTO = productToolService.getByProductQueryBean(productQueryBean);// 根据查询条件得到结果集
			if (productSearchResultDTO != null && productSearchResultDTO.getCount() > 0) {
				size = Integer.valueOf(String.valueOf(productSearchResultDTO.getCount()));// 商品总数
				productSolrDTOList = productSearchResultDTO.getList();// 商品集合
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace(e.getMessage());
		}
		pageTotal = size % PAGE_SIZE == 0 ? size / PAGE_SIZE : size / PAGE_SIZE + 1;
		if (pageNum > pageTotal && pageTotal > 0) {
			pageNum = pageTotal;
		}
		mv.addObject("productSolrDTOList", productSolrDTOList);
		mv.addObject("totalCount", size);// 记录总数
		mv.addObject("productQueryBean", productQueryBean);
		mv.addObject("pageNow", pageNum);// 当前页
		mv.addObject("pageTotal", pageTotal);// 总页数
		mv.addObject("priceOrder", StringUtils.isEmpty(priceOrder) ? "" : priceOrder.split(",")[0]);// 排序方式
		mv.addObject("keyWord", keyWord);// 搜索关键字
		mv.addObject("searchModel", 2);// 搜索模式，偶数标识搜索产品
		mv.addObject("marketId", marketId);// 市场Id
		mv.addObject("topCate", topCate);// 一级菜单集合
		mv.addObject("secondCate", secondCate);// 二级菜单集合
		mv.addObject("thirdCate", thirdCate);// 三级菜单集合
		mv.addObject("cateId1", cateId1);// 一级菜单
		mv.addObject("cateId2", cateId2);// 二级菜单
		mv.addObject("cateId3", cateId3);// 三级菜单
		mv.addObject("isBig", isBig);// 三级菜单
		if (StringUtils.isNotEmpty(isBig)) {
			mv.setViewName("search/product/market");
		} else {
			mv.setViewName("search/product/market-big");
		}
		return mv;
	}

	/**
	 * @Description getProductPurchaseList 会员中心 我要进货，对发布的货物进行搜索
	 * @param productQueryBean
	 * @param mv
	 * @return
	 * @CreationDate 2015年10月28日 上午11:37:40
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping(value = { "userCenter/purchaseList", "userCenter/purchaseList/{marketId}" }, produces = "text/html;charset=UTF-8")
	public ModelAndView getProductPurchaseList(HttpServletRequest request, ModelAndView mv,ProductQueryBean productQueryBean) {
		try {
			if (!BusinessUtil.isUserHaveBusiness(getRequest(), businessBaseinfoToolService)) {
				// this.putModel("reason", "没有店铺，不能发布产品");
				// this.putModel("reason", "没有店铺，不能查看关注***");
				mv.setViewName("usercenter/userCenterNoBusiness");
				return mv;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String pageNow = getRequest().getParameter("pageNow");
		String cateId1 = getRequest().getParameter("cate-level-1");
		String cateId2 = getRequest().getParameter("cate-level-2");
		String keyWord = null;// 搜索关键词
		keyWord = getRequest().getParameter("keyWord");// 搜索关键词
		/*** 分页当前页 ***/
		int pageNum = 1;
		if (StringUtils.isNotEmpty(pageNow)) {
			pageNum = Integer.valueOf(pageNow);
		}
		/*** 产品类型 ***/
		if (StringUtils.isNumeric(cateId2)) {
			productQueryBean.setCateId(Long.valueOf(cateId2));// 产品类型
		} else {
			if (StringUtils.isNumeric(cateId1)) {
				productQueryBean.setCateId(Long.valueOf(cateId1));// 产品类型
			}
		}
		/** 产地供应商发布的产品 **/
		productQueryBean.setRoleType("4");
		/** 排序 **/
		Map<String, String> order = new HashMap<String, String>();
		/** 排序 **/
		order.put("createTime", "desc");
		productQueryBean.setOrder(order);// 排序规则
		/** 商品总数 **/
		Integer size = 0;
		ProductSearchResultDTO productSearchResultDTO = null;// 搜索结果集合,包含商品集合和商品总数
		/** 商品集合 ***/
		List<ProductSolrDTO> productSolrDTOList = null;
		/** 分页页面大小 **/
		productQueryBean.setPageSize(PAGE_SIZE);
		/*** 分页返回结果，起始行数数 **/
		productQueryBean.setStartRow((pageNum - 1) * PAGE_SIZE);
		/*** 分页总数 ***/
		int pageTotal = 1;
		// 搜索结果集合
		try {
			if (StringUtils.isNumeric(cateId1)) {
				String cate1Name = proCategoryToolService.getProductCategoryById(Long.valueOf(cateId1)).getCateName();
				mv.addObject("cate1Name", cate1Name);// 一级分类
			}
			if (StringUtils.isNumeric(cateId2)) {
				String cate2Name = proCategoryToolService.getProductCategoryById(Long.valueOf(cateId2)).getCateName();
				mv.addObject("cate2Name", cate2Name);// 二级分类
			}
			productQueryBean.setName(keyWord);// 搜索关键词
			productSearchResultDTO = productToolService.getByProductQueryBean(productQueryBean);// 根据查询条件得到结果集
			size = Integer.valueOf(String.valueOf(productSearchResultDTO.getCount()));// 商品总数
			productSolrDTOList = productSearchResultDTO.getList();// 商品集合
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		pageTotal = size % PAGE_SIZE == 0 ? size / PAGE_SIZE : size / PAGE_SIZE + 1;
		mv.addObject("productSolrDTOList", productSolrDTOList);
		mv.addObject("totalCount", size);// 记录总数
		mv.addObject("productQueryBean", productQueryBean);
		mv.addObject("pageNow", pageNum);// 当前页
		mv.addObject("pageTotal", pageTotal);// 总页数
		mv.addObject("keyWord2", keyWord);// 搜索关键字
		mv.addObject("cateId1", cateId1 == null ? "-1" : cateId1);// 一级分类
		mv.addObject("cateId2", cateId2);// 二级分类
		mv.addObject("searchModel", 0);// 搜索模式，0标识搜索产品
		mv.setViewName("usercenter/product/productPurchase");
		return mv;
	}

	/**
	 * @Description getChildProductCategory 根据商品分类ID获取下级分类
	 * @param categoryId
	 * @param map
	 * @return
	 * @CreationDate 2015年10月29日 下午3:14:13
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@ResponseBody
	@RequestMapping("product/getChildProductCategory/{categoryId}")
	public String getChildProductCategory(@PathVariable("categoryId") String categoryId) {
		List<ProductCategoryDTO> children = null;
		try {
			children = productToolService.getChildProductCategory(Long.valueOf(categoryId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(children);
	}

	/**
	 * @Description listTopProductCategoryByMarketId 根据市场ID获取商品顶级分类菜单
	 * @param marketId
	 * @param map
	 * @return
	 * @CreationDate 2015年10月29日 下午3:13:39
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@ResponseBody
	@RequestMapping("product/listProductCategoryByLvAndMarketId")
	public String listProductCategoryByLvAndMarketId() {
		String curLevel = getRequest().getParameter("curLevel");// 分类级别
		String marketId = getRequest().getParameter("marketId");// 市场ID
		List<ProductCategoryDTO> children = null;
		try {
			if (StringUtils.isEmpty(marketId)) {
				marketId = getCookieByMarketId(getRequest());// 若没有传入市场ID，则从cookies中获取
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", marketId);
			map.put("curLevel", curLevel);
			children = productToolService.listProductCategoryByLvAndMarketId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(children);
	}

	/**
	 * 右侧框基础数据加载
	 * 
	 * @param bid
	 * @throws Exception
	 */
	public void initStoreLeft(String bid) throws Exception {
		/*
		 * 右边数据需要 start
		 */

		StoreLeftDTO dto = new StoreLeftDTO();
		BusinessBaseinfoDTO bdto = businessBaseinfoToolService.getById(bid);
		// 获得对应商铺对象
		dto.setBusiInfo(bdto);
		// 获得对应商铺自定义类别
		dto.setBusiProducttypeList(businessProducttypeToolService.getByBusinessId(bid));

		dto.setIsLogin(checkLogin(getRequest()));

		// 增加浏览次数
		/*
		 * 判断是否第一次，第一次修改数据
		 * 
		 */
		if (bdto.getBrowseCount() == null) {
			BusinessBaseinfoDTO bdtoUpdate = new BusinessBaseinfoDTO();
			bdtoUpdate.setBusinessId(Long.parseLong(bid));
			bdtoUpdate.setBrowseCount(1L);
			businessBaseinfoToolService.updateBusinessBaseinfoDTO(bdtoUpdate);
			bdto.setBrowseCount(1L);
		} else {
			businessBaseinfoToolService.addBrowser(Long.parseLong(bid));
			bdto.setBrowseCount(bdto.getBrowseCount() + 1);
		}

		/*
		 * 获取供应产品数
		 */
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("businessId", bid);
		query.put("state", 3);

		// 产品总数
		dto.setBusiProductTotal(productToolService.getCount(query));
		/*
		 * 查询是否收藏此店铺
		 */
		MemberBaseinfoEntity user = getUser(getRequest());

		/*
		 * 判断是否收藏
		 */
		// 判断是否登录
		if (user == null) {
			dto.setIsFocusStore(false);
		} else {
			UsercollectShopDTO usDTO = usercollectShopToolService.getCollectShop(user.getMemberId(), Long.valueOf(bid));
			if (usDTO == null) {
				dto.setIsFocusStore(false);

				// usercollectShopToolService.focusShop(1L, Long.valueOf(bid));
			} else {
				dto.setIsFocusStore(true);
			}
		}

		putModel("dto", dto);
		/*
		 * 右边数据需要 end
		 */
	}

	/**
	 * 获得对应商铺的商铺图片
	 * 
	 * @param bid
	 * @return
	 */
	private String getStoreImgUrl(String bid) throws Exception {
		String url = "";
		BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getById(bid);
		Map<String, Object> mapb = new HashMap<>();
		mapb.put("businessId", businessBaseinfoDTO.getBusinessId());
		mapb.put("startRow", 0);
		mapb.put("endRow", 1);
		List<ReBusinessCategoryDTO> list = reBusinessCategoryToolService.getBySearch(mapb);
		if (null != businessBaseinfoDTO.getBanelImgUrl() && !businessBaseinfoDTO.getBanelImgUrl().equals("")) {
			url = businessBaseinfoDTO.getBanelImgUrl();
		} else if (null != list && list.size() > 0) {
			List<ReCategoryBanelImgDTO> reList = reCategoryBanelImgToolService.getByCategoryId(list.get(0).getCategoryId());
			if (null != reList && reList.size() > 0) {
				url = reList.get(0).getBanelImgUrl();
			}
		}

		return url;
	}

}
