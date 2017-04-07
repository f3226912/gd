package com.gudeng.commerce.gd.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.impl.ProductToolServiceImpl;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;

public class TraverseProductCallBackImpl2 implements TraverseProductCallBack{

	private ProductToolService productToolService;
	
	public void setProductService(ProductToolService productToolService) {
		this.productToolService = productToolService;
	}
	
	/**
	 * 遍历的产品id集合
	 */
	private List<String> productList = new ArrayList<String>();
	
	@Override
	public void collect(ProductDto item) {
		//设置展示价格
		if (item.getPrice().doubleValue() == 0){
			item.setShowedPrice("面议");
		}else{
			item.setShowedPrice(String.valueOf(item.getPrice()));
		}
		productList.add(String.valueOf(item.getProductId()));
	}
	public List<String> getProductList() {
		return productList;
	}
	
	/**
	 * 添加审核信息(审核信息为数组形式返回,恒有两项, 第一项为手工添加的原因, 第二项为选择的原因)
	 * @param refusedList	被驳回的产品
	 */
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
		//查询驳回原因
		Map<String, AuditInfoDTO> auditInfos = productToolService.getAuditInfos(refusedProductIds, params);	
		if (CommonUtils.isEmpty(auditInfos)){
			return ;
		}
		ProductDto currentProduct = null;
		AuditInfoDTO currentInfo = null;
		//遍历
		for(Iterator<ProductDto> it = refusedList.iterator(); it.hasNext();){
			currentProduct = it.next();
			//设置不通过的产品的审核原因
			currentInfo = auditInfos.get(currentProduct.getProductId().toString());
			if ( currentInfo != null){
				List<Object> infos = new ArrayList<Object>();
				//手工填写的原因
				if (CommonUtils.isEmpty(currentInfo.getOtherReason())){
					infos.add("");
				}else{
					infos.add(currentInfo.getOtherReason());
				}
				//选择的原因
				if (CommonUtils.isEmpty(currentInfo.getReason())){
					infos.add("");
				}else{
					infos.add(currentInfo.getReason());
				}
				currentProduct.setAuditInfos(infos);
			}
		}
	}
	/**
	 * 给产品图片设置图片服务器前缀
	 * @param pictureDto	图片pojo
	 */
	@Override
	public void assignHost(ProductPictureDto pictureDto) {
		String host = ((ProductToolServiceImpl)productToolService).getGdProperties().getProperties().getProperty("gd.image.server");
		pictureDto.setImgHost(host);
	}
}
