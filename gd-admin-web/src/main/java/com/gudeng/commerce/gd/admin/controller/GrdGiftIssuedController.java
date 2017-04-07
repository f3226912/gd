package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.gudeng.commerce.gd.admin.enums.GrdGiftDetailTypeEnum;
import com.gudeng.commerce.gd.admin.exception.BusinessException;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.GrdGiftDetailToolService;
import com.gudeng.commerce.gd.admin.service.GrdGiftRecordToolService;
import com.gudeng.commerce.gd.admin.service.GrdMemberToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordExportDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftRecordEntity;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * “地推管理中心--礼品发放管理 ”控制类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("grdGiftIssued")
public class GrdGiftIssuedController extends AdminBaseController {

	/**
	 * 市场服务类
	 */
	@Autowired
	private MarketManageService marketManageService;
	/**
	 * 地推礼品记录服务类
	 */
	@Autowired
	private GrdGiftRecordToolService grdGiftRecordToolService;
	/**
	 * 地推礼品详情服务类
	 */
	@Autowired
	private GrdGiftDetailToolService grdGiftDetailToolService;
	/**
	 * 地推人员基础信息服务类
	 */
	@Autowired
	private GrdMemberToolService grdMemberToolService;
	/**
	 * 图片上传服务类
	 */
	@Autowired
	private FileUploadToolService fileUploadToolService;
	/**
	 * 配置文件
	 */
	@Autowired
	public GdProperties gdProperties;

	/**
	 * 显示地推礼品列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showList")
	public String grdGiftIssuedList(HttpServletRequest request) throws Exception {
		// 查询出全部市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		request.setAttribute("marketDTOs", marketDTOs);
		return "grdGiftIssued/grd_gift_issued_list";
	}

	/**
	 * 显示地推礼品详情
	 * 
	 * @param recordId
	 *            礼品发放记录id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showDetail")
	public String grdGiftIssuedDetail(Integer recordId, HttpServletRequest request) throws Exception {
		// 根据礼品记录id查询。结果包含礼品明细、订单明细和注册礼品
		List<GrdGiftDetailDTO> grdGiftDetails = grdGiftDetailToolService.queryByRecordId(recordId);
		// 礼品明细
		List<GrdGiftDetailDTO> grdPresentDetils = new ArrayList<GrdGiftDetailDTO>();
		// 订单明细
		List<GrdGiftDetailDTO> grdOrderDetails = new ArrayList<GrdGiftDetailDTO>();
		// 获得礼品明细和订单明细
		for (GrdGiftDetailDTO grdGiftDetailDTO : grdGiftDetails) {
			// 1表示礼品明细
			if ("1".equals(grdGiftDetailDTO.getType())) {
				grdPresentDetils.add(grdGiftDetailDTO);
				continue;
			} else if ("2".equals(grdGiftDetailDTO.getType())) {// 2表示普通订单明细
				grdOrderDetails.add(grdGiftDetailDTO);
			} else if ("3".equals(grdGiftDetailDTO.getType())) {// 3表示注册明细。此处统计的订单明细包含普通订单和注册明细。
				grdGiftDetailDTO.setOrderNo("首次注册邀请");
				grdOrderDetails.add(grdGiftDetailDTO);
			}
		}

		GrdGiftRecordDTO grdGiftRecord = grdGiftRecordToolService.getById(recordId.toString());
		processGrdGiftRecordDTOById(grdGiftRecord);
		// 车辆图片数量
		int carPicCount = 0;
		if (grdGiftRecord.getCarPictures() != null) {
			carPicCount = grdGiftRecord.getCarPictures().length;
		}

		request.setAttribute("grdPresentDetils", grdPresentDetils);
		request.setAttribute("grdOrderDetails", grdOrderDetails);
		request.setAttribute("grdGiftRecord", grdGiftRecord);
		request.setAttribute("carPicCount", carPicCount);

		return "grdGiftIssued/grd_gift_issued_detail";
	}

	/**
	 * 显示地推礼品详情(农速通)
	 * 
	 * @param recordId
	 *            礼品发放记录id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showDetailNst")
	public ModelAndView grdGiftIssuedDetailByNst(Integer recordId, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 根据礼品记录id查询。结果包含礼品明细、订单明细和注册礼品
		List<GrdGiftDetailDTO> grdGiftDetails = grdGiftDetailToolService.queryByRecordId(recordId);
		// 礼品明细
		List<GrdGiftDetailDTO> grdPresentDetils = new ArrayList<GrdGiftDetailDTO>();
		// 业务统计明细
		List<GrdGiftDetailDTO> grdBusinessDetails = new ArrayList<GrdGiftDetailDTO>();
		// 获得礼品明细和业务统计明细
		for (GrdGiftDetailDTO grdGiftDetailDTO : grdGiftDetails) {
			String grdType = grdGiftDetailDTO.getType();
			if (GrdGiftDetailTypeEnum.GIFT_DETAIL.getGrdType().equals(grdType)) {
				grdPresentDetils.add(grdGiftDetailDTO);
			} else if (GrdGiftDetailTypeEnum.NST_REGISTER.getGrdType().equals(grdType)
					|| GrdGiftDetailTypeEnum.RELEASE_RESOURCE.getGrdType().equals(grdType)
					|| GrdGiftDetailTypeEnum.MESSAGE_ORDER.getGrdType().equals(grdType)
					|| GrdGiftDetailTypeEnum.GOODS_ORDER.getGrdType().equals(grdType)) {
				filterGiftDetailDTO(grdGiftDetailDTO);
				grdBusinessDetails.add(grdGiftDetailDTO);

			}
		}

		GrdGiftRecordDTO grdGiftRecord = grdGiftRecordToolService.getById(recordId.toString());
		processGrdGiftRecordDTOById(grdGiftRecord);
		// 车辆图片数量
		int carPicCount = 0;
		if (grdGiftRecord.getCarPictures() != null) {
			carPicCount = grdGiftRecord.getCarPictures().length;
		}
		mv.addObject("grdPresentDetils", grdPresentDetils);
		mv.addObject("grdBusinessDetails", grdBusinessDetails);
		mv.addObject("grdGiftRecord", grdGiftRecord);
		mv.addObject("carPicCount", carPicCount);
		mv.setViewName("grdGiftIssued/grd_gift_issued_detail_nst");
		return mv;
	}

	/**
	 * 对DTO数据补充
	 * 
	 * @param dto
	 */
	private void filterGiftDetailDTO(GrdGiftDetailDTO dto) {
        //code对应日期描述
		Integer code = dto.getCode();
		if (code != null) {
			String s0 = String.valueOf(code);
			if (s0.length() == 7) {
				String s1 = s0.substring(0, 4);
				String s2 = s0.substring(6, s0.length());
				String s3 = s0.substring(4, 6);
				String s4 = s1 + "-" + s3 + "-%s~" + s1 + "-" + s3 + "-%s";

				if ("1".equals(s2)) {
					s4 = String.format(s4, "01", "11");
				}
				if ("2".equals(s2)) {
					s4 = String.format(s4, "11", "20");
				}
				if ("3".equals(s2)) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR, Integer.parseInt(s1));
					calendar.set(Calendar.MONTH, Integer.parseInt(s3) - 1);
					calendar.set(Calendar.DATE, 1);
					calendar.roll(Calendar.DATE, -1);
					int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					s4 = String.format(s4, "21", maxDay);
				}
				dto.setCodeDetail(s4);
			}

		}
		if(dto.getCountNo()==null){
			dto.setCountNo(0);
		}
		// 农速通注册
		if (GrdGiftDetailTypeEnum.NST_REGISTER.getGrdType().equals(dto.getType())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dto.setCodeDetail("");
			if (dto.getOrderTime() != null) {
				dto.setCodeDetail(sdf.format(dto.getOrderTime()));
			}
		}
		//类型名称
		dto.setTypeName(GrdGiftDetailTypeEnum.getTypeNameByType(dto.getType()));
		
	}

	/**
	 * 对根据id查询出来的礼品记录数据进行加工处理
	 * 
	 * @param grdGiftRecord
	 * @throws Exception
	 */
	private void processGrdGiftRecordDTOById(GrdGiftRecordDTO grdGiftRecord) throws Exception {
		if (grdGiftRecord.getMarketid() != null) {
			MarketDTO markes = marketManageService.getById(grdGiftRecord.getMarketid().toString());
			if (markes != null) {
				grdGiftRecord.setMarketName(markes.getMarketName());
			}
		}

		if (StringUtil.isNotEmpty(grdGiftRecord.getGrantUserId())) {
			GrdMemberDTO grdMembers = grdMemberToolService.getById(grdGiftRecord.getGrantUserId());
			if (grdMembers != null) {
				grdGiftRecord.setGrantUser(grdMembers.getName());
			}
		}

		if (StringUtil.isNotEmpty(grdGiftRecord.getCreateUserId())) {
			GrdMemberDTO grdMembers = grdMemberToolService.getById(grdGiftRecord.getCreateUserId());
			if (grdMembers != null) {
				grdGiftRecord.setCreateUser(grdMembers.getName());
			}
		}

		if (StringUtil.isNotEmpty(grdGiftRecord.getCarPicture())) {
			String[] pictures = grdGiftRecord.getCarPicture().split(",");
			grdGiftRecord.setCarPictures(pictures);
		}
	}

	/**
	 * 更新礼品记录信息的车牌号码(carNo)和图片(carPicture)。
	 * 
	 * @param grdGiftRecord
	 *            参数的封装对象
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateGrdGiftRecord")
	@ResponseBody
	public String updateGrdGiftRecord(GrdGiftRecordEntity grdGiftRecord, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = null;
		try {
			String carNo = grdGiftRecord.getCarNo();
			if (StringUtil.isNotEmpty(carNo) && !verifyCarNumber(carNo)) {
				throw new BusinessException("车牌号码不正确!");
			}

			// 添加修改人和修改时间
			grdGiftRecord.setUpdateTime(new Date());
			grdGiftRecord.setUpdateUserId(super.getUser(request).getUserID());

			// 更新数据库
			grdGiftRecordToolService.dynamicUpdate(grdGiftRecord);

			resultMap = generalResponseResultMap(true, "恭喜你，更新成功!");
		} catch (BusinessException e) {
			resultMap = generalResponseResultMap(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			resultMap = generalResponseResultMap(false, "非常抱歉，更新失败!");
			e.printStackTrace();
		}

		return JSONObject.toJSONString(resultMap);
	}

	/**
	 * 校验车牌号码
	 * 
	 * @param carNum
	 *            车牌号码
	 * @param result
	 * @return true表示校验通过，反之false。
	 */
	private boolean verifyCarNumber(String carNum) {
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}$");
		Matcher matcher = pattern.matcher(carNum);
		if (matcher.matches()) {
			String carNumProvince = carNum.substring(0, 1);
			String allProvince = "京津冀晋辽吉黑沪苏浙皖闽赣鲁甘豫鄂湘川粤桂琼渝藏青宁新蒙贵云陕甘";
			if (allProvince.contains(carNumProvince)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 根据查询条件获取礼物发放数据
	 * 
	 * @param sqb
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryBySearch", method = RequestMethod.POST)
	@ResponseBody
	public String queryBySearch(HttpServletRequest request) {
		Map<String, Object> resultMap = null;
		try {
			// 生成查询参数
			Map<String, Object> searchParam = generateSearchParam(request);
			// 设定分页,排序
			setCommParameters(request, searchParam);

			int total = grdGiftRecordToolService.countBySearch(searchParam);
			List<GrdGiftRecordDTO> grdGiftRecordDTOs = null;
			if (total < 1) {
				grdGiftRecordDTOs = Collections.emptyList();
			} else {
				grdGiftRecordDTOs = grdGiftRecordToolService.queryBySearch(searchParam);
				// 查询出全部市场
				List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
				// 根据市场id获得市场
				for (GrdGiftRecordDTO grdGiftRecordDTO : grdGiftRecordDTOs) {
					if (grdGiftRecordDTO.getMarketid() != null) {
						for (MarketDTO marketDTO : marketDTOs) {
							if (grdGiftRecordDTO.getMarketid().toString().equals(marketDTO.getId().toString())) {
								grdGiftRecordDTO.setMarketName(marketDTO.getMarketName());
								break;
							}
						}
					}
				}
			}

			resultMap = generalResponseResultMap(true, "请求处理成功");
			resultMap.put("total", total);
			resultMap.put("rows", grdGiftRecordDTOs);
		} catch (Exception e) {
			resultMap = generalResponseResultMap(false, "请求处理失败");
			e.printStackTrace();
		}

		// 保留空值的字段过滤器
		ValueFilter filter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				return value != null ? value : "";
			}
		};

		return JSONObject.toJSONString(resultMap, filter, SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 生成查询参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> generateSearchParam(HttpServletRequest request) {
		Map<String, Object> requestParamMap = request.getParameterMap();

		Map<String, Object> searchParamMap = new HashMap<String, Object>(requestParamMap.size());
		String key;
		Object value;
		for (Entry<String, Object> entry : requestParamMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			// 如果是数组参数，则获取第一个参数
			if (value != null && value.getClass().isArray()) {
				value = ((String[]) value)[0];
			}
			searchParamMap.put(key, value);
		}

		return searchParamMap;
	}

	/**
	 * 生成相应的结果集合。
	 * 
	 * @param isSuccess
	 * @param msg
	 * @return
	 */
	private Map<String, Object> generalResponseResultMap(boolean isSuccess, String msg) {
		Map<String, Object> resultMap = new HashMap<String, Object>(4);
		resultMap.put("isSuccess", isSuccess);
		resultMap.put("msg", msg);
		return resultMap;
	}

	/**
	 * 检测导出excel的查询参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkExportExcelParams")
	public String checkExportExcelParams(HttpServletRequest request) {
		Map<String, Object> resultMap;
		try {
			// 生成查询参数
			Map<String, Object> searchParam = generateSearchParam(request);
			searchParam.put("type", 1);//仅仅导出农批商的记录
			int total = grdGiftRecordToolService.countBySearchExport(searchParam);
			resultMap = generalResponseResultMap(true, "请求处理成功");

			if (total > 10000) {
				resultMap.put("isPassed", false);
				resultMap.put("reason", "导出的数据量太大, 请缩减日期范围, 或者修改其他查询条件...");
			} else if (total < 1) {
				resultMap.put("isPassed", false);
				resultMap.put("reason", "导出的结果集无任何数据，请重新修改查询条件...");
			} else {
				resultMap.put("isPassed", true);
				resultMap.put("reason", "参数检测通过");
			}

		} catch (Exception e) {
			resultMap = generalResponseResultMap(false, "请求处理失败");
			e.printStackTrace();
		}
		return JSONObject.toJSONString(resultMap);
	}

	/**
	 * 导出数据到excel
	 * 
	 * @param sqb
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "exportExcelData")
	public String exportExcelData(HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try {

			// 生成查询参数 先查询出type =1 的
			Map<String, Object> searchParam = generateSearchParam(request);
			searchParam.put("type", 1);//仅仅导出农批商的记录
			// 结果集
			List<GrdGiftRecordExportDTO> result = new ArrayList<GrdGiftRecordExportDTO>();
			Map<String, List<GrdGiftRecordExportDTO>> tempMap = new HashMap<String, List<GrdGiftRecordExportDTO>>();
			List<GrdGiftRecordExportDTO> tempList = new ArrayList<GrdGiftRecordExportDTO>();
			int recordId = 0;
			List<GrdGiftRecordExportDTO> grdGiftRecordDTO_all = grdGiftRecordToolService
					.queryBySearchExport(searchParam);
			// 以recordId为主键 存入Map
			for (int i = 0; i < grdGiftRecordDTO_all.size(); i++) {
				GrdGiftRecordExportDTO dto = grdGiftRecordDTO_all.get(i);
				if (recordId == dto.getRecordId() || i == 0) {
					tempList.add(dto);
				} else {
					tempMap.put(String.valueOf(recordId), tempList);
					tempList = new ArrayList<GrdGiftRecordExportDTO>();
					tempList.add(dto);
				}
				if (i == grdGiftRecordDTO_all.size() - 1) {
					tempMap.put(String.valueOf(recordId), tempList);
				}
				recordId = dto.getRecordId();
			}
			for (String str : tempMap.keySet()) {
				List<GrdGiftRecordExportDTO> group_list = tempMap.get(str);
				// 礼品的
				List<GrdGiftRecordExportDTO> grdGiftRecordDTOs_one = new ArrayList<GrdGiftRecordExportDTO>();
				// 订单的
				List<GrdGiftRecordExportDTO> grdGiftRecordDTOs_two = new ArrayList<GrdGiftRecordExportDTO>();
				// 将订单和礼品的分别存放两个LIST方便比较合并
				for (GrdGiftRecordExportDTO group : group_list) {
					if ("1".equals(group.getType())) {
						grdGiftRecordDTOs_one.add(group);
					} else {
						grdGiftRecordDTOs_two.add(group);
					}
				}
				// 对两个LIST合并 说明礼品的条数大于订单的 将订单的移掉只留 下礼品的
				if (grdGiftRecordDTOs_one.size() > grdGiftRecordDTOs_two.size()) {
					for (int i = 0; i < grdGiftRecordDTOs_one.size(); i++) {
						GrdGiftRecordExportDTO dto = grdGiftRecordDTOs_one.get(i);
						if (i < grdGiftRecordDTOs_two.size()) {
							dto.setOrderNo(grdGiftRecordDTOs_two.get(i).getOrderNo());
							dto.setOrderTime(grdGiftRecordDTOs_two.get(i).getOrderTime());
							dto.setShopsName(grdGiftRecordDTOs_two.get(i).getShopsName());
							dto.setOrderPrice(grdGiftRecordDTOs_two.get(i).getOrderPrice());
							dto.setMobile(grdGiftRecordDTOs_two.get(i).getMobile());
							dto.setRealName(grdGiftRecordDTOs_two.get(i).getRealName());
						}
						result.add(dto);
					}
					// 对两个LIST合并 说明订单的条数大于礼品的 将礼品的移掉只留 下订单的
				} else if (grdGiftRecordDTOs_one.size() < grdGiftRecordDTOs_two.size()) {
					for (int i = 0; i < grdGiftRecordDTOs_two.size(); i++) {
						GrdGiftRecordExportDTO dto_two = grdGiftRecordDTOs_two.get(i);
						if (i < grdGiftRecordDTOs_one.size()) {
							dto_two.setGiftName(grdGiftRecordDTOs_one.get(i).getGiftName());
							dto_two.setCountNo(grdGiftRecordDTOs_one.get(i).getCountNo());
						}
						result.add(dto_two);
					}
				} else {
					// 条数一样 留订单
					for (int i = 0; i < grdGiftRecordDTOs_two.size(); i++) {
						GrdGiftRecordExportDTO dto_two = grdGiftRecordDTOs_two.get(i);
						dto_two.setGiftName(grdGiftRecordDTOs_one.get(i).getGiftName());
						dto_two.setCountNo(grdGiftRecordDTOs_one.get(i).getCountNo());
						result.add(dto_two);
					}
				}
			}
			// 排序
			Collections.sort(result, new Comparator<GrdGiftRecordExportDTO>() {
				public int compare(GrdGiftRecordExportDTO arg0, GrdGiftRecordExportDTO arg1) {
					return arg0.getRecordId().compareTo(arg1.getRecordId());
				}
			});
			// 数据额外处理
			processGrdGiftRecordDTOBySearch(result);
			createExcel(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void createExcel(HttpServletResponse response, List<GrdGiftRecordExportDTO> result) throws Exception {
		// 设置输出响应头信息，
		OutputStream ouputStream = null;
		response.setContentType("application/vnd.ms-excel");
		String fileName = new String("礼品发放报表".getBytes(), "ISO-8859-1") + RandomIdGenerator.random("yyyy-MM-dd-HH-");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
		ouputStream = response.getOutputStream();
		// 查询数据list
		// 在输出流中创建一个新的写入工作簿
		WritableWorkbook wwb = Workbook.createWorkbook(ouputStream);
		if (wwb != null) {
			WritableSheet sheet = wwb.createSheet("礼品发放报表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
			// 第一个参数表示列，第二个参数表示行
			Label label00 = new Label(0, 0, "礼品发放记录ID");// 填充第一行第一个单元格的内容
			Label label10 = new Label(1, 0, "礼品放发人");// 填充第一行第二个单元格的内容
			Label label20 = new Label(2, 0, "礼品发放时间");// 填充第一行第三个单元格的内容
			Label label30 = new Label(3, 0, "地推人员姓名");// 填充第一行第四个单元格的内容
			Label label40 = new Label(4, 0, "订单号");//
			Label label50 = new Label(5, 0, "交易日期");//
			Label label60 = new Label(6, 0, "买家姓名");//
			Label label70 = new Label(7, 0, "买家手机");//
			Label label80 = new Label(8, 0, "车牌");//
			Label label90 = new Label(9, 0, "卖家商铺");//
			Label label100 = new Label(10, 0, "交易金额");//
			Label label110 = new Label(11, 0, "礼品名称");//
			Label label120 = new Label(12, 0, "礼品数量");//
			Label label130 = new Label(13, 0, "所属仓库");//
			Label label140 = new Label(14, 0, "领取状态");//
			sheet.addCell(label00);// 将单元格加入表格
			sheet.addCell(label10);// 将单元格加入表格
			sheet.addCell(label20);
			sheet.addCell(label30);
			sheet.addCell(label40);
			sheet.addCell(label50);
			sheet.addCell(label60);
			sheet.addCell(label70);
			sheet.addCell(label80);
			sheet.addCell(label90);
			sheet.addCell(label100);
			sheet.addCell(label110);
			sheet.addCell(label120);
			sheet.addCell(label130);
			sheet.addCell(label140);
			// *** 循环添加数据到工作簿 ***//*
			if (result != null && result.size() > 0) {
				for (int i = 0, lenght = result.size(); i < lenght; i++) {
					GrdGiftRecordExportDTO res = result.get(i);
					/*
					 * memberAccount marketName orderAmount userStar
					 * creditQuotaRange createTime
					 */
					Label label_0 = new Label(0, i + 1, String.valueOf(res.getRecordId()));
					Label label_1 = new Label(1, i + 1, String.valueOf(res.getGrantName()));
					Label label_2 = new Label(2, i + 1, String.valueOf(res.getGrantTimeStr()));
					Label label_3 = new Label(3, i + 1, String.valueOf(res.getCreateUserName()));
					Label label_4 = new Label(4, i + 1, String.valueOf(("-1".equals(res.getOrderNo()))?"首次邀请注册":res.getOrderNo()));
					Label label_5 = new Label(5, i + 1, String.valueOf(res.getOrderTimeStr()));
					Label label_6 = new Label(6, i + 1, String.valueOf(res.getRealName()));
					Label label_7 = new Label(7, i + 1, String.valueOf(res.getMobile()));
					Label label_8 = new Label(8, i + 1, String.valueOf(res.getCarNo()));
					Label label_9 = new Label(9, i + 1, String.valueOf(res.getShopsName()));
					Label label_10 = new Label(10, i + 1, String.valueOf(res.getOrderPrice()));
					Label label_11 = new Label(11, i + 1, String.valueOf(res.getGiftName()));
					Label label_12 = new Label(12, i + 1, String.valueOf(res.getCountNo()));
					Label label_13 = new Label(13, i + 1, String.valueOf(res.getGiftstoreName()));
					Label label_14 = new Label(14, i + 1, String.valueOf(res.getStatus()));
					sheet.addCell(label_0);// 将单元格加入表格
					sheet.addCell(label_1);//
					sheet.addCell(label_2);
					sheet.addCell(label_3);
					sheet.addCell(label_4);
					sheet.addCell(label_5);
					sheet.addCell(label_6);
					sheet.addCell(label_7);
					sheet.addCell(label_8);
					sheet.addCell(label_9);
					sheet.addCell(label_10);
					sheet.addCell(label_11);
					sheet.addCell(label_12);
					sheet.addCell(label_13);
					sheet.addCell(label_14);
				}
			}
			wwb.write();// 将数据写入工作簿
		}
		wwb.close();// 关闭
		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * 对查询出来的DTO数据加工处理
	 * 
	 * @param grdGiftRecordDTOs
	 * @throws Exception
	 */
	private void processGrdGiftRecordDTOBySearch(List<GrdGiftRecordExportDTO> grdGiftRecordDTOs) throws Exception {
		for (GrdGiftRecordExportDTO grdGiftRecordDTO : grdGiftRecordDTOs) {
			if (StringUtil.isNotEmpty(grdGiftRecordDTO.getStatus())) {
				if (grdGiftRecordDTO.getStatus().equals("0")) {
					grdGiftRecordDTO.setStatus("未发放");
				} else if (grdGiftRecordDTO.getStatus().equals("1")) {
					grdGiftRecordDTO.setStatus("已发放");
				}
			}

			if (null == grdGiftRecordDTO.getOrderNo() || "".equals(grdGiftRecordDTO.getOrderNo())) {
				grdGiftRecordDTO.setOrderNo("--");
			}
			if (null == grdGiftRecordDTO.getShopsName() || "".equals(grdGiftRecordDTO.getShopsName())) {
				grdGiftRecordDTO.setShopsName("--");
			}
			if (null == grdGiftRecordDTO.getGiftName() || "".equals(grdGiftRecordDTO.getGiftName())) {
				grdGiftRecordDTO.setGiftName("--");
			}
			if (null == grdGiftRecordDTO.getCarNo() || "".equals(grdGiftRecordDTO.getCarNo())) {
				grdGiftRecordDTO.setCarNo("--");
			}
			if (null == grdGiftRecordDTO.getGrantName() || "".equals(grdGiftRecordDTO.getGrantName())) {
				grdGiftRecordDTO.setGrantName("--");
			}
			if (null == grdGiftRecordDTO.getCreateUserName() || "".equals(grdGiftRecordDTO.getCreateUserName())) {
				grdGiftRecordDTO.setCreateUserName("--");
			}
			if ("0".equals(grdGiftRecordDTO.getCountNo()) || null == grdGiftRecordDTO.getCountNo()) {
				grdGiftRecordDTO.setCountNo("--");
			}
			if (null == grdGiftRecordDTO.getOrderPrice() || "".equals(grdGiftRecordDTO.getOrderPrice())
					|| "0.00".equals(grdGiftRecordDTO.getOrderPrice())) {
				grdGiftRecordDTO.setOrderPrice("--");
			}
			if (null == grdGiftRecordDTO.getMobile() || "".equals(grdGiftRecordDTO.getMobile())) {
				grdGiftRecordDTO.setMobile("--");
			}
			if (null == grdGiftRecordDTO.getRealName() || "".equals(grdGiftRecordDTO.getRealName())) {
				grdGiftRecordDTO.setRealName("--");
			}
			if (null == grdGiftRecordDTO.getGiftstoreName() || "".equals(grdGiftRecordDTO.getGiftstoreName())) {
				grdGiftRecordDTO.setGiftstoreName("--");
			}
			if (null == grdGiftRecordDTO.getOrderTime() || "".equals(grdGiftRecordDTO.getOrderTime())) {
				grdGiftRecordDTO.setOrderTimeStr("--");
			} else {
				grdGiftRecordDTO.setOrderTimeStr(
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(grdGiftRecordDTO.getOrderTime()));
			}
			if (null == grdGiftRecordDTO.getGrantTime() || "".equals(grdGiftRecordDTO.getGrantTime())) {
				grdGiftRecordDTO.setGrantTimeStr("--");
			} else {
				grdGiftRecordDTO.setGrantTimeStr(
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(grdGiftRecordDTO.getGrantTime()));
			}
		}
	}

	/**
	 * 上传车辆图片
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadCarPic")
	public String uploadCarPic(HttpServletRequest request,
			@RequestParam(value = "carPicture", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(file.getBytes(), fileName);
			} else {
				map.put("status", 0);
				map.put("message", "upload file failed!!");
				return JSONObject.toJSONString(map);
			}
		} catch (Exception e) {
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		}
		map.put("status", 1);
		map.put("message", masterPicPath);
		map.put("url", masterPicPath);
		return JSONObject.toJSONString(map);
	}

	/**
	 * @Description 获取所属仓库
	 * @param marketid
	 * @param request
	 * @return
	 * @CreationDate 2016年8月15日 下午1:42:56
	 * @Author liufan
	 */
	@RequestMapping("getChildStoreInfo/{marketid}")
	@ResponseBody
	public String getChildStoreInfo(@PathVariable("marketid") String marketid, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketid", marketid);
			List<GrdGiftRecordDTO> list = grdGiftRecordToolService.getChildStoreInfo(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
