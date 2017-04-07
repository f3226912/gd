package com.gudeng.commerce.gd.admin.util;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.entity.PriceTypeEnum;
import com.gudeng.commerce.gd.admin.entity.ProductPictureTypeEnum;
import com.gudeng.commerce.gd.admin.entity.ProductStatusEnum;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.ProductService;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class SystemLogAopUtil {

	@Autowired
	public GdProperties gdProperties;
	
	private static ProductService productService;
	
	private static SystemLogService systemLogService;
	
	/**
	 * 功能描述:产品接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ProductService getHessianProductService()
			throws MalformedURLException {
		String url = gdProperties.getProductUrl();
		if (productService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productService = (ProductService) factory.create(
					ProductService.class, url);
		}
		return productService;
	}
	
	/**
	 * 功能描述:日志服务
	 * 
	 * @param
	 * @return
	 */
	protected SystemLogService getHessianSystemLogService()
			throws MalformedURLException {
		String url = gdProperties.getSystemLogUrl();
		if (systemLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemLogService = (SystemLogService) factory.create(
					SystemLogService.class, url);
		}
		return systemLogService;
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.updateProductStatus(..))")
	public void logProductStatusUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Map map = (Map) objects[0];
		try {
			String productId = map.get("productId").toString();
			ProductDto product = getHessianProductService().getById(productId);
			String content = "id为" + map.get("updateUserId") + "的系统用户在"+ map.get("updateTimeString") + "更新了(via updateProductStatus)id为"+ productId+ "的产品的状态为"+ map.get("state") + ", 产品信息如下: 产品名称-" 
					+ product.getProductName() + ", 库存量-" + product.getStockCount() + ", 有效期-"+product.getInfoLifeDay();
			
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(product.getCreateUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.batchUpdateProductStatus(..))")
	public void logProductStatusBatchUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		List<Map<String, Object>> params = (List<Map<String, Object>>) objects[0];
		try {
			String result = "[";
			String userId = "", updateTime = "", state = "";
			for (Map<String, Object> item : params){
				userId = String.valueOf(item.get("updateUserId"));
				updateTime = String.valueOf(item.get("updateTimeString"));
				state = String.valueOf(item.get("state"));
				result += "{" +  item.get("productId") + "}";
			}
			result += "]";
			String content = "id为" + userId + "的系统用户在" + updateTime + "批量更新了(via batchUpdateProductStatus)产品状态为 : "+ 
					ProductStatusEnum.getValue(state) + ", 被修改的产品如下: " + result ;
					
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(userId);
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.persistProductViaEntity(com.gudeng.commerce.gd.supplier.entity.ProductEntity))")
	public void logProductAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		ProductEntity product = (ProductEntity) objects[0];
		try {
			String content = "id为" + product.getCreateUserId() + "的系统用户新增了产品:" + product.getProductName() 
					+ ", 库存量 : " + product.getStockCount() + ", 有效期:"+product.getInfoLifeDay();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(product.getCreateUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.updateProduct(com.gudeng.commerce.gd.supplier.dto.ProductDto))")
	public void logProductEdit(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		ProductDto productDto = (ProductDto) objects[0];
		try {
			ProductDto product = getHessianProductService().getById(productDto.getProductId().toString());
			String content = "id为" + product.getUpdateUserId() + "的系统用户修改了id为"+ product.getProductId()+ "的产品, 修改后的信息如下: 产品名称-" 
					+ product.getProductName() + ", 库存量-" + product.getStockCount() + ", 有效期-"+product.getInfoLifeDay();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(product.getUpdateUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.updateProduct(java.util.Map))")
	public void logProductEdit2(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Map map = (Map) objects[0];
		try {
			String productId = String.valueOf(map.get("productId"));
			ProductDto product = getHessianProductService().getById(productId);
			
			String content = "id为" + product.getUpdateUserId() + "的系统用户修改了id为"+ product.getProductId()+ "的产品, 修改后的信息如下: 产品名称-" 
					+ product.getProductName() + ", 状态-" + product.getState() + ", 库存量-" + product.getStockCount()
					+ ", 有效期-"+product.getInfoLifeDay();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(product.getUpdateUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.deleteProduct(java.lang.String))")
	public void logProductDetele(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String productId = (String) objects[0];
			ProductDto product = getHessianProductService().getById(productId);
			
			String content = "id为" + getSysUserId() + "的系统用户删除了产品“:" + product.getProductName() 
					+ "”, 产品id为 : " + productId +", 价格类型 : " + PriceTypeEnum.getValue(product.getPriceType())+ 
					"如果价格类型为区间价格, 则会删除对应的区间价格";

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.batchDeletePrices(java.lang.String))")
	public void logProductPricesDelete(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String productId = (String) objects[0];
			ProductDto product = getHessianProductService().getById(productId);
			
			String content = "id为" + getSysUserId() + "的系统用户删除了产品:“" + product.getProductName() 
					+ "”的区间价格, 产品id为 : " + productId ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.deletePicByProductId(java.lang.String))")
	public void logProductPicDelete(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String productId = (String) objects[0];
			ProductDto product = getHessianProductService().getById(productId);
			
			String content = "id为" + getSysUserId() + "的系统用户删除了产品:“" + product.getProductName() 
					+ "”的所有图片, 产品id为 : " + productId ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.deletePicByProductIdAndType(java.lang.String, java.lang.String))")
	public void logProductPicDeleteByType(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String productId = (String) objects[0];
			String picType = (String) objects[1];
			ProductDto product = getHessianProductService().getById(productId);
			
			String content = "id为" + getSysUserId() + "的系统用户删除了产品“" + product.getProductName() 
					+ "”的类型为"+ ProductPictureTypeEnum.getValue(picType)+ "的图片, 产品id为 : " + productId ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.batchDeleteProduct(..))")
	public void logProductBatchDelete(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String[] productIds = (String[]) objects[0];
			
			String result = "[";
			for (String item : productIds){
				result += "{" + item + "}";
			}
			result += "]";
			
			String content = "id为" + getSysUserId() + "的系统用户批量删除了id为下列值的产品:" + result 
					+ "" ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.batchDeleteProductPic(..))")
	public void logProductPicBatchDelete(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			
			String[] pictureTypes = (String[]) objects[0];
			String result = "[";
			for (String item : pictureTypes){
				result += "{" + item + "}";
			}
			result += "]";
			String productId = (String) objects[1];
			String content = "id为" + getSysUserId() + "的系统用户批量删除了产品id为:[" + productId 
					+ "]同时产品图片类型为[" + result +"]的图片" ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.batchUpdatePrice(..))")
	public void logProductPriceBatchUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			@SuppressWarnings("unchecked")
			List<ProductPriceDto> priceDtoList = (List<ProductPriceDto>) objects[0];
			String productId = priceDtoList.get(0).getProductId().toString();
			String result = "[";
			for(ProductPriceDto item : priceDtoList){
				result += "{"+item.getBuyCountStart() + " -- "+  item.getBuyCountEnd() +"}";
			}
			result +="]";
			String content = "id为" + getSysUserId() +
					"的系统用户新增的价格区间:"+ result+"产品id为:" + productId ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.batchUpdateProduct(..))")
	public void logProductBatchUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			@SuppressWarnings("unchecked")
			List<ProductDto> productList = (List<ProductDto>) objects[0];
			String result = "[";
			for (ProductDto item : productList){
				result += "{ 产id : "+ item.getProductId() + ", 产品名称-" + item.getProductName() + ", 状态-" + item.getState() + ", 库存量-" + item.getStockCount()
					+ ", 有效期-"+item.getInfoLifeDay() + " }";
			}
			result += "]";
			String content = "id为" + getSysUserId() + "的系统用户批量修改了产品, 修改后的信息如下:"+ result  ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.batchUpdateProductState(..))")
	public void logProductStateBatchUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String[] productIds = (String[]) objects[0];
			String result = "[";
			for (String item : productIds){
				result += "{" + item + "}";
			}
			result += "]";
			String state = (String) objects[1];
			String content = "id为" + getSysUserId() + "的系统用户批量修改(via batchUpdateProductState)产品状态为:" + state 
					+ ", 被修改的产品的产品id如下:[" + result +"]" ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getSysUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.updateProductPic(..))")
	public void logProductPicUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			ProductPictureDto pictureDto = (ProductPictureDto) objects[0];
			String content = "id为" + pictureDto.getCreateUserId() + "的系统用户修改了产品id为:" + pictureDto.getProductId() 
					+ "的图片,  修改后的图片地址为"  + pictureDto.getUrlOrg() ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(pictureDto.getCreateUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.addProductPic(..))")
	public void logProductPicAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			ProductPictureDto pictureDto = (ProductPictureDto) objects[0];
			String content = "id为" + pictureDto.getCreateUserId() + "的系统用户批量新增了产品id为:" + pictureDto.getProductId() 
					+ "的图片,  图片地址为"  + pictureDto.getUrlOrg() ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(pictureDto.getCreateUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.addProductPicViaEntity(..))")
	public void logProductPicAdd2(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			ProductPictureEntity pictureEntity = (ProductPictureEntity) objects[0];
			String content = "id为" + pictureEntity.getCreateUserId() + "的系统用户批量新增了产品id为:" + pictureEntity.getProductId() 
					+ "的图片,  图片地址为"  + pictureEntity.getUrlOrg() ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(pictureEntity.getCreateUserId());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getSysUserId(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		SysRegisterUser user= (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
		return user.getUserID();
	}
	
}
