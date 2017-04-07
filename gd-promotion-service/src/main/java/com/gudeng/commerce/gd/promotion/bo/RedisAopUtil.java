package com.gudeng.commerce.gd.promotion.bo;

import java.io.Serializable;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * aop拦截：缓存处理
 * 
 * @author wwj
 * 
 */
@Component
@Aspect
public class RedisAopUtil {
	@Autowired
	private IGDBinaryRedisClient redisClient;

	/** ---------------查询缓存start---------------- */
	public static final String GET_PROM_PROD_ID = "execution(* com.gudeng.commerce.gd.promotion.service.impl.PromActProdInfoServiceImpl.getPromProdInfo(..))";

	public static final String GET_PROM_ID = "execution(* com.gudeng.commerce.gd.promotion.service.impl.PromActProdInfoServiceImpl.getProdBaseinfo(..))";

	//public static final String DELETE_PROM_ID = "execution(* com.gudeng.commerce.gd.report.service.IDataCacheService.delDataCache(..))";
	
//	public static final String GET_ACT_INFO = "execution(* com.gudeng.commerce.gd.promotion.service.impl.GdOrderActivityBaseServiceImpl.getActivityInfo(..))";

//	public static final String DELETE_ACT_INFO = "execution(* com.gudeng.commerce.gd.promotion.service.impl.GdOrderActivityBaseServiceImpl.deleteCach(..))";

	// 读写redis缓存
	private Object readWriteRedis(Object obj, String key, ProceedingJoinPoint joinPoint) {

		try {
			obj = redisClient.get(key);
		} catch (Exception e) {
			// 处理redis服务器异常
			e.printStackTrace();
			obj = null;
		}
		if (obj != null) {
			return obj;
		} else {
			try {
				obj = joinPoint.proceed(joinPoint.getArgs());
				if (null != obj) {
					try {
						redisClient.set(key, (Serializable) obj);

						/*
						 * 设置redis有效性
						 */
						if (obj instanceof PromProdInfoDTO) {
							PromProdInfoDTO promProd = (PromProdInfoDTO) obj;

							redisClient.expire(key,
									new Long(promProd.getEndTime().getTime() - (new Date()).getTime()).intValue());
						}
						
						if (obj instanceof PromBaseinfoDTO) {
							PromBaseinfoDTO prom = (PromBaseinfoDTO) obj;

							redisClient.expire(key,
									new Long(prom.getEndTime().getTime() - (new Date()).getTime()).intValue());
						}
						
						// System.out.println("写入key"+key);
					} catch (Exception e) {
						// 处理redis服务器异常
						e.printStackTrace();
					}
				}
			} catch (Throwable e) {
				// 处理hessian异常
				e.printStackTrace();
				obj = null;
			}
		}
		return obj;
	}

	// 清除缓存
	private void delRedis(String key) {
		try {
			redisClient.del(key);
			// System.out.println("除件"+key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取key
	private String getRedisKey(String preFix, String id) {

		return preFix + id;
	}

	public IGDBinaryRedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(IGDBinaryRedisClient redisClient) {
		this.redisClient = redisClient;
	}

	/********************************* 活动缓存统一处理start *****************************/
	/**
	 * 获得商品活动信息
	 * 
	 * @param joinPoint
	 * @return
	 */
	@Around(GET_PROM_PROD_ID)
	public Object promProdAround(ProceedingJoinPoint joinPoint) {

		Object obj = null;
		Object[] args = joinPoint.getArgs();
		if (args.length > 0) {
			String key = getRedisKey(RedisConstant.RedisKey.PROM_PROD_ID.value, args[0].toString());
			obj = readWriteRedis(obj, key, joinPoint);
		}
		return obj;
	}

	/**
	 * 获得活动信息
	 * 
	 * @param joinPoint
	 * @return
	 */
	@Around(GET_PROM_ID)
	public Object promAround(ProceedingJoinPoint joinPoint) {

		Object obj = null;
		Object[] args = joinPoint.getArgs();
		if (args.length > 0) {
			String key = getRedisKey(RedisConstant.RedisKey.PROM_ID.value, args[0].toString());
			obj = readWriteRedis(obj, key, joinPoint);
		}
		return obj;
	}
	
	/**
	 * 获取所有活动信息
	 * @param joinPoint
	 * @return
	 */
//	@Around(GET_ACT_INFO)
//	public Object getActInfoAround(ProceedingJoinPoint joinPoint) {
//		System.out.println("=========== Getting act info started ===========");
//		Object obj = null;
//		Object[] args = joinPoint.getArgs();
//		if (args.length > 0) {
//			String key = getRedisKey(RedisConstant.RedisKey.GD_ACTIVITY.value, args[0].toString());
//			obj = readWriteRedis(obj, key, joinPoint);
//		}
//		System.out.println("=========== Getting act info ended ===========");
//		return obj;
//	}
	
	/**
	 * 清除所有活动信息
	 * @param joinPoint
	 */
//	@Around(DELETE_ACT_INFO)
//	public void delActInfoAround(ProceedingJoinPoint joinPoint) {
//		System.out.println("=========== Delete started ===========");
//		delRedis(RedisConstant.RedisKey.GD_ACTIVITY.value);
//		System.out.println("=========== Delete ended ===========");
//	}

	/**
	 * 删除活动信息 暂时选择全部清除活动缓存 由于获得活动关联商品的ID需要查询数据库
	 * 
	 * @param joinPoint
	 */
	//@Around(DELETE_PROM_ID)
	public void delPromDeleteAround(ProceedingJoinPoint joinPoint) {

		delRedis("PROM_*");

		/*
		 * Object[] args = joinPoint.getArgs(); if (args.length > 0) {
		 * 
		 * String key = getRedisKey(RedisConstant.RedisKey.PROM_ID.value,
		 * args[0].toString());
		 * 
		 * //获取缓存中的活动对象 PromBaseinfoDTO promBaseinfoDTO = redisClient.get(key);
		 * 
		 * // 清掉缓存 delRedis("PROM_*");
		 * 
		 * 
		 * 判断是否存在
		 * 
		 * if (promBaseinfoDTO != null) {
		 * 
		 * //删除活动关联的手续费 key =
		 * getRedisKey(RedisConstant.RedisKey.PROM_FEES_ID.value,
		 * promBaseinfoDTO.getActId().toString()); // 清掉缓存 delRedis(key);
		 * 
		 * //获取活动的市场 key =
		 * getRedisKey(RedisConstant.RedisKey.PROM_MARKET_ID.value,
		 * promBaseinfoDTO.getActId().toString()); // 清掉缓存 delRedis(key);
		 * 
		 * 
		 * 判断是否关联商品
		 * 
		 * if (promBaseinfoDTO.getProductId() != null) { key =
		 * getRedisKey(RedisConstant.RedisKey.PROM_PROD_ID.value,
		 * promBaseinfoDTO.getProductId().toString()); // 清掉缓存 delRedis(key);
		 * 
		 * } }
		 * 
		 * }
		 */
	}

	/********************************* 活动缓存统一处理end *****************************/
}
