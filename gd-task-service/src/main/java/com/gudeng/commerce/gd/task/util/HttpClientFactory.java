
 /*
 * 文 件 名:  HttpClientFactory.java
 * 版    权:  Tydic Technologies Co., Ltd. Copyright 1993-2012,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yan
 * 修改时间:  2013-11-26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
  
package com.gudeng.commerce.gd.task.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;


 /**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  yan
 * @version  [版本号, 2013-11-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class HttpClientFactory {
    /**
     * http链接管理器
     */
    private static final  HttpConnectionManager httpConnectionManager;
    public static HttpClient getHttpClient(){
       return  new HttpClient(httpConnectionManager);
    }
    static{
        httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(30*1000);
        params.setSoTimeout(40*1000);
        params.setDefaultMaxConnectionsPerHost(100);//very important!!
        params.setMaxTotalConnections(500);//very important!!
    }
}
