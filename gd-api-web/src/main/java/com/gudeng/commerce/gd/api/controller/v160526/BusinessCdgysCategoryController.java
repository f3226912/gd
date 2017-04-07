package com.gudeng.commerce.gd.api.controller.v160526;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.BusinessParamsBean;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifBaseToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;

/**
 * 产地供应商控制中心
 * @author TerryZhang
 */
@Controller
@RequestMapping("cdgys/v160526/business")
public class BusinessCdgysCategoryController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(BusinessCdgysCategoryController.class);
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	@Autowired
	public MemberToolService memberToolService ;
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService ;
	@Autowired
	public ProCategoryService productCategoryService;
	@Autowired
	public PvStatisticBusinessToolService pvStatisticBusinessToolService;
	@Autowired
	public CertifBaseToolService certifBaseToolService;
	
	@RequestMapping("/getShopByBusinessId")
	public void getShopByBusinessId(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String businessId = (String)GSONUtils.getJsonValueStr(jsonStr, "businessId");
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			
			if(StringUtils.isBlank(businessId)){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getById(businessId);
			if(dto==null || dto.getBusinessId()==null){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			BusinessParamsBean params=new BusinessParamsBean();
			// 查询认证状态
			Map<String, Object> cmap = new HashMap<String, Object>();
			cmap.put("memberId", dto.getUserId());
			Map<String, Object> cres = certifBaseToolService.getStatusCombination(cmap);
			//合作社认证
			params.setCcstatus(CommonUtils.isEmpty(cres.get("ccstatus")) ? "" : String.valueOf(cres.get("ccstatus")));
			//基地认证
			params.setCbstatus("0");
			//企业认证
			params.setComstatus(CommonUtils.isEmpty(cres.get("comstatus")) ? "" : String.valueOf(cres.get("comstatus")));
			//个人认证
			params.setCpstatus(CommonUtils.isEmpty(cres.get("cpstatus")) ? "" : String.valueOf(cres.get("cpstatus")));
			//
			businessBaseinfoToolService.copyPropeties(dto, params);//注意是否copy过去
			StringBuffer sbf=new StringBuffer("");
			Map<String, Number> map =new HashMap<>();
			map.put("businessId", dto.getBusinessId());
			map.put("startRow", 0);
			map.put("endRow", 9999);
			List<ReBusinessCategoryDTO>  categoryIds=businessBaseinfoToolService.getReBusinessCategoryBySearch(map);
			
			if(categoryIds!=null && categoryIds.size()>0){
				for(ReBusinessCategoryDTO category:categoryIds){
					if(sbf.length()==0){
						sbf.append(category.getCategoryId());
					}else{
						sbf.append(",");
						sbf.append(category.getCategoryId());
					}
				}
			}
			params.setCategoryIds(sbf.toString());
			
			/** 2016年5月26日 增加主营分类及选中状态 -start */
			List<ProductCategoryDTO> retList = productCategoryService.getListTopProductCategoryByMarketId(Long.valueOf(dto.getMarketId()));
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(ProductCategoryDTO cdto:retList){
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("categoryId", cdto.getCategoryId());
				map1.put("cateName", cdto.getCateName());
				map1.put("isCheck", "0");//0表示未经营此分类，
				if(categoryIds!=null && categoryIds.size()>0){
					for(ReBusinessCategoryDTO category:categoryIds){
						//主营设置选中
						if(category.getCategoryId().longValue()==cdto.getCategoryId().longValue()&&"0".equals(category.getBusinessType())){
							map1.put("isCheck", "1");//1表示有经营
							break;
						}
					}
				}
				list.add(map1);
			}
			/** 2016年5月26日 增加兼营分类及选中状态 -start */
			List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
			for(ProductCategoryDTO cdto:retList){
				Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("categoryId", cdto.getCategoryId());
				map2.put("cateName", cdto.getCateName());
				map2.put("isCheck", "0");//0表示未经营此分类，
				if(categoryIds!=null && categoryIds.size()>0){
					for(ReBusinessCategoryDTO category:categoryIds){
						//兼营设置选中
						if(category.getCategoryId().longValue()==cdto.getCategoryId().longValue()&&"1".equals(category.getBusinessType())){
							map2.put("isCheck", "1");//1表示有经营，
							break;
						}
					}
				}
				list2.add(map2);
			}
			params.setZyCategory(list);//主营
			params.setJyCategory(list2);//兼营
			
			//增加商铺详情页浏览量
			if(StringUtils.isNotBlank(memberId) && memberId.equals(dto.getUserId().toString())){
				logger.info("User is browsing his own shop, memberId: " + memberId);
			}else{
				BusinessPvStatisDTO businessPvStatisDTO = new BusinessPvStatisDTO();
				businessPvStatisDTO.setAddPv(CommonUtils.getNumFromRange(1, 4));
				businessPvStatisDTO.setBusinessId(Long.parseLong(businessId));
				businessPvStatisDTO.setFromPage("3");
				pvStatisticBusinessToolService.addPv(businessPvStatisDTO);
			}
			
			result.setObject(params);
			
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("/getShopByUserId")
	public void getShopByUserId(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String userId = (String)GSONUtils.getJsonValueStr(jsonStr, "userId");
			if(StringUtils.isBlank(userId)){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_USER_ID_IS_NULL, request, response);
				return;
			}
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getByUserId(userId);
			if(dto==null || dto.getBusinessId()==null){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			BusinessParamsBean params=new BusinessParamsBean();
			businessBaseinfoToolService.copyPropeties(dto, params);//注意是否copy过去
			StringBuffer sbf=new StringBuffer("");
			Map<String, Number> map =new HashMap<>();
			map.put("businessId", dto.getBusinessId());
			map.put("startRow", 0);
			map.put("endRow", 9999);
			List<ReBusinessCategoryDTO>  categoryIds=businessBaseinfoToolService.getReBusinessCategoryBySearch(map);
			if(categoryIds!=null && categoryIds.size()>0){
				for(ReBusinessCategoryDTO category:categoryIds){
					if(sbf.length()==0){
						sbf.append(category.getCategoryId());
					}else{
						sbf.append(",");
						sbf.append(category.getCategoryId());
					}
				}
			}
			params.setCategoryIds(sbf.toString());

			/** 2016年5月26日 增加主营分类及选中状态 -start */
			List<ProductCategoryDTO> retList = productCategoryService.getListTopProductCategoryByMarketId(Long.valueOf(dto.getMarketId()));
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(ProductCategoryDTO cdto:retList){
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("categoryId", cdto.getCategoryId());
				map1.put("cateName", cdto.getCateName());
				map1.put("isCheck", "0");//0表示未经营此分类，
				if(categoryIds!=null && categoryIds.size()>0){
					for(ReBusinessCategoryDTO category:categoryIds){
						//主营设置选中
						if(category.getCategoryId().longValue()==cdto.getCategoryId().longValue()
								&& "0".equals(category.getBusinessType())){
							map1.put("isCheck", "1");//1表示有经营
							break;
						}
					}
				}
				list.add(map1);
			}
			/** 2016年5月26日 增加兼营分类及选中状态 -start */
			List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
			for(ProductCategoryDTO cdto:retList){
				Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("categoryId", cdto.getCategoryId());
				map2.put("cateName", cdto.getCateName());
				map2.put("isCheck", "0");//0表示未经营此分类，
				if(categoryIds!=null && categoryIds.size()>0){
					for(ReBusinessCategoryDTO category:categoryIds){
						//兼营设置选中
						if(category.getCategoryId().longValue()==cdto.getCategoryId().longValue()
								&& "1".equals(category.getBusinessType())){
							map2.put("isCheck", "1");//1表示有经营，
							break;
						}
					}
				}
				list2.add(map2);
			}
			params.setZyCategory(list);//主营
			params.setJyCategory(list2);//兼营
			result.setObject(params);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("/updateShopInfo")
	public void updateShopInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String  jsonStr = getParamDecodeStr(request);
			BusinessParamsBean params = (BusinessParamsBean) GSONUtils.fromJsonToObject(jsonStr, BusinessParamsBean.class);
			Long businessId = params.getBusinessId();
			if(businessId==null ){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			String shopsName = params.getShopsName();
			if(StringUtils.isBlank(shopsName)){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_NAME_IS_NULL, request, response);
				return;
			}
			if(shopsName.length()>30){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_NAME_OVER_30_LENGTH, request, response);
				return;
			}
			if(params.getAddress()!=null && params.getAddress().length()>50){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ADDRESS_OVER_LENGTH, request, response);
				return;
			}
			Integer businessModel = params.getBusinessModel();
			if(businessModel==null){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_MODEL_IS_NULL, request, response);
				return;
			}
			
			String userId =String.valueOf(params.getUserId());
			if(StringUtils.isBlank(userId)){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_USER_ID_IS_NULL, request, response);
				return;
			}
			
			String catagoryIds[] = null;
			String categorys;
				categorys=params.getCategoryIds();
				if( !StringUtils.isBlank(categorys)){
					catagoryIds=categorys.split(",");
				}else{
					setEncodeResult(result, ErrorCodeEnum.BUSINESS_CATEGORY_ID_IS_NULL, request, response);
					return ;
				}
			
			//修改店铺信息
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getById(String.valueOf(businessId));
			dto.setBusinessId(businessId);
			dto.setShopsName(shopsName);
			dto.setName(params.getName());
			dto.setBusinessModel(businessModel);
			dto.setShopsDesc(params.getShopsDesc());
			dto.setMainProduct(params.getMainProduct());
			dto.setManagementType(params.getManagementType());  //产地供应商，不可修改商铺类型，仅仅管理后台可以修改
			dto.setProvinceId(params.getProvinceId()==null?0L:params.getProvinceId());
			dto.setCityId(params.getCityId()==null?0L:params.getCityId());
			dto.setAreaId(params.getAreaId()==null?0L:params.getAreaId());
			dto.setAddress(params.getAddress());
			businessBaseinfoToolService.updateBusinessBaseinfoDTO(dto);
			
			if(catagoryIds!=null && catagoryIds.length>0){
				businessBaseinfoToolService.deleteReBusinessCategoryByBusinessId(businessId);
				for(String categoryId:catagoryIds){
					ReBusinessCategoryDTO rcdto=new ReBusinessCategoryDTO();
					rcdto.setBusinessId(businessId);
					//拆分分类及经营类型,categoryId 组成分类编号：经营类型
					String str[]=categoryId.split(":");
					rcdto.setCategoryId(Long.valueOf(str[0]));
					rcdto.setBusinessType(str[1]);
					businessBaseinfoToolService.addReBusinessCategoryDTO(rcdto);
				}
			}
			
		} catch (Exception e) {
			logger.error("编辑店铺异常",e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	

	@RequestMapping("/addShopInfo")
	public void addShopInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		Map<String, Long> resultMap=new HashMap<>();
		try {
			String  jsonStr = getParamDecodeStr(request);
			BusinessParamsBean params = (BusinessParamsBean) GSONUtils.fromJsonToObject(jsonStr, BusinessParamsBean.class);
			String shopsName = params.getShopsName();
			if(StringUtils.isBlank(shopsName)){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_NAME_IS_NULL, request, response);
				return;
			}
			if(shopsName.length()>30){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_NAME_OVER_30_LENGTH, request, response);
				return;
			}
			if(params.getAddress()!=null && params.getAddress().length()>50){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ADDRESS_OVER_LENGTH, request, response);
				return;
			}
			Integer businessModel = params.getBusinessModel();
			if(businessModel==null){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_MODEL_IS_NULL, request, response);
				return;
			}
			String marketId = params.getMarketId();
			if(marketId==null){
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
				return;
			}
			
			Integer level = params.getLevel();
			if(level==null){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_LEVEL_IS_NULL, request, response);
				return;
			}
			if(level==4){
				Integer managementType = params.getManagementType();
				if(params.getAreaType()!=null && params.getAreaType().equals("1")){
					if(managementType==null){
						setEncodeResult(result, ErrorCodeEnum.BUSINESS_CATEGORY_ID_IS_NULL, request, response);
						return;
					}
				}
				marketId="3";//产地供应商直接设置市场为3
			}
			
			String userId =String.valueOf(params.getUserId());
			if(StringUtils.isBlank(userId)){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_USER_ID_IS_NULL, request, response);
				return;
			}
			BusinessBaseinfoDTO businessDto = businessBaseinfoToolService.getByUserId(userId);
			if(businessDto!=null && businessDto.getBusinessId()!=null ){
				resultMap.put("businessId", businessDto.getBusinessId());
				result.setObject(resultMap);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			String catagoryIds[] = null;
			String categorys;
				categorys=params.getCategoryIds();
				if( categorys!=null && !categorys.equals("null") && !categorys.equals("")){
					catagoryIds=categorys.split(",");
				}else{
					setEncodeResult(result, ErrorCodeEnum.BUSINESS_CATEGORY_ID_IS_NULL, request, response);
					return ;
				}
			
			//新增店铺信息
			BusinessBaseinfoEntity dto=new BusinessBaseinfoEntity();
			dto.setShopsName(shopsName);
			dto.setName(params.getName());//公司名称
			dto.setBusinessModel(businessModel);
			dto.setShopsDesc(params.getShopsDesc());
			dto.setMainProduct(params.getMainProduct());
			dto.setUserId(params.getUserId());
			dto.setCreateTime(new Date());
			dto.setManagementType(params.getManagementType());
//			dto.setProvinceId(params.getProvinceId());
//			dto.setCityId(params.getCityId());
//			dto.setAreaId(params.getAreaId());
			dto.setProvinceId(params.getProvinceId()==null?0L:params.getProvinceId());
			dto.setCityId(params.getCityId()==null?0L:params.getCityId());
			dto.setAreaId(params.getAreaId()==null?0L:params.getAreaId());
			dto.setAddress(params.getAddress());
			
			Long businessId=businessBaseinfoToolService.addBusinessBaseinfoEnt(dto);
			resultMap.put("businessId", businessId);

				ReBusinessMarketDTO rbmDto =reBusinessMarketToolService.getByBusinessId(businessId);
				if(rbmDto==null){
					ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
					rbm.setBusinessId(businessId);
					rbm.setMarketId(Long.valueOf(marketId));
					reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
				}

			if(catagoryIds!=null && catagoryIds.length>0){
				for(String categoryId:catagoryIds){
					ReBusinessCategoryDTO rcdto=new ReBusinessCategoryDTO();
					rcdto.setBusinessId(businessId);
					//拆分分类及经营类型,categoryId 组成分类编号：经营类型
					String str[]=categoryId.split(":");
					rcdto.setCategoryId(Long.valueOf(str[0]));
					rcdto.setBusinessType(str[1]);
					businessBaseinfoToolService.addReBusinessCategoryDTO(rcdto);
				}
			}
		} catch (Exception e) {
			logger.error("编辑店铺异常",e);
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		result.setObject(resultMap);
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
}
