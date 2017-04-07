package com.gudeng.commerce.gd.api.controller.certif160929;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller("ProductController4V160929")
@RequestMapping("v160929/productCategory/")
public class ProductCategoryController extends GDAPIBaseController  {

	private static final GdLogger logger = GdLoggerFactory.getLogger(ProductCategoryController.class);

	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;

	@RequestMapping("categoryList")
	public void categoryList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String marketId = (String)GSONUtils.getJsonValueStr(jsonStr, "marketId");
			String sortByCategory = (String)GSONUtils.getJsonValueStr(jsonStr, "sortByCategory");
			String businessId = (String)GSONUtils.getJsonValueStr(jsonStr, "businessId");
			if (CommonUtils.isEmpty(marketId)){
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_IS_NULL, request, response);
				return ;
			}
			List<ProductCategoryDTO> res = productToolService.categoryList(marketId);
			if ("1".equalsIgnoreCase(sortByCategory)){
				res = sortByCategory(res, businessId);
			}
			result.setObject(res);
		} catch (Exception e) {
			logger.info("categoryList with ex : ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	private List<ProductCategoryDTO> sortByCategory(List<ProductCategoryDTO> categoryList, String businessId) throws Exception{
		//如果businessId为空则令下面的list为空
		if (CommonUtils.isEmpty(businessId)){
			businessId = "-1";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//businessType 0-主营分类, 1-兼营分类
		map.put("sortName", "businessType");
		map.put("sortOrder", "a");
		map.put("businessId", businessId);
		map.put("startRow", 0);
		map.put("endRow", 10000000);
		//排序后主营为list第一个元素
		List<ReBusinessCategoryDTO> list = reBusinessCategoryToolService.getBySearch(map);
		List<ProductCategoryDTO> prority = new ArrayList<ProductCategoryDTO>();
		ProductCategoryDTO current = null ;
		//遍历主营与兼营分类
		for(int i = 0, len = list.size(); i < len ; i++){
			Iterator<ProductCategoryDTO> it = categoryList.iterator();
			while(it.hasNext()){
				current = it.next();
				if (list.get(i).getCategoryId().longValue() == current.getCategoryId().longValue()){
					prority.add(current);
					//将当前分类(主营或者兼营)从一级分类列表categoryList中移除
					it.remove();
					break ;
				}
			}
		}
		//添加一级分类列表categoryList中的其他元素
		prority.addAll(categoryList);
		return prority;
	}

}
