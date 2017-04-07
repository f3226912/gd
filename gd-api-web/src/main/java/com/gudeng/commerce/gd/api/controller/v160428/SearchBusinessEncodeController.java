package com.gudeng.commerce.gd.api.controller.v160428;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;

/**
 * 农批商搜索
 * @author  
 */
@Controller
@RequestMapping("v28/business")
public class SearchBusinessEncodeController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SearchBusinessEncodeController.class);
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 

	
	@RequestMapping("searchByBusinessNameEncode")
	public void searchByBusinessName(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		
		try{
			String jsonStr = getParamDecodeStr(request);
			BusinessBaseinfoDTO businessBaseinfoDTO = (BusinessBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, BusinessBaseinfoDTO.class);
			
			BusinessQueryBean businessQueryBean=new BusinessQueryBean();
			businessQueryBean.setCategoryId(businessBaseinfoDTO.getCategoryId()==null?null:businessBaseinfoDTO.getCategoryId().intValue());
			businessQueryBean.setMarketId(Integer.valueOf(businessBaseinfoDTO.getMarketId()));
			businessQueryBean.setShopsName(businessBaseinfoDTO.getKeyWord());
			businessQueryBean.setMainProduct(businessBaseinfoDTO.getKeyWord());
			businessQueryBean.setLevel(businessBaseinfoDTO.getLevel());
			businessQueryBean.setProvinceId(String.valueOf(businessBaseinfoDTO.getProvinceId()));
			businessQueryBean.setCityId(String.valueOf(businessBaseinfoDTO.getCity()));
			businessQueryBean.setAreaId(String.valueOf(businessBaseinfoDTO.getAreaId()));
			if(businessBaseinfoDTO.getLevel()!=null && businessBaseinfoDTO.getLevel()!=0){
				businessQueryBean.setManagementType(businessBaseinfoDTO.getManagementType());
			}
			Long userId=businessBaseinfoDTO.getUserId();
			
			//当前第几页
//			String page=request.getParameter("pageNum");
			String page=GSONUtils.getJsonValueStr(jsonStr, "pageNum");
			//每页显示的记录数
//			String pageSize=request.getParameter("pageSize"); 
			String pageSize=GSONUtils.getJsonValueStr(jsonStr, "pageSize");

			Integer startRow=0;
			Integer rows=10;
			
			if(pageSize==null || "".equals(pageSize)){
	//			logger.warn("获取产品列表异常", e);
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
			businessQueryBean.setStartRow(startRow);
			businessQueryBean.setPageSize(rows);
			
	        CommonPageDTO pageDTO = new CommonPageDTO(Integer.valueOf(page), Integer.valueOf(pageSize));
	        
			BusinessSearchResultDTO bresult=businessBaseinfoToolService.getByBusinessQueryBean(businessQueryBean);
			List<BusinessAppDTO> businessAppList=new ArrayList<BusinessAppDTO>();
			
			businessAppList=businessBaseinfoToolService.translate(bresult.getList(),businessAppList,userId);
			pageDTO.setRecordCount(bresult.getCount());//设置翻页
			pageDTO.initiatePage(bresult.getCount().intValue());
			pageDTO.setRecordList(businessAppList);
 			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("获取农批商搜索列表", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	 
	
}
