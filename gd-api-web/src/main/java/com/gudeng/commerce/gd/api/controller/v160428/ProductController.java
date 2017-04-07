package com.gudeng.commerce.gd.api.controller.v160428;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.ProductListAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.PictureTypeEmum;
import com.gudeng.commerce.gd.api.enums.PriceTypeEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.params.GetShopProductBean;
import com.gudeng.commerce.gd.api.params.ProductParamsBean;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.PromChainToolService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.service.SystemCodeToolService;
import com.gudeng.commerce.gd.api.service.TaskToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.enums.ProductTypeEnum;
import com.gudeng.commerce.gd.support.BusinessSupport;
import com.gudeng.commerce.gd.support.ProductSupport;
import com.gudeng.commerce.gd.support.TraverseProductCallBackImpl2;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller("ProductController4Encrypt")
@RequestMapping("v2Encrypt/product/")
public class ProductController extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(ProductController.class);

	@Autowired
	private TaskToolService taskToolService;

	@Autowired
	public MemberToolService memberToolService;

	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;

	@Autowired
	public ProductToolService productToolService;

	@Autowired
	public SystemCodeToolService systemCodeToolService;

	@Autowired
	public PromChainToolService promChainToolService;

	@RequestMapping("categoryList")
	public void categoryList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String marketId = (String) GSONUtils.getJsonValueStr(jsonStr, "marketId");
			if (CommonUtils.isEmpty(marketId)) {
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
				return;
			}
			List<ProductCategoryDTO> res = productToolService.categoryList(marketId);
			result.setObject(res);
		} catch (Exception e) {
			logger.info("categoryList with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("productList")
	public void productList(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					ProductParamsBean.class);
			// 商铺id
			String businessId = queryParams.getBusinessId();
			if (StringUtils.isEmpty(businessId)) {
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			// 用户id
			String userId = queryParams.getUserId();
			if (StringUtils.isEmpty(userId)) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// 操作码
			String option = queryParams.getOption();
			// 系统支持的操作码 : 1-出售中,2-待审核, 3-已下架, 4-制单
			if (CommonUtils.isEmpty(option)
					|| !new HashSet<>(Arrays.asList(new String[] { "1", "2", "3", "4", "5" })).contains(option)) {
				setEncodeResult(result, ErrorCodeEnum.OPERATION_TYPE_ERROR, request, response);
				return;
			}
			// 查询市场
			ReBusinessMarketDTO rbmDto = reBusinessMarketToolService.getByBusinessId(Long.valueOf(businessId));
			String marketId = "";
			if (rbmDto != null) {
				marketId = String.valueOf(rbmDto.getMarketId());
			} else {
				String ms = "店铺市场关系有误, 找不到businessId为" + businessId + "对应的市场";
				logger.info(ms);
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_NOT_FOUND, request, response);
				return;
			}
			List<String> stateList = new ArrayList<String>();
			if ("1".equals(option)) {// 出售中
				stateList.add(ProductStatusEnum.ON.getkey());
			} else if ("2".equals(option)) {// 待审核
				stateList.add(ProductStatusEnum.NEEDAUDIT.getkey());
				stateList.add(ProductStatusEnum.REFUSE.getkey());
			} else if ("3".equals(option)) {// 已下架
				stateList.add(ProductStatusEnum.DRAFT.getkey());
				stateList.add(ProductStatusEnum.OFF.getkey());
			} else if ("4".equals(option)) { // 制单
				// 排除已删除、新增商品
				stateList.add(ProductStatusEnum.ON.getkey());
			} else if ("0".equals(option)) {
				// 排除已删除
				stateList.add(ProductStatusEnum.DRAFT.getkey());
				stateList.add(ProductStatusEnum.NEEDAUDIT.getkey());
				stateList.add(ProductStatusEnum.REFUSE.getkey());
				stateList.add(ProductStatusEnum.ON.getkey());
				stateList.add(ProductStatusEnum.OFF.getkey());
			} else if ("5".equals(option)) {// 已下架+出售中
				stateList.add(ProductStatusEnum.ON.getkey());
				stateList.add(ProductStatusEnum.OFF.getkey());
			}
			// 查询所有一级分类以及一级分类下所有产品(产品未查询图片)
			List<ProductCategoryDTO> pclist = productToolService.getCategoryAndProductListForSeller(stateList, userId,
					marketId, businessId);
			// 遍历-回调
			TraverseProductCallBackImpl2 callback = new TraverseProductCallBackImpl2();
			callback.setProductService(productToolService);
			// 产品排序(对应app中每个页签的产品顺序)
			pclist = ProductSupport.sort(pclist, callback, option);
			// 查询产品列表对应的产品图片
			List<ProductPictureDto> pictureList = productToolService.getPicturesByProductId(callback.getProductList());
			// 将产品图片重新组织成以产品id为分组单元的图片集合, 并对产品图片进行排序, 设置图片地址前缀
			Map<String, List<ProductPictureDto>> mapPictures = ProductSupport.separatePictures(pictureList, callback);
			// 设置产品图片
			ProductSupport.appendPictures(pclist, mapPictures);
			result.setObject(pclist);
		} catch (Exception e) {
			logger.info("query product list with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		// setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/addProduct")
	public void addProduct(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					ProductParamsBean.class);

			// 操作码
			String option = queryParams.getOption();
			// 空串-保存, 1-上架
			if (!new HashSet<>(Arrays.asList(new String[] { "", "1" })).contains(option)) {
				setEncodeResult(result, ErrorCodeEnum.OPERATION_TYPE_ERROR, request, response);
				return;
			}
			if (logger.isInfoEnabled()) {
				logger.info("post params : {} , option : {}", new Object[] { queryParams, option });
			}
			// 用户
			String userId = queryParams.getUserId();
			if (StringUtils.isBlank(userId)) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// 产品分类
			String categoryId = queryParams.getCategoryId();
			if (StringUtils.isBlank(categoryId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return;
			}
			// 产品名称
			String productName = queryParams.getProductName();
			if (StringUtils.isBlank(productName)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_NAME_IS_NULL, request, response);
				return;
			}
			// 图片路径
			String picturePaths = queryParams.getPicture();
			if (StringUtils.isBlank(picturePaths)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
				return;
			}
			// 单位
			String unit = queryParams.getUnit();
			if (StringUtils.isBlank(unit)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
				return;
			}
			if (!CommonUtils.isNumber(unit)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
				return;
			}
			// 价格
			String price = queryParams.getPrice();
			if (StringUtils.isBlank(price) || Double.valueOf(price) < 0) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
				return;
			}
			// 库存
			String stockCount = queryParams.getStockCount();
			if (StringUtils.isBlank(stockCount) || Double.valueOf(stockCount) < 0) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			// 商铺id
			String businessId = queryParams.getBusinessId();
			if (StringUtils.isBlank(businessId)) {
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			// 产品详情
			String description = queryParams.getDescription();
			if (StringUtils.isBlank(description)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_DESCRIP_IS_NULL, request, response);
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
			productEntity.setDescription(queryParams.getDescription());
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
			// 产地-省
			productEntity.setOriginProvinceId(queryParams.getOriginProvinceId());
			// 产地-市
			if (CommonUtils.isEmpty(queryParams.getOriginCityId())) {
				productEntity.setOriginCityId("0");
			} else {
				productEntity.setOriginCityId(queryParams.getOriginCityId());
			}
			// 产地-区/县
			if (CommonUtils.isEmpty(queryParams.getOriginAreaId())) {
				productEntity.setOriginAreaId("0");
			} else {
				productEntity.setOriginAreaId(queryParams.getOriginAreaId());
			}
			Date now = new Date();
			productEntity.setCreateTime(now);
			productEntity.setCreateUserId(userId);
			productEntity.setExpirationDate(DateUtil.addDays(now, Constant.Product.PERIOD_OF_VALIDITY));
			productEntity.setUpdateTime(now);
			productEntity.setUpdateUserId(userId);
			// 角色
			productEntity.setRoleType(String.valueOf(member.getLevel()));
			long productId = productToolService.persistProductViaEntity(productEntity);

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
			productToolService.addProductPicViaEntity(pictureEntity);
			// 多角度
			pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
			productToolService.addProductPicViaEntity(pictureEntity);
			// app
			pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
			productToolService.addProductPicViaEntity(pictureEntity);
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
					productToolService.addProductPicViaEntity(pictureEntity);
				}
			}

			if (BusinessSupport.getIncludeList().contains(businessId)) {
				try {
					taskToolService.addTask(TaskDTO.getProductSyncTask(Long.toString(productId)));
				} catch (Exception e) {
					logger.info(e.getMessage());
					throw e;
				}
			}
		} catch (Exception e) {
			logger.info("addProduct with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);

	}

	@RequestMapping("productUpAndDown")
	public void productUpAndDown(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					ProductParamsBean.class);
			// 用户id
			String userId = queryParams.getUserId();
			if (StringUtils.isEmpty(userId)) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// 产品id
			String productId = queryParams.getProductId();
			if (StringUtils.isEmpty(productId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			// 操作码
			String option = queryParams.getOption();
			// 1-下架, 2-上架
			if (CommonUtils.isEmpty(option)
					|| !new HashSet<>(Arrays.asList(new String[] { "1", "2" })).contains(option)) {
				setEncodeResult(result, ErrorCodeEnum.OPERATION_TYPE_ERROR, request, response);
				return;
			}
			map.put("productId", productId);
			map.put("option", option);
			map.put("userId", userId);
			// 执行上下架操作
			ErrorCodeEnum res = productToolService.productUpAndDown(map);
			if (ErrorCodeEnum.SUCCESS != res) {
				setEncodeResult(result, res, request, response);
				return;
			}
			result.setObject(res);
		} catch (MalformedURLException e) {
			logger.info("productUpAndDown with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		} catch (Exception e) {
			logger.info("productUpAndDown with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		// setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/saveEditInfo")
	public void saveProductEditInfo(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		ProductParamsBean queryParams = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr, ProductParamsBean.class);

			// 参数校验开始<-----------------------
			// 1-下架, 2-上架
			String option = queryParams.getOption();
			if (!new HashSet<>(Arrays.asList(new String[] { "", Constant.Product.PRODUCT_OPTION_OFFLINE,
					Constant.Product.PRODUCT_OPTION_ONLINE })).contains(option)) {
				setEncodeResult(result, ErrorCodeEnum.OPERATION_TYPE_ERROR, request, response);
				return;
			}
			if (logger.isInfoEnabled()) {
				logger.info("post params : {} , option : {}", new Object[] { queryParams, option });
			}
			// 产品id
			String productId = queryParams.getProductId();
			if (StringUtils.isBlank(productId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			// 用户
			String userId = queryParams.getUserId();
			if (StringUtils.isBlank(userId)) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// 分类
			String categoryId = queryParams.getCategoryId();
			if (StringUtils.isBlank(categoryId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return;
			}
			// 产品名称
			String productName = queryParams.getProductName();
			if (StringUtils.isBlank(productName)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_NAME_IS_NULL, request, response);
				return;
			}
			// 图片路径
			String picturePaths = queryParams.getPicture();
			if (logger.isInfoEnabled()) {
				logger.info("picturePaths : {}, productId : {}", new Object[] { picturePaths, productId });
			}
			if (StringUtils.isBlank(picturePaths)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_IMAGE_IS_NULL, request, response);
				return;
			}
			// 单位
			String unit = queryParams.getUnit();
			if (StringUtils.isBlank(unit)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_UNIT_IS_NULL, request, response);
				return;
			}
			if (!CommonUtils.isNumber(unit)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_UNIT_ERROR, request, response);
				return;
			}
			// 价格
			String price = queryParams.getPrice();
			if (StringUtils.isBlank(price)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
				return;
			}
			// 库存
			String stockCount = queryParams.getStockCount();
			if (StringUtils.isBlank(stockCount)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			if (Double.valueOf(stockCount) < 0) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			// 商铺id
			String businessId = queryParams.getBusinessId();
			if (StringUtils.isBlank(businessId)) {
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			// 产品详情
			String description = queryParams.getDescription();
			if (StringUtils.isBlank(description)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_DESCRIP_IS_NULL, request, response);
				return;
			}
			// 产地-省
			String originProvinceId = queryParams.getOriginProvinceId();
			if (StringUtils.isBlank(originProvinceId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PROVICE_IS_NULL, request, response);
				return;
			}
			// 产地-市
			String originCityId = queryParams.getOriginCityId();
			if (CommonUtils.isEmpty(originCityId)) {
				originCityId = "0";
			}
			// 产地-区/县
			String originAreaId = queryParams.getOriginAreaId();
			if (CommonUtils.isEmpty(originAreaId)) {
				originAreaId = "0";
			}
			// 参数校验结束<-----------------------

			ProductDto old = productToolService.getProductById(productId);

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
			// 原图片
			List<ProductPictureDto> oldPictures = productToolService.getPictureByProductId(productId);
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
			if (!oldPrice.equals(queryParams.getPrice())) {
				productDto.setUpdatePriceTimeString(updateTime);
			}
			// 有效期
			String expirationDateString = DateUtil.toString(
					DateUtil.addDays(now, Integer.valueOf(old.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
			productDto.setExpirationDateString(expirationDateString);
			// 设置编辑标志
			if (modified) {
				productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_YES);
			}

			// 操作为保存时-即option为空串
			if (CommonUtils.isEmpty(option)) {

				Map<String, Object> param = new HashMap<>();
				// 特定用户直接上架
				if (BusinessSupport.getIncludeList().contains(String.valueOf(old.getBusinessId()))) {
					param.put("productId", productId);
					param.put("state", ProductStatusEnum.ON.getkey());
					param.put("updateUserId", userId);
					param.put("updateTimeString", updateTime);
					// 更新产品状态
					productToolService.updateProductStatus(param);
					productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
					// 更新产品信息
					productToolService.updateProduct(productDto);
				} else {
					param.put("productId", productId);
					param.put("updateUserId", userId);
					param.put("updateTimeString", updateTime);
					// 不能编辑待审核的商品
					if (ProductStatusEnum.NEEDAUDIT.getkey().equals(old.getState())) {
						setEncodeResult(result, ErrorCodeEnum.PRODUCT_IS_AUDITING, request, response);
						return;
					} else if (ProductStatusEnum.DRAFT.getkey().equals(old.getState())) {
						// 对新增状态的商品进行编辑, 商品状态仍然为新增状态, 置空编辑标志
						productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
					} else if (ProductStatusEnum.REFUSE.getkey().equals(old.getState())) {
						// 对于被驳回的商品, 无论是否改变过关键内容, 进行保存操作都变为新增状态, 置空编辑标志
						param.put("state", ProductStatusEnum.DRAFT.getkey());
						productToolService.updateProductStatus(param);
						productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
					} else if (ProductStatusEnum.ON.getkey().equals(old.getState())) {
						// 编辑出售中的商品, 并且修改了关键内容, 商品进入待审核状态, 置空编辑标志
						if (modified) {
							param.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
							productToolService.updateProductStatus(param);
							productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
						}
					} else if (ProductStatusEnum.OFF.getkey().equals(old.getState())) {
						// 编辑下架状态的商品, 并且修改了关键内容, 设置编辑标志
						if (modified) {
							productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_YES);
						}
					}
					// 更新产品信息
					productToolService.updateProduct(productDto);
				}

			} else {// 上下架操作

				// 特定用户直接上架
				if (BusinessSupport.getIncludeList().contains(String.valueOf(old.getBusinessId()))) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("productId", productId);
					param.put("state", ProductStatusEnum.ON.getkey());
					param.put("updateUserId", userId);
					param.put("updateTimeString", updateTime);
					// 更新产品状态
					productToolService.updateProductStatus(param);
					productDto.setEditSign(Constant.Product.PRODUCT_EDIT_SIGN_NO);
					// 更新产品息
					productToolService.updateProduct(productDto);
				} else {
					// 更新产品信息
					productToolService.updateProduct(productDto);
					// 执行上下架操作
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("productId", productId);
					map.put("option", option);
					map.put("userId", userId);
					ErrorCodeEnum res = productToolService.productUpAndDown(map);
					// 上下架是否正确执行
					if (ErrorCodeEnum.SUCCESS != res) {
						setEncodeResult(result, res, request, response);
						return;
					}
				}

			}

			// 图片有变更
			if (pictureChanged) {
				ProductPictureEntity pictureEntity = new ProductPictureEntity();
				paths = replaceDimension(paths);
				// 删除原来的图片
				productToolService.deletePicByProductId(productId);
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
				productToolService.addProductPicViaEntity(pictureEntity);
				// 多角度, 第一张图片同时存为web多角度图
				pictureEntity.setPictureType(PictureTypeEmum.MULTIPLE.getValue());
				productToolService.addProductPicViaEntity(pictureEntity);
				// app主图, 第一张图片同时存为APP主图
				pictureEntity.setPictureType(PictureTypeEmum.APP.getValue());
				productToolService.addProductPicViaEntity(pictureEntity);
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
						productToolService.addProductPicViaEntity(pictureEntity);
					}
				}
			}

			if (BusinessSupport.getIncludeList().contains(businessId)) {
				try {
					taskToolService.addTask(TaskDTO.getProductSyncTask(queryParams.getProductId()));
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
			}

		} catch (Exception e) {
			logger.info("saveEditInfo with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);

	}

	@RequestMapping("removeProduct")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					ProductParamsBean.class);
			// 用户id
			String userId = queryParams.getUserId();
			if (StringUtils.isEmpty(userId)) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			// 产品id
			String productId = queryParams.getProductId();
			if (StringUtils.isEmpty(productId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}

			Map<String, Object> params = new HashMap<String, Object>();
			String updateTime = DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME);
			params.put("productId", productId);
			params.put("state", ProductStatusEnum.DELETED.getkey());
			params.put("updateUserId", userId);
			params.put("updateTimeString", updateTime);
			int res = productToolService.updateProductStatus(params);
			result.setObject(res);

		} catch (Exception e) {
			logger.info("remove product with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		// setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/loadProductUnits")
	public void loadProductUnits(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			List<SystemCode> units = systemCodeToolService.getListViaType("ProductUnit");
			result.setObject(units);
		} catch (Exception e) {
			logger.info("loadProductUnits with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/modifyProductPrice")
	public void modifyProductPrice(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					ProductParamsBean.class);
			// 产品id
			String productId = queryParams.getProductId();
			if (StringUtils.isBlank(productId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			// 价格
			String price = queryParams.getPrice();
			if (StringUtils.isBlank(price) || Double.valueOf(price) < 0) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PRICE_ERROR, request, response);
				return;
			}
			// 用户id
			String userId = queryParams.getUserId();
			if (StringUtils.isBlank(userId)) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			ProductDto productDto = new ProductDto();
			String updateTime = DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME);
			productDto.setPriceType(PriceTypeEnum.SINGLE.getkey());
			productDto.setProductId(Long.valueOf(productId));
			productDto.setPrice(Double.valueOf(price));
			productDto.setUpdateUserId(userId);
			productDto.setUpdateTimeString(updateTime);
			productDto.setUpdatePriceTimeString(updateTime);
			int ret = productToolService.updateProduct(productDto);
			result.setObject(ret);
		} catch (Exception e) {
			logger.info("modify product price with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/modifyProductStockCount")
	public void modifyProductStockCount(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr,
					ProductParamsBean.class);
			// 产品id
			String productId = queryParams.getProductId();
			if (StringUtils.isBlank(productId)) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			// 库存
			String stockCount = queryParams.getStockCount();
			if (StringUtils.isBlank(stockCount) || Double.valueOf(stockCount) < 0) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_STOCKCOUNT_ERROR, request, response);
				return;
			}
			// 用户id
			String userId = queryParams.getUserId();
			if (StringUtils.isBlank(userId)) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			ProductDto productDto = new ProductDto();
			String updateTime = DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME);
			productDto.setProductId(Long.valueOf(productId));
			productDto.setStockCount(Double.valueOf(stockCount));
			productDto.setUpdateUserId(userId);
			productDto.setUpdateTimeString(updateTime);
			int ret = productToolService.updateProduct(productDto);
			result.setObject(ret);
		} catch (Exception e) {
			logger.info("modify product stockCount with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
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

	/**
	 * 获取商铺的商品列表 （有商品产地）
	 * 
	 * @param request
	 * @param response
	 * @param inputParamDTO
	 */
	@RequestMapping("/getShopProductListNew")
	public void getShopProductListNew(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			GetShopProductBean inputParamDTO = (GetShopProductBean) GSONUtils.fromJsonToObject(jsonStr,
					GetShopProductBean.class);

			Long memberId = inputParamDTO.getMemberId();
			Long productId = inputParamDTO.getProductId();

			if (productId == null) {
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
				return;
			}
			ProductDto pdto = productToolService.getProductById(productId.toString());
			Long businessId = pdto.getBusinessId();
			if (businessId == null) {
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("businessId", businessId);
			map.put("productId", productId);

			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);

			int total = productToolService.getShopsProductTotalNew(map);
			List<ProductListAppDTO> productList = productToolService.getShopProductListNew(map);
			if (productList != null && productList.size() > 0) {
				for (ProductListAppDTO productDto : productList) {
					// 获取产品活动价
					PromProdInfoDTO promProdInfoDTO = promChainToolService
							.getPromotionProduct(productDto.getProductId());
					if (promProdInfoDTO != null && promProdInfoDTO.getActPrice() > 0) {
						productDto.setPrice(promProdInfoDTO.getActPrice());
						productDto.setPromotion(1);
					}
				}
			}

			// 根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(productList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("获取农批商产品列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
