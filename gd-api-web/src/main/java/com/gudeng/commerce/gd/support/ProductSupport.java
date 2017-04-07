package com.gudeng.commerce.gd.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.enums.ProductPictureTypeEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.service.impl.cdgys.OriPlaceVenProdServiceImpl.Constants;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;

public class ProductSupport {

	/**
	 *
	 * @param list	入参list在数据库端已经按照更新时间排好序
	 * @return
	 */
	public static List<ProductCategoryDTO> sort(List<ProductCategoryDTO> list, TraverseProductCallBack callback, String option){
		ProductCategoryDTO current = null ;
		//遍历所有一级分类
		for(Iterator<ProductCategoryDTO> it = list.iterator(); it.hasNext();){
			current = it.next();
			//遍历当前一级分类
			current.setProductList(sortProducts(current.getProductList(), callback, option));
		}
		return list;
	}

	private static List<ProductDto> sortProducts(List<ProductDto> productList, TraverseProductCallBack callback, String option){

		ProductDto current = null ;
		//已下架页签(含已下架、新增状态), 需要按状态、时间排序; 其他页签不按状态排序
		if (Constants.PRODUCT_LIST_OFF.equals(option)){	//已下架页签
			List<ProductDto> draftList = new ArrayList<ProductDto>();
			List<ProductDto> offList = new ArrayList<ProductDto>();
			List<ProductDto> others = new ArrayList<ProductDto>();
			for(Iterator<ProductDto> it = productList.iterator(); it.hasNext();){
				current = it.next();
				if (ProductStatusEnum.DRAFT.getkey().equals(current.getState())){
					draftList.add(current);
				} else if (ProductStatusEnum.OFF.getkey().equals(current.getState())){
					offList.add(current);
				}else {
					others.add(current);
				}
				//设置展示价格, 收集产品id
				callback.collect(current);
				it.remove();
			}
			//清空原有元素
			productList.clear();
			//排序
			productList.addAll(draftList);
			productList.addAll(offList);
			productList.addAll(others);

		}else if (Constants.PRODUCT_LIST_NEEDCHECK.equals(option)){	//待审核页签(含被驳回、待审核状态)
			try {
				//存储被驳回的商品
				List<ProductDto> refusedList = new ArrayList<ProductDto>();
				for(Iterator<ProductDto> it = productList.iterator(); it.hasNext();){
					current = it.next();
					if  (ProductStatusEnum.REFUSE.getkey().equals(current.getState())){
						refusedList.add(current);
					}
					//设置展示价格, 收集产品id
					callback.collect(current);
				}
				//添加审核信息
				callback.appendAuditInfo(refusedList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (Constants.PRODUCT_LIST_All.equals(option)){	//全部
			try {
				//存储被驳回的商品
				List<ProductDto> refusedList = new ArrayList<ProductDto>();
				for(Iterator<ProductDto> it = productList.iterator(); it.hasNext();){
					current = it.next();
					if  (ProductStatusEnum.REFUSE.getkey().equals(current.getState())){
						refusedList.add(current);
					}
					//设置展示价格, 收集产品id
					callback.collect(current);
				}
				//添加审核信息
				callback.appendAuditInfo(refusedList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (Constants.PRODUCT_LIST_ONSALE.equals(option)){//出售中

			for(Iterator<ProductDto> it = productList.iterator(); it.hasNext();){
				current = it.next();
				//设置展示价格, 收集产品id
				callback.collect(current);
			}
		}else if (Constants.PRODUCT_LIST_MAKE_ORDER.equals(option)){//制单

			for(Iterator<ProductDto> it = productList.iterator(); it.hasNext();){
				current = it.next();
				//制单页排除库存为0的产品
				if (current.getStockCount().longValue() == 0){
					it.remove();
					continue ;
				}
				//设置展示价格, 收集产品id
				callback.collect(current);
			}
		}else if ("5".equals(option)){//出售中+已下架

			for(Iterator<ProductDto> it = productList.iterator(); it.hasNext();){
				current = it.next();
				//设置展示价格, 收集产品id
				callback.collect(current);
			}
		}

		return productList;
	}

	/**
	 * 将产品图片重新组织成以产品id为分组单元的图片集合
	 * @param list
	 * @param callback
	 * @return
	 */
	public static Map<String, List<ProductPictureDto>> separatePictures(List<ProductPictureDto> list, TraverseProductCallBack callback){
		Map<String, List<ProductPictureDto>> result = new HashMap<String, List<ProductPictureDto>>();
		if (CommonUtils.isEmpty(list)){
			return result;
		}
		ProductPictureDto current = null ;
		List<ProductPictureDto> plist = null ;
		//遍历产品图片
		for(Iterator<ProductPictureDto> it = list.iterator(); it.hasNext(); ){
			current = it.next() ;
			plist = result.get(String.valueOf(current.getProductId()));
			if ( plist == null){
				plist = new ArrayList<ProductPictureDto>();
				result.put(String.valueOf(current.getProductId()), plist);
			}
			//设置图片地址前缀
			callback.assignHost(current);
			plist.add(current);
		}
		List<ProductPictureDto> currentList = null;
		//遍历每个产品的图片
		for(String key : result.keySet()){
			//当前产品-图片排序(按照图片类型)
			currentList = sortPictures(result.get(key));
			result.put(key, currentList);
		}
		return result;
	}

	/**
	 * 对某个产品的图片进行排序
	 * @param list	入参为一个指定产品的所有图片
	 * @return
	 */
	public static List<ProductPictureDto> sortPictures(List<ProductPictureDto> list){
//		ProductPictureDto master = null;
		List<ProductPictureDto> multiList = new ArrayList<ProductPictureDto>();
		ProductPictureDto app = null;
		ProductPictureDto current = null;
		for(Iterator<ProductPictureDto> it = list.iterator(); it.hasNext();){
			current = it.next();
			if (ProductPictureTypeEnum.MAIN.getkey().equals(current.getPictureType().toString())){
//				master = current;
			} else if (ProductPictureTypeEnum.MUTIPLE.getkey().equals(current.getPictureType().toString())){
				multiList.add(current);
			} else if (ProductPictureTypeEnum.APP.getkey().equals(current.getPictureType().toString())){
				app = current ;
			}
			it.remove();
		}
		list.clear();
		list.add(app);

		for(Iterator<ProductPictureDto> it = multiList.iterator(); it.hasNext();){
			//多角度图第一张同时也是web主图, 并且有可能[app图、web主图、多角度图的第一张]这3张图指向同一个图片地址
			//当这三张图一致时,只留一张
			if ( it.next().getUrlOrg().trim().equalsIgnoreCase(app.getUrlOrg().trim()) ){
				it.remove();
			}
			break ;
		}
		list.addAll(multiList);

/*		//app图与web主图不一样
		if (!app.getUrlOrg().trim().equalsIgnoreCase(master.getUrlOrg().trim()) ){
			list.add(master);
		}else{
			//master equals app, do nothing
		}
		for(Iterator<ProductPictureDto> it = multiList.iterator(); it.hasNext();){
			if (it.next().getUrlOrg().trim().equalsIgnoreCase(master.getUrlOrg().trim()) ){
				it.remove();
				break ;
			}
		}*/

		return list;
	}

	/**
	 * 为产品一级分类下的产品设置产品图片
	 * @param list
	 * @param map
	 */
	public static void appendPictures(List<ProductCategoryDTO> list, Map<String, List<ProductPictureDto>> map){
		ProductCategoryDTO current = null ;
		Iterator<ProductDto> innerIterator = null ;
		ProductDto currentProduct = null ;
		//遍历产品一级分类
		for(Iterator<ProductCategoryDTO> it = list.iterator(); it.hasNext();){
			//当前产品分类
			current = it.next();
			if (!CommonUtils.isEmpty(current.getProductList())){
				//遍历当前分类下的产品, 设置图片
				innerIterator = current.getProductList().iterator();
				//遍历当前分类下的产品,以设置图片
				while(innerIterator.hasNext()){
					currentProduct = innerIterator.next();
					//设置图片
					currentProduct.setPictures(map.get(String.valueOf(currentProduct.getProductId())));
				}
			}
		}
	}
}
