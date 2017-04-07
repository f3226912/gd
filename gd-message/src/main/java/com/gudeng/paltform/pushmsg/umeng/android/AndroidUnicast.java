package com.gudeng.paltform.pushmsg.umeng.android;

import com.gudeng.paltform.pushmsg.umeng.AndroidNotification;

public class AndroidUnicast extends AndroidNotification {
	public AndroidUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}