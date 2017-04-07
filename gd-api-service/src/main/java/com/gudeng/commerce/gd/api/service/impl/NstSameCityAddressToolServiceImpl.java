package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NstSameCityAddressToolService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.EGoodsType;
import com.gudeng.commerce.gd.api.util.ENeedCarType;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;
import com.gudeng.commerce.gd.customer.service.NstSameCityAddressService;

public class NstSameCityAddressToolServiceImpl implements NstSameCityAddressToolService {

	@Autowired
	private GdProperties gdProperties;
	// 同城服务
	private static NstSameCityAddressService nstSameCityAddressService;
	// 订单服务
	private static NstOrderBaseinfoService nstOrderBaseinfoService;

	private NstSameCityAddressService getHessianNstSameCityAddressService() throws MalformedURLException {
		String hessianUrl = gdProperties.getNstSameCityAddressUrl();
		if (nstSameCityAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstSameCityAddressService = (NstSameCityAddressService) factory.create(NstSameCityAddressService.class,
					hessianUrl);
		}
		return nstSameCityAddressService;
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
	public Long insert(NstSameCityAddressEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstSameCityAddressService().insert(entity);
	}

	@Override
	public boolean assign(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstSameCityAddressService().assign(map);
	}

	@Override
	public List<NstSameCityAddressDTO> getNstSameCityAddressListByPage(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List<NstSameCityAddressDTO> listByPage = getHessianNstSameCityAddressService()
				.getNstSameCityAddressListByPage(map);
		if (listByPage != null && listByPage.size() != 0) {
			for (NstSameCityAddressDTO dto : listByPage) {
				canMakeOrder(dto,map);
				handleDto(dto);
				assignNameAndMobile(dto);
				concatImageUrl(dto);
			}
		}
		return listByPage;
	}
	
	
	@Override
	public List<NstSameCityAddressDTO> matchGoodsByCarline(NstSameCityCarlineEntityDTO nscadto) throws Exception {
		// TODO Auto-generated method stub
		List<NstSameCityAddressDTO> listByPage = getHessianNstSameCityAddressService().matchGoodsByCarline(nscadto);
		Map<String, Object> map=new HashMap<>();
		map.put("currentMemberId", nscadto.getCurrentMemberId());
		// 时间差的显示
		if (listByPage != null && listByPage.size() != 0) {
			for (NstSameCityAddressDTO dto : listByPage) {
				canMakeOrder(dto,map);
				handleDto(dto);
				assignNameAndMobile(dto);
			}
		}
		return listByPage;
	}

	@Override
	public List<NstSameCityAddressDTO> getMemberNSCAList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List<NstSameCityAddressDTO> list = getHessianNstSameCityAddressService().getMemberNSCAList(map);
		if (list != null && list.size() >= 0) {
			for (NstSameCityAddressDTO dto : list) {
				handleDto(dto);
			}
		}
		return list;
	}
	/**
	 * 处理前台接单按钮的置灰状况
	 * @param dto
	 * @param map
	 */
	private void canMakeOrder(NstSameCityAddressDTO dto,Map<String, Object> map){
		//当canMakeOrder为1时表示接单置灰，为0时表示可以接单
		if (map.get("currentMemberId")==null) {
			return;
		}
		Long memberId=Long.parseLong(map.get("currentMemberId")+"");
		if (memberId.equals(dto.getMemberId())
				|| (dto.getAssignMemberId() != null && memberId.equals(dto.getAssignMemberId()))) {
			dto.setCanMakeOrder("1");
		}else {
			dto.setCanMakeOrder("0");
		}
	}
	/**
	 * 处理货源被分配后 在 找货和一键配货中 显示的是分配给信息部的姓名和电话 @param dto @throws Exception @throws
	 */
	private void assignNameAndMobile(NstSameCityAddressDTO dto) throws Exception {
		if (dto != null && dto.getAssignMemberId() != null) {
			// 引用了农速通订单的服务 取得 姓名和电话
			NstOrderBaseinfoDTO nst = getHessianNstOrderBaseinfoService()
					.getMemberInfoByMemberId(dto.getAssignMemberId());
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
	}

	/**
	 * 处理返回的dto
	 * 
	 * @param dto
	 */
	private void handleDto(NstSameCityAddressDTO dto) {
		// 计算 时间差
		if (dto.getReleaseTime() != null) {
			dto.setTimeDiffString(DateTimeUtils.getTimeBetween(dto.getReleaseTime()));
		}
		// 将数据库日期 转换带时分秒
		if (dto.getUseCarTime() != null) {
			Date date = DateUtil.getDate(dto.getUseCarTime(), DateUtil.DATE_FORMAT_DATETIME);
			dto.setUseCarTimeString(DateUtil.getDate(date, DateUtil.DATE_FORMAT_DATETIME));
		}
		// 货物类型和用车类型String
		dto.setGoodsTypeString(EGoodsType.getValueByCode(Integer.parseInt(dto.getGoodsType() + "")));
		dto.setNeedCarTypeString(ENeedCarType.getValueByCode(Integer.parseInt(dto.getNeedCarType() + "")));

		// N 表示可以接单 Y 表示已接单
		if (dto.getOrderStatus() == null || dto.getOrderStatus() == 4 || dto.getOrderStatus() == 5) {
			dto.setShowGoodsStatus("N");
		} else {

			dto.setShowGoodsStatus("Y");
		}

		// 如果没有计算距离，则返回给app端，表示无法计算距离
		if (StringUtils.isEmpty(dto.getDistance())) {
			dto.setDistance("-1");
		}

		// 处理在接单的时候，这笔货源的最终订单 处理人为谁
		if (dto.getAssignMemberId() != null  && dto.getNstRule()!=null&&dto.getNstRule() == 1) {
			dto.setCreateUserId(dto.getAssignMemberId() + "");
		} else {
			dto.setCreateUserId(dto.getMemberId() + "");
		}
	}

	@Override
	public Integer getNstSameCityAddressListByPageCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstSameCityAddressService().getNstSameCityAddressListByPageCount(map);
	}

	@Override
	public Integer getMemberNSCAListCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstSameCityAddressService().getMemberNSCAListCount(map);
	}

	@Override
	public Integer updateMemberNSCA(NstSameCityAddressEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstSameCityAddressService().updateMemberNSCA(entity);
	}

	@Override
	public Integer deleteMemberNSCA(NstSameCityAddressEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNstSameCityAddressService().deleteMemberNSCA(entity);
	}

	/**
	 * 拼接图片URL
	 * 
	 * @param nstDto
	 */
	private void concatImageUrl(NstSameCityAddressDTO dto) {
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		if (StringUtils.isNotBlank(dto.getAndupurl())) {
			dto.setAndupurl(imageHost + dto.getAndupurl());
		}
	}
}
