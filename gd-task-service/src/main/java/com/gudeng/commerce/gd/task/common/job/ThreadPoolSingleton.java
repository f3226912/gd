package com.gudeng.commerce.gd.task.common.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.commerce.gd.task.util.SpringContextUtil;

/**
 * 线程池单例类
 * 
 * @author panmin
 * @version [版本号, 2014-9-12]
 * @since [产品/模块版本]
 */
public class ThreadPoolSingleton {

	private volatile static ExecutorService threadPoolInstance;
	
	private volatile static ExecutorService singletonThreadPoolInstance;

	public static final String CORE_POOL_SIZE = "CORE_POOL_SIZE"; // 线程池维护线程的最少数量
	public static final String MAXIMUM_POOL_SIZE = "MAXIMUM_POOL_SIZE"; // 线程池维护线程的最大数量
	public static final String KEEP_ALIVE_TIME = "KEEP_ALIVE_TIME"; // 线程池维护线程所允许的空闲时间
	public static final String QUEUE_CAPACITY = "QUEUE_CAPACITY"; // 队列容量
	
	private static Object lock = new Object();

	private ThreadPoolSingleton() {
	}
	
	public static ExecutorService getThreadPoolInstance() {
		if (threadPoolInstance == null) {
			synchronized (lock) {
				if (threadPoolInstance == null) {
					GdProperties gdProperties = (GdProperties) SpringContextUtil.getBean("gdProperties");
					int corePoolSize = Integer.parseInt(gdProperties.getProperties().getProperty(CORE_POOL_SIZE));
					int maximumPoolSize = Integer.parseInt(gdProperties.getProperties().getProperty(MAXIMUM_POOL_SIZE));
					int keepAliveTime = Integer.parseInt(gdProperties.getProperties().getProperty(KEEP_ALIVE_TIME));
					int capacity = Integer.parseInt(gdProperties.getProperties().getProperty(QUEUE_CAPACITY));
					threadPoolInstance = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(capacity), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
				}
			}
		}
		return threadPoolInstance;
	}
	
	public static ExecutorService getSingletonThreadPoolInstance() {
		if (singletonThreadPoolInstance == null) {
			synchronized (ThreadPoolSingleton.class) {
				if (singletonThreadPoolInstance == null) {
					singletonThreadPoolInstance = Executors.newSingleThreadExecutor();
				}
			}
		}
		return singletonThreadPoolInstance;
	}

}
