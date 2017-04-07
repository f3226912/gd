package com.gudeng.commerce.gd.api.controller.v2;

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
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.InStoreDetailToolService;
import com.gudeng.commerce.gd.api.service.PreWeighCarDetailToolService;
import com.gudeng.commerce.gd.api.service.impl.OrderNoToolServiceImpl;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;


@Controller
@RequestMapping("/v2/hotproduct")
public class HotProductController extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(HotProductController.class);
	
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	private PreWeighCarDetailToolService preWeighCarDetailToolService;
	
	@Autowired
	private InStoreDetailToolService inStoreDetailToolService;
	@Autowired
	private OrderNoToolServiceImpl orderNoToolServiceImpl;

	/**
	 * 通过用户id找到推送给当前用户的热门好货
	 * @param request
	 * @param response
	 * @param PreWeighCarDetailDTO
	 */
	@RequestMapping("getHotProductByUserId")
	public void getShop(HttpServletRequest request, HttpServletResponse response, Long userId){
		ObjectResult result = new ObjectResult();
		try{
			if(userId==null || userId==0){
				setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map =new HashMap<>();
			map.put("userId", userId);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			
			int total = preWeighCarDetailToolService.getCountByUserId(userId);//获取当前用户下的总数
			List<PreWeighCarDetailDTO> listBusiness = preWeighCarDetailToolService.getByBusinessUserId(map);

			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(listBusiness);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]根据userid查找热门好货失败：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 通过产地供应商mobile 找到热门好货
	 * @param request
	 * @param response
	 * @param PreWeighCarDetailDTO
	 */
	@RequestMapping("getHotProductByMobile")
	public void getShop(HttpServletRequest request, HttpServletResponse response, String mobile){
		ObjectResult result = new ObjectResult();
		try{
			Map<String, Object> map =new HashMap<>();
			map.put("mobile", mobile);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = preWeighCarDetailToolService.getCountByMobile(mobile);//获取当前用户下的总数
			List<PreWeighCarDetailDTO> listBusiness = preWeighCarDetailToolService.getByMobile(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(listBusiness);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]根据mobile查找热门好货：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 抢货，进货接口
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("grapGoods")
	public void grapGoods(HttpServletRequest request, HttpServletResponse response, PreWeighCarDetailDTO preWeighCarDetailDTO){
		ObjectResult result = new ObjectResult();
		try{
			if(preWeighCarDetailDTO==null || preWeighCarDetailDTO.getPwdId()==0){
				setResult(result, ErrorCodeEnum.GOODS_ID_IS_NULL, request, response);
				return;
			}
			if(preWeighCarDetailDTO.getWeigh()==null || preWeighCarDetailDTO.getWeigh()<=0){
				setResult(result, ErrorCodeEnum.GOODS_WEIGHT_IS_NULL, request, response);
				return;
			}
			PreWeighCarDetailDTO pwd = preWeighCarDetailToolService.getById(preWeighCarDetailDTO.getPwdId());
			if(pwd.getWeigh()==null || pwd.getWeigh()==0 || preWeighCarDetailDTO.getWeigh() > pwd.getMarginWeigh()){
				//抢货重量大于货物重量，不能下单
				setResult(result, ErrorCodeEnum.GREATER_THAN_GOODS_WIGHT, request, response);
				return;
			}

			Double weigh= pwd.getMarginWeigh()-preWeighCarDetailDTO.getWeigh();
			PreWeighCarDetailDTO updateDto= new PreWeighCarDetailDTO();
			updateDto.setPwdId(pwd.getPwdId());
			updateDto.setMarginWeigh(weigh);
			updateDto.setWeigh(preWeighCarDetailDTO.getWeigh());
			updateDto.setWeighCarId(pwd.getWeighCarId());
			updateDto.setPrice(pwd.getPrice());
			BusinessBaseinfoDTO business = businessBaseinfoToolService.getByUserId(String.valueOf(preWeighCarDetailDTO.getMemberId()));

			Long inStoreNo = new Long(orderNoToolServiceImpl.getOrderNo());//使用订单号的生成规则，生成入库单号
//		     	  热门好货商铺推送，默认销售全部商品
//			   1 产地供应商有100吨，批发商取消购买，直接删除推送商铺的关联表，即此好货不再显示与批发商的好货列表
//			   2 产地供应商有100度，批发商购买了部分，如50吨，减去热门好货的库存后，删除推送商铺的关联表，此好货不再显示与批发商的好货列表
//			
			preWeighCarDetailToolService.grapGoods(updateDto, inStoreNo,business.getBusinessId());
			
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]抢货/进货或生成入库单失败：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 取消进货接口
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("cancelPurchase")
	public void cancelPurchase(HttpServletRequest request, HttpServletResponse response, Long pwdId,Long userId ){
		ObjectResult result = new ObjectResult();
		try{
			if(pwdId==null){
				setResult(result, ErrorCodeEnum.GOODS_ID_IS_NULL, request, response);
				return;
			}
			if(userId==null){
				setResult(result, ErrorCodeEnum.BUSINESS_USER_ID_IS_NULL, request, response);
				return;
			}
			PreWeighCarDetailDTO preWeighCarDetailDTO=preWeighCarDetailToolService.getById(pwdId);
			BusinessBaseinfoDTO business = businessBaseinfoToolService.getByUserId(String.valueOf(userId));

			preWeighCarDetailToolService.deleteBusiness(preWeighCarDetailDTO.getWeighCarId(), business.getBusinessId());
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]取消进货失败：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 取消进货接口
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("getInStoreDetail")
	public void getInStoreDetail(HttpServletRequest request, HttpServletResponse response, String userId ){
		ObjectResult result = new ObjectResult();
		try{
			
			if(userId==null || userId.trim().equals("")){
				setResult(result, ErrorCodeEnum.BUSINESS_USER_ID_IS_NULL, request, response);
				return;
			}
			BusinessBaseinfoDTO business = businessBaseinfoToolService.getByUserId(userId);
			if(business==null ){
				setResult(result, ErrorCodeEnum.BUSINESS_IS_NOT_EXISTED, request, response);
				return;
			}
			Map<String, Object> map =new HashMap<>();
			map.put("businessId", business.getBusinessId());
			CommonPageDTO pageDTO = getPageInfo(request, map);

			List<InStoreDetailDTO> instorelist = inStoreDetailToolService.getByBusinessId(map);
			int total = inStoreDetailToolService.getCountByBusinessId(map);
			
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(instorelist);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取入库单失败：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
}
