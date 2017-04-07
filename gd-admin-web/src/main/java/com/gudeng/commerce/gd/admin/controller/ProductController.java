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
import com.gudeng.commerce.gd.admin.entity.ProductStatusEnum;
import com.gudeng.commerce.gd.admin.entity.ProductTypeEnum;
import com.gudeng.commerce.gd.admin.service.AuditInfoToolService;
import com.gudeng.commerce.gd.admin.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.GdOrderActivityBaseToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
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
import com.gudeng.commerce.gd.admin.util.excel.cb.ProductBeanToSheetCallBack;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
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

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
public class ProductController extends AdminBaseController implements ApplicationContextAware {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(ProductController.class);

	private ApplicationContext applicationContext;

	@Autowired
	public ProductToolService productToolService;

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
	
	@Autowired
	private GdOrderActivityBaseToolService gdOrderActivityBaseToolService;

	@ResponseBody
	@RequestMapping("product/queryMarketByMemberId")
	public String queryMarketIdByMemberId(String memberId) {

		Map<String, Object> map = new HashMap<String, Object>();
		MarketDTO market = null;
		try {
			map.put("memberId", memberId);
			map.put("startRow", 0);
			map.put("endRow", 100);
			List<ReMemberMarketDTO> list = reMemberMarkeToolService.getBySearch(map);
			if (list != null && !list.isEmpty()){
				market = marketManageService.getById(String.valueOf(list.get(0).getMarketId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(market);
	}

	@ResponseBody
	@RequestMapping("product/queryMarketByBusinessId")
	public String queryMarketByBusinessId(String businessId) {

		MarketDTO market = null;
		try {
//			map.put("businessId", businessId);
//			map.put("startRow", 0);
//			map.put("endRow", 100);
//			List<ReBusinessMarketDTO> list = reBusinessMarketToolService.getBySearch(map);
//			if (list != null && !list.isEmpty()){
//				market = marketManageService.getById(String.valueOf(list.get(0).getMarketId()));
//			}
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
	@RequestMapping("product/queryMarketInfoByMarketId")
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
	@RequestMapping("product/queryMemberList")
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

	@RequestMapping("product/toAdd")
	public String toAdd(ModelMap map) {
		try {
			// 商品类目
//			List<ProductCategoryDTO> firstList = productToolService
//					.listTopProductCategory();
//			List<ProductCategoryDTO> secondList = productToolService
//					.getChildProductCategory(firstList.get(0).getCategoryId());
//			List<ProductCategoryDTO> thirdList = productToolService
//					.getChildProductCategory(secondList.get(0).getCategoryId());
//			map.put("firstCategorys", firstList);
//			map.put("secondCategorys", secondList);
//			map.put("thirdCategorys", thirdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product/addProduct";
	}

	@ResponseBody
	@RequestMapping("product/productUpAndDown")
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
	@RequestMapping("product/queryBusinessByMemberId/{memberId}")
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
	@RequestMapping(value="product/loadProductLabels/{hasDefault}",produces="application/json; charset=utf-8")
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

	@ResponseBody
	@RequestMapping("product/getChildProductCategory/{parentId}")
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

	@RequestMapping("product/marketSelect")
	public String marketSelect() {
		return "product/marketSelectList";
	}

	@RequestMapping("product/toRefuse")
	public String toRefuse() {
		return "product/auditRefuse";
	}

	@RequestMapping("product/businessSelect")
	public String businessSelect() {
		return "product/businessSelectList";
	}

	@RequestMapping("product/memberSelect/{status}")
	public String memberSelect(@PathVariable("status") String status) {
		putModel("status", status);
		return "product/memberSelectList";
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
	@RequestMapping(value = "product/queryMarketByName")
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
	@RequestMapping(value = "product/queryMarketById")
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
	@RequestMapping("product/priceKeyValuePair")
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
	@RequestMapping("product/unitKeyValuePair")
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


/*		KeyValuePair pair2 = new KeyValuePair();
		pair2.setKeyString("千克");
		pair2.setValueString("2");
		list.add(pair2);

		KeyValuePair pair3 = new KeyValuePair();
		pair3.setKeyString("公斤");
		pair3.setValueString("3");
		list.add(pair3);

		KeyValuePair pair4 = new KeyValuePair();
		pair4.setKeyString("克");
		pair4.setValueString("4");
		list.add(pair4);

		KeyValuePair pair5 = new KeyValuePair();
		pair5.setKeyString("支");
		pair5.setValueString("5");
		list.add(pair5);

		KeyValuePair pair6 = new KeyValuePair();
		pair6.setKeyString("包");
		pair6.setValueString("6");
		list.add(pair6);

		KeyValuePair pair7 = new KeyValuePair();
		pair7.setKeyString("箱");
		pair7.setValueString("7");
		list.add(pair7);

		KeyValuePair pair8 = new KeyValuePair();
		pair8.setKeyString("升");
		pair8.setValueString("8");
		list.add(pair8);

		KeyValuePair pair9 = new KeyValuePair();
		pair9.setKeyString("袋");
		pair9.setValueString("9");
		list.add(pair9);

		KeyValuePair pair10 = new KeyValuePair();
		pair10.setKeyString("条");
		pair10.setValueString("10");
		list.add(pair10);

		KeyValuePair pair11 = new KeyValuePair();
		pair11.setKeyString("尾");
		pair11.setValueString("11");
		list.add(pair11);

		KeyValuePair pair12 = new KeyValuePair();
		pair12.setKeyString("头");
		pair12.setValueString("12");
		list.add(pair12);

		KeyValuePair pair13 = new KeyValuePair();
		pair13.setKeyString("盒");
		pair13.setValueString("13");
		list.add(pair13);

		KeyValuePair pair14 = new KeyValuePair();
		pair14.setKeyString("枚");
		pair14.setValueString("14");
		list.add(pair14);

		KeyValuePair pair15 = new KeyValuePair();
		pair15.setKeyString("组");
		pair15.setValueString("15");
		list.add(pair15);

		KeyValuePair pair16 = new KeyValuePair();
		pair16.setKeyString("件");
		pair16.setValueString("16");
		list.add(pair16);

		KeyValuePair pair17 = new KeyValuePair();
		pair17.setKeyString("只");
		pair17.setValueString("17");
		list.add(pair17);

		KeyValuePair pair18 = new KeyValuePair();
		pair18.setKeyString("瓶");
		pair18.setValueString("18");
		list.add(pair18);

		KeyValuePair pair19 = new KeyValuePair();
		pair19.setKeyString("罐");
		pair19.setValueString("19");
		list.add(pair19);

		KeyValuePair pair20 = new KeyValuePair();
		pair20.setKeyString("桶");
		pair20.setValueString("20");
		list.add(pair20);
		KeyValuePair pair21 = new KeyValuePair();
		pair21.setKeyString("份");
		pair21.setValueString("21");
		list.add(pair21);*/

		return JSONObject.toJSONString(list);
	}
	@ResponseBody
	@RequestMapping(value = "product/queryBusiness")
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
	@RequestMapping("product/queryBusinessById")
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
	@RequestMapping("product/listTopProductCategory/{marketId}")
	public String listTopProductCategory(@PathVariable("marketId")String marketId) {
		List<ProductCategoryDTO> list = null;
		try {
			list = productToolService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@RequestMapping("product/toEdit/{productId}")
	public String toEdit(@PathVariable("productId") String productId,
			ModelMap map) {
		try {
			//产品
			ProductDto productDto = productToolService.getByProductId(productId);
			map.put("product", productDto);

			List<ProductCategoryDTO> categoryAncestors =  productToolService.getCategoryAncestors(productDto.getCateId());
			map.put("categorys", categoryAncestors);
//			map.put("grandParentCategoryId", categoryAncestors.get(0).getCategoryId());
//			map.put("parentCategoryId", categoryAncestors.get(1).getCategoryId());

/*			//省市区
			if (productDto.getProvinceId() != null){
				map.put("provinceName", queryAreaService.getArea(String.valueOf(productDto.getProvinceId())).getArea());
			}
			if (productDto.getCityId() != null){
				map.put("cityName", queryAreaService.getArea(String.valueOf(productDto.getCityId())).getArea());
			}
			if (productDto.getAreaId() != null){
				map.put("areaName", queryAreaService.getArea(String.valueOf(productDto.getAreaId())).getArea());
			}*/

			//会员+商铺
			BusinessBaseinfoDTO businessBaseinfoDTO =
					businessBaseinfoToolService.getById(String.valueOf(productDto.getBusinessId()));
			map.put("business", businessBaseinfoDTO);

			if ("4".equals(productDto.getRoleType())){
				//产地供应商固定为3
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
//			params.put("memberId", businessBaseinfoDTO.getUserId());

//			List<ReMemberMarketDTO> relations = reMemberMarkeToolService.getBySearch(params);
				List<ReBusinessMarketDTO> relations = reBusinessMarketToolService.getBySearch(params);

				if (relations != null && !relations.isEmpty()){
					market = marketManageService.getById(String.valueOf(relations.get(0).getMarketId()));
					map.put("marketId", market.getId());
					map.put("marketName", market.getMarketName());
				}
			}

			//查询图片
			List<ProductPictureDto> list = productToolService.getPictureByProductId(productId);
			boolean first = true;	String types = "";
			List<Map<String, String>> multiplePictureList = new ArrayList<Map<String, String>>();
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
					List<Map<String, String>> appPicture = new ArrayList<Map<String, String>>();
					Map<String, String> pair = new HashMap<String, String>();
					//pair.put("dataOriginalUrl", dto.getUrlOrg());
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
			map.put("types", types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product/editProduct";
	}

	@RequestMapping("product/toUpload/{productId}")
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
		return "product/uploadProductPic";
	}

	@RequestMapping("product/reAudit/{productId}")
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
		return "product/productDetail";
	}

	@RequestMapping("product/toDetail/{productId}")
	public String toDetail(@PathVariable("productId") String productId,	HttpServletRequest request,ModelMap map) {

		try {
			ProductDto productDto = productToolService.getByProductId(productId);
			// 区间价格
			if ("1".equals(productDto.getPriceType())) {
				List<ProductPriceDto> mutiplePrices = productToolService.getLadderPriceByProductId(productId);
				map.put("mutiplePrices", mutiplePrices);
			}
			if (ProductStatusEnum.REFUSE.getkey().equals(productDto.getState())) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("mainId", productId);
				param.put("type", "1");
				param.put("status", "0");
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
				param.put("status", "1");
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

			//省市区
/*			if (productDto.getProvinceId() != null){
				map.put("provinceName", queryAreaService.getArea(String.valueOf(productDto.getProvinceId())).getArea());
			}
			if (productDto.getCityId() != null){
				map.put("cityName", queryAreaService.getArea(String.valueOf(productDto.getCityId())).getArea());
			}
			if (productDto.getAreaId() != null){
				map.put("areaName", queryAreaService.getArea(String.valueOf(productDto.getAreaId())).getArea());
			}*/
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
			//单位
			List<SystemCode> units = systemCodeToolService.getListViaType("ProductUnit");
			map.put("units", units);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果从活动管理页面，进入商品详情，则返回别一个JPS地址
		String act = request.getParameter("source");
		if(act != null && act.equals("act")){
			return "active/productDetail";
		}
		return "product/productDetail";
	}

	@ResponseBody
	@RequestMapping("product/add")
	public String add(ProductParamsBean params, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		ProductEntity product = new ProductEntity();
		String productName = params.getProductName();
		try {

			SysRegisterUser user = getUser(request);

			product.setProductName(productName);
			product.setBusinessId(Long.valueOf(params.getBusinessId()));
			product.setCateId(Long.valueOf(params.getCategoryId()));
			//product.setMarketId(Long.valueOf(params.getMarketId()));

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

			product.setRoleType(params.getRoleType());
/*			product.setProvinceId(Long.valueOf(params.getProvinceId()));
			product.setCityId(Long.valueOf(params.getCityId()));
			product.setAreaId(Long.valueOf(params.getAreaId()));
			product.setAddress(params.getAddress());*/
			//后台人员添加的商品无需审核, 直接通过, 状态设为3(审核通过)
			product.setState("3");

			product.setInfoLifeDay(params.getInfoLifeDay());
			product.setDescription(params.getDescription());
			product.setChannel(params.getChannel());
			product.setCreateUserId(user.getUserID());
			product.setProductType(ProductTypeEnum.Common.getKey());
			product.setOriginProvinceId(params.getOriginProvinceId());
//			product.setOriginCityId(params.getOriginCityId());
//			product.setOriginAreaId(params.getOriginAreaId());
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

			Date now = new Date();
			product.setCreateTime(now);
			product.setExpirationDate(DateUtil.addDays(now, Integer.valueOf(params.getInfoLifeDay())));
			product.setUpdateTime(now);
			product.setUpdateUserId(user.getUserID());

			// 插入多价格区间列表
			product.setPriceDtoList(params.getPriceDtoList());// 价格区间集合
			long productId = productToolService.persistProductViaEntity(product);
			//插入图片
//			savePicture(user.getUserID(), params.getMasterPicture(), 1, productId);
			String[] multiplePictures = params.getMultiplePicture().split(",");
			boolean first = true;
			for(String item : multiplePictures){
				if (first){
					first = false;
					//保存第一张多角度图为主图
					savePicture(user.getUserID(), item, 1, productId);
				}
				savePicture(user.getUserID(), item, 2, productId);
			}
			savePicture(user.getUserID(), params.getAppPicture(), 4, productId);

			//添加审核信息-审核通过 start.......
			AuditInfoDTO aid = new AuditInfoDTO();
			aid.setMainId(Long.valueOf(productId));
			aid.setStatus("1");
			String dateTime = DateUtil.toString(new Date(),
					DateUtil.DATE_FORMAT_DATETIME);
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

	@ResponseBody
	@RequestMapping("product/edit")
	public String edit(ProductParamsBean params, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ProductDto productDto = new ProductDto();
			Map<String, Object> paramsMap = new HashMap<String, Object>();

			SysRegisterUser user = getUser(request);

			ProductDto productDto_old = productToolService.getByProductId(params.getProductId());

			Long productId = Long.valueOf(params.getProductId());
			
			productDto.setProductId(productId);
			productDto.setProductName(params.getProductName());
			productDto.setBusinessId(Long.valueOf(params.getBusinessId()));
			productDto.setCateId(Long.valueOf(params.getCategoryId()));
			productDto.setUnit(params.getUnit());
			productDto.setStockCount(Double.valueOf(params.getStockCount()));
			productDto.setInfoLifeDay(params.getInfoLifeDay());
			productDto.setDescription(params.getDescription());
			productDto.setChannel(params.getChannel());
			// 产地-省
			productDto.setOriginProvinceId(params.getOriginProvinceId());
			//产地-市
			if (CommonUtil.isEmpty(params.getOriginCityId())){
				productDto.setOriginCityId("0");
			}else {
				productDto.setOriginCityId(params.getOriginCityId());
			}
			//产地-区/县
			if (CommonUtil.isEmpty(params.getOriginAreaId())){
				productDto.setOriginAreaId("0");
			}else {
				productDto.setOriginAreaId(params.getOriginAreaId());
			}

			paramsMap.put("productId", productId);
			paramsMap.put("productName", params.getProductName());
			paramsMap.put("cateId", Long.valueOf(params.getCategoryId()));
			paramsMap.put("unit", params.getUnit());
			paramsMap.put("stockCount", Double.valueOf(params.getStockCount()));
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
			
			productDto.setUpdateUserId(user.getUserID());
			productDto.setUpdateTimeString(updateTime);
			//只要点击的编辑页面的确定按钮就更新过期时间(不管产品信息是否有任何变更)
			productDto.setExpirationDateString(expirationDateString);
			
			paramsMap.put("updateUserId", user.getUserID());
			paramsMap.put("updateTimeString", updateTime);
			paramsMap.put("expirationDateString", expirationDateString);

			
			//productDto.setPriceType(params.getPriceType());
			paramsMap.put("priceType", params.getPriceType());
			//区间价
			List<ProductPriceDto> priceList = params.getPriceDtoList();
			if ("1".equals(params.getPriceType()) && !CommonUtil.isEmpty(priceList)){

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
						if (productPriceDto.getPrice() == null || productPriceDto.getPrice().doubleValue() < 0){
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

			paramsMap.put("allowBlankUnit", 1);
			int result = productToolService.updateProduct(paramsMap);

			if (ProductStatusEnum.REFUSE.getkey().equals(productDto_old.getState())){
				Map<String, Object> param = new HashMap<>();
				param.put("productId", productId);
				param.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
				productToolService.updateProductStatus(param);
			}

			//更新图片
			//多角度图, 主图
			String mutiple = params.getMultiplePicture();
			if ( mutiple != null && !"".equals(mutiple)){
				String[] multiplePictures = mutiple.split(",");
				//删除主图
				productToolService.deletePicByProductIdAndType(String.valueOf(productId), "1");
				//删除多角度图
				productToolService.deletePicByProductIdAndType(String.valueOf(productId), "2");
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
			if ( app != null && !"".equals(app)){
				//删除原App图
				productToolService.deletePicByProductIdAndType(String.valueOf(productId), "4");
				//新增App图
				savePicture(user.getUserID(), params.getAppPicture(), 4, productId);
			}

			map.put("status", 1);
			map.put("message", result);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "edit product failed!!");
		}
		return JSONObject.toJSONString(map);
	}

/*	private int updatePicture(String userId, String picturePath, int pictureType, Long productId) throws Exception {

		ProductPictureDto pictureDto = new ProductPictureDto();
		pictureDto.setProductId(productId);
		pictureDto.setPictureType(pictureType);
		pictureDto.setUrlOrg(picturePath);
		pictureDto.setUrl650(CommonUtil.generatePictureName(picturePath, 650));
		pictureDto.setUrl400(CommonUtil.generatePictureName(picturePath, 370));//400
		pictureDto.setUrl170(CommonUtil.generatePictureName(picturePath, 200));//170
		pictureDto.setUrl120(CommonUtil.generatePictureName(picturePath, 160));//120
		pictureDto.setUrl60(CommonUtil.generatePictureName(picturePath, 150));//60

		String dateTime = DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME);
		pictureDto.setUpdateTimeString(dateTime);
		pictureDto.setUpdateUserId(userId);

		return productToolService.updateProductPic(pictureDto);
	}*/

	/**
	 * 跳转商品列表页
	 *
	 * @return
	 */
	@RequestMapping("product/adProduct")
	public String toProductList(HttpServletRequest request) {
		return "product/adProductList";
	}

	
	/**
	 * 跳转商品列表页
	 *
	 * @return
	 */
	@RequestMapping("product/jumpToList/{state}")
	public String toList(HttpServletRequest request, @PathVariable("state") String state) {
		putModel("state", state);
		return "product/productList";
	}
	/**
	 * 分页查询产品列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("product/adProductList")
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
	 * 分页查询产品列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("product/list")
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
			if (StringUtil.isNotEmpty(params.getAccount())) {
				map.put("account", params.getAccount());
			}
			if (StringUtil.isNotEmpty(params.getCategoryId())) {
				map.put("categoryId", params.getCategoryId());
			}
			if (StringUtil.isNotEmpty(params.getCreateUserName())) {
				map.put("userCode", params.getCreateUserName());
			}
			//账号/手机号
			if (StringUtil.isNotEmpty(params.getAccountOrPhone())) {
				map.put("accountOrPhone", params.getAccountOrPhone());
			}

			if (StringUtil.isNotEmpty(params.getMarketId())) {
				map.put("marketId", params.getMarketId());
			}
			if (StringUtil.isNotEmpty(params.getState()) && Integer.valueOf(params.getState()) != -1) {
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
			map.put("productType", ProductTypeEnum.Common.getKey());
			// 记录数
			map.put("total", productToolService.getCount(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<ProductDto> list = productToolService.getProductList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	@RequestMapping("product/delete")
	public String deleteProduct(String productId, ModelMap map, HttpServletRequest request) {
		int result = 0;
		try {
			SysRegisterUser user = getUser(request);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("productId", productId);
			param.put("state", 5);
			param.put("updateUserId", user.getUserID());
			param.put("updateTimeString", DateUtil.getSysDateTimeString());
			result = productToolService.updateProductStatus(param);
//			result = productToolService.deleteProduct(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(result);
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
	@RequestMapping("product/batchUpdateProductStatus")
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
	@RequestMapping("product/batchUpdateProductUpdateTime")
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
	@RequestMapping("product/batchRefreshExpire")
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
	@RequestMapping("product/batchUpdatePlabels")
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
	@RequestMapping("product/batchDelete")
	public String batchDeleteProduct(HttpServletRequest request, String productIds, ModelMap map) {

		String[] ids = productIds.split(",");
		int[] result = null;
		try {
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			List<ProductDto> productList = productToolService.getProductsByIds(ids);
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
	@RequestMapping("product/uploadProductPic")
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

	@ResponseBody
	@RequestMapping(value = "product/audit")
	public String audit(HttpServletRequest request, String productId,
			String status, String reason, String otherReason) {

		Map<String, Object> map = new HashMap<String, Object>();
		String oldState = "";
		try {
			oldState = productToolService.getByProductId(productId).getState();
		} catch (Exception e1) {
			e1.printStackTrace();
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
			//清楚活动缓存--add by TerryZhang
			gdOrderActivityBaseToolService.deleteCach(0);
			map.put("status", "1");
			map.put("message", result);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "0");
			map.put("message", e.toString());
		}
		return JSONObject.toJSONString(map,
				SerializerFeature.WriteDateUseDateFormat);
	}


	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "product/checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
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
			if (StringUtil.isNotEmpty(params.getState()) && Integer.valueOf(params.getState()) != -1) {
				map.put("state", params.getState());
			}
			if ("1".equals(params.getVendor())){
				map.put("roleType", "4");
			}
			map.put("productType", ProductTypeEnum.Common.getKey());
			
			int total = productToolService.getCount(map);
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
	 * 注意 : 如果修改查询条件, 需要同步修改product/checkExportParams接口
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "product/exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
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
			if (StringUtil.isNotEmpty(params.getState()) && Integer.valueOf(params.getState()) != -1) {
				map.put("state", params.getState());
			}
			if ("1".equals(params.getVendor())){
				map.put("roleType", "4");
			}
			map.put("productType", ProductTypeEnum.Common.getKey());
			// 记录数
			map.put("total", productToolService.getCount(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			list = productToolService.getProductListAll(map);

		}catch(Exception e){
			logger.info("product exportData with ex : {} ", new Object[]{e});
		}

		OutputStream ouputStream = null;
		try {
			// 响应流
			ouputStream = response.getOutputStream();
			// 设置响应头等信息
			ExcelUtils.preProcessResponse(response, "产品列表");
			// 填充数据并导出
			ExcelUtils.export("产品列表", ouputStream, list, new ProductBeanToSheetCallBack());
			
		} catch (Exception e) {
			logger.info("exportProductData with ex : {} ", new Object[]{e});
		} finally {
			try {
				ouputStream.close();
			} catch (IOException e) {
				logger.info("exportProductData with ex : {} ", new Object[]{e});
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
	@RequestMapping(value = "product/adProductCheckExportParams", produces="application/json; charset=utf-8")
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
	
	
	/**
	 * 导出产品查询结果
	 * 注意 : 如果修改查询条件, 需要同步修改product/adProductCheckExportParams接口
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "product/exportAdProductData")
	public String exportAdProductData(HttpServletRequest request, HttpServletResponse response, ProductParamsBean params) {
		List<ProductDto> list = null ;
		try{
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

			// 记录数
		//	map.put("total", productToolService.getAdProductCount(map));
			map.put("startRow", 0);
			map.put("endRow", 100000000);
			// 设定分页,排序
			//setCommParameters(request, map);
			// list
			list = productToolService.getAdProduct(map);

		}catch(Exception e){
			logger.info("product exportData with ex : {} ", new Object[]{e});
		}

		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("广告产品列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("广告产品列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "ID");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "商品名称");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "商品类目");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "商品产地");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "商品标签");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "更新时间");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "广告位");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "广告状态");// 填充第一行第七个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						ProductDto item = list.get(i);
						Label label0 = new Label(0, i + 1, String.valueOf(item.getProductId()));
						Label label1 = new Label(1, i + 1, item.getProductName());
						Label label2 = new Label(2, i + 1, item.getCateName());
						Label label3 = new Label(3, i + 1, item.getOriginProvinceName());
						Label label4 = new Label(4, i + 1, item.getProductSign());
						Label label5 = new Label(5, i + 1, DateUtil.toString(item.getUpdateTime(),
								DateUtil.DATE_FORMAT_DATETIME));
						Label label6 = new Label(6, i + 1, item.getAdName());
						String stateName="";
						if("01".equals(item.getState())||"1".equals(item.getState())){
							 stateName="上架";
						}else if("2".equals(item.getState())){
							 stateName="等待";
						}else if("3".equals(item.getState())||"02".equals(item.getState())){
							 stateName="到期";
						}else if("4".equals(item.getState())||"03".equals(item.getState())){
							 stateName="下架";
						}
						Label label7 = new Label(7, i + 1, stateName);
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
					    sheet.addCell(label6);
						sheet.addCell(label7);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@RequestMapping(value="product/queryforact" )
	@ResponseBody
	public String querybysearch(ProductBaseinfoDTO dto, HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", dto.getName());
			map.put("shopsName", dto.getShopsName());
			map.put("level", request.getParameter("level"));
			map.put("marketId", request.getParameter("marketId"));
			map.put("mobile", request.getParameter("mobile"));
			map.put("state", "3");
			
			/*
			 * 判断类目级别
			 * 不同级别设置不同参数
			 */
			if(request.getParameter("categoryId_add")!=null && !"-1".equals(request.getParameter("categoryId_add"))) {
				Long categoryId = Long.parseLong(request.getParameter("categoryId_add"));

				ProductCategoryDTO category = productToolService.getCategoryById(categoryId);
				
				if(category.getCurLevel()==0) {
					map.put("topParentId", categoryId);
				} else if(category.getCurLevel()==1) {
					map.put("parentId", categoryId);
				} else {
					map.put("categoryId", categoryId);
				}
			}
			
			
			//设定分页,排序
			setCommParameters(request, map);
			//list
			PageQueryResultDTO<ProductBaseinfoDTO> results = productToolService.getListPage(map);
			map.put("rows", results.getDataList());//rows键 存放每页记录 list  
			map.put("total", results.getTotalCount());
			
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
}
