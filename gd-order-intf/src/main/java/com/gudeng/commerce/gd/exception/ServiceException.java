package com.gudeng.commerce.gd.exception;

/**
* 服务层异常
*/
public class ServiceException extends Exception implements CommonException {
    
    /**
    * 注释内容
    */
    private static final long serialVersionUID = -8404029869798299145L;
    
    /**
     * 异常编码
     */
    private String errCode;
    
    /**
     * 构造函数
     * @param message 异常消息
     */
    public ServiceException(String message) {
        super(message);
    }
    
    /**
     * 构造函数
     * @param errCode 异常编码
     * @param message 异常消息
     */
    public ServiceException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }
    
    /**
     * 构造函数
     * @param ex 异常
     */
    public ServiceException(Throwable ex) {
        super(ex);
    }
    
    /**
     * 构造函数
     * @param message 异常消息
     * @param ex 异常
     */
    public ServiceException(String message, Throwable ex) {
        super(message, ex);
    }
    
    /**
     * 构造函数
     * @param code 异常编码
     * @param message 异常消息
     * @param ex 异常
     */
    public ServiceException(String errCode, String message, Throwable ex) {
        super(message, ex);
        this.errCode = errCode;
    }
    
    /**
     * @return 返回 errCode
     */
    public String getErrCode() {
        return errCode;
    }
    
}
