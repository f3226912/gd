package com.gudeng.commerce.gd.home.util;

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
	
	/** 文件上传的地址 */
	public String getFileUploadServiceUrl() {
		return properties.getProperty("gd.fileUploadService.url");
	}
	public String getFileUploadUrl() {
		return properties.getProperty("gd.fileUpload.url");
	}
	/**
	 * 获取图片生成的尺寸
	 * @return
	 */
	public String getImgUploadSize() {
		return properties.getProperty("gd.imgSizes");
	}
	
	/** BusinessBaseinfoService的地址 */
	public String getBusinessBaseinfoServiceUrl() {
		return properties.getProperty("gd.businessBaseinfoService.url");
	}
	
	/** businessProducttypeService的地址 */
	public String getBusinessProducttypeServiceUrl() {
		return properties.getProperty("gd.businessProducttypeService.url");
	}
	
	/** productService的地址 */
	public String getProductServiceUrl() {
		return properties.getProperty("gd.productService.url");
	}
	
	/** usercollectShopService的地址 */
	public String getUsercollectShopServiceUrl() {
		return properties.getProperty("gd.usercollectShopService.url");
	}
	
	/** productCategory的地址 */
	public String getProductCategoryUrl() {
		return properties.getProperty("gd.productCategory.url");
	}
	public String getProductUrl() {
		return properties.getProperty("gd.product.url");
	}
	
	public String getProductPicUrl() {
		return properties.getProperty("gd.productPic.url");
	}
	public String getPushOffLineUrl() {
		return properties.getProperty("gd.pushOfflineService.url");
	}
	public String getPromotionStatistics() {
		return properties.getProperty("gd.promotionStatistics.url");
	}
	
	/** detectionService的地址 */
	public String getDetectionServiceUrl() {
		return properties.getProperty("gd.detectionService.url");
	}
	
	/** memberBaseinfoService的地址 */
	public String getMemberBaseinfoUrl() {
		return properties.getProperty("gd.memberBaseinfoService.url");
	}
	
	/** reBusinessMarketService的地址 */
	public String getReBusinessMarketServiceUrl() {
		return properties.getProperty("gd.reBusinessMarketService.url");
	}
	
	/** reBusinessCategoryService 的地址 */
	public String getReBusinessCategoryServiceUrl() {
		return properties.getProperty("gd.reBusinessCategoryService.url");
	}
	
	/** ProCategoryToolService的地址 */
	public String getProCategoryToolServiceUrl() {
		return properties.getProperty("gd.proCategoryToolService.url");
	}
	
	/** marketService的地址 */
	public String getMarketServiceUrl() {
		return properties.getProperty("gd.marketService.url");
	}
	
	/** areaService的地址 */
	public String getAreaServiceUrl() {
		return properties.getProperty("gd.area.url");
	}
	/**商品solr搜索服务**/
	public String getSearchProductServiceUrl() {
		return properties.getProperty("gd.searchProductService.url");
	}
	/**商铺solr搜索服务***/
	public String getSearchBusinessServiceUrl() {
		return properties.getProperty("gd.searchBusinessService.url");
	}
	
	/**关注产品服务**/
	public String getUsercollectProductServiceUrl() {
		return properties.getProperty("gd.usercollectProductService.url");
	}
	
	/**公告服务**/
	public String getPushNoticeServiceUrl() {
		return properties.getProperty("gd.pushNoticeService.url");
	}
	
	/**广告服务**/
	public String getPushAdInfoServiceUrl() {
		return properties.getProperty("gd.pushAdInfoService.url");
	}
	
	/**用户认证服务**/
	public String getMemberCertifiServiceUrl() {
		return properties.getProperty("gd.memberCertifiService.url");
	}
	
	/**农市价格**/
	public String getPricesUrl() {
		return properties.getProperty("gd.priceService.url");
	}
	
	/**农市价格**/
	public String getReCategoryBanelImgServiceUrl() {
		return properties.getProperty("gd.categorybanelimg.url");
	}
	/**
	 * @Description getSystemCodeServiceUrl 数据字典服务
	 * @return
	 * @CreationDate 2015年11月9日 上午9:38:36
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public String getSystemCodeServiceUrl() {
		return properties.getProperty("gd.systemCodeService.url");
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
	
	public String getSystemLogUrl() {
		return properties.getProperty("gd.systemlog.url");
	}
	/** friendsLinksService的地址 */
	public String getFriendsLinksUrl() {
		return properties.getProperty("gd.friendsLinksService.url");
	}
	
	public String getIpAddressLogUrl(){
		return properties.getProperty("gd.ipAddressLogService.url");
	}
	
	public String getIpAddressBlackUrl(){
		return properties.getProperty("gd.ipAddressBlackService.url");
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
}
