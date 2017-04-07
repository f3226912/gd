package com.gudeng.commerce.info.home.util;

import java.util.Properties;

/**
 * 参数属性;
 * 
 */
public class GdProperties {

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/** demo的地址 */
	public String getDemoUrl() {
		return properties.getProperty("gd.demo.url");
	}

	/** sysRegisterUser的地址 */
	public String getSysRegisterUserUrl() {
		return properties.getProperty("gd.sysRegisterUserService.url");
	}
	/** sysMessage的地址 */
	public String getSysMessageUrl() {
		return properties.getProperty("gd.sysmessageService.url");
	}
	/** sysMessageuser的地址 */
	public String getSysMessageuserUrl() {
		return properties.getProperty("gd.sysmessageuserService.url");
	}
	/** sysUserBoard的地址 */
	public String getSysUserBoardUrl() {
		return properties.getProperty("gd.sysUserBoardService.url");
	}
	/**
     * 获取交易预报表服务
     * @return
     */
	public String getProBszbankUrl() {
		return properties.getProperty("gd.proBszbankService.url");
	}
	
	public String getReportsServiceUrl(){
   	 return properties.getProperty("gd.reportsService.url");
   }
	public String getSysrolereportsServiceUrl(){
        return properties.getProperty("gd.sysrolereportsService.url");
    }
    
    public String getBoardServiceUrl(){
     return properties.getProperty("gd.boardService.url");
   }
    public String getDatasourceServiceUrl(){
     return properties.getProperty("gd.datasourceService.url");
   }
    public String getOrderBillServiceUrl(){
     return properties.getProperty("gd.orderBillService.url");
   }
    public String getSysroleboardServiceUrl(){
        return properties.getProperty("gd.sysroleboardService.url");
    }
    public String getSysmessageServiceUrl(){
        return properties.getProperty("gd.sysmessageService.url");
    }
    public String getSysmessageuserServiceUrl(){
        return properties.getProperty("gd.sysmessageuserService.url");
    }
    /** sysLoginService的地址 */
    public String getSysLoginServiceUrl() {
        return properties.getProperty("gd.sysLoginService.url");
    }
    /** sysUserRoleService的地址 */
    public String getSysUserRoleServiceUrl() {
        return properties.getProperty("gd.sysUserRoleService.url");
    }
}
