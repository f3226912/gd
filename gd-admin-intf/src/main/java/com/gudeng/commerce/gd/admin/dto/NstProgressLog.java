package com.gudeng.commerce.gd.admin.dto;

import java.util.Date;

/**
 * @purpose 物流进度
 * @author zlb
 * @date 2016年12月8日
 */
	public class NstProgressLog{
		private String createTime;   //进度步骤创建 时间
		private Integer transStatus;//进度状态
		private String transStatusVar;  //状态描述
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public Integer getTransStatus() {
			return transStatus;
		}
		public void setTransStatus(Integer transStatus) {
			this.transStatus = transStatus;
		}
		public String getTransStatusVar() {
			return transStatusVar;
		}
		public void setTransStatusVar(String transStatusVar) {
			this.transStatusVar = transStatusVar;
		}
		
		
	}
