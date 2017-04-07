package com.gudeng.commerce.gd.notify.util;

import java.util.Properties;

/**
 * 惠农参数属性;
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

	/**
	 * 获取系统编码
	 * @author wwj
	 * @return
	 */
	public String getCasSystemCode() {
		return properties.getProperty("CAS_SYSTEM_CODE");
	}
	/** pushNotice的地址 */
	public String getPushNoticeUrl() {
		return properties.getProperty("gd.pushNoticeService.url");
	}
	/** 用户-活动 */
	public String getReUserActivity() {
		return properties.getProperty("gd.reUserActivity.url");
	}
	/** 礼品-活动 */
	public String getReActivityGift() {
		return properties.getProperty("gd.reActivityGift.url");
	}
	/** businessBaseinfoService 的地址 */
	public String getBusinessBaseinfoUrl() {
		return properties.getProperty("gd.businessBaseinfoService.url");
	}
	/** reBusinessCategoryService 的地址 */
	public String getReBusinessCategoryServiceUrl() {
		return properties.getProperty("gd.reBusinessCategoryService.url");
	}
	/** productCategory的地址 */
	public String getProductCategoryUrl() {
		return properties.getProperty("gd.productCategoryService.url");
	}
	/** memberBaseinfoService 的地址 */
	public String getMemberBaseinfoUrl() {
		return properties.getProperty("gd.memberBaseinfoService.url");
	}
	/** reBusinessMarketService的地址 */
	public String getReBusinessMarketServiceUrl() {
		return properties.getProperty("gd.reBusinessMarketService.url");
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
    /** areaService的地址 */
    public String getAreaServiceUrl() {
        return properties.getProperty("gd.area.url");
    }

    public String getProductPicUrl() {
        return properties.getProperty("gd.productPic.url");
    }

    public String getProductServiceUrl() {
        return properties.getProperty("gd.product.url");
    }
    public String getImgHostServiceUrl() {
        return properties.getProperty("img.host.url");
    }

    public String getWXAppId() {
        return properties.getProperty("gd.weixin.appId");
    }
    public String getWXAppSecret() {
        return properties.getProperty("gd.weixin.appSecret");
    }

    public String getNstOrderBaseinfoServiceUrl() {
        return properties.getProperty("gd.nstOrderBaseinfoService.url");
    }
    public String getMemberAddressServiceUrl() {
        return properties.getProperty("gd.memberAddressService.url");
    }

	public String getProductDeliveryDetailServiceUrl() {
		return properties.getProperty("gd.productDeliveryDetailService.url");
	}
	public String getActWechatInviteServiceUrl() {
	    return properties.getProperty("gd.actWechatInviteService.url");
	}
	public String getActWechatShareServiceUrl() {
	    return properties.getProperty("gd.actWechatShareService.url");
	}
	public String getActActivityBaseinfoServiceUrl() {
	    return properties.getProperty("gd.actActivityBaseinfoService.url");
	}
	public String getUsercollectProductServiceUrl() {
		return properties.getProperty("gd.usercollectProductService.url");
	}

	public String getTaskServiceUrl() {
		return properties.getProperty("gd.taskService.url");
	}
	
	public String getPayResultServiceUrl() {
		return properties.getProperty("gd.payResultService.url");
	}
	
	public String getPvStatisticBusinessServiceUrl() {
		return properties.getProperty("gd.pvStatisticBusinessServiceUrl.url");
	}

	public String getMemberUrl() {
		return properties.getProperty("gd.member.url");
	}
	public String getMemberChangeLogUrl() {
		return properties.getProperty("gd.memberChangeLogService.url");
	}
	public String getMemberMessageFlagUrl() {
		return properties.getProperty("gd.memberMessageFlagService.url");
	}

	// 获取农速通api地址
	public String getApiUrl() {
		return getProperties().getProperty("gd_api_url");
	}
}
