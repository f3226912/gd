package com.gudeng.commerce.gd.customer.enums;

public enum ErrorCodeEnum {
	/** 成功返回 */
	SUCCESS(0, ""),
	/** 返回失败 */
	FAIL(-10001, "服务器繁忙，请稍后重试"),
	
	NST_FAIL(-10001, "农速通服务器繁忙，请稍后重试"),
	
	NST_TOKEN_FAIL(21016, "登录超时,请登录后访问"),
	
	VERSION_NOT_CORRECT(-1, "请先更新版本再进行操作"),
	
	//商品相关 start
	PRODUCT_ID_IS_NULL(-1, "商品id不能为空"),
	/** 库存为0  */
	PRODUCT_NO_STOCK_COUNT(-5, "商品已售馨, 请您刷新商品后重新购买"),
	
	PRODUCT_NAME_IS_NULL(-1, "商品名称不能为空"),
	
	PRODUCT_INFO_IS_NULL(-1, "商品信息不能为空"),
	
	PRODUCT_IMAGE_IS_NULL(-1, "请上传图片"),
	
	PRODUCT_UNIT_IS_NULL(-1, "单位不能为空"),
	
	PRODUCT_UNIT_ERROR(-1, "单位必须是整数类型"),
	
	PRODUCT_CATEGORY_ID_IS_NULL(-1, "商品分类id不能为空"),
	
	PRODUCT_TYPE_IS_NULL(-1, "商品类型不能为空"),
	
	PRODUCT_SOURCE_IS_NULL(-1, "商品来源id不能为空"),
	
	PRODUCT_STOCKCOUNT_ERROR(-1, "库存量不能为空, 不能小于0"),
	
	PRODUCT_PRICE_ERROR(-1, "商品价格不能为空, 不能小于0"),
	
	PRODUCT_DESCRIP_IS_NULL(-1, "商品介绍不能为空"),
	
	PRODUCT_PROVICE_IS_NULL(-1, "产地-省不能为空"),
	
	PRODUCT_ISDID_IS_NULL(-1, "入库表id不能为空"),
	
	OPERATION_TYPE_ERROR(-1, "操作码不正确"),
	
	PRODUCT_IS_AUDITING(-1, "商品正在审核中"),
	
	PRODUCT_NOT_ON_SALE(-1, "<下架操作>只能操作销售中的产品"),
	
	PRODUCT_ALREADY_ON_SALE(-1, "不能对上架状态的产品进行上架操作..."),
	
	PRODUCT_FACEWORD_IS_NULL(-1, "提示词不能为空"),
	
	PRODUCT_FACEFIELD_IS_NULL(-1, "提示字段不能为空"),
	
	PRODUCT_SIGN_IS_NULL(-1, "商品标签参数不能为空"),
	
	PRODUCT_PAGESIZE_IS_NULL(-1, "请传入pageSize"),
	
	PRODUCT_PAGENUM_IS_NULL(-1, "请传入pageNum"),
	//商品相关 start
	
	PARAM_IS_NULL(-1, "传入参数不能为空"),
	
	PARAM_IS_ERROR(-1, "传入参数格式不正确"),
	
	APP_NAME_IS_NULL(-1, "app名称不能为空"),
	
	UPLOAD_IMAGE_IS_NULL(-1, "上传图片为空"),
	
	//用户相关 start
	MEMBER_ID_IS_NULL(-2, "用户ID不能为空"),
	
	ACCOUNT_IS_NULL(-1, "用户账号不能为空"),
	
	ACCOUNT_NOT_EXISTED(-2, "用户账号不存在"),
	
	PASSWORD_ERROR(-3, "手机号码或密码错误"),
	
	PASSWORD_IS_EMPTY(-1, "用户密码不能为空"),
	
	TWO_PASSWORD_NOT_MATCH(-1, "两次密码输入不一致"),
	
	TRADEPASSWORD_NOT_EXISTED(-4, "请先设置交易密码"),
	
	TRADEPASSWORD_IS_INCORECT(-3, "交易密码错误"),
	
	MOBILE_IS_EMPTY(-1, "手机号码不能为空"),
	
	MOBILE_INCORRECT(-1, "手机号码格式错误"),
	
	MOBILE_NOT_EXISTED(-1, "该手机号码未注册"),
	
	MOBILE_IS_EXISTED(-1, "该手机号码已注册"),
	
	MOBILE_ENCRYPT_IS_NULL(-1, "手机密串不能为空"),
	
	MOBILE_IS_NOT_MATCHED(-1, "手机密串与手机号不匹配"),
	
	VERIFYCODE_IS_EMPTY(-1, "验证码不能为空"),
	
	VERIFYCODE_INCORRECT(-1, "验证码错误"),
	
	VERIFYCODE_TIMEOUT(-1, "验证码超时, 请重新发送验证码"),
	
	TYPE_IS_EMPTY(-1, "类型不能为空"),
	
	TYPE_IS_INCORRECT(-1, "类型不正确"),
	
	USER_LEVEL_IS_EMPTY(-1, "账号类别不能为空"),
	
	USERNAME_IS_NULL(-1, "真实姓名不能为空"),
	
	USERNAME_OVER_LENGTH(-1, "真实姓名不能超过14字"),
	
	USERNAME_OVER_LENGTH30(-1, "真实姓名不能超过30字"),
	
	USER_RESOURCE_IS_EMPTY(-1, "注册来源不能为空"),
	
	NST_APP_IS_NOT_USED(-1, "请关注农速通微信公众号下载"),
	
	NSYUSERTYPE_IS_EMPTY(-1, "用户类型不能为空"),
	
	NSYUSERTYPE_INCORRECT(-1, "请输入正确的用户类型"),
	
	ACTIVE_CALL_MEMBERID_EMPTY(-1, "请输入主叫人的用户id"),
	/** 账号禁用  */
	ACCOUNT_IS_DISABLE(-10000, "您的账号已被禁用, 如有问题, 请联系客服"),
	/** 所属市场发生变化  */
	ACCOUNT_MARKET_MODIFY(-1000, "您的账号所属市场发生变化，请重新登录"),

	GYS_ACCOUNT_NOT_PERMIT(-2, "产地供应商用户拒绝登录"),
	
	GYS_ACCOUNT_ONLY_PERMIT(-2, "非供应商用户"),
	
	ID_CARD_IS_NULL(-1, "身份证号码不能为空"),
	
	ID_CARD_ERROR(-1, "身份证号码不正确"),
	
	ID_CARD_IMAGE_IS_NULL(-1, "请上传身份证图"),
	
	ID_CARD_IMAGE_ERROR(-1, "请上传身份证正反面图"),
	//用户相关 end
	
	COMPANY_NAME_IS_NULL(-1, "企业名称不能为空"),
	
	LINKMAN_IS_NULL(-1, "法人代表不能为空"),
	
	LINKMAN_OVER_LENGTH(-1, "法人代表不能超过14个字"),
	
	BZL_IS_NULL(-1, "营业执照号码不能为空"),
	
	BZL_PHOTO_IS_NULL(-1, "营业执照图不能为空"),
	
	CERTIFY_ID_IS_NULL(-1, "认证id不能为空"),
	
	ALREADY_CERTIFIED(-1, "用户已经提交过认证，不能修改认证类型"),
	
	VEHICLE_PHOTO_URL_ID_IS_NULL(-1, "请上传行驶证"),
	
	//钱包相关 start
	WALLET_BALANCE_NOT_ENOUGH(-1, "钱包余额不足"),
	//钱包相关 end
	
	AREA_NAME_IS_NULL(-1, "城市名不能为空"),
	
	AREA_NAME_NOT_FOUND(-1, "找不到所在城市或省份"),
	
	AREA_ID_IS_NULL(-1, "城市或省份id不能为空"),
	
	AREA_PARAM_IS_NULL(-1, "区域 参数不能为空"),
	
	COMPLAINT_CONTENT_IS_NULL(-1, "投诉内容不能为空"),
	
	COMPLAINT_CONTENT_OVER_LENGTH(-1, "投诉内容不能超过500字符"),
	
	COMPLAINT_MEMBER_IS_NULL(-1, "投诉人账号不能为空"),
	
	COMPLAINT_SOURCE_IS_NULL(-1, "投诉来源不能为空"),
	
	//商铺相关 start
	BUSINESS_USER_ID_IS_NULL(-2, "商家用户ID不能为空"),
	
	BUSINESS_ID_IS_NULL(-1, "商铺id不能为空"),
	
	BUSINESS_NAME_IS_NULL(-1, "商铺名称不能为空"),
	
	GYS_ID_IS_NULL(-1, "供应商id不能为空"),
	
	GYS_NAME_IS_NULL(-1, "供应商名称不能为空"),
	
	BUSINESS_NAME_OVER_30_LENGTH(-1, "商铺名称不能超过30个字"),
	
	BUSINESS_NAME_OVER_50_LENGTH(-1, "商铺名称不能超过50个字"),
	
	BUSINESS_ADDRESS_OVER_LENGTH(-1, "商铺详细地址不能超过50个字"),
	
	BUSINESS_MODEL_IS_NULL(-1, "经营模式不能为空"),
	
	BUSINESS_LEVEL_IS_NULL(-1, "经销类型不能为空"),
	
	BUSINESS_MANAGEMENT_TYPE_IS_NULL(-1, "经营类型不能为空"),
	
	BUSINESS_IS_NOT_EXISTED(-1, "商铺不存在"),
	
	BUSINESS_IS_EXISTED(-1, "此用户已经存在商铺，不能重复添加商铺"),
	
	BUSINESS_CATEGORY_ID_IS_NULL(-1, "经营分类不能为空"),
	//商铺相关 end
	
	//市场相关 start
	MARKET_ID_IS_NULL(-1, "市场id不能为空"),
	
	MARKET_ID_NOT_FOUND(-1, "找不到对应市场"),
	
	MARKET_NAME_IS_NULL(-1, "市场名不能为空"),
	
	MARKET_NO_AD(-1, "该市场没有广告位"),
	//市场相关 end
	
	BANK_NAME_NOT_FOUND(1, "银行信息查找不到"),
	
	BANKCARD_NO_IS_NULL(1, "银行卡号不能为空"),
	
	//农速通相关 start
	INPUT_NOT_CHINSES_ENGLISH(-1, "请输入中文或者英文"),
	
	ACCOUNT_ALREADY_RECOMMEND(-1, "您的账号不能被重复推荐"),
	
	RECOMMEND_CODE_INCRECT(-1, "你输入的推荐码无效"),
	
	RECOMMEND_CODE_ALREADY_USED(-1, "当前活动,你已经使用过推荐码,请勿重复使用"),
	
	GOODS_COMPANY_IS_NULL(-1, "用户请选择公司发布货源"),
	
	MEMBER_ADDRESS_ID_IS_NULL(-1, "货源id不能为空"),
	
	MEMBER_ADDRESS_NOT_EXISTED(-1, "货源信息不存在"),
	
	NST_ORDER_ALREADY_DELIVER(-1, "货源信息已经被接单"),
	
	NST_ORDER_ALREADY_CANCEL(-1, "订单已被取消"),
	
	NST_ORDER_ALREADY_REFUSE(-1, "订单已被拒绝"),
	
	NST_ORDER_ALREADY_CONFIRM(-1, "订单已被确认"),
	
	GOODS_ID_IS_NULL(-1, "货物id不能为null，也不能为0"),
	
	GOODS_WEIGHT_IS_NULL(-1, "货物重量不能为空，也不能为0"),
	
	GREATER_THAN_GOODS_WIGHT(-1, "抢货重量不能大于货物重量"),
	
	WIGHT_CAR_ID_IS_NULL(-1, "过磅记录id不能为空"),
	
	CONTROLLER_ID_IS_NULL(-1, "检察员id不能为空"),
	
	CAR_NO_IS_NULL(-1, "车牌号码不能为空"),
	
	CAR_NO_NOT_MATCH_MOBILE(-1, "手机号与车牌不匹配，请重新检索"),
	
	ORDERS_NEED_HANDLE(-1, "您还有5条订单未处理"),
	
	CARS_IS_NULL(-1, "请先新增车辆"),
	
	CARS_NOT_EXISTED(-1, "车辆不存在"),
	
	CARS_LINE_IS_NULL(-1, "请选择车辆线路"),
	
	CARS_LINE_NOT_EXISTED(-1, "车辆线路不存在"),
	
	MESSAGE_ID_IS_NULL(-1, "传入推送信息的Id不能为空"),
	
	CANNOT_LOCATE(-1, "无定位信息"),
	
	CITY_NOT_MATCH(-1, "发货地和收货地城市请保持一致"),
	//农速通相关 end
	
	//订单相关错误码 start
	ORDER_ORDERNO_IS_NULL(-1, "订单号不能为空"),
	
	ORDER_MEMBERID_IS_NULL(-1, "下单用户id不能为空"),
	
	ORDER_BUYER_IS_NULL(-1, "买家用户id不能为空"),
	
	ORDER_SELLER_IS_NULL(-1, "卖家用户id不能为空"),
	
	ORDER_MARKET_IS_NULL(-1, "市场不能为空"),
	
	ORDER_PRODUCT_INFO_IS_NULL(-1, "订单商品信息不能为空"),
	
	ORDER_PRODUCT_ID_IS_NULL(-1, "商品id不能为空"),
	
	ORDER_PRODUCT_PERCHASE_QUANTITY_IS_NULL(-1, "请输入采购量"),
	
	ORDER_SHOPNAME_IS_NULL(-1, "商铺名称不能为空"),
	
	ORDER_PAYTYPE_ERROR(-1, "订单支付方式不正确"),
	
	ORDER_PAYAMOUNT_ERROR(-1, "订单实付金额不正确"),
	
	ORDER_AMOUNT_NOT_MATCH(-1, "商品金额和订单金额不匹配"),
	
	ORDER_PRODUCT_PRICE_IS_NULL(-1, "面议商品不能加入订单"),
	
	ORDER_AMOUNT_IS_NULL(-1, "订单金额不能为空或0"),
	
	ORDER_PAYAMT_IS_NULL(-1, "支付金额不能为空或0"),
	
	ORDER_QCCODE_IS_NULL(-1, "订单二维码不能为空或0"),
	
	ORDER_PRODUCT_STOCKCOUNT_LACK(-5, "库存不足，请重新输入采购量"),
	
	ORDER_EXCEED_MAX_AMOUNT(-1, "订单超额，无法交易"),
	
	ORDER_BUY_YOUR_OWN_PRODUCT(-1, "自有商品，无法交易"),
	
	ORDER_CANNOT_CANCEL(-1, "该订单不是未付款状态, 不能取消"),
	
	ORDER_IS_PAYING(-1, "订单正在支付, 不能取消"),
	
	ORDER_NOT_ORDER_SELLER(-1, "您不是卖家, 不能确认收款"),
	
	ORDER_CANNOT_CONFIRM_RECEIVABLES(-1, "该订单不能确认收款"),
	
	ORDER_NOT_EXISTED(-1, "订单不存在"),
	
	ORDER_USERTYPE_IS_NULL(-1, "订单用户类型不能为空"),
	
	ORDER_USERTYPE_ERROR(-1, "订单用户类型错误"),
	
	ORDER_NOT_HAS_AUTHORITY(-1, "您没有权限进行该操作"),
	
	ORDER_PUT_ON_PRODUCT(-1, "请先上架商品"),
	
	ORDER_ALREADY_HAS_CUSTOMER(-1, "该订单已补充客户信息"),
	
	ORDER_ALREADY_HAS_PRODUCT(-1, "该订单已补充商品信息"),
	
	ORDER_BUYER_EQUAL_SELLER(-1, "订单的买家不能与卖家一致"),  
	
	ORDER_ILLEAGLE_OPERATION_TYPE(-1, "请输入正确的操作类型"),
	
	ORDER_NOT_UNPAID_STATUS(-1, "订单状态不是待收款状态， 不能修改价格"),
	
	ORDER_IS_PAYING_STATUS(-1, "订单状态正在支付， 不能修改价格"),
	
	ORDER_IN_ACT(-1, "活动订单的价格不允许修改"),
	
	ORDER_ILLEAGLE_CHANGED_PAYAMOUNT(-1, "请输入修改后的价格(不能为0)"),
	
	ORDER_NOT_DELIVERING_STATUS(-1, "订单状态不是待发货状态， 不能确认发货"),
	
	ORDER_ALREADY_CANCEL(-1, "订单已经被手动取消， 不能确认发货"),
	
	SEARCH_STRING_IS_NULL(-1, "请输入查询的订单号, 手机号, 姓名或者商品名称"),
	
	ORDER_SUB_STATUS_ERROR(-1, "订单补贴状态不正确"),
	
	ORDER_STATUS_ERROR(-1, "订单状态不正确"),
	
	ORDER_PAY_INFO_IS_NULL(-1, "上传凭证图片或参考号不能为空"),
	
	ORDER_PAYSERIAL_NOT_EXISTED(-1, "订单支付流水不存在"),
	//订单相关错误码 end
	
	//发货相关错误码 start
	DELIVER_ALREADY_DELIVERED(-1, "商品已发布货源"),
	
	DELIVER_GOODS_FORMAT_ERROR(-1, "货源货物格式不正确"),
	
	DELIVER_GOODS_NUM_ERROR(-1, "货源商品数不正确"),
	
	GOODS_ALREADY_DELIVERED(-1, "商品已发布货源"),
	
	SEND_PROVINCE_IS_NULL(-1, "发货地省份不能为空"),
	
	SEND_CITY_IS_NULL(-1, "发货地城市不能为空"),
	
	SEND_AREA_IS_NULL(-1, "发货地区县不能为空"),
	
	RECEIVE_PROVINCE_IS_NULL(-1, "收货地省份不能为空"),
	
	RECEIVE_CITY_IS_NULL(-1, "收货地城市不能为空"),
	
	RECEIVE_AREA_IS_NULL(-1, "收货地区县不能为空"),
	
	GOODS_TYPE_IS_NULL(-1, "货物类型不能为空"),
	
	GOODS_TYPE_ERROR(-1, "货物类型不正确"),
	
	GOODS_SIZE_ERROR(-1, "货物体积必须为正整数"),
	
	SEND_DATE_ERROR(-1, "装车时间不能早于当前时间"),
	
	SEND_DATE_ERROR_MORNING(-1, "装车时间不能早于当前时间, 上午时间段是6:00~12:00"),
	
	SEND_DATE_ERROR_NOON(-1, "装车时间不能早于当前时间, 中午时间段是12:00~14:00"),
	
	SEND_DATE_ERROR_AFTERNOON(-1, "装车时间不能早于当前时间, 下午时间段是14:00~18:00"),
	
	GOODS_PRICE_ERROR(-1, "价格只能输入两位小数点的正数"),
	
	GOODS_PRICE_OVER_MAX(-1, "请输入一个小于10,000,000的价格"),
	
	GOODS_WEIGHT_ERROR(-1, "货物重量只能输入一位小数点的正数"),
	
	CONTACT_NAME_IS_NULL(-1, "联系人不能为空"),
	
	ORDER_CLIENT_IS_NULL(-1, "下单客户端不能为空"),
	//发货相关错误码 end
	
	//活动相关错误码 start
	PROM_ID_IS_NULL(-1, "活动Id为空"),
	
	PROM_ACT_NOT_EXISTED(-1, "活动未找到"),
	
	PROM_ACT_ENDED(-1, "您购买的商品所参与的活动已取消, 请刷新页面重新下单"),
	
	FUNCTION_DISABLE(-1, "由于设备原因, 您暂时只能购买特定的商品, 请期待下一个版本"),
	//活动相关错误码 end
	
	RECORDIDS_IS_NULL(-1, "recordIds不能为空"),
	
	FAIL_FOR_SP_PRODUCT_REAT(-1, "该产品已经存在一条认证记录了, 请勿重复添加.."),
	
	FAIL_FOR_EDIT_RECORD_UNDER_AUDIT(-1, "不可编辑待认证的记录"),
	
	/** 需要登录 */
	NEED_LOGIN(-999, "请先登录"),
	
	DISTRIBUTE_MODE_IS_NULL(-1,"配送方式不能为空");
	
	ErrorCodeEnum(Integer statusCode, String statusMsg){
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}
	
	private final Integer statusCode;
	
	private final String statusMsg;
	
	public Integer getStatusCode(){
		return statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}
}
