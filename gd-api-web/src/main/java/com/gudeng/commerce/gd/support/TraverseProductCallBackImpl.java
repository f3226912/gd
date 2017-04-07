package com.gudeng.commerce.gd.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.service.cdgys.OriPlaceVenProdService;
import com.gudeng.commerce.gd.api.service.impl.cdgys.OriPlaceVenProdServiceImpl;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;

public class TraverseProductCallBackImpl implements TraverseProductCallBack{

	private OriPlaceVenProdService oriPlaceVenProdService;
	
	public void setOriPlaceVenProdService(
			OriPlaceVenProdService oriPlaceVenProdService) {
		this.oriPlaceVenProdService = oriPlaceVenProdService;
	}
	
	private List<String> productList = new ArrayList<String>();
	
	@Override
	public void collect(ProductDto item) {
		if (item.getPrice().doubleValue() == 0){
			item.setShowedPrice("面议");
		}else{
			item.setShowedPrice(String.valueOf(item.getPrice()));
		}
		//商品详情去除<span></span>标签
		String des = item.getDescription();
		if(!CommonUtils.isEmpty(des)){
			int index = des.indexOf(">");
			if (index > -1){
				des = des.substring(index+1);
				des = des.replace("</span>", "");
				item.setDescription(des);
			}
		}
		productList.add(String.valueOf(item.getProductId()));
	}
	public List<String> getProductList() {
		return productList;
	}
	@Override
	public void appendAuditInfo(List<ProductDto> refusedList) throws Exception {
		if (CommonUtils.isEmpty(refusedList)){
			return ;
		}
		List<String> refusedProductIds = new ArrayList<String>();
		for(ProductDto item : refusedList){
			refusedProductIds.add(item.getProductId().toString());
		}
		//获取审核不通过的产品的审核信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "1");
		params.put("status", "0");
		Map<String, AuditInfoDTO> auditInfos = oriPlaceVenProdService.getAuditInfos(refusedProductIds, params);	
		if (CommonUtils.isEmpty(auditInfos)){
			return ;
		}
		ProductDto currentProduct = null;
		AuditInfoDTO currentInfo = null;
		for(Iterator<ProductDto> it = refusedList.iterator(); it.hasNext();){
			currentProduct = it.next();
			//设置不通过的产品的审核原因
			currentInfo = auditInfos.get(currentProduct.getProductId().toString());
			if ( currentInfo != null){
				List<Object> infos = new ArrayList<Object>();
				if (currentInfo.getOtherReason() == null){
					infos.add("");
				}else{
					infos.add(currentInfo.getOtherReason());
				}
				if (currentInfo.getReason() == null){
					infos.add("");
				}else{
					infos.add(currentInfo.getReason());
				}
				currentProduct.setAuditInfos(infos);
			}
		}
	}
	@Override
	public void assignHost(ProductPictureDto pictureDto) {
		String host = ((OriPlaceVenProdServiceImpl)oriPlaceVenProdService).getGdProperties().getProperties().getProperty("gd.image.server");
		pictureDto.setImgHost(host);
	}
}
