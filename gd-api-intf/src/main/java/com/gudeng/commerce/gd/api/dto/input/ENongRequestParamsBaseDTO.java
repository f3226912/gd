package com.gudeng.commerce.gd.api.dto.input;

/**
 * E农平台请求数据对象
 * @author TerryZhang
 */
public class ENongRequestParamsBaseDTO {

	/** 接口版本号(填1.0) */
	private String version;
	
	/** 字符集(填1) 1-UTF-8 */
	private String charset ;
	
	/** POS终端号 */
	private String machinenum;
	
	/** POS商户号 */
	private String merchantnum;
	
	/** 保留字节 */
	private String reserved;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getMachinenum() {
		return machinenum;
	}

	public void setMachinenum(String machinenum) {
		this.machinenum = machinenum;
	}

	public String getMerchantnum() {
		return merchantnum;
	}

	public void setMerchantnum(String merchantnum) {
		this.merchantnum = merchantnum;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
}
