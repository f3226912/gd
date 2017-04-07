package com.gudeng.commerce.gd.report.data;

import java.util.Comparator;

import com.gudeng.commerce.gd.report.dto.TradeResult;

public class TradeTimeComparetor implements Comparator<TradeResult> {

	@Override
	public int compare(TradeResult o1, TradeResult o2) {
		 long result = Long.parseLong(o1.getTime().replace("-", "")) - Long.parseLong(o2.getTime().replace("-", ""));
		 
		 if(result>0)
			 return 1;
		 else if(result<0)
			 return -1;
		 else
			 return 0;
		 
		 
	}

}
