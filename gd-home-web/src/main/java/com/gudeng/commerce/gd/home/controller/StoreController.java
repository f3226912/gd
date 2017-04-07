package com.gudeng.commerce.gd.home.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReCategoryBanelImgDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.dto.StoreLeftDTO;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.BusinessProducttypeToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.home.service.ReCategoryBanelImgToolService;
import com.gudeng.commerce.gd.home.service.UsercollectShopToolService;
import com.gudeng.commerce.gd.home.util.BaseJsonResult;
import com.gudeng.commerce.gd.home.util.BusinessUtil;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("business/shop")
public class StoreController extends HomeBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(StoreController.class);

	@Autowired
	public BusinessBaseinfoToolService baseinfoService;

	@Autowired
	public BusinessProducttypeToolService businessProducttypeToolService;

	@Autowired
	public ProductToolService productToolService;

	@Autowired
	public UsercollectShopToolService usercollectShopToolService;

	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;

	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;

	@Autowired
	public ReCategoryBanelImgToolService reCategoryBanelImgToolService;

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

	/**
	 * 自己商铺首页
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String storeMeIndex() {
		try {
			HttpServletRequest request = getRequest();

			/*
			 * 判断是否登录
			 */
			MemberBaseinfoEntity user = getUser(request);

			if (user == null) {
				this.putModel("reason", "没有店铺，请先添加商铺");
				return "usercenter/userCenterNoBusiness";
			}

			/*
			 * 判断是否包含店铺
			 */
			BusinessBaseinfoDTO baseinfoDTO = BusinessUtil.getBusinessBaseinfo(request, businessBaseinfoToolService);

			// BusinessBaseinfoDTO baseinfoDTO = baseinfoService.getByUserId(user.getMemberId().toString());

			if (baseinfoDTO == null) {
				this.putModel("reason", "没有店铺，请先添加商铺");
				return "usercenter/userCenterNoBusiness";
			}

			return "redirect:/business/shop/" + baseinfoDTO.getBusinessId();
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "redirect:/userCenter";
	}

	/**
	 * 商铺首页
	 * 
	 * @return
	 */
	/**
	 * @Description storeIndex 加入HttpServletRequest request参数，用于aop获得request，从而获取用户信息
	 * @param request 该参数类型及在参数列表中位置不要变动
	 * @param bid 该参数类型及在参数列表中位置不要变动
	 * @param response
	 * @return
	 * @CreationDate 2015年12月21日 下午4:35:22
	 * @Author lidong(dli@gdeng.cn)
	*/
	@RequestMapping("{bid}")
	public String storeIndex(HttpServletRequest request, @PathVariable String bid, HttpServletResponse response) {
		try {

			/*
			 * 联系我们
			 */
			baseDataGeneral(response, bid);

			/*
			 * left data
			 */
			initStoreLeft(bid);
			
			/*
			 * 类目加载
			 */
			//putModel("categorys", collToStr(baseinfoService.getCategoryList(Long.parseLong(bid), 0, 3), ","));

			/*
			 * 获取对应商铺的产品
			 */
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("businessId", bid);
			query.put("state", 3);

			/*
			 * 限制显示产品数量
			 */
			query.put(com.gudeng.commerce.gd.home.Constant.START_ROW, 0);
			query.put(com.gudeng.commerce.gd.home.Constant.ROW_SIZE, 12);
			List<ProductDto> productList = productToolService.getProductList(query);
			StringBuilder productLine = new StringBuilder();
			int i = 0;
			for (ProductDto productDto : productList) {
				i++;
				productLine.append(productDto.getProductName());
				if (i < 3) {
					productLine.append(",");
				} else {
					break;
				}
			}
			putModel("productList", productList);
			putModel("productLine", productLine.toString());
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "store/storeIndex";
	}

	/**
	 * 用于关注商铺 ajax
	 * 
	 * @param bid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("focus/{bid}")
	public String focusStore(@PathVariable String bid) throws Exception {
		ModelMap map = new ModelMap();
		MemberBaseinfoEntity user = getUser(getRequest());
		if (user != null) {
			Long userId = user.getMemberId();

			/*
			 * 判断您是否已经收藏
			 */
			UsercollectShopDTO usDTO = usercollectShopToolService.getCollectShop(userId, Long.valueOf(bid));

			/*
			 * 未收藏添加
			 */
			if (usDTO == null) {
				try {
					usercollectShopToolService.focusShop(userId, Long.valueOf(bid));
				} catch (NumberFormatException e) {
					map.put("statusCode", -1);
					map.put("msg", "商铺标识不正确");
					return JSONArray.toJSONString(map);
				} catch (Exception e) {
					map.put("statusCode", -1);
					map.put("msg", "关联失败");
					return JSONArray.toJSONString(map);
				}
			}

			Map<String, Object> params = new HashMap<>();
			params.put("businessId", bid);

			Integer total = usercollectShopToolService.getCount(params);

			map.put("statusCode", 0);
			map.put("msg", "关注成功");
			map.put("number", total);
			return JSONArray.toJSONString(map);
		}

		map.put("statusCode", -1);
		map.put("msg", "登录超时，请重新登录。");
		return JSONArray.toJSONString(map);
	}

	/**
	 * 检查是否登录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkLogon")
	public BaseJsonResult checkLogon() {
		BaseJsonResult result = new BaseJsonResult();

		if (checkLogin(getRequest())) {
			result.setMsg("已登录");
			result.setStatusCode(0);
		} else {
			result.setMsg("未登录");
			result.setStatusCode(-1);
		}

		return result;
	}

	private void baseDataGeneral(HttpServletResponse response, String bid) throws Exception {
		BusinessBaseinfoDTO bdto = baseinfoService.getById(bid);
		putModel("busiInfo", bdto);

		putModel("busiInfoUrl", getStoreImgUrl(bdto.getBusinessId().toString()));
		/**
		 * 批发商才能绑定市场
		 */
		if (bdto.getLevel() == 1) {
			addCookie(response, "marketId", bdto.getMarketId(), 60 * 60 * 24 * 365);
		} else {
			addCookie(response, "marketId", "", 60 * 60 * 24 * 365);
		}

		putModel("isLogin", checkLogin(getRequest()));

	}

	/**
	 * 用于取消关注商铺 ajax
	 * 
	 * @param bid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("blur/{bid}")
	public BaseJsonResult blurStore(@PathVariable String bid) {
		MemberBaseinfoEntity user = getUser(getRequest());
		BaseJsonResult result = new BaseJsonResult();

		/*
		 * 判断是否登录或者登录超时
		 */
		if (user != null) {
			Long userId = user.getMemberId();

			try {
				usercollectShopToolService.blurShop(userId, Long.valueOf(bid));
			} catch (NumberFormatException e) {
				result.setStatusCode(-1);
				result.setMsg("商铺标识不正确");
				return result;
			} catch (Exception e) {
				result.setStatusCode(-1);
				result.setMsg("关联失败");
				return result;
			}

			result.setStatusCode(0);
			result.setMsg("关注成功");
			return result;
		}
		result.setStatusCode(-1);
		result.setMsg("登录超时，请重新登录。");
		return result;
	}

	/**
	 * 商铺介绍
	 * 
	 * @param bid
	 * @return
	 */
	@RequestMapping("{bid}/intro")
	public String storeInfo(HttpServletResponse response, @PathVariable String bid) {
		try {

			/*
			 * 联系我们
			 */
			baseDataGeneral(response, bid);
			/*
			 * left data
			 */
			initStoreLeft(bid);
			/*
			 * 获取对应商铺的产品
			 */
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("businessId", bid);
			query.put("state", 3);

			query.put(com.gudeng.commerce.gd.home.Constant.START_ROW, 0);
			query.put(com.gudeng.commerce.gd.home.Constant.ROW_SIZE, 4);

			// 按照时间最新排序
			query.put("sortName", "createTime");
			query.put("sortOrder", "DESC");

			putModel("productList", productToolService.getProductList(query));

		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "store/storeInfo";
	}

	/**
	 * 联系我们
	 * 
	 * @param bid
	 * @return
	 */
	@RequestMapping("{bid}/contact")
	public String storeContact(HttpServletResponse response, @PathVariable String bid) {
		try {
			/*
			 * 联系我们
			 */
			baseDataGeneral(response, bid);
			/*
			 * left data
			 */
			initStoreLeft(bid);
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "store/storeContact";
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
		BusinessBaseinfoDTO bdto = baseinfoService.getById(bid);
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
			baseinfoService.updateBusinessBaseinfoDTO(bdtoUpdate);
			bdto.setBrowseCount(1L);
		} else {
			baseinfoService.addBrowser(Long.parseLong(bid));
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
	 * 
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkCollect/{bid}")
	public String checkCollectStore(String bid) throws Exception {

		/*
		 * 查询是否收藏此店铺
		 */
		MemberBaseinfoEntity user = getUser(getRequest());

		// 判断是否登录
		if (user == null) {
			return "0";
		} else {
			UsercollectShopDTO usDTO = usercollectShopToolService.getCollectShop(user.getMemberId(), Long.valueOf(bid));
			if (usDTO == null) {
				return "-1";

				// usercollectShopToolService.focusShop(1L, Long.valueOf(bid));
			} else {
				return "0";
			}
		}
	}

	/**
	 * 集合转字符串
	 * 
	 * @param c
	 * @param delimiter
	 * @return
	 */
	private String collToStr(List<ReBusinessCategoryDTO> c, String delimiter) throws Exception {
		if (c == null || c.size() == 0) {
			return "";
		}

		Iterator<ReBusinessCategoryDTO> it = c.iterator();

		StringBuilder sb = new StringBuilder(it.next().getCateName());

		while (it.hasNext()) {
			sb.append(delimiter);
			sb.append(it.next().getCateName());
		}

		return sb.toString();
	}

	/**
	 * 供应产品
	 * 
	 * @param bid
	 * @return
	 */
	@RequestMapping(value={"{bid}/supply","{bid}/supply/page/{page}"})
	public String storeProduct(HttpServletResponse response, @PathVariable String bid,PageDTO<String> pageDTO) {
		try {
			/*
			 * 头部判断需要
			 */
			baseDataGeneral(response, bid);
			/*
			 * left data
			 */
			initStoreLeft(bid);
			/*
			 * 类目加载
			 */
			//putModel("categorys", collToStr(baseinfoService.getCategoryList(Long.parseLong(bid), 0, 3), ","));

			/*
			 * 获取对应商铺的产品
			 */
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("businessId", bid);
			query.put("state", 3);
			/*
			 * 测试分页 query 查询条件 需要的页面参数 page 页码 size 页大小
			 */

			// 获取总数 通过query提供的参数 此处有service层提供方法
			int size = productToolService.getCount(query);

			/*
			 * 默认使用创建pageDTO对象 页大小=5
			 */
			PageDTO<ProductDto> pageDto = PageUtil.create(ProductDto.class, 8);

			// 设置总数据
			pageDto.setTotalSize(size);
			if (pageDTO != null && pageDTO.getCurrentPage() != null && pageDTO.getCurrentPage() > 0) {
                pageDto.setCurrentPage(pageDTO.getPage());
            }
			// 设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, query);

			// 获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(productToolService.getProductList(query));

			// 数据添加到model 前台准备显示
			putPagedata(pageDto);
			/*
			 * pageDTO中数据 getPageTotal 总页数 pageData 显示数据
			 */

		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "store/storeProduct";
	}

}
