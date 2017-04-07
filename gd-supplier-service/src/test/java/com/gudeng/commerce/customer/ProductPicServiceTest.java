package com.gudeng.commerce.customer;

import java.net.MalformedURLException;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.service.ProductPicService;

public class ProductPicServiceTest extends TestCase{

	private static ProductPicService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-supplier/service/productPicService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (ProductPicService) factory.create(ProductPicService.class, url);				
	}
	
/*	public void testAdd() throws Exception{
		ProductPicService service = this.getService();
		ProductPictureDto pictureDto = new ProductPictureDto();
		pictureDto = service.getPicture("1", "1");
		System.out.println(pictureDto.getId());
	}*/
	
	public void testUpdate() throws Exception{
		ProductPicService service = this.getService();
//		ProductPictureDto pictureDto = service.getPicture("62", "1");
		ProductPictureEntity entity = new ProductPictureEntity();
//		entity.setId(28L);
		entity.setUrl650("");
		entity.setUrlOrg("111.jpg");
		entity.setProductId(10001L);
		entity.setPictureType(5);
		service.addProductPicViaEntity(entity);
//		long id = service.updateProductPicViaEntity(entity);
//		long id = service.dynamicMergeProductPic(entity);
//		System.out.println(id);
	}
}
