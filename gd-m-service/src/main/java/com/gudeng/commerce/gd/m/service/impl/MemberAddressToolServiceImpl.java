package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;
import com.gudeng.commerce.gd.m.service.MemberAddressToolService;
import com.gudeng.commerce.gd.m.util.DateTimeUtils;
import com.gudeng.commerce.gd.m.util.GdProperties;


/**
 *功能描述：收发货地址管理
 */
public class MemberAddressToolServiceImpl implements MemberAddressToolService{
	
	@Autowired
	public GdProperties gdProperties;
	
	private static MemberAddressService memberAddressService;
	
	private static AreaService areaService;
	
	private MemberAddressService getHessianService() throws MalformedURLException {
		String hessianUrl = gdProperties.getMemberAddressServiceUrl();
		if(memberAddressService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberAddressService = (MemberAddressService) factory.create(MemberAddressService.class, hessianUrl);
		}
		return memberAddressService;
	}
	

	private AreaService getHessianAreaService()
			throws MalformedURLException {
		String url = gdProperties.getAreaServiceUrl();
		if (areaService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(
					AreaService.class, url);
		}
		
		return areaService;
	}
	
	public List<MemberAddressDTO> listMemberAddressByUserId(MemberAddressDTO memberAddressDTO) throws Exception {
		return getHessianService().listMemberAddressByUserId(memberAddressDTO);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianService().getTotal(map);
	}

	@Override
	public List<MemberAddressDTO> getByCondition(Map<String, Object> map) throws Exception {
		List<MemberAddressDTO> result = getHessianService().getByCondition(map);
		
		for (int i = 0; i < result.size(); i++) {
			MemberAddressDTO mad = result.get(i);
			
			/*
			 * 地名
			 */

			AreaDTO area = getHessianAreaService().getArea(String.valueOf(mad.getS_provinceId()));
			mad.setS_provinceName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getS_cityId()));
			mad.setS_cityName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getS_areaId()));
			mad.setS_areaName(area.getArea());
			
			area = getHessianAreaService().getArea(String.valueOf(mad.getF_provinceId()));
			mad.setF_provinceName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getF_cityId()));
			mad.setF_cityName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getF_areaId()));
			mad.setF_areaName(area.getArea());
			
			switchCarType(mad);
			
			switchHundredweight(mad);
			
			switchGoodsType(mad);
		}
		
		return result;
	}
	
	@Override
	public Integer getTotalForOrder(Map<String, Object> getTotalForOrder) throws Exception {
		return getHessianService().getTotalForOrder(getTotalForOrder);
	}


	@Override
	public List<MemberAddressDTO> getListForOrder(Map<String, Object> query) throws Exception {
		List<MemberAddressDTO> result = getHessianService().getListForOrder(query);
		
		for (int i = 0; i < result.size(); i++) {
			MemberAddressDTO mad = result.get(i);
			
			/*
			 * 地名
			 */
			AreaDTO area = getHessianAreaService().getArea(String.valueOf(mad.getS_provinceId()));
			if(area!=null)
				mad.setS_provinceName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getS_cityId()));
			if(area!=null)
				mad.setS_cityName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getS_areaId()));
			if(area!=null)
				mad.setS_areaName(area.getArea());
			
			area = getHessianAreaService().getArea(String.valueOf(mad.getF_provinceId()));
			if(area!=null)
				mad.setF_provinceName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getF_cityId()));
			if(area!=null)
				mad.setF_cityName(area.getArea());
			area = getHessianAreaService().getArea(String.valueOf(mad.getF_areaId()));
			if(area!=null)
				mad.setF_areaName(area.getArea());
			
			//修饰发布时间
			mad.setTimebefore(DateTimeUtils.formatDate(mad.getCreateTime(),"MM-dd"));
			
			switchCarType(mad);
			
			switchHundredweight(mad);
			
			switchGoodsType(mad);
		}
		
		return result;
	}
	
	private void switchCarType(MemberAddressDTO mad) {
		/*
		 * 车型 车辆类型  
		 */
		if(mad.getCarType()==null) return;
		
		if(mad.getSource() == 1){			
		
			mad.setCarTypeString(getSameCityCarTypeString(mad.getCarType()));			
			return;
		}		
				
		switch(mad.getCarType()) {
		case 0:
			mad.setCarTypeString("厢式货车");
			break;
		case 1:
			mad.setCarTypeString("敞车");
			break;
		case 2:
			mad.setCarTypeString("冷藏车");
			break;
		case 3:
			mad.setCarTypeString("保温车");
			break;
		case 4:
			mad.setCarTypeString("其他");
			break;
		case 5:
			mad.setCarTypeString("高栏车");
			break;
		case 6:
			mad.setCarTypeString("平板车");
			break;
		case 7:
			mad.setCarTypeString("活鲜水车");
			break;
		case 8:
			mad.setCarTypeString("集装箱");
			break;
		case 9:
			mad.setCarTypeString("不限");
			break;
		}
	}
	
	/**
	 * 设置同城车辆类型
	 * @param type
	 * @return
	 */
	private String getSameCityCarTypeString(Integer type){
		
		// 0小型面包,1金杯,2小型平板,3中型平板,4小型厢货,5大型厢货,6不限
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		typeMap.put(0, "小型面包");
		typeMap.put(1, "金杯");
		typeMap.put(2, "小型平板");
		typeMap.put(3, "中型平板");
		typeMap.put(4, "小型厢货");
		typeMap.put(5, "大型厢货");
		typeMap.put(6, "不限");
		
		return MapUtils.getString(typeMap, type, "不限");
	}	
	
	private void switchHundredweight(MemberAddressDTO mad) {
		/*
		 * 重量单位(0:吨,1公斤)  
		 */
		if(mad.getHundredweight()==null) return;
		
		switch(mad.getHundredweight()) {
		case 0:
			mad.setHundredweightString("吨");
			break;
		case 1:
			mad.setHundredweightString("斤");
			break;
		}
	}
	
	private void switchGoodsType(MemberAddressDTO mad) {
		/*
		 * 货物类型  (0 普货,1 冷藏,2 生鲜水产,3 其他,4 重货,5 抛货,6 蔬菜,7 水果,8 农副产品,9 日用品,10纺织, 11 木材)
		 */
		if(mad.getGoodsType()==null) return;
		
		switch(mad.getGoodsType()) {
		case 0:
			mad.setGoodsTypeString("普货");
			break;
		case 1:
			mad.setGoodsTypeString("冷藏");
			break;
		case 2:
			mad.setGoodsTypeString("生鲜水产");
			break;
		case 3:
			mad.setGoodsTypeString("其他");
			break;
		case 4:
			mad.setGoodsTypeString("重货");
			break;
		case 5:
			mad.setGoodsTypeString("抛货");
			break;
		case 6:
			mad.setGoodsTypeString("蔬菜");
			break;
		case 7:
			mad.setGoodsTypeString("水果");
			break;
		case 8:
			mad.setGoodsTypeString("农副产品");
			break;
		case 9:
			mad.setGoodsTypeString("日用品");
			break;
		case 10:
			mad.setGoodsTypeString("纺织");
			break;
		case 11:
			mad.setGoodsTypeString("木材");
			break;
		}
	}

	@Override
	public MemberAddressDTO getByIdForOrder(String id, Integer type, Integer source) throws Exception {
		
		MemberAddressDTO mad = getHessianService().getByIdForOrder(id, type, source);
		
		AreaDTO area = getHessianAreaService().getArea(String.valueOf(mad.getS_provinceId()));
		if(area!=null)
			mad.setS_provinceName(area.getArea());
		area = getHessianAreaService().getArea(String.valueOf(mad.getS_cityId()));
		if(area!=null)
			mad.setS_cityName(area.getArea());
		area = getHessianAreaService().getArea(String.valueOf(mad.getS_areaId()));
		if(area!=null)
			mad.setS_areaName(area.getArea());
		
		area = getHessianAreaService().getArea(String.valueOf(mad.getF_provinceId()));
		if(area!=null)
			mad.setF_provinceName(area.getArea());
		area = getHessianAreaService().getArea(String.valueOf(mad.getF_cityId()));
		if(area!=null)
			mad.setF_cityName(area.getArea());
		area = getHessianAreaService().getArea(String.valueOf(mad.getF_areaId()));
		if(area!=null)
			mad.setF_areaName(area.getArea());
		
		switchCarType(mad);
		//意向价格
		// showPrice(mad);
		//发车时间
		// showSendDate(mad);
		//货物分类
		switchGoodsType(mad);
		
		//修饰发布时间
		mad.setTimebefore(DateTimeUtils.formatDate(mad.getCreateTime(),"MM-dd"));
		
		switchHundredweight(mad);
		
		switchGoodsType(mad);
		return mad;
	}
	
	private void showPrice(MemberAddressDTO mad) {
		
		if(mad.getPrice()==null)
			return;
		
		if(mad.getPrice()==0) {
			mad.setPriceString("面议");
		} else {
			switch(mad.getPriceUnitType()) {
			case 0:
				mad.setPriceUnitTypeString("元/吨");
				break;
			case 1:
				mad.setPriceUnitTypeString("元/公斤");
				break;
			case 2:
				mad.setPriceUnitTypeString("2");
				break;
			default:
				mad.setPriceUnitTypeString("");
				break;
			}
			mad.setPriceString(mad.getPriceString()+mad.getPriceUnitTypeString());
		}
		
	}
	
	/**
	 * 0:上午,1:中午,2:下午,3:晚上,4:不限
	 * @param mad
	 */
	private void showSendDate(MemberAddressDTO mad) {
		if(mad.getSendDate()==null)
			return;
		switch(mad.getSendDateType()) {
		case 0:
			mad.setSendDateTypeString("上午");
			break;
		case 1:
			mad.setSendDateTypeString("中午");
			break;
		case 2:
			mad.setSendDateTypeString("下午");
			break;
		case 3:
			mad.setSendDateTypeString("晚上");
			break;
		case 4:
			mad.setSendDateTypeString("不限");
			break;
		}
		mad.setSendDateString(showDate(mad.getSendDate()));
	}

	private String showDate(Date date) {
		if(date==null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	@Override
	public Integer updateMemberAdressStatusByid(String memberAdressIds)
			throws Exception {
		return getHessianService().updateMemberAdressStatusByid(memberAdressIds);
	}





}
