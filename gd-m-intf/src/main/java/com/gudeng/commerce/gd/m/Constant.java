package com.gudeng.commerce.gd.m;

/**
 * 功能描述：常量的定义
 */
public class Constant {
    /** 登录用户 **/
    public static final String PASSWORD_SHUFIX = "gudeng2015@*&^-";
    
    /** 登录用户 **/
    public static final String SYSTEM_LOGINUSER = "loginMember";
    /** 微信用户 **/
    public static final String SYSTEM_WEIXINUSER = "weixinUser";
    /** 手机验证码发送时间 **/
    public static final String MOBILE_CHECK_TIME = "mobileCheckTime";
    /** 手机验证码模版 **/
    public static final String SMSMSG = "【谷登科技】您好,谷登农批网手机验证码:{code}";
    /** 手机验证码 **/
    public static final String MOBILE_CHECK = "mobileCheck";
/**
     * 分页开始Index 数据库参数标识
     */
    public static String START_ROW = "startRow";

    /**
     * 分页页大小 数据库参数标识
     */
    public static String ROW_SIZE = "endRow";
    
	public static final String PRICE_INTERVAL = "1";
	
	public static final String PICTURE_MUTIPLE="2";

    public final static class ActStatus {
        public static String STATUS = "act_status";
        /** 保存成功 */
        public static String SAVE_SUCCESS = "sv_sucs";
        /**
         * 更新成功
         */
        public static String UPDATE_SUCCESS = "up_sucs";
        /**
         * 删除成功
         */
        public static String DELETE_SUCCESS = "del_sucs";
        /**
         * 操作成功
         */
        public static String HANDLE_SUCCESS = "hd_sucs";
    }

    public final static class ActResult {
        /** 保存成功 */
        public static String SAVE_SUCCESS = "保存成功!";
        /**
         * 更新成功
         */
        public static String UPDATE_SUCCESS = "更新成功!";
        /**
         * 删除成功
         */
        public static String DELETE_SUCCESS = "删除成功!";
        /**
         * 操作成功
         */
        public static String HANDLE_SUCCESS = "操作成功!";
        /**
         * 操作失败
         */
        public static String HANDLE_FAIL = "操作失败!";
        /**
         * 帐户已存在
         */
        public static String ISEXISTACCOUNT = "该帐户已存在!";
        /**
         * 帐户已存在
         */
        public static String ISEXISTPRODUCT = "该商家下已有同名商品!";

    }/**
 *app类型名称
 * @author 
 *
 */
public final static class APP_TYPE{	/**
	 *  1:农商友
	 */
	public final static String APP_TYPE_NSY = "1";

	/**
	 * 3:农批商
	 */
	public final static String APP_TYPE_NPS = "3";
	/**
	 *  2:农速通，
	 */
	public final static String APP_TYPE_NST = "2";
	/**
	 *  5:供应商
	 */
	public final static String APP_TYPE_GYS = "5";
	
	}
	/**
     * 阿里大鱼常量
     * 
     * @author gcwu
     *
     */
    public final static class Alidayu {
        /** alidayu模版id **/
        public static final String TEMPLATECODE = "templateCode";

        /** alidayu短信签名 **/
        public static final String FREESIGNNAME = "freeSignName";

        /** alidayu手机验证码 **/
        public static final String CODE = "code";

        /** alidayu手机m密码 **/
        public static final String PASSWORD = "password";

        /** code模版内容 **/
        public static final String PARAM = "param";

        /** 阿里大鱼短信通道 **/
        public static final String REDISTYPE = "4";

        /** 找回密码 **/
        public static final Integer MESSAGETYPE1 = 1;

        /** WEB端注册验证码 **/
        public static final Integer MESSAGETYPE2 = 2;

        /** 手机端注册验证码 **/
        public static final Integer MESSAGETYPE3 = 3;

        /** 商户添加用户短信通知 **/
        public static final Integer MESSAGETYPE4 = 4;

        /** 修改支付密码 **/
        public static final Integer MESSAGETYPE5 = 5;
	}

	public static final String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
}
