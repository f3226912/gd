package com.gudeng.commerce.gd.api.controller.v160428;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;

/**
 * 商品搜索
 * @author  
 */
@Controller
@RequestMapping("/v28/product")
public class SearchProductEncodeController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SearchProductEncodeController.class);
	
	@Autowired
	public ProductToolService productToolService;
	@Autowired
	public ProductBaseinfoToolService productBaseinfoToolService;

	
	@RequestMapping("searchByProductNameEncode")
	public void searchByEncodeProductName(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();

		try{
			ProductQueryBean productQueryBean=new ProductQueryBean();
			PushAdInfoDTO pushAdInfoDTO=new PushAdInfoDTO();
			String jsonStr = getParamDecodeStr(request);
			pushAdInfoDTO = (PushAdInfoDTO) GSONUtils.fromJsonToObject(jsonStr, PushAdInfoDTO.class);

			productQueryBean.setName(pushAdInfoDTO.getKeyWord());
//			String roleType=request.getParameter("roleType");
//			String orderMapStr=request.getParameter("orderMap");
			String roleType=GSONUtils.getJsonValueStr(jsonStr, "roleType");
			String orderMapStr=GSONUtils.getJsonValueStr(jsonStr, "orderMap");
			Map<String, String> order = new HashMap<>();
			
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
			productQueryBean.setOrder(order);
			
			
			if(roleType!=null && roleType.equals("4")){
				productQueryBean.setMarketId(3L);
				productQueryBean.setRoleType("4");
			}else{
				productQueryBean.setMarketId(Long.valueOf(pushAdInfoDTO.getMarketId()));
				productQueryBean.setRoleType("1");
			}
//			productQueryBean.setMarketId(Long.valueOf(pushAdInfoDTO.getMarketId()));
//			productQueryBean.setRoleType("1");
//			String managementType=request.getParameter("managementType");
			String managementType=GSONUtils.getJsonValueStr(jsonStr, "managementType");

			if(managementType!=null && !managementType.trim().equals("")){
				productQueryBean.setManagementType(Integer.valueOf(managementType));
			}
//			String categoryId=request.getParameter("categoryId");
			String categoryId=GSONUtils.getJsonValueStr(jsonStr, "categoryId");
			if(categoryId!=null && !categoryId.equals("")){
				productQueryBean.setCateId(Long.valueOf(categoryId));
			}
			String provinceId=GSONUtils.getJsonValueStr(jsonStr, "provinceId");
			String cityId=GSONUtils.getJsonValueStr(jsonStr, "cityId");
			String areaId=GSONUtils.getJsonValueStr(jsonStr, "areaId");
			
			productQueryBean.setProvinceId((provinceId==null||provinceId.equals(""))?null:Long.valueOf(provinceId));
			productQueryBean.setCityId((cityId==null||cityId.equals(""))?null:Long.valueOf(cityId));
			productQueryBean.setAreaId((areaId==null||areaId.equals(""))?null:Long.valueOf(areaId));
			
			//当前第几页
//			String page=request.getParameter("pageNum");
			String page=GSONUtils.getJsonValueStr(jsonStr, "pageNum");
			//每页显示的记录数
//			String pageSize=request.getParameter("pageSize"); 
			String pageSize=GSONUtils.getJsonValueStr(jsonStr, "pageSize");
			Integer startRow=0;
			Integer rows=10;
			
			if(pageSize==null || "".equals(pageSize)){
//				logger.warn("获取产品列表异常", e);
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGESIZE_IS_NULL, request, response);	
				return;
			}else{
				rows=Integer.valueOf(pageSize);
			}
			if(page==null || "".equals(page)){
				//抛出异常
				setEncodeResult(result, ErrorCodeEnum.PRODUCT_PAGENUM_IS_NULL, request, response);
				return;
			}else{
				startRow=(Integer.valueOf(page)-1)*rows;
			}
			productQueryBean.setStartRow(startRow);
			productQueryBean.setPageSize(rows);
			
	        CommonPageDTO pageDTO = new CommonPageDTO(Integer.valueOf(page), Integer.valueOf(pageSize));
	        
        
    	
		
			ProductSearchResultDTO presult = productToolService.getByProductQueryBean(productQueryBean);
			List<PushProductDTO> pushAdInfoList=new ArrayList<PushProductDTO>();
			
			pushAdInfoList=productToolService.translate(presult.getList(),pushAdInfoList);
			if(pushAdInfoList!=null && pushAdInfoList.size()>0){
				if (pushAdInfoDTO.getMemberId() != null && pushAdInfoDTO.getMemberId().longValue() != 0) {
					for (PushProductDTO productDTO : pushAdInfoList) {
						int platform = productBaseinfoToolService.checkProductActivity(productDTO.getProductId(), pushAdInfoDTO.getMemberId() ,
								null, productDTO.getMarketId());
						productDTO.setPlatform(platform);
					}
				}
			}
			
			
			pageDTO.setRecordCount(presult.getCount());//设置翻页
			pageDTO.initiatePage(presult.getCount().intValue());
			
			pageDTO.setRecordList(pushAdInfoList);
			result.setObject(pageDTO);
			
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取产品列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	 
	
}
