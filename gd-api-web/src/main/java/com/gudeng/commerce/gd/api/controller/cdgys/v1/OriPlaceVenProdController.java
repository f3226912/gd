package com.gudeng.commerce.gd.api.controller.cdgys.v1;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.PictureTypeEmum;
import com.gudeng.commerce.gd.api.enums.PriceTypeEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.params.ProductParamsBean;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.PromChainToolService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.service.TaskToolService;
import com.gudeng.commerce.gd.api.service.cdgys.OriPlaceVenProdService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.enums.ProductTypeEnum;
import com.gudeng.commerce.gd.support.BusinessSupport;
import com.gudeng.commerce.gd.support.ProductSupport;
import com.gudeng.commerce.gd.support.TraverseProductCallBackImpl;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 产地供应商-产品控制器
 */
@Controller
@RequestMapping("/cdgys/v1/oriPlaceVenProd/")
public class OriPlaceVenProdController extends GDAPIBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(OriPlaceVenProdController.class);

	@Autowired
	public OriPlaceVenProdService oriPlaceVenProdService;

	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;

	@Autowired
	public MemberToolService memberToolService;

	// private final static String BUSINESS_ID = "14758";

	@Autowired
	private TaskToolService taskToolService;

	@Autowired
	public PromChainToolService promChainToolService;

	@RequestMapping("categoryList")
	public void categoryList(String option, String productId, String userId, HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			List<ProductCategoryDTO> res = oriPlaceVenProdService.categoryList();
			result.setObject(res);
		} catch (Exception e) {
			logger.info("categoryList with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("productList")
	public void productList(String option, String businessId, String userId, HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			if (StringUtils.isEmpty(userId)) {
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			MemberBaseinfoDTO member = memberToolService.getById(userId);
			if (member.getLevel().intValue() != 4) {
				setResult(result, ErrorCodeEnum.GYS_ACCOUNT_ONLY_PERMIT, request, response);
				return;
			}
			if (StringUtils.isEmpty(businessId)) {
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			/*
			 * ReBusinessMarketDTO rbmDto =
			 * reBusinessMarketToolService.getByBusinessId(Long.valueOf(
			 * businessId)); String marketId = ""; if (rbmDto != null ){
			 * marketId = String.valueOf(rbmDto.getMarketId()); }else{ String ms
			 * = "店铺市场关系有误, 找不到businessId为"+ businessId +"对应的市场";
			 * logger.info(ms); setResult(result, ErrorCodeEnum.FAIL.getValue(),
			 * ms, request, response); return ; }
			 */
			// 查询产品(未查询图片)
			List<ProductCategoryDTO> pclist = oriPlaceVenProdService.getProductList(option, "3", businessId);
			TraverseProductCallBackImpl callback = new TraverseProductCallBackImpl();
			callback.setOriPlaceVenProdService(oriPlaceVenProdService);
			pclist = ProductSupport.sort(pclist, callback, option);
			// 查询图片
			List<ProductPictureDto> pictureList = oriPlaceVenProdService
					.getPicturesByProductId(callback.getProductList());
			Map<String, List<ProductPictureDto>> mapPictures = ProductSupport.separatePictures(pictureList, callback);
			// 设置产品图片
			ProductSupport.appendPictures(pclist, mapPictures);
			// 设置产品活动价
			if (pclist != null && pclist.size() > 0) {
				for (ProductCategoryDTO productCategoryDTO : pclist) {
					List<ProductDto> productList = productCategoryDTO.getProductList();
					if (productList != null && productList.size() > 0) {
						for (ProductDto productDto : productList) {
							PromProdInfoDTO promProdInfoDTO = promChainToolService
									.getPromotionProduct(productDto.getProductId());
							if (promProdInfoDTO != null && promProdInfoDTO.getActPrice() > 0) {
								productDto.setPrice(promProdInfoDTO.getActPrice());
								productDto.setPromotion(1);
							}
						}
					}
				}
			}
			result.setObject(pclist);
		} catch (Exception e) {
			logger.info("query product list with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("productUpAndDown")
	public void productUpAndDown(String option, String productId, String userId, HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("productId", productId);
			map.put("option", option);
			map.put("userId", userId);
			ErrorCodeEnum res = oriPlaceVenProdService.productUpAndDown(map);
			if (ErrorCodeEnum.SUCCESS != res) {
				setResult(result, res, request, response);
				return;
			}

			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("status", "1");
			resultMap.put("message", "操作成功");
			result.setObject(resultMap);
		} catch (MalformedURLException e) {
			logger.info("productUpAndDown with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		} catch (Exception e) {
			logger.info("productUpAndDown with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("productBatchUpAndDown")
	public void productBatchUpAndDown(String option, String[] productIds, String userId, HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			if (CommonUtils.isEmpty(productIds)) {
				setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			if (CommonUtils.isEmpty(option)) {
				setResult(result, ErrorCodeEnum.OPERATION_TYPE_ERROR, request, response);
				return;
			}
			if (CommonUtils.isEmpty(userId)) {
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			params.put("productIds", productIds);
			params.put("option", option);
			params.put("userId", userId);
			int[] res = oriPlaceVenProdService.productBatchUpAndDown(params);
			resultMap.put("status", 1);
			resultMap.put("message", res);
			result.setObject(resultMap);
		} catch (MalformedURLException e) {
			logger.info("productBatchUpAndDown with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		} catch (Exception e) {
			logger.info("productBatchUpAndDown with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/addProduct")
	public void addProduct(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params,
			String option) {
		ObjectResult result = new ObjectResult();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("post params : {} , option : {}", new Object[] { params, option });
			}
			// 用户
			String userId = params.getUserId();
			if (StringUtils.isBlank(userId)) {
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// 产品分类
			String categoryId = params.getCategoryId();
			if (StringUtils.isBlank(categoryId)) {
				setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return;
			}
			// 产品名称
			String productName = params.getProductName();
			if (StringUtils.isBlank(productName)) {
				setResult(result, ErrorCodeEnum.PRODUCT_NAME_IS_NULL, request, response);
				return;
			}
			// 图片路径
			String picturePaths = params.getPicture();
			if (StringUtils.isBlank(picturePaths)) {
				setResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
				return;
			}
			// 单位
			String unit = params.getUnit();
			if (StringUtils.isBlank(unit)) {
				setResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
				return;
			}
			if (!CommonUtils.isNumber(unit)) {
				setResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
				return;
			}
			// 价格
			String price = params.getPrice();
			if (StringUtils.isBlank(price)) {
				setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
				return;
			}
			/*
			 * if (Double.valueOf(price) < 0){ setResult(result,
			 * ErrorCodeEnum.FAIL.getValue(), "价格必须大于0", request, response);
			 * return; }
			 */
			// 库存
			String stockCount = params.getStockCount();
			if (StringUtils.isBlank(stockCount)) {
				setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			if (Double.valueOf(stockCount) < 0) {
				setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			// 商铺
			String businessId = params.getBusinessId();
			if (StringUtils.isBlank(businessId)) {
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			// 产品详情
			String description = params.getDescription();
			if (StringUtils.isBlank(description)) {
				setResult(result, ErrorCodeEnum.PRODUCT_DESCRIP_IS_NULL, request, response);
				return;
			}
			MemberBaseinfoDTO member = memberToolService.getById(userId);
			// 新增
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
			// 特定用户直接上架
			if (BusinessSupport.getIncludeList().contains(businessId)) {
				productEntity.setState(ProductStatusEnum.ON.getkey());
			} else {// 否则需要判断当前操作类型(保存或上架)
				if (!CommonUtils.isEmpty(option) && "1".equals(option)) {// 用户新增时提交上架请求
					productEntity.setState(ProductStatusEnum.NEEDAUDIT.getkey());
				} else {
					productEntity.setState(ProductStatusEnum.DRAFT.getkey());
				}
			}

			// 有效期默认30天
			productEntity.setInfoLifeDay(String.valueOf(Constant.Product.PERIOD_OF_VALIDITY));
			productEntity.setOriginProvinceId(params.getOriginProvinceId());
			if (CommonUtils.isEmpty(params.getOriginCityId())) {
				productEntity.setOriginCityId("0");
			} else {
				productEntity.setOriginCityId(params.getOriginCityId());
			}
			if (CommonUtils.isEmpty(params.getOriginAreaId())) {
				productEntity.setOriginAreaId("0");
			} else {
				productEntity.setOriginAreaId(params.getOriginAreaId());
			}
			Date now = new Date();
			productEntity.setCreateTime(now);
			productEntity.setCreateUserId(userId);
			productEntity.setExpirationDate(DateUtil.addDays(now, Constant.Product.PERIOD_OF_VALIDITY));
			productEntity.setUpdateTime(now);
			productEntity.setUpdateUserId(userId);
			// 角色
			productEntity.setRoleType(String.valueOf(member.getLevel()));
			long productId = oriPlaceVenProdService.persistProductViaEntity(productEntity);

			String[] paths = picturePaths.split(",");
			ProductPictureEntity pictureEntity = new ProductPictureEntity();
			// 主图, 第一张图片同时存为APP主图, web主图, web多角度图
			pictureEntity.setPictureType(PictureTypeEmum.Main.getValue());
			pictureEntity.setProductId(productId);
			pictureEntity.setCreateTime(now);
			pictureEntity.setCreateUserId(userId);
			pictureEntity.setUrlOrg(paths[0].trim());
			pictureEntity.setUrl650(CommonUtils.generatePictureName(paths[0].trim(), 650));// 650
			pictureEntity.setUrl400(CommonUtils.generatePictureName(paths[0].trim(), 370));// 400
			pictureEntity.setUrl170(CommonUtils.generatePictureName(paths[0].trim(), 200));// 170
			pictureEntity.setUrl120(CommonUtils.generatePictureName(paths[0].trim(), 160));// 120
			pictureEntity.setUrl60(CommonUtils.generatePictureName(paths[0].trim(), 150));// 60
			oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
			// 多角度
			pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
			oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
			// app
			pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
			oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
			// 若上传了多张图片,则从第二张开始, 都存为多角度图
			if (paths.length > 1) {
				for (int i = 1, len = paths.length; i < len; i++) {
					pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
					pictureEntity.setUrlOrg(paths[i]);
					pictureEntity.setUrl650(CommonUtils.generatePictureName(paths[i], 650));// 650
					pictureEntity.setUrl400(CommonUtils.generatePictureName(paths[i], 370));// 400
					pictureEntity.setUrl170(CommonUtils.generatePictureName(paths[i], 200));// 170
					pictureEntity.setUrl120(CommonUtils.generatePictureName(paths[i], 160));// 120
					pictureEntity.setUrl60(CommonUtils.generatePictureName(paths[i], 150));// 60
					oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
				}
			}

			// if (BUSINESS_ID.equals(params.getBusinessId())) {
			// try {
			// taskToolService.addTask(TaskDTO.getProductSyncTask(Long.toString(productId)));
			// } catch (Exception e) {
			// logger.info(e.getMessage());
			// }
			// }
		} catch (Exception e) {
			logger.info("addProduct with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);

	}

	@RequestMapping("/saveEditInfo")
	public void saveProductEditInfo(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params,
			String option) {
		ObjectResult result = new ObjectResult();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("post params : {} , option : {}", new Object[] { params, option });
			}
			// 产品id
			String productId = params.getProductId();
			if (StringUtils.isBlank(productId)) {
				setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			// 用户
			String userId = params.getUserId();
			if (StringUtils.isBlank(userId)) {
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// 分类
			String categoryId = params.getCategoryId();
			if (StringUtils.isBlank(categoryId)) {
				setResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return;
			}
			String productName = params.getProductName();
			if (StringUtils.isBlank(productName)) {
				setResult(result, ErrorCodeEnum.PRODUCT_NAME_IS_NULL, request, response);
				return;
			}
			// 图片路径
			String picturePaths = params.getPicture();
			if (logger.isInfoEnabled()) {
				logger.info("picturePaths : {}, productId : {}", new Object[] { picturePaths, productId });
			}
			if (StringUtils.isBlank(picturePaths)) {
				setResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
				return;
			}
			// 单位
			String unit = params.getUnit();
			if (StringUtils.isBlank(unit)) {
				setResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
				return;
			}
			if (!CommonUtils.isNumber(unit)) {
				setResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
				return;
			}
			// 价格
			String price = params.getPrice();
			if (StringUtils.isBlank(price)) {
				setResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
				return;
			}
			// 库存
			String stockCount = params.getStockCount();
			if (StringUtils.isBlank(stockCount)) {
				setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			if (Double.valueOf(stockCount) < 0) {
				setResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			// //商铺
			// String businessId = params.getBusinessId();
			// if(StringUtils.isBlank(businessId)){
			// setResult(result, ErrorCodeEnum.FAIL.getValue(), "店铺id不能为空",
			// request, response);
			// return;
			// }
			// 产品详情
			String description = params.getDescription();
			if (StringUtils.isBlank(description)) {
				setResult(result, ErrorCodeEnum.PRODUCT_DESCRIP_IS_NULL, request, response);
				return;
			}
			// 产地-省
			String originProvinceId = params.getOriginProvinceId();
			if (StringUtils.isBlank(originProvinceId)) {
				setResult(result, ErrorCodeEnum.PRODUCT_PROVICE_IS_NULL, request, response);
				return;
			}
			// 产地-市
			String originCityId = params.getOriginCityId();
			if (CommonUtils.isEmpty(originCityId)) {
				originCityId = "0";
			}
			// 产地-区/县
			String originAreaId = params.getOriginAreaId();
			if (CommonUtils.isEmpty(originAreaId)) {
				originAreaId = "0";
			}

			ProductDto old = oriPlaceVenProdService.getProductById(productId);
			// 检查是否有关键内容变化 检测开始--------->
			boolean modified = false;
			if (!description.equalsIgnoreCase(old.getDescription())) {
				logger.info("edit product, description changed, productId : {}, old value :{}, new value : {}",
						new Object[] { productId, old.getDescription(), description });
				modified = true;
			}
			if (!unit.equalsIgnoreCase(old.getUnit())) {
				modified = true;
				logger.info("edit product, unit changed, productId : {}, old value :{}, new value : {}",
						new Object[] { productId, old.getUnit(), unit });
			}
			if (!categoryId.equalsIgnoreCase(old.getCateId().toString())) {
				modified = true;
				logger.info("edit product, categoryId changed, productId : {}, old value :{}, new value : {}",
						new Object[] { productId, old.getCateId(), categoryId });
			}
			if (!productName.equalsIgnoreCase(old.getProductName())) {
				modified = true;
				logger.info("edit product, productName changed, productId : {}, old value :{}, new value : {}",
						new Object[] { productId, old.getProductName(), productName });
			}
			if (!originProvinceId.equalsIgnoreCase(old.getOriginProvinceId())) {
				modified = true;
				logger.info("edit product, originProvinceId changed, productId : {}, old value :{}, new value : {}",
						new Object[] { productId, old.getOriginProvinceId(), originProvinceId });
			}
			if (!originCityId.equalsIgnoreCase(old.getOriginCityId())) {
				modified = true;
				logger.info("edit product, originCityId changed, productId : {}, old value :{}, new value : {}",
						new Object[] { productId, old.getOriginCityId(), originCityId });
			}
			if (!originAreaId.equalsIgnoreCase(old.getOriginAreaId())) {
				modified = true;
				logger.info("edit product, originAreaId changed, productId : {}, old value :{}, new value : {}",
						new Object[] { productId, old.getOriginAreaId(), originAreaId });
			}
			String[] paths = picturePaths.split(",");
			List<ProductPictureDto> oldPictures = oriPlaceVenProdService.getPictureByProductId(productId);
			boolean pictureChanged = false;
			if (isPictureChanged(paths, oldPictures)) {
				modified = true;
				pictureChanged = true;
				logger.info("edit product, picture changed, productId : {}, new value :{}",
						new Object[] { productId, paths });
			}
			if (ProductStatusEnum.REFUSE.getkey().equalsIgnoreCase(old.getState())) {
				logger.info("edit product, old state is notPass, productId : {}", new Object[] { productId });
				modified = true;
			}
			// 关键内容检测结束<---------

			Date now = new Date();
			String updateTime = DateUtil.getDate(now, DateUtil.DATE_FORMAT_DATETIME);
			ProductDto productDto = new ProductDto();
			productDto.setProductId(Long.valueOf(productId));
			productDto.setProductName(productName);
			productDto.setCateId(Long.valueOf(categoryId));
			productDto.setUnit(unit);
			// app端修改为单价, 不处理区间价格
			productDto.setPriceType(PriceTypeEnum.SINGLE.getkey());
			productDto.setPrice(Double.valueOf(price));
			productDto.setDescription(description);
			productDto.setStockCount(Double.valueOf(stockCount));
			productDto.setOriginProvinceId(originProvinceId);
			productDto.setOriginCityId(originCityId);
			productDto.setOriginAreaId(originAreaId);
			productDto.setUpdateUserId(userId);
			productDto.setUpdateTimeString(updateTime);

			// 价格更新
			String oldPrice = old.getPrice() == null ? "0" : String.valueOf(old.getPrice());
			if (!oldPrice.equals(params.getPrice())) {
				productDto.setUpdatePriceTimeString(updateTime);
			}
			// 有效期
			String expirationDateString = DateUtil.toString(
					DateUtil.addDays(now, Integer.valueOf(old.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
			productDto.setExpirationDateString(expirationDateString);

			if (modified) {
				productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_YES);
			}
			// 操作为保存时
			if (CommonUtils.isEmpty(option)) {
				Map<String, Object> param = new HashMap<>();
				param.put("productId", productId);
				param.put("updateUserId", userId);
				param.put("updateTimeString", updateTime);
				// 不能编辑待审核的商品
				if (ProductStatusEnum.NEEDAUDIT.getkey().equals(old.getState())) {
					setResult(result, ErrorCodeEnum.PRODUCT_IS_AUDITING, request, response);
					return;
				} else if (ProductStatusEnum.DRAFT.getkey().equals(old.getState())) {
					// 对新增状态的商品进行编辑, 商品状态仍然为新增状态, 置空编辑标志
					productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
				} else if (ProductStatusEnum.REFUSE.getkey().equals(old.getState())) {
					// 对于被驳回的商品, 无论是否改变过关键内容, 进行保存操作都变为新增状态, 置空编辑标志
					// productDto.setState(ProductStatusEnum.DRAFT.getkey());
					param.put("state", ProductStatusEnum.DRAFT.getkey());
					oriPlaceVenProdService.updateProductStatus(param);
					productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
				} else if (ProductStatusEnum.ON.getkey().equals(old.getState())) {
					// 编辑出售中的商品, 并且修改了关键内容, 商品进入待审核状态, 置空编辑标志
					if (modified) {
						param.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
						oriPlaceVenProdService.updateProductStatus(param);
						productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
						// productDto.setState(ProductStatusEnum.NEEDAUDIT.getkey());
					}
				} else if (ProductStatusEnum.OFF.getkey().equals(old.getState())) {
					// 编辑下架状态的商品, 并且修改了关键内容, 设置编辑标志
					if (modified) {
						productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_YES);
					}
				}

				// 只要页面点击了"已修改"(即'确定')按钮, 就修改状态为待审核, 无论实际数据是否有变更
			}
			// 更新产品
			oriPlaceVenProdService.updateProduct(productDto);

			// 上下架操作
			if (!CommonUtils.isEmpty(option)) {// 操作不为空
				// option:1-保存并且下架, 2-保存并且上架, 为空时:只保存数据,不进行上下架
				if (!(Constant.Product.PRODUCT_OPTION_OFFLINE.equals(option)
						|| Constant.Product.PRODUCT_OPTION_ONLINE.equals(option))) {
					setResult(result, ErrorCodeEnum.OPERATION_TYPE_ERROR, request, response);
					return;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productId", productId);
				map.put("option", option);
				map.put("userId", userId);
				ErrorCodeEnum res = oriPlaceVenProdService.productUpAndDown(map);
				if (ErrorCodeEnum.SUCCESS != res) {
					setResult(result, res, request, response);
					return;
				}
			}
			// 特定用户直接上架
			if (BusinessSupport.getIncludeList().contains(String.valueOf(old.getBusinessId()))) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("productId", productId);
				param.put("state", ProductStatusEnum.ON.getkey());
				param.put("updateUserId", userId);
				param.put("updateTimeString", updateTime);
				// 更新产品状态
				oriPlaceVenProdService.updateProductStatus(param);
				productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
				oriPlaceVenProdService.updateProduct(productDto);
			}
			ProductPictureEntity pictureEntity = new ProductPictureEntity();
			// 图片有变更
			if (pictureChanged) {
				paths = replaceDimension(paths);
				// 删除原来的图片
				oriPlaceVenProdService.deletePicByProductId(productId);
				// web主图, 第一张图片同时存为APP主图, web主图, web多角度图
				pictureEntity.setPictureType(PictureTypeEmum.Main.getValue());
				pictureEntity.setProductId(Long.valueOf(productId));
				pictureEntity.setCreateTime(now);
				pictureEntity.setCreateUserId(userId);
				pictureEntity.setUrlOrg(paths[0]);
				pictureEntity.setUrl650(CommonUtils.generatePictureName(paths[0], 650));// 650
				pictureEntity.setUrl400(CommonUtils.generatePictureName(paths[0], 370));// 400
				pictureEntity.setUrl170(CommonUtils.generatePictureName(paths[0], 200));// 170
				pictureEntity.setUrl120(CommonUtils.generatePictureName(paths[0], 160));// 120
				pictureEntity.setUrl60(CommonUtils.generatePictureName(paths[0], 150));// 60
				oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
				// 多角度, 第一张图片同时存为web多角度图
				pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
				oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
				// app主图, 第一张图片同时存为APP主图
				pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
				oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
				// 若上传了多张图片,则从第二张开始, 都存为多角度图, 注意: 此操作可能会导致一些多角度图被删除,
				// 因为当前App最多为3张
				if (paths.length > 1) {
					for (int i = 1, len = paths.length; i < len; i++) {
						pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
						pictureEntity.setUrlOrg(paths[i]);
						pictureEntity.setUrl650(CommonUtils.generatePictureName(paths[i], 650));// 650
						pictureEntity.setUrl400(CommonUtils.generatePictureName(paths[i], 370));// 400
						pictureEntity.setUrl170(CommonUtils.generatePictureName(paths[i], 200));// 170
						pictureEntity.setUrl120(CommonUtils.generatePictureName(paths[i], 160));// 120
						pictureEntity.setUrl60(CommonUtils.generatePictureName(paths[i], 150));// 60
						oriPlaceVenProdService.addProductPicViaEntity(pictureEntity);
					}
				}
			}

		} catch (Exception e) {
			logger.info("saveEditInfo with ex : ", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	private boolean isPictureChanged(String path[], List<ProductPictureDto> oldPictures) {
		if (CommonUtils.isEmpty(path)) {
			return true;
		}
		int newLength = path.length;
		List<ProductPictureDto> nonRepeatList = ProductSupport.sortPictures(oldPictures);
		// 200尺寸的集合
		Set<String> pics = new HashSet<String>();
		ProductPictureDto current = null;
		for (Iterator<ProductPictureDto> it = nonRepeatList.iterator(); it.hasNext();) {
			current = it.next();
			pics.add(StringUtils.trim(current.getUrl170()));
		}
		if (newLength > 2) {
			// 逐个匹配
			for (int i = 0, len = path.length; i < len; i++) {
				if (!pics.contains(StringUtils.trim(path[i]))) {
					if (logger.isInfoEnabled()) {
						logger.info("length = 3; not match picture :{}, old pictures :{}",
								new Object[] { StringUtils.trim(path[i]), pics });
					}
					return true;
				}
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("new path length :{}, nonRepeatList length : {} ",
						new Object[] { newLength, nonRepeatList.size() });
			}
			// 长度不同肯定不匹配
			if (newLength != nonRepeatList.size()) {
				return true;
			} else {
				// 长度相同需要逐个匹配
				for (int i = 0, len = path.length; i < len; i++) {
					if (!pics.contains(StringUtils.trim(path[i]))) {
						if (logger.isInfoEnabled()) {
							logger.info("length < 3; not match picture :{}, old pictures :{}",
									new Object[] { StringUtils.trim(path[i]), pics });
						}
						return true;
					}
				}
			}
		}
		// nonRepeatList的元素个数肯定大于等于newLength
		/*
		 * for(int i = 0; i < newLength; i++){ if
		 * (!StringUtils.trim(path[i]).equalsIgnoreCase(StringUtils.trim(
		 * nonRepeatList.get(i).getUrl170()))){ return true ; } }
		 */
		return false;
	}

	private String[] replaceDimension(String[] paths) {
		int index = 0, len = paths.length;
		String dimension = "", extension = "", profix = "", current = "";
		for (int i = 0; i < len; i++) {
			current = paths[i].trim();
			index = current.lastIndexOf(".");
			dimension = current.substring(index - 7, index);
			extension = current.substring(index);
			profix = current.substring(0, index - 7);
			// 相等则说明该是原来的图片
			if ("200_200".equalsIgnoreCase(dimension)) {
				paths[i] = profix + extension;
			}
		}
		return paths;

	}
}
