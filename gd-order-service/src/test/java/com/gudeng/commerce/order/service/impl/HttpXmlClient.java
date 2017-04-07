package com.gudeng.commerce.order.service.impl;
import java.io.FileInputStream;
import java.io.IOException;  
import java.io.InputStream;
import java.io.UnsupportedEncodingException;  
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
  





import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.ParseException;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.client.methods.HttpUriRequest;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.protocol.HTTP;  
import org.apache.http.util.EntityUtils;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HttpXmlClient {
	public static String LOCAL_HOST="http://localhost:8080/gd-api/";
	public static String DEV_HOST="http://192.168.20.182:8082/";
	public static String TEST_HOST="http://192.168.20.174:8082/";

	private static Logger logger = LoggerFactory.getLogger(HttpXmlClient.class);
      
	    public static String post(String url, Map<String, String> params) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient();  
	        String body = null;  
	          
	        logger.info("create httppost:" + url);  
	        HttpPost post = postForm(url, params);  
	          
	        body = invoke(httpclient, post);  
	          
	        httpclient.getConnectionManager().shutdown();  
	          
	        return body;  
	    }  
	      
	    public static String get(String url) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient();  
	        String body = null;  
	          
	        logger.info("create httppost:" + url);  
	        HttpGet get = new HttpGet(url);  
	        body = invoke(httpclient, get);  
	          
	        httpclient.getConnectionManager().shutdown();  
	          
	        return body;  
	    }  
	    
	    
	    private static String invoke(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	          
	        HttpResponse response = sendRequest(httpclient, httpost);  
	        String body = paseResponse(response);  
	          
	        return body;  
	    }  
	  
	    private static String paseResponse(HttpResponse response) {  
	        logger.info("get response from http server..");  
	        HttpEntity entity = response.getEntity();  
	          
	        logger.info("response status: " + response.getStatusLine());  
	        String charset = EntityUtils.getContentCharSet(entity);  
	        logger.info(charset);  
	          
	        String body = null;  
	        try {  
	            body = EntityUtils.toString(entity);  
	            logger.info(body);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return body;  
	    }  
	  
	    private static HttpResponse sendRequest(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	        logger.info("execute post...");  
	        HttpResponse response = null;  
	          
	        try {  
	            response = httpclient.execute(httpost);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return response;  
	    }  
	  
	    private static HttpPost postForm(String url, Map<String, String> params){  
	          
	        HttpPost httpost = new HttpPost(url);  
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	          
	        Set<String> keySet = params.keySet();  
	        for(String key : keySet) {  
	            nvps.add(new BasicNameValuePair(key, params.get(key)));  
	        }  
	          
	        try {  
	            logger.info("set utf-8 form entity to httppost");  
	            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return httpost;  
	    }  
	    
	    
	    
	    
	    public static void main(String[] args) {
	    	String imgFile = "d:\\test\\1449737844202.jpg";// 待处理的图片
			InputStream in = null;
			byte[] data = null;
			// 读取图片字节数组
			try {
				in = new FileInputStream(imgFile);
				data = new byte[in.available()];
				in.read(data);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
//			return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	    	String URL=DEV_HOST+"v2/fileUpload/uploadImageByStr";
	    	String imageStr="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEB AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/ 2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCABaAFoDASIAAhEBAxEB/8QA HwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUF BAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkK FhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1 dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXG x8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEB AQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAEC AxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRom JygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOE hYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU 1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD48sHYo3mH aGH+tMhBYhnDEKW5B5wR+Oc5rfj3C3KFXcHDRxOyNGsYLlHbJIXA53epXbxk nn7RTGWLqXKHZ2cLtLgshAB7dQT/AAnkg524m+Ty0jwNuGXLJnliiMCc7Ywc MWyxHLEkihW1bXVKL3928tdH1stNeuqabfkUINq21rLTS7T0vprs+99Xo1ra IXyi7DI2RmILuUs4BBlkCnLBWVip77gc9TVq3klKrbyLBMiHcgfCEmUuZXAY bj86gsQc8jkkcZwRXMnESriLDBCPNGDhd3VYl54ByT3zurVSRgw2/wAXlxqC 5wsaeaPlZiXRZPkYIDg4P3sEVtRdnJrey5bprrHW199+t7N3dkk+2NJKN1bR JPbo5fzJ6XtZLW7lu99i1Aa12MA7CVRFuyU2bpC6RAnAUbRukPcFNxGTW15g aFDChPkyeYkcbPJb+YglQsYskKQDIMngbjzkgtRs3kmOSRmMqzT5KvGFQp5a SKQUA/hUdTjJ6mtZIrdcxMWAXbIzE5dl2NjzHYsH3ux4HGQvXmumLdtddnbp vLVJO+qWnVWSerbbjTj77v0jFuy6NJa3fNfVp7Xsr7klrHLtBVz5ZlMrJteM b1VyskQBUrtdhz0ZiAMncTq2McMUhuFMiTySGPDnzJZQ29JGdlJbbhty5PzE BWB74+AGbKAPlPL+VcIobnou4MVB4BHO3OSprorOVQ3mqzxnY8aZPyJw5UFS OA+1g4GBljwc84VG726NpNuyW8lZu17vRp9FazettaMFflSWnfR9bN7aLpfT Vq1r36CFSbRU89I0eJfL4KgpvbahVm+ZydrZHGcnOc1FceYSY1Z1KwgGPLqC pZiTEQwIVyrblJ2jcMg4JLrNEk3DzDscF9hb75DMThQMBSF5LZYLnLZwS8gR jGW2BmJiYjbuYvvRSMKobsCM4IByM582q3zS3203S+35O61281q3c3jSeqSd rLzvvvbttt2V1qjAkQyeYDDIA7BnV2JMIVmCurK2Gx3JJJGMsSCQwLNjg2pH GCXGcZfr83Ugfn9BnXKMzOyFi2AN5LJPGrE5jMgYfKOcjlT/AHSGxU4klAx5 EjYwN3mx84LjIyhPOM+uMZJxzzxkmrvRdHda62b3Xbz0UU99YjDdPS3L+EtN L6Xd353d23qfMNoFiBMa7VR1ZNifKoJYGQkN80rdRnhCSQpJrQVN7PIH+Vd8 ihiWMpYkhpXJ5bPLEjHIG3AqhbgICFYuVwI0CgA8uCXBBODxtG8kjGfmwa0m VoxsyOr7lRQu8gvhArFsKCDliWOCwySOO6lK7k7JPS91rv6v+63rf3t7xd+e lFWet1ZNcqXe2tr3V0/PVc11q2xvub98uVdzgRnCsMD94RtG7B3BF+6oDHAJ OdW1jDBmEkhj3qVR2Y5ZC0alm4A+5yAAoXy0w20tWZmXaCoGV2GQA+YqjDBV BIAy2AWIUruzkYIBv2UkhYu26OMoN8ahXB+ZwuRjai5JIxjcMEAMDXZTUrSa Sesba9FbZXlq7b77JS+Jy0hbVLfTf7VnJLS3q11V01e00+lskTzJHMhLbQxU Lz/Gu8ldobJACjrtJBJC5rXgYyBZXZHjKttzGu5GicKhdCDvCEMVUnarMDgs GaqFnCExLwVBAO1xt2kM5O1gdzfd2ncAMOSQSANFVxF8q4MrnBLldoy7YQbS Pm2ZPO7JAyRW20b3XRatJbu2qdusrddHo22hxTXNoultX3fNa6bd9G/O+r3J UCEu37xCZEHyMUd8uCXIjKc4Vsd8YBzkmugtsrKHDSMrrl85LgbXCrvLElgQ G3dOmVOG3Yke3aoRGcEq7szHzEyXGRhzvAI+98oGWHUGt2xyJcJwikiPflsn D7TJuJwDjC4VgvyjJCsawqa7Wt8Kbtq7ytq3fV+f8t07NHTTp6Nt225fPVvZ tX++2u90mdBAWkibAXcFVZGI+XZkncyFSRlVAYIwzu+bqaWS3CRTO0kfHDIY 2weX2cs+MnC5+XPTJyTm3bGQlQyoq52FQ4Riyq4TLKqghjkYYMD3BzSTxLIW WU7kCplCQ7IfmzuYhTwQSoOSOhZgpNeZUVnJ21td2/xSV7Xk/l2ad3Z36YwX K7/3U77PV9E9Uvm16WazFijw5aIA/IDhABjJ2ggk88ZK8+m4ZBNxbOUqCIRg gEYlUDHzYwCcgc8A5I56kmiUKQojPzgBX4LKQMgOp3DIIGTwNvI2sQSUTIUD dIMKowd3+37fTP4V57nK8uV6aNa7L5t22Tt300S1n2ale6b1Wu99u97d7efX Q+X4IJNwx8mxtkcZZRyQ4LswLk7AcjPQ7doJJB0I0LDJKIR0fduLnJxjcmSo 4JOeTkkA1Ja25Gd7Mo3fMNoZsbm6kNxwASMliWxgYY1egt8CRxnavyqCF24D OSEAJ3DBxng5zkhRmvbpwjzS1dm+W+ujTfdu7el93q9XbXy6acVKys7LR2vZ NaW3skrq3d3cmymEMmNo+Vs7sfIZADjIA3D5irFRyFGCGY5B0rK1VSyoPLBV MqwZvlJkyWZmGWIGFDKRncQFI+aIQu0gxnGNicYBCNIACQoH94gnA2kcbs50 oIGYFXwAGjB2swfPzfPgEgjB7nABPGSc9tJS5ZJJaNLb3nZrfXsl52tqmrO6 dkpa2d1p13S9Vfz0Wu70NWFU4jIY8ofujYQvmKqryDgj5mJ4U5XBByNCKN2L hm3IHQrzgLgTf3d4B4HTPGcgZClLNFZm2lZHVUjKsOo3S4OAGwCEHzcHAAzn mtZIm2lCoADAkgHC5Y4OFBY9MYHQEZJBqpJ2etlp003lre2mq81e+qaafTCm pLmlLS6StfV3238mvlo9ZXijhj5MgwCVA2MylRltwPzgHgKSAu3o2NwGegsY VB3iNcY2RnzG5CCQjJwCC2Bjg5LDkAtVBEiLLHtJBIDHghfvkAgYKklQTycg kEDGDs20Z+bLkqCAgA+UDLgEjg5yc8+hHIya5JysnG7utU3pu5Xs7a35Y7aJ tJts6kklb+Xa99rtK3orf5uTZtRIGjVmkCkBQsbINwwW43Lk5PJ3EkjDAAkg l0ir5ch7q3LEE5bdITg7ixPTqAACB";

	    	Map<String, String> params = new HashMap<String, String>();  
//	    	params.put("memberId", "93");
	    	params.put("imageStr", imageStr);
	    	     
	    	String xml = HttpXmlClient.post(URL, params);  
	    	System.out.println(xml);  
		}
	    
	    
	    
}
