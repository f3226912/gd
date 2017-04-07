package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;
import com.gudeng.commerce.gd.customer.entity.MemberLoginLogEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class MemberLoginLogDTO extends MemberLoginLogEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1426863110480427773L;
	
	@ExcelConf(excelHeader="登录来源", sort = 7)
	private String loginTypeStr;
//	`loginType` varchar(50) DEFAULT NULL COMMENT '登录来源:1.web主站,2.农速通,3.农商友,4.农批商,5.供应商',

	public String getLoginTypeStr() {
    	if("1".equals(this.getLoginType())){
    		return "web主站";
    	}else if("2".equals(this.getLoginType())){
    		return "农速通";
    	}else if("3".equals(this.getLoginType())){
    		return "农商友";
    	}else if("4".equals(this.getLoginType())){
    		return "农批商";
    	}else if("5".equals(this.getLoginType())){
    		return "供应商";
    	}else{
    		return "";
    	}
	}

	public void setLoginTypeStr(String loginTypeStr) {
		this.loginTypeStr = loginTypeStr;
	}
	
}