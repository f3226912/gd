package com.gudeng.commerce.gd.api.thread;


import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.MapUtil;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 计算驾车里程线程
 * @author TerryZhang
 */
public class CountCarMileageThread implements Runnable {
	private static final GdLogger logger = GdLoggerFactory.getLogger(CountCarMileageThread.class);
	
	private MemberAddressService memberAddressService;
	
	private AreaToolService areaToolService;
	
	private MemberAddressInputDTO inputDTO;

	public CountCarMileageThread(MemberAddressInputDTO inputDTO, AreaToolService areaToolService,
			MemberAddressService memberAddressService){
		this.inputDTO = inputDTO;
		this.areaToolService = areaToolService;
		this.memberAddressService = memberAddressService;
	}
	
	@Override
	public void run() {
		Long memberAddressId = inputDTO.getMemberAddressId();
		if(memberAddressId == null){
			return;
		}
		
		if(memberAddressService == null){
			return;
		}
		
		if(areaToolService == null){
			return;
		}
		
		if(inputDTO == null){
			return;
		}
		
		//加驾车里程计算
		try {
			Long time1 = System.currentTimeMillis();
			String startCity="";
			String endCity="";
			Long s_provinceId = inputDTO.getS_provinceId();
			Long s_cityId = inputDTO.getS_cityId();
			Long f_provinceId = inputDTO.getF_provinceId();
			Long f_cityId = inputDTO.getF_cityId();
			if(s_provinceId.longValue() != 0 && s_provinceId.longValue() != -1){
				if(s_provinceId.longValue() == 110000 || s_provinceId.longValue() == 120000
						|| s_provinceId.longValue() == 310000 || s_provinceId.longValue() == 500000){
					startCity = areaToolService.getByAreaId(s_provinceId).getArea();	
				}else{
					if(s_cityId.longValue() != 0 && s_cityId.longValue() != -1){
						startCity = areaToolService.getByAreaId(s_cityId).getArea();
					}
				}
			}
			
			if(f_provinceId.longValue() != 0 && f_provinceId.longValue() != -1){
				if(f_provinceId.longValue() == 110000 || f_provinceId.longValue() == 120000
						|| f_provinceId.longValue() == 310000 || f_provinceId.longValue() == 500000){
						endCity = areaToolService.getByAreaId(f_provinceId).getArea();				
				}else{
					if(f_cityId.longValue()!=0 && f_cityId.longValue() != -1){
						endCity = areaToolService.getByAreaId(f_cityId).getArea();
					}
				}
			}
			
			MemberAddressDTO memberAddressDTO = memberAddressService.getById(memberAddressId+"");
			if(memberAddressDTO == null){
				return;
			}
			if(StringUtils.isNotBlank(startCity) && StringUtils.isNotBlank(endCity)){
				Long time3 = System.currentTimeMillis();
				String mileage;
				
					mileage = MapUtil.getDsitanceByArea(startCity,endCity);
				
				Long time4 = System.currentTimeMillis();
				logger.info("百度里程计算使用时间: " + (time4-time3));
				memberAddressDTO = memberAddressService.getById(memberAddressId+"");
				if(memberAddressDTO == null){
					return;
				}
				memberAddressDTO .setMileage(mileage);
			}else{
				memberAddressDTO.setMileage("-1");
			}
			
			if(memberAddressDTO.getSendDate() != null){
				memberAddressDTO.setSendDateString(DateUtil.getDate(memberAddressDTO.getSendDate(), DateUtil.DATE_FORMAT_DATEONLY));
			}
			memberAddressService.updateMemberAddressDTO(memberAddressDTO);
			Long time2 = System.currentTimeMillis();
			logger.info("加驾车里程计算使用时间: " + (time2-time1));
		} catch (Exception e) {
			logger.warn("[ERROR]驾车里程计算异常: memberAddressId: " + memberAddressId, e);
		}
	}
	
}
