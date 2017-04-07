package com.gudeng.commerce.gd.home;

/**
 *功能描述：常量的定义
 */
public class Constant {
	
	/**
	 * 成功标志
	 */
	public static String SUCCESS = "1";
	/**
	 * 失败标志
	 */
	public static String FALIED = "0";
	
	/**
	 * 分页开始Index 数据库参数标识
	 */
	public static String START_ROW = "startRow";
	
	/**
	 * 分页页大小  数据库参数标识
	 */
	public static String ROW_SIZE = "endRow";
	
	/** 登录用户 **/
	public static final String SYSTEM_LOGINUSER = "loginMember";
	
	/** 手机验证码 **/
	public static final String MOBILE_CHECK = "mobileCheck";
	
	/** 手机验证码 **/
	public static final String PASS_CHECK = "passCheck";
	
	/** 记住的手机号**/
	public static final String REMEMBER_MOBILE="rememberMobile";
	
	/** 手机验证码发送时间 **/
	public static final String MOBILE_CHECK_TIME = "mobileCheckTime";
	
	/** 市场ID **/
	public static final String MARKETID = "marketId";
	
	/** 市场 name **/
	public static final String MARKETNAME = "marketName";
	
	/** 店铺ID **/
	public static final String BUSINESSID = "businessId";
	
	/** 手机验证码模版**/
	public static final String SMSMSG = "【谷登科技】您好,谷登农批网手机验证码:{code}";
	
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
		public static final String  DEFAULTNO ="4";
		
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
	}
	
}
