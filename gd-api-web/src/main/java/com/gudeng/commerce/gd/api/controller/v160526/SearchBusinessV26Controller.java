package com.gudeng.commerce.gd.api.controller.v160526;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;

/**
 * 农批商搜索
 * @author  
 */
@Controller
@RequestMapping("v26/business")
public class SearchBusinessV26Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SearchBusinessV26Controller.class);
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 


	@RequestMapping("getFacetMarket")
	public void getFacetMarket(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
//			BusinessQueryBean queryParams = (BusinessQueryBean) GSONUtils.fromJsonToObject(jsonStr, BusinessQueryBean.class);
			String keyWord = GSONUtils.getJsonValueStr(jsonStr, "keyWord");
			BusinessQueryBean businessQueryBean=new BusinessQueryBean();
			businessQueryBean.setShopsName(keyWord);
			List<ProductFacetMarketResultDTO> list=businessBaseinfoToolService.getFacetMarket(businessQueryBean);		
			result.setObject(list);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}
	
}
