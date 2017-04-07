package com.gudeng.commerce.gd.exception;

/**
 * @Description: TODO(任务锁定定时异常，任务池处理返回此异常，会重新定时尝试此任务)
 * @author mpan
 * @date 2015年12月22日 上午10:11:43
 */
public class ServiceTaskLockTimeException extends ServiceException {

	private static final long serialVersionUID = -1731068494715531627L;

	public ServiceTaskLockTimeException(String message) {
		super(message);
	}

}
