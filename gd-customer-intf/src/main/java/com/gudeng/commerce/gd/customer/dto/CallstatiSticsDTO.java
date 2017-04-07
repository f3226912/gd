package com.gudeng.commerce.gd.customer.dto;

import java.text.SimpleDateFormat;

import com.gudeng.commerce.gd.customer.entity.CallstatiSticsEntity;

/**
 * 拨打电话记录表DTO
 * @author Ailen
 *
 */
public class CallstatiSticsDTO extends CallstatiSticsEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -541560949303320102L;
	
	private Long businessId;
	
	private Integer level;
	
	private Integer b_level;
	
	private String createTimeStr;
	
	private String  startDate;
	private String endDate;
	
	public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Integer getLevel() {
		return level;
	}
	public String getLevelStr() {
		if(this.getLevel()==null)
			return "";
		
		switch(this.getLevel()) {
		case 1:
			return "谷登农批";
		case 2:
			return "农速通";
		case 3:
			return "农商友";
		case 4:
			return "供应商";
		}
		return "";
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getB_level() {
		return b_level;
	}
	
	public String getB_levelStr() {
		if(this.getB_level()==null)
			return "";
		
		switch(this.getB_level()) {
		case 1:
			return "谷登农批";
		case 2:
			return "农速通";
		case 3:
			return "农商友";
		case 4:
			return "供应商";
		}
		return "";
	}
	
	public void setB_level(Integer b_level) {
		this.b_level = b_level;
	}
	
	public String getCreateTimeStr() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		if(this.getCreateTime()!=null) {
			return format.format(this.getCreateTime());
		}
		return createTimeStr;
	}
	
}
