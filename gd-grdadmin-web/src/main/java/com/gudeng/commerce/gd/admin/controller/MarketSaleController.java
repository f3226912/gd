package com.gudeng.commerce.gd.admin.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketSaleService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.supplier.dto.MarketSaleDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 市场价格管理
 * 
 * @author 李冬
 * @time 2015年10月13日 上午10:41:45
 */
@Controller
public class MarketSaleController extends AdminBaseController {
	/** 记录日志 */
	@SuppressWarnings("unused")
	private static final GdLogger logger = GdLoggerFactory.getLogger(MarketSaleController.class);

	@Autowired
	public MarketSaleService marketSaleService;

	/**
	 * 市场价格列表首页
	 * 
	 * @param request
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 上午11:20:50
	 */
	@RequestMapping("marketSale/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("marketSale/index");
		return mv;
	}

	/**
	 * 市场交易额列表数据查找
	 * 
	 * @param request
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 上午11:21:32
	 */
	@RequestMapping("marketSale/query")
	@ResponseBody
	public String query(HttpServletRequest request, MarketSaleDTO marketSaleDTO) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", marketSaleDTO.getMarketId());
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));
			// 设置查询参数
			// 记录数
			map.put("total", marketSaleService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MarketSaleDTO> list = marketSaleService.getMarketSaleDTOList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 打开新增页面
	 * 
	 * @param request
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 下午5:02:22
	 */
	@RequestMapping("marketSale/addDto")
	public ModelAndView addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("marketSale/addDto");
		return mv;
	}

	/**
	 * 保存或者编辑市场交易额
	 * 
	 * @param request
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 上午11:54:24
	 */
	@RequestMapping(value = "marketSale/save", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, MarketSaleDTO marketSaleDTO) {
		try {
			// UserSummary userInfo = getUserInfo(request);// 当前用户
			SysRegisterUser userInfo = (SysRegisterUser) request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			// 在DTO类中，加上一个String类型的birthday，以此实现mybaties保存或者更新到mysql时，Date类型的数据无法insert的问题。
			int i = 0;
			if (marketSaleDTO.getId() != null && marketSaleDTO.getId() > 0) {
				// 根据id判断是否存在，存在即为更新，不存在即为增加
				marketSaleDTO.setUpdateUserId(userInfo.getUserID());// 修改者ID
				marketSaleDTO.setUpdateTime_str(DateUtil.getSysDateTimeString());// 修改时间
				i = marketSaleService.updateMarketSaleDTO(marketSaleDTO);
			} else {
				// 新增
				marketSaleDTO.setCreateUserId(userInfo.getUserID());// 创建者ID
				marketSaleDTO.setCreateTime_str(DateUtil.getSysDateTimeString());// 创建时间
				marketSaleDTO.setRecordDate_str(DateUtil.getSysDateTimeString());// 记录时间
				i = marketSaleService.addMarketSale(marketSaleDTO);
			}
			return i > 0 ? "success" : "failed";
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 打开编辑页
	 * 
	 * @param request
	 * @param id
	 *            记录ID
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 下午5:01:54
	 */
	@RequestMapping("marketSale/edit/{id}")
	public ModelAndView editbyid(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			MarketSaleDTO dto = marketSaleService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {

		}
		mv.setViewName("marketSale/addDto");
		return mv;
	}

	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 下午5:01:37
	 */
	@RequestMapping(value = "marketSale/delete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteById(String ids, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(ids)) {
				return "failed";
			}
			List<String> list = Arrays.asList(ids.split(","));
			int i = marketSaleService.deleteMarketSale(list);
			return i > 0 ? "success" : "failed";
		} catch (Exception e) {

		}
		return null;
	}

}
