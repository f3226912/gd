package com.gudeng.commerce.gd.m.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {

    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
    private static final String REGEX_HTML = "<[^>]+>"; // 定义HTML标签的正则表达式  
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";//定义空格回车换行符  
      
    /** 
     * @param htmlStr 
     * @return 
     *  删除Html标签 
     */  
    public static String delHTMLTag(String htmlStr) {  
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);  
        Matcher m_script = p_script.matcher(htmlStr);  
        htmlStr = m_script.replaceAll(""); // 过滤script标签  
  
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);  
        Matcher m_style = p_style.matcher(htmlStr);  
        htmlStr = m_style.replaceAll(""); // 过滤style标签  
  
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);  
        Matcher m_html = p_html.matcher(htmlStr);  
        htmlStr = m_html.replaceAll(""); // 过滤html标签  
  
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
        
        htmlStr = htmlStr.replaceAll("\"", ""); // 过滤双引号
        return htmlStr.trim(); // 返回文本字符串  
    }  
      
    public static String getTextFromHtml(String htmlStr){  
        htmlStr = delHTMLTag(htmlStr);  
        htmlStr = htmlStr.replaceAll("&nbsp;", "");   
        return htmlStr;  
    }  
      
    public static void main(String[] args) {  
        String str = "<p><p>洋葱（学名：Allium cepa），别名球葱、圆葱、玉葱、葱头、荷兰葱、皮牙子等，.百合科、葱属二年生草本植物。洋葱含有前列腺素A，能降低外周血管阻力，降低血粘度，可用于降低血压、提神醒脑、缓解压力、预防感冒。此外，洋葱还能清除体内氧自由基，增强新陈代谢能力，抗衰老，预防骨质疏松，是适合中老年人的保健食物</p></p>";  
        System.out.println(getTextFromHtml(str));  
    }  

}
