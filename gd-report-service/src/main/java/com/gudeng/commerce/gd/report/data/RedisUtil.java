package com.gudeng.commerce.gd.report.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * aop拦截：缓存处理
 * @author wwj
 * 
 */
@Component
public class RedisUtil {
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	//读写redis缓存
	public DataDTO ReadWriteDataRedisCache(String key, ADataCacheService dataCacheService, DataCacheQuery dataCacheQuery, DataServiceQuery dataServiceQuery){
		DataDTO obj;
		try{
			obj = (DataDTO)redisClient.get(key);
		}catch(Exception e){
			//处理redis服务器异常
			e.printStackTrace();
			obj= null;
		}
		if(obj != null){
			return obj;
		}else{
			try{
				obj = dataCacheService.getDataCache(key, dataCacheQuery, dataServiceQuery);
				if(null != obj){
					try{
						redisClient.setex(key, dataCacheQuery.getTimetype().getExpirt().intValue(), obj);
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
	public void delRedis(String key){
		try{
			redisClient.del(key);
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

	/*********************************所有统计缓存统一处理end*****************************/
}
