package com.gudeng.commerce.gd.home.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.dto.ProductQueryParams;
import com.gudeng.commerce.gd.home.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.FileUploadToolService;
import com.gudeng.commerce.gd.home.service.MarketToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.home.service.SystemCodeService;
import com.gudeng.commerce.gd.home.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.home.util.BaseJsonResult;
import com.gudeng.commerce.gd.home.util.BusinessUtil;
import com.gudeng.commerce.gd.home.util.CommonUtil;
import com.gudeng.commerce.gd.home.util.DateUtil;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("userCenter")
public class ProductController  extends HomeBaseController{
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(ProductController.class);
	
	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public UsercollectProductToolService usercollectProductToolService;
	
	@Autowired
	public FileUploadToolService fileUploadToolService;
	
	@Autowired
	public MarketToolService marketToolService ;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public SystemCodeService systemCodeService;
	
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;
	
	@Autowired
	public QueryAreaToolService queryAreaService;
	
	@RequestMapping("product/toEditProductCategory/{productId}")
	public String toEditProductCategory(@PathVariable("productId")String productId, ModelMap map, HttpServletRequest request){
//		String marketId = getCookieByMarketId(request);
//		MemberBaseinfoEntity user = getUser(getRequest());
//		//用户级别为4表示产地供应商
//		if (4 == user.getLevel().intValue()){
//			marketId = "3";
//		}
		try {
			Long marketId = null ;
			MemberBaseinfoEntity user = getUser(getRequest());
			//用户级别为4表示产地供应商
			if (4 == user.getLevel().intValue()){
				marketId = new Long(3);
			}else {
				Long businessId = BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
				Map<String, Object> paramsMap = new HashMap<>();
				paramsMap.put("businessId", businessId);
				List<ReBusinessMarketDTO> list = reBusinessMarketToolService.getAllBySearch(paramsMap);
				if (list == null || list.isEmpty()){
					throw new Exception("未维护市场与店铺的关系");
				}else if (list.size() > 1){
					throw new Exception("市场与店铺的关系有误, 存在多个关系");
				}
				marketId = list.get(0).getMarketId();
				
			}
			//	包含区间价格查询
			ProductDto product = productToolService.getByProductId(productId);
			List<ProductCategoryDTO> categoryList = productToolService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
			map.put("firstList", categoryList);
			map.put("categoryId", product.getCateId());
			//根据产品分类id 查询  产品分类层级列表
			List<ProductCategoryDTO> categoryLevels = productToolService.getCategoryAncestors(product.getCateId());
			int len = categoryLevels.size();
			if (len <= 2){
				map.put("secondList", productToolService.getChildProductCategory(categoryLevels.get(0).getCategoryId()));
			}else if (len <= 3){
				map.put("secondList", productToolService.getChildProductCategory(categoryLevels.get(0).getCategoryId()));
				map.put("thirdList", productToolService.getChildProductCategory(categoryLevels.get(1).getCategoryId()));
			}else if (len <= 4){
				map.put("secondList", productToolService.getChildProductCategory(categoryLevels.get(0).getCategoryId()));
				map.put("thirdList", productToolService.getChildProductCategory(categoryLevels.get(1).getCategoryId()));
				map.put("fourthList", productToolService.getChildProductCategory(categoryLevels.get(2).getCategoryId()));
			}
			map.put("productId", productId);
			map.put("categoryLevels", categoryLevels);
			map.put("roleType", user.getLevel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usercenter/product/reChooseProductCategory";
	}
	
	@ResponseBody
	@RequestMapping("product/isProductDelete")
	public String isProductDelete(String productId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//	包含区间价格查询
			ProductDto product = productToolService.getByProductId(productId);
			if ("5".equals(product.getState())){
				map.put("status", 0);
				map.put("message", "该产品已被删除, 无法修改");
			}else{
				map.put("status", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}
	
	@RequestMapping("product/toEdit/{productId}-{categoryId}")
	public String toEdit(@PathVariable("productId")String productId, @PathVariable("categoryId")String categoryId,
			ModelMap map, HttpServletRequest request){
		
		try {
			//	包含区间价格查询
			ProductDto product = productToolService.getByProductId(productId);
			
			Long businessId = BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("businessId", businessId);
			List<ReBusinessMarketDTO> bmlist = reBusinessMarketToolService.getAllBySearch(paramsMap);
			if (bmlist == null || bmlist.isEmpty()){
				throw new Exception("未维护市场与店铺的关系");
			}else if (bmlist.size() > 1){
				throw new Exception("市场与店铺的关系有误, 存在多个关系");
			}
			String marketId = bmlist.get(0).getMarketId().toString();
//			String marketId = getCookieByMarketId(request);
			
			//农批中心
			MarketDTO market = marketToolService.getById(marketId);
			map.put("marketName", market.getMarketName());
			map.put("product", product);
			//查询图片
			List<ProductPictureDto> list = productToolService.getPictureByProductId(productId);
			//存储多角度url
			List<Map<String, String>> multiplePictureList = new ArrayList<Map<String, String>>();
			for (ProductPictureDto dto : list) {
				if (1 == dto.getPictureType().intValue()){
					
				}else if (4 == dto.getPictureType().intValue()){
					List<Map<String, String>> appPicture = new ArrayList<Map<String, String>>();
					Map<String, String> pair = new HashMap<String, String>();
//					pair.put("dataOriginalUrl", dto.getUrlOrg());
					pair.put("url", dto.getUrlOrg());
					appPicture.add(pair);
					map.put("appPicture", JSONObject.toJSONString(appPicture));
				}else if (3 == dto.getPictureType().intValue()){
					
				}else if (2 == dto.getPictureType().intValue()){
					//多角度
					Map<String, String> pair = new HashMap<String, String>();
					pair.put("dataOriginalUrl", dto.getUrlOrg());
					pair.put("url", dto.getUrlOrg());
					multiplePictureList.add(pair);
				}
			}
			
			if (multiplePictureList != null && !multiplePictureList.isEmpty()){
				//多角度
				map.put("multiplePicture", JSONObject.toJSONString(multiplePictureList));
				map.put("multiplePictureList", multiplePictureList);
			}
			
			//单位
			List<SystemCode> productUnit = systemCodeService.getListViaType("ProductUnit");
			map.put("productUnit", productUnit);
			//产地
			List <AreaDTO> initOriginProvinceList = queryAreaService.findProvince();
			List <AreaDTO> initOriginCityList = queryAreaService.findCity(product.getOriginProvinceId());
			List <AreaDTO> initOriginAreaList = queryAreaService.findCity(product.getOriginCityId());
			map.put("initOriginProvinceList", initOriginProvinceList);
			map.put("initOriginCityList", initOriginCityList);
			map.put("initOriginAreaList", initOriginAreaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usercenter/product/editProductInfo";
	}
	
	@ResponseBody
	@RequestMapping("product/edit")
	public String edit(ProductQueryParams params, HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<String, Object>();
		ProductDto product = new ProductDto();
		String productId = params.getProductId();
		try {
//			ProductDto product_old = productToolService.getByProductId(productId);

			Long businessId = BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
			MemberBaseinfoEntity user = getUser(request);
			
			product.setProductId(Long.valueOf(productId));
			
			product.setProductName(params.getProductName());
			product.setBusinessId(businessId);
			product.setCateId(Long.valueOf(params.getCategoryId()));
//			//待审核
//			product.setState(ProductStatusEnum.NEEDAUDIT.getKey());
			
			product.setUnit(params.getUnit());
			product.setStockCount(Double.valueOf(params.getStockCount()));
			product.setInfoLifeDay(params.getInfoLifeDay());
			product.setDescription(params.getDescription());

//			product.setUpdateUserId(user.getAccount());
			product.setUpdateUserId(user.getMemberId().toString());
			String updateTime = DateUtil.getSysDateTimeString();
			product.setUpdateTimeString(updateTime);
			
/*			//有效期变更则刷新过期时间
			if (!product_old.getInfoLifeDay().equalsIgnoreCase(params.getInfoLifeDay()) ){
				Date updateDate = DateUtil.parseDate(updateTime, DateUtil.DATE_FORMAT_DATETIME);
				String expirationDateString = DateUtil.toString(DateUtil.addDays(updateDate, Integer.valueOf(params.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
				product.setExpirationDateString(expirationDateString);
			}*/
			
			//只要点击的编辑页面的确定按钮就更新过期时间(不管产品信息是否有任何变更)
			Date updateDate = DateUtil.parseDate(updateTime, DateUtil.DATE_FORMAT_DATETIME);
			String expirationDateString = DateUtil.toString(DateUtil.addDays(updateDate, 
					Integer.valueOf(params.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
			product.setExpirationDateString(expirationDateString);
			
//			Date updateDate = DateUtil.parseDate(updateTime, DateUtil.DATE_FORMAT_DATETIME);
//			product.setExpirationDate(DateUtil.addDays(updateDate, Integer.valueOf(params.getInfoLifeDay())));
			
//			product.setRoleType(String.valueOf(user.getLevel()));
//			product.setMarketId(Long.valueOf(params.getMarketId()));
//			product.setChannel(params.getChannel());
			
			product.setOriginProvinceId(params.getOriginProvinceId());
			product.setOriginCityId(params.getOriginCityId());
			product.setOriginAreaId(params.getOriginAreaId());
			
			product.setPriceType(params.getPriceType());
			//区间价
			List<ProductPriceDto> prices = params.getPriceDtoList();
			if ("1".equals(params.getPriceType()) && prices != null 
					&& !prices.isEmpty()){
				
				//排序取得最小价格, 不改变prices, 即不对prices排序
				ProductPriceDto[] priceArray = new ProductPriceDto[prices.size()]; 
				prices.toArray(priceArray);
				checkAndSort(priceArray);
				product.setPrice(priceArray[0].getPrice());
				
				//删除原来的价格区间
				productToolService.batchDeletePrices(productId);
				//添加新的价格区间
				for (ProductPriceDto productPriceDto : prices) {
					if (productPriceDto != null ) {
						if (productPriceDto.getPrice() == null || productPriceDto.getPrice().doubleValue() < 0){
							productPriceDto.setPrice(Double.valueOf(0));
						}
						productPriceDto.setCreateUserId(product.getUpdateUserId());
						productPriceDto.setProductId(product.getProductId());
					}
				}
				productToolService.batchUpdatePrice(prices);
				
			}else {
				product.setPrice(params.getPrice()== null ? Double.valueOf(0): params.getPrice());
			}
			
			productToolService.updateProduct(product);
			//更新产品状态为待审核
			Map<String, Object> smap = new HashMap<>();
			smap.put("productId", productId);
			smap.put("state", ProductStatusEnum.NEEDAUDIT.getKey());
			productToolService.updateProductStatus(smap);
			
			//多角度图, 主图
			String mutiple = params.getMultiplePicture();
			if ( mutiple != null && !"".equals(mutiple)){
				logger.info("edit web mutiple : {}, productId : {}", new Object[]{mutiple, productId});
				String[] multiplePictures = mutiple.split(",");
				//删除主图
				productToolService.deletePicByProductIdAndType(productId, "1");
				//删除多角度图
				productToolService.deletePicByProductIdAndType(productId, "2");
				boolean first = true;
				for(String item : multiplePictures){
					if (first){
						first = false;
						//保存第一张多角度图为主图
						savePicture(user.getMemberId().toString(), item, 1, productId);
					}
					savePicture(user.getMemberId().toString(),  item, 2, productId);
				}
			}
			//App图
			String app = params.getAppPicture();
			if ( app != null && !"".equals(app)){
				logger.info("edit web app : {}, productId : {}", new Object[]{app, productId});
				productToolService.deletePicByProductIdAndType(productId, "4");
				savePicture(user.getMemberId().toString(), params.getAppPicture(), 4, productId);
			}
			
			map.put("status", 1);
			map.put("productId", productId);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "add product failed!!");
		}
		return JSONObject.toJSONString(map);
	}
	@ResponseBody
	@RequestMapping("product/publish")
	public String publishProduct(ProductQueryParams params, HttpServletRequest request){

		Map<String, Object> map = new HashMap<String, Object>();
		ProductEntity product = new ProductEntity();
		String productName = params.getProductName();
		try {

			Long businessId = BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
			MemberBaseinfoEntity user = getUser(request);
			
			product.setProductName(productName);
			product.setBusinessId(businessId);
			product.setCateId(Long.valueOf(params.getCategoryId()));
			//待审核
			product.setState(ProductStatusEnum.NEEDAUDIT.getKey());
			
			product.setPriceType(params.getPriceType());
			//区间价
			List<ProductPriceDto> prices = params.getPriceDtoList();
			if ("1".equals(params.getPriceType()) && prices != null 
					&& !prices.isEmpty()){
//				checkAndSort(prices);
//				product.setPrice(prices.get(0).getPrice());
				
				//排序取得最小价格, 不改变prices, 即不对prices排序
				ProductPriceDto[] priceArray = new ProductPriceDto[prices.size()]; 
				prices.toArray(priceArray);
				checkAndSort(priceArray);
				product.setPrice(priceArray[0].getPrice());
				
			}else {
				product.setPrice(params.getPrice()== null ? Double.valueOf(0): params.getPrice());
			}
			
			product.setUnit(params.getUnit());
			product.setStockCount(Double.valueOf(params.getStockCount()));
			
			product.setRoleType(String.valueOf(user.getLevel()));

/*			String marketId = getCookieByMarketId(request);
			MarketDTO market = marketToolService.getById(marketId);
			product.setProvinceId(market.getProvinceId());
			product.setCityId(market.getCityId());
			product.setAreaId(market.getAreaId());
			product.setAddress(market.getAddress());*/

			product.setInfoLifeDay(params.getInfoLifeDay());
			product.setDescription(params.getDescription());

//			product.setCreateUserId(user.getAccount());
			product.setCreateUserId(String.valueOf(user.getMemberId()));
			Date now = new Date();
			product.setCreateTime(now);
			product.setExpirationDate(DateUtil.addDays(now, Integer.valueOf(params.getInfoLifeDay())));
			product.setUpdateTime(now);
			product.setUpdateUserId(user.getMemberId().toString());
			
			product.setOriginProvinceId(params.getOriginProvinceId());
			product.setOriginCityId(params.getOriginCityId());
			product.setOriginAreaId(params.getOriginAreaId());
			
//			product.setMarketId(Long.valueOf(params.getMarketId()));
//			product.setChannel(params.getChannel());
			
			// 插入多价格区间列表
			product.setPriceDtoList(params.getPriceDtoList());
			long productId = productToolService.persistProductViaEntity(product);
			
			//插入图片
			String[] multiplePictures = params.getMultiplePicture().split(",");
			logger.info("add web multiplePictures : {}, productId : {}", new Object[]{multiplePictures, productId});
			boolean first = true;
			for(String item : multiplePictures){
				if (first){
					first = false;
					//保存第一张多角度图为主图
					savePicture(user.getMemberId().toString(), item, 1, String.valueOf(productId));
				}
				savePicture(user.getMemberId().toString(),  item, 2, String.valueOf(productId));
			}
			logger.info("add web app : {}, productId : {}", new Object[]{params.getAppPicture(), productId});
			savePicture(user.getMemberId().toString(), params.getAppPicture(), 4, String.valueOf(productId));
			
			map.put("status", 1);
			map.put("productId", productId);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "add product failed!!");
		}
		return JSONObject.toJSONString(map);
	}
	
/*	private List<ProductPriceDto> checkAndSort(List<ProductPriceDto> list){
		for(ProductPriceDto item : list){
			if (item.getPrice() == null){
				item.setPrice(Double.valueOf(0));
			}
		}
		Collections.sort(list);
		return list;
	}*/
	private ProductPriceDto[] checkAndSort(ProductPriceDto[] priceArray){
		for(ProductPriceDto item : priceArray){
			if (item.getPrice() == null){
				item.setPrice(Double.valueOf(0));
			}
		}
		Arrays.sort(priceArray);
		return priceArray;
	}
	private void savePicture(String userId, String picturePath, int pictureType, String productId) throws Exception{
		
		logger.info("web savePicture urlOrg : {}, productId : {}, userId : {}, pictureType : {}",
				new Object[]{picturePath, productId, userId, pictureType});
		 ProductPictureEntity pictureDto = new ProductPictureEntity();
		 pictureDto.setProductId(Long.valueOf(productId));
		 pictureDto.setPictureType(pictureType);
		 pictureDto.setUrlOrg(picturePath);
		 pictureDto.setUrl650(CommonUtil.generatePictureName(picturePath, 650));//650
		 pictureDto.setUrl400(CommonUtil.generatePictureName(picturePath, 370));//400
		 pictureDto.setUrl170(CommonUtil.generatePictureName(picturePath, 200));//170
		 pictureDto.setUrl120(CommonUtil.generatePictureName(picturePath, 160));//120
		 pictureDto.setUrl60(CommonUtil.generatePictureName(picturePath, 150));//60
		 pictureDto.setCreateTime(new Date());
		 pictureDto.setCreateUserId(userId);
		 productToolService.addProductPicViaEntity(pictureDto);
	}
	
	@RequestMapping("product/toPublish/{categoryId}")
	public String toPublish(@PathVariable("categoryId")String categoryId, ModelMap map, HttpServletRequest request){
//		String marketId = getCookieByMarketId(request);
		try {
			Long marketId = null ;
			MemberBaseinfoEntity user = getUser(getRequest());
			//用户级别为4表示产地供应商
			if (4 == user.getLevel().intValue()){
				marketId = new Long(3);
			}else {
				Long businessId = BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
				Map<String, Object> paramsMap = new HashMap<>();
				paramsMap.put("businessId", businessId);
				List<ReBusinessMarketDTO> list = reBusinessMarketToolService.getAllBySearch(paramsMap);
				if (list == null || list.isEmpty()){
					throw new Exception("未维护市场与店铺的关系");
				}else if (list.size() > 1){
					throw new Exception("市场与店铺的关系有误, 存在多个关系");
				}
				marketId = list.get(0).getMarketId();
				
			}
			//农批中心
			MarketDTO market = marketToolService.getById(marketId.toString());
			
			map.put("marketName", market.getMarketName());
			putModel("categoryId", categoryId);
			//单位
			List<SystemCode> productUnit = systemCodeService.getListViaType("ProductUnit");
			map.put("productUnit", productUnit);
			
			List <AreaDTO> arealist = queryAreaService.findProvince();
			map.put("arealist", arealist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usercenter/product/productPublish";
	}
	
	@ResponseBody
	@RequestMapping("product/queryProvince")
	public String queryProvince() {
		List<AreaDTO> list = null;
		try {
			list = queryAreaService.findProvince();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}
	
	@ResponseBody
	@RequestMapping("product/queryCity/{provinceId}")
	public String queryCity(@PathVariable("provinceId") String provinceId) {
		List<AreaDTO> list = null;
		try {
			list = queryAreaService.findCity(provinceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("product/queryArea/{cityId}")
	public String queryArea(@PathVariable("cityId") String cityId) {
		List<AreaDTO> list = null;
		try {
			list = queryAreaService.findCounty(cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}
	
	/**
	 *  获取商铺的产品
	 */
	@RequestMapping("product/saleList/{state}")
	public String saleList(@PathVariable("state")String state, String productName) {
		String jspName = "usercenter/product/offSaleList"; 
		try {
			if(!BusinessUtil.isUserHaveBusiness(getRequest(), businessBaseinfoToolService)){
				putModel("reason", "亲, 你还没有商铺, 请先创建商铺...");
				return "usercenter/userCenterNoBusiness";
			}
			
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			
			if(StringUtils.isBlank(state)){
				state = "4";
			}
			
			if(StringUtils.isNotBlank(productName)){
//				productName =  new String(productName.getBytes("ISO-8859-1"),"utf-8");
				paramsMap.put("productName", productName);
				putModel("productName", productName);
			}
			
			//已上架的产品
			if("3".equals(state)){
				jspName = "usercenter/product/onSaleList"; 
			}
			MemberBaseinfoEntity user = getUser(getRequest());
			Long memberId = user.getMemberId();
			
			BusinessBaseinfoDTO businessDto = businessBaseinfoToolService.getByUserId(memberId + "");
			//获取商铺id
			Long businessId = businessDto.getBusinessId() ; 
//			Long businessId = new Long(1); 
			
			paramsMap.put("businessId", businessId);
			paramsMap.put("state", state);
			// 获取产品总数 
			int size = productToolService.countByStateAndBusinessId(paramsMap);
			PageDTO<ProductDto> pageDto = PageUtil.create(ProductDto.class, 8);
			// 设置总数据
			pageDto.setTotalSize(size);
			// 设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, paramsMap);
			
			// 获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(productToolService.getByStateAndBusinessId(paramsMap));
			// 数据添加到model 前台准备显示
			putPagedata(pageDto);
			putModel("state", state);
		} catch (Exception e) {
			logger.trace("[ERROR]获取商铺产品异常:" + e.getMessage());
		}
		
		return jspName;
	}
	
	/**
	 * 更新产品状态
	 * 
	 * @param request
	 * @param productId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="product/updateState",produces = "text/html;charset=UTF-8")
	public String updateProductStatus(Long productId, Integer state) {
		Map<String, Object> map = new HashMap<String, Object>();
		//执行状态， 0失败， 1成功
		map.put("status", 0);   
		try {
			ProductDto productDto = productToolService.getByProductId(String.valueOf(productId));
			MemberBaseinfoEntity user = getUser(getRequest());
			Map<String, Object> params = new HashMap<String, Object>();
			switch (state) {
			// 上架
			case 3:
				if (!"4".equals(productDto.getState())) {
					map.put("status", 0);
					map.put("message", "只能对状态为已下架的产品执行上架操作");
					break;
				}
				// 有效期
				String expirationDateString = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME)
						.format(DateUtil.addDays(new Date(), Integer.valueOf(productDto.getInfoLifeDay())));
				params.put("expirationDateString", expirationDateString);
				params.put("state", state);
				params.put("productId", productId);
				params.put("updateUserId", user.getMemberId());
				params.put("updateTimeString", DateUtil.getSysDateTimeString());
				productToolService.updateProductStatus(params);
				map.put("status", 1);
				break;
			// 下架
			case 4: 
				if (!"3".equals(productDto.getState())) {
					map.put("status", 0);
					map.put("message", "只能对销售中的产品执行下架操作");
					break;
				}
				params.put("state", state);
				params.put("productId", productId);
				params.put("updateUserId", user.getMemberId());
				params.put("updateTimeString", DateUtil.getSysDateTimeString());
				productToolService.updateProductStatus(params);
				map.put("status", 1);
				break;
			// 删除
			case 5: 
//				productToolService.deleteProduct(productId + "");
				params.put("state", state);
				params.put("productId", productId);
				params.put("updateUserId", user.getMemberId());
				params.put("updateTimeString", DateUtil.getSysDateTimeString());
				productToolService.updateProductStatus(params);
				map.put("status", 1);
				break;
			}
			
		} catch (Exception e) {
			logger.warn("[ERROR]更新产品状态异常", e.getStackTrace());
			map.put("message", "更新产品状态异常");
		}
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 批量更新产品状态
	 * 
	 * @param request
	 * @param productId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="product/batchUpdate",produces = "text/html;charset=UTF-8")
	public String batchUpdateProductStatus(String productIds, String state) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		MemberBaseinfoEntity user = getUser(getRequest());
		Long memberId = user.getMemberId();
		String[] ids = productIds.split(",");
		try {
			List<ProductDto> products = productToolService.getProductsByIds(ids);
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			if ("3".equals(state)){
				//上架
				List<String> list = new ArrayList<String>();
				for(ProductDto current : products){
					//判断前置状态是否为下架状态
					if (!"4".equals(current.getState())){
						list.add(String.valueOf(current.getProductId()));
					}else {
						//有效期
						String expirationDateString = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME)
								.format(DateUtil.addDays(new Date(), Integer.valueOf(current.getInfoLifeDay())));
						
						Map<String, Object> paramsMap = new HashMap<String, Object>();
						paramsMap.put("productId", current.getProductId());
						paramsMap.put("updateUserId", memberId);
						paramsMap.put("updateTimeString", DateUtil.getSysDateTimeString());
						paramsMap.put("state", state);
						paramsMap.put("expirationDateString", expirationDateString);
						paramList.add(paramsMap);
						
/*						current.setState(state);
						current.setUpdateUserId(memberId +"");
						current.setUpdateTimeString(DateUtil.getSysDateString());
						current.setExpirationDateString(expirationDateString);*/
					}
				}
				if (!list.isEmpty()){
					map.put("status", 0);
					map.put("message", "存在非下架状态的产品, 不能上架");
					return JSONObject.toJSONString(map);
				}
				productToolService.batchUpdateProductStatus(paramList);
				map.put("status", 1);
			}else if ("4".equals(state)){
				//下架
				List<String> list = new ArrayList<String>();
				for(ProductDto current : products){
					//判断前置状态是否为上架状态
					if (!"3".equals(current.getState())){
						list.add(String.valueOf(current.getProductId()));
					}else {
						Map<String, Object> paramsMap = new HashMap<String, Object>();
						paramsMap.put("productId", current.getProductId());
						paramsMap.put("updateUserId", memberId);
						paramsMap.put("updateTimeString", DateUtil.getSysDateTimeString());
						paramsMap.put("state", state);
						paramList.add(paramsMap);
						
//						current.setState(state);
//						current.setUpdateUserId(String.valueOf(memberId));
//						current.setUpdateTimeString(DateUtil.getSysDateString());
					}
				}
				if (!list.isEmpty()){
					map.put("status", 0);
					map.put("message", "存在非销售中的产品, 不能下架");
					return JSONObject.toJSONString(map);
				}
				productToolService.batchUpdateProductStatus(paramList);
				map.put("status", 1);
			}else if ("5".equals(state)){
				for(ProductDto current : products){
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("productId", current.getProductId());
					paramsMap.put("updateUserId", memberId);
					paramsMap.put("updateTimeString", DateUtil.getSysDateTimeString());
					paramsMap.put("state", state);
					paramList.add(paramsMap);
				}
				//删除
				productToolService.batchUpdateProductStatus(paramList);
				map.put("status", 1);
			}
			
		} catch (Exception e) {
			map.put("status", 0);
			map.put("message", "批量更新产品状态异常");
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 删除某个产品记录
	 * 
	 * @param request
	 * @param productId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("product/delete")
	public String delete(String productId) {
		int result = 0;
		try {
			result = productToolService.deleteProduct(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping("product/queryProductMutilPrice/{productId}")
	public String queryProductMutilPrice(@PathVariable("productId")String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ProductPriceDto> list = productToolService.getProductPriceList(productId);
			map.put("status", 1);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return JSONArray.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping("product/modifyPrice")
	public String modifyPrice(ProductQueryParams params) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MemberBaseinfoEntity user = getUser(getRequest());
			String productId = params.getProductId();
			ProductDto productDto = productToolService.getByProductId(productId);
			String updateTime = DateUtil.getSysDateTimeString();
			Date updateDate = DateUtil.parseDate(updateTime, DateUtil.DATE_FORMAT_DATETIME);
			
			if ("0".equals(params.getPriceType())){
				//修改单价
				Double price = params.getPrice();
				map.put("price", price);
				map.put("priceType", "0");
				map.put("productId", productId);
				map.put("updateUserId", user.getMemberId());
				map.put("updateTimeString", updateTime);
				map.put("updatePriceTime", updateTime);
				
				String expirationDateString = DateUtil.toString(DateUtil.addDays(updateDate, 
						Integer.valueOf(productDto.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
				map.put("expirationDateString", expirationDateString);
				
				productToolService.updateProduct(map);
			}else {
				//修改区间价格
				List<ProductPriceDto> priceDtoList = params.getPriceDtoList();
				if (priceDtoList != null && !priceDtoList.isEmpty()){
					//删除原来的区间价格
					productToolService.batchDeletePrices(productId);
					for (ProductPriceDto productPriceDto : priceDtoList) {
						if (productPriceDto != null ) {
							if (productPriceDto.getPrice() == null || productPriceDto.getPrice().doubleValue() < 0){
								productPriceDto.setPrice(Double.valueOf(0));
							}
							
							productPriceDto.setCreateUserId(user.getMemberId().toString());
							productPriceDto.setCreateTimeString(DateUtil.getSysDateTimeString());
							productPriceDto.setProductId(Long.valueOf(productId));
						}
					}
					//更新区间价格
					productToolService.batchUpdatePrice(priceDtoList);
					//排序
//					checkAndSort(priceDtoList);
					//排序取得最小价格, 不改变prices, 即不对prices排序
					ProductPriceDto[] priceArray = new ProductPriceDto[priceDtoList.size()]; 
					priceDtoList.toArray(priceArray);
					checkAndSort(priceArray);
					//将区间价格的最小值写入产品信息表
					map.put("price", priceArray[0].getPrice());
					map.put("priceType", "1");
					map.put("productId", params.getProductId());
					map.put("updateUserId", user.getMemberId());
					map.put("updateTimeString", DateUtil.getSysDateTimeString());
					map.put("updatePriceTime", DateUtil.getSysDateTimeString());
					
					String expirationDateString = DateUtil.toString(DateUtil.addDays(updateDate, 
							Integer.valueOf(productDto.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
					map.put("expirationDateString", expirationDateString);
					
					productToolService.updateProduct(map);
				}
			}
			map.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return JSONArray.toJSONString(map);
	}
	
	/**
	 * 批量产品记录
	 * 
	 * @param request
	 * @param productId
	 * @param map
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping("product/batchDelete")
	public String batchDelete(String productIds, ModelMap map) {

		String[] ids = productIds.split(",");
		int[] result = null;
		try {
			result = productToolService.batchDeleteProduct(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONArray.toJSONString(result);
	}*/
	
	/**
	 * 用于关注产品 ajax
	 * @param bid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("focus/{pid}")
	public BaseJsonResult focusProduct(@PathVariable String pid) throws Exception {
		
		MemberBaseinfoEntity user = getUser(getRequest());
		BaseJsonResult result = new BaseJsonResult();
		if (user != null) {
			Long userId = user.getMemberId();
			
			/*
			 * 判断您是否已经收藏
			 */
			UsercollectProductDTO usDTO = usercollectProductToolService
					.getCollect(userId, Long.valueOf(pid));
			
			/*
			 * 未收藏添加
			 */
			if(usDTO==null) {
				try {
					usercollectProductToolService.addFocus(userId,
							Long.valueOf(pid),null);
				} catch (NumberFormatException e) {
					result.setStatusCode(-1);
					result.setMsg("产品标识不正确");
					return result;
				} catch (Exception e) {
					result.setStatusCode(-1);
					result.setMsg("关联失败");
					return result;
				}
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
	 * 用于取消关注产品 ajax
	 * @param bid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("blur/{pid}")
	public BaseJsonResult blurProduct(@PathVariable String pid) throws Exception {
		
		MemberBaseinfoEntity user = getUser(getRequest());
		BaseJsonResult result = new BaseJsonResult();
		if (user != null) {
			Long userId = user.getMemberId();
			try {
				usercollectProductToolService.cancelFocus(userId,
						Long.valueOf(pid));
			} catch (NumberFormatException e) {
				result.setStatusCode(-1);
				result.setMsg("产品标识不正确");
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
	
	
	
	@RequestMapping("product/chooseCategory")
	public String chooseProductCategory(ModelMap map, HttpServletRequest request){
		
		try {
			if(!BusinessUtil.isUserHaveBusiness(getRequest(), businessBaseinfoToolService)){
				putModel("reason", "亲, 你还没有商铺, 请先创建商铺...");
				return "usercenter/userCenterNoBusiness";
			}
			Long marketId = null ;
			MemberBaseinfoEntity user = getUser(getRequest());
			//用户级别为4表示产地供应商
			if (4 == user.getLevel().intValue()){
				marketId = new Long(3);
			}else {
				Long businessId = BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
				Map<String, Object> paramsMap = new HashMap<>();
				paramsMap.put("businessId", businessId);
				List<ReBusinessMarketDTO> list = reBusinessMarketToolService.getAllBySearch(paramsMap);
				if (list == null || list.isEmpty()){
					throw new Exception("未维护市场与店铺的关系");
				}else if (list.size() > 1){
					throw new Exception("市场与店铺的关系有误, 存在多个关系");
				}
				marketId = list.get(0).getMarketId();
				
			}
			List<ProductCategoryDTO> categoryList = productToolService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
			map.put("categoryList", categoryList);
			map.put("roleType", user.getLevel());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usercenter/product/chooseCategory";
	}
	
	@ResponseBody
	@RequestMapping("product/getChildProductCategory/{categoryId}")
	public String getChildProductCategory(@PathVariable("categoryId")String categoryId, ModelMap map){
		List<ProductCategoryDTO> children = null;
		try {
			children = productToolService.getChildProductCategory(Long.valueOf(categoryId));
			map.put("children", children);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("children", "");
		}
		return JSONObject.toJSONString(children);
	}
	
	@ResponseBody
	@RequestMapping("uploadProductPic")
	public String uploadProductPic(
			HttpServletRequest request,
			@RequestParam(value = "productPicture", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				String fileName = CommonUtil.generateSimpleFileName(file
						.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(
						file.getBytes(), fileName);
			} else {
				map.put("status", 0);
				map.put("message", "upload file failed!!");
				return JSONObject.toJSONString(map);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		}

		map.put("status", 1);
		map.put("message", "upload file success");
		map.put("url", masterPicPath);
		return JSONObject.toJSONString(map);
	}
}
