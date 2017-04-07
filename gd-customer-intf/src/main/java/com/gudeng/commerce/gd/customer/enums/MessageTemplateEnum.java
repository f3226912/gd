package com.gudeng.commerce.gd.customer.enums;

/**
 * 短信模板枚举类
 * @author TerryZhang
 *
 */
public enum MessageTemplateEnum {

	/**
	 * 浏览量达标后短信提醒
	 */
	PVSTATISTOP("亲爱的{P}：\r\t\r\t祝贺您发布的商品信息浏览量突破{D}次！现在更新商品信息，可以获取更多全国农批市场农批商关注与交易机会哟，赶紧行动吧！"),
	/**
	 * 金牌会员支付成功后短信提醒
	 */
	PAYSUCCESS("亲爱的{P}：\r\t\r\t恭喜您成为谷登平台金牌供应商会员，即刻起您可享受：随时了解供应行情、精准买家匹配和推荐、专属客服等服务，更尊享全国农批市场驻场人员交易撮合服务，为您带来更多交易机会，感谢您的支持！"),
	
	/**
	 * 自动注册
	 */
	FORGET_PAY_PASSWORD("验证码:{P}，请及时输入以完成新支付密码设置。【谷登科技】"),
	
	/**
	 * 广告上线通知
	 */
	ADVERTONLINE("亲爱的{P}：\r\t\r\t您的农产品已登上谷登平台广告推荐首页，商品信息已面向谷登平台全国农批市场的农批商展示，足不出户，生意上门。谷登电商，中国领先的O2O农业电商平台。");

	
	MessageTemplateEnum(String template){
		this.template = template;
	}
	
	private final String template;
	
	public String getTemplate(){
		return template;
	}
}
