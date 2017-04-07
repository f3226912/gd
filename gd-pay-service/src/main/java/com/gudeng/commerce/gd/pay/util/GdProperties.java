package com.gudeng.commerce.gd.pay.util;

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
}