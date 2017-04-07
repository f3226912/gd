package com.gudeng.commerce.gd.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/** 
 * 接收从手机app端发送过来的加密串进行解密
 * @author 杨浩宇 百度地图API ,以及和地图有关的公用方法
 */
public class MapUtil {
	private static double EARTH_RADIUS = 6378.137; 
	   private static double rad(double d) { 
	        return d * Math.PI / 180.0; 
	    }
	   
    public static Map<String, String> testPost(String city) throws IOException {
        URL url = new URL("http://api.map.baidu.com/geocoder/v2/?address=" + city + 
        		"&callback=renderReverse&location=" + 
                    "&output=json&ak=28X45gxmua3TOds6sRTLLG5MA9vpuUfV&city="+city+"&callback=showLocation");
        URLConnection connection = url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf-8");
//        remember to clean up
        out.flush();
        out.close();
//        一旦发送成功，用以下方法就可以得到服务器的回应：
        String res;
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                l_urlStream,"UTF-8"));
        StringBuilder sb = new StringBuilder("");
        while ((res = in.readLine()) != null) {
            sb.append(res.trim());
        }
        String str = sb.toString();
        System.out.println(str);
        Map<String,String> map = null;
    if(StringUtils.isNotEmpty(str)) {
      int addStart = str.indexOf("lng\":");
      int addEnd = str.indexOf(",\"lat");
      int addStart2 = str.indexOf("lat\":");
      int addEnd2 = str.indexOf("},\"precise");
      if(addStart > 0 && addEnd > 0) {
        String address = str.substring(addStart+5, addEnd);
        String address2 = str.substring(addStart2+5, addEnd2);
        map = new HashMap<String,String>();
        map.put("lng", address);
        map.put("lat", address2);
        return map;		
      }
    }
    
    return null;
    
    }
    
    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为公里）
     * 参数为String类型
     * @param mlat 用户经度
     * @param mlng 用户纬度
     * @param clat 城市经度
     * @param clng 城市纬度
     * @return
     */
    
    public static String dist(Double mlng,Double mlat,Double clng,Double clat) {
         double radLat1 = rad(mlat);
         double radLat2 = rad(clat);
         double difference = radLat1 - radLat2;
         double mdifference = rad(mlng) - rad(clng);
         double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                 + Math.cos(radLat1) * Math.cos(radLat2)
                 * Math.pow(Math.sin(mdifference / 2), 2)));
         distance = distance * EARTH_RADIUS;
         distance = Math.round(distance * 10000) / 10000;
         String distanceStr = distance+"";
         distanceStr = distanceStr.
             substring(0, distanceStr.indexOf(".")+3);
           
         return distanceStr;
    }
    
    
    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static  Map<String,String>  getAround(String latStr, String lngStr, String raidus) {
    	 Map<String,String>  map = new HashMap<String, String>();
          
        Double latitude = Double.parseDouble(latStr);// 传值给经度
        Double longitude = Double.parseDouble(lngStr);// 传值给纬度
  
        Double degree = (24901 * 1609) / 360.0; // 获取每度
        double raidusMile = Double.parseDouble(raidus);
          
        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = longitude - radiusLng;
        // 获取最大经度
        Double maxLat = longitude + radiusLng;
          
        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = latitude - radiusLat;
        // 获取最大纬度
        Double maxLng = latitude + radiusLat;
          
        map.put("minLat", minLat+"");
        map.put("maxLat", maxLat+"");
        map.put("minLng", minLng+"");
        map.put("maxLng", maxLng+"");
          
        return map;
    }
    
    /**
     * 根据目的地和起始地的名称,获取两地最短的路线长度
     * @param latStr
     * @param lngStr
     * @param raidus
     * @return
     * @throws IOException 
     */
    public static String  getDsitanceByArea(String strCity, String endCity) throws IOException {
    	
    	
        URL url = new URL("http://api.map.baidu.com/direction/v1?mode=driving&origin=" + strCity + "&destination="+endCity
                    +"&origin_region="+strCity+"&destination_region="+endCity+"&tactics=12"+"&output=json&ak=28X45gxmua3TOds6sRTLLG5MA9vpuUfV");
        URLConnection connection = url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf-8");
//        remember to clean up
        out.flush();
        out.close();
//        一旦发送成功，用以下方法就可以得到服务器的回应：
        String res;
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                l_urlStream,"UTF-8"));
        StringBuilder sb = new StringBuilder("");
        while ((res = in.readLine()) != null) {
            sb.append(res.trim());
        }
        String str = sb.toString();
    if(StringUtils.isNotEmpty(str)) {
      int addStart = str.indexOf("distance\":");
      int addEnd = str.indexOf(",\"duration\":");
      if(addStart > 0 && addEnd > 0) {
      String distance = str.substring(addStart+10, addEnd);
   ///   distance=  distance.substring(0,distance.length()-3)+"."+distance.substring(distance.length()-3,distance.length());
      double b=Double.parseDouble(distance)/1000;
      BigDecimal  d  =   new   BigDecimal(b);  
      double   f1   =   d.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
      return f1+"";
      
    }
      
    }
    //说明无法获取里程
	return "-1";
    
    }
	public static void main(String[] args) throws IOException {

        System.out.println(MapUtil.getDsitanceByArea("武汉市","北京市"));
	}
}
