package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.CarManagerApiService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.NstSameCityAddressToolService;
import com.gudeng.commerce.gd.api.service.NstSameCityCarlineApiService;
import com.gudeng.commerce.gd.api.util.AssignSameCityThread;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.LngLatUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ThreadPoolSingleton;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("nst2/nstSameCityCarline")
public class NstSameCityCarlineController extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(NstSameCityCarlineController.class);

	@Autowired
	public GdProperties gdProperties;

	@Autowired
	public NstSameCityCarlineApiService nstSameCityCarlineApiService;

	@Autowired
	public MemberToolService memberToolService;

	@Autowired
	public CarManagerApiService carManagerApiService;

	@Autowired
	private AreaToolService areaToolService;

	@Autowired
	private NstSameCityAddressToolService nstSameCityAddressToolService;

	@Autowired
	public MemberAddressApiService memberAddressApiService;

	/**
	 * 根据用户id获取车牌号
	 * 
	 * @param request
	 * @param response
	 * @param cityCarlineEntityDTO
	 */
	@RequestMapping("/getCarNumbers")
	public void getCarNumbers(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			// 解密
			String jsonStr = getParamDecodeStr(request);
			// 将json 字符串封装为对象
			NstSameCityCarlineEntityDTO dto = (NstSameCityCarlineEntityDTO) GSONUtils.fromJsonToObject(jsonStr,
					NstSameCityCarlineEntityDTO.class);
			List<NstSameCityCarlineEntityDTO> cityCarlineList = nstSameCityCarlineApiService
					.queryCarsByUserId(dto.getUserId() + "");
			if (cityCarlineList != null && cityCarlineList.size() > 0) {
				result.setObject(cityCarlineList);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
				renderEncodeJson(result, request, response, null, true);
			} else {
				result.setObject(cityCarlineList);
				//请先新增车辆
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]根据用户id获取车牌号异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 根据车辆id查询车辆信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCarDtoById")
	public void getCarDtoById(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			// 解密
			String jsonStr = getParamDecodeStr(request);
			String carId = GSONUtils.getJsonValueStr(jsonStr, "carId");
			CarsDTO dto = carManagerApiService.getCarMessageById(carId);
			if (dto != null) {
				result.setObject(dto);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
				renderEncodeJson(result, request, response, null, true);
			} else {
				result.setObject(dto);
				setEncodeResult(result, ErrorCodeEnum.CARS_IS_NULL, request, response);
				return;
			}
		} catch (Exception e) {
			logger.warn("[ERROR]根据车辆id查询车辆信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 新增同城线路发布
	 * 
	 * @param request
	 * @param response
	 * @param cityCarlineEntityDTO
	 */
	@RequestMapping("/addNstSameCityCarLineDTO")
	public void addNstSameCityCarLineDTO(HttpServletRequest request, HttpServletResponse response) {

		ObjectResult result = new ObjectResult();
		try {
			// 解密
			String jsonStr = getParamDecodeStr(request);
			NstSameCityCarlineEntityDTO dto = (NstSameCityCarlineEntityDTO) GSONUtils.fromJsonToObject(jsonStr,
					NstSameCityCarlineEntityDTO.class);
			if (dto.getCarId() == 0) {
				setEncodeResult(result, ErrorCodeEnum.CARS_NOT_EXISTED, request, response);
				return;
			}
			if (dto.getMemberId() == 0) {
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			result = checkResult(dto, result);// 数据校验
			if (!"".equals(result.getMsg())) {
				renderEncodeJson(result, request, response, null, true);
				return;
			}

			getEntityDTO(dto);// 查询经纬度
			dto.setCreateUserId(dto.getMemberId() + "");
			int row = nstSameCityCarlineApiService.addNstSameCityCarline(dto);
			if (row > 0) {
				CarsDTO carDto = carManagerApiService.getCarMessageById(dto.getCarId() + "");
				dto.setCarType(carDto.getCarType());
				dto.setCarLength(carDto.getCarLength() + "");
				Long id = nstSameCityCarlineApiService.queryLastNstSameCityCarLineDTO(dto);
				dto.setId(id);
				pushGoods(dto);// 根据线路 匹配货源并推送消息

				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);

			} else {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]同城线路发布异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 新增同城线路发布数据校验
	 * 
	 * @param dto
	 * @param result
	 * @return
	 */
	public ObjectResult checkResult(NstSameCityCarlineEntityDTO dto, ObjectResult result) {
		if ((dto.getS_provinceId() == null || "".equals(dto.getS_provinceId()))
				&& StringUtils.isEmpty(dto.getS_provinceName())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("出发地省份id不能为空");
			return result;
		}
		if ((dto.getS_cityId() == null || "".equals(dto.getS_cityId()))
				&& StringUtils.isEmpty(dto.getS_cityName())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("出发地城市id不能为空");
			return result;
		}
		if (StringUtils.isEmpty(dto.getS_areaName()) && (dto.getS_areaId()!=null && dto.getS_areaId().intValue()==0)) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("出发地区域不能为空");
			return result;
		}
		if (dto.getE_provinceId() == null || "".equals(dto.getE_provinceId())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("目的地省份id不能为空");
			return result;
		}
		if (dto.getE_cityId() == null || "".equals(dto.getE_cityId())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("目的地城市id不能为空");
			return result;
		}
		if (dto.getE_areaId()!=null && dto.getE_areaId().intValue()==0) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("目的地区域不能为空");
			return result;
		}

		if (dto.getS_detail() == null || "".equals(dto.getS_detail())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("发货地详细地址(包括省份，城市，区域中文)");
			return result;
		}
		if (dto.getE_detail() == null || "".equals(dto.getE_detail())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("目的地详细地址(包括省份，城市，区域中文)");
			return result;
		}
		/*if (!dto.getS_cityId().equals(dto.getE_cityId())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("同城出发地与目的地必须一致");
			return result;
		}*/
		if (dto.getPrice() != null && dto.getPrice() > 1000000) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("意向价格不能高于1百万");
			return result;
		}
		if (dto.getClat() == null || "".equals(dto.getClat())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("客户端纬度不能为空");
			return result;
		}
		if (dto.getClng() == null || "".equals(dto.getClng())) {
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("客户端经度必填");
			return result;
		}
		return result;
	}

	/**
	 * 修改同城线路
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateNstSameCityCarLineDTO")
	public void updateNstSameCityCarLineDTO(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			// 解密
			String jsonStr = getParamDecodeStr(request);
			// 将json 字符串封装为对象
			NstSameCityCarlineEntityDTO dto = (NstSameCityCarlineEntityDTO) GSONUtils.fromJsonToObject(jsonStr,
					NstSameCityCarlineEntityDTO.class);
			if (dto.getId() == 0) {
				setResult(result, ErrorCodeEnum.CARS_LINE_NOT_EXISTED, request, response);
				return;
			}
			if (dto.getCarId() == 0) {
				setResult(result, ErrorCodeEnum.CARS_NOT_EXISTED, request, response);
				return;
			}
			if (dto.getMemberId() == 0) {
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			result = checkResult(dto, result);// 数据校验
			if (!"".equals(result.getMsg())) {
				renderEncodeJson(result, request, response, null, true);
				return;
			}

			getEntityDTO(dto);// 查询经纬度
			dto.setUpdateUserId(dto.getMemberId() + "");
			int row = nstSameCityCarlineApiService.updateNstSameCityCarLineDTO(dto);
			if (row > 0) {
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
				renderEncodeJson(result, request, response, null, true);
			} else {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			}

		} catch (Exception e) {
			logger.warn("[ERROR]修改同城线路异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 查询同城找车列表 同城线路搜索
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryNstSameCityCarLineList")
	public void queryNstSameCityCarLineList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			// 将json 字符串封装为对象
			NstSameCityCarlineEntityDTO dto = (NstSameCityCarlineEntityDTO) GSONUtils.fromJsonToObject(jsonStr,
					NstSameCityCarlineEntityDTO.class);
			Map<String, Object> map = new HashMap<String, Object>();
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = nstSameCityCarlineApiService.queryNstSameCityCarLineCount(dto);
			dto.setStartRow(pageDTO.getOffset());
			dto.setEndRow(pageDTO.getPageSize());
			List<NstSameCityCarlineEntityDTO> list = nstSameCityCarlineApiService.queryNstSameCityCarLineList(dto);
			// 根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			renderEncodeJson(result, request, response, null, true);
		} catch (Exception e) {
			logger.warn("[ERROR]查询同城找车列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 同城找车线路详情
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryNstSameCityCarLineDetail")
	public void queryNstSameCityCarLineDetail(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityCarlineEntityDTO cdto = (NstSameCityCarlineEntityDTO) GSONUtils.fromJsonToObject(jsonStr,
					NstSameCityCarlineEntityDTO.class);
			NstSameCityCarlineEntityDTO dto = nstSameCityCarlineApiService.getByDto(cdto);
			if(dto==null&&cdto.getId()!=null){
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("此条信息刚刚已被删除!");
				renderEncodeJson(result, request, response, null, true);
			}else{
				// 查询地市 app用于只显示 城市+地区
				AreaDTO s_city = areaToolService.getByAreaId(Long.parseLong(dto.getS_cityId() + ""));
				AreaDTO s_area = areaToolService.getByAreaId(Long.parseLong(dto.getS_areaId() + ""));
				dto.setS_city_area(s_city.getArea() + s_area.getArea());
				AreaDTO e_city = areaToolService.getByAreaId(Long.parseLong(dto.getE_cityId() + ""));
				AreaDTO e_area = areaToolService.getByAreaId(Long.parseLong(dto.getE_areaId() + ""));
				dto.setE_city_area(e_city.getArea() + e_area.getArea());
				// 时间差的显示
				if (dto.getUpdateTime() != null) {
					dto.setDayNum(DateTimeUtils.getTimeBetween(dto.getUpdateTime()));
				}
				// 判断是否接单
				if (dto.getOrderStatus() == null || dto.getOrderStatus() == 4 || dto.getOrderStatus() == 5) {
					dto.setShowGoodsStatus("N");
				} else {
					dto.setShowGoodsStatus("Y");
				}
				String imageHost = gdProperties.getProperties().getProperty("gd.image.server");// 头像地址
				dto.setAndupurl(imageHost + dto.getAndupurl());
				result.setObject(dto);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
				renderEncodeJson(result, request, response, null, true);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]同城找车详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}

	}

	/**
	 * 线路管理 我的发车
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryMyCitylineList")
	public void queryMyCitylineList(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityCarlineEntityDTO dto = (NstSameCityCarlineEntityDTO) GSONUtils.fromJsonToObject(jsonStr,
					NstSameCityCarlineEntityDTO.class);
			Map<String, Object> map = new HashMap<String, Object>();
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = nstSameCityCarlineApiService.queryMyCitylineCount(dto);
			dto.setStartRow(pageDTO.getOffset());
			dto.setEndRow(pageDTO.getPageSize());
			List<NstSameCityCarlineEntityDTO> list = nstSameCityCarlineApiService.queryMyCitylineList(dto);
			// 根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
			renderEncodeJson(result, request, response, null, true);
		} catch (Exception e) {
			logger.warn("[ERROR]我的发车异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}

	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 */
	@RequestMapping("/deleteNstSameCityCarLineDTO")
	public void deleteNstSameCityCarLineDTO(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			NstSameCityCarlineEntityDTO dto = (NstSameCityCarlineEntityDTO) GSONUtils.fromJsonToObject(jsonStr,
					NstSameCityCarlineEntityDTO.class);
			if (dto.getMemberId() == 0) {
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}

			if (dto.getId() == 0) {
				setResult(result, ErrorCodeEnum.CARS_LINE_NOT_EXISTED, request, response);
				return;
			}

			int row = nstSameCityCarlineApiService.deleteNstSameCityCarLineDTO(dto);
			if (row > 0) {
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
				renderEncodeJson(result, request, response, null, true);
			} else {
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			}

		} catch (Exception e) {
			logger.warn("[ERROR]删除同城线路异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 查询经纬度
	 * 
	 * @param dto
	 * @return
	 */
	public NstSameCityCarlineEntityDTO getEntityDTO(NstSameCityCarlineEntityDTO dto) throws Exception {
		//如果客户端默认传入出发地为中文，则将目的地的省份和城市id给出发地
		if (StringUtils.isNotEmpty(dto.getS_areaName())) {
			dto.setS_provinceId(dto.getE_provinceId());
			dto.setS_cityId(dto.getE_cityId());
			List<AreaDTO> areaList=areaToolService.getChildrenByParentId(dto.getS_cityId()+"");
			if (areaList!=null && areaList.size()>0) {
				for (AreaDTO area : areaList) {
					if (area.getArea().equals(dto.getS_areaName())) {
						dto.setS_areaId(Integer.parseInt(area.getAreaID()));
					}
				}
			}
		}
		// 如果APP传过来的经纬度为空 则根据地址调用百度来查询经纬度 出发地地址
		if (dto.getS_lng() == null || "".equals(dto.getS_lng()) || dto.getS_lat() == null
				|| "".equals(dto.getS_lat())) {
			Map<String, Object> paraMap = LngLatUtil.getLntLatByBaidu(dto.getS_detail());
			dto.setS_lat(paraMap.get("lat") == null ? null : Double.parseDouble(paraMap.get("lat") + ""));
			dto.setS_lng(paraMap.get("lng") == null ? null : Double.parseDouble(paraMap.get("lng") + ""));

		}
		// 目的地地址
		if (dto.getE_lng() == null || "".equals(dto.getE_lng()) || dto.getE_lat() == null
				|| "".equals(dto.getE_lat())) {
			Map<String, Object> paraMap = LngLatUtil.getLntLatByBaidu(dto.getE_detail());
			dto.setE_lat(paraMap.get("lat") == null ? null : Double.parseDouble(paraMap.get("lat") + ""));
			dto.setE_lng(paraMap.get("lng") == null ? null : Double.parseDouble(paraMap.get("lng") + ""));
		}
		return dto;
	}

	/**
	 * 根据线路 匹配货源并推送消息
	 * 
	 * @param dto
	 */
	public void pushGoods(NstSameCityCarlineEntityDTO dto) {
		try {

			// 根据发布线路匹配货源 并做推送
			ExecutorService service = ThreadPoolSingleton.getSingletonThreadPoolInstance();
			MemberBaseinfoDTO memberDTO = memberToolService.getById(dto.getMemberId() + "");
			// 根据城市id获取城市中文名称
			NstSameCityCarlineEntityDTO entityDTO = nstSameCityCarlineApiService.getCityName(dto.getS_cityId());
			dto.setCityName(entityDTO.getCityName());
			service.execute(new AssignSameCityThread(memberDTO, dto, nstSameCityCarlineApiService));
		} catch (Exception e) {
			logger.warn("新增线路，匹配货源失败");
			e.printStackTrace();

		}
	}

	/**
	 * 发布线路 匹配货源后 推送消息 查看推送货源记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCityGoodsListDetail")
	public void queryCityGoodsListDetail(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			// 将json 字符串封装为对象
			String messageId = GSONUtils.getJsonValueStr(jsonStr, "messageId");
			String mCity = GSONUtils.getJsonValueStr(jsonStr, "mCity");
			// String messageId=request.getParameter("messageId");
			// String mCity=request.getParameter("mCity");
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("id", messageId);
			p.put("mCity", mCity);
			List<NstSameCityAddressDTO> list = nstSameCityCarlineApiService.queryCityGoodsListDetail(p);
			if (list != null && list.size() > 0) {
				result.setObject(list);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
				renderEncodeJson(result, request, response, null, true);
			} else {
				result.setObject(list);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]查看推送货源记录异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 发布货源 匹配线路后 推送消息 查看推送线路记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCityLineListDetail")
	public void queryCityLineListDetail(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String messageId = GSONUtils.getJsonValueStr(jsonStr, "messageId");
			String mCity = GSONUtils.getJsonValueStr(jsonStr, "mCity");
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("id", messageId);
			p.put("mCity", mCity);
			List<NstSameCityCarlineEntityDTO> list = nstSameCityCarlineApiService.queryCityLineListDetail(p);
			if (list != null && list.size() > 0) {
				result.setObject(list);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");
				renderEncodeJson(result, request, response, null, true);
			} else {
				result.setObject(list);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]查看推送线路记录异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 点击消息管理 再次推送货源
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getAgainCityAdress")
	public void getAgainCityAdress(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			PushNstMessageDTO dto = (PushNstMessageDTO) GSONUtils.fromJsonToObject(jsonStr, PushNstMessageDTO.class);
			String clat = GSONUtils.getJsonValueStr(jsonStr, "clat");// 客户端纬度
			String clng = GSONUtils.getJsonValueStr(jsonStr, "clng");// 客户端经度
			if (dto.getId() == null) {
				logger.warn("传入推送信息的Id不能为空");
				setEncodeResult(result, ErrorCodeEnum.MESSAGE_ID_IS_NULL, request, response);
				return;
			}
			if (clat == null || "".equals(clat) || clng == null || "".equals(clng)) {
				logger.debug("无定位信息");
				setEncodeResult(result, ErrorCodeEnum.CANNOT_LOCATE, request, response);
				return;
			}
			// 根据传入推送信息的ID查询当前nstpush_message_info中存在的线路推送信息,取出clId集合
			NstSameCityCarlineEntityDTO lineDto = nstSameCityCarlineApiService.queryNstpushMessageById(dto.getId());
			if (lineDto != null) {
				NstSameCityCarlineEntityDTO carLinDto = setNstSameCityCarlineEntityDTO(lineDto);
				if (carLinDto != null) {
					carLinDto.setClat(Double.parseDouble(clat));
					carLinDto.setClng(Double.parseDouble(clng));
					MemberBaseinfoDTO memberDTO = memberToolService.getById(carLinDto.getMemberId() + "");
					List<NstSameCityAddressDTO> lineDTOs = nstSameCityCarlineApiService.excutePushCity(memberDTO,
							carLinDto, nstSameCityCarlineApiService);
					if (lineDTOs != null && lineDTOs.size() > 0) {
						setEncodeResult(result, ErrorCodeEnum.SUCCESS, request,
								response);
					} else {
						result.setObject(lineDTOs);
						setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
					}
				} else {
					result.setObject(null);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				}
			} else {
				result.setObject(null);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}

		} catch (Exception e) {
			logger.warn("[ERROR]同城货源再次推送异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 再次推送货源 封装参数
	 * 
	 * @param lineDto
	 * @return
	 */
	public NstSameCityCarlineEntityDTO setNstSameCityCarlineEntityDTO(NstSameCityCarlineEntityDTO lineDto) {
		List<PushNstMessageInfoDTO> pmInfoDtoLists = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(lineDto.getClId())) {
				// 得到农速通推送消息表中clId的值，排除表中已经对货源推荐过的货源Id
				Long clId = Long.parseLong(lineDto.getClId());
				pmInfoDtoLists = nstSameCityCarlineApiService.getCarLinesByClId(clId);
				lineDto.setId(clId);
			}
			List<Long> cityAddressIds = new ArrayList<>();
			if (pmInfoDtoLists != null && pmInfoDtoLists.size() != 0) {
				for (PushNstMessageInfoDTO p : pmInfoDtoLists) {
					cityAddressIds.add(p.getMb_Id());
				}
			}
			NstSameCityCarlineEntityDTO carLinDto = nstSameCityCarlineApiService.getByDto(lineDto);
			carLinDto.setCityAddressIds(cityAddressIds);
			AreaDTO s_city = areaToolService.getByAreaId(Long.parseLong(carLinDto.getS_cityId() + ""));
			carLinDto.setCityName(s_city.getArea());// 常用城市
			return carLinDto;
		} catch (Exception e) {
			logger.warn("[ERROR]同城货源再次推送异常", e);
		}
		return null;

	}

	/**
	 * 点击消息管理 再次推送线路
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getAgainCityLine")
	public void getAgainCityLine(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			PushNstMessageDTO dto = (PushNstMessageDTO) GSONUtils.fromJsonToObject(jsonStr, PushNstMessageDTO.class);
			String clat = GSONUtils.getJsonValueStr(jsonStr, "clat");// 客户端纬度
			String clng = GSONUtils.getJsonValueStr(jsonStr, "clng");// 客户端经度
			if (dto.getId() == null) {
				logger.warn("传入推送信息的Id不能为空");
				setEncodeResult(result, ErrorCodeEnum.MESSAGE_ID_IS_NULL, request, response);
				return;
			}
			if (clat == null || "".equals(clat) || clng == null || "".equals(clng)) {
				logger.debug("无定位信息");
				setEncodeResult(result, ErrorCodeEnum.CANNOT_LOCATE, request, response);
				return;
			}
			// 根据传入推送信息的ID查询当前nstpush_message_info中存在的线路推送信息,取出clId集合
			NstSameCityCarlineEntityDTO lineDto = nstSameCityCarlineApiService.queryNstpushMessageById(dto.getId());
			if (lineDto != null) {
				NstSameCityAddressDTO cityAddrdto = setNstSameCityAddressDTO(lineDto);
				if (cityAddrdto != null) {
					cityAddrdto.setClat(Double.parseDouble(clat));
					cityAddrdto.setClng(Double.parseDouble(clng));
					MemberBaseinfoDTO memberDTO = memberToolService.getById(cityAddrdto.getMemberId() + "");
					List<NstSameCityCarlineEntityDTO> goodsDTOs = nstSameCityCarlineApiService.excutePushLine(memberDTO,
							cityAddrdto, nstSameCityCarlineApiService);
					if (goodsDTOs != null && goodsDTOs.size() > 0) {
						result.setObject(null);
						setEncodeResult(result, ErrorCodeEnum.SUCCESS, request,
								response);
					} else {
						result.setObject(null);
						setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
					}
				} else {
					result.setObject(null);
					setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				}
			} else {
				result.setObject(null);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}

		} catch (Exception e) {
			logger.warn("[ERROR]同城线路再次推送异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 再次推送线路 封装参数
	 * 
	 * @param cityAddrdto
	 * @return
	 */
	public NstSameCityAddressDTO setNstSameCityAddressDTO(NstSameCityCarlineEntityDTO lineDto) {
		List<PushNstMessageInfoDTO> pmInfoDtoLists = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(lineDto.getMbId())) {
				// 得到农速通推送消息表中mbId的值，排除表中已经对货源推荐过的线路Id
				Long mbId = Long.parseLong(lineDto.getMbId());
				pmInfoDtoLists = memberAddressApiService.getCarLinesBymessageId(mbId);
			}
			List<Long> carLineIds = new ArrayList<>();
			if (pmInfoDtoLists != null && pmInfoDtoLists.size() != 0) {
				for (PushNstMessageInfoDTO p : pmInfoDtoLists) {
					carLineIds.add(p.getCl_Id());
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("id", lineDto.getMbId());
			List<NstSameCityAddressDTO> listByPage = nstSameCityAddressToolService.getNstSameCityAddressListByPage(map);
			if (listByPage.size() > 0 && listByPage != null) {
				NstSameCityAddressDTO cityAddrdto = listByPage.get(0);
				cityAddrdto.setCarLineIds(carLineIds);
				AreaDTO s_city = areaToolService.getByAreaId(Long.parseLong(cityAddrdto.getS_cityId() + ""));
				cityAddrdto.setMcity(s_city.getArea());// 常用城市
				return cityAddrdto;

			}
		} catch (Exception e) {
			logger.warn("[ERROR]同城线路再次推送异常", e);
		}
		return null;
	}

}
