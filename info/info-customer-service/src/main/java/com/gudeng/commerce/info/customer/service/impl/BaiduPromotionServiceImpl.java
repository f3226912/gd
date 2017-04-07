package com.gudeng.commerce.info.customer.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.info.customer.dto.BaiDuDoLoginResponseDTO;
import com.gudeng.commerce.info.customer.service.BaiDuApiService;
import com.gudeng.commerce.info.customer.service.BaiduPromotionService;
import com.gudeng.commerce.info.customer.util.BaiDuApiUtil;
import com.gudeng.commerce.info.customer.util.DateUtil;
import com.gudeng.commerce.info.customer.util.JacksonUtil;

public class BaiduPromotionServiceImpl implements BaiduPromotionService {
	@Autowired
	public BaiDuApiService apiService;
	
    private static final String USER_NAME = BaiDuApiUtil.USER_NAME;
    
    private static final String PASSWORD = BaiDuApiUtil.PASSWORD;
    
    private static final String TOKEN = BaiDuApiUtil.TOKEN;
	
	@Override
	public String baiduPromotionReportService( Map map) throws Exception {
		try {
			apiService = new BaiDuApiServiceImpl();
			BaiDuDoLoginResponseDTO resposeDTO = apiService.doLogin(USER_NAME,PASSWORD,TOKEN);
			String json = JacksonUtil.obj2Str(resposeDTO);
			   System.out.println("结果："+json);
			   JSONObject reJson = new JSONObject(json);  
		      URL strUrl = new URL("https://api.baidu.com/json/tongji/v1/ProductService/api" );
		        HttpURLConnection conn = (HttpURLConnection) strUrl.openConnection();
		        conn.setRequestProperty("accept", "*/*");
		        conn.setRequestProperty("connection", "Keep-Alive");
		        conn.setRequestProperty("user-agent",
		            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        conn.setRequestProperty("UUID", BaiDuApiUtil.getLocalMac());  //     UUID	String	必填，唯一标识符，与登录时一致
		        conn.setRequestProperty("USERID", reJson.get("ucid").toString());   //    USERID	String	必填，成功登录后获取的用户id（ucid）
		  //      conn.setRequestProperty("tracker", "query");// tracker  String选填，请求定位符，可用于定位请求
		        conn.setDoOutput(true); 
		        conn.setDoInput(true);
		        conn.setRequestMethod("POST");
		        DataOutputStream out = new DataOutputStream(conn.getOutputStream()); 
		        
		        JSONObject jsonHeader=new JSONObject();
		        jsonHeader.put("username", BaiDuApiUtil.USER_NAME);//String	用户名	必填
		        jsonHeader.put("password", reJson.get("st").toString());//String	成功登录后获取的会话id(st)	必填
		        jsonHeader.put("token", BaiDuApiUtil.TOKEN);//String	api权限码	必填
		      //  jsonObject.put("target", "target");  target	String	被操作用户名	选填（一期无效）
		      //  jsonObject.put("accessToken", "accessToken"); oauth access token	选填（一期无效）
		        jsonHeader.put("account_type", 2);//Integer	账户类型	必填1：站长账号 2：凤巢账号3：联盟账号4：哥伦布账号
		        JSONObject jsonBody=new JSONObject();
		        jsonBody.put("serviceName", "report");//string	服务名	必填 report
		        jsonBody.put("methodName", "query");//string	方法名	必填 query
		        //  parameterJSON	QueryParameterType	查询相关参数	必填JSON格式
		        JSONObject parameterJSON=new JSONObject();
		        parameterJSON.put("reportid", 1);//	必填	int   取值范围：     1：受访页面报告
		        parameterJSON.put("siteid", 1);//	必填    int   取值范围： 用户名下的siteid，可通过 profileService的getsites方法查询 
		       // DateUtil
		        parameterJSON.put("start_time", "20160218101201");//	格式为YYYYmmddHHiiss，例如：20130218000000 不大于当前时间
		        parameterJSON.put("end_time", "20160318101201");//20130218000000 不大于当前时间不小于start_time end_time与start_time间隔不超过一年
		        String[] dimensions={"pageid"};
		        parameterJSON.put("dimensions", dimensions);//数组	groupby的维度	选填，默认pageid
		        String[] metrics={"pageviews","visitors","ips","entrances","outwards","exits","stayTime","exitRate"};
		      /*   metrics	数组	查询的指标	必填 取值范围： pageviews:浏览量（PV） visitors:访客数（UV）   ips:IP数  entrances:入口页次数 outwards:贡献下游流量
		        exits:退出页次数   stayTime:平均停留时长 exitRate:退出率 */
		        parameterJSON.put("metrics", metrics);
				/*
				 * 选填 fromType: 1 直达 2 搜索引擎 3 外部链接 newVisitor: 1 新访客 2 老访客
				 */
		        String[] filters={"fromType=2","newvisitor=1"};
		        parameterJSON.put("filters", filters);
		        String[] sort={"pageviews desc","exitRate desc"};
		        /*
				 * 选填 排序的指标必须在查询的指标中 排序方式可以是 asc 正序（默认值） desc 逆序
				 * 排序指标可填多个（最多3个），优先级由高到低
				 */
		        parameterJSON.put("sort", sort);
		        parameterJSON.put("start_index", 0);
		        parameterJSON.put("max_results", 10000);
		        jsonBody.put("parameterJSON", parameterJSON);
		        StringBuffer strPost = new StringBuffer();
		        System.out.println("heade:"+jsonHeader.toString());
		        System.out.println("body:"+jsonBody.toString());
		        strPost.append("header").append("=").append(jsonHeader.toString()).append("&")
	              .append("body").append("=").append(jsonBody.toString());
		        System.out.println("strPost:"+strPost);
		        byte[] midbytes = strPost.toString().getBytes("UTF8");

		        out.write(midbytes);
		        out.flush();
		        out.close();
		        // 设置编码,否则中文乱码
		        BufferedReader reader =
		            new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		        // 接收返回代码
		        String strReturn = "";
		        String re = "";
		        while ((strReturn = reader.readLine()) != null) {
		          re += strReturn;
		        }
		        reader.close();
		        conn.disconnect();
		        System.out.println("report服务："+re);
		       /* if (re.contains("<error_code>1</error_code>")) {
		          break;
		        }*/
		    } catch (Exception e) {
		    	System.out.println(e.getMessage());
		    }
		
		return null;
	}

}
