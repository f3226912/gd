package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MemberAddressManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.MemberCertifiToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.AreaUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.JsonConvertUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 收发货管理Controller
 * 
 * @author
 */
@Controller
@RequestMapping("consignment")
public class MemberAddressManagerController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MemberAddressManagerController.class);

	@Autowired
	public MemberAddressManageService memberAddressManageService;

	@Autowired
	public QueryAreaService queryAreaService;

	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired                                                                                                                      
	public MemberCertifiToolService memberCertifiToolService;

	/**
	 * memberAddress
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String memberAddress(HttpServletRequest request) {
		return "memberAddress/memberAddressList";
	}

	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			// 设置查询参数
			// 记录数
			map.put("total", memberAddressManageService.getTotalByName(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberAddressDTO> list = memberAddressManageService.getByName(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据name查询
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryByName")
	@ResponseBody
	public String queryByName(@RequestParam String realName,@RequestParam String startDate,@RequestParam String endDate, @RequestParam String queryType,
			@RequestParam String orderStatus, @RequestParam String cityName, @RequestParam String isOrderDeleted, @RequestParam String  clients, HttpServletRequest request) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotEmpty(cityName)) {
				AreaDTO area = queryAreaService.getAreaByName(cityName);
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			map.put("realName", realName);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("userType", queryType);
			map.put("orderStatus", orderStatus);
			map.put("isOrderDeleted", isOrderDeleted);
			map.put("clients", clients);
			// 记录数
			map.put("total", memberAddressManageService.getTotalByName(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberAddressDTO> list = memberAddressManageService.getByName(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			String returnStr = JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deletebyid")
	@ResponseBody
	public String deleteById(@RequestParam String id, HttpServletRequest request) {
		try {
			logger.info("删除货源操作 id="+id);
			if (null == id || id.equals("")) {
				return "faild";
			}
			
			//memberAddressManageService.deleteById(id);
			//此处改为updateMemberAdressStatusByid  保留删除记录
			memberAddressManageService.updateMemberAdressStatusByid(id);
			logger.info("删除货源操作 id="+id + "成功！");
			return "success";
		} catch (Exception e) {
			logger.warn("删除货源操作 id="+id + "失败！");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request) {
		return "memberAddress/addAddress";
	}

	/**
	 * 处理提交的MemberAddressDTO 中 sendDate ,endDate 字符串转换时间处理
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 增加修改同一个方法
	 * 
	 * @param request
	 * @param memberId
	 *            企业ID
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, MemberAddressDTO addressDTO, String memberId) {
		try {
			String s_provinceId = request.getParameter("start_provinceId");
			String s_cityId = request.getParameter("start_cityId");
			String s_areaId = request.getParameter("start_areaId");
			addressDTO.setS_provinceId(!StringUtils.isEmpty(s_provinceId)?Long.parseLong(s_provinceId):0);                            
			addressDTO.setS_cityId(!StringUtils.isEmpty(s_cityId)?Long.parseLong(s_cityId):0);                                        
			addressDTO.setS_areaId(!StringUtils.isEmpty(s_areaId)?Long.parseLong(s_areaId):0);    
			String f_provinceId = request.getParameter("end_provinceId");
			String f_cityId = request.getParameter("end_cityId");
			String f_areaId = request.getParameter("end_areaId");
			addressDTO.setF_provinceId(!StringUtils.isEmpty(f_provinceId)?Long.parseLong(f_provinceId):0);                            
			addressDTO.setF_cityId(!StringUtils.isEmpty(f_cityId)?Long.parseLong(f_cityId):0);                                        
			addressDTO.setF_areaId(!StringUtils.isEmpty(f_areaId)?Long.parseLong(f_areaId):0); 
			
			addressDTO.setSendDateString(StringUtil.isNotEmpty(request.getParameter("sendDate")) ? request.getParameter("sendDate") : null);
			addressDTO.setEndDateString(StringUtil.isNotEmpty(request.getParameter("endDate")) ? request.getParameter("endDate") : null);
			String totalWeight = request.getParameter("totalWeight");
			addressDTO.setTotalWeight(StringUtil.isNotEmpty(totalWeight) ? Double.parseDouble(totalWeight) : 0);
			String totalSize = request.getParameter("totalSize");
			addressDTO.setTotalSize(StringUtil.isNotEmpty(totalSize) ? Integer.parseInt(totalSize) : 0);
			
			//不对发运方式做处理
			/*if (StringUtil.isEmpty(request.getParameter("price"))) {
				addressDTO.setSendGoodsType(2);
			} else if (StringUtil.isNotEmpty(request.getParameter("price")) && StringUtil.isEmpty(request.getParameter("priceUnitType"))) {
				addressDTO.setSendGoodsType(1);
			}*/

			 String mobile = request.getParameter("memberMobile");
			 if(StringUtil.isNotEmpty(mobile)){
		        MemberBaseinfoDTO member = memberBaseinfoToolService.getByMobile(mobile);
			    //输入手机号码
			    if(member != null)
			    {
			    	//个人发货
			    	if("1".equals(String.valueOf(member.getUserType())) || null ==member.getUserType())
			    	{
			    		     addressDTO.setUserId(member.getMemberId());
					    	 addressDTO.setCreateUserId(memberId);
					         addressDTO.setUserMobile(mobile);
					         addressDTO.setUserType(1);
			    	}
			    	else{
			    		 addressDTO.setCreateUserId(memberId);
						 addressDTO.setUserId(Long.valueOf(memberId));
					     addressDTO.setUserMobile(mobile);
					     addressDTO.setUserType(2);
			    	}
			    }
			    //没有输入手机号码，企业发货
		    	else{
		    		 addressDTO.setCreateUserId(memberId);
					 addressDTO.setUserId(Long.valueOf(memberId));
				     addressDTO.setUserMobile(mobile);
				     addressDTO.setUserType(2);
		    	}
			    
			 }else{
				   return "exception";	
			 }
			 
			if("1".equals(addressDTO.getSendGoodsType())){
				addressDTO.setPriceUnitType(0);
			}
			String id = request.getParameter("id");
			MemberAddressDTO memberAddress = memberAddressManageService.getById(id);
			int i = 0;
			// TODO
			if (memberAddress != null) {
				logger.info("更新货源操作 id="+id);
				addressDTO.setId(Long.parseLong(id));
				//addressDTO.setUpdateUserId(getUser(request) != null ? getUser(request).getUserID() : "1");
				addressDTO.setUpdateTimeString(DateUtil.getSysDateTimeString());
				i = memberAddressManageService.updateMemberAddressDTO(addressDTO);
				logger.info("更新货源操作 id="+id +"成功！");
			} else {

				addressDTO.setCreateTimeString(DateUtil.getSysDateTimeString());
				i = memberAddressManageService.addMemberAddressDTO(addressDTO);

			}
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.warn("保存货源操作 memberId="+memberId +" 失败！");
			logger.warn("Exception is ："+e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			MemberAddressDTO dto = memberAddressManageService.getById(id);
			putModel("id", dto.getId());
			putModel("goodsName", dto.getGoodsName());
			putModel("totalWeight", dto.getTotalWeight());
			putModel("totalSize", dto.getTotalSize());
			if (dto.getPrice() != null) {
				DecimalFormat df = new DecimalFormat("0.00");
				putModel("price", df.format(dto.getPrice()));
			}
			putModel("priceUnitType", dto.getPriceUnitType());
			putModel("sendDate", DateUtil.toString(dto.getSendDate(), DateUtil.DATE_FORMAT_DATEONLY));
			putModel("sendDateType", dto.getSendDateType());
			putModel("endDate", DateUtil.toString(dto.getEndDate(), DateUtil.DATE_FORMAT_DATEONLY));
			putModel("endDateType", dto.getEndDateType());
			putModel("carType", dto.getCarType());
			putModel("carLength", dto.getCarLength());
			putModel("goodsType", dto.getGoodsType());
			putModel("sendGoodsType", dto.getSendGoodsType());
			putModel("start_provinceId", dto.getS_provinceId());
			putModel("start_cityId", dto.getS_cityId());
			putModel("start_areaId", dto.getS_areaId());

			putModel("end_provinceId", dto.getF_provinceId());
			putModel("end_cityId", dto.getF_cityId());
			putModel("end_areaId", dto.getF_areaId());
			putModel("userMobile", dto.getUserMobile());
			putModel("userType", dto.getUserType());
			putModel("remark", dto.getRemark());
			if(dto.getUserType() !=null)
			{
				 //根据发货人手机号码查询
				 MemberBaseinfoDTO member = memberBaseinfoToolService.getByMobile(dto.getUserMobile());
				if(1==dto.getUserType())
				{
				  putModel("memberName", member != null ? member.getRealName() : "");
				}
				else if(2==dto.getUserType())
				{
				  putModel("memberName", member != null ? member.getCompanyContact() : "");
				}
			}
			
			if (StringUtils.isNotEmpty(dto.getCreateUserId())) {
				MemberBaseinfoDTO memberDTO = memberBaseinfoToolService.getById(dto.getCreateUserId());
				if (memberDTO != null) {
					putModel("memberId", memberDTO.getMemberId());
					putModel("companyName", memberDTO.getCompanyName());
					putModel("companyMobile", memberDTO.getCompanyMobile());
				}
			}
			return "memberAddress/editAddress";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 查询会员信息
	@RequestMapping(value = "queryMemberByMobile")
	@ResponseBody
	public MemberBaseinfoDTO queryMemberByMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mobile = request.getParameter("userMobile");
		MemberBaseinfoDTO member  = memberBaseinfoToolService.getByMobile(mobile);
		return member;
	}

	// *******************区域查询************************//

	// 查询全国行政区代码省
	@RequestMapping(value = "queryProvince")
	@ResponseBody

	public String queryProvince(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String type =request.getParameter("type");
		List<AreaDTO> list = queryAreaService.findProvince();
		if("start".equals(type)){
		 this.jsonUtil(list, response);	
		}else{
		 List<AreaDTO>  newList= new ArrayList<AreaDTO>(); 
		 AreaDTO area =new AreaDTO();
		 area.setAreaID("0");
		 area.setArea("全国");
		 newList.add(area);
		 newList.addAll(list);
		this.jsonUtil(newList, response);
		}
		return null;
	}

	// 查询全国行政区代码市
	@RequestMapping(value = "queryCity")
	@ResponseBody
	public String queryCity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<?> list = queryAreaService.findCity(request.getParameter("province"));
		this.jsonUtil(list, response);
		return null;
	}

	// 查询全国行政区代码县区
	@RequestMapping(value = "queryCounty")
	@ResponseBody
	public String queryCounty(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String city = request.getParameter("city");
		List<?> list = queryAreaService.findCounty(city);
		this.jsonUtil(list, response);
		return null;
	}

	private void jsonUtil(Object list, HttpServletResponse response) throws Exception {
		logger.info("JSON格式：" + list.toString());
		String returnJson = JsonConvertUtil.returnJson(list);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(returnJson);
	}

	private void convertPageList(List<MemberAddressDTO> list) throws Exception {

		if (list != null && list.size() > 0) {
			for (MemberAddressDTO dto : list) {
				//始发地目的地
				setAddress(dto);

				if (dto.getCarType() != null) {
					switch (dto.getCarType()) {
					case 0:
						dto.setCarTypeString("厢式货车");
						break;
					case 1:
						dto.setCarTypeString("敞车");
						break;
					case 2:
						dto.setCarTypeString("冷藏车");
						break;
					case 3:
						dto.setCarTypeString("保温车");
						break;
					case 4:
						dto.setCarTypeString("其他");
						break;
					default:
						break;
					}
				}
				if (dto.getSendGoodsType() != null) {
					switch (dto.getSendGoodsType()) {
					case 0:
						dto.setSendGoodsTypeString("零担");
						break;
					case 1:
						dto.setSendGoodsTypeString("整车");
						break;
					case 2:
						dto.setSendGoodsTypeString("不限");
						break;
					default:
						break;
					}
				}
				
				if (dto.getGoodsType() != null) {
					switch (dto.getGoodsType()) {
					case 0:
						dto.setGoodsTypeString("普货");
						break;
					case 1:
						dto.setGoodsTypeString("冷藏");
						break;
					case 2:
						dto.setGoodsTypeString("鲜活水产");
						break;
					case 3:
						dto.setGoodsTypeString("其他");
						break;
					case 4:
						dto.setGoodsTypeString("重货");
						break;
					case 5:
						dto.setGoodsTypeString("抛货");
						break;
					case 6:
						dto.setGoodsTypeString("蔬菜");
						break;
					case 7:
						dto.setGoodsTypeString("水果");
						break;
					case 8:
						dto.setGoodsTypeString("农副产品");
						break;
					case 9:
						dto.setGoodsTypeString("日用品");
						break;
					case 10:
						dto.setGoodsTypeString("纺织");
						break;
					case 11:
						dto.setGoodsTypeString("木材");
						break;
					case 101:
						dto.setGoodsTypeString("蔬菜水果");
						break;
					case 102:
						dto.setGoodsTypeString("干调粮油");
						break;
					case 103:
						dto.setGoodsTypeString("活鲜水产");
						break;
					case 104:
						dto.setGoodsTypeString("食品饮料");
						break;
					case 105:
						dto.setGoodsTypeString("冷冻商品");
						break;
					case 106:
						dto.setGoodsTypeString("化肥种子");
					case 107:
						dto.setGoodsTypeString("农资农具");
						break;
					case 108:
						dto.setGoodsTypeString("日用品");
						break;
					case 109:
						dto.setGoodsTypeString("建材设备");
						break;
					case 110:
						dto.setGoodsTypeString("其他商品");
						break;
					default:
						break;
					}
				}
				dto.setTotalSize(dto.getTotalSize()!=null ?dto.getTotalSize() :0);
				
				NumberFormat weightFormat = NumberFormat.getNumberInstance();
				weightFormat.setMaximumFractionDigits(1);
				
				if (dto.getHundredweight() != null) {
					if (dto.getTotalWeight() != null && dto.getTotalWeight() > 0) {
						switch (dto.getHundredweight()) {
						case 0:
							dto.setHundredweightString(weightFormat.format(dto.getTotalWeight()) + "吨");
							break;
						case 1:
							dto.setHundredweightString(weightFormat.format(dto.getTotalWeight()) + "公斤");
							break;
						default:
							break;
						}
					}
				}else if(dto.getTotalWeight() != null && dto.getTotalWeight() > 0) {
				 dto.setHundredweightString(weightFormat.format(dto.getTotalWeight()) + "吨");
				}
				
				NumberFormat nf = NumberFormat.getNumberInstance();
				nf.setMaximumFractionDigits(2);
				//单位转换
				if (null !=dto.getPrice() && dto.getPrice() >0 && null != dto.getPriceUnitType()) {

					switch (dto.getPriceUnitType()) {
					case 0:
						dto.setPriceString(nf.format(dto.getPrice()) + "元/吨");
						break;
					case 1:
						dto.setPriceString(nf.format(dto.getPrice()) + "元/公斤");
						break;
					case 2:
						dto.setPriceString(nf.format(dto.getPrice()) + "元/立方");
						break;
					default:
						break;
					}
				}
				else if (null !=dto.getPrice() && dto.getPrice() >0) {
					if(null == dto.getPriceUnitType()){
						dto.setPriceString(nf.format(dto.getPrice()) + "元");
					}else if( 0 == dto.getPriceUnitType()){
						dto.setPriceString(nf.format(dto.getPrice()) + "元");
					}
				}else {
					dto.setPriceString("面议");
				}
				
				if(StringUtils.isEmpty(dto.getOrderStatus()))
				{
	            	dto.setOrderStatus("");
	            }else if ("1".equals(dto.getOrderStatus())) {
					dto.setOrderStatus("待成交");
				} else if ("2".equals(dto.getOrderStatus())) {
					dto.setOrderStatus("已成交");
				} else if ("3".equals(dto.getOrderStatus())) {
					dto.setOrderStatus("已完成");
				} else if ("4".equals(dto.getOrderStatus())) {
					dto.setOrderStatus("未完成");
				} else if ("5".equals(dto.getOrderStatus())) {
					dto.setOrderStatus("已取消");
				} else {
					dto.setOrderStatus("待接单");
				}				//订单是否删除
                dto.setIsOrderDeleted("1".equals(dto.getIsOrderDeleted())?"已删除":"未删除");
				dto.setSendDateString(DateUtil.toString(dto.getSendDate(), DateUtil.DATE_FORMAT_DATEONLY));
				dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
				
		      /*   MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(dto.getUserId()));
                //常驻城市
		        if(member != null && member.getCcityId() !=null){
            	AreaDTO areaDTO = queryAreaService.getArea(String.valueOf(member.getCcityId()));
                dto.setBaseCity(areaDTO !=null? areaDTO.getArea():"");
                }*/
				
				//发布来源
				dto.setClients(getPublishType(dto.getClients()));
		    	AreaDTO areaDTO = queryAreaService.getArea(String.valueOf(dto.getCcityId()));
                dto.setBaseCity(areaDTO !=null? areaDTO.getArea():"");
				if (dto.getUserType() != null) {
					switch (dto.getUserType()) {
					case 1:
						dto.setUserTypeName("个人");
						break;
					case 2:
						dto.setUserTypeName("企业");
		                MemberCertifiDTO m =memberCertifiToolService.getByUserId(String.valueOf(dto.getUserId()));
						dto.setCompanyName(m != null ?m.getCompanyName() :"");
						dto.setCompanyMobile(m != null ?m.getMobile():"");
						break;
					default:
						break;
					}
				}
				
			}
		}
	
	}

	private void setAddress(MemberAddressDTO dto) throws Exception {
		StringBuffer startPlace = new StringBuffer();
		if(dto.getS_provinceId() != null && dto.getS_provinceId() >0){
		AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId()));
		startPlace.append(province != null ? province.getArea() : "");
		AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId()));
		//如果二级目录名称为市辖区，县，省直辖行政单位，不展示
		startPlace.append(AreaUtil.isCity(city)?" " +city.getArea() : "");
		AreaDTO area = queryAreaService.getArea(String.valueOf(dto.getS_areaId()));
		startPlace.append(area != null ? " " + area.getArea() : "");
		}else{
			startPlace.append("全国");
		}

		StringBuffer endPlace = new StringBuffer();
		if(dto.getF_provinceId() != null && dto.getF_provinceId() >0){
		AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getF_provinceId()));
		endPlace.append(e_province != null ? e_province.getArea() : "");
		AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getF_cityId()));
		endPlace.append(AreaUtil.isCity(e_city)?" " +e_city.getArea() : "");
		AreaDTO e_area = queryAreaService.getArea(String.valueOf(dto.getF_areaId()));
		endPlace.append(e_area != null ? " " + e_area.getArea() : "");
		}else{
			endPlace.append("全国");
		}
		dto.setStartPlace(startPlace.toString());
		dto.setEndPlace(endPlace.toString());
	}

	/**
	 * @Description listCompany 初始化所有公司
	 * @return
	 * @CreationDate 2015年11月11日 下午4:16:28
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@ResponseBody
	@RequestMapping("initCompany")
	public String listCompany() {
		List<MemberBaseinfoDTO> company = null;
		try {
			company = memberBaseinfoToolService.getCompany();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(company);
	}

	/**
	 * @Description entSelect 进入选择企业页面
	 * @return
	 * @CreationDate 2015年11月11日 下午8:10:49
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping("entList")
	public String entSelect() {
		return "memberAddress/entList";
	}

	/**
	 * @Description queryEntList 查询企业列表
	 * @param carNumber
	 * @param request
	 * @return
	 * @CreationDate 2015年11月11日 下午8:12:41
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping("queryEnt")
	@ResponseBody
	public String queryEntList(HttpServletRequest request) {
		try {
			String companyName =request.getParameter("entName");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyName", companyName);
			// 设置查询参数
			// 记录数
			map.put("total", memberBaseinfoToolService.getCompanyByConditionTotal(map));
			// //设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.getCompanyByCondition(map);
			if (list != null && list.size() > 0) {
				Iterator<MemberBaseinfoDTO> iterator = list.iterator();
				while (iterator.hasNext()) {
					MemberBaseinfoDTO dto = iterator.next();
					if (StringUtils.isEmpty(dto.getCompanyName())) {
						list.remove(dto);
					}
				}
			}
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			String cityName = request.getParameter("cityName");
			if (StringUtils.isNotEmpty(cityName)) {
				try {
					AreaDTO area = queryAreaService.getAreaByName(cityName);
					// 设置查询参数
					if (area != null) {
						map.put("cCityId", area.getAreaID());
					} else {
						map.put("cCityId", "000000");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("realName", request.getParameter("realName"));
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("userType", request.getParameter("queryType"));
			map.put("orderStatus", request.getParameter("orderStatus"));
			map.put("clients", request.getParameter("clients"));
			map.put("isOrderDeleted", request.getParameter("isOrderDeleted"));
			int total = memberAddressManageService.getTotalByName(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-货源信息导出验证异常 ", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String realName,String startDate,
			String endDate, String queryType, String cityName, String isDel, String orderStatus , String isOrderDeleted ,String clients) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		if (StringUtils.isNotEmpty(cityName)) {
			try {
				AreaDTO area = queryAreaService.getAreaByName(cityName);
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("realName", realName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("userType", queryType);
		map.put("orderStatus", orderStatus);
		map.put("isOrderDeleted", isOrderDeleted);
		map.put("clients", clients);

		// 记录数
		// list
		map.put("startRow", 0);
		map.put("endRow", 100000000);
	
		//用来判断已进行软删除的的字段，导出已删除数据用
		map.put("isDel", isDel);
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = null;
			if(!"1".equals(isDel )){
				fileName = new String("货源信息表".getBytes(), "ISO-8859-1")+startDate+"_"+endDate;
			}else{
				fileName = new String("已删除货源信息表".getBytes(), "ISO-8859-1")+startDate+"_"+endDate;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<MemberAddressDTO> list = memberAddressManageService.getByName(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("货源信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "始发地");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "目的地 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "姓名");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "手机号码 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "用户类型 ");// 
				Label label50 = new Label(5, 0, "企业名称");// 
				Label label60 = new Label(6, 0, "企业联系电话");// 
				Label label70 = new Label(7, 0, "货物名称");// 
				Label label80 = new Label(8, 0, "总重量 ");//
				Label label90 = new Label(9, 0, "总体积 ");//
				
				Label label100 = new Label(10, 0, "发运方式 ");//
				Label label101 = new Label(11, 0, "价格(元) ");//
				Label label102 = new Label(12, 0, "发布时间");//
				Label label103= new Label(13, 0, "常用城市");//
				Label label104= new Label(14, 0, "订单状态");//
				Label label105= new Label(15, 0, "订单是否删除");//
				Label label106= new Label(16, 0, "发布来源");//
				
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
				sheet.addCell(label101);
				sheet.addCell(label102);
				sheet.addCell(label103);
				sheet.addCell(label104);
				sheet.addCell(label105);
				sheet.addCell(label106);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					convertPageList(list);
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						MemberAddressDTO dto = list.get(i);
						setAddress(dto);
						Label label0 = new Label(0, i + 1, dto.getStartPlace());
						Label label1 = new Label(1, i + 1, dto.getEndPlace());
						Label label2 = new Label(2, i + 1, dto.getNickName());
						Label label3 = new Label(3, i + 1, dto.getUserMobile());
						Label label4 = new Label(4, i + 1, dto.getUserTypeName());
						Label label5 = new Label(5, i + 1, dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getCompanyMobile());
						Label label7 = new Label(7, i + 1, dto.getGoodsName());
						Label label8 = new Label(8, i + 1, dto.getHundredweightString());
						Label label9= new Label(9, i + 1, String.valueOf(dto.getTotalSize()));
						Label label11 = new Label(10, i + 1, dto.getSendGoodsTypeString());
						Label label12= new Label(11, i + 1, dto.getPriceString());
						Label label13 = new Label(12, i + 1, dto.getCreateTimeString());
						Label label14 = new Label(13, i + 1, dto.getBaseCity());
						Label label15 = new Label(14, i + 1, dto.getOrderStatus());
						Label label16 = new Label(15, i + 1, dto.getIsOrderDeleted());
						Label label17= new Label(16, i + 1, dto.getClients());
						
						sheet.addCell(label0);//将单元格加入表格
						sheet.addCell(label1);// 
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label11);
						sheet.addCell(label12);
						sheet.addCell(label13);
						sheet.addCell(label14);
						sheet.addCell(label15);
						sheet.addCell(label16);
						sheet.addCell(label17);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private String getPublishType(String client) {
		//'1谷登农批，2农速通，3农商友，4产地供应商，5农商友-农批商',
		String type = null;
		if ("1".equals(client)) {
			type = "谷登农批";
		} else if ("2".equals(client) || client==null ) {
			type = "农速通";
		} else if ("3".equals(client)) {
			type = "农商友";
		} else if ("4".equals(client)) {
			type = "产地供应商";
		}else if ("5".equals(client) || "6".equals(client)) {
			type = "农商友-农批商";
		}
		return type;
	}
	
	
}
