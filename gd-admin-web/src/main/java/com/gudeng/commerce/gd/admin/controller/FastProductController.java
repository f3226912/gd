package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.KeyValuePair;
import com.gudeng.commerce.gd.admin.dto.ProductParamsBean;
import com.gudeng.commerce.gd.admin.entity.AuditStatusEnum;
import com.gudeng.commerce.gd.admin.entity.PriceTypeEnum;
import com.gudeng.commerce.gd.admin.entity.ProductStatusEnum;
import com.gudeng.commerce.gd.admin.entity.ProductTypeEnum;
import com.gudeng.commerce.gd.admin.service.AuditInfoToolService;
import com.gudeng.commerce.gd.admin.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.ProductToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.admin.service.ReMemberMarkeToolService;
import com.gudeng.commerce.gd.admin.service.SystemCodeToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.admin.util.excel.ExcelUtils;
import com.gudeng.commerce.gd.admin.util.excel.cb.FastProductBeanToSheetCallBack;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.Area;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 *	快速制单商品
 */
@Controller
public class FastProductController extends AdminBaseController implements ApplicationContextAware {
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(FastProductController.class);

	private ApplicationContext applicationContext;

	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public ProCategoryService proCategoryService;

	@Autowired
	public FileUploadToolService fileUploadToolService;

	@Autowired
	public MarketManageService marketManageService;

	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;

	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;

	@Autowired
	public QueryAreaService queryAreaService;

	@Autowired
	public AuditInfoToolService auditInfoToolService;

	@Autowired
	public ReMemberMarkeToolService reMemberMarkeToolService;

	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;

	@Autowired
	public SysRegisterUserAdminService sysRegisterUserAdminService;

	@Autowired
	public SystemCodeToolService systemCodeToolService;
	
	/**
	 * 跳转快速制单商品列表页
	 *
	 * @return
	 */
	@RequestMapping("fastProduct/toProductList")
	public String toProductList() {
		return "fastProduct/fastProductList";
	}
	
	/**
	 * 跳转新增快单产品界面
	 * @param map
	 * @return
	 */
	@RequestMapping("fastProduct/toAddProduct")
	public String toAddProduct(ModelMap map) {
		return "fastProduct/addFastProduct";
	}
	
	/**
	 * 加载分类查询开关
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/categorySwitch")
	public String categorySwitch() {

		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		KeyValuePair all = new KeyValuePair();
		all.setKeyString("全部分类");
		all.setValueString("0");
		KeyValuePair choose = new KeyValuePair();
		choose.setKeyString("选择分类");
		choose.setValueString("1");

		list.add(all);
		list.add(choose);

		return JSONObject.toJSONString(list);
	}
	
	/**
	 * 加载价格类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/loadPriceType")
	public String loadPriceType() {

		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		KeyValuePair simple = new KeyValuePair();
		simple.setKeyString("单价");
		simple.setValueString("0");
		KeyValuePair multiple = new KeyValuePair();
		multiple.setKeyString("多价");
		multiple.setValueString("1");

		list.add(simple);
		list.add(multiple);

		return JSONObject.toJSONString(list);
	}
	
	/**
	 * 分页查询产品列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/list")
	public String list(HttpServletRequest request, ProductParamsBean params) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(params.getStartDate())) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(params.getStartDate())));
			}
			if (StringUtil.isNotEmpty(params.getEndDate())) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(params.getEndDate())));
			}
			if (StringUtil.isNotEmpty(params.getProductName())) {
				map.put("productName", params.getProductName());
			}
			if (StringUtil.isNotEmpty(params.getCategoryId())) {
				map.put("categoryId", params.getCategoryId());
			}
			if (StringUtil.isNotEmpty(params.getCreateUserName())) {
				map.put("userCode", params.getCreateUserName());
			}
			//快单产品
			map.put("productType", ProductTypeEnum.FAST_PRODUCT.getKey());
			// 排序
/*			map.put("sortName", "createTime");
			map.put("sortOrder", "d");*/
			// 记录数
			map.put("total", productToolService.getCountForFast(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<ProductDto> list = productToolService.getFastProductList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.info("query fastProduct with ex : {} ", e);
		}
		return null;
	}
	
	/**
	 * 新增快单商品
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/addFastProduct")
	public String addFastProduct(ProductParamsBean params) {

		Map<String, Object> map = new HashMap<String, Object>();
		ProductEntity product = new ProductEntity();
		String productName = params.getProductName();
		try {
			// 登录用户
			SysRegisterUser user = getUser(request);
			//商品名称
			product.setProductName(productName);
			// 商铺id置为无效
			product.setBusinessId(Long.valueOf(-1));
			//商品分类
			product.setCateId(Long.valueOf(params.getCategoryId()));
			// 价格类型(单价/区间价)
			product.setPriceType(params.getPriceType());
			//区间价
			List<ProductPriceDto> prices = params.getPriceDtoList();
			if (PriceTypeEnum.REGION.getkey().equals(params.getPriceType()) && prices != null
					&& !prices.isEmpty()){
				//排序取得最小价格, 不改变prices, 即不对prices排序
				ProductPriceDto[] priceArray = new ProductPriceDto[prices.size()];
				prices.toArray(priceArray);
				checkAndSort(priceArray);
				product.setPrice(priceArray[0].getPrice());
			}else {
				product.setPrice(params.getPrice()== null ? Double.valueOf(0): params.getPrice());
			}
			// 单位
			product.setUnit(params.getUnit());
			// 库存
			if (!CommonUtil.isEmpty(params.getStockCount())){
				product.setStockCount(Double.valueOf(params.getStockCount()));
			}
			// 角色???
			product.setRoleType(params.getRoleType());
			// 快单产品默认状态为 已上架
			product.setState(ProductStatusEnum.ON.getkey());
			// 信息有效期
			product.setInfoLifeDay(params.getInfoLifeDay());
			// 详细信息
			product.setDescription(params.getDescription());
			// 发布渠道???
			product.setChannel(params.getChannel());
			// 产地-省
			product.setOriginProvinceId(params.getOriginProvinceId());
			//产地-市
			if (CommonUtil.isEmpty(params.getOriginCityId())){
				product.setOriginCityId("0");
			}else {
				product.setOriginCityId(params.getOriginCityId());
			}
			//产地-区/县
			if (CommonUtil.isEmpty(params.getOriginAreaId())){
				product.setOriginAreaId("0");
			}else {
				product.setOriginAreaId(params.getOriginAreaId());
			}
			// 创建人
			product.setCreateUserId(user.getUserID());
			// 产品类型
			product.setProductType(ProductTypeEnum.FAST_PRODUCT.getKey());

			Date now = new Date();
			// 创建时间
			product.setCreateTime(now);
			// 过期时间
			product.setExpirationDate(DateUtil.addDays(now, Integer.valueOf(params.getInfoLifeDay())));
			product.setUpdateTime(now);
			product.setUpdateUserId(user.getUserID());

			// 插入多价格区间列表
			product.setPriceDtoList(params.getPriceDtoList());
			// 生成产品记录
			long productId = productToolService.persistProductViaEntity(product);
			
			// 如果上传了多角度图 则插入图片
			String multiPictureString = params.getMultiplePicture() ;
			if(!CommonUtil.isEmpty(multiPictureString)){
				String[] multiplePictures = multiPictureString.split(",");
				boolean first = true;
				for(String item : multiplePictures){
					if (first){
						first = false;
						//保存第一张多角度图为主图
						savePicture(user.getUserID(), item, 1, productId);
					}
					savePicture(user.getUserID(), item, 2, productId);
				}
			}
			// 如果上传了APP图	则插入APP图
			String appPictureString = params.getAppPicture() ;
			if(!CommonUtil.isEmpty(appPictureString)){
				savePicture(user.getUserID(), appPictureString, 4, productId);
			}

			//添加审核信息-审核通过 start.......
			AuditInfoDTO aid = new AuditInfoDTO();
			aid.setMainId(Long.valueOf(productId));
			aid.setStatus(AuditStatusEnum.PASS.getKey());
			String dateTime = DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME);
			aid.setAuditTime_string(dateTime);
			aid.setMemberId(user.getUserID());
			aid.setMemberName(user.getUserName());
			aid.setCreateTime_string(dateTime);
			aid.setType("1");
			auditInfoToolService.addAuditInfoDTO(aid);
			//添加审核信息-审核通过 end.......

			map.put("status", 1);
			map.put("productId", productId);

		} catch (Exception e) {
			logger.info("Add FastProduct With ex : {} ", e);
			map.put("status", 0);
			map.put("message", "Add FastProduct Failed!!");
		}
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 编辑快单商品
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/editProduct")
	public String editProduct(ProductParamsBean params) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//产品查询参数
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("productId", params.getProductId());
			queryParams.put("productType", ProductTypeEnum.FAST_PRODUCT.getKey());
			// 原产品信息
			ProductDto oldProductDto = productToolService.getOneProduct(queryParams);
			// 区间价列表
			if ("1".equals(oldProductDto.getPriceType())) {
				oldProductDto.setPriceDtoList(productToolService.getProductPriceList(params.getProductId()));
			}
			// 登录用户
			SysRegisterUser user = getUser(request);
			// 当前商品
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			// 产品id
			Long productId = Long.valueOf(params.getProductId());
			paramsMap.put("productId", productId);
			paramsMap.put("productName", params.getProductName());
			paramsMap.put("cateId", Long.valueOf(params.getCategoryId()));
			paramsMap.put("unit", params.getUnit());
			if (!CommonUtil.isEmpty(params.getStockCount())){
				paramsMap.put("stockCount", Double.valueOf(params.getStockCount()));
			}
			paramsMap.put("infoLifeDay", params.getInfoLifeDay());
			paramsMap.put("description", params.getDescription());
			paramsMap.put("channel", params.getChannel());
			// 产地-省
			paramsMap.put("originProvinceId", params.getOriginProvinceId());
			//产地-市
			if (CommonUtil.isEmpty(params.getOriginCityId())){
				paramsMap.put("originCityId", 0);
			}else {
				paramsMap.put("originCityId", params.getOriginCityId());
			}
			//产地-区/县
			if (CommonUtil.isEmpty(params.getOriginAreaId())){
				paramsMap.put("originAreaId", 0);
			}else {
				paramsMap.put("originAreaId", params.getOriginAreaId());
			}
			String updateTime = DateUtil.getSysDateTimeString();
			Date updateDate = DateUtil.parseDate(updateTime, DateUtil.DATE_FORMAT_DATETIME);
			String expirationDateString = DateUtil.toString(DateUtil.addDays(updateDate,
					Integer.valueOf(params.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
			paramsMap.put("updateUserId", user.getUserID());
			paramsMap.put("updateTimeString", updateTime);
			paramsMap.put("expirationDateString", expirationDateString);

			// 价格类型
			paramsMap.put("priceType", params.getPriceType());
			//区间价
			List<ProductPriceDto> priceList = params.getPriceDtoList();
			if ("1".equals(params.getPriceType())  && !CommonUtil.isEmpty(priceList)){
				//排序取得最小价格, 不改变priceList, 即不对priceList排序
				ProductPriceDto[] priceArray = new ProductPriceDto[priceList.size()];
				priceList.toArray(priceArray);
				checkAndSort(priceArray);
				//productDto.setPrice(priceArray[0].getPrice());
				paramsMap.put("price", priceArray[0].getPrice());

				//删除原来的价格区间
				productToolService.batchDeletePrices(String.valueOf(productId));
				//添加新的价格区间
				for (ProductPriceDto productPriceDto : priceList) {
					if (productPriceDto != null ) {
						if (CommonUtil.isEmpty(productPriceDto.getPrice()) || productPriceDto.getPrice().doubleValue() < 0){
							productPriceDto.setPrice(Double.valueOf(0));
						}
						productPriceDto.setCreateUserId(user.getUserID());
						productPriceDto.setProductId(productId);
					}
				}
				//更新
				productToolService.batchUpdatePrice(priceList);

			}else {
				//productDto.setPrice(params.getPrice()== null ? Double.valueOf(0): params.getPrice());
				paramsMap.put("price", params.getPrice()== null ? Double.valueOf(0): params.getPrice());
			}
			// 更新产品记录
			paramsMap.put("allowBlankUnit", 1);
			int result = productToolService.updateProduct(paramsMap);
			
			// 驳回 --> 待审核
			if (ProductStatusEnum.REFUSE.getkey().equals(oldProductDto.getState())){
				Map<String, Object> param = new HashMap<>();
				param.put("productId", productId);
				param.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
				productToolService.updateProductStatus(param);
			}

			//更新图片
			//删除主图
			productToolService.deletePicByProductIdAndType(String.valueOf(productId), "1");
			//删除多角度图
			productToolService.deletePicByProductIdAndType(String.valueOf(productId), "2");
			//删除原App图
			productToolService.deletePicByProductIdAndType(String.valueOf(productId), "4");
			// 新增多角度图, 主图
			String mutiple = params.getMultiplePicture();
			if ( !CommonUtil.isEmpty(mutiple)){
				String[] multiplePictures = mutiple.split(",");
				boolean first = true;
				for(String item : multiplePictures){
					if (first){
						first = false;
						//保存第一张多角度图为主图
						savePicture(user.getUserID(), item, 1, productId);
					}
					savePicture(user.getUserID(),  item, 2, productId);
				}
			}
			//App图
			String app = params.getAppPicture();
			if (!CommonUtil.isEmpty(app)){
				//新增App图
				savePicture(user.getUserID(), params.getAppPicture(), 4, productId);
			}
			map.put("status", 1);
			map.put("message", result);
			
		} catch (Exception e) {
			logger.info("Edit FastProduct With ex : {} ", e);
			map.put("status", 0);
			map.put("message", "edit product failed!!");
		}
		return JSONObject.toJSONString(map);
	}	
	
	
	/**
	 * 删除某个产品记录
	 *
	 * @param request
	 * @param productId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/delete")
	public String deleteProduct(String productId, ModelMap map, HttpServletRequest request) {
		int result = 0;
		try {
			// 系统用户
			SysRegisterUser user = getUser(request);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("productId", productId);
			param.put("state", ProductStatusEnum.DELETED.getkey());
			param.put("updateUserId", user.getUserID());
			param.put("updateTimeString", DateUtil.getSysDateTimeString());
			// 更新状态为已删除
			result = productToolService.updateProductStatus(param);
		} catch (Exception e) {
			logger.info("update FastProduct Status With ex : {} ", e);
		}
		return String.valueOf(result);
	}
	
	@RequestMapping("fastProduct/toDetail/{productId}")
	public String toDetail(@PathVariable("productId") String productId,	HttpServletRequest request,ModelMap map) {

		try {
			//产品查询参数
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("productId", productId);
			queryParams.put("productType", ProductTypeEnum.FAST_PRODUCT.getKey());
			// 产品信息
			ProductDto productDto = productToolService.getOneProduct(queryParams);
			// 区间价格
			if ("1".equals(productDto.getPriceType())) {
				List<ProductPriceDto> mutiplePrices = productToolService.getLadderPriceByProductId(productId);
				map.put("mutiplePrices", mutiplePrices);
			}
			// //
			if (ProductStatusEnum.REFUSE.getkey().equals(productDto.getState())) {// 已驳回
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("mainId", productId);
				param.put("type", "1");
				param.put("status", AuditStatusEnum.NOT_PASS.getKey());
				param.put("startRow", 0);
				param.put("endRow", 100);
				List<AuditInfoDTO> list = auditInfoToolService.getBySearch(param);
				if (list == null || list.isEmpty()){
					String message = "商品未通过审核, 审核信息缺失, 数据异常" ;
					map.put("message", message);
					map.put("status", 0);
				}else{
					for (AuditInfoDTO item : list) {
						//转换审核时间-页面展示使用
						item.setAuditTime_string((DateUtil.toString(item.getAuditTime(),
								DateUtil.DATE_FORMAT_DATETIME)));
					}
					map.put("auditInfoList", list);
					map.put("status", 1);
				}
			}
			else if (ProductStatusEnum.ON.getkey().equals(productDto.getState())) {// 审核通过-上架
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("mainId", productId);
				param.put("type", "1");
				param.put("status", AuditStatusEnum.PASS.getKey());
				param.put("startRow", 0);
				param.put("endRow", 10);
				List<AuditInfoDTO> list = auditInfoToolService.getBySearch(param);
				if (list == null || list.isEmpty()){
					String message = "商品已通过审核, 但审核信息缺失, 数据异常" ;
					map.put("message", message);
					map.put("status", 0);
				}else{
					AuditInfoDTO target = list.get(0);
					target.setAuditTime_string(DateUtil.toString(
							target.getAuditTime(), DateUtil.DATE_FORMAT_DATETIME));
					map.put("auditInfo", target);
					map.put("status", 1);
				}
			}

			//产地-省市区
			if (!CommonUtil.isEmpty(productDto.getOriginProvinceId())){
				map.put("originProvinceName", queryAreaService.getArea(String.valueOf(productDto.getOriginProvinceId())).getArea());
			}
			if (!CommonUtil.isEmpty(productDto.getOriginCityId())){
				Area city= queryAreaService.getArea(String.valueOf(productDto.getOriginCityId()));
				map.put("originCityName", city==null?"":city.getArea());
			}
			if (!CommonUtil.isEmpty(productDto.getOriginAreaId())){
				Area area= queryAreaService.getArea(String.valueOf(productDto.getOriginAreaId()));
				map.put("originAreaName", area==null?"":area.getArea());
			}
			map.put("product", productDto);

			//查询图片
			List<ProductPictureDto> list = productToolService.getPictureByProductId(productId);
			List<Map<String, String>> multiplePictureList = new ArrayList<Map<String, String>>();
			for (ProductPictureDto dto : list) {
				if (1 == dto.getPictureType().intValue()){
					map.put("masterPicture", dto.getUrlOrg());
				}else if (4 == dto.getPictureType().intValue()){
					map.put("appPicture", dto.getUrlOrg());
				}else if (3 == dto.getPictureType().intValue()){
					map.put("webPicture", dto.getUrlOrg());
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
			// 单位
			List<SystemCode> units = systemCodeToolService.getListViaType("ProductUnit");
			map.put("units", units);
		} catch (Exception e) {
			logger.info("View FastProduct With ex : {} ", e);
		}
		//如果从活动管理页面，进入商品详情，则返回别一个JPS地址
		String act = request.getParameter("source");
		if(act != null && act.equals("act")){
			return "active/productDetail";
		}
		return "fastProduct/productDetail";
	}
	
	/**
	 * 审核快单商品 
	 */
	@ResponseBody
	@RequestMapping(value = "fastProduct/auditFastProduct")
	public String auditFastProduct(HttpServletRequest request, String productId,
			String status, String reason, String otherReason) {

		Map<String, Object> map = new HashMap<String, Object>();
		String oldState = "";
		try {
			oldState = productToolService.getByProductId(productId).getState();
		} catch (Exception e) {
			logger.info("audit FastProduct With ex : {} ", e);
		}
		if (!"1".equals(oldState)){
			map.put("status", "0");
			map.put("message", "只能操作状态为待审核的数据");
			return JSONObject.toJSONString(map,	SerializerFeature.WriteDateUseDateFormat);
		}
		SysRegisterUser user = getUser(request);
		String dateTime = DateUtil.toString(new Date(),	DateUtil.DATE_FORMAT_DATETIME);

		AuditInfoDTO aid = new AuditInfoDTO();
		aid.setMainId(Long.valueOf(productId));
		aid.setReason(reason);
		aid.setOtherReason(otherReason);
		aid.setStatus(status);
		aid.setAuditTime_string(dateTime);
		aid.setMemberId(user.getUserID());
		aid.setMemberName(user.getUserName());
		aid.setCreateTime_string(dateTime);
		aid.setType("1");

		int result = 0;
		try {
			result = auditInfoToolService.addAuditInfoDTO(aid);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("productId", productId);
			if ("1".equals(status)) {
				param.put("state", ProductStatusEnum.ON.getkey());
			} else {
				param.put("state", ProductStatusEnum.REFUSE.getkey());
			}
			param.put("updateUserId", user.getUserID());
			param.put("updateTimeString", dateTime);
			productToolService.updateProductStatus(param);
			map.put("status", "1");
			map.put("message", result);
		} catch (Exception e) {
			logger.info("audit FastProduct With ex : {} ", e);
			map.put("status", "0");
			map.put("message", e.toString());
		}
		return JSONObject.toJSONString(map,	SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	/**
	 * 跳转选择市场页面
	 *
	 * @return
	 */
	@RequestMapping("fastProduct/toMarketSelect/{callback}")
	public String toMarketSelect(@PathVariable String callback, ModelMap map) {
		map.put("callback", callback);
		return "fastProduct/marketSelect";
	}
	
	@ResponseBody
	@RequestMapping("fastProduct/getListOfMarket")
	public String queryMarketIdByMemberId(String marketName) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<MarketDTO> marketList = null;
		try {
			// '0:产地供应商,1: 街市,2 :市场(农批中心),3:前台用户添加', 4:市场活动
			map.put("marketType", 4);
			// 0:启用, 1:停用, 2:已删除
			map.put("status", 0);
			// 市场名称
			map.put("marketName", marketName);
			map.put("startRow", 0);
			map.put("endRow", 100);
			marketList = marketManageService.getListOfMarket(map);
		} catch (Exception e) {
			logger.info("getListOfMarket With ex : {} ", e);
		}
		return JSONObject.toJSONString(marketList);
	}
	
	// TODO
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	

	@ResponseBody
	@RequestMapping("fastProduct/queryMarketByBusinessId")
	public String queryMarketByBusinessId(String businessId) {

		MarketDTO market = null;
		try {
			ReBusinessMarketDTO rbmDto=reBusinessMarketToolService.getByBusinessId(Long.valueOf(businessId));
			if (rbmDto != null ){
				market = marketManageService.getById(String.valueOf(rbmDto.getMarketId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(market);
	}

	@ResponseBody
	@RequestMapping("fastProduct/queryMarketInfoByMarketId")
	public String queryMarketInfoByMarketId(String marketId) {
		MarketDTO market = null;
		try {
			market = marketManageService.getById(marketId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(market);
	}

	@ResponseBody
	@RequestMapping("fastProduct/queryMemberList")
	public String query(HttpServletRequest request, String status, String accountOrPhone){
		try {
			String id = request.getParameter("id");

			Map<String, Object> map = new HashMap<String, Object>();
			if(null==id || id.equals("")){
			}else{
				map.put("id", id);
			}
			// 设置查询参数
			map.put("status", status);
			//账号/手机号
			map.put("accountOrPhone", accountOrPhone);
			//记录数
			map.put("total", memberBaseinfoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.getBySearch(map);
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

		}
		return null;
	}



	@ResponseBody
	@RequestMapping("fastProduct/productUpAndDown")
	public String updateProductStatus(String productId, String option, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			SysRegisterUser user = getUser(request);
			ProductDto productDto = productToolService.getByProductId(productId);

			if ("0".equals(option)){
				//下架
				if (!"3".equals(productDto.getState())){
					resultMap.put("status", 0);
					resultMap.put("message", "只能操作销售中的产品");
					return JSONObject.toJSONString(resultMap);
				}
				param.put("state", ProductStatusEnum.OFF.getkey());
			}else if ("1".equals(option)){
				//上架
				if (!"4".equals(productDto.getState())){
					resultMap.put("status", 0);
					resultMap.put("message", "只能操作已下架的产品");
					return JSONObject.toJSONString(resultMap);
				}
				param.put("state", ProductStatusEnum.ON.getkey());
				//更新有效期
				String expirationDateString = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME)
					.format(DateUtil.addDays(new Date(), Integer.valueOf(productDto.getInfoLifeDay())));
				param.put("expirationDateString", expirationDateString);
			}else if ("2".equals(option)){
				//删除
				param.put("state", ProductStatusEnum.DELETED.getkey());
			}

			param.put("productId", productId);
			param.put("updateUserId", user.getUserID());
			param.put("updateTimeString", DateUtil.getSysDateTimeString());
			int result = productToolService.updateProductStatus(param);
			resultMap.put("status", 1);
			resultMap.put("message", result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(resultMap);
	}
	

	

	@ResponseBody
	@RequestMapping("fastProduct/queryBusinessByMemberId/{memberId}")
	public String queryBusinessByMemberId(@PathVariable("memberId") String memberId) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<BusinessBaseinfoDTO> list = null;
		try {
			param.put("userId", memberId);
			param.put("startRow", 0);
			param.put("endRow", 100);
			list = businessBaseinfoToolService.getBySearch(param);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<BusinessBaseinfoDTO>();
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping(value="fastProduct/loadProductLabels/{hasDefault}",produces="application/json; charset=utf-8")
	public String loadProductLabels(@PathVariable("hasDefault")String hasDefault) {
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		try {
			if("1".equalsIgnoreCase(hasDefault)){
				KeyValuePair pair = new KeyValuePair();
				pair.setKeyString("请选择标签");
				pair.setValueString("-1");
				list.add(pair);
			}
//			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//			Resource resource = resolver.getResource(ResourceLoader.CLASSPATH_URL_PREFIX+"conf/product-labels.properties");
			Resource resource = applicationContext.getResource(ResourceLoader.CLASSPATH_URL_PREFIX +"conf/product-labels.properties");
			Properties properties = new Properties();
			properties.load(resource.getInputStream());
			for(Object key : properties.keySet()){
				KeyValuePair pair = new KeyValuePair();
				pair.setKeyString(String.valueOf(properties.get(key)));
				pair.setValueString(String.valueOf(properties.get(key)));
				list.add(pair);
			}
		} catch (Exception e) {
			logger.info("loadProductLabels with ex : ", e);
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("fastProduct/queryProvince")
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
	@RequestMapping("fastProduct/queryCity/{provinceId}")
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
	@RequestMapping("fastProduct/queryArea/{cityId}")
	public String queryArea(@PathVariable("cityId") String cityId) {
		List<AreaDTO> list = null;
		try {
			list = queryAreaService.findCounty(cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("fastProduct/getChildProductCategory/{parentId}")
	public String getChildProductCategory(
			@PathVariable("parentId") long parentId) {
		List<ProductCategoryDTO> list = null;
		try {
			list = productToolService.getChildProductCategory(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@RequestMapping("fastProduct/marketSelect")
	public String marketSelect() {
		return "fastProduct/marketSelectList";
	}

	@RequestMapping("fastProduct/toRefuse")
	public String toRefuse() {
		return "fastProduct/auditRefuse";
	}

	@RequestMapping("fastProduct/businessSelect")
	public String businessSelect() {
		return "fastProduct/businessSelectList";
	}

	@RequestMapping("fastProduct/memberSelect/{status}")
	public String memberSelect(@PathVariable("status") String status) {
		putModel("status", status);
		return "fastProduct/memberSelectList";
	}
	/**
	 * 根据name查询街市
	 *
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fastProduct/queryMarketByName")
	public String queryMarketByName(String marketName, String startDate,
			String endDate, HttpServletRequest request) {
		List<MarketDTO> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 设置查询参数
			map.put("marketName", marketName);
			// 记录数
			map.put("total", marketManageService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			list = marketManageService.getByName(map);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<MarketDTO>();
		}
		map.put("rows", list);// rows键 存放每页记录 list
		return JSONObject.toJSONString(map,
				SerializerFeature.WriteDateUseDateFormat);
	}

	@ResponseBody
	@RequestMapping(value = "fastProduct/queryMarketById")
	public String queryMarketById(String marketId, String startDate,
			String endDate, HttpServletRequest request) {
		List<MarketDTO> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 设置查询参数
			map.put("marketName", marketId);
			// 记录数
			map.put("total", marketManageService.getByCondition(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			list = marketManageService.getByName(map);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<MarketDTO>();
		}
		map.put("rows", list);// rows键 存放每页记录 list
		return JSONObject.toJSONString(map,
				SerializerFeature.WriteDateUseDateFormat);
	}


	@ResponseBody
	@RequestMapping("fastProduct/priceKeyValuePair")
	public String priceKeyValuePair() {

		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		KeyValuePair pair = new KeyValuePair();
		pair.setKeyString("单价");
		pair.setValueString("0");
		KeyValuePair pair2 = new KeyValuePair();
		pair2.setKeyString("区间价");
		pair2.setValueString("1");

		list.add(pair);
		list.add(pair2);

		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("fastProduct/unitKeyValuePair")
	public String unitKeyValuePair() {
		List<SystemCode> codes = null;
		try {
			codes = systemCodeToolService.getListViaType("ProductUnit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		if (codes != null && !codes.isEmpty()){
			for(SystemCode code : codes){
				KeyValuePair pair = new KeyValuePair();
				pair.setKeyString(code.getCodeValue());
				pair.setValueString(code.getCodeKey());
				list.add(pair);
			}
		}

		return JSONObject.toJSONString(list);
	}
	@ResponseBody
	@RequestMapping(value = "fastProduct/queryBusiness")
	public String queryBusiness(String startDate, String endDate,
			HttpServletRequest request) {

		List<BusinessBaseinfoDTO> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 设置查询参数
			// 记录数
			map.put("total", businessBaseinfoToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			list = businessBaseinfoToolService.getBySearch(map);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<BusinessBaseinfoDTO>();
		}
		map.put("rows", list);// rows键 存放每页记录 list
		return JSONObject.toJSONString(map,
				SerializerFeature.WriteDateUseDateFormat);
	}

	@ResponseBody
	@RequestMapping("fastProduct/queryBusinessById")
	public String queryBusinessById(String businessId) {
		BusinessBaseinfoDTO baseinfoDTO = null;
		try {
			baseinfoDTO = businessBaseinfoToolService.getById(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(baseinfoDTO);
	}

	@ResponseBody
	@RequestMapping("fastProduct/listTopProductCategory/{marketId}")
	public String listTopProductCategory(@PathVariable("marketId")String marketId) {
		List<ProductCategoryDTO> list = null;
		try {
			list = productToolService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@RequestMapping("fastProduct/toEditProduct/{productId}")
	public String toEditProduct(ModelMap map, @PathVariable("productId") String productId) {
		try {
			//产品查询参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", productId);
			params.put("productType", ProductTypeEnum.FAST_PRODUCT.getKey());
			// 原产品信息
			ProductDto productDto = productToolService.getOneProduct(params);
			// 区间价列表
			if ("1".equals(productDto.getPriceType())) {
				productDto.setPriceDtoList(productToolService.getProductPriceList(productId));
			}
			map.put("product", productDto);

			// 获取分类所在市场
			ProductCategoryDTO category = proCategoryService.getProductCategoryById(productDto.getCateId());
			map.put("cmarketId", category.getMarketId());
			MarketDTO market = marketManageService.getById(category.getMarketId().toString());
			map.put("marketName", market.getMarketName());
			
			// 查询产品分类id的所有父辈祖辈分类
			List<ProductCategoryDTO> categoryAncestors =  productToolService.getCategoryAncestors(productDto.getCateId());
			map.put("categorys", categoryAncestors);

/*			//会员+商铺
			BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getById(String.valueOf(productDto.getBusinessId()));
			map.put("business", businessBaseinfoDTO);*/

/*			if ("4".equals(productDto.getRoleType())){
				//产地供应商 对应市场固定为3
				map.put("marketId", 3);
				MarketDTO market = marketManageService.getById("3");
				map.put("marketName", market.getMarketName());
			}else{
				//农批中心
				Map<String, Object> params = new HashMap<String, Object>();
				MarketDTO market = null;
				params.put("businessId", businessBaseinfoDTO.getBusinessId());
				params.put("startRow", 0);
				params.put("endRow", 100);
				
//				params.put("memberId", businessBaseinfoDTO.getUserId());
//				List<ReMemberMarketDTO> relations = reMemberMarkeToolService.getBySearch(params);
				
				// 商铺-市场 关系
				List<ReBusinessMarketDTO> relations = reBusinessMarketToolService.getBySearch(params);
				if (relations != null && !relations.isEmpty()){
					market = marketManageService.getById(String.valueOf(relations.get(0).getMarketId()));
					map.put("marketId", market.getId());
					map.put("marketName", market.getMarketName());
				}
			}*/

			//查询图片
			List<ProductPictureDto> list = productToolService.getPictureByProductId(productId);
			boolean first = true;	String types = "";
			List<Map<String, String>> multiplePictureList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> appPicture = new ArrayList<Map<String, String>>();
			for (ProductPictureDto dto : list) {
				if (first) {
					first = false;
					types = String.valueOf(dto.getPictureType());
				} else {
					types = types + ","	+ String.valueOf(dto.getPictureType());
				}
				if (1 == dto.getPictureType().intValue()){
					map.put("masterPicture", dto.getUrlOrg());
				}else if (4 == dto.getPictureType().intValue()){
					Map<String, String> pair = new HashMap<String, String>();
					//pair.put("dataOriginalUrl", dto.getUrlOrg());
					pair.put("url", dto.getUrlOrg());
					appPicture.add(pair);
				}else if (3 == dto.getPictureType().intValue()){

				}else if (2 == dto.getPictureType().intValue()){
					//多角度
					Map<String, String> pair = new HashMap<String, String>();
					pair.put("dataOriginalUrl", dto.getUrlOrg());
					pair.put("url", dto.getUrlOrg());
					multiplePictureList.add(pair);
				}
			}

			//多角度
			map.put("multiplePicture", JSONObject.toJSONString(multiplePictureList));
			map.put("multiplePictureList", multiplePictureList);
			// App
			map.put("appPicture", JSONObject.toJSONString(appPicture));
			// 图片类型???
			map.put("types", types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fastProduct/editProduct";
	}

	@RequestMapping("fastProduct/toUpload/{productId}")
	public String toUpload(@PathVariable("productId") String productId,
			ModelMap map) {
		map.put("productId", productId);
		try {
			List<ProductPictureDto> list = productToolService
					.getPictureByProductId(productId);
			boolean first = true;
			String types = "";
			for (ProductPictureDto dto : list) {
				if (first) {
					first = false;
					types = String.valueOf(dto.getPictureType());
				} else {
					types = types + "," + String.valueOf(dto.getPictureType());
				}
			}
			map.put("types", types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fastProduct/uploadProductPic";
	}

	@RequestMapping("fastProduct/reAudit/{productId}")
	public String reAudit(@PathVariable("productId") String productId,
			ModelMap map) {
		try {
			ProductDto productDto = productToolService
					.getByProductId(productId);
			// 区间价格
			if ("1".equals(productDto.getPriceType())) {
				List<ProductPriceDto> mutiplePrices = productToolService
						.getLadderPriceByProductId(productId);
				map.put("mutiplePrices", mutiplePrices);
			}
			map.put("product", productDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fastProduct/productDetail";
	}



	private ProductPriceDto[] checkAndSort(ProductPriceDto[] priceArray){
		for(ProductPriceDto item : priceArray){
			if (item.getPrice() == null){
				item.setPrice(Double.valueOf(0));
			}
		}
		Arrays.sort(priceArray);
		return priceArray;
	}
	private void savePicture(String userId, String picturePath, int pictureType, Long productId) throws Exception{

		 ProductPictureEntity pictureDto = new ProductPictureEntity();
		 pictureDto.setProductId(productId);
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




	/**
	 * 跳转商品列表页
	 *
	 * @return
	 */
	@RequestMapping("fastProduct/adProduct")
	public String toProductList(HttpServletRequest request) {
		return "fastProduct/adProductList";
	}

	

	/**
	 * 分页查询产品列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/adProductList")
	public String adProductList(HttpServletRequest request, ProductParamsBean params) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(request.getParameter("adName"))) {
				map.put("adName", request.getParameter("adName"));
			}
			if (StringUtil.isNotEmpty(params.getStartDate())) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(params.getStartDate())));
			}
			if (StringUtil.isNotEmpty(params.getEndDate())) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(params.getEndDate())));
			}
			if (StringUtil.isNotEmpty(params.getProductName())) {
				map.put("productName", params.getProductName());
			}
			if (StringUtil.isNotEmpty(params.getAccount())) {
				map.put("account", params.getAccount());
			}
			if (StringUtil.isNotEmpty(params.getCategoryId())) {
				map.put("categoryId", params.getCategoryId());
			}
			if (StringUtil.isNotEmpty(params.getCreateUserName())) {
				map.put("userCode", params.getCreateUserName());
			}

			if (StringUtil.isNotEmpty(params.getMarketId())) {
				map.put("marketId", params.getMarketId());
			}
			if (StringUtil.isNotEmpty(params.getState())&&!"0".equals(params.getState())) {
				if("1".equals(params.getState())){
					map.put("oldState", "01");
				}
				if("3".equals(params.getState())){
					map.put("oldState", "02");
				}
				if("4".equals(params.getState())){
					map.put("oldState", "03");
				}
				map.put("state", params.getState());
			}
			if ("1".equals(params.getVendor())){
				map.put("roleType", "4");
			}
			if (StringUtil.isNotEmpty(params.getOriginProvinceId())) {
				map.put("originProvinceId", params.getOriginProvinceId());
			}
			if (StringUtil.isNotEmpty(params.getOriginCityId())) {
				map.put("originCityId", params.getOriginCityId());
			}
			if (StringUtil.isNotEmpty(params.getOriginAreaId())) {
				map.put("originAreaId", params.getOriginAreaId());
			}
			if (StringUtil.isNotEmpty(params.getProductLabel()) && !"-1".equalsIgnoreCase(params.getProductLabel())) {
				map.put("productLabel", params.getProductLabel());
			}
			// 记录数
			map.put("total", productToolService.getAdProductCount(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<ProductDto> list = productToolService.getAdProduct(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	





	/**
	 * 批量上架
	 *
	 * @param request
	 * @param productId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/batchUpdateProductStatus")
	public String batchUpdateProductStatus(String productIds,HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		String[] ids = productIds.split(",");
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		int[] result = null;
		try {
			List<ProductDto> productList = productToolService.getProductsByIds(ids);
			for (ProductDto current : productList){
				if (!"4".equals(current.getState())){
					list.add(String.valueOf(current.getProductId()));
				}else {
					//有效期
					String expirationDateString = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME)
					.format(DateUtil.addDays(new Date(), Integer.valueOf(current.getInfoLifeDay())));
					SysRegisterUser user = getUser(request);

					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("productId", current.getProductId());
					paramsMap.put("updateUserId", user.getUserID());
					paramsMap.put("updateTimeString", DateUtil.getSysDateTimeString());
					paramsMap.put("state", ProductStatusEnum.ON.getkey());
					paramsMap.put("expirationDateString", expirationDateString);
					paramList.add(paramsMap);
				}
			}
			if (!list.isEmpty()){
				resultMap.put("status", 0);
				resultMap.put("message", "存在非下架状态的产品, 不能进行批量上架");
				return JSONArray.toJSONString(resultMap);
			}
			result = productToolService.batchUpdateProductStatus(paramList);
			resultMap.put("status", 1);
			resultMap.put("message", result);
		} catch (Exception e) {
			resultMap.put("status", 0);
			resultMap.put("message", "批量上架异常");
			e.printStackTrace();
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	
	/**
	 * 批量更新
	 *
	 * @param request
	 * @param productId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/batchUpdateProductUpdateTime")
	public String batchUpdateProductUpdateTime(String productIds,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] ids = productIds.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		String result=null;
		try {
			int i=0;
			for(String id:ids){
				map.put("productId", id);
			int re =productToolService.updateProductUpdateTime(map);
				map.clear();
			if(re==0){
				i++;
			}	
			}
			resultMap.put("status", 1);
			if(i>0&&i<ids.length){
				result="部分成功";
			}else if(i==ids.length){
				resultMap.put("status", 0);
				result="失败";
			}else{
				result="批量修改成功";
			}
			resultMap.put("message", result);
		} catch (Exception e) {
			resultMap.put("status", 0);
			resultMap.put("message", "批量修改异常");
			e.printStackTrace();
		}
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 批量刷新产品过期时间
	 *
	 * @param request
	 * @param productId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/batchRefreshExpire")
	public String batchRefreshExpire(String productIds, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String updateTime = DateUtil.getSysDateTimeString();
		int[] result = null;
		try {
			String[] ids = productIds.split(",");
			SysRegisterUser user = getUser(request);
			Date updateDate = DateUtil.parseDate(updateTime, DateUtil.DATE_FORMAT_DATETIME);
			List<ProductDto> productList = productToolService.getProductsByIds(ids);
			for (ProductDto current : productList){
				String expirationDateString = DateUtil.toString(DateUtil.addDays(updateDate,
						Integer.valueOf(current.getInfoLifeDay())), DateUtil.DATE_FORMAT_DATETIME);
				current.setUpdateUserId(user.getUserID());
				current.setUpdateTimeString(updateTime);
				current.setExpirationDateString(expirationDateString);
			}
			result = productToolService.batchUpdateProduct(productList);
			resultMap.put("status", 1);
			resultMap.put("message", result);
		} catch (Exception e) {
			resultMap.put("status", 0);
			resultMap.put("message", "批量刷新有效期异常");
			e.printStackTrace();
		}
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 批量更新产品标签
	 * @param productIds
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/batchUpdatePlabels")
	public String batchUpdatePlabels(String productIds, String label, String option, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String updateTime = DateUtil.getSysDateTimeString();
		int[] result = null;
		try {
			//产品id集合
			String[] ids = StringUtils.commaDelimitedListToStringArray(productIds);
			if(CommonUtil.isEmpty(ids)){
				resultMap.put("status", 0);
				resultMap.put("message", "产品id为空");
				return JSONArray.toJSONString(resultMap);
			}
			if("1".equalsIgnoreCase(option) && CommonUtil.isEmpty(label)){
				resultMap.put("status", 0);
				resultMap.put("message", "产品标签为空, 不能添加");
				return JSONArray.toJSONString(resultMap);
			}
			//用户
			SysRegisterUser user = getUser(request);
			//查询产品集合
			List<ProductDto> productList = productToolService.getProductsByIds(ids);
			for (ProductDto current : productList){
				current.setUpdateUserId(user.getUserID());
				current.setUpdateTimeString(updateTime);
				//新增
				if("1".equalsIgnoreCase(option)){
					Set<String> labels = StringUtils.commaDelimitedListToSet(current.getProductSign());
					//如果没有包含当前标签,则添加当前标签
					if (!labels.contains(label)){
						labels.add(label);
						current.setProductSign(StringUtils.collectionToCommaDelimitedString(labels));
					}
				}else{//清除
					current.setProductSign(label);
				}
			}
			result = productToolService.batchUpdateProduct(productList);
			resultMap.put("status", 1);
			resultMap.put("message", result);
		} catch (Exception e) {
			resultMap.put("status", 0);
			resultMap.put("message", "批量刷新有效期异常");
			logger.info("batchUpdatePlabels with ex : ", e);
		}
		return JSONArray.toJSONString(resultMap);
	}
	/**
	 * 批量删除产品记录
	 *
	 * @param request
	 * @param productId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("fastProduct/batchDelete")
	public String batchDeleteProduct(HttpServletRequest request, String productIds, ModelMap map) {

		String[] ids = productIds.split(",");
		int[] result = null;
		try {
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			// 快速制单商品
			List<ProductDto> productList = productToolService.getFastPListByIds(ids);
			for (ProductDto current : productList){
				SysRegisterUser user = getUser(request);
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("productId", current.getProductId());
				paramsMap.put("updateUserId", user.getUserID());
				paramsMap.put("updateTimeString", DateUtil.getSysDateTimeString());
				paramsMap.put("state", ProductStatusEnum.DELETED.getkey());
				paramList.add(paramsMap);
			}
//			result = productToolService.batchDeleteProduct(ids);
			result = productToolService.batchUpdateProductStatus(paramList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONArray.toJSONString(result);
	}

	@ResponseBody
	@RequestMapping("fastProduct/uploadProductPic")
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




	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fastProduct/checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = params.getStartDate();
			String endDate = params.getEndDate();
/*			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}*/
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(startDate)));
			}
			if (StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(endDate)));
			}
			if (StringUtil.isNotEmpty(params.getProductName())) {
				map.put("productName", params.getProductName());
			}
			if (StringUtil.isNotEmpty(params.getCategoryId())) {
				map.put("categoryId", params.getCategoryId());
			}
			if (StringUtil.isNotEmpty(params.getCreateUserName())) {
				map.put("userCode", params.getCreateUserName());
			}
			//快单产品
			map.put("productType", ProductTypeEnum.FAST_PRODUCT.getKey());
			
			int total = productToolService.getCountForFast(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("product checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}
	
	
	
	
	

	/**
	 * 导出产品查询结果
	 * 注意 : 如果修改查询条件, 需要同步修改fastProduct/checkExportParams接口
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "fastProduct/exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
		
		// Step1 : 查询待导出数据
		List<ProductDto> list = null ;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(params.getStartDate())) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(params.getStartDate())));
			}
			if (StringUtil.isNotEmpty(params.getEndDate())) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(params.getEndDate())));
			}
			if (StringUtil.isNotEmpty(params.getProductName())) {
				map.put("productName", params.getProductName());
			}
			if (StringUtil.isNotEmpty(params.getCategoryId())) {
				map.put("categoryId", params.getCategoryId());
			}
			if (StringUtil.isNotEmpty(params.getCreateUserName())) {
				map.put("userCode", params.getCreateUserName());
			}
			//快单产品
			map.put("productType", ProductTypeEnum.FAST_PRODUCT.getKey());
			
			// 记录数
			map.put("total", productToolService.getCountForFast(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			list = productToolService.getFastPListForALl(map);

		}catch(Exception e){
			logger.info("fastProduct exportData with ex : {} ", new Object[]{e});
			return ;
		}

		// Step2 : 执行导出阶段	
		OutputStream ouputStream = null;
		try {
			
			ouputStream = response.getOutputStream();
			// 设置响应头等信息
			ExcelUtils.preProcessResponse(response, "产品列表");
			// 填充数据并导出
			ExcelUtils.export("产品列表", ouputStream, list, new FastProductBeanToSheetCallBack());
			
		} catch (Exception e) {
			logger.info("exportProductData with ex : {} ", new Object[]{e});
		} finally {
			try {
				ouputStream.close();
			} catch (IOException e) {
				logger.info("finally exportProductData with ex : {} ", new Object[]{e});
			}
		}
		
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fastProduct/adProductCheckExportParams", produces="application/json; charset=utf-8")
	public String adProductCheckExportParams(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = params.getStartDate();
			String endDate = params.getEndDate();
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(request.getParameter("adName"))) {
				map.put("adName", request.getParameter("adName"));
			}
			if (StringUtil.isNotEmpty(params.getStartDate())) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(params.getStartDate())));
			}
			if (StringUtil.isNotEmpty(params.getEndDate())) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(params.getEndDate())));
			}
			if (StringUtil.isNotEmpty(params.getProductName())) {
				map.put("productName", params.getProductName());
			}
			if (StringUtil.isNotEmpty(params.getCategoryId())) {
				map.put("categoryId", params.getCategoryId());
			}

			if (StringUtil.isNotEmpty(params.getMarketId())) {
				map.put("marketId", params.getMarketId());
			}
			if (StringUtil.isNotEmpty(params.getState())&&!"0".equals(params.getState())) {
				if("1".equals(params.getState())){
					map.put("oldState", "01");
				}
				if("3".equals(params.getState())){
					map.put("oldState", "02");
				}
				if("4".equals(params.getState())){
					map.put("oldState", "03");
				}
				map.put("state", params.getState());
			}
			if ("1".equals(params.getVendor())){
				map.put("roleType", "4");
			}
			int total = productToolService.getAdProductCount(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("product checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
