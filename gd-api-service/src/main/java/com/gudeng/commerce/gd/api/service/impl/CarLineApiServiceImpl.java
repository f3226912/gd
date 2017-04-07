package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.CarLineApiService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.ENeedTrunkCarType;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkCarLineDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.customer.service.CarLineService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class CarLineApiServiceImpl implements CarLineApiService {
	private static final GdLogger logger = GdLoggerFactory .getLogger(CarLineApiServiceImpl.class);
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	private static CarLineService carLineService;
	private static AreaService areaService;

	protected AreaService getHessianAreaService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.area.url");
		if (areaService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(AreaService.class, url);
		}
		return areaService;
	}
	
	private CarLineService hessianCategoryService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getCarLineUrl();
		if (carLineService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carLineService = (CarLineService) factory.create(
					CarLineService.class, hessianUrl);
		}
		return carLineService;
	}
	
	@Override
	public List<CarLineDTO> getCarlineApiByCondition(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCarlineApiByCondition(carLineDTO);
	}

	@Override
	public int addCarline(CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().addCarLineDTO(carLineDTO);
	}

	@Override
	public int delCarline(Long id) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().deleteById(Long.toString(id));
	}

	@Override
	public int repalyCarLine(CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().repalyCarLine(carLineDTO);
	}

	@Override
	public int updateCarLine(CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().updateCarLineDTO(carLineDTO);
	}

	@Override
	public int getCountByCondition(CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCountByCondition(carLineDTO);
	}

	@Override
	public List<CarLineDTO> getCarlineApiByConditionNew(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCarlineApiByConditionNew(carLineDTO);
	}

	@Override
	public List<CarLineDTO> getCarlineApiMessage(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCarlineApiMessage(carLineDTO);
	}

	@Override
	public void setMebApiMessage(CarLineDTO carLineDTO, List<MemberAddressDTO> list) throws Exception {
		// TODO Auto-generated method stub
		 hessianCategoryService().setMebApiMessage(carLineDTO,list);
	}

	@Override
	public Long getCarLineId(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return  hessianCategoryService().getCarLineId(memberId);
	}

	@Override
	public Integer updateCarLineByid(String carLineIds) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().updateCarLineByid(carLineIds);
	}

	@Override
	public CarLineDTO getCarLIneById(Long clId) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCarLIneById(clId);
	}

	@Override
	public List<CarLineDTO> getCarlineApiByConditionNewNst2(
			CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCarlineApiByConditionNewNst2(carLineDTO);
	}

	@Override
	public int getCountByConditionNst2(CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCountByConditionNst2(carLineDTO);
	}

	@Override
	public List<CarLineDTO> getCarlineApiByIdNst2(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCarlineApiByIdNst2(carLineDTO);
	}

	@Override
	public List<CarLineDTO> getCarlineApiByConditionUser(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().getCarlineApiByConditionUserId(carLineDTO);
	}

	@Override
	public Integer getCarLineCount(Map<String, Object> paramMap) throws Exception {
		return hessianCategoryService().getCarLineCount(paramMap);
	}

	@Override
	public List<TrunkCarLineDTO> getCarLineList(Map<String, Object> paramMap) throws Exception {
		List<TrunkCarLineDTO> list = hessianCategoryService().getCarLineList(paramMap);
		if(null == list || list.size() < 1){
			return list;
		}
		for (TrunkCarLineDTO dto : list) {
			handleDto(dto);
		}
		return list;
	};
	
	/**
	 * 分页结果集属性转换
	 * @param dto
	 * @throws Exception
	 */
	private void handleDto(TrunkCarLineDTO dto) throws Exception {
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		// 车辆类型
		if (dto.getCarType() == null) {
			dto.setCarTypeString("");
		}
		dto.setCarTypeString(ENeedTrunkCarType.getValueByCode(Integer.parseInt(dto.getCarType())));
		// 计算时间差
		if (dto.getCreateTime() != null) {
			dto.setTimeDiffString(DateTimeUtils.getTimeBetween(dto.getCreateTime()));
		}
		// 拼接图片地址
		if (StringUtils.isNotBlank(dto.getAndupurl())) {
			dto.setAndupurl(imageHost + dto.getAndupurl());
		}
		//上午中午下午转换
		if (dto.getSentDateType() == null) {
			dto.setSentDateType("");
		}
		switch(dto.getSentDateType()){
			case "0": dto.setSentDateType("上午") ;break;
			case "1": dto.setSentDateType("中午") ;break;
			case "2": dto.setSentDateType("下午") ;break;
			case "3": dto.setSentDateType("晚上") ;break;
			default : dto.setSentDateType("不限");
		}
		// 城市+区域
		trunckAddressHandle(dto);
		//发运方式
		if (dto.getSendGoodsType() == null) {
			dto.setSendGoodsType("");
		}
		switch(dto.getSendGoodsType()){
			case "0": dto.setSendGoodsType("零担") ;break;
			case "1": dto.setSendGoodsType("整车") ;break;
			default : dto.setSendGoodsType("不限");
		}
	}
	
	/**
	 * 发货地和收货地拼接
	 */
	private void trunckAddressHandle(TrunkCarLineDTO dto) throws Exception {
		dto.setS_cityStr(appendAddress(dto.getS_provinceId(),dto.getS_cityId(),dto.getS_areaId()));
		dto.setS_cityStr2(appendAddress(dto.getS_provinceId2(),dto.getS_cityId2(),dto.getS_areaId2()));
		dto.setS_cityStr3(appendAddress(dto.getS_provinceId3(),dto.getS_cityId3(),dto.getS_areaId3()));
		dto.setE_cityStr(appendAddress(dto.getE_provinceId(),dto.getE_cityId(),dto.getE_areaId()));
		dto.setE_cityStr2(appendAddress(dto.getE_provinceId2(),dto.getE_cityId2(),dto.getE_areaId2()));
		dto.setE_cityStr3(appendAddress(dto.getE_provinceId3(),dto.getE_cityId3(),dto.getE_areaId3()));
		dto.setE_cityStr4(appendAddress(dto.getE_provinceId4(),dto.getE_cityId4(),dto.getE_areaId4()));
		dto.setE_cityStr5(appendAddress(dto.getE_provinceId5(),dto.getE_cityId5(),dto.getE_areaId5()));
	}
	/**
	 * 发货地和收货地拼接
	 */
	private String appendAddress(Long proviceId, Long cityId, Long areaId) throws Exception {
		// 发货地
		StringBuffer sb = new StringBuffer();
		// 城市
		if (cityId != null && cityId!=0 && cityId!=1) {
			AreaDTO scity = getHessianAreaService().getArea(cityId+"");
			if (scity != null && proviceId!=0 && proviceId!=1) {
				if ("市辖区".equals(scity.getArea()) || "县".equals(scity.getArea()) || "市".equals(scity.getArea())) {
					scity=getHessianAreaService().getArea(proviceId+ "");
				}
				sb.append(scity.getArea());
			}else if( proviceId!=0 && proviceId!=1){
				scity=getHessianAreaService().getArea(proviceId + "");
				if(null != scity){
					sb.append(scity.getArea());
				}
			}
		}
		// 区域
		if (areaId != null &&  areaId!=0 && areaId!=1 ) {
			AreaDTO sarea = getHessianAreaService().getArea(areaId + "");
			if (sarea != null) {
				sb.append(sarea.getArea());
			}
		}
		return sb.toString();
	}
	
}
