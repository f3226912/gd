package com.gudeng.commerce.gd.report.data;

import java.util.Comparator;

import com.gudeng.commerce.gd.report.dto.GoodsTradeResult;

public class GoodsTradeAmtComparetor implements Comparator<GoodsTradeResult> {

	@Override
	public int compare(GoodsTradeResult o1, GoodsTradeResult o2) {
		 double result = o1.getTradeAmt() - o2.getTradeAmt();
		 
		 if(result>0)
			 return 1;
		 else if(result<0)
			 return -1;
		 else
			 return 0;
		 
		 
	}

}
