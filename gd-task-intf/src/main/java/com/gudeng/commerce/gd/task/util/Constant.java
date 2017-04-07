package com.gudeng.commerce.gd.task.util;

/**
 * Created by Administrator on 2015/6/3.
 */
public class Constant {

    /**登录用户Id session key**/
    public static String LOGIN_SESSSION_USER = "GD_UserInfo";

    /**登录cookie login uuid**/
    public static String LOGIN_SSO_NAME="GD_LOGIN_SSO_NAME";

    /**自动登录cookie login uuid**/
    public static String LOGIN_AUTO_NAME="GD_LOGIN_AUTO_NAME";

    /**登录cookie login account**/
    public static String LOGIN_ACCOUNT="GD_LOGIN_ACCOUNT";

    /**登录redis前缀**/
    public static String LOGIN_REDIS_PRE="GD_LOGIN_PRE_";

    /**验证redis前缀**/
    public static String VERIFY_REDIS_PRE="GD_VERIFY_PRE_";

    /**修改密码redis前缀**/
    public static String EDIT_PASSWORD_REDIS_PRE="GD_EDIT_PWD_PRE_";

    /**修改手机redis前缀**/
    public static String EDIT_MOBLIE_REDIS_PRE="GD_EDIT_MOBLIE_PRE_";

    /** ------------- 登录代码 1000 - 1100 start --------------------- */

    public static final int JSON_RESULT_NOTLOGIN_CODE = 11;
    public static final String JSON_RESULT_NOTLOGIN_DESC = "Not login";
    public static final int JSON_RESULT_REQUEST_PARAM_EMPTY_CODE = 101;
    public static final String JSON_RESULT_REQUEST_PARAM_EMPTY_DESC = "Request parameter {0} can't be empty";

    public static final int JSON_RESULT_REQUEST_PARAM_INVALID_CODE = 102;
    public static final String JSON_RESULT_REQUEST_PARAM_INVALID_DESC = "Request parameter {0} invalid";
    public static final int JSON_RESULT_LOGIN_USER_NOT_EXIST_CODE = 1000;
    public static final String JSON_RESULT_LOGIN_USER_NOT_EXIST_DESC = "用户不存在";
    public static final int JSON_RESULT_LOGIN_PASSWORD_ERROR_CODE = 1001;
    public static final String JSON_RESULT_LOGIN_PASSWORD_ERROR_DESC = "密码错误";
    public static final int JSON_RESULT_LOGIN_INVALID_CODE = 1002;
    public static final String JSON_RESULT_LOGIN_INVALID_DESC = "自动登录失效";
    public static final int JSON_RESULT_AUTO_LOGIN_DISABLED_CODE = 1003;
    public static final String JSON_RESULT_AUTO_LOGIN_DISABLED_DESC = "没有开启自动登录";
    public static final int JSON_RESULT_WEIXIN_AUTH_FAIL_CODE = 1010;
    public static final String JSON_RESULT_WEIXIN_AUTH_FAIL_DESC = "微信授权失败";
    /** ------------- 登录代码 1000 - 1100 end --------------------- */

    /** ------------- 用户注册，用户资料，用户设置 1100 - 1199 start --------------------- */
    public static final int JSON_RESULT_REGISTER_NUMBER_EXIST_CODE = 1110;
    public static final String JSON_RESULT_REGISTER_NUMBER_EXIST_DESC = "用户名已注册";
    public static final int JSON_RESULT_REGISTER_VERIFI_ERROR_CODE = 1111;
    public static final String JSON_RESULT_REGISTER_VERIFI_ERROR_DESC = "验证码不正确";
    /** ------------- 用户注册，用户资料，用户设置 1100 - 1199 end --------------------- */

    /**
     * 帐号类型
     */
    public static enum AccountTypeEnum {
        USERNAME("用户名", 0),

        MOBLIE("手机", 1),

        EMAIL("邮箱", 2),

        NICKNAME("昵称", 3);

        public String value;

        public int num;

        AccountTypeEnum(String value, int num) {
            this.value = value;
            this.num = num;
        }
    }

    /**
     * 密码类型
     */
    public static enum PasswordTypeEnum {
        PLAIN("明文", 0),

        CIPHER("密文", 1);

        public String value;

        public int num;

        PasswordTypeEnum(String value, int num) {
            this.value = value;
            this.num = num;
        }
    }

    public static enum LoginClientEnum {

        WEB("WEB", 0),

        WEIXIN("微信HTML5", 1),

        IOS("iOS客户端", 2),

        ANDORID("android客户端", 3);

        public String value;

        public int num;

        LoginClientEnum(String value, int num) {
            this.value = value;
            this.num = num;
        }

    }

}
