package com.gudeng.commerce.info.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.info.customer.entity.Reports;

public class ReportsDTO extends Reports implements Serializable {

    private static final long serialVersionUID = -6355912835653350591L;
    
    private ReportDataDTO data;

	public ReportDataDTO getData() {
		return data;
	}
	public void setData(ReportDataDTO data) {
		this.data = data;
	}
	private Object sum;
	
	private Integer sumDate;
	
    public Integer getSumDate() {
		return sumDate;
	}
	public void setSumDate(Integer sumDate) {
		this.sumDate = sumDate;
	}
	public Object getSum() {
		return sum;
	}
	public void setSum(Object sum) {
		this.sum = sum;
	}
	//是否被选中
    private int isChecked=0;
    
    private String menuName;
    private String idStr;
    
	public int getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
    
    

}
