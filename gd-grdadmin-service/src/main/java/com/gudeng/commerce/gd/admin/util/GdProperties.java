package com.gudeng.commerce.gd.admin.util;

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
	public String getProductUrl() {
		return properties.getProperty("gd.product.url");
	}

	public String getSystemLogUrl() {
		return properties.getProperty("gd.systemlog.url");
	}

	public String getProductPicUrl() {
		return properties.getProperty("gd.productPic.url");
	}
	/**
	 * 市场价格管理调用地址
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 下午7:52:33
	 */
	public String getPricesUrl() {
		return properties.getProperty("gd.prices.url");
	}
	/**
	 * 检测信息管理调用地址
	 * @return
	 * @author 李冬
	 * @time 2015年10月13日 下午7:53:24
	 */
	public String getDetectionUrl() {
		return properties.getProperty("gd.detection.url");
	}

	/** memberBaseinfoService 的地址 */
	public String getMemberBaseinfoUrl() {
		return properties.getProperty("gd.memberBaseinfoService.url");
	}

	/** argTelStatService 的地址 */
	public String getArgTelStatUrl() {
		return properties.getProperty("gd.argTelStatService.url");
	}

	/** giftService 的地址 */
	public String getGiftUrl() {
		return properties.getProperty("gd.giftService.url");
	}

	/** memberCertifiService 的地址 */
	public String getMemberCertifiUrl() {
		return properties.getProperty("gd.memberCertifiService.url");
	}

	/** businessBaseinfoService 的地址 */
	public String getBusinessBaseinfoUrl() {
		return properties.getProperty("gd.businessBaseinfoService.url");
	}


	/** productCategory的地址 */
	public String getProductCategoryUrl() {
		return properties.getProperty("gd.productCategory.url");
	}

	/** friendsLinksService的地址 */
	public String getFriendsLinksUrl() {
		return properties.getProperty("gd.friendsLinksService.url");
	}

	/** ReMemberMarket的地址 */
	public String getReMemberMarketUrl() {
		return properties.getProperty("gd.reMemberMarket.url");
	}

	public String getSystemCodeUrl() {
		return properties.getProperty("gd.systemCode.url");
	}

	/** PushAdInfo的地址 */
	public String getPushAdInfoUrl() {
		return properties.getProperty("gd.pushadinfo.url");
	}

	/** PushNotice的地址 */
	public String getPushNoticeUrl() {
		return properties.getProperty("gd.pushnotice.url");
	}


	/** 农贸市场 */
	public String getFarmersMarketUrl() {
		return properties.getProperty("gd.farmersmarket.url");
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

	/**
	 * 获取系统编码
	 * @author wwj
	 * @return
	 */
	public String getCasSystemCode() {
		return properties.getProperty("CAS_SYSTEM_CODE");
	}

	/** sysLoginService的地址 */
	public String getSysLoginServiceUrl() {
		return properties.getProperty("gd.sysLoginService.url");
	}

	/** sysMenuButtonService的地址 */
	public String getSysMenuButtonServiceUrl() {
		return properties.getProperty("gd.sysMenuButtonService.url");
	}

	/** sysMenuService的地址 */
	public String getSysMenuServiceUrl() {
		return properties.getProperty("gd.sysMenuService.url");
	}

	/** sysParamsService的地址 */
	public String getSysParamsServiceUrl() {
		return properties.getProperty("gd.sysParamsService.url");
	}

	/** sysRegisterUserService的地址 */
	public String getSysRegisterUserServiceUrl() {
		return properties.getProperty("gd.sysRegisterUserService.url");
	}

	/** sysResGroupService的地址 */
	public String getSysResGroupServiceUrl() {
		return properties.getProperty("gd.sysResGroupService.url");
	}

	/** sysRoleManagerService的地址 */
	public String getSysRoleManagerServiceUrl() {
		return properties.getProperty("gd.sysRoleManagerService.url");
	}

	/** sysRoleService的地址 */
	public String getSysRoleServiceUrl() {
		return properties.getProperty("gd.sysRoleService.url");
	}

	/** sysUserRoleService的地址 */
	public String getSysUserRoleServiceUrl() {
		return properties.getProperty("gd.sysUserRoleService.url");
	}


	/** reBusinessMarketService 的地址 */
	public String getReBusinessMarketServiceUrl() {
		return properties.getProperty("gd.reBusinessMarketService.url");
	}

	/** auditInfoUrl 的地址 */
	public String getAuditInfoServiceUrl() {
		return properties.getProperty("gd.auditInfoService.url");
	}


	/**
	 * 获取超级管理员ID
	 * @return
	 *
	 */
	public String getSysSupperAdminId(){

		return properties.getProperty("system.admin.id");
	}

	public String getSysSupperAdminTip(){

		return properties.getProperty("admin.lock.error");
	}


	/** reBusinessCategoryService 的地址 */
	public String getReBusinessCategoryServiceUrl() {
		return properties.getProperty("gd.reBusinessCategoryService.url");
	}


	/** reBusinessCategoryService 的地址 */
	public String getPushRecUrl() {
		return properties.getProperty("gd.pushRecord.url");
	}

	/**
	 * @Description getMarketSaleServiceUrl 市场交易额
	 * @return
	 * @CreationDate 2015年11月10日 下午6:55:39
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public String getMarketSaleServiceUrl() {
		return properties.getProperty("gd.marketSaleService.url");
	}
	/**
	 * 获取积分兑换url
	 * @Author xiaojun
	*/
	public String getMemberIntegralConversionServiceUrl() {
		return properties.getProperty("gd.MemberIntegralConversionService.url");
	}


	/** promotionSourceService 的地址 */
	public String getPromotionSourceUrl() {
		return properties.getProperty("gd.promotionSourceService.url");
	}

	/** 积分流水  */
	public String getIntegralServiceUrl(){
		return properties.getProperty("gd.integral.url");
	}

	/** 活动 */
	public String getActivityServiceUrl(){
		return properties.getProperty("gd.activity.url");
	}

	/**
	 * 订单相关
	 * @return
	 *
	 */
	public String getOrderBaseinfoUrl(){
		return properties.getProperty("gd.OrderBaseinfo.url");
	}
	public String getOrderProductDetailUrl(){
		return properties.getProperty("gd.OrderProductDetail.url");
	}
	public String getPaySerialnumberUrl(){
		return properties.getProperty("gd.PaySerialnumber.url");
	}
	public String getPreSaleDetailUrl(){
		return properties.getProperty("gd.PreSaleDetail.url");
	}
	public String getPreSaleUrl(){
		return properties.getProperty("gd.PreSale.url");
	}

	public String getAuditLogUrl(){
		return properties.getProperty("gd.auditLog.url");
	}

	/** 补贴审核 */
	public String getSubAuditServiceUrl(){
		return properties.getProperty("gd.subAudit.url");
	}
	public String getSubPayRuleServiceUrl(){
		return properties.getProperty("gd.subRule.url");
	}

	/** 提现查询 */
	public String getCashRequestServiceUrl(){
		return properties.getProperty("gd.cashRequestService.url");
	}

	/** 过磅 */
	public String getWeighCarServiceUrl(){
		return properties.getProperty("gd.weighCarService.url");
	}
	/** 会员补贴统计  */
	public String getMemberSubsidyServiceUrl(){
		return properties.getProperty("gd.memberSubsidyService.url");
	}

	/** 补贴HELP */
	public String getSubHelpServiceUrl(){
		return properties.getProperty("gd.subHelpService.url");
	}

	/** 车载重标准 */
	public String getCarWeighProServiceUrl(){
		return properties.getProperty("gd.carWeighProService.url");
	}
	/** 交易账单 */
	public String getOrderBillUrl(){
		return properties.getProperty("gd.orderBillService.url");
	}

	/** area的地址 */
	public String getAreaUrl() {
		return properties.getProperty("gd.area.url");
	}

	/** 账号（钱包）流水的地址 */
	public String getAccTransInfoUrl() {
		return properties.getProperty("gd.accTransInfo.url");
	}


	public String getAccBankCardInfoUrl() {
		return properties.getProperty("gd.accBankCardInfoService.url");
	}
	/** 账号（钱包）的地址 */
	public String getAccInfoUrl() {
		return properties.getProperty("gd.accInfo.url");
	}
	/** area的地址 */
	public String getPreWeighCarDetailServiceUrl() {
		return properties.getProperty("gd.preWeighCarDetailService.url");
	}

	public String getCarBaseinfoServiceUrl() {
		return properties.getProperty("gd.carBaseinfoService.url");
	}


	/** PushOfflineService的地址 */
    public String getPushOfflineServiceUrl() {
        return properties.getProperty("gd.pushOfflineService.url");
    }

	public String getComplaintUrl() {
		return properties.getProperty("gd.complaintService.url");
	}
	public String getReBusinessPosUrl() {
		return properties.getProperty("gd.reBusinessPosService.url");
	}
/**
	 * 获取农速通url服务地址
	 * @return
	 */
	public String getNstNoticeUrl() {
		return properties.getProperty("gd.nstNoticeService.url");
	}

	/**
	 * 获取农速通一手货源分配服务地址
	 * @return
	 */
	public String getnstGoodAssignRuleUrl() {
		return properties.getProperty("gd.nstGoodAssignRule.url");
	}
	/**	 * @Description 广告管理服务
	 * @return
	 * @CreationDate 2016年4月13日 上午11:24:17
	 * @Author lidong(dli@gdeng.cn)
	*/
	public String getAdAdvertiseServiceUrl() {
	    return properties.getProperty("gd.adAdvertiseService.url");
	}
	public String getAdMenuServiceUrl() {
	    return properties.getProperty("gd.adMenuService.url");
	}

	public String getAdSpaceServiceUrl() {
		return properties.getProperty("gd.adSpaceService.url");
	}

	public String getRefCateSupNpsServiceUrl() {
		return properties.getProperty("gd.refCateSupNpsService.url");
	}

	public String getAppVersionServiceUrl() {
		return properties.getProperty("gd.appVersionService.url");
	}


	public String getActGiftBaseinfoServiceUrl() {
		return properties.getProperty("gd.actGiftBaseinfoService.url");
	}

	public String getActActvityBaseinoServiceUrl() {
		return properties.getProperty("gd.actActivityBaseinfoService.url");
	}

	public String getReActvityGiftServiceUrl() {
		return properties.getProperty("gd.reActivityGiftService.url");
	}

	public String getActivityScoreRecordServiceUrl() {
		return properties.getProperty("gd.activityScoreRecordService.url");
	}

	public String getActivityGiftExchangServiceUrl() {
		return properties.getProperty("gd.activityGiftExchangService.url");
	}

	public String getReUserActivityServiceUrl() {
		return properties.getProperty("gd.reUserActivityService.url");
	}

	public String getReActivityGiftServiceUrl() {
		return properties.getProperty("gd.reActivityGiftService.url");
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

	public String getCertifCorpServiceUrl() {
		return properties.getProperty("gd.certifCorpService.url");
	}
	public String getCertifLogServiceUrl() {
		return properties.getProperty("gd.certifLogService.url");
	}

	public String getCertifPersonalServiceUrl() {
		return properties.getProperty("gd.certifPersonalService.url");
	}

	public String getCertifCompanyServiceUrl() {
		return properties.getProperty("gd.certifCompanyService.url");
	}

	public String getCertifBaseServiceUrl() {
		return properties.getProperty("gd.certifBaseService.url");
	}

	public String getCertifSpProductServiceUrl() {
		return properties.getProperty("gd.certifSpProductService.url");
	}
	
	public String getSpecialcharacterServiceUrl() {
		return properties.getProperty("gd.specialcharacterService.url");
	}
	public String getPromBaseinfoServiceUrl() {
		return properties.getProperty("gd.promBaseinfoService.url");
	}
	
	public String getPromProdinfoServiceUrl() {
		return properties.getProperty("gd.promProdinfoService.url");
	}
	
	public String getPromRuleFeeServiceUrl() {
		return properties.getProperty("gd.promRuleFeeService.url");
	}
	public String getCertifShopServiceUrl() {
		return properties.getProperty("gd.certifShopService.url");
	}
	public String getSensitiveWordServiceUrl() {
		return properties.getProperty("gd.sensitiveWordService.url");
	}
	public String getMemberLoginLogServiceUrl() {
		return properties.getProperty("gd.memberLoginLogService.url");
	}
	public String getSensitiveLogServiceUrl() {
		return properties.getProperty("gd.sensitiveLogService.url");
	}
	public String getAppshareServiceUrl() {
		return properties.getProperty("gd.appshareService.url");
	}

	public String getGrdPurchaseServiceUrl() {
		return properties.getProperty("gd.grdPurchaseService.url");
	}


	public String getGrdGdGiftstoreServiceUrl() {
		return properties.getProperty("gd.grdGdGiftstoreService.url");
	}

	public String getGrdGdGiftteamServiceUrl() {
		return properties.getProperty("gd.grdGdGiftteamService.url");
	}

	public String getGrdGdGiftServiceUrl() {
		return properties.getProperty("gd.grdGdGiftService.url");
	}
	public String getAppChannelLinkServiceUrl() {
		return properties.getProperty("gd.appChannelLinkService.url");
	}

	public String getGrdInstorageServiceUrl() {
		return properties.getProperty("gd.grdInstorageService.url");
	}
	
	public String getGrdPurchasegiftServiceUrl(){
		return properties.getProperty("gd.grdPurchasegift.url");
	}
	public String getGrdProMemberInvitedRegisterServiceUrl() {
		return properties.getProperty("gd.grdProMemberInvitedRegisterService.url");
	}
	public String getGrdProPersonalAuthServiceUrl() {
		return properties.getProperty("gd.grdProPersonalAuthService.url");
	}
	public String getGrdProSupplyofgoodHandoutServiceUrl() {
		return properties.getProperty("gd.grdProSupplyofgoodHandoutService.url");
	}
	public String getGrdUserTeamServiceUrl() {
		return properties.getProperty("gd.grdUserTeamService.url");
	}
	public String getGrdProOrderRecievedServiceUrl() {
		return properties.getProperty("gd.grdProOrderRecievedService.url");
	}
	public String getGrdProInfoOrderServiceUrl() {
		return properties.getProperty("gd.grdProInfoOrderService.url");
	}
	public String getGrdProFreightOrderServiceUrl() {
		return properties.getProperty("gd.grdProFreightOrderService.url");
	}
	public String getGrdUserCustomerServiceUrl() {
		return properties.getProperty("gd.grdUserCustomerService.url");
	}
	public String getOrderDeliveryAddressServiceUrl() {
		return properties.getProperty("gd.orderDeliveryAddressService.url");
	}
}
