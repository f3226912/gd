package com.gudeng.commerce.gd.api.service.impl.order;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant.Order;
import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.dto.input.MiningOrderInputDTO;
import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.dto.input.OrderBatchAddBusinessInputDTO;
import com.gudeng.commerce.gd.api.dto.input.OrderBatchAddInputDTO;
import com.gudeng.commerce.gd.api.dto.input.OrderCallbackInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.GoodsTypeEnum;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.DataToolService;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ProductCategoryToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.api.service.impl.OrderNoToolServiceImpl;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.service.order.OrderActivityToolService;
import com.gudeng.commerce.gd.api.service.order.OrderFeeItemDetailToolService;
import com.gudeng.commerce.gd.api.service.order.OrderTool3Service;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.thread.AddReMemCustThread;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.api.util.RestultEntity;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;
import com.gudeng.commerce.gd.customer.service.DeliveryAddressService;
import com.gudeng.commerce.gd.order.dto.MsgCons;
import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.RefundResponseDTO;
import com.gudeng.commerce.gd.order.enm.EOrderFeeType;
import com.gudeng.commerce.gd.order.enm.EOrderFeeUserType;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

public class OrderTool3ServiceImpl implements OrderTool3Service {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderTool3ServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;

	private static OrderBaseinfoService orderBaseinfoService;

	private static DeliveryAddressService deliveryAddressService;

	@Autowired
	private ProductToolService productToolService;
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private OrderNoToolServiceImpl orderNoToolServiceImpl;
	@Autowired
	private MemberToolService memberToolService;

	@Autowired
	private DataToolService dataToolService;

	@Autowired
	private OrderActivityToolService orderActivityToolService;
	@Autowired
	private OrderFeeItemDetailToolService orderFeeItemDetailToolService;

	@Autowired
	private NstApiCommonService nstApiCommonService;

	@Autowired
	private ReBusinessCategoryToolService reBusinessCategoryToolService;

	@Autowired
	private ProductCategoryToolService productCategoryToolService;

	@Autowired
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;

	@Autowired
	private AreaToolService areaToolService;

	@Autowired
	private ReBusinessMarketToolService reBusinessMarketToolService;

	@Autowired
	private MarketToolService marketToolService;

	private static OrderProductDetailService orderProductDetailService;

	private static PaySerialnumberService paySerialnumberService;

	private OrderBaseinfoService getHessianOrderbaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderBaseinfo.url");
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, hessianUrl);
			
		}
		return orderBaseinfoService;
	}

	protected DeliveryAddressService getHessianDeliveryAddressService() throws MalformedURLException {
		String url = gdProperties.getDeliveryAddressServiceUrl();
		if (deliveryAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			deliveryAddressService = (DeliveryAddressService) factory.create(DeliveryAddressService.class, url);
		}
		return deliveryAddressService;
	}

	@SuppressWarnings("unused")
	private OrderProductDetailService getHessianOrderProductService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderProductDetail.url");
		if (orderProductDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderProductDetailService = (OrderProductDetailService) factory.create(OrderProductDetailService.class,
					hessianUrl);
		}
		return orderProductDetailService;
	}

	protected PaySerialnumberService getPaySerialnumberService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.paySerialnumberService.url");
		if (paySerialnumberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			paySerialnumberService = (PaySerialnumberService) factory.create(PaySerialnumberService.class, url);
		}
		return paySerialnumberService;
	}

	/**
	 * @author weiwenke
	 * @param requestParamMap
	 * @return
	 */
	public Map<String, Object> AddGoodsTransfer(Map<String, Object> requestParamMap) {
		// 增加货源请求
		String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.ADD_GOODS_TRANSFER.getUri();
		Long memberAddressId = null;
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("flag", "Y"); // 默认是提交成功的
		try {
			Long time1 = System.currentTimeMillis();
			MemberAddressInputDTO inputDTO = (MemberAddressInputDTO) GSONUtils
					.fromJsonToObject(GSONUtils.toJson(requestParamMap, false), MemberAddressInputDTO.class);
			NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(requestParamMap, url);
			Long time2 = System.currentTimeMillis();
			System.out.println("[INFO]Add delivery cost time: " + (time2 - time1));

			// 不成功则返回错误信息
			if (nstResponse.getCode().intValue() != 10000) {
				mapParam.put("flag", "N");
				mapParam.put("message", nstResponse.getMsg());
				logger.error("添加货源信息出错：" + nstResponse.getMsg());
				return mapParam;
			}
			// 获取物流id
			memberAddressId = Long.parseLong(nstResponse.getResult().get("id"));

			if (StringUtils.isNotBlank(inputDTO.getSelectedList())) {
				// 增加出货信息
				logger.info("增加出货信息：" + inputDTO + ",物流单号：memberAddressId:" + memberAddressId);
				ErrorCodeEnum addResult2 = productDeliveryDetailToolService.add(inputDTO, memberAddressId, false);
				if (ErrorCodeEnum.SUCCESS != addResult2) {
					mapParam.put("flag", "N");
					mapParam.put("message", "增加出货信息数据失败！");
					logger.error("增加出货信息数据失败！");
					return mapParam;
				}
				return mapParam;
			}

		} catch (Exception e) {
			logger.error("[ERROR]添加货源异常", e);
			mapParam.put("flag", "N");
			mapParam.put("message", "[ERROR]添加货源异常" + e.getMessage());
			return mapParam;
		}
		return mapParam;
	}

	/**
	 *
	 * 批量添加订单商铺信息，并且将商品信息同步到货源中
	 */
	@Override
	public RestultEntity batchAddOrder(OrderBatchAddInputDTO inputDTO) throws Exception {
		RestultEntity result = new RestultEntity(); // 返回结果
		Long memberId = ParamsUtil.getLongFromString(inputDTO.getMemberId(), null);
		String channel = ParamsUtil.getStringFromString(inputDTO.getChannel(), "1");
		String orderSource = ParamsUtil.getStringFromString(inputDTO.getOrderSource(), "2");
		String clients = ParamsUtil.getStringFromString(inputDTO.getClients(), "4"); // 客户端来源
		String flag = ParamsUtil.getStringFromString(inputDTO.getFlag(), "1");
		String jsonAddress = inputDTO.getJsonAddress();// 收货地址
		Double zero = 0D;

		// memberId，businessDetailsJsonList商铺商品详细信息列表 为空，则为无效订单

		if (memberId == null) {
			result.setStatusCode("-2");
			result.setMsg("用户ID不能为空");
			return result;
		}
		if (StringUtils.isBlank(inputDTO.getBusinessDetailsJsonList())) {
			result.setStatusCode("-1");
			result.setMsg("订单商铺信息不能为空");
			return result;
		}

		Map<String, Object> cartInfoMap = null;
		Map<String, Object> GoodsTransferMap = new HashMap<>(); // 货源信息
		GoodsTransferMap.put("carLength", "-2");
		GoodsTransferMap.put("token", nstApiCommonService.getNstToken(memberId + ""));
		GoodsTransferMap.put("memberId", memberId); // 关联会员id，即货主的会员id(买家)
		GoodsTransferMap.put("clients", clients); // 客户端来源

		List<Long> cartProductIdList = new ArrayList<>();
		if ("1".equals(flag)) {
			cartInfoMap = new HashMap<>();
			cartInfoMap.put("memberId", memberId);
			cartInfoMap.put("state", 2);
			cartInfoMap.put("productIds", cartProductIdList);
		}
		List<Map<String, Object>> totalMapList = new ArrayList<>();
		JSONArray businessDetailsJsonArr = JSONUtils.parseArray(inputDTO.getBusinessDetailsJsonList());
		logger.debug("商铺商品详细信息列表信息businessDetailsJsonArr:" + businessDetailsJsonArr);

		for (int ii = 0, length = businessDetailsJsonArr.size(); ii < length; ii++) {
			JSONObject jsonObject = (JSONObject) businessDetailsJsonArr.get(ii);
			OrderBatchAddBusinessInputDTO businessInfo = JSONObject.toJavaObject(jsonObject,
					OrderBatchAddBusinessInputDTO.class);
			String businessId = businessInfo.getBusinessId();
			String jsonArrStr = businessInfo.getProductDetails();
			Double buyerComsn = ParamsUtil.getDoubleFromString(businessInfo.getBuyerCommsn()); // 买家佣金
			Double sellerComsn = ParamsUtil.getDoubleFromString(businessInfo.getSellerCommsn());
			String orderAmountStr = businessInfo.getOrderAmount().replace(",", "").replace("，", ""); // 解决
			// 100,100,100.00这种数据的问题
			Double orderAmount = ParamsUtil.getDoubleFromString(orderAmountStr, null);
			Double userPayAmount = ParamsUtil
					.getDoubleFromString(businessInfo.getPayAmount().replace(",", "").replace("，", ""), null);

			if (StringUtils.isBlank(businessId)) {
				result.setStatusCode("-1");
				result.setMsg("商铺id不能为空");
				return result;
			}

			if (StringUtils.isBlank(jsonArrStr)) {
				result.setStatusCode("-1");
				result.setMsg("订单商品信息不能为空");
				return result;
			}

			if (orderAmount == null || orderAmount.compareTo(zero) < 0) {
				result.setStatusCode("-1");
				result.setMsg("订单金额不能为空或小于0");
			}

			if (userPayAmount == null || userPayAmount.compareTo(zero) == 0) {
				result.setStatusCode("-1");
				result.setMsg("支付金额不能为空或0");
			}
			if (orderAmount.compareTo(Order.MAX_ORDER_PRICE) > 0) {
				result.setStatusCode("-1");
				result.setMsg("订单超额，无法交易");
				return result;
			}

			BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(businessId);
			Long sellerId = businessDTO.getUserId();
			if (memberId != null && memberId.compareTo(sellerId) == 0) {
				result.setStatusCode("-1");
				result.setMsg("自有商品，无法交易");
				return result;
			}
			MemberBaseinfoDTO sellerMember = memberToolService.getById(sellerId.toString()); // 商家信息
			MemberBaseinfoDTO buyerMember = memberToolService.getById(memberId.toString()); // 买家信息

			/****** 出发地信息begin **********/
			if (null != businessDTO.getProvinceId()) {
				GoodsTransferMap.put("sProvinceId", businessDTO.getProvinceId());// 出发地省会id
				GoodsTransferMap.put("sProvinceName", businessDTO.getProvince()); // 出发地省会名称
			}
			if ( null == businessDTO.getCityId() ||0 == businessDTO.getCityId()) {
				ReBusinessMarketDTO remarket = reBusinessMarketToolService.getByBusinessId(Long.valueOf(businessId)); // 如果店铺取不到区域信息，
				// 则去取店铺所在市场的区域信息
				if (null != remarket) {
					MarketDTO market = marketToolService.getById(remarket.getMarketId() + "");
					if (null == market.getCityId()) {
						result.setStatusCode("-1");
						result.setMsg("城市名不能为空");
						return result;
					} else {
						GoodsTransferMap.put("sProvinceId", market.getProvinceId());// 出发地省会id
						AreaDTO areadto = areaToolService.getAreaDetail(market.getProvinceId(), null, null);
						GoodsTransferMap.put("sProvinceName", areadto.getpParentName()); // 出发地省会名称
						GoodsTransferMap.put("sCityId", market.getCityId()); // 出发地城市id
						areadto = areaToolService.getAreaDetail(null, market.getCityId(), null);
						GoodsTransferMap.put("sCityName", areadto.getParentName()); // 出发地城市名称
						GoodsTransferMap.put("sAreaId", market.getAreaId()); // 出发地区（县）id
						areadto = areaToolService.getAreaDetail(null, null, market.getAreaId());
						GoodsTransferMap.put("sAreaName", areadto.getArea()); // 出发地区（县）名称
						// ***************************
						// 获取市，区，县 组合
						String province = areadto.getpParentName() == null ? "" : (areadto.getpParentName() + "/"); // 省
						String city = areadto.getParentName() == null ? "" : (areadto.getParentName() + "/");// 市
						String area = areadto.getArea() == null ? "" : areadto.getArea(); // 区
						String sDetail = province + city + area;
						GoodsTransferMap.put("sDetail", sDetail); // 出发地
						// 省份+城市+区或县（/
						// 作为分隔符）
						GoodsTransferMap.put("sDetailedAddress", businessDTO.getAddress()); // 发货地详细地址(补充详情地址);

						String merchantAddress = sDetail.replace("/", "") + businessDTO.getAddress(); // 店铺地址

						AreaDTO areaDTO = null; // 获取目的地的地理文字坐标，如果市区没有，则查询省
						areaDTO = areaToolService.getByAreaName(areadto.getParentName());
						if (null == areaDTO) {
							areaDTO = areaToolService.getByAreaName(areadto.getpParentName());
						}
						GoodsTransferMap.put("sLng", areaDTO.getLng()); // 出发地经度
						GoodsTransferMap.put("sLat", areaDTO.getLat()); // 出发地纬度
						GoodsTransferMap.put("merchantAddress", merchantAddress); // 平台配送商家店铺地址
					}
				} else {
					result.setStatusCode("-1");
					result.setMsg("城市名不能为空");
					return result;
				}
			}
			if (null != businessDTO.getCityId()&&businessDTO.getCityId()>0) {
				GoodsTransferMap.put("sCityId", businessDTO.getCityId()); // 出发地城市id
				GoodsTransferMap.put("sCityName", businessDTO.getCity()); // 出发地城市名称
				GoodsTransferMap.put("sAreaId", businessDTO.getAreaId()); // 出发地区（县）id
				GoodsTransferMap.put("sAreaName", businessDTO.getArea()); // 出发地区（县）名称
				// 获取市，区，县 组合
				String province = businessDTO.getProvince() == null ? "" : (businessDTO.getProvince() + "/"); // 省
				String city = businessDTO.getCity() == null ? "" : (businessDTO.getCity() + "/");// 市
				String area = businessDTO.getArea() == null ? "" : businessDTO.getArea(); // 区
				String sDetail = province + city + area;
				GoodsTransferMap.put("sDetail", sDetail); // 出发地 省份+城市+区或县（/
				// 作为分隔符）
				GoodsTransferMap.put("sDetailedAddress", businessDTO.getAddress()); // 发货地详细地址(补充详情地址);

				String merchantAddress = sDetail.replace("/", "") + businessDTO.getAddress(); // 店铺地址

				AreaDTO areaDTO = null; // 获取目的地的地理文字坐标，如果市区没有，则查询省
				areaDTO = areaToolService.getByAreaId(businessDTO.getCityId());

				if (null == areaDTO) {
					areaDTO = areaToolService.getByAreaName(businessDTO.getProvince());
				}
				if(null==areaDTO){
					result.setStatusCode("-1");
					result.setMsg("商铺ID："+businessId+"对应的省，市区域未维护！");
					return result;
				}
				GoodsTransferMap.put("sCityId", businessDTO.getCityId()); // 出发地城市id
				GoodsTransferMap.put("sCityName", businessDTO.getCity()); // 出发地城市名称
				GoodsTransferMap.put("sAreaId", businessDTO.getAreaId()); // 出发地区（县）id
				GoodsTransferMap.put("sAreaName", businessDTO.getArea()); // 出发地区（县）名称
				GoodsTransferMap.put("sLng", areaDTO.getLng()); // 出发地经度
				GoodsTransferMap.put("sLat", areaDTO.getLat()); // 出发地纬度
				GoodsTransferMap.put("merchantAddress", merchantAddress); // 平台配送商家店铺地址
			}

			/****** 出发地信息end **********/

			Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
			/****** 出发地信息end **********/

			OrderBaseinfoDTO orderObj=getHessianOrderbaseService().getByOrderNo(orderNo);
		    logger.info(orderObj+"");
			if (null!=orderObj&&null!=orderObj.getOrderNo()&&orderObj.getOrderNo()>0) {  //如果此订单已经存在，就让用户重新下单
		    	result.setStatusCode("-1");
				result.setMsg("此订单"+orderNo+"已存在，请刷新页面重新下单！");
				return result;
			}
			/*** 保存收货地址BEGIN ***/
			DeliveryAddress addressEntity = null;
			if (StringUtils.isNotEmpty(jsonAddress) && orderNo != null && orderNo > 0) {
				addressEntity = JSONObject.parseObject(jsonAddress, DeliveryAddress.class);
				addressEntity.setOrderNo(orderNo);
				addressEntity.setCreateUser(memberId);
				addressEntity.setCreateTime(DateUtil.getNow());
				addressEntity.setStatus("1");
				if (StringUtils.isEmpty(addressEntity.getGender())) {
					addressEntity.setGender("0");
				}
				getHessianDeliveryAddressService().insert(addressEntity);
			}
			/*** 保存收货地址END ***/

			/****** 目的地信息begin **********/
			GoodsTransferMap.put("eProvinceId", ""); // 目的地省会id
			GoodsTransferMap.put("eProvinceName", addressEntity.getDistrict1()); // 目的地省会名称
			GoodsTransferMap.put("eCityId", ""); // 目的地城市id
			GoodsTransferMap.put("eCityName", addressEntity.getDistrict2()); // 目的地城市名称
			GoodsTransferMap.put("eAreaId", ""); // 目的地的区(县);id
			GoodsTransferMap.put("eAreaName", addressEntity.getDistrict3()); // 目的地的区(县);名称

			String eprovince = addressEntity.getDistrict1() == null ? "" : (addressEntity.getDistrict1() + "/"); // 省
			String ecity = addressEntity.getDistrict2() == null ? "" : (addressEntity.getDistrict2() + "/");// 市
			String earea = addressEntity.getDistrict3() == null ? "" : addressEntity.getDistrict3(); // 区

			String eDetail = eprovince + ecity + earea;
			GoodsTransferMap.put("eDetail", eDetail); // 目的地 省份+城市+区域（/ 作为分隔符）
			GoodsTransferMap.put("eDetailedAddress", addressEntity.getDetail()); // 目的地详细地址(补充详情地址);
			AreaDTO eareaDTO = null; // 获取目的地的地理文字坐标，如果市区没有，则查询省
			eareaDTO = areaToolService.getByAreaName(addressEntity.getDistrict2());
			if (null == eareaDTO) {
				eareaDTO = areaToolService.getByAreaName(addressEntity.getDistrict1());
			}
			GoodsTransferMap.put("eLng", eareaDTO.getLng()); // 目的地经度
			GoodsTransferMap.put("eLat", eareaDTO.getLat()); // 目的地纬度
			/****** 目的地信息end **********/

			Double newOrderAmount = 0D;
			// 订单信息实体类
			OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
			// 订单产品信息list
			List<OrderProductDetailEntity> entityList = new ArrayList<>();
			// 产品购买数量list， 用于减库存
			List<Map<String, Object>> stockList = new ArrayList<>();

			GoodsTransferMap.put("totalWeight", businessInfo.getOrderWeight()); // 总重量
			GoodsTransferMap.put("totalSize", ""); // 总体积 二选一

			// 车辆类型: -1:不限 1:小型面包 2:金杯 3:小型平板 4:中型平板
			// 5:小型厢货 6:大型厢货 7:敞车 8:厢式货车 9 高栏车
			// 10 平板车 ,11 集装箱, 12 保温车, 13 冷藏车, 14 活鲜水车
			GoodsTransferMap.put("carType", "-1"); // 车型
			// 发货方式: 1:不限; 2:整车; 3:零担
			GoodsTransferMap.put("sendGoodsType", "1"); // 发货方式
			GoodsTransferMap.put("carLength", "-1"); // 车长
			GoodsTransferMap.put("freight", "0"); // 意向运费 0表示面议
			GoodsTransferMap.put("remark", businessInfo.getMessage()); // 货主留言
			GoodsTransferMap.put("shipperName", buyerMember.getRealName()); // 当前货主名称
			GoodsTransferMap.put("shipperMobile", buyerMember.getMobile()); // 当前货主电话
			GoodsTransferMap.put("platform", businessInfo.getDistributeMode()); // 是否平台配送
			GoodsTransferMap.put("merchantMemberId", businessId); // 平台配送商家会员id
			GoodsTransferMap.put("merchantName", businessDTO.getName()); // 平台配送商家名称
			GoodsTransferMap.put("merchantMobile", sellerMember.getMobile()); // 平台配送商家电话
			GoodsTransferMap.put("merchantTitle", businessDTO.getShopsName()); // 平台配送商家店铺名称

			// 车辆类型: -1:不限 1:小型面包 2:金杯 3:小型平板 4:中型平板
			// 5:小型厢货 6:大型厢货 7:敞车 8:厢式货车 9 高栏车
			// 10 平板车 ,11 集装箱, 12 保温车, 13 冷藏车, 14 活鲜水车
			GoodsTransferMap.put("carType", "-1"); // 车型
			// 发货方式: 1:不限; 2:整车; 3:零担
			GoodsTransferMap.put("sendGoodsType", "1"); // 发货方式
			GoodsTransferMap.put("carLength", "-1"); // 车长
			GoodsTransferMap.put("freight", "0"); // 意向运费 0表示面议
			GoodsTransferMap.put("remark", businessInfo.getMessage()); // 货主留言
			GoodsTransferMap.put("shipperName", buyerMember.getRealName()); // 当前货主名称
			GoodsTransferMap.put("shipperMobile", buyerMember.getMobile()); // 当前货主电话
			GoodsTransferMap.put("platform", businessInfo.getDistributeMode()); // 是否平台配送
			GoodsTransferMap.put("merchantMemberId", businessId); // 平台配送商家会员id
			GoodsTransferMap.put("merchantName", businessDTO.getName()); // 平台配送商家名称
			GoodsTransferMap.put("merchantMobile", sellerMember.getMobile()); // 平台配送商家电话
			GoodsTransferMap.put("merchantTitle", businessDTO.getShopsName()); // 平台配送商家店铺名称
			// 产品id list， 用来查找校验产品信息
			List<Long> pIdList = new ArrayList<>();
			String[] strArr = jsonArrStr.split("#_#");
			StringBuffer selectedList = new StringBuffer(orderNo + "_");
			;
			for (int i2 = 0, len2 = strArr.length; i2 < len2; i2++) {
				// 商品id_数量
				String[] pArr = strArr[i2].split("_");
				Long productId = Long.parseLong(pArr[0]);
				Double quantity = Double.parseDouble(pArr[1]);

				selectedList.append(productId);
				if (i2 + 1 < len2) {
					selectedList.append("-");
				}

				// 检查商品 数量
				if (quantity == null || quantity.compareTo(zero) == 0) {
					result.setStatusCode("-1");
					result.setMsg("请输入采购量");
					return result;
				}

				pIdList.add(productId);

				OrderProductDetailEntity productEntity = new OrderProductDetailEntity();
				productEntity.setOrderNo(orderNo);
				productEntity.setProductId(productId.intValue());
				productEntity.setPurQuantity(quantity);
				entityList.add(productEntity);
				/************ 货物信息封装begin ***********/
				ProductDto productDto = productToolService.getByProductId(productId + "");
				Integer goodsTypeCode=110;
				String produtcName="其他商品";
				if (null != reBusinessCategoryToolService.getCateIdByBusId(businessId)) {
					Long categoryId = reBusinessCategoryToolService.getCateIdByBusId(businessId).getCategoryId();

					ProductCategoryDTO categoryDTO = productCategoryToolService.getCategoryById(categoryId);

					if (null != categoryDTO) {
						String cateName = categoryDTO.getCateName();// 获取当前店铺的主营分类名称
						goodsTypeCode = GoodsTypeEnum.getKeyByValue(cateName);
					}

				}
				if (null!=productDto&&null!=productDto.getProductName()) {
					produtcName=productDto.getProductName();
				}
				GoodsTransferMap.put("goodsType", goodsTypeCode); // 货物类型
				GoodsTransferMap.put("goodsName",produtcName ); // 货物名称


				/************ 货物信息封装end ***********/

			}
			GoodsTransferMap.put("selectedList", selectedList.toString()); // 平台配送商家店铺地址
			cartProductIdList.addAll(pIdList);

			// 插入订单信息
			orderEntity.setOrderNo(orderNo);
			orderEntity.setOrderSource(orderSource); // 订单来源 1卖家代客下单
			orderEntity.setChannel(channel); // 渠道 1android 2ios 3pc
			orderEntity.setOrderStatus(Order.STATUS_NOT_PAY);
			orderEntity.setSellMemberId(sellerId.intValue());
			orderEntity.setOrderTime(DateUtil.getNow());
			orderEntity.setShopName(businessDTO.getShopsName());
			orderEntity.setBusinessId(Integer.parseInt(businessId));
			orderEntity.setMarketId(Integer.parseInt(businessDTO.getMarketId()));
			orderEntity.setCreateTime(DateUtil.getNow());
			orderEntity.setUpdateTime(DateUtil.getNow());
			orderEntity.setOutmarkStatus("0");
			orderEntity.setExamineStatus("0");
			orderEntity.setOrderType(Order.TYPE_FROM_NSY);
			orderEntity.setPromType("0");
			orderEntity.setMessage(businessInfo.getMessage());
			orderEntity.setHasCustomer("0");
			orderEntity.setDistributeMode(businessInfo.getDistributeMode());
			orderEntity.setMemberId(memberId == null ? null : memberId.intValue());
			orderEntity.setCreateUserId(memberId == null ? "" : memberId + "");
			String posInfo = businessBaseinfoToolService.getPosInfoByBusinessId(Long.parseLong(businessId));
			logger.info("Pos info referense: businessId: " + businessId + ", Pos info: " + posInfo);
			orderEntity.setValidPosNum(posInfo);
			// 预付款 判断如果预付款

			// orderEntity.setPrepayAmt(Double.valueOf(businessInfo.getPrepayAmt()));

			if (null!=businessInfo.getOrderWeight()) {
				orderEntity.setOrderWeight(Double.valueOf(businessInfo.getOrderWeight())); // 订单重量
			}


			// 设置订单商品库存
			List<ProductDto> productList = productToolService.getListByIds(pIdList);
			for (int i = 0, len = productList.size(); i < len; i++) {
				ProductDto pDTO = productList.get(i);
				Double pPrice = pDTO.getPrice();
				Double existedQuantity = pDTO.getStockCount();
				String state = pDTO.getState();
				// 检查产品是否已下架
				if (!"3".equals(state) || existedQuantity.compareTo(zero) == 0) {
					result.setStatusCode("-1");
					result.setMsg("您购买的部分商品卖完了，请您刷新商品后重新提交");
					return result;
				}

				if (pPrice.compareTo(zero) == 0) {
					result.setStatusCode("-1");
					result.setMsg("您购买的部分商品价格为0,请您刷新商品后重新提交");
					return result;
				}

				for (int j = 0, len2 = entityList.size(); j < len2; j++) {
					OrderProductDetailEntity entity = entityList.get(j);
					Integer pId = entity.getProductId();
					Double purchaseQuantity = entity.getPurQuantity();
					if (pId == pDTO.getProductId().intValue()) {
						Map<String, Object> map = new HashMap<>();
						map.put("productId", pId);
						// 判断库存是否足够
						if (purchaseQuantity.compareTo(existedQuantity) > 0) {
							logger.warn("库存不足, 订单号：" + orderNo + ",产品:" + entity.getProductName() + "的购买数量:"
									+ purchaseQuantity + " 和库存:" + existedQuantity);
							result.setStatusCode("-5");
							result.setMsg(
									"产品id:" + entity.getProductId() + "目前库存数量" + existedQuantity + ",库存不足，请重新输入采购量！");
							return result;
						} else {
							map.put("status", "3");
						}

						entity.setPrice(pPrice);
						Double productPayAmount = MathUtil.round(MathUtil.mul(pPrice, purchaseQuantity), 2);
						newOrderAmount = MathUtil.add(newOrderAmount, productPayAmount);
						entity.setProductName(pDTO.getProductName());
						entity.setPrice(pDTO.getPrice());
						entity.setNeedToPayAmount(productPayAmount);
						entity.setTradingPrice(productPayAmount);

						map.put("stockCount", MathUtil.sub(existedQuantity, purchaseQuantity));
						stockList.add(map);

						entity.setUnit(pDTO.getUnit());
					}
				}
			}

			orderEntity.setOrderAmount(newOrderAmount);
			orderEntity.setDiscountAmount(0D);
			orderEntity.setPayAmount(userPayAmount.compareTo(orderAmount) == 0 ? newOrderAmount : userPayAmount);

			// 尾款计算规则
			// 尾款=商品总价-预付款+买家佣金
			double productAmt = MathUtil.sub(orderEntity.getOrderAmount(),
					businessInfo.getPrepayAmt() == null ? 0d : Double.valueOf(businessInfo.getPrepayAmt()));// 商品尾款
			double restAmt = MathUtil.add(productAmt, buyerComsn); // 尾款

			/*** 活动相关 START ***/
			GdOrderActivityResultDTO orderActResult = orderActivityToolService.checkOrderActivity(orderEntity,
					entityList);
			// 农商友匹配买家佣金
			if ("2".equals(inputDTO.getOrderSource()) && buyerComsn.compareTo(0D) > 0
					&& orderActResult.getBuyerActInfo() == null) {
				result.setStatusCode("-1");
				result.setMsg("买家佣金跟系统设置不匹配, 请刷新页面重新下单");
				return result;
			}
			if (newOrderAmount.compareTo(orderAmount) != 0) { // 如果数据库中的商品单价*商品数量
				// 之和跟下单时总额不一致，可认为是单价发送编号导致
				result.setStatusCode("-1");
				result.setMsg("商品单价进行了修改, 请刷新页面重新下单");
				return result;
			}
			// 如果平台配送规则不在可用规则中，则说明下单过程中有规则被禁用，需要用户重新下单
			Integer distributeMode = 0;
			if (null != orderEntity.getDistributeMode()) {
				distributeMode = Integer.parseInt(orderEntity.getDistributeMode());
			}

			if (distributeMode == 1) { // 如果是平台配送，才需要加预付款
				orderEntity.setRestAmt(restAmt); // 尾款
			}
			// 如果是平台配送，预付款跟规则中的预付款不一致，则提示规则变化
			Double prepayAmt = ParamsUtil.getDoubleFromString(businessInfo.getPrepayAmt());
			if (distributeMode == 1 && null != orderActResult.getBuyerActInfo()
					&& prepayAmt.compareTo(orderActResult.getBuyerActInfo().getPrepayAmt()) != 0) {
				result.setStatusCode("-1");
				result.setMsg("预付款跟规则中的预付款不一致, 请刷新页面重新下单");
				return result;
			}
			/*if (null != orderActResult.getBuyerActInfo()
					&& null != orderActResult.getBuyerActInfo().getDistributeModeList()
					&& orderActResult.getBuyerActInfo().getDistributeModeList().size() > 0
					&& !orderActResult.getBuyerActInfo().getDistributeModeList().contains(distributeMode)) {
				logger.info("DistributeMode: " + orderEntity.getDistributeMode() + ", distributeModeList: "
						+ orderActResult.getBuyerActInfo().getDistributeModeList());
				result.setStatusCode("-1");
				result.setMsg("配送规则已修改, 请刷新页面重新下单");
				return result;
			}*/
			if ("2".equals(inputDTO.getOrderSource()) && orderActResult.getBuyerActInfo() != null) {
				Double buyerCommsn = MathUtil.add(orderActResult.getBuyerActInfo().getMarketCommision(),
						orderActResult.getBuyerActInfo().getPlatCommision());
				if (buyerCommsn.compareTo(buyerComsn) != 0) {
					logger.error("佣金不一致，买家佣金为：" + buyerComsn + ",平台佣金和市场佣金为：" + buyerCommsn);
					result.setStatusCode("-1");
					result.setMsg("买家佣金与系统规则不一致, 请刷新页面重新下单");
					return result;
				}
			}

			// 农批商来源要匹配卖家佣金
			if ("1".equals(inputDTO.getOrderSource()) && sellerComsn.compareTo(0D) > 0
					&& orderActResult.getSellerActInfo() == null) {
				result.setStatusCode("-1");
				result.setMsg("卖家佣金与系统规则不一致, 请刷新页面重新下单");
				return result;
			}
			if ("1".equals(inputDTO.getOrderSource()) && orderActResult.getSellerActInfo() != null
					&& orderActResult.getSellerActInfo().getMarketCommision().compareTo(sellerComsn) != 0) {
				Double sellerCommsn = MathUtil.add(orderActResult.getSellerActInfo().getMarketCommision(),
						orderActResult.getSellerActInfo().getPlatCommision());
				if (sellerCommsn.compareTo(sellerComsn) != 0) {
					result.setStatusCode("-1");
					result.setMsg("卖家佣金与系统规则不一致, 请刷新页面重新下单");
					return result;
				}
			}

			// 订单总额=商品总额+买家平台佣金+买家市场佣金
			// double
			// buyPlatAmt=orderActResult.getBuyerActInfo()==null?0:orderActResult.getBuyerActInfo().getPlatCommision();//买家平台佣金
			double totalPayAmt = MathUtil.add(orderAmount, buyerComsn);
			orderEntity.setTotalPayAmt(totalPayAmt); // 订单总额+买家佣金(包含市场佣金和买家佣金)
			// 农批商佣金大于订单金额
			// if(orderActResult.getSellerActInfo() != null
			// &&
			// orderActResult.getSellerActInfo().getMarketCommision().compareTo(orderEntity.getPayAmount())
			// > 0){
			// statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_COMMISSION_ERROR);
			// return statusCode;
			// }
			// 获取订单商品活动信息
			List<OrderActRelationEntity> orderActList = orderActivityToolService.getOrderRelationDetail(orderActResult,
					orderNo);
			// 获取订单费用信息
			List<OrderFeeItemDetailDTO> orderActFeeList = orderFeeItemDetailToolService
					.getOrderFeeDetail(orderActResult, orderNo);
			/*** 活动相关 END ***/

			// 插入订单商品明细
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("orderBase", orderEntity);
			totalMap.put("orderProductList", entityList);
			totalMap.put("stockList", stockList); // 减少库存
			if (orderActList != null && orderActList.size() > 0) {
				totalMap.put("orderActList", orderActList);
			}
			if (orderActFeeList != null && orderActFeeList.size() > 0) {
				totalMap.put("orderActFeeList", orderActFeeList);
			}
			totalMap.put("prepayAmt", businessInfo.getPrepayAmt());
			totalMapList.add(totalMap);

			// 清空缓存
			try {
				if (sellerId != null) {
					dataToolService.cleanTradeCacheSpecial(sellerId, TimeCacheType.HOUR_CACHE);
				}
			} catch (Exception e) {
				logger.error("清空缓存失败", e);
				result.setStatusCode("-10001");
				result.setMsg("清空缓存失败, 请刷新页面重新下单");
				return result;
			}

			// 新建线程 新增客户关联表
			new AddReMemCustThread(memberToolService, orderEntity).start();

			getHessianOrderbaseService().batchAddOrder(totalMapList, cartInfoMap);

			totalMapList.clear();
			// 自提和商家配送不用发布货源
			if (distributeMode == 1) {
				// 发布货源，添加订单信息到本地库
				logger.info("发布货源信息：" + selectedList);
				Map<String, Object> retuls = AddGoodsTransfer(GoodsTransferMap);
				if (retuls.get("flag").equals("N")) {
					logger.error("发布货源出错：" + retuls.get("message") + "_订单信息：" + selectedList);

					result.setStatusCode("-1");
					result.setMsg("发布货源出错!" + retuls.get("message"));
					return result;
				}
			}

		}
		result.setStatusCode("0");
		result.setMsg("下单成功!");
		return result;
	}

	@Override
	public ErrorCodeEnum updateStatus(OrderCallbackInputDTO paramValue) throws Exception {
		String orderNo = paramValue.getOrderNo();
		if (StringUtils.isEmpty(orderNo)) {
			return ErrorCodeEnum.ORDER_CALLBACK_ORDERNO_IS_NULL;
		}
		Integer type = paramValue.getType();
		if (type == null) {
			return ErrorCodeEnum.ORDER_CALLBACK_TYPE_IS_NULL;
		}
		String userId = paramValue.getUserId();
		if (StringUtils.isEmpty(userId)) {
			userId = "0";
		}

		OrderBaseinfoDTO dto = new OrderBaseinfoDTO();
		dto.setMemberAddressId(orderNo);
		dto.setUpdateUserId(userId);
		int result = 0, result2 = 0, lastResult = 0;
		boolean prepayZero = false;
		String cancelReason = "";
		switch (type) {
		case 1:// 货源分配不成功
		case 6:// 货源分配失败
			cancelReason = "分配物流失败";
			dto.setCancelReason(cancelReason);
			dto.setOrderStatus(EOrderStatus.CANNEL.getCode());
			dto.setCloseTimeStr(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME));
			lastResult = getHessianOrderbaseService().updateStatus(dto);
			break;
		case 3:// 司机验货不通过 需要退预退款
		case 4:// 司机3天未提交验货结果(三天内验货超时) 需要退预退款
			if (type == 3) {
				cancelReason = "验货不通过";
			}
			if (type == 4) {
				cancelReason = "验货超时";
			}
			dto.setCancelReason(cancelReason);
			dto.setOrderStatus(EOrderStatus.CANNEL.getCode());
			dto.setCloseTimeStr(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME));
			// result = getHessianOrderbaseService().updateStatus(dto);
			result2 = refundPrepayAmt(dto);
			logger.info("回调更新订单状态参数：" + dto.getMemberAddressId() + "回调退预付款状态结果：" + result2);
			if (result2 > 0) {
				lastResult = 1;
			}
			break;
		case 2:// 司机验货通过
			dto.setOrderStatus(EOrderStatus.WAIT_SIGN.getCode());
			dto.setDeliverTimeStr(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME));
			lastResult = getHessianOrderbaseService().updateStatus(dto); // 友盟推送
			break;
		case 5:// 司机确认送达
			lastResult = getHessianDeliveryAddressService().updateArriveTime(orderNo);
			break;
		case 7:// 货运订单生产成功
			String nstOrderNo = paramValue.getNstOrderNo();// 货运单号
			String distributeMode = paramValue.getDistributeMode();// 配送方式
			Integer companyId = paramValue.getCompanyId();// 物流公司ID
			Integer driverId = paramValue.getDriverId();// 司机ID

			DeliveryAddressDTO deliveryObj = new DeliveryAddressDTO();
			deliveryObj.setNstOrderNo(nstOrderNo);
			deliveryObj.setDistributeMode(distributeMode);
			deliveryObj.setCompanyId(companyId);
			deliveryObj.setDriverId(driverId);
			deliveryObj.setUpdateUser(Long.parseLong(userId));
			deliveryObj.setMemberAddressId(orderNo);
			result = getHessianDeliveryAddressService().update(deliveryObj);

			// 判断订单是否预付款为0，则更新状态为待发货
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("memberAddressId", orderNo);
			param.put("feeType", EOrderFeeType.PRE_PAY.getCode());
			List<OrderFeeItemDetailDTO> feeList = orderFeeItemDetailToolService.getOrderFeeByParam(param);
			Double zero = new Double(0D);
			for (OrderFeeItemDetailDTO fee : feeList) {
				if (zero.equals(fee.getAmount())) {
					prepayZero = true;
					dto.setOrderStatus(EOrderStatus.WAIT_SEND.getCode());
					result2 = getHessianOrderbaseService().updateStatus(dto);
				}
			}
			if (prepayZero && result > 0 && result2 > 0) {
				lastResult = 1;
			}
			if (!prepayZero && result > 0) {
				lastResult = 1;
			}
			// 友盟推送及短信推送
			try {
				MemberBaseinfoDTO memberDTO = memberToolService.getbyMemberAddressId(dto.getMemberAddressId());
				if (memberDTO != null) {
					UMengPushMessage pushMessage2 = new UMengPushMessage();

					GdMessageDTO gdMessage2 = new GdMessageDTO();
					Map<String,String> extraMap = new HashMap<String,String>();
					extraMap.put("openmenu", "NSY_DD");
					gdMessage2.setExtraMap(extraMap);
					gdMessage2.setSendApp("1");
					gdMessage2.setSendType("1");
					gdMessage2.setTicket("农商友");
					gdMessage2.setTitle("农商友");
					gdMessage2.setContent("已为您的订单指派司机，请尽快支付预付款。【谷登科技】");
					// gdMessage2.setAfter_open("go_app");
					gdMessage2.setDevice_tokens(memberDTO.getDevice_tokens());
					gdMessage2.setProduction_mode(false);
					logger.info("友盟推送参数：" + gdMessage2);
					pushMessage2.pushMessage(gdMessage2);
				}
			} catch (Exception e) {
				logger.info("友盟推送失败, 异常信息" + e);
			}
			break;
		}

		if (lastResult > 0) {
			return ErrorCodeEnum.ORDER_CALLBACK_SUCCESS;
		} else {
			return ErrorCodeEnum.ORDER_CALLBACK_FAIL;
		}
	}

	/**
	 * 退预付款
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	private int refundPrepayAmt(OrderBaseinfoDTO dto) throws Exception {
		int result = 0;
		// #region 通过货源ID查买家违约金
		GdOrderPenaltyQueryDTO penalty = new GdOrderPenaltyQueryDTO();
		Map<String, String> refundParams = new HashMap<String, String>();
		initQueryObject(penalty, dto);
		penalty = orderActivityToolService.queryOrderPenalty(penalty);
		// #endregion

		// #region 退款参数
		refundParams.put("orderNo", String.valueOf(dto.getOrderNo()));
		refundParams.put("payCenterNumber", dto.getStatementId());
		refundParams.put("sellerRefundAmt", "0");
		refundParams.put("logisRefundAmt", "0");
		refundParams.put("platRefundAmt", "0");
		refundParams.put("refundAmt", String.valueOf(penalty.getPrepayAmt()));
		refundParams.put("appKey", "nsy");
		refundParams.put("refundRequestNo", "0");
		refundParams.put("refundUserId", "0");// 退款人ID
		refundParams.put("refundReason", dto.getCancelReason());// 退款原因
		// #endregion

		// 添加买家违约金记录
		dealFeeDetail(refundParams);

		// #region 退预付款
		RefundResponseDTO refundResponse = getHessianOrderbaseService().preOrderRefund(refundParams);
		if (refundResponse.getCode().equals(MsgCons.C_10000)) {
			result = 1;
		}
		// #endregion
		return result;
	}

	/**
	 * 初始化查预付款违约金参数对象
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void initQueryObject(GdOrderPenaltyQueryDTO queryDTO, OrderBaseinfoDTO orderDTO) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberAddressId", orderDTO.getMemberAddressId());
		List<OrderBaseinfoDTO> orderList = getHessianOrderbaseService().getListByCondition(paramMap);

		OrderBaseinfoDTO orderInfo = null;
		if (CollectionUtils.isNotEmpty(orderList)) {
			orderInfo = orderList.get(0);
			paramMap.put("payType", EPayType.ALIPAY.getCode());
			paramMap.put("orderNo", orderInfo.getOrderNo());
			List<PaySerialnumberDTO> payList = getPaySerialnumberService().getListByCondition(paramMap);
			if (CollectionUtils.isNotEmpty(payList)) {
				orderDTO.setStatementId(payList.get(0).getStatementId());// 支付中心流水号
			}

			orderDTO.setOrderNo(orderInfo.getOrderNo());
			queryDTO.setOrderNo(String.valueOf(orderInfo.getOrderNo()));
			queryDTO.setOrderAmount(orderInfo.getOrderAmount());
			queryDTO.setPayAmount(orderInfo.getPayAmount());
			queryDTO.setMarketId(orderInfo.getMarketId());
			queryDTO.setBusinessId(orderInfo.getBusinessId());
			queryDTO.setSellerId(orderInfo.getSellMemberId());
			queryDTO.setBuyerId(orderInfo.getMemberId());
			// queryDTO.setPrepayAmt(orderInfo.getPrepayAmt());

			List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(paramMap);
			// 查找订单活动信息
			List<OrderActRelationDTO> productRefActIdList = orderActivityToolService
					.getByOrderNo(orderInfo.getOrderNo());

			if (productList != null && productList.size() > 0) {
				if (productRefActIdList != null && productRefActIdList.size() > 0) {
					List<GdProductActInfoDTO> pIdList = new ArrayList<>();
					queryDTO.setProductList(pIdList);
					for (OrderProductDetailDTO dto : productList) {
						GdProductActInfoDTO pDTO = new GdProductActInfoDTO();
						pDTO.setPrice(dto.getPrice());
						pDTO.setProductId(dto.getProductId());
						pDTO.setProductAmount(dto.getNeedToPayAmount());
						pDTO.setQuantity(dto.getPurQuantity());
						boolean hasAct = false;
						for (OrderActRelationDTO actRef : productRefActIdList) {
							if (actRef.getProductId().intValue() == dto.getProductId().intValue()) {
								GdOrderActivityDetailDTO actInfo = pDTO.getActInfo();
								if (actInfo == null) {
									actInfo = new GdOrderActivityDetailDTO();
								}
								List<GdOrderActivityDTO> pActList = actInfo.getProductActInfo()
										.get(actRef.getProductId());
								if (pActList == null) {
									pActList = new ArrayList<>();
								}
								GdOrderActivityDTO actDto = new GdOrderActivityDTO();
								actDto.setActId(actRef.getActId());
								actDto.setActType(actRef.getActType());
								pActList.add(actDto);
								actInfo.getProductActInfo().put(actRef.getProductId(), pActList);
								pDTO.setActInfo(actInfo);
								hasAct = true;
							}
						}
						if (hasAct) {
							pIdList.add(pDTO);
						}

					}
				}
			}
		}
	}

	/**
	 * 添加违约金记录
	 * 
	 * @param params
	 * @throws Exception
	 */
	private void dealFeeDetail(Map<String, String> params) throws Exception {
		Map<String, Object> feelMap = new HashMap<String, Object>();
		// 查询农商友用户订单预付款违约金记录
		String orderNo = params.get("orderNo");// 订单号
		String payerType = "nsy"; // 付方类型
		String feeType = EOrderFeeType.PENALTY_PAY.getCode(); // 4违约金

		feelMap.put("orderNo", orderNo);
		feelMap.put("feeType", feeType);
		feelMap.put("payerType", payerType);
		int total = orderFeeItemDetailToolService.getTotal(feelMap);
		if (total > 0) {
			orderFeeItemDetailToolService.deleteByParam(feelMap);
		}
		List<OrderFeeItemDetailEntity> feelList = new ArrayList<OrderFeeItemDetailEntity>();
		OrderFeeItemDetailEntity entity1 = new OrderFeeItemDetailEntity();// 违约金（卖家）
		entity1.setOrderNo(Long.parseLong(orderNo));
		entity1.setFeeType(feeType);
		entity1.setPayerType(payerType);
		entity1.setCreateUser(params.get("refundUserId"));
		entity1.setPayeeType(EOrderFeeUserType.NPS.getCode());
		entity1.setAmount(Double.parseDouble(params.get("sellerRefundAmt")));
		feelList.add(entity1);

		OrderFeeItemDetailEntity entity2 = new OrderFeeItemDetailEntity();// 违约金（物流公司）
		entity2.setOrderNo(Long.parseLong(orderNo));
		entity2.setFeeType(feeType);
		entity2.setPayerType(payerType);
		entity2.setCreateUser(params.get("refundUserId"));
		entity2.setPayeeType(EOrderFeeUserType.WLGS.getCode());
		entity2.setAmount(Double.parseDouble(params.get("logisRefundAmt")));
		feelList.add(entity2);

		OrderFeeItemDetailEntity entity3 = new OrderFeeItemDetailEntity();// 违约金（平台）
		entity3.setOrderNo(Long.parseLong(orderNo));
		entity3.setFeeType(feeType);
		entity3.setPayerType(payerType);
		entity3.setCreateUser(params.get("refundUserId"));
		entity3.setPayeeType(EOrderFeeUserType.PLATFORM.getCode());
		entity3.setAmount(Double.parseDouble(params.get("platRefundAmt")));
		feelList.add(entity3);

		orderFeeItemDetailToolService.batchInsert(feelList);
	}

	/**
	 * 现场采销数据入库
	 */
	@Override
	public RestultEntity miningSalesAddOrder(String jsonStr) throws Exception {
		RestultEntity result=new RestultEntity();
		
		MiningOrderInputDTO inputDTO = (MiningOrderInputDTO) GSONUtils.fromJsonToObject(jsonStr, MiningOrderInputDTO.class);
		Map<String,Object> orderMap=new HashMap<>();
		String orderBaseInfo=inputDTO.getOrderBaseInfo(); //订单基本信息
		Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo()); //获取订单号
		if (StringUtils.isNotEmpty(orderBaseInfo)&&StringUtils.isNotEmpty(inputDTO.getOrderProductDetail())) {
			OrderBaseinfoEntity orderBase=JSONObject.parseObject(orderBaseInfo, OrderBaseinfoEntity.class);
			
			if (null==orderBase.getSellMemberId()||orderBase.getSellMemberId()<=0) {
				result.setStatusCode("-1");
				result.setMsg("买家Id无效");
				return result;
			}
		/*	if (StringUtils.isEmpty(orderBase.getShopName())) {
				result.setStatusCode("-1");
				result.setMsg("商铺名称不能为空");
				return result;
			}*/
			if (null==orderBase.getBusinessId()) {
				result.setStatusCode("-1");
				result.setMsg("商铺Id不能为空");
				return result;
			}
			if (null==orderBase.getOrderAmount()) {
				result.setStatusCode("-1");
				result.setMsg("商品总价不能为空");
				return result;
			}
			if (null==orderBase.getPayAmount()) {
				result.setStatusCode("-1");
				result.setMsg("实收款不能为空");
				return result;
			}
			if (null==orderBase.getActivityType()) {
				result.setStatusCode("-1");
				result.setMsg("活动类型不能为空");
				return result;
			}
			BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(orderBase.getBusinessId()+"");
			orderBase.setShopName(businessDTO.getShopsName()); //获取商铺名称
			orderBase.setOrderNo(orderNo); 
			orderBase.setOrderSource("1"); //订单来源 3POS机
			orderBase.setOrderType("1"); //1:农商友采购订单
			orderBase.setHasCustomer("0"); //'是否为补充订单:0不是(默认),1是' 
			orderBase.setCreateTime(new Date()); //创建时间
			orderBase.setUpdateTime(new Date()); //更新时间
			orderBase.setOrderTime(new Date()); //订单时间
			orderBase.setOrderStatus("13"); //13 待确认
			orderBase.setDiscountAmount(0.00); //为了适应之前的接口
			orderBase.setTotalPayAmt(orderBase.getPayAmount()); //农商友用到此字段
			String businessId=orderBase.getBusinessId().toString(); //商铺id
			ReBusinessMarketDTO remarket=reBusinessMarketToolService.getByBusinessId(Long.valueOf(businessId)); //根据商铺id查询市场id
            if (null==remarket) {
            	result.setStatusCode("-1");
				result.setMsg("此商铺未分配市场暂不能下单");
				return result;
			}
            String marketId=remarket.getMarketId()+"";
            orderBase.setMarketId(Integer.parseInt(marketId)); //设置市场Id，为农商友扫描查询积分使用
            orderMap.put("orderBase", orderBase);
			
			List<OrderProductDetailEntity> orderProductDetails=new ArrayList<>(); 
			JSONArray orderProductDetailArr = JSONUtils.parseArray(inputDTO.getOrderProductDetail());//订单商品详情信息

			for (int ii = 0, length = orderProductDetailArr.size(); ii < length; ii++) {
				JSONObject jsonObject = (JSONObject) orderProductDetailArr.get(ii);
				OrderProductDetailEntity productDetail = JSONObject.toJavaObject(jsonObject,
						OrderProductDetailEntity.class);
				if(null==productDetail.getProductId()) {
					result.setStatusCode("-1");
					result.setMsg("商品Id不能为空");
					return result;
				}
				int count=productToolService.validProductAliveByBusinessId(productDetail.getProductId().toString()); //根据productId查询对应的产品信息
				if (count<1) {
					result.setStatusCode("-1");
					result.setMsg("商品不存在或已下架");
					return result;
				}
				 if(null==productDetail.getProductName()) {
					result.setStatusCode("-1");
					result.setMsg("商品名称不能为空");
					return result;
				} 
				/*if(null==productDetail.getPurQuantity()) {
					result.setStatusCode("-1");
					result.setMsg("采购数量不能为空");
					return result;
				}
				if(null==productDetail.getPrice()) {
					result.setStatusCode("-1");
					result.setMsg("单价不能为空");
					return result;
				}
				if (null==productDetail.getSubTotal()) {
					result.setStatusCode("-1");
					result.setMsg("小计不能为空");
					return result;
				}*/
				productDetail.setUnit("3");
				productDetail.setOrderNo(orderNo);
				productDetail.setCreateTime(new Date()); //创建时间
				productDetail.setUpdateTime(new Date()); //更新时间
				productDetail.setHasDelivered(0);  //'是否出货 0未出货 1出货中 2已出货',
				productDetail.setNeedToPayAmount(productDetail.getTradingPrice());
				orderProductDetails.add(productDetail);
			}
			orderMap.put("orderProductList", orderProductDetails);
			boolean res=getHessianOrderbaseService().addOrder(orderMap);
			if (!res) {
				result.setStatusCode("-1000");
				result.setMsg("下单失败，请稍后重试");
			}else{
				result.setStatusCode("0");
				result.setMsg(orderNo+"");
			}
			
		}else{
			result.setStatusCode("-1000");
			result.setMsg("订单基本信息或订单详情信息不能为空");
			return result;
		}
		return result;
	}
    
	/**
	 * 修改订单
	 */
	@Override
	public int updateByOrderNo(OrderBaseinfoDTO dto) throws Exception {
		
		return getHessianOrderbaseService().updateByOrderNo(dto);
	}
    /**
     * 根据订单号获取订单基本信息和详情信息
     */
	@Override
	public OrderBaseinfoDTO getproductDetailListByOrderNo(long orderNo) throws Exception {
		OrderBaseinfoDTO baseinfoDTO=getHessianOrderbaseService().getBaseOrderInfoById(orderNo);
		if (null!=baseinfoDTO) {
			String sesslerid=baseinfoDTO.getSellMemberId()+"";
			if (null!=sesslerid) {
				MemberCertifiDTO member=memberToolService.getByUserId(Long.valueOf(sesslerid));
				if (member!=null) {
					baseinfoDTO.setSellMobile(member.getMobile()); //设置卖家手机信息
				}
			}
			
		}
		return baseinfoDTO;  
	}




}
