package com.gudeng.commerce.gd.m.controller.act;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.controller.data.DataReportController;
import com.gudeng.commerce.gd.m.service.AdToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年9月23日 下午4:06:44
 */
@Controller
@RequestMapping("advert")
public class AdvertController extends MBaseController {
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(DataReportController.class);
	
	@Autowired
	private AdToolService adToolService;
	
	@Autowired
	public GdProperties gdProperties;
	
	@RequestMapping("showIndex/{menuSign}")
	public String showBill(@PathVariable("menuSign") String menuSign,HttpServletRequest request)
			throws Exception{
		logger.info("menuSign=" + menuSign);
		String imgRootUrl = gdProperties.getProperties().getProperty("img.host.url");
		request.setAttribute("imgRootUrl", imgRootUrl);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("menuSign", menuSign);
		List<AdAdvertiseDTO> adAdvertises = adToolService.queryAdvertiseList(params);
		request.setAttribute("adAdvertises", adAdvertises);
		if (CollectionUtils.isNotEmpty(adAdvertises)) {
			request.setAttribute("adName", adAdvertises.get(0).getAdName());
		}
		return "H5/advert/index";
	}

}
