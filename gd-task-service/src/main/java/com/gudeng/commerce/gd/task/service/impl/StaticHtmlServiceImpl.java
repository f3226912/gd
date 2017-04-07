package com.gudeng.commerce.gd.task.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.task.service.StaticHtmlService;
import com.gudeng.commerce.gd.task.util.GdProperties;

public class StaticHtmlServiceImpl implements StaticHtmlService{
	
	public static Logger logger = LoggerFactory.getLogger(StaticHtmlServiceImpl.class);
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	public HttpClient httpClient = null; //HttpClient实例  
	public GetMethod getMethod =null; //GetMethod实例  
	public BufferedWriter fw = null;  
	public String page = null;  
	public BufferedReader br = null;  
	public InputStream in = null;  
	public StringBuffer sb = null;  
	public String line = null; 
	public String path = null;
	
	@Override
	public void generatorHtml(){
//		boolean[] isGenerator = new Array)();
		logger.info("------------生成静态页面任务");
		path = gdProperties.getHtmlDir();
		String urls = gdProperties.getHtmlUrls();
		String[] uris = urls.split("\\|");
		logger.info("=======================>"+uris.length);
		if(uris.length<=0)return;
		for(String uri:uris){
			String[] tempUris = uri.split(",");
			String htmlName = tempUris[1]==null?"temp.html":tempUris[1];
			generatorHtmlTask(tempUris[0],htmlName);
		}
		logger.info("------------生成静态页面任务结束");
	}
	


	public boolean generatorHtmlTask(String url,String htmlName) {
		
		boolean status = false;	
		int statusCode = 0;				
		try{
			httpClient = new HttpClient();
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");			
			getMethod = new GetMethod(url);
			//使用系统提供的默认的恢复策略，在发生异常时候将自动重试3次
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			//设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
			getMethod.addRequestHeader("Content-Type","text/html;charset=UTF-8");
			//执行Get方法并取得返回状态码，200表示正常，其它代码为异常
			statusCode = httpClient.executeMethod(getMethod);			
			if (statusCode!=200) {
				logger.error("静态页面引擎在解析"+url+"产生静态页面"+htmlName+"时出错!");
				return status;
			}

			//读取解析结果
			sb = new StringBuffer();
			in = getMethod.getResponseBodyAsStream();
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			while((line=br.readLine())!=null){
				sb.append(line+"\n");
			}
			if(br!=null)br.close();
			page = sb.toString();
			//page = page.replaceAll("100.98.154.94:8080", "www.gdeng.cn");
			page = page.replaceAll("100.98.154.94:8080", "www.gdeng.cn");
			page = page.replaceAll("100.98.154.94", "www.gdeng.cn");
			page = page.replaceAll("100.98.154.94:80", "www.gdeng.cn");
			page = page.replaceAll(":8080/", "/");
			page = page.replaceAll(":8080", "");
			String pagetemp = page.substring(page.length()-8, page.length());
			logger.info("=====================>"+pagetemp);
			//判断是否读取了整个的html文档页面，如果没有，就不生产新的静态页
			if(null != pagetemp && (pagetemp.contains("/html>")||pagetemp.contains("</html>")||pagetemp.contains("</stript>"))){
				writeHtmlFile(htmlName,path);
				status = true;
			}else{
				logger.info("===============检测到文档不完整，没有重新生成===========");
				status = false;
			}
			//page = build(page);//去除空格，节省带宽
			//将解析结果写入指定的静态HTML文件中，实现静态HTML生成
		}catch(Exception ex){
			logger.info("静态页面引擎在解析"+url+"产生静态页面"+htmlName+"时出错:"+ex.getMessage());			
        }finally{
        	getMethod.releaseConnection();
        }
		return status;
	}
	
	
    @SuppressWarnings("unused")
	private String build(String page){
        StringBuffer content = null;
        String body = "";
        try {
            content = new StringBuffer(page);
            Pattern pattern;
            Matcher matcher;
            // 查找body内容
            pattern = Pattern.compile("<body>[\\s\\S]*?</body>");
            matcher = pattern.matcher( content.toString().trim());
            if(matcher.find()){
                body= matcher.group();
                body = body.replaceAll("[\r\t\n]", "");
                body = body.replaceAll(">\\s+<", "><");
                body = body.replaceAll("^*\\s{2,}", " ");
                //body = body.replaceAll(" ", "");
               logger.info(body);
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        	return page;
        }
        return content.replace(content.indexOf("<body>"), content.indexOf("</body>")+7, body).toString();
    }	
	
	
	//将解析结果写入指定的静态HTML文件中
	private synchronized boolean writeHtmlFile(String htmlFileName,String path){
		logger.info("---------------->"+path);
		try{
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			htmlFileName = path+File.separator+htmlFileName;
			fw = new BufferedWriter(new FileWriter(htmlFileName));
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(htmlFileName),"UTF-8");
			fw.write(page);	
//			FileUtils.copyFile(srcFile, destFile);
			if(fw!=null)fw.close();	
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	
	}
	

	/**
	 * 读取文档的最后一行
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public synchronized static String readLastLine(File file, String charset) throws IOException {  
		  if (!file.exists() || file.isDirectory() || !file.canRead()) {  
		    return null;  
		  }  
		  RandomAccessFile raf = null;  
		  try {  
		    raf = new RandomAccessFile(file, "r");  
		    long len = raf.length();  
		    if (len == 0L) {  
		      return "";  
		    } else {  
		      long pos = len - 1;  
		      while (pos > 0) {  
		        pos--;  
		        raf.seek(pos);  
		        if (raf.readByte() == '\n') {  
		          break;  
		        }  
		      }  
		      if (pos == 0) {  
		        raf.seek(0);  
		      }  
		      byte[] bytes = new byte[(int) (len - pos)];  
		      raf.read(bytes);  
		      if (charset == null) {  
		        return new String(bytes);  
		      } else {  
		        return new String(bytes, charset);  
		      }  
		    }  
		  } catch (FileNotFoundException e) {  
		  } finally {  
		    if (raf != null) {  
		      try {  
		        raf.close();  
		      } catch (Exception e2) {  
		      }  
		    }  
		  }  
		  return null;  
		} 
	
	//测试方法
	public static void main(String[] args){
//		StaticHtmlService h = new StaticHtmlServiceImpl();
//		h.generatorHtmlTask("http://localhost:8080/gd-home/index","E:/temp/tt/index.html");
//		System.out.println("静态页面已经生成到E:/temp/index.html");
//		try {
//			System.out.println(readLastLine(new File("E:\\temp\\html\\yulin.html"),"utf-8"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
