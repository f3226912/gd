package com.gudeng.commerce.gd.customer.bo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.commerce.gd.customer.dto.DataCacheQuery;
import com.gudeng.commerce.gd.customer.dto.TimeCacheType;
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
	
	/*********************************地区start*****************************/
	/**一级地区*/
	public static final String GET_AREA_TOP_ALL="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.listTopArea(..))";
	/**地区*/
	public static final String GET_AREA_AREAID="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getAreaById(..))";
	/**下级地区*/
	public static final String GET_AREA_CHILD_AREAID="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.listChildArea(..))";
	/**下级地区树*/
	public static final String GET_AREA_TREE_AREAID="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getAreaChildTree(..))";
	/**获取所有省份城市*/
	public static final String GET_AREA_ALL_PROVINCE_CITY="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getAllProvinceCity(..))";
	
	/**删除地区***/
	public static final String DELETE_AREA_AREAID="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.deleteAreaCacheById(..))";
	

	/*********************************地区end*****************************/
	
	/*********************************会员start*****************************/
	/**会员id*/
	public static final String GET_MEMBER_ID="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getMemberById(..))";
	/**会员手机号*/
	public static final String GET_MEMBER_MOBILE="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getMemberByMobile(..))";
	/**会员账号*/
	public static final String GET_MEMBER_ACCOUNT="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getMemberByAccount(..))";
	/***删除会员*/
	public static final String DELETE_MEMBER_ID="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.deleteMemberCacheById(..))";
	
	/*********************************会员start*****************************/
	
	/*********************************特殊字符start*****************************/
	public static final String GET_CHARACTER_LIST="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getCharacterList(..))";

	/*********************************特殊字符end*****************************/

	/*********************************获取商铺交易动态**************************/
	public static final String GET_TRADING_DYNAMICS="execution(* com.gudeng.commerce.gd.customer.bo.CacheBo.getTradingDynamics(..))";
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	/**---------------查询缓存start----------------*/
	
	/*********************************地区start*****************************/
	//地区
	@Around(GET_AREA_TOP_ALL)
	public Object areaTopAllAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.AREA_TOP_ALL.value,"");
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	//所有省份城市
	@Around(GET_AREA_ALL_PROVINCE_CITY)
	public Object AREA_ALL_PROVINCE_CITYAround(ProceedingJoinPoint joinPoint) {
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.AREA_ALL_PROVINCE_CITY.value,"");
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	//地区
	@Around(GET_AREA_AREAID)
	public Object areaAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.AREA_AREAID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	//下级地区
	@Around(GET_AREA_TREE_AREAID)
	public Object areaTreeAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.AREA_TREE_AREAID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	//下级地区树
	@Around(GET_AREA_CHILD_AREAID)
	public Object areaChildAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.AREA_CHILD_AREAID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	//删除地区
	@Around(DELETE_AREA_AREAID)
	public void areaDeleteAround(ProceedingJoinPoint joinPoint) {
		
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.AREA_TOP_ALL.value,"");
			String key2 = getRedisKey(RedisConstant.RedisKey.AREA_AREAID.value,args[0].toString());
			String key3 = getRedisKey(RedisConstant.RedisKey.AREA_TREE_AREAID.value,args[0].toString());
			String key4 = getRedisKey(RedisConstant.RedisKey.AREA_CHILD_AREAID.value,args[0].toString());
			//清掉缓存
			delRedis(key);
			delRedis(key2);
			delRedis(key3);
			delRedis(key4);
		}
	}
	
	/*********************************地区end*****************************/
	
	/*********************************会员Start*****************************/
	@Around(GET_MEMBER_ID)
	public Object memberIdAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.MEMBER_ID.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(GET_MEMBER_MOBILE)
	public Object memberMobileAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.MEMBER_MOBILE.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	@Around(GET_MEMBER_ACCOUNT)
	public Object memberAccountAround(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.MEMBER_ACCOUNT.value,args[0].toString());
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	
	//删除地区
	@Around(DELETE_MEMBER_ID)
	public void memberDeleteAround(ProceedingJoinPoint joinPoint) {
		
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.MEMBER_ID.value,args[0].toString());
			String key2 = getRedisKey(RedisConstant.RedisKey.MEMBER_MOBILE.value,args[0].toString());
			String key3 = getRedisKey(RedisConstant.RedisKey.MEMBER_ACCOUNT.value,args[0].toString());
			//清掉缓存
			delRedis(key);
			delRedis(key2);
			delRedis(key3);
		}
	}
	/*********************************会员end*****************************/
	
	
	/*********************************会员end*****************************/
	@Around(GET_CHARACTER_LIST)
	public Object getCharacterList(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			String key = getRedisKey(RedisConstant.RedisKey.CHARACTER_LIST.value,"");
			obj=readWriteRedis(obj,key,joinPoint);
		}
		return obj;
	}
	/*********************************获取商铺交易动态*****************************/
	
	
	@Around(GET_TRADING_DYNAMICS)
	public Object getTradingDynamics(ProceedingJoinPoint joinPoint) {
		
		Object obj=null;
		Object[] args = joinPoint.getArgs();
		if(args.length>0){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String id=sdf.format(new Date());
			String key = getRedisKey(RedisConstant.RedisKey.BUSINESS_TRADING_DYNAMICS.value,id);
			//delRedis(key);
			obj=readWriteRedisExpirt(obj,key,joinPoint);
		}
		return obj;
	}
	/*********************************获取商铺交易动态end*****************************/
	
	
	
	
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
	
	
	//读写redis缓存 设置有效规则
		private Object readWriteRedisExpirt(Object obj,String key,ProceedingJoinPoint joinPoint){
			
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
							//redisClient.set(key, (Serializable) obj);
							redisClient.setex(key, TimeCacheType.DAY_CACHE.getExpirt().intValue(), (Serializable) obj);
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
}
