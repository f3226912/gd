package com.gudeng.commerce.gd.api.controller.v160630;

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

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.PushProductAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;

/**
 * 用户-关注
 */
@Controller
@RequestMapping("/v30/UserConcern")
public class UserConcernedController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(UserConcernedController.class);

	@Autowired
	private UsercollectProductToolService userConcernedService;
	@Autowired
	private ProductBaseinfoToolService productBaseinfoToolService;


	@RequestMapping("/productsConcerned")
	public void addProduct(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String userid = (String)GSONUtils.getJsonValueStr(jsonStr, "userid");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userid);

			CommonPageDTO pageDTO = getPageInfo(request, jsonStr, params);
			int total = userConcernedService.getProductsCountOfConcerned(params);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);

			List<UsercollectProductDTO> list = userConcernedService.getProductsOfConcerned(params);
			if (!StringUtils.isEmpty(userid)) {
				if(list!=null && list.size()>0){
					for (UsercollectProductDTO productDTO : list) {
						int platform = productBaseinfoToolService.checkProductActivity(productDTO.getProductId(), Long.valueOf(userid),
								null, productDTO.getMarketId());
						productDTO.setPlatform(platform);
					}
				}
			}
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);

		}catch (Exception e) {
			logger.info("获取用户关注的产品列表异常..", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	protected CommonPageDTO getPageInfo(HttpServletRequest request, String jsonStr, Map<String, Object> map){

		//当前第几页
		String page = (String)GSONUtils.getJsonValueStr(jsonStr, "pageNum");
		//每页显示的记录数
		String rows = (String)GSONUtils.getJsonValueStr(jsonStr, "pageSize");
		//当前页
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);
        //每页显示条数
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10":rows);

        CommonPageDTO pageDTO = new CommonPageDTO(currentPage, pageSize);
        map.put(START_ROW, pageDTO.getOffset());
		map.put(END_ROW, pageDTO.getPageSize());
		return pageDTO;
	}
}
