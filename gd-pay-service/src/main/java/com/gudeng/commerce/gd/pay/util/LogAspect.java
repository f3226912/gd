package com.gudeng.commerce.gd.pay.util;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法执行时间日志记录
 * 
 * @author  panmin
 * @version  [版本号, 2013-3-27]
 * @since  [产品/模块版本]
 */
public class LogAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
	
	public Object doAround(ProceedingJoinPoint call) throws Throwable {
		String className = call.getSignature().getDeclaringTypeName();
		String methodName = call.getSignature().getName();
		Object[] args = call.getArgs();
		String argsStr = args == null ? "" : Arrays.toString(args);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("enter the " + className + "." + methodName + "(" + argsStr + ") method");
		}
		long startTime = System.currentTimeMillis();
		Object retObj;
		try {
			retObj = call.proceed(args);
		} catch (Exception e) {
			Throwable cause = getDeepestCause(e);
			LOGGER.error(className + "." + methodName + " throwing Exception", cause);
			throw cause;
		} finally {
			long endTime = System.currentTimeMillis();
			long time = (endTime - startTime);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(className + "." + methodName + " - run time:" + time + "ms");
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(className + "." + methodName + " - return " + (retObj == null ? "" : retObj.toString()));
		}
		return retObj;
	}
	
	/** 
	 * 返回此异常的根本原因
	 */
	private Throwable getDeepestCause(Throwable throwable) {
		if (throwable == null) {
			return throwable;
		}
		return throwable.getCause();
	}

}
