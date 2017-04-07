package com.gudeng.commerce.gd.report.data;

import java.util.Comparator;

import com.gudeng.commerce.gd.report.dto.ChannelPhoneResult;

public class PhoneDataComparetor implements Comparator<ChannelPhoneResult> {

	@Override
	public int compare(ChannelPhoneResult o1, ChannelPhoneResult o2) {
		 Long result = o1.getCount() - o2.getCount();
		 
		 if(result>0)
			 return 1;
		 else if(result<0)
			 return -1;
		 else
			 return 0;
	}

}
