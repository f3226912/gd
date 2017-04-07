package com.gudeng.commerce.gd.api.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件工具类
 * @author TerryZhang
 */
public class FileUtils {

	/**
	 * 下载文件
	 * @param path 下载的文件的路径
	 * @param response
	 * @return
	 */
	public static HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public static void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
        String fileName = "Operator.doc".toString(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadNet(HttpServletResponse response) throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL("windine.blogdriver.com/logo.gif");

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("c:/abc.gif");

            byte[] buffer = new byte[1204];
            //int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); // 非常重要
        if (isOnLine) { // 在线打开方式
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }
    
    /** 
	 *  根据路径删除指定的目录或文件，无论存在与否 
	 *@param sPath  要删除的目录或文件 
	 *@return 删除成功返回 true，否则返回 false。 
	 */  
	public static boolean DeleteFolder(String sPath) {  
	    File file = new File(sPath);  
	    // 判断目录或文件是否存在  
	    if (!file.exists()) {  // 不存在返回 false  
	        return false;  
	    } else {  
	        // 判断是否为文件  
	        if (file.isFile()) {  // 为文件时调用删除文件方法  
	            return deleteFile(sPath);  
	        } else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(sPath);  
	        }  
	    }  
	}  
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
		boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public static boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}
	
	public static void main(String[] args) {
		//FileUtils.download(path, response);
	}

}
