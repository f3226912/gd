package com.gudeng.paltform.pushmsg.umeng.android;

import com.gudeng.paltform.pushmsg.umeng.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast() {
		try {
			this.setPredefinedKeyValue("type", "broadcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
