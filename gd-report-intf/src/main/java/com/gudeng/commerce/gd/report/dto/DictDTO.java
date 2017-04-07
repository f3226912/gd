package com.gudeng.commerce.gd.report.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.report.entity.DictEntity;


public class DictDTO extends DictEntity  implements Serializable, Comparator{

	private static final long serialVersionUID = -8570045510083288129L;

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
    public String getBirthday_string() {
		return birthday_string;
	}

	public void setBirthday_string(String birthday_string) {
		this.birthday_string = birthday_string;
	}

	public String birthday_string;
    
	 
}
