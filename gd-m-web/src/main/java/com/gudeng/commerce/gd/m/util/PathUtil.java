package com.gudeng.commerce.gd.m.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件路径相关的工具类
 * 
 * @author 冰封
 *
 */
public class PathUtil {

    /**
     * @Description 将url中的连续多个/转换为一个/
     * @param url
     * @return
     * @CreationDate 2016年3月18日 上午11:50:07
     * @Author lidong(dli@gdeng.cn)
     */
    public static String urlFormat(String url) {
        url = urlFormat2(url);
        url = url.replaceAll("/+", "/");
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * @Description 将url中的\转化为/
     * @param url
     * @return
     * @CreationDate 2016年3月18日 上午11:51:12
     * @Author lidong(dli@gdeng.cn)
     */
    public static String urlFormat2(String url) {
        return url.replace("\\", "/");
    }

    /**
     * @Description 去除URL头部的http://localhost:8080/gd-admin/信息
     * @param url
     * @return
     * @CreationDate 2016年3月18日 下午12:03:45
     * @Author lidong(dli@gdeng.cn)
     */
    public static String urlCut(HttpServletRequest request, String url) {
        return url.replace(PathUtil.getBasePath(request), "");
    }

    public static void main(String[] args) {
        System.out.println(urlFormat("http:\\\\wwww.baidu.com//sdasd\\sadasd\\asd\\asdas//////////sadasdasasd/\\\\//sad///"));
        System.out.println(urlFormat2("http://wwww.baidu.com//sdasd\\sadasd\\asd\\asdas//////////sadasdasasd/\\\\//sad///"));
    }

    /** 开关字段。该字段决定是否将字符串中路径分隔符统一成'/'。默认为true **/
    private static boolean replace = true;

    /**
     * 获取用String表示的类路径。默认将具体系统的分隔符替换为'/'
     * 
     * @return
     */
    public static String classPath() {
        File classpathFile = classpathFile();
        String classpathStr = classpathFile.getAbsolutePath();
        if (replace) {
            // 将具体系统的分隔符统一替换为'/'
            classpathStr = classpathStr.replace(File.separatorChar, '/');
        }
        return classpathStr;
    }

    /**
     * 获取用File表示的类路径
     * 
     * @return
     */
    public static File classpathFile() {
        URI classpathURI;
        try {
            classpathURI = PathUtil.class.getResource("/").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("获取类路径失败。", e);
        }
        return new File(classpathURI);
    }

    /*--------------getter和setter--------------*/

    public static boolean isReplace() {
        return replace;
    }

    public static void setReplace(boolean replace) {
        PathUtil.replace = replace;
    }

    /**
     * 获取basePath
     * 
     * @author songhui
     * @date 创建时间：2015年7月29日 下午2:55:44
     * @param request
     * @return
     *
     */
    public static String getBasePath(HttpServletRequest request) {

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath;
    }
}
