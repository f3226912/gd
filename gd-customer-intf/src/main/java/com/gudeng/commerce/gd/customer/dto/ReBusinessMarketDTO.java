package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.ReBusinessMarketEntity;


public class ReBusinessMarketDTO extends ReBusinessMarketEntity  implements Serializable, Comparator{

  
	/**
	 * 
	 */
	private static final long serialVersionUID = -1622601738572051433L;

	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	} 
	 
}
