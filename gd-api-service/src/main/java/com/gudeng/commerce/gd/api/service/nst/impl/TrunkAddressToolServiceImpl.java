package com.gudeng.commerce.gd.api.service.nst.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.JDKDSASigner.stdDSA;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.nst.TrunkAddressToolService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.EGoodsType;
import com.gudeng.commerce.gd.api.util.ENeedTrunkCarType;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkAddressDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;
import com.gudeng.commerce.gd.customer.service.TrunkAddressService;

import net.sf.ehcache.search.expression.And;

public class TrunkAddressToolServiceImpl implements TrunkAddressToolService {
	@Autowired
	private GdProperties gdProperties;

	private static TrunkAddressService trunkAddressService;

	private static NstOrderBaseinfoService nstOrderBaseinfoService;

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

	private TrunkAddressService getHessianTrunkAddressService() throws MalformedURLException {
		String hessianUrl = gdProperties.getTrunkAddressServiceUrl();
		if (trunkAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			trunkAddressService = (TrunkAddressService) factory.create(TrunkAddressService.class, hessianUrl);
		}
		return trunkAddressService;
	}

	private NstOrderBaseinfoService getHessianNstOrderBaseinfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getNstOrderBaseinfoUrl();
		if (nstOrderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderBaseinfoService = (NstOrderBaseinfoService) factory.create(NstOrderBaseinfoService.class,
					hessianUrl);
		}
		return nstOrderBaseinfoService;
	}

	@Override
	public List<TrunkAddressDTO> getTrunkAddressList(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		List<TrunkAddressDTO> list = getHessianTrunkAddressService().getTrunkAddressList(paramMap);
		if (list != null && list.size() > 0) {
			for (TrunkAddressDTO dto : list) {
				handleDto(dto);
			}
		}
		return list;
	}

	@Override
	public Integer getTrunkAddressListCount(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getHessianTrunkAddressService().getTrunkAddressListCount(paramMap);
	}
	
	@Override
	public List<TrunkAddressDTO> getTrunkAddressListByMemberId(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		List<TrunkAddressDTO> list = getHessianTrunkAddressService().getTrunkAddressListByMemberId(paramMap);
		if (list != null && list.size() > 0) {
			for (TrunkAddressDTO dto : list) {
				handleMemberDto(dto);
			}
		}
		return list;
	}

	@Override
	public Integer getTrunkAddressListCountByMemberId(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getHessianTrunkAddressService().getTrunkAddressListCountByMemberId(paramMap);
	}
	
	/**
	 * 针对我发的货处理字段
	 * @param dto
	 */
	private void handleMemberDto(TrunkAddressDTO dto) throws Exception{
		// 货物类型
		if (dto.getGoodsType() != null) {
			dto.setGoodsTypeString(EGoodsType.getValueByCode(dto.getGoodsType()));
		}
		// 车辆类型
		if (dto.getCarType() != null) {
			dto.setCarTypeString(ENeedTrunkCarType.getValueByCode(dto.getCarType()));
		}
		if (dto.getSendGoodsType()!=null) {
			//发运方式
			switch (dto.getSendGoodsType()) {
			case 0:
				dto.setSendGoodsTypeString("零担");
				break;
			case 1:
				dto.setSendGoodsTypeString("整车");
			default:
				dto.setSendGoodsTypeString("不限");
				break;
			}
		}
		if (dto.getSendDateType()!=null) {
			//发车时间类别
			switch (dto.getSendDateType()) {
			case 0:
				dto.setSendDateTypeString("上午");
				break;
			case 1:
				dto.setSendDateTypeString("中午");
				break;
			case 2:
				dto.setSendDateTypeString("下午");
				break;
			case 3:
				dto.setSendDateTypeString("晚上");
				break;
			default:
				dto.setSendDateTypeString("不限");
				break;
			}
		}
		// 城市+区域
		trunckAddressHandle(dto);
	}
	
	/**
	 * 处理dto
	 * 
	 * @param dto
	 */
	private void handleDto(TrunkAddressDTO dto) throws Exception {
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		// 货物类型
		if (dto.getGoodsType() != null) {
			dto.setGoodsTypeString(EGoodsType.getValueByCode(dto.getGoodsType()));
		}
		// 车辆类型
		if (dto.getCarType() != null) {
			dto.setCarTypeString(ENeedTrunkCarType.getValueByCode(dto.getCarType()));
		}
		// 计算时间差
		if (dto.getCreateTime() != null) {
			dto.setTimeDiffString(DateTimeUtils.getTimeBetween(dto.getCreateTime()));
		}
		// 替换信息部
		if (dto != null && dto.getCreateUserId() != null && dto.getMemberId() != null
				&& dto.getCreateUserId().longValue() != dto.getMemberId().longValue()) {
			// 引用了农速通订单的服务 取得 姓名和电话
			NstOrderBaseinfoDTO nst = getHessianNstOrderBaseinfoService()
					.getMemberInfoByMemberId(dto.getCreateUserId());
			// 将货主的姓名和电话，替换为信息部的姓名和电话
			if (nst != null) {
				if (StringUtils.isNotEmpty(nst.getName())) {
					dto.setLinkMan(nst.getName());
				}
				if (StringUtils.isNotEmpty(nst.getMobile())) {
					dto.setMobile(nst.getMobile());
				}
				if (StringUtils.isNotEmpty(nst.getAndupurl())) {
					dto.setAndupurl(nst.getAndupurl());
				}
				if (StringUtils.isNotEmpty(nst.getIsCertify())) {
					dto.setIsCertify(nst.getIsCertify());
				}
			}
		}
		// 拼接图片地址
		if (StringUtils.isNotBlank(dto.getAndupurl())) {
			dto.setAndupurl(imageHost + dto.getAndupurl());
		}
		// 城市+区域
		trunckAddressHandle(dto);
	}

	/**
	 * 发货地和收货地拼接
	 */
	private void trunckAddressHandle(TrunkAddressDTO dto) throws Exception {
		// 发货地
		StringBuffer sb = new StringBuffer();
		// 城市
		if (dto.getS_cityId() != null) {
			AreaDTO scity = getHessianAreaService().getArea(dto.getS_cityId() + "");
			if (scity != null) {
				if ("市辖区".equals(scity.getArea()) || "县".equals(scity.getArea()) || "市".equals(scity.getArea())) {
					scity=getHessianAreaService().getArea(dto.getS_provinceId() + "");
				}
				sb.append(scity.getArea());
			}else {
				scity=getHessianAreaService().getArea(dto.getS_provinceId() + "");
				sb.append(scity.getArea());
			}
		}
		// 区域
		if (dto.getS_areaId() != null) {
			AreaDTO sarea = getHessianAreaService().getArea(dto.getS_areaId() + "");
			if (sarea != null) {
				sb.append(sarea.getArea());
			}
		}
		dto.setS_trunckAddress(sb.toString());

		// 收货地
		StringBuffer sb2 = new StringBuffer();
		// 城市
		if (dto.getF_cityId() != null) {
			AreaDTO fcity = getHessianAreaService().getArea(dto.getF_cityId() + "");
			if (fcity != null) {
				if ("市辖区".equals(fcity.getArea()) || "县".equals(fcity.getArea()) || "市".equals(fcity.getArea())) {
					fcity=getHessianAreaService().getArea(dto.getF_provinceId() + "");
				}
				sb2.append(fcity.getArea());
			}else {
				fcity=getHessianAreaService().getArea(dto.getF_provinceId() + "");
				sb2.append(fcity.getArea());
			}
		}
		// 区域
		if (dto.getF_areaId() != null) {
			AreaDTO farea = getHessianAreaService().getArea(dto.getF_areaId() + "");
			if (farea != null) {
				sb2.append(farea.getArea());
			}
		}
		dto.setF_trunckAddress(sb2.toString());
	}

}
