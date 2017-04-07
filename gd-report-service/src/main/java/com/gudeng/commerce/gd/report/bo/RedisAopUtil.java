package com.gudeng.commerce.gd.report.bo;

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
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	/**---------------查询缓存start----------------*/
	
	public static final String DATA_REDIS_GET="execution(* com.gudeng.commerce.gd.report.service.IDataCacheService.getDataCache(..))";
	
	public static final String DATA_REDIS_DELETE="execution(* com.gudeng.commerce.gd.report.service.IDataCacheService.delDataCache(..))";
	
	
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
	
	public IGDBinaryRedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(IGDBinaryRedisClient redisClient) {
		this.redisClient = redisClient;
	}
	

	/*********************************所有统计缓存统一处理start*****************************/
	@Around(DATA_REDIS_GET)
	public Object getDataRedisCache(ProceedingJoinPoint joinPoint) {
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = args[0].toString();
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(DATA_REDIS_DELETE)
	public void delDataRedisCache(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = args[0].toString();
			delRedis(key);
		}
	}

	/*********************************所有统计缓存统一处理end*****************************/
}
