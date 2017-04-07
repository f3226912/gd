package com.gudeng.commerce.gd.supplier.bo;

import java.io.Serializable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * aop拦截：缓存处理
 * @author wwj
 * 
 */
@Component
@Aspect
public class RedisAopUtil {
	
	/**----------------查询：缓存处理--------------------**/
	
	/*********************************产品分类start*****************************/
	/**单个产品分类*/
	public static final String GET_PRODUCT_CATEGORY_ID="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.getProductCategory(..))";
	/**市场id所有产品分类*/
	public static final String GET_PRODUCT_CATEGORY_ALL_MARKETID="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.listProductCategory(..))";
	/**根据id产品分类子集*/
	public static final String GET_PRODUCT_CATEGORY_CHILD="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.getChildProductCategory(..))";
	/**根据市场id产品分类子集*/
	public static final String GET_PRODUCT_CATEGORY_CHILD_MARKETID="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.getChildProductCategoryByMarketId(..))";
	/**根据id查询一级产品分类*/
	public static final String GET_PRODUCT_CATEGORY_TOP="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.listTopProductCategory(..))";
	/**根据id及市场id查询一级产品分类*/
	public static final String GET_PRODUCT_CATEGORY_TOP_MARKETID="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.listTopProductCategoryByMarketId(..))";
	/**删除分类***/
	public static final String DELETE_PRODUCT_CATEGROY_ID="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.deleteProductCategoryCacheById(..))";
	

	/*********************************产品分类end*****************************/
	
	/*********************************产品start*****************************/
	/**单个产品*/
	public static final String GET_PRODUCT_ID="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.getProductById(..))";
	/**删除产品***/
	public static final String DELETE_PRODUCT_ID="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.deleteProductCacheById(..))";
	
	/*********************************产品end*****************************/
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	/**---------------查询缓存start----------------*/
	
	/*********************************农批商供应商关联start*****************************/
	
	public static final String GET_CATEGORY_REF_NPS="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.getRefCateSupNpsByNpsCateId(..))";
	
	public static final String DEL_CATEGORY_REF_NPS="execution(* com.gudeng.commerce.gd.supplier.bo.CacheBo.deleteRefCateSupNpsByCateId(..))";
	
	
	/*********************************农批商供应商关联分类end*****************************/
	
	/*********************************产品start*****************************/
	
	@Around(GET_PRODUCT_CATEGORY_ID)
	public Object productCategoryAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_ID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(GET_PRODUCT_CATEGORY_ALL_MARKETID)
	public Object produtCategoryAllMarketAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_ALL_MARKETID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(GET_PRODUCT_CATEGORY_CHILD)
	public Object productCategoryChildAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_CHILD.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(GET_PRODUCT_CATEGORY_CHILD_MARKETID)
	public Object productCategoryChildMarketAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_CHILD_MARKETID.value,args[0].toString());
			
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(GET_PRODUCT_CATEGORY_TOP)
	public Object productCategoryTopAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_TOP.value,"");
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(GET_PRODUCT_CATEGORY_TOP_MARKETID)
	public Object productCategoryTopMarketAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_TOP_MARKETID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(DELETE_PRODUCT_CATEGROY_ID)
	public void areaDeleteAround(ProceedingJoinPoint joinPoint) {
		
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_ID.value,args[0].toString());
			String key2 = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_ALL_MARKETID.value,args[0].toString());
			String key3 = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_CHILD.value,args[0].toString());
			String key4 = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_CHILD_MARKETID.value,args[0].toString());
			String key5 = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_TOP.value,"");
			String key6 = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_TOP_MARKETID.value,args[0].toString());
			//清掉缓存
			delRedis(key);
			delRedis(key2);
			delRedis(key3);
			delRedis(key4);
			delRedis(key5);
			delRedis(key6);
			
			
		}
	}
	
	/*********************************产品分类end*****************************/
	
	/*********************************产品start*****************************/
	@Around(GET_PRODUCT_ID)
	public Object productAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_PRODUCTID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(DELETE_PRODUCT_ID)
	public void productDeleteAround(ProceedingJoinPoint joinPoint) {
		
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_PRODUCTID.value,args[0].toString());
			//清掉缓存
			delRedis(key);
		}
	}
	/*********************************产品end*****************************/
	
	
	
	/**---------------end----------------*/
	
	//读写redis缓存
	private Object readWriteRedis(Object obj,String key,ProceedingJoinPoint joinPoint){
		
		try{
			obj = redisClient.get(key);
		}catch(Exception e){
			//处理redis服务器异常
			e.printStackTrace();
			obj= null;
		}
		if(obj != null){
			return obj;
		}else{
			try{
				obj = joinPoint.proceed(joinPoint.getArgs());
				if(null != obj){
					try{
						redisClient.set(key, (Serializable) obj);
						//System.out.println("写入key"+key);
					}catch(Exception e){
						//处理redis服务器异常
						e.printStackTrace();
					}
				}
			} catch (Throwable e) {
				//处理hessian异常
				e.printStackTrace();
				obj = null;
			}
		}
		return obj;
	}
	
	//清除缓存
	private void delRedis(String key){
		try{
			redisClient.del(key);
			//System.out.println("除件"+key);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//获取key
	private String getRedisKey(String preFix, String id){

		return preFix+id;
	}
	
	public IGDBinaryRedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(IGDBinaryRedisClient redisClient) {
		this.redisClient = redisClient;
	}
	
	/**
	 * 获取对应农批商的供应商关联数据
	 * @param joinPoint
	 * @return
	 */
	@Around(GET_CATEGORY_REF_NPS)
	public Object categoryNpsRefAround(ProceedingJoinPoint joinPoint) {
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_NPS_REF_SUPP.value,args[0].toString()+args[1].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(DEL_CATEGORY_REF_NPS)
	public void refCategoryDeleteAround(ProceedingJoinPoint joinPoint) {
		
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.PRODUCT_CATEGORY_NPS_REF_SUPP.value,args[0].toString()+args[1].toString());
			//清掉缓存
			delRedis(key);
		}
	}
}
