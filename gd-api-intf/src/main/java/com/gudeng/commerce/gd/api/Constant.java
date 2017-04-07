package com.gudeng.commerce.gd.api;


/**
 *功能描述：常量的定义
 */
public class Constant {
	public final static class ActStatus{
		public static String STATUS = "act_status";
		/**保存成功*/
		public static String SAVE_SUCCESS="sv_sucs";
		/**
		 * 更新成功
		 */
		public static String UPDATE_SUCCESS="up_sucs";
		/**
		 * 删除成功
		 */
		public static String DELETE_SUCCESS="del_sucs";
		/**
		 * 操作成功
		 */
		public static String HANDLE_SUCCESS="hd_sucs";
	}
	
	public final static class ActResult{
		/**保存成功*/
		public static String SAVE_SUCCESS="保存成功!";
		/**
		 * 更新成功
		 */
		public static String UPDATE_SUCCESS="更新成功!";
		/**
		 * 删除成功
		 */
		public static String DELETE_SUCCESS="删除成功!";
		/**
		 * 操作成功
		 */
		public static String HANDLE_SUCCESS="操作成功!";
		/**
		 * 操作失败
		 */
		public static String HANDLE_FAIL="操作失败!";
		/**
		 * 帐户已存在
		 */
		public static String ISEXISTACCOUNT="该帐户已存在!";
		/**
		 * 帐户已存在
		 */
		public static String ISEXISTPRODUCT="该商家下已有同名商品!";
		
		
	}
	public final static class Member{
		/**
		 *  会员类别（1谷登农批，2农速通，3农批宝，4.产地供应商，5.门岗， 其余待补）
		 */
		public final static Integer MEMBER_LEVEL_FIVE=5;
		
		/**
		 *  sid
		 */
		public final static String MEMBER_SID="88888888";
	}
	
	public final static class Order{
		
		/**
		 *  订单支付凭证， 最大图片数
		 */
		public final static Integer MAX_IMAGE_NUM = 4;
		
		/**
		 * 订单参考号最大个数
		 */
		public final static Integer MAX_SERIAL_NUMBER = 10;
		
		/**
		 * 最大产品库存
		 */
		public final static Double MAX_PRODUCT_STOCK = 999999.99;
		
		/**
		 * 最大订单金额
		 */
		public final static Double MAX_ORDER_PRICE = 99999999.99;
		
		/**
		 * pos机最大订单金额
		 */
		public final static Double MAX_POS_ORDER_PRICE = 9999999.99;
		
		/**
		 *  订单状态: 未付款
		 */
		public final static String STATUS_NOT_PAY = "1";
		
		/**
		 *  订单状态: 部分付款
		 */
//		public final static String STATUS_PARTIAL_PAY = "2";
//		去掉部分付款的状态，钱包+刷卡，钱包+现金   当做已经付款
		public final static String STATUS_PARTIAL_PAY = "1";
		
		/**
		 *  订单状态: 已付款
		 */
		public final static String STATUS_PAID = "3";
		
		/**
		 *  订单状态: 已出场
		 */
		public final static String STATUS_OUT_MARKET = "4";
		
		/**
		 *  订单状态: 已取消
		 */
		public final static String STATUS_CANCEL = "8";
		
		/**
		 *  订单状态: 已作废
		 */
		public final static String STATUS_INVALID = "9";
		
		/**
		 *  订单状态: 已完成
		 */
		public final static String STATUS_FINISH = "10";
		
		/**
		 *  订单状态: 待发货
		 */
		public final static String STATUS_WAITING_DELIVER = "11";
		
		/**
		 *  订单状态: 待收货
		 */
		public final static String STATUS_WAITING_ACCEPT = "12";
		
		/**
		 *  支付方式: 钱包余额
		 */
		public final static String PAYTYPE_WALLET = "1";
		
		/**
		 *  支付方式: 线下刷卡
		 */
		public final static String PAYTYPE_BANKCARD = "2";
		
		/**
		 *  支付方式: 现金
		 */
		public final static String PAYTYPE_CASH = "3";
		
		/**
		 *  支付方式: 钱包余额 + 线下刷卡
		 */
		public final static String PAYTYPE_WALLET_AND_BANKCARD = "12";
		
		/**
		 *  支付方式: 钱包余额 + 现金
		 */
		public final static String PAYTYPE_WALLET_AND_CASH = "13";
		
		/**
		 * 订单锁住状态
		 */
		public final static Integer ORDER_IS_LOCKED = 1;
		
		/**
		 * 订单解锁状态
		 */
		public final static Integer ORDER_IS_UNLOCKED = 0;
		
		/**
		 *  订单类型 1:农商友采购订单
		 */
		public final static String TYPE_FROM_NSY = "1";
		
		/**
		 *  订单类型 2:农批商采购订单
		 */
		public final static String TYPE_FROM_NPS = "2";
		
		/**
		 * 农商友6+1订单
		 */
		public final static String TYPE_FROM_6_PLUS_1 = "3";
		
		/**
		 * 服务订单
		 */
		public final static String TYPE_FOR_GOLD_MEMBER = "4";
	}
	
	/**
	 * 支付流水
	 * @author TerryZhang
	 *
	 */
	public final static class PAY_SERIALNUMBER{
		
		/**
		 *  支付状态: 未支付
		 */
		public final static String STATUS_NOT_PAY = "0";
		
		/**
		 *  支付状态: 已支付
		 */
		public final static String STATUS_PAY = "1";
		
		/**
		 *  支付状态: 支付失败
		 */
		public final static String STATUS_FAIL_PAY = "9";
		
		/**
		 *  支付方式: 钱包支付
		 */
		public final static String PAYTYPE_WALLET = "1";
		
		/**
		 *  支付方式: 线下刷卡
		 */
		public final static String PAYTYPE_BANKCARD = "2";
		
		/**
		 *  支付方式: 现金支付
		 */
		public final static String PAYTYPE_CASH = "3";
		
		/**
		 *  支付方式: 钱包余额 + 线下刷卡
		 */
		public final static String PAYTYPE_WALLET_AND_BANKCARD = "12";
		
		/**
		 *  支付方式: 钱包余额 + 现金
		 */
		public final static String PAYTYPE_WALLET_AND_CASH = "13";
		
	}
	
	public final static class Product {
		
		public final static int PERIOD_OF_VALIDITY = 30;
		
		public static final String PRODUCT_EDIT_SIGN_NO = "0";
		public static final String PRODUCT_EDIT_SIGN_YES = "1";
		
		/**
		 * 下架
		 */
		public static final String PRODUCT_OPTION_OFFLINE = "1";
		/**
		 * 上架
		 */
		public static final String PRODUCT_OPTION_ONLINE = "2";
		
	}
	/**
	 * 阿里大鱼常量
	 * @author gcwu
	 *
	 */
	public final static class Alidayu{
		/** alidayu模版id **/
		public static final String TEMPLATECODE = "templateCode";
		
		/** alidayu短信签名 **/
		public static final String FREESIGNNAME = "freeSignName";
		
		/** alidayu手机验证码  **/
		public static final String CODE = "code";
		
		/** alidayu手机m密码  **/
		public static final String PASSWORD = "password";
		
		/** code模版内容 **/
		public static final String PARAM = "param";
		
		/** 阿里大鱼短信通道**/
		public static final String REDISTYPE="4";
		/** 默认短信通道**/
		public static final String DEFAULTNO="4";
		
		/** 找回密码**/
		public static final Integer MESSAGETYPE1=1;
		
		/** WEB端注册验证码 **/
		public static final Integer MESSAGETYPE2=2;
		
		/** 手机端注册验证码 **/
		public static final Integer MESSAGETYPE3=3;
		
		/** 商户添加用户短信通知 **/
		public static final Integer MESSAGETYPE4=4;
		
		/** 修改支付密码**/
		public static final Integer MESSAGETYPE5=5;
		
		/** 农速通确认旧手机 【谷登科技】验证码：XXXXXX，您正在进行手机客户端修改手机号码验证操作，请勿向他人泄漏**/
		public static final Integer MESSAGETYPE6=6;
		
		/** 农速通确认新手机 【谷登科技】验证码：XXXXXX，您正在进行手机客户端绑定新手机号码验证操作，请勿向他人泄漏**/
		public static final Integer MESSAGETYPE7=7;
		
		
	}
	/**
	 * 商铺状态
	 * @author houxp
	 *
	 */
	public final static class shopStatus {
		/**待审核***/
		public static final String SHOP_STATUS_0 = "0";
		
		/**已认证***/
		public static final String SHOP_STATUS_1 = "1";
		
		/**已驳回***/
		public static final String SHOP_STATUS_2 = "0";
	}

	public final static class Cart{
		public static final String SELECTED="1";
		public static final String STATE_NORMAL="1";
		public static final String STATE_DELETE="2";//
	}
}
