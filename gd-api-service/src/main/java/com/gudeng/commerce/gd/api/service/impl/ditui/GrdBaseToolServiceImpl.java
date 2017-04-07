package com.gudeng.commerce.gd.api.service.impl.ditui;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.ditui.GiftNstOrderDTO;
import com.gudeng.commerce.gd.api.dto.ditui.GiftOrderDTO;
import com.gudeng.commerce.gd.api.dto.ditui.GrandGiftInputDTO;
import com.gudeng.commerce.gd.api.enums.DateTenEnum;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.GiftDetailEnum;
import com.gudeng.commerce.gd.api.service.ditui.GrdBaseToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.bi.dto.GrdProMemberInvitedRegisterDTO;
import com.gudeng.commerce.gd.bi.dto.GrdProPertenDTO;
import com.gudeng.commerce.gd.bi.service.GrdProMemberInvitedRegisterService;
import com.gudeng.commerce.gd.bi.service.GrdProPertenService;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftRecordEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGdGiftstoreService;
import com.gudeng.commerce.gd.promotion.service.GrdGiftDetailService;
import com.gudeng.commerce.gd.promotion.service.GrdGiftRecordService;
import com.gudeng.commerce.gd.promotion.service.GrdGiftService;
import com.gudeng.commerce.gd.promotion.service.GrdMemberService;

public class GrdBaseToolServiceImpl implements GrdBaseToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static GrdGiftDetailService giftDetailService;
	
	private static OrderBaseinfoService orderBaseinfoService;
	
	private static MemberBaseinfoService memberBaseinfoService;
	
	private static GrdGiftRecordService grdGiftRecordService;

	private static GrdGiftService grdGiftService;
	
	private static GrdMemberService grdMemberService;
	
	private static GrdProPertenService grdProPertenService;
	
	private static GrdGdGiftstoreService grdGdGiftstoreService;

	private static GrdProMemberInvitedRegisterService grdProMemberInvitedRegisterService;

	protected GrdProMemberInvitedRegisterService getHessianGrdProMemberInvitedRegisterService() throws MalformedURLException {
		String url = gdProperties.getGrdProMemberInvitedRegisterServiceUrl();
		if (grdProMemberInvitedRegisterService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProMemberInvitedRegisterService = (GrdProMemberInvitedRegisterService) factory.create(GrdProMemberInvitedRegisterService.class, url);
		}
		return grdProMemberInvitedRegisterService;
	}
	
	protected GrdMemberService getHessianGrdMemberService() throws MalformedURLException {
		String url = gdProperties.getGrdMemberServiceUrl();
		if (grdMemberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdMemberService = (GrdMemberService) factory.create(GrdMemberService.class, url);
		}
		return grdMemberService;
	}

	protected GrdGiftService getHessianGrdGiftService() throws MalformedURLException {
		String url = gdProperties.getGrdGiftServiceUrl();
		if (grdGiftService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGiftService = (GrdGiftService) factory.create(GrdGiftService.class, url);
		}
		return grdGiftService;
	}

	
	protected MemberBaseinfoService getHessianMemberService() throws MalformedURLException {
		String url = gdProperties.getMemberUrl();
		if(memberBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class, url);
		}
		return memberBaseinfoService;
	}

	protected GrdGiftDetailService getHessianGrdGiftDetailService() throws MalformedURLException {
		String url = gdProperties.getGiftDetailServiceUrl();
		if (giftDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			giftDetailService = (GrdGiftDetailService) factory.create(GrdGiftDetailService.class, url);
		}
		return giftDetailService;
	}

	private OrderBaseinfoService getHessianOrderbaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderBaseinfo.url");
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, hessianUrl);
		}
		return orderBaseinfoService;
	}
	
	protected GrdGiftRecordService getHessianGrdGiftRecordService() throws MalformedURLException {
		String url = gdProperties.getGrdGiftRecordServiceUrl();
		if (grdGiftRecordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGiftRecordService = (GrdGiftRecordService) factory.create(GrdGiftRecordService.class, url);
		}
		return grdGiftRecordService;
	}
	
	protected GrdProPertenService getHessianGrdProPertenService() throws MalformedURLException {
		String url = gdProperties.getGrdProPertenServiceUrl();
		if (grdProPertenService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProPertenService = (GrdProPertenService) factory.create(GrdProPertenService.class, url);
		}
		return grdProPertenService;
	}
	

	protected GrdGdGiftstoreService getHessianGrdGdGiftstoreService() throws MalformedURLException {
		String url = gdProperties.getGrdGdGiftstoreServiceUrl();
		if (grdGdGiftstoreService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGdGiftstoreService = (GrdGdGiftstoreService) factory.create(GrdGdGiftstoreService.class, url);
		}
		return grdGdGiftstoreService;
	}
	
	public List<GiftOrderDTO> getGiftOrderList(MemberBaseinfoDTO memberBaseinfoDTO) throws Exception{
		List<GiftOrderDTO> returnGiftOrderDTOs=new ArrayList<GiftOrderDTO>();//返回所用
		//判断首次注册，增加一个可发放礼品订单--start
		//需要改为memberId
		Map map =new HashMap();
		map.put("memberId", memberBaseinfoDTO.getMemberId());
		map.put("type", GiftDetailEnum.REGIST_TYPE.getKey());
		List<GrdGiftDetailDTO> registGift = getHessianGrdGiftDetailService().getDetailByMap(map);
//		List<GrdGiftDetailDTO> registGift = getHessianGrdGiftDetailService().getDetailByMemberIdAndType(String.valueOf(memberBaseinfoDTO.getMemberId()),GiftDetailEnum.REGIST_TYPE.getKey());
		if(registGift==null || registGift.size()==0){//判断首次注册是否有领取过礼品
			if(DateUtil.getBetweenDays(memberBaseinfoDTO.getCreateTime(), new Date())<3 && memberBaseinfoDTO.getLevel()!=null && (memberBaseinfoDTO.getLevel().intValue()==2 ||memberBaseinfoDTO.getLevel().intValue()==3)){
				//三天内注册的农商友和农速通用户，不区分注册来源
				GiftOrderDTO giftOrderDTO=new GiftOrderDTO();
				giftOrderDTO.setOrderNo("首次邀请注册");
				giftOrderDTO.setOrderPrice(0d);
				giftOrderDTO.setMobile(memberBaseinfoDTO.getMobile());
				giftOrderDTO.setRealName(memberBaseinfoDTO.getRealName());
				giftOrderDTO.setOrderTimeStr(DateUtil.toString(memberBaseinfoDTO.getCreateTime(),DateUtil.DATE_FORMAT_DATETIME));
				giftOrderDTO.setType(GiftDetailEnum.REGIST_TYPE.getKey());
				returnGiftOrderDTOs.add(giftOrderDTO);
			}
		}
		//判断首次注册，增加一个可发放礼品订单--end
		
		//判断可发放礼品订单--start
		Map queryOrder =new HashMap();
		queryOrder.put("memberId", memberBaseinfoDTO.getMemberId());
		queryOrder.put("marketId", memberBaseinfoDTO.getMarketId());
		List<OrderBaseinfoDTO> orderList = getHessianOrderbaseService().getAbleGiftOrder(queryOrder);	//获取三天内可以发放礼品的订单
		
		translate(orderList, returnGiftOrderDTOs);//未处理已经领取过礼物的订单
		
		//判断可发放礼品订单--end

		return returnGiftOrderDTOs;
	}
	
	
	public List<GiftNstOrderDTO> getGiftNstOrderList(Map map) throws Exception{
		List<GiftNstOrderDTO> returnGiftOrderDTOs=new ArrayList<GiftNstOrderDTO>();//返回所用
		MemberBaseinfoDTO memberBaseinfoDTO = getHessianMemberService().getById(String.valueOf(map.get("memberId")));
		//判断首次注册，增加一个可发放礼品订单--start
		map.put("type", GiftDetailEnum.NST_REGIST_TYPE.getKey());
		List<GrdGiftDetailDTO> registGift = getHessianGrdGiftDetailService().getDetailByMap(map);
//		List<GrdGiftDetailDTO> registGift = getHessianGrdGiftDetailService().getDetailByMemberIdAndType(String.valueOf(map.get("memberId")),"4");
		if(registGift==null || registGift.size()==0){//判断首次注册是否有领取过礼品
			if(DateUtil.getBetweenDays(memberBaseinfoDTO.getCreateTime(), new Date())<3 && memberBaseinfoDTO.getLevel()!=null && memberBaseinfoDTO.getLevel().intValue()==2 
					){
//					&& memberBaseinfoDTO.getLevel()!=null && (memberBaseinfoDTO.getLevel().intValue()==2 ||memberBaseinfoDTO.getLevel().intValue()==3)){
				//三天内注册的农商友和农速通用户，不区分注册来源
				GiftNstOrderDTO giftOrderDTO=new GiftNstOrderDTO();
				String time = DateUtil.toString(memberBaseinfoDTO.getCreateTime(),"yyyyMMdd");
				giftOrderDTO.setCount(0);
				giftOrderDTO.setDescription(time+"首次邀请注册");
				giftOrderDTO.setType(GiftDetailEnum.NST_REGIST_TYPE.getKey());
				returnGiftOrderDTOs.add(giftOrderDTO);
			}
		}
		map.remove("type");
		List<GrdProPertenDTO> list = getHessianGrdProPertenService().getList(map);
		for(GrdProPertenDTO dto:list){
			GiftNstOrderDTO giftOrderDTO=new GiftNstOrderDTO();
//			String time = String.valueOf(dto.getCode()/10);
			int time=dto.getCode()/10;
			int ten= dto.getCode() % 10;
			String type =String.valueOf(dto.getType());
			String description=""+time;
			if(DateTenEnum.DATE_FIRST.getKey()==ten){
				description+=DateTenEnum.DATE_FIRST.getValue();
			}else if(DateTenEnum.DATE_SECOND.getKey()==ten){
				description+=DateTenEnum.DATE_SECOND.getValue();
			}else if(DateTenEnum.DATE_THIRD.getKey()==ten){
				description+=DateTenEnum.DATE_THIRD.getValue();
			}
			
			if(GiftDetailEnum.NST_SUPPLYOFGOOD_TYPE.getKey().equals(type)){
				description+=GiftDetailEnum.NST_SUPPLYOFGOOD_TYPE.getValue();
			}else if(GiftDetailEnum.NST_INFOORDER_TYPE.getKey().equals(type)){
				description+=GiftDetailEnum.NST_INFOORDER_TYPE.getValue();
				giftOrderDTO.setDescription(dto.getCode()+GiftDetailEnum.NST_INFOORDER_TYPE.getValue());
			}else if(GiftDetailEnum.NST_FREIGHTORDER_TYPE.getKey().equals(type)){
				description+=GiftDetailEnum.NST_FREIGHTORDER_TYPE.getValue();
			}
			
			giftOrderDTO.setDescription(description);
			giftOrderDTO.setCount(dto.getCount());
			giftOrderDTO.setType(type);
			giftOrderDTO.setCode(dto.getCode());
//			giftOrderDTO.setCustomerId(memberBaseinfoDTO.getMemberId().intValue());
			giftOrderDTO.setId(dto.getId());
			returnGiftOrderDTOs.add(giftOrderDTO);
		}
		
		//判断可发放礼品订单--end

		return returnGiftOrderDTOs;
	}
	

	public MemberBaseinfoDTO checkMobile(String mobile) throws Exception{
		MemberBaseinfoDTO memberBaseinfoDTO=new MemberBaseinfoDTO(); 
		if(StringUtils.isEmpty(mobile)){//手机号为空，设置memberId=-1
			memberBaseinfoDTO.setMemberId(-1L); 
			return memberBaseinfoDTO;
		}
		Pattern pattern = Pattern.compile("^[1][34587][0-9]{9}$");
		Matcher matcher = pattern.matcher(mobile);
		if(!matcher.matches()){//手机不符合规则,设置memberId=-2
			memberBaseinfoDTO.setMemberId(-2L);
			return memberBaseinfoDTO;
		}
		MemberBaseinfoDTO m = getHessianMemberService().getByMobile(mobile);
		if(m==null){//用户不存在则,设置memberId=-3
			memberBaseinfoDTO.setMemberId(-3L);
			return memberBaseinfoDTO;
		}else{
			memberBaseinfoDTO=m;
		}
		return memberBaseinfoDTO;
	}

	@Override
	public void translate(List<OrderBaseinfoDTO> orderList, List<GiftOrderDTO> giftOrderDTOs) throws Exception {
		if(orderList!=null){
			for(OrderBaseinfoDTO order:orderList){
				GiftOrderDTO giftOrderDTO=new GiftOrderDTO();
				giftOrderDTO.setOrderNo(String.valueOf(order.getOrderNo()));
				giftOrderDTO.setOrderPrice(order.getPayAmount());//获取订单支付金额
				giftOrderDTO.setOrderStatus(order.getOrderStatus());
				giftOrderDTO.setOrderTimeStr(DateUtil.toString(order.getOrderTime(),DateUtil.DATE_FORMAT_DATETIME));
				giftOrderDTO.setBusinessId(order.getBusinessId());
				if(StringUtils.isBlank(order.getCustomerMobile())){
					if(order.getBuyerType() == null || order.getBuyerType() == 7){
						continue;
					}else{
						giftOrderDTO.setRealName(order.getRealName());
						giftOrderDTO.setMobile(order.getMobile());
					}
				}else {
					giftOrderDTO.setRealName(order.getCustomerName());
					giftOrderDTO.setMobile(order.getCustomerMobile());
				}
				giftOrderDTO.setShopName(order.getShopName());
				giftOrderDTO.setType(GiftDetailEnum.ORDER_TYPE.getKey());
				giftOrderDTOs.add(giftOrderDTO);
			}
		}
	}

	
	@Override
	public List<GrdGiftRecordDTO> getRecordByMemberIdAndStatus(Map queryMap) throws Exception {
		return getHessianGrdGiftRecordService().getRecordByMemberIdAndStatus(queryMap);
	}

	@Override
	public int getRecordTotalByMemberIdAndStatus(Map queryMap) throws Exception {
		return getHessianGrdGiftRecordService().getRecordTotalByMemberIdAndStatus(queryMap);
	}
	
	@Override
	public List<GrdGiftDTO> getGiftList(String marketId, Map queryMap) throws Exception {
		return getHessianGrdGiftService().getGiftList(marketId, queryMap);
	}


	@Override
	public int getGiftTotal(String marketId) throws Exception {
		return getHessianGrdGiftService().getGiftTotal(marketId);
	}


	@Override
	public String grantGift(GrandGiftInputDTO grandGiftInputDTO) throws Exception {
		boolean invited=false;//判断是否需要插入到bi邀请注册的表中
		String returnString="OK";
		Integer userId=grandGiftInputDTO.getGrdUserId();
		String carNo = grandGiftInputDTO.getCarNo();
//		String mobile = grandGiftInputDTO.getMobile();
		String customerId = grandGiftInputDTO.getCustomerId();
		String type = grandGiftInputDTO.getType();
//		Integer giftstoreId=grandGiftInputDTO.getGiftstoreId();
		String sourceType="1";//农批的发放礼品，若app没有传入，直接设置为1
//		String sourceType = grandGiftInputDTO.getSourceType();
//		if(StringUtils.isEmpty(sourceType)){
//			sourceType="1";//农批的发放礼品，若app没有传入，直接设置为1
//		}
//		MemberBaseinfoDTO memberBaseinfoDTO=checkMobile(mobile);
		MemberBaseinfoDTO memberBaseinfoDTO=getHessianMemberService().getById(customerId);
		if(memberBaseinfoDTO==null){
			return "customerId有误";
		}
		Map queryMap=new HashMap();
		queryMap.put("grdUserId", userId);
		queryMap.put("sourceType", sourceType);
		List<GrdGdGiftstoreDTO> list = getStoreByUserAndType(queryMap);
		if(list==null || list.size()==0){
			return "地推用户无所属团队";
		}
		Integer giftstoreId=list.get(0).getId();//获取当前地推用户的仓库id

		GrdMemberDTO grdMemberDTO = getHessianGrdMemberService().getById(String.valueOf(userId));
		if(grdMemberDTO==null){
			return "地推用户ID有误";
		}
		Integer marketId=grdMemberDTO.getMarketId().intValue();
		String name = grdMemberDTO.getName();
		
		if(userId==null || userId<=0){
			return "地推人员id输入不对";
		}
		
		JSONArray jsonArr = JSONUtils.parseArray(grandGiftInputDTO.getOrderDetails());
		if(jsonArr==null || jsonArr.size()==0){
			return "订单不能为空,请至少选择一条订单";
		}
		GrdGiftDetailDTO registDetail=new GrdGiftDetailDTO();
		List<GrdGiftDetailDTO> orderDetails=new ArrayList<GrdGiftDetailDTO>();
		for(int i=0, len=jsonArr.size(); i<len; i++){
			String jsonStr=jsonArr.getString(i);
			GrdGiftDetailDTO rdGiftDetailDTO = (GrdGiftDetailDTO) GSONUtils.fromJsonToObject(jsonStr, GrdGiftDetailDTO.class);
			String shopName=GSONUtils.getJsonValueStr(jsonStr, "shopName");//有的地方叫shopsName,有的地方叫shopName ,不好统一，直接用string传值
			rdGiftDetailDTO.setShopsName(shopName);
			if(StringUtils.isEmpty(rdGiftDetailDTO.getOrderNo())){
				return "第"+i+"条订单号为空";
			}
			if(rdGiftDetailDTO.getOrderPrice()==null || rdGiftDetailDTO.getOrderPrice()<0){
				return "订单号"+rdGiftDetailDTO.getOrderNo()+"的金额不对";
			}
			if(StringUtils.isEmpty(rdGiftDetailDTO.getType())){
				return "订单号"+rdGiftDetailDTO.getOrderNo()+"的类型为空";
			}
			rdGiftDetailDTO.setCreateUserId(String.valueOf(userId));//明细表，增加创建人，即地推人的id
			if(rdGiftDetailDTO.getType()!=null && rdGiftDetailDTO.getType().equals(GiftDetailEnum.ORDER_TYPE.getKey())){
				if(StringUtils.isEmpty(rdGiftDetailDTO.getShopsName())){
					return "订单号"+rdGiftDetailDTO.getOrderNo()+"的商铺名称为空";
				}
				if(rdGiftDetailDTO.getBusinessId()==null || rdGiftDetailDTO.getBusinessId()<=0){
					return "订单号"+rdGiftDetailDTO.getOrderNo()+"的商铺id不正确";
				}
				Map map=new HashMap<>();
				map.put("orderNo", rdGiftDetailDTO.getOrderNo());
				List<GrdGiftDetailDTO> orderDto = getHessianGrdGiftDetailService().getList(map);
				if(orderDto!=null && orderDto.size()>0){
					return "订单号"+rdGiftDetailDTO.getOrderNo()+"已经发放过礼品，不允许再次发放！";
				}
				rdGiftDetailDTO.setOrderTimeStr(GSONUtils.getJsonValueStr(jsonStr, "orderTimeStr"));
			}
			if(rdGiftDetailDTO.getType()!=null && rdGiftDetailDTO.getType().equals(GiftDetailEnum.REGIST_TYPE.getKey())){
				rdGiftDetailDTO.setOrderNo("-1");//数据库中，无法对“首次注册邀请”建立索引，故存-1当做一个标识，在页面进行转意
				Map map =new HashMap();
				map.put("memberId", memberBaseinfoDTO.getMemberId());
				map.put("type", GiftDetailEnum.REGIST_TYPE.getKey());
				List<GrdGiftDetailDTO> giftDetailDTOs = getHessianGrdGiftDetailService().getDetailByMap(map);
//				List<GrdGiftDetailDTO> giftDetailDTOs = getHessianGrdGiftDetailService().getDetailByMemberIdAndType(String.valueOf(memberBaseinfoDTO.getMemberId()),GiftDetailEnum.REGIST_TYPE.getKey());
				if(giftDetailDTOs!=null && giftDetailDTOs.size()>0){
					return "手机号"+memberBaseinfoDTO.getMobile()+"已经发放过'首次注册邀请'的礼品，不允许再次发放！";
				}
				rdGiftDetailDTO.setOrderTimeStr(DateUtil.toString(memberBaseinfoDTO.getCreateTime(),DateUtil.DATE_FORMAT_DATETIME));
				registDetail=rdGiftDetailDTO;
				invited=true;
			}
			orderDetails.add(rdGiftDetailDTO);
		}
		
		List<Map<String, Object>> stockCountList=new ArrayList<Map<String, Object>>();
		
		Integer countGift=0;
		JSONArray jsonArrGift = JSONUtils.parseArray(grandGiftInputDTO.getGiftDeatils());
		if(jsonArrGift==null || jsonArrGift.size()==0){
			return "礼品不能为空,请至少选择一件礼品";
		}
		List<GrdGiftDetailDTO> giftDetails=new ArrayList<GrdGiftDetailDTO>();
		for(int i=0, len=jsonArrGift.size(); i<len; i++){
			String jsonStr=jsonArrGift.getString(i);
			GrdGiftDetailDTO rdGiftDetailDTO = (GrdGiftDetailDTO) GSONUtils.fromJsonToObject(jsonStr, GrdGiftDetailDTO.class);
			if(StringUtils.isEmpty(rdGiftDetailDTO.getGiftId())){
				return "第"+i+"条礼品Id为空";
			}
			if(StringUtils.isEmpty(rdGiftDetailDTO.getGiftName())){
				return "礼品Id为"+rdGiftDetailDTO.getGiftId()+"的礼品名称为空";
			}
			if(rdGiftDetailDTO.getCountNo()==null){
				return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品数量为空";
			}else if(rdGiftDetailDTO.getCountNo()<=0){
				return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品数量必须大于0";
			}
			GrdGiftDTO grdGiftDTO = getHessianGrdGiftService().getById(rdGiftDetailDTO.getGiftId());
			if(grdGiftDTO.getStockTotal() <=0||grdGiftDTO.getStockAvailable()<=0){
//				return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品数量库存不足";
				return rdGiftDetailDTO.getGiftName()+"礼品库存不足！";
			}
//			待领取数量
			int noCount = getHessianGrdGiftService().getNoCount(rdGiftDetailDTO.getGiftId());
			if(rdGiftDetailDTO.getCountNo()>grdGiftDTO.getStockAvailable()-noCount){
				//判断库存或者可用库存，本期两个统一，应该在下单的时候，减去可用库存，实际成交的时候，减去实际库存
//				return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品数量大于库存数量，请重新输入礼品数";
				return rdGiftDetailDTO.getGiftName()+"礼品库存不足！";
			}
			rdGiftDetailDTO.setType("1");//type=1为礼品明细
			rdGiftDetailDTO.setCreateUserId(String.valueOf(userId));//明细表，增加创建人，即地推人的id
			countGift+=rdGiftDetailDTO.getCountNo();
			giftDetails.add(rdGiftDetailDTO);
			
			
			Map map=new HashMap<>();
			map.put("stockTotal", grdGiftDTO.getStockAvailable()-rdGiftDetailDTO.getCountNo());
			map.put("id", grdGiftDTO.getId());
			map.put("orignValue", grdGiftDTO.getStockAvailable());
			map.put("realVale", grdGiftDTO.getStockAvailable()-rdGiftDetailDTO.getCountNo());
			map.put("createUserId", name);//直接设置地堆人员的姓名 
			stockCountList.add(map);
		}
		
		GrdGiftRecordEntity giftRecordEntity=new GrdGiftRecordEntity();
		giftRecordEntity.setMemberId(customerId);//增加客户id
		giftRecordEntity.setType(sourceType);//设置农速通或者农批的类型
		giftRecordEntity.setMobile(memberBaseinfoDTO.getMobile());
		giftRecordEntity.setRealName(memberBaseinfoDTO.getRealName());
		giftRecordEntity.setLevel(String.valueOf(memberBaseinfoDTO.getLevel()));
		giftRecordEntity.setMarketid(marketId);
		giftRecordEntity.setStatus(type);
		giftRecordEntity.setCount(countGift);
		giftRecordEntity.setCarNo(carNo);
		giftRecordEntity.setGiftstoreId(giftstoreId);
		Date date=new Date();
		giftRecordEntity.setCreateTime(date);
		giftRecordEntity.setCreateUserId(String.valueOf(userId));
		if(type!=null && type.equals("1")){//现场发放
			giftRecordEntity.setGrantUserId(String.valueOf(userId));
			giftRecordEntity.setGrantTime(date);
		}
		
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("type", type);
		map.put("record", giftRecordEntity);
		map.put("giftDetail", giftDetails);
		map.put("orderDetail", orderDetails);
		map.put("stockCountList", stockCountList);
		
		Long recordId = getHessianGrdGiftRecordService().addRecord(map);
		if(recordId.longValue()==-4){//增加库存校验。领取的礼品数量 > 库存数量-待领取数量时，提示库存不足。
			return "礼品库存不足，请检查库存数量";
		}else if(recordId<=0){
			return "订单可能已经被他人领取，请刷新后重试。";
		}
//		registDetail
//		插入bi库
		try{
			if(invited){
				GrdProMemberInvitedRegisterDTO dto=new GrdProMemberInvitedRegisterDTO();
				map.put("id", null);
				map.put("teamId", null);
				map.put("teamName", null);
				map.put("createTime", null);
				map.put("updateTime", null);
				map.put("regetypeName", null);
				map.put("updateUserId", null);
				map.put("createUserId", null);
				
				map.put("marketId", grdMemberDTO.getMarketId());
				map.put("marketName", grdMemberDTO.getMarket());
				map.put("grdId", grdMemberDTO.getId());
				map.put("grdMobile", grdMemberDTO.getMobile());
				map.put("grdUserName", grdMemberDTO.getName());
				map.put("registerTime", DateUtil.toString(memberBaseinfoDTO.getCreateTime(),DateUtil.DATE_FORMAT_DATETIME));
				map.put("account", memberBaseinfoDTO.getAccount());
				map.put("memberMobile", memberBaseinfoDTO.getMobile());
				map.put("realName", memberBaseinfoDTO.getRealName());
				map.put("regetype", memberBaseinfoDTO.getRegetype());
				map.put("memberId", memberBaseinfoDTO.getMemberId());
				map.put("type", 1);
				getHessianGrdProMemberInvitedRegisterService().insert(map);
			}
		}catch(Exception e){
			//do Nothing
			e.printStackTrace();
		}
		
		
		return returnString+"#_#"+recordId;
	}

	@Override
	public String centralized(String recordIds,String userId) throws Exception {
		
		String returnString="OK";
		GrdMemberDTO grdMemberDTO = getHessianGrdMemberService().getById(String.valueOf(userId));
		String name = grdMemberDTO.getName();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String []records=recordIds.split(",");
		for(int i=0;i<records.length;i++){
			if(StringUtils.isEmpty(records[i])){
				return "第"+i+"条记录Id为空";
			}
			GrdGiftRecordDTO grdGiftRecordDTO =  getHessianGrdGiftRecordService().getById(records[i]);
			if(grdGiftRecordDTO==null){
				return "第"+i+"条记录不存在";
			}
			if(grdGiftRecordDTO.getId()==null ){
				return "第"+i+"条记录不存在";
			}
			if(grdGiftRecordDTO.getStatus()==null || grdGiftRecordDTO.getStatus().equals("1")){
				return "ID为"+grdGiftRecordDTO.getId()+"的记录已经领取过礼品";
			}
			
			List<Map<String, Object>> stockCountList=new ArrayList<Map<String, Object>>();
			Map mapR =new HashMap<>();
			mapR.put("recordId", records[i]);
			mapR.put("type", 1);
			List<GrdGiftDetailDTO> giftDetailDTOs = getHessianGrdGiftDetailService().getList(mapR);//获取当前记录的所有礼品明细
			for(GrdGiftDetailDTO rdGiftDetailDTO:giftDetailDTOs){
				GrdGiftDTO grdGiftDTO = getHessianGrdGiftService().getById(rdGiftDetailDTO.getGiftId());
				if(grdGiftDTO.getStockTotal() <=0||grdGiftDTO.getStockAvailable()<=0){
//					return "礼品Id为"+rdGiftDetailDTO.getGiftId()+"(礼品名称为"+rdGiftDetailDTO.getGiftName()+")的礼品数量库存不足";
					return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品库存不足";
				}
				if(rdGiftDetailDTO.getCountNo()>grdGiftDTO.getStockAvailable()){
					//判断库存或者可用库存，本期两个统一，应该在下单的时候，减去可用库存，实际成交的时候，减去实际库存
//					return "礼品Id为"+rdGiftDetailDTO.getGiftId()+"(礼品名称为"+rdGiftDetailDTO.getGiftName()+")的礼品数量库存不足";
					return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品库存不足";
				}
				
				Map map=new HashMap<>();
				map.put("count", rdGiftDetailDTO.getCountNo());
				map.put("stockTotal", grdGiftDTO.getStockAvailable()-rdGiftDetailDTO.getCountNo());
				map.put("id", grdGiftDTO.getId());
				map.put("orignValue", grdGiftDTO.getStockAvailable());
				map.put("realVale", grdGiftDTO.getStockAvailable()-rdGiftDetailDTO.getCountNo());
				map.put("createUserId", name);//直接设置地堆人员的姓名 
				stockCountList.add(map);
			}		
			
			Map recordMap=new HashMap<>();
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("recordId", grdGiftRecordDTO.getId());
			map.put("grantTime", DateUtil.toString(new Date(),DateUtil.DATE_FORMAT_DATETIME));
			map.put("grantUserId", userId);
			map.put("recordId", grdGiftRecordDTO.getId());
			map.put("id", grdGiftRecordDTO.getId());
			map.put("stockCountList", stockCountList);
			list.add(map);
		}
		
		int  result = getHessianGrdGiftRecordService().centralized(list);
		if(result>0){
			String str="成功操作"+result+"条记录";
			return returnString+"#_#"+str;
		}else{
			return "其他地推人员，已经发放礼品，请刷新页面后重试";
		}
	}

	@Override
	public int getGiftTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftService().getTotal(map);
	}

	@Override
	public List<GrdGiftDTO> getGiftListPage(Map<String, Object> queryMap)
			throws Exception {
		return getHessianGrdGiftService().getListPage(queryMap);
	}


	@Override
	public List<GrdGiftDTO> getGiftListPage2(Map<String, Object> queryMap)
			throws Exception {
		return getHessianGrdGiftService().getListPage2(queryMap);
	}
	
	@Override
	public int getTotal2(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftService().getTotal2(map);
	}

	@Override
	public List<GrdGdGiftstoreDTO> getStoreByUserAndType(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftstoreService().getStoreByUserAndType(map);
	}


	@Override
	public String grantGiftNst(GrandGiftInputDTO grandGiftInputDTO) throws Exception {
		
		String returnString="OK";
		Integer userId=grandGiftInputDTO.getGrdUserId();
		String carNo = grandGiftInputDTO.getCarNo();
		String customerId = grandGiftInputDTO.getCustomerId();
		String type = grandGiftInputDTO.getType();
		String sourceType = sourceType="2";//农批的发放礼品，若app没有传入，直接设置为1
//		String sourceType = grandGiftInputDTO.getSourceType();
//		if(StringUtils.isEmpty(sourceType)){
//			sourceType="2";//农批的发放礼品，若app没有传入，直接设置为1
//		}
		if(StringUtils.isEmpty(sourceType)){
			sourceType="2";//农批的发放礼品，若app没有传入，直接设置为1
		}
		MemberBaseinfoDTO memberBaseinfoDTO=getHessianMemberService().getById(customerId);
		if(memberBaseinfoDTO==null){
			return "customerId有误";
		}
		Map queryMap=new HashMap();
		queryMap.put("grdUserId", userId);
		queryMap.put("sourceType", sourceType);
		List<GrdGdGiftstoreDTO> list = getStoreByUserAndType(queryMap);
		if(list==null || list.size()==0){
			return "地推用户无所属团队";
		}
		Integer giftstoreId=list.get(0).getId();//获取当前地推用户的仓库id

		GrdMemberDTO grdMemberDTO = getHessianGrdMemberService().getById(String.valueOf(userId));
		if(grdMemberDTO==null){
			return "地推用户ID有误";
		}
		Integer marketId=grdMemberDTO.getMarketId().intValue();
		String name = grdMemberDTO.getName();
		
		if(userId==null || userId<=0){
			return "地推人员id输入不对";
		}
		
		JSONArray jsonArr = JSONUtils.parseArray(grandGiftInputDTO.getNstOrderDetails());
		if(jsonArr==null || jsonArr.size()==0){
			return "订单不能为空,请至少选择一条订单";
		}
		List<GrdGiftDetailDTO> nstOrderDetails=new ArrayList<GrdGiftDetailDTO>();
		List<Map> nstOrderMaps=new ArrayList<Map>();

		for(int i=0, len=jsonArr.size(); i<len; i++){
			String jsonStr=jsonArr.getString(i);
			GrdGiftDetailDTO rdGiftDetailDTO = (GrdGiftDetailDTO) GSONUtils.fromJsonToObject(jsonStr, GrdGiftDetailDTO.class);
			Map map =new HashMap();
			map.put("memberId", memberBaseinfoDTO.getMemberId());
			if(rdGiftDetailDTO.getType()!=null && rdGiftDetailDTO.getType().equals(GiftDetailEnum.NST_REGIST_TYPE.getKey())){
				rdGiftDetailDTO.setOrderNo("-1");//数据库中，无法对“首次注册邀请”建立索引，故存-1当做一个标识，在页面进行转意
				map.put("type", GiftDetailEnum.NST_REGIST_TYPE.getKey());
				List<GrdGiftDetailDTO> giftDetailDTOs = getHessianGrdGiftDetailService().getDetailByMap(map);
				if(giftDetailDTOs!=null && giftDetailDTOs.size()>0){
					return "手机号"+memberBaseinfoDTO.getMobile()+"已经发放过'首次注册邀请'的礼品，不允许再次发放！";
				}
				rdGiftDetailDTO.setOrderTimeStr(DateUtil.toString(memberBaseinfoDTO.getCreateTime(),DateUtil.DATE_FORMAT_DATETIME));
			}else {
				map.put("type", rdGiftDetailDTO.getType());
				map.put("code", rdGiftDetailDTO.getCode());
				List<GrdGiftDetailDTO> giftDetailDTOs = getHessianGrdGiftDetailService().getDetailByMap(map);
				nstOrderMaps.add(map);
				if(giftDetailDTOs!=null && giftDetailDTOs.size()>0){
					return  rdGiftDetailDTO.getDescription()+"已经发放过礼品，不允许再次发放！";
				}
			}
			rdGiftDetailDTO.setCreateUserId(String.valueOf(userId));//明细表，增加创建人，即地推人的id
			nstOrderDetails.add(rdGiftDetailDTO);
		}
		
		List<Map<String, Object>> stockCountList=new ArrayList<Map<String, Object>>();
		Integer countGift=0;
		JSONArray jsonArrGift = JSONUtils.parseArray(grandGiftInputDTO.getGiftDeatils());
		if(jsonArrGift==null || jsonArrGift.size()==0){
			return "礼品不能为空,请至少选择一件礼品";
		}
		List<GrdGiftDetailDTO> giftDetails=new ArrayList<GrdGiftDetailDTO>();
		for(int i=0, len=jsonArrGift.size(); i<len; i++){
			String jsonStr=jsonArrGift.getString(i);
			GrdGiftDetailDTO rdGiftDetailDTO = (GrdGiftDetailDTO) GSONUtils.fromJsonToObject(jsonStr, GrdGiftDetailDTO.class);
			if(StringUtils.isEmpty(rdGiftDetailDTO.getGiftId())){
				return "第"+i+"条礼品Id为空";
			}
			if(StringUtils.isEmpty(rdGiftDetailDTO.getGiftName())){
				return "礼品Id为"+rdGiftDetailDTO.getGiftId()+"的礼品名称为空";
			}
			if(rdGiftDetailDTO.getCountNo()==null){
				return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品数量为空";
			}else if(rdGiftDetailDTO.getCountNo()<=0){
				return "礼品名称为"+rdGiftDetailDTO.getGiftName()+"的礼品数量必须大于0";
			}
			GrdGiftDTO grdGiftDTO = getHessianGrdGiftService().getById(rdGiftDetailDTO.getGiftId());
			if(grdGiftDTO.getStockTotal() <=0||grdGiftDTO.getStockAvailable()<=0){
				return rdGiftDetailDTO.getGiftName()+"礼品库存不足！";
			}
//			待领取数量
			int noCount = getHessianGrdGiftService().getNoCount(rdGiftDetailDTO.getGiftId());
			if(rdGiftDetailDTO.getCountNo()>grdGiftDTO.getStockAvailable()-noCount){
				return rdGiftDetailDTO.getGiftName()+"礼品库存不足！";
			}
			rdGiftDetailDTO.setType("1");//type=1为礼品明细
			rdGiftDetailDTO.setCreateUserId(String.valueOf(userId));//明细表，增加创建人，即地推人的id
			countGift+=rdGiftDetailDTO.getCountNo();
			giftDetails.add(rdGiftDetailDTO);
			
			
			Map map=new HashMap<>();
			map.put("stockTotal", grdGiftDTO.getStockAvailable()-rdGiftDetailDTO.getCountNo());
			map.put("id", grdGiftDTO.getId());
			map.put("orignValue", grdGiftDTO.getStockAvailable());
			map.put("realVale", grdGiftDTO.getStockAvailable()-rdGiftDetailDTO.getCountNo());
			map.put("createUserId", name);//直接设置地堆人员的姓名 
			stockCountList.add(map);
		}
		
		GrdGiftRecordEntity giftRecordEntity=new GrdGiftRecordEntity();
		giftRecordEntity.setType(sourceType);//范围：1表示农批，2表示农速通
		giftRecordEntity.setMemberId(customerId);
		giftRecordEntity.setMobile(memberBaseinfoDTO.getMobile());
		giftRecordEntity.setRealName(memberBaseinfoDTO.getRealName());
		giftRecordEntity.setLevel(String.valueOf(memberBaseinfoDTO.getLevel()));
		giftRecordEntity.setMarketid(marketId);
		giftRecordEntity.setStatus(type);
		giftRecordEntity.setCount(countGift);
		giftRecordEntity.setCarNo(carNo);
		giftRecordEntity.setGiftstoreId(giftstoreId);
		Date date=new Date();
		giftRecordEntity.setCreateTime(date);
		giftRecordEntity.setCreateUserId(String.valueOf(userId));
		if(type!=null && type.equals("1")){//现场发放
			giftRecordEntity.setGrantUserId(String.valueOf(userId));
			giftRecordEntity.setGrantTime(date);
		}
		
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("type", type);
		map.put("record", giftRecordEntity);
		map.put("giftDetail", giftDetails);
		map.put("nstOrderDetail", nstOrderDetails);
		map.put("stockCountList", stockCountList);
		
		Long recordId = getHessianGrdGiftRecordService().addRecordNst(map);
		try {
			if(recordId>0){//异步操作，可能导致实际已经发放礼品，但是记录没有更新
				getHessianGrdProPertenService().batchUpdate(customerId,nstOrderMaps);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(recordId.longValue()==-4){//增加库存校验。领取的礼品数量 > 库存数量-待领取数量时，提示库存不足。
			return "礼品库存不足，请检查库存数量";
		}else if(recordId<=0){
			return "订单可能已经被他人领取，请刷新后重试。";
		}
		return returnString+"#_#"+recordId;
	}

}