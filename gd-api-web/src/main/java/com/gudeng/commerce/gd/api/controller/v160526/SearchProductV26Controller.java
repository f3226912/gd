package com.gudeng.commerce.gd.api.controller.v160526;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.ProductParamsBean;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.service.search.SearchProductToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.search.dto.FaceWordDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("/v26/product/")
public class SearchProductV26Controller extends GDAPIBaseController  {
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(SearchProductV26Controller.class);
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;
	
	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public SearchProductToolService searchProductToolService;
	@Autowired
	public ProductBaseinfoToolService productBaseinfoToolService;
	
	
	@RequestMapping("getFacetWord")
	public void getFacetWord(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductQueryBean queryParams = (ProductQueryBean) GSONUtils.fromJsonToObject(jsonStr, ProductQueryBean.class);
			if(queryParams.getMarketId() == null){
				//普通市场传入当前市场的ID，产地供应商传入marketId=3
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
				return ;
			}
			if(queryParams.getRoleType()==null || queryParams.getRoleType().trim().equals("")){
				//农批商的产品传入roleType=1，产地供应商的产品传入roleType=4
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_TYPE_IS_NULL, request, response);
				return ;
			}
			if(queryParams.getFacetWord()==null || queryParams.getFacetWord().trim().equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_FACEWORD_IS_NULL, request, response);
				return ;
			}
			if(queryParams.getFacetField()==null || queryParams.getFacetField().trim().equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_FACEFIELD_IS_NULL, request, response);
				return ;
			}
			
			List<FaceWordDTO> list=searchProductToolService.getFacetWord(queryParams);		
			result.setObject(list);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}
	

	@RequestMapping("getFacetMarket")
	public void getFacetMarket(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
			ProductQueryBean queryParams = (ProductQueryBean) GSONUtils.fromJsonToObject(jsonStr, ProductQueryBean.class);
			List<ProductFacetMarketResultDTO> list = searchProductToolService.getFacetMarket(queryParams);		
			result.setObject(list);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}
	
	/**
	 * 产品分类ID，获取到产品列表
	 * 
	 * 分类ID可以是一级，二级和三级
	 * 
	 */
	@RequestMapping("getProductByCateIds")
	public void getProductByCateIds(HttpServletRequest request, HttpServletResponse response){
		
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
			
			if(queryParams.getCateIds()==null || queryParams.getCateIds().trim().equals("")){
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_CATEGORY_ID_IS_NULL, request, response);
				return ;
			}
			
//			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			
			String orderMapStr= GSONUtils.getJsonValueStr(jsonStr, "orderMap");
			Map<String, String> order=new HashMap<>();
			if(orderMapStr!=null && !orderMapStr.equals("")){//2016-4-28日需求，农批商和产地供应商一样，默认updatePriceTime desc 
				JSONObject jsonObject =JSONUtils.parseObject(orderMapStr);
				if(jsonObject.getString("price")!=null &&!jsonObject.getString("price").equals("")){
					order.put("price", jsonObject.getString("price"));
				}else if(jsonObject.getString("sales")!=null &&!jsonObject.getString("sales").equals("")){
					order.put("sales", jsonObject.getString("sales"));
				}
			}else{
				order.put("updatePriceTime", "desc");
			}
			ProductQueryBean pqb=new ProductQueryBean();

			pqb.setOrder(order);
//			pqb.setMarketId(queryParams.getMarketId()==null?3L:Long.valueOf(queryParams.getMarketId()));//市场ID
			pqb.setRoleType(queryParams.getRoleType()==null?"4":queryParams.getRoleType());//搜索好货源，必传
			pqb.setProductSign(queryParams.getProductSign());
//			pqb.setCateId(Long.valueOf(queryParams.getCategoryId()));//产品分类
			pqb.setCateIds(queryParams.getCateIds());//多个产品分类
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
			if(pushAdInfoList!=null && pushAdInfoList.size()>0){
				if (queryParams.getMemberId() != null && queryParams.getMemberId().longValue() != 0) {
					for (PushProductDTO productDTO : pushAdInfoList) {
						int platform = productBaseinfoToolService.checkProductActivity(productDTO.getProductId(), queryParams.getMemberId() ,
								null, productDTO.getMarketId());
						productDTO.setPlatform(platform);
					}
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
	
	
}
