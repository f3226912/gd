package com.gudeng.commerce.gd.api.controller.v2;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.params.OrderOutmarketBean;
import com.gudeng.commerce.gd.api.params.WeighCarParamsBean;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.CarBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.OrderoutmarketinfoToolService;
import com.gudeng.commerce.gd.api.service.PreWeighCarDetailToolService;
import com.gudeng.commerce.gd.api.service.ShipperToolService;
import com.gudeng.commerce.gd.api.service.WeighCarToolService;
import com.gudeng.commerce.gd.api.service.impl.FileUploadToolServiceImpl;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.JavaMd5;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.ReWeighCarBusinessDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;


@Controller
@RequestMapping("/v2/shipper")
public class ShipperController extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ShipperController.class);
	
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	private PreWeighCarDetailToolService preWeighCarDetailToolService;
	
	@Autowired
	private ShipperToolService shipperService;
	
	@Autowired
	private MemberToolService memberToolService;
	
	@Autowired
	private CarBaseinfoToolService carBaseinfoToolService;
	
	@Autowired
	private WeighCarToolService weighCarToolService;
	
	@Autowired
	private OrderoutmarketinfoToolService orderoutmarketinfoToolService;
	
	@Autowired
	public FileUploadToolServiceImpl fileUploadService; 
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	/**
	 * 通过名字模糊查询商品名称
	 * @param request
	 * @param response
	 * @param businessBaseinfoDTO
	 */
	@RequestMapping("getShop")
	public void getShop(HttpServletRequest request, HttpServletResponse response, BusinessBaseinfoDTO businessBaseinfoDTO){
		ObjectResult result = new ObjectResult();
		try{
			if (businessBaseinfoDTO.getShopsName()!=null || businessBaseinfoDTO.getMobile()!=null) {
				List<BusinessBaseinfoDTO> businessBaseinfoDTOList=businessBaseinfoToolService.getShop(businessBaseinfoDTO);
				result.setObject(businessBaseinfoDTOList);
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
		}catch(Exception e){
			logger.warn("[ERROR]查找商铺失败：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 根据过磅记录id获取货主某次过磅的入场商品
	 * @param weighCarId
	 * @param request
	 * @param response
	 */
	@RequestMapping("getWeighProduct")
	public void getWeighProduct(Long weighCarId, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			List<PreWeighCarDetailDTO> list = preWeighCarDetailToolService.getByWeighCarId(weighCarId);
			if(list != null){
				for(PreWeighCarDetailDTO dto : list){
					double weigh = dto.getWeigh() == null ? 0 : dto.getWeigh();
					double marginWeigh = dto.getMarginWeigh() == null ? 0 : dto.getMarginWeigh();
					dto.setMarginWeigh(MathUtil.sub(weigh, marginWeigh));
				}
			}
			result.setObject(list);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			logger.warn("[Error]获取货主入场商品异常:", e);
			result.setMsg("获取货主入场商品异常");
		}
		renderJson(result, request, response);
	}

	
	
	/**
	 * 保存过磅记录
	 * 
	 * @return
	 */
	@RequestMapping("/goods/saveWeightLog")
	public void saveWeightLog(HttpServletRequest request, HttpServletResponse response, WeighCarDTO weighCarDTO,@RequestParam(value="file",required=false) MultipartFile[] files) {
		ObjectResult result = new ObjectResult();
		// 判断用户是否注册
		if (weighCarDTO.getGoodsPhone() == null) {
			logger.error("MemberId is null");
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}

		// 通过电话获得货主信息
		MemberBaseinfoDTO memberInfo = null;
		try {
			memberInfo = memberToolService.getByMobile(weighCarDTO.getGoodsPhone());
		} catch (Exception e1) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			e1.printStackTrace();
			logger.error("get memberinfo error" + e1.getMessage());
			return;
		}
		if (memberInfo == null) {
			// 如果未找到货主信息新增信息货主信息
			MemberBaseinfoEntity member = new MemberBaseinfoEntity();
			member.setAccount(weighCarDTO.getGoodsPhone());// 账号
			member.setLevel(4);// 会员类别
			member.setRealName(weighCarDTO.getGoolsName());
			member.setMobile(weighCarDTO.getGoodsPhone());// 手机号
			member.setStatus("1");// 状态1启用0未启用
			member.setCreateUserId("system");// 默认system创建
			member.setRegetype("5");//注册来源门岗
			String code =CommonUtils.getEightCode();
			member.setPassword(JavaMd5.getMD5(code+"gudeng2015@*&^-").toUpperCase());
			Long memberId = null;
			try {
				memberId = memberToolService.addMemberBaseinfoEnt(member);
				if (memberId != null) {
					
					logger.debug("setAccount success!");
					//发送短信通知
					//取redis缓存,获取通道号
					String channel = "";
					try{
						Object obj = redisClient.get("GDSMS_CHANNEL");
						System.out.println("redis channel:###############"+ obj);
						channel = obj==null? Constant.Alidayu.DEFAULTNO:obj.toString();
						System.out.println("redis channel:###############"+ channel);
					}catch(Exception e){
						//处理redis服务器异常
						e.printStackTrace();
						logger.info("获取redis 消息通道出错!");
					}
					String content=null;
					if(Constant.Alidayu.REDISTYPE.equals(channel)){
						content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE4,code);
						
					}else{
						content=code;
					}
					CommonUtils.autoRegistSendMsg(channel,content,member.getMobile());
					// 获取memberid
					weighCarDTO.setMemberId(memberId);
				} else {
					setResult(result, ErrorCodeEnum.FAIL, request, response);
					return;
				}
			} catch (Exception e) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				e.printStackTrace();
				logger.error("set memberinfo error" + e.getMessage());
				return;
			}
			
		}else{
			weighCarDTO.setMemberId(memberInfo.getMemberId());
		}
	
			
			// 获取车牌信息
			CarBaseinfoDTO carBaseinfo=null;
			try {
				carBaseinfo = carBaseinfoToolService.getByCarNumber(weighCarDTO.getCarNumber());
			} catch (Exception e1) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				e1.printStackTrace();
				return;
			}
			if (carBaseinfo == null) {
				//未获取车牌车牌就新增车牌信息
				CarBaseinfoEntity carinfo = new CarBaseinfoEntity();
				carinfo.setCarNumber(weighCarDTO.getCarNumber());//车牌号码
				carinfo.setCreateUserId(weighCarDTO.getCreateUserId());//创建人
				carinfo.setCreateTime(weighCarDTO.getCreateTime());//创建时间
				Long carId=null;
				try {
					carId = carBaseinfoToolService.insertEntity(carinfo);
				} catch (Exception e) {
					setResult(result, ErrorCodeEnum.FAIL, request, response);
					logger.error("add CarBaseinfoEntity error"+e.getMessage());
					e.printStackTrace();
					return;
				}
				if(carId==null){
					logger.error("carId is null");
					setResult(result, ErrorCodeEnum.FAIL, request, response);
					return;
				}
				weighCarDTO.setCarId(carId);//设置车辆id
			}else{
				weighCarDTO.setCarId(carBaseinfo.getCarId());
			}
			try {
				if(files == null){
					setResult(result, ErrorCodeEnum.UPLOAD_IMAGE_IS_NULL, request, response);
					return;
				}
				StringBuilder sb = new StringBuilder();
				for(MultipartFile file : files){
					String masterPicPath = fileUploadService.dataToPic(file);
					sb.append(masterPicPath).append("|");
				}	
				WeighCarEntity weighCarEntity =new WeighCarEntity();
			//保存入磅信息
				weighCarEntity.setMemberId(weighCarDTO.getMemberId());
				weighCarEntity.setCarId(weighCarDTO.getCarId());
				weighCarEntity.setType("1");//默认货主商
				weighCarEntity.setCreateUserId("242");
				weighCarEntity.setCreateTime(new Date());
				weighCarEntity.setTotalWeight(weighCarDTO.getTotalWeight());
				weighCarEntity.setStatus("1");//默认已进场
				weighCarEntity.setTapWeight("1");//皮重为空总重有数
				weighCarEntity.setTotalUnit(weighCarDTO.getTotalUnit());//总重单位（预留）
				weighCarEntity.setTotalMemberId(weighCarDTO.getTotalMemberId());//总重查验员ID
				weighCarEntity.setTotalCreateTime(weighCarEntity.getCreateTime());//总重称重时间
				weighCarEntity.setPlace(weighCarDTO.getPlace());//场地
				weighCarEntity.setQuality(weighCarDTO.getQuality());//质量，品质 1:优,2:良,3:中,4:差
				weighCarEntity.setAllWeigh(weighCarDTO.getAllWeigh());//满载 1:是,2:否
				weighCarEntity.setOthers(weighCarDTO.getOthers());//其他
				weighCarEntity.setCarNumberImage(sb.toString());//图片
				weighCarEntity.setMarketId(weighCarDTO.getMarketId());//市场id
				weighCarEntity.setIsFinish("2");//默认状态为2
			Long weighCarId = shipperService.saveWeightLog(weighCarEntity);
			if(weighCarId!=null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("menberId", weighCarEntity.getMemberId());//memberid
				map.put("weighCarId", weighCarId);//过磅记录id
				result.setObject(map);
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);	
			}else{
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
		return;
	}

	/**
	 * 提交
	 * 
	 * @return
	 */
	@RequestMapping("/goods/submitWeightLog") 
	public void submitWeightLog(HttpServletRequest request, HttpServletResponse response,  ReWeighCarBusinessDTO reWeighCarBusinessDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Integer res =shipperService.submit(reWeighCarBusinessDTO);
			if(res==1){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
			logger.error("submit error:"+e.getMessage());
		}
		
	}
	/**
	 * 添加产品
	 * 
	 * @return
	 */
	@RequestMapping("/goods/addProduct")
	public void addProduct(HttpServletRequest request, HttpServletResponse response,  PreWeighCarDetailDTO preWeighCarDetail) {
		ObjectResult result = new ObjectResult();
		try {
			Integer res =shipperService.addProduct(preWeighCarDetail);
			if(res==1){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);	
			}else{
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			logger.error("add product error"+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 修改产品
	 * 
	 * @return
	 */
	@RequestMapping("/goods/updateProduct")
	public void updateProduct(HttpServletRequest request, HttpServletResponse response,  PreWeighCarDetailDTO preWeighCarDetail) {
		ObjectResult result = new ObjectResult();
		
		try {
			Integer res =shipperService.updateProduct(preWeighCarDetail);
			if(res==1){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);	
			}else{
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
			logger.error("save product error:"+e.getMessage());
		}
	}
	/**
	 * 删除产品
	 * 
	 * @return
	 */
	@RequestMapping("/goods/delProduct")
	public void delProduct(HttpServletRequest request, HttpServletResponse response,  PreWeighCarDetailDTO preWeighCarDetail) {
		ObjectResult result = new ObjectResult();
		try {
			Integer res =shipperService.delProduct(preWeighCarDetail);
			if(res==1){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setResult(result, ErrorCodeEnum.FAIL, request, response);
			}
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
			logger.error("delete product error"+e.getMessage());
		}
	}
	/**
	 * 显示添加的产品
	 * 
	 * @return
	 */
	@RequestMapping("/goods/getProductList")
	public void getProductList(HttpServletRequest request, HttpServletResponse response,  PreWeighCarDetailDTO preWeighCarDetail) {
		ObjectResult result = new ObjectResult();
		try {
			 List<PreWeighCarDetailDTO> PreWeighCarDetailList =shipperService.getProductlist(preWeighCarDetail);
			 result.setObject(PreWeighCarDetailList);
			 setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			setResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
			logger.error("get productList error"+e.getMessage());
		}
	}
	
	
	
	/**
	 * 货主出场皮重过磅
	 */
	@RequestMapping("outMarketWeigh")
	public void outMarketWeigh(WeighCarParamsBean paramsBean, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		if(paramsBean.getWeighCarId() == null){
			setResult(result, ErrorCodeEnum.WIGHT_CAR_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			WeighCarDTO weighCarDTO = weighCarToolService.getById(paramsBean.getWeighCarId());
			if(weighCarDTO != null){
				WeighCarEntity entity = new WeighCarEntity();
				entity.setWeighCarId(weighCarDTO.getWeighCarId());
				entity.setTare(paramsBean.getTareWeight());
				if(weighCarDTO.getTotalWeight() != null && paramsBean.getTareWeight() != null){
					entity.setNetWeight(MathUtil.sub(weighCarDTO.getTotalWeight(), paramsBean.getTareWeight()));
				}
				entity.setTareMemberId(paramsBean.getTareMemberId());
				entity.setTapWeight("4");//皮总全过磅
				entity.setUpdateUserId(String.valueOf(paramsBean.getTareMemberId()));
				weighCarToolService.updateTareWeight(entity);
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("weighCarId", paramsBean.getWeighCarId());
			result.setObject(resultMap);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			logger.warn("[Error]皮重过磅异常:", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 货主确认出场
	 * @param paramsBean
	 * @param request
	 * @param response
	 */
	@RequestMapping("outMarket")
	public void outMarket(OrderOutmarketBean paramsBean, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		if(StringUtils.isBlank(paramsBean.getWeighCarId())){
			setResult(result, ErrorCodeEnum.WIGHT_CAR_ID_IS_NULL, request, response);
			return;
		}
		if(StringUtils.isBlank(paramsBean.getControllerId())){
			setResult(result, ErrorCodeEnum.CONTROLLER_ID_IS_NULL, request, response);
			return;
		}
		if(StringUtils.isBlank(paramsBean.getProductIds())){
			setResult(result, ErrorCodeEnum.PRODUCT_ID_IS_NULL, request, response);
			return;
		}
		
		try{
			/**出场信息*/
			OrderOutmarketinfoEntity orderOutmarketEntity = new OrderOutmarketinfoEntity();
			orderOutmarketEntity.setWeighCarId(Long.parseLong(paramsBean.getWeighCarId()));
			orderOutmarketEntity.setControllerId(Integer.parseInt(paramsBean.getControllerId()));
			orderOutmarketEntity.setOrderWeigh(paramsBean.getWeight());
			orderOutmarketEntity.setCreateUserId(String.valueOf(paramsBean.getControllerId()));
			orderOutmarketEntity.setCreateTime(new Date());
			orderOutmarketEntity.setUpdateTime(new Date());
			
			String[] productIdStrArr = paramsBean.getProductIds().split(",");
			List<String> productIdList = null;
			if(productIdStrArr != null){
				productIdList = Arrays.asList(productIdStrArr);
			}
			orderoutmarketinfoToolService.shipperOutMarket(orderOutmarketEntity, productIdList);
			
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			logger.warn("[Error]确认出场异常:", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
}
