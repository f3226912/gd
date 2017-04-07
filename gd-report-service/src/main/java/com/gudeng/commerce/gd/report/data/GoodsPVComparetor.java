package com.gudeng.commerce.gd.report.data;

import java.util.Comparator;

import com.gudeng.commerce.gd.report.dto.GoodsTradeResult;

public class GoodsPVComparetor implements Comparator<GoodsTradeResult> {

	@Override
	public int compare(GoodsTradeResult o1, GoodsTradeResult o2) {
		 Long result = o1.getPv() - o2.getPv();
		 
		 if(result>0)
			 return 1;
		 else if(result<0)
			 return -1;
		 else
			 return 0;
		 
		 
	}

}
