package com.gudeng.commerce.gd.m.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifSpProductDTO;
import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.Constant.APP_TYPE;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.enums.ResponseCodeEnum;
import com.gudeng.commerce.gd.m.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.ProductToolService;
import com.gudeng.commerce.gd.m.service.PromChainToolService;
import com.gudeng.commerce.gd.m.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.m.service.statistic.PvStatisticToolService;
import com.gudeng.commerce.gd.m.util.CommonUtil;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.HtmlUtil;
import com.gudeng.commerce.gd.m.util.MoneyUtil;
import com.gudeng.commerce.gd.m.util.ObjectResult;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdResult;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;


/**
 * @Description H5产品
 * @Project gd-m-web
 * @ClassName ProductController.java
 * @Author liufan
 * @CreationDate 2016年6月12日 下午5:14:22
 * @Version 
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 * 
 */
@Controller
@RequestMapping("product")
public class ProductController extends MBaseController {
	  private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	  @Autowired
	  public UsercollectProductToolService usercollectProductToolService;
	  @Autowired
	  public MemberBaseinfoToolService memberBaseinfoToolService;
	
	  @Autowired
	  public ProductToolService productToolService;
	  @Autowired
	  public GdProperties gdProperties;
	  @Autowired
	  private CallstatiSticsToolService callService;
	  @Autowired
	  private BusinessBaseinfoToolService businessBaseinfoToolService;
	  @Autowired
	  public PromChainToolService promChainToolService;
	  @Autowired
	  public PvStatisticToolService pPvStatisticToolService;
	 /**
     * @Description 查询商品详情
     * @param productId
     * @param request
     * @return
     * @CreationDate 2016年6月13日 下午5:26:11
     * @Author liufan
     */
    @RequestMapping(value = "getProductByProdId")
    public String getProductByProdId(String productId,String fromCode,String userId,String flag, String hasGotoShop,Integer platform, HttpServletRequest request) {
    	logger.info("productId=" + productId + ",fromCode=" + fromCode + ",userId=" + userId + ",flag=" + flag);
    	ProductDto productDto = null;
    	Integer userGrade = 0; //默认用户非黄金会员
    	try {
    		String gdBusinessId = gdProperties.getProperties().getProperty("gudeng_business_id");
        	request.setAttribute("gdBusinessId", gdBusinessId);
        	if (StringUtils.isNotBlank(hasGotoShop)) {
        		request.setAttribute("hasGotoShop", hasGotoShop);
        	}
        	Map<String, Object> param = new HashMap<String, Object>();
        	//查询特定商品的“地理标志产品认证”状态,  0:待审核1:已认证;2:已驳回, 此处查询已认证的状态
        	param.put("certifStatus", 1);
        	param.put("productId", productId);
        	//
        	param.put("pictureType", 4);
        	param.put("startRow", 0);
        	param.put("endRow", 10);
        	//地理标志产品认证状态
        	List<CertifSpProductDTO> plist = productToolService.getCertifOfSpProduct(param);
        	if (plist == null || plist.isEmpty() || plist.size() > 1){
        		request.setAttribute("productCertifStatus", -1);
        	}else{
        		request.setAttribute("productCertifStatus", plist.get(0).getStatus());
        	}
        	
        	//查询商品基本信息
        	 productDto = productToolService.getProdDetailByProdId(productId);
        	 if(null != productDto){
 				PromProdResult promProdResult = promChainToolService.getPromProdResult(productDto.getProductId());
 				if(promProdResult != null) {
 					logger.info("productId=" + productId + "是促销产品");
 					PromProdInfoDTO promProdInfoDTO = promProdResult.getPromProdInfoDTO();
 					if(promProdInfoDTO != null && promProdInfoDTO.getActPrice() > 0){
 						productDto.setPrice(promProdInfoDTO.getActPrice());
 						productDto.setPromotion(1);
 						PromBaseinfoDTO promBaseinfoDTO = promProdResult.getPromBaseInfo();
 						productDto.setPromotionStartTime(DateFormatUtils.format(promBaseinfoDTO.getStartTime(), "yyyy/MM/dd HH:mm:ss"));
 						productDto.setPromotionEndTime(DateFormatUtils.format(promBaseinfoDTO.getEndTime(), "yyyy/MM/dd HH:mm:ss"));
 					}
 				}
 				
				productDto.setFormattedPrice(MoneyUtil.format(productDto.getPrice()));
				
				//查询此商铺的全部商品
				if(null != productDto.getBusinessId()){
					Map<String,Object> businessMap = new HashMap<String,Object>();
					businessMap.put("businessId", productDto.getBusinessId());
					int count = productToolService.getShopsProductTotal(businessMap);
					putModel("productCount", count);
				}
				//查询图片(2代表查询多角度图片)
				List<ProductPictureDto> picList = productToolService.getPictureByProdIdAndType(productId,Constant.PICTURE_MUTIPLE);
				productDto.setPictures(picList);
	            putModel("productDto", productDto);
	            putModel("imgHostUrl", gdProperties.getImgHostServiceUrl());
	            //如果是直接从分享链接中点击进来的，不存在userId,不计入打电话统计，不查询是否关注
				if(StringUtils.isNotBlank(userId)){
					productDto.setMemberId(Long.valueOf(userId));
					// 查询是否已关注
					UsercollectProductDTO usDTO = usercollectProductToolService.getCollect(Long.valueOf(userId), Long.valueOf(productId));
					if(null != usDTO){
						//1代表已关注
						productDto.setHasFocused(1);
					}
					//查找用户是否黄金会员 --add by TerryZhang
					MemberBaseinfoDTO memberInfo = memberBaseinfoToolService.getById(userId);
					if(memberInfo != null && memberInfo.getMemberGrade() != null && memberInfo.getMemberGrade() == 1){
						userGrade = 1;
					}
				}
	           
				putModel("userGrade", userGrade);
	            putModel("fromCode", fromCode);
	            putModel("flag",flag);
	            //处理分享时需要的descript,去掉标签
	            if(StringUtils.isNotBlank(productDto.getDescription())){
	            	String dealDes = HtmlUtil.getTextFromHtml(productDto.getDescription());
	            	 putModel("dealDes", dealDes);
	            }
	            //处理分享时需要的价格加单位
	            String priceSign = "";
	            if(!"面议".equals(productDto.getFormattedPrice())){
	            	priceSign = productDto.getFormattedPrice()+"元/"+productDto.getUnitName();
	            }else{
	            	priceSign = productDto.getFormattedPrice();
	            }
	            putModel("priceSign", priceSign);
	            //处理商品名中的特殊符号\n\r
	            if(StringUtils.isNotBlank(productDto.getProductName())){
	            	productDto.setProductName(getDealStr(productDto.getProductName()));
	            }
	            //处理商品主体产品中的特殊符号\n\r
	            if(StringUtils.isNotBlank(productDto.getMainProduct())){
	            	productDto.setMainProduct(getDealStr(productDto.getMainProduct()));
	            }
	            //处理店铺名中的特殊符号\n\r
	            if(StringUtils.isNotBlank(productDto.getShopsName())){
	            	productDto.setShopsName(getDealStr(productDto.getShopsName()));
	            }
	         	//处理商品的库存数量，如果大于等于1000则显示大量，否则显示实际的值
	            String dealStockCount = "";
	            if(null != productDto.getStockCount()){
	            	if(productDto.getStockCount().compareTo(new Double("1000"))>=0){
	            		dealStockCount= "大量";
	            	}else{
	            		dealStockCount = productDto.getStockCount()+productDto.getUnitName();
	            	}
	            }
	            putModel("dealStockCount", dealStockCount);
	            putModel("stockCount", productDto.getStockCount());
        	 }
        	 //查询店铺信息(名称, 店铺下的商品总数)以及店铺相关的认证信息(企业、基地、合作社)
        	 Map<String, Object> params = new HashMap<String, Object>();
        	 params.put("businessId", productDto.getBusinessId());
        	 BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getBusinessShortInfoBySearch(params);
        	 params.put("memberId", businessBaseinfoDTO.getUserId());
        	 Map<String, Object>  certifResult = businessBaseinfoToolService.getCertifForBusinessBySearch(params);
        	 businessBaseinfoDTO.setCbstatus("0");
        	 businessBaseinfoDTO.setCcstatus(String.valueOf(certifResult.get("ccstatus")));
        	 businessBaseinfoDTO.setComstatus(String.valueOf(certifResult.get("comstatus")));
        	 //设置商铺是否金牌供应商
        	 String memberGrade = StringUtils.isBlank(businessBaseinfoDTO.getMemberGrade()) ? "0" :businessBaseinfoDTO.getMemberGrade(); 
        	 businessBaseinfoDTO.setMemberGrade(memberGrade);
        	 putModel("business", businessBaseinfoDTO);
        	 putModel("platform", platform);
        	 
        	//增加浏览量
        	 if(StringUtils.isNotBlank(userId) && userId.equals(businessBaseinfoDTO.getUserId().toString())){
        		 logger.info("User is browsing his own shop, userId: " + userId);
        	 }else{
        		 pPvStatisticToolService.addPvForProduct(Long.parseLong(productId), CommonUtil.getNumFromRange(1, 4));
        	 }
     		
    	} catch (Exception e) {
        	logger.info("显示商品详情单失败", e);
            e.printStackTrace();
        }
        if(productDto != null && productDto.getPromotion() == 1) {
        	 return "H5/marketing/gys-article";
        } else {
	        switch (fromCode) {
		        //1代表农商友
		        case APP_TYPE.APP_TYPE_NSY:
		        	return "H5/product/nsyproduct-detail";
		        //3代表农批商
		        case APP_TYPE.APP_TYPE_NPS:
		        	return "H5/product/npsproduct-detail";
		        //5代表供应商
		        case APP_TYPE.APP_TYPE_GYS:
			        return "H5/product/gysproduct-detail";
		        default:
		        	//查询商品详情页面(无打电话功能)
		        	return "H5/product/product-detail";
		        }
        }
      
    }
    
    //处理字符串中特殊字符
    public String getDealStr(String str){
    	String dealStr = str.replaceAll("[\n\r]","");
    	return dealStr;
    }
    
    /**
     * 增加电话统计记录
     * @param request
     * @param response
     * @param callstatiSticsDTO
     */
    @RequestMapping("addCall")
	@ResponseBody
	public void addCall(HttpServletRequest request, HttpServletResponse response, CallstatiSticsDTO callstatiSticsDTO) {
    	ObjectResult result = new ObjectResult();
    	try {
			Long memberId = callstatiSticsDTO.getMemberId();
			logger.info("memberId=" + memberId);
			if(memberId != null){
				MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(memberId));
				if (member == null) {
					result.setStatusCode(ErrorCodeEnum.ACCOUNT_NOT_EXISTED.getStatusCode());
					result.setMsg(ErrorCodeEnum.ACCOUNT_NOT_EXISTED.getStatusMsg());
				}else{
					callstatiSticsDTO.setE_Mobile(member.getMobile());
					callstatiSticsDTO.setE_Name(member.getRealName());
					callService.insert(callstatiSticsDTO);
	
					//填充结果集
					result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
					result.setMsg("success");
				}
			}			
		} catch (Exception e) {
			logger.info("添加电话记录失败", e);
			e.printStackTrace();
		}
		//发送JSON数据
		renderJson(result, request, response);
	}

    
    /**
	 * 用于关注产品 ajax
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("focus/{pid}")
	public String focusProduct(@PathVariable String pid,String userId) throws Exception {
		try {
			if (StringUtils.isNotBlank(userId)) {
				//判断您是否已经收藏
				UsercollectProductDTO usDTO = usercollectProductToolService.getCollect(Long.valueOf(userId), Long.valueOf(pid));
				//未收藏添加
				if(usDTO==null) {
					usercollectProductToolService.addFocus(Long.valueOf(userId),Long.valueOf(pid),null);	
				}
			}
		} catch (Exception e) {
			logger.info("关联失败", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
		return getResponseJson(ResponseCodeEnum.OPERATION_SUCCESS);
	}
	
	/**
	 * 用于取消关注产品 ajax
	 * @param bid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("blur/{pid}")
	public String blurProduct(@PathVariable String pid,String userId) throws Exception {
		try {
			if (StringUtils.isNotBlank(userId)) {
				usercollectProductToolService.cancelFocus(Long.valueOf(userId), Long.valueOf(pid));
			}
		} catch (Exception e) {
			logger.info("取消关联失败", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
		return getResponseJson(ResponseCodeEnum.OPERATION_SUCCESS);
	}
	
}
