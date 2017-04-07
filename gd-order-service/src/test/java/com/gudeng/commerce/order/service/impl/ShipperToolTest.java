package com.gudeng.commerce.order.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.ReWeighCarBusinessDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;

public class ShipperToolTest {
	
	public static com.gudeng.commerce.gd.order.service.ShipperService shipperService;
	protected com.gudeng.commerce.gd.order.service.ShipperService hessianOrderWeighLogService()
			throws MalformedURLException {
		if (shipperService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			shipperService = (com.gudeng.commerce.gd.order.service.ShipperService) factory
					.create(com.gudeng.commerce.gd.order.service.ShipperService.class, "http://127.0.0.1:8080/gd-order/service/ShipperService.hs");
		}
		return shipperService;
	}
	//添加货主入磅基本信息
	@Test
	  public void testSaveWeightLog() throws MalformedURLException {
		WeighCarDTO weighCarDTO =new WeighCarDTO();
		weighCarDTO.setMemberId(new Long(252));
		weighCarDTO.setCarId(new Long(10));
		weighCarDTO.setType("1");
		weighCarDTO.setCreateUserId("252");
		weighCarDTO.setTareCreateTime(new Date());
		weighCarDTO.setTotalWeight(new Double(100));
		weighCarDTO.setStatus("1");
		weighCarDTO.setTapWeight("1");
		weighCarDTO.setTotalUnit("t");
		weighCarDTO.setTotalMemberId(new Long(252));
		weighCarDTO.setTotalCreateTime(new Date());
		weighCarDTO.setPlace("深圳");
		weighCarDTO.setQuality("1");
		weighCarDTO.setAllWeigh("1");
		weighCarDTO.setOthers("其他");
		weighCarDTO.setMarketId(new Long(1));
		weighCarDTO.setCarNumberImage("img1.jpg|img2.jpg|img3.jpg|img4.jpg|");
		Long res =hessianOrderWeighLogService().saveWeightLog(weighCarDTO);
		if(res==1){
			System.out.println("success");
		}else{
			System.out.println("fail");
		}
		
	}
	//添加产品
	@Test
	  public void testAddProduct() throws MalformedURLException {
		PreWeighCarDetailDTO preWeighCarDetail = new PreWeighCarDetailDTO();
		preWeighCarDetail.setWeighCarId(new Long(288));
		preWeighCarDetail.setMemberId(new Long(252));
		preWeighCarDetail.setCreateUserId("252");
		preWeighCarDetail.setCateId(new Long(54));
		preWeighCarDetail.setProductName("河蟹");
		preWeighCarDetail.setUnit("t");
		preWeighCarDetail.setWeigh(new Double(20));
		hessianOrderWeighLogService().addProduct(preWeighCarDetail);
		
	}
	//更新产品
	@Test
	  public void testUpdateProduct() throws MalformedURLException {
		PreWeighCarDetailDTO preWeighCarDetail = new PreWeighCarDetailDTO();
		preWeighCarDetail.setWeighCarId(new Long(288));
		preWeighCarDetail.setMemberId(new Long(252));
		preWeighCarDetail.setCreateUserId("252");
		preWeighCarDetail.setCateId(new Long(54));
		preWeighCarDetail.setProductName("河蟹");
		//preWeighCarDetail.setUnit("t");
		preWeighCarDetail.setWeigh(new Double(25));
		preWeighCarDetail.setPwdId(new Long(13));
		hessianOrderWeighLogService().updateProduct(preWeighCarDetail);
		
	}
	
	//删除产品
		@Test
		  public void testDelProduct() throws MalformedURLException {
			PreWeighCarDetailDTO preWeighCarDetail = new PreWeighCarDetailDTO();
			preWeighCarDetail.setPwdId(new Long(13));
			hessianOrderWeighLogService().delProduct(preWeighCarDetail);
		}
		//查询
		@Test
		  public void testGetProductList() throws MalformedURLException {
			PreWeighCarDetailDTO preWeighCarDetail = new PreWeighCarDetailDTO();
			preWeighCarDetail.setWeighCarId(new Long(288));
			List<PreWeighCarDetailDTO> preWeighCarDetailList=hessianOrderWeighLogService().getProductlist(preWeighCarDetail);
			for (PreWeighCarDetailDTO iterable_element : preWeighCarDetailList) {
				System.out.println("------------------------"+iterable_element);
			}
			
		}
		
		//提交
		@Test
		  public void testSubmitWeightLog() throws MalformedURLException {
			ReWeighCarBusinessDTO preWeighCarDetail = new ReWeighCarBusinessDTO();
			//preWeighCarDetail.setBusinessIds("1,2,3,");//商铺id多个用，隔开
			preWeighCarDetail.setCategoryId(new Long("12"));//类目id
			preWeighCarDetail.setWeighCarId(new Long(289));//过磅记录id
			preWeighCarDetail.setCreateUserId("252");
			hessianOrderWeighLogService().submit(preWeighCarDetail);
		}
		
	
}
