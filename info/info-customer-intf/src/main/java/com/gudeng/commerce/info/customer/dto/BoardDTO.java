package com.gudeng.commerce.info.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.info.customer.entity.BoardEntity;

public class BoardDTO extends BoardEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idStr;
	private String status;
    public String getIdStr() {
        return idStr;
    }
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
