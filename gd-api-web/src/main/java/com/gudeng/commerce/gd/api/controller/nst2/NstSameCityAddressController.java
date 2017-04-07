package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.NstSameCityAddressToolService;
import com.gudeng.commerce.gd.api.service.NstSameCityCarlineApiService;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.util.AssignSameCityLineThread;
import com.gudeng.commerce.gd.api.util.AssignSameCityRuleThread;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.DistanceCalculationUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.api.util.ThreadPoolSingleton;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 农速通同城货源Controller
 * 
 * @author xiaojun
 */
@Controller
@RequestMapping("nst2/sameCityGoods")
public class NstSameCityAddressController extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(NstSameCityAddressController.class);
	@Autowired
	private NstSameCityAddressToolService nstSameCityAddressToolService;
	@Autowired
	private AreaToolService areaToolService;
	@Autowired
	private NstSameCityCarlineApiService NstSameCityCarlineApiService;
	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	public NstSameCityCarlineApiService nstSameCityCarlineApiService;
	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;
	@Autowired
	public MemberAddressApiService memberAddressApiService;
	@Autowired
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;

	/**
	 * 插入同城发布货源
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressEntity
	 */
	@RequestMapping("/insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			//判断是否开启发货开关
			Integer deliverStatus = productDeliveryDetailToolService.isOpenForAddDelivery();
			if(deliverStatus == null || deliverStatus == 0){
				setEncodeResult(result, ErrorCodeEnum.VERSION_NOT_CORRECT, request, response);
				return;
			}
			
			 String jsonStr = getParamDecodeStr(request);
			 NstSameCityAddressEntity nstSameCityAddressEntity =
			 (NstSameCityAddressEntity) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressEntity.class);
			 String  clat=GSONUtils.getJsonValueStr(jsonStr,"clat");//客户端纬度
			 String  clng=GSONUtils.getJsonValueStr(jsonStr,"clng");//客户端经度
			 String selectedList=GSONUtils.getJsonValueStr(jsonStr, "selectedList");//新加 字段  判断是否选中订单/商品
			 //同城的 城市必须相同
			if (!nstSameCityAddressEntity.getS_cityName().equals(nstSameCityAddressEntity.getF_cityName())) {
				logger.debug("同城必须发货地和收货地一致");
				setEncodeResult(result, ErrorCodeEnum.CITY_NOT_MATCH, request, response);
				return;
			}
			boolean isless=isLessCurrentTime(nstSameCityAddressEntity);
			if (isless) {
				logger.debug("装车时间不能小于当前时间");
				setEncodeResult(result, ErrorCodeEnum.SEND_DATE_ERROR, request, response);
				return;
			}
			if(clat == null || "".equals(clat) || clng == null || "".equals(clng)){
				logger.debug("无定位信息");
				setEncodeResult(result, ErrorCodeEnum.CANNOT_LOCATE, request, response);
				return;
			}
			boolean isVa = validate(nstSameCityAddressEntity);
			if (!isVa) {
				logger.debug("前端输入格式不对");
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_ERROR, request, response);
				return;
			}
			boolean isRe = required(nstSameCityAddressEntity);
			if (!isRe) {
				logger.debug("所需字段没有填完整");
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
			getEntity(nstSameCityAddressEntity);
			Long id = nstSameCityAddressToolService.insert(nstSameCityAddressEntity);
			if (id != null && id > 0) {
				// 线程执行货源分配
				MemberBaseinfoDTO mbTO = memberToolService.getById(nstSameCityAddressEntity.getMemberId() + "");
				//如果 查询出的userType为空，则更新为 1
				if (mbTO.getUserType()==null) {
					mbTO.setMemberId(nstSameCityAddressEntity.getMemberId());
					mbTO.setUserType(1);
					memberToolService.updateMemberApp(mbTO);
				}
				if ((mbTO != null && mbTO.getUserType() != 2) || nstSameCityAddressEntity.getClients().byteValue()!=2) {
					executeThreadForAssignGoods(id, nstSameCityAddressEntity);
				}
				// 批量车辆 并推送消息 ,只有在农速通这边发货才进行
				if (nstSameCityAddressEntity.getClients().byteValue()==2) {
					nstSameCityAddressEntity.setId(id);
					pushLine(nstSameCityAddressEntity,Double.parseDouble(clat),Double.parseDouble(clng));
				}
				// 如果 selectedList 字段存在 则插入 出货详细表
				if(StringUtils.isNotBlank(selectedList)){
					MemberAddressInputDTO inputDTO =new MemberAddressInputDTO();
					inputDTO.setMemberId(nstSameCityAddressEntity.getMemberId());
					inputDTO.setSelectedList(selectedList);
					inputDTO.setSourceType("1");
					//增加出货信息
					ErrorCodeEnum addResult = productDeliveryDetailToolService.add(inputDTO, id, false);
					if(ErrorCodeEnum.SUCCESS != addResult){
						logger.warn("插入到产品出货详细表失败");
						setEncodeResult(result, addResult, request, response);
						return;
					}
				}
				result.setObject(null);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
		} catch (Exception e) {
			logger.warn("插入同城货源失败");
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}

	/**
	 * 获取同城货源列表
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/getNSCAListByPage")
	public void getNSCAListByPage(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityAddressDTO nstSameCityAddressDTO =
			 (NstSameCityAddressDTO) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressDTO.class); 
			
			Map<String, Object> map = getMap(nstSameCityAddressDTO);
			// 做分页
			int total = nstSameCityAddressToolService.getNstSameCityAddressListByPageCount(map);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			List<NstSameCityAddressDTO> listByPage = nstSameCityAddressToolService.getNstSameCityAddressListByPage(map);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(listByPage);

			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("查询同城货源列表异常");
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}

	/**
	 * 获取同城货源详情
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/getNSCADetail")
	public void getNSCADetail(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityAddressDTO nstSameCityAddressDTO =
			 (NstSameCityAddressDTO) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressDTO.class);
			Map<String, Object> map = new HashMap<>();
			if (nstSameCityAddressDTO.getId() == null) {
				logger.warn("请app端输入货源id");
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			map.put("id", nstSameCityAddressDTO.getId());
			//当前登录人memberId
			map.put("currentMemberId", nstSameCityAddressDTO.getCurrentMemberId());
			// 如果客户端传了经纬度，则计算距离
			if (nstSameCityAddressDTO.getClat() != null && nstSameCityAddressDTO.getClng() != null) {
				map.put("clat", nstSameCityAddressDTO.getClat());
				map.put("clng", nstSameCityAddressDTO.getClng());
			}
			//--根据showAlready==Y判断 是否显示已接单的
			map.put("showAlready", "Y");
			List<NstSameCityAddressDTO> listByPage = nstSameCityAddressToolService.getNstSameCityAddressListByPage(map);
			if (listByPage.size() == 0) {
				logger.warn("根据货源id查询不到数据!");
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_NOT_EXISTED, request, response);
				return;
			}
			result.setObject(listByPage.get(0));
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("查询同城货源列表异常");
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}

	/**
	 * 一键配货接口
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/matchGoodsByCarline")
	public void matchGoodsByCarline(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityAddressDTO nstSameCityAddressDTO =
			 (NstSameCityAddressDTO) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressDTO.class);
			// 根据线路来匹配货源
			if (nstSameCityAddressDTO.getSameCitycarLineId() == null) {
				logger.warn("请选择线路");
				setEncodeResult(result, ErrorCodeEnum.CARS_LINE_IS_NULL, request, response);
				return;
			}
			NstSameCityCarlineEntityDTO entityDTO = NstSameCityCarlineApiService
					.getById(nstSameCityAddressDTO.getSameCitycarLineId());
			if (entityDTO == null) {
				logger.warn("不能得到线路信息");
				setEncodeResult(result, ErrorCodeEnum.CARS_LINE_NOT_EXISTED, request, response);
				return;
			}
			// 获取当前的会员经纬度
			if (nstSameCityAddressDTO.getClat() != null && nstSameCityAddressDTO.getClng() != null) {
				entityDTO.setClat(nstSameCityAddressDTO.getClat());
				entityDTO.setClng(nstSameCityAddressDTO.getClng());
			}
			// 传入当前车主的登录人，来查出此车主总共接单多少笔（待成交）
			if (nstSameCityAddressDTO.getMemberId() == null) {
				logger.warn("请输入当前登录人memberId");
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			//将查询出的memberId设置空
			entityDTO.setMemberId(null);
			//获取当前登录人memberId
			entityDTO.setCurrentMemberId(nstSameCityAddressDTO.getCurrentMemberId());
			List<NstSameCityAddressDTO> list = nstSameCityAddressToolService.matchGoodsByCarline(entityDTO);
			// 做分页
			int total = list.size();
			Map<String, Object> map = new HashMap<>();
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);

			// 查询出当前车主的接单数（待成交）
			Integer count = nstOrderBaseinfoToolService
					.getMemberSameCityOrderCount(nstSameCityAddressDTO.getMemberId());
			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("count", count);

			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(list);

			result.setExtra(returnMap);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("一键配货失败");
			e.printStackTrace();
		}
	}

	/**
	 * 获取我发的货列表
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/getMemberNSCAList")
	public void getMemberNSCAList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityAddressDTO nstSameCityAddressDTO =
			 (NstSameCityAddressDTO) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressDTO.class);
			if (nstSameCityAddressDTO.getMemberId() == null) {
				logger.warn("请输入会员Id");
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("memberId", nstSameCityAddressDTO.getMemberId());
			// 做分页
			int total = nstSameCityAddressToolService.getMemberNSCAListCount(map);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);;
			List<NstSameCityAddressDTO> nscaList = nstSameCityAddressToolService.getMemberNSCAList(map);

			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(nscaList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("查询我发的货失败");
			e.printStackTrace();
		}
	}

	/**
	 * 点击 修改重发 调用的接口
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/getMemberNSCA")
	@Deprecated
	public void getMemberNSCA(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityAddressDTO nstSameCityAddressDTO =
			 (NstSameCityAddressDTO) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressDTO.class);
			if (nstSameCityAddressDTO.getId() == null) {
				logger.warn("请输入同城货源Id");
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("id", nstSameCityAddressDTO.getId());
			List<NstSameCityAddressDTO> nscaList = nstSameCityAddressToolService.getMemberNSCAList(map);
			if (nscaList.size() == 0) {
				logger.warn("根据货源id查询不出");
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_NOT_EXISTED, request, response);
				return;
			}
			result.setObject(nscaList.get(0));
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("查询我发的货失败");
			e.printStackTrace();
		}
	}

	/**
	 * 点击 修改接口
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/updateMemberNSCA")
	@Deprecated
	public void updateMemberNSCA(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			 String jsonStr = getParamDecodeStr(request);
			 NstSameCityAddressEntity entity =
			 (NstSameCityAddressEntity) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressEntity.class);
			if (entity.getId() == null) {
				logger.warn("请输入必须字段");
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
			
			boolean isVa = validate(entity);
			if (!isVa) {
				logger.debug("前端输入格式不对");
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_ERROR, request, response);
				return;
			}
			
			boolean isRe = required(entity);
			if (!isRe) {
				logger.debug("所需字段没有填完整");
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
			
			// 同城的 城市必须相同
			if (!entity.getS_cityName().equals(entity.getF_cityName())) {
				logger.debug("同城必须发货地和收货地一致");
				setEncodeResult(result, ErrorCodeEnum.CITY_NOT_MATCH, request, response);
				return;
			}
			getEntity(entity);
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(entity.getMemberId() + "");
			Integer status = nstSameCityAddressToolService.updateMemberNSCA(entity);
			if (status <= 0) {
				logger.debug("查询失败");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			result.setObject(null);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("查询我发的货失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除  我发的货源
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/deleteMemberNSCA")
	public void deleteMemberNSCA(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			 String jsonStr = getParamDecodeStr(request);
			 NstSameCityAddressEntity entity =
			 (NstSameCityAddressEntity) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressEntity.class);
			if (entity.getId() == null || entity.getMemberId()==null) {
				logger.warn("请输入必须字段");
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(entity.getMemberId() + "");
			Integer status = nstSameCityAddressToolService.deleteMemberNSCA(entity);
			if (status <= 0) {
				logger.warn("删除货源失败");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}else{
				nstOrderBaseinfoToolService.updateDeliverStatusByMemberAddressId(entity.getId(), -1);
			}
			result.setObject(null);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("删除货源失败");
			e.printStackTrace();
		}
	}

	/**
	 * 匹配我发的货
	 * 
	 * @param request
	 * @param response
	 * @param nstSameCityAddressDTO
	 */
	@RequestMapping("/checkSameCityGoodsInfo")
	public void checkSanmeCityGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityAddressDTO nstSameCityAddressDTO =
			 (NstSameCityAddressDTO) GSONUtils
			 .fromJsonToObject(jsonStr, NstSameCityAddressDTO.class);
			// 请app端传入，当前线路的线路id
			if (nstSameCityAddressDTO.getSameCitycarLineId() == null) {
				logger.warn("请选择线路 ");
				setEncodeResult(result, ErrorCodeEnum.CARS_LINE_IS_NULL, request, response);
				return;
			}
			if (nstSameCityAddressDTO.getMemberId()==null) {
				logger.warn("请输入memberId ");
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}

			NstSameCityCarlineEntityDTO entityDTO = NstSameCityCarlineApiService
					.getById(nstSameCityAddressDTO.getSameCitycarLineId());
			if (entityDTO == null) {
				logger.warn("不能得到线路信息");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			//将当前memberId 存入需要匹配的dto中
			entityDTO.setMemberId(nstSameCityAddressDTO.getMemberId());
			List<NstSameCityAddressDTO> list = nstSameCityAddressToolService.matchGoodsByCarline(entityDTO);
			// 做分页
			int total = list.size();
			Map<String, Object> map = new HashMap<>();
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(list);

			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]匹配我发的货异常");
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			e.printStackTrace();
		}
	}

	/**
	 * 拼装查询Map
	 * 
	 * @param nstSameCityAddressDTO
	 */
	private Map<String, Object> getMap(NstSameCityAddressDTO nstSameCityAddressDTO) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("id", nstSameCityAddressDTO.getId());
		map.put("s_provinceId", nstSameCityAddressDTO.getS_provinceId());
		//直辖市 县 市 需判断
		if (nstSameCityAddressDTO.getS_cityId()!=null && !isSpecialId(nstSameCityAddressDTO.getS_cityId())) {
			map.put("s_cityId", nstSameCityAddressDTO.getS_cityId());
		}
		map.put("s_areaId", nstSameCityAddressDTO.getS_areaId());
		map.put("f_provinceId", nstSameCityAddressDTO.getF_provinceId());
		//直辖市 县 市 需判断
		if (nstSameCityAddressDTO.getF_cityId()!=null && !isSpecialId(nstSameCityAddressDTO.getF_cityId())) {
			map.put("f_cityId", nstSameCityAddressDTO.getF_cityId());
		}
		map.put("f_areaId", nstSameCityAddressDTO.getF_areaId());
		map.put("useCarTime", nstSameCityAddressDTO.getUseCarTime());
		//当前登录人id
		if (nstSameCityAddressDTO.getCurrentMemberId()!=null) {
			map.put("currentMemberId", nstSameCityAddressDTO.getCurrentMemberId());
		}
		//--根据showAlready==Y判断 是否显示已接单的
		map.put("showAlready", "Y");
		// 是否进行最近排序
		if (StringUtils.isNotEmpty(nstSameCityAddressDTO.getClosest())) {
			map.put("closest", nstSameCityAddressDTO.getClosest());
		}
		// 如果app端传了当前用户的经纬度，则使用计算最短距离，如果没有在根据当前用户的常用城市计算
		if (nstSameCityAddressDTO.getClat() != null && nstSameCityAddressDTO.getClng() != null) {
			map.put("clat", nstSameCityAddressDTO.getClat());
			map.put("clng", nstSameCityAddressDTO.getClng());
		}

		return map;
	}
	/**
	 * 判断传入的城市id是不是特殊id
	 * @param id
	 * @return
	 */
	private boolean isSpecialId(Integer id){
		int[] str={110100,110200,120100,120200,310100,310200,500100,500200,500300};
		for (int i : str) {
			if (id.intValue()==i) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 处理插入同步货源的entity
	 * 
	 * @param entity
	 * @return
	 */
	private NstSameCityAddressEntity getEntity(NstSameCityAddressEntity entity) throws Exception {
		// 根据经纬度获取计算里程
		if (entity.getS_lat() != null && entity.getS_lng() != null && entity.getF_lat() != null
				&& entity.getF_lng() != null) {
			Double s = DistanceCalculationUtil.GetDistance(entity.getS_lat(), entity.getS_lng(), entity.getF_lat(),
					entity.getF_lng());
			entity.setMileage(s);
		}
		// 根据app端传的省份，城市，区域名称查询 各自对应的id (当定位时 四个直辖市 传入为(s OR f)_cityName)
		
		//如果省份不为空 则使用省份查询出id
		if (StringUtils.isNotEmpty(entity.getS_provinceName())) {
			AreaDTO area = areaToolService.getByAreaName(entity.getS_provinceName());
			if (area != null) {
				entity.setS_provinceId(Integer.parseInt(area.getAreaID()));
			}
		//如果收货地省份为空，则根据城市查询去省份id
		}else {
			if (StringUtils.isNotEmpty(entity.getS_cityName())) {
				AreaDTO area = areaToolService.getByAreaName(entity.getS_cityName());
				if (area != null) {
					//只有在app端传入 北京市 天津市 上海市 重庆市 时 所取的的上一级为空，则直接将查出的id设置到省份id上
					entity.setS_provinceId(Integer.parseInt(area.getFather()==null?area.getAreaID():area.getFather()));
				}
			}
		}
		if (StringUtils.isNotEmpty(entity.getS_cityName())) {
			AreaDTO area = areaToolService.getByAreaName(entity.getS_cityName());
			if (area != null) {
				entity.setS_cityId(Integer.parseInt(area.getAreaID()));
			}
		}
		if (StringUtils.isNotEmpty(entity.getS_areaName())) {
			List<AreaDTO> areaList=areaToolService.getChildrenByParentId(entity.getS_cityId()+"");
			if (areaList!=null && areaList.size()>0) {
				for (AreaDTO area : areaList) {
					if (area.getArea().equals(entity.getS_areaName())) {
						entity.setS_areaId(Integer.parseInt(area.getAreaID()));
						break;
					}
				}
			}
			//如果区域为空 则 表示 是  北京市 天津市 上海市 重庆市 传入的
			if (entity.getS_areaId()==null) {
				AreaDTO area=areaToolService.getByAreaName(entity.getS_areaName());
				entity.setS_areaId(Integer.parseInt(area.getAreaID()));
			}
		}
		//如果收货地省份为空，则根据城市查询去省份id
		if (StringUtils.isNotEmpty(entity.getF_provinceName())) {
			AreaDTO area = areaToolService.getByAreaName(entity.getF_provinceName());
			if (area != null) {
				entity.setF_provinceId(Integer.parseInt(area.getAreaID()));
			}
		}else{
			if (StringUtils.isNotEmpty(entity.getF_cityName())) {
				AreaDTO area = areaToolService.getByAreaName(entity.getF_cityName());
				if (area != null) {
					//只有在app端传入 北京市 天津市 上海市 重庆市 时 所取的的上一级为空，则直接将查出的id设置到省份id上
					entity.setF_provinceId(Integer.parseInt(area.getFather()==null?area.getAreaID():area.getFather()));
				}
			}
		}
		if (StringUtils.isNotEmpty(entity.getF_cityName())) {
			AreaDTO area = areaToolService.getByAreaName(entity.getF_cityName());
			if (area != null) {
				entity.setF_cityId(Integer.parseInt(area.getAreaID()));
			}
		}
		if (StringUtils.isNotEmpty(entity.getF_areaName())) {
			List<AreaDTO> areaList=areaToolService.getChildrenByParentId(entity.getF_cityId()+"");
			if (areaList!=null && areaList.size()>0) {
				for (AreaDTO area : areaList) {
					if (area.getArea().equals(entity.getF_areaName())) {
						entity.setF_areaId(Integer.parseInt(area.getAreaID()));
						break;
					}
				}
			}
			//如果区域为空 则 表示 是  北京市 天津市 上海市 重庆市 传入的
			if (entity.getF_areaId()==null) {
				AreaDTO area=areaToolService.getByAreaName(entity.getF_areaName());
				entity.setF_areaId(Integer.parseInt(area.getAreaID()));
			}
		}
		entity.setIsDeleted((byte) 0);
		entity.setHundredweight(0);
		entity.setCreateUserId(entity.getMemberId() + "");
		entity.setCreateTime(new Date());
		entity.setReleaseTime(new Date());
		return entity;
	}

	/**
	 * 拼接货源分配的需要的dto
	 * 
	 * @param id
	 * @param nstSameCityAddressEntity
	 * @param dto
	 * @return
	 */
	private NstSameCityAddressDTO getDTO(Long id, NstSameCityAddressEntity nstSameCityAddressEntity,
			NstSameCityAddressDTO dto) {
		dto.setId(id);
		dto.setS_cityId(nstSameCityAddressEntity.getS_cityId());
		dto.setClients(nstSameCityAddressEntity.getClients());
		return dto;
	}
	
	/**
	 * 判断用车时间是否小于当前时间
	 * @param entity
	 * @return
	 */
	private boolean isLessCurrentTime(NstSameCityAddressEntity entity){
		if (StringUtils.isNotEmpty(entity.getUseCarTime())) {
			Date userCarTime=DateUtil.getDate(entity.getUseCarTime(), DateUtil.DATE_FORMAT_DATETIME);
			Date currentTime=new Date();
			if (userCarTime.getTime()<currentTime.getTime()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 所需字段判断
	 * 
	 * @param entity
	 * @return
	 */
	private boolean required(NstSameCityAddressEntity entity) {
		
		// 客户端传给后台所需字段
		if (entity.getClients() == null || entity.getMemberId() == null) {
			return false;
		}
		// 如果客户端不是农速通,则必须输入 联系人
		if (entity.getClients().byteValue()!=2 && StringUtils.isEmpty(entity.getContactName())) {
			return false;
		}
		// (用户输入)客户端必传字段
		if (entity.getNeedCarType() == null || StringUtils.isEmpty(entity.getS_detail())
				|| StringUtils.isEmpty(entity.getF_detail())
				|| (entity.getUseCarTime() == null && StringUtils.isEmpty(entity.getUseCarTimeString()))
				|| entity.getGoodsType() == null || entity.getTotalWeight() == null) {
			return false;
		}

		return true;
	}
	/**
	 * 验证前台输入字段
	 * @param entity
	 * @return
	 */
	private boolean validate(NstSameCityAddressEntity entity){
		//总重不能大于1000
		if (entity.getTotalWeight()!=null && entity.getTotalWeight()>1000.0d) {
			return false;
		}
		//总体积不能大于1000
		if (entity.getTotalSize()!=null && entity.getTotalSize()>1000) {
			return false;
		}
		//意向价格不能大于1000000
		if (entity.getPrice()!=null && entity.getPrice()>1000000.0d) {
			return false;
		}
		
		return true;
	}
	/**
	 * 线程执行货源分配
	 * 
	 * @param id
	 * @param nstSameCityAddressEntity
	 */
	private void executeThreadForAssignGoods(Long id, NstSameCityAddressEntity nstSameCityAddressEntity) {
		ExecutorService service = ThreadPoolSingleton.getSingletonThreadPoolInstance();
		NstSameCityAddressDTO dto = new NstSameCityAddressDTO();
		getDTO(id, nstSameCityAddressEntity, dto);
		service.execute(new AssignSameCityRuleThread(nstSameCityAddressToolService, dto));
	}

	/**
	 * 新增货源 匹配线路并推送消息
	 * 
	 * @param nstSameCityAddressEntity
	 */
	public void pushLine(NstSameCityAddressEntity nstSameCityAddressEntity,Double clat,Double clng) {
		try {
			// 根据发布货源 匹配相应线路 并做推送
			NstSameCityAddressDTO dto = new NstSameCityAddressDTO();
			ExecutorService service = ThreadPoolSingleton.getSingletonThreadPoolInstance();
			MemberBaseinfoDTO memberDTO = memberToolService.getById(nstSameCityAddressEntity.getMemberId() + "");
			dto.setMcity(nstSameCityAddressEntity.getS_cityName());
			dto.setS_provinceId(nstSameCityAddressEntity.getS_provinceId());
			dto.setS_cityId(nstSameCityAddressEntity.getS_cityId());
			dto.setS_areaId(nstSameCityAddressEntity.getS_areaId());
			dto.setF_provinceId(nstSameCityAddressEntity.getF_provinceId());
			dto.setF_cityId(nstSameCityAddressEntity.getF_cityId());
			dto.setF_areaId(nstSameCityAddressEntity.getF_areaId());
			dto.setS_lat(nstSameCityAddressEntity.getS_lat());
			dto.setS_lng(nstSameCityAddressEntity.getS_lng());
			dto.setNeedCarType(nstSameCityAddressEntity.getNeedCarType());
			dto.setMemberId(nstSameCityAddressEntity.getMemberId());
			dto.setCreateUserId(nstSameCityAddressEntity.getCreateUserId());
			dto.setId(nstSameCityAddressEntity.getId());
			dto.setClat(clat);
			dto.setClng(clng);
			service.execute(new AssignSameCityLineThread(memberDTO, dto, nstSameCityCarlineApiService));

		} catch (Exception e) {
			logger.warn("新增货源，匹配车辆失败");
			e.printStackTrace();

		}
	}
	
}
