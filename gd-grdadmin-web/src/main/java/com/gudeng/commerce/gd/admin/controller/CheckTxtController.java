package com.gudeng.commerce.gd.admin.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("checkTxt")
public class CheckTxtController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CheckTxtController.class);

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("index")
	public String demo(HttpServletRequest request){
		return "checkTxt/check";
	}
	
	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="check" )
	@ResponseBody
    public String check( HttpServletRequest request) {    

		try {
			String description=request.getParameter("description");
			description=description.replace("<br />", "");
			String[]strs=description.split("\r\n");
			int i = checkStr(strs);
			if(i==0){
				return "success";
			}else{
				return ""+i;
			}
		} catch (Exception e) {
			return "falied";
		}
	}
	
	
	private int checkStr(String str[]) {
        Pattern p = Pattern.compile(">([^</]+)</");
		int line = 1;
		try {
			if (str == null || str.length == 0) {
			} else {
				for (; line <= str.length; line++) {
					if (line == 1)
						continue;
					else if (StringUtil.isEmpty(str[line-1])) {
						line++;
						continue;
					} else if (str[line-1].indexOf("|") < 0) {
						return line;
					} else {
				        Matcher m = p.matcher(str[line-1]);//开始编译
				        String s="";
						if (m.find()) {
							s=m.group(1);
				    	}else{
				    		s=str[line-1];
				    	}
//						System.out.println(s);
						String[] contents = s.split("\\|");
						String column = StringUtil.replaceAllEmptyStr(contents[0]);// 商户号
						String column1 = StringUtil.replaceAllEmptyStr(contents[1]);// 商户名
						String column2 = StringUtil.replaceAllEmptyStr(contents[2]);// 交易类型
						String column3 = StringUtil.replaceAllEmptyStr(contents[3]);// 交易年月日
						String column4 = StringUtil.replaceAllEmptyStr(contents[4]);// 交易时分秒
						String column5 = StringUtil.replaceAllEmptyStr(contents[5]);// 交易卡号
						String column6 = StringUtil.replaceAllEmptyStr(contents[6]);// 终端号
						Double column7 = Double.parseDouble(StringUtil.replaceAllEmptyStr(contents[7]));// 交易金额
						String column8 = StringUtil.replaceAllEmptyStr(contents[8]);// 系统参考号
						String column9 = StringUtil.replaceAllEmptyStr(contents[9]); // 银行卡类别
						if (contents.length >= 11) {
							Long column10 = Long.valueOf(StringUtil.replaceAllEmptyStr(contents[10]));
							if (contents.length >= 12) {
								Double column11 = Double.valueOf(StringUtil.replaceAllEmptyStr(contents[11]));
							}
						}
					}
				}
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return line;
		}
	}
	public static void main(String[] args) {
		String s="<span>113410351310011|深圳谷登科技有限公司（BMP）|消费|20160727|163215|622700*********4328|12033563|110000.00|334137133|不明|419|3.00</span>";
        Pattern p = Pattern.compile(">([^</]+)</");
        Matcher m = p.matcher(s);//开始编译
        while (m.find()) {
        	String ss=m.group(1);
    		System.out.println(ss);        
    	}
		
	}
}
