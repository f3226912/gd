package com.gudeng.paltform.pushmsg.umeng.android;

import com.gudeng.paltform.pushmsg.umeng.AndroidNotification;

public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast() {
		try {
			this.setPredefinedKeyValue("type", "groupcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
