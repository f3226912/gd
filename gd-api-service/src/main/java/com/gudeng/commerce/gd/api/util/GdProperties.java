package com.gudeng.commerce.gd.api.util;

import java.util.Properties;

/**
 * 参数属性;
 * 
 */
public class GdProperties {

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/** demo的地址 */
	public String getDemoUrl() {
		return properties.getProperty("gd.demo.url");
	}
	/** 获取产品分类的地址 */
	public String getProductCategoryUrl() {
		return properties.getProperty("gd.productCategory.url");
	}
	
	public String getSystemCodeUrl() {
		return properties.getProperty("gd.systemCode.url");
	}
	
	//获取农速通api地址
	public String getNstApiUrl() {
		return getProperties().getProperty("gd_nstApi_url");
	}
	
	//获取谷登会员id
	public String getGdMemberId() {
		return getProperties().getProperty("gd.memberId");
	}
	
	/**
	 * 获取审核信息的地址
	 */
	public String getAuditInfoServiceUrl() {
		return properties.getProperty("gd.auditInfo.url");
	}
	
	/** 获取产品分类的地址 */
	public String getUserProductCategoryUrl() {
		return properties.getProperty("gd.userProductCategory.url");
	}
    	/** 获取用户关注产品分类的地址 */
	public String getProductPicUrl() {
		return properties.getProperty("gd.userProductCategory.url");
	}
	
	/** 获取用户关注产品分类的地址 */
	public String getMemberUrl() {
		return properties.getProperty("gd.member.url");
	}
	
	/**
	 * 产品图片
	 * @return
	 */
	public String getProductPictureUrl() {
		return properties.getProperty("gd.productPic.url");
	}
	/**
	 * add by yanghaoyu
	 * 获取货源管理的地址
	 */
	public String getMemberAddressUrl() {
		return properties.getProperty("gd.memberAddress.url");
	}
	
	public String getUserProduct() {
		return properties.getProperty("gd.userProduct.url");
	}
	
	
	public String getProductUrl() {
		return properties.getProperty("gd.productTool.url");
	}
	/**
	 * add by yanghaoyu
	 * 获取车辆管理的地址
	 */
	public String getCarsManagerUrl() {
		return properties.getProperty("gd.carmanager.url");
	}
	
	public String getBusinessBaseinfoUrl() {
		return properties.getProperty("gd.businessBaseinfo.url");
	}

	public String getCarLineUrl() {
		return properties.getProperty("gd.carLine.url");
	}
	
	public String getMemberCertifiUrl() {
		return properties.getProperty("gd.memberCertifi.url");
	}
	/**商品solr搜索服务**/
	public String getSearchProductServiceUrl() {
		return properties.getProperty("gd.searchProductService.url");
	}
	/**商铺solr搜索服务***/
	public String getSearchBusinessServiceUrl() {
		return properties.getProperty("gd.searchBusinessService.url");
	}
	
	/**
	 * 获取图片上传路径
	 * @return
	 * @author 李冬
	 * @time 2015年10月15日 下午4:13:24
	 */
	public String getFileUploadServiceUrl() {
		return properties.getProperty("gd.fileUploadService.url");
	}
	
	/**
	 * 获取图片上传路径
	 * @return
	 * @author 李冬
	 * @time 2015年10月15日 下午4:13:24
	 */
	public String getFileUploadUrl() {
		return properties.getProperty("gd.fileUpload.url");
	}
	
	/**
	 * 获取图片生成的尺寸
	 * @return
	 * @author 李冬
	 * @time 2015年10月15日 下午7:24:27
	 */
	public String getImgUploadSize() {
		return properties.getProperty("gd.imgSizes");
	}
	
	/** reBusinessCategoryService 的地址 */
	public String getReBusinessCategoryServiceUrl() {
		return properties.getProperty("gd.reBusinessCategoryService.url");
	}

	/**
	 * 车辆类型默认载重地址
	 * @return
	 */
	public String getCarWeighProUrl() {
		return properties.getProperty("gd.carWeighProService.url");
	}

	/**
	 * 过磅服务
	 * @return
	 */
	public String getWeighCarSerivceUrl() {
		return properties.getProperty("gd.weighCarService.url");
	}

	public String getCarBaseinfoServiceUrl() {
		return properties.getProperty("gd.carBaseinfoService.url");
	}

	public String getReCarMemberServiceUrl() {
		return properties.getProperty("gd.reCarMemberService.url");
	}

	public String getPreweighCarDetailUrl() {
		return properties.getProperty("gd.preWeighCarDetail.url");
	}
	public String getInStoreDetailServiceUrl() {
		return properties.getProperty("gd.inStoreDetail.url");
	}

	public String getTaskServiceUrl() {
		return properties.getProperty("gd.taskService.url");
	}
	
	/** reBusinessMarketService 的地址 */
	public String getReBusinessMarketServiceUrl() {
		return properties.getProperty("gd.reBusinessMarketService.url");
	}
	
	/** 积分流水  */
	public String getIntegralServiceUrl(){
		return properties.getProperty("gd.integral.url");
	}
	
	/**
	 * 推送消息
	 */
	public String getPushNstMessageUrl(){
		return properties.getProperty("gd.pushNstMessage.url");
	}
	
	/**
	 * 农速通运单信息
	 */
	public String getNstOrderBaseinfoUrl(){
		return properties.getProperty("gd.nstOrderBaseinfoService.url");
	}

	/**
	 * 专线
	 * @return
	 */
	public String getNstSpecailLineServiceUrl() {
		return properties.getProperty("gd.nstSpecialLineService.url");
	}

	/**
	 * 农速通运单评论
	 * @return
	 */
	public String getNstOrderCommentServiceUrl() {
		return properties.getProperty("gd.nstOrderCommentService.url");
	}

	public String getNstOrderNoServiceUrl() {
		return properties.getProperty("gd.nstOrderNoService.url");
	}

	/**
	 * 农速通运单投诉
	 * @return
	 */
	public String getNstOrderComplaintServiceUrl() {
		return properties.getProperty("gd.nstOrderComplaintService.url");
	}
	
	public String getNst400MailAddr(){
		return properties.getProperty("gd.nst400Mail.addr");
	}

	public String getComplaintUrl() {
		return properties.getProperty("gd.complaintService.url");
	}
	
	/**
	 * 客户信息
	 */
	public String getReMemForCustService() {
		return properties.getProperty("gd.reMemForCustService.url");
	}

	public String getAreaUrl() {
		return properties.getProperty("gd.area.url");
	}
	
	public String getReBusinessPosUrl() {
		return properties.getProperty("gd.reBusinessPosService.url");
	}
	
	/** PushNotice的地址 */
	public String getPushNoticeUrl() {
		return properties.getProperty("gd.pushnotice.url");
	}
	
	public String getAppVersionServiceUrl() {
		return properties.getProperty("gd.appVersionService.url");
	}	
	public String getPaySerialnumberUrl(){
		return properties.getProperty("gd.PaySerialnumber.url");
	}

	/**
	 * 同城发布线路
	 * @return
	 */
	public String getNstSameCityCarlineService() {
		return properties.getProperty("gd.nstSameCityCarlineService.url");
	}

	/** 获取发布同城的地址 */
	public String getNstSameCityAddressUrl() {
		return properties.getProperty("gd.nstSameCityAddressService.url");
	}
	public String getGiftDetailServiceUrl() {
		return properties.getProperty("gd.grdGiftDetailService.url");
	}

	public String getGrdGiftLogServiceUrl() {
		return properties.getProperty("gd.grdGiftLogService.url");
	}

	public String getGrdMemberServiceUrl() {
		return properties.getProperty("gd.grdMemberService.url");
	}

	public String getGrdGiftRecordServiceUrl() {
		return properties.getProperty("gd.grdGiftRecordService.url");
	}

	public String getGrdGiftServiceUrl() {
		return properties.getProperty("gd.grdGiftService.url");
	}
	public String getTrunkAddressServiceUrl() {
		return properties.getProperty("gd.trunkAddressService.url");
	}	
	
	public String getSystemLogServiceUrl(){
		return properties.getProperty("gd.systemLogService.url");
	}
	public String getSpecialcharacterServiceUrl() {
		return properties.getProperty("gd.specialcharacterService.url");
	}
	public String getMemberLoginLogServiceUrl() {
		return properties.getProperty("gd.memberLoginLogService.url");
	}
	public String getSensitiveLogServiceUrl() {
		return properties.getProperty("gd.sensitiveLogService.url");
	}
	public String getAppshareServiceUrl(){
		return properties.getProperty("gd.appshareService.url");
	}
	public String getMyAddressServiceUrl() {
		return properties.getProperty("gd.myAddressService.url");
	}
	public String getDeliveryAddressServiceUrl() {
		return properties.getProperty("gd.deliveryAddressService.url");
	}
	public String getCertifSpProductServiceUrl() {
		return properties.getProperty("gd.certifSpProductService.url");
	}
	public String getCertifBaseServiceUrl() {
		return properties.getProperty("gd.certifBaseService.url");
	}
	public String getCertifCorpServiceUrl() {
		return properties.getProperty("gd.certifCorpService.url");
	}
	public String getCertifCompanyServiceUrl() {
		return properties.getProperty("gd.certifCompanyService.url");
	}
	public String getCertifShopServiceUrl() {
		return properties.getProperty("gd.certifShopService.url");
	}
	public String getGrdProPertenServiceUrl() {
		return properties.getProperty("gd.grdProPertenService.url");
	}
	public String getGrdGdGiftstoreServiceUrl() {
		return properties.getProperty("gd.grdGdGiftstoreService.url");
	}
	
	public String getGdNstJumpUrl() {
		return properties.getProperty("gd_nst_jump_url");
	}
	public String getGdHomeUrl() {
		return properties.getProperty("gd_home_url");
	}
	public String getGrdProMemberInvitedRegisterServiceUrl() {
		return properties.getProperty("gd.grdProMemberInvitedRegisterService.url");
	}
	
	public String getAppactivitystatServiceUrl() {
		return properties.getProperty("gd.appactivitystatService.url");
	}

	public String getProductCategoryServiceUrl() {
		return properties.getProperty("gd.productCategoryService.url");
	}
	
	public String getMemberPageStatisticServiceUrl() {
		return properties.getProperty("gd.memberPageStatisticService.url");
	}
	
	public String getPvStatisticBusinessServiceUrl() {
		return properties.getProperty("gd.pvStatisticBusinessService.url");
	}

	public String getProductBaseinfoServiceUrl() {
		return properties.getProperty("gd.productBaseinfoService.url");
	}
	
	/**
	 * 获取支付私钥
	 * @return
	 */
	public String getPrivateKey(){
        return properties.getProperty("order.pay.privateKey");
    }
	
	/**
	 * 获取支付公钥
	 * @return
	 */
	public String getPublicKey(){
        return properties.getProperty("order.pay.publicKey");
    }
	
	/**
	 * 获取支付version
	 * @return
	 */
	public String getVersion(){
        return properties.getProperty("order.pay.version");
    }
	/**
	 * 获取appkey
	 * @return
	 */
	public String getAppKeyGys(){
        return properties.getProperty("order.pay.appKey_gys");
    }
	/**
	 * 获取appkey
	 * @return
	 */
//	public String getAppKeyGoods(){
//        return properties.getProperty("order.pay.appKey_goods");
//    }
	/**
	 * 超时时间
	 * @return
	 */
	public String getTimeOut(){
        return properties.getProperty("oredr.pay.timeOut");
    }
	/**
	 * 获取同步地址
	 * @return
	 */
	public String getReturnUrl(){
        return properties.getProperty("order.pay.returnUrl");
    }
	/**
	 * 获取异步地址
	 * @return
	 */
//	public String getNotifyUrl(){
//        return properties.getProperty("order.pay.notifyUrl");
//    }
	
	/**
	 * 获取使用什么加密方式
	 * @return
	 */
	public String getKeyType(){
        return properties.getProperty("order.pay.KeyType");
    }
	
	/**
	 * 获取title
	 * @return
	 */
	public String getTitle(){
        return properties.getProperty("order.pay.title");
    }
	/**
	 * 获取支付中心地址
	 * @return
	 */
	public String getCenterPayUrl(){
        return properties.getProperty("center.pay.url");
    }
	
	/**
	 * 获取支付中心api地址
	 * @return
	 */
	public String getCenterPayApiUrl(){
        return properties.getProperty("center.pay.api.url");
    }

	public String getCartInfoServiceUrl() {
		return properties.getProperty("gd.cartInfoService.url");
	}
	public String getGdOrderActivityBaseServiceUrl() {
		return properties.getProperty("gd.gdOrderActivityBaseService.url");
	}

	public String getQuickMakeSheetServiceUrl(){
		
		return properties.getProperty("gd.gdQuickMakeSheetService.url");
	}
	public String getActivityUserintegralServiceUrl() {
		return properties.getProperty("gd.activityUserintegralService.url");
	}
	public String getActivityUserintegralChangeServiceUrl() {
		return properties.getProperty("gd.activityUserintegralChangeService.url");
	}
}