package com.gudeng.commerce.gd.api.controller.v160428;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.ProductCateResultDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.ProductParamsBean;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.PromChainToolService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("/v28/product/")
public class ProductSolrController extends GDAPIBaseController  {
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(ProductSolrController.class);
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;
	
	@Autowired
	public ProductToolService productToolService;
    @Autowired
    public PromChainToolService promChainToolService;
	
	/**
	 * 根据顶级分类，获取到带分类导航的结果集合
	 */
	@RequestMapping("getProductCateResult")
	public void getProductCateResult(HttpServletRequest request, HttpServletResponse response){
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr, ProductParamsBean.class);
			
			String pageNum = GSONUtils.getJsonValueStr(jsonStr, "pageNum");
			if(pageNum==null || pageNum.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGENUM_IS_NULL, request, response);
				return ;
			}
			String pageSize = GSONUtils.getJsonValueStr(jsonStr, "pageSize");
			if(pageSize==null || pageSize.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGESIZE_IS_NULL, request, response);
				return ;
			}
			if(queryParams.getCategoryId()==null || queryParams.getCategoryId().equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return ;
			}
			
			ProductQueryBean pqb=new ProductQueryBean();
			pqb.setMarketId(queryParams.getMarketId()==null?3L:Long.valueOf(queryParams.getMarketId()));//市场ID
			pqb.setRoleType(queryParams.getRoleType()==null?"4":queryParams.getRoleType());//搜索好货源，必传
			
			if(null!=queryParams.getOriginCityId()&&!"".equals(queryParams.getOriginCityId())){
				pqb.setCityId(Long.valueOf(queryParams.getOriginCityId()));//产地-市级
			}
			if(null!=queryParams.getOriginAreaId()&&!"".equals(queryParams.getOriginAreaId())){
				pqb.setAreaId(Long.valueOf(queryParams.getOriginAreaId()));//产地-市级
			}
			
			pqb.setCateId(Long.valueOf(queryParams.getCategoryId()));//产品分类
			
			if(null!=queryParams.getManagementType()&&!"".equals(queryParams.getManagementType())){
				pqb.setManagementType(Integer.valueOf(queryParams.getManagementType()==null?"0":queryParams.getManagementType()));////经营类型
			}
			
			if(pageNum!=null&&!"".equals(pageNum)){
				pqb.setPageNum(Integer.valueOf(pageNum));
			}
			if(pageSize!=null&&!"".equals(pageSize)){
				pqb.setPageSize(Integer.valueOf(pageSize));
			}
			List<ProductCateResultDTO> psrdto=productToolService.getProductCateResult(pqb);
			result.setObject(psrdto);
		} catch (Exception e) {
			logger.info("query product list with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}


	/**
	 * 根据区域，获取到带分类导航的结果集合
	 */
	@RequestMapping("getProductAreaResult")
	public void getProductAreaResult(HttpServletRequest request, HttpServletResponse response){
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr, ProductParamsBean.class);
			
			String pageNum = GSONUtils.getJsonValueStr(jsonStr, "pageNum");
			if(pageNum==null || pageNum.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGENUM_IS_NULL, request, response);
				return ;
			}
			String pageSize = GSONUtils.getJsonValueStr(jsonStr, "pageSize");
			if(pageSize==null || pageSize.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGESIZE_IS_NULL, request, response);
				return ;
			}
			
			if((queryParams.getOriginProvinceId()==null || queryParams.getOriginProvinceId().equals("")) 
					&& (queryParams.getOriginCityId()==null || queryParams.getOriginCityId().equals("")) 
					&& (queryParams.getOriginAreaId()==null ||queryParams.getOriginAreaId().equals("")) ){
				setEncodeResult(result, ErrorCodeEnum.AREA_PARAM_IS_NULL, request, response);
				return ;
			}
			
			ProductQueryBean pqb=new ProductQueryBean();
			pqb.setMarketId(queryParams.getMarketId()==null?3L:Long.valueOf(queryParams.getMarketId()));//市场ID
			pqb.setRoleType(queryParams.getRoleType()==null?"4":queryParams.getRoleType());//搜索好货源，必传
			
			if(null!=queryParams.getOriginCityId()&&!"".equals(queryParams.getOriginCityId())){
				pqb.setCityId(Long.valueOf(queryParams.getOriginCityId()));//产地-市级
			}
			if(null!=queryParams.getOriginAreaId()&&!"".equals(queryParams.getOriginAreaId())){
				pqb.setAreaId(Long.valueOf(queryParams.getOriginAreaId()));//产地-市级
			}
			
			pqb.setCateId(queryParams.getCategoryId()==null?null:Long.valueOf(queryParams.getCategoryId()));//产品分类
			
			if(null!=queryParams.getManagementType()&&!"".equals(queryParams.getManagementType())){
				pqb.setManagementType(Integer.valueOf(queryParams.getManagementType()));////经营类型
			}
			
			if(pageNum!=null&&!"".equals(pageNum)){
				pqb.setPageNum(Integer.valueOf(pageNum));
			}
			if(pageSize!=null&&!"".equals(pageSize)){
				pqb.setPageSize(Integer.valueOf(pageSize));
			}
			List<ProductCateResultDTO> psrdto=productToolService.getProductAreaResult(pqb);
			result.setObject(psrdto);
		} catch (Exception e) {
			logger.info("query product list with ex : ", e);
			e.printStackTrace();
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	

	/**
	 * 产品标签，获取到产品列表
	 */
	@RequestMapping("getProductSignResult")
	public void getProductSignResult(HttpServletRequest request, HttpServletResponse response){
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr, ProductParamsBean.class);
			
			String pageNum = GSONUtils.getJsonValueStr(jsonStr, "pageNum");
			if(pageNum==null || pageNum.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGENUM_IS_NULL, request, response);
				return ;
			}
			String pageSize = GSONUtils.getJsonValueStr(jsonStr, "pageSize");
			if(pageSize==null || pageSize.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGESIZE_IS_NULL, request, response);
				return ;
			}
			
			if(queryParams.getProductSign()==null || queryParams.getProductSign().equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_SIGN_IS_NULL, request, response);
				return ;
			}
			
			String orderMapStr= GSONUtils.getJsonValueStr(jsonStr, "orderMap");
			Map<String, String> order = new HashMap<>();
			if(orderMapStr!=null && !orderMapStr.equals("")){//2016-4-28日需求，农批商和产地供应商一样，默认updatePriceTime desc 
				JSONObject jsonObject =JSONUtils.parseObject(orderMapStr);
				order.put("price", jsonObject.getString("price"));
			}else{
				order.put("updatePriceTime", "desc");
			}
			ProductQueryBean pqb=new ProductQueryBean();

			pqb.setOrder(order);
			pqb.setMarketId(queryParams.getMarketId()==null?3L:Long.valueOf(queryParams.getMarketId()));//市场ID
			pqb.setRoleType(queryParams.getRoleType()==null?"4":queryParams.getRoleType());//搜索好货源，必传
			pqb.setProductSign(queryParams.getProductSign());
			
			Integer startRow=0;
			Integer rows=10;
			rows=Integer.valueOf(pageSize);
			startRow=(Integer.valueOf(pageNum)-1)*rows;
			pqb.setStartRow(startRow);
			pqb.setPageSize(rows);
			
			ProductSearchResultDTO presult = productToolService.getByProductQueryBean(pqb);
			
			List<PushProductDTO> pushAdInfoList =new ArrayList<PushProductDTO>();
	        CommonPageDTO pageDTO = new CommonPageDTO(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
			pushAdInfoList=productToolService.translate(presult.getList(), pushAdInfoList);
            for (PushProductDTO productDto : pushAdInfoList) {
                //获取产品活动价
                PromProdInfoDTO promProdInfoDTO = promChainToolService.getPromotionProduct(productDto.getProductId());
                if(promProdInfoDTO != null && promProdInfoDTO.getActPrice() > 0){
                	productDto.setPrice(promProdInfoDTO.getActPrice());
                	productDto.setPromotion(1);
                }
            }
			pageDTO.setRecordCount(presult.getCount());//设置翻页
			pageDTO.initiatePage(presult.getCount().intValue());
			pageDTO.setRecordList(pushAdInfoList);
			result.setObject(pageDTO);
		} catch (Exception e) {
			logger.info("query product list with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	/**
	 * 产品分类ID，获取到产品列表
	 * 
	 * 分类ID可以是一级，二级和三级
	 * 
	 */
	@RequestMapping("getProductCateList")
	public void getProductChileCateList(HttpServletRequest request, HttpServletResponse response){
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductParamsBean queryParams = (ProductParamsBean) GSONUtils.fromJsonToObject(jsonStr, ProductParamsBean.class);
			
			String pageNum = GSONUtils.getJsonValueStr(jsonStr, "pageNum");
			if(pageNum==null || pageNum.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGENUM_IS_NULL, request, response);
				return ;
			}
			String pageSize = GSONUtils.getJsonValueStr(jsonStr, "pageSize");
			if(pageSize==null || pageSize.equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGESIZE_IS_NULL, request, response);
				return ;
			}
			if(queryParams.getCategoryId()==null || queryParams.getCategoryId().trim().equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return ;
			}
			
			String orderMapStr= GSONUtils.getJsonValueStr(jsonStr, "orderMap");
			Map<String, String> order = new HashMap<>();
			//2016-4-28日需求，农批商和产地供应商一样，默认updatePriceTime desc
			if(orderMapStr!=null && !orderMapStr.equals("")){ 
				JSONObject jsonObject =JSONUtils.parseObject(orderMapStr);
				order.put("price", jsonObject.getString("price"));
			}else{
				order.put("updatePriceTime", "desc");
			}
			ProductQueryBean pqb=new ProductQueryBean();

			pqb.setOrder(order);
			pqb.setMarketId(queryParams.getMarketId()==null?3L:Long.valueOf(queryParams.getMarketId()));//市场ID
			pqb.setRoleType(queryParams.getRoleType()==null?"4":queryParams.getRoleType());//搜索好货源，必传
			pqb.setProductSign(queryParams.getProductSign());
			pqb.setCateId(Long.valueOf(queryParams.getCategoryId()));//产品分类
			Integer startRow=0;
			Integer rows=10;
			rows=Integer.valueOf(pageSize);
			startRow=(Integer.valueOf(pageNum)-1)*rows;
			pqb.setStartRow(startRow);
			pqb.setPageSize(rows);
			ProductSearchResultDTO presult = productToolService.getByProductQueryBean(pqb);
			
			List<PushProductDTO> pushAdInfoList =new ArrayList<PushProductDTO>();
	        CommonPageDTO pageDTO = new CommonPageDTO(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
			pushAdInfoList=productToolService.translate(presult.getList(), pushAdInfoList);
			pageDTO.setRecordCount(presult.getCount());//设置翻页
			pageDTO.initiatePage(presult.getCount().intValue());
			pageDTO.setRecordList(pushAdInfoList);
			result.setObject(pageDTO);
		} catch (Exception e) {
			logger.info("query product list with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	
}
