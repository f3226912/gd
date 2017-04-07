package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ProductToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.customer.service.ProductBaseinfoService;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;
import com.gudeng.commerce.gd.supplier.service.ProductPicService;
import com.gudeng.commerce.gd.supplier.service.ProductService;

/**
 * 功能描述：
 */
@Service
public class ProductToolServiceImpl implements ProductToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static ProductService productService;

	private static ProductPicService productPicService;

	public static ProductCategoryService productCategoryService;
	
	public static ProductBaseinfoService productBaseinfoService;

	protected ProductCategoryService getHessianProductCategoryService()
			throws MalformedURLException {
		String url = gdProperties.getProductCategoryUrl();
		if (productCategoryService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productCategoryService = (ProductCategoryService) factory.create(
					ProductCategoryService.class, url);
		}
		return productCategoryService;

	}

	protected ProductPicService getHessianProductPicService()
			throws MalformedURLException {
		String url = gdProperties.getProductPicUrl();
		if (productPicService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productPicService = (ProductPicService) factory.create(
					ProductPicService.class, url);
		}

		return productPicService;
	}

	public List<ProductPictureDto> getPictureByProductId(String productId)
			throws Exception {
		return getHessianProductPicService().getPictureByProductId(productId);
	}

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
	 * 功能描述:商品customer接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ProductBaseinfoService getHessianProductBaseinfoService()
			throws MalformedURLException {
		String url = gdProperties.getProductBaseinfoUrl();
		if (productBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productBaseinfoService = (ProductBaseinfoService) factory.create(
					ProductBaseinfoService.class, url);
		}
		return productBaseinfoService;
	}

	@Override
	public List<ProductDto> getProductsByIds(String[] productIds) throws Exception {
		return getHessianProductService().getProductsByIds(productIds);
	}
	
	@Override
	public ProductDto getByProductId(String productId) throws Exception {
		ProductDto productDto = getHessianProductService().getById(productId);
		List<ProductPriceDto> productPriceList=null;
		if ("1".equals(productDto.getPriceType())) {
			productPriceList = getHessianProductService().getProductPriceList(productId);
		}
		productDto.setPriceDtoList(productPriceList);
		return productDto;
	}

	@Override
	public ProductDto getOneProduct(Map<String, Object> params) throws Exception {
		return getHessianProductService().getOneProduct(params);
	}
	
	@Override
	public List<ProductPriceDto> getProductPriceList(String productId) throws Exception {
		return getHessianProductService().getProductPriceList(productId);
	}
	
	@Override
	public ProductDto getByProductName(String productName) throws Exception {
		return getHessianProductService().getByName(productName);
	}

	@Override
	public List<ProductDto> getProductList(Map<String, Object> map)
			throws Exception {
		return getHessianProductService().getProductList(map);
	}

	@Override
	public List<ProductDto> getProductListAll(Map<String, Object> map)
			throws Exception {
		return getHessianProductService().getProductListAll(map);
	}
	
	@Override
	public int getCount(Map<String, Object> map) throws Exception {
		return getHessianProductService().getCount(map);
	}

	@Override
	@Transactional
	public int deleteProduct(String productId) throws Exception {
		getHessianProductService().batchDeletePrices(productId);
		return getHessianProductService().deleteProduct(productId);
	}

/*	@Override
	public ProductDto addProduct(ProductDto productDto) throws Exception {
		return getHessianProductService().persistProduct(productDto);
	}*/

	@Override
	@Transactional
	public Long persistProductViaEntity(ProductEntity productEntity)
			throws Exception {
		
		long id = getHessianProductService().persistProductViaEntity(productEntity);
		
		if (productEntity.getPriceDtoList() != null 	&& productEntity.getPriceDtoList().size() > 0) {
			
			List<ProductPriceDto> priceDtoList = productEntity.getPriceDtoList();
			Iterator<ProductPriceDto> iterator =  priceDtoList.iterator();
			ProductPriceDto current = null;
			while(iterator.hasNext()){
				current = iterator.next();
				if (current != null ) {
					current.setCreateUserId(productEntity.getCreateUserId());
					current.setProductId(id);
				}
			}
			getHessianProductService().batchUpdatePrices(priceDtoList);
		}
		return id;
	}

	@Override
	public ProductPictureDto addProductPic(ProductPictureDto pictureDto)
			throws Exception {
		return getHessianProductPicService().addProductPic(pictureDto);
	}

	@Override
	public long addProductPicViaEntity(ProductPictureEntity pictureEntity)
			throws Exception {
		return getHessianProductPicService().addProductPicViaEntity(
				pictureEntity);
	}

	@Override
	public List<ProductCategoryDTO> getChildProductCategory(Long id)
			throws Exception {
		return getHessianProductCategoryService().getChildProductCategory(id);
	}

	@Override
	public List<ProductCategoryDTO> listTopProductCategory() throws Exception {
		return getHessianProductCategoryService().listTopProductCategory();
	}

	@Override
	public List<ProductDto> getList(Map<String, Object> map) throws Exception {
		return getHessianProductService().getList(map);
	}

/*	@Override
	public int auditProduct(Map<String, Object> map) throws Exception {
		return getHessianProductService().auditProduct(map);
	}*/

	@Override
	public List<ProductPriceDto> getLadderPriceByProductId(String productId)
			throws Exception {
		return getHessianProductService().getLadderPriceByProductId(productId);
	}

	@Override
	public List<ProductCategoryDTO> getCategoryAncestors(Long id) throws Exception {
		return getHessianProductCategoryService().getCategoryAncestors(id);
	}

	@Override
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(
			Long marketId) throws Exception {
		return getHessianProductCategoryService().listTopProductCategoryByMarketId(marketId);
	}


	@Override
	public int deletePicByProductId(String productId) throws Exception {
		return getHessianProductPicService().deletePicByProductId(productId);
	}

	@Override
	public int updateProduct(Map map) throws Exception {
		return getHessianProductService().updateProduct(map);
	}

	@Override
	public int updateProduct(ProductDto productDto) throws Exception {
		return getHessianProductService().updateProduct(productDto);
	}
	
	@Override
	public int updateProductStatus(Map map) throws Exception {
		return getHessianProductService().updateProductStatus(map);
	}
	
	@Override
	public int updateProductPic(ProductPictureDto pictureDto) throws Exception {
		return getHessianProductPicService().updateProductPic(pictureDto);
	}
	

	@Override
	public int deletePicByProductIdAndType(String productId, String pictureType)
			throws Exception {
		return getHessianProductPicService().deletePicByProductIdAndType(productId, pictureType);
	}

	@Override
	public int[] batchUpdateProduct(List<ProductDto> productList)
			throws Exception {
		return getHessianProductService().batchUpdateProduct(productList);
	}
	
	@Override
	public int[] batchUpdateProductState(String[] productIds, String state) throws Exception {
		return getHessianProductService().batchUpdateProductState(productIds, state);
	}
	
	@Override
	public int[] batchUpdateProductStatus(List<Map<String, Object>> params)
			throws Exception {
		return getHessianProductService().batchUpdateProductStatus(params);
	}
	@Override
	public int[] batchDeleteProduct(String[] productIds) throws Exception {
		return getHessianProductService().batchDeleteProduct(productIds);
	}

	@Override
	public int[] batchDeleteProductPic(String[] pictureTypes, String productId) throws Exception {
		return getHessianProductPicService().batchDeleteProductPic(pictureTypes, productId);
	}
	
	@Override
	public int batchDeletePrices(String productId) throws Exception {
		return getHessianProductService().batchDeletePrices(productId);
	}
	
	@Override
	public void batchUpdatePrice(List<ProductPriceDto> priceDtoList) throws Exception {
		getHessianProductService().batchUpdatePrices(priceDtoList);		
	}
	

	@Override
	public List<ProductDto> getAdProduct(Map<String, Object> param)
			throws Exception {
		return getHessianProductService().getAdProduct(param);
	}
	@Override
	public int getAdProductCount(Map<String, Object> param)
			throws Exception {
		return getHessianProductService().getAdProductCount(param);
	}

	@Override
	public int updateProductUpdateTime(Map<String, Object> map)
			throws Exception {
		return  getHessianProductService().updateProductUpdateTime(map);
	}

	@Override
	public PageQueryResultDTO<ProductBaseinfoDTO> getListPage(Map<String, Object> paramsMap) throws Exception {
		return  getHessianProductBaseinfoService().getListPage(paramsMap);
	}
	
	@Override
	public ProductCategoryDTO getCategoryById(Long id) throws Exception {
		return getHessianProductCategoryService().getProductCategory(id);
	}

	@Override
	public List<ProductDto> getFastProductList(Map<String, Object> map) throws Exception {
		return getHessianProductService().getFastProductList(map);
	}

	@Override
	public int getCountForFast(Map<String, Object> map) throws Exception {
		return getHessianProductService().getCountForFast(map);
	}

	@Override
	public List<ProductDto> getFastPListForALl(Map<String, Object> map) throws Exception {
		return getHessianProductService().getFastPListForALl(map);
	}

	@Override
	public List<ProductDto> getFastPListByIds(String[] productIds) throws Exception {
		return getHessianProductService().getFastPListByIds(productIds);
	}

}
