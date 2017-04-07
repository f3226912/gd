package com.gudeng.commerce.gd.bi.dto;

import com.gudeng.commerce.gd.bi.annotation.ExcelConf;
import com.gudeng.commerce.gd.bi.entity.GrdProMemberInvitedRegisterEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProMemberInvitedRegisterDTO extends GrdProMemberInvitedRegisterEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8494527365817750983L;
	private String registerTimeString;
	
	//@ExcelConf(excelHeader="业务类型", sort=10)
	private String typeStr;
	
	public String getRegisterTimeString() {
		return registerTimeString;
	}
	public void setRegisterTimeString(String registerTimeString) {
		this.registerTimeString = registerTimeString;
	}
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	

}