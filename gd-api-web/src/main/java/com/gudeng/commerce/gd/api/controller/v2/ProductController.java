package com.gudeng.commerce.gd.api.controller.v2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.ProductListAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.PictureTypeEmum;
import com.gudeng.commerce.gd.api.enums.PriceTypeEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.params.ProductParamsBean;
import com.gudeng.commerce.gd.api.service.InStoreDetailToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.OrderSubToolService;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.service.SystemCodeToolService;
import com.gudeng.commerce.gd.api.service.TaskToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.enums.ProductTypeEnum;
import com.gudeng.commerce.gd.support.BusinessSupport;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 货源发布管理
 * 
 * @author yanghaoyu
 * 
 */
@Controller
@RequestMapping("v2/product/")
public class ProductController extends GDAPIBaseController {

		private static final GdLogger logger = GdLoggerFactory.getLogger(ProductController.class);
		
		@Autowired
		public ProCategoryService productCategory;
		
		@Autowired
		public ProductToolService productToolService;
		
		@Autowired
		public MemberToolService memberToolService;
		
		@Autowired
		public SystemCodeToolService systemCodeToolService;
		
		@Autowired
		public OrderSubToolService orderSubToolService;
		
		@Autowired
		public InStoreDetailToolService inStoreDetailToolService;
		
		@Autowired
		public ReBusinessMarketToolService reBusinessMarketToolService;
		
//		private final static String BUSINESS_ID = "14758";
		
		@Autowired
		private TaskToolService taskToolService;
		
		@RequestMapping("/addProduct")
		public void addProduct(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				//校验
				String userId = params.getUserId();
				if(StringUtils.isBlank(userId)){
					setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
					return;
				}
				String categoryId = params.getCategoryId();
				if(StringUtils.isBlank(categoryId)){
					setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
					return;
				}
				String productName = params.getProductName();
				if(StringUtils.isBlank(productName)){
					setResult(result, ErrorCodeEnum.PRODUCT_NAME_IS_NULL, request, response);
					return;
				}
				String picturePath = params.getPicture();
				if(StringUtils.isBlank(picturePath)){
					setResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
					return;
				}
				String unit = params.getUnit();
				if(StringUtils.isBlank(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
					return;
				}
				if (!CommonUtils.isNumber(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
					return;
				}
				String price = params.getPrice();
				if(StringUtils.isBlank(price)){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				if (Double.valueOf(price) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				String stockCount = params.getStockCount();
				if(StringUtils.isBlank(stockCount)){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				if (Double.valueOf(stockCount) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				String businessId = params.getBusinessId();
				if(StringUtils.isBlank(businessId)){
					setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
					return;
				}
				
				MemberBaseinfoDTO member = memberToolService.getById(userId);
				//新增
				ProductEntity productEntity = new ProductEntity();
				productEntity.setCateId(Long.valueOf(categoryId));
				productEntity.setProductName(productName);
				productEntity.setBusinessId(Long.valueOf(businessId));
				productEntity.setUnit(unit);
				productEntity.setPrice(Double.valueOf(price));
				productEntity.setDescription(params.getDescription());
				productEntity.setStockCount(Double.valueOf(stockCount));
				productEntity.setPriceType(PriceTypeEnum.SINGLE.getkey());
				productEntity.setProductType(ProductTypeEnum.Common.getKey());
				if (BusinessSupport.getIncludeList().contains(businessId)){
					productEntity.setState(ProductStatusEnum.ON.getkey());
				}else{
					productEntity.setState(ProductStatusEnum.NEEDAUDIT.getkey());
				}
				//有效期默认30天
				productEntity.setInfoLifeDay("30");
				Date now = new Date();
				productEntity.setCreateTime(now);
				productEntity.setCreateUserId(userId);
				productEntity.setExpirationDate(DateUtil.addDays(now, 30));
				productEntity.setUpdateTime(now);
				productEntity.setUpdateUserId(userId);
				//角色
				productEntity.setRoleType(member.getLevel().toString());
				
				long productId = productToolService.persistProductViaEntity(productEntity);
				ProductPictureEntity pictureEntity = new ProductPictureEntity();
				//主图
				pictureEntity.setPictureType(PictureTypeEmum.Main.getValue());
				pictureEntity.setProductId(productId);
				pictureEntity.setCreateTime(now);
				pictureEntity.setCreateUserId(userId);
				pictureEntity.setUrlOrg(picturePath);
				pictureEntity.setUrl650(CommonUtils.generatePictureName(picturePath, 650));//650
				pictureEntity.setUrl400(CommonUtils.generatePictureName(picturePath, 370));//400
				pictureEntity.setUrl170(CommonUtils.generatePictureName(picturePath, 200));//170
				pictureEntity.setUrl120(CommonUtils.generatePictureName(picturePath, 160));//120
				pictureEntity.setUrl60(CommonUtils.generatePictureName(picturePath, 150));//60
				productToolService.addProductPicViaEntity(pictureEntity);
				//多角度
				pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
				productToolService.addProductPicViaEntity(pictureEntity);
				//app
				pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
				productToolService.addProductPicViaEntity(pictureEntity);
				
//				if (BUSINESS_ID.equals(params.getBusinessId())) {
//					try {
//						taskToolService.addTask(TaskDTO.getProductSyncTask(Long.toString(productId)));
//					} catch (Exception e) {
//						logger.info(e.getMessage());
//					}
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/loadProductInfo")
		public void loadProductInfo(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				String productId = params.getProductId();
				if(StringUtils.isBlank(productId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
					return;
				}
				ProductDto productDto = productToolService.getProductById(productId);
				ProductPictureDto pictureDto = productToolService.getPicture(productId, PictureTypeEmum.APP.getValue().toString());
				productDto.setUrlOrg(pictureDto.getUrlOrg());
				productDto.setFormattedPrice(MoneyUtil.format(productDto.getPrice()));
				//区间价格
				if (PriceTypeEnum.REGION.getkey().equalsIgnoreCase(productDto.getPriceType())){
					//取区间价格最小值
					List<ProductPriceDto> priceDtoList = productToolService.getLadderPriceByProductId(productId);
					if (priceDtoList != null && !priceDtoList.isEmpty()){
						checkAndSort(priceDtoList);
						productDto.setPrice(priceDtoList.get(0).getPrice());
						productDto.setFormattedPrice(MoneyUtil.format(priceDtoList.get(0).getPrice()));
					}
				}
				result.setObject(productDto);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		private List<ProductPriceDto> checkAndSort(List<ProductPriceDto> list){
			for(ProductPriceDto item : list){
				if (item.getPrice() == null){
					item.setPrice(Double.valueOf(0));
				}
			}
			Collections.sort(list);
			return list;
		}
		
		@RequestMapping("/saveProductEditInfo")
		public void saveProductEditInfo(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				//校验
				String productId = params.getProductId();
				if(StringUtils.isBlank(productId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
					return;
				}
				String userId = params.getUserId();
				if(StringUtils.isBlank(userId)){
					setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
					return;
				}
				String categoryId = params.getCategoryId();
				if(StringUtils.isBlank(categoryId)){
					setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
					return;
				}
//				String productName = params.getProductName();
//				if(StringUtils.isBlank(productName)){
//					setResult(result, ErrorCodeEnum.FAIL.getValue(), "产品名称不能为空", request, response);
//					return;
//				}
				String picturePath = params.getPicture();
				if(StringUtils.isBlank(picturePath)){
					setResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
					return;
				}
				String unit = params.getUnit();
				if(StringUtils.isBlank(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
					return;
				}
				if (!CommonUtils.isNumber(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
					return;
				}
				String price = params.getPrice();
				if(StringUtils.isBlank(price)){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				if (Double.valueOf(price) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				String stockCount = params.getStockCount();
				if(StringUtils.isBlank(stockCount)){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				if (Double.valueOf(stockCount) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				String businessId = params.getBusinessId();
				if(StringUtils.isBlank(businessId)){
					setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
					return;
				}
				String description = params.getDescription();
				if(StringUtils.isBlank(description)){
					setResult(result, ErrorCodeEnum.PRODUCT_DESCRIP_IS_NULL, request, response);
					return;
				}
				
				ProductDto old = productToolService.getByProductId(productId);
				boolean needAudit = false;
				//产品介绍有变更, 需要审核
				if (!description.equalsIgnoreCase(old.getDescription())){
					logger.info("edit product, description changed, productId : {}, old value :{}, new value : {}", 
							new Object[]{productId, old.getDescription(), description});
					needAudit = true;
				}
				//单位有变更, 需要审核
				if (!unit.equalsIgnoreCase(old.getUnit())){
					needAudit = true;
					logger.info("edit product, unit changed, productId : {}, old value :{}, new value : {}", 
							new Object[]{productId, old.getUnit(), unit});
				}
				//分类有变更, 需要审核
				if (!needAudit && !categoryId.equalsIgnoreCase(old.getCateId().toString())){
					needAudit = true;
					logger.info("edit product, categoryId changed, productId : {}, old value :{}, new value : {}", 
							new Object[]{productId, old.getCateId(), categoryId});
				}
//				ProductPictureDto picture = productToolService.getPicture(productId, PictureTypeEmum.APP.getValue().toString());
				boolean pictureChanged = false ;
				//图片有变更, 需要审核
				if (isPictureChanged(picturePath)){
					needAudit = true;
					pictureChanged = true ;
					logger.info("edit product, picturePath changed, productId : {}, old value :{}, new value : {}", 
							new Object[]{productId, old.getUrlOrg(), picturePath});
				}
				if (ProductStatusEnum.REFUSE.getkey().equalsIgnoreCase(old.getState())){
					logger.info("edit product, old state is notPass, productId : {}", 
							new Object[]{productId});
					needAudit = true;
				}
				Date now = new Date();
				String updateTime = DateUtil.getDate(now, DateUtil.DATE_FORMAT_DATETIME);
				ProductDto productDto = new ProductDto();
				productDto.setProductId(Long.valueOf(productId));
				productDto.setCateId(Long.valueOf(categoryId));
				productDto.setUnit(unit);
				//app端修改为单价, 不处理区间价格
				productDto.setPriceType(PriceTypeEnum.SINGLE.getkey());
				productDto.setPrice(Double.valueOf(price));
				productDto.setDescription(description);
				productDto.setStockCount(Double.valueOf(stockCount));
				if (needAudit){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("productId", productId);
					param.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
					param.put("updateUserId", userId);
					param.put("updateTimeString", updateTime);
					//更新产品状态
					productToolService.updateProductStatus(param);
				}
				//特定用户直接上架
				if (BusinessSupport.getIncludeList().contains(businessId)){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("productId", productId);
					param.put("state", ProductStatusEnum.ON.getkey());
					param.put("updateUserId", userId);
					param.put("updateTimeString", updateTime);
					//更新产品状态
					productToolService.updateProductStatus(param);
				}
				productDto.setUpdateUserId(userId);
				productDto.setUpdateTimeString(updateTime);
				//有效期
				String expirationDateString = DateUtil.toString(DateUtil.addDays(now, 
						Integer.valueOf(old.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
				productDto.setExpirationDateString(expirationDateString);
				//更新产品
				productToolService.updateProduct(productDto);
				
				ProductPictureEntity pictureEntity = new ProductPictureEntity();
				//图片有变更
				if (pictureChanged){
					//删除原来的图片
					productToolService.deletePicByProductId(productId);
					//插入新图 - 主图
					pictureEntity.setPictureType(PictureTypeEmum.Main.getValue());
					pictureEntity.setProductId(Long.valueOf(productId));
					pictureEntity.setCreateTime(now);
					pictureEntity.setCreateUserId(userId);
					pictureEntity.setUrlOrg(picturePath);
					pictureEntity.setUrl650(CommonUtils.generatePictureName(picturePath, 650));//650
					pictureEntity.setUrl400(CommonUtils.generatePictureName(picturePath, 370));//400
					pictureEntity.setUrl170(CommonUtils.generatePictureName(picturePath, 200));//170
					pictureEntity.setUrl120(CommonUtils.generatePictureName(picturePath, 160));//120
					pictureEntity.setUrl60(CommonUtils.generatePictureName(picturePath, 150));//60
					productToolService.addProductPicViaEntity(pictureEntity);
					//多角度
					pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
					productToolService.addProductPicViaEntity(pictureEntity);
					//app
					pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
					productToolService.addProductPicViaEntity(pictureEntity);
				}
//				if (BUSINESS_ID.equals(params.getBusinessId())) {
//					try {
//						taskToolService.addTask(TaskDTO.getProductSyncTask(productId));
//					} catch (Exception e) {
//						logger.info(e.getMessage());
//					}
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		private boolean isPictureChanged(String path){
			int index = path.lastIndexOf(".");
			String dimension = path.substring(index - 7, index);
			if ("200_200".equalsIgnoreCase(dimension)){
				return false;
			}
			return true;
		}
		
		@RequestMapping("getShopProductList")
		public void getShopProductList(String userId, String businessId, String productId, 
				HttpServletRequest request, HttpServletResponse response){
			ObjectResult result = new ObjectResult();
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(StringUtils.isEmpty(userId)){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			if(StringUtils.isEmpty(businessId)){
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			try {
				map.put("memberId", userId);
				map.put("businessId", businessId);
				map.put("productId", productId);
				CommonPageDTO pageDTO = getPageInfo(request, map);
				int total = productToolService.getShopsProductTotal(map);
				List<ProductListAppDTO> productList = productToolService.getShopProductList(map);
				
				//根据总数设置pageDTO信息
				pageDTO.setRecordCount(total);
				pageDTO.initiatePage(total);
				pageDTO.setRecordList(productList);
				result.setObject(pageDTO);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("getShopProductListInCateId")
		public void getShopProductListInCateId(String userId, String businessId, String cateId, HttpServletRequest request, HttpServletResponse response){
			ObjectResult result = new ObjectResult();
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isEmpty(userId)){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			if(StringUtils.isEmpty(businessId)){
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			
			try {
				MemberBaseinfoDTO member = memberToolService.getById(userId);
				List<Long> second = new ArrayList<Long>();
				List<ProductCategoryDTO> list = productToolService.getChildProductCategory(Long.valueOf(cateId));
				for (ProductCategoryDTO item : list){
					second.add(item.getCategoryId());
				}
				//产地供应商-两级商品分类
				if (member.getLevel().intValue() == 4){
					map.put("cateIdList", second);
					map.put("memberId", userId);
					map.put("businessId", businessId);
					CommonPageDTO pageDTO = getPageInfo(request, map);
					int total = productToolService.getShopsProductTotal(map);
					List<ProductListAppDTO> plist = productToolService.getShopProductList(map);
					//根据总数设置pageDTO信息
					pageDTO.setRecordCount(total);
					pageDTO.initiatePage(total);
					pageDTO.setRecordList(plist);
					result.setObject(pageDTO);
				}else{
					List<Long> cateIdList = new ArrayList<Long>();
					List<ProductCategoryDTO> thirdList = null ;
					//其他--三级分类
					for (Long current :  second){
						thirdList = productToolService.getChildProductCategory(current);
						for(ProductCategoryDTO item : thirdList){
							cateIdList.add(item.getCategoryId());
						}
					}
					map.put("cateIdList", cateIdList);
					map.put("memberId", userId);
					map.put("businessId", businessId);
					CommonPageDTO pageDTO = getPageInfo(request, map);
					int total = productToolService.getShopsProductTotal(map);
					List<ProductListAppDTO> plist = productToolService.getShopProductList(map);
					//根据总数设置pageDTO信息
					pageDTO.setRecordCount(total);
					pageDTO.initiatePage(total);
					pageDTO.setRecordList(plist);
					result.setObject(pageDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/getCategoryAncestors")
		public void getCategoryAncestors(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				String productId = params.getProductId();
				if(StringUtils.isBlank(productId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
					return;
				}
				ProductDto productDto = productToolService.getProductById(productId);
				List<ProductCategoryDTO> list = productToolService.getCategoryAncestors(productDto.getCateId());
				result.setObject(list);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/loadTopCategoryByMarketId")
		public void loadTopCategoryByMarketId(String userId, String marketId, HttpServletRequest request, HttpServletResponse response) {
			
			List<ProductCategoryDTO> list = null;
			ObjectResult result = new ObjectResult();
			try {
				MemberBaseinfoDTO member = memberToolService.getById(userId);
				if (member.getLevel().intValue() == 4){
					marketId = "3" ;
				}
				list = productToolService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
				result.setObject(list);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("getChildProductCategory")
		public void getChildProductCategory(String parentId, HttpServletRequest request, HttpServletResponse response) {
			List<ProductCategoryDTO> list = null;
			ObjectResult result = new ObjectResult();
			try {
				if(StringUtils.isEmpty(parentId)){
					setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
					return;
				}
				list = productToolService.getChildProductCategory(Long.valueOf(parentId));
				result.setObject(list);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		
		@RequestMapping("getCategoryAndProducts")
		public void getCategoryAndProducts(String userId, String businessId, String marketId, String sourceId,
				HttpServletRequest request, HttpServletResponse response){
			ObjectResult result = new ObjectResult();
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			//1-制单页, 2-卖家商品首页
			if(StringUtils.isEmpty(sourceId)){
				setResult(result, ErrorCodeEnum.PRODUCT_SOURCE_IS_NULL, request, response);
				return;
			}
			if(StringUtils.isEmpty(userId)){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			if(StringUtils.isEmpty(businessId)){
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			try {
				ReBusinessMarketDTO rbmDto=reBusinessMarketToolService.getByBusinessId(Long.valueOf(businessId));
				if (rbmDto != null ){
					marketId =  String.valueOf(rbmDto.getMarketId());
				}else{
					String ms = "店铺市场关系有误, 找不到businessId为"+ businessId +"对应的市场";
					logger.info(ms);
					setResult(result, ErrorCodeEnum.MARKET_ID_NOT_FOUND, request, response);
					return ;
				}
				
				MemberBaseinfoDTO member = memberToolService.getById(userId);
				if(member == null){
					setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
					return ;
				}
//				List<AppProductCategoryDTO> relist = new ArrayList<AppProductCategoryDTO>();
				List<String> stateList = new ArrayList<String>();
				if ("1".equalsIgnoreCase(sourceId)){//制单
					stateList.add("3");
				}else if ("2".equalsIgnoreCase(sourceId)){//卖家首页
					stateList.add("1");
					stateList.add("2");
					stateList.add("3");
					stateList.add("4");
				}
				//一级分类
				List<ProductCategoryDTO> pclist = productToolService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
				Iterator<ProductCategoryDTO> iterator = pclist.iterator();
				//查询并填充产品
				while(iterator.hasNext()){
					ProductCategoryDTO currentCategory = iterator.next();
					//二级分类
					List<Long> second = new ArrayList<Long>();
					List<ProductCategoryDTO> list = productToolService.getChildProductCategory(currentCategory.getCategoryId());
					for (ProductCategoryDTO item : list){
						second.add(item.getCategoryId());
					}
					//产地供应商-两级商品分类
					if (member.getLevel().intValue() == 4){
						//分类id集合
						map.put("cateIdList", second);
						map.put("memberId", userId);
						map.put("businessId", businessId);
						map.put("stateList", stateList);
//						CommonPageDTO pageDTO = getPageInfo(request, map);
//						int total = productToolService.getShopsProductTotal(map);
						
						List<ProductDto> plist = productToolService.getShopProductTotalList(map);
						if (plist == null || plist.isEmpty()){
							iterator.remove();
						}else {
//							//补贴判断
//							subsidy(plist);
							//分类设置产品列表
							currentCategory.setProductList(plist);
						}
						//根据总数设置pageDTO信息
//						pageDTO.setRecordCount(total);
//						pageDTO.initiatePage(total);
//						pageDTO.setRecordList(plist);
//						result.setObject(pageDTO);
					}else{
						List<Long> cateIdList = new ArrayList<Long>();
						List<ProductCategoryDTO> thirdList = null ;
						//其他--三级分类
						for (Long current :  second){
							thirdList = productToolService.getChildProductCategory(current);
							for(ProductCategoryDTO item : thirdList){
								cateIdList.add(item.getCategoryId());
							}
						}
						//分类id集合
						map.put("cateIdList", cateIdList);
						map.put("memberId", userId);
						map.put("businessId", businessId);
						map.put("stateList", stateList);
//						CommonPageDTO pageDTO = getPageInfo(request, map);
//						int total = productToolService.getShopsProductTotal(map);
						//
						List<ProductDto> plist = productToolService.getShopProductTotalList(map);
						if (plist == null || plist.isEmpty()){
							iterator.remove();
						}else {
//							//补贴判断
//							subsidy(plist);
							//分类设置产品列表
							currentCategory.setProductList(plist);
						}
						//根据总数设置pageDTO信息
//						pageDTO.setRecordCount(total);
//						pageDTO.initiatePage(total);
//						pageDTO.setRecordList(plist);
//						result.setObject(pageDTO);
					}
				
				}
				if (logger.isInfoEnabled()){
					String messages =  "[ ";
					for(ProductCategoryDTO item : pclist){
						List<ProductDto> prolist = item.getProductList();
						if (prolist != null && !prolist.isEmpty()){
							for(ProductDto pitem : prolist){
								messages += " { productId :" + pitem.getProductId() + " , hasBuySub : " + pitem.getHasBuySub()
										+ ", hasSellSub" + pitem.getHasSellSub() + "} "; 
							}
						}
					}
					messages += "]";
					logger.info("products subsidy  messages : {}", new Object[]{messages});
				}
				result.setObject(pclist);
				
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
/*		*//**
		 * 补贴判断
		 * @param plist
		 * @throws Exception
		 *//*
		private void subsidy(List<ProductDto> plist) throws Exception{
			if (plist == null || plist.isEmpty()){
				return ;
			}
			List<OrderProductDetailDTO> orderProductDetails = new ArrayList<OrderProductDetailDTO>();
			for (ProductDto item :plist){
				OrderProductDetailDTO detailDTO = new OrderProductDetailDTO();
				detailDTO.setProductId(item.getProductId().intValue());
				orderProductDetails.add(detailDTO);
			}
			List<OrderProductDetailDTO> result = orderSubToolService.queryProductSubList(orderProductDetails);
			Map<String,String> map = new HashMap<String,String>();
			for (OrderProductDetailDTO item : result){
				map.put(item.getProductId().toString(), item.getHasBuySub());
			}
			for (ProductDto item :plist){
				item.setHasBuySub(map.get(item.getProductId().toString()));
			}
			
		}*/
		
		@RequestMapping("/modifyProductPrice")
		public void modifyProductPrice(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				String productId = params.getProductId();
				if(StringUtils.isBlank(productId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
					return;
				}
				String price = params.getPrice();
				if(StringUtils.isBlank(price) || Double.valueOf(price) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				String updateTime = DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME);
				ProductDto productDto = new ProductDto();
				productDto.setPriceType(PriceTypeEnum.SINGLE.getkey());
				productDto.setProductId(Long.valueOf(productId));
				productDto.setPrice(Double.valueOf(price));
				productDto.setUpdatePriceTimeString(updateTime);
				int ret = productToolService.updateProduct(productDto);
				result.setObject(ret);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/modifyProductStockCount")
		public void modifyProductStockCount(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				String productId = params.getProductId();
				if(StringUtils.isBlank(productId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
					return;
				}
				String stockCount = params.getStockCount();
				if(StringUtils.isBlank(stockCount) || Double.valueOf(stockCount) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
//				String userId = params.getUserId();
//				if(StringUtils.isBlank(stockCount)){
//					setResult(result, ErrorCodeEnum.FAIL.getValue(), "用户id不能为空", request, response);
//					return;
//				}
				ProductDto productDto = new ProductDto();
				productDto.setProductId(Long.valueOf(productId));
				productDto.setStockCount(Double.valueOf(stockCount));
//				productDto.setUpdateUserId(userId);
				int ret = productToolService.updateProduct(productDto);
				result.setObject(ret);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/loadProductUnits")
		public void loadProductUnits(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				List<SystemCode> units = systemCodeToolService.getListViaType("ProductUnit");
				result.setObject(units);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/deleteProduct")
		public void deleteProduct(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			
			ObjectResult result = new ObjectResult();
			String productId = params.getProductId();
			
			if(StringUtils.isBlank(productId)){
				setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			try {
				int ret = productToolService.deleteProduct(productId);
				result.setObject(ret);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
/*		@RequestMapping("/loadInstoreProductInfo")
		public void loadInstoreProductInfo(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			//入库表id
			String isdId = params.getIsdId();
			if(StringUtils.isBlank(isdId)){
				setResult(result, ErrorCodeEnum.FAIL.getValue(), "入库表id不能为空", request, response);
				return;
			}
			try {
				InStoreDetailDTO inStoreDetailDTO = inStoreDetailToolService.getById(Long.valueOf(isdId));
				result.setObject(inStoreDetailDTO);
			} catch (NumberFormatException e) {
				setResult(result, ErrorCodeEnum.FAIL.getValue(), "加载入库单产品数据异常.....", request, response);
				return ;
			} catch (Exception e) {
				setResult(result, ErrorCodeEnum.FAIL.getValue(), "加载入库单产品数据异常.....", request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}*/
		
		
		/**
		 * 新增产品(带入库表id)
		 * @param request
		 * @param response
		 * @param params
		 */
		@RequestMapping("/addProductWithIsdId")
		public void addProductWithIsdId(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				//校验
				String userId = params.getUserId();
				if(StringUtils.isBlank(userId)){
					setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
					return;
				}
				String categoryId = params.getCategoryId();
				if(StringUtils.isBlank(categoryId)){
					setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
					return;
				}
				String productName = params.getProductName();
				if(StringUtils.isBlank(productName)){
					setResult(result, ErrorCodeEnum.PRODUCT_NAME_IS_NULL, request, response);
					return;
				}
				String picturePath = params.getPicture();
				if(StringUtils.isBlank(picturePath)){
					setResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
					return;
				}
				String unit = params.getUnit();
				if(StringUtils.isBlank(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
					return;
				}
				if (!CommonUtils.isNumber(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
					return;
				}
				String price = params.getPrice();
				if(StringUtils.isBlank(price)){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				if (Double.valueOf(price) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				String stockCount = params.getStockCount();
				if(StringUtils.isBlank(stockCount)){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				if (Double.valueOf(stockCount) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				String businessId = params.getBusinessId();
				if(StringUtils.isBlank(businessId)){
					setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
					return;
				}
				//入库表id
				String isdId = params.getIsdId();
				if(StringUtils.isBlank(isdId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ISDID_IS_NULL, request, response);
					return;
				}
				
				MemberBaseinfoDTO member = memberToolService.getById(userId);
				//新增
				ProductEntity productEntity = new ProductEntity();
				productEntity.setCateId(Long.valueOf(categoryId));
				productEntity.setProductName(productName);
				productEntity.setBusinessId(Long.valueOf(businessId));
				productEntity.setUnit(unit);
				productEntity.setPrice(Double.valueOf(price));
				productEntity.setDescription(params.getDescription());
				productEntity.setStockCount(Double.valueOf(stockCount));
				productEntity.setPriceType(PriceTypeEnum.SINGLE.getkey());
				productEntity.setState(ProductStatusEnum.NEEDAUDIT.getkey());
				//有效期默认30天
				productEntity.setInfoLifeDay("30");
				Date now = new Date();
				productEntity.setCreateTime(now);
				productEntity.setCreateUserId(userId);
				productEntity.setExpirationDate(DateUtil.addDays(now, 30));
				productEntity.setUpdateTime(now);
				productEntity.setUpdateUserId(userId);
				//角色
				productEntity.setRoleType(member.getLevel().toString());
				
				long productId = productToolService.persistProductViaEntity(productEntity);
				ProductPictureEntity pictureEntity = new ProductPictureEntity();
				//主图
				pictureEntity.setPictureType(PictureTypeEmum.Main.getValue());
				pictureEntity.setProductId(productId);
				pictureEntity.setCreateTime(now);
				pictureEntity.setCreateUserId(userId);
				pictureEntity.setUrlOrg(picturePath);
				pictureEntity.setUrl650(CommonUtils.generatePictureName(picturePath, 650));//650
				pictureEntity.setUrl400(CommonUtils.generatePictureName(picturePath, 370));//400
				pictureEntity.setUrl170(CommonUtils.generatePictureName(picturePath, 200));//170
				pictureEntity.setUrl120(CommonUtils.generatePictureName(picturePath, 160));//120
				pictureEntity.setUrl60(CommonUtils.generatePictureName(picturePath, 150));//60
				productToolService.addProductPicViaEntity(pictureEntity);
				//多角度
				pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
				productToolService.addProductPicViaEntity(pictureEntity);
				//app
				pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
				productToolService.addProductPicViaEntity(pictureEntity);
				
				//回写入库表, 填入productId
				InStoreDetailDTO inStoreDetailDTO = new InStoreDetailDTO();
				inStoreDetailDTO.setIsdId(Long.valueOf(isdId));
				inStoreDetailDTO.setProductId(productId);
				inStoreDetailToolService.updateByDto(inStoreDetailDTO);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		/**
		 * 编辑产品(带入库表id)
		 * @param request
		 * @param response
		 * @param params
		 */
		@RequestMapping("/saveProductEditInfoWithIsdId")
		public void saveProductEditInfoWithIsdId(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			try {
				//校验
				String productId = params.getProductId();
				if(StringUtils.isBlank(productId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
					return;
				}
				String userId = params.getUserId();
				if(StringUtils.isBlank(userId)){
					setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
					return;
				}
				String categoryId = params.getCategoryId();
				if(StringUtils.isBlank(categoryId)){
					setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
					return;
				}
//				String productName = params.getProductName();
//				if(StringUtils.isBlank(productName)){
//					setResult(result, ErrorCodeEnum.FAIL.getValue(), "产品名称不能为空", request, response);
//					return;
//				}
				String picturePath = params.getPicture();
				if(StringUtils.isBlank(picturePath)){
					setResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
					return;
				}
				String unit = params.getUnit();
				if(StringUtils.isBlank(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
					return;
				}
				if (!CommonUtils.isNumber(unit)){
					setResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
					return;
				}
				String price = params.getPrice();
				if(StringUtils.isBlank(price)){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				if (Double.valueOf(price) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
					return;
				}
				String stockCount = params.getStockCount();
				if(StringUtils.isBlank(stockCount)){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				if (Double.valueOf(stockCount) < 0){
					setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
					return;
				}
				String businessId = params.getBusinessId();
				if(StringUtils.isBlank(businessId)){
					setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
					return;
				}
				
				//入库表id
				String isdId = params.getIsdId();
				if(StringUtils.isBlank(isdId)){
					setResult(result, ErrorCodeEnum.PRODUCT_ISDID_IS_NULL, request, response);
					return;
				}
				String description = params.getDescription();
				if(StringUtils.isBlank(description)){
					setResult(result, ErrorCodeEnum.PRODUCT_DESCRIP_IS_NULL, request, response);
					return;
				}
				
				ProductDto old = productToolService.getByProductId(productId);
				boolean needAudit = false;
				//产品介绍有变更, 需要审核
				if (!description.equalsIgnoreCase(old.getDescription())){
					needAudit = true;
				}
				//单位有变更, 需要审核
				if (!unit.equalsIgnoreCase(old.getUnit())){
					needAudit = true;
				}
				//分类有变更, 需要审核
				if (!needAudit && !categoryId.equalsIgnoreCase(old.getCateId().toString())){
					needAudit = true;
				}
				boolean pictureChanged = false ;
				//图片有变更, 需要审核
				if (!needAudit && !picturePath.equalsIgnoreCase(CommonUtils.generatePictureName(old.getUrlOrg(), 200))){
					needAudit = true;
					pictureChanged = true ;
				}
/*				//价格类型有变更(即原来为区间价格, app编辑之后只能是单价), 需要审核
				if (!needAudit && PriceTypeEnum.REGION.getkey().equalsIgnoreCase(old.getPriceType())){
					needAudit = true;
				}*/
				if (ProductStatusEnum.REFUSE.getkey().equalsIgnoreCase(old.getState())){
					needAudit = true;
				}
				
				ProductDto productDto = new ProductDto();
				productDto.setProductId(Long.valueOf(productId));
				productDto.setCateId(Long.valueOf(categoryId));
//				productDto.setProductName(productName);
				productDto.setUnit(unit);
				//app端修改为单价, 不处理区间价格
				productDto.setPriceType(PriceTypeEnum.SINGLE.getkey());
				productDto.setPrice(Double.valueOf(price));
				productDto.setDescription(params.getDescription());
				productDto.setStockCount(Double.valueOf(stockCount));
				if (needAudit){
					productDto.setState(ProductStatusEnum.NEEDAUDIT.getkey());
				}
				Date now = new Date();
				productDto.setUpdateUserId(userId);
				productDto.setUpdateTimeString(DateUtil.toString(now, DateUtil.DATE_FORMAT_DATETIME));
				
				productToolService.updateProduct(productDto);
				
				if (pictureChanged){
					ProductPictureEntity pictureEntity = new ProductPictureEntity();
					//删除原来的图片
					productToolService.deletePicByProductId(productId);
					//插入新图 - 主图
					pictureEntity.setPictureType(PictureTypeEmum.Main.getValue());
					pictureEntity.setProductId(Long.valueOf(productId));
					pictureEntity.setCreateTime(now);
					pictureEntity.setCreateUserId(userId);
					pictureEntity.setUrlOrg(picturePath);
					pictureEntity.setUrl650(CommonUtils.generatePictureName(picturePath, 650));//650
					pictureEntity.setUrl400(CommonUtils.generatePictureName(picturePath, 370));//400
					pictureEntity.setUrl170(CommonUtils.generatePictureName(picturePath, 200));//170
					pictureEntity.setUrl120(CommonUtils.generatePictureName(picturePath, 160));//120
					pictureEntity.setUrl60(CommonUtils.generatePictureName(picturePath, 150));//60
					productToolService.addProductPicViaEntity(pictureEntity);
					//多角度
					pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
					productToolService.addProductPicViaEntity(pictureEntity);
					//app
					pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
					productToolService.addProductPicViaEntity(pictureEntity);
				}
				
				//回写入库表, 填入productId
				InStoreDetailDTO inStoreDetailDTO = new InStoreDetailDTO();
				inStoreDetailDTO.setIsdId(Long.valueOf(isdId));
				inStoreDetailDTO.setProductId(Long.valueOf(productId));
				inStoreDetailToolService.updateByDto(inStoreDetailDTO);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/fillStockCount")
		public void fillStockCount(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
			ObjectResult result = new ObjectResult();
			Map<String, Object> map = new HashMap<String, Object>();
			String productId = params.getProductId();
			if(StringUtils.isBlank(productId)){
				setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			String unit = params.getUnit();
			if(StringUtils.isBlank(unit)){
				setResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
				return;
			}
			if (!CommonUtils.isNumber(unit)){
				setResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
				return;
			}
			String stockCount = params.getStockCount();
			if(StringUtils.isBlank(stockCount) || Double.valueOf(stockCount) < 0){
				setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
				return;
			}
			try {
				ProductDto product = productToolService.getByProductId(productId);
				if (unit.equalsIgnoreCase(product.getUnit())){
					product.setStockCount(product.getStockCount()+ Double.valueOf(stockCount));
					productToolService.updateProduct(product);
					map.put("updateStatus", 1);
				}else {
					map.put("updateStatus", 0);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				map.put("updateStatus", 0);
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			} catch (Exception e) {
				e.printStackTrace();
				map.put("updateStatus", 0);
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			result.setObject(map);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
		@RequestMapping("/productUpAndDown")
		public void productUpAndDown(String productId,String stockCount, String option, String userId,HttpServletResponse response, HttpServletRequest request) {
			
			ObjectResult result = new ObjectResult();
			Map<String, Object> param = new HashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Date now = new Date();
			String updateTime = DateUtil.getDate(now, DateUtil.DATE_FORMAT_DATETIME);
			try {
				ProductDto productDto = productToolService.getByProductId(productId);
				if ("0".equals(option)){
					//下架
					if (!"3".equals(productDto.getState())){
						resultMap.put("status", 0);
						resultMap.put("message", "<下架>只能操作销售中的产品");
						result.setObject(resultMap);
						setResult(result, ErrorCodeEnum.PRODUCT_NOT_ON_SALE, request, response);
						return ;
					}
					param.put("state", ProductStatusEnum.OFF.getkey());
					
				}else if ("1".equals(option)){
					if (stockCount == null || "".equals(stockCount)){
						resultMap.put("status", 0);
						resultMap.put("message", "上架操作需要检测库存量, 但是库存量为空");
						result.setObject(resultMap);
						setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
						return ;
					}else if(Double.valueOf(stockCount) <= 0) {
						resultMap.put("status", 0);
						resultMap.put("message", "库存量必须大于0");
						result.setObject(resultMap);
						setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
						return ;
					}
					//上架
					if (!"4".equals(productDto.getState())){
						resultMap.put("status", 0);
						resultMap.put("message", "<上架>只能操作已下架的产品");
						result.setObject(resultMap);
						setResult(result, ErrorCodeEnum.PRODUCT_ALREADY_ON_SALE, request, response);
						return ;
					}
					
					param.put("state", ProductStatusEnum.ON.getkey());
					//更新有效期
					String expirationDateString = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME)
								.format(DateUtil.addDays(now, Integer.valueOf(productDto.getInfoLifeDay())));
					param.put("expirationDateString", expirationDateString);
					
					//修改库存
					Map<String, Object> p_param = new HashMap<String, Object>();
					p_param.put("productId", productId);
					p_param.put("updateUserId", userId);
					p_param.put("stockCount", stockCount);
					p_param.put("updateTimeString", updateTime);
					productToolService.updateProduct(p_param);
				}
				
				param.put("productId", productId);
				param.put("updateUserId", userId);
				param.put("updateTimeString", updateTime);
				productToolService.updateProductStatus(param);
				resultMap.put("status", 1);
				resultMap.put("message", "操作成功");
				result.setObject(resultMap);
			} catch (Exception e) {
				e.printStackTrace();
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}
		
	}

