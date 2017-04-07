package com.gudeng.commerce.gd.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gudeng.commerce.gd.home.service.PricesToolService;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 市场行情
 * @author xiaodong
 */
@Controller
@RequestMapping("price")
public class MarketPriceController extends HomeBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MarketPriceController.class);
	
	@Autowired
	public PricesToolService pricesToolService;
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView queryPriceList(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 记录数
			int count = pricesToolService.getTotal(map);
			map.put("total", count);
			// 设定分页,排序
			setCommParameters(request, map);
			List<PricesDTO> list = pricesToolService.getByCondition(map);
			ModelAndView mv = new ModelAndView("marketPrice/priceList");
			mv.addObject("list", list);
			mv.addObject("page", request.getParameter("page")!=null ?request.getParameter("page"):1);
			mv.addObject("totalPage", count % 10 == 0 ? count / 10
					: count / 10 + 1);
			mv.addObject("isLogin", checkLogin(request));
			return mv;
		} catch (Exception e) {
			logger.warn("exception :"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	/**
	 * 根据总记录计算出 分页条件起始页 记录总页数 
	 * 
	 * @param request
	 * @param map
	 */
	private void setCommParameters(HttpServletRequest request, Map<String, Object> map){

		//排序字段名称。
		String sort=StringUtils.trimToNull(request.getParameter("sort"));
		//排序顺序
		String sortOrder=StringUtils.trimToNull(request.getParameter("order"));
		//当前第几页
		String page=request.getParameter("page");
		//每页显示的记录数
		String rows=request.getParameter("rows"); 
		// 当前页
		int currentPage = Integer.parseInt((StringUtils.isEmpty(page) || page == "0") ? "1" : page);
		// 每页显示条数
		int pageSize = Integer.parseInt((StringUtils.isEmpty(rows) || rows == "0") ? "10" : rows);
        //每页的开始记录  第一页为1  第二页为number +1   
        int startRow = (currentPage-1)*pageSize;  
        map.put("startRow", startRow);
		map.put("endRow", pageSize);
		map.put("sortName", sort);
		map.put("sortOrder", sortOrder);
	}
}
