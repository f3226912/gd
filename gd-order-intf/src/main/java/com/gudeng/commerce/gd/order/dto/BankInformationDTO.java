package com.gudeng.commerce.gd.order.dto;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.order.entity.BankInformationEntity;

/**
 * 
 * 银行信息管理
 * 
 * @date 2016年11月9日
 */
public class BankInformationDTO extends BankInformationEntity {

	private static final long serialVersionUID = -5351145989064782899L;

//	private String statusView;
//
//	public String getStatusView() {
//		if (this.getStatus() != null) {
//			return EStatus.getItemByCode(this.getStatus()).getDesc();
//		}
//		return null;
//	}
//
//	public void setStatusView(String statusView) {
//		this.statusView = statusView;
//	}
//
//	public enum EStatus {
//		START("1", "启用"), CLOSE("0", "关闭");
//		EStatus(String code, String desc) {
//			this.code = code;
//			this.desc = desc;
//		}
//
//		private String code;
//		private String desc;
//
//		public String getCode() {
//			return code;
//		}
//
//		public void setCode(String code) {
//			this.code = code;
//		}
//
//		public String getDesc() {
//			return desc;
//		}
//
//		public void setDesc(String desc) {
//			this.desc = desc;
//		}
//
//		public static EStatus getItemByCode(String code) {
//			for (EStatus item : EStatus.values()) {
//				if (StringUtils.equals(code, item.getCode())) {
//					return item;
//				}
//			}
//			return null;
//		}
//
//	};

}