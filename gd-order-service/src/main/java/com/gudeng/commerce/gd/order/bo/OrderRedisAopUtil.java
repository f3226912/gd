package com.gudeng.commerce.gd.order.bo;

import java.io.Serializable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * aop拦截：订单缓存处理
 * @author zab
 * 
 */
@Component
@Aspect
public class OrderRedisAopUtil {
	
	/**----------------查询：缓存处理--------------------**/
	
	/*********************************订单缓存 start*****************************/
	/**订单详情*/                                            //com.gudeng.commerce.gd.order.bo.OrderCacheBo.getByOrderNo
	public static final String GET_ORDER_DETAIL="execution(* com.gudeng.commerce.gd.order.bo.OrderCacheBo.getByOrderNo(..))";

	/**删除订单***/
	public static final String DELETE_ORDER_BY_ORDERNO="execution(* com.gudeng.commerce.gd.order.bo.OrderCacheBo.deleteByOrderNo(..))";
	/*********************************订单缓存 end*****************************/
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	/**---------------查询缓存start----------------*/
	
	@Around(GET_ORDER_DETAIL)
	public Object orderDetailAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(OrderRedisKeys.RedisKey.ORDER_DEAIL_ID.value, args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(DELETE_ORDER_BY_ORDERNO)
	public void orderDeleteAround(ProceedingJoinPoint joinPoint) {
		
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(OrderRedisKeys.RedisKey.ORDER_DEAIL_ID.value,args[0].toString());
			//清掉缓存
			delRedis(key);
		}
	}
	
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//获取key
	private String getRedisKey(String preFix, String id){

		return preFix + id;
	}
	
	public IGDBinaryRedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(IGDBinaryRedisClient redisClient) {
		this.redisClient = redisClient;
	}
}
