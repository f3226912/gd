package com.gudeng.paltform.pushmsg.umeng.ios;

import com.gudeng.paltform.pushmsg.umeng.IOSNotification;

public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast() {
		try {
			this.setPredefinedKeyValue("type", "broadcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
