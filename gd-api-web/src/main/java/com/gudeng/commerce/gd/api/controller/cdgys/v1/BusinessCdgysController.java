package com.gudeng.commerce.gd.api.controller.cdgys.v1;

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
import com.gudeng.commerce.gd.api.dto.input.BusinessInputDTO;
import com.gudeng.commerce.gd.api.dto.output.BusinessDetailAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.BusinessParamsBean;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifBaseToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.exception.BusinessException;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;

/**
 * 产地供应商控制中心
 * @author TerryZhang
 */
@Controller
@RequestMapping("cdgys/v1/business")
public class BusinessCdgysController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(BusinessCdgysController.class);
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	@Autowired
	public MemberToolService memberToolService ;
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService ;
	@Autowired
	public ProCategoryService productCategoryService;	
	@Autowired
	public CertifBaseToolService certifBaseToolService;
	
	@RequestMapping("/getShopByBusinessId")
	public void getShopByBusinessId(HttpServletRequest request, HttpServletResponse response, String businessId) {
		ObjectResult result = new ObjectResult();
		try {
			if(StringUtils.isBlank(businessId)){
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getById(businessId);
			if(dto==null || dto.getBusinessId()==null){
				setResult(result, ErrorCodeEnum.BUSINESS_IS_NOT_EXISTED, request, response);
				return;
			}
			
			BusinessParamsBean params=new BusinessParamsBean();
			// 查询认证状态
			BusinessBaseinfoDTO baseinfoDTO = businessBaseinfoToolService.getById(businessId);
			Map<String, Object> cmap = new HashMap<String, Object>();
			cmap.put("memberId", baseinfoDTO.getUserId());
			logger.info("cmap memberId : {}, businessId : {} ", baseinfoDTO.getUserId(), businessId);
			Map<String, Object> cres = certifBaseToolService.getStatusCombination(cmap);
			//合作社认证
			params.setCcstatus(CommonUtils.isEmpty(cres.get("ccstatus")) ? "" : String.valueOf(cres.get("ccstatus")));
			//基地认证
			params.setCbstatus("0");
			//企业认证
			params.setComstatus(CommonUtils.isEmpty(cres.get("comstatus")) ? "" : String.valueOf(cres.get("comstatus")));
			
			businessBaseinfoToolService.copyPropeties(dto, params);//注意是否copy过去
//			if(dto.getLevel()!=null && dto.getLevel()!=4){} //根据商铺id和根据userid获取用户类型的时候，不定能获取到，直接设置setCategoryIds
			StringBuffer sbf=new StringBuffer("");
			Map map =new HashMap();
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
			
			List<ProductCategoryDTO> retList = productCategoryService.getListTopProductCategoryByMarketId(3L);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(ProductCategoryDTO cdto:retList){
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("categoryId", cdto.getCategoryId());
				map1.put("cateName", cdto.getCateName());
				map1.put("isCheck", "0");//0表示未经营此分类，
				if(categoryIds!=null && categoryIds.size()>0){
					for(ReBusinessCategoryDTO category:categoryIds){
						if(category.getCategoryId().longValue()==cdto.getCategoryId().longValue()){
							map1.put("isCheck", "1");//1表示有经营，
							break;
						}
					}
				}
				list.add(map1);
			}
			params.setAllCategory(list);
			//获取会员地址
//			MemberBaseinfoDTO memberBaseinfoDTO =memberToolService.getById(String.valueOf(dto.getUserId()));
//			params.setAddress(memberBaseinfoDTO.getAddress());
			
			result.setObject(params);
			
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("/getShopByUserId")
	public void getShopByUserId(HttpServletRequest request, HttpServletResponse response, String userId) {
		ObjectResult result = new ObjectResult();
		try {
			if(StringUtils.isBlank(userId)){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getByUserId(userId);
			if(dto==null || dto.getBusinessId()==null){
				setResult(result, ErrorCodeEnum.BUSINESS_IS_NOT_EXISTED, request, response);
				return;
			}
			BusinessParamsBean params=new BusinessParamsBean();
			businessBaseinfoToolService.copyPropeties(dto, params);//注意是否copy过去
//			if(dto.getLevel()!=null && dto.getLevel()!=4){} //根据商铺id和根据userid获取用户类型的时候，不定能获取到，直接设置setCategoryIds
			StringBuffer sbf=new StringBuffer("");
			Map map =new HashMap();
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
			

			List<ProductCategoryDTO> retList = productCategoryService.getListTopProductCategoryByMarketId(3L);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(ProductCategoryDTO cdto:retList){
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("categoryId", cdto.getCategoryId());
				map1.put("cateName", cdto.getCateName());
				map1.put("isCheck", "0");//0表示未经营此分类，
				if(categoryIds!=null && categoryIds.size()>0){
					for(ReBusinessCategoryDTO category:categoryIds){
						if(category.getCategoryId().longValue()==cdto.getCategoryId().longValue()){
							map1.put("isCheck", "1");//1表示有经营，
							break;
						}
					}
				}
				list.add(map1);
			}
			params.setAllCategory(list);
			//获取会员地址
//			MemberBaseinfoDTO memberBaseinfoDTO =memberToolService.getById(String.valueOf(dto.getUserId()));
//			params.setAddress(memberBaseinfoDTO.getAddress());
			
			result.setObject(params);
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("/updateShopInfo")
	public void updateShopInfo(HttpServletRequest request, HttpServletResponse response, BusinessParamsBean params) {
		ObjectResult result = new ObjectResult();
		try {
			Long businessId = params.getBusinessId();
			if(businessId==null ){
				setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			String shopsName = params.getShopsName();
			if(StringUtils.isBlank(shopsName)){
				setResult(result, ErrorCodeEnum.BUSINESS_NAME_IS_NULL, request, response);
				return;
			}
			if(shopsName.length()>50){
				setResult(result, ErrorCodeEnum.BUSINESS_NAME_OVER_50_LENGTH, request, response);
				return;
			}
			Integer businessModel = params.getBusinessModel();
			if(businessModel==null){
				setResult(result, ErrorCodeEnum.BUSINESS_MODEL_IS_NULL, request, response);
				return;
			}
			
			String userId =String.valueOf(params.getUserId());
			if(StringUtils.isBlank(userId)){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			params.setManagementType(null);//更新的时候，不能修改经营类型
			
			MemberBaseinfoDTO mbdto= memberToolService.getById(userId);
			
			String catagoryIds[] = null;
			String categorys;

//			if(null != mbdto.getLevel() && mbdto.getLevel()!=4){
				//非产地供应商的时候，才需要加入经营分类
				categorys=params.getCategoryIds();
				if( !StringUtils.isBlank(categorys)){
					catagoryIds=categorys.split(",");
				}else{
					setResult(result, ErrorCodeEnum.BUSINESS_CATEGORY_ID_IS_NULL, request, response);
					return ;
				}
//			}
			
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
			
			//修改会员地址
//			MemberBaseinfoDTO memberBaseinfoDTO = new MemberBaseinfoDTO();
////			memberBaseinfoDTO.setProvinceId(Long.valueOf(params.getProvince()));
////			memberBaseinfoDTO.setCityId(Long.valueOf(params.getCity()));
////			memberBaseinfoDTO.setAreaId(Long.valueOf(params.getArea()));
//			memberBaseinfoDTO.setAddress(params.getAddress());
//			memberBaseinfoDTO.setMemberId(dto.getUserId());
//			memberToolService.updateMember(memberBaseinfoDTO);
			
			if(catagoryIds!=null && catagoryIds.length>0){
				businessBaseinfoToolService.deleteReBusinessCategoryByBusinessId(businessId);
				for(String categoryId:catagoryIds){
					ReBusinessCategoryDTO rcdto=new ReBusinessCategoryDTO();
					rcdto.setBusinessId(businessId);
					rcdto.setCategoryId(Long.valueOf(categoryId));
					businessBaseinfoToolService.addReBusinessCategoryDTO(rcdto);
				}
			}
			
		} catch (Exception e) {
			logger.error("编辑店铺异常",e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	

	@RequestMapping("/addShopInfo")
	public void addShopInfo(HttpServletRequest request, HttpServletResponse response, BusinessParamsBean params) {
		ObjectResult result = new ObjectResult();
		Map resultMap=new HashMap();
		try {
			
			String shopsName = params.getShopsName();
			if(StringUtils.isBlank(shopsName)){
				setResult(result, ErrorCodeEnum.BUSINESS_NAME_IS_NULL, request, response);
				return;
			}
			if(shopsName.length()>50){
				setResult(result, ErrorCodeEnum.BUSINESS_NAME_OVER_50_LENGTH, request, response);
				return;
			}
			Integer businessModel = params.getBusinessModel();
			if(businessModel==null){
				setResult(result, ErrorCodeEnum.BUSINESS_MODEL_IS_NULL, request, response);
				return;
			}
			
			
			
			String marketId = params.getMarketId();
			if(marketId==null){
				setResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
				return;
			}
			
			Integer level = params.getLevel();
			if(level==null){
				setResult(result, ErrorCodeEnum.BUSINESS_LEVEL_IS_NULL, request, response);
				return;
			}
			
			if(level==4){
				Integer managementType = params.getManagementType();
				if(params.getAreaType()!=null && params.getAreaType().equals("1")){
					if(managementType==null){
						setResult(result, ErrorCodeEnum.BUSINESS_MANAGEMENT_TYPE_IS_NULL, request, response);
						return;
					}
				}
				marketId="3";//产地供应商直接设置市场为3
			}
			
			String userId =String.valueOf(params.getUserId());
			if(StringUtils.isBlank(userId)){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			BusinessBaseinfoDTO businessDto = businessBaseinfoToolService.getByUserId(userId);
			if(businessDto!=null && businessDto.getBusinessId()!=null ){
				resultMap.put("businessId", businessDto.getBusinessId());
				result.setObject(resultMap);
				setResult(result, ErrorCodeEnum.BUSINESS_IS_EXISTED, request, response);
				return;
			}
			
			MemberBaseinfoDTO mbdto= memberToolService.getById(userId);
			
			String catagoryIds[] = null;
			String categorys;

//			if(null != mbdto.getLevel() && mbdto.getLevel()!=4){
				//非产地供应商的时候，才需要加入经营分类
				categorys=params.getCategoryIds();
//				if( !StringUtils.isBlank(categorys)){
				if( categorys!=null && !categorys.equals("null") && !categorys.equals("")){
					catagoryIds=categorys.split(",");
				}else{
					setResult(result, ErrorCodeEnum.BUSINESS_CATEGORY_ID_IS_NULL, request, response);
					return ;
				}
//			}
			
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
			dto.setProvinceId(params.getProvinceId());
			dto.setCityId(params.getCityId());
			dto.setAreaId(params.getAreaId());
			dto.setAddress(params.getAddress());
			
			Long businessId=businessBaseinfoToolService.addBusinessBaseinfoEnt(dto);
			resultMap.put("businessId", businessId);
//			if(level!=null &&level!=4){
				ReBusinessMarketDTO rbmDto =reBusinessMarketToolService.getByBusinessId(businessId);
				if(rbmDto==null){
					ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
					rbm.setBusinessId(businessId);
					rbm.setMarketId(Long.valueOf(marketId));
					reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
				}
//			}
			
			//修改会员地址
//			MemberBaseinfoDTO memberBaseinfoDTO = new MemberBaseinfoDTO();
////			memberBaseinfoDTO.setProvinceId(Long.valueOf(params.getProvince()));
////			memberBaseinfoDTO.setCityId(Long.valueOf(params.getCity()));
////			memberBaseinfoDTO.setAreaId(Long.valueOf(params.getArea()));
//			memberBaseinfoDTO.setAddress(params.getAddress());
//			memberBaseinfoDTO.setMemberId(dto.getUserId());
////			if(level!=null && level==4){
////				//用户level为1,2,3等情况的时候，不变更用户的类型，用户的level为4的时候，由农批商升级为产地供应商
////				memberBaseinfoDTO.setLevel(level);
////			}
//			memberToolService.updateMember(memberBaseinfoDTO);
			
			
			
			if(catagoryIds!=null && catagoryIds.length>0){
				for(String categoryId:catagoryIds){
					ReBusinessCategoryDTO rcdto=new ReBusinessCategoryDTO();
					rcdto.setBusinessId(businessId);
					rcdto.setCategoryId(Long.valueOf(categoryId));
					businessBaseinfoToolService.addReBusinessCategoryDTO(rcdto);
				}
			}
			
		} catch (Exception e) {
			logger.error("编辑店铺异常",e);
			e.printStackTrace();
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		result.setObject(resultMap);
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	
	/**
	 * 根据市场id获取一级分类
	 * @param request
	 * @param response
	 * @param usercollectProductCategoryDTO
	 */
	@RequestMapping("/getCdgysCateList")
	public void getHhyCateList(HttpServletRequest request,
			HttpServletResponse response,
			String marketId) {
		ObjectResult result = new ObjectResult();
		if(StringUtils.isBlank(marketId)){
			result.setMsg("参数异常");
			renderJson(result, request, response);
		}
		Long mid = Long.parseLong(marketId);
		try {
			List<ProductCategoryDTO> retList = productCategoryService.getListTopProductCategoryByMarketId(mid);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(ProductCategoryDTO dto:retList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("categoryId", dto.getCategoryId());
				map.put("cateName", dto.getCateName());
				list.add(map);
			}
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setObject(list);
		} catch (BusinessException e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		} catch (Exception e) {
			result.setMsg("获取关注种类异常,请稍后再试");
			logger.info(" 获取用户关注的所有分类异常", e);
		}
		renderJson(result, request, response);
	}
		
	
}
